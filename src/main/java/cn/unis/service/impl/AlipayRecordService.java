package cn.unis.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.unis.dao.AlipayRecordDao;
import cn.unis.dao.AlipayRecordMybatisDao;
import cn.unis.dto.SycoinOrTokenDto;
import cn.unis.entity.AlipayRecord;
import cn.unis.service.interfaces.IAlipayRecordService;
import cn.unis.service.interfaces.IGameCenterService;
import cn.unis.transit.BaseReturnValue;
import cn.unis.transit.ChargeToSycoin;
import cn.unis.transit.token.ChargeToToken;
import cn.unis.utils.JsonUtils;
import cn.unis.utils.Security;

/**
 *
 * @author fanzz
 *
 */
@Component
@Transactional(readOnly = false)
public class AlipayRecordService implements IAlipayRecordService{
	private static Logger logger =LoggerFactory.getLogger(AlipayRecordService.class);
	@Autowired
	private AlipayRecordDao alipayRecordDao;
	@Autowired
	private AlipayRecordMybatisDao alipayRecordMybatisDao;
	@Autowired
	private IGameCenterService iGameCenterService;

	@Transactional(readOnly=false)
	@Override
	public void saveAndFlush(AlipayRecord alipayRecord) {
		alipayRecordDao.save(alipayRecord);
		alipayRecordDao.flush();
	}

	@Transactional(readOnly=true)
	@Override
	public AlipayRecord findBySignAndStatus(String sign,byte status) {
		return alipayRecordDao.findBySignAndStatus(sign, status);
	}

	@Transactional(readOnly=true)
	@Override
	public AlipayRecord findByOutTradeNo(String outTradeNo) {
		return alipayRecordDao.findByOutTradeNo(outTradeNo);
	}
	@Transactional(readOnly=false)
	@Override
	public int saveAndReturnUpdateRows(byte UNHANDLE, byte ALIPAY_SUCCESS,String outTradeNo){
		return alipayRecordMybatisDao.saveAndReturnUpdateRows(UNHANDLE, ALIPAY_SUCCESS, outTradeNo);
	}

	@Transactional(readOnly=false)
	@Override
	public int saveAndReturnUpdateRowsBySign(byte ALIPAY_SUCCESS, byte SEND ,String sign){
		return alipayRecordMybatisDao.saveAndReturnUpdateRowsBySign(ALIPAY_SUCCESS, SEND, sign);
	}

	@Override
	public List<AlipayRecord> findByStatus(byte status) {
		return alipayRecordDao.findByStatus(status);
	}
	/**
	 *  调walo接口
	 */
	@Transactional(readOnly=true)
	@Override
	public  BaseReturnValue<ChargeToSycoin> requestSycoinToWalo(String out_trade_no){

		AlipayRecord alipayRecord = findByOutTradeNo(out_trade_no);

		SycoinOrTokenDto sycoinOrTokenDto = new SycoinOrTokenDto();
		SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(alipayRecord.getDate());
		sycoinOrTokenDto.setDate_time(str);
		sycoinOrTokenDto.setOutTradeNo(alipayRecord.getOutTradeNo());
		sycoinOrTokenDto.setPayMoney(alipayRecord.getPayMoney());
		String jsonData = JsonUtils.toJson(sycoinOrTokenDto);
		logger.info("jsonData ======================================> " + jsonData);
		//String sign = AESUtils.encrypt(jsonData);
		String sign = Security.encrypt(jsonData,Security.ENCRYPTION_KEY);
		logger.info("jsonData sign======================================> " + sign);


		BaseReturnValue<ChargeToSycoin> chargeToSycoin = null;
		if(alipayRecord.getWalletType()==SYCOIN){
			chargeToSycoin = iGameCenterService.chargeToSycoin(alipayRecord.getUserkey(), alipayRecord.getSycoin(), sign);
		}
		return chargeToSycoin;
	}

	@Transactional(readOnly=true)
	@Override
	public  BaseReturnValue<ChargeToToken> requestTokenToWalo(String out_trade_no){

		AlipayRecord alipayRecord = findByOutTradeNo(out_trade_no);

		SycoinOrTokenDto sycoinOrTokenDto = new SycoinOrTokenDto();
		SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(alipayRecord.getDate());
		sycoinOrTokenDto.setDate_time(str);
		sycoinOrTokenDto.setOutTradeNo(alipayRecord.getOutTradeNo());
		sycoinOrTokenDto.setPayMoney(alipayRecord.getPayMoney());
		String jsonData = JsonUtils.toJson(sycoinOrTokenDto);
		logger.info("jsonData ======================================> " + jsonData);
		//String sign = AESUtils.encrypt(jsonData);
		String sign = Security.encrypt(jsonData,Security.ENCRYPTION_KEY);
		logger.info("jsonData sign======================================> " + sign);


		BaseReturnValue<ChargeToToken>  chargeToToken = null;
		if(alipayRecord.getWalletType()==TOKEN){
			chargeToToken = iGameCenterService.chargeToToken(alipayRecord.getUserkey(), alipayRecord.getToken(), sign);
		}
		return chargeToToken;
	}

}
