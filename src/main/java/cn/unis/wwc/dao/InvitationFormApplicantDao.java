package cn.unis.wwc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.ruiyi.base.simplemvc.dao.BaseManagerDao;
import cn.unis.wwc.entity.InvitationFormApplicant;


/**
 *
 * 邀请 详细信息表单 与会者，dao层
 * @author eric
 * @version 1.0, 2014-04-01
 */
public interface InvitationFormApplicantDao extends BaseManagerDao<InvitationFormApplicant> {

	/**
	 * 根据InvitationFormId查找InvitationFormApplicant列表
	 */
	@Query("from InvitationFormApplicant ifa where ifa.invitationForm.id=?1 and ifa.invitationForm.invitationCode = ?2 ")
	public List<InvitationFormApplicant> getListByInvitationFormId(Long invitationFormId,String invitationCode);


	/**
	 * 根据identityCode查找InvitationFormApplicant
	 */
	@Query("from InvitationFormApplicant ifa where ifa.identityCode=?1 ")
	public InvitationFormApplicant findByIdentityCode(String identityCode);
}
