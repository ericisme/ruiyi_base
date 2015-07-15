package cn.unis.api4walo.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.ruiyi.base.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ruiyi.base.entity.Mcode;
import cn.ruiyi.base.service.mcode.McodeService;
import cn.ruiyi.base.service.mcode.McodeUtil;
import cn.ruiyi.base.util.PathUtil;
import cn.ruiyi.base.util.StringUtil;
import cn.unis.api4walo.dto.NewsDto;
import cn.unis.api4walo.dto.NewsTypeDto;
import cn.unis.api4walo.dto.PageDto;
import cn.unis.entity.News;
import cn.unis.service.impl.NewsService;
import cn.unis.utils.JsonUtils;

import com.google.common.collect.Lists;

/**
 * 提供给walo app 新闻(活动)接口
 * @author unis
 *
 */
@Controller
public class NewsApi {
	
	
	/**
	 * 新闻分页列表-排序方式
	 */
	private final static String RELEASEDATE = "3";		//按文章日期
	private final static String EDITORRECOMMENDS = "4";	//按编辑推荐度，现暂用news的排序号
	private final static String domain = PathUtil.getConfigResource("CURRENT_SYSTEM_DOMAIN");

	@Autowired
	private McodeService mcodeService;
	@Autowired
	private NewsService newsService;
	
	
	
