package cn.unis.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.ruiyi.base.simplemvc.dao.BaseManagerDao;
import cn.unis.entity.CommodityLogistics;
import cn.unis.entity.CommodityOrder;


/**
 * 
 * 商品物流商管理，dao层
 * @author eric
 * @version 1.0, 2014-01-16
 */
public interface CommodityLogisticsDao extends BaseManagerDao<CommodityLogistics> {	
	
	/**
	 * 获得所有 可用的物流商 list
	 * @return
	 */
	@Query("from CommodityLogistics cl where cl.status=1 order by cl.sortNumber desc ")
	public List<CommodityLogistics> getAllEnableCommodityLogistic();
	
	
}
