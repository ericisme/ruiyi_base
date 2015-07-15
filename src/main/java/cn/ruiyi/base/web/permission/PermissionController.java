package cn.ruiyi.base.web.permission;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.entity.Menu;
import cn.ruiyi.base.entity.Permission;
import cn.ruiyi.base.service.menu.MenuService;
import cn.ruiyi.base.service.permission.PermissionService;

/**
 * <pre>
 * 权限管理
 * </pre>
 * @author ben
 * @version 1.0, 2012-11-19
 */
@Controller
@RequestMapping(value = "/permission")
public class PermissionController
{
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private MenuService menuService;
	
	@RequiresPermissions("perm:view")
	@RequestMapping(value = "/list/{pageNumber}")
	public String list(@PathVariable
	Integer pageNumber, String menuName, Model model)
	{
		PageRequest request = new PageRequest(pageNumber - 1, Constants.PAGE_SIZE, Sort.Direction.DESC, "id");
		Page<Permission> page = permissionService.findByAll(menuName, request);
		int current = page.getNumber() + 1;
		model.addAttribute("page", page);
		model.addAttribute("currentIndex", current);
		return "permission/list";
	}
	
	/**
	 * <pre>
	 * 添加
	 * </pre>
	 * @param model
	 * @return
	 */
	@RequiresPermissions("perm:edit")
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public String create(Model model)
	{
		List<Menu> list = menuService.findAll();
		model.addAttribute("permission", new Permission());
		list.remove(0);
		model.addAttribute("mlist",list);
		return "permission/edit";
	}
	/**
	 * <pre>
	 * 保存
	 * </pre>
	 * @param role
	 * @param result
	 * @return
	 */
	@RequiresPermissions("perm:edit")
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(@Valid Permission permission,BindingResult result,Model model)
	{
		if(result.hasErrors())
		{
			List<Menu> list = menuService.findAll();
			list.remove(0);
			model.addAttribute("mlist",list);
			return "permission/edit";
		}
		else
		{
			permissionService.save(permission);
			return "redirect:/permission/list/1";
		}
	}
	
	/**
	 * <pre>
	 * 批量删除
	 * </pre>
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("perm:delete")
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public boolean batchDel(String ids)
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
			permissionService.deleteByIds(list);
			success=true;
			
		}
		return success;
	}
}
