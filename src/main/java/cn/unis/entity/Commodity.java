package cn.unis.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.entity.User;

import com.google.common.collect.Lists;

/**
 * 商品
 * 
 * @author fanzz
 * 
 */
@Entity
@Table(name = "unis_commodity", schema = Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Commodity extends IdEntity implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3501744139346837002L;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 价钱（积分）
	 */
	private int price;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 商品编号
	 */
	private String commodityNo;
	/**
	 * 所属商品类别
	 */
	private CommodityCategory commodityCategory;		
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="commodity_category_id", nullable=false)
	public CommodityCategory getCommodityCategory() {
		return commodityCategory;
	}
	public void setCommodityCategory(CommodityCategory commodityCategory) {
		this.commodityCategory = commodityCategory;
	}
	/**
	 * 状态 1.启用 0.停用
	 */
	private int status = 1;
	/**
	 * 排序号
	 */
	private Integer sortNumber;
	/**
	 * 库存
	 */
	private int stocks;
	
	/**
	 * 已兑换量
	 * 用定时器定时统计已下订单
	 */
	private int exchangeCount;
	
	/**
	 * 创建记录日期
	 */
	private Date createAt;
	/**
	 * 创建人
	 */
	private User createBy;
	/**
	 * 库存更新日期
	 */
	private Date stocksLastUpdateTime;
	/**
	 * //不定扩展属性 Ex 长度:173cm;高度:50cm;
	 */
	private String extendedAttribute;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 图片
	 */
	private List<Picture> pictureList = Lists.newArrayList();
	/**
	 * 图片编号
	 */
	private String pictureNumber = "";
	
	/**
	 * 商城首页图片
	 */
	private Picture indexPagePicture;
	/**
	 * 商城首页图片编号
	 */
	private Long indexPagePictureNumber;
	/**
	 * 商城首页图片地址
	 */
	private String indexPagePictureUrl;
	
	/**
	 * 属性map
	 */
	private LinkedHashMap<String, String> attributesMap = new LinkedHashMap<String, String>();
	/**
	 * 
	 * @return ex:11,12,13
	 */
	public String getPictureNumber() {
		return pictureNumber;
	}

	public void setPictureNumber(String pictureNumber) {
		this.pictureNumber = pictureNumber;
	}

	@Column(unique=true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}

	public int getStocks() {
		return stocks;
	}

	public void setStocks(int stocks) {
		this.stocks = stocks;
	}

	public Date getStocksLastUpdateTime() {
		return stocksLastUpdateTime;
	}

	public void setStocksLastUpdateTime(Date stocksLastUpdateTime) {
		this.stocksLastUpdateTime = stocksLastUpdateTime;
	}

	@Length(max = 65536)
	public String getExtendedAttribute() {
		return extendedAttribute;
	}

	public void setExtendedAttribute(String extendedAttribute) {
		this.extendedAttribute = extendedAttribute;
	}
	
	
	@Transient
	public LinkedHashMap<String, String> getAttributesMap() {
		if(StringUtils.isNotBlank(extendedAttribute)){
			String[] attrs = extendedAttribute.split(";|；");
			for(int i=0;i<attrs.length;i++){
				String att = attrs[i];
				if(StringUtils.isNotBlank(att)){
					String [] keyValue = att.split("：|:");
					if(keyValue.length == 2  && StringUtils.isNotBlank(keyValue[0]) && StringUtils.isNotBlank(keyValue[1]) ){
						attributesMap.put(keyValue[0],keyValue[1]);
					}
				}
			}
		}
		return attributesMap;
	}

	public void setAttributesMap(LinkedHashMap<String, String> attributesMap) {
		this.attributesMap = attributesMap;
	}

	@Length(max = 65536)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToMany
	@JoinColumn(name="commodity_id")
	public List<Picture> getPictureList() {
		return pictureList;
	}
	
	@Transient
	public List<Picture> getSortedPictureList(){
		List<Long> pictureNumberList = getPictureNumberList();
		List<Picture> sortedList = Lists.newArrayList();
		for(Long id : pictureNumberList){
			for(Picture picture : pictureList){
				if(id.compareTo(picture.getId()) == 0){
					sortedList.add(picture);
				}
			}
		}
		return sortedList;
	}

	public void setPictureList(List<Picture> pictureList) {
		this.pictureList = pictureList;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	@OneToOne
	@JoinColumn(name="user_id")
	public User getCreateBy() {
		return createBy;
	}

	
	public void setCreateBy(User createBy) {
		this.createBy = createBy;
	}
	
	
	@Transient
	public String getIndexPagePictureUrl() {
		return indexPagePictureUrl;
	}
	public void setIndexPagePictureUrl(String indexPagePictureUrl) {
		this.indexPagePictureUrl = indexPagePictureUrl;
	}
	@Transient
	public List<Long> getPictureNumberList() {
		List<Long> pictureNumberList = Lists.newArrayList();
		if(StringUtils.isNotBlank(getPictureNumber())){
			String[] pictureNumberArr = getPictureNumber().split(",");
			
			for (int i = 0; i < pictureNumberArr.length; i++) {
				long number = 0L;

				try {
					if (StringUtils.isNotBlank(pictureNumberArr[i])) {
						number = Long.parseLong(pictureNumberArr[i]);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					pictureNumberList.add(number);
				}
			}
		}
		return pictureNumberList;
	}
	
	@OneToOne
	public Picture getIndexPagePicture() {
		return indexPagePicture;
	}
	public void setIndexPagePicture(Picture indexPagePicture) {
		this.indexPagePicture = indexPagePicture;
	}
	
	public Long getIndexPagePictureNumber() {
		return indexPagePictureNumber;
	}
	public void setIndexPagePictureNumber(Long indexPagePictureNumber) {
		this.indexPagePictureNumber = indexPagePictureNumber;
	}
	
	@Column(unique=true)
	public String getCommodityNo() {
		return commodityNo;
	}
	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}
	public int getExchangeCount() {
		return exchangeCount;
	}
	public void setExchangeCount(int exchangeCount) {
		this.exchangeCount = exchangeCount;
	}
	
	
	
}
