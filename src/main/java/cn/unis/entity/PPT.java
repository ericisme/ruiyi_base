package cn.unis.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.ruiyi.base.constants.Constants;

@Entity
@Table(name = "backend_ppt",schema=Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PPT extends IdEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6607069105794385088L;
	/**
	 * 名字
	 */
	private String pictrueName;
	/**
	 * 排序号
	 */
	private int sortNumber;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 状态
	 */
	private int status;
	/**
	 * 最后更新时间
	 */
	private Date uploadDate;
	/**
	 * 地址
	 */
	private String url;
	/**
	 * 预览地址
	 */
	private String previewUrl;
	/**
	 * 文章路径
	 */
	private String articleUrl;
	
	
	public String getPreviewUrl() {
		return previewUrl;
	}
	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}
	public String getPictrueName() {
		return pictrueName;
	}
	public void setPictrueName(String pictrueName) {
		this.pictrueName = pictrueName;
	}
	public int getSortNumber() {
		return sortNumber;
	}
	public void setSortNumber(int sortNumber) {
		this.sortNumber = sortNumber;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getArticleUrl() {
		return articleUrl;
	}
	public void setArticleUrl(String articleUrl) {
		this.articleUrl = articleUrl;
	}
	
}
