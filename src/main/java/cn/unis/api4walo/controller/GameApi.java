package cn.unis.api4walo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.entity.Mcode;
import cn.ruiyi.base.service.mcode.McodeService;
import cn.ruiyi.base.util.DateUtil;
import cn.ruiyi.base.util.PathUtil;
import cn.unis.api4walo.dto.GameDto;
import cn.unis.api4walo.dto.GamePictureDto;
import cn.unis.api4walo.dto.GamePlatformAndMarketDto;
import cn.unis.api4walo.dto.GameTypeDto;
import cn.unis.api4walo.dto.PageDto;
import cn.unis.entity.Game;
import cn.unis.entity.Picture;
import cn.unis.entity.PlatformAndMarket;
import cn.unis.service.impl.GameService;
import cn.unis.utils.JsonUtils;

import com.google.common.collect.Lists;

/**
 * 提供给walo app游戏接口
 * @author unis
 *
 */
@Controller
public class GameApi {
	
	
	/**
	 * 游戏分页列表-排序方式
	 */
	private final static String POPULARITY = "1";			//按人气
	private final static String DOWNLOADTIMES = "2";		//按下载量
	private final static String RELEASEDATE = "3";		//按上线时间
	private final static String EDITORRECOMMENDS = "4";	//按编辑推荐度，现暂用game的排序号
	private final static String domain = PathUtil.getConfigResource("CURRENT_SYSTEM_DOMAIN");
	private final static String platformAndMarket_downloader_url = "/frontEnd/downloader/platformAndMarket/";

