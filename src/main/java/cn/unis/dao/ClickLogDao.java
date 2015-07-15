package cn.unis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.unis.entity.ClickLog;

/**
 * 下载量/点击量详细记录 dao
 * @author eric
 * 
 */
public interface ClickLogDao extends JpaRepository<ClickLog, Long>,
		JpaSpecificationExecutor<ClickLog> {
	
}
