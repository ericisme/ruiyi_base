package cn.unis.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.scribe.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ruiyi.base.util.DateUtil;
import cn.ruiyi.base.util.PathUtil;
import cn.unis.entity.Commodity;
import cn.unis.entity.CommodityOrder;
import cn.unis.entity.CommodityOrderStatus;
import cn.unis.entity.DeliveryAddress;
import cn.unis.service.impl.CommodityOrderService;
import cn.unis.service.impl.CommodityService;
import cn.unis.service.impl.DeliveryAddressService;
import cn.unis.service.impl.GameWalletRateService;
import cn.unis.service.interfaces.IGameCenterService;
import cn.unis.transit.BaseReturnValue;
import cn.unis.transit.GameAndGameCenter;
import cn.unis.transit.GameCenter;
import cn.unis.transit.UserDetailInfo;
import cn.unis.transit.achievement.AchievementPaymentRollBackReturns;
import cn.unis.transit.chargeRecord.Page;
import cn.unis.transit.chargeRecord.SycoinExchangeRecord;
import cn.unis.transit.chargeRecord.SycoinRechargeRecord;
import cn.unis.transit.chargeRecord.TokensRechargeRecordNew;
import cn.unis.transit.exchange.ExchangeReturn;
import cn.unis.transit.ticket.TicketBalance;
import cn.unis.transit.ticket.TicketDebitRollBackReturns;
import cn.unis.transit.token.TokenBalance;
import cn.unis.transit.userWallet.UserWallet;
import cn.unis.utils.JsonUtils;
import cn.unis.utils.MD5;
import cn.unis.utils.MyPageRequest;

import com.google.common.collect.Maps;


/**
 *walo account center controller
 */
@SuppressWarnings("restriction")
@Controller
@RequestMapping(value = "/frontEnd")
public class WaloAccountController {	

	@Resource(name = "waloGameCenterServiceAdapter")
	private IGameCenterService iGamecenterService;
	@Autowired
	private AlipayController alipayController;
	@Autowired
	private GameWalletRateService gameWalletRateService;
	
	@Autowired
	private CommodityOrderService commodityOrderService;
	
	@Autowired
	private DeliveryAddressService deliveryAddressService;
	
	@Autowired
	private CommodityService commodityService;
	/**
	 * account info
	 */
	@RequestMapping(value = "/accountCenter/accountInfo")
	public String accountInfo(Model model, HttpServletRequest request) {
		//检查是否已经登录
		Token accessToken = (Token)request.getSession().getAttribute("accessToken");
		boolean loginOrNot = false;
		if(accessToken==null){
		}else if(iGamecenterService.getAccountInfo(accessToken).getErr_num()==512 || iGamecenterService.getAccountInfo(accessToken).getErr_num()==256){			
		}else{
			loginOrNot = true;
		}
		if(!loginOrNot){	
			return  "unis/frontEnd/accountCenter/reLogin";//new ModelAndView(new StringView("<script>window.location.href='/frontEnd/walo3LeggedLogin'</script>"));
		}
		//accountInfo
		BaseReturnValue<UserDetailInfo> userDetailInfo    = iGamecenterService.getAccountInfo(accessToken);		
		BaseReturnValue<TicketBalance>  userTicketBalance = iGamecenterService.getAccountTicketBalance(accessToken);	
		BaseReturnValue<TokenBalance>  userTokenBalance = iGamecenterService.getAccountTokenBalance(accessToken);
		model.addAttribute("user", userDetailInfo.getMsg());		
		model.addAttribute("ticket",userTicketBalance.getMsg());
		model.addAttribute("token",userTokenBalance.getMsg());
		model.addAttribute("sycoin", iGamecenterService.getSycoin(accessToken).getMsg());
		
		
//		//扣减世宇积分demo
//		BaseReturnValue<AchievementPaymentReturns> returns = iGamecenterService.achievementTxPayment(accessToken, 1);		
//		if(returns.getErr_num()==0){//扣减成功，返回tx_code(保存到数据库，用于退款的号码),和剩余积分数
//			System.out.println("tx_code:"+returns.getMsg().getTx_code());// tx_code: 请保存到数据库用于交易失败时回退扣减的积分
//			System.out.println("balance:"+returns.getMsg().getBalance());
//		}else if(returns.getErr_num()==402){//扣减不成功，积分数不够用于扣减
//			System.out.println("err_msg:"+returns.getErr_msg());			
//		}
//		
//		//回退世宇积分demo, 交易失败时需要回退扣减的积分
//		BaseReturnValue<AchievementPaymentRollBackReturns> returns2 = iGamecenterService.achievementTxPaymentRollBack(accessToken, "AD8bc0756d4158fdc0afa00742b21632ec");	
//		if(returns2.getErr_num()==0){//回退扣减世宇积分成功
//			System.out.println("balance2:"+returns2.getMsg().getBalance());
//		}else if(returns2.getErr_num()==404){//回退扣减世宇积分不成功，没找到对应的tx_code的扣减记录
//			System.out.println("err_msg:"+returns2.getErr_msg());		
//		}else if(returns2.getErr_num()==410){//回退扣减世宇积分不成功，该tx_code已经被回退过了
//			System.out.println("err_msg:"+returns2.getErr_msg());
//		}
		
		return "unis/frontEnd/accountCenter/info";
	}
	
