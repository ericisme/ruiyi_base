package cn.unis.transit.userWallet;

import java.util.Date;

/**
 * 世宇钱包,sycoin
 * @author Administrator
 *
 */
public class Sycoin {

	//余额
	private Integer balance;
	//更新时间
	private Date updated_at;
	private Date last_update;
	/**
	 * 状态
	 *const WALLET_STATUS_NORMAL = 1;
	 *const WALLET_STATUS_LOCKED = 2;
	 *const WALLET_STATUS_BANNED = 3;
	 */
	private Integer status;
	
	
	
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getLast_update() {
		return last_update;
	}
	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}
	
	
}
