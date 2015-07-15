package cn.unis.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.ruiyi.base.constants.Constants;
/**
 * 商品销售记录
 * @author fanzz
 *
 */
@Entity
@Table(name = "unis_commodity_sale_record",schema=Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CommoditySaleRecord extends IdEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4841103146746227569L;
	/**
	 * 所属商品
	 */
	private Commodity commodity;
	/**
	 * 数量
	 */
	private int amount;
	/**
	 * 总价
	 */
	private double totalPrice;
	/**
	 * 扩展属性，与商品的扩展属性不同，这个用于描述其他可选类型（颜色：红色，橙色，黄色）
	 */
	private String extendedAttribute;
	
	@OneToOne
	@JoinColumn(name="commodity_id")
	public Commodity getCommodity() {
		return commodity;
	}
	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getExtendedAttribute() {
		return extendedAttribute;
	}
	public void setExtendedAttribute(String extendedAttribute) {
		this.extendedAttribute = extendedAttribute;
	}
	
	
}
