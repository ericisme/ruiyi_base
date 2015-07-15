package cn.unis.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.ruiyi.base.constants.Constants;

/**
 * 商品物流商管理  实体
 * @author eric
 */
@Entity
@Table(name = "unis_commodity_logistics",schema=Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)// 默认的缓存策略.
public class CommodityLogistics extends IdEntity implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1717877452087357811L;
	private String name;//物流商 名称
	@Column(nullable=false,length=128)
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	private String path;//物流查询网址
	@Column(nullable=true,length=1024)
	public String getPath() {return path;}
	public void setPath(String path) {this.path = path;}
	
	private String tel;//联系电话
	@Column(nullable=true,length=128)
	public String getTel() {return tel;}
	public void setTel(String tel) {this.tel = tel;}
	
	private String remark;//备注
	@Column(nullable=true,length=1024)
	public String getRemark() {	return remark;}
	public void setRemark(String remark) {this.remark = remark;}
	
	private int status;//状态（0：停用。1：启用（默认值））
	@Column(nullable = true,columnDefinition="INTEGER default 1")
	public int getStatus(){return status;}
	public void setStatus(int status){this.status = status;}
	
	private int sortNumber;//排序号
	public int getSortNumber() {return sortNumber;}
	public void setSortNumber(int sortNumber) {this.sortNumber = sortNumber;}
	

}