	/**
	 * change account password 
	 */
	@RequestMapping(value = "/accountCenter/changePassord")
	public String changePassord(Model model, HttpServletRequest request) {
		//检查是否已经登录
		Token accessToken = (Token)request.getSession().getAttribute("accessToken");
		boolean loginOrNot = false;
		if(accessToken==null){
		}else if(iGamecenterService.getAccountInfo(accessToken).getErr_num()==512 || iGamecenterService.getAccountInfo(accessToken).getErr_num()==256){			
		}else{
			loginOrNot = true;
		}
		if(!loginOrNot){	
			return  "unis/frontEnd/accountCenter/reLogin";//new ModelAndView(new StringView("<script>window.location.href='/frontEnd/walo3LeggedLogin'</script>"));
		}
		//accountInfo
		BaseReturnValue<UserDetailInfo> userDetailInfo= iGamecenterService.getAccountInfo(accessToken);				
		String changePasswordPath = PathUtil.getConfigResource("WALO_WAOL_PATH")+"/account/password.html?uk="+userDetailInfo.getMsg().getKey();
		model.addAttribute("changePasswordPath", changePasswordPath);		
		return "unis/frontEnd/accountCenter/changePassword";
	}
	
	
	
	/**
	 * account profile edit
	 */
	@RequestMapping(value = "/accountCenter/accountEditProfile")
	@ResponseBody
	public String accountEditProfile(Model model, HttpServletRequest request, String edit_field, String edit_value) {
		//检查是否已经登录
		Token accessToken = (Token)request.getSession().getAttribute("accessToken");
		boolean loginOrNot = false;
		if(accessToken==null){
		}else if(iGamecenterService.getAccountInfo(accessToken).getErr_num()==512 || iGamecenterService.getAccountInfo(accessToken).getErr_num()==256){			
		}else{
			loginOrNot = true;
		}
		if(!loginOrNot){	
			return  "{\"status\" : \"not login\"}";//new ModelAndView(new StringView("<script>window.location.href='/frontEnd/walo3LeggedLogin'</script>"));
		}
		//accountEditProfile
		if(edit_field.equals("handle_name") || edit_field.equals("phone") || edit_field.equals("personal_status")){
			try{
				System.out.println("edit_field:"+edit_field+ ", edit_value:"+edit_value);
				UserDetailInfo userDetailInfo= iGamecenterService.getAccountInfo(accessToken).getMsg();	
				System.out.println("edit profile return:"+iGamecenterService.editAccountProfile(accessToken, edit_field, edit_value, userDetailInfo.getHandle_name()));
				return "{\"status\" : \"success\"}";
			}catch(Exception e){
				return "{\"status\" : \"fail\"}";
			}				
		}else{
			return "{\"status\" : \"fail\"}";
		}
	}
	
	/**
	 * game wallet info index
	 */
	@RequestMapping(value = "/accountCenter/walletInfo")
	public String walletInfo(Model model, HttpServletRequest request) {
		//检查是否已经登录
		Token accessToken = (Token)request.getSession().getAttribute("accessToken");
		boolean loginOrNot = false;
		if(accessToken==null){
		}else if(iGamecenterService.getAccountInfo(accessToken).getErr_num()==512 || iGamecenterService.getAccountInfo(accessToken).getErr_num()==256){			
		}else{
			loginOrNot = true;
		}
		if(!loginOrNot){	
			return  "unis/frontEnd/accountCenter/reLogin";//new ModelAndView(new StringView("<script>window.location.href='/frontEnd/walo3LeggedLogin'</script>"));
		}
		//accountInfo		
		BaseReturnValue<UserDetailInfo> userDetailInfo= iGamecenterService.getAccountInfo(accessToken);		
		model.addAttribute("user", userDetailInfo.getMsg());		
		return "unis/frontEnd/accountCenter/walletInfo";
	}
	
