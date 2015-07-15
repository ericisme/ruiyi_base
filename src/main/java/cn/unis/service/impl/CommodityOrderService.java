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

import org.apache.commons.lang3.StringUtils;
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

import cn.ruiyi.base.constants.Constants;
import cn.unis.dao.CommodityOrderDao;
import cn.unis.dto.CommodityOrderGroup;
import cn.unis.dto.PageForCommodityOrder;
import cn.unis.entity.CommodityOrder;
import cn.unis.entity.CommodityOrderStatus;

/**
 *  积分商城 订单的service类
 * @author eric
 *
 */
@Component
@Transactional(readOnly = true)
public class CommodityOrderService {

	@Autowired
	private CommodityOrderDao commodityOrderDao;
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * 根据订单ID查找订单
	 * @param id
	 * @return
	 */
	public CommodityOrder findById(Long id) {
		return commodityOrderDao.findOne(id);
	}
	
	/**
	 * 删除订单
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void delete(Long id) {
		commodityOrderDao.delete(id);
	}
	
	/**
	 *保存订单
	 */			
	@Transactional(readOnly=false)
	public CommodityOrder save(CommodityOrder commodityOrder){
		return commodityOrderDao.saveAndFlush(commodityOrder);
	}

	/**
	 * 查找已经到期的已发货订单List<Id>
	 * @param date
	 * @return
	 */
	public List<Long> list70CommodityOrderIdAfterDeadLine(){
		return commodityOrderDao.list70CommodityOrderIdAfterDeadLine(new Date());
	}
	/**
	 * 处理一条已到期的 已发货订单
	 * @param id
	 */
	@Transactional(readOnly=false)
	public void autoCompleteOneCommodityOrderById(Long id){
		CommodityOrder commodityOrder = commodityOrderDao.findOne(id);
		if(commodityOrder.getStatus() == 70){
			commodityOrder.setStatus(90);
			CommodityOrderStatus commodityOrderStatus = new CommodityOrderStatus();
			commodityOrderStatus.setStatus(90);
			commodityOrderStatus.setRemarks("系统自动确认");
			commodityOrderStatus.setUserType(1);
			commodityOrderStatus.setStatusDate(new Date());
			commodityOrder.addCommodityOrderStatus(commodityOrderStatus);
			commodityOrderDao.saveAndFlush(commodityOrder);			
		}
		
	}
	
	public long countDelivery(Long id){
		return commodityOrderDao.countDelivery(id);
	}
	
