package cn.unis.transit.userWallet;

import java.util.List;

public class UserGameCenterWallet {
	//游场key
	private String key;
	//游场name
	private String name;
	//游戏代币balance s
	private List<UserGameToken> tokens;
	//游场的彩票balance
	private UserGameCenterTicket ticket;
	
	
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

	public List<UserGameToken> getTokens() {
		return tokens;
	}
	public void setTokens(List<UserGameToken> tokens) {
		this.tokens = tokens;
	}
	public UserGameCenterTicket getTicket() {
		return ticket;
	}
	public void setTicket(UserGameCenterTicket ticket) {
		this.ticket = ticket;
	}
	
	
	
}
