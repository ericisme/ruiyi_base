package cn.unis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.unis.dao.AlipayDiscountDao;
import cn.unis.entity.AlipayDiscount;
import cn.unis.service.interfaces.IAlipayDiscountService;


@Component
@Transactional(readOnly = false)
public class AlipayDiscountService implements IAlipayDiscountService {
	@Autowired
	private  AlipayDiscountDao alipayDiscountDao;
	
	@Override
	public void save(AlipayDiscount alipayDiscount) {
		alipayDiscountDao.save(alipayDiscount);
	}

	@Override
	public AlipayDiscount findOne() {
		List<AlipayDiscount> list = alipayDiscountDao.findAll();
		AlipayDiscount alipayDiscount = list!=null && list.size() > 0 ? list.get(list.size() - 1) : new AlipayDiscount();
		return alipayDiscount;
	}
}
