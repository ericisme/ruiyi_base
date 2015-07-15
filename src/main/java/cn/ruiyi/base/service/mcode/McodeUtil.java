package cn.ruiyi.base.service.mcode;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.ruiyi.base.entity.Mcode;

/**
 * Title: 		码表常量管理及方法  -- 
 * Project: 	unisCommerce
 * Type:		cn.ruiyi.base.service.mocode.McodeUtil
 * Author:		eric
 * Create:	 	2013-10-23 上午10:02:38
 * Copyright: 	Copyright (c) 2013
 * Company:		
 * 
 */@Component
public class McodeUtil {

	public static String GAME_TYPE = "GAME_TYPE";	//游戏类型
	public static String NEWS_TYPE = "NEWS_TYPE";	//新闻类型
	
	public static String INVITATION_NEWS_TYPE_CHINESE = "INVITATION_NEWS_TYPE_CHINESE";	//新闻类型-中文
	public static String INVITATION_NEWS_TYPE_ENGLISH = "INVITATION_NEWS_TYPE_ENGLISH";	//新闻类型-英文
	
	
	
	@Resource
	private McodeService mcodeService;
	
	/**
	 * 码表查询，返回html下拉菜单
	 * @param type
	 * @param selectedId
	 * @return
	 */
	public String getMbHtmlSelect(String lx, String selectedId){
		List<Mcode> list = mcodeService.findByMtype(lx);
		
		StringBuffer sb = new StringBuffer();
		for(Mcode mb : list){
			sb.append("<option value='");
			sb.append(mb.getMvalue());
            sb.append("'");
            if(selectedId != null && !"".equals(selectedId)){
            	if(selectedId.equals(mb.getMvalue())){
    				sb.append(" selected");
    			}
            }
            sb.append(">");
            sb.append(mb.getMkey());
            sb.append("</option>");
		}
		
		return sb.toString();
	}
	
	/**
	 * 根据类型和码值返回Mb对象
	 * @param lx
	 * @param mz
	 * @return
	 */
	public Mcode getMcode(String lx, String mz){
		return mcodeService.findByMtypeAndMvalue(lx, mz);
	}
}
