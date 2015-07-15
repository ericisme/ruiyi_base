package cn.unis.wwc.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import com.google.common.collect.Lists;

import cn.ruiyi.base.constants.Constants;
import cn.unis.entity.IdEntity;

/**
 * 邀请 详细信息表单 与会者 实体
 *
 * @author eric
 */
@Entity
@Table(name = "unis_invitation_form_applicant", schema = Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
// 默认的缓存策略.
public class InvitationFormApplicant extends IdEntity implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8570956535303577682L;

	/**
	 * 身份码，即身份二维码,用UUID即可
	 */
	private String identityCode;

	@Column(nullable = false)
	public String getIdentityCode() {
		return identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	/**
	 * 所属 InvitationForm(邀请 详细信息表单)
	 */
	private InvitationForm invitationForm;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invitation_form_id", nullable = false)
	public InvitationForm getInvitationForm() {
		return invitationForm;
	}

	public void setInvitationForm(InvitationForm invitationForm) {
		this.invitationForm = invitationForm;
	}

	/**
	 * 是否为 日常事务联络人 (即表单上的第一个联系人默认为 日常事务联络人), 一个表单只有一个日常事务联络人
	 *
	 * 1=是 0=否
	 */
	private Integer isRoutine;

	@Column(nullable = true, length = 1)
	public Integer getIsRoutine() {
		return isRoutine;
	}

	public void setIsRoutine(Integer isRoutine) {
		this.isRoutine = isRoutine;
	}

	/**
	 * 姓名(必填)
	 */
	private String name;

	@Column(nullable = true, length = 256)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 英文姓
	 */
	private String firstName;	
	@Column(nullable = true, length = 256)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * 英文名
	 */
	private String lastName;	
	@Column(nullable = true, length = 256)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	/**
	 * 性别(非必填) 0=男 1=女
	 */
	private Integer gender;

	@Column(nullable = true, length = 1)
	public Integer getGender() {
		return gender;
	}
	@Transient
	public String getGenderChinese(){
		if(gender == null)
			return "";
		if(gender == 0)
			return "男";
		if(gender ==1)
			return "女";
		return "";
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	/**
	 * 职位(非必填)
	 */
	private String position;

	@Column(nullable = true, length = 256)
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * 手机号(必填)
	 */
	private String mobileNumber;

	@Column(nullable = false, length = 64)
	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * 电邮(必填)
	 */
	private String email;

	@Column(nullable = false, length = 128)
	@Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	/**
	 * 签到历史记录List
	 */
	private List<InvitationFormApplicantCheckIn> invitationFormApplicantCheckInList = Lists.newArrayList();	
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy="invitationFormApplicant",orphanRemoval=true)
	public List<InvitationFormApplicantCheckIn> getInvitationFormApplicantCheckInList() {
		return invitationFormApplicantCheckInList;
	}
	@SuppressWarnings("unused")
	private void setInvitationFormApplicantCheckInList(
			List<InvitationFormApplicantCheckIn> invitationFormApplicantCheckInList) {
		this.invitationFormApplicantCheckInList = invitationFormApplicantCheckInList;
	}
	public void addInvitationFormApplicantCheckInList(List<InvitationFormApplicantCheckIn> invitationFormApplicantCheckInList){
		this.invitationFormApplicantCheckInList.clear();
		this.invitationFormApplicantCheckInList.addAll(invitationFormApplicantCheckInList);
	}
	
	

//	@OneToMany(cascade = { CascadeType.ALL }, mappedBy="invitationForm",orphanRemoval=true)
//	public List<InvitationFormApplicant> getInvitationFormApplicantList() {
//		return invitationFormApplicantList;
//	}
//
//	@SuppressWarnings("unused")
//	private void setInvitationFormApplicantList(
//			List<InvitationFormApplicant> invitationFormApplicantList) {
//		this.invitationFormApplicantList = invitationFormApplicantList;
//	}
//	public void addInvitationFormApplicantList(
//			List<InvitationFormApplicant> invitationFormApplicantList){
//		this.invitationFormApplicantList.clear();
//		this.invitationFormApplicantList.addAll(invitationFormApplicantList);
//	}
	
}
