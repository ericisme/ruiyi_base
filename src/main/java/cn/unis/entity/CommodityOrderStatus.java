package cn.unis.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.entity.User;
/**
 * 订单 状态 表
 * @author eric
 *
 */
@Entity
@Table(name = "unis_commodity_order_status",schema=Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CommodityOrderStatus extends IdEntity implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 168578683379578948L;
	/**
	 * 所属订单
	 */
	private CommodityOrder commodityOrder;	
	@ManyToOne(fetch=FetchType.LAZY,cascade = {CascadeType.ALL },optional=false )
	@JoinColumn(name="commodity_order_id", nullable=false)
	public CommodityOrder getCommodityOrder() {	return commodityOrder;}
	public void setCommodityOrder(CommodityOrder commodityOrder) {this.commodityOrder = commodityOrder;}

	
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
	 * 状态备注/留言
	 */
	private String remarks;	
	@Column(nullable=true,length=1024)
	public String getRemarks() {return remarks;}
	public void setRemarks(String remarks) {this.remarks = remarks;}


	/**
	 * 操作人类型，1为walo会员, 2为订单管理员
	 */
	private Integer userType;
	@Column(nullable=false,length=2)
	public Integer getUserType() {return userType;}
	public void setUserType(Integer userType) {this.userType = userType;}


	/**
	 * 操作人，订单管理员，userType=2时必填
	 */
	private User user;	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	public User getUser() {	return user;}
	public void setUser(User user) {this.user = user;}

	
	/**
	 * 操作时间，必填
	 */
	private Date statusDate;
	@Column(nullable=false)
	public Date getStatusDate() {return statusDate;}
	public void setStatusDate(Date statusDate) {this.statusDate = statusDate;}
	
	
	
	//因为userkey可从订单的实体中获得，所以这里暂时不需要了	
//	/**
//	 * 操作人,walo会员 user_key, userType=1时必填
//	 */
//	private String waloUserKey;	
//	public String getWaloUserKey() {return waloUserKey;}
//	public void setWaloUserKey(String waloUserKey) {this.waloUserKey = waloUserKey;}
	

	
	
	
	
}
