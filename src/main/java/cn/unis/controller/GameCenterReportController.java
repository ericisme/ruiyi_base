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
import cn.unis.service.impl.UserGameCenterService;
import cn.unis.service.interfaces.IGameCenterService;
import cn.unis.transit.Arcade;
import cn.unis.transit.BaseReturnValue;
import cn.unis.transit.BaseReturnValues;
import cn.unis.transit.UserDetailInfo;
import cn.unis.transit.WaloGameSimpleInfo;
import cn.unis.transit.userWallet.UserWallet;
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
public class GameCenterReportController {	
	
	@Resource
	private McodeService mcodeService;
	@Resource(name = "waloGameCenterServiceAdapter")
	private IGameCenterService iGamecenterService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserGameCenterService userGameCenterService;

	
	/**
	 * 交易报表 资讯,列表
	 * @return
	 */
	@RequestMapping(value = "/frontEnd/gameCenter/reports", method = RequestMethod.GET)
	public String reports(Model model, HttpServletRequest request) {		
		Object shiroUser = SecurityUtils.getSubject().getPrincipal();
		User user = new User();	
		if(shiroUser!=null){
			user = accountService.findUserByLoginName(shiroUser.toString());
		}
		model.addAttribute("userType", user.getUsertype());		
		return "unis/frontEnd/gameCenterReport/report_list";
	}
	

	/**
	 * 總派票/總投幣 (不同遊戲)
	 */
	@RequestMapping(value = "/frontEnd/notDecoratorsGameCenter/report/TX_REPORT_BY_GAME", method = RequestMethod.GET)
	public String tx_report_by_game(Model model, HttpServletRequest request) {
		String selectedGameCenterKey = (String) request.getSession().getAttribute("selected_game_center_key");
		model.addAttribute("reportJson", iGamecenterService.reprotTxByGame(selectedGameCenterKey));
		return "unis/frontEnd/gameCenterReport/report_tx_by_game";
	}
	
	/**
	 * 選擇遊戲(派票/投幣)(不同遊戲),index
	 */
	@RequestMapping(value = "/frontEnd/notDecoratorsGameCenter/report/GAME_DAILY_TX_REPORT_INDEX_SELECTED_GAME", method = RequestMethod.GET)
	public String game_daily_tx_report_index_selected_game(Model model, HttpServletRequest request) {
		String selectedGameCenterKey = (String) request.getSession().getAttribute("selected_game_center_key");
		BaseReturnValues<WaloGameSimpleInfo> waloGames = iGamecenterService.getWaloArcadeGamesByGameCenterKey(selectedGameCenterKey);		
		model.addAttribute("games", waloGames.getMsg());
		return "unis/frontEnd/gameCenterReport/report_game_daily_tx_report_selected_game";
	}
	
	/**
	 * 選擇遊戲(派票/投幣)(不同遊戲),query
	 * ajax 异步方法，直接返回json
	 */	
	@RequestMapping(value = "/frontEnd/notDecoratorsGameCenter/report/GAME_DAILY_TX_REPORT_QUERY_SELECTED_GAME")
	@ResponseBody
	public String game_daily_tx_report_query_selected_game(HttpServletRequest request, String begin_date, String until_date, String consumer_key) {
		String selectedGameCenterKey = (String) request.getSession().getAttribute("selected_game_center_key");
		String reportJson = iGamecenterService.game_daily_tx_report(selectedGameCenterKey, begin_date, until_date, consumer_key);
		return reportJson;
	}
	
	
	/**
	 * 指定日期(派票/投幣)(不同遊戲),index
	 */
	@RequestMapping(value = "/frontEnd/notDecoratorsGameCenter/report/GAME_DAILY_TX_REPORT_INDEX", method = RequestMethod.GET)
	public String game_daily_tx_report_index(Model model, HttpServletRequest request) {
		return "unis/frontEnd/gameCenterReport/report_game_daily_tx_report";
	}
	/**
	 * 指定日期(派票/投幣)(不同遊戲),query
	 * ajax 异步方法，直接返回json
	 */	
	@RequestMapping(value = "/frontEnd/notDecoratorsGameCenter/report/GAME_DAILY_TX_REPORT_QUERY")
	@ResponseBody
	public String game_daily_tx_report_query(HttpServletRequest request, String begin_date, String until_date) {
		String selectedGameCenterKey = (String) request.getSession().getAttribute("selected_game_center_key");
		String reportJson = iGamecenterService.game_daily_tx_report(selectedGameCenterKey, begin_date, until_date);
		return reportJson;
	}
	
	
	/**
	 * daily個別機台派票/投币,index
	 */
	@RequestMapping(value = "/frontEnd/notDecoratorsGameCenter/report/ARCADE_DAILY_TX_REPORT_INDEX", method = RequestMethod.GET)
	public String arcade_daily_tx_report_index_selected_game(Model model, HttpServletRequest request) {
		String selectedGameCenterKey = (String) request.getSession().getAttribute("selected_game_center_key");
		List<Arcade> arcades = new ArrayList<Arcade>();
		boolean hasNextPage = true;
		BaseReturnValues<Arcade> arcade_list = iGamecenterService.getArcadeMachinesOfGameCenter(selectedGameCenterKey, 1, 30);
		arcades.addAll(arcade_list.getMsg());
		int page =2;
		while(hasNextPage){
			BaseReturnValues<Arcade> arcade_list_next_page = iGamecenterService.getArcadeMachinesOfGameCenter(selectedGameCenterKey, page, 30);
			page++;
			if(arcade_list_next_page.getMsg()==null?false:(arcade_list_next_page.getMsg().size()>0?true:false)){
				arcades.addAll(arcade_list_next_page.getMsg());
				hasNextPage = true;
			}else{
				hasNextPage = false;
			}
		}		
		model.addAttribute("arcades", arcades);
		return "unis/frontEnd/gameCenterReport/report_arcade_daily_tx_report";
	}
	
