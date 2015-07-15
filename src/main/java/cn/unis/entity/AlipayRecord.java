package cn.unis.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.ruiyi.base.constants.Constants;

/**
 * 支付宝充值记录
 * @author fanzz
 *
 */
@Entity
@Table(name = "backend_alipay_record", schema = Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AlipayRecord extends IdEntity implements Serializable {

	public static final byte STATUS_UNHANDLE 					= 0; //状态，未处理
	public static final byte STATUS_APLPAY_CALLBACK_SUCCESS 	= 1; //状态，支付宝回调成功
	public static final byte STATUS_SEND_WALO 				= 2; //状态，已发送walo
	public static final byte STATUS_WALO_SUCCESS 				= 3; //状态，walo处理成功	
	
	public static final int WALLET_TYPE_SYCONIN = 1;	//钱包类型，游币(以前称世宇币)
	public static final int WALLET_TYPE_TOKEN   = 3;	//钱包类型，实体币
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8772278154330509731L;
	/**
	 * 充值用户
	 */
	private String username;
	/**
	 * walo的key
	 */
	private String userkey;
	/**
	 * 世宇大钱包
	 */
	private int sycoin;
	/**
	 * 游戏钱包
	 */
	private int token;
	/**
	 * 支付金额（折扣 = payMoney / czMount ）
	 */
	private float payMoney;
	/**
	 * 支付方式
	 * 1.支付宝即时到帐
	 * 2.支付宝网银
	 */
	private byte payType;
	/**
	 * 充值状态
	 * 0.未处理
	 * 1.支付宝回调成功
	 * 2.已发送walo
	 * 3.walo处理成功
	 */
	private byte status;
	/**
	 * 签名MD5(outTradeNo + amount + userkey)
	 */
	private String sign;
	/**
	 * 订单编号
	 */
	private String outTradeNo;
	/**
	 * 充值的游戏key
	 */
	private String gameKey;
	/**
	 * 游乐场的key
	 */
	private String gameCenterKey;
	/**
	 * 钱包类型
	 * 1.游币钱包 （以前称的世宇钱包）
	 * 2.游戏钱包  已不使用 2014-06-26
	 * 3.实体币钱包 2014-06-26新增
	 */
	private int walletType;
	/**
	 * 充值时间
	 */
	private Date date;
	/**
	 * 充值折扣
	 */
	private float discount;
	/**
	 * 兑换比例
	 */
	private Integer changeRate;
	
	private String alipayTradeNo;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserkey() {
		return userkey;
	}
	public void setUserkey(String userkey) {
		this.userkey = userkey;
	}
	public int getSycoin() {
		return sycoin;
	}
	public void setSycoin(int sycoin) {
		this.sycoin = sycoin;
	}
	public int getToken() {
		return token;
	}
	public void setToken(int token) {
		this.token = token;
	}
	public float getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(float payMoney) {
		this.payMoney = payMoney;
	}
	public byte getPayType() {
		return payType;
	}
	public void setPayType(byte payType) {
		this.payType = payType;
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getGameKey() {
		return gameKey;
	}
	public void setGameKey(String gameKey) {
		this.gameKey = gameKey;
	}
	public String getGameCenterKey() {
		return gameCenterKey;
	}
	public void setGameCenterKey(String gameCenterKey) {
		this.gameCenterKey = gameCenterKey;
	}
	public int getWalletType() {
		return walletType;
	}
	public void setWalletType(int walletType) {
		this.walletType = walletType;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public String getAlipayTradeNo() {
		return alipayTradeNo;
	}
	public void setAlipayTradeNo(String alipayTradeNo) {
		this.alipayTradeNo = alipayTradeNo;
	}
	public Integer getChangeRate() {
		return changeRate;
	}
	public void setChangeRate(Integer changeRate) {
		this.changeRate = changeRate;
	}
	
}
