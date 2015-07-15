package cn.unis.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import cn.ruiyi.base.simplemvc.dao.BaseManagerDao;
import cn.unis.entity.News;


/**
 * 
 * 新闻管理类的实现，dao层
 * @author eric
 * @version 1.0, 2013-10-09
 */
public interface NewsDao extends BaseManagerDao<News> {	

	/**
	 * 根据 tag 返回新闻分页列表
	 * @param tag
	 * @return
	 */
	@Query("from News n where n.tag=?1 and n.status=1")
	Page<News> findNewsPagedByTag(Pageable pageable, String tag);
	
	/**
	 * 返回所有新闻分页列表
	 * @param tag
	 * @return
	 */
	@Query("from News n where n.status=1")
	Page<News> findAllNewsPaged(Pageable pageable);
	
	
}
