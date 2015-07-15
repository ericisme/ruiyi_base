package cn.unis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;

import cn.ruiyi.base.service.account.AccountService;
import cn.unis.dao.PictureDao;
import cn.unis.dao.TempUrlDao;
import cn.unis.entity.Picture;
import cn.unis.service.interfaces.IPictureService;
import cn.unis.utils.FileUtils;
import cn.unis.utils.PictureUploader;
import cn.unis.utils.StringUtils2;

/**
 * 图片管理接口实现
 *
 * @author fanzz
 *
 */
@Component
@Transactional(readOnly = true)
public class PictureService implements IPictureService {

	@PersistenceContext
	private EntityManager em;
	@Autowired
	private PictureDao pictureDao;
	@Autowired
	private TempUrlDao tempUrlDao;
	@Autowired
	private AccountService accountService;

	@Transactional(readOnly = false)
	public void save(Picture picture){
		pictureDao.save(picture);
	}
	@Transactional(readOnly = false)
	public boolean save(Picture picture,String contextPath) {
		boolean flag = true;
		if (picture != null) {
			if (picture.getId() == null || picture.getId() == 0) {// 新增
				movePicture(picture , contextPath);
			} else {// 编辑
				Picture oldPicture = findById(picture.getId());
				if (oldPicture != null && !picture.getUrl().equals(oldPicture.getUrl())) {
					movePicture(picture , contextPath);
					PictureUploader.delete(contextPath + oldPicture.getUrl());
					PictureUploader.delete(contextPath + oldPicture.getPreviewUrl());
					PictureUploader.delete(contextPath + oldPicture.getPreviewLargeUrl());
				}
			}
			picture.setUrl(FileUtils.replace(picture.getUrl()));
			picture.setPreviewUrl(FileUtils.replace(picture.getPreviewUrl()));
			picture.setPreviewLargeUrl(FileUtils.replace(picture.getPreviewLargeUrl()));
			pictureDao.save(picture);
		} else {
			flag = false;
		}
		return flag;
	}

	private void movePicture(Picture picture,String contextPath){
		String urlPicName = PictureUploader.getPicName(picture.getUrl());
		PictureUploader.move(contextPath + picture.getUrl(), contextPath + ORIGINAL + urlPicName);
		picture.setUrl(ORIGINAL + urlPicName);
		String previewPicName = PictureUploader.getPicName(picture.getPreviewUrl());
		PictureUploader.move(contextPath + picture.getPreviewUrl(), contextPath + PREVIEW + previewPicName);
		picture.setPreviewUrl(PREVIEW + previewPicName);
		String previewLargePicName = PictureUploader.getPicName(picture.getPreviewLargeUrl());
		PictureUploader.move(contextPath + picture.getPreviewLargeUrl(), contextPath + PREVIEWLARGE + previewLargePicName);
		picture.setPreviewLargeUrl(PREVIEWLARGE + previewLargePicName);

	}