	/**
	 * game wallet info query
	 */
	@RequestMapping(value = "/accountCenter/walletInfoQuery")
	@ResponseBody
	public String walletInfoQuery(HttpServletRequest request) {
		//检查是否已经登录
		Token accessToken = (Token)request.getSession().getAttribute("accessToken");
		boolean loginOrNot = false;
		if(accessToken==null){
		}else if(iGamecenterService.getAccountInfo(accessToken).getErr_num()==512 || iGamecenterService.getAccountInfo(accessToken).getErr_num()==256){			
		}else{
			loginOrNot = true;
		}
		if(!loginOrNot){	
			return  "unis/frontEnd/accountCenter/reLogin";//new ModelAndView(new StringView("<script>window.location.href='/frontEnd/walo3LeggedLogin'</script>"));
		}
		//accountWallet
		BaseReturnValue<UserWallet> userWallet = iGamecenterService.getWallet(accessToken);		
		//put to map to json
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userWallet", userWallet.getMsg());	
		String reportJson = JsonUtils.toJson(map);
		return reportJson;
	}	
	
	/**
	 * game exchange index
	 */
	@RequestMapping(value = "/accountCenter/exchange")
	public String exchange(Model model, HttpServletRequest request) {
		//检查是否已经登录
		Token accessToken = (Token)request.getSession().getAttribute("accessToken");
		boolean loginOrNot = false;
		if(accessToken==null){
		}else if(iGamecenterService.getAccountInfo(accessToken).getErr_num()==512 || iGamecenterService.getAccountInfo(accessToken).getErr_num()==256){			
		}else{
			loginOrNot = true;
		}
		if(!loginOrNot){	
			return  "unis/frontEnd/accountCenter/reLogin";//new ModelAndView(new StringView("<script>window.location.href='/frontEnd/walo3LeggedLogin'</script>"));
		}
		//accountInfo,sycoin	
		BaseReturnValue<UserDetailInfo> userDetailInfo= iGamecenterService.getAccountInfo(accessToken);		
		model.addAttribute("user", userDetailInfo.getMsg());	
		model.addAttribute("sycoin", iGamecenterService.getSycoin(accessToken).getMsg());
		
		
		
		
		List<GameAndGameCenter> gameAndGameCenterList = alipayController.getGameAndGameCenterList(userDetailInfo.getMsg().getUsername());
		for(GameAndGameCenter gameAndGameCenter : gameAndGameCenterList){
			for(GameCenter gameCenter : gameAndGameCenter.getGameCenterList()){
				gameCenter.setChangeRate(gameWalletRateService.getRateByConsumerKeyAndGameCenterKey(gameAndGameCenter.getGameKey(),gameCenter.getGameCenterKey()));
			}
		}
		
		/*GameAndGameCenter gameAndGameCenter = new GameAndGameCenter();
		GameCenter gameCenter = new GameCenter();
		List<GameCenter> gameCenterList = Lists.newArrayList();
		gameAndGameCenter.setGameKey("1");
		gameAndGameCenter.setGameName("1");
		gameCenter.setGameCenterKey("1");
		gameCenter.setGameCenterName("1");
		gameCenter.setChangeRate(1d);
		gameCenterList.add(gameCenter);
		gameCenter = new GameCenter();
		gameCenter.setGameCenterKey("2");
		gameCenter.setGameCenterName("2");
		gameCenter.setChangeRate(2d);
		gameCenterList.add(gameCenter);
		gameAndGameCenter.setGameCenterList(gameCenterList);
		gameAndGameCenterList.add(gameAndGameCenter);
		
		gameAndGameCenter = new GameAndGameCenter();
		gameCenter = new GameCenter();
		gameCenterList = Lists.newArrayList();
		gameAndGameCenter.setGameKey("2");
		gameAndGameCenter.setGameName("2");
		gameCenter.setGameCenterKey("1");
		gameCenter.setGameCenterName("1");
		gameCenter.setChangeRate(1d);
		gameCenterList.add(gameCenter);
		gameCenter = new GameCenter();
		gameCenter.setGameCenterKey("2");
		gameCenter.setGameCenterName("2");
		gameCenter.setChangeRate(2d);
		gameCenterList.add(gameCenter);
		gameAndGameCenter.setGameCenterList(gameCenterList);
		gameAndGameCenterList.add(gameAndGameCenter);*/
		
		String gameAndGameCenterListJsonData =  JsonUtils.toJson(gameAndGameCenterList);
		model.addAttribute("gameAndGameCenterListJsonData", gameAndGameCenterListJsonData);
		return "unis/frontEnd/accountCenter/exchange";
	}	
	
