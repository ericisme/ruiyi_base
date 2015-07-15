package cn.unis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.unis.service.interfaces.IGameCenterService;
import cn.unis.service.oauth10a.Walo2LeggedApi;
import cn.unis.service.oauth10a.Walo2LeggedService;
import cn.unis.service.oauth10a.Walo3LeggedApi;
import cn.unis.service.oauth10a.Walo3LeggedService;
import cn.unis.transit.App;
import cn.unis.transit.Arcade;
import cn.unis.transit.BaseReturnValue;
import cn.unis.transit.BaseReturnValues;
import cn.unis.transit.ChargeToSycoin;
import cn.unis.transit.Consumer;
import cn.unis.transit.GameCenterDetailInfo;
import cn.unis.transit.GameCenterSimpleInfo;
import cn.unis.transit.UserDetailInfo;
import cn.unis.transit.UserGameWallet;
import cn.unis.transit.WaloGameSimpleInfo;
import cn.unis.transit.achievement.AchievementPaymentReturns;
import cn.unis.transit.achievement.AchievementPaymentRollBackReturns;
import cn.unis.transit.chargeRecord.Page;
import cn.unis.transit.chargeRecord.SycoinExchangeRecord;
import cn.unis.transit.chargeRecord.SycoinRechargeRecord;
import cn.unis.transit.chargeRecord.TokensRechargeRecord;
import cn.unis.transit.chargeRecord.TokensRechargeRecordNew;
import cn.unis.transit.exchange.ExchangeReturn;
import cn.unis.transit.ticket.TicketBalance;
import cn.unis.transit.ticket.TicketDebitReturns;
import cn.unis.transit.ticket.TicketDebitRollBackReturns;
import cn.unis.transit.token.ChargeToToken;
import cn.unis.transit.token.TokenBalance;
import cn.unis.transit.userWallet.Sycoin;
import cn.unis.transit.userWallet.UserGameCenterTicket;
import cn.unis.transit.userWallet.UserGameCenterWallet;
import cn.unis.transit.userWallet.UserWallet;
import cn.unis.utils.JsonUtils;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * 游乐场service ,WALO 实现
 * @author eric
 *
 */
@Service("waloGameCenterServiceAdapter")
public class WaloGameCenterServiceAdapter implements IGameCenterService {
	
