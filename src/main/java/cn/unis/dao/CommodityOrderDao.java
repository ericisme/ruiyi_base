package cn.unis.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.unis.entity.CommodityOrder;
import cn.unis.entity.DeliveryAddress;




/**
 * 订单实体的dao类
 */
public interface CommodityOrderDao extends JpaRepository<CommodityOrder, Long>,JpaSpecificationExecutor<CommodityOrder> {	
	@Query("select count(amount) from CommodityOrder where commodity.id = ?1 and status != 10 ")
	public long countDelivery(Long id);
//	
//	@Query("select deliveryAddress.id from CommodityOrder where status=30 group by deliveryAddress.id order by min(orderDate) asc")
//	public Page<Long> query30Group(Pageable pageable);
//	
//	@Query("select deliveryAddress.id from CommodityOrder where status=30 group by deliveryAddress.id order by min(orderDate) asc LIMIT 0,2")
//	public List<Long> query30Group();
	
	/**
	 * 根据收货地址查找对应的未发货订单列表 状态：30
	 * @param deliveryAddressId
	 * @return
	 */
	@Query("from CommodityOrder co where co.status=30 and co.deliveryAddress.id=?1")
	public List<CommodityOrder> list30CommodityOrderByDeliveryAddressId(Long deliveryAddressId);
	
	
	/**
	 * 根据 运单号 查找对应的已发货订单列表 状态：70
	 * @param deliveryAddressId
	 * @return
	 */
	@Query("from CommodityOrder co where co.status=70 and co.logisticsCode=?1")
	public List<CommodityOrder> list70CommodityOrderByLogisticsCode(String logisticsCode);
	
	
	/**
	 * 根据 运单号 查找对应的交易成功订单列表 状态：90
	 * @param deliveryAddressId
	 * @return
	 */
	@Query("from CommodityOrder co where co.status=90 and co.logisticsCode=?1")
	public List<CommodityOrder> list90CommodityOrderByLogisticsCode(String logisticsCode);
	
	
	/**
	 * 根据收货地址查找对应的 取消状态 订单列表 状态：10
	 * @param deliveryAddressId
	 * @return
	 */
	@Query("from CommodityOrder co where co.status=10 and co.deliveryAddress.id=?1")
	public List<CommodityOrder> list10CommodityOrderByDeliveryAddressId(Long deliveryAddressId);
	
	
	/**
	 * 查找已经到期的已发货订单
	 * @param date
	 * @return
	 */
	@Query("select co.id From CommodityOrder co where co.status = 70 and co.deadLineDate < ?1 ")
	public List<Long> list70CommodityOrderIdAfterDeadLine(Date date);
	
	/**
	 * 查找 未发货 订单记录数
	 * @param date
	 * @return
	 */
	@Query("select count(co.id) From CommodityOrder co where co.status = 30 ")
	public Long count10CommodityOrder();
	
}