	/**
	 * 获得新闻分类列表
	 * @return
	 */
	@RequestMapping(value="/api/public/news/types",method={RequestMethod.GET,RequestMethod.POST},produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getNewsTypeList(){
		List<Mcode> mcodeList = mcodeService.findByMtype(McodeUtil.NEWS_TYPE);
		List<NewsTypeDto> newsTypeDtos = Lists.newArrayList();
		for(Mcode  mcode : mcodeList){
			NewsTypeDto newsTypeDto = new NewsTypeDto();
			newsTypeDto.setName(mcode.getMkey());
			newsTypeDto.setValue(mcode.getMvalue());
			newsTypeDtos.add(newsTypeDto);
		}
		String response = JsonUtils.toJson(newsTypeDtos);

		return response;
	}
	
	
	/**
	 * 按条件获得新闻分页列表, 内容只出shortCut
	 * 
	 * @param catId 目录
	 * @param sortId 排序方式 
	 * @param searchName 标题名字匹配（模糊）
	 * @param pageNum 第几页, 不填写默认第1页
	 * @param rowsPerPage 每页记录数，不填写默认1页10条记录
	 * @return
	 */
	@RequestMapping(value="/api/public/news",method={RequestMethod.GET,RequestMethod.POST},produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getNewsList(
				@RequestParam(value="catId",required=false)String catId,
				@RequestParam(value="sortId",required=false)String sortId,
				@RequestParam(value="searchName",required=false)String searchName,
				@RequestParam(value="pageNum",required=false)Integer pageNum,
				@RequestParam(value="rowsPerPage",required=false)Integer rowsPerPage
				){		
		
		//默认 文章日期
		String[] sortProperties = new String[] { "addTime" };
		if(sortId!=null){
			if(sortId.equals(RELEASEDATE))
				sortProperties = new String[] { "addTime" };
			if(sortId.equals(EDITORRECOMMENDS))//按编辑推荐
				 sortProperties = new String[] { "sortNumber" };
		}
		Pageable pageable = new PageRequest(pageNum==null?0:(Math.abs(pageNum - 1)), rowsPerPage==null?10:Math.abs(rowsPerPage), Sort.Direction.DESC, sortProperties);
		Page<News> news_list = newsService.findAll(catId, searchName, pageable);
		for(News news : news_list){
			news.setContentWithoutHtml(StringUtil.html2Text(news.getContent()));
			news.setTagChinese(mcodeService.findByMtypeAndMvalue(McodeUtil.NEWS_TYPE, news.getTag()).getMkey());
		}
		
		PageDto<NewsDto> newsPage = new PageDto<NewsDto>(news_list.getNumber()+1, news_list.getSize(), news_list.getTotalPages(),news_list.getTotalElements(), entity2Dto(news_list.getContent()));
		return JsonUtils.toJson(newsPage);
	}
	
	/**
	 * 根据id获得一条新闻详细记录
	 * @PathVariable id, 新闻id
	 * @return
	 */
	@RequestMapping(value="/api/public/news/{id}",method={RequestMethod.GET},produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getOneNewsById(@PathVariable(value="id") Long id){
		News news = newsService.findById(id);
		if(news==null){
			return JsonUtils.toJson(null);
		}		
		news.setContentWithoutHtml(StringUtil.html2Text(news.getContent()));
		news.setTagChinese(mcodeService.findByMtypeAndMvalue(McodeUtil.NEWS_TYPE, news.getTag()).getMkey());
		return JsonUtils.toJson(entity2Dto(news,true));
	}
	
	
	private List<NewsDto> entity2Dto(List<News> news_list){
		List<NewsDto> newsDtos = Lists.newArrayList();
		for(News news : news_list){
			NewsDto nd = new NewsDto();
			nd = entity2Dto(news, false);
			newsDtos.add(nd);
		}
		return newsDtos;
	}
	
	private NewsDto entity2Dto(News n, boolean withContent){
		NewsDto nd = new NewsDto();
		nd.setId(n.getId());
		nd.setTitle(n.getTitle());
		nd.setTitleImg(domain + n.getTitleImg());
		nd.setAuthor(n.getAuthor());
		if(withContent){
			nd.setContent(n.getContent().replaceAll("src=\"/", "src=\""+domain+"/"));
		}else{
			nd.setContentShortCut(n.getContentWithoutHtml().substring(0, 100));
		}
		nd.setAddDate((new SimpleDateFormat("yyyy-MM-dd")).format(n.getAddTime()));
		nd.setCatId(n.getTag());
		nd.setCatName(n.getTagChinese());
		return nd;
	}

	

//	
//	private String getMkey(List<Mcode> mcodeList , String value){
//		for(Mcode mcode : mcodeList){
//			if(mcode.getMvalue().equals(value)){
//				return mcode.getMkey();
//			}
//		}
//		return "";
//	}
//	
//	private List<GameDto> entity2Dto(List<Game> games){
//		List<GameDto> gameDtos = Lists.newArrayList();
//		//List<Mcode> mcodeList = mcodeService.findByMtype("GAME_TYPE");
//		for(Game game : games){
//
//			GameDto gameDto = new GameDto();
//			gameDto = entity2Dto(game);
//			gameDtos.add(gameDto);
//		}
//		return gameDtos;
//	}
//	
//	private GameDto entity2Dto(Game game){
//		
//		GameDto gameDto = new GameDto();
//		gameDto.setId(game.getId());
//		gameDto.setCatId(game.getType());
//		gameDto.setCatName(getMkey(mcodeService.findByMtype("GAME_TYPE"),game.getType()));
//		gameDto.setNameForCH(game.getNameForCH());
//		gameDto.setNameForEN(game.getNameForEN());
//		gameDto.setPopularity((long)game.getPopularity());
//		gameDto.setIconPath(domain + game.getIconPath());
//		gameDto.setIsHot(game.getIsHot());
//		gameDto.setIsNew(game.getIsNew());
//		gameDto.setReleaseDate(DateUtil.getDate(game.getReleaseDate()));
//		gameDto.setDescription(game.getDescription());
//		gameDto.setDownloadTimes(game.getDownloadTimes());
//		gameDto.setIos_size(game.getIos_size());
//		gameDto.setAndroid_size(game.getAndroid_size());
//
//		List<GamePictureDto> pictureList = Lists.newArrayList();
//		for(Picture picture : game.getPictureList()){
//			GamePictureDto gamePictureDto = new GamePictureDto();
//			gamePictureDto.setUrl(domain  + picture.getUrl());
//			pictureList.add(gamePictureDto);
//		}
//		List<GamePlatformAndMarketDto> platformAndMarkets = Lists.newArrayList();
//		for(PlatformAndMarket platformAndMarket : game.getPlatformAndMarkets()){
//			GamePlatformAndMarketDto gamePlatformAndMarketDto = new GamePlatformAndMarketDto();
//			gamePlatformAndMarketDto.setDownloadTimes(platformAndMarket.getDownloadTimes());
//			String downloadUrl =  platformAndMarket.getDownloadUrl();
//
//			if(downloadUrl.startsWith("/")){
//				gamePlatformAndMarketDto.setDownloadUrl(domain + platformAndMarket.getDownloadUrl());
//			}else{
//				gamePlatformAndMarketDto.setDownloadUrl(platformAndMarket.getDownloadUrl());
//			}
//			
//			gamePlatformAndMarketDto.setMarketName(platformAndMarket.getMarketName());
//			gamePlatformAndMarketDto.setPlatform(platformAndMarket.getPlatform());
//			platformAndMarkets.add(gamePlatformAndMarketDto);
//		}
//		gameDto.setPictureList(pictureList);
//		gameDto.setPlatformAndMarkets(platformAndMarkets);		
//		
//		return gameDto;
//	}

}
