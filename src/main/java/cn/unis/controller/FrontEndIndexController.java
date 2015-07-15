package cn.unis.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.ruiyi.base.entity.Mcode;
import cn.ruiyi.base.service.mcode.McodeService;
import cn.ruiyi.base.service.mcode.McodeUtil;
import cn.ruiyi.base.util.StringUtil;
import cn.ruiyi.base.web.mvc.Paginate;
import cn.unis.entity.Game;
import cn.unis.entity.News;
import cn.unis.entity.PPT;
import cn.unis.service.impl.NewsService;
import cn.unis.service.impl.PPTService;
import cn.unis.service.interfaces.IGameCenterService;
import cn.unis.service.interfaces.IGameService;
import cn.unis.service.interfaces.IPictureService;
import cn.unis.service.oauth10a.Walo3LeggedService;
import cn.unis.transit.BaseReturnValue;
import cn.unis.transit.token.TokenBalance;
import cn.unis.utils.MyPageRequest;

import com.google.common.collect.Maps;

/**
 *
 * Title: 前台index Project: unis Type: cn.unis.base.web.LoginController Author:
 * eric Create: 2012-11-8 下午05:58:03 Copyright: Copyright (c) 2012 Company: unis
 *
 */
@Controller
@RequestMapping(value = "/frontEnd")
public class FrontEndIndexController {

	@Autowired
	private PPTService pptService;
	@Autowired
	private NewsService newsService;
	@Resource
	private McodeService mcodeService;
	@Resource(name = "waloGameCenterServiceAdapter")
	private IGameCenterService iGamecenterService;
	@Autowired
	private IGameService iGameService;
	@Autowired
	private IPictureService iPictureService;
	@Autowired
	private Walo3LeggedService walo3LeggedService;



	/**
	 * 前台游戏描述
	 * @param id 游戏id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/game/description", method = RequestMethod.GET)
	public String gameDescription(Long id, Model model) {
		/**
		 * 统计人气
		 */
		//iGameService.countGamePopularity(id);
		
