package cn.ruiyi.base.web.login;

import java.net.URLDecoder;
import java.security.interfaces.RSAPrivateKey;
import java.util.List;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.ruiyi.base.constants.CurrentSystemUtil;
import cn.ruiyi.base.entity.Menu;
import cn.ruiyi.base.entity.SubSystem;
import cn.ruiyi.base.entity.User;
import cn.ruiyi.base.service.account.AccountService;
import cn.ruiyi.base.service.menu.MenuService;
import cn.ruiyi.base.service.system.SubSystemService;
import cn.ruiyi.base.util.Encrypt;
import cn.ruiyi.base.util.PathUtil;
import cn.ruiyi.base.util.RSAUtil;
import cn.ruiyi.base.web.mvc.StringView;
import cn.unis.service.impl.CommodityCategoryService;
import cn.unis.service.impl.CommodityOrderService;

/**
 * 
 * Title: 		系统登录
 * Project: 	unis
 * Type:		cn.unis.base.web.LoginController
 * Author:		eric
 * Create:	 	2012-11-8 下午05:58:03
 * Copyright: 	Copyright (c) 2012
 * Company:		unis
 *
 */
@Controller
@RequestMapping(value = "/backEnd/")
public class LoginController {
	
	@Autowired
	private MenuService menuSevice;
	@Autowired
	private SubSystemService subSysService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private CommodityOrderService commodityOrderService;	

	/**
	 * 跳转到登录界面
	 * @return
	 */
	@RequestMapping(value = "loginIndex", method = RequestMethod.GET)
	public String login() {
		return "backEnd/login";
	}	
	/**
	 * 后台登录动作
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "login" )
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, String username, String s_password) throws Exception {			
		//RSA解密
		RSAPrivateKey loginRSAprivateKey = (RSAPrivateKey) request.getSession().getAttribute("loginRSAPrivateKey");
		String password = RSAUtil.decodeRSAStr(loginRSAprivateKey,s_password);	
			
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
	
	
	/**
	 * 系统frame框架
	 * 
	 * @return
	 */
	@RequestMapping(value = "frame",method = RequestMethod.GET)
	public String middle(@RequestParam(value="flag",required=false) Integer flag,Model model ) {
		model.addAttribute("flag", flag);
		return "backEnd/frame";
	}
	/**
	 * 系统头部
	 * 
	 * @return
	 */
	@RequestMapping(value = "top",method = RequestMethod.GET)
	public String top(HttpServletRequest request,Model model) {
		User user = accountService.getUser(request);
		PageRequest pageable = new PageRequest(0, 9999, Sort.Direction.ASC, "orderNum");
		List<SubSystem> list = subSysService.findAll("", -1, pageable).getContent();
		String logoutUrl = PathUtil.getConfigResource("logout_url");
		String sysurl = PathUtil.getConfigResource("ypt_url");
		String HTML = subSysService.generateSybSystemICOHTML(list,user);
		model.addAttribute("HTML", HTML);
		model.addAttribute("user", user);
		model.addAttribute("logoutUrl",logoutUrl);
		model.addAttribute("sysurl",sysurl);
		return "backEnd/top";
	}
	/**
	 * 系统下部
	 * 
	 * @return
	 */
	@RequestMapping(value = "bottom",method = RequestMethod.GET)
	public String bottom() {
		return "backEnd/bottom";
	}
	/**
	 * 系统主体
	 * 
	 * @return
	 */
	@RequestMapping(value = "main")
	public String main(@RequestParam(value="flag",required=false) Integer flag,HttpServletRequest request,Model model) {
		model.addAttribute("flag", flag);
		return "backEnd/main";
	}
	/**
	 * 左边菜单
	 * 
	 * @return
	 */
	@RequestMapping(value = "left")
	public String left(HttpServletRequest request,Model model) {
		//User user = accountService.getUser(request);		
		Object shiroUser = SecurityUtils.getSubject().getPrincipal();
		User user = accountService.findUserByLoginName(shiroUser.toString());
		//System.out.println("userName:"+user.getUsername());	
		//System.out.println("intSys:"+CurrentSystemUtil.getCurrentSystem());	
		int intSys = CurrentSystemUtil.getCurrentSystem();
		List<Menu> menuList = menuSevice.getMenuListByCurrUser(user,intSys);
		//System.out.println("menuList.size():"+menuList.size());
		model.addAttribute("menuList", menuList);
		return "backEnd/left";
	}
	/**
	 * 欢迎页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "welcome")
	public String welcome(HttpServletRequest request,Model model) {
		model.addAttribute("numOf10CommodityOrder", commodityOrderService.count10CommodityOrder());
		return "backEnd/welcome";
	}
//	/**
//	 * 显示各系统图标首页
//	 * 
//	 * @return
//	 */
//	@RequestMapping(value = "index")
//	public String index(HttpServletRequest request,Model model) {
//		User user = accountService.getUser(request);
//		PageRequest pageable = new PageRequest(0, 9999, Sort.Direction.ASC, "orderNum");
//		List<SubSystem> list = subSysService.findAll("", -1, pageable).getContent();
//		String HTML = subSysService.generateHomePageSybSystemICOHTML(list,user);
//		String logoutUrl = PathUtil.getResource("logout_url");
//		model.addAttribute("HTML",HTML);
//		model.addAttribute("user",user);
//		model.addAttribute("logoutUrl",logoutUrl);
//		//return "index";
//		return "redirect:/zcgl";
//	}
//	/**
//	 * 用户登录
//	 * 
//	 * @param username
//	 * @param password
//	 * @return
//	 */
//	@RequestMapping(value = "login", method = RequestMethod.POST)
//	public ModelAndView login(String username,String password){
//		StringView view = new StringView();
//		view.setContent("success");
//		return new ModelAndView(view);
//	}
}
