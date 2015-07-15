package cn.unis.wwc.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.util.DateUtil;
import cn.unis.entity.IdEntity;
import cn.unis.wwc.controller.WWCIndexController;

import com.google.common.collect.Lists;

/**
 * 邀请 详细信息表单 实体
 *
 * @author eric
 */
@Entity
@Table(name = "unis_invitation_form", schema = Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
// 默认的缓存策略.
public class InvitationForm extends IdEntity implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -483504752455409368L;
	
	/**
	 * 国家/地区码 2位英文
	 * 使用码表类型 COUNTRY_CHINESE/COUNTRY_ENGLISH
	 */
	private String districtCode;
	@Column(nullable = true, length = 18)
	public String getDistrictCode() {return districtCode;}
	public void setDistrictCode(String districtCode) {this.districtCode = districtCode;}

	/**
	 * 国家/地区码 中文意思， 不保存数据库
	 */
	private String districtCodeChinese;	
	@Transient
	public String getDistrictCodeChinese() {
		return districtCodeChinese;
	}
	public void setDistrictCodeChinese(String districtCodeChinese) {
		this.districtCodeChinese = districtCodeChinese;
	}

	/**
	 * 邀请码
	 */
	private InvitationCode invitationCode;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invitation_code_id", nullable = false, unique=true)
	public InvitationCode getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(InvitationCode invitationCode) {
		this.invitationCode = invitationCode;
	}

	/**
	 * 是否已确认，后台操作，即确认后用户不能修改 0=未确认 1=已确认
	 */
	private Integer isSure = 0;

	@Column(nullable = false, length = 1)
	public Integer getIsSure() {
		return isSure;
	}
	@Transient
	public String getIsSureChinese(){
		if(isSure == 1)
			return "已确认";
		if(isSure == 0)
			return "未确认";
		return "";
	}
	public void setIsSure(Integer isSure) {
		this.isSure = isSure;
	}

	/**
	 * 表单版本 11=英文 21=中文
	 */
	private Integer formType;

	@Column(nullable = false, length = 2)
	public Integer getFormType() {
		return formType;
	}
	@Transient
	public String getFormTypeChinese(){
		if(formType==11)
			return "英文";
		if(formType==21)
			return "中文";
		return "";
	}

	public void setFormType(Integer formType) {
		this.formType = formType;
	}

	/**
	 * 单位名称
	 */
	private String companyName;

	@Column(nullable = false, length = 256)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * 抵达时间
	 */
	private Date arriveDate;

	@Column(nullable = true)
	public Date getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}

	private String arriveDateStr ;

	@Transient
	public String getArriveDateStr() {
		arriveDateStr = DateUtil.getDate(arriveDate, WWCIndexController._DATE_FORMAT);
		return arriveDateStr;
	}

	public void setArriveDateStr(String arriveDateStr) {
		this.arriveDateStr = arriveDateStr;
	}

	/**
	 * 是否需要接机 0 为 否 1 为 是
	 */
	private Integer pickUpOrNot;

	@Column(nullable = false, length = 1)
	public Integer getPickUpOrNot() {
		return pickUpOrNot;
	}

	public void setPickUpOrNot(Integer pickUpOrNot) {
		this.pickUpOrNot = pickUpOrNot;
	}

	private String flightNo;
	@Column(nullable = false, length = 64)
	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	/**
	 * 入住酒店 可选项： null=不入住 11=喜来登酒店 (英文版本用) 21=金钻酒店 (中文版本用) 32=金沙酒店 (中文版本用)
	 */
	private Integer hotel;

	@Column(nullable = true, length = 2)
	public Integer getHotel() {
		return hotel;
	}
	@Transient
	public String getHotelChinese(){
		if(hotel == null)
			return "不入住";
		if(hotel == -1)
			return "不入住";
		if(hotel == 11)
			return "喜来登酒店";
		if(hotel == 21)
			return "金钻酒店";
		if(hotel == 32)
			return "金沙酒店";
		return "";
	}
	public void setHotel(Integer hotel) {
		this.hotel = hotel;
	}

	/**
	 * 用餐选择 1 = 中餐 2 = 西餐
	 */
	private Integer dinnerType;

	@Column(nullable = true, length = 1)
	public Integer getDinnerType() {
		return dinnerType;
	}
	@Transient
	public String getDinnerTypeChinese(){
		if(dinnerType == null)
			return "";
		if(dinnerType == 1)
			return "中餐";
		if(dinnerType == 2)
			return "西餐";
		return "";
	}

	public void setDinnerType(Integer dinnerType) {
		this.dinnerType = dinnerType;
	}

	/**
	 * 餐饮特殊要求(忌食) null=无
	 */
	private String specialDiet;

	@Column(nullable = true, length = 256)
	public String getSpecialDiet() {
		return specialDiet;
	}

	public void setSpecialDiet(String specialDiet) {
		this.specialDiet = specialDiet;
	}

	/**
	 * 活动，岐江夜游（10月20日晚） 0=不参加 1=参加
	 */
	private Integer nightTourOfQiRiver;

	@Column(nullable = true, length = 1)
	public Integer getNightTourOfQiRiver() {
		if(nightTourOfQiRiver==null)
			return 0;
		return nightTourOfQiRiver;
	}

	public void setNightTourOfQiRiver(Integer nightTourOfQiRiver) {
		this.nightTourOfQiRiver = nightTourOfQiRiver;
	}

	/**
	 * 活动,故居参观（10月20~22下午） 0=不参加 1=参加
	 */
	private Integer visitFormerResidenceOfSunYatSen;

	@Column(nullable = true, length = 1)
	public Integer getVisitFormerResidenceOfSunYatSen() {
		if(visitFormerResidenceOfSunYatSen==null)
			return 0;
		return visitFormerResidenceOfSunYatSen;
	}

	public void setVisitFormerResidenceOfSunYatSen(
			Integer visitFormerResidenceOfSunYatSen) {
		this.visitFormerResidenceOfSunYatSen = visitFormerResidenceOfSunYatSen;
	}

	/**
	 * 旅游购物 0=不参加 1=参加
	 */
	private Integer shopping;

	@Column(nullable = true, length = 1)
	public Integer getShopping() {
		if(shopping==null)
			return 0;
		return shopping;
	}

	public void setShopping(Integer shopping) {
		this.shopping = shopping;
	}

	/**
	 * 娱乐活动（10月20、21日晚） 0=不参加 1=参加
	 */
	private Integer entertainment;

	@Column(nullable = true, length = 1)
	public Integer getEntertainment() {
		if(entertainment==null)
			return 0;
		return entertainment;
	}

	public void setEntertainment(Integer entertainment) {
		this.entertainment = entertainment;
	}

	/**
	 * 其它需要或建议
	 */
	private String message;

	@Column(nullable = true, length = 512)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private List<InvitationFormApplicant> invitationFormApplicantList = Lists.newArrayList();

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy="invitationForm",orphanRemoval=true)
	public List<InvitationFormApplicant> getInvitationFormApplicantList() {
		return invitationFormApplicantList;
	}

	@SuppressWarnings("unused")
	private void setInvitationFormApplicantList(
			List<InvitationFormApplicant> invitationFormApplicantList) {
		this.invitationFormApplicantList = invitationFormApplicantList;
	}
	public void addInvitationFormApplicantList(
			List<InvitationFormApplicant> invitationFormApplicantList){
		this.invitationFormApplicantList.clear();
		this.invitationFormApplicantList.addAll(invitationFormApplicantList);
	}

}