	/**
	 * game exchange excute
	 */
	@RequestMapping(value = "/accountCenter/change")
	@ResponseBody
	public String change( HttpServletRequest request,String gameSelect ,String gameCenterSelect,Integer chargeNumber) {
		Exchange exchange = new Exchange();
		Token accessToken = (Token)request.getSession().getAttribute("accessToken");
		boolean loginOrNot = false;
		if(accessToken==null){
		}else if(iGamecenterService.getAccountInfo(accessToken).getErr_num()==512 || iGamecenterService.getAccountInfo(accessToken).getErr_num()==256){			
		}else{
			loginOrNot = true;
		}
		if(!loginOrNot){	
			return  "unis/frontEnd/accountCenter/reLogin";//new ModelAndView(new StringView("<script>window.location.href='/frontEnd/walo3LeggedLogin'</script>"));
		}
		
		if(accessToken==null || chargeNumber == null || chargeNumber <= 0){
			exchange.setExchangeStatus(-1);
			return JsonUtils.toJson(exchange);
		}
		Integer rate = gameWalletRateService.getRateByConsumerKeyAndGameCenterKey(gameSelect,gameCenterSelect);
		/**
		 * 充值代码:
		 */
		System.out.println("------------------------");
		BaseReturnValue<ExchangeReturn> exchangeReturn = iGamecenterService.accountExchange(accessToken, chargeNumber, gameSelect, gameCenterSelect, rate);
		if(exchangeReturn.getErr_num()==0){
			exchange.setExchangeStatus(1);
			exchange.setSycoin((exchangeReturn.getMsg().getSycoin().getBalance().intValue()));
			exchange.setToken(exchangeReturn.getMsg().getTokens().getBalance());
			System.out.println("充值成功;充值后世宇币为："+exchangeReturn.getMsg().getSycoin().getBalance()+"; 充值后游戏代币为:"+exchangeReturn.getMsg().getTokens().getBalance());
			return JsonUtils.toJson(exchange);
			
		}else{
			exchange.setExchangeStatus(0);
			System.out.println("世宇币 余额 不足于充值游戏代币");
			return JsonUtils.toJson(exchange);
		}
	}
	/**
	 * 充值返回json实体
	 * @author fanzz
	 *
	 */
	class Exchange{
		private int exchangeStatus;
		private int sycoin;
		private int token;
		public int getExchangeStatus() {
			return exchangeStatus;
		}
		public void setExchangeStatus(int exchangeStatus) {
			this.exchangeStatus = exchangeStatus;
		}
		public int getSycoin() {
			return sycoin;
		}
		public void setSycoin(int sycoin) {
			this.sycoin = sycoin;
		}
		public int getToken() {
			return token;
		}
		public void setToken(int token) {
			this.token = token;
		}
		
	}
	
	/**
	 * account charge record index
	 */
	@RequestMapping(value = "/accountCenter/chargeRecordIndex")
	public String chargeRecordIndex(Model model, HttpServletRequest request) {
		//检查是否已经登录
		Token accessToken = (Token)request.getSession().getAttribute("accessToken");
		boolean loginOrNot = false;
		if(accessToken==null){
		}else if(iGamecenterService.getAccountInfo(accessToken).getErr_num()==512 || iGamecenterService.getAccountInfo(accessToken).getErr_num()==256){			
		}else{
			loginOrNot = true;
		}
		if(!loginOrNot){
			return  "unis/frontEnd/accountCenter/reLogin";//new ModelAndView(new StringView("<script>window.location.href='/frontEnd/walo3LeggedLogin'</script>"));
		}
		//accountInfo		
		BaseReturnValue<UserDetailInfo> userDetailInfo= iGamecenterService.getAccountInfo(accessToken);	
		model.addAttribute("user", userDetailInfo.getMsg());
		return "unis/frontEnd/accountCenter/chargeRecord";
	}
	
