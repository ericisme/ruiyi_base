package cn.unis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.ruiyi.base.entity.User;
import cn.ruiyi.base.simplemvc.dao.BaseManagerDao;
import cn.ruiyi.base.simplemvc.service.BaseManagerService;
import cn.unis.dao.UserGameCenterDao;
import cn.unis.entity.UserGameCenter;




/**
 * 新闻管理类.service
 * 
 * @author eric
 */
@Component
@Transactional(readOnly = true)
public class UserGameCenterService extends BaseManagerService<UserGameCenter>{
	
	@Autowired
	private UserGameCenterDao userGameCenterDao;
	@Override
	protected BaseManagerDao<UserGameCenter> getDomainDao() {
		return userGameCenterDao;
	}

	/**
	 * 根据user_id，根gamecenterIds保存关联记录
	 */
	@Transactional(readOnly = false)
	public void saveByUserIdAndGameCenterIds(User user, String gamecenterIds){
		//System.out.println("gamecenterIds:"+gamecenterIds);
		userGameCenterDao.deleteByUser(user);
		userGameCenterDao.flush();
		if(gamecenterIds!=null){
			if(!"".equals(gamecenterIds)){
				String[] gamecenterIdsArr = gamecenterIds.split(",");
				for(String gamecenterId : gamecenterIdsArr){
					UserGameCenter ugc = new  UserGameCenter();
					ugc.setUser(user);
					ugc.setGameCenterKey(gamecenterId);
					userGameCenterDao.save(ugc);
				}				
			}
		}		
	}
	
	/**
	 * 根据用户查找关联记录
	 */
	public List<UserGameCenter> findByUser(User user){
		return userGameCenterDao.findByUser(user);
	}
	
	
	
}
