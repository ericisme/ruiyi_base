package cn.unis.api4walo.dto;

import java.util.Date;

public class NewsDto {
	/**
	 * id
	 */
	private Long id;
	
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
	private String addDate;

	/**
	 * 标签, 用于区分文章类别 
	 */
	private String catId;
	
	/**
	 * 标签中文，只用于显示，不保存到数据库
	 * @return
	 */
	private String catName;
	
	/**
	 * 去掉html的内容，用于新闻预览
	 * 只用于显示，不保存到数据库
	 */
	private String contentShortCut;

	
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleImg() {
		return titleImg;
	}

	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}



	public String getAddDate() {
		return addDate;
	}

	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getContentShortCut() {
		return contentShortCut;
	}

	public void setContentShortCut(String contentShortCut) {
		this.contentShortCut = contentShortCut;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

	
	
	
	
	
	
	
	
}
