package cn.unis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.ruiyi.base.simplemvc.dao.BaseManagerDao;
import cn.ruiyi.base.simplemvc.service.BaseManagerService;
import cn.unis.dao.CommodityLogisticsDao;
import cn.unis.entity.CommodityLogistics;




/**
 * 商品物流商管理 .service
 * 
 * @author eric
 */
@Component
@Transactional(readOnly = true)
public class CommodityLogisticsService extends BaseManagerService<CommodityLogistics>{
	
	@Autowired
	private CommodityLogisticsDao commodityLogisticsDao;
	@Override
	protected BaseManagerDao<CommodityLogistics> getDomainDao() {
		return commodityLogisticsDao;
	}
	
	/**
	 * 获得所有 可用的物流商 list
	 * @return
	 */
	public List<CommodityLogistics> getAllEnableCommodityLogistic(){
		return commodityLogisticsDao.getAllEnableCommodityLogistic();
	}
//	//@Autowired
//	//private NewsMybatisDao newsMybatisDao;
//
//	@PersistenceContext
//	private EntityManager entityManager;
//	@Autowired
//	private McodeService mcodeService;
//
//		
//	
//	/**
//	 * 根据id查找上一条新闻id
//	 */
//	@SuppressWarnings("unchecked")
//	public Long findPrewNewsId(News news){
//		List<Long> newsIdList = ((Session)entityManager.getDelegate()).createCriteria(News.class)
//									.add(Restrictions.lt("sortNumber", news.getSortNumber()))
//									.addOrder(Order.desc("sortNumber"))
//									.add(Restrictions.eq("status", 1))
//									.setProjection(Projections.property("id"))
//									.setFirstResult(0).setMaxResults(1).list();
//		return newsIdList.size()>0?newsIdList.get(0):null;
//		//return newsMybatisDao.findPrewNewsId(news.getSortNumber(), news.getId());
//	}
//	/**
//	 * 根据id查找下一条新闻id
//	 */
//	@SuppressWarnings("unchecked")
//	public Long findNextNewsId(News news){
//		List<Long> newsIdList = ((Session)entityManager.getDelegate()).createCriteria(News.class)
//									.add(Restrictions.gt("sortNumber", news.getSortNumber()))
//									.addOrder(Order.asc("sortNumber"))
//									.add(Restrictions.eq("status", 1))
//									.setProjection(Projections.property("id"))
//									.setFirstResult(0).setMaxResults(1).list();
//		return newsIdList.size()>0?newsIdList.get(0):null;
//		//return newsMybatisDao.findNextNewsId(news.getSortNumber(), news.getId());
//	}
//
//	/**
//	 * 获得最近的5条相关信息
//	 */
//	@SuppressWarnings("unchecked")
//	public List<News> getRelatedNewList(final String tag){		
//		List<News> relatedNewList = ((Session)entityManager.getDelegate()).createCriteria(News.class)
//										.add(Restrictions.eq("tag", tag))
//										.add(Restrictions.eq("status", 1))
//										.addOrder(Order.desc("sortNumber"))
//										.setFirstResult(0).setMaxResults(5).list();		
//		return relatedNewList;
//	}
//	
//	/**
//	 * 获得最近的5条其它信息
//	 */
//	@SuppressWarnings("unchecked")
//	public List<News> getOtherNewList(final String tag){		
//		List<News> otherNewList = ((Session)entityManager.getDelegate()).createCriteria(News.class)
//										.add(Restrictions.not(Restrictions.eq("tag", tag)))
//										.add(Restrictions.eq("status", 1))
//										.addOrder(Order.desc("sortNumber"))
//										.setFirstResult(0).setMaxResults(5).list();
//		return otherNewList;
//	}
//
//	/**
//	 * 获得排序号最大的newsList
//	 * 用于首页的展示 
//	 * @param rows, 取得的记录数。
//	 */
//	@SuppressWarnings("unchecked")
//	public List<News> getForIndexNewList(int rows){		
//		List<News> newsListForIndex = ((Session)entityManager.getDelegate()).createCriteria(News.class)
//										.addOrder(Order.desc("sortNumber"))
//										.add(Restrictions.eq("status", 1))
//										.setFirstResult(0).setMaxResults(rows).list();
//		for(News news :newsListForIndex){
//			news.setContentWithoutHtml(StringUtil.html2Text(news.getContent()));
//			news.setTagChinese(mcodeService.findByMtypeAndMvalue(McodeUtil.NEWS_TYPE, news.getTag()).getMkey());
//		}
//		return newsListForIndex;
//	}
//	
//	
//	/**
//	 * 根据 tag 返回新闻分页列表
//	 * @param tag
//	 * @return
//	 */
//	public Page<News> findNewsPagedByTag(PageRequest pageable,String tag){
//		return newsDao.findNewsPagedByTag(pageable, tag);
//	}
//	
//	/**
//	 * 返回所有新闻分页列表
//	 * @param tag
//	 * @return
//	 */
//	public Page<News> findAllNewsPaged(PageRequest pageable){
//		return newsDao.findAllNewsPaged(pageable);
//	}
//	
	
}
