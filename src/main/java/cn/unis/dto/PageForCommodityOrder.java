package cn.unis.dto;

import java.util.List;

/**
 * 出于按 收货地址/运单号 分组的订单 分页列表
 * @author Administrator
 *
 */
public class PageForCommodityOrder {
	
	/**
	 * hql查找出来的收货地址id列表
	 */
	//private List<Long> groupDeliveryAddressIds;
	
	/**
	 * 按收货地址分组的礼品订单列表的列表
	 */
	private List<CommodityOrderGroup> commodityOrderGroupList;
	
	/**
	 * 当前页
	 */
	private int curPage;
	/**
	 * 每页条数
	 */
	private int sizePerPage;
	/**
	 * 总记录数
	 */
	private Long totalElements;
	
	
	//public List<Long> getGroupDeliveryAddressIds() {
	//	return groupDeliveryAddressIds;
	//}
	//public void setGroupDeliveryAddressIds(List<Long> groupDeliveryAddressIds) {
	//	this.groupDeliveryAddressIds = groupDeliveryAddressIds;
	//}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getSizePerPage() {
		return sizePerPage;
	}
	public void setSizePerPage(int sizePerPage) {
		this.sizePerPage = sizePerPage;
	}
	public Long getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}
	public List<CommodityOrderGroup> getCommodityOrderGroupList() {
		return commodityOrderGroupList;
	}
	public void setCommodityOrderGroupList(
			List<CommodityOrderGroup> commodityOrderGroupList) {
		this.commodityOrderGroupList = commodityOrderGroupList;
	}

	
	
	
	
}