	@Autowired
	private McodeService mcodeService;
	@Autowired
	private GameService gameService;
	
	
	/**
	 * 获得游戏分类列表
	 * @return
	 */
	@RequestMapping(value="/api/public/games/types",method={RequestMethod.GET,RequestMethod.POST},produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getGameTypeList(){
		List<Mcode> mcodeList = mcodeService.findByMtype("GAME_TYPE");
		List<GameTypeDto> gameTypeDtos = Lists.newArrayList();
		for(Mcode  mcode : mcodeList){
			GameTypeDto gameTypeDto = new GameTypeDto();
			gameTypeDto.setName(mcode.getMkey());
			gameTypeDto.setValue(mcode.getMvalue());
			gameTypeDtos.add(gameTypeDto);
		}
		String response = JsonUtils.toJson(gameTypeDtos);

		return response;
	}
	
	
	/**
	 * 按条件获得游戏分页列表
	 * 
	 * @param catId 目录
	 * @param sortId 排序方式 
	 * @param searchName 名字匹配（模糊）
	 * @param isNew 是否新游戏; 非必填，1为是，0为不是
	 * @param isHot 是否热门游戏; 非必填，1为是，0为不是
	 * @param isLinked 是否联动游戏; 非必填，1为是，0为不是
	 * @param pageNum 第几页, 不填写默认第1页
	 * @param rowsPerPage 每页记录数，不填写默认1页10条记录
	 * @return
	 */
	@RequestMapping(value="/api/public/games",method={RequestMethod.GET,RequestMethod.POST},produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getGameList(
				@RequestParam(value="catId",required=false)String catId,
				@RequestParam(value="sortId",required=false)String sortId,
				@RequestParam(value="searchName",required=false)String searchName,
				@RequestParam(value="isNew",required=false)Integer isNew,
				@RequestParam(value="isHot",required=false)Integer isHot,
				@RequestParam(value="isLinked",required=false)Integer isLinked,
				@RequestParam(value="pageNum",required=false)Integer pageNum,
				@RequestParam(value="rowsPerPage",required=false)Integer rowsPerPage
				){		
		
		//默认 上架时间
		String[] sortProperties = new String[] { "releaseDate" };		
		if(sortId!=null){
			if(sortId.equals(POPULARITY))//按从气
				 sortProperties = new String[] { "popularity" };
			if(sortId.equals(DOWNLOADTIMES))//按下载量
				 sortProperties = new String[] { "downloadTimes" };
			if(sortId.equals(RELEASEDATE))//按上线时间
				 sortProperties = new String[] { "releaseDate" };
			if(sortId.equals(EDITORRECOMMENDS))//按编辑推荐
				 sortProperties = new String[] { "sortNumber" };
		}
		Pageable pageable = new PageRequest(pageNum==null?0:(Math.abs(pageNum - 1)), rowsPerPage==null?10:Math.abs(rowsPerPage), Sort.Direction.DESC, sortProperties);
		Page<Game> games = gameService.findAll(catId, searchName, isNew, isHot, isLinked, pageable);
		PageDto<GameDto> gamesPage = new PageDto<GameDto>(games.getNumber()+1, games.getSize(), games.getTotalPages(),games.getTotalElements(), entity2Dto(games.getContent()));
		return JsonUtils.toJson(gamesPage);
	}

	
	/**
	 * 根据id获得一条游戏详细记录
	 * @PathVariable id, 游戏id
	 * @return
	 */
	@RequestMapping(value="/api/public/games/{id}",method={RequestMethod.GET},produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getOneGameById(@PathVariable(value="id") Long id){
		Game game = gameService.findById(id);
		if(game==null){
			return JsonUtils.toJson(null);
		}		
		return JsonUtils.toJson(entity2Dto(game));
	}
	
	private String getMkey(List<Mcode> mcodeList , String value){
		for(Mcode mcode : mcodeList){
			if(mcode.getMvalue().equals(value)){
				return mcode.getMkey();
			}
		}
		return "";
	}
	
	private List<GameDto> entity2Dto(List<Game> games){
		List<GameDto> gameDtos = Lists.newArrayList();
		//List<Mcode> mcodeList = mcodeService.findByMtype("GAME_TYPE");
		for(Game game : games){

			GameDto gameDto = new GameDto();
			gameDto = entity2Dto(game);
			gameDtos.add(gameDto);
		}
		return gameDtos;
	}
	
	private GameDto entity2Dto(Game game){
		
		GameDto gameDto = new GameDto();
		gameDto.setId(game.getId());
		gameDto.setCatId(game.getType());
		gameDto.setCatName(getMkey(mcodeService.findByMtype("GAME_TYPE"),game.getType()));
		gameDto.setNameForCH(game.getNameForCH());
		gameDto.setNameForEN(game.getNameForEN());
		gameDto.setPopularity((long)game.getPopularity());
		gameDto.setIconPath(domain + game.getIconPath());
		gameDto.setIsHot(game.getIsHot());
		gameDto.setIsNew(game.getIsNew());
		gameDto.setIsLinked(game.getIsLinked());
		gameDto.setReleaseDate(DateUtil.getDate(game.getReleaseDate()));
		gameDto.setDescription(game.getDescription());
		gameDto.setDownloadTimes(game.getDownloadTimes());
		gameDto.setIos_size(game.getIos_size());
		gameDto.setAndroid_size(game.getAndroid_size());

		List<GamePictureDto> pictureList = Lists.newArrayList();
		for(Picture picture : game.getPictureList()){
			GamePictureDto gamePictureDto = new GamePictureDto();
			gamePictureDto.setUrl(domain  + picture.getUrl());
			pictureList.add(gamePictureDto);
		}
		List<GamePlatformAndMarketDto> platformAndMarkets = Lists.newArrayList();
		for(PlatformAndMarket platformAndMarket : game.getPlatformAndMarkets()){
			GamePlatformAndMarketDto gamePlatformAndMarketDto = new GamePlatformAndMarketDto();
			gamePlatformAndMarketDto.setDownloadTimes(platformAndMarket.getDownloadTimes());
			

			//改用downloader 下载
			gamePlatformAndMarketDto.setDownloadUrl(domain+platformAndMarket_downloader_url+platformAndMarket.getId());
			String downloadUrl =  platformAndMarket.getDownloadUrl();
			//if(downloadUrl.startsWith("/")){
			//	gamePlatformAndMarketDto.setDownloadUrl(domain + platformAndMarket.getDownloadUrl());
			//}else{
			//	gamePlatformAndMarketDto.setDownloadUrl(platformAndMarket.getDownloadUrl());
			//}
			
			gamePlatformAndMarketDto.setMarketName(platformAndMarket.getMarketName());
			gamePlatformAndMarketDto.setPlatform(platformAndMarket.getPlatform());
			platformAndMarkets.add(gamePlatformAndMarketDto);
		}
		gameDto.setPictureList(pictureList);
		gameDto.setPlatformAndMarkets(platformAndMarkets);		
		
		return gameDto;
	}

}
