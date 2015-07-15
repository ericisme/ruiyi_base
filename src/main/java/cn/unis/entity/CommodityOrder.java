package cn.unis.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.collect.Lists;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.util.DateUtil;
/**
 * 订单
 * @author fanzz
 *
 */
@Entity
@Table(name = "unis_commodity_order",schema=Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CommodityOrder extends IdEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -667228046620070055L;
	/**
	 * 订单号，订单的唯一编码
	 */
	private String orderNum;
	@Column(unique=true)
	public String getOrderNum() {return orderNum;}
	public void setOrderNum(String orderNum) {this.orderNum = orderNum;}


	/**
	 * 会员用户id, 即walo的user_key
	 */
	private String userKey;	
	public String getUserKey() {return userKey;}
	public void setUserKey(String userKey) {this.userKey = userKey;}
	

	/**
	 * 商品
	 */
	private Commodity commodity;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="commodity_id", nullable=false)
	public Commodity getCommodity() {return commodity;}
	public void setCommodity(Commodity commodity) {this.commodity = commodity;}


	/**
	 * 数量
	 */
	private Integer amount;	
	public Integer getAmount() {return amount;}
	public void setAmount(Integer amount) {this.amount = amount;}
	


	/**
	 * 单价(即单间商品所需积分数)
	 */
	private Integer singlePrice;
	public Integer getSinglePrice() {return singlePrice;}
	public void setSinglePrice(Integer singlePrice) {this.singlePrice = singlePrice;}


	/**
	 * 合计价格
	 */
	private Integer totalPrice;	
	public Integer getTotalPrice() {return totalPrice;}
	public void setTotalPrice(Integer totalPrice) {this.totalPrice = totalPrice;}


	/**
	 * tx_code（扣减walo会员的世宇积分后用于回退扣减的码）
	 */
	private String txCode;	
	public String getTxCode() {	return txCode;}
	public void setTxCode(String txCode) {this.txCode = txCode;}	


	/**
	 * 收货地址 
	 */
	private DeliveryAddress deliveryAddress;	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="delivery_address_id", nullable=false)
	public DeliveryAddress getDeliveryAddress() {return deliveryAddress;}
	public void setDeliveryAddress(DeliveryAddress deliveryAddress) {this.deliveryAddress = deliveryAddress;}


	/**
	 * 留言（用户）
	 */
	private String leaveAmessage;	
	@Column(nullable=true,length=2048)
	public String getLeaveAmessage() {return leaveAmessage;}
	public void setLeaveAmessage(String leaveAmessage) {this.leaveAmessage = leaveAmessage;}


	/**
	 * 留言（管理员）
	 */
	private String adminAmessage;	
	@Column(nullable=true,length=2048)
	public String getAdminAmessage() {return adminAmessage;}
	public void setAdminAmessage(String adminAmessage) {this.adminAmessage = adminAmessage;}

	/**
	 * 订单状态
	 * 10 ：取消订单
	 * 30 ：已下订单
	 * 70 ：已发货
	 * 90 ：确认收货
	 */
	private Integer status;
	@Column(nullable=false,length=4)
	public Integer getStatus() {return status;}
	public void setStatus(Integer status) {this.status = status;}


	/**
	 * 订单生成日期
	 */
	private Date orderDate;
	public Date getOrderDate() {return orderDate;}
	public void setOrderDate(Date orderDate) {this.orderDate = orderDate;}


	/**
	 * 确认成功交易截止日期(为发货后起算15天, 临近最后的5天还未到货会员可以手动增加截止日期多15天)
	 */
	private Date deadLineDate;
	public Date getDeadLineDate() {	return deadLineDate;}
	public void setDeadLineDate(Date deadLineDate) {this.deadLineDate = deadLineDate;}
	
	
	/**
	 * 订单状态历史记录列表,一对多
	 */

	private List<CommodityOrderStatus> commodityOrderStatusList = Lists.newArrayList();
	@OneToMany(cascade = { CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.DETACH},mappedBy="commodityOrder")
	public List<CommodityOrderStatus> getCommodityOrderStatusList() {return commodityOrderStatusList;}
	public void setCommodityOrderStatusList(List<CommodityOrderStatus> commodityOrderStatusList) {this.commodityOrderStatusList = commodityOrderStatusList;}
	/**
	 * 该方法用于向CommodityOrder中加CommodityOrderStatus项 
	 * @param commodityOrderStatus
	 */
	public void addCommodityOrderStatus(CommodityOrderStatus commodityOrderStatus){
		commodityOrderStatus.setCommodityOrder(this);//用关系维护端来维护关系   
		if(commodityOrderStatusList==null){
			commodityOrderStatusList = Lists.newArrayList();
		}
		this.commodityOrderStatusList.add(commodityOrderStatus);		
	}
	
	
	
	/**
	 * 物流
	 */
	private CommodityLogistics commodityLogistics;	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="commodity_logistics_id", nullable=true)
	public CommodityLogistics getCommodityLogistics() {	return commodityLogistics;}
	public void setCommodityLogistics(CommodityLogistics commodityLogistics) {this.commodityLogistics = commodityLogistics;}


	/**
	 * 运单号
	 */
	private String logisticsCode;
	public String getLogisticsCode() {return logisticsCode;}
	public void setLogisticsCode(String logisticsCode) {this.logisticsCode = logisticsCode;}
	
//	/**
//	 * 收货地址 
//	 */
//	private String receiveAddress;
//	@Column(nullable=false,length=1024)
//	public String getReceiveAddress() {	return receiveAddress;}
//	public void setReceiveAddress(String receiveAddress) {this.receiveAddress = receiveAddress;}
//
//
//	/**
//	 * 收货人名字
//	 */
//	private String receiveName;	
//	public String getReceiveName() {return receiveName;}
//	public void setReceiveName(String receiveName) {this.receiveName = receiveName;}
//
//
//	/**
//	 * 收货人手机号
//	 */
//	private String receivePhone;	
//	public String getReceivePhone() {return receivePhone;}
//	public void setReceivePhone(String receivePhone) {this.receivePhone = receivePhone;}
//	
//
//	/**
//	 * 邮政编码
//	 */
//	private String receivePostcode;	
//	public String getReceivePostcode() {return receivePostcode;}
//	public void setReceivePostcode(String receivePostcode) {this.receivePostcode = receivePostcode;}
}
