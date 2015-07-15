package cn.unis.transit.userWallet;

import java.util.List;
//用户钱包，
public class UserWallet {
	//世宇币
	private Sycoin sycoin;
	//用户在各游场中的游戏代币数 ，和游场中的彩票数
	private List<UserGameCenterWallet> game_centers;
	
	
	public Sycoin getSycoin() {
		return sycoin;
	}
	public void setSycoin(Sycoin sycoin) {
		this.sycoin = sycoin;
	}
	public List<UserGameCenterWallet> getGame_centers() {
		return game_centers;
	}
	public void setGame_centers(List<UserGameCenterWallet> game_centers) {
		this.game_centers = game_centers;
	}
	
	
}
