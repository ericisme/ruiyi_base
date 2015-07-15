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
public class Walo2LeggedApi extends DefaultApi10a {
	
	/**
	 * key and secret
	 */
	protected static String APIKEY;
	protected static String APISECRET;	
	
	/**
	 * walo path
	 */
	private static String WALOPATH;
	
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
	private static final String REQUEST_TOKEN_URL = WALOPATH + "/oauth/2legged?device_id="+HardWareUtils.getCpuId();
	private static final String ACCESS_TOKEN_URL  = "";
	private static final String AUTHORIZE_URL     = "";

	/**
	 * API 
	 */
	//使用user_key,ls(一次性密码)登录walo，并获得accessToken
	public static final String WALO_SSO_VERIFY_URL = WALOPATH + "/api/users/<user_key>/sso";
	//根据game_center_key取得游乐场详细信息,json
	public static final String GAME_CENTER_INFO_JSON_URL = WALOPATH + "/api/game_centers/<game_center_key>/";
	//取得排名HTML
	public static final String SCORES_HTML_URL = WALOPATH + "/api/scores.html";
	//根据省代码获得游乐场分页列表
	public static final String GAME_CENTERS_BY_PROVINCE_CODE_URL = WALOPATH + "/api/province/<province_code>/game_centers?pg=<page_num>&rpp=<item_per_page>";
	//根据省代码获得游乐场全部列表
	public static final String GAME_CENTERS_BY_PROVINCE_CODE_URL_ALL = WALOPATH + "/api/province/<province_code>/game_centers";
	//根据市代码获得游乐场分页列表
	public static final String GAME_CENTERS_BY_CODE_CITY_URL = WALOPATH + "/api/city/<city_code>/game_centers?pg=<page_num>&rpp=<item_per_page>";
	//根据市代码获得游乐场全部列表
	public static final String GAME_CENTERS_BY_CODE_CITY_URL_ALL = WALOPATH + "/api/city/<city_code>/game_centers";
	//根据game_center_key取得机台分页列表
	public static final String ARCADE_MACHINES_BY_GAME_CENTER_KEY_JSON_CENTER = WALOPATH + "/api/game_centers/<game_center_key>/arcades?pg=<page_num>&rpp=<item_per_page>";
	//根据USER_KEY获得USER_DETAIL_INFO,fields=key,username,handle_name,grade,email,phone,timezone,country,lang,personal_status,registered_arcades,achievement_score,accumulated_ascore,registered_at,expire,status
	public static final String USER_DETAIL_INFO = WALOPATH + "/api/users/<user_key>/info.json?fields=<fields>";
	
	//充值用：
	//根据user_key获得充值游戏列表
	public static final String GET_CONSUMERS_BY_USER_KEY = WALOPATH + "/api/users/<user_key>/consumers.json";
	//根据user_key,游戏consumer_key获得 用户的游戏钱包集合
	public static final String GET_USER_GAME_WALLETS = WALOPATH +"/api/users/<user_key>/consumers/<consumer_key>/tokens.json";
	//根据user_key,游戏consumer_key,game_center_key，获得用户的游戏钱包
	public static final String GET_USER_GAME_WALLET = WALOPATH +"/api/users/<user_key>/consumers/<consumer_key>/game_centers/<game_center_key>/tokens.json";
	//所有游戏，游戏在APP下面,
	public static final String GET_ALL_APP = WALOPATH + "/api/apps.json";
	//获得所有game_center
	public static final String GET_ALL_GAME_CENTER = WALOPATH + "/api/game_centers.json";
	
	//根据user_key查询用户的游币(以前叫世宇币)钱包
	public static final String GET_SYCOIN_BY_USER_KEY = WALOPATH + "/api/users/<user_key>/sycoin.json";
	
	//根据user_key查询用户的实体币钱包 2014-06-27
	public static final String GET_TOKEN_BALANCE_BY_USER_KEY = WALOPATH +"/api/users/<user_key>/tokens.json";
	
	//根据user_key,game_center_key,获得用户在某个游场的彩票结余
	public static final String GET_USER_GAME_CENTER_TICKETS = WALOPATH + "/api/users/<user_key>/game_centers/<game_center_key>/tickets.json";
	
