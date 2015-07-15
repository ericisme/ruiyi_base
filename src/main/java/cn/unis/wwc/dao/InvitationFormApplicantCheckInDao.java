package cn.unis.wwc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.ruiyi.base.simplemvc.dao.BaseManagerDao;
import cn.unis.wwc.entity.InvitationFormApplicant;
import cn.unis.wwc.entity.InvitationFormApplicantCheckIn;


/**
 *
 * 邀请 详细信息表单 与会者 签到记录，dao层
 * @author eric
 * @version 1.0, 2014-04-01
 */
public interface InvitationFormApplicantCheckInDao extends BaseManagerDao<InvitationFormApplicantCheckIn> {

//	/**
//	 * 根据InvitationFormId查找InvitationFormApplicant列表
//	 */
//	@Query("from InvitationFormApplicant ifa where ifa.invitationForm.id=?1 and ifa.invitationForm.invitationCode = ?2 ")
//	public List<InvitationFormApplicant> getListByInvitationFormId(Long invitationFormId,String invitationCode);

	/**
	 * 根据InvitationFormApplicantId查找InvitationFormApplicantCheckIn列表
	 */
	@Query("from InvitationFormApplicantCheckIn ifaci where ifaci.invitationFormApplicant.id=?1 order by ifaci.checkInDateTime desc")
	public List<InvitationFormApplicantCheckIn> getListByInvitationFormApplicantId(Long invitationFormApplicantId);

}
