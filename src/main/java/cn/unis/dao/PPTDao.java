package cn.unis.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.unis.entity.PPT;

/**
 * 
 * @author fanzz
 * 
 */
public interface PPTDao extends JpaRepository<PPT, Long>,
		JpaSpecificationExecutor<PPT> {
	public List<PPT> findByStatusOrderBySortNumberAsc(int status);
}
