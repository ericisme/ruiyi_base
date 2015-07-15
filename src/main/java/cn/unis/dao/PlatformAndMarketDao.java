package cn.unis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.unis.entity.PlatformAndMarket;

/**
 * 
 * @author fanzz
 * 
 */
public interface PlatformAndMarketDao extends JpaRepository<PlatformAndMarket, Long>,
		JpaSpecificationExecutor<PlatformAndMarket> {
}
