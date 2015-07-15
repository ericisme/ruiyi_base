package cn.ruiyi.base.dao.menu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.ruiyi.base.entity.Menu;

/**
 * 
 * Title: 		菜单管理DAO
 * Project: 	qtypt
 * Type:		cn.ruiyi.base.dao.MenuDao
 * Author:		郭世江
 * Create:	 	2012-11-12 上午09:26:47
 * Copyright: 	Copyright (c) 2012
 * Company:		qtone
 * 
 */
public interface MenuDao extends JpaRepository<Menu, Long>{
	
	/**
	 *  获取当前级别菜单
	 *  
	 * @param menuLevel
	 * @param intSys 系统代号
	 * @return
	 */
	@Query("FROM Menu m WHERE m.menuLevel = ?1 AND (m.intSys = ?2 or m.intSys = 0)")
	Page<Menu> getMenuListByLevel(int menuLevel,int intSys,Pageable pageable);
	
	/**
	 *  根据当前菜单获取其下一级别子菜单
	 * 
	 * @param pid
	 * @return
	 */
	@Query("FROM Menu m WHERE m.menu.id = ?1 AND (m.intSys = ?2 or m.intSys = 0)")
	Page<Menu> getSubMenuListByPMenu(Long pid,int intSys,Pageable pageable);
	
	/**
	 * 获取当前菜单下一级别数量
	 * 
	 * @param currMenuId
	 * @return
	 */
	@Query("SELECT COUNT(m.id) FROM Menu m WHERE m.menu.id = ?1")
	Integer nextLevelCount(Long currMenuId);
	
	/**
	 * 根据菜单名字查询
	 * 
	 * @param menuName
	 * @return
	 */
	@Query("FROM Menu m WHERE m.menuName = ?1")
	Menu findByMenuName(String menuName);
}
