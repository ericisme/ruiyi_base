package cn.unis.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import cn.ruiyi.base.entity.User;
import cn.unis.entity.TempUrl;

/**
 *
 * @author fanzz
 *
 */
public interface TempUrlDao extends  Repository<TempUrl, Long>,JpaRepository<TempUrl, Long>,JpaSpecificationExecutor<TempUrl> {

		public List<TempUrl> findByUser(User user);

		@Modifying
		@Query("delete from TempUrl where user = ?1")
		public void deleteByUser(User user);

}
