package cn.ruiyi.base.service.userActionLog;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.ruiyi.base.entity.User;
import cn.ruiyi.base.service.account.AccountService;
import cn.ruiyi.base.util.DateUtil;
import cn.ruiyi.base.util.LogsReaderWriter;


/**
 * 用户后台行为记录，用于记录那些用户做了那些后台操作
 * 以文件形式保存，以日期为单位
 * @author Administrator
 *
 */
@Component
public class UserActionLogService {
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * 记录
	 * @param userLoginName 用户登录名
	 * @param model  模块
	 * @param actionDescript 行为描述
	 */
	public void log(String model, String actionDescript){
		Object shiroUser = SecurityUtils.getSubject().getPrincipal();
		StringBuffer sb = new StringBuffer();
		sb.append(DateUtil.getFormateDate("yyyy-MM-dd HH:mm:ss:SSS")).append(", ");
		sb.append(shiroUser.toString()).append(", ");
		sb.append(model).append(", ");
		sb.append(actionDescript).append("\n");
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd"); 
		String cpath = System.getProperty("user.dir")+"/logs";
		//System.out.println("log: "+sb.toString()+" file:"+cpath+"user_action_log/user_action_log_"+formatDate.format(new Date())+".csv");
		LogsReaderWriter.writeIntoFile(sb.toString(), cpath+"/user_action_log/user_action_log_"+formatDate.format(new Date())+".csv", true);
	}
}
