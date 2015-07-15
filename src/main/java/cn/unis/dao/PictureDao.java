package cn.unis.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.unis.entity.Picture;

/**
 * 
 * @author fanzz
 * 
 */
public interface PictureDao extends JpaRepository<Picture, Long>,
		JpaSpecificationExecutor<Picture> {
	public List<Picture> findByStatusOrderByIdAsc(int status);
	
	@Query("from Picture p order by instr('2,4,3,5,7,6',id) desc")
	Page<Picture> testFind(Pageable pageable,String where);
}
