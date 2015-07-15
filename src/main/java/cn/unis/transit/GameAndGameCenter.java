package cn.unis.transit;

import java.util.List;

import com.google.common.collect.Lists;

public class GameAndGameCenter {
	private String gameKey;
	private String gameName;
	private List<GameCenter> gameCenterList = Lists.newArrayList();
	public String getGameKey() {
		return gameKey;
	}
	public void setGameKey(String gameKey) {
		this.gameKey = gameKey;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public List<GameCenter> getGameCenterList() {
		return gameCenterList;
	}
	public void setGameCenterList(List<GameCenter> gameCenterList) {
		this.gameCenterList = gameCenterList;
	}
}

