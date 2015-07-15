package cn.ruiyi.base.service.system;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Collections3;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.dao.system.SubSystemDao;
import cn.ruiyi.base.entity.SubSystem;
import cn.ruiyi.base.entity.User;

/**
 * 
 * Title: 		子菜单管理SERVICE
 * Project: 	qtypt
 * Type:		cn.ruiyi.base.service.SubSystemService
 * Author:		郭世江
 * Create:	 	2012-11-12 上午09:26:22
 * Copyright: 	Copyright (c) 2012
 * Company:		qtone
 * 
 */
@Component
@Transactional(readOnly = true)
public class SubSystemService{
	
	@Autowired
	private SubSystemDao subSysDao;
	
	/**
	 * 根据ID查询实体
	 * 
	 * @param id
	 * @return
	 */
	public SubSystem findById(Long id){
		return subSysDao.findOne(id);
	}
	
	/**
	 * 保存/更新
	 * 
	 * @param sytem
	 */
	@Transactional(readOnly = false)
	public void save(SubSystem system){
		subSysDao.saveAndFlush(system);
	}

	/**
	 * 删除子系统
	 * 
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void delete(Long id){
		subSysDao.delete(id);
	}
	
	/**
	 * 获取所有子系统列表
	 * 
	 * @param pageable
	 * @return
	 */
	public Page<SubSystem> findAll(final String subName,final int status,Pageable pageable){
		if(StringUtils.isEmpty(subName) && (status == -1)){
			return this.subSysDao.findAll(pageable);
		}else{
			return this.subSysDao.findAll(new Specification<SubSystem>(){
				@Override
				public Predicate toPredicate(Root<SubSystem> subSys, CriteriaQuery<?> query, CriteriaBuilder cb)
				{
					List<Predicate> predicateList = new ArrayList<Predicate>();
					if(StringUtils.isNotEmpty(subName)){
						Path<String> expression = subSys.get("subName");
						Predicate p = cb.like(cb.lower(expression), "%" + subName.toLowerCase() + "%");
						predicateList.add(p);
					}
					
					if(status != -1){
						Path<String> expression = subSys.get("status");
						Predicate p = cb.equal(expression, status);
						predicateList.add(p);
					}
					
					Predicate[] predicates = new Predicate[predicateList.size()];
					predicateList.toArray(predicates);
					
					if(predicates.length > 0){
						return cb.and(predicates);
					}else{
						return cb.conjunction();
					}
				}
			}, pageable);
		}
	}
	
	/**
	 * 生成子系统统一列表图标 -- 系统里鼠标移上去的
	 * 
	 * @param list
	 * @param user 当前用户
	 * @return
	 */
	public String generateSybSystemICOHTML(List<SubSystem> list,User user){
		boolean permis = false;
		List<Long> permisSubSysIds = new ArrayList<Long>();
		if(StringUtils.isNotEmpty(user.getSystemIds())){
			permis = true;
			for(String subSysId : user.getSystemIds().split(",")){
				permisSubSysIds.add(new Long(subSysId));
			}
		}
		String css = "<style type='text/css'>";
		String html = "";
		for (int i = 0;i < list.size();i++){
			SubSystem subSys = list.get(i);
			if(subSys.getStatus() == 1){
				if(permis){
					if(permisSubSysIds.contains(subSys.getId())){
						css += ".button" + i + " a{background:url(/upload/system/icon/" +subSys.getVisIcon() +") no-repeat;} ";
						css += ".button" + i + " a:hover{background:url(/upload/system/icon/" + subSys.getHighIcon() + ") no-repeat; } ";
						html += "<div class='button"+i+"'><a href='#' onclick=\"base.forward('"+subSys.getMainpage()+"',parent);\" title='"+Constants.SUBSYS_PERMIS_NORMAL_TITLE+"'>"+subSys.getSubName()+"</a></div>";	
					}else{
						css += ".button" + i + " a{background:url(/upload/system/icon/" + subSys.getUnvisIcon() +") no-repeat;} ";
						html += "<div class='button"+i+"'><a href='#'  title='"+Constants.SUBSYS_PERMIS_NOTENOUGH_TITLE+"'>"+subSys.getSubName()+"</a></div>";
					}
				}else{
					css += ".button" + i + " a{background:url(/upload/system/icon/" + subSys.getUnvisIcon() +") no-repeat;} ";
					html += "<div class='button"+i+"'><a href='#'  title='"+Constants.SUBSYS_PERMIS_NOTENOUGH_TITLE+"'>"+subSys.getSubName()+"</a></div>";
				}
			}else{
				css += ".button" + i + " a{background:url(/upload/system/icon/" + subSys.getUnvisIcon() +") no-repeat;} ";
				html += "<div class='button"+i+"'><a href='#' title='"+Constants.SUBSYS_PERMIS_UNABLE_TITLE+"'>"+subSys.getSubName()+"</a></div>";
			}
		}
		css += "</style> ";
		return css + html;
	}
	
