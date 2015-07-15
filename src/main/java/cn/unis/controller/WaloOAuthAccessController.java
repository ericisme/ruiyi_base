package cn.unis.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.scribe.model.Token;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.unis.service.interfaces.IGameCenterService;
import cn.unis.service.oauth10a.Walo3LeggedApi;
import cn.unis.utils.HardWareUtils;

/**
 *walo OAuth1.0a logining
 */
@Controller
@RequestMapping(value = "/frontEnd")
public class WaloOAuthAccessController {	

	@Resource(name = "waloGameCenterServiceAdapter")
	private IGameCenterService iGamecenterService;
	
	/**
	 * walo user 3Legged Login page
	 * @return
	 */
	@RequestMapping(value = "/walo3LeggedLogin")
	public String walo3LeggedLogin(Model model, HttpServletRequest request, String protocol, String host) {

		Token requestToken = iGamecenterService.get3LeggedRequestToken(protocol+"//"+host+"/frontEnd/walo3LeggedCallBack");
		request.getSession().setAttribute("requestToken", requestToken);		
		//model.addAttribute("waloLoginPath", Walo3LeggedApi.WALOPATH+"/oauth/login?oauth_token="+requestToken.getToken()+"&device_id="+HardWareUtils.getCpuId()+"&platform=web");
		model.addAttribute("waloLoginPath", Walo3LeggedApi.WALOPATH+"/oauth/authorize?oauth_token="+requestToken.getToken()+"&device_id="+HardWareUtils.getCpuId()+"&platform=web");
		return "unis/frontEnd/waloLogin/login";

	}
	
	
	/**
	 * login callback methord.
	 * 登录成功后放入session,accessToken
	 * @return
	 */
	@RequestMapping(value = "/walo3LeggedCallBack")
	public String walo3LeggedCallBack(Model model, HttpServletRequest request, String oauth_token, String oauth_verifier, String fail) {
		System.out.println("fail:"+fail);
		if(fail!=null){
			if("denied".equals(fail)){
				return "redirect:/";
			}
		}
		System.out.println("oauth_token:"+oauth_token);
		System.out.println("oauth_verifier:"+oauth_verifier);
		Token requestToken = (Token) request.getSession().getAttribute("requestToken");
		Token accessToken = iGamecenterService.get3LeggedAccessToken(requestToken, oauth_verifier);	
		//登录成功后放入session,accessToken
		request.getSession().setAttribute("accessToken", accessToken);
		return "redirect:/frontEnd/accountCenter/accountInfo";
	}
	
	/**
	 * log out.
	 * 登出后把session中的accessToken取出
	 * @return
	 */
	@RequestMapping(value = "/walo3LeggedLogout")
	public String walo3LeggedLogout(HttpServletRequest request) {
		Token accessToken = (Token)request.getSession().getAttribute("accessToken");
		if(accessToken!=null){
			String logoutMessage = iGamecenterService.logout3Legged(accessToken);
			System.out.println("logoutMessage:"+logoutMessage);
			request.getSession().removeAttribute("accessToken");
		}
		return "redirect:/";
	}
	
}
