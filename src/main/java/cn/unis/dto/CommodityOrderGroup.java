package cn.unis.dto;

import java.util.List;

import cn.unis.entity.CommodityOrder;

/**
 * 按 收货地址/运单号 分组的礼品订单列表
 * @author Administrator
 *
 */
public class CommodityOrderGroup {
	
	/**
	 * 收货地址id, 查待发货订单列表时使用
	 */
	private Long groupDeliveryAddressId;
	
	/**
	 * 运单号, 查已发货订单列表时使用
	 */
	private String groupLogisticsCode;
	
	
	/**
	 * 对应收货地址下的订单列表
	 */
	private List<CommodityOrder> commodityOrderList;

	public Long getGroupDeliveryAddressId() {
		return groupDeliveryAddressId;
	}

	public void setGroupDeliveryAddressId(Long groupDeliveryAddressId) {
		this.groupDeliveryAddressId = groupDeliveryAddressId;
	}

	public List<CommodityOrder> getCommodityOrderList() {
		return commodityOrderList;
	}

	public void setCommodityOrderList(List<CommodityOrder> commodityOrderList) {
		this.commodityOrderList = commodityOrderList;
	}

	public String getGroupLogisticsCode() {
		return groupLogisticsCode;
	}

	public void setGroupLogisticsCode(String groupLogisticsCode) {
		this.groupLogisticsCode = groupLogisticsCode;
	}


	
	
}
