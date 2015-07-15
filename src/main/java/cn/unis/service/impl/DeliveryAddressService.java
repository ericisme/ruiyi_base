package cn.unis.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.unis.dao.DeliveryAddressDao;
import cn.unis.entity.DeliveryAddress;
import cn.unis.service.interfaces.IDeliveryAddressService;


@Component
@Transactional(readOnly = false)
public class DeliveryAddressService implements IDeliveryAddressService {
	@Autowired
	private DeliveryAddressDao deliveryAddressDao;
	/**
	 * 保存，未作判断
	 */
	@Override
	public void save(DeliveryAddress deliveryAddress) {
		deliveryAddressDao.save(deliveryAddress);
	}
	/**
	 * 判断记录是否存在
	 */
	@Override
	public DeliveryAddress isDeliveryAddressExist(String md5HashStr) {
		List<DeliveryAddress> deliveryAddressList = deliveryAddressDao.findBymd5Hash(md5HashStr);
		return deliveryAddressList != null ? deliveryAddressList.get(0) : null ;
	}

	/**
	 * 根据userKey查找最后一条填写的收货地址
	 */
	@Override
	public DeliveryAddress findTheLastDeliveryAddress(final String userKey) {
		Page<DeliveryAddress> deliveryAddressList = deliveryAddressDao.findAll(new Specification<DeliveryAddress>() {
			
			public Predicate toPredicate(Root<DeliveryAddress> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Path<String> expression = root.get("userKey");
				Predicate p = cb.equal(expression, userKey);
				predicateList.add(p);
				Predicate[] predicates = new Predicate[predicateList.size()];
				predicateList.toArray(predicates);
				if (predicates.length > 0) {
					return cb.and(predicates);
				} else {
					return cb.conjunction();
				}
			}
		}, new PageRequest(0,1,Sort.Direction.DESC, "id"));
		return StringUtils.isBlank(userKey)  || deliveryAddressList.getContent().size()==0 ? null : deliveryAddressList.getContent().get(0);
	}

	/**
	 * 根据id查找记录
	 */
	@Override
	public DeliveryAddress findById(Long id) {
		return deliveryAddressDao.findOne(id);
	}
	

	/**
	 * 根据id删除记录
	 */
	@Override
	public void delete(Long id) {
		deliveryAddressDao.delete(id);
	}

	/**
	 * 根据md5Hash删除记录
	 */
	@Override
	public void delete(String md5Hash) {
		deliveryAddressDao.deleteBymd5Hash(md5Hash);
	}

	/**
	 * 如果不存则保存 返回保存 否则不保存 返回原对象
	 */
	@Override
	public DeliveryAddress saveIfNotExist(DeliveryAddress deliveryAddress) {
		List<DeliveryAddress> deliveryAddressList = deliveryAddressDao.findBymd5Hash(deliveryAddress.getMd5Hash());
		if(deliveryAddressList.size() == 0){
			deliveryAddress.setCreateTime(new Date());
		}
		return deliveryAddressList.size() == 0 ? deliveryAddressDao.save(deliveryAddress) : deliveryAddressList.get(0);
	}
}
