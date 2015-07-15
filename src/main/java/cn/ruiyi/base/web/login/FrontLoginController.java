package cn.ruiyi.base.web.login;

import java.net.URLDecoder;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.scribe.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ruiyi.base.entity.User;
import cn.ruiyi.base.service.account.AccountService;
import cn.ruiyi.base.service.menu.MenuService;
import cn.ruiyi.base.service.system.SubSystemService;
import cn.ruiyi.base.util.Encrypt;
import cn.ruiyi.base.util.PathUtil;
import cn.ruiyi.base.util.RSAUtil;
import cn.ruiyi.base.web.mvc.StringView;
import cn.unis.service.interfaces.IGameCenterService;
import cn.unis.utils.JsonUtils;

/**
 * 
 * Title: 		前台系统登录
 * Project: 	unis
 * Type:		cn.unis.base.web.LoginController
 * Author:		eric
 * Create:	 	2012-11-8 下午05:58:03
 * Copyright: 	Copyright (c) 2012
 * Company:		unis
 *
 */
@Controller
@RequestMapping(value = "/frontEnd/")
public class FrontLoginController {
	
	@Autowired
	private MenuService menuSevice;
	@Autowired
	private SubSystemService subSysService;
	@Autowired
	private AccountService accountService;
	@Resource(name = "waloGameCenterServiceAdapter")
	private IGameCenterService iGamecenterService;
	
	/**
	 * getLoginRSAPublicKey
	 * @throws Exception 
	 */
	@RequestMapping(value = "getLoginRSAPublicKey")
	@ResponseBody
	public String getLoginRSAPublicKey(HttpServletRequest request) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();	
		KeyPair kp = RSAUtil.generateKeyPair();
		RSAPublicKey loginRSAPublicKey= (RSAPublicKey)kp.getPublic();
		request.getSession().setAttribute("loginRSAPrivateKey", (RSAPrivateKey)kp.getPrivate());	
		map.put("module",  loginRSAPublicKey.getModulus().toString(16));
		map.put("empoent", loginRSAPublicKey.getPublicExponent().toString(16));
		return JsonUtils.toJson(map);
	}
	
	
	/**
	 * check login
	 * @throws Exception 
	 */
	@RequestMapping(value = "checkLogin")
	@ResponseBody
	public String checkLogin(HttpServletRequest request) throws Exception{
		//先检测是否登录了walo帐号.,userType=0定义为walo用户类型
		Token accessToken = (Token)request.getSession().getAttribute("accessToken");
		if(accessToken!=null){
			try{
				Map<String,Object> map = new HashMap<String,Object>();			
				map.put("userName", iGamecenterService.getAccountInfo(accessToken).getMsg().getHandle_name());
				map.put("waloPath", PathUtil.getConfigResource("WALO_WAOL_PATH"));
				map.put("userKey", iGamecenterService.getAccountInfo(accessToken).getMsg().getKey());
				map.put("userType", 9);
				return JsonUtils.toJson(map);
			}catch(Exception e){		
				request.getSession().removeAttribute("accessToken");
			}
		}
		
		//不是wlao帐号，检测是否本系统用户
		Object shiroUser = SecurityUtils.getSubject().getPrincipal();
		User user = new User();
		Map<String,Object> map = new HashMap<String,Object>();		
		if(shiroUser!=null){
			user = accountService.findUserByLoginName(shiroUser.toString());
		}
		map.put("userName", user.getUsername());
		map.put("userType", user.getUsertype());
		if(user.getUsertype()==1){
			map.put("backEndPath", "/backEnd/frame");
		}
		if(user.getUsertype()==2 || user.getUsertype()==3){
			map.put("gameCenterPath", "/frontEnd/gameCenter/index");
		}
		return JsonUtils.toJson(map);
	}
	

	
	/**
	 * 前台登录动作
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "login" )
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, String username, String s_password) throws Exception {
		//RSA解密
		RSAPrivateKey loginRSAprivateKey = (RSAPrivateKey) request.getSession().getAttribute("loginRSAPrivateKey");
		String password = RSAUtil.decodeRSAStr(loginRSAprivateKey,s_password);
		
		//System.out.println("frontEnd login");
		// 验证器
		Subject currentUser = SecurityUtils.getSubject();		
		// 密码 前台传过来时应该用JS加密一次
		String minwen = Encrypt.MD5(password);		
		UsernamePasswordToken token = new UsernamePasswordToken(
				username, minwen);		
		// 记住我功能不是记住密码而是在整个会话过程记住会话ID,对未登录用户时用购物车有点用
		/*
		 * if( rememberMe != null ){ if( rememberMe ){
		 * token.setRememberMe(true); } }
		 */
		StringView view = new StringView();
		try {
			currentUser.login(token);
		} catch (UnknownAccountException uae) {
			view.setContent("用户名不存在系统！");				
		} catch (IncorrectCredentialsException ice) {
			view.setContent("密码错误！");			
		} catch (LockedAccountException lae) {
			view.setContent("用户已经被锁定不能登录，请与管理员联系！");			
		} catch (ExcessiveAttemptsException eae) {
			view.setContent("错误次数过多！");			
		} catch (AuthenticationException ae) {
			view.setContent("其他的登录错误！");			
		}
		// 验证是否成功登录的方法
		if (currentUser.isAuthenticated()) {
			// 在session生命周期内有效	
			//User user = accountService.findByUsername(username).get(0);
			User user = accountService.findUserByLoginName(username);
			view.setContent("success"+user.getUsertype());
			//view.setContent("success");
		}		
		return new ModelAndView(view);
	}
	
	
}