	/**
	 * account charge record query,query for sycoin recharge record
	 */
	@RequestMapping(value = "/accountCenter/chargeRecordQuerySycoinRechargeRecord")
	@ResponseBody
	public String chargeRecordQuerySycoinRecord(Model model, HttpServletRequest request, Integer page) {
		Map<String,Object> map = new HashMap<String,Object>();
		//检查是否已经登录
		Token accessToken = (Token)request.getSession().getAttribute("accessToken");
		boolean loginOrNot = false;
		if(accessToken==null){
		}else if(iGamecenterService.getAccountInfo(accessToken).getErr_num()==512 || iGamecenterService.getAccountInfo(accessToken).getErr_num()==256){			
		}else{
			loginOrNot = true;
		}
		if(!loginOrNot){
			map.put("status",  "not login");
			return JsonUtils.toJson(map);
		}
		if(page==null){
			page=1;
		}					
		Integer rpp = 10;
		BaseReturnValue<Page<SycoinRechargeRecord>> syCoinRechargeRecordReturns = iGamecenterService.sycoinRechargeReport(accessToken, page, rpp);
		//System.out.println("--------"+iGamecenterService.sycoinExchangeReport(accessToken, page, rpp));
		Integer total_record = syCoinRechargeRecordReturns.getMsg().getTotal_count();
		Integer total_page = total_record/rpp + (total_record%rpp>0?1:0);
		if(total_page==0){
			total_page=1;
		}
		map.put("status", "success");
		map.put("records", syCoinRechargeRecordReturns.getMsg().getRecords());		
		map.put("hasNextPage", page<total_page?true:false);
		return JsonUtils.toJson(map);
	}
	
	/**
	 * account charge record query,query for tokens recharge record
	 */
	@RequestMapping(value = "/accountCenter/chargeRecordQueryTokensRechargeRecord")
	@ResponseBody
	public String chargeRecordQueryTokensRecord(Model model, HttpServletRequest request, Integer page) {
		Map<String,Object> map = new HashMap<String,Object>();
		//检查是否已经登录
		Token accessToken = (Token)request.getSession().getAttribute("accessToken");
		boolean loginOrNot = false;
		if(accessToken==null){
		}else if(iGamecenterService.getAccountInfo(accessToken).getErr_num()==512 || iGamecenterService.getAccountInfo(accessToken).getErr_num()==256){			
		}else{
			loginOrNot = true;
		}
		if(!loginOrNot){
			map.put("status",  "not login");
			return JsonUtils.toJson(map);
		}
		if(page==null){
			page=1;
		}					
		Integer rpp = 10;
		BaseReturnValue<Page<TokensRechargeRecordNew>> tokensRechargeRecordReturns = iGamecenterService.tokensRechargeReport(accessToken, page, rpp);
		Integer total_record = tokensRechargeRecordReturns.getMsg().getTotal_count();
		Integer total_page = total_record/rpp + (total_record%rpp>0?1:0);
		if(total_page==0){
			total_page=1;
		}
		map.put("status", "success");
		map.put("records", tokensRechargeRecordReturns.getMsg().getRecords());		
		map.put("hasNextPage", page<total_page?true:false);
		return JsonUtils.toJson(map);
	}
	
	/**
	 * account charge record query,query for sycoin exchange record
	 */
	@RequestMapping(value = "/accountCenter/chargeRecordQuerySycoinExchangeRecord")
	@ResponseBody
	public String chargeRecordQuerySycoinExchangeRecord(Model model, HttpServletRequest request, Integer page) {
		Map<String,Object> map = new HashMap<String,Object>();
		//检查是否已经登录
		Token accessToken = (Token)request.getSession().getAttribute("accessToken");
		boolean loginOrNot = false;
		if(accessToken==null){
		}else if(iGamecenterService.getAccountInfo(accessToken).getErr_num()==512 || iGamecenterService.getAccountInfo(accessToken).getErr_num()==256){			
		}else{
			loginOrNot = true;
		}
		if(!loginOrNot){
			map.put("status",  "not login");
			return JsonUtils.toJson(map);
		}
		if(page==null){
			page=1;
		}					
		Integer rpp = 10;
		BaseReturnValue<Page<SycoinExchangeRecord>> sycoinExchangeRecordReturns = iGamecenterService.sycoinExchangeReport(accessToken, page, rpp);
		Integer total_record = sycoinExchangeRecordReturns.getMsg().getTotal_count();
		Integer total_page = total_record/rpp + (total_record%rpp>0?1:0);
		if(total_page==0){
			total_page=1;
		}
		map.put("status", "success");
		map.put("records", sycoinExchangeRecordReturns.getMsg().getRecords());		
		map.put("hasNextPage", page<total_page?true:false);
		return JsonUtils.toJson(map);
	}
	
