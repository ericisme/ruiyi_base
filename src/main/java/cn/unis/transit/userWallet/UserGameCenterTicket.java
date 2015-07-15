package cn.unis.transit.userWallet;

import java.util.Date;

/**
 * 用户在某游场里的 彩票结余
 * @author Administrator
 *
 */
public class UserGameCenterTicket {

	private Integer balance;
	private Date last_update;
	private Integer status;
	
	
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
