package cn.unis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.unis.entity.ADSCount;

/**
 *
 * @author fanzz
 *
 */
public interface ADSCountDao extends JpaRepository<ADSCount, Long>,JpaSpecificationExecutor<ADSCount> {

}
