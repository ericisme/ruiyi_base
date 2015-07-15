package cn.unis.service.interfaces;

import java.util.List;

import org.scribe.model.Token;

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
import cn.unis.transit.achievement.AchievementPaymentReturns;
import cn.unis.transit.achievement.AchievementPaymentRollBackReturns;
import cn.unis.transit.chargeRecord.Page;
import cn.unis.transit.chargeRecord.SycoinExchangeRecord;
import cn.unis.transit.chargeRecord.SycoinRechargeRecord;
import cn.unis.transit.chargeRecord.TokensRechargeRecord;
import cn.unis.transit.chargeRecord.TokensRechargeRecordNew;

/**
 * walo游戏平台Oauth服务 管理类.service接口
 * 
 * @author eric
 */
public interface IGameCenterService {

	
	/**
	 * 根据查询条件，查询值，获得会员kdy分页列表
	 * @param page_num
	 * @param item_per_page
	 * @param filter_key
	 * @param filter_value
	 * @return
	 */
	public BaseReturnValues<String> queryUserList(int page_num , int item_per_page, String filter_key, String filter_value );
	
	
	/**
	 * 获得全部会员kdy分页列表
	 * @param page_num
	 * @param item_per_page
	 * @return
	 */
	public BaseReturnValues<String> queryUserList(int page_num , int item_per_page);
	
	/**
	 * 根据user_key,获得user 实体信息,string
	 * @param user_key
	 * @param fields fields=key,username,handle_name,grade,email,phone,timezone,country,lang,personal_status,registered_arcades,achievement_score,accumulated_ascore,registered_at,expire,status
	 * @return
	 */
	public BaseReturnValue<UserDetailInfo> getUserDetailInfoByUserKey(String user_key, String fields);

	/**
	 * 用户在某一个游场上的 代币数 根彩票数，还有last_update的时间
	 * @param user_key
	 * @param game_center_key
	 * @return
	 */
	public BaseReturnValue<UserGameCenterWallet> getBalanceByUserKeyAndGameCenterKey(String user_key, String game_center_key);
	
	/**
	 * 根据game_center_key获得游戏列表
	 */
	public BaseReturnValues<WaloGameSimpleInfo> getWaloArcadeGamesByGameCenterKey(String game_center_key);
	
	/**
	 * 获得某个用户在某个游场，某个游戏的登录次数
	 */
	public BaseReturnValues<WaloGameSimpleInfo> getUserLoginCountByGameCenterKeyAndGameConsumerKey(String user_key, String game_center_key, String game_consumber_key);
	
	/**
	 *根据user_key,获得用户在各游场的钱包信息
	 */
	public BaseReturnValue<UserWallet> getUserWalletsByUserKey(String user_key);
	
	/**
	 *游场报表api,總派票/總投幣 (不同遊戲)
	 *直接返回json.
	 */
	public String reprotTxByGame(String game_center_key);
	
	/**
	 *游场报表api,指定日期(派票/投幣)(不同遊戲),指定游戏,日期格式 YYYY-MM-DD
	 *直接返回json.
	 */
	public String game_daily_tx_report(String game_center_key, String begin_date, String until_date, String consumer_key);
	/**
	 *游场报表api,指定日期(派票/投幣)(不同遊戲),         日期格式 YYYY-MM-DD
	 *直接返回json.
	 */
	public String game_daily_tx_report(String game_center_key, String begin_date, String until_date);
	
	/**
	 *個別機台派票/投币,         日期格式 YYYY-MM-DD
	 *直接返回json.
	 */
	public String arcade_daily_tx_report(String cuk, String begin_date, String until_date);
	
	
	
	/**
	 * 根据 gameConterKey 获得 游乐场 基本信息
	 * @param gameCenterKey
	 * @return
	 */
	public String getGameCenterJsonByGameCenterKey(String gameCenterKey);	
	
	/**
	 * 取得 分数 html
	 * @return
	 */
	public String getScoresHtml();	
		
	/**
	 * 根据 gameConterKey 获得 游乐场 详细信息
	 * @param gameCenterKey
	 */
	public BaseReturnValue<GameCenterDetailInfo> getGameCenter(String gameCenterKey);
	
	/**
	 * 根据 省代码 获得分页game_center列表
	 * 
	 * @param province_code
	 * @param page_num
	 * @param item_per_page
	 * @return
	 */
	public BaseReturnValues<GameCenterSimpleInfo> getGameCentersByProvinceCode(String province_code, int page_num, int item_per_page);
	
	/**
	 * 根据 市代码 获得分页game_center列表
	 * 
	 * @param province_code
	 * @param page_num
	 * @param item_per_page
	 * @return
	 */
	public BaseReturnValues<GameCenterSimpleInfo> getGameCentersByCityCode(String city_code, int page_num, int item_per_page);

	/**
	 * 根据 省代码 获得全部game_center列表
	 */
	public BaseReturnValues<GameCenterSimpleInfo> getGameCentersByProvinceCode(String province_code);
	
	/**
	 * 根据 市代码 获得全部game_center列表
	 */
	public BaseReturnValues<GameCenterSimpleInfo> getGameCentersByCityCode(String city_code);
	
