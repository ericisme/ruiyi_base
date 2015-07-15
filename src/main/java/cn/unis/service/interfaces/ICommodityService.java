package cn.unis.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import cn.unis.entity.Commodity;

/**
 * 商品管理接口
 * @author fanzz
 *
 */
public interface ICommodityService {
	/**
	 * 用于后台管理员 新增/编辑 的save方法
	 * @param commodity
	 * @return
	 */
	@Transactional(readOnly = false)
	public String saveForAdmin(Commodity commodity);
	/**
	 * 直接保存，不作逻辑处理
	 * @param commodity
	 * @return
	 */
	public Commodity save(Commodity commodity);	
	/**
	 * 改变状态
	 * @param id
	 * @param status
	 * @return
	 */
	public boolean changeStatus(Long id,Integer status);
	/**
	 * 删除
	 * @param ids
	 */
	public void delete(String ids);
	/**
	 * 根据id查找记录
	 * @param id
	 * @return
	 */
	public Commodity findById(Long id);
	/**
	 * 分页查询
	 * @param pictrueName 名字
	 * @param queryStatus 状态
	 * @param pageable 
	 * @return
	 */
	public Page<Commodity> findAll(final Long _commodityCategory_id,final String name ,final Integer  queryStatus ,final Integer price,Pageable pageable);
	/**
	 * 
	 * @return
	 */
	public Commodity findTheLastPriceCommodity();
	/**
	 * 获取所有启用状态的ppt
	 * @return
	 */
	public List<Commodity> getAllPPTWithEnabledStatus();
	/**
	 * 根据类别查询商品list
	 */
	public List<Commodity> getCommodityListByCommoditCategoryId(Long CommoditCategoryId);
	
	public boolean updateStocks(Long id,int delta);
}
