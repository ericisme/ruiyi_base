package cn.unis.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.entity.Mcode;
import cn.ruiyi.base.service.mcode.McodeService;
import cn.ruiyi.base.util.DateUtil;
import cn.ruiyi.base.web.mvc.Paginate;
import cn.ruiyi.base.web.mvc.StringView;
import cn.unis.dto.GameDto;
import cn.unis.entity.Game;
import cn.unis.entity.PlatformAndMarket;
import cn.unis.service.interfaces.IGameService;
import cn.unis.utils.JsonUtils;
import cn.unis.utils.PictureUploader;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 游戏管理
 *
 * @author fanzz
 *
 */
@Controller
@RequestMapping(value = "/backEnd/game/")
public class GameController {
	@Autowired
	private IGameService iGameService;
	@Autowired
	private McodeService mcodeService;

	@RequiresPermissions("game:query")
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request ,Model model) {
		model.addAttribute("type",mcodeService.getMbHtmlSelect("GAME_TYPE", ""));
		return "unis/backEnd/game/index";
	}
	/**
	 * 分页查询
	 *
	 * @param request
	 * @param queryName
	 *            图片名字
	 * @param queryStatus
	 *            状态
	 * @param page
	 * @param model
	 * @return
	 */
	@RequiresPermissions("game:query")
	@RequestMapping(value = "query")
	public String list(HttpServletRequest request,
			@RequestParam(value = "queryStatus") Integer queryStatus,
			@RequestParam(value = "queryType") String queryType,
			@RequestParam(value = "queryName") String queryName,
			@RequestParam(value = "queryIsHot") Integer queryIsHot,
			@RequestParam(value = "queryIsNew") Integer queryIsNew,
			@RequestParam("page") Integer page, Model model) {
		Pageable pageable = new PageRequest(page - 1, Constants.PAGE_SIZE,
				Sort.Direction.ASC, "sortNumber");
		String rootPath = getRootPath(request);
		Page<Game> paginate = iGameService.findAll(rootPath , queryStatus, queryType,
				queryName, queryIsNew, queryIsHot, pageable);
		for (Game game : paginate) {
			Mcode mcode = mcodeService.findByMtypeAndMvalue("GAME_TYPE",
					game.getType());
			if(mcode!=null){
				game.setType(mcode.getMkey());
			}

		}
		String roll = Paginate.getPage(request, page, paginate, "base.roll",
				"list");
		model.addAttribute("page", paginate.getContent());
		model.addAttribute("pageSize", paginate.getContent().size());
		model.addAttribute("roll", roll);


		return "unis/backEnd/game/list";
	}

	/**
	 * 编辑
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("game:edit")
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		Game game = iGameService.findById(id);
		if (game == null) {
			game = new Game();
			game.setId(0L);
		}
		model.addAttribute("game", game);
		model.addAttribute("type", mcodeService.getMbHtmlSelect("GAME_TYPE",game == null ? "" : game.getType()));
		model.addAttribute("iosPlatformAndMarkets", game.getIOS_PlatformAndMarket());
		model.addAttribute("androidPlatformAndMarkets", game.getAndroid_PlatformAndMarket());
		model.addAttribute("wpPlatformAndMarkets", game.getWP_PlatformAndMarket());
		model.addAttribute("ios_url_size", game.getIOS_Url_Download_Numbers());
		model.addAttribute("android_url_size", game.getAndroid_Url_Download_Numbers());
		model.addAttribute("wp_url_size", game.getWP_Url_Download_Numbers());
		model.addAttribute("pictureType",mcodeService.getMbHtmlSelect("PICTURE_TYPE", ""));
		return "unis/backEnd/game/edit";
	}

	@RequiresPermissions("game:edit")
	@RequestMapping(value = "/save")
	@ResponseBody
	public ModelAndView save(HttpServletRequest request, Game game,
			@RequestParam("release_Date") String releaseDate,GameDto gameDto) {
		String rootPath = PictureUploader.getContextPath(request);
		StringView view = new StringView();
		Date date = StringUtils.isNotBlank(releaseDate) ? DateUtil.parseSimpleDT("yyyy-MM-dd", releaseDate) : new Date();
		game.setReleaseDate(date);
		game.addPlatformAndMarkets(getPlatformAndMarketEntityFromGameDto(gameDto));
		iGameService.save(game,rootPath);
		view.setContent("success");
		return new ModelAndView(view);
	}

	@RequiresPermissions("game:edit")
	@RequestMapping(value = "/uploadIcon")
	public String uploadIcon(HttpServletRequest request,
			@RequestParam(value = "qqfile", required = false) MultipartFile file,Model model) {
		String contextPath = PictureUploader.getContextPath(request);
		boolean isPicture = PictureUploader.isPictureStream(file);
		Map<String, String> map = Maps.newHashMap();
		if(isPicture){
			String url = iGameService.savePicture(file, contextPath + IGameService.TMP);
			if(url != null) {
				map.put("status", "success");
				map.put("url", (IGameService.TMP + url));
			}else{
				map.put("status", "error");
			}
		}else{
			map.put("status", "error");
		}
		model.addAttribute("success_json",JsonUtils.toJson(map));
		return "unis/backEnd/upload/upload_success";
	}

	/**
	 * @param ids
	 *            (格式："1,2,3")
	 * @return
	 */
	@RequiresPermissions("game:delete")
	@RequestMapping(value = "/delete")
	public ModelAndView delete(HttpServletRequest request, String ids) {
		String rootPath = getRootPath(request);
		StringView view = new StringView();
		iGameService.delete(ids,rootPath);
		view.setContent("success");
		return new ModelAndView(view);
	}

	/**
	 * 启用 / 禁用
	 */
	@RequiresPermissions("game:edit")
	@RequestMapping(value = "/setup")
	public ModelAndView setup(Long id, Integer status) {
		StringView view = new StringView();
		view.setContent(iGameService.changeStatus(id, status) ? "success"
				: "error");
		return new ModelAndView(view);
	}

	private String getRootPath(HttpServletRequest request){
		return  request.getSession().getServletContext().getRealPath("/");
	}


	private List<PlatformAndMarket> getPlatformAndMarketEntityFromGameDto(GameDto gameDto){
		PlatformAndMarket platformAndMarket;
		List<PlatformAndMarket> list = Lists.newArrayList();
		if(gameDto!=null){
			if(gameDto.getIos_marketname() != null && gameDto.getIos_url() != null){
				if(gameDto.getIos_marketname().length == gameDto.getIos_url().length){
					for(int i=0;i<gameDto.getIos_url().length;i++){
						if( gameDto.getIos_marketname().length > 4){
							break;
						}
						platformAndMarket = new PlatformAndMarket();
						long ios_id = gameDto.getIos_id() == null || gameDto.getIos_id().length <= i ? 0L : gameDto.getIos_id()[i];
						platformAndMarket.setId(ios_id);
						platformAndMarket.setMarketName(gameDto.getIos_marketname()[i]);
						platformAndMarket.setPlatform(PlatformAndMarket.IOS);
						platformAndMarket.setDownloadUrl(gameDto.getIos_url()[i]);
						list.add(platformAndMarket);
					}
				}
			}
			if(gameDto.getAndroid_marketname()!=null && gameDto.getAndroid_url()!=null){
				if(gameDto.getAndroid_marketname().length == gameDto.getAndroid_url().length){
					for(int i=0;i<gameDto.getAndroid_url().length;i++){
						if(gameDto.getAndroid_url().length > 4){
							break;
						}
						platformAndMarket = new PlatformAndMarket();
						long android_id = gameDto.getAndroid_id() == null || gameDto.getAndroid_id().length <= i ? 0L : gameDto.getAndroid_id()[i];
						platformAndMarket.setId(android_id);
						platformAndMarket.setMarketName(gameDto.getAndroid_marketname()[i]);
						platformAndMarket.setPlatform(PlatformAndMarket.ANDROID);
						platformAndMarket.setDownloadUrl(gameDto.getAndroid_url()[i]);
						list.add(platformAndMarket);
					}
				}
			}
		}
		return list;
	}
}
