package cn.unis.transit;

public class Arcade {
	
	private String device_id;
	private String cuk;
	private int console_index;
	
	private Consumer consumer;
	
	private String arcade_status;
	private String connection_state;
	
	
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getCuk() {
		return cuk;
	}
	public void setCuk(String cuk) {
		this.cuk = cuk;
	}
	public int getConsole_index() {
		return console_index;
	}
	public void setConsole_index(int console_index) {
		this.console_index = console_index;
	}
	public String getArcade_status() {
		return arcade_status;
	}
	public void setArcade_status(String arcade_status) {
		this.arcade_status = arcade_status;
	}
	public String getConnection_state() {
		return connection_state;
	}
	public void setConnection_state(String connection_state) {
		this.connection_state = connection_state;
	}
	public Consumer getConsumer() {
		return consumer;
	}
	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}
	
	
	
	
}
