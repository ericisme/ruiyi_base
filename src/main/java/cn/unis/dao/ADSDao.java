package cn.unis.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.unis.entity.ADS;

/**
 *
 * @author fanzz
 *
 */
public interface ADSDao extends JpaRepository<ADS, Long>,JpaSpecificationExecutor<ADS> {
	public List<ADS> findByStatusOrderBySortNumberAsc(int status);
	@Query("From ADS _ads where _ads.status = 1")
	public Page<ADS> getTopItems(Pageable pageable);
}