		Game game = iGameService.findById(id);
		if (game != null) {
			game.setType(mcodeService.findByMtypeAndMvalue("GAME_TYPE",game.getType()).getMkey());
			model.addAttribute("qrcodeurl", game.getQrcodeUrl());
			model.addAttribute("game", game);
			return "unis/frontEnd/game/gamedesc";
		}else{
			return "unis/frontEnd/index";
		}

	}

	/**
	 * 前台index
	 *
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String login(Model model) {
		//iGamecenterService.testJsonString();
		List<PPT> ppt = pptService.getAllPPTWithEnabledStatus();
//		List<Game> game = iGameService.getAllWithEnabledStatus();
		List<Game> game = iGameService.getIndexGameList(8,"sortNumber");
		List<News> news = newsService.getForIndexNewList(9);
		List<Game> rank = iGameService.getIndexGameList(12,"downloadTimes");
		model.addAttribute("ppt", ppt);
		model.addAttribute("game", game);
		model.addAttribute("news", news);
		model.addAttribute("rank", rank);
		// System.out.println("test:"+iGamecenterService.testJsonString());
		// Token requestToken = walo3LeggedService.getRequestToken();
		//BaseReturnValue<TokenBalance> tokenBalance = iGamecenterService.getTokenBalanceByUserKey("0a26071099692979d609f47c442549a1");
		
		return "unis/frontEnd/index/index";
	}

	/**
	 * 前台 获得新闻Html
	 *
	 * @return
	 */
	@RequestMapping(value = "/news/show/{id}", method = RequestMethod.GET)
	public String getNewsHtml(@PathVariable Long id, Model model) {
		// System.out.println(mcodeService.getMbHtmlSelect(McodeUtil.GAME_TYPE,null));
		News news = newsService.findById(id);
		news.setTagChinese(mcodeService.findByMtypeAndMvalue(
				McodeUtil.NEWS_TYPE, news.getTag()).getMkey());
		Long prewId = newsService.findPrewNewsId(news);
		Long nextId = newsService.findNextNewsId(news);
		List<News> relatedNewsList = newsService.getRelatedNewList(news
				.getTag());
		List<News> otherNewsList = newsService.getOtherNewList(news.getTag());
		news.setContentWithoutHtml(StringUtil.html2Text(news.getContent()));
		model.addAttribute("news", news);
		model.addAttribute("prewId", prewId == null ? 0L : prewId);
		model.addAttribute("nextId", nextId == null ? 0L : nextId);
		model.addAttribute("relatedNewsList", relatedNewsList);
		model.addAttribute("otherNewsList", otherNewsList);
		// model.addAttribute("gameCenterInfo",
		// iGamecenterService.getGameCenterJsonByGameCenterKey("7e8621ac566943e4f3f9464859558d32")
		// +iGamecenterService.getGameCenterJsonByGameCenterKey("a449623b427d90567555c972dfb77344")
		// +iGamecenterService.getGameCenterJsonByGameCenterKey("3c5af76f64db081dd35fa7b12f98c281")
		// );
		// //b28ebd4c0fcfd722c07b0aa576a7713a produnction才能用的game_center_key
		// model.addAttribute("scoresHtml", iGamecenterService.getScoresHtml());
		// System.out.println(iGamecenterService.getScoresHtml());
		// System.out.println("==========>" +
		// iGamecenterService.getCenterListByProvince("810000"));
		// String jsonString =
		// iGamecenterService.getArcadeMachineOfGameCenter("7e8621ac566943e4f3f9464859558d32");
		// BaseReturnValues<Arcade> arcades =
		// iGamecenterService.getArcadeMachinesOfGameCenter("7e8621ac566943e4f3f9464859558d32",
		// 1, Integer.MAX_VALUE);
		// JsonUtils.fromJson(jsonString, new
		// TypeReference<BaseReturnValues<Arcade>>(){});
		// model.addAttribute("arcades", arcades);

		// BaseReturnValues<String> user_key_list =
		// iGamecenterService.queryUserList(1, 10, "game_center_key",
		// "a449623b427d90567555c972dfb77344");
		// for(String user_key : user_key_list.getMsg()){
		// BaseReturnValue<UserDetailInfo> user =
		// iGamecenterService.getUserDetailInfoByUserKey(user_key);
		// BaseReturnValue<UserGameCenterBalance> userGameCenterBalance =
		// iGamecenterService.getBalanceByUserKeyAndGameCenterKey(user.getMsg().getKey(),
		// "7e8621ac566943e4f3f9464859558d32");
		// System.out.println("用户名:"+user.getMsg().getUsername());
		// System.out.println("用户名2:"+user.getMsg().getHandle_name());
		// System.out.println("注册时间："+user.getMsg().getRegistered_at());
		// System.out.println("期满时间："+user.getMsg().getExpire());
		// System.out.println("代币数："+((userGameCenterBalance.getErr_num()==404)?"":userGameCenterBalance.getMsg().getToken()));
		// System.out.println("彩票数："+((userGameCenterBalance.getErr_num()==404)?"":userGameCenterBalance.getMsg().getTicket()));
		//
		// }
		// BaseReturnValues<Arcade> arcades =
		// iGamecenterService.getArcadeMachinesOfGameCenter("7e8621ac566943e4f3f9464859558d32",
		// 1, 20);
		// for(Arcade arcade : arcades.getMsg()){
		// System.out.println(arcade.getDevice_id()+" "+arcade.getCuk()+" "+arcade.getConsole_index()+" "+arcade.getArcade_status()+" "+arcade.getConnection_state());
		// }
		// System.out.println(iGamecenterService.testJsonString());

		// String str =
		// iGamecenterService.getUserInfoByUserKey("1215410ebc20ec9a6a00f8290c350d96");
		// System.out.println(str);
		return "unis/frontEnd/news/single";
	}

	/**
	 * 前台 获得新闻分页列表
	 *
	 * @return
	 */
	@RequestMapping(value = "/news/list", method = RequestMethod.GET)
	public String getNewsList(HttpServletRequest request, Model model,
			String tag, Integer page, MyPageRequest myPageRequest) {
		page = page == null ? 1 : page;
		tag = tag == null ? "" : tag;
		// PageRequest pageable = new PageRequest(page - 1, Constants.PAGE_SIZE,
		// Sort.Direction.DESC, "sortNumber");
		PageRequest pageable = new PageRequest(myPageRequest.getPageNo() - 1,
				myPageRequest.getPageSize(), Sort.Direction.DESC, "sortNumber");
		Page<News> paginate;
		if (!"".equals(tag)) {
			paginate = newsService.findNewsPagedByTag(pageable, tag);
		} else {
			paginate = newsService.findAllNewsPaged(pageable);
		}
		for (News news : paginate) {
			news.setContentWithoutHtml(StringUtil.html2Text(news.getContent()));
			news.setTagChinese(mcodeService.findByMtypeAndMvalue(
					McodeUtil.NEWS_TYPE, news.getTag()).getMkey());
		}
		String roll = Paginate.getPage(request, page, paginate, "rollDirect",
				"");
		model.addAttribute("page", paginate.getContent());
		model.addAttribute("pageSize", paginate.getContent().size());
		model.addAttribute("roll", roll);
		model.addAttribute("tag", tag);
		model.addAttribute("lastestFiveNews", newsService.getForIndexNewList(5));
		if (!"".equals(tag)) {
			model.addAttribute("tagChinese",
					mcodeService.findByMtypeAndMvalue(McodeUtil.NEWS_TYPE, tag)
							.getMkey());
		}
		long total = paginate.getTotalElements();
		myPageRequest.setTotalCount(total);
		model.addAttribute("pageRequest", myPageRequest);
		return "unis/frontEnd/news/article_list";
	}

	@RequestMapping(value = "/game/list", method = RequestMethod.GET)
	public String gameList(Model model, MyPageRequest myPageRequest,
			@RequestParam(value = "f1", required = false) String f1,//筛选条件1 全部or热门游戏or最新游戏
			@RequestParam(value = "f2", required = false) String f2) {//筛选条件2 全部or益智游戏or动作游戏or竞速游戏or格斗游戏or模块设计or其他类型
		PageRequest pageable = new PageRequest(myPageRequest.getPageNo() - 1,
				myPageRequest.getPageSize(), Sort.Direction.DESC, "sortNumber");
		Page<Game> paginate;
		if (StringUtils.isEmpty(f1)) {
			f1 = "0";
		}
		if (StringUtils.isEmpty(f2)) {
			f2 = "0";
		}
		String queryType = f2.equals("0") ? "-1" : f2;

		if (f1.equals("0")) {
			paginate = iGameService.findAll(-1, -1, queryType, pageable);
		} else if (f1.equals("1")) {
			paginate = iGameService.findAll(-1, 1, queryType, pageable);
		} else if (f1.equals("2")) {
			paginate = iGameService.findAll(1, -1, queryType, pageable);
		} else {
			paginate = iGameService.findAll(1, 1, queryType, pageable);
		}

		List<Mcode> gameTypelist = mcodeService.findByMtype("GAME_TYPE");
		Map<String, String> typeValue = Maps.newHashMap();
		for (Mcode mcode : gameTypelist) {
			typeValue.put(mcode.getMvalue(), mcode.getMkey());
		}

		for (Game game : paginate.getContent()) {
			game.setType(typeValue.get(game.getType()));
		}

		model.addAttribute("page", paginate.getContent());
		long total = paginate.getTotalElements();
		myPageRequest.setTotalCount(total);
		model.addAttribute("pageRequest", myPageRequest);

		model.addAttribute("gameType", gameTypelist);
		model.addAttribute("select1", f1);
		model.addAttribute("select2", f2);
		return "unis/frontEnd/game/gameslist";
	}

}
