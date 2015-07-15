package cn.unis.service.interfaces;

import java.util.List;

import cn.unis.entity.AlipayRecord;
import cn.unis.transit.BaseReturnValue;
import cn.unis.transit.ChargeToSycoin;
import cn.unis.transit.token.ChargeToToken;

public interface IAlipayRecordService {

	public static final byte ALIPAY = 0;
	public static final byte BANKPAY = 1;
	public static final byte UNHANDLE = 0;//未处理
	public static final byte ALIPAY_SUCCESS = 1;//支付宝回调成功
	public static final byte SEND = 2;//已经发送到walo
	public static final byte HANDLE = 3;//walo处理成功
	public static final int SYCOIN = 0;
	public static final int TOKEN = 1;

	public void saveAndFlush(AlipayRecord alipayRecord);

	public List<AlipayRecord> findByStatus(byte status);

	public AlipayRecord findBySignAndStatus(String sign,byte status);

	public AlipayRecord findByOutTradeNo(String outTradeNo);

	public int saveAndReturnUpdateRows(byte UNHANDLE, byte ALIPAY_SUCCESS,String outTradeNo);

	public int saveAndReturnUpdateRowsBySign(byte ALIPAY_SUCCESS, byte SEND,String sign);

	public  BaseReturnValue<ChargeToSycoin> requestSycoinToWalo(String out_trade_no);

	public  BaseReturnValue<ChargeToToken> requestTokenToWalo(String out_trade_no);
}
