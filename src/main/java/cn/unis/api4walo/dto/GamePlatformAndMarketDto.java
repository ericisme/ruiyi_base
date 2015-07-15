package cn.unis.api4walo.dto;

public class GamePlatformAndMarketDto {

	/* 1.ios
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
