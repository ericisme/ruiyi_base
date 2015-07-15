package cn.ruiyi.base.web.account;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.entity.Role;
import cn.ruiyi.base.entity.SubSystem;
import cn.ruiyi.base.entity.User;
import cn.ruiyi.base.service.account.AccountService;
import cn.ruiyi.base.service.role.RoleService;
//import cn.ruiyi.base.service.school.SchoolService;
import cn.ruiyi.base.service.system.SubSystemService;
//import cn.ruiyi.base.service.unit.UnitService;
import cn.ruiyi.base.util.Encrypt;
import cn.ruiyi.base.util.RandomUtil;
import cn.ruiyi.base.util.StringUtil;
import cn.ruiyi.base.web.mvc.Paginate;
import cn.ruiyi.base.web.mvc.StringView;
import cn.unis.dao.UserGameCenterDao;
import cn.unis.entity.UserGameCenter;
import cn.unis.service.impl.UserGameCenterService;
import cn.unis.service.impl.WaloGameCenterServiceAdapter;
import cn.unis.service.interfaces.IGameCenterService;
import cn.unis.transit.BaseReturnValues;
import cn.unis.transit.GameCenterSimpleInfo;
import cn.unis.utils.JsonUtils;

/**
 * 用户管理控制器
 * @author chen
 * @version 1.0, 2012-11-13
 */
@Controller
@RequestMapping(value = "/account/user/")
public class AccountController{
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private SubSystemService subSystemService;
	@Autowired
	private UserGameCenterService userGameCenterService;
	@Resource(name = "waloGameCenterServiceAdapter")
	private IGameCenterService iGamecenterService;
	
