package cn.unis.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.entity.User;
import cn.ruiyi.base.service.account.AccountService;
import cn.ruiyi.base.service.mcode.McodeService;
import cn.ruiyi.base.service.mcode.McodeUtil;
import cn.ruiyi.base.util.DateUtil;
import cn.ruiyi.base.util.FileManager;
import cn.ruiyi.base.util.HttpCookieUtil;
import cn.ruiyi.base.util.PathUtil;
import cn.ruiyi.base.util.StringUtil;
import cn.ruiyi.base.web.mvc.Paginate;
import cn.ruiyi.base.web.mvc.StringView;
import cn.ruiyi.base.simplemvc.controller.BaseManagerController;
import cn.ruiyi.base.simplemvc.service.BaseManagerService;
import cn.unis.dto.PageForCommodityOrder;
import cn.unis.entity.CommodityOrder;
import cn.unis.entity.CommodityOrderStatus;
import cn.unis.entity.News;
import cn.unis.service.impl.CommodityLogisticsService;
import cn.unis.service.impl.CommodityOrderService;
import cn.unis.service.impl.NewsService;
import cn.unis.service.interfaces.IGameCenterService;
import cn.unis.transit.BaseReturnValue;
import cn.unis.transit.UserDetailInfo;


/**
 * 礼品订单管理 controller
 * @author eric 
 *
 */
@Controller
@RequestMapping(value = "/backEnd/commodityOrder")
public class CommodityOrderController {
	
	@Autowired
	private CommodityOrderService commodityOrderService;	
	@Autowired
	private CommodityLogisticsService commodityLogisticsService;
	@Autowired
	private AccountService accountService;
	@Resource(name = "waloGameCenterServiceAdapter")
	private IGameCenterService iGamecenterService;
	
