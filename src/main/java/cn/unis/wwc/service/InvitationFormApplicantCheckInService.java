package cn.unis.wwc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.ruiyi.base.simplemvc.dao.BaseManagerDao;
import cn.ruiyi.base.simplemvc.service.BaseManagerService;
import cn.unis.wwc.dao.InvitationFormApplicantCheckInDao;
import cn.unis.wwc.entity.InvitationFormApplicantCheckIn;




/**
 * 邀请 详细信息表单 与会者 签到记录.service层
 *
 * @author eric
 */
@Component
@Transactional(readOnly = false)
public class InvitationFormApplicantCheckInService extends BaseManagerService<InvitationFormApplicantCheckIn>{

	@Autowired
	private InvitationFormApplicantCheckInDao invitationFormApplicantCheckInDao;
	@Override
	protected BaseManagerDao<InvitationFormApplicantCheckIn> getDomainDao() {
		return invitationFormApplicantCheckInDao;
	}

	/**
	 * 根据InvitationFormId查找InvitationFormApplicant列表
	 */
	public List<InvitationFormApplicantCheckIn> getListByInvitationFormId(Long invitationFormApplicantId){
		return invitationFormApplicantCheckInDao.getListByInvitationFormApplicantId(invitationFormApplicantId);
	}

}
