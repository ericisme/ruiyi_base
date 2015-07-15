package cn.ruiyi.base.dao.role;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.ruiyi.base.entity.Role;

/**
 * <pre>
 * 角色dao
 * </pre>
 * @author ben
 * @version 1.0, 2012-11-14
 */
public interface RoleDao extends JpaRepository<Role, Long>,JpaSpecificationExecutor<Role>
{
	@Modifying
	@Query("delete from Role role where role.id in :ids")
	void deleteByRoleIds(@Param("ids") Collection<Long> ids);
	
	@Modifying
	@Query("update Role role set role.status=:status where role.id=:id")
	void updateRoleStatus(@Param("status") int status,@Param("id") Long id);
	
}
