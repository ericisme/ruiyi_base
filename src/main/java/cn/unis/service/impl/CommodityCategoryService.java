package cn.unis.service.impl;

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

import cn.ruiyi.base.util.RandomUtil;
import cn.unis.dao.CommodityCategoryDao;
import cn.unis.entity.CommodityCategory;

/**
 * 
 * Title: 		商品类别管理SERVICE
 * Author:		eric
 * Company:		unis
 * 
 */
@Component
@Transactional(readOnly = true)
public class CommodityCategoryService{
	
	@Autowired
	private CommodityCategoryDao commodityCategoryDao;
	private PageRequest sort;
	
	/**
	 * 根据类别，获得顶层类别,顶层类别是即level=2那级。
	 */
	public CommodityCategory getTopCommodityCategory(CommodityCategory commodityCategory){
		CommodityCategory topCommodityCategory = commodityCategory;
		while(topCommodityCategory.getLevel()>2){
			topCommodityCategory = commodityCategory.getCommodityCategory();			
		}
		return topCommodityCategory;
	}
	
	/**
	 * 删除商品类别
	 */
	@Transactional(readOnly = false)
	public void delete(Long id){
		commodityCategoryDao.delete(id);
	}
	
	/**
	 * 删除所有子商品类别
	 */
	public void deleteSubBms(List<CommodityCategory> list){
		for (CommodityCategory commodityCategory : list)
		{
			this.deleteSubBms(commodityCategory.getSubCommodityCategoryList());
			this.delete(commodityCategory.getId());
		}
	}
	
	/**
	 * 保存/更新 商品类别
	 */
	@Transactional(readOnly = false)
	public void save(CommodityCategory commodityCategory){
		commodityCategoryDao.saveAndFlush(commodityCategory);
	}	
	
	/**
	 * 判断 商品类别名称 是否已经存在
	 */
	public boolean nameExist(CommodityCategory commodityCategory){
		return commodityCategoryDao.nameExist(commodityCategory.getId(),commodityCategory.getName()).size()>0;
	}
	
