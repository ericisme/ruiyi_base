package cn.unis.wwc.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.ruiyi.base.service.mcode.McodeService;
import cn.ruiyi.base.service.mcode.McodeUtil;
import cn.ruiyi.base.util.StringUtil;
import cn.ruiyi.base.simplemvc.dao.BaseManagerDao;
import cn.ruiyi.base.simplemvc.service.BaseManagerService;
import cn.unis.wwc.dao.InvitationNewsDao;
import cn.unis.wwc.entity.InvitationNews;




/**
 * 大会新闻管理类.service
 * 
 * @author eric
 */
@Component
@Transactional(readOnly = true)
public class InvitationNewsService extends BaseManagerService<InvitationNews>{
	
	@Autowired
	private InvitationNewsDao invitationNewsDao;
	@Override
	protected BaseManagerDao<InvitationNews> getDomainDao() {
		return invitationNewsDao;
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
	public Long findPrewNewsId(InvitationNews invitationNews){
		List<Long> newsIdList = ((Session)entityManager.getDelegate()).createCriteria(InvitationNews.class)
									.add(Restrictions.lt("sortNumber", invitationNews.getSortNumber()))
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
	public Long findNextNewsId(InvitationNews invitationNews){
		List<Long> newsIdList = ((Session)entityManager.getDelegate()).createCriteria(InvitationNews.class)
									.add(Restrictions.gt("sortNumber", invitationNews.getSortNumber()))
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
	public List<InvitationNews> getRelatedNewList(final String tag){		
		List<InvitationNews> relatedNewList = ((Session)entityManager.getDelegate()).createCriteria(InvitationNews.class)
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
	public List<InvitationNews> getOtherNewList(final String tag){		
		List<InvitationNews> otherNewList = ((Session)entityManager.getDelegate()).createCriteria(InvitationNews.class)
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
	 * @param formType, 表单版本 11=英文 21=中文
	 */
	@SuppressWarnings("unchecked")
	public List<InvitationNews> getForIndexNewList(int rows, Integer formType){		
		List<InvitationNews> newsListForIndex = ((Session)entityManager.getDelegate()).createCriteria(InvitationNews.class)
										.addOrder(Order.desc("sortNumber"))
										.add(Restrictions.eq("status", 1))
										.add(Restrictions.eq("formType", formType))
										.setFirstResult(0).setMaxResults(rows).list();
		for(InvitationNews invitationNews :newsListForIndex){
			invitationNews.setContentWithoutHtml(StringUtil.html2Text(invitationNews.getContent()));
			invitationNews.setTagChinese(mcodeService.findByMtypeAndMvalue(McodeUtil.INVITATION_NEWS_TYPE_CHINESE, invitationNews.getTag()).getMkey());
			invitationNews.setTagEnglish(mcodeService.findByMtypeAndMvalue(McodeUtil.INVITATION_NEWS_TYPE_ENGLISH, invitationNews.getTag()).getMkey());
		}
		return newsListForIndex;
	}
	
	
	/**
	 * 根据 tag 返回新闻分页列表
	 * @param tag
	 * @return
	 */
	public Page<InvitationNews> findNewsPagedByTag(PageRequest pageable,String tag){
		return invitationNewsDao.findInvitationNewsPagedByTag(pageable, tag);
	}
	
	/**
	 * 返回所有新闻分页列表
	 * @param tag
	 * @return
	 */
	public Page<InvitationNews> findAllNewsPaged(PageRequest pageable){
		return invitationNewsDao.findAllInvitationNewsPaged(pageable);
	}
	
	
}
