package cn.unis.transit.achievement;

public class AchievementPaymentReturns {

	/**
	 * tx_code,请保存此码到数据库，用于退款使用(即交易不成功时回退已经扣减的积分)
	 */
	private String tx_code;
	
	
	/**
	 * 扣减成功后的剩余金额
	 */
	private Integer balance;


	
	
	
	public String getTx_code() {
		return tx_code;
	}


	public void setTx_code(String tx_code) {
		this.tx_code = tx_code;
	}


	public Integer getBalance() {
		return balance;
	}


	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	
	
}
