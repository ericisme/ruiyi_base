package cn.unis.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ruiyi.base.entity.User;
import cn.ruiyi.base.service.account.AccountService;
import cn.ruiyi.base.service.mcode.McodeService;
import cn.unis.entity.UserGameCenter;
import cn.unis.service.impl.UserGameCenterService;
import cn.unis.service.interfaces.IGameCenterService;
import cn.unis.transit.Arcade;
import cn.unis.transit.BaseReturnValue;
import cn.unis.transit.BaseReturnValues;
import cn.unis.transit.GameCenterSimpleInfo;
import cn.unis.transit.UserDetailInfo;
import cn.unis.transit.WaloGameSimpleInfo;
import cn.unis.transit.userWallet.UserGameCenterWallet;
import cn.unis.utils.JsonUtils;

/**
 * 
 * Title: 		前台游乐场管理功能
 * Project: 	unis
 * Type:		cn.unis.base.web.LoginController
 * Author:		eric
 * Create:	 	2012-11-8 下午05:58:03
 * Copyright: 	Copyright (c) 2012
 * Company:		unis
 *
 */
@Controller
@RequestMapping(value = "")
public class GameCenterController {	

	@Resource
	private McodeService mcodeService;
	@Resource(name = "waloGameCenterServiceAdapter")
	private IGameCenterService iGamecenterService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserGameCenterService userGameCenterService;
	
	/**
	 * 前台index
	 * @return
	 */
	@RequestMapping(value = "/frontEnd/gameCenter/index", method = RequestMethod.GET)
	public String index(Model model) {
		//System.out.println("test:"+iGamecenterService.testJsonString());
		return "unis/frontEnd/gameCenter/index";
	}
	
	/**
	 * 账号资讯,详细，内跳页面，不做decorators
	 * @return
	 */
	@RequestMapping(value = "/frontEnd/notDecoratorsGameCenter/userDetail", method = RequestMethod.GET)
	public String userDetail(Model model, HttpServletRequest request, String user_key) {
		//System.out.println("user_key:"+user_key);
		String selectedGameCenterKey = (String) request.getSession().getAttribute("selected_game_center_key");
		BaseReturnValue<UserDetailInfo> user = iGamecenterService.getUserDetailInfoByUserKey(user_key,"key,username,handle_name,grade,email,phone,timezone,country,lang,personal_status,registered_arcades,achievement_score,accumulated_ascore,registered_at,expire,status");
		
		//BaseReturnValue<UserGameCenterBalance> userBalance = iGamecenterService.getBalanceByUserKeyAndGameCenterKey(user_key, selectedGameCenterKey);
		BaseReturnValue<UserGameCenterWallet> userGameCenterWallet = iGamecenterService.getBalanceByUserKeyAndGameCenterKey(user_key, selectedGameCenterKey);
		BaseReturnValues<WaloGameSimpleInfo> waloGames = iGamecenterService.getWaloArcadeGamesByGameCenterKey(selectedGameCenterKey);

		for(WaloGameSimpleInfo game: waloGames.getMsg()){
			BaseReturnValues<WaloGameSimpleInfo> userLoginsList = iGamecenterService.getUserLoginCountByGameCenterKeyAndGameConsumerKey(user_key, selectedGameCenterKey, game.getConsumer_key());
			if(userLoginsList.getMsg()==null){
				game.setLogin_count(0);
			}else{
				game.setLogin_count(userLoginsList.getMsg().size()>0?userLoginsList.getMsg().get(0).getLogin_count():0);
			}
		}		
		
		model.addAttribute("user", user.getMsg());
		model.addAttribute("userGameCenterWallet", userGameCenterWallet.getMsg());
		model.addAttribute("waloGames", waloGames.getMsg());
		return "unis/frontEnd/gameCenter/user_detail";
	}
	/**
	 * 账号资讯,列表
	 * @return
	 */
	@RequestMapping(value = "/frontEnd/gameCenter/users", method = RequestMethod.GET)
	public String users(Model model, HttpServletRequest request, Integer page) {
		if(page==null){
			page=1;
		}
		String selectedGameCenterKey = (String) request.getSession().getAttribute("selected_game_center_key");
		//System.out.println("session selectedGameKey:"+selectedGameKey);		
		BaseReturnValues<String> user_key_list = iGamecenterService.queryUserList(page, 10, "game_center_key", selectedGameCenterKey);
		BaseReturnValues<String> user_key_list_next_page = iGamecenterService.queryUserList(page+1, 10, "game_center_key", selectedGameCenterKey);
		List<UserDetailInfo> userPage = new ArrayList<UserDetailInfo>();
		for(String user_key : user_key_list.getMsg()){
			BaseReturnValue<UserDetailInfo> user = iGamecenterService.getUserDetailInfoByUserKey(user_key,"key,username,handle_name,achievement_score,accumulated_ascore,registered_at");
			userPage.add(user.getMsg());
		}
		model.addAttribute("userPage", userPage);
		model.addAttribute("currPageNum", page);
		model.addAttribute("hasNextPage", user_key_list_next_page.getMsg()==null?false:(user_key_list_next_page.getMsg().size()>0?true:false));	
		return "unis/frontEnd/gameCenter/user_list";
	}
	
