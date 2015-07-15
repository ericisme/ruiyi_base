package cn.unis.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.scribe.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.unis.entity.ADS;
import cn.unis.entity.Commodity;
import cn.unis.entity.CommodityCategory;
import cn.unis.entity.CommodityOrder;
import cn.unis.entity.CommodityOrderStatus;
import cn.unis.entity.DeliveryAddress;
import cn.unis.service.impl.CommodityCategoryService;
import cn.unis.service.impl.CommodityOrderService;
import cn.unis.service.interfaces.IADSService;
import cn.unis.service.interfaces.ICommodityService;
import cn.unis.service.interfaces.IDeliveryAddressService;
import cn.unis.service.interfaces.IGameCenterService;
import cn.unis.transit.BaseReturnValue;
import cn.unis.transit.GiftCenterUsernameAndScore;
import cn.unis.transit.UserDetailInfo;
import cn.unis.transit.achievement.AchievementPaymentReturns;
import cn.unis.transit.achievement.AchievementPaymentRollBackReturns;
import cn.unis.transit.ticket.TicketBalance;
import cn.unis.transit.ticket.TicketDebitReturns;
import cn.unis.transit.ticket.TicketDebitRollBackReturns;
import cn.unis.utils.JsonUtils;
import cn.unis.utils.MD5;
import cn.unis.utils.MyPageRequest;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 积分商城
 *
 * @author fanzz
 *
 */
@Controller
@RequestMapping("/frontEnd/giftcenter")
public class GiftCenterController {
	@Autowired
	private IGameCenterService iGamecenterService;
	@Autowired
	private CommodityCategoryService commodityCategoryService;
	@Autowired
	private ICommodityService iCommodityService;
	@Autowired
	private IDeliveryAddressService iDeliveryAddressService;
	@Autowired
	private CommodityOrderService commodityOrderService;
	@Autowired
	private IADSService iadsService;

