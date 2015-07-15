package cn.unis.dao;

import org.apache.ibatis.annotations.Param;

import cn.unis.utils.MyBatisRepository;

@MyBatisRepository
public interface AlipayRecordMybatisDao {
	public int saveAndReturnUpdateRows(@Param("UNHANDLE") int UNHANDLE,@Param("ALIPAY_SUCCESS") int ALIPAY_SUCCESS,@Param("outTradeNo") String outTradeNo);
	
	public int saveAndReturnUpdateRowsBySign(@Param("ALIPAY_SUCCESS") int ALIPAY_SUCCESS,@Param("SEND") int SEND,@Param("sign") String sign);
}
