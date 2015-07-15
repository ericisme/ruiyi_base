package cn.unis.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;

import cn.ruiyi.base.constants.Constants;

/**
 * 下载量/点击量详细记录
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "unis_game_click_log",schema=Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ClickLog extends IdEntity implements Serializable {
	
	
	
	public static final Integer GAME_POPULARITY = 101;	//点击类型，游戏人气(点击量)
	public static final Integer GAME_DOWNLOAD   = 102;	//点击类型，游戏下载
	public static final Integer News_POPULARITY = 201;	//点击类型，新闻人气(点击量)	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9057092222981220337L;
	
	/**
	 * 构造方法
	 */
	public ClickLog(Integer type, Long entity_id, Integer times, Date dateTime, String sessionId){
		this.type=type;
		this.entity_id=entity_id;
		this.times=times;
		this.dateTime=dateTime;
		this.sessionId=sessionId;
	}
	
	/**
	 * 类型
	 * 101.游戏点击量, 对应entity_id为game_id
	 * 202.游戏下载量, 对应entity_id为platformAndMarket_id
	 * 201.新闻点点击量, 对应entity_id为news_id
	 */
	private Integer type;
	@Index(name="index_unis_click_log_type")
	@Column(nullable=false,length=5)
	public Integer getType() {return type;}
	public void setType(Integer type) {this.type = type;}

	/**
	 * 要统计的实体 点击量 的id
	 */
	private Long entity_id;	
	@Index(name="index_unis_click_log_entity_id")
	@Column(nullable=false)
	public Long getEntity_id() {return entity_id;}
	public void setEntity_id(Long entity_id) {this.entity_id = entity_id;}

	/**
	 * 次数
	 */
	private Integer times;	
	@Index(name="index_unis_click_log_times")
	@Column(nullable=false)
	public Integer getTimes() {	return times;}
	public void setTimes(Integer times) {this.times = times;}

	/**
	 * 时间
	 */
	private Date dateTime;	
	@Index(name="index_unis_click_log_date_time")
	@Column(nullable=false)
	public Date getDateTime() {return dateTime;}
	public void setDateTime(Date dateTime) {this.dateTime = dateTime;}

	/**
	 * 客户端ID
	 */
	@Index(name="index_unis_click_session_id")
	private String sessionId;
	public String getSessionId() {return sessionId;}	
	public void setSessionId(String sessionId) {this.sessionId = sessionId;}
	
}
