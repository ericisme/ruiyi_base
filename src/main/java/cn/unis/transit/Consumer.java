package cn.unis.transit;

public class Consumer {

	private String key;
	private String name;
	private boolean rechargeable;
	
	/**
	 * 'CONSUMER_TYPE_MOBILE' => "mobile",
	 * 'CONSUMER_TYPE_TABLET' => "tablet",
	 * 'CONSUMER_TYPE_ARCADE' => "arcade",
	 * 'CONSUMER_TYPE_SERVER' => "server",
	 * 'CONSUMER_TYPE_WEB'    => "web",
	 */
	private String type;
	private Integer status;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isRechargeable() {
		return rechargeable;
	}
	public void setRechargeable(boolean rechargeable) {
		this.rechargeable = rechargeable;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
