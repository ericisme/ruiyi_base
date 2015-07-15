package cn.unis.api4walo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.unis.api4walo.dto.DateAndTimesDto;
import cn.unis.api4walo.dto.GameDownloadTimesReportDto;
import cn.unis.entity.Game;
import cn.unis.service.impl.ClickLogService;
import cn.unis.service.impl.GameService;
import cn.unis.utils.JsonUtils;


/**
 * 报表数据api
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/api/public")
public class ReportController {

	@Autowired
	private GameService gameService;
	@Autowired
	private ClickLogService clickLogService;
	
	/**
	 * 获得游戏下载报表
	 */
	@RequestMapping(value="/games_download_report", method={RequestMethod.GET,RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String games_download_report(Long game_id, String begin_date, String end_date){
		List<GameDownloadTimesReportDto> GameDownloadTimesReportDto_list = new ArrayList<GameDownloadTimesReportDto>();
		if(game_id==null){
			List<Game> game_list = gameService.getAllWithEnabledStatus();
			for(Game game : game_list){
				List<DateAndTimesDto> dateAndTimesDto_list = clickLogService.listDateAndTimesListByGameId(game.getId(), begin_date, end_date);	
				GameDownloadTimesReportDto_list.add(new GameDownloadTimesReportDto(game.getId(),game.getNameForCH(),game.getNameForEN(), dateAndTimesDto_list));
			}
		}else{
			Game game = gameService.findById(game_id);
			if(game!=null){
				List<DateAndTimesDto> dateAndTimesDto_list = clickLogService.listDateAndTimesListByGameId(game_id, begin_date, end_date);	
				GameDownloadTimesReportDto_list.add(new GameDownloadTimesReportDto(game_id,game.getNameForCH(),game.getNameForEN(), dateAndTimesDto_list));
			}
		}
		return JsonUtils.toJson(GameDownloadTimesReportDto_list);		
	}
	
	
}
