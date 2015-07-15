package cn.unis.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.ruiyi.base.entity.User;
import cn.ruiyi.base.simplemvc.dao.BaseManagerDao;
import cn.unis.entity.UserGameCenter;


/**
 * 
 * 用户 与 游乐场  关联 实体，dao层
 * @author eric
 * @version 1.0, 2013-10-09
 */
public interface UserGameCenterDao extends BaseManagerDao<UserGameCenter> {	

	public List<UserGameCenter> findByUser(User user);
	
	@Modifying
	@Query("delete from UserGameCenter where user = ?1")
	public void deleteByUser(User user);
}
