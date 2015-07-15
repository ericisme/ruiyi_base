package cn.unis.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.ruiyi.base.entity.User;
import cn.ruiyi.base.service.account.AccountService;
import cn.ruiyi.base.web.mvc.StringView;
import cn.unis.entity.CommodityCategory;
import cn.unis.service.impl.CommodityCategoryService;
import cn.unis.service.interfaces.ICommodityService;


/**
 * 
 * Title: 		商品类别管理
 * Author:		eric
 * Company:		unis
 *
 */
@Controller
@RequestMapping(value = "/backEnd/commodityCategory")
public class CommodityCategoryController {
	
	@Autowired
	private CommodityCategoryService commodityCategoryService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ICommodityService iCommodityService;
	
	/**
	 * 商品类别管理  -- 首页
	 */
	@RequestMapping(value = "/index")
	public String index(){
		return "unis/backEnd/commodityCategory/index";
	}	
	
	
	/**
	 * 商品类别管理  -- 列表
	 */
	@RequiresPermissions("commodityCategory:view")
	@RequestMapping(value = "/list")
	public String list(Model model,HttpServletRequest request) throws Exception{		
		//Long xx_id = (Long)request.getSession().getAttribute("ding_xx_id");		
		
		List<CommodityCategory> list = commodityCategoryService.getCommodityCategoryListByLevel(1);
		String jValue = "[";
		jValue = commodityCategoryService.getJsonStringForZTree(list,jValue);
		if(list.size() > 0){
			jValue = jValue.substring(0,jValue.length() - 1);
		}
		jValue += "]";	
		model.addAttribute("jValue", jValue);	
		
		//获得用户选择下拉
		//List<User> user_list= this.commodityCategoryService.findUserByXx_id(xx_id);		
		//String user_listOptionHtml = SelectHTMLUtil.buildSelectHTML(user_list, "username", "id", "");
		//model.addAttribute("user_listOptionHtml", user_listOptionHtml);
		
		return "unis/backEnd/commodityCategory/list";

	}
	
	
	/**
	 * 商品类别管理  -- 列表,公用方法，供其它模块查询 商品类别列表使用
	 */	
	@RequestMapping(value = "/listCommodityCategory")
	public String listCommodityCategory(Model model,HttpServletRequest request, String onClickMethord) throws Exception{		
		//Long xx_id = (Long)request.getSession().getAttribute("ding_xx_id");		
		
		List<CommodityCategory> list = commodityCategoryService.getCommodityCategoryListByLevel(1);
		String jValue = "[";
		jValue = commodityCategoryService.getJsonStringForZTree(list,jValue);
		if(list.size() > 0){
			jValue = jValue.substring(0,jValue.length() - 1);
		}
		jValue += "]";	
		model.addAttribute("jValue", jValue);	
		model.addAttribute("onClickMethord", onClickMethord);
		
		//获得用户选择下拉
		//List<User> user_list= this.commodityCategoryService.findUserByXx_id(xx_id);		
		//String user_listOptionHtml = SelectHTMLUtil.buildSelectHTML(user_list, "username", "id", "");
		//model.addAttribute("user_listOptionHtml", user_listOptionHtml);
		
		return "unis/backEnd/commodityCategory/listCommodityCategory";
	}
	
	
//	/**
//	 * 部门管理  -- 根据 部门管理员   列出部门列表,公用方法，供其它模块查询 部门列表使用
//	 */	
//	@RequestMapping(value = "listBmByBmgly")
//	public String listBmByBmgly(Model model,HttpServletRequest request) throws Exception{		
//		Long xx_id = (Long)request.getSession().getAttribute("ding_xx_id");		
//		
//		//获取当前用户	
//		Object shiroUser = SecurityUtils.getSubject().getPrincipal();
//		User bmgly = accountService.findUserByLoginName(shiroUser.toString());
//		
//		List<Bm> list = bmService.getBmListByLevel(1, xx_id);
//		String jValue = "[";
//		jValue = bmService.getJsonStringForZTree(list,jValue,bmgly);
//		if(list.size() > 0){
//			jValue = jValue.substring(0,jValue.length() - 1);
//		}
//		jValue += "]";	
//		model.addAttribute("jValue", jValue);	
//		
//		//获得用户选择下拉
//		List<User> user_list= this.bmService.findUserByXx_id(xx_id);		
//		String user_listOptionHtml = SelectHTMLUtil.buildSelectHTML(user_list, "username", "id", "");
//		model.addAttribute("user_listOptionHtml", user_listOptionHtml);
//		
//		return "zcgl/bm/listBm";
//	}
	
	
	
