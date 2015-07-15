package cn.unis.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.ruiyi.base.service.account.AccountService;
import cn.unis.dao.PPTDao;
import cn.unis.dao.TempUrlDao;
import cn.unis.entity.PPT;
import cn.unis.service.interfaces.IPPTService;
import cn.unis.utils.FileUtils;
import cn.unis.utils.PictureUploader;
import cn.unis.utils.StringUtils2;

/**
 * 幻灯片管理接口实现
 *
 * @author fanzz
 *
 */
@Component
@Transactional(readOnly = true)
public class PPTService implements IPPTService {

	@Autowired
	private PPTDao pptDao;
	@Autowired
	private TempUrlDao ppt_TempUrlDao;
	@Autowired
	private AccountService accountService;

	@Transactional(readOnly = false)
	public boolean save(PPT ppt,String contextPath) {
		boolean flag = true;
		if (ppt != null) {
			if (ppt.getId() == null || ppt.getId() == 0) {// 新增
//				String[] tmpUrls = fileUtils.movePicture(contextPath,ppt.getUrl(), ppt.getPreviewUrl());
//				ppt.setUrl(tmpUrls[0]);
//				ppt.setPreviewUrl(tmpUrls[1]);
				movePictrue(ppt, contextPath);
				ppt.setUploadDate(new Date());
			} else {// 编辑
				PPT oldPPT = findById(ppt.getId());
				if (oldPPT != null && !ppt.getUrl().equals(oldPPT.getUrl())) {//旧图片与原图片不同
					movePictrue(ppt, contextPath);
//					String[] tmpUrls = fileUtils.movePicture(contextPath,ppt.getUrl(),ppt.getPreviewUrl());
//					ppt.setUrl(tmpUrls[0]);//保存新地址
//					ppt.setPreviewUrl(tmpUrls[1]);
					ppt.setUploadDate(new Date());

					PictureUploader.delete(contextPath + oldPPT.getUrl());
					PictureUploader.delete(contextPath + oldPPT.getPreviewUrl());
				}
			}
			ppt.setUrl(FileUtils.replace(ppt.getUrl()));
			ppt.setPreviewUrl(FileUtils.replace(ppt.getPreviewUrl()));
			pptDao.save(ppt);
		} else {
			flag = false;
		}
		return flag;
	}
	private void movePictrue(PPT ppt,String contextPath){
		String picName = PictureUploader.getPicName(ppt.getUrl());
		boolean picFlag = PictureUploader.move(contextPath + ppt.getUrl(), contextPath + ORIGINAL + picName);
		if(picFlag){
			ppt.setUrl(ORIGINAL + picName);
		}
		boolean prePicFlag = PictureUploader.move(contextPath + ppt.getPreviewUrl(), contextPath + PREVIEW + picName);
		if(prePicFlag){
			ppt.setPreviewUrl(PREVIEW + picName);
		}
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
		PPT ppt = findById(id);
		if (ppt != null) {
			ppt.setStatus(status);
			pptDao.save(ppt);
		} else {
			flag = false;
		}
		return flag;
	}

	@Transactional(readOnly = false)
	public void delete(Long id,String contextPath) {
		PPT ppt = findById(id);
		PictureUploader.delete(contextPath + ppt.getUrl());
		PictureUploader.delete(contextPath + ppt.getPreviewUrl());
		pptDao.delete(id);
	}

	@Transactional(readOnly = false)
	@Override
	public void delete(String ids,String rootPath) {
		List<Long> idList = StringUtils2.getTFromString(ids, ",|，", Long.class, false);
		for(Long id : idList ){
			delete(id, rootPath);
		}
	}

	@Transactional(readOnly = true)
	public PPT findById(Long id) {
		return pptDao.findOne(id);
	}

	public List<PPT> getAllPPTWithEnabledStatus() {
		List<PPT> pptList = pptDao.findByStatusOrderBySortNumberAsc(ENABEL_STATUS);
		return pptList;
	}

	/**
	 * 列表查找
	 */
	@Transactional(readOnly = false)
	public Page<PPT> findAll(final String pictrueName,
			final Integer queryStatus, Pageable pageable,String rootPath) {

		return this.pptDao.findAll(new Specification<PPT>() {
			@Override
			public Predicate toPredicate(Root<PPT> ppt, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(pictrueName)) {
					Path<String> expression = ppt.get("pictrueName");
					Predicate p = cb.like(expression, "%" + pictrueName + "%");
					predicateList.add(p);
				}

				if (queryStatus != null && queryStatus != -1) {
					Path<Integer> expression = ppt.get("status");
					Predicate p = cb.equal(expression, queryStatus);
					predicateList.add(p);
				}

				Predicate[] predicates = new Predicate[predicateList.size()];
				predicateList.toArray(predicates);

				if (predicates.length > 0) {
					return cb.and(predicates);
				} else {
					return cb.conjunction();
				}
			}
		}, pageable);
	}
	@Override
	public String savePicture(MultipartFile file, String directory) {
		String url = PictureUploader.save(file,directory);
		return url;
	}

}
