package cn.unis.service.interfaces;

import cn.unis.entity.DeliveryAddress;

public interface IDeliveryAddressService {
	/**
	 * 保存 
	 */
	public void save(DeliveryAddress deliveryAddress);
	/**
	 * 根据md5hash判断是否有重复记录
	 */
	public DeliveryAddress isDeliveryAddressExist(String md5HashStr);
	/**
	 * 最新一条记录 
	 */
	public DeliveryAddress findTheLastDeliveryAddress(String userKey);
	/**
	 * 根据ID查找 
	 */
	public DeliveryAddress findById(Long id);
	/**
	 * 删除(根据id)
	 */
	public void delete(Long id);
	/**
	 * 删除(根据md5hash)
	 */
	public void delete(String md5Hash);
	/**
	 * 保存 （不存在=true 存在=false）
	 */
	public DeliveryAddress saveIfNotExist(DeliveryAddress deliveryAddress);
}
