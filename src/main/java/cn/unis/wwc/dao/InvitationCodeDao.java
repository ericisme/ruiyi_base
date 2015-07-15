package cn.unis.wwc.dao;

import org.springframework.data.jpa.repository.Query;

import cn.ruiyi.base.simplemvc.dao.BaseManagerDao;
import cn.unis.wwc.entity.InvitationCode;


/**
 * 
 * 邀请码管理，dao层
 * @author eric
 * @version 1.0, 2014-04-01
 */
public interface InvitationCodeDao extends BaseManagerDao<InvitationCode> {	
	
	/**
	 * 根据code返回记录
	 */
	@Query("from InvitationCode ic where ic.code=upper(?1) ")
	public InvitationCode getByCode(String code);
	
//	/**
//	 * 获得所有 可用的物流商 list
//	 * @return
//	 */
//	@Query("from CommodityLogistics cl where cl.status=1 order by cl.sortNumber desc ")
//	public List<CommodityLogistics> getAllEnableCommodityLogistic();
	
	
}
