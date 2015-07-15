package cn.unis.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ruiyi.base.entity.User;
import cn.ruiyi.base.service.account.AccountService;
import cn.unis.entity.GameWalletRate;
import cn.unis.service.impl.GameWalletRateService;
import cn.unis.service.interfaces.IGameCenterService;
import cn.unis.transit.BaseReturnValues;
import cn.unis.transit.Consumer;
import cn.unis.transit.GameCenterSimpleInfo;
import cn.unis.utils.JsonUtils;


/**
 * 游戏币兑换比率设定controller
 * @author eric
 *
 */
@Controller
@RequestMapping(value = "/backEnd/gameWalletRate")
public class GameWalletRateController{

	@Autowired
	private GameWalletRateService gameWalletRateService;
	@Resource(name = "waloGameCenterServiceAdapter")
	private IGameCenterService iGamecenterService;
	@Autowired
	private AccountService accountService;
	

	/**
	 * index
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<Consumer> chargeableConsumerList = iGamecenterService.getAllChargeableConsumer();
		map.put("chargeableConsumerList", chargeableConsumerList);
		return  new ModelAndView("unis/backEnd/gameWalletRate/index", map);
	}

	/**
	 * index
	 * @return
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, String consumer_key, String provinceSel, String citySel, String type) {
		if(consumer_key==null){
			consumer_key = "";
		}
		if(type==null){
			type = "";
		}
		Map<String,Object> map = new HashMap<String,Object>();
		List<GameWalletRate> gameWalletRateList = new ArrayList<GameWalletRate>();
		
		//game_center_list
		BaseReturnValues<GameCenterSimpleInfo> gameCenterSimpleInfoList ;
		if("000000".equals(provinceSel)){	
			gameCenterSimpleInfoList = iGamecenterService.getAllGameCenter();			
		}else if(provinceSel.equals(citySel)){
			gameCenterSimpleInfoList = iGamecenterService.getGameCentersByProvinceCode(provinceSel);
			System.out.println(gameCenterSimpleInfoList = iGamecenterService.getGameCentersByProvinceCode(provinceSel));
		}else{
			gameCenterSimpleInfoList = iGamecenterService.getGameCentersByCityCode(citySel);
		}
		//chargeable_consumer_list		
		List<Consumer> chargeableConsumerList =  new ArrayList<Consumer>();
		if("".equals(consumer_key)){
			chargeableConsumerList = iGamecenterService.getAllChargeableConsumer();
		}else{
			chargeableConsumerList.add(iGamecenterService.getConsumerDetail(consumer_key).getMsg());
		}
		//按游戏类型筛选
		List<Consumer> gametypeConsumerList =  new ArrayList<Consumer>();
		if(!type.equals("")){
			for(Consumer consumer : chargeableConsumerList){
				if(type.equals(consumer.getType())){
					gametypeConsumerList.add(consumer);
				}
			}
			chargeableConsumerList = gametypeConsumerList;
		}			
			if(gameCenterSimpleInfoList.getMsg()!=null){
				for(GameCenterSimpleInfo gameCenterSimpleInfo : gameCenterSimpleInfoList.getMsg()){
					for(Consumer consumer : chargeableConsumerList){
						System.out.println("consumer.getType():"+consumer.getType());
						GameWalletRate gameWalletRate = new GameWalletRate();
						gameWalletRate.setConsumerKey(consumer.getKey());
						gameWalletRate.setConsumerName(consumer.getName());
						gameWalletRate.setGameCenterKey(gameCenterSimpleInfo.getKey());
						gameWalletRate.setGameCenterName(gameCenterSimpleInfo.getName());
						gameWalletRate.setGameCenterProvinceName(gameCenterSimpleInfo.getProvince());
						gameWalletRate.setGameCenterProvinceCode(gameCenterSimpleInfo.getProvince_code());
						gameWalletRate.setGameCenterCityName(gameCenterSimpleInfo.getCity());
						gameWalletRate.setGameCenterCityCode(gameCenterSimpleInfo.getCity_code());
						gameWalletRateList.add(gameWalletRate);
					}
				}
			}

		for(GameWalletRate gameWalletRate : gameWalletRateList){
			gameWalletRate.setRate(gameWalletRateService.getRateByConsumerKeyAndGameCenterKeyForController(gameWalletRate.getConsumerKey(), gameWalletRate.getGameCenterKey()));
		}
			
		map.put("gameWalletRateList", gameWalletRateList);
			
		
		return  new ModelAndView("unis/backEnd/gameWalletRate/list", map);
	}
	
	
//	/**
//	 * edit
//	 * @param id
//	 * @return
//	 */
//	@RequiresPermissions("gameWalletRate:edit")
//	@RequestMapping(value = "/edit/{id}")
//	public ModelAndView edit(HttpServletRequest request, @PathVariable Long id){
//		Map<String,Object> map = new HashMap<String,Object>();		
//		GameWalletRate doamin = gameWalletRateService.findById(id);		
//		map.put("entity", doamin);				//编辑新闻，当为新增时，此变量为空
//		map.put("key",id.equals(0L)); 			//区分用户的修改和新增(key为true时：新增；key为false时：修改。）
//		return new ModelAndView("unis/backEnd/gameWalletRate/edit", map);
//	}

