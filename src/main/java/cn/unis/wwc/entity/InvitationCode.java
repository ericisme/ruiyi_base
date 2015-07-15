package cn.unis.wwc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.ruiyi.base.constants.Constants;
import cn.unis.entity.IdEntity;

/**
 * 邀请码管理  实体
 * @author eric
 */
@Entity
@Table(name = "unis_invitation_code",schema=Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)// 默认的缓存策略.
public class InvitationCode extends IdEntity implements Serializable  {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7417192127966288015L;


	/**
	 * 类型
	 * 1=管理员内部邀请
	 * 2=外部自助申请
	 */
	private Integer type = 1;
	@Column(nullable = true, length = 1)
	public Integer getType() {return type;}
	@Transient
	public String getTypeChinese(){if(type==null)return "";if(type==1)return "内部邀请";if(type==2)return "自助申请";return"";};
	public void setType(Integer type) {this.type = type;}
	
	/**
	 * 审核状态(type=2时有用)
	 *  null = 未审核;
	 *  0 = 审核不通过;
	 *  1 = 审核通过;
	 */
	private Integer reviewStatus;
	public Integer getReviewStatus() {return reviewStatus;}
	public void setReviewStatus(Integer reviewStatus) {this.reviewStatus = reviewStatus;}
	

	/**
	 * 名字
	 */
	private String name;
	@Column(nullable=false,length=128)
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}

	/**
	 * 职位
	 */
	private String position;
	@Column(nullable=true,length=128)
	public String getPosition() {return position;}
	public void setPosition(String position) {this.position = position;}
	
	

	/**
	 * 公司
	 */
	private String company;
	@Column(nullable=true,length=300)
	public String getCompany() {return company;}
	public void setCompany(String company) {this.company = company;}


	/**
	 * 电话
	 */
	private String tel;
	@Column(nullable=true,length=128)
	public String getTel() {return tel;}
	public void setTel(String tel) {this.tel = tel;}


	/**
	 * 电邮
	 */
	private String email;
	@Column(nullable=true,length=256)
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}

	/**
	 * 地区码
	 */
	private String districtCode;
	@Column(nullable = true, length = 18)
	public String getDistrictCode() {return districtCode;}
	public void setDistrictCode(String districtCode) {this.districtCode = districtCode;}
	
	/**
	 * 备注
	 */
	private String remark;
	@Column(nullable=true,length=1024)
	public String getRemark() {	return remark;}
	public void setRemark(String remark) {this.remark = remark;}


	/**
	 * 邀请码
	 */
	private String code;
	@Column(nullable=true,length=6, unique=true)
	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}



}
