package cn.unis.service.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.ruiyi.base.util.StringUtil;
import cn.unis.api4walo.dto.DateAndTimesDto;
import cn.unis.dao.ClickLogDao;
import cn.unis.entity.ClickLog;

/**
 * 下载量/点击量详细记录 service
 *
 * @author fanzz
 *
 */
@Component
 @Transactional(readOnly = true)
public class ClickLogService {
	
	@Autowired
	private ClickLogDao gameDao;
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	public ClickLog save(ClickLog entity){
		return gameDao.saveAndFlush(entity);
	}
	
	
	public List<DateAndTimesDto> listDateAndTimesListByGameId(Long game_id, String begin_date, String end_date){
		StringBuffer sb = new StringBuffer(" select date_format(gcl.date_time,'%Y-%m-%d') as date ,sum(times) as times ");
		sb.append(" from unis_game_click_log gcl ");
		sb.append(" where gcl.entity_id in (select pana.id from unis_platform_and_market pana where pana.game_fk_id=:game_id) ");
		sb.append(" and gcl.type=:click_log_type ");
		if(!StringUtil.isNullAndBlank(begin_date))
			sb.append(" and date_format(gcl.date_time,'%Y-%m-%d') >= :begin_date ");
		if(!StringUtil.isNullAndBlank(end_date))
			sb.append(" and date_format(gcl.date_time,'%Y-%m-%d') <= :end_date ");
		sb.append(" group by date_format(gcl.date_time,'%Y-%m-%d') ");		
		
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("game_id", game_id);
		query.setParameter("click_log_type", ClickLog.GAME_DOWNLOAD);
		if(!StringUtil.isNullAndBlank(begin_date))
			query.setParameter("begin_date", begin_date);
		if(!StringUtil.isNullAndBlank(end_date))
			query.setParameter("end_date", end_date);
		//to entity
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(DateAndTimesDto.class));
		//to map
		/*query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List rows = query.getResultList();
	    for (Object obj : rows) {
	        Map row = (Map) obj;
	        System.out.println("id = " + row.get("ID"));
	        System.out.println("name = " + row.get("NAME"));
	        System.out.println("age = " + row.get("AGE"));
	    }*/
		//一般做法
		/*List rows = query.getResultList();
	    for (Object row : rows) {
	        Object[] cells = (Object[]) row;
	        System.out.println("id = " + cells[0]);
	        System.out.println("name = " + cells[1]);
	        System.out.println("age = " + cells[2]);
	    }*/
		return query.getResultList();		
	}
	
}