	@RequestMapping(value = "/accountCenter/mygift")
	public String mygiftList(HttpServletRequest request,Model model ,MyPageRequest myPageRequest){
		//检查是否已经登录
		Token accessToken = (Token)request.getSession().getAttribute("accessToken");
		boolean loginOrNot = accessToken!=null && iGamecenterService.getAccountInfo(accessToken).getErr_num()!=512 && iGamecenterService.getAccountInfo(accessToken).getErr_num()!=256;
		if(!loginOrNot){	
			return  "unis/frontEnd/accountCenter/reLogin";
		}
		BaseReturnValue<UserDetailInfo> userDetailInfo= iGamecenterService.getAccountInfo(accessToken);	
		myPageRequest.setPageSize(10);
		PageRequest pageable = new PageRequest(myPageRequest.getPageNo() - 1, myPageRequest.getPageSize(), Sort.Direction.DESC, "id");		
		org.springframework.data.domain.Page<CommodityOrder> commodityOrderPage = commodityOrderService.findAll(pageable,userDetailInfo.getMsg().getKey());
		model.addAttribute("commodityOrderList", commodityOrderPage.getContent());
		model.addAttribute("page", commodityOrderPage.getContent());
		long total = commodityOrderPage.getTotalElements();
		myPageRequest.setTotalCount(total);
		model.addAttribute("pageRequest", myPageRequest);	
		return "unis/frontEnd/accountCenter/mygift";
	}
	
	
	@RequestMapping(value = "/accountCenter/mygift/show/{id}")
	public String mygiftDetail(HttpServletRequest request,@PathVariable(value="id")Long id,Model model,@RequestParam(value="pageNo",required=false)String pageNoStr,@RequestParam(value="raf",required=false)Integer raf){
		Token accessToken = (Token)request.getSession().getAttribute("accessToken");
		boolean loginOrNot = accessToken!=null && iGamecenterService.getAccountInfo(accessToken).getErr_num()!=512 && iGamecenterService.getAccountInfo(accessToken).getErr_num()!=256;
		if(!loginOrNot){	
			return  "unis/frontEnd/accountCenter/reLogin";
		}
		
		CommodityOrder commodityOrder = null;
		if(id!=null && id!=0L){
			commodityOrder = commodityOrderService.findById(id);
		}
		if(commodityOrder==null){
			return "unis/frontEnd/accountCenter/mygift";
		}
		
		if(commodityOrder.getDeadLineDate() !=null){
			Long time1 = commodityOrder.getDeadLineDate().getTime();
			Long time2 = new Date().getTime();
			boolean flag = (time1 - time2) - (6 * 24 * 60 * 60 * 1000L) <= 0L;
			//距离截至收货日期 日数
			model.addAttribute("lastDeadLineFlag", flag);
		}
		boolean reviseAddressFlag = false;
		if(raf!=null){
			reviseAddressFlag = raf == 1 ? true : false;
		}
		model.addAttribute("reviseAddressFlag", reviseAddressFlag);
		model.addAttribute("pageNo", getPageNo(pageNoStr));
		model.addAttribute("commodityOrder", commodityOrder);
		return "unis/frontEnd/accountCenter/mygift_detail";
	}
	