	/**
	 * 生成首页子系统统一列表图标 -- 首页的
	 * 
	 * @param list
	 * @param user 当前用户
	 * @return
	 */
	public String generateHomePageSybSystemICOHTML(List<SubSystem> list,User user){
		boolean permis = false;
		List<Long> permisSubSysIds = new ArrayList<Long>();
		if(StringUtils.isNotEmpty(user.getSystemIds())){
			permis = true;
			for(String subSysId : user.getSystemIds().split(",")){
				permisSubSysIds.add(new Long(subSysId));
			}
		}
		String css = "<style type='text/css'>";
		String html = "";
		for (int i = 0;i < list.size();i++){
			SubSystem subSys = list.get(i);
			if(subSys.getStatus() == 1){
				if(permis){
					if(permisSubSysIds.contains(subSys.getId())){
						css += ".button" + i + " a{background:url(/upload/system/icon/" +subSys.getVisIcon() +") no-repeat;} ";
						css += ".button" + i + " a:hover{background:url(/upload/system/icon/" + subSys.getHighIcon() + ") no-repeat; } ";
						if("xxt".equalsIgnoreCase(subSys.getIdentifyCode())){
							html += "<div class='button"+i+"'><a href='"+subSys.getMainpage()+"' title='"+Constants.SUBSYS_PERMIS_NORMAL_TITLE+"' target='_blank'>"+subSys.getSubName()+"</a></div>";
						}else{
							html += "<div class='button"+i+"'><a href='#' onclick=\"base.forward('"+subSys.getMainpage()+"');\" title='"+Constants.SUBSYS_PERMIS_NORMAL_TITLE+"'>"+subSys.getSubName()+"</a></div>";
						}
					}else{
						css += ".button" + i + " a{background:url(/upload/system/icon/" + subSys.getUnvisIcon() +") no-repeat;} ";
						html += "<div class='button"+i+"'><a href='#' title='"+Constants.SUBSYS_PERMIS_NOTENOUGH_TITLE+"'>"+subSys.getSubName()+"</a></div>";
					}
				}else{
					css += ".button" + i + " a{background:url(/upload/system/icon/" + subSys.getUnvisIcon() +") no-repeat;} ";
					html += "<div class='button"+i+"'><a href='#' title='"+Constants.SUBSYS_PERMIS_NOTENOUGH_TITLE+"'>"+subSys.getSubName()+"</a></div>";
				}
			}else{
				css += ".button" + i + " a{background:url(/upload/system/icon/" + subSys.getUnvisIcon() +") no-repeat;} ";
				html += "<div class='button"+i+"'><a href='#' title='"+Constants.SUBSYS_PERMIS_UNABLE_TITLE+"'>"+subSys.getSubName()+"</a></div>";
			}
		}
		css += "</style>";
		return css + html;
	}
}
