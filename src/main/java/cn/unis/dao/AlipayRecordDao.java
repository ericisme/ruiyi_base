package cn.unis.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.unis.entity.AlipayRecord;

/**
 * 
 * @author fanzz
 * 
 */
public interface AlipayRecordDao extends JpaRepository<AlipayRecord, Long>,
		JpaSpecificationExecutor<AlipayRecord> {
	@Query("from AlipayRecord where sign = ?1 and status = ?2")
	public AlipayRecord findBySignAndStatus(String sign,byte status);
	
	public AlipayRecord findByOutTradeNo(String outTradeNo);
	
	public List<AlipayRecord> findByStatus(byte status);
	
}