	/**
	 * 机台资讯,列表
	 * @return
	 */
	@RequestMapping(value = "/frontEnd/gameCenter/arcades", method = RequestMethod.GET)
	public String arcades(Model model, HttpServletRequest request, Integer page) {
		if(page==null){
			page=1;
		}
		String selectedGameCenterKey = (String) request.getSession().getAttribute("selected_game_center_key");
		System.out.println("session selectedGameKey:"+selectedGameCenterKey);	
		BaseReturnValues<Arcade> arcade_list = iGamecenterService.getArcadeMachinesOfGameCenter(selectedGameCenterKey, page, 10);
		BaseReturnValues<Arcade> arcade_list_next_page = iGamecenterService.getArcadeMachinesOfGameCenter(selectedGameCenterKey, page+1, 10);
		model.addAttribute("arcade_list", arcade_list.getMsg());
		model.addAttribute("currPageNum", page);
		model.addAttribute("hasNextPage", arcade_list_next_page.getMsg()==null?false:(arcade_list_next_page.getMsg().size()>0?true:false));	
		return "unis/frontEnd/gameCenter/arcade_list";
	}
	
	
	/**
	 * checkGameCenters
	 */
	@RequestMapping(value = "/frontEnd/gameCenter/checkGameCenters")
	@ResponseBody
	public String checkLogin(HttpServletRequest request){
		String selectedGameKey = (String) request.getSession().getAttribute("selected_game_center_key");
		String selectedGameName = "";
		Map<String,Object> map = new HashMap<String,Object>();	
		Object shiroUser = SecurityUtils.getSubject().getPrincipal();
		User user = accountService.findUserByLoginName(shiroUser.toString());		
		map.put("userType", user.getUsertype());
		if(user.getUsertype()==1){
			return JsonUtils.toJson(map);
		}
		if(user.getUsertype()==2){
			List<UserGameCenter> gameCenters = userGameCenterService.findByUser(user);			
			for(UserGameCenter ugc : gameCenters){
				ugc.setGameCenterName(iGamecenterService.getGameCenter(ugc.getGameCenterKey()).getMsg().getName());
			}
			map.put("gameCenters", gameCenters);			
			//默认第一个
			if(gameCenters.size()>0){				
				if(selectedGameKey == null){
					selectedGameKey = gameCenters.get(0).getGameCenterKey();
					selectedGameName = gameCenters.get(0).getGameCenterName();
				}else{
					selectedGameName = iGamecenterService.getGameCenter(selectedGameKey).getMsg().getName();
				}
			}
		}
		if(user.getUsertype()==3){
			//默认中山世宇
			if(selectedGameKey == null){
				selectedGameKey = "a449623b427d90567555c972dfb77344";
				selectedGameName = iGamecenterService.getGameCenter("a449623b427d90567555c972dfb77344").getMsg().getName();
			}else{
				selectedGameName = iGamecenterService.getGameCenter(selectedGameKey).getMsg().getName();
			}
		}
		map.put("selectedGameCenterName", selectedGameName);
		request.getSession().setAttribute("selected_game_center_key", selectedGameKey);		
		return JsonUtils.toJson(map);
	}	
	
