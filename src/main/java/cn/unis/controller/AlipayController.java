package cn.unis.controller;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.scribe.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.util.Encrypt;
import cn.ruiyi.base.util.PathUtil;
import cn.unis.dao.AlipayDiscountDao;
import cn.unis.entity.AlipayDiscount;
import cn.unis.entity.AlipayRecord;
import cn.unis.service.impl.GameWalletRateService;
import cn.unis.service.interfaces.IAlipayRecordService;
import cn.unis.service.interfaces.IGameCenterService;
import cn.unis.transit.AlipayRequestDto;
import cn.unis.transit.BaseReturnValue;
import cn.unis.transit.BaseReturnValues;
import cn.unis.transit.ChargeToSycoin;
import cn.unis.transit.Consumer;
import cn.unis.transit.GameAndGameCenter;
import cn.unis.transit.GameCenter;
import cn.unis.transit.GameCenterSimpleInfo;
import cn.unis.transit.UserDetailInfo;
import cn.unis.transit.UserGameWallet;
import cn.unis.transit.token.ChargeToToken;
import cn.unis.transit.token.TokenBalance;
import cn.unis.transit.userWallet.Sycoin;
import cn.unis.utils.JsonUtils;
import cn.unis.utils.MessyCodeCheck;
import cn.unis.utils.ThreadSafeData;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import com.google.common.collect.Lists;

/**
 * 支付宝
 *
 * @author fanzz
 *
 */
@Controller
@RequestMapping(value = "/frontEnd/alipay/")
public class AlipayController {

	private static Logger logger =LoggerFactory.getLogger(AlipayController.class);
	@Autowired
	private AlipayDiscountDao alipayDiscountDao;
	@Autowired
	private IAlipayRecordService iAlipayRecordService;
	@Autowired
	private IGameCenterService iGameCenterService;
	@Autowired
	private GameWalletRateService gameWalletRateService;

	@RequestMapping(value="agreement",method=RequestMethod.GET)
	public String agreement(HttpServletRequest request ){
		logger.info("agreement request id ==============================>" + request.getSession().getId());
		return "/unis/frontEnd/alipay/agreement";
	}

	@RequestMapping(value="successful",method=RequestMethod.GET)
	public String successful(String outTradeNo,Model model){
		AlipayRecord alipayRecord = iAlipayRecordService.findByOutTradeNo(outTradeNo);
		model.addAttribute("payMoney", alipayRecord.getPayMoney());
		model.addAttribute("outTradeNo", outTradeNo);
		model.addAttribute("usename", alipayRecord.getUsername());
		return "/unis/frontEnd/alipay/successful";
	}
	@RequestMapping(value="unsuccessful",method=RequestMethod.GET)
	public String unsuccessful(String outTradeNo,Model model){
		model.addAttribute("outTradeNo", outTradeNo);
		return "/unis/frontEnd/alipay/unsuccessful";
	}
	/**
	 * 数据库没有记录则为1，否则取数据库值
	 * @return
	 */
	public float getDiscount(){
		AlipayDiscount alipayDiscount = alipayDiscountDao.findByDate(new Date());
		return alipayDiscount == null  ? 1 : alipayDiscount.getDiscount();
	}
	/**
	 * 世宇币充值页面
	 * @param request
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value="sycoin",method={RequestMethod.POST,RequestMethod.GET})
	public String sycoin(HttpServletRequest request ,Model model,String flag){
		logger.info("sycoin request id ==============================>" + request.getSession().getId());
		AlipayRequestDto entity = getRedirectEntity(flag);
		setUserName(request,entity);
		model.addAttribute("entity", entity);
		return "/unis/frontEnd/alipay/sycoin";
	}
	/**
	 * 游戏币充值页面
	 * @param request
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value="token",method={RequestMethod.POST,RequestMethod.GET})
	public String token(HttpServletRequest request , Model model,String flag){
		logger.info("sycoin request id ==============================>" + request.getSession().getId());
		AlipayRequestDto entity = getRedirectEntity(flag);
		setUserName(request, entity);

//		if(StringUtils.isBlank(entity.getGameOptionHtml())){
//			List<GameAndGameCenter> gameAndGameCenterList  = getGameAndGameCenterList(entity.getUsername());
//			String gameOptionHtml = "";
//			for(GameAndGameCenter tmp : gameAndGameCenterList){
//				if(tmp.getGameKey().equals(entity.getGameSelect())){
//					gameOptionHtml = gameOptionHtml + "<option selected value=\"" + tmp.getGameKey() + "\">" + tmp.getGameName() + "</option>";
//				}else{
//					gameOptionHtml = gameOptionHtml + "<option value=\"" + tmp.getGameKey() + "\">" + tmp.getGameName() + "</option>";
//				}
//			}
//			entity.setGameSelectTmpDataJson(checkAccountExsitOrNot(entity.getUsername()));
//			entity.setGameOptionHtml(gameOptionHtml);
//		}

		model.addAttribute("entity", entity);
		return "/unis/frontEnd/alipay/newtoken";
	}
	/**
	 * 重定向后的实体内容
	 * @param flag
	 * @return
	 */
	private AlipayRequestDto getRedirectEntity(String flag){
		AlipayRequestDto entity = null;
		if(flag == null){
			entity =  new AlipayRequestDto();
		}else{
			entity = (AlipayRequestDto)ThreadSafeData.data.get(flag);
			if(entity==null){
				entity = new AlipayRequestDto();
			}else{
				ThreadSafeData.data.remove(flag);
			}
		}
		entity.setDiscount(getDiscount());
		return entity;
	}