	/**
	 * 修改地址
	 * @param request
	 * @param commodityOrderId
	 * @param deliveryAddress
	 * @return
	 */
	@RequestMapping(value = "/accountCenter/mygift/reviseAddress")
	public String reviseAddress(HttpServletRequest request,Long commodityOrderId , DeliveryAddress deliveryAddress,@RequestParam(value="pageNo",required=false)String pageNoStr,Model model){
		Token accessToken = (Token)request.getSession().getAttribute("accessToken");
		boolean loginOrNot = accessToken!=null && iGamecenterService.getAccountInfo(accessToken).getErr_num()!=512 && iGamecenterService.getAccountInfo(accessToken).getErr_num()!=256;
		if(!loginOrNot){	
			return  "unis/frontEnd/accountCenter/reLogin";
		}
		BaseReturnValue<UserDetailInfo> userDetailInfo= iGamecenterService.getAccountInfo(accessToken);	
		
		//全部不为空
		if(!deliveryAddress.isAllNotNull()){
			//非法提交
			return  "redirect:/frontEnd/index";
		}
		//长度符合要求
		if(deliveryAddress.getAddress().length() > 255 || deliveryAddress.getCellNumber().length() > 20 || deliveryAddress.getZipcode().length() > 20 || deliveryAddress.getReceiverName().length() > 20 ){
			return  "redirect:/frontEnd/index";
		}
		//ID正确
		if(userDetailInfo!=null && commodityOrderId!=null&&commodityOrderId!=0L){
			CommodityOrder commodityOrder = commodityOrderService.findById(commodityOrderId);
			String userKey = userDetailInfo.getMsg().getKey();
			if(commodityOrder!=null && commodityOrder.getUserKey().equals(userKey)){
				if(commodityOrder.getStatus() == 30){
					
					String md5Hash = MD5.MD5Encode(deliveryAddress.getReceiverName().trim() + deliveryAddress.getZipcode().trim() + deliveryAddress.getAddress().trim() + deliveryAddress.getCellNumber().trim() + userKey);
					deliveryAddress.setMd5Hash(md5Hash);
					deliveryAddress.setUserKey(userKey);
					DeliveryAddress saved = deliveryAddressService.saveIfNotExist(deliveryAddress);
					
					commodityOrder.setDeliveryAddress(saved);
					
					CommodityOrderStatus commodityOrderStatus = new CommodityOrderStatus();
					commodityOrderStatus.setStatus(30);
					commodityOrderStatus.setRemarks("修改收货地址");
					commodityOrderStatus.setStatusDate(new Date());
					commodityOrderStatus.setUserType(1);
					commodityOrder.addCommodityOrderStatus(commodityOrderStatus);
					
					commodityOrderService.save(commodityOrder);
					
					model.addAttribute("pageNo", getPageNo(pageNoStr));
					return  "redirect:/frontEnd/accountCenter/mygift/show/" + commodityOrderId;
				}
				
			}
		}
		return  "redirect:/frontEnd/index";
	}
	
	@RequestMapping(value = "/accountCenter/mygift/confirmreceipt/{id}")
	public String ConfirmReceipt(HttpServletRequest request,@PathVariable(value="id")Long commodityOrderId,@RequestParam(value="pageNo",required=false)String pageNoStr,Model model){
		model.addAttribute("pageNo", getPageNo(pageNoStr));
		return updateStatus(request,commodityOrderId,1);
	}
	
	@RequestMapping(value = "/accountCenter/mygift/cancelorder/{id}")
	public String CancelOrder(HttpServletRequest request,@PathVariable(value="id")Long commodityOrderId,@RequestParam(value="pageNo",required=false)String pageNoStr,Model model){
		model.addAttribute("pageNo", getPageNo(pageNoStr));
		return updateStatus(request,commodityOrderId,2);
	}
	
