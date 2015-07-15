package cn.unis.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.ruiyi.base.constants.Constants;
import cn.unis.utils.StringUtils2;

import com.google.common.collect.Lists;
/**
 *
 * @author fanzz
 *
 */
@Entity
@Table(name = "unis_game",schema=Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Game extends IdEntity implements Serializable {
	private static final long serialVersionUID = -5004044789768263079L;
	/**
	 * 类型(益智，动作)
	 */
	private String type;
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
	private int popularity;
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
	 * 状态
	 */
	private int status;
	/**
	 * 排序号
	 */
	private Integer sortNumber;
	/**
	 * 发布日期
	 */
	private Date releaseDate;
	/**
	 * 下载次数
	 */
	private int downloadTimes;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * ios大小
	 */
	private Float ios_size;
	/**
	 * ios下载连接数
	 */
	private int ios_downloadNumber;
	/**
	 * android大小
	 */
	private Float android_size;
	/**
	 * android下载连接数
	 */
	private int android_downloadNumber;

	/**
	 * windows phone大小
	 */
	private Float wp_size;

	private List<PlatformAndMarket> platformAndMarkets = Lists.newArrayList();

	/**
	 * 游戏版本
	 */
	private String gameVersion;
	/**
	 * 资费
	 */
	private String needMoneyOrNot;
	/**
	 * 图片列表
	 */
	private String pictureNumber;
	/**
	 * 图片列表
	 */
	private List<Picture> pictureList = Lists.newArrayList();
	/**
	 * qrcode的地址信息（transisit）
	 */
	private String qrcodeUrl;
	/**
	 * 乐观锁测试
	 */
	private int version;




	public int getPopularity() {
		return popularity;
	}
	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}
	public void setIos_downloadNumber(int ios_downloadNumber) {
		this.ios_downloadNumber = ios_downloadNumber;
	}
	public void setAndroid_downloadNumber(int android_downloadNumber) {
		this.android_downloadNumber = android_downloadNumber;
	}
	public String getPictureNumber() {
		return pictureNumber;
	}
	public void setPictureNumber(String pictureNumber) {
		this.pictureNumber = pictureNumber;
	}
	public String getNeedMoneyOrNot() {
		return needMoneyOrNot;
	}
	public void setNeedMoneyOrNot(String needMoneyOrNot) {
		this.needMoneyOrNot = needMoneyOrNot;
	}
	public String getGameVersion() {
		return gameVersion;
	}
	public void setGameVersion(String gameVersion) {
		this.gameVersion = gameVersion;
	}
//	@Version
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name="game_fk_id")
	public List<PlatformAndMarket> getPlatformAndMarkets() {
		return platformAndMarkets;
	}
	@SuppressWarnings("unused")
	private void setPlatformAndMarkets(List<PlatformAndMarket> platformAndMarkets) {
		this.platformAndMarkets = platformAndMarkets;
	}
	public void addPlatformAndMarkets(List<PlatformAndMarket> platformAndMarkets){
		this.platformAndMarkets.clear();
		this.platformAndMarkets = platformAndMarkets;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	@Temporal(TemporalType.DATE)
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public int getDownloadTimes() {
		return downloadTimes;
	}
	public void setDownloadTimes(int downloadTimes) {
		this.downloadTimes = downloadTimes;
	}
	@Column(length=512)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public Float getWp_size() {
		return wp_size;
	}
	public void setWp_size(Float wp_size) {
		this.wp_size = wp_size;
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
	public int getIsLinked() {
		return isLinked;
	}
	public void setIsLinked(int isLinked) {
		this.isLinked = isLinked;
	}

	/**
	 * 获取二维码的内容（第一个IOS平台的下载链接）
	 * @return
	 */
	@Transient
	public String getQrcodeUrl() {
		for(PlatformAndMarket platformAndMarket : platformAndMarkets){
			if(platformAndMarket.getPlatform()==PlatformAndMarket.IOS){
				qrcodeUrl =  platformAndMarket.getDownloadUrl();
				break;
			}
		}
		return qrcodeUrl;
	}

	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}


	@Transient
	public int getIos_downloadNumber() {
		ios_downloadNumber = 0;
		for(int i=0;i<platformAndMarkets.size();i++){
			if(platformAndMarkets.get(i).getPlatform()==PlatformAndMarket.IOS){
				ios_downloadNumber ++;
			}
		}
		return ios_downloadNumber;
	}
	@Transient
	public int getAndroid_downloadNumber() {
		android_downloadNumber = 0;
		for(int i=0;i<platformAndMarkets.size();i++){
			if(platformAndMarkets.get(i).getPlatform()==PlatformAndMarket.ANDROID){
				android_downloadNumber ++;
			}
		}
		return android_downloadNumber;
	}

//	@ManyToMany(fetch=FetchType.EAGER)
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="game_id")
	public List<Picture> getPictureList() {
		return pictureList;
	}
	public void setPictureList(List<Picture> pictureList) {
		this.pictureList = pictureList;
	}
	/**
	 * 获取已排序的图片List（根据pictueNumber）
	 * @param sorted ture 倒序 false 正序
	 * @return
	 */

	private List<Picture> sortedPictureList;
	@Transient
	public List<Picture> getSortedPictureList(){
		List<Long> pictureNumberList = getPictureNumberList();
		sortedPictureList = Lists.newArrayList();
			for(int i=pictureNumberList.size()-1;i>=0;i--){
				for(Picture picture : pictureList){
					if(pictureNumberList.get(i).compareTo(picture.getId()) == 0){
						sortedPictureList.add(picture);
					}
				}
			}
		return sortedPictureList;
	}
	/**
	 * 获取所有图片编号的
	 * @return
	 */
	@Transient
	public List<Long> getPictureNumberList() {
		List<Long> pictureNumberList = StringUtils2.getTFromString(this.getPictureNumber(),",|，" , Long.class, false);
		return pictureNumberList;
	}

	/**
	 * 获取ios平台的下载链接数
	 * @return
	 */
	@Transient
	public int getIOS_Url_Download_Numbers(){
		return getUrl_Download_Numbers_By_Platform(PlatformAndMarket.IOS);
	}
	/**
	 * 获取android平台的下载链接数
	 * @return
	 */
	@Transient
	public int getAndroid_Url_Download_Numbers(){
		return getUrl_Download_Numbers_By_Platform(PlatformAndMarket.ANDROID);
	}
	/**
	 * 获取windows phone平台的下载链接数
	 * @return
	 */
	@Transient
	public int getWP_Url_Download_Numbers(){
		return getUrl_Download_Numbers_By_Platform(PlatformAndMarket.WP);
	}
	@Transient
	private int getUrl_Download_Numbers_By_Platform(byte platform){
		int url_download_numbers = 0;
		int liseSize = this.getPlatformAndMarkets().size();
		for(int i=0;i<liseSize;i++){
			if(this.getPlatformAndMarkets().get(i).getPlatform() == platform){
				url_download_numbers++;
			}
		}
		return url_download_numbers;
	}

	/**
	 * 获取ios平台下载信息
	 * @return
	 */
	@Transient
	public List<PlatformAndMarket> getIOS_PlatformAndMarket(){
		return getPlatformAndMarketByPlatform(PlatformAndMarket.IOS);
	}
	/**
	 * 获取android平台下载信息
	 * @return
	 */
	@Transient
	public List<PlatformAndMarket> getAndroid_PlatformAndMarket(){

		return getPlatformAndMarketByPlatform(PlatformAndMarket.ANDROID);
	}
	/**
	 * 获取wp平台下载信息
	 * @return
	 */
	@Transient
	public List<PlatformAndMarket> getWP_PlatformAndMarket(){
		return getPlatformAndMarketByPlatform(PlatformAndMarket.WP);
	}
	@Transient
	private List<PlatformAndMarket> getPlatformAndMarketByPlatform(byte platform){
		List<PlatformAndMarket> list = Lists.newArrayList();
		int listSize = this.getPlatformAndMarkets().size();
		for (int i = 0; i < listSize; i++) {
			if(this.getPlatformAndMarkets().get(i).getPlatform() == platform){
				list.add(this.getPlatformAndMarkets().get(i));
			}
		}
		return list;
	}



}
