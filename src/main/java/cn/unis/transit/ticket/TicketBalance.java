package cn.unis.transit.ticket;

import java.util.Date;


/**
 * 会员的彩票结余查询
 * @author Administrator
 *
 */
public class TicketBalance {
	
	//彩票结余
	private Integer balance;
	
	//时间
	private Date updated_at;
	
	//状态,已知 1为正常，其它未知
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
	
	
	
	
}