	/**
	 * 单件商品展示页面
	 * show
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/show/{id}")
	public String show(@PathVariable Long id, Model model, HttpServletRequest request){
		//检查是否已经登录
		Token accessToken = (Token)request.getSession().getAttribute("accessToken");
		boolean loginOrNot = false;
		if(accessToken==null){
		}else if(iGamecenterService.getAccountInfo(accessToken).getErr_num()==512 || iGamecenterService.getAccountInfo(accessToken).getErr_num()==256){
		}else{
			loginOrNot = true;
		}
		if(!loginOrNot){
			model.addAttribute("isLogin", false);
		}else{
			model.addAttribute("isLogin", true);
			BaseReturnValue<UserDetailInfo> userDetailInfo= iGamecenterService.getAccountInfo(accessToken);
			BaseReturnValue<TicketBalance>  userTicketBalance = iGamecenterService.getAccountTicketBalance(accessToken);
			model.addAttribute("deliveryAddress", iDeliveryAddressService.findTheLastDeliveryAddress(userDetailInfo.getMsg().getKey()));
			model.addAttribute("user", userDetailInfo.getMsg());
			model.addAttribute("ticket", userTicketBalance.getMsg());//,2014-06-25 by eric modify score to ticket
		}
		//根据id查找商品
		Commodity commodity = iCommodityService.findById(id);
		CommodityCategory topCommodityCategory =commodityCategoryService.getTopCommodityCategory(commodity.getCommodityCategory());
		System.out.println("commodity name:"+commodity.getName());
		System.out.println("commodity topCommodityCategory name:"+topCommodityCategory.getName());
		model.addAttribute("commodity", commodity);
		model.addAttribute("topCommodityCategory", topCommodityCategory);

		long count = commodityOrderService.countDelivery(id);
		model.addAttribute("count",count );//发货数量


		return "/unis/frontEnd/giftCenter/show";
	}


	@RequestMapping("index")
	public String index(Model model, HttpServletRequest request,
			MyPageRequest myPageRequest,
			@RequestParam(value = "_c_id", required = false) Long _c_id, // 商品分类id
			@RequestParam(value = "order", required = false) Integer order, // 排序id
			@RequestParam(value = "c_flag", required = false) Integer c_flag) {


		myPageRequest.setPageSize(12);// 每页显示6条记录
		GiftCenterUsernameAndScore usernameAndScore = new GiftCenterUsernameAndScore();
		// 分类
		_c_id = _c_id == null ? 0L : _c_id;
		usernameAndScore.setSort(_c_id.intValue());
		// 排序
		String sort = "id";
		Direction dir = Sort.Direction.ASC;

		if (order == null || order == 0) {
			order = 0;
			sort = "price";
			dir = Sort.Direction.ASC;
		} else if(order == 10){
			order = 0;
			sort = "price";
			dir = Sort.Direction.DESC;
		} else if (order == 1) {
			order = 1;
			sort = "exchangeCount";
			dir = Sort.Direction.ASC;
		} else if( order == 11){
			order = 1;
			sort = "exchangeCount";
			dir = Sort.Direction.DESC;
		} else if (order == 2) {
			order = 2;
			sort = "createAt";
			dir = Sort.Direction.DESC;
		} else if(order == 12){
			order = 2;
			sort = "createAt";
			dir = Sort.Direction.ASC;
		}
		Pageable pageable = new PageRequest(myPageRequest.getPageNo() - 1, myPageRequest.getPageSize(), dir, sort);

		usernameAndScore.setOrder(order);

		Page<Commodity> paginate = null;
		Token accessToken = (Token) request.getSession().getAttribute("accessToken");

		boolean loginOrNot = false;
		if(accessToken != null && iGamecenterService.getAccountInfo(accessToken).getErr_num()!=512 && iGamecenterService.getAccountInfo(accessToken).getErr_num()!=256){
			loginOrNot = true;
		}

		if(loginOrNot){// 已经登录
			BaseReturnValue<UserDetailInfo> userDetailInfo = iGamecenterService.getAccountInfo(accessToken);
			BaseReturnValue<TicketBalance>  userTicketBalance = iGamecenterService.getAccountTicketBalance(accessToken);
			Integer score = 0;// 用户游戏彩票数,2014-06-25 by eric modify score to ticket
			if(userDetailInfo!=null && userDetailInfo.getMsg().getAchievement_score()!= null){
				//score = userDetailInfo.getMsg().getAchievement_score();改成使用彩票兑换实体商品,2014-06-25 by eric modify score to ticket
				score = userTicketBalance.getMsg().getBalance();
			}
			Commodity commodity = iCommodityService.findTheLastPriceCommodity();
			boolean cFlag = c_flag != null && c_flag == 1;//点击“看看我可以兑换什么礼品”标记
			if (cFlag && commodity != null && commodity.getPrice() > score) {
				usernameAndScore.setEnoughScore(0);// 显示彩票不够
			} else {
				usernameAndScore.setEnoughScore(1);// 不显示彩票不够
			}
			if(cFlag && commodity != null && commodity.getPrice() <= score){
				usernameAndScore.setShowWeChooseFor(1);// 显示我们为你选择的商品
			}
			usernameAndScore.setIsShow(1);
			if(userDetailInfo!=null && userDetailInfo.getMsg().getUsername()!=null){
				usernameAndScore.setUserName(userDetailInfo.getMsg().getUsername());
			}

			usernameAndScore.setScore(score);
			if (c_flag != null && c_flag == 1) {
				paginate = iCommodityService.findAll(_c_id, "", 1, score,pageable);
			} else {
				paginate = iCommodityService.findAll(_c_id, "", 1, null,pageable);
			}
		} else {//未登录
			usernameAndScore.setEnoughScore(1);
			usernameAndScore.setIsShow(0);
			paginate = iCommodityService.findAll(_c_id, "", 1, null, pageable);

		}
		List<Commodity> CommodityList = setDefaultPicture(paginate);
		model.addAttribute("UsernameAndScore", usernameAndScore);
		model.addAttribute("page", CommodityList);

		List<CommodityCategory> comList = commodityCategoryService.getCommodityCategoryListByLevel(2);

		model.addAttribute("commodityCategoryList", comList);
		long total = paginate.getTotalElements();
		myPageRequest.setTotalCount(total);

		model.addAttribute("pageRequest", myPageRequest);


		int biggest = iadsService.getTheCount();
		Pageable adsPageable = new PageRequest(0, biggest,Sort.Direction.DESC, "sortNumber");
		List<ADS> adsList = iadsService.getTopItems(adsPageable);
		model.addAttribute("adsList", adsList);

		return "/unis/frontEnd/giftCenter/index";
	}

	@RequestMapping(value="exchange",method=RequestMethod.POST)
	@ResponseBody
	public String exchange(HttpServletRequest request,Long commodityId,Integer amount,DeliveryAddress deliveryAddress) {

		Map<String,String> returnMap = Maps.newHashMap();

		Token accessToken = (Token)request.getSession().getAttribute("accessToken");

		boolean loginOrNot = accessToken!=null && iGamecenterService.getAccountInfo(accessToken).getErr_num() != 512 && iGamecenterService.getAccountInfo(accessToken).getErr_num()!=256;
		if(!loginOrNot){
			returnMap.put("code", "1");
//			returnMap.put("msg",  "not login!");
			returnMap.put("msg",  "未登录!");
			return  JsonUtils.toJson(returnMap);//未登录
		}

		CommodityOrder commodityOrder = new CommodityOrder();

		String orderNum = UUID.randomUUID().toString();
		commodityOrder.setOrderNum(orderNum);//订单编号

		BaseReturnValue<UserDetailInfo> userDetailInfo= iGamecenterService.getAccountInfo(accessToken);
		String userKey = userDetailInfo.getMsg().getKey();
		commodityOrder.setUserKey(userKey);//userKey

		if(commodityId!=null && commodityId > 0L){
			Commodity commodity = iCommodityService.findById(commodityId);
			if(commodity!=null){
				commodityOrder.setCommodity(commodity);//commodity
				commodityOrder.setSinglePrice(commodity.getPrice());//单价(即单间商品所需彩票数),2014-06-25 by eric modify score to ticket
			}else{
				returnMap.put("code", "2");
				//returnMap.put("msg",  "commodity not exist!");
				returnMap.put("msg",  "商品不存在!");
				return  JsonUtils.toJson(returnMap);
			}
		}else{ // 没有提交commodityId，或者commodityId少于0
			returnMap.put("code", "3");
//			returnMap.put("msg",  "commodityId error!");
			returnMap.put("msg",  "商品id出错!");

			return  JsonUtils.toJson(returnMap);
		}


		if(amount!=null && amount>0){
			commodityOrder.setAmount(amount);//数量
			commodityOrder.setTotalPrice(amount * commodityOrder.getSinglePrice());//总价
		}else{
			returnMap.put("code", "4");
//			returnMap.put("msg",  "amount error!");
			returnMap.put("msg",  "填写数量出错!");

			return  JsonUtils.toJson(returnMap);
		}

		Date date = new Date();

		if(deliveryAddress!=null && deliveryAddress.isAllNotNull()){
			deliveryAddress.setUserKey(userKey);

			String md5Hash = MD5.MD5Encode(deliveryAddress.getReceiverName().trim() + deliveryAddress.getZipcode().trim()
					+ deliveryAddress.getAddress().trim() + deliveryAddress.getCellNumber().trim() + deliveryAddress.getUserKey().trim());

			deliveryAddress.setMd5Hash(md5Hash);
			deliveryAddress.setCreateTime(date);

			if(deliveryAddress.getAddress().length() > 255 || deliveryAddress.getCellNumber().length() > 20 || deliveryAddress.getZipcode().length() > 20 || deliveryAddress.getReceiverName().length() > 20 ){
				returnMap.put("code", "5");
//				returnMap.put("msg",  "deliveryAddress length error!");
				returnMap.put("msg",  "收货地址字段长度不符合规格!");
				return  JsonUtils.toJson(returnMap);
			}

			DeliveryAddress saved = iDeliveryAddressService.saveIfNotExist(deliveryAddress);

			commodityOrder.setDeliveryAddress(saved);//地址
		}else{
			returnMap.put("code", "5");
//			returnMap.put("msg",  "deliveryAddress error!");
			returnMap.put("msg",  "收货地址出错!");
			return  JsonUtils.toJson(returnMap);
		}

		commodityOrder.setStatus(30);//已下单

		commodityOrder.setOrderDate(date);

		CommodityOrderStatus commodityOrderStatus = new CommodityOrderStatus();
		commodityOrderStatus.setCommodityOrder(commodityOrder);
		commodityOrderStatus.setRemarks("订单创建");
		commodityOrderStatus.setStatus(30);
		commodityOrderStatus.setUserType(1);
		commodityOrderStatus.setStatusDate(date);
		commodityOrder.addCommodityOrderStatus(commodityOrderStatus);


		if(!iCommodityService.updateStocks(commodityId,commodityOrder.getAmount())){
			returnMap.put("code", "6");
//			returnMap.put("msg",  "not enough commodity");
			returnMap.put("msg",  "商品库存不足!");

			return  JsonUtils.toJson(returnMap);
		}//更新库存(在保存地址后，再检查库存是否足够)


		//扣减世宇积分,改成扣减彩票数,2014-06-25 by eric modify score to ticket
		//BaseReturnValue<AchievementPaymentReturns> returns = iGamecenterService.achievementTxPayment(accessToken, commodityOrder.getTotalPrice());
		BaseReturnValue<TicketDebitReturns> returns = iGamecenterService.ticketDebit(accessToken, commodityOrder.getTotalPrice());
		
		CommodityOrder saved = null;
		if(returns!=null && returns.getErr_num()==0){//扣减成功，返回tx_code(保存到数据库，用于退款的号码),和剩余积分数
			//System.out.println("tx_code:"+returns.getMsg().getTx_code());// tx_code: 请保存到数据库用于交易失败时回退扣减的积分
			//System.out.println("balance:"+returns.getMsg().getBalance());
			commodityOrder.setTxCode(returns.getMsg().getTx_code());

			try {
				saved = commodityOrderService.save(commodityOrder);
				//saved.setOrderNum("LP" + (  1000000000 + saved.getId()) );
				//commodityOrderService.save(saved);
			} catch (Exception e) {
				//回退世宇积分demo, 交易失败时需要回退扣减的积分, 改成回退彩票,2014-06-25 by eric modify score to ticket
				//BaseReturnValue<AchievementPaymentRollBackReturns> returns2 = iGamecenterService.achievementTxPaymentRollBack(accessToken, returns.getMsg().getTx_code());
				BaseReturnValue<TicketDebitRollBackReturns> returns2 = iGamecenterService.ticketDebitRollBack(accessToken, returns.getMsg().getTx_code());
				
				if(returns2.getErr_num()==0){//回退扣减世宇积分成功
					System.out.println("balance2:"+returns2.getMsg().getBalance());
				}else if(returns2.getErr_num()==404){//回退扣减世宇积分不成功，没找到对应的tx_code的扣减记录
					System.out.println("err_msg:"+returns2.getErr_msg());
				}else if(returns2.getErr_num()==410){//回退扣减世宇积分不成功，该tx_code已经被回退过了
					System.out.println("err_msg:"+returns2.getErr_msg());
				}

				iCommodityService.updateStocks(commodityId,0-commodityOrder.getAmount());//更新库存

				returnMap.put("code", "7");
//				returnMap.put("msg",  "something error when saving commodityOrder!");
				returnMap.put("msg",  "系统出错!");

				return  JsonUtils.toJson(returnMap);
			}


		}else{//扣减不成功，彩票数不够用于扣减,2014-06-25 by eric modify score to ticket
			iCommodityService.updateStocks(commodityId,0-commodityOrder.getAmount());//更新库存


			returnMap.put("code", "8");
//			returnMap.put("msg",  "not enough score or other error!");
			returnMap.put("msg",  "你的彩票不足兑换本商品!");//,2014-06-25 by eric modify score to ticket

			return  JsonUtils.toJson(returnMap);
		}

		returnMap.put("code", "0");
		returnMap.put("msg",  ""+saved.getId());

		return  JsonUtils.toJson(returnMap);
	}


	/**
	 * 设置默认图片
	 * @param paginate
	 * @return
	 */
	private List<Commodity> setDefaultPicture(Page<Commodity> paginate) {
		List<Commodity> CommodityList = null;
		if (paginate == null) {
			CommodityList = Lists.newArrayList();
		} else {
			CommodityList = paginate.getContent();
			for (Commodity commodity : CommodityList) {
				if (commodity.getIndexPagePicture() == null) {
					commodity
							.setIndexPagePictureUrl("/static/static-page/img/300x236.jpg");
				} else {
					commodity.setIndexPagePictureUrl(commodity
							.getIndexPagePicture().getUrl());
				}
			}
		}
		return CommodityList;
	}

}
