package cn.ruiyi.base.web.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ruiyi.base.entity.User;
import cn.ruiyi.base.service.account.AccountService;
import cn.ruiyi.base.util.Encrypt;


@Controller
@RequestMapping(value = "/backEnd/")
public class ModifyPasswordController {
	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "modifyPasswordEdit")
	public String edit(){
		return "/backEnd/modifyPassword";
	}
	

	@ResponseBody
	@RequestMapping(value = "modifyPassword", method = RequestMethod.POST)
	public String modify(@RequestParam(value = "oldPassword") String oldPassword,
			@RequestParam(value = "newPassword") String newPassword,
			@RequestParam(value = "newPasswordAgain") String newPasswordAgain, HttpServletRequest httpRequest) {
		if (!newPassword.equals(newPasswordAgain)){
			return "1";//两个密码不一致！！！
		}//判断新密码
		
		Object shiroUser = SecurityUtils.getSubject().getPrincipal();
		User curUser = accountService.findUserByLoginName(shiroUser.toString()); //获取当前用户	
		String plainPassword = Encrypt.MD5(oldPassword);
		if (!plainPassword.equals(curUser.getPassword())){
			return "2";//旧密码输入错误！
		}//判断旧密码
		try {
			curUser.setPassword(Encrypt.MD5(newPassword));
			curUser.setInitPassword(newPassword);
			accountService.saveUser(curUser);
		} catch (Exception e) {
			e.printStackTrace();
			return "3";//密码修改失败！
		}
		return "4";//密码修改成功！
	}
}
