package cn.unis.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.entity.User;



/**
 * 商品类别  实体 （多级类别共用）
 * @author eric
 */
@Entity
@Table(name = "unis_commodity_category",schema=Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)// 默认的缓存策略.
public class CommodityCategory extends IdEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3075459547367828222L;
	private int level;//商品类别的级别(一级类别：1，二级类别：2，依此类推)
	@Column(nullable = true, columnDefinition="INTEGER default 1")
	public int getLevel(){return level;}
	public void setLevel(int level){this.level = level;}
	
	
	private int status;// 商品类别的状态（0：停用。1：启用（默认值））
	@Column(nullable = true,columnDefinition="INTEGER default 1")
	public int getStatus(){return status;}
	public void setStatus(int status){this.status = status;}

	
	private int orderNum;//商品类别的排序（所在级别的排序）
	@Column(nullable = true)
	public int getOrderNum(){return orderNum;}
	public void setOrderNum(int orderNum){this.orderNum = orderNum;}
	
	
	private CommodityCategory commodityCategory;//父类别，	父商品类别
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="up_commodity_category_id")
	public CommodityCategory getCommodityCategory() {return commodityCategory;}
	public void setCommodityCategory(CommodityCategory commodityCategory) {this.commodityCategory = commodityCategory;}


	private String name;//商品类别名称
	@Column(nullable=false,length=100)
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}

	
	private String description;//商品类别描述
	@Column(nullable=true,length=100)
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}

	
	private User user; //录入人
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", nullable=false)
	public User getUser() {return user;}
	public void setUser(User user) {this.user = user;}


	private Date addTime; // 录入时间
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getAddTime(){return addTime;}
	public void setAddTime(Date addTime){this.addTime = addTime;}


	
	/**
	 * 临时存储容器，存储当前部门的下一级别子商品类别
	 */
	private List<CommodityCategory> subCommodityCategoryList = new ArrayList<CommodityCategory>();
	@Transient
	public List<CommodityCategory> getSubCommodityCategoryList(){return subCommodityCategoryList;}
	public void setSubCommodityCategoryList(List<CommodityCategory> subCommodityCategoryList){this.subCommodityCategoryList = subCommodityCategoryList;}
	
	/**
	 * 是否存在下一级别(子商品类别) 存在:1    不存在:0;
	 */
	private int hasNext;	
	@Transient
	public int getHasNext(){return hasNext;}
	public void setHasNext(int hasNext){this.hasNext = hasNext;}
	

}