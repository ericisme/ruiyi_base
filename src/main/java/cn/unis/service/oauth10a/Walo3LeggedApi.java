package cn.unis.service.oauth10a;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

import cn.ruiyi.base.util.PathUtil;
import cn.unis.utils.HardWareUtils;

/**
 * walo API 
 * @author eric
 *
 */
public class Walo3LeggedApi extends DefaultApi10a {
	
	/**
	 * key and secret
	 */
	protected static String APIKEY;
	protected static String APISECRET;	
	
	/**
	 * walo path
	 */
	public static String WALOPATH;
	
	/**
	 * init key, secret, walo path from resources/config.properties.
	 */
	static {
		APIKEY    = PathUtil.getConfigResource("WALO_API_KEY");
		APISECRET = PathUtil.getConfigResource("WALO_API_SECRET");
		WALOPATH  = PathUtil.getConfigResource("WALO_WAOL_PATH");
	}
	
	/**
	 * auth path
	 */
	private static final String REQUEST_TOKEN_URL = WALOPATH + "/oauth/request_token?device_id="+HardWareUtils.getCpuId();	
	private static final String AUTHORIZE_URL     = WALOPATH + "/oauth/authorize";
	private static final String ACCESS_TOKEN_URL  = WALOPATH + "/oauth/access_token?device_id="+HardWareUtils.getCpuId();

	/**
	 * API 
	 */
	//帐号注销/登出
	public static final String LOGOUT = WALOPATH + "/api/account/logout";
	//获得帐号详细
	public static final String ACCOUNT_INFO = WALOPATH + "/api/account/info.json";
	//用户资料修改; methord:put;允许字段：handle_name(string 50),phone(string 12),personal_status(string 50)
	public static final String ACCOUNT_EDIT_PROFILE = WALOPATH + "/api/account/edit_profile";
	//用户世宇币sycoin,改成叫游币2014-06-26
	public static final String ACCOUNT_SYCOIN = WALOPATH + "/api/sycoin.json";
	//用户 世宇币,游场彩票，游戏钱包
	public static final String ACCOUNT_WALLET = WALOPATH + "/api/wallets.json";	
	//用户游戏币充值，使用世宇币充值游戏币
	public static final String ACCOUNT_EXCHANGE = WALOPATH + "/api/sycoin/exchange.json";
	//游币（以前叫世宇币）充值记录，分页
	public static final String SYCOIN_RECHARGE_REPORT = WALOPATH + "/api/sycoin/recharge_report.json?pg=<page>&rpp=<item_per_page|25>"; 
	//游戏币充值记录，分页,不用了
	public static final String TOKENS_RECHARGE_REPORT = WALOPATH + "/api/tokens/recharge_report.json?pg=<page>&rpp=<item_per_page|25> ";
	//游戏币兑换记录，分页,不用了
	public static final String SYCOIN_EXCHANGE_REPORT = WALOPATH + "/api/sycoin/exchange_report.json?pg=<page>&rpp=<item_per_page|25>";

	
	//世宇积分扣减
	public static final String ACHIEVEMENT_TX_PAYMENT = WALOPATH + "/api/achievement_tx/payment";
	//世宇积分扣减回滚
	public static final String ACHIEVEMENT_TX_PAYMENT_ROLLBACK = WALOPATH + "/api/achievement_tx/void";
	
	
	//彩票相关 2014-06-25
	//彩票结余查询
	public static final String ACCOUNT_TICKET_BALANCE = WALOPATH + "/api/tickets.json";
	//彩票增加,POST,parameters: amount=<amount>
	public static final String ACCOUNT_TICKET_CREDIT = WALOPATH + "/api/tickets";
	//彩票扣减,PUT,
	public static final String ACCOUNT_TICKET_DEBIT = WALOPATH + "/api/tickets";
	//彩票扣减回滚,PUT
	public static final String ACCOUNT_TICKET_DEBIT_ROLLBACK = WALOPATH + "/api/tickets/void";
	
	
	//实体币相关 2014-06-26
	//实体币结余查询
	public static final String ACCOUNT_TOKEN_BALANCE = WALOPATH + "/api/tokens.json"; 
	
	
	@Override
	public String getRequestTokenEndpoint()
	{
		return REQUEST_TOKEN_URL;
	}

	@Override
	public String getAccessTokenEndpoint()
	{
		return ACCESS_TOKEN_URL;
	}

	@Override
	public String getAuthorizationUrl(Token requestToken)
	{
		return String.format(AUTHORIZE_URL, requestToken.getToken());
	}
}
