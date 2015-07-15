package cn.unis.transit.userWallet;

import java.util.Date;

/**
 * 用户钱包下的 游戏钱包
 * @author Administrator
 *
 */
public class UserGameToken {
	//<consumer key>
	private String key;
	//<game name>
	private String name;
	//<token wallet balance>
	private Integer balance;
	//<token wallet updated at>
	private Date last_update;
	//<token wallet status>
	private Integer status;
	
	
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public Date getLast_update() {
		return last_update;
	}
	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
}
