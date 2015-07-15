package cn.ruiyi.base.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.ruiyi.base.constants.Constants;


@Entity
@Table(name = "base_system",schema=Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@SequenceGenerator(name = "YPT_SUBSYSTEM", sequenceName = Constants.YPT_BASE+"."+"YPT_SUBSYSTEM_SEQ", allocationSize = 1)
public class SubSystem implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1276633177846516981L;
	private Long id;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="YPT_SUBSYSTEM")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="subsytem_id")
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
	/**
	 * 子系统名称
	 */
	private String subName;
	/**
	 * 子系统主页地址
	 */
	private String mainpage;
	/**
	 * 子系统状态（0：停用；1：启用（默认））
	 */
	private int status;
	
	/**
	 * 排序号
	 */
	private int orderNum;
	
	/**
	 * 子系统标识
	 */
	private String identifyCode;

	/**
	 * 可见图标名称（非地址） -- 改为正常图标
	 */
	private String visIcon;
	/**
	 * 不可见图标名称（非地址） -- 改为灰色图标
	 */
	private String unvisIcon;
	
	/**
	 * 高亮选中时图标
	 */
	private String highIcon;	
	
	/**
	 * 子系统创建者
	 */
	private User creater;
	
	@Column(nullable=false,length=200)
	public String getSubName()
	{
		return subName;
	}
	public void setSubName(String subName)
	{
		this.subName = subName;
	}

	@Column(nullable=false,length=500)
	public String getMainpage()
	{
		return mainpage;
	}
	public void setMainpage(String mainpage)
	{
		this.mainpage = mainpage;
	}
	
	public int getOrderNum()
	{
		return orderNum;
	}

	public void setOrderNum(int orderNum)
	{
		this.orderNum = orderNum;
	}

	@Column(nullable=true,columnDefinition="INTEGER default 1")
	public int getStatus()
	{
		return status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
	
	@Column(nullable=false,length=255)
	public String getIdentifyCode()
	{
		return identifyCode;
	}

	public void setIdentifyCode(String identifyCode)
	{
		this.identifyCode = identifyCode;
	}

	@Column(nullable=true,length=200)
	public String getVisIcon()
	{
		return visIcon;
	}
	public void setVisIcon(String visIcon)
	{
		this.visIcon = visIcon;
	}
	
	@Column(nullable=true,length=200)
	public String getUnvisIcon()
	{
		return unvisIcon;
	}
	public void setUnvisIcon(String unvisIcon)
	{
		this.unvisIcon = unvisIcon;
	}
	
	@Column(nullable=true,length=200)
	public String getHighIcon()
	{
		return highIcon;
	}

	public void setHighIcon(String highIcon)
	{
		this.highIcon = highIcon;
	}

	@Transient
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="createrId")
	public User getCreater()
	{
		return creater;
	}
	public void setCreater(User creater)
	{
		this.creater = creater;
	}
	

}
