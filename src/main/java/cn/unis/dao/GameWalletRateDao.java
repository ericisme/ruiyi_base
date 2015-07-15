package cn.unis.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.ruiyi.base.simplemvc.dao.BaseManagerDao;
import cn.unis.entity.GameWalletRate;


/**
 * 
 * 游戏      兑换比值            实体，dao层
 * @author eric
 * @version 1.0, 2013-12-03
 */
public interface GameWalletRateDao extends BaseManagerDao<GameWalletRate> {	

	@Query("select g.rate from GameWalletRate g where g.consumerKey=?1 and g.gameCenterKey=?2 and g.history=0")
	public List<Integer> getRateByConsumerAndGameCenterKey(String consumer_key, String game_center_key);
	
	@Query("from GameWalletRate g where g.consumerKey=?1 and g.gameCenterKey=?2 and g.history=0")
	public List<GameWalletRate> getByConsumerAndGameCenterKey(String consumer_key, String game_center_key);
	
//	public List<UserGameCenter> findByUser(User user);
//	
//	@Modifying
//	@Query("delete from UserGameCenter where user = ?1")
//	public void deleteByUser(User user);
}
