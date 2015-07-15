package cn.ruiyi.base.web.permission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ruiyi.base.entity.Menu;
import cn.ruiyi.base.entity.Permission;
import cn.ruiyi.base.service.menu.MenuService;
import cn.ruiyi.base.service.permission.PermissionService;

/**
 * <pre>
 * 权限管理
 *  使用@ModelAttribute, 实现Struts2 Preparable二次绑定的效果。 
 * 	因为@ModelAttribute被默认执行, 而其他的action url中并没有${id}，所以需要独立出一个Controller.
 * </pre>
 * @author ben
 * @version 1.0, 2012-11-19
 */
@Controller
@RequestMapping(value = "/permission")
public class PermissionDetailController
{
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private MenuService menuService;
	
	@RequiresPermissions("perm:edit")
	@RequestMapping(value = "update/{id}",method=RequestMethod.GET)
	public String update(@ModelAttribute("permission")Permission permission,HttpServletRequest request,Model model)
	{
		int currentIndex = Integer.parseInt(StringUtils.trimToEmpty(request.getParameter("currentIndex")));
		List<Menu> list = menuService.findAll();
		list.remove(0);
		model.addAttribute("mlist",list);
		model.addAttribute("currentIndex",currentIndex);
		return "permission/edit";
	}
	
	@RequiresPermissions("perm:edit")
	@RequestMapping(value = "save/{id}",method=RequestMethod.POST)
	public String save(@Valid Permission permission,BindingResult result,HttpServletRequest request)
	{
		int currentIndex = Integer.parseInt(StringUtils.trimToEmpty(request.getParameter("currentIndex")));
		if(result.hasErrors())
		{
			return "permission/edit";
		}
		else
		{
			permissionService.save(permission);
			return "redirect:/permission/list/"+currentIndex;
		}
	}
	

	@ModelAttribute("permission")
	public Permission getAccount(@PathVariable("id") Long id) {
		return permissionService.getById(id);
	}
}
