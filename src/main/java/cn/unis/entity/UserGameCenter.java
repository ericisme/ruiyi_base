package cn.unis.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.entity.User;

/** 
 * 用户 与 游乐场  关联 实体
 * 
 * @author chen
 */
@Entity
@Table(name = "unis_user_game_center", schema = Constants.BASE)
// 默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserGameCenter extends IdEntity implements Serializable  {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2684335413879137471L;
	private User user;
	private String gameCenterKey;
	
	/**
	 * 显示用，不作数据库保存
	 */
	private String gameCenterName;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getGameCenterKey() {
		return gameCenterKey;
	}
	public void setGameCenterKey(String gameCenterKey) {
		this.gameCenterKey = gameCenterKey;
	}
	
	@Transient
	public String getGameCenterName() {
		return gameCenterName;
	}
	public void setGameCenterName(String gameCenterName) {
		this.gameCenterName = gameCenterName;
	}
	
	
	
	
}