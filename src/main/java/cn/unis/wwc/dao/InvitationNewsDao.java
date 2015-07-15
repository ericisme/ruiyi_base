package cn.unis.wwc.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import cn.ruiyi.base.simplemvc.dao.BaseManagerDao;
import cn.unis.entity.News;
import cn.unis.wwc.entity.InvitationNews;


/**
 * 
 * 大会新闻管理类的实现，dao层
 * @author eric
 * @version 1.0, 2013-10-09
 */
public interface InvitationNewsDao extends BaseManagerDao<InvitationNews> {	

	/**
	 * 根据 tag 返回大会新闻分页列表
	 * @param tag
	 * @return
	 */
	@Query("from InvitationNews n where n.tag=?1 and n.status=1")
	Page<InvitationNews> findInvitationNewsPagedByTag(Pageable pageable, String tag);
	
	/**
	 * 返回所有大会新闻分页列表
	 * @param tag
	 * @return
	 */
	@Query("from InvitationNews n where n.status=1")
	Page<InvitationNews> findAllInvitationNewsPaged(Pageable pageable);
	
	
}
