package cn.unis.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import cn.unis.entity.ClickLog;
import cn.unis.service.impl.ClickLogService;
import cn.unis.service.interfaces.IGameService;

/**
 * 用于统计各种点击量，下载量
 * @author Administrator
 */
@Controller
@RequestMapping(value = "/frontEnd/counts")
public class CountsController {

	@Autowired
	private IGameService iGameService;
	@Autowired
	private ClickLogService clickLogService;
	
	
	/**
	 * 统计游戏下载量
	 * @param platformAndMarket_id
	 */
	@RequestMapping(value = "/game/countGameDownloadTimes", method = RequestMethod.GET)
	@ResponseBody
	public void countGameDownloadTimes(Long platformAndMarket_id,HttpServletRequest req){
		iGameService.countGameDownloadTimes(platformAndMarket_id);
		ClickLog cl = new ClickLog(ClickLog.GAME_DOWNLOAD,platformAndMarket_id, 1, new Date(), req.getSession().getId());
		clickLogService.save(cl);
	}
	
	
	/**
	 * 统计游戏点击量(人气)
	 */
	@RequestMapping(value = "/game/countGamePopularity", method = RequestMethod.GET)
	@ResponseBody
	public void countGamePopularity(Long game_id,HttpServletRequest req){
		iGameService.countGamePopularity(game_id);
		ClickLog cl = new ClickLog(ClickLog.GAME_POPULARITY,game_id, 1, new Date(), req.getSession().getId());
		clickLogService.save(cl);
	}
	
	
	
}
