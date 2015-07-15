package cn.unis.transit.chargeRecord;

import cn.unis.transit.Consumer;
import cn.unis.transit.GameCenterSimpleInfo;

/**
 * 世宇币充值记录
 * @author Administrator
 *
 */
public class TokensRechargeRecord {
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
	
	/**
	 * 游戏, only key and name
	 */
	private Consumer consumer;
	
	/**
	 * 游乐场, only key and name
	 */
	private GameCenterSimpleInfo game_center;
	
	
	
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
	public Consumer getConsumer() {
		return consumer;
	}
	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}
	public GameCenterSimpleInfo getGame_center() {
		return game_center;
	}
	public void setGame_center(GameCenterSimpleInfo game_center) {
		this.game_center = game_center;
	}
	
	
}
