package cn.unis.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import cn.unis.entity.CommodityCategory;

/**
 * 
 * Title: 		商品类别管理DAO
 * Project: 	unisCommerce
 * Company:		qtone
 * 
 */
public interface CommodityCategoryDao extends JpaRepository<CommodityCategory, Long>{
	
	/**
	 *  获取某一级别 类别列表
	 */
	@Query("FROM CommodityCategory cc WHERE cc.level = ?1 ")
	Page<CommodityCategory> getCommodityCategoryListByLevel(int level, Pageable pageable);
	
	/**
	 *  根据当前部门获取其下一级别子部门
	 */
	@Query("FROM CommodityCategory cc WHERE cc.commodityCategory.id = ?1 ")
	Page<CommodityCategory> getSubCommodityCategoryListByPCommodityCategory(Long pid, Pageable pageable);
	
	/**
	 * 判断 商品类别名称 是否 已存在
	 */
	@Query("FROM CommodityCategory cc WHERE cc.id!=?1 and cc.name = ?2 ")
	List<CommodityCategory> nameExist(Long exceptId, String name);
	

}
