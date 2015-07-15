package cn.unis.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.ruiyi.base.constants.Constants;

/**
 * 图片
 * 
 * @author fanzz
 * 
 */
@Entity
@Table(name = "unis_picture", schema = Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Picture extends IdEntity implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2103250634168798153L;
	/**
	 * 图片名称
	 */
	private String name;
	/**
	 * 状态 1.启用 0.停用
	 */
	private int status = 1;
	/**
	 * 图片所属模块
	 */
	private String type;
	/**
	 * 图片路径
	 */
	private String url;
	/**
	 * 预览地址
	 */
	private String previewUrl;
	/**
	 * 730 x auto px
	 */
    private String previewLargeUrl;	
    
	public String getPreviewUrl() {
		return previewUrl;
	}
	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPreviewLargeUrl() {
		return StringUtils.isBlank(previewLargeUrl) ? url : previewLargeUrl;
	}
	public void setPreviewLargeUrl(String previewLargeUrl) {
		this.previewLargeUrl = previewLargeUrl;
	}
	
}