	/**
	 * 获取当前级别商品类别
	 */
	public List<CommodityCategory> getCommodityCategoryListByLevel(int level){
		sort =  new PageRequest(0, 999999, Sort.Direction.ASC, "orderNum,id");
		List<CommodityCategory> list = commodityCategoryDao.getCommodityCategoryListByLevel(level, sort).getContent();
		//获取其下所有子菜单
		for (CommodityCategory commodityCategory : list){
			commodityCategory.setSubCommodityCategoryList(this.getSubCommodityCategoryListByPCommodityCategory(commodityCategory));
		}
		return list;
	}
	/**
	 * 根据当前部门获取其下一级别子部门
	 */
	public List<CommodityCategory> getSubCommodityCategoryListByPCommodityCategory(CommodityCategory pCommodityCategory){
		sort =  new PageRequest(0, 9999, Sort.Direction.ASC, "orderNum,id");
		List<CommodityCategory> list = commodityCategoryDao.getSubCommodityCategoryListByPCommodityCategory(pCommodityCategory.getId(), sort).getContent();
		if(list.size() == 0){
			pCommodityCategory.setHasNext(0);	//没有下级商品类别
		}else{
			pCommodityCategory.setHasNext(1);
			//获取其下所有子商品类别
			for (CommodityCategory commodityCategory : list){
				commodityCategory.setSubCommodityCategoryList(this.getSubCommodityCategoryListByPCommodityCategory(commodityCategory));
			}
		}
		return list;
	}
	
	
	

	
	
	
	/**
	 * 组合 商品类别 数据供zTree使用
	 * 
	 * @param list
	 * @return
	 */
	public String getJsonStringForZTree(List<CommodityCategory> list,String jsonString){
		for (CommodityCategory commodityCategory : list){
			jsonString += "{";
			jsonString += "id : "           + commodityCategory.getId() + ",";		
			jsonString += "name : '"        + commodityCategory.getName() + "'" + ",";
			jsonString += "description : '" + commodityCategory.getDescription() + "'" + ",";
			//jsonString += "bmgly_id : '" + bm.getBmgly().getId() + "'" + ",";
			//jsonString += "bmgly_mc : '" + bm.getBmgly().getUsername() + "'" + ",";
			jsonString += "status : "   + commodityCategory.getStatus() +  ",";
			jsonString += "orderNum : " + commodityCategory.getOrderNum()  + ",";
			jsonString += "commodityCategoryLevel : "    + commodityCategory.getLevel()  + ",";
			jsonString += "hasNext : "  + commodityCategory.getHasNext()  + ",";
			jsonString += "pMaxNum : "  + list.size()  + ",";
			jsonString += "maxNum : "   + commodityCategory.getSubCommodityCategoryList().size()  + ",";			
			
			if(commodityCategory.getStatus() == 0){
				jsonString += "font : {'color' : '#c6c6c6'},";
			}
			
			if(commodityCategory.getLevel() == 1 ){
				if(commodityCategory.getHasNext() == 1){
					jsonString += "pId : 0,";
					jsonString += "open : true,";
					//jsonString += "menuUrl : '',";
					jsonString += "iconSkin : 'pIcon01'";
				}else{
					jsonString += "pId : 0,";
					jsonString += "open : true,";
					//jsonString += "menuUrl : '',";
					jsonString += "iconSkin : 'icon0" + RandomUtil.getInstance().randomInt(2,8) + "'";
				}
			}else{
				if(commodityCategory.getHasNext() == 1){
					jsonString += "pId : " + commodityCategory.getCommodityCategory().getId() + ",";
					if(commodityCategory.getLevel() == 2){
						jsonString += "open : true,";
					}
					//jsonString += "menuUrl : '" + bm.getMenuUrl() + "',";
					jsonString += "iconSkin : 'pIcon01'";
				}else{
					jsonString += "pId : " + commodityCategory.getCommodityCategory().getId() + ",";
					if(commodityCategory.getLevel() == 2){
						jsonString += "open : true,";
					}
					//jsonString += "menuUrl : '" + menu.getMenuUrl() + "',";
					jsonString += "iconSkin : 'icon0" + RandomUtil.getInstance().randomInt(2,8) + "'";
				}
			}
			jsonString += "},";
			
			if(commodityCategory.getHasNext() == 1){
				jsonString = this.getJsonStringForZTree(commodityCategory.getSubCommodityCategoryList(), jsonString);
			}
		}
		return jsonString;
	}
	
	
	
//	/**
//	 * 组合 部门 数据供zTree使用,根据 部门管理员   列出部门列表
//	 */
//	public String getJsonStringForZTree(List<Bm> list,String jsonString, User bmgly){
//		for (Bm bm : list){
//			jsonString += "{";
//			jsonString += "id : " + bm.getId() + ",";		
//			if(bm.getBmLevel()!=1){
//				jsonString += "name : '" + bm.getBmmc() + "'" + ",";
//			}else{
//				jsonString += "name : '" + "置空" + "'" + ",";
//			}
//			jsonString += "bmgly_id : '" + bm.getBmgly().getId() + "'" + ",";
//			jsonString += "bmgly_mc : '" + bm.getBmgly().getUsername() + "'" + ",";
//			jsonString += "status : " + bm.getStatus() +  ",";
//			//不是该部门的部门管理员显示灰色
//			if(bm.getBmgly().getId()==bmgly.getId() || bm.getBmLevel()==1){
//				jsonString += "if_bmgly : " + 1 +  ",";
//			}else{
//				jsonString += "if_bmgly : " + 0 +  ",";
//			}
//			
//			jsonString += "orderNum : " + bm.getOrderNum()  + ",";
//			jsonString += "bmLevel : " + bm.getBmLevel()  + ",";
//			jsonString += "hasNext : " + bm.getHasNext()  + ",";
//			jsonString += "pMaxNum : " + list.size()  + ",";
//			jsonString += "maxNum : " + bm.getSubBmList().size()  + ",";			
//			
//			if(bm.getStatus() == 0){
//				jsonString += "font : {'color' : '#c6c6c6'},";
//			}else{
//				//不是该部门的部门管理员显示灰色
//				if(!(bm.getBmgly().getId()==bmgly.getId()  || bm.getBmLevel()==1 )){
//					jsonString += "font : {'color' : '#c6c6c6'},";
//				}
//			}
//			if(bm.getBmLevel() == 1 ){
//				if(bm.getHasNext() == 1){
//					jsonString += "pId : 0,";
//					jsonString += "open : true,";
//					//jsonString += "menuUrl : '',";
//					jsonString += "iconSkin : 'pIcon01'";
//				}else{
//					jsonString += "pId : 0,";
//					jsonString += "open : true,";
//					//jsonString += "menuUrl : '',";
//					jsonString += "iconSkin : 'icon0" + RandomUtil.getInstance().randomInt(2,8) + "'";
//				}
//			}else{
//				if(bm.getHasNext() == 1){
//					jsonString += "pId : " + bm.getBm().getId() + ",";
//					if(bm.getBmLevel() == 2){
//						jsonString += "open : true,";
//					}
//					//jsonString += "menuUrl : '" + bm.getMenuUrl() + "',";
//					jsonString += "iconSkin : 'pIcon01'";
//				}else{
//					jsonString += "pId : " + bm.getBm().getId() + ",";
//					if(bm.getBmLevel() == 2){
//						jsonString += "open : true,";
//					}
//					//jsonString += "menuUrl : '" + menu.getMenuUrl() + "',";
//					jsonString += "iconSkin : 'icon0" + RandomUtil.getInstance().randomInt(2,8) + "'";
//				}
//			}
//			jsonString += "},";
//			
//			if(bm.getHasNext() == 1){
//				jsonString = this.getJsonStringForZTree(bm.getSubBmList(), jsonString, bmgly);
//			}
//		}
//		return jsonString;
//	}
	
	
	
