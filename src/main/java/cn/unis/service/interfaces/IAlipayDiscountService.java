package cn.unis.service.interfaces;

import cn.unis.entity.AlipayDiscount;

/**
 * 
 * @author fanzz
 *
 */
public interface IAlipayDiscountService {

	public void save(AlipayDiscount alipayDiscount);
	
	public AlipayDiscount findOne();
	
}
