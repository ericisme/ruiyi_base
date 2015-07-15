package cn.unis.timer;

import java.io.File;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ruiyi.base.filter.RequestLogServiceJDBCAdaptor;
import cn.unis.dao.TempUrlDao;
import cn.unis.entity.AlipayRecord;
import cn.unis.entity.TempUrl;
import cn.unis.service.impl.CommodityOrderService;
import cn.unis.service.interfaces.IAlipayRecordService;
import cn.unis.utils.PictureUploader;
/**
 *
 * @author fanzz
 *
 */
@Service("quartzJob")
public class TimerJobs {

	private static final Logger logger = LoggerFactory.getLogger(TimerJobs.class);

	@Autowired
	private IAlipayRecordService iAlipayRecordService;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private CommodityOrderService commodityOrderService;
	@Autowired
	private RequestLogServiceJDBCAdaptor requestLogServiceJDBCAdaptor;
	@Autowired
	private TempUrlDao tempUrlDao;

	/**
	 * 为支付成功 已没有成功的记录 操作。
	 */
	public void Recharge(){
		List<AlipayRecord> alipayRecordList = iAlipayRecordService.findByStatus(IAlipayRecordService.ALIPAY_SUCCESS);
		if(alipayRecordList.size() > 0) {
			String idstr = "";
			for(int i=0;i<alipayRecordList.size();i++){
				idstr = i + ":" + alipayRecordList.get(i).getId() + "  ";
			}
			logger.info("Recharge record idstr ==============================>" + idstr);
		}
		for(AlipayRecord alipayRecord :alipayRecordList ){// Recharge request to walo again!
			if(alipayRecord.getWalletType() == 0){
				iAlipayRecordService.requestSycoinToWalo(alipayRecord.getOutTradeNo());
			}else if(alipayRecord.getWalletType() == 1){
				iAlipayRecordService.requestTokenToWalo(alipayRecord.getOutTradeNo());
			}

		}
	}

	/**
	 * 自动确认 已到期 的 礼品 订单（状态是已发货下 deadLineDate 少于当前时间的订单）
	 */
	public void autoCompleteCommodityOrder(){
		logger.info("======================>autoCompleteCommodityOrder<======================");
		List<Long> ids = commodityOrderService.list70CommodityOrderIdAfterDeadLine();
		for(Long id : ids){
			commodityOrderService.autoCompleteOneCommodityOrderById(id);
		}
		logger.info("size==============================>autoCompleteCommodityOrder：finished");
	}

	@Transactional(readOnly=false)
	public void clearTmpPicture(){
		String path = System.getProperty("user.dir")  + File.separator + "src" + File.separator + "main" + File.separator + "webapp";
		PageRequest pageable = new PageRequest(0,200, Sort.Direction.DESC, "id");
		List<TempUrl> tempUrls = tempUrlDao.findAll(pageable).getContent();
		if(tempUrls!=null){
			logger.info("size================>" + tempUrls.size());
		}
		for(int i=0;i<tempUrls.size();i++){
			String deletePath = path  + tempUrls.get(i).getTempUrl();
			boolean flag = PictureUploader.delete(deletePath);
			if(flag){
				tempUrlDao.delete(tempUrls.get(i).getId());
			}
		}
	}
}