	/**
	 * 把选择好的game_center_key存入session,
	 * 当然在存入的时候会判断一下用户是否对这个game_center有访问权
	 */
	@RequestMapping(value = "/frontEnd/gameCenter/setSelectedGameCenterKeyToSession")
	@ResponseBody
	public String setSelectedGameCenterKeyToSession(HttpServletRequest request, String game_center_key){
		Map<String,Object> map = new HashMap<String,Object>();	
		map.put("success", false);
		Object shiroUser = SecurityUtils.getSubject().getPrincipal();
		User user = accountService.findUserByLoginName(shiroUser.toString());	
		if(user.getUsertype()==2){
			boolean flag = false;
			List<UserGameCenter> gameCenters = userGameCenterService.findByUser(user);
			for(UserGameCenter ugc : gameCenters){
				if(game_center_key.equals(ugc.getGameCenterKey())){
					flag = true;
				}
			}
			if(flag){
				request.getSession().setAttribute("selected_game_center_key", game_center_key);
				map.put("success", true);
			}
		}
		if(user.getUsertype()==3){
				request.getSession().setAttribute("selected_game_center_key", game_center_key);
				map.put("success", true);
		}
		return JsonUtils.toJson(map);		
	}
	
	
	/**
	 * 根据provinceCode获得Game Center html OPTION
	 * @param provinceCode
	 * @return
	 */
	@RequestMapping(value = "/frontEnd/gameCenter/getGameCentersByProvinceCode")
	@ResponseBody
	public String getGameCentersByProvinceCode(String provinceCode, int page){
		BaseReturnValues<GameCenterSimpleInfo> gameCenters= iGamecenterService.getGameCentersByProvinceCode(provinceCode, page, 10);
		BaseReturnValues<GameCenterSimpleInfo> gameCentersNextPage= iGamecenterService.getGameCentersByProvinceCode(provinceCode, page+1, 10);
		StringBuffer sb = new StringBuffer();
		if(gameCenters.getMsg()!=null){
			for(GameCenterSimpleInfo gcsi : gameCenters.getMsg()){
				sb.append("<li><a href=\"javascript:type3Select('").append(gcsi.getKey()).append("','").append(gcsi.getName()).append("')\">").append(gcsi.getName()).append("</a></li>");
			}		
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("gc_select", sb.toString());
		map.put("hasNextPage", gameCentersNextPage.getMsg()==null?false:true);
		return JsonUtils.toJson(map);
	}
	
	/**
	 * 根据cityCode获得Game Center html OPTION
	 * @param provinceCode
	 * @return
	 */
	@RequestMapping(value = "/frontEnd/gameCenter/getGameCentersByCityCode")
	@ResponseBody
	public String getGameCentersByCityCode(String cityCode, int page){
		BaseReturnValues<GameCenterSimpleInfo> gameCenters= iGamecenterService.getGameCentersByCityCode(cityCode, page, 10);
		BaseReturnValues<GameCenterSimpleInfo> gameCentersNextPage= iGamecenterService.getGameCentersByCityCode(cityCode, page+1, 10);
		StringBuffer sb = new StringBuffer();
		if(gameCenters.getMsg()!=null){
			for(GameCenterSimpleInfo gcsi : gameCenters.getMsg()){
				sb.append("<li><a href=\"javascript:type3Select('").append(gcsi.getKey()).append("','").append(gcsi.getName()).append("')\">").append(gcsi.getName()).append("</a></li>");
			}		
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("gc_select", sb.toString());	
		map.put("hasNextPage", gameCentersNextPage.getMsg()==null?false:true);
		return JsonUtils.toJson(map);
	}

	
	
	
}
