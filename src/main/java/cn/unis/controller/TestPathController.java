package cn.unis.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.service.mcode.McodeService;
import cn.ruiyi.base.service.mcode.McodeUtil;
import cn.ruiyi.base.util.FileManager;
import cn.ruiyi.base.util.PathUtil;
import cn.ruiyi.base.web.mvc.JsView;
import cn.ruiyi.base.web.mvc.Paginate;
import cn.ruiyi.base.web.mvc.StringView;
import cn.ruiyi.base.simplemvc.controller.BaseManagerController;
import cn.ruiyi.base.simplemvc.service.BaseManagerService;
import cn.unis.entity.News;
import cn.unis.service.impl.NewsService;


/**
 * 新闻管理 controller
 * @author eric
 *
 */
@Controller
@RequestMapping(value = "/testPath/")
public class TestPathController{

	/**
	 * test1
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/test1", method = RequestMethod.GET)
	public String test1(HttpServletRequest request) {		
		System.out.println("nihao");
		return "abc";
	}
	

	/**
	 * test2
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/secondPah/test2", method = RequestMethod.GET)
	public String test2(HttpServletRequest request) {		
		return "{\"name\":\"多一级目录测试二\"}";
	}













}