	//post方法，充值到游币钱包(以前叫世宇币)
	public static final String CHARGE_TO_SYCOIN = WALOPATH + "/api/users/<user_key>/sycoin/recharge.json";
	//post方法，充值到游戏钱包
	public static final String CHARGE_TO_GAME_TOKEN = WALOPATH + "/api/users/<user_key>/consumers/<consumer_key>/game_centers/<game_center_key>/tokens/recharge.json";
	
	//post方法，充值到实体币钱包,Parameters: amount=<amount> sign=<encrypted_data> 2014-06-27
	public static final String CHARGE_TO_TOKEN = WALOPATH + "/api/users/<user_key>/tokens/recharge.json";
	
	
	//根据consumer_key获得consumer_detail
	public static final String GET_CONSUMER_DETAIL = WALOPATH + "/api/consumers/<consumer_key>/";
	
	
	
	
	//下面的api是面向游乐场用户的3-legged api,这里暂时使用2-legged方式进行
	
	
	//根據filter_key,filter_value,獲得游場下的帳號列表; 因為帳號會比較多，最好有篩選功能根分頁功能;不建议使用。
	public static final String QUERY_USER_LIST = WALOPATH + "/api/users.json?pg=<page>&rpp=<item_per_page|25>&filter=<filter_key>&value=<filter_value>";
	//所有帐号的分页列表。
	public static final String USER_PAGE_LIST = WALOPATH + "/api/users.json?pg=<page>&rpp=<item_per_page|25>";
	//根據游樂場key,獲得游場下的帳號列表; 因為帳號會比較多，最好有篩選功能根分頁功能;
	public static final String QUERY_USER_LIST_BY_GAME_CENTER_KEY = WALOPATH + "/api/game_centers/<game_center_key>/users.json?pg=<page>&rpp=<item_per_page|25>";
	//根據游場key,帳號key, 獲得該帳號在該游場下的 游戲幣數，根彩票數, 最后登入時間
	public static final String USER_GAME_CENTER_BALANCE = WALOPATH + "/api/users/<user_key>/wallets/<game_center_key>.json";
	//根据game_center_key 返回游戏列表      <----未完成，完成后 去掉注释
	public static final String GET_GAMES_BY_GAME_CENTER_KEY= WALOPATH + "/api/game_centers/<game_center_key>/consumers";
	//根据game_center_key 返回 机台游戏列表
	public static final String GET_ARCADE_GAMES_BY_GAME_CENTER_KEY= WALOPATH + "/api/game_centers/<game_center_key>/arcade_games";	
	//获得某个用户在某个游场，某个游戏的登录次数
	public static final String GET_USER_LOGIN_COUNT=WALOPATH + "/api/users/<user_key>/game_centers/<game_centere_key>/login_summary.json?consumer_key=<consumer_key>";
	//游场报表api,總派票/總投幣 (不同遊戲)
	public static final String TX_REPORT_BY_GAME = WALOPATH + "/api/game_centers/<game_center_key>/tx_report_by_game.json";
	//游场报表api,指定日期(派票/投幣)(不同遊戲),        日期格式 YYYY-MM-DD
	public static final String GAME_DAILY_TX_REPORT = WALOPATH + "/api/game_centers/<game_center_key>/daily_tx_report.json?begin=<begin_date>&until=<until_date>";
	//游场报表api,指定日期(派票/投幣)(不同遊戲),指定游戏,日期格式 YYYY-MM-DD
	public static final String GAME_DAILY_TX_REPORT_SELECTED_GAME = WALOPATH + "/api/game_centers/<game_center_key>/daily_tx_report.json?begin=<begin_date>&until=<until_date>&consumer_key=<consumer_key>";
	//游场报表api,個別機台派票/投币
	public static final String ARCADE_DAILY_TX_REPORT = WALOPATH +"/api/arcades/<cuk>/daily_tx_report.json?begin=<begin_date>&until=<until_date>";
	//游场报表api,個別帳號錢包
	public static final String USER_WALLETS = WALOPATH+"/api/users/<user_key>/wallets.json";
	
	
	
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