	/**
	 * 根据game_center_key获得分页arcade列表
	 * 
	 * @param game_center_key
	 * @param page_num
	 * @param item_per_page
	 * @return
	 */
	public BaseReturnValues<Arcade> getArcadeMachinesOfGameCenter(String game_center_key, int page_num, int item_per_page);
	
	
	
	
	/**
	 * 获得所有walo app(app为consumer的上级)
	 */
	public BaseReturnValues<App> getAllApp();
	/**
	 * 获得所有充值consumer(游戏)列表
	 */
	public List<Consumer> getAllChargeableConsumer();
	/**
	 * 根据user_key获得consumer列表
	 */
	public BaseReturnValues<Consumer> getConsumersUserKey(String user_key);
	/**
	 * 根据user_key获得充值consumer(游戏)列表
	 */
	public BaseReturnValues<Consumer> getChargeableConsumerListByUserKey(String user_key);	
	
	/**
	 * 根据user_key查询用户的 游币钱包(以前叫世宇币)
	 */
	public BaseReturnValue<Sycoin> getSycoinByUserKey(String user_key);
	
	/**
	 * 根据user_key查询用户的 实体币钱包
	 */
	public BaseReturnValue<TokenBalance> getTokenBalanceByUserKey(String user_key);
	
	/**
	 * 获得所有game_center
	 */
	public BaseReturnValues<GameCenterSimpleInfo> getAllGameCenter();
	/**
	 * 根据user_key, consumer_key获得钱包列表
	 */
	public BaseReturnValues<UserGameWallet> getUserGameWalletListByUserKeyAndConsumerKey(String user_key, String consumer_key);
	/**
	 * 根据user_key, consumer_key, game_center_key获得游戏钱包
	 */
	public BaseReturnValue<UserGameWallet> getUserGameWallet(String user_key, String consumer_key, String game_center_key);
	/**
	 * 根据user_key,game_center_key,获得用户在某个游场的彩票结余
	 */
	public BaseReturnValue<UserGameCenterTicket> getTicketsByUserKeyAndGameCenterKey(String user_key, String game_center_key);
	
	/**
	 * 根据consumer_key获得consumer_detail(游戏详细)
	 */
	public BaseReturnValue<Consumer> getConsumerDetail(String consumer_key);
	
	/**
	 * 用户游币钱包(以前叫世宇币)充值
	 * @param user_key 要充值的用户key
	 * @param amount   要充值的数量，游币(以前叫世宇币)
	 * @param sign	加密签名，AES(json_data),json_data 例子：{"outTradeNo":"fsdfdsf88fhdsfhsda8fh3","date_time":"2013-12-12 10:10:10",payMoney":"100.05}
	 * @return
	 */
	public BaseReturnValue<ChargeToSycoin> chargeToSycoin(String user_key, Integer amount, String sign );
	
	/**
	 * 用户游戏钱包充值
	 * @param user_key 要充值的用户key
	 * @param consumer_key 要充值钱包的游戏key
	 * @param game_center_key 要充值钱包的游场key
	 * @param amount   要充值的数量，世宇币
	 * @param sign	加密签名，AES(json_data),json_data 例子：{"outTradeNo":"fsdfdsf88fhdsfhsda8fh3","date_time":"2013-12-12 10:10:10","payMoney":100.05}
	 * @return
	 */
	public BaseReturnValue<ChargeToSycoin> chargeToGameToken(String user_key, String consumer_key, String game_center_key, Integer amount, String sign );
	
	
	
	/**
	 * 用户实体币钱包充值
	 * @param user_key 要充值的用户key
	 * @param amount   要充值的数量，世宇币
	 * @param sign	加密签名，AES(json_data),json_data 例子：{"outTradeNo":"fsdfdsf88fhdsfhsda8fh3","date_time":"2013-12-12 10:10:10",payMoney":"100.05}
	 * @return
	 */
	public BaseReturnValue<ChargeToToken> chargeToToken(String user_key, Integer amount, String sign );
	
	
	
	
	
	
	
	/**
	 * 取得3-legged 的RequestToken
	 * @return
	 */
	public Token get3LeggedRequestToken(String callback_path);
	
	/**
	 * 取得3-legged 的AccessToken
	 * @param requestToken
	 * @param oauth_verifier
	 * @return
	 */
	public Token get3LeggedAccessToken(Token requestToken, String oauth_verifier);
	
	/**
	 * walo帐号注销/登出
	 * @param accessToken 帐号登录后的accessToken.
	 * @return
	 */
	public String logout3Legged(Token accessToken);
	
	/**
	 * 用户详细资料，3-legged
	 * @param accessToken 帐号登录后的accessToken.
	 */
	public BaseReturnValue<UserDetailInfo> getAccountInfo(Token accessToken);
	
	/**
	 * 用户彩票结余，3-legged
	 * @param accessToken 帐号登录后的accessToken.
	 */
	public BaseReturnValue<TicketBalance> getAccountTicketBalance(Token accessToken);
	
