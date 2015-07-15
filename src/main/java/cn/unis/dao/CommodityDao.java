package cn.unis.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import cn.unis.entity.Commodity;

/**
 * 
 * @author fanzz
 * 
 */
public interface CommodityDao extends JpaRepository<Commodity, Long>,
		JpaSpecificationExecutor<Commodity>,PagingAndSortingRepository<Commodity, Long> {
	public Commodity findByCommodityNo(String commodityNo);
	
	public Commodity findByName(String name);
	/**
	 * 根据状态查找出所有商品
	 */
	public List<Commodity> findByStatusOrderBySortNumberAsc(int status);
	
	/**
	 * 根据类别查询商品list
	 */
	@Query("FROM Commodity c WHERE c.commodityCategory.id = ?1 ")
	List<Commodity> getCommodityListByCommoditCategoryId(Long CommoditCategoryId);
	
}
