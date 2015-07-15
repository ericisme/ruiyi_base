package cn.unis.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.ruiyi.base.constants.Constants;

/**
 * 支付宝充值折扣
 * @author fanzz
 *
 */
@Entity
@Table(name = "backend_alipay_discount", schema = Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AlipayDiscount extends IdEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1059131290469257441L;
	/**
	 * 折扣
	 */
	private float discount;
	/**
	 * 有效期开始
	 */
	private Date effectiveBegin;
	/**
	 * 有效期结束
	 */
	private Date effectiveEnd;
	
	
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public Date getEffectiveBegin() {
		return effectiveBegin;
	}
	public void setEffectiveBegin(Date effectiveBegin) {
		this.effectiveBegin = effectiveBegin;
	}
	public Date getEffectiveEnd() {
		return effectiveEnd;
	}
	public void setEffectiveEnd(Date effectiveEnd) {
		this.effectiveEnd = effectiveEnd;
	}
	
}