	/**
	 * 启用or停用
	 *
	 * @param id
	 * @param status
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean changeStatus(Long id, Integer status) {
		boolean flag = true;
		Picture picture = findById(id);
		if (picture != null) {
			picture.setStatus(status);
			pictureDao.save(picture);
		} else {
			flag = false;
		}
		return flag;
	}

	@Transactional(readOnly = false)
	public void delete(Long id,String contextPath) {
		Picture picture = findById(id);
		pictureDao.delete(id);
		pictureDao.flush();
		Picture afterDelete = findById(id);
		if(afterDelete==null){
			PictureUploader.delete(contextPath + picture.getUrl());
			PictureUploader.delete(contextPath + picture.getPreviewUrl());
			PictureUploader.delete(contextPath + picture.getPreviewLargeUrl());
		}
	}

	@Transactional(readOnly = false)
	@Override
	public void delete(String ids,String rootPath) {
		List<Long> idList = StringUtils2.getTFromString(ids, ",|，", Long.class,false);
		for(Long id : idList ){
			delete(id, rootPath);
		}
	}

	@Transactional(readOnly = true)
	public Picture findById(Long id) {
		return pictureDao.findOne(id);
	}
	public List<Picture> findByIds(List<Long> ids){
		return (List<Picture>) pictureDao.findAll(ids);
	}

	public List<Picture> getAllWithEnabledStatus() {
		List<Picture> list = pictureDao.findByStatusOrderByIdAsc(ENABEL_STATUS);
		return list;
	}


	/**
	 * @param type 图片类型
	 * @param name 图片名称
	 * @param status 状态
	 * @param id
	 *
	 */
	@Transactional(readOnly = false)
	public PicturePageable findAll(final String type,final String name,final Integer status, final Long id, String numStr,Pageable pageable) {
		String where = null;

		//===============拼凑where方式1===============
		/*
		where = "";
		if (StringUtils.isNotBlank(name)) {
			where += " name like '%" + name + "%' ";
		}
		if (status != null && status != -1) {
			where += (StringUtils.isNotBlank(where) ?  " and status = ": " status = " ) + status;
		}
		if (StringUtils.isNotBlank(type) && !type.equals("all")) {
			where += (StringUtils.isNotBlank(where) ?  " and type = " : " type = ") + type;
		}
		if(id!=null){
			where += (StringUtils.isNotBlank(where) ? " and id = " : " id = ") + id;
		}
		if(StringUtils.isNotBlank(where)){
			where = " where " + where;
		}
		*/
		//===============end of 1===============

		//===============拼凑where方式2===============
		List<String> whereList = Lists.newArrayList();
		if(StringUtils.isNotBlank(name)){
			whereList.add(" name like '%" + name + "%' ");
		}
		if(status != null && status != -1){
			whereList.add(" status = " + status);
		}
		if(StringUtils.isNotBlank(type) && !type.equals("all")){
			whereList.add(" type = " + type);
		}
		if(id!=null){
			whereList.add(" id = " + id);
		}
		where = whereList.toString().replaceAll(",", " and ");
		where = where.substring(1,where.length() - 1);//去除[]
		if(StringUtils.isNotBlank(where)){
			where = " where " + where;
		}
		//===============end of 2===============

		String queryStr = "from Picture p " + where + " order by instr(" + numStr + ",concat(',' , id , ',')) desc,id desc";
		Session session = (Session)em.getDelegate();
		Query query = session.createQuery(queryStr);
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		@SuppressWarnings("unchecked")
		List<Picture> pictureList = (List<Picture>)query.list();
		String queryStrCount = "select count(*) from Picture " + where ;
		Long pageSize = (Long)((Session)em.getDelegate()).createQuery(queryStrCount).uniqueResult();
		if(pageSize==null){
			pageSize = 0L;
		}
		PicturePageable pp = new PicturePageable();
		pp.setPictureList(pictureList);
		pp.setSize(pageSize);
		return pp;
	}
	/**
	 * 保存后返回文件的路径，用于预览图片
	 *
	 * @param file
	 * @return
	 */
	@Transactional(readOnly = false)
	public String savePicture(MultipartFile file, String contextPath) {
		String url = PictureUploader.save(file, contextPath);
		return url;
	}


	@Transactional(readOnly = false)
	@Override
	public Page<Picture> findAll(final String type,final String name,final Integer queryStatus, final Long id,Pageable pageable) {
		return this.pictureDao.findAll(new Specification<Picture>() {
			@Override
			public Predicate toPredicate(Root<Picture> picture, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				setNamePredicate(name, predicateList, cb, picture);
				setStatusPredicate(queryStatus, predicateList, cb, picture);
				setTypePredicate(type, predicateList, cb, picture);
				setIdPredicate(id, predicateList, cb, picture);
				Predicate[] predicates = new Predicate[predicateList.size()];
				predicateList.toArray(predicates);

				return predicates.length > 0 ? cb.and(predicates) :cb.conjunction();
			}
		}, pageable);
	}

	@Override
	public Page<Picture> findAll(final String pictrueType, final String pictrueName,
			final Integer queryStatus, Pageable pageable) {
		return this.pictureDao.findAll(new Specification<Picture>() {
			@Override
			public Predicate toPredicate(Root<Picture> picture, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				setNamePredicate(pictrueName, predicateList, cb, picture);
				setStatusPredicate(queryStatus, predicateList, cb, picture);
				setTypePredicate(pictrueType, predicateList, cb, picture);

				Predicate[] predicates = new Predicate[predicateList.size()];
				predicateList.toArray(predicates);

				return predicates.length > 0 ? cb.and(predicates) :cb.conjunction();
			}
		}, pageable);
	}


	private void setNamePredicate(String name,List<Predicate> predicateList,CriteriaBuilder cb,Root<Picture> picture){
		if (StringUtils.isNotBlank(name)) {
			Path<String> expression = picture.get("name");
			Predicate p = cb.like(expression, "%" + name + "%");
			predicateList.add(p);
		}
	}
	private void setStatusPredicate(Integer status,List<Predicate> predicateList,CriteriaBuilder cb,Root<Picture> picture){
		if (status != null && status != -1) {
			Path<Integer> expression = picture.get("status");
			Predicate p = cb.equal(expression, status);
			predicateList.add(p);
		}
	}
	private void setTypePredicate(String type,List<Predicate> predicateList,CriteriaBuilder cb,Root<Picture> picture){
		if (StringUtils.isNotBlank(type) && !type.equals("all")) {
			Path<String> expression = picture.get("type");
			Predicate p = cb.equal(expression, type);
			predicateList.add(p);
		}
	}
	private void setIdPredicate(Long id,List<Predicate> predicateList,CriteriaBuilder cb,Root<Picture> picture){
		if(id!=null){
			Path<Long> expression = picture.get("id");
			Predicate p = cb.equal(expression,id);
			predicateList.add(p);
		}
	}

}