	@Autowired
	private Walo2LeggedService walo2LeggedService;
	@Autowired
	private Walo3LeggedService walo3LeggedService;
	
	
	/**
	 * 根据 gameConterKey 获得 游乐场 基本信息
	 * @param gameCenterKey
	 * @return
	 */
	@Override
	public String getGameCenterJsonByGameCenterKey(String gameCenterKey) {
		return walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GAME_CENTER_INFO_JSON_URL, gameCenterKey);
	}

	/**
	 * 取得 分数 html
	 * @return
	 */
	@Override
	public String getScoresHtml(){
		return walo2LeggedService.requetHtmlBodyForWalo2LeggedApi(Walo2LeggedApi.SCORES_HTML_URL);
	}

	//@Override
	//public String getCenterListByProvince(String province_code) {
	//	return walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GAME_CENTERS_BY_PROVINCE_CODE_URL, province_code);
	//}

	
	/**
	 * 根据 gameConterKey 获得 游乐场 详细信息
	 */
	public BaseReturnValue<GameCenterDetailInfo> getGameCenter(String gameCenterKey){
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GAME_CENTER_INFO_JSON_URL, gameCenterKey), new TypeReference<BaseReturnValue<GameCenterDetailInfo>>(){});
	}
	
	/**
	 * 根据 省代码 获得分页game_center列表
	 */
	public BaseReturnValues<GameCenterSimpleInfo> getGameCentersByProvinceCode(String province_code, int page_num, int item_per_page){
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GAME_CENTERS_BY_PROVINCE_CODE_URL, province_code, String.valueOf(page_num), String.valueOf(item_per_page)), new TypeReference<BaseReturnValues<GameCenterSimpleInfo>>(){});
	}
	
	/**
	 * 根据 市代码 获得分页game_center列表
	 */
	public BaseReturnValues<GameCenterSimpleInfo> getGameCentersByCityCode(String city_code, int page_num, int item_per_page){
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GAME_CENTERS_BY_CODE_CITY_URL, city_code, String.valueOf(page_num), String.valueOf(item_per_page)), new TypeReference<BaseReturnValues<GameCenterSimpleInfo>>(){});
	}
	
	/**
	 * 根据 省代码 获得全部game_center列表
	 */
	public BaseReturnValues<GameCenterSimpleInfo> getGameCentersByProvinceCode(String province_code){
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GAME_CENTERS_BY_PROVINCE_CODE_URL, province_code), new TypeReference<BaseReturnValues<GameCenterSimpleInfo>>(){});
	}
	
	/**
	 * 根据 市代码 获得全部game_center列表
	 */
	public BaseReturnValues<GameCenterSimpleInfo> getGameCentersByCityCode(String city_code){
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GAME_CENTERS_BY_CODE_CITY_URL, city_code), new TypeReference<BaseReturnValues<GameCenterSimpleInfo>>(){});
	}
	
	
	/**
	 * 根据game_center_key获得分页arcade列表
	 */
	@Override
	public BaseReturnValues<Arcade> getArcadeMachinesOfGameCenter(String game_center_key, int page_num, int item_per_page) {
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.ARCADE_MACHINES_BY_GAME_CENTER_KEY_JSON_CENTER, game_center_key, String.valueOf(page_num), String.valueOf(item_per_page)), new TypeReference<BaseReturnValues<Arcade>>(){});
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 根据user_key,获得user 实体信息,string
	 * @param user_key
	 * @param fields fields=key,username,handle_name,grade,email,phone,timezone,country,lang,personal_status,registered_arcades,achievement_score,accumulated_ascore,registered_at,expire,status
	 * @return
	 */
	@Override
	public BaseReturnValue<UserDetailInfo> getUserDetailInfoByUserKey(String user_key, String fields) {
		// TODO Auto-generated method stub
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.USER_DETAIL_INFO, user_key, fields), new TypeReference<BaseReturnValue<UserDetailInfo>>(){});
	}

	/**
	 * 根据查询条件，查询值，获得会员分页列表
	 * @param page_num
	 * @param item_per_page
	 * @param filter_key
	 * @param filter_value
	 * @return
	 */
	@Override
	public BaseReturnValues<String> queryUserList(int page_num, int item_per_page, String filter_key, String filter_value) {
		if("game_center_key".equals(filter_key)){
			return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.QUERY_USER_LIST_BY_GAME_CENTER_KEY, filter_value, String.valueOf(page_num), String.valueOf(item_per_page)), new TypeReference<BaseReturnValues<String>>(){});
		}else{
			return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.QUERY_USER_LIST, String.valueOf(page_num), String.valueOf(item_per_page), filter_key, filter_value), new TypeReference<BaseReturnValues<String>>(){});
		}
		
	}

	/**
	 * 获得全部会员kdy分页列表
	 * @param page_num
	 * @param item_per_page
	 * @return
	 */
	public BaseReturnValues<String> queryUserList(int page_num , int item_per_page){
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.USER_PAGE_LIST, String.valueOf(page_num), String.valueOf(item_per_page)), new TypeReference<BaseReturnValues<String>>(){});
	}

	/**
	 * 用户在某一个游场上的 代币数 根彩票数，还有last_update的时间
	 * @param user_key
	 * @param game_center_key
	 * @return
	 */
	@Override
	public BaseReturnValue<UserGameCenterWallet> getBalanceByUserKeyAndGameCenterKey(String user_key, String game_center_key) {
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.USER_GAME_CENTER_BALANCE, user_key, game_center_key), new TypeReference<BaseReturnValue<UserGameCenterWallet>>(){});
	}

	/**
	 * 根据game_center_key获得游戏列表
	 */
	@Override
	public BaseReturnValues<WaloGameSimpleInfo> getWaloArcadeGamesByGameCenterKey(String game_center_key){
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GET_ARCADE_GAMES_BY_GAME_CENTER_KEY, game_center_key), new TypeReference<BaseReturnValues<WaloGameSimpleInfo>>(){});
	}

	/**
	 * 获得某个用户在某个游场，某个游戏的登录次数
	 */
	public BaseReturnValues<WaloGameSimpleInfo> getUserLoginCountByGameCenterKeyAndGameConsumerKey(String user_key, String game_center_key, String game_consumber_key){
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GET_USER_LOGIN_COUNT, user_key, game_center_key, game_consumber_key), new TypeReference<BaseReturnValues<WaloGameSimpleInfo>>(){});
	}
	
	/**
	 *根据user_key,获得用户的钱包信息
	 */
	public BaseReturnValue<UserWallet> getUserWalletsByUserKey(String user_key){
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.USER_WALLETS, user_key), new TypeReference<BaseReturnValue<UserWallet>>(){});
	}

	/**
	 *游场报表api,總派票/總投幣 (不同遊戲)
	 *直接返回json.
	 */
	public String reprotTxByGame(String game_center_key){
		return walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.TX_REPORT_BY_GAME, game_center_key);
	}
	
	/**
	 *游场报表api,指定日期(派票/投幣)(不同遊戲),指定游戏,日期格式 YYYY-MM-DD
	 *直接返回json.
	 */
	public String game_daily_tx_report(String game_center_key, String begin_date, String until_date, String consumer_key){
		return walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GAME_DAILY_TX_REPORT_SELECTED_GAME, game_center_key, begin_date, until_date, consumer_key);
	}
	/**
	 *游场报表api,指定日期(派票/投幣)(不同遊戲),         日期格式 YYYY-MM-DD
	 *直接返回json.
	 */
	public String game_daily_tx_report(String game_center_key, String begin_date, String until_date){
		return walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GAME_DAILY_TX_REPORT, game_center_key, begin_date, until_date);
	}
	/**
	 *個別機台派票/投币,         日期格式 YYYY-MM-DD
	 *直接返回json.
	 */
	public String arcade_daily_tx_report(String cuk, String begin_date, String until_date){
		return walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.ARCADE_DAILY_TX_REPORT, cuk, begin_date, until_date);
	}

	/**
	 * 获得所有walo app(app为consumer的上级)
	 */
	public BaseReturnValues<App> getAllApp(){
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GET_ALL_APP), new TypeReference<BaseReturnValues<App>>(){});
	}
	/**
	 * 获得所有充值consumer(游戏)列表
	 */
	public List<Consumer> getAllChargeableConsumer(){
		List<Consumer> chargeableConsumerList = new ArrayList<Consumer>();
		BaseReturnValues<App> allApp = JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GET_ALL_APP), new TypeReference<BaseReturnValues<App>>(){});
		if(allApp.getMsg()!=null){
			for(App app : allApp.getMsg()){
				if(app.getConsumers()!=null){
					for(Consumer consumer : app.getConsumers()){
						if(consumer!=null){
							if(consumer.isRechargeable()){
								chargeableConsumerList.add(consumer);
							}
						}
					}
				}
			}
		}
		return chargeableConsumerList;
	}
	/**
	 * 根据user_key获得consumer列表
	 */
	public BaseReturnValues<Consumer> getConsumersUserKey(String user_key){
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GET_CONSUMERS_BY_USER_KEY, user_key), new TypeReference<BaseReturnValues<Consumer>>(){});
	}

	/**
	 * 根据user_key获得充值consumer(游戏)列表
	 */
	public BaseReturnValues<Consumer> getChargeableConsumerListByUserKey(String user_key){
		BaseReturnValues<Consumer> allConsumers = JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GET_CONSUMERS_BY_USER_KEY, user_key), new TypeReference<BaseReturnValues<Consumer>>(){});
		List<Consumer> removeIndexes = new ArrayList<Consumer>();
		if(allConsumers.getMsg()!=null){
			for(int i=0;i< allConsumers.getMsg().size();i++){
				if(allConsumers.getMsg().get(i)!=null){
					if(!allConsumers.getMsg().get(i).isRechargeable()){
						removeIndexes.add(allConsumers.getMsg().get(i));
					}
				}
			}
		}
		for(Consumer consumer :removeIndexes){
			allConsumers.getMsg().remove(consumer);
		}
		return allConsumers;		
	}
	
	/**
	 * 根据user_key查询用户的世宇钱包
	 */
	public BaseReturnValue<Sycoin> getSycoinByUserKey(String user_key){
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GET_SYCOIN_BY_USER_KEY, user_key), new TypeReference<BaseReturnValue<Sycoin>>(){});
	}

	/**
	 * 根据user_key查询用户的 实体币钱包
	 */
	public BaseReturnValue<TokenBalance> getTokenBalanceByUserKey(String user_key){
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GET_TOKEN_BALANCE_BY_USER_KEY, user_key), new TypeReference<BaseReturnValue<TokenBalance>>(){});
	}
	
	
	/**
	 * 获得所有game_center
	 */
	public BaseReturnValues<GameCenterSimpleInfo> getAllGameCenter(){
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GET_ALL_GAME_CENTER), new TypeReference<BaseReturnValues<GameCenterSimpleInfo>>(){});
	}
	
	/**
	 * 根据user_key, consumer_key获得游戏钱包列表
	 */
	public BaseReturnValues<UserGameWallet> getUserGameWalletListByUserKeyAndConsumerKey(String user_key, String consumer_key){
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GET_USER_GAME_WALLETS, user_key, consumer_key), new TypeReference<BaseReturnValues<UserGameWallet>>(){});
	}
	/**
	 * 根据user_key, consumer_key, game_center_key获得游戏钱包
	 */
	public BaseReturnValue<UserGameWallet> getUserGameWallet(String user_key, String consumer_key, String game_center_key){
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GET_USER_GAME_WALLET, user_key, consumer_key, game_center_key), new TypeReference<BaseReturnValue<UserGameWallet>>(){});
	}
	/**
	 * 根据user_key,game_center_key,获得用户在某个游场的彩票结余
	 */
	public BaseReturnValue<UserGameCenterTicket> getTicketsByUserKeyAndGameCenterKey(String user_key, String game_center_key){
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GET_USER_GAME_CENTER_TICKETS, user_key, game_center_key), new TypeReference<BaseReturnValue<UserGameCenterTicket>>(){});
	}
	/**
	 * 根据consumer_key获得consumer_detail(游戏详细)
	 */
	public BaseReturnValue<Consumer> getConsumerDetail(String consumer_key){
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GET_CONSUMER_DETAIL, consumer_key), new TypeReference<BaseReturnValue<Consumer>>(){});
	}
	
	
	/**
	 * 用户游币钱包(以前叫世宇币)充值
	 * @param user_key 要充值的用户key
	 * @param amount   要充值的数量，游币(以前叫世宇币)
	 * @param sign	加密签名，AES(json_data),json_data 例子：{"outTradeNo":"fsdfdsf88fhdsfhsda8fh3","date_time":"2013-12-12 10:10:10",payMoney":"100.05}
	 * @return
	 */
	public BaseReturnValue<ChargeToSycoin> chargeToSycoin(String user_key, Integer amount, String sign ){
		String apiPath = walo2LeggedService.apiFormat(Walo2LeggedApi.CHARGE_TO_SYCOIN, user_key);	
		OAuthRequest request = new OAuthRequest(Verb.POST, apiPath);
		request.addBodyParameter("amount", String.valueOf(amount));
		request.addBodyParameter("sign", sign);
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApiByOAuthRequest(request),new TypeReference<BaseReturnValue<ChargeToSycoin>>(){});
	}
	
	/**
	 * 用户游戏钱包充值
	 * @param user_key 要充值的用户key
	 * @param consumer_key 要充值钱包的游戏key
	 * @param game_center_key 要充值钱包的游场key
	 * @param amount   要充值的数量，世宇币
	 * @param sign	加密签名，AES(json_data),json_data 例子：{"outTradeNo":"fsdfdsf88fhdsfhsda8fh3","date_time":"2013-12-12 10:10:10","payMoney":100.05}
	 * @return
	 */
	public BaseReturnValue<ChargeToSycoin> chargeToGameToken(String user_key, String consumer_key, String game_center_key, Integer amount, String sign ){
		String apiPath = walo2LeggedService.apiFormat(Walo2LeggedApi.CHARGE_TO_GAME_TOKEN, user_key, consumer_key, game_center_key);	
		OAuthRequest request = new OAuthRequest(Verb.POST, apiPath);
		request.addBodyParameter("amount", String.valueOf(amount));
		request.addBodyParameter("sign", sign);
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApiByOAuthRequest(request),new TypeReference<BaseReturnValue<ChargeToSycoin>>(){});
	}
	
	
	/**
	 * 用户实体币钱包充值
	 * @param user_key 要充值的用户key
	 * @param amount   要充值的数量，世宇币
	 * @param sign	加密签名，AES(json_data),json_data 例子：{"outTradeNo":"fsdfdsf88fhdsfhsda8fh3","date_time":"2013-12-12 10:10:10",payMoney":"100.05}
	 * @return
	 */
	public BaseReturnValue<ChargeToToken> chargeToToken(String user_key, Integer amount, String sign ){
		String apiPath = walo2LeggedService.apiFormat(Walo2LeggedApi.CHARGE_TO_TOKEN, user_key);	
		OAuthRequest request = new OAuthRequest(Verb.POST, apiPath);
		request.addBodyParameter("amount", String.valueOf(amount));
		request.addBodyParameter("sign", sign);
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApiByOAuthRequest(request), new TypeReference<BaseReturnValue<ChargeToToken>>(){});
	}
	
	
	
	
	/**
	 * 取得3-legged 的RequestToken
	 * @return
	 */
	public Token get3LeggedRequestToken(String callback_path){
		return walo3LeggedService.getRequestToken(callback_path);
	}
	
	/**
	 * 取得3-legged 的AccessToken
	 * @param oauth_verifier
	 * @return
	 */
	public Token get3LeggedAccessToken(Token requestToken, String oauth_verifier){
		return walo3LeggedService.getAccessToken(requestToken, oauth_verifier);
	}
	/**
	 * walo帐号注销/登出
	 * @param accessToken 帐号登录后的accessToken.
	 * @return
	 */
	public String logout3Legged(Token accessToken){
		OAuthRequest request = new OAuthRequest(Verb.DELETE, Walo3LeggedApi.LOGOUT);
		return walo3LeggedService.requetJsonBodyForWalo3LeggedApiByOAuthRequest(accessToken, request);	
	}
	
	/**
	 * 用户详细资料，3-legged
	 */
	public BaseReturnValue<UserDetailInfo> getAccountInfo(Token accessToken){		
		return JsonUtils.fromJson(walo3LeggedService.requetJsonBodyForWalo3LeggedApi(accessToken, Walo3LeggedApi.ACCOUNT_INFO),new TypeReference<BaseReturnValue<UserDetailInfo>>(){});
	}
	
	/**
	 * 用户彩票结余，3-legged
	 */
	public BaseReturnValue<TicketBalance> getAccountTicketBalance(Token accessToken){		
//		String ticket_credit_apiPath = walo3LeggedService.apiFormat(Walo3LeggedApi.ACCOUNT_TICKET_CREDIT);
//		System.out.println("=====ticket credit apiPath:"+ticket_credit_apiPath);
//		OAuthRequest ticket_credit_request = new OAuthRequest(Verb.POST, ticket_credit_apiPath);
//		ticket_credit_request.addBodyParameter("amount", "3");
//		String ticket_credit_returns = walo3LeggedService.requetJsonBodyForWalo3LeggedApiByOAuthRequest(accessToken, ticket_credit_request);
//		System.out.println("=====ticket_credit_returns:"+ticket_credit_returns);
//		
//		String ticket_debit_apiPath = walo3LeggedService.apiFormat(Walo3LeggedApi.ACCOUNT_TICKET_DEBIT);
//		System.out.println("*****ticket_debit_apiPath:"+ticket_debit_apiPath);
//		OAuthRequest ticket_debit_request = new OAuthRequest(Verb.PUT, ticket_credit_apiPath);
//		ticket_debit_request.addBodyParameter("amount", "5");
//		String ticket_debit_returns = walo3LeggedService.requetJsonBodyForWalo3LeggedApiByOAuthRequest(accessToken, ticket_debit_request);
//		System.out.println("*****ticket_debit_returns:"+ticket_debit_returns);
//		
//		String ticket_debit_rollback_apiPath = walo3LeggedService.apiFormat(Walo3LeggedApi.ACCOUNT_TICKET_DEBIT_ROLLBACK);
//		System.out.println("*****ticket_debit_rollback_apiPath:"+ticket_debit_rollback_apiPath);
//		OAuthRequest ticket_debit_rollback_request = new OAuthRequest(Verb.PUT, ticket_debit_rollback_apiPath);
//		ticket_debit_rollback_request.addBodyParameter("tx_code", "UD6575f030a95940b900695c5d36ddd4d8");
//		String ticket_debit_rollback_returns = walo3LeggedService.requetJsonBodyForWalo3LeggedApiByOAuthRequest(accessToken, ticket_debit_rollback_request);
//		System.out.println("*****ticket_debit_rollback_returns:"+ticket_debit_rollback_returns);
		
		return JsonUtils.fromJson(walo3LeggedService.requetJsonBodyForWalo3LeggedApi(accessToken, Walo3LeggedApi.ACCOUNT_TICKET_BALANCE),new TypeReference<BaseReturnValue<TicketBalance>>(){});
	}
	
	/**
	 * 彩票 扣减, 3-legged
	 * @param accessToken	3-legged-accessToken
	 * @param amount   		要扣减的世宇彩票数目   
	 * @return BaseReturnValue<AchievementPaymentReturns> 
	 * 						err_num=0时为成功，err_num=402时为彩票不够用于扣减
	 * 						成功后TicketDebitRetuns里的tx_code用于退款使用，请保存到数据库 			
	 */
	public BaseReturnValue<TicketDebitReturns> ticketDebit(Token accessToken, Integer amount){
		String apiPath = walo3LeggedService.apiFormat(Walo3LeggedApi.ACCOUNT_TICKET_DEBIT);
		OAuthRequest request = new OAuthRequest(Verb.PUT, apiPath);
		request.addBodyParameter("amount", amount+"");
		return JsonUtils.fromJson(walo3LeggedService.requetJsonBodyForWalo3LeggedApiByOAuthRequest(accessToken, request), new TypeReference<BaseReturnValue<TicketDebitReturns>>(){}) ;
	}
	
	/**
	 * 回退已扣减的 彩票，用于交易不成功时的退款, 3-legged
	 * @param accessToken
	 * @param tx_code
	 * @return BaseReturnValue<AchievementPaymentReturns> 
	 * 						err_num=0   时为成功回退，
	 * 						err_num=404 时为没找到对应的tx_code的扣减记录, 
	 *                      err_num=410 时为该tx_code已经被回退过了	
	 */
	public BaseReturnValue<TicketDebitRollBackReturns> ticketDebitRollBack(Token accessToken, String tx_code){
		String apiPath = walo3LeggedService.apiFormat(Walo3LeggedApi.ACCOUNT_TICKET_DEBIT_ROLLBACK);
		OAuthRequest request = new OAuthRequest(Verb.PUT, apiPath);
		request.addBodyParameter("tx_code", tx_code);
		return JsonUtils.fromJson(walo3LeggedService.requetJsonBodyForWalo3LeggedApiByOAuthRequest(accessToken, request), new TypeReference<BaseReturnValue<TicketDebitRollBackReturns>>(){}) ;
	}
	
	
	/**
	 * 用户实体币结余，3-legged
	 */
	public BaseReturnValue<TokenBalance> getAccountTokenBalance(Token accessToken){				
		return JsonUtils.fromJson(walo3LeggedService.requetJsonBodyForWalo3LeggedApi(accessToken, Walo3LeggedApi.ACCOUNT_TOKEN_BALANCE),new TypeReference<BaseReturnValue<TokenBalance>>(){});
	}
	
	/**
	 * 用户资料修，3-legged
	 * @param accessToken 帐号登录后的accessToken.
	 * @param edit_field  修改的字段，只允许修改handle_name(string 50),phone(string 12),personal_status(string 50)
	 * @param edit_value  要修改字段的值
	 */
	public String editAccountProfile(Token accessToken, String edit_field, String edit_value, String original_handle_name){
		String apiPath = walo3LeggedService.apiFormat(Walo3LeggedApi.ACCOUNT_EDIT_PROFILE);
		System.out.println("exchange apiPath:"+apiPath);
		OAuthRequest request = new OAuthRequest(Verb.PUT, apiPath);
		request.addBodyParameter(edit_field, edit_value);
		if(!"handle_name".equals(edit_field)){
			request.addBodyParameter("handle_name", original_handle_name);
		}
		return walo3LeggedService.requetJsonBodyForWalo3LeggedApiByOAuthRequest(accessToken, request);
	}
	
	/**
	 * 用户世宇币，3-legged
	 */
	public BaseReturnValue<Sycoin> getSycoin(Token accessToken){
		return JsonUtils.fromJson(walo3LeggedService.requetJsonBodyForWalo3LeggedApi(accessToken, Walo3LeggedApi.ACCOUNT_SYCOIN), new TypeReference<BaseReturnValue<Sycoin>>(){});
	}
	/**
	 * 用户 世宇币,游场彩票，游戏钱包，3-legged
	 */
	public BaseReturnValue<UserWallet> getWallet(Token accessToken){
		return JsonUtils.fromJson(walo3LeggedService.requetJsonBodyForWalo3LeggedApi(accessToken, Walo3LeggedApi.ACCOUNT_WALLET), new TypeReference<BaseReturnValue<UserWallet>>(){});
	}
	
	/**
	 * 
	 *  用户游戏币充值，使用世宇币充值游戏代币，3-legged	 
	 *  
	 * @param accessToken 		3-legged-accessToken
	 * @param amount			要充值 的世宇币数目
	 * @param consumer_key		要充值 的游戏key
	 * @param game_center_key 	要充值的游场key
	 * @param rate 				充值游戏代币比率
	 * @return
	 */
	public BaseReturnValue<ExchangeReturn> accountExchange(Token accessToken, Integer amount, String consumer_key, String game_center_key, Integer rate){
		String apiPath = walo3LeggedService.apiFormat(Walo3LeggedApi.ACCOUNT_EXCHANGE);
		OAuthRequest request = new OAuthRequest(Verb.PUT, apiPath);
		request.addBodyParameter("amount", amount+"");
		request.addBodyParameter("consumer_key", consumer_key);
		request.addBodyParameter("game_center_key", game_center_key);
		request.addBodyParameter("rate", rate+"");
		return JsonUtils.fromJson(walo3LeggedService.requetJsonBodyForWalo3LeggedApiByOAuthRequest(accessToken, request), new TypeReference<BaseReturnValue<ExchangeReturn>>(){});
	}
	
	/**
	 * 世宇币充值记录，3-legged	
	 * @param accessToken 	3-legged-accessToken
	 * @param pg			页数
	 * @param rpp			每页条数
	 * @return
	 */
	public BaseReturnValue<Page<SycoinRechargeRecord>> sycoinRechargeReport(Token accessToken, Integer pg, Integer rpp){
		return JsonUtils.fromJson(walo3LeggedService.requetJsonBodyForWalo3LeggedApi(accessToken, Walo3LeggedApi.SYCOIN_RECHARGE_REPORT,String.valueOf(pg), String.valueOf(rpp)), new TypeReference<BaseReturnValue<Page<SycoinRechargeRecord>>>(){});
	}
	
	/**
	 * 游戏币充值记录，3-legged	
	 * @param accessToken 	3-legged-accessToken
	 * @param pg			页数
	 * @param rpp			每页条数
	 * @return
	 */
	public BaseReturnValue<Page<TokensRechargeRecordNew>> tokensRechargeReport(Token accessToken, Integer pg, Integer rpp){
		return JsonUtils.fromJson(walo3LeggedService.requetJsonBodyForWalo3LeggedApi(accessToken, Walo3LeggedApi.TOKENS_RECHARGE_REPORT,String.valueOf(pg), String.valueOf(rpp)), new TypeReference<BaseReturnValue<Page<TokensRechargeRecordNew>>>(){});
	}
	
	/**
	 * 世宇币兑换记录，3-legged	
	 * @param accessToken 	3-legged-accessToken
	 * @param pg			页数
	 * @param rpp			每页条数
	 * @return
	 */
	public BaseReturnValue<Page<SycoinExchangeRecord>> sycoinExchangeReport(Token accessToken, Integer pg, Integer rpp){
		return JsonUtils.fromJson(walo3LeggedService.requetJsonBodyForWalo3LeggedApi(accessToken, Walo3LeggedApi.SYCOIN_EXCHANGE_REPORT,String.valueOf(pg), String.valueOf(rpp)), new TypeReference<BaseReturnValue<Page<SycoinExchangeRecord>>>(){});
	}
	
	/**
	 * 世宇积分 扣减, 3-legged
	 * @param accessToken	3-legged-accessToken
	 * @param amount   		要扣减的世宇积分数目   
	 * @return BaseReturnValue<AchievementPaymentReturns> 
	 * 						err_num=0时为成功，err_num=402时为积分不够用于扣减
	 * 						成功后AchievementPaymentReturns里的tx_code用于退款使用，请保存到数据库 			
	 */
	public BaseReturnValue<AchievementPaymentReturns> achievementTxPayment(Token accessToken, Integer amount){
		String apiPath = walo3LeggedService.apiFormat(Walo3LeggedApi.ACHIEVEMENT_TX_PAYMENT);
		OAuthRequest request = new OAuthRequest(Verb.PUT, apiPath);
		request.addBodyParameter("amount", amount+"");
		return JsonUtils.fromJson(walo3LeggedService.requetJsonBodyForWalo3LeggedApiByOAuthRequest(accessToken, request), new TypeReference<BaseReturnValue<AchievementPaymentReturns>>(){}) ;
	}
	
	/**
	 * 回退已扣减的 世宇积分，用于交易不成功时的退款, 3-legged
	 * @param accessToken
	 * @param tx_code
	 * @return BaseReturnValue<AchievementPaymentReturns> 
	 * 						err_num=0   时为成功回退，
	 * 						err_num=404 时为没找到对应的tx_code的扣减记录, 
	 *                      err_num=410 时为该tx_code已经被回退过了	
	 */
	public BaseReturnValue<AchievementPaymentRollBackReturns> achievementTxPaymentRollBack(Token accessToken, String tx_code){
		String apiPath = walo3LeggedService.apiFormat(Walo3LeggedApi.ACHIEVEMENT_TX_PAYMENT_ROLLBACK);
		OAuthRequest request = new OAuthRequest(Verb.PUT, apiPath);
		request.addBodyParameter("tx_code", tx_code);
		return JsonUtils.fromJson(walo3LeggedService.requetJsonBodyForWalo3LeggedApiByOAuthRequest(accessToken, request), new TypeReference<BaseReturnValue<AchievementPaymentRollBackReturns>>(){}) ;
	}
	
	
	/**
	 * 验证 一次性登录密码，并返回accessToken(需要用AES解密，密码IV与充值使用的一样), 2-legged
	 * 
	 * @param user_key	用户key
	 * @param ls		一次性登录密码
	 * @return	
	 */
	public BaseReturnValue<String> getAccessTokenForSSO(String user_key, String ls){
		String apiPath = walo2LeggedService.apiFormat(Walo2LeggedApi.WALO_SSO_VERIFY_URL, user_key);
		System.out.println("apiPath:"+apiPath);
		OAuthRequest request = new OAuthRequest(Verb.POST, apiPath);
		request.addBodyParameter("ls", ls);		
		return JsonUtils.fromJson(walo2LeggedService.requetJsonBodyForWalo2LeggedApiByOAuthRequest(request), new TypeReference<BaseReturnValue<String>>(){});
	}
	
	/**
	 * test json string
	 */
	public String testJsonString(){
		//return walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.USER_DETAIL_INFO,"1215410ebc20ec9a6a00f8290c350d96");
		//"07e8b92607d1215777db2957707307c1","a449623b427d90567555c972dfb77344","36f9d1dd97","df9fb1f860"
		//return walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.ARCADE_MACHINES_BY_GAME_CENTER_KEY_JSON_CENTER,"a449623b427d90567555c972dfb77344","1","20");
		//return walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GET_USER_LOGIN_COUNT,"07e8b92607d1215777db2957707307c1","a449623b427d90567555c972dfb77344","df9fb1f860");
		//return walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GAME_DAILY_TX_REPORT_SELECTED_GAME,"7e8621ac566943e4f3f9464859558d32","2013-10-01","2013-11-28","df9fb1f860");
		//return walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.USER_WALLETS,"1215410ebc20ec9a6a00f8290c350d96");
		//return walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.USER_PAGE_LIST,"1","30");
		//return walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GET_CHARGE_GAME_LIST_BY_USER_KEY,"1215410ebc20ec9a6a00f8290c350d96");			
		//return walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GET_ALL_APP);			
		//return walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GET_SYCOIN_BY_USER_KEY,"1215410ebc20ec9a6a00f8290c350d96");	
		//return walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GET_ALL_GAME_CENTER);
		//return walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GET_USER_GAME_WALLETS,"1215410ebc20ec9a6a00f8290c350d96","36f9d1dd97");	
		//return walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GET_USER_GAME_WALLET,"1215410ebc20ec9a6a00f8290c350d96","36f9d1dd97","7e8621ac566943e4f3f9464859558d32");	
		//return walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GET_USER_GAME_CENTER_TICKETS,"1215410ebc20ec9a6a00f8290c350d96","a449623b427d90567555c972dfb77344");	
		return walo2LeggedService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GET_CONSUMER_DETAIL,"36f9d1dd97");	
	}
	

	
}