	/**
	 * 根据provinceCode获得Game Center html OPTION
	 * @param provinceCode
	 * @return
	 */
	@RequiresPermissions("user:edit")
	@RequestMapping(value = "getGameCentersByProvinceCode")
	@ResponseBody
	public String getGameCentersByProvinceCode(String provinceCode){
		BaseReturnValues<GameCenterSimpleInfo> gameCenters= iGamecenterService.getGameCentersByProvinceCode(provinceCode, 1, 50);
		StringBuffer sb = new StringBuffer();
		if(gameCenters.getMsg()!=null){
			for(GameCenterSimpleInfo gcsi : gameCenters.getMsg()){
				sb.append("<option value=\"").append(gcsi.getKey()).append("\">").append(gcsi.getName()).append("</option>");
			}		
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("gc_select", sb.toString());		
		return JsonUtils.toJson(map);
	}
	
	/**
	 * 根据cityCode获得Game Center html OPTION
	 * @param provinceCode
	 * @return
	 */
	@RequiresPermissions("user:edit")
	@RequestMapping(value = "getGameCentersByCityCode")
	@ResponseBody
	public String getGameCentersByCityCode(String cityCode){
		BaseReturnValues<GameCenterSimpleInfo> gameCenters= iGamecenterService.getGameCentersByCityCode(cityCode, 1, 50);
		StringBuffer sb = new StringBuffer();
		if(gameCenters.getMsg()!=null){
			for(GameCenterSimpleInfo gcsi : gameCenters.getMsg()){
				sb.append("<option value=\"").append(gcsi.getKey()).append("\">").append(gcsi.getName()).append("</option>");
			}		
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("gc_select", sb.toString());		
		return JsonUtils.toJson(map);
	}
	
	
	/**
	 * 进入用户管理菜单
	 * @return
	 */
	@RequestMapping(value = "index")
	public String index(Model model){
		Object shiroUser = SecurityUtils.getSubject().getPrincipal();
		User user = accountService.findUserByLoginName(shiroUser.toString());	
		model.addAttribute("roles", roleService.findAllRoles());		
		model.addAttribute("user", user);
		return "account/index";
	}	
	/**
	 * 用户的列表查询.
	 */
	@RequiresPermissions("user:query")
	@RequestMapping(value = "query")
	public ModelAndView list(HttpServletRequest request,Integer page,String qryName,Long roleId){
		Object shiroUser = SecurityUtils.getSubject().getPrincipal();
		User user = accountService.findUserByLoginName(shiroUser.toString());
		Map<String,Object> map = new HashMap<String,Object>();		
		PageRequest pageable = new PageRequest(page - 1, Constants.PAGE_SIZE, Sort.Direction.ASC, "id");		
		Page<User> paginate = accountService.findAll(qryName, roleId, pageable);		
		//Page<User> paginate = null;
		String roll = Paginate.getPage(request, page, paginate, "base.roll","list");
		map.put("page", paginate.getContent());
		map.put("pageSize", paginate.getContent().size());
		map.put("roll", roll);
		map.put("user", user);
		return new ModelAndView("account/list",map);
	}
	
	
	/**
	 * 删除用户，支持多个用户勾选批量删除
	 * @param ids(格式："1,2,3")
	 * @return
	 */	
	@RequiresPermissions("user:delete")
	@RequestMapping(value = "/delete")
	public ModelAndView delete(HttpServletRequest request,String ids){		
		StringView view = new StringView();
		if(StringUtils.isNotEmpty(ids)){			
			String[] arr= ids.split(",");
			try{				
				for(int i=0;i<arr.length;i++){
					long id=Long.parseLong(arr[i]);
					accountService.deleteUser(id);
				}
			}catch (Exception e) {
				view.setContent("false");
			}
			
		}
		view.setContent("success");	
		return new ModelAndView(view);
		
	}

	/**
	 *用户的新增与修改的界面初始化
	 * @param id
	 * @return
	 */
	@RequiresPermissions("user:edit")
	@RequestMapping(value = "/edit/{id}")
	public ModelAndView edit(@PathVariable Long id){
		Object shiroUser = SecurityUtils.getSubject().getPrincipal();
		User curUser = accountService.findUserByLoginName(shiroUser.toString()); //获取当前用户
		User user = accountService.getUser(id);				//编辑的用户，当为新增时，此为空
		Map<String,Object> map = new HashMap<String,Object>();		
		//当前用户新建或者修改用户的基础权限集合应该为自身拥有的集合的子集
		List<Role> roleList=curUser.getRoleList();
		List<Role> roles=null;
		if(user !=null){		//编辑用户模式时
			roles=user.getRoleList();		
		}
		//当前用户新建或者修改用户的可登陆子系统集合应该为自身可登陆的集合的子集
	//	String[] subSys=curUser.getSystemIds().split(",");
		String subSys =curUser.getSystemIds();
	//	System.out.println("======="+subSys.length);
		List<SubSystem> sysList=new ArrayList<SubSystem> ();
		if(subSys !=null && !"".equals(subSys)){
			for(String sysId:subSys.split(",")){
				sysList.add(subSystemService.findById(Long.parseLong(sysId)));
			}
		}
		List<Long> havedSys=new ArrayList<Long>();
		if(user !=null){	//编辑用户模式时
			if(user.getSystemIds() !=null && !"".equals(user.getSystemIds())){
				String[] temp=user.getSystemIds().split(",");
				for(String s:temp){
					havedSys.add(Long.parseLong(s));
				}
			}						
		}
		
		List<UserGameCenter> gameCenters=null;
		if(user !=null){		//编辑用户模式时
			gameCenters = userGameCenterService.findByUser(user);
			for(UserGameCenter ugc : gameCenters){
				//ugc.setGameCenterName(ugc.getGameCenterKey());
				ugc.setGameCenterName(iGamecenterService.getGameCenter(ugc.getGameCenterKey()).getMsg().getName());
			}
		}
		if(user==null){
			user = new User();
			user.setUsertype(1);
		}
		
		
		map.put("user", user);		//编辑用户，当为新增时，此变量为空
		map.put("curUser", curUser); //当前用户
		map.put("roleList", roleList);  //可供选择的角色
		map.put("roles", roles); //用户已有的角色
		map.put("gameCenters", gameCenters); //用户已有的游乐场
		map.put("sysList",sysList); //当前用户可以登录的子系统（可供选择操作的子系统）
		map.put("havedSys", havedSys);
		map.put("key", id==0); 	//区分用户的修改和新增(key为true时：新增；key为false时：修改。）
		return new ModelAndView("account/edit",map);
	}
	
	/**
	 * 保存用户（新增、修改用户）
	 * 
	 * @return
	 */
	@RequiresPermissions("user:edit")
	@RequestMapping(value = "/save")
	public ModelAndView save(HttpServletRequest request, User user, String birth, String roleIds, String gamecenterIds){
		System.out.println("roleIds:"+roleIds);
		System.out.println("gamecenterIds:"+gamecenterIds);
		User DbUser = null;
		boolean flag = false;		//flag为true时：编辑模式，flag为false时：新增模式
		if(user.getId() != null){		//编辑模式下的保存应该先获取数据库中用户实体
			DbUser=accountService.getUser(user.getId());
			//System.out.println("====="+DbUser.getPassword());
			flag = true;
		}
		StringView view = new StringView();
//		System.out.println("当前模式"+flag);
//		System.out.println(user.getLoginName()+"==="+user.getSystemIds()+"==="+birth+"==="+roleIds+"==="+user.getTypeId());	
	//	System.out.println("获取的子系统==="+user.getSystemIds());
		try
		{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date birthday = null;
			if( !"".equals(birth) && birth !=null){
				birthday= sdf.parse(birth);
			}
			if(flag){				
				DbUser.setBirthday(birthday);
			}else{
				user.setBirthday(birthday);
			}
			
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		String[] roles=null;
		List<Role> roleList = new ArrayList();
		if(!"".equals(roleIds) && roleIds !=null){
			roles=roleIds.split(",");		//注意：空字符串经过split操作得到的数组长度为1，会产生异常
		}			
	//	System.out.println("角色的数组长度："+roles.length);
		if(roles !=null){
			for(String roleId:roles){
				Role role =roleService.getRoleById(Long.parseLong(roleId));
				roleList.add(role);
			}
		}
		if(flag){ //编辑模式保留之前的地段内容
			DbUser.setRoleList(roleList);	//编辑模式不涉及密码的处理
			DbUser.setUsername(user.getUsername());
			DbUser.setGender(user.getGender());
			DbUser.setMobile(user.getMobile());
			DbUser.setEmail(user.getEmail());
			DbUser.setUsertype(user.getUsertype());
			//DbUser.setTypeId(user.getTypeId());
			DbUser.setSystemIds(user.getSystemIds());
		}else{
			user.setRoleList(roleList);	
			user.setPassword(Encrypt.MD5(user.getInitPassword()));
		}
		
		if(flag){
			accountService.saveUser(DbUser);
		}else{
			//新增用户，生成用户唯一标识
			//user.setIdentifyCode(DESEncrypt.encrypt(user.getLoginName()));
			user.setIdentifyCode(user.getLoginName());
			accountService.saveUser(user);
		}
		System.out.println("save user id:"+user.getId());
		userGameCenterService.saveByUserIdAndGameCenterIds(user, gamecenterIds);		
		view.setContent("success");
		return new ModelAndView(view);
	}
	
	//登录名的唯一性检查
	@RequestMapping(value = "/check")
	public ModelAndView checkLoginName(HttpServletRequest request,String loginName){
		StringView view = new StringView();
		if(accountService.findUserByLoginName(loginName) == null){
			view.setContent("true");
		}else{
			view.setContent("false");
		}
		return new ModelAndView(view);
		
	}
	/**
	 * 用户的停用与启用
	 * @param request
	 * @param userId
	 * @param status
	 * @return
	 */
	@RequiresPermissions("user:setup")
	@RequestMapping(value = "/setup",method=RequestMethod.POST)
	public ModelAndView setup(HttpServletRequest request,Long userId,int sta){
		StringView view = new StringView();
//		System.out.println(userId+"===="+sta);
		User user=accountService.getUser(userId);
		user.setStatus(sta);
		accountService.saveUser(user);
		view.setContent("success");
		return new ModelAndView(view);
		
	}
	

}
