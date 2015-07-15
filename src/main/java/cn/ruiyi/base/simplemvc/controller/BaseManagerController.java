package cn.ruiyi.base.simplemvc.controller;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.MappedSuperclass;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.ruiyi.base.util.StringUtil;
import cn.ruiyi.base.web.mvc.Paginate;
import cn.ruiyi.base.web.mvc.StringView;
import cn.ruiyi.base.simplemvc.dao.BaseManagerDao;
import cn.ruiyi.base.simplemvc.service.BaseManagerService;

/**
 * 管理后台的控制器基类，负责单个域对象CRUD操作的基类，子类只需继承此类便拥有CRUD操作功能
 * 目前提供index、query、edit、save、show、delete六个基础方法，子类可以通过重载这几个方法实现特殊需求。
 * @author eric
 *
 * @param <T> CRUD对应的entity类, 只支持Long类型id
 */
@MappedSuperclass
public abstract class BaseManagerController<T> {		
	
	/**
	 * 获得domainService的抽象方法，子类需要重写并返回已注入的doaminService
	 * @return
	 */
	protected abstract BaseManagerService<T> getDomainService();
	
	protected String indexPage  = "";	
	protected String listPage   = "";	
	protected String editPage   = "";
	protected String showPage   = "";
	
	protected String indexPermission   = "";
	protected String queryPermission   = "";
	protected String editPermission    = "";
	protected String savePermission    = "";
	protected String deletePermission  = "";
	

	
	/**
	 * index
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {		
		/*
		 * 方法 式 权限过滤
		 */
		if(!("".equals(indexPermission) || SecurityUtils.getSubject().isPermitted(indexPermission))){
			return new ModelAndView(new StringView("<script type='text/javascript'>alert('权限不足');</script>"));
		}		
		return  new ModelAndView(indexPage);
	}
	
	/**
	 * edit
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}")
	public ModelAndView edit(HttpServletRequest request, @PathVariable Long id){
		/*
		 * 方法 式 权限过滤
		 */
		if(!("".equals(editPermission) || SecurityUtils.getSubject().isPermitted(editPermission))){
			return new ModelAndView(new StringView("<script type='text/javascript'>alert('权限不足');</script>"));
		}		
		Map<String,Object> map = new HashMap<String,Object>();		
		T doamin = getDomainService().findById(id);		
		map.put("entity", doamin);				//编辑新闻，当为新增时，此变量为空
		map.put("key",id.equals(0L)); 			//区分用户的修改和新增(key为true时：新增；key为false时：修改。）
		return new ModelAndView(editPage, map);
	}
	
	
	/**
	 * save
	 * @param request
	 * @param doamin
	 * @return
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save(HttpServletRequest request, T doamin) {	
		/*
		 * 方法 式 权限过滤
		 */
		if(!("".equals(savePermission) || SecurityUtils.getSubject().isPermitted(savePermission))){
			return new ModelAndView(new StringView("<script type='text/javascript'>alert('权限不足');</script>"));
		}		
		StringView view = new StringView();
		try{		
			/*
			 * 处理日期字符串的实体属性注入，格式 以日期属性名+"_date_str"
			 */
			Enumeration<String> params = request.getParameterNames();		
			while (params.hasMoreElements()) {
				String element = (String) params.nextElement();
				if(element.indexOf("_date_str")>0){
					String v = request.getParameter(element);			
			        Method m1 = doamin.getClass().getDeclaredMethod("set"+StringUtil.capitalize(element.substring(0, element.indexOf("_date_str"))),Date.class);
			        m1.invoke(doamin, StringUtil.strToDate(v)); 				
				}
			}			
			getDomainService().save(doamin);			
			view.setContent("success");			
		}catch(Exception ex){
			view.setContent("保存不成功");
			ex.printStackTrace();
		}		
		return new ModelAndView(view);
	}
	
	/**
	 * delete
	 * @param ids(格式："1,2,3")
	 * @return
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView delete(HttpServletRequest request,String ids){	
		/*
		 * 方法 式 权限过滤
		 */
		if(!("".equals(deletePermission) || SecurityUtils.getSubject().isPermitted(deletePermission))){
			return new ModelAndView(new StringView("<script type='text/javascript'>alert('权限不足');</script>"));
		}		
		StringView view = new StringView();
		if(StringUtils.isNotEmpty(ids)){			
			String[] arr= ids.split(",");
			try{				
				for(int i=0;i<arr.length;i++){
					getDomainService().delete(Long.parseLong(arr[i]));
				}
			}catch (Exception e) {
				view.setContent("出现未知异常，操作失败!");
			}			
		}
		view.setContent("success");	
		return new ModelAndView(view);		
	}
	
	
	/**
	 * page query 
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "query")
	public ModelAndView query(HttpServletRequest request){//,Integer page){	
		/*
		 * 方法 式 权限过滤
		 */
		if(!("".equals(queryPermission) || SecurityUtils.getSubject().isPermitted(queryPermission))){
			return new ModelAndView(new StringView("<script type='text/javascript'>alert('权限不足');</script>"));
		}		
		Map<String,Object> map = new HashMap<String,Object>();				
		Page<T> paginate = getDomainService().pageParamQuery(request);		
		int page = Integer.parseInt(request.getParameter("page"));
		String roll = Paginate.getPage(request, page, paginate, "base.roll","list");
		map.put("page", paginate.getContent());
		map.put("pageSize", paginate.getContent().size());
		map.put("roll", roll);
		return new ModelAndView(listPage, map);
	}
	
	/**
	 * show
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/show/{id}")
	public ModelAndView show(@PathVariable Long id){
		/*
		 * 方法 式 权限过滤
		 */
		if(!("".equals(queryPermission) || SecurityUtils.getSubject().isPermitted(queryPermission))){
			return new ModelAndView(new StringView("<script type='text/javascript'>alert('权限不足');</script>"));
		}		
		Map<String,Object> map = new HashMap<String,Object>();	
		map.put("entity", getDomainService().findById(id));				
		return new ModelAndView(showPage, map);
	}
	
	
	
	
	
	
}








