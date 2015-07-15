package cn.ruiyi.base.service.permission;

import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Collections3;

import cn.ruiyi.base.dao.permission.PermissionDao;
import cn.ruiyi.base.entity.Menu;
import cn.ruiyi.base.entity.Permission;
import cn.ruiyi.base.entity.Role;

/**
 * <pre>
 * 权限业务bean
 * </pre>
 * @author ben
 * @version 1.0, 2012-11-19
 */
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class PermissionService
{
	private PermissionDao permissionDao;

	@Autowired
	public void setPermissionDao(PermissionDao permissionDao)
	{
		this.permissionDao = permissionDao;
	}
	
	/**
	 * <pre>
	 * 根据菜单名查找权限(分页)
	 * </pre>
	 * @param menuName 菜单名
	 * @param pageable 分页组件
	 * @return
	 */
	public Page<Permission> findByAll(final String menuName,Pageable pageable)
	{
		if(StringUtils.isEmpty(menuName))
		{
			return this.permissionDao.findAll(pageable);
		}
		else
		{
			return this.permissionDao.findAll(new Specification<Permission>(){
	
				@Override
				public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query, CriteriaBuilder cb)
				{
					 final Subquery<Long> menuQuery = query.subquery(Long.class);
		                final Root<Menu> menu = menuQuery.from(Menu.class);
		               // final Join<Menu, Permission> permission = menu.join("permission");
		                menuQuery.select(menu.<Long> get("id"));
					//Path<String> expression = root.get("menu.menuName");
					menuQuery.where(cb.like(menu.<String>get("menuName"), "%" + menuName + "%"));
					return cb.in(root.get("menu")).value(menuQuery);
				}
			}, pageable);
		}
	}
	
	/**
	 * <pre>
	 * 保存权限
	 * </pre>
	 * @param permission
	 */
	@Transactional(readOnly = false)
	public void save(Permission permission)
	{
		permissionDao.saveAndFlush(permission);
	}
	
	/**
	 * <pre>
	 * 批量删除
	 * </pre>
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void deleteByIds(Collection<Long> ids)
	{
		permissionDao.deleteByIds(ids);
	}
	
	public Permission getById(Long id)
	{
		return permissionDao.findOne(id);
	}
	
	/**
	 * <pre>
	 * 根据菜单id查找该菜单的权限集
	 * </pre>
	 * @param menuId
	 * @return
	 */
	public List<Permission> getPermissionsByMenuId(Long menuId)
	{
		return permissionDao.getPermissionsByMenuId(menuId);
	}
	
	/**
	 * <pre>
	 *  根据菜单ID和角色ID查找权限集
	 * </pre>
	 * @param menuId
	 * @param roleId
	 * @return
	 */
	public List<Permission> getPermissionByMenuIdAndRoleId(Long menuId,Long roleId)
	{
		return permissionDao.getPermissionsByMenuIdAndRoleId(menuId, roleId);
	}
	/**
	 * <pre>
	 * 根据权限ID集合查找权限集
	 * </pre>
	 * @param permList
	 * @return
	 */
	public List<Permission> getPermissionByPermIds(Collection<Permission> permList)
	{
		@SuppressWarnings("unchecked")
		List<Long> permIds = Collections3.extractToList(permList, "id");
		//System.out.println("=======" + permIds.size());
		return permissionDao.getPermissionByPermIds(permIds);
	}
	
	/**
	 * <pre>
	 * 根据角色ID集合查找菜单权限集
	 * </pre>
	 * @param roles
	 * @return
	 */
	public List<Permission> getPermissionByRoleIds(Collection<Role> roles)
	{
		@SuppressWarnings("unchecked")
		List<Long> roleids = Collections3.extractToList(roles, "id");
		return permissionDao.getPermissionByRoles(roleids);
	}
}
