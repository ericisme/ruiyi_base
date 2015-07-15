package cn.ruiyi.base.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.ruiyi.base.entity.SubSystem;

/**
 * 
 * Title: 		子系统管理DAO
 * Project: 	qtypt
 * Type:		cn.ruiyi.base.dao.system.SubSystemDao
 * Author:		郭世江
 * Create:	 	2012-11-12 上午09:34:07
 * Copyright: 	Copyright (c) 2012
 * Company:		qtone
 * 
 */
public interface SubSystemDao extends JpaRepository<SubSystem, Long>,JpaSpecificationExecutor<SubSystem>{
	
}