	public CommodityCategory findById(Long id)
	{
		return commodityCategoryDao.findOne(id);
	}
	
	
	/**
	 * 根据 父商品类别  组合 下属的所有商品类别    到同一个list中去
	 */
	public List<CommodityCategory> getOneCommodityCategoryListByCommodityCategory(CommodityCategory commodityCategory){
		List<CommodityCategory> oneCommodityCategoryList = new ArrayList<CommodityCategory>();
		oneCommodityCategoryList.add(commodityCategory);
		oneCommodityCategoryList.addAll(getOneCommodityCategoryListByCommodityCategoryList(getSubCommodityCategoryListByPCommodityCategory(commodityCategory), new ArrayList<CommodityCategory>()));
		return oneCommodityCategoryList;
	}	
	/**
	 * 组合 商品类别list 及下属的所有商品类别    到同一个list中去
	 */
	public List<CommodityCategory> getOneCommodityCategoryListByCommodityCategoryList(List<CommodityCategory> commodityCategoryList, List<CommodityCategory> oneCommodityCategoryList){
		for (CommodityCategory commodityCategory : commodityCategoryList){
			oneCommodityCategoryList.add(commodityCategory);
			if(commodityCategory.getHasNext() == 1){
				oneCommodityCategoryList = this.getOneCommodityCategoryListByCommodityCategoryList(commodityCategory.getSubCommodityCategoryList(), oneCommodityCategoryList);
			}
		}
		return oneCommodityCategoryList;
	}
	
//	/*
//	 * 根据 用户id 判断是否为部门管理 员
//	 */
//	public boolean ifBmgly(Long user_id)
//	{
//		return bmDao.findByBmgly_id(user_id).size()>0;
//	}
//	
//	/*
//	 * 根据  部门管理员_id 返回 他所管理的部门列表
//	 */
//	public List<Bm> findByBmgly_id(Long bmgly_id)
//	{
//		return bmDao.findByBmgly_id(bmgly_id);
//	}


}
