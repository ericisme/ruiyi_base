package cn.ruiyi.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.ruiyi.base.constants.Constants;


/**
 *	码表实体
 *	设置规则：各实体中码值统一采用String类型
 * @author eric
 * @version 1.0, 2012-11-9
 */
@Entity
@Table(name="base_mcode", schema=Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@SequenceGenerator(name = "YPT_MCODE", sequenceName = Constants.YPT_BASE+"."+"YPT_MCODE_SEQ", allocationSize = 1)
public class Mcode implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2188697268397487314L;
	private Long id;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="YPT_MCODE")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="mcode_id")
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
	
	/**
	 * 码类型
	 */
	private String mtype;
	/**
	 * 码名
	 */
	private String mkey;
	/**
	 * 码值
	 */
	private String mvalue;
	/**
	 * 码备注
	 */
	private String remark;
	/**
	 * 码排序
	 */
	private long orderNum;
	/**=============以下为基础库字段，备用============**/
	/**
	 * 未标注，估测为：创建者Id
	 */
	private long creator;
	/**
	 * 未标注，估测为：创建日期
	 */
	private Date creatdate;
	/**
	 * 未标注，估测为：日期级别
	 */
	private int datelevel;
	
	@Column(nullable = false, length=300)
	public String getMtype()
	{
		return mtype;
	}
	public void setMtype(String mtype)
	{
		this.mtype = mtype;
	}
	
	@Column(nullable = false, length=100)
	public String getMkey()
	{
		return mkey;
	}
	public void setMkey(String mkey)
	{
		this.mkey = mkey;
	}
	
	@Column(nullable = false, length=50)
	public String getMvalue()
	{
		return mvalue;
	}
	public void setMvalue(String mvalue)
	{
		this.mvalue = mvalue;
	}
	
	@Column(nullable = true, length=300)
	public String getRemark()
	{
		return remark;
	}
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	
	@Column(nullable = true)
	public long getOrderNum()
	{
		return orderNum;
	}
	public void setOrderNum(long orderNum)
	{
		this.orderNum = orderNum;
	}
	
	//@Column(nullable = true)
	@Transient
	public long getCreator()
	{
		return creator;
	}
	public void setCreator(long creator)
	{
		this.creator = creator;
	}
	
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatdate()
	{
		return creatdate;
	}
	public void setCreatdate(Date creatdate)
	{
		this.creatdate = creatdate;
	}
	
	@Column(nullable=true,columnDefinition="INTEGER default 0")
	public int getDatelevel()
	{
		return datelevel;
	}
	public void setDatelevel(int datelevel)
	{
		this.datelevel = datelevel;
	}
	
	
}

