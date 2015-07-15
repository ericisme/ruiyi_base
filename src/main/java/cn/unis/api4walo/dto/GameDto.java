package cn.unis.api4walo.dto;

import java.util.List;

import com.google.common.collect.Lists;

public class GameDto {

	/**
	 * 游戏id
	 */
	private Long id;
	/**
	 * 游戏类别
	 */
	private String catId;
	/**
	 * 游戏类别名称
	 */
	private String catName;
	/**
	 * 游戏名称(中文)
	 */
	private String nameForCH;
	
	/**
	 * 游戏名称(英文)
	 */
	private String nameForEN;
	
	/**
	 * 人气(游戏页面查看次数)
	 */
	private Long popularity;
	/**
	 * 图标路径
	 */
	private String iconPath;
	/**
	 * 是否热门
	 */
	private int isHot;
	/**
	 * 是否新游戏
	 */
	private int isNew;
	/**
	 * 是否联动版
	 */
	private int isLinked;
	/**
	 * 发布日期,yyyy-mm-dd
	 */
	private String releaseDate;
	/**
	 * 游戏描述
	 */
	private String description;
	/**
	 * 游戏图片list
	 */
	private List<GamePictureDto> pictureList = Lists.newArrayList();
	/**
	 * 下载次数，合计次数
	 */
	private int downloadTimes;
	/**
	 * ios游戏大小,单位mb
	 */
	private Float ios_size;
	/**
	 *  android游戏大小,单位mb
	 */
	private Float android_size;
	/**
	 * 具体不同平台上的游戏list
	 */
	private List<GamePlatformAndMarketDto> platformAndMarkets = Lists.newArrayList();
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNameForCH() {
		return nameForCH;
	}
	public void setNameForCH(String nameForCH) {
		this.nameForCH = nameForCH;
	}
	public String getNameForEN() {
		return nameForEN;
	}
	public void setNameForEN(String nameForEN) {
		this.nameForEN = nameForEN;
	}
	public Long getPopularity() {
		return popularity;
	}
	public void setPopularity(Long popularity) {
		this.popularity = popularity;
	}
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	public int getIsHot() {
		return isHot;
	}
	public void setIsHot(int isHot) {
		this.isHot = isHot;
	}
	public int getIsNew() {
		return isNew;
	}
	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<GamePictureDto> getPictureList() {
		return pictureList;
	}
	public void setPictureList(List<GamePictureDto> pictureList) {
		this.pictureList = pictureList;
	}
	public int getDownloadTimes() {
		return downloadTimes;
	}
	public void setDownloadTimes(int downloadTimes) {
		this.downloadTimes = downloadTimes;
	}
	public Float getIos_size() {
		return ios_size;
	}
	public void setIos_size(Float ios_size) {
		this.ios_size = ios_size;
	}
	public Float getAndroid_size() {
		return android_size;
	}
	public void setAndroid_size(Float android_size) {
		this.android_size = android_size;
	}
	public List<GamePlatformAndMarketDto> getPlatformAndMarkets() {
		return platformAndMarkets;
	}
	public void setPlatformAndMarkets(
			List<GamePlatformAndMarketDto> platformAndMarkets) {
		this.platformAndMarkets = platformAndMarkets;
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
	public int getIsLinked() {
		return isLinked;
	}
	public void setIsLinked(int isLinked) {
		this.isLinked = isLinked;
	}

	

	
	
	
}
