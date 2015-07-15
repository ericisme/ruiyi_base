package cn.unis.transit.exchange;

/**
 *  用于存放  世宇币 充值 游戏代币 的返回信息。
 * @author Administrator
 *
 */
public class ExchangeReturn {
	//充值后的世宇币
	private ExchangeSycoin sycoin;
	//充值后的游戏代币
	private ExchangeTokens tokens;
	public ExchangeSycoin getSycoin() {
		return sycoin;
	}
	public void setSycoin(ExchangeSycoin sycoin) {
		this.sycoin = sycoin;
	}
	public ExchangeTokens getTokens() {
		return tokens;
	}
	public void setTokens(ExchangeTokens tokens) {
		this.tokens = tokens;
	}
	
	
}
