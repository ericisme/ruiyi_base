package cn.unis.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.ruiyi.base.entity.User;
import cn.ruiyi.base.service.account.AccountService;
import cn.unis.dao.CommodityDao;
import cn.unis.dao.PictureDao;
import cn.unis.dao.TempUrlDao;
import cn.unis.entity.Commodity;
import cn.unis.entity.CommodityCategory;
import cn.unis.entity.Picture;
import cn.unis.service.interfaces.ICommodityService;
import cn.unis.utils.StringUtils2;

/**
 * 商品管理接口实现
 *
 * @author fanzz
 *
 */
@Component
@Transactional(readOnly = true)
public class CommodityService implements ICommodityService {

	private static int ENABEL_STATUS = 1;

	@Autowired
	private CommodityDao commodityDao;
	@Autowired
	private CommodityCategoryService commodityCategoryService;
	@Autowired
	private TempUrlDao ppt_TempUrlDao;
	@Autowired
	private AccountService accountService;
	@Autowired
	private PictureDao pictureDao;

	@PersistenceContext
	private EntityManager entityManager;


	/**
	 * 用于后台管理员 新增/编辑 的save方法
	 * @param commodity
	 * @return
	 */
	@Transactional(readOnly = false)
	public String saveForAdmin(Commodity commodity) {
		if (commodity != null) {
			List<Long> numberList = commodity.getPictureNumberList();
			if(numberList.size()>0){
				List<Picture> pictureList = (List<Picture>) pictureDao.findAll(numberList);

				String pictureNumberStr = "";
				for(Long number : numberList){
					for(Picture picture : pictureList){
						if(picture.getId().compareTo(number) == 0 ){
							pictureNumberStr = pictureNumberStr + number + ",";
						}
					}
				}

				//if(pictureNumberStr.length()>0){
				//	pictureNumberStr = pictureNumberStr.substring(0, pictureNumberStr.length()-1);
				//}
				commodity.setPictureNumber(pictureNumberStr);
				commodity.setPictureList(pictureList);
			}
			if(commodity.getIndexPagePictureNumber()!=null){
				Picture tempPicture = pictureDao.findOne(commodity.getIndexPagePictureNumber());
				commodity.setIndexPagePicture(tempPicture);
				if(tempPicture==null){
					commodity.setIndexPagePictureNumber(null);
				}
			}


			Object shiroUser = SecurityUtils.getSubject().getPrincipal();
			User curUser = accountService.findUserByLoginName(shiroUser.toString());

			if (commodity.getId() == null || commodity.getId() == 0L) {// 新增
				commodity.setStocksLastUpdateTime(new Date());
				commodity.setCreateAt(new Date());
				commodity.setCreateBy(curUser);

				Commodity tempName = commodityDao.findByName(commodity.getName());
				if(tempName!=null){
					return "error1";
				}
				Commodity tempCommodityNo = commodityDao.findByCommodityNo(commodity.getCommodityNo());
				if(tempCommodityNo!=null){
					return "error2";
				}
			} else {// 编辑
				Commodity oldCommodity = findById(commodity.getId());
				if(oldCommodity!=null && !oldCommodity.getName().equals(commodity.getName())){
					Commodity tempName = commodityDao.findByName(commodity.getName());
					if(tempName!=null){
						return "error1";
					}
				}
				if(oldCommodity!=null && !oldCommodity.getCommodityNo().equals(commodity.getCommodityNo())){
					Commodity tempCommodityNo = commodityDao.findByCommodityNo(commodity.getCommodityNo());
					if(tempCommodityNo!=null){
						return "error2";
					}
				}

				if(oldCommodity != null){
					commodity.setCreateAt(oldCommodity.getCreateAt());
					commodity.setCreateBy(oldCommodity.getCreateBy());
					if (oldCommodity.getStocks() != commodity.getStocks()) {
						commodity.setStocksLastUpdateTime(new Date());
					}else{
						commodity.setStocksLastUpdateTime(oldCommodity.getStocksLastUpdateTime());
					}
				}
			}
			commodityDao.save(commodity);
			return "success";
		} else {
			return "error";
		}
	}


	/**
	 * 直接保存，不作逻辑处理
	 * @param commodity
	 * @return
	 */
	public Commodity save(Commodity commodity){
		return commodityDao.saveAndFlush(commodity);
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
		Commodity commodity = findById(id);
		if (commodity != null) {
			commodity.setStatus(status);
			commodityDao.save(commodity);
		} else {
			flag = false;
		}
		return flag;
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		commodityDao.delete(id);
	}

	@Transactional(readOnly = false)
	@Override
	public void delete(String ids) {
		List<Long> idList = StringUtils2.getTFromString(ids, ",|，", Long.class, false);
		for(Long id : idList ){
			delete(id);
		}
	}

