package cn.unis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.unis.entity.Test1;

/**
 *
 * @author fanzz
 *
 */
public interface Test1Dao extends JpaRepository<Test1, Long>,
		JpaSpecificationExecutor<Test1> {
}
