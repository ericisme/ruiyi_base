package cn.ruiyi.base.dao.user;
//package cn.ruiyi.base.dao.user;
//
//import java.util.Collection;
//import java.util.List;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import cn.ruiyi.base.entity.User;
//
//public interface UserDaoCustom{
//
//	
//	/**
//	 * 组合条件用户查找——单位、学校、用户姓名（模糊查询）、角色
//	 */
//	Page<User> findBySubject(String units, String schools, String keyName,String roles,Pageable pageable);	
//
//	/**
//	 * 根据查询条件查找用户
//	 */
//	Page<User> getNeedUser(String qryName, long roleId,long typeId,int userType,Pageable pageable);
//}
