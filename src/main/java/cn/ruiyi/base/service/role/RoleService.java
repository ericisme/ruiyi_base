package cn.ruiyi.base.service.role;

import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.ruiyi.base.dao.role.RoleDao;
import cn.ruiyi.base.entity.Role;
import cn.ruiyi.base.service.shiro.ShiroDbRealm;

//Spring Bean的标识.
/**
 * <pre>
 * 角色业务bean
 * </pre>
 * @author ben
 * @version 1.0, 2012-11-14
 */
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class RoleService
{
	private RoleDao roleDao;
	private ShiroDbRealm shiroRealm;
	@Autowired
	public void setRoleDao(RoleDao roleDao)
	{
		this.roleDao = roleDao;
	}
	@Autowired
	public void setShiroRealm(ShiroDbRealm shiroRealm)
	{
		this.shiroRealm = shiroRealm;
	}

	/**
	 * <pre>
	 * 根据角色名查找角色(分页)
	 * </pre>
	 * @param roleName 角色名
	 * @param pageable 分页组件
	 * @return
	 */
	public Page<Role> findByAll(final String roleName,Pageable pageable)
	{
		if(StringUtils.isEmpty(roleName))
		{
			return this.roleDao.findAll(pageable);
		}
		else
		{
			return this.roleDao.findAll(new Specification<Role>(){
	
				@Override
				public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb)
				{
					Path<String> expression = root.get("name");
					return cb.and(cb.like(expression, "%" + roleName + "%"));
				}
			}, pageable);
		}
	}
	
	/**
	 * <pre>
	 * 保存角色
	 * </pre>
	 * @param role
	 */
	@Transactional(readOnly = false)
	public void save(Role role)
	{
		roleDao.saveAndFlush(role);
		shiroRealm.clearAllCachedAuthorizationInfo();
	}
	
	/**
	 * <pre>
	 * 获取角色
	 * </pre>
	 * @param id
	 * @return
	 */
	public Role getRoleById(Long id)
	{
		return roleDao.findOne(id);
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
		roleDao.deleteByRoleIds(ids);
		shiroRealm.clearAllCachedAuthorizationInfo();
	}
	
	/**
	 * <pre>
	 * 修改角色状态
	 * </pre>
	 * @param status
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void updateRoleByStatus(int status ,Long id)
	{
		roleDao.updateRoleStatus(status, id);
		shiroRealm.clearAllCachedAuthorizationInfo();
	}
	
	/**
	 * 获取所有可用角色
	 */
	public List<Role> findAllRoles(){
		return this.roleDao.findAll();
	}
}
