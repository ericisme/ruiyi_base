package cn.unis.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.ruiyi.base.constants.Constants;

@Entity
@Table(name = "unis_platform_and_market",schema=Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlatformAndMarket extends IdEntity implements Serializable {
	private static final long serialVersionUID = -2411128540067668446L;
	public static final byte IOS = 1;
	public static final byte ANDROID = 2;
	public static final byte WP =3;
	/**
	 * 1.ios
	 * 2.android
	 * 3.windows phone
	 */
	private byte platform;
	/**
	 * 市场名场
	 */
	private String marketName;
	/**
	 * 下载链接
	 */
	private String downloadUrl;
	
	/**
	 * 下载次数
	 */
	private int downloadTimes;
	

	public byte getPlatform() {
		return platform;
	}
	public void setPlatform(byte platform) {
		this.platform = platform;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public int getDownloadTimes() {
		return downloadTimes;
	}
	public void setDownloadTimes(int downloadTimes) {
		this.downloadTimes = downloadTimes;
	}

}