	/**
	 * daily個別機台派票/投币,query
	 * ajax 异步方法，直接返回json
	 */	
	@RequestMapping(value = "/frontEnd/notDecoratorsGameCenter/report/ARCADE_DAILY_TX_REPORT_QUERY")
	@ResponseBody
	public String arcade_daily_tx_report_query_selected_game(HttpServletRequest request, String begin_date, String until_date, String cuk) {
		String reportJson = iGamecenterService.arcade_daily_tx_report(cuk, begin_date, until_date);
		return reportJson;
	}
	
	
	
	/**
	 * 個別帳號錢包,index
	 */
	@RequestMapping(value = "/frontEnd/notDecoratorsGameCenter/report/USER_WALLETS_TX_REPORT_INDEX", method = RequestMethod.GET)
	public String user_wallets_tx_report_index(Model model, HttpServletRequest request) {
		return "unis/frontEnd/gameCenterReport/report_user_wallets_tx_report_2";
	}
	
	/**
	 * 個別帳號錢包,query
	 * ajax 异步方法，直接返回json
	 */	
	@RequestMapping(value = "/frontEnd/notDecoratorsGameCenter/report/USER_WALLETS_TX_REPORT_QUERY")
	@ResponseBody
	public String user_wallets_tx_report_query(HttpServletRequest request, String filter_key, String filter_value, Integer page) {	
		if(filter_value == null){
			filter_value ="";
		}
		BaseReturnValues<String> user_key_list;
		BaseReturnValues<String> user_key_list_next_page;
		if("".equals(filter_value)){
			user_key_list = iGamecenterService.queryUserList(page, 10);
			user_key_list_next_page = iGamecenterService.queryUserList(page+1, 10);
		}else{
			user_key_list = iGamecenterService.queryUserList(page, 10, filter_key, filter_value);
			user_key_list_next_page = iGamecenterService.queryUserList(page+1, 10, filter_key, filter_value);
		}
		List<UserDetailInfo> userPage = new ArrayList<UserDetailInfo>();
		//List<List<UserWallets>> walletsList = new ArrayList<List<UserWallets>>();		
		List<UserWallet> walletList = new ArrayList<UserWallet>();				
		if(user_key_list.getMsg()!=null){
			for(String user_key : user_key_list.getMsg()){
				BaseReturnValue<UserDetailInfo> user = iGamecenterService.getUserDetailInfoByUserKey(user_key,"key,username,handle_name");
				userPage.add(user.getMsg());
				//BaseReturnValues<UserWallets> user_wallets = iGamecenterService.getUserWalletsByUserKey(user_key);
				BaseReturnValue<UserWallet> user_wallet = iGamecenterService.getUserWalletsByUserKey(user_key);
				//walletsList.add( user_wallet.getMsg());
				walletList.add(user_wallet.getMsg());
			}		
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userList", userPage);
		map.put("walletsList", walletList);
		map.put("hasNextPage", user_key_list_next_page.getMsg()==null?false:(user_key_list_next_page.getMsg().size()>0?true:false));	
		String reportJson = JsonUtils.toJson(map);
		return reportJson;
	}
	
	
	

	
}
