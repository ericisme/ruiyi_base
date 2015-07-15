package cn.unis.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.unis.entity.AlipayDiscount;

/**
 * 
 * @author fanzz
 * 
 */
public interface AlipayDiscountDao extends JpaRepository<AlipayDiscount, Long>,
		JpaSpecificationExecutor<AlipayDiscount> {
	
	@Query("from AlipayDiscount where ?1 >= effectiveBegin and ?1 <= effectiveEnd")
	public AlipayDiscount findByDate(Date date);
}
