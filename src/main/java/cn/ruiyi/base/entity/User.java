package cn.ruiyi.base.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springside.modules.utils.Collections3;

import cn.ruiyi.base.constants.Constants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

/**
 * 用户实体，收集教师、学生的共同属性（本想以基类实现派生，有限于JPA的单个supperclass）
 * 
 * @author chen
 */
@Entity
@Table(name = "base_user", schema = Constants.BASE)
// 默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User implements Serializable  {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7126817694855814889L;
	private Long id;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 登录名(不允许空值)
	 */
	private String loginName;
	/**
	 * 密码(不允许空值)
	 */
	private String password;
	/**
	 * 初始密码(不允许空值)
	 */
	private String initPassword;
	/**
	 * 真实姓名（或昵称，依系统而定）
	 */
	private String username;
	/**
	 * 用户状态（0：启用（默认）；1：禁用）
	 */
	private int status;
	/**
	 * 用户类型    1后台用户, 2游乐场用户, 3游乐场世宇管理员
	 */
	private int usertype;
	/**
	 * 身份证号（保留字段）
	 */
	private String IDCard;
	/**
	 * 具有权限访问的子系统的Id字符串
	 */
	private String systemIds;
	/**
	 * 用户唯一标识，应用于子系统的关联字段（不采用ID的原因：加密外键字段，安全性更高）
	 */
	private String identifyCode;

	private List<Role> roleList = Lists.newArrayList(); // 有序的关联对象集合

	/** =============以下为基础库字段，备用============ **/
	/**
	 * 性别,0：女性；1：男性
	 */
	private int gender;
	private String mobile; // 手机号码
	private String email; // 用户电邮
	private Date birthday; // 出生日期
	private Date addTime; // 用户添加时间
	private Date updateTime; // 用户修改时间
	private Date lastLoginTime; // 用户最后登陆时间
	private String lastLoginIP; // 用户最后登陆IP地址
	private long loginTimes; // 登陆的总次数
	private int superManager; // 是否超级管理员，默认为0
	/**
	 * 临时使用变量
	 */
	private String bdayStr;
	
	@Transient
	public String getBdayStr() {
		return bdayStr;
	}

	public void setBdayStr(String bdayStr) {
		this.bdayStr = bdayStr;
	}

	@Column(nullable = false, length = 200)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(nullable = false, length = 200)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(nullable = false, length = 200)
	public String getInitPassword() {
		return initPassword;
	}

	public void setInitPassword(String initPassword) {
		this.initPassword = initPassword;
	}

	@Column(nullable = true, length = 50)
	public String getIDCard() {
		return IDCard;
	}

	public void setIDCard(String iDCard) {
		IDCard = iDCard;
	}

	@Column(nullable = false, length = 200)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(nullable = true, columnDefinition = "INTEGER default 0")
	public int getUsertype() {
		return usertype;
	}

	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}

	@Column(nullable = true, columnDefinition = "INTEGER default 0")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(nullable = true, length = 300)
	public String getSystemIds() {
		return systemIds;
	}

	public void setSystemIds(String systemIds) {
		this.systemIds = systemIds;
	}

	@Column(nullable = true, length = 50)
	public String getIdentifyCode() {
		return identifyCode;
	}

	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
	}

	// 多对多定义
	@ManyToMany
	@JoinTable(name = "base_user_role", schema = Constants.BASE, joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	// Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	// 集合按id排序
	@OrderBy("id ASC")
	// 缓存策略
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	@Transient
	@JsonIgnore
	public String getRoleNames() {
		return Collections3.extractToString(roleList, "name", ", ");
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/** =========备用字段原则：int、long、Date设置为可空，String按照文档指定长度================ */
	@Column(nullable = true, columnDefinition = "INTEGER DEFAULT 1")
	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIP() {
		return lastLoginIP;
	}

	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}

	@Column(nullable = true, columnDefinition = "INTEGER default 0")
	public long getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(long loginTimes) {
		this.loginTimes = loginTimes;
	}

	@Column(nullable = true, columnDefinition = "INTEGER default 0")
	public int getSuperManager() {
		return superManager;
	}

	public void setSuperManager(int superManager) {
		this.superManager = superManager;
	}

}