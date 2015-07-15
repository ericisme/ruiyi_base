package cn.ruiyi.base.web.left;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.ruiyi.base.constants.CurrentSystemUtil;
import cn.ruiyi.base.entity.Menu;
import cn.ruiyi.base.entity.User;
import cn.ruiyi.base.service.account.AccountService;
import cn.ruiyi.base.service.menu.MenuService;
import cn.ruiyi.base.web.mvc.StringView;

/**
 * 
 * Title: 		菜单管理
 * Project: 	qtypt
 * Type:		cn.ruiyi.base.web.LoginController
 * Author:		郭世江
 * Create:	 	2012-11-12 下午05:58:03
 * Copyright: 	Copyright (c) 2012
 * Company:		qtone
 *
 */
@Controller
@RequestMapping(value = "/left/menu/")
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	@Autowired
	private AccountService accountService;
	
	/**
	 * 菜单管理  -- 首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "index")
	public String index(){
		return "menu/index";
	}
	
	/**
	 * 菜单管理  -- 列表
	 * 
	 * @return
	 */
	@RequiresPermissions("menu:view")
	@RequestMapping(value = "list")
	public String list(Model model){
		int intSys = CurrentSystemUtil.getCurrentSystem();
		List<Menu> list = menuService.getMenuListByLevel(1,intSys);
		String jValue = "[";
		jValue = menuService.getJsonStringForZTree(list,jValue);
		if(list.size() > 0){
			jValue = jValue.substring(0,jValue.length() - 1);
		}
		jValue += "]";
		//StringUtil.debug(jValue);
		
		model.addAttribute("jValue", jValue);
		
		return "menu/list";
	}
	
	
	/**
	 * 菜单管理  -- 保存
	 * 
	 * @return
	 */
	@RequiresPermissions("menu:save")
	@RequestMapping(value = "save")
	public ModelAndView save(HttpServletRequest request,Menu menu,Long pid){
		StringView view = new StringView();
		Menu m = null;
		if(StringUtils.isEmpty(menu.getMenuUrl())){
			menu.setMenuUrl("/");
		}
		try{
			//当前所属系统标识
			int intSys = CurrentSystemUtil.getCurrentSystem();
			
			menu.setIntSys(intSys);
			if(menu.getId() == 0){	//新增菜单
				if(pid != 0){
					m = menuService.findById(pid);
				}else{
					m = menuService.findByMenuName("ROOT");
				}
				
				User user = accountService.getUser(request);
				//StringUtil.debug(m.getId());
				menu.setMenuLevel(m.getMenuLevel() + 1);
				menu.setMenu(m);
				menu.setCreater(user);
				
				menuService.save(menu);
			}else{	//修改菜单
				m =  menuService.findById(menu.getId());
				m.setMenuName(menu.getMenuName());
				m.setMenuUrl(menu.getMenuUrl());
				m.setOrderNum(menu.getOrderNum());
				m.setStatus(menu.getStatus());
				
				menuService.save(m);
			}
			view.setContent("success");
		}catch(Exception ex){
			view.setContent("faild");
			ex.printStackTrace();
		}
		
		return new ModelAndView(view);
	}
	
	/**
	 * 菜单管理  -- 删除菜单及子菜单
	 * 
	 * @return
	 */
	@RequiresPermissions("menu:delete")
	@RequestMapping(value = "delNode")
	public ModelAndView delNode(Long id){
		StringView view = new StringView();
		int intSys = CurrentSystemUtil.getCurrentSystem();
		Menu m = null;
		try{
			m = menuService.findById(id);
			//StringUtil.debug(m.getId());
			//有外键关联需删除所有子菜单
			List<Menu> list = menuService.getSubMenuListByPMenu(m,intSys);
			menuService.deleteSubMenus(list);
			//删除菜单
			menuService.delete(m.getId());
			
			view.setContent("success");
		}catch(Exception ex){
			view.setContent("faild");
			ex.printStackTrace();
		}
		
		return new ModelAndView(view);
	}
	
	/**
	 * 菜单管理  -- 禁用 / 启用
	 * 
	 * @return
	 */
	@RequiresPermissions("menu:setup")
	@RequestMapping(value = "setup")
	public ModelAndView setup(Long id,Integer status){
		StringView view = new StringView();
		Menu m = null;
		try{
			m = menuService.findById(id);
			//StringUtil.debug(m.getId());
			m.setStatus(status);
			menuService.save(m);
			view.setContent("success");
		}catch(Exception ex){
			view.setContent("faild");
			ex.printStackTrace();
		}
		
		return new ModelAndView(view);
	}
}