	public Page<CommodityOrder> findAll(Pageable pageable,final String userKey){
		
		return commodityOrderDao.findAll(new Specification<CommodityOrder>() {
			
			@Override
			public Predicate toPredicate(Root<CommodityOrder> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(userKey)) {
					Path<String> expression = root.get("userKey");
					Predicate p = cb.equal(expression, userKey);
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
	 * 批量保存
	 */
	@Transactional(readOnly = false)
	public boolean saveList(List<CommodityOrder> commodityOrder_list){
		for(CommodityOrder commodityOrder : commodityOrder_list){
			commodityOrderDao.saveAndFlush(commodityOrder);
			//this.save(commodityOrder);
		}		
		return true;
	}

//	/**
//	 * 查询待发货订单组
//	 */
//	public void query30Group(){
//		int page = 1;
//		//Pageable pageable = new PageRequest(page - 1, Constants.PAGE_SIZE);
//		Pageable pageable = new PageRequest(page - 1, Constants.PAGE_SIZE,
//					Sort.Direction.ASC, "min(orderDate)");
//		 
//		 Page<Long> address30 = commodityOrderDao.query30Group(pageable);
//		 for(Long deliverAddressId : address30.getContent()){
//			 System.out.println("================");
//			 System.out.println("id:"+deliverAddressId);
//			// System.out.println("id:"+deliverAddress.getId());
//			 //System.out.println("receiverName:"+deliverAddress.getReceiverName());
//			 System.out.println("================");
//		 }
//		;
//	}

	
	
	
	/**
	 * 查询待发货订单组 状态：30
	 */
	public PageForCommodityOrder query30GroupPage(int curPage, int sizePerPage, String _orderNum){
		PageForCommodityOrder pageForCommodityOrder =  new PageForCommodityOrder();
		pageForCommodityOrder.setCurPage(curPage);
		pageForCommodityOrder.setSizePerPage(sizePerPage);		
		int firstResult = (curPage-1)*sizePerPage;
		String where = " where status=30 ";
		//查询记录
		if(_orderNum!=null && !_orderNum.trim().equals("")){
			where +=" and orderNum=:orderNum ";
		}		
		String hql = "select deliveryAddress.id from CommodityOrder "+ where +" group by deliveryAddress.id order by min(orderDate) asc ";		
		Query query =((Session)entityManager.getDelegate()).createQuery(hql);
		if(_orderNum!=null && !_orderNum.trim().equals("")){
			query.setString("orderNum", _orderNum);
		}
		query.setMaxResults(sizePerPage);
	    query.setFirstResult(firstResult);    
	    List<Long> address30 =  query.list();
	    //查询记录数，分页用
		hql = "select count(distinct deliveryAddress.id) from CommodityOrder " + where;
		Query queryTotalSize =((Session)entityManager.getDelegate()).createQuery(hql);
		if(_orderNum!=null && !_orderNum.trim().equals("")){
			queryTotalSize.setString("orderNum", _orderNum);
		}
		Long totalElements = (Long)queryTotalSize.uniqueResult();
		pageForCommodityOrder.setTotalElements(totalElements);			 
		//按组 组装待发货订单记录		 
		List<CommodityOrderGroup> commodityOrderGroupList = new ArrayList<CommodityOrderGroup>();
		for(Long deliverAddressId : address30){
			CommodityOrderGroup commodityOrderGroup = new CommodityOrderGroup();
			commodityOrderGroup.setGroupDeliveryAddressId(deliverAddressId);
			commodityOrderGroup.setCommodityOrderList(this.list30CommodityOrderByDeliveryAddressId(deliverAddressId));
			commodityOrderGroupList.add(commodityOrderGroup);
		}
		pageForCommodityOrder.setCommodityOrderGroupList(commodityOrderGroupList);		 
		return pageForCommodityOrder;
	}	
	/**
	 * 根据收货地址查找对应的未发货订单列表 状态：30
	 * @param deliveryAddressId
	 * @return
	 */
	public List<CommodityOrder> list30CommodityOrderByDeliveryAddressId(Long deliveryAddressId){
		return commodityOrderDao.list30CommodityOrderByDeliveryAddressId(deliveryAddressId);
	}
	
	
	
	/**
	 * 查询已发货订单组 状态：70
	 */
	public PageForCommodityOrder query70GroupPage(int curPage, int sizePerPage, String _orderNum, String _logisticsCode){
		PageForCommodityOrder pageForCommodityOrder =  new PageForCommodityOrder();
		pageForCommodityOrder.setCurPage(curPage);
		pageForCommodityOrder.setSizePerPage(sizePerPage);		
		int firstResult = (curPage-1)*sizePerPage;
		String where = " where status=70 ";
		//查询记录
		if(_orderNum!=null && !_orderNum.trim().equals("")){
			where +=" and orderNum=:orderNum ";
		}		
		if(_logisticsCode!=null && !_logisticsCode.trim().equals("")){
			where +=" and logisticsCode=:logisticsCode ";
		}
		String hql = "select logisticsCode from CommodityOrder " + where + " group by logisticsCode order by min(orderDate) desc ";
		Query query =((Session)entityManager.getDelegate()).createQuery(hql);
		if(_orderNum!=null && !_orderNum.trim().equals("")){
			query.setString("orderNum", _orderNum);
		}
		if(_logisticsCode!=null && !_logisticsCode.trim().equals("")){
			query.setString("logisticsCode", _logisticsCode);
		}
	    query.setMaxResults(sizePerPage);
	    query.setFirstResult(firstResult);    
	    List<String> address70 =  query.list();
	    //查询记录总数（用于分页）
		hql = "select count(distinct logisticsCode) from CommodityOrder " + where;
		Query queryTotalSize =((Session)entityManager.getDelegate()).createQuery(hql);
		if(_orderNum!=null && !_orderNum.trim().equals("")){
			queryTotalSize.setString("orderNum", _orderNum);
		}
		if(_logisticsCode!=null && !_logisticsCode.trim().equals("")){
			queryTotalSize.setString("logisticsCode", _logisticsCode);
		}
		Long totalElements = (Long)queryTotalSize.uniqueResult();
		pageForCommodityOrder.setTotalElements(totalElements);			 
		 
		//按组 组装已发货订单记录		 
		List<CommodityOrderGroup> commodityOrderGroupList = new ArrayList<CommodityOrderGroup>();
		for(String logisticsCode : address70){
			CommodityOrderGroup commodityOrderGroup = new CommodityOrderGroup();
			commodityOrderGroup.setGroupLogisticsCode(logisticsCode);
			commodityOrderGroup.setCommodityOrderList(this.list70CommodityOrderByLogisticsCode(logisticsCode));
			commodityOrderGroupList.add(commodityOrderGroup);
		}
		pageForCommodityOrder.setCommodityOrderGroupList(commodityOrderGroupList);		 
		return pageForCommodityOrder;
	}	
	/**
	 * 根据 运单号 查找对应的已发货订单列表 状态：70
	 * @param deliveryAddressId
	 * @return
	 */
	public List<CommodityOrder> list70CommodityOrderByLogisticsCode(String logisticsCode){
		return commodityOrderDao.list70CommodityOrderByLogisticsCode(logisticsCode);
	}
	
	
	/**
	 * 查询交易成功订单组 状态：90
	 */
	public PageForCommodityOrder query90GroupPage(int curPage, int sizePerPage, String _orderNum, String _logisticsCode){
		PageForCommodityOrder pageForCommodityOrder =  new PageForCommodityOrder();
		pageForCommodityOrder.setCurPage(curPage);
		pageForCommodityOrder.setSizePerPage(sizePerPage);		
		int firstResult = (curPage-1)*sizePerPage;
		String where = " where status=90 ";
		//查询记录
		if(_orderNum!=null && !_orderNum.trim().equals("")){
			where +=" and orderNum=:orderNum ";
		}		
		if(_logisticsCode!=null && !_logisticsCode.trim().equals("")){
			where +=" and logisticsCode=:logisticsCode ";
		}
		String hql = "select logisticsCode from CommodityOrder " + where + " group by logisticsCode order by min(orderDate) desc ";
		Query query =((Session)entityManager.getDelegate()).createQuery(hql);
		if(_orderNum!=null && !_orderNum.trim().equals("")){
			query.setString("orderNum", _orderNum);
		}
		if(_logisticsCode!=null && !_logisticsCode.trim().equals("")){
			query.setString("logisticsCode", _logisticsCode);
		}
	    query.setMaxResults(sizePerPage);
	    query.setFirstResult(firstResult);    
	    List<String> address90 =  query.list();
	    //查询记录总数（用于分页）
		hql = "select count(distinct logisticsCode) from CommodityOrder " + where;
		Query queryTotalSize =((Session)entityManager.getDelegate()).createQuery(hql);
		if(_orderNum!=null && !_orderNum.trim().equals("")){
			queryTotalSize.setString("orderNum", _orderNum);
		}
		if(_logisticsCode!=null && !_logisticsCode.trim().equals("")){
			queryTotalSize.setString("logisticsCode", _logisticsCode);
		}
		Long totalElements = (Long)queryTotalSize.uniqueResult();
		pageForCommodityOrder.setTotalElements(totalElements);				 
		//按组 组装已发货订单记录		 
		List<CommodityOrderGroup> commodityOrderGroupList = new ArrayList<CommodityOrderGroup>();
		for(String logisticsCode : address90){
			CommodityOrderGroup commodityOrderGroup = new CommodityOrderGroup();
			commodityOrderGroup.setGroupLogisticsCode(logisticsCode);
			commodityOrderGroup.setCommodityOrderList(this.list90CommodityOrderByLogisticsCode(logisticsCode));
			commodityOrderGroupList.add(commodityOrderGroup);
		}
		pageForCommodityOrder.setCommodityOrderGroupList(commodityOrderGroupList);		 
		return pageForCommodityOrder;
	}	
	/**
	 * 根据 运单号 查找对应的交易成功订单列表 状态：90
	 * @param deliveryAddressId
	 * @return
	 */
	public List<CommodityOrder> list90CommodityOrderByLogisticsCode(String logisticsCode){
		return commodityOrderDao.list90CommodityOrderByLogisticsCode(logisticsCode);
	}
	
	
	
	/**
	 * 查询 取消状态 订单组 状态：10
	 */
	public PageForCommodityOrder query10GroupPage(int curPage, int sizePerPage, String _orderNum){
		PageForCommodityOrder pageForCommodityOrder =  new PageForCommodityOrder();
		pageForCommodityOrder.setCurPage(curPage);
		pageForCommodityOrder.setSizePerPage(sizePerPage);		
		int firstResult = (curPage-1)*sizePerPage;
		//查询记录
		String where = " where status=10 ";
		if(_orderNum!=null && !_orderNum.trim().equals("")){
			where +=" and orderNum=:orderNum ";
		}			
		String hql = "select deliveryAddress.id from CommodityOrder " + where + " group by deliveryAddress.id order by min(orderDate) asc ";
		Query query =((Session)entityManager.getDelegate()).createQuery(hql);
		if(_orderNum!=null && !_orderNum.trim().equals("")){
			query.setString("orderNum", _orderNum);
		}
	    query.setMaxResults(sizePerPage);
	    query.setFirstResult(firstResult);    
	    List<Long> address10 =  query.list();
	    //查询记录数（用于分页）
		hql = "select count(distinct deliveryAddress.id) from CommodityOrder "+where;
		Query queryTotalSize =((Session)entityManager.getDelegate()).createQuery(hql);
		if(_orderNum!=null && !_orderNum.trim().equals("")){
			queryTotalSize.setString("orderNum", _orderNum);
		}
		Long totalElements = (Long)queryTotalSize.uniqueResult();
		pageForCommodityOrder.setTotalElements(totalElements);		 
		//按组 组装待发货订单记录		 
		List<CommodityOrderGroup> commodityOrderGroupList = new ArrayList<CommodityOrderGroup>();
		for(Long deliverAddressId : address10){
			CommodityOrderGroup commodityOrderGroup = new CommodityOrderGroup();
			commodityOrderGroup.setGroupDeliveryAddressId(deliverAddressId);
			commodityOrderGroup.setCommodityOrderList(this.list10CommodityOrderByDeliveryAddressId(deliverAddressId));
			commodityOrderGroupList.add(commodityOrderGroup);
		}
		pageForCommodityOrder.setCommodityOrderGroupList(commodityOrderGroupList);		 
		return pageForCommodityOrder;
	}	
	/**
	 * 根据收货地址查找对应的取消状态订单列表 状态：10
	 * @param deliveryAddressId
	 * @return
	 */
	public List<CommodityOrder> list10CommodityOrderByDeliveryAddressId(Long deliveryAddressId){
		return commodityOrderDao.list10CommodityOrderByDeliveryAddressId(deliveryAddressId);
	}
	
	
	/**
	 * 查找 未发货 订单记录数
	 * @param date
	 * @return
	 */
	public Long count10CommodityOrder(){
		return commodityOrderDao.count10CommodityOrder();
	}
	
}
