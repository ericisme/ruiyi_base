package cn.unis.wwc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.entity.User;
import cn.unis.entity.IdEntity;

/**
 * 邀请 详细信息表单 与会者 签到记录表
 *
 * @author eric
 */
@Entity
@Table(name = "unis_invitation_form_applicant_check_in", schema = Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
// 默认的缓存策略.
public class InvitationFormApplicantCheckIn extends IdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4862575879795870296L;

	
	/**
	 * 对应 详细信息表单 与会者
	 */
	private InvitationFormApplicant invitationFormApplicant;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invitation_form_applicant_id", nullable = false)
	public InvitationFormApplicant getInvitationFormApplicant() {return invitationFormApplicant;}
	public void setInvitationFormApplicant(InvitationFormApplicant invitationFormApplicant) {this.invitationFormApplicant = invitationFormApplicant;}
	
	/**
	 * 签到时间
	 */
	private Date checkInDateTime;
	public Date getCheckInDateTime() {return checkInDateTime;}
	public void setCheckInDateTime(Date checkInDateTime) {this.checkInDateTime = checkInDateTime;}
	
	/**
	 * 协助签名后台用户
	 */
	private User handleUser;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "handle_user_id", nullable = false)
	public User getHandleUser() {return handleUser;}
	public void setHandleUser(User handleUser) {this.handleUser = handleUser;}
	


}
