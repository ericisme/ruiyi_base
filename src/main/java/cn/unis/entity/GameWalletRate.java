package cn.unis.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.entity.User;

/**
 * 游戏      兑换比值            实体
 * 游戏钱包 由     游戏(consumer_key)+游场(game_center_key) 二者组合而成。
 * 兑换比值，一世宇币换多少游戏币
 * 如，如果1世宇币兑换60游戏币，则rate为60;如果10个世宇币兑换一个游戏币，则rate为0.1; 
 * 如果不创建此记录，兑换比值默认为1	 *
 * @author Eric
 *
 */
@Entity
@Table(name = "unis_game_wallet_rate",schema=Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GameWalletRate extends IdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4530783397821204741L;

	/**
	 * 游场key
	 */
	private String gameCenterKey;	
	
	/**
	 * 游场名称
	 */
	private String gameCenterName;	
	/**
	 * 游戏key
	 */
	private String consumerKey;
	
	/**
	 * 游戏名
	 */
	private String consumerName;
	/**
	 * 兑换比值;
	 * 必须大于1的整数
	 * 一世宇币换多少游戏币;
	 * 如，如果1世宇币兑换60游戏币，则填写60;
	 * 如果不创建此记录，兑换比值默认为1.
	 */
	private Integer rate;	
	/**
	 * 新增/编辑时间
	 */
	private Date editTime;
	/**
	 * 操作人
	 */
	private User user;
	
	
	/**
	 * 是否为历史记录
	 * 此处做了历史处理，会保留所有修改过的历史
	 * 所以每次编辑旧记录的时候，会创建新记录，旧记录状态 会改为1
	 * 可用记录的状态都为0;
	 * @return
	 */
	private int history = 0;
	
	
	
	private String gameCenterProvinceName;//传输用，不保存到数库
	private String gameCenterProvinceCode;//传输用，不保存到数库
	private String gameCenterCityName;//传输用，不保存到数库
	private String gameCenterCityCode;//传输用，不保存到数库
	
	
	public String getGameCenterKey() {
		return gameCenterKey;
	}
	public void setGameCenterKey(String gameCenterKey) {
		this.gameCenterKey = gameCenterKey;
	}
	public String getGameCenterName() {
		return gameCenterName;
	}
	public void setGameCenterName(String gameCenterName) {
		this.gameCenterName = gameCenterName;
	}
	public String getConsumerKey() {
		return consumerKey;
	}
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	public Date getEditTime() {
		return editTime;
	}
	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}
	public int getHistory() {
		return history;
	}
	public void setHistory(int history) {
		this.history = history;
	}	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Transient
	public String getGameCenterProvinceName() {
		return gameCenterProvinceName;
	}
	public void setGameCenterProvinceName(String gameCenterProvinceName) {
		this.gameCenterProvinceName = gameCenterProvinceName;
	}
	@Transient
	public String getGameCenterProvinceCode() {
		return gameCenterProvinceCode;
	}
	public void setGameCenterProvinceCode(String gameCenterProvinceCode) {
		this.gameCenterProvinceCode = gameCenterProvinceCode;
	}
	@Transient
	public String getGameCenterCityName() {
		return gameCenterCityName;
	}
	public void setGameCenterCityName(String gameCenterCityName) {
		this.gameCenterCityName = gameCenterCityName;
	}
	@Transient
	public String getGameCenterCityCode() {
		return gameCenterCityCode;
	}
	public void setGameCenterCityCode(String gameCenterCityCode) {
		this.gameCenterCityCode = gameCenterCityCode;
	}
	
	
	
	
	
}
