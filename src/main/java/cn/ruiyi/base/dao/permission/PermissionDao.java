package cn.ruiyi.base.dao.permission;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.ruiyi.base.entity.Permission;

/**
 * <pre>
 * 权限Dao
 * </pre>
 * @author ben
 * @version 1.0, 2012-11-19
 */
public interface PermissionDao extends JpaRepository<Permission, Long>,JpaSpecificationExecutor<Permission>
{
	@Modifying
	@Query("delete from Permission p where p.id in :ids")
	void deleteByIds(@Param("ids") Collection<Long> ids);
	
	@Query("from Permission p where p.menu.id=:menuId")
	List<Permission> getPermissionsByMenuId(@Param("menuId")Long menuId);
	
	/**
	 * <pre>
	 * 根据菜单ID和角色ID查询权限
	 * </pre>
	 * @param menuId
	 * @param roleId
	 * @return
	 */
	@Query("select p from Permission p join p.roleList r where p.menu.id=:menuId and r.id=:roleId")
	List<Permission> getPermissionsByMenuIdAndRoleId(@Param("menuId")Long menuId,@Param("roleId")Long roleId);
	
	/**
	 * <pre>
	 * 根据角色集合查询菜单权限
	 * </pre>
	 * @param roleids
	 * @return
	 */
	@Query("select p from Permission p join p.roleList r where p.permType=-1 and r.id in:roleids")
	List<Permission> getPermissionByRoles(@Param("roleids")Collection<Long> roleids);
	
	@Query("from Permission p where p.id in :permIds")
	List<Permission> getPermissionByPermIds(@Param("permIds")Collection<Long> permIds);
}
