package cn.unis.wwc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.ruiyi.base.constants.Constants;
import cn.unis.entity.IdEntity;

/**
 * 大会新闻 实体
 * @author Administrator
 *
 */
@Entity
@Table(name = "unis_invitation_news",schema=Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InvitationNews extends IdEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5504389445408983028L;
	
	/**
	 * 表单版本 11=英文 21=中文
	 */
	private Integer formType;

	@Column(nullable = false, length = 2)
	public Integer getFormType() {
		return formType;
	}
	@Transient
	public String getFormTypeChinese(){
		if(formType==11)
			return "英文";
		if(formType==21)
			return "中文";
		return "";
	}

	public void setFormType(Integer formType) {
		this.formType = formType;
	} 
	
	
	/**
	 * 标题
	 */
	private String title;	
	/**
	 * 标题图片
	 */
	private String titleImg;	
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private Date addTime;
	
	/**
	 * 排序号
	 */
	private int sortNumber;
	/**
	 * 状态
	 */
	private int status;		
	/**
	 * 标签, 用于区分文章类别 
	 */
	private String tag;
	
	/**
	 * 标签中文，只用于显示，不保存到数据库
	 * @return
	 */
	private String tagChinese;
	
	/**
	 * 标签英文，只用于显示，不保存到数据库
	 * @return
	 */
	private String tagEnglish;
	
	/**
	 * 去掉html的内容，用于新闻预览
	 * 只用于显示，不保存到数据库
	 */
	private String contentWithoutHtml;
	
	
	@Transient
	public String getContentWithoutHtml() {
		return contentWithoutHtml;
	}
	public void setContentWithoutHtml(String contentWithoutHtml) {
		this.contentWithoutHtml = contentWithoutHtml;
	}
	@Transient
	public String getTagChinese() {
		return tagChinese;
	}
	public void setTagChinese(String tagChinese) {
		this.tagChinese = tagChinese;
	}		
	@Transient
	public String getTagEnglish() {
		return tagEnglish;
	}
	public void setTagEnglish(String tagEnglish) {
		this.tagEnglish = tagEnglish;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	@Column(length=10000)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
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
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getTitleImg() {
		return titleImg;
	}
	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}


	
	
}
