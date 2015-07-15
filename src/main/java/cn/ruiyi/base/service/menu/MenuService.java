package cn.ruiyi.base.service.menu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Collections3;

import cn.ruiyi.base.dao.menu.MenuDao;
import cn.ruiyi.base.dao.permission.PermissionDao;
import cn.ruiyi.base.entity.Menu;
import cn.ruiyi.base.entity.Permission;
import cn.ruiyi.base.entity.Role;
import cn.ruiyi.base.entity.User;
import cn.ruiyi.base.util.RandomUtil;
import cn.ruiyi.base.util.StringUtil;

/**
 * 
 * Title: 		菜单管理SERVICE
 * Project: 	qtypt
 * Type:		cn.ruiyi.base.service.MenuService
 * Author:		郭世江
 * Create:	 	2012-11-12 上午09:25:08
 * Copyright: 	Copyright (c) 2012
 * Company:		qtone
 * 
 */
@Component
@Transactional(readOnly = true)
public class MenuService{
	
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private PermissionDao permissionDao;
	
	private PageRequest sort;
	
	/**
	 * 根据ID查询实体
	 * 
	 * @param id
	 * @return
	 */
	public Menu findById(Long id){
		return menuDao.findOne(id);
	}
	
	/**
	 * 根据菜单名称查询
	 * 
	 * @param menuName
	 * @return
	 */
	public Menu findByMenuName(String menuName){
		return menuDao.findByMenuName(menuName);
	}
	
	/**
	 * 保存/更新
	 * 
	 * @param menu
	 */
	@Transactional(readOnly = false)
	public void save(Menu menu){
		menuDao.saveAndFlush(menu);
	}
	
	/**
	 * 删除菜单
	 */
	@Transactional(readOnly = false)
	public void delete(Long id){
		menuDao.delete(id);
	}
	
	/**
	 * 删除所有子菜单
	 * 
	 * @param list
	 */
	public void deleteSubMenus(List<Menu> list){
		for (Menu menu : list)
		{
			this.deleteSubMenus(menu.getSubMenuList());
			this.delete(menu.getId());
		}
	}
	
	/**
	 * 获取当前级别菜单
	 * 
	 * @param menuLevel
	 * @param status
	 * @return
	 */
	public List<Menu> getMenuListByCurrUser(User user,int intSys){
		sort =  new PageRequest(0, 9999, Sort.Direction.ASC, "orderNum,id");
		List<Long> roleIds;
		if(user.getRoleList() == null || user.getRoleList().isEmpty()){
			roleIds = new ArrayList<Long>();
			roleIds.add(new Long(5));
		}else{
			roleIds = Collections3.extractToList(user.getRoleList(), "id");
		}
		List<Permission> list = permissionDao.getPermissionByRoles(roleIds);
		List<Long> menuIds = new ArrayList<Long>();
		for (Permission per : list){
			menuIds.add(Long.parseLong(per.getPermValue()));
		}
		List<Menu> listMenu = menuDao.getMenuListByLevel(1,intSys,sort).getContent();
		//获取其下所有子菜单
		for (Menu menu : listMenu){
			menu.setSubMenuList(this.getSubMenuListByPMenu(menu,intSys));
		}
		for (Iterator<Menu> it = listMenu.iterator() ; it.hasNext();){
			int menuCount = 0;
			Menu menu = it.next();
			if(menu.getHasNext() == 1){
				if(menu.getStatus() == 1){
					menuCount = constructionMenuTree(menu,menuIds,menuCount);
					if(menuCount == 0){
						menu.setStatus(0);
					}
				}else{
					//it.remove()报错，把当前menu状态设置禁用，页面一样不显示
					menu.setStatus(0);
				}
			}else{
				if(!menuIds.contains(menu.getId())){
					//it.remove()报错，把当前menu状态设置禁用，页面一样不显示
					menu.setStatus(0);
				}
			}
			
		}
		return listMenu;
	}
	
