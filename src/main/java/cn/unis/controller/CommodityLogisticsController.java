package cn.unis.controller;

import javax.annotation.PostConstruct;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.ruiyi.base.web.mvc.StringView;
import cn.ruiyi.base.simplemvc.controller.BaseManagerController;
import cn.ruiyi.base.simplemvc.service.BaseManagerService;
import cn.unis.entity.CommodityLogistics;
import cn.unis.service.impl.CommodityLogisticsService;


/**
 * 商品物流商管理 controller
 * @author eric 
 *
 */
@SuppressWarnings("restriction")
@Controller
@RequestMapping(value = "/backEnd/commodityLogistics")
public class CommodityLogisticsController extends BaseManagerController<CommodityLogistics>{
	
	@Autowired
	private CommodityLogisticsService commodityLogisticsService;	
	@Override
	protected BaseManagerService<CommodityLogistics> getDomainService() {
		return commodityLogisticsService;
	}
	
	@PostConstruct//此注解功能是，让spring加载bean之后会调用一次的方法。
	public void controllerInit(){
		indexPage  = "unis/backEnd/commodityLogistics/index";	
		listPage   = "unis/backEnd/commodityLogistics/list";	
		editPage   = "unis/backEnd/commodityLogistics/edit";
		showPage   = "unis/backEnd/commodityLogistics/show";
		
		indexPermission   = "";
		queryPermission   = "";
		editPermission    = "commodityLogistics:edit";
		savePermission    = "commodityLogistics:edit";
		deletePermission  = "commodityLogistics:delete";
	}	

	/**
	 * 启用  / 禁用
	 */
	@RequiresPermissions("commodityLogistics:edit")
	@RequestMapping(value = "/setup")
	public ModelAndView setup(Long id,Integer status){
		StringView view = new StringView();
		try{
			CommodityLogistics commodityLogistics = getDomainService().findById(id);			
			commodityLogistics.setStatus(status);			
			getDomainService().save(commodityLogistics);
			view.setContent("success");
		}catch(Exception ex){
			view.setContent("出现未知异常，操作失败");
			ex.printStackTrace();
		}
		return new ModelAndView(view);
	}
	
	/**
	 * 排序号加1
	 */
	@RequiresPermissions("commodityLogistics:edit")
	@RequestMapping(value = "/up")
	public ModelAndView up(Long id){
		StringView view = new StringView();
		try{
			CommodityLogistics commodityLogistics = getDomainService().findById(id);			
			commodityLogistics.setSortNumber(commodityLogistics.getSortNumber()+1);			
			getDomainService().save(commodityLogistics);
			view.setContent("success");
		}catch(Exception ex){
			view.setContent("出现未知异常，操作失败");
			ex.printStackTrace();
		}
		return new ModelAndView(view);
	}
	/**
	 * 排序号减1
	 */
	@RequiresPermissions("commodityLogistics:edit")
	@RequestMapping(value = "/down")
	public ModelAndView down(Long id){
		StringView view = new StringView();
		try{
			CommodityLogistics commodityLogistics = getDomainService().findById(id);
			if(commodityLogistics.getSortNumber()>1){
				commodityLogistics.setSortNumber(commodityLogistics.getSortNumber()-1);			
				getDomainService().save(commodityLogistics);
			}
			view.setContent("success");
		}catch(Exception ex){
			view.setContent("出现未知异常，操作失败");
			ex.printStackTrace();
		}
		return new ModelAndView(view);
	}














	
	
	
	
	
	
	
}




