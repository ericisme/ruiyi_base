package cn.unis.dto;
/**
 *
 * @author unis
 *
 */
public class GameDto {
	private long [] ios_id;
	private String [] ios_url;
	private String [] ios_marketname;
	private long [] android_id;
	private String [] android_url;
	private String [] android_marketname;
	public long[] getIos_id() {
		return ios_id;
	}
	public void setIos_id(long[] ios_id) {
		this.ios_id = ios_id;
	}
	public String[] getIos_url() {
		return ios_url;
	}
	public void setIos_url(String[] ios_url) {
		this.ios_url = ios_url;
	}
	public String[] getIos_marketname() {
		return ios_marketname;
	}
	public void setIos_marketname(String[] ios_marketname) {
		this.ios_marketname = ios_marketname;
	}
	public long[] getAndroid_id() {
		return android_id;
	}
	public void setAndroid_id(long[] android_id) {
		this.android_id = android_id;
	}
	public String[] getAndroid_url() {
		return android_url;
	}
	public void setAndroid_url(String[] android_url) {
		this.android_url = android_url;
	}
	public String[] getAndroid_marketname() {
		return android_marketname;
	}
	public void setAndroid_marketname(String[] android_marketname) {
		this.android_marketname = android_marketname;
	}

}
