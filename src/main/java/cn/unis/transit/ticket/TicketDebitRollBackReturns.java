package cn.unis.transit.ticket;

public class TicketDebitRollBackReturns {

	/**
	 * tx_code,请保存此码到数据库，用于退款使用(即交易不成功时回退已经扣减的彩票)
	 */
	private String tx_code;
	
	/**
	 * 彩票 回退成功后的结余
	 */
	private Integer balance;

	
	
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public String getTx_code() {
		return tx_code;
	}
	public void setTx_code(String tx_code) {
		this.tx_code = tx_code;
	}

	
}