	@RequestMapping(value = "/accountCenter/mygift/addDeadLineDate/{id}")
	@ResponseBody
	public String AddDeadLineDates(HttpServletRequest request,@PathVariable(value="id")Long commodityOrderId){
		Map<String,String> returnMap = Maps.newHashMap();
		Token accessToken = (Token)request.getSession().getAttribute("accessToken");
		boolean loginOrNot = accessToken!=null && iGamecenterService.getAccountInfo(accessToken).getErr_num()!=512 && iGamecenterService.getAccountInfo(accessToken).getErr_num()!=256;
		if(!loginOrNot){	
			returnMap.put("code", "-1");
			returnMap.put("msg", "未登录！");
			return  JsonUtils.toJson(returnMap);
		}
		if(commodityOrderId!=null&&commodityOrderId!=0L){
			CommodityOrder commodityOrder = commodityOrderService.findById(commodityOrderId);
			CommodityOrder saved = null;
			Long time1 = commodityOrder.getDeadLineDate().getTime();
			Long time2 = new Date().getTime();
			boolean flag = (time1 - time2) - (6 * 24 * 60 * 60 * 1000L) <= 0L;
			if(flag){
				Date before = commodityOrder.getDeadLineDate();
				System.out.println("before=================================>" + before);
				Date after = DateUtil.getDateAfterNDays(before, 16);
				System.out.println("after==================================>" + after);
				commodityOrder.setDeadLineDate(after);
				saved = commodityOrderService.save(commodityOrder);
				Date savedDate = saved.getDeadLineDate();
				System.out.println("saved==================================>" + savedDate);
				returnMap.put("code", "1");
				returnMap.put("msg", DateUtil.getDate(savedDate));
				return  JsonUtils.toJson(returnMap);
			}else{
				returnMap.put("code", "-3");
				returnMap.put("msg", "deadLineDate error！");
				return  JsonUtils.toJson(returnMap);
			}
		}else{
			returnMap.put("code", "-2");
			returnMap.put("msg", "commodityOrderId error!");
			return  JsonUtils.toJson(returnMap);
		}
	}
	
	private String updateStatus(HttpServletRequest request,Long commodityOrderId,int flag){
		
		Token accessToken = (Token)request.getSession().getAttribute("accessToken");
		boolean loginOrNot = accessToken!=null && iGamecenterService.getAccountInfo(accessToken).getErr_num()!=512 && iGamecenterService.getAccountInfo(accessToken).getErr_num()!=256;
		if(!loginOrNot){	
			return  "unis/frontEnd/accountCenter/reLogin";
		}
		BaseReturnValue<UserDetailInfo> userDetailInfo= iGamecenterService.getAccountInfo(accessToken);	
		if(commodityOrderId!=null&&commodityOrderId!=0L){
			CommodityOrder commodityOrder = commodityOrderService.findById(commodityOrderId);
			if(commodityOrder.getUserKey().equals(userDetailInfo.getMsg().getKey())){
				if(flag==1 && commodityOrder.getStatus() == 70){
					CommodityOrderStatus commodityOrderStatus = new CommodityOrderStatus();
					commodityOrderStatus.setRemarks("确认收货");
					commodityOrderStatus.setCommodityOrder(commodityOrder);
					commodityOrderStatus.setStatusDate(new Date());
					commodityOrderStatus.setUserType(1);
					commodityOrderStatus.setStatus(90);
					commodityOrder.addCommodityOrderStatus(commodityOrderStatus);
					commodityOrder.setStatus(90);	
					commodityOrderService.save(commodityOrder);
					
				}else if(flag==2 && commodityOrder.getStatus() == 30){
					
					//, 改成回退彩票,2014-06-25 by eric modify score to ticket
					//BaseReturnValue<AchievementPaymentRollBackReturns> returns2 = iGamecenterService.achievementTxPaymentRollBack(accessToken, commodityOrder.getTxCode());	
					BaseReturnValue<TicketDebitRollBackReturns> returns2 = iGamecenterService.ticketDebitRollBack(accessToken, commodityOrder.getTxCode());	
					
					if(returns2.getErr_num()==0){
						
						CommodityOrderStatus commodityOrderStatus = new CommodityOrderStatus();
						commodityOrderStatus.setRemarks("取消订单");
						commodityOrderStatus.setCommodityOrder(commodityOrder);
						commodityOrderStatus.setStatusDate(new Date());
						commodityOrderStatus.setUserType(1);
						commodityOrderStatus.setStatus(10);
						commodityOrder.addCommodityOrderStatus(commodityOrderStatus);
						commodityOrder.setStatus(10);
						
						commodityService.updateStocks(commodityOrder.getCommodity().getId(), 0 - commodityOrder.getAmount());//更新库存
						
						commodityOrderService.save(commodityOrder);
					}
				}
				
			}
		}
		return "redirect:/frontEnd/accountCenter/mygift/show/"+commodityOrderId;
	}
	
	private int getPageNo(String pageNoStr){
		int pageNo = 1;
		if(StringUtils.isNotBlank(pageNoStr)){
			try {
				pageNo = Integer.parseInt(pageNoStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return pageNo;
	}
	
	public static void main(String[] args){
		System.out.println(DateUtil.getDateAfterNDays(new Date(), 20));
	}
	
}
