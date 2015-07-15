package cn.ruiyi.base.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体，收集教师、学生的共同属性（本想以基类实现派生，有限于JPA的单个supperclass）
 * @author chen
 */

public class RemoteUser implements Serializable {

	private static final long serialVersionUID = 4793022675884814607L;
	
	private Long id;
	

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
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
	 * 用户类型 1学校 2机构
	 */
	private int usertype;
	/**
	 * 身份证号（保留字段）
	 */
	private String IDCard;
/*	*//**
	 * 用户所在单位(学生、教师为学校所属单位，市级管理员为当市，省级管理员为当省等)
	 *//*
	private Unit unit;*/
/*	*//**
	 * 任职/就读学校
	 *//*
	private School school;*/
	/**
	 * 当用户为学校时，此字段为学校ID，当用户为机构是此字段为机构ID
	 */
	private long typeId;
	/**
	 * 具有权限访问的子系统的Id字符串
	 */
	private String systemIds;
	/**
	 * 用户唯一标识，应用于子系统的关联字段（不采用ID的原因：加密外键字段，安全性更高）
	 */
	private String identifyCode;
	
	
	
	
	/**=============以下为基础库字段，备用============**/
	/**
	 * 性别,0：女性；1：男性
	 */
	private int gender;
	private String mobile; // 手机号码
	private String email; // 用户电邮
	private Date birthday; // 出生日期
	private Date addTime; // 用户添加时间
	private Date updateTime; //用户修改时间
	private Date lastLoginTime; // 用户最后登陆时间
	private String lastLoginIP; // 用户最后登陆IP地址
	private long loginTimes; // 登陆的总次数
	private int  superManager; // 是否超级管理员，默认为0
	public static int XX = 1;
	public static int JG = 2;
	/**
	 * 临时使用变量
	 */
	private String bdayStr;
	
	public String getBdayStr()
	{
		return bdayStr;
	}

	public void setBdayStr(String bdayStr)
	{
		this.bdayStr = bdayStr;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getInitPassword()
	{
		return initPassword;
	}

	public void setInitPassword(String initPassword)
	{
		this.initPassword = initPassword;
	}

	public String getIDCard()
	{
		return IDCard;
	}

	public void setIDCard(String iDCard)
	{
		IDCard = iDCard;
	}
	
	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public int getUsertype()
	{
		return usertype;
	}

	public void setUsertype(int usertype)
	{
		this.usertype = usertype;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
/*		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="unitId",nullable=true)
	public Unit getUnit()
	{
		return unit;
	}

	public void setUnit(Unit unit)
	{
		this.unit = unit;
	}
		*/
	public long getTypeId()
	{
		return typeId;
	}

	public void setTypeId(long typeId)
	{
		this.typeId = typeId;
	}
	
	public String getSystemIds()
	{
		return systemIds;
	}

	public void setSystemIds(String systemIds)
	{
		this.systemIds = systemIds;
	}

	public String getIdentifyCode()
	{
		return identifyCode;
	}

	public void setIdentifyCode(String identifyCode)
	{
		this.identifyCode = identifyCode;
	}

	public int getGender()
	{
		return gender;
	}

	public void setGender(int gender)
	{
		this.gender = gender;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Date getBirthday()
	{
		return birthday;
	}

	public void setBirthday(Date birthday)
	{
		this.birthday = birthday;
	}

	public Date getAddTime()
	{
		return addTime;
	}

	public void setAddTime(Date addTime)
	{
		this.addTime = addTime;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}
	
	public Date getLastLoginTime()
	{
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime)
	{
		this.lastLoginTime = lastLoginTime;
	}
	
	public String getLastLoginIP()
	{
		return lastLoginIP;
	}

	public void setLastLoginIP(String lastLoginIP)
	{
		this.lastLoginIP = lastLoginIP;
	}

	public long getLoginTimes()
	{
		return loginTimes;
	}

	public void setLoginTimes(long loginTimes)
	{
		this.loginTimes = loginTimes;
	}

	public int getSuperManager()
	{
		return superManager;
	}

	public void setSuperManager(int superManager)
	{
		this.superManager = superManager;
	}
}