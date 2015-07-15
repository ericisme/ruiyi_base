package cn.unis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.ruiyi.base.service.mcode.McodeService;
import cn.ruiyi.base.service.mcode.McodeUtil;
import cn.ruiyi.base.util.StringUtil;
import cn.ruiyi.base.simplemvc.dao.BaseManagerDao;
import cn.ruiyi.base.simplemvc.service.BaseManagerService;
import cn.unis.dao.NewsDao;
import cn.unis.entity.Game;
import cn.unis.entity.News;




/**
 * 新闻管理类.service
 * 
 * @author eric
 */
@Component
@Transactional(readOnly = true)
public class NewsService extends BaseManagerService<News>{
	
	@Autowired
	private NewsDao newsDao;
	@Override
	protected BaseManagerDao<News> getDomainDao() {
		return newsDao;
	}
	//@Autowired
	//private NewsMybatisDao newsMybatisDao;

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private McodeService mcodeService;

		
	
	/**
	 * 根据id查找上一条新闻id
	 */
	@SuppressWarnings("unchecked")
	public Long findPrewNewsId(News news){
		List<Long> newsIdList = ((Session)entityManager.getDelegate()).createCriteria(News.class)
									.add(Restrictions.lt("sortNumber", news.getSortNumber()))
									.addOrder(Order.desc("sortNumber"))
									.add(Restrictions.eq("status", 1))
									.setProjection(Projections.property("id"))
									.setFirstResult(0).setMaxResults(1).list();
		return newsIdList.size()>0?newsIdList.get(0):null;
		//return newsMybatisDao.findPrewNewsId(news.getSortNumber(), news.getId());
	}
	/**
	 * 根据id查找下一条新闻id
	 */
	@SuppressWarnings("unchecked")
	public Long findNextNewsId(News news){
		List<Long> newsIdList = ((Session)entityManager.getDelegate()).createCriteria(News.class)
									.add(Restrictions.gt("sortNumber", news.getSortNumber()))
									.addOrder(Order.asc("sortNumber"))
									.add(Restrictions.eq("status", 1))
									.setProjection(Projections.property("id"))
									.setFirstResult(0).setMaxResults(1).list();
		return newsIdList.size()>0?newsIdList.get(0):null;
		//return newsMybatisDao.findNextNewsId(news.getSortNumber(), news.getId());
	}

	/**
	 * 获得最近的5条相关信息
	 */
	@SuppressWarnings("unchecked")
	public List<News> getRelatedNewList(final String tag){		
		List<News> relatedNewList = ((Session)entityManager.getDelegate()).createCriteria(News.class)
										.add(Restrictions.eq("tag", tag))
										.add(Restrictions.eq("status", 1))
										.addOrder(Order.desc("sortNumber"))
										.setFirstResult(0).setMaxResults(5).list();		
		return relatedNewList;
	}
	
	/**
	 * 获得最近的5条其它信息
	 */
	@SuppressWarnings("unchecked")
	public List<News> getOtherNewList(final String tag){		
		List<News> otherNewList = ((Session)entityManager.getDelegate()).createCriteria(News.class)
										.add(Restrictions.not(Restrictions.eq("tag", tag)))
										.add(Restrictions.eq("status", 1))
										.addOrder(Order.desc("sortNumber"))
										.setFirstResult(0).setMaxResults(5).list();
		return otherNewList;
	}

	/**
	 * 获得排序号最大的newsList
	 * 用于首页的展示 
	 * @param rows, 取得的记录数。
	 */
	@SuppressWarnings("unchecked")
	public List<News> getForIndexNewList(int rows){		
		List<News> newsListForIndex = ((Session)entityManager.getDelegate()).createCriteria(News.class)
										.addOrder(Order.desc("sortNumber"))
										.add(Restrictions.eq("status", 1))
										.setFirstResult(0).setMaxResults(rows).list();
		for(News news :newsListForIndex){
			news.setContentWithoutHtml(StringUtil.html2Text(news.getContent()));
			news.setTagChinese(mcodeService.findByMtypeAndMvalue(McodeUtil.NEWS_TYPE, news.getTag()).getMkey());
		}
		return newsListForIndex;
	}
	
	
	/**
	 * 根据 tag 返回新闻分页列表
	 * @param tag
	 * @return
	 */
	public Page<News> findNewsPagedByTag(PageRequest pageable,String tag){
		return newsDao.findNewsPagedByTag(pageable, tag);
	}
	
	/**
	 * 返回所有新闻分页列表
	 * @param tag
	 * @return
	 */
	public Page<News> findAllNewsPaged(PageRequest pageable){
		return newsDao.findAllNewsPaged(pageable);
	}
	
	
	/**
	 * 按条件 分页 查询 
	 * @param queryType null || "-1" 不作为筛选条件
	 * @param queryName null 不作为筛选条件
	 * @param isHot 是否热门游戏; 非必填，1为是，0为不是
	 * @param pageNum 第几页, 不填写默认第1页
	 * @param pageable
	 * @return
	 */
	public Page<News> findAll(
						final String queryType,
						final String queryTitle,					
						final Pageable pageable
						) {
		return this.newsDao.findAll(new Specification<News>() {
			@Override
			public Predicate toPredicate(Root<News> news,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				
				//类型
				if (StringUtils.isNotBlank(queryType) && !queryType.equals("-1")) {
					Path<String> expression = news.get("tag");
					Predicate p = cb.equal(expression, queryType);
					predicateList.add(p);
				}
				//标题
				if(StringUtils.isNotBlank(queryTitle)){
					Path<String> expression = news.get("title");
					Predicate p = cb.like(expression, "%" + queryTitle + "%") ;
					predicateList.add(p);
				}

				//状态，固定为启用
				Path<Integer> expression = news.get("status");
				Predicate p = cb.equal(expression, 1);
				predicateList.add(p);

				Predicate[] predicates = new Predicate[predicateList.size()];
				predicateList.toArray(predicates);

				if (predicates.length > 0) {
					return cb.and(predicates);
				} else {
					return cb.conjunction();
				}
			}
		},pageable);
	}
	
	
}