	/**
	 * 重新构造菜单
	 * 
	 * @param list
	 */
	public int constructionMenuTree(Menu menu,List<Long> menuIds,int menuCount){
		for (Iterator<Menu> it = menu.getSubMenuList().iterator() ; it.hasNext();){
			Menu m = it.next();
			if(m.getStatus() != 0){
				if(!menuIds.contains(m.getId())){
					//it.remove()报错，把当前menu状态设置禁用，页面一样不显示
					m.setStatus(0);
				}else{
					menuCount++;
					menuCount = constructionMenuTree(m,menuIds,menuCount);
				}
			}
		}
		//StringUtil.debug(menuCount);
		return menuCount;
	}
	
	/**
	 * 获取当前级别菜单
	 * 
	 * @param menuLevel
	 * @param status
	 * @return
	 */
	public List<Menu> getMenuListByLevel(int menuLevel,int intSys){
		sort =  new PageRequest(0, 9999, Sort.Direction.ASC, "orderNum,id");
		List<Menu> list = menuDao.getMenuListByLevel(menuLevel,intSys,sort).getContent();
		//获取其下所有子菜单
		for (Menu menu : list){
			//StringUtil.debug(menu.getMenuName());
			menu.setSubMenuList(this.getSubMenuListByPMenu(menu,intSys));
		}
		return list;
	}
	
	/**
	 * 根据当前菜单获取其下一级别子菜单
	 * 
	 * @param pid
	 * @param status
	 * @return
	 */
	public List<Menu> getSubMenuListByPMenu(Menu pMenu,int intSys){
		sort =  new PageRequest(0, 9999, Sort.Direction.ASC, "orderNum,id");
		List<Menu> list = menuDao.getSubMenuListByPMenu(pMenu.getId(),intSys ,sort).getContent();
		if(list.size() == 0){
			pMenu.setHasNext(0);	//没有下级菜单
		}else{
			pMenu.setHasNext(1);
			//获取其下所有子菜单
			for (Menu menu : list){
				//StringUtil.debug(menu.getMenuName());
				menu.setSubMenuList(this.getSubMenuListByPMenu(menu,intSys));
			}
		}
		return list;
	}
	
	/**
	 * 组合菜单数据供zTree使用
	 * 
	 * @param list
	 * @return
	 */
	public String getJsonStringForZTree(List<Menu> list,String jsonString){
		for (Menu menu : list){
			jsonString += "{";
			jsonString += "id : " + menu.getId() + ",";			
			jsonString += "name : '" + menu.getMenuName() + "'" + ",";
			jsonString += "status : " + menu.getStatus() +  ",";
			jsonString += "orderNum : " + menu.getOrderNum()  + ",";
			jsonString += "menuLevel : " + menu.getMenuLevel()  + ",";
			jsonString += "hasNext : " + menu.getHasNext()  + ",";
			jsonString += "pMaxNum : " + list.size()  + ",";
			jsonString += "maxNum : " + menu.getSubMenuList().size()  + ",";
			
			if(menu.getStatus() == 0){
				jsonString += "font : {'color' : '#c6c6c6'},";
			}
			
			if(menu.getMenuLevel() == 1){
				if(menu.getHasNext() == 1){
					jsonString += "pId : 0,";
					jsonString += "open : true,";
					jsonString += "menuUrl : '',";
					jsonString += "iconSkin : 'pIcon01'";
				}else{
					jsonString += "pId : 0,";
					jsonString += "open : true,";
					jsonString += "menuUrl : '',";
					jsonString += "iconSkin : 'icon0" + RandomUtil.getInstance().randomInt(2,8) + "'";
				}
			}else{
				if(menu.getHasNext() == 1){
					jsonString += "pId : " + menu.getMenu().getId() + ",";
					jsonString += "menuUrl : '" + menu.getMenuUrl() + "',";
					jsonString += "iconSkin : 'pIcon01'";
				}else{
					jsonString += "pId : " + menu.getMenu().getId() + ",";
					jsonString += "menuUrl : '" + menu.getMenuUrl() + "',";
					jsonString += "iconSkin : 'icon0" + RandomUtil.getInstance().randomInt(2,8) + "'";
				}
			}
			jsonString += "},";
			
			if(menu.getHasNext() == 1){
				jsonString = this.getJsonStringForZTree(menu.getSubMenuList(), jsonString);
			}
		}
		return jsonString;
	}
	
	/**
	 * <pre>
	 * 获取所有菜单
	 * </pre>
	 * @return
	 */
	public List<Menu> findAll()
	{
		return menuDao.findAll();
	}
}
