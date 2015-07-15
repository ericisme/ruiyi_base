package cn.unis.transit;

import java.util.Date;

/**
 * 用户 根 游戏 的钱包 列表 
 * 用于选择用户 根游戏之后 的联动 下位选择器
 * @author Administrator
 *
 */
public class UserGameWallet {
	
	private GameCenterSimpleInfo game_center;
	private Integer balance;
	private Date updated_at;
	private Integer status;
	
	public GameCenterSimpleInfo getGame_center() {
		return game_center;
	}
	public void setGame_center(GameCenterSimpleInfo game_center) {
		this.game_center = game_center;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
		
	
}