	/**
	 * 商品类别管理  -- 保存
	 */
	@RequiresPermissions("commodityCategory:save")
	@RequestMapping(value = "/save")
	public ModelAndView save(HttpServletRequest request,CommodityCategory commodityCategory, Long pid){
		if(commodityCategory.getName()==null){
			return new ModelAndView(new StringView("商品类别名称  不能为空"));
		}else if("".equals(commodityCategory.getName().trim())){
			return new ModelAndView(new StringView("商品类别名称  不能为空"));
		}
		
		//Long xx_id = (Long)request.getSession().getAttribute("ding_xx_id");	
		User user = accountService.getUser(request);		
		StringView view = new StringView();
		//Bm b = null;
		CommodityCategory c = null;
		try{
			if(commodityCategory.getId() == 0){	//新增商品类别
				//获得上级商品类别
				c = commodityCategoryService.findById(pid);	
				//set 值
				commodityCategory.setLevel(c.getLevel()+1);
				commodityCategory.setCommodityCategory(c);
				commodityCategory.setAddTime(new Date());
				commodityCategory.setUser(user);
				//commodityCategory.setBmgly(this.accountService.getUser(bmgly_id));
				//commodityCategory.setSchool(this.schoolService.findById(xx_id));				
				//判断 商品类别名称 是否存在
				if(commodityCategoryService.nameExist(commodityCategory)){
					return new ModelAndView(new StringView("商品类别名称  已存在"));
				};
				//保存
				commodityCategoryService.save(commodityCategory);		
			}else{	//修改商品类别
				//取出要修改的商品类别
				c = commodityCategoryService.findById(commodityCategory.getId());
				//修改项
				c.setName(commodityCategory.getName().trim());
				c.setDescription(commodityCategory.getDescription().trim());
				c.setOrderNum(commodityCategory.getOrderNum());
				c.setStatus(commodityCategory.getStatus());
				//判断 商品类别名称 是否存在
				if(commodityCategoryService.nameExist(c)){
					return new ModelAndView(new StringView("商品类别名称  已存在"));
				};
				//保存修改
				commodityCategoryService.save(c);
			}
			view.setContent("success");
		}catch(Exception ex){
			view.setContent("faild");
			ex.printStackTrace();
		}
		
		return new ModelAndView(view);
	}
	
	
	/**
	 * 商品类别管理  -- 删除商品类别单及子商品类别
	 */
	@RequiresPermissions("commodityCategory:delete")
	@RequestMapping(value = "/delNode")
	public ModelAndView delNode(HttpServletRequest request, Long id){
		//Long xx_id = (Long)request.getSession().getAttribute("ding_xx_id");
		StringView view = new StringView();
		CommodityCategory c = null;
		try{
			c = commodityCategoryService.findById(id);
			List<CommodityCategory> list = commodityCategoryService.getSubCommodityCategoryListByPCommodityCategory(c);			
			
			//检查是否可以删除
			List<CommodityCategory> list_one = commodityCategoryService.getOneCommodityCategoryListByCommodityCategory(c);
			for (CommodityCategory commodityCategory : list_one){
				if(iCommodityService.getCommodityListByCommoditCategoryId(commodityCategory.getId()).size()>0){
					return new ModelAndView(new StringView("删除 不成功， 商品类别   < "+commodityCategory.getName()+" > 下已创建 商品 记录"));
				}
			}
			
			//有外键关联需删除所有子部门
			commodityCategoryService.deleteSubBms(list);
			//删除部门
			commodityCategoryService.delete(c.getId());			
			view.setContent("success");
		}catch(Exception ex){
			view.setContent("faild");
			ex.printStackTrace();
		}
		
		return new ModelAndView(view);
	}
	
	
	/**
	 * 商品类别管理  -- 禁用 / 启用
	 */
	@RequiresPermissions("commodityCategory:setup")
	@RequestMapping(value = "/setup")
	public ModelAndView setup(Long id,Integer status){
		StringView view = new StringView();
		CommodityCategory c = null;
		try{
			c = commodityCategoryService.findById(id);
			c.setStatus(status);
			commodityCategoryService.save(c);
			view.setContent("success");
		}catch(Exception ex){
			view.setContent("faild");
			ex.printStackTrace();
		}		
		return new ModelAndView(view);
	}
	
	
	
}