	/**
	 * index
	 * @return
	 */
	@RequiresPermissions("commodityOrder:query")
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();	
		//map.put("_tage_select_html", mcodeService.getMbHtmlSelect(McodeUtil.NEWS_TYPE, null));
		return  new ModelAndView("unis/backEnd/commodityOrder/index", map);
	}
	
	
	/**
	 * 按地址分组 查询未发货订单列表 status:30(待发货)
	 * page query 
	 * @param request
	 * @param page
	 * @return
	 */
	@RequiresPermissions("commodityOrder:query")
	@RequestMapping(value = "query30")
	public ModelAndView query30(HttpServletRequest request, Integer page, String _orderNum, Integer _status, String _logisticsCode) {
		Map<String,Object> map = new HashMap<String,Object>();	
		PageForCommodityOrder  pageForCommodityOrder= commodityOrderService.query30GroupPage(page, Constants.PAGE_SIZE, _orderNum);
		String roll = Paginate.getPage(request, page, pageForCommodityOrder.getTotalElements(), pageForCommodityOrder.getSizePerPage(), "base.roll","list");
		map.put("page", pageForCommodityOrder.getCommodityOrderGroupList());
		map.put("pageSize", pageForCommodityOrder.getCommodityOrderGroupList().size());
		map.put("roll", roll);
		return new ModelAndView("unis/backEnd/commodityOrder/listStatus30", map);
	}
	
	/**
	 * 发货页面 status:30(待发货)
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("commodityOrder:deliveryOut")
	@RequestMapping(value = "deliveryOut")
	public ModelAndView deliveryOut(String ids){
		Map<String,Object> map = new HashMap<String,Object>();			
		List<CommodityOrder> commodityOrderList = new ArrayList<CommodityOrder>();		
		String[] id_str_list = ids.split(",");
		for(String id_str : id_str_list){
			if(!id_str.equals("")){
			System.out.println("id_str:"+id_str);
			CommodityOrder commodityOrder = commodityOrderService.findById(Long.parseLong(id_str));
			commodityOrderList.add(commodityOrder);
			}
		}
		String user_key = commodityOrderList.get(0).getUserKey();
		BaseReturnValue<UserDetailInfo> user = iGamecenterService.getUserDetailInfoByUserKey(user_key,"key,username,handle_name,grade,email,phone,timezone,country,lang,personal_status,registered_arcades,achievement_score,accumulated_ascore,registered_at,expire,status");
		map.put("user", user.getMsg());
		map.put("commodityOrderList", commodityOrderList);
		map.put("deliveryAddress", commodityOrderList.get(0).getDeliveryAddress());
		map.put("allEnableCommodityLogistic", commodityLogisticsService.getAllEnableCommodityLogistic());
		//System.out.println("ids:"+ids);
		return new ModelAndView("unis/backEnd/commodityOrder/deliveryOutPage", map);
	}
	
	/**
	 * 发货操作 status:30(待发货)
	 * @param request
	 * @param doamin
	 * @return
	 */
	@RequiresPermissions("commodityOrder:deliveryOut")
	@RequestMapping(value = "/excuteDeliveryOut")
	public ModelAndView excuteDeliveryOut(HttpServletRequest request, Long commodityLogistics_id, String logisticsCode) {		
		//取得当前用户
		Object shiroUser = SecurityUtils.getSubject().getPrincipal();
		User user = accountService.findUserByLoginName(shiroUser.toString());
		//创建返回view
		StringView view = new StringView();
		//String[] commodityOrderId_str_list = commodityOrderIds.split(",");
		System.out.println("commodityLogistics_id:"+commodityLogistics_id);
		System.out.println("logisticsCode:"+logisticsCode);		
		String[] commodityOrderId_str_list = request.getParameterValues("commodityOrder_ids");
		
		//预保存订单list
		List<CommodityOrder> commodityOrder_list = new ArrayList<CommodityOrder>();
		
		for(String commodityOrderId_str : commodityOrderId_str_list){
			CommodityOrder commodityOrder = commodityOrderService.findById(Long.parseLong(commodityOrderId_str));
			if(commodityOrder==null){
				view.setContent("发货不成功，出现异常情况");
				return new ModelAndView(view);
			}
			//判断是否为待发货订单
			if(commodityOrder.getStatus()!=30){
				view.setContent("操作不成功，因为订单:"+commodityOrder.getOrderNum()+" 不是待发货状态的订单!");
				return new ModelAndView(view);
			}
			//物流商
			commodityOrder.setCommodityLogistics(commodityLogisticsService.findById(commodityLogistics_id));			
			//运单号
			commodityOrder.setLogisticsCode(logisticsCode.trim());
			//留言
			String adminAmessage = (String) request.getParameter("adminAmessage_"+commodityOrderId_str);
			commodityOrder.setAdminAmessage(adminAmessage);
			//状态
			commodityOrder.setStatus(70);
			//确认成功交易截止日期(为发货后起算15天)
			commodityOrder.setDeadLineDate(DateUtil.getDateAfterNDays(new Date(),16));
			//增加状态历史
			CommodityOrderStatus commodityOrderStatus = new CommodityOrderStatus();
			commodityOrderStatus.setRemarks("礼品发货");
			commodityOrderStatus.setUser(user);
			commodityOrderStatus.setUserType(2);
			commodityOrderStatus.setStatus(70);
			commodityOrderStatus.setStatusDate(new Date());
			commodityOrder.addCommodityOrderStatus(commodityOrderStatus);
			//存入预保存订单list
			commodityOrder_list.add(commodityOrder);
		}
		try{
			commodityOrderService.saveList(commodityOrder_list);
			view.setContent("success");
		}catch(Exception e){
			view.setContent("出现异常，操作不成功");
			e.printStackTrace();
		}
		return new ModelAndView(view);
	}
	
	
	

	/**
	 * 按运单号分组 查询已发货订单列表 status:70(待发货)
	 * page query 
	 * @param request
	 * @param page
	 * @return
	 */
	@RequiresPermissions("commodityOrder:query")
	@RequestMapping(value = "query70")
	public ModelAndView query70(HttpServletRequest request, Integer page, String _orderNum, Integer _status, String _logisticsCode) {
		Map<String,Object> map = new HashMap<String,Object>();	
		PageForCommodityOrder  pageForCommodityOrder= commodityOrderService.query70GroupPage(page, Constants.PAGE_SIZE, _orderNum, _logisticsCode);
		String roll = Paginate.getPage(request, page, pageForCommodityOrder.getTotalElements(), pageForCommodityOrder.getSizePerPage(), "base.roll","list");
		map.put("page", pageForCommodityOrder.getCommodityOrderGroupList());
		map.put("pageSize", pageForCommodityOrder.getCommodityOrderGroupList().size());
		map.put("roll", roll);
		return new ModelAndView("unis/backEnd/commodityOrder/listStatus70", map);
	}
	
	/**
	 * 取消发货页面 status:70(已发货)
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("commodityOrder:cancelDelivery")
	@RequestMapping(value = "cancelDelivery")
	public ModelAndView cancelDelivery(String ids){
		Map<String,Object> map = new HashMap<String,Object>();	
		
		List<CommodityOrder> commodityOrderList = new ArrayList<CommodityOrder>();
		
		String[] id_str_list = ids.split(",");
		for(String id_str : id_str_list){
			if(!id_str.equals("")){
			System.out.println("id_str:"+id_str);
			commodityOrderList.add(commodityOrderService.findById(Long.parseLong(id_str)));
			}
		}
		String user_key = commodityOrderList.get(0).getUserKey();
		BaseReturnValue<UserDetailInfo> user = iGamecenterService.getUserDetailInfoByUserKey(user_key,"key,username,handle_name,grade,email,phone,timezone,country,lang,personal_status,registered_arcades,achievement_score,accumulated_ascore,registered_at,expire,status");
		map.put("user", user.getMsg());
		map.put("commodityOrderList", commodityOrderList);
		map.put("deliveryAddress", commodityOrderList.get(0).getDeliveryAddress());
		map.put("allEnableCommodityLogistic", commodityLogisticsService.getAllEnableCommodityLogistic());
		//System.out.println("ids:"+ids);
		return new ModelAndView("unis/backEnd/commodityOrder/cancelDeliveryPage", map);
	}
	
	
	/**
	 * 取消发货操作 status:70(已发货)
	 * @param request
	 * @param doamin
	 * @return
	 */
	@RequiresPermissions("commodityOrder:cancelDelivery")
	@RequestMapping(value = "/excuteCancelDelivery")
	public ModelAndView excuteCancelDelivery(HttpServletRequest request) {		
		//取得当前用户
		Object shiroUser = SecurityUtils.getSubject().getPrincipal();
		User user = accountService.findUserByLoginName(shiroUser.toString());
		//创建返回view
		StringView view = new StringView();
		//String[] commodityOrderId_str_list = commodityOrderIds.split(",");
		//System.out.println("commodityLogistics_id:"+commodityLogistics_id);
		//System.out.println("logisticsCode:"+logisticsCode);		
		
		String[] commodityOrderId_str_list = request.getParameterValues("commodityOrder_ids");
		
		//预保存订单list
		List<CommodityOrder> commodityOrder_list = new ArrayList<CommodityOrder>();
		
		for(String commodityOrderId_str : commodityOrderId_str_list){
			CommodityOrder commodityOrder = commodityOrderService.findById(Long.parseLong(commodityOrderId_str));
			if(commodityOrder==null){
				view.setContent("发货不成功，出现异常情况");
				return new ModelAndView(view);
			}
			//判断是否为已发货订单
			if(commodityOrder.getStatus()!=70){
				view.setContent("操作不成功，因为订单:"+commodityOrder.getOrderNum()+" 不是已发货状态的订单!");
				return new ModelAndView(view);
			}
			//留言
			String adminAmessage = (String) request.getParameter("adminAmessage_"+commodityOrderId_str);
			commodityOrder.setAdminAmessage(adminAmessage);
			//状态
			commodityOrder.setStatus(30);
			//增加状态历史
			CommodityOrderStatus commodityOrderStatus = new CommodityOrderStatus();
			commodityOrderStatus.setRemarks("取消发货");
			commodityOrderStatus.setUser(user);
			commodityOrderStatus.setUserType(2);
			commodityOrderStatus.setStatus(30);
			commodityOrderStatus.setStatusDate(new Date());
			commodityOrder.addCommodityOrderStatus(commodityOrderStatus);
			//存入预保存订单list
			commodityOrder_list.add(commodityOrder);
		}
		try{
			commodityOrderService.saveList(commodityOrder_list);
			view.setContent("success");
		}catch(Exception e){
			view.setContent("出现异常，操作不成功");
			e.printStackTrace();
		}
		return new ModelAndView(view);
	}
	
	
	/**
	 * 按运单号分组 查询已发货订单列表 status:90(交易成功)
	 * page query 
	 * @param request
	 * @param page
	 * @return
	 */
	@RequiresPermissions("commodityOrder:query")
	@RequestMapping(value = "query90")
	public ModelAndView query90(HttpServletRequest request, Integer page, String _orderNum, Integer _status, String _logisticsCode) {
		Map<String,Object> map = new HashMap<String,Object>();	
		PageForCommodityOrder  pageForCommodityOrder= commodityOrderService.query90GroupPage(page, Constants.PAGE_SIZE, _orderNum, _logisticsCode);
		String roll = Paginate.getPage(request, page, pageForCommodityOrder.getTotalElements(), pageForCommodityOrder.getSizePerPage(), "base.roll","list");
		map.put("page", pageForCommodityOrder.getCommodityOrderGroupList());
		map.put("pageSize", pageForCommodityOrder.getCommodityOrderGroupList().size());
		map.put("roll", roll);
		return new ModelAndView("unis/backEnd/commodityOrder/listStatus90", map);
	}
	
	
	/**
	 * 按地址分组 查询 取消状态的订单 列表 status:10(待发货)
	 * page query 
	 * @param request
	 * @param page
	 * @return
	 */
	@RequiresPermissions("commodityOrder:query")
	@RequestMapping(value = "query10")
	public ModelAndView query10(HttpServletRequest request, Integer page, String _orderNum, Integer _status, String _logisticsCode) {
		Map<String,Object> map = new HashMap<String,Object>();	
		PageForCommodityOrder  pageForCommodityOrder= commodityOrderService.query10GroupPage(page, Constants.PAGE_SIZE, _orderNum);
		String roll = Paginate.getPage(request, page, pageForCommodityOrder.getTotalElements(), pageForCommodityOrder.getSizePerPage(), "base.roll","list");
		map.put("page", pageForCommodityOrder.getCommodityOrderGroupList());
		map.put("pageSize", pageForCommodityOrder.getCommodityOrderGroupList().size());
		map.put("roll", roll);
		return new ModelAndView("unis/backEnd/commodityOrder/listStatus10", map);
	}
	
	
	
	/**
	 *查看/留言 页面（仅供 取消状态 根 待发货 状态 订单组 使用）  status:10/30(待发货/取消订单)
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("commodityOrder:query")
	@RequestMapping(value = "showOrMessageFor10And30")
	public ModelAndView showOrMessageFor10And30(String ids){
		Map<String,Object> map = new HashMap<String,Object>();			
		List<CommodityOrder> commodityOrderList = new ArrayList<CommodityOrder>();		
		String[] id_str_list = ids.split(",");
		for(String id_str : id_str_list){
			if(!id_str.equals("")){
			System.out.println("id_str:"+id_str);
			CommodityOrder commodityOrder = commodityOrderService.findById(Long.parseLong(id_str));
			commodityOrderList.add(commodityOrder);
			}
		}
		String user_key = commodityOrderList.get(0).getUserKey();
		BaseReturnValue<UserDetailInfo> user = iGamecenterService.getUserDetailInfoByUserKey(user_key,"key,username,handle_name,grade,email,phone,timezone,country,lang,personal_status,registered_arcades,achievement_score,accumulated_ascore,registered_at,expire,status");
		map.put("user", user.getMsg());
		map.put("commodityOrderList", commodityOrderList);
		map.put("deliveryAddress", commodityOrderList.get(0).getDeliveryAddress());
		map.put("allEnableCommodityLogistic", commodityLogisticsService.getAllEnableCommodityLogistic());
		//System.out.println("ids:"+ids);
		return new ModelAndView("unis/backEnd/commodityOrder/showOrMessageFor10And30Page", map);
	}
	
	
	
	
	/**
	 * 留言操作 status:10/30(订单取消/待发货)
	 * @param request
	 * @param doamin
	 * @return
	 */
	@RequiresPermissions("commodityOrder:deliveryOut")
	@RequestMapping(value = "/excuteMessageFor10Or30")
	public ModelAndView excuteMessageFor10Or30(HttpServletRequest request, Long commodityLogistics_id, String logisticsCode) {		
		//创建返回view
		StringView view = new StringView();
		String[] commodityOrderId_str_list = request.getParameterValues("commodityOrder_ids");		
		//预保存订单list
		List<CommodityOrder> commodityOrder_list = new ArrayList<CommodityOrder>();		
		for(String commodityOrderId_str : commodityOrderId_str_list){
			CommodityOrder commodityOrder = commodityOrderService.findById(Long.parseLong(commodityOrderId_str));
			if(commodityOrder==null){
				view.setContent("留言不成功，出现异常情况");
				return new ModelAndView(view);
			}
			//判断是否为 待发货订单 或 取消状态 订单
			if(!(commodityOrder.getStatus()==30 || commodityOrder.getStatus()==10)){
				view.setContent("留言不成功，因为订单:"+commodityOrder.getOrderNum()+" 不是 待发货状态 或者 取消状态 的订单!");
				return new ModelAndView(view);
			}
			//留言
			String adminAmessage = (String) request.getParameter("adminAmessage_"+commodityOrderId_str);
			commodityOrder.setAdminAmessage(adminAmessage);
			//存入预保存订单list
			commodityOrder_list.add(commodityOrder);
		}
		try{
			commodityOrderService.saveList(commodityOrder_list);
			view.setContent("success");
		}catch(Exception e){
			view.setContent("出现异常，操作不成功");
			e.printStackTrace();
		}
		return new ModelAndView(view);
	}
	
	
	
	/**
	 * 查看/留言 页面（仅供 交易成功 根 已发货 状态 订单组 使用）  status:70/90(已发货/交易成功)
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("commodityOrder:query")
	@RequestMapping(value = "showOrMessageFor70And90")
	public ModelAndView showOrMessageFor70And90(String ids){
		Map<String,Object> map = new HashMap<String,Object>();	
		
		List<CommodityOrder> commodityOrderList = new ArrayList<CommodityOrder>();
		
		String[] id_str_list = ids.split(",");
		for(String id_str : id_str_list){
			if(!id_str.equals("")){
			System.out.println("id_str:"+id_str);
			commodityOrderList.add(commodityOrderService.findById(Long.parseLong(id_str)));
			}
		}
		String user_key = commodityOrderList.get(0).getUserKey();
		BaseReturnValue<UserDetailInfo> user = iGamecenterService.getUserDetailInfoByUserKey(user_key,"key,username,handle_name,grade,email,phone,timezone,country,lang,personal_status,registered_arcades,achievement_score,accumulated_ascore,registered_at,expire,status");
		map.put("user", user.getMsg());
		map.put("commodityOrderList", commodityOrderList);
		map.put("deliveryAddress", commodityOrderList.get(0).getDeliveryAddress());
		map.put("allEnableCommodityLogistic", commodityLogisticsService.getAllEnableCommodityLogistic());
		//System.out.println("ids:"+ids);
		return new ModelAndView("unis/backEnd/commodityOrder/showOrMessageFor70And90Page", map);
	}
	/**
	 * 留言操作 status:70/90(交易成功/已发货)
	 * @param request
	 * @param doamin
	 * @return
	 */
	@RequiresPermissions("commodityOrder:cancelDelivery")
	@RequestMapping(value = "/excuteMessageFor70Or90")
	public ModelAndView excuteMessageFor70Or90(HttpServletRequest request, Long commodityLogistics_id, String logisticsCode) {		
		//创建返回view
		StringView view = new StringView();
		String[] commodityOrderId_str_list = request.getParameterValues("commodityOrder_ids");		
		//预保存订单list
		List<CommodityOrder> commodityOrder_list = new ArrayList<CommodityOrder>();		
		for(String commodityOrderId_str : commodityOrderId_str_list){
			CommodityOrder commodityOrder = commodityOrderService.findById(Long.parseLong(commodityOrderId_str));
			if(commodityOrder==null){
				view.setContent("留言不成功，出现异常情况");
				return new ModelAndView(view);
			}
			//判断是否为 待发货订单 或 取消状态 订单
			if(!(commodityOrder.getStatus()==70 || commodityOrder.getStatus()==90)){
				view.setContent("留言不成功，因为订单:"+commodityOrder.getOrderNum()+" 不是 已发货状态 或者 交易成功 的订单!");
				return new ModelAndView(view);
			}
			//留言
			String adminAmessage = (String) request.getParameter("adminAmessage_"+commodityOrderId_str);
			commodityOrder.setAdminAmessage(adminAmessage);
			//存入预保存订单list
			commodityOrder_list.add(commodityOrder);
		}
		try{
			commodityOrderService.saveList(commodityOrder_list);
			view.setContent("success");
		}catch(Exception e){
			view.setContent("出现异常，操作不成功");
			e.printStackTrace();
		}
		return new ModelAndView(view);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@Autowired
//	private NewsService newsService;	
//	@Autowired
//	private McodeService mcodeService;
//	@Override
//	protected BaseManagerService<News> getDomainService() {
//		return newsService;
//	}
//	@PostConstruct//此注解功能是，让spring加载bean之后会调用一次的方法。
//	public void controllerInit(){
//		indexPage  = "unis/backEnd/news/index";	
//		listPage   = "unis/backEnd/news/list";	
//		editPage   = "unis/backEnd/news/edit";
//		showPage   = "unis/backEnd/news/show";
//		
//		indexPermission   = "";
//		queryPermission   = "";
//		editPermission    = "news:edit";
//		savePermission    = "news:edit";
//		deletePermission  = "news:delete";
//	}	
//	
//	/**
//	 * index
//	 * @return
//	 */
//	@RequestMapping(value = "/index", method = RequestMethod.GET)
//	public ModelAndView index(HttpServletRequest request) {
//		System.out.println("_tage_select_html");
//		/*
//		 * 方法 式 权限过滤
//		 */
//		if(!("".equals(indexPermission) || SecurityUtils.getSubject().isPermitted(indexPermission))){
//			return new ModelAndView(new StringView("<script type='text/javascript'>alert('权限不足');</script>"));
//		}		
//		Map<String,Object> map = new HashMap<String,Object>();	
//		map.put("_tage_select_html", mcodeService.getMbHtmlSelect(McodeUtil.NEWS_TYPE, null));
//		return  new ModelAndView(indexPage, map);
//	}
//	
//	/**
//	 * edit
//	 * @param id
//	 * @return
//	 */
//	@RequestMapping(value = "/edit/{id}")
//	public ModelAndView edit(HttpServletRequest request, @PathVariable Long id){
//		/*
//		 * 方法 式 权限过滤
//		 */
//		if(!("".equals(editPermission) || SecurityUtils.getSubject().isPermitted(editPermission))){
//			return new ModelAndView(new StringView("<script type='text/javascript'>alert('权限不足');</script>"));
//		}		
//		Map<String,Object> map = new HashMap<String,Object>();		
//		News doamin = getDomainService().findById(id);		
//		map.put("entity", doamin);				//编辑新闻，当为新增时，此变量为空
//		map.put("key",id.equals(0L)); 			//区分用户的修改和新增(key为true时：新增；key为false时：修改。）
//		map.put("tage_select_html", mcodeService.getMbHtmlSelect(McodeUtil.NEWS_TYPE, doamin==null ? null : doamin.getTag()));
//		return new ModelAndView(editPage, map);
//	}
//	
//	/**
//	 * page query 
//	 * @param request
//	 * @param page
//	 * @return
//	 */
//	@RequestMapping(value = "query")
//	public ModelAndView query(HttpServletRequest request) {
//		/*
//		 * 方法 式 权限过滤
//		 */
//		if(!("".equals(queryPermission) || SecurityUtils.getSubject().isPermitted(queryPermission))){
//			return new ModelAndView(new StringView("<script type='text/javascript'>alert('权限不足');</script>"));
//		}		
//		Map<String,Object> map = new HashMap<String,Object>();				
//		Page<News> paginate = getDomainService().pageParamQuery(request);	
//		for(News news : paginate){
//			news.setTagChinese(mcodeService.findByMtypeAndMvalue(McodeUtil.NEWS_TYPE, news.getTag()).getMkey());
//		}		
//		int page = Integer.parseInt(request.getParameter("page"));
//		String roll = Paginate.getPage(request, page, paginate, "base.roll","list");
//		map.put("page", paginate.getContent());
//		map.put("pageSize", paginate.getContent().size());
//		map.put("roll", roll);
//		return new ModelAndView(listPage, map);
//	}
//	
//	
//	
//	/**
//	 * 图片上传动作
//	 */
//	@RequiresPermissions("news:edit")
//	@RequestMapping(value = "/uploadImg")
//	public ModelAndView uploadImg(HttpServletRequest request, String uploadFileName,String CKEditorFuncNum ){
//		StringView view = new StringView();
//       MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//       String fileDir = request.getSession().getServletContext().getRealPath("/") + Constants.NewsImgPath ;
//       String randomFileName = "";        
//       Map<String,MultipartFile> files = multipartRequest.getFileMap();
//       Iterator<String> fileNames = multipartRequest.getFileNames();
//       boolean flag = false;
//       for (; fileNames.hasNext();) {
//           String filename = (String) fileNames.next();
//           CommonsMultipartFile file = (CommonsMultipartFile) files.get(filename);
//           byte[] bytes = file.getBytes();
//           if (bytes.length != 0) {
//               if (!flag) {
//                   File dirPath = new File(fileDir);
//                   if (!dirPath.exists()) {
//                       flag = dirPath.mkdirs();
//                   }
//               }
//               //判断文件后缀名是否为图片格式
//               String partRightType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")).toLowerCase();
//               if( !( 		".jpg".equals(partRightType) 
//               		||  ".gif".equals(partRightType)
//               		||  ".jpeg".equals(partRightType)
//               		||  ".png".equals(partRightType))){
//               	String alt_msg = "Sorry! Image format selection is incorrect, please choose GIF, jpeg, PNG format JPG, picture!";
//               	view.setContent("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ", '" + "/none" + "' , '" + alt_msg + "');</script>");
//               	return new ModelAndView(view);
//               }                         
//               //生成的随机文件名
//               randomFileName = PathUtil.createRandomFileName(file.getOriginalFilename());
//               try{
//					FileManager.saveFileInputStream(file.getInputStream(), randomFileName, fileDir);					
//				}catch (IOException e){
//					e.printStackTrace();
//				}
//           }
//       }		
//		view.setContent("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ", '" + Constants.NewsImgPath+"/"+randomFileName + "' , '" + "upload success" + "');</script>");
//		return new ModelAndView(view);	
//	}	
//	
//	/**
//	 * 启用  / 禁用
//	 */
//	@RequiresPermissions("news:setup")
//	@RequestMapping(value = "/setup")
//	public ModelAndView setup(Long id,Integer status){
//		StringView view = new StringView();
//		try{
//			News news = getDomainService().findById(id);			
//			news.setStatus(status);			
//			getDomainService().save(news);
//			view.setContent("success");
//		}catch(Exception ex){
//			view.setContent("出现未知异常，操作失败");
//			ex.printStackTrace();
//		}
//		return new ModelAndView(view);
//	}
//	
//	/**
//	 * 排序号加1
//	 */
//	@RequiresPermissions("news:UpDown")
//	@RequestMapping(value = "/up")
//	public ModelAndView up(Long id){
//		StringView view = new StringView();
//		try{
//			News news = getDomainService().findById(id);			
//			news.setSortNumber(news.getSortNumber()+1);			
//			getDomainService().save(news);
//			view.setContent("success");
//		}catch(Exception ex){
//			view.setContent("出现未知异常，操作失败");
//			ex.printStackTrace();
//		}
//		return new ModelAndView(view);
//	}
//	/**
//	 * 排序号减1
//	 */
//	@RequiresPermissions("news:UpDown")
//	@RequestMapping(value = "/down")
//	public ModelAndView down(Long id){
//		StringView view = new StringView();
//		try{
//			News news = getDomainService().findById(id);
//			if(news.getSortNumber()>1){
//				news.setSortNumber(news.getSortNumber()-1);			
//				getDomainService().save(news);
//			}
//			view.setContent("success");
//		}catch(Exception ex){
//			view.setContent("出现未知异常，操作失败");
//			ex.printStackTrace();
//		}
//		return new ModelAndView(view);
//	}














	
	
	
	
	
	
	
}