	/**
	 * save
	 * @param request
	 * @param doamin
	 * @return
	 */
	@RequiresPermissions("gameWalletRate:edit")
	@RequestMapping(value = "/save")
	@ResponseBody
	public String save(HttpServletRequest request, String game_center_key, String consumer_key, Integer rate) {	
		Map<String,Object> map = new HashMap<String,Object>();
		System.out.println("game_center_key:"+game_center_key+" consumer_key:"+consumer_key+" rate:"+rate);
		try{
			Object shiroUser = SecurityUtils.getSubject().getPrincipal();
			User user = accountService.findUserByLoginName(shiroUser.toString());
			GameWalletRate gameWalletRate = gameWalletRateService.getByConsumerAndGameCenterKey(consumer_key, game_center_key);
			if(gameWalletRate==null){//新增
				gameWalletRate = new GameWalletRate();
				gameWalletRate.setConsumerKey(consumer_key);
				gameWalletRate.setConsumerName(iGamecenterService.getConsumerDetail(consumer_key).getMsg().getName());
				gameWalletRate.setGameCenterKey(game_center_key);
				gameWalletRate.setGameCenterName(iGamecenterService.getGameCenter(game_center_key).getMsg().getName());
				gameWalletRate.setUser(user);;
				gameWalletRate.setEditTime(new Date());
				gameWalletRate.setRate(rate);
				gameWalletRateService.save(gameWalletRate);
			}else{//编辑
				//保存历史
				gameWalletRate.setHistory(1);
				gameWalletRateService.save(gameWalletRate);
				//保存新记录
				gameWalletRate = new GameWalletRate();
				gameWalletRate.setConsumerKey(consumer_key);
				gameWalletRate.setConsumerName(iGamecenterService.getConsumerDetail(consumer_key).getMsg().getName());
				gameWalletRate.setGameCenterKey(game_center_key);
				gameWalletRate.setGameCenterName(iGamecenterService.getGameCenter(game_center_key).getMsg().getName());
				gameWalletRate.setUser(user);;
				gameWalletRate.setEditTime(new Date());
				gameWalletRate.setRate(rate);
				gameWalletRateService.save(gameWalletRate);
			}
				
			map.put("success", true);
			return JsonUtils.toJson(map);
		}catch(Exception ex){
			//view.setContent("保存不成功");
			ex.printStackTrace();
			map.put("success", false);
			return JsonUtils.toJson(map);
		}		
		//return new ModelAndView(view);
	}

	
	
}




