package cn.unis.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.unis.entity.DeliveryAddress;

public interface DeliveryAddressDao extends JpaRepository<DeliveryAddress, Long>,
JpaSpecificationExecutor<DeliveryAddress>{
	
	public List<DeliveryAddress> findBymd5Hash(String md5Hash);
	
	@Modifying
	@Query("delete from DeliveryAddress where md5Hash = ?1")
	public void deleteBymd5Hash(String md5Hash);
}
