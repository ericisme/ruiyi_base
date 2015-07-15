package cn.unis.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.ruiyi.base.constants.Constants;
/**
 * 收货地址
 * @author fanzz
 *
 */
@Entity
@Table(name = "unis_delivery_address", schema = Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeliveryAddress extends IdEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3197512209180942078L;
	/**
	 * 收货人姓名
	 */
	private String receiverName;
	/**
	 * 邮编
	 */
	private String zipcode;
	/**
	 * 收货地址
	 */
	private String address;
	/**
	 * 电话号码
	 */
	private String cellNumber;
	/**
	 * userKey
	 */
	private String userKey;
	/**
	 * 校验（md5（收货人姓名+邮编+收货地址+电话号码+userKey））
	 */
	private String md5Hash;
	/**
	 * 记录时间
	 */
	private Date createTime;
	
	@NotNull
	@Column(length=255)
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
	@NotNull
	@Column(length=255)
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	@NotNull
	@Column(length=65536)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@NotNull
	@Column(length=255)
	public String getCellNumber() {
		return cellNumber;
	}
	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}
	
	@NotNull
	@Column(length=255)
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	
	@NotNull
	@Column(length=255,unique=true)
	public String getMd5Hash() {
		return md5Hash;
	}

	public void setMd5Hash(String md5Hash) {
		this.md5Hash = md5Hash;
	}
	
	@NotNull
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Transient
	public boolean isAllNotNull(){
		return  StringUtils.isNotBlank(receiverName) && StringUtils.isNotBlank(zipcode) && 
				StringUtils.isNotBlank(address) && StringUtils.isNotBlank(cellNumber)  ;
	}
	
}