	@Transactional(readOnly = true)
	public Commodity findById(Long id) {
		return commodityDao.findOne(id);
	}

	public List<Commodity> getAllPPTWithEnabledStatus() {
		List<Commodity> commodityList = commodityDao
				.findByStatusOrderBySortNumberAsc(ENABEL_STATUS);
		return commodityList;
	}

	/**
	 * 列表查找
	 */
	@Transactional(readOnly = false)
	public Page<Commodity> findAll(final Long _commodityCategory_id, final String name,
			final Integer queryStatus, final Integer price,Pageable pageable) {


		return this.commodityDao.findAll(new Specification<Commodity>() {
			@Override
			public Predicate toPredicate(Root<Commodity> commodity,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(name)) {
					Path<String> expression = commodity.get("name");
					Predicate p = cb.like(expression, "%" + name + "%");
					predicateList.add(p);
				}

				if (queryStatus != null && queryStatus != -1) {
					Path<Integer> expression = commodity.get("status");
					Predicate p = cb.equal(expression, queryStatus);
					predicateList.add(p);
				}
				if(price!=null){
					Path<Integer> expression = commodity.get("price");
					Predicate p = cb.lessThan(expression, price);
					predicateList.add(p);
				}



				//if (StringUtils.isNotBlank(type) && !type.equals("all")) {
				//	Path<String> expression = commodity.get("type");
				//	Predicate p = cb.equal(expression, type);
				//	predicateList.add(p);
				//}
				/**
				 * 商品类别
				 */
				if(_commodityCategory_id != 0L){//全部
					final List<CommodityCategory> _commodityCategory_list= commodityCategoryService.getOneCommodityCategoryListByCommodityCategory(commodityCategoryService.findById(_commodityCategory_id));
					Path<CommodityCategory> commodityCategory_expression = commodity.get("commodityCategory");
					Predicate p = commodityCategory_expression.in(_commodityCategory_list);
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

	/**
	 * 根据类别查询商品list
	 */
	public List<Commodity> getCommodityListByCommoditCategoryId(Long CommoditCategoryId){
		return commodityDao.getCommodityListByCommoditCategoryId(CommoditCategoryId);
	}

	@Override
	public Commodity findTheLastPriceCommodity() {
		Pageable pageable = new PageRequest(0,1,Sort.Direction.ASC, "price");
		Page<Commodity> page = commodityDao.findAll(new Specification<Commodity>() {

			@Override
			public Predicate toPredicate(Root<Commodity> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<Integer> expression = root.get("status");
				Predicate p = cb.equal(expression, 1);
				predicateList.add(p);

				Predicate[] predicates = new Predicate[predicateList.size()];
				predicateList.toArray(predicates);

				if (predicates.length > 0) {
					return cb.and(predicates);
				} else {
					return cb.conjunction();
				}
			}
		}, pageable);
		Commodity commodity = null;
		if(page!=null&&page.getContent().size()>0){
			commodity = page.getContent().get(0);
		}
		return commodity;
	}

	/**
	 * 库存增加或减少	 
	 * 同时对已兑换量处理，库存减n已兑换量加n,库存加n已兑换量减n	  
	 * delta > 0 扣库存, 增加已兑换量
	 * delta < 0 增加库存, 扣已兑换量
	 */
	@Transactional(readOnly = false)
	public boolean updateStocks(Long id ,int delta){
		String queryStr ;
		if(delta > 0 ){
			queryStr = "update Commodity c set stocks = c.stocks - " + delta + " where id = " + id + " and c.stocks > 0 and c.stocks >= " + delta;
		}else{
			queryStr = "update Commodity c set stocks = c.stocks + " + Math.abs(delta ) + " where id = " + id ;
		}
		Session session = ((Session)entityManager.getDelegate());
		Query query = session.createQuery(queryStr);
		int ret = query.executeUpdate();
		
		//处理 已兑换量
		if(delta > 0 ){
			//足够库存才会执行
			if(ret>0){
				String updateExchangeCountHql = "update Commodity c set exchangeCount = c.exchangeCount + " + delta + " where id = " + id + " ";				
				Query query_for_updateExchangeCountHql = session.createQuery(updateExchangeCountHql);
				query_for_updateExchangeCountHql.executeUpdate();
			}			
		}else{
			String updateExchangeCountHql = "update Commodity c set exchangeCount = c.exchangeCount - " + Math.abs(delta ) + " where id = " + id ;
			Query query_for_updateExchangeCountHql = session.createQuery(updateExchangeCountHql);
			query_for_updateExchangeCountHql.executeUpdate();
		}		
		
		return ret > 0 ;
	}

}
