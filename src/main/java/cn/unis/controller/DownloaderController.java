package cn.unis.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.ruiyi.base.util.PathUtil;
import cn.unis.entity.ClickLog;
import cn.unis.entity.PlatformAndMarket;
import cn.unis.service.impl.ClickLogService;
import cn.unis.service.interfaces.IGameService;



/**
 * 文件下载器
 * 用于统计下载数次等作用
 * @author eric
 *
 */
@Controller
@RequestMapping(value = "/frontEnd/downloader")
public class DownloaderController {

	@Autowired
	private IGameService iGameService;
	@Autowired
	private ClickLogService clickLogService;
	private final static String domain = PathUtil.getConfigResource("CURRENT_SYSTEM_DOMAIN");
	
	/**
	 * 游戏文件下载器
	 * 游戏文件地址多用外连，此处只做redirect,不输出流下载
	 */
	@RequestMapping(value = "/platformAndMarket/{id}")
	public String platformAndMarket(HttpServletRequest req, @PathVariable(value="id") Long id){	
		//处理内外网ip问题
		//System.out.println("req.getRemoteAddr():"+req.getRemoteAddr());
		//System.out.println("req.getLocalAddr():"+req.getLocalAddr());
		String remoteAddr = req.getRemoteAddr();
		boolean local = false;
		if(remoteAddr.startsWith("19") || remoteAddr.startsWith("127") || remoteAddr.startsWith("0")){
			local = true;
		}
		
		PlatformAndMarket platformAndMarket = iGameService.countGameDownloadTimes(id==null?-1:id);
		if(platformAndMarket!=null){
			clickLogService.save(new ClickLog(ClickLog.GAME_DOWNLOAD, id, 1, new Date(), req.getSession().getId()));	
			if(platformAndMarket.getDownloadUrl().startsWith("/")){
				return "redirect:"+(local?"":domain)+platformAndMarket.getDownloadUrl();
			}else{
				return "redirect:"+platformAndMarket.getDownloadUrl();
			}
		}else{
			return "redirect:"+(local?"":domain)+"/";
		}
	}
	
	
	
}
