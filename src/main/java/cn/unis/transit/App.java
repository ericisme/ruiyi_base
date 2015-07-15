package cn.unis.transit;

import java.util.List;

/**
 * walo 的App对象， 为consumer的上级
 * @author Administrator
 *
 */
public class App {
	
	private String key;
	private String name;
	private List<Consumer> consumers;
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
	public List<Consumer> getConsumers() {
		return consumers;
	}
	public void setConsumers(List<Consumer> consumers) {
		this.consumers = consumers;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
