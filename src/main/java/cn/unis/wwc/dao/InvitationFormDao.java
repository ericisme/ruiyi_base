package cn.unis.wwc.dao;

import org.springframework.data.jpa.repository.Query;

import cn.ruiyi.base.simplemvc.dao.BaseManagerDao;
import cn.unis.wwc.entity.InvitationForm;


/**
 *
 * 邀请 详细信息表单，dao层
 * @author eric
 * @version 1.0, 2014-04-01
 */
public interface InvitationFormDao extends BaseManagerDao<InvitationForm> {

	@Query("from InvitationForm where invitationCode.code = ?1")
	InvitationForm findByinvitationCode(String invitationCode);

}