	/**
	 * 彩票 扣减, 3-legged
	 * @param accessToken	3-legged-accessToken
	 * @param amount   		要扣减的世宇彩票数目   
	 * @return BaseReturnValue<AchievementPaymentReturns> 
	 * 						err_num=0时为成功，err_num=402时为彩票不够用于扣减
	 * 						成功后TicketDebitRetuns里的tx_code用于退款使用，请保存到数据库 			
	 */
	public BaseReturnValue<TicketDebitReturns> ticketDebit(Token accessToken, Integer amount);
	
	/**
	 * 回退已扣减的 彩票，用于交易不成功时的退款, 3-legged
	 * @param accessToken
	 * @param tx_code
	 * @return BaseReturnValue<AchievementPaymentReturns> 
	 * 						err_num=0   时为成功回退，
	 * 						err_num=404 时为没找到对应的tx_code的扣减记录, 
	 *                      err_num=410 时为该tx_code已经被回退过了	
	 */
	public BaseReturnValue<TicketDebitRollBackReturns> ticketDebitRollBack(Token accessToken, String tx_code);
	
	/**
	 * 用户实体币结余，3-legged
	 */
	public BaseReturnValue<TokenBalance> getAccountTokenBalance(Token accessToken);
	
	/**
	 * 用户资料修，3-legged
	 * @param accessToken 帐号登录后的accessToken.
	 * @param edit_field  修改的字段，只允许修改handle_name,phone,personal_status
	 * @param edit_value  要修改字段的值
	 */
	public String editAccountProfile(Token accessToken, String edit_field, String edit_value, String original_handle_name);
	
	/**
	 * 用户世宇币，3-legged
	 */
	public BaseReturnValue<Sycoin> getSycoin(Token accessToken);
	/**
	 * 用户 世宇币,游场彩票，游戏钱包，3-legged
	 */
	public BaseReturnValue<UserWallet> getWallet(Token accessToken);
	
	/**
	 * 
	 *  用户游戏币充值，使用世宇币充值游戏币，3-legged	 
	 *  
	 * @param accessToken 		3-legged-accessToken
	 * @param amount			要充值 的世宇币数目
	 * @param consumer_key		要充值 的游戏key
	 * @param game_center_key 	要充值的游场key
	 * @param rate 				充值游戏代币比率
	 * @return
	 */
	public BaseReturnValue<ExchangeReturn> accountExchange(Token accessToken, Integer amount, String consumer_key, String game_center_key, Integer rate);
	
	/**
	 * 世宇币充值记录，3-legged	
	 * @param accessToken 	3-legged-accessToken
	 * @param pg			页数
	 * @param rpp			每页条数
	 * @return
	 */
	public BaseReturnValue<Page<SycoinRechargeRecord>> sycoinRechargeReport(Token accessToken, Integer pg, Integer rpp);
	
	/**
	 * 游戏币充值记录，3-legged	
	 * @param accessToken 	3-legged-accessToken
	 * @param pg			页数
	 * @param rpp			每页条数
	 * @return
	 */
	public BaseReturnValue<Page<TokensRechargeRecordNew>> tokensRechargeReport(Token accessToken, Integer pg, Integer rpp);
	
	/**
	 * 世宇币兑换记录，3-legged	
	 * @param accessToken 	3-legged-accessToken
	 * @param pg			页数
	 * @param rpp			每页条数
	 * @return
	 */
	public BaseReturnValue<Page<SycoinExchangeRecord>> sycoinExchangeReport(Token accessToken, Integer pg, Integer rpp);
	
	
	/**
	 * 世宇积分 扣减, 3-legged,已不用
	 * @param accessToken	3-legged-accessToken
	 * @param amount   		要扣减的世宇积分数目   
	 * @return BaseReturnValue<AchievementPaymentReturns> 
	 * 						err_num=0   时为成功，
	 * 						err_num=402 时为积分不够用于扣减,
	 * 						成功后AchievementPaymentReturns里的tx_code用于退款使用，请保存到数据库 			
	 */
	public BaseReturnValue<AchievementPaymentReturns> achievementTxPayment(Token accessToken, Integer amount);
	
	/**
	 * 回退已扣减的 世宇积分，用于交易不成功时的退款, 3-legged,已不用
	 * @param accessToken
	 * @param tx_code
	 * @return BaseReturnValue<AchievementPaymentReturns> 
	 * 						err_num=0   时为成功回退，
	 * 						err_num=404 时为没找到对应的tx_code的扣减记录, 
	 *                      err_num=410 时为该tx_code已经被回退过了	
	 */
	public BaseReturnValue<AchievementPaymentRollBackReturns> achievementTxPaymentRollBack(Token accessToken, String tx_code);
	
	
	/**
	 * 验证 一次性登录密码，并返回accessToken(需要用AES解密，密码IV与充值使用的一样)
	 * @param user_key	用户key
	 * @param ls		一次性登录密码
	 * @return	
	 */
	public BaseReturnValue<String> getAccessTokenForSSO(String user_key, String ls);
	
	/**
	 * test json string
	 */
	public String testJsonString();
	
}
