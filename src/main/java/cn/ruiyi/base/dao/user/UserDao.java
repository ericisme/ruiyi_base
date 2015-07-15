package cn.ruiyi.base.dao.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cn.ruiyi.base.entity.User;


/**
 * 
 * 用户管理的实现，dao层
 * @author chen
 * @version 1.0, 2012-11-12
 */
//public interface UserDao extends JpaRepository<User, Long> ,UserDaoCustom{
public interface UserDao extends JpaRepository<User, Long>,JpaSpecificationExecutor<User> {	
	/**
	 * 根据登录名查找用户（登录名控制唯一）
	 * @param loginName
	 * @return
	 */
	@Query("from User u where u.loginName=?1")
	User findByLoginName(String loginName);
	
	/**
	 * 用户真实姓名可能不唯一
	 * 根据用户的真实姓名查找用户
	 * @param loginName
	 * @return
	 */
	@Query("from User u where u.username=?1")
	List<User> findByUsername(String username);
	
	/**
	 * 查找所有启用的用户
	 * 
	 * @param pageable
	 * @return
	 */
	@Query("from User u where u.status=1")
	Page<User> findAllUser(Pageable pageable);
}
