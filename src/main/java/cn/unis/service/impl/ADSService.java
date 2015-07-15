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
import cn.unis.dao.ADSCountDao;
import cn.unis.dao.ADSDao;
import cn.unis.dao.TempUrlDao;
import cn.unis.entity.ADS;
import cn.unis.entity.ADSCount;
import cn.unis.service.interfaces.IADSService;
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
public class ADSService implements IADSService {

	@Autowired
	private ADSDao adsDao;
	@Autowired
	private TempUrlDao ads_TempUrlDao;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ADSCountDao adsCountDao;

	@Transactional(readOnly = false)
	public boolean save(ADS ads,String contextPath) {
		boolean flag = true;
		if (ads != null) {
			if (ads.getId() == null || ads.getId() == 0) {// 新增
				movePictrue(ads, contextPath);
				ads.setUploadDate(new Date());
			} else {// 编辑
				ADS oldads = findById(ads.getId());
				if (oldads != null && !ads.getUrl().equals(oldads.getUrl())) {//旧图片与原图片不同
					movePictrue(ads, contextPath);
					ads.setUploadDate(new Date());

					PictureUploader.delete(contextPath + oldads.getUrl());
					PictureUploader.delete(contextPath + oldads.getPreviewUrl());
				}
			}
			ads.setUrl(FileUtils.replace(ads.getUrl()));
			ads.setPreviewUrl(FileUtils.replace(ads.getPreviewUrl()));
			adsDao.save(ads);
		} else {
			flag = false;
		}
		return flag;
	}
	private void movePictrue(ADS ads,String contextPath){
		String picName = PictureUploader.getPicName(ads.getUrl());
		boolean picFlag = PictureUploader.move(contextPath + ads.getUrl(), contextPath + ORIGINAL + picName);
		if(picFlag){
			ads.setUrl(ORIGINAL + picName);
		}
		boolean prePicFlag = PictureUploader.move(contextPath + ads.getPreviewUrl(), contextPath + PREVIEW + picName);
		if(prePicFlag){
			ads.setPreviewUrl(PREVIEW + picName);
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
		ADS ads = findById(id);
		if (ads != null) {
			ads.setStatus(status);
			adsDao.save(ads);
		} else {
			flag = false;
		}
		return flag;
	}

	@Transactional(readOnly = false)
	public void delete(Long id,String contextPath) {
		ADS ads = findById(id);
		PictureUploader.delete(contextPath + ads.getUrl());
		PictureUploader.delete(contextPath + ads.getPreviewUrl());
		adsDao.delete(id);
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
	public ADS findById(Long id) {
		return adsDao.findOne(id);
	}

	public List<ADS> getAllADSWithEnabledStatus() {
		List<ADS> adsList = adsDao.findByStatusOrderBySortNumberAsc(ENABEL_STATUS);
		return adsList;
	}

	/**
	 * 列表查找
	 */
	@Transactional(readOnly = false)
	public Page<ADS> findAll(final String pictrueName,
			final Integer queryStatus, Pageable pageable,String rootPath) {

		return this.adsDao.findAll(new Specification<ADS>() {
			@Override
			public Predicate toPredicate(Root<ADS> ads, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(pictrueName)) {
					Path<String> expression = ads.get("adsName");
					Predicate p = cb.like(expression, "%" + pictrueName + "%");
					predicateList.add(p);
				}

				if (queryStatus != null && queryStatus != -1) {
					Path<Integer> expression = ads.get("status");
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

	@Transactional(readOnly=true)
	public List<ADS> getTopItems(Pageable pageable){
		return adsDao.getTopItems(pageable).getContent();
	}

	@Transactional(readOnly=false)
	@Override
	public void updateCount(int count) {
		List<ADSCount> adsCountList = adsCountDao.findAll();
		if(adsCountList.size() > 0){
			ADSCount adsCount = adsCountList.get(0);
			adsCount.setCount(count);
			adsCountDao.save(adsCount);
		}else{
			ADSCount adsCount = new ADSCount();
			adsCount.setId(0L);
			adsCount.setCount(count);
			adsCountDao.save(adsCount);
		}
 	}
	@Transactional(readOnly=true)
	public int getTheCount(){
		int count_ = 0;
		List<ADSCount> adsCountList = adsCountDao.findAll();
		if(adsCountList.size() > 0 ){
			count_ = adsCountList.get(0).getCount();
		}
		if(count_ < 0){
			count_ = 3;
		}
		return count_;

	}

}
