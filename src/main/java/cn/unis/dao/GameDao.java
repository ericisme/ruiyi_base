package cn.unis.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.unis.entity.Game;

/**
 *
 * @author fanzz
 *
 */
public interface GameDao extends JpaRepository<Game, Long>,
		JpaSpecificationExecutor<Game> {
	public List<Game> findByStatusOrderBySortNumberAsc(int status);

	public Page<Game> findByStatus(int status,Pageable pageable);

}
