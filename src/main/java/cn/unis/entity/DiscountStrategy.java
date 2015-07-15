package cn.unis.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.ruiyi.base.constants.Constants;
/**
 * 打折策略（组合打折，组合优惠，数量打折）<br>
 * 推荐
 * @author fanzz
 *
 */
//@Entity
//@Table(name = "unis_discount_strategy",schema=Constants.BASE)
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DiscountStrategy extends IdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6200467028983140576L;
	/**
	 * 1.N种商品组合，打折，优惠，包邮
	 * 2.1种商品N件
	 * 3.支付宝充值优惠（冲100送5，累计充值优惠）
	 * 
	 */
	
}
