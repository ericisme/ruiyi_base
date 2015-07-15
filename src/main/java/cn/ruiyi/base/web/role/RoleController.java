package cn.ruiyi.base.web.role;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.entity.Permission;
import cn.ruiyi.base.entity.Role;
import cn.ruiyi.base.service.menu.MenuService;
import cn.ruiyi.base.service.permission.PermissionService;
import cn.ruiyi.base.service.role.RoleService;

/**
 * <pre>
 * 角色管理controller
 * 授权信息: role:view 角色查询，role:add 角色添加，role:save 角色保存，role:delete 角色删除
 * </pre>
 * @author ben
 * @version 1.0, 2012-11-14
 * 
 * Urls:
 * List   page        : GET/POST  /role/list/{pages}
 * Create page        : GET  /role/create
 * Create action      : POST /role/save
 * Update page        : GET  /role/update/{id}
 * Update action      : POST /role/save/{id}
 * Delete action      : POST /role/delete
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController
{
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private PermissionListEditor permissionListEditor;
	

	@RequiresPermissions("role:view")
	@RequestMapping(value = "/list/{pageNumber}")
	public String list(@PathVariable("pageNumber") Integer pageNumber, String roleName, Model model)
	{
		PageRequest request = new PageRequest(pageNumber - 1, Constants.PAGE_SIZE, Sort.Direction.DESC, "id");
		Page<Role> page = roleService.findByAll(roleName, request);
		int current = page.getNumber() + 1;
		model.addAttribute("page", page);
		model.addAttribute("currentIndex", current);
		return "role/list";
	}
	
	/**
	 * <pre>
	 * 添加
	 * </pre>
	 * @param model
	 * @return
	 */
	@RequiresPermissions("role:add")
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public String add(Model model)
	{
		model.addAttribute("role", new Role());
		return "role/edit";
	}
	
	/**
	 * <pre>
	 * 保存角色
	 * </pre>
	 * @param role
	 * @param result
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("role:save")
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(@Valid Role role,BindingResult result, RedirectAttributes redirectAttributes)
	{
		if(result.hasErrors())
		{
			return "role/edit";
		}
		else
		{
			roleService.save(role);
			redirectAttributes.addFlashAttribute("message", "保存角色" + role.getName() + "成功");
			return "redirect:/role/list/1";
		}
	}
	
	/**
	 * <pre>
	 * 批量删除
	 * </pre>
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("role:delete")
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public boolean delete(String ids)
	{
		boolean success = false;
		if(StringUtils.isNotEmpty(ids.trim()))
		{
			List<Long> list = new ArrayList<Long>();
			String[] roleids = ids.split(",");
			for(String id : roleids)
			{
				list.add(new Long(id));
			}
			roleService.deleteByIds(list);
			success=true;
			
		}
		return success;
	}
	
	/**
	 * <pre>
	 * 保存授权
	 * </pre>
	 * @param role
	 * @return
	 */
	@RequiresPermissions("role:permission")
	@RequestMapping(value = "menu/save",method=RequestMethod.POST)
	public String accreditRole(Role role,Long menuId,RedirectAttributes redirectAttributes)
	{
		Role r = roleService.getRoleById(role.getId());
		List<Permission> rolePermList = permissionService.getPermissionByMenuIdAndRoleId(menuId, role.getId());
		if(role.getPermissionList()== null )
		{
			//原来有权限现在没有了。
			if(rolePermList !=null && rolePermList.size()>0)
			{
				r.getPermissionList().removeAll(rolePermList);
			}
		}
		else 
		{
			//当原来的权限为空，且现在有授权时
			if(rolePermList==null || rolePermList.size()==0)
			{
				r.getPermissionList().addAll(role.getPermissionList());
			}
			else 
			{ 
				//过滤权限添加
				for(Permission p : role.getPermissionList())
				{
					if(!rolePermList.contains(p))
					{
						r.getPermissionList().add(p);
					}
				}
			   //过滤权限删除
				for(Permission p : rolePermList)
				{
					if(!role.getPermissionList().contains(p))
					{
						r.getPermissionList().remove(p);
					}
				}
			}
		}
		roleService.save(r);
		redirectAttributes.addFlashAttribute("message", "授权给" + r.getName() + "成功");
		return "redirect:/role/accredit/"+role.getId();
	}
	@InitBinder
	public void initBinder(WebDataBinder b) {
		b.registerCustomEditor(List.class, "permissionList", permissionListEditor);
	}
}