	@RequestMapping(value="check",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String checkAccountExsitOrNot(String account){
		List<GameAndGameCenter> gameAndGameCenterList = getGameAndGameCenterList(account);
		return  JsonUtils.toJson(gameAndGameCenterList);
	}

	@RequestMapping(value="rate",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String rate(String consumerKey, String gameCenterKey){
		return JsonUtils.toJson(gameWalletRateService.getRateByConsumerKeyAndGameCenterKey(consumerKey,gameCenterKey));
	}

	private void setUserName(HttpServletRequest request ,AlipayRequestDto entity){
		String userName = getLoginUserName(request);
		if(StringUtils.isBlank(entity.getUsername()) && StringUtils.isBlank(entity.getConfirmusername())){
			entity.setUsername(userName);
			entity.setConfirmusername(userName);
		}
	}


	public List<GameAndGameCenter> getGameAndGameCenterList(String account){
		BaseReturnValues<Consumer> consumer = getConsumer(account);
		List<GameAndGameCenter> gameAndGameCenterList = Lists.newArrayList();
		String userKey = checkUsername(account);
//		if(userKey!=null){
//			for(Consumer tmp : consumer.getMsg()){
//				GameAndGameCenter gameAndGameCenter = new GameAndGameCenter();
//				gameAndGameCenter.setGameKey(tmp.getKey());
//				gameAndGameCenter.setGameName(tmp.getName());
//				BaseReturnValues<UserGameWallet> userGameWallet = iGameCenterService.getUserGameWalletListByUserKeyAndConsumerKey(userKey,tmp.getKey());
//				for(int i=0;i<userGameWallet.getMsg().size();i++){
//					GameCenterSimpleInfo gameCenterSimpleInfo = userGameWallet.getMsg().get(i).getGame_center();
//					GameCenter gameCenter = new GameCenter();
//					gameCenter.setGameCenterKey(gameCenterSimpleInfo.getKey());
//					gameCenter.setGameCenterName(gameCenterSimpleInfo.getName());
//					gameCenter.setChangeRate(gameWalletRateService.getRateByConsumerKeyAndGameCenterKey(tmp.getKey(),gameCenterSimpleInfo.getKey()));
//					gameAndGameCenter.getGameCenterList().add(gameCenter);
//				}
//				gameAndGameCenterList.add(gameAndGameCenter);
//			}
//		}
		return gameAndGameCenterList;
	}

	private BaseReturnValues<Consumer> getConsumer(String account){
		if(StringUtils.isNotBlank(account)){
			String userKey = checkUsername(account);
			if (userKey!=null) {
				BaseReturnValue<UserDetailInfo> userBaseReturnValue = iGameCenterService
						.getUserDetailInfoByUserKey(userKey, "status");
				if (userBaseReturnValue != null) {
					if (userBaseReturnValue.getMsg().getStatus() == 1) {
						BaseReturnValues<Consumer> consumer = iGameCenterService.getChargeableConsumerListByUserKey(userKey);
						return consumer;
					}
				}
			}
		}
		return null;
	}
	/**
	 *
	 * @param username
	 * @return userKey
	 */
	private String checkUsername(String username){
		if(StringUtils.isNotBlank(username)){
			BaseReturnValues<String> user = iGameCenterService.queryUserList(1,30, "username", username);
			if(user != null && user.getMsg()!=null && user.getMsg().size()>0){
				return user.getMsg().get(0);
			}
		}
		return null;
	}

	/**
	 * 前台通过post方式提交必要参数
	 */
	@RequestMapping(value = "request", method = RequestMethod.POST)
	public String sendRequest2Alipay(AlipayRequestDto entity , HttpServletRequest request,HttpServletResponse response,Model model) throws Exception {
		//存在服务器的验证码
		String captcha = (String) SecurityUtils.getSubject().getSession().getAttribute(Constants.ALIPAY_KEY_CAPTCHA);
		//用户key
		String userKey = checkUsername( entity.getUsername());
		BaseReturnValue<Sycoin> sycoin  = null;
		int userStatus = 0;
		String userGrade = "";
		if(StringUtils.isNotBlank(userKey)){
			BaseReturnValue<UserDetailInfo> userDetailInfo = iGameCenterService.getUserDetailInfoByUserKey(userKey, "status,grade");
			if(userDetailInfo!=null){
				userStatus = userDetailInfo.getMsg().getStatus();
				userGrade = userDetailInfo.getMsg().getGrade();
			}
			sycoin = iGameCenterService.getSycoinByUserKey(userKey);
		}

		boolean captchaFlag = !entity.getCaptcha().equalsIgnoreCase(captcha);//验证码
		boolean usernameFlag = (StringUtils.isBlank(entity.getUsername())
				|| StringUtils.isBlank(entity.getConfirmusername())
				|| !entity.getUsername().equals(entity.getConfirmusername()));//帐号
		boolean userStatusFlag = userKey==null || userStatus!=1 ;//用户状态
		boolean userGradeFlag = userGrade==null || userGrade.equals("") || userGrade.equals("p");
		boolean mount = ( entity.getSycoin() <= 0 && entity.getToken() <=0 );//充值数量（两个都少于0，基本验证前台）

		int userErrorReturnFlag = 0;
		if(usernameFlag){
			userErrorReturnFlag = 1;
		}
		if(userStatusFlag){
			userErrorReturnFlag = 2;
		}
		if(userGradeFlag){
			userErrorReturnFlag = 3;
		}

		Integer rate = gameWalletRateService.getRateByConsumerKeyAndGameCenterKey(entity.getGameSelect(),entity.getGameCenterSelect());

		boolean allChecked = true;
		if(entity.getRechargetype().equals("sycoin")){
			boolean sycoinStatusFlag = !(sycoin!=null&&sycoin.getMsg().getStatus()==1);//世宇钱包状态
			allChecked = (captchaFlag || usernameFlag || userStatusFlag || userGradeFlag || mount || sycoinStatusFlag  );
		}else if(entity.getRechargetype().equals("token")){
//			boolean gameOrGameCenterFlag = entity.getToken() > 0 && (StringUtils.isBlank(entity.getGameSelect()) || StringUtils.isBlank(entity.getGameCenterSelect()));//游戏&游乐场选择
//			BaseReturnValue<GameCenterDetailInfo> gGaBaseReturnValue = iGameCenterService.getGameCenter(entity.getGameCenterSelect());
//			boolean gameCenterStatusFlag = gGaBaseReturnValue.getMsg().getStatus()!=1 ;//游乐场状态
//			BaseReturnValue<Consumer> cBaseReturnValue = iGameCenterService.getConsumerDetail(entity.getGameSelect());
//			boolean gameStatusFlag = cBaseReturnValue.getMsg().getStatus()!=1;
//			BaseReturnValue<UserGameWallet> uBaseReturnValue = iGameCenterService.getUserGameWallet(userKey, entity.getGameSelect(), entity.getGameCenterSelect());
//			boolean tokenStatusFlag = uBaseReturnValue.getMsg().getStatus()!=1;//游戏钱包状态
//			allChecked = (captchaFlag || usernameFlag || gameOrGameCenterFlag || userStatusFlag || userGradeFlag || mount || gameStatusFlag || gameCenterStatusFlag || tokenStatusFlag);
			BaseReturnValue<TokenBalance> tBaseReturnValue = iGameCenterService.getTokenBalanceByUserKey(userKey);
			boolean tokenStatusFlag = tBaseReturnValue.getMsg().getStatus()!=1;
			allChecked = (captchaFlag || usernameFlag || userStatusFlag || mount || userGradeFlag || tokenStatusFlag);
		}


		if(allChecked){
			String isCheckedTmp = request.getParameter("czMountRadio");
			int agreement = request.getParameter("agreement").equals("on") ? 1 : 0;
			entity.setIsChecked(StringUtils.isBlank(isCheckedTmp) ? 0 : Integer.parseInt(isCheckedTmp));
			entity.setIsAgree(agreement);
			if(StringUtils.isBlank(entity.getBankselect())){
				entity.setBankselect("no");
			}
			entity.setGameSelectTmpDataJson(checkAccountExsitOrNot(entity.getUsername()));

			String redirectPath ="redirect:/frontEnd/index";
			if(entity.getRechargetype().equals("sycoin")){
				redirectPath = "redirect:/frontEnd/alipay/sycoin";

			}else if(entity.getRechargetype().equals("token")){
//				BaseReturnValues<Consumer> consumer = getConsumer(entity.getUsername());
//				if(consumer!=null&&consumer.getMsg().size() > 0){
//					String gameOptionHtml = "";
//					for(Consumer tmp : consumer.getMsg()){
//						if(tmp.getKey().equals(entity.getGameSelect())){
//							gameOptionHtml = gameOptionHtml + "<option selected value=\"" + tmp.getKey() + "\">" + tmp.getName() + "</option>";
//						}else{
//							gameOptionHtml = gameOptionHtml + "<option value=\"" + tmp.getKey() + "\">" + tmp.getName() + "</option>";
//						}
//					}
//					entity.setGameOptionHtml(gameOptionHtml);
//				}
				redirectPath = "redirect:/frontEnd/alipay/token";
			}

			entity.setUserErrorReturnFlag(userErrorReturnFlag);
			entity.setCaptchaFlag(captchaFlag);
			entity.setUsernameFlag(usernameFlag);
			String requestID = request.getSession().getId();
			ThreadSafeData.data.put(requestID, entity);
			model.addAttribute("flag", requestID);
			return redirectPath;
		}


		//清除session中验证，防止出现“浏览器后退验证码没有过期”
		SecurityUtils.getSubject().getSession().removeAttribute(Constants.ALIPAY_KEY_CAPTCHA);

		// 支付类型
		String payment_type = "1";
		// 必填，不能修改
		// 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数
		String notify_url = AlipayConfig.notify_url;

		// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
		String return_url = AlipayConfig.return_url;

		// 卖家支付宝帐户 必填
		String seller_email = AlipayConfig.seller_email;

		// 商户订单号 商户网站订单系统中唯一订单号，必填
		String out_trade_no = UUID.randomUUID().toString();//TODO 随机唯一

		// 订单名称 必填
		String subject = "游币/实体币充值。";

		// 付款金额 必填
		float discount = getDiscount();
		float num = entity.getSycoin() * getDiscount();
		if(entity.getRechargetype().equals("sycoin")){
			num = entity.getSycoin() * getDiscount();
		}else if(entity.getRechargetype().equals("token")){
			num = entity.getToken()  * getDiscount();
		}
		String testOrNot = PathUtil.getConfigResource("test_or_not");
		if(testOrNot.equals("true")){
			num = 0.01f;//test
		}
		DecimalFormat fmt = new DecimalFormat("#.##");
		String total_fee = fmt.format(num);//充值金币数量 x 折扣

		// 订单描述
		String body = "世宇币/游戏币充值。";

		// 商品展示地址 需以http://开头的完整路径，例如：http://www.xxx.com/myorder.html
		String show_url = "http://localhost:8080/frontEnd/alipay/index";

		// 防钓鱼时间戳 若要使用请调用类文件submit中的query_timestamp函数
		String anti_phishing_key = AlipaySubmit.query_timestamp();

		// 客户端的IP地址 非局域网的外网IP地址，如：221.0.0.1
		String exter_invoke_ip = request.getRemoteAddr();

		// ////////////////////////////////////////////////////////////////////////////////

		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("seller_email", seller_email);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("body", body);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("anti_phishing_key", anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", exter_invoke_ip);


		//默认支付方式
		String paymethod = "bankPay";
		//必填
		//默认网银
		String defaultbank = request.getParameter("bankselect");
		//必填，银行简码请参考接口技术文档

		String paytype = request.getParameter("optionsPay");
		if(paytype.equals("bankpay")){
			sParaTemp.put("paymethod", paymethod);
			sParaTemp.put("defaultbank", defaultbank);
		}
		// 建立请求

		/**
		 * 记录充值记录
		 */
		AlipayRecord alipayRecord = new AlipayRecord();

		byte payTypeTmp = (byte)-1;

		String sign = "";
		if(paytype.equals("alipay")){
			payTypeTmp = IAlipayRecordService.ALIPAY ;
		}else if(paytype.equals("bankpay")){
			payTypeTmp = IAlipayRecordService.BANKPAY ;
		}


		Date date = new Date();

		alipayRecord.setUsername(entity.getUsername());//充值用户
		alipayRecord.setUserkey(userKey);//用户key
		alipayRecord.setSycoin(entity.getSycoin());//世宇币
		alipayRecord.setToken(entity.getToken());//游戏币
		alipayRecord.setPayMoney(Float.parseFloat(total_fee));//应付费金额
		alipayRecord.setPayType(payTypeTmp);//支付类型
		alipayRecord.setStatus(IAlipayRecordService.UNHANDLE);//充值状态
		alipayRecord.setOutTradeNo(out_trade_no);//订单号

		alipayRecord.setDate(date);//日期
		alipayRecord.setGameKey(entity.getGameSelect());
		alipayRecord.setGameCenterKey(entity.getGameCenterSelect());
		alipayRecord.setDiscount(discount);
		alipayRecord.setChangeRate(rate);

		int walletType = -1;
		if(entity.getRechargetype().equals("sycoin")){
			walletType = 0;
			sign = Encrypt.MD5(alipayRecord.getOutTradeNo() + alipayRecord.getUserkey() +  alipayRecord.getSycoin());
		}else if(entity.getRechargetype().equals("token")){
			walletType = 1;
//			sign = Encrypt.MD5(alipayRecord.getOutTradeNo() + alipayRecord.getUserkey()  + alipayRecord.getGameKey() + alipayRecord.getGameCenterKey() + alipayRecord.getToken());
			sign = Encrypt.MD5(alipayRecord.getOutTradeNo() + alipayRecord.getUserkey()  + alipayRecord.getToken());
		}
		alipayRecord.setWalletType(walletType);
		alipayRecord.setSign(sign);//签名

		iAlipayRecordService.saveAndFlush(alipayRecord);

		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
		model.addAttribute("sHtmlText", sHtmlText);

		return "/unis/frontEnd/alipay/transit";
	}

	@RequestMapping(value = "transit", method = RequestMethod.GET)
	public String transit(String url,Model model){
		model.addAttribute("url", url);
		return "/unis/frontEnd/alipay/transit";
	}

	/**
	 * 支付宝回调,指定GET同步请求
	 */
	@RequestMapping(value = "callBackBySynchronous", method = RequestMethod.GET)
	public String callBackBySynchronous(HttpServletRequest request,Model model) throws Exception {
		logger.info("=========================>支付宝回调 同步<=========================");
		int flag = process(request, model,"post");
		return flag == 1 ? "redirect:/frontEnd/alipay/successful" : "redirect:/frontEnd/alipay/unsuccessful";
	}

	/**
	 * 支付宝回调,指定POST异步请求
	 */
	@RequestMapping(value = "callBackByAsynchronous", method = RequestMethod.POST)
	@ResponseBody
	public String callBackByAsynchronous(HttpServletRequest request,Model model) throws Exception {
		logger.info("=========================>支付宝回调 异步<=========================");
		int flag = process(request, model,"post");
		return flag != -1 ? "success" : "fail";
	}
	/**
	 *
	 * @param request
	 * @param model
	 * @param requestMethod post / get
	 * @return -1支付宝回调未成功</br>0成功更改状态 </br>1处理成功 </br>2处理失败
	 * @throws Exception
	 */
	private int  process(HttpServletRequest request,Model model,String requestMethod) throws Exception{
		// 获取支付宝GET过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		@SuppressWarnings("rawtypes")
		Map requestParams = request.getParameterMap();
		for (@SuppressWarnings("rawtypes")
		Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			if(requestMethod.equalsIgnoreCase("post") ){
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			}else if(requestMethod.equalsIgnoreCase("get")){
				if(MessyCodeCheck.isMessyCode(valueStr)){
					valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				}
			}
			params.put(name, valueStr);
		}

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		// 商户订单号

		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

		// 支付宝交易号

		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

		// 交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

		logger.info("=========================> 回调时间：" + new Date() + "<=======================");
		logger.info("=========================> 支付宝交易号：" + trade_no + "<=======================");
		logger.info("=========================> 交易状态 ： " + trade_status + "<=======================");
		logger.info("=========================> 商户订单号 ：" + out_trade_no + "<=======================");

		model.addAttribute("outTradeNo", out_trade_no);
		AlipayRecord temp = iAlipayRecordService.findByOutTradeNo(out_trade_no);
		temp.setAlipayTradeNo(trade_no);
		model.addAttribute("usename", temp.getUsername());
		model.addAttribute("payMoney", temp.getPayMoney());
		iAlipayRecordService.saveAndFlush(temp);

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

		// 计算得出通知验证结果(key泄漏 回调会被伪造)
		int returnFlag = -1 ;
		boolean verify_result = AlipayNotify.verify(params);
		logger.info("alipay callback verify_result=================================>" + verify_result );
		if (verify_result) {// 验证成功
			// ////////////////////////////////////////////////////////////////////////////////////////
			// 请在这里加上商户的业务逻辑程序代码

			// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
				returnFlag = 0;
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序
				int updateRow = iAlipayRecordService.saveAndReturnUpdateRows(IAlipayRecordService.UNHANDLE, IAlipayRecordService.ALIPAY_SUCCESS, out_trade_no);
				logger.info("update AlipayRecord status updateRow==================================>" + updateRow );
				if(updateRow > 0){
						AlipayRecord tmp = iAlipayRecordService.findByOutTradeNo(out_trade_no);
						int SYCOIN = 0;
						int TOKEN = 1;
						BaseReturnValue<ChargeToSycoin> chargeToSycoin = null;
						BaseReturnValue<ChargeToToken> chargeToToken = null;
						if(tmp.getWalletType() == SYCOIN){
							chargeToSycoin = iAlipayRecordService.requestSycoinToWalo(out_trade_no);
						}else if(tmp.getWalletType() == TOKEN){
							chargeToToken = iAlipayRecordService.requestTokenToWalo(out_trade_no);
						}

						if((chargeToSycoin!=null && chargeToSycoin.getErr_num()==0) ||(chargeToToken !=null && chargeToToken.getErr_num() == 0)){
							AlipayRecord alipayRecord = iAlipayRecordService.findByOutTradeNo(out_trade_no);
							alipayRecord.setStatus(IAlipayRecordService.HANDLE);
							iAlipayRecordService.saveAndFlush(alipayRecord);//处理成功
							returnFlag = 1;
						}else{
							returnFlag = 2;//walo 验证失败
						}
				}
				else{
					AlipayRecord alipayRecord = iAlipayRecordService.findByOutTradeNo(out_trade_no);
					if(alipayRecord.getStatus() == IAlipayRecordService.HANDLE){
						returnFlag = 1;
					}else{
						logger.info("update AlipayRecord error==================================>" + out_trade_no );
						returnFlag = 2;
					}
				}
			}else{
				returnFlag = 2;
			}

		} else { //验证失败,可能包括post回调成功，get再回调
			AlipayRecord alipayRecord = iAlipayRecordService.findByOutTradeNo(out_trade_no);
			if(alipayRecord.getStatus() == IAlipayRecordService.HANDLE){
				logger.info("requestMethod: " + requestMethod + "alipay process returnFlag = 1");
				returnFlag = 1;
			}else if(alipayRecord.getStatus() == IAlipayRecordService.UNHANDLE){
				logger.info("requestMethod: " + requestMethod + "alipay process returnFlag = -1");
				returnFlag = -1;
			}else{
				logger.info("requestMethod: " + requestMethod + "alipay process returnFlag = 2");
				returnFlag = 2;
			}
		}
		return returnFlag;
	}

	/**
	 * 验证是否有记录
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "verifyBySign", method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String verifyBySign(String sign){
		logger.info("walo request to us verify by sign=========================>" + sign);
		String flag = null;
		int updateRows = iAlipayRecordService.saveAndReturnUpdateRowsBySign(IAlipayRecordService.ALIPAY_SUCCESS, IAlipayRecordService.SEND, sign);
		flag =  updateRows > 0 ?  "success" : "error";
		logger.info("walo request to us verify result==========================>" + flag);
		return flag;
	}
	/**
	 * 获取用户登录名
	 * @param request
	 * @return
	 */
	public String getLoginUserName(HttpServletRequest request){
		Token accessToken = (Token)request.getSession().getAttribute("accessToken");
		String userName = "";
		if(accessToken!=null){
			BaseReturnValue<UserDetailInfo> userDetailInfo = iGameCenterService.getAccountInfo(accessToken);
			if(userDetailInfo!=null && userDetailInfo.getErr_num()!=512 && userDetailInfo.getErr_num()!=256){
				 userName = iGameCenterService.getAccountInfo(accessToken).getMsg().getUsername();
				 if(userName == null){
					 userName = "";
				 }
			}
		}
		return userName;
	}
}
