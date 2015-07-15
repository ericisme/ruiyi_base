package cn.unis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.ruiyi.base.simplemvc.dao.BaseManagerDao;
import cn.ruiyi.base.simplemvc.service.BaseManagerService;
import cn.unis.dao.GameWalletRateDao;
import cn.unis.entity.GameWalletRate;




/**
 * 游戏      兑换比值 .service
 * 
 * @author eric
 */
@Component
@Transactional(readOnly = true)
public class GameWalletRateService extends BaseManagerService<GameWalletRate>{
	
	@Autowired
	private GameWalletRateDao gameWalletRateDao;
	@Override
	protected BaseManagerDao<GameWalletRate> getDomainDao() {
		return gameWalletRateDao;
	}


	/**
	 * 根据 游戏key 根 游场key 获得 钱包 兑换比值。
	 * @param consumerKey
	 * @param gameCenterKey
	 * @return
	 * 兑换比值;
	 * 必须大于1的整数
	 * 一世宇币换多少游戏币;
	 * 如，如果1世宇币兑换60游戏币，则填写60;
	 * 如果不创建此记录，兑换比值默认为1.
	 */
	public Integer getRateByConsumerKeyAndGameCenterKey(String consumerKey, String gameCenterKey){
		List<Integer> rateList = gameWalletRateDao.getRateByConsumerAndGameCenterKey(consumerKey, gameCenterKey);
		if(rateList.size()>0){
			return rateList.get(0);
		}else{
			return 1;
		}
	}
	
	
	/**
	 * 根据 游戏key 根 游场key 获得 钱包 兑换比值。controller专用,没有值不会default为1
	 * @param consumerKey
	 * @param gameCenterKey
	 * @return
	 * 兑换比值;
	 * 必须大于1的整数
	 * 一世宇币换多少游戏币;
	 * 如，如果1世宇币兑换60游戏币，则填写60;如果10个世宇币兑换一个游戏币，则填写0.1; 
	 */
	public Integer getRateByConsumerKeyAndGameCenterKeyForController(String consumerKey, String gameCenterKey){
		List<Integer> rateList = gameWalletRateDao.getRateByConsumerAndGameCenterKey(consumerKey, gameCenterKey);
		if(rateList.size()>0){
			return rateList.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 根据consumer_key,game_center_key,获得记录
	 * @param consumer_key
	 * @param game_center_key
	 * @return
	 */
	public GameWalletRate getByConsumerAndGameCenterKey(String consumer_key, String game_center_key){
		List<GameWalletRate> gameWalletRateList = gameWalletRateDao.getByConsumerAndGameCenterKey(consumer_key, game_center_key);		
		if(gameWalletRateList.size()>0){
			return gameWalletRateList.get(0);
		}else{
			return null;
		}
	}
	
}
