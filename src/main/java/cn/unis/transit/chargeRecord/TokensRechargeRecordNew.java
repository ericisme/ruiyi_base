package cn.unis.transit.chargeRecord;

import cn.unis.transit.Consumer;
import cn.unis.transit.GameCenterSimpleInfo;

/**
 * 世宇币充值记录
 * @author Administrator
 *
 */
public class TokensRechargeRecordNew {
	/**
	 * 日期
	 */
	private String record_date;
	/**
	 * 
	 */
	private String tx_num;
	/**
	 * 数量
	 */
	private Integer amount;
	/**
	 * 订单号
	 */
	private String outTradeNo;
	
	private String game_center_key;
	
	
	
	public String getRecord_date() {
		return record_date;
	}
	public void setRecord_date(String record_date) {
		this.record_date = record_date;
	}
	public String getTx_num() {
		return tx_num;
	}
	public void setTx_num(String tx_num) {
		this.tx_num = tx_num;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getGame_center_key() {
		return game_center_key;
	}
	public void setGame_center_key(String game_center_key) {
		this.game_center_key = game_center_key;
	}

	
	
}
