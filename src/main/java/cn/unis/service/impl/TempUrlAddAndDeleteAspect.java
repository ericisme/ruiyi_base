package cn.unis.service.impl;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.ruiyi.base.entity.User;
import cn.ruiyi.base.service.account.AccountService;
import cn.unis.dao.TempUrlDao;
import cn.unis.entity.TempUrl;

/**
 * 尝试使用aop ，分离上传图片写文件与保存数据库动作
 * @author fanzz
 *
 */
@Aspect
@Transactional(readOnly = false)
// @Component
public class TempUrlAddAndDeleteAspect {

	@Autowired
	private TempUrlDao tempUrlDao;
	@Autowired
	private AccountService accountService;

	@AfterReturning(pointcut = "execution(* cn.unis.service.impl.*.savePicture(..))  && args(file,directory)", returning = "result")
	public void doAdd(MultipartFile file, String directory,String result) {
		if(file!=null && result!=null){
			Object shiroUser = SecurityUtils.getSubject().getPrincipal();
			if(shiroUser!=null){
				User user = accountService.findUserByLoginName(shiroUser.toString());
				TempUrl tempUrl = new TempUrl();
				tempUrl.setTempUrl(getDirctory(directory) + result);
				tempUrl.setUser(user);
				tempUrlDao.save(tempUrl);
			}
		}
	}

	/**
	 * TODO 如果文件上传的目录upload改变，这里需要修改
	 * @param directory
	 * @return
	 */
	private String getDirctory(String directory){
		String returnStr = "";
		if(directory != null){
			int index = directory.indexOf("upload");
			returnStr = index > 0 ? returnStr = directory.substring(index - 1,directory.length())  : directory;
		}
		return returnStr;
	}
}