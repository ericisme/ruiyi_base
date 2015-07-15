package cn.unis.transit;

import java.util.Date;

public class UserWallets {
	private GameCenterSimpleInfo game_center;
	private Integer token;
	private Integer ticket;
	private Date last_update;
	private String status;
	public String getStatusChinese() {		
		if("1".equals(status)){
			return "正常";
		}
		if("2".equals(status)){
			return "被锁";
		}
		if("3".equals(status)){
			return "被停用";
		}
		return "";
	}
	
	
	public GameCenterSimpleInfo getGame_center() {
		return game_center;
	}
	public void setGame_center(GameCenterSimpleInfo game_center) {
		this.game_center = game_center;
	}
	public Integer getToken() {
		return token;
	}
	public void setToken(Integer token) {
		this.token = token;
	}
	public Integer getTicket() {
		return ticket;
	}
	public void setTicket(Integer ticket) {
		this.ticket = ticket;
	}
	public Date getLast_update() {
		return last_update;
	}
	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
