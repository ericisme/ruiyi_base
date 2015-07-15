package cn.unis.wwc.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.entity.User;
import cn.ruiyi.base.service.account.AccountService;
import cn.ruiyi.base.service.mcode.McodeService;
import cn.ruiyi.base.util.StringUtil;
import cn.ruiyi.base.web.mvc.Paginate;
import cn.ruiyi.base.web.mvc.StringView;
import cn.unis.utils.JsonUtils;
import cn.unis.wwc.entity.InvitationForm;
import cn.unis.wwc.entity.InvitationFormApplicant;
import cn.unis.wwc.entity.InvitationFormApplicantCheckIn;
import cn.unis.wwc.service.InvitationFormApplicantCheckInService;
import cn.unis.wwc.service.InvitationFormApplicantService;


/**
 * 与会者签到模块controller
 * @author eric
 *
 */
@Controller
@RequestMapping(value = "/backEnd/wwc/invitationFormApplicant")
public class InvitationFormApplicantController {

	@Autowired
	private InvitationFormApplicantService invitationFormApplicantService;
	@Autowired
	private InvitationFormApplicantCheckInService invitationFormApplicantCheckInService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private McodeService mcodeService;

	/**
	 * invitationFormApplicant search index
	 */
	@RequestMapping(value = "/checkInCount")
	@ResponseBody
	public String checkInCount() {
		Map<String,Object> map = new HashMap<String,Object>();
		Long totalCount = invitationFormApplicantService.getTotalCount();
		Long checkInCount = invitationFormApplicantService.getCheckInCount();
		map.put("totalCount", totalCount);
		map.put("checkInCount", checkInCount);
		return  JsonUtils.toJson(map);
	}
	
	/**
	 * invitationFormApplicant search index
	 */
	@RequestMapping(value = "/searchIndex", method = RequestMethod.GET)
	public ModelAndView searchIndex(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("districtCodeOptions", mcodeService.getMbHtmlSelect("COUNTRY_CHINESE", "-1"));
		return  new ModelAndView("unis/backEnd/wwc/invitationFormApplicant/searchIndex",map);
	}
	/**
	 *  invitationFormApplicant search query
	 */
	@RequestMapping(value = "searchQuery")
	public ModelAndView searchQuery(HttpServletRequest request, Integer page, 
													Integer _checkOrNot, String _districtCode, String _companyName, String _name, String _firstName, String _lastName, String _mobileNumber, String _email){
		Map<String,Object> map = new HashMap<String,Object>();
		PageRequest pageable = new PageRequest(page - 1, Constants.PAGE_SIZE, Sort.Direction.DESC, "id");		
		Page<InvitationFormApplicant> paginate = invitationFormApplicantService.pageParamQuery(pageable, _checkOrNot, _districtCode, _companyName, _name, _firstName, _lastName, _mobileNumber, _email);
		String roll = Paginate.getPage(request, page, paginate, "base.roll","list");
		for(InvitationFormApplicant invitationFormApplicant : paginate.getContent()){
			invitationFormApplicant.getInvitationForm().setDistrictCodeChinese((mcodeService.findByMtypeAndMvalue("COUNTRY_CHINESE", invitationFormApplicant.getInvitationForm().getDistrictCode()).getMkey()));
		}
		map.put("page", paginate.getContent());
		map.put("pageSize", paginate.getContent().size());
		map.put("roll", roll);
		return new ModelAndView("unis/backEnd/wwc/invitationFormApplicant/searchList", map);

	}
	/**
	 * checkInIndex
	 */
	@RequestMapping(value = "/checkInIndex", method = RequestMethod.GET)
	public ModelAndView checkInIndex(HttpServletRequest request) {

		return  new ModelAndView("unis/backEnd/wwc/invitationFormApplicant/checkInIndex");
	}
	
	/**
	 * checkIn
	 */
	@RequestMapping(value = "checkIn")
	public ModelAndView checkIn(HttpServletRequest request, String _identityCode){
		/*
		 * 方法 式 权限过滤
		 */
		if(!("".equals("invitationFormApplicant:checkIn") || SecurityUtils.getSubject().isPermitted("invitationFormApplicant:checkIn"))){
			return new ModelAndView(new StringView("<script type='text/javascript'>alert('权限不足');</script>"));
		}	
		Object shiroUser = SecurityUtils.getSubject().getPrincipal();
		User user = accountService.findUserByLoginName(shiroUser.toString());
		Map<String,Object> map = new HashMap<String,Object>();		
		map.put("identityCode", _identityCode);
		InvitationFormApplicant invitationFormApplicant= invitationFormApplicantService.findByIdentityCode(_identityCode);
		if(invitationFormApplicant==null){
			map.put("exist", false);
		}else if(StringUtil.isNullAndBlank(invitationFormApplicant.getIdentityCode())){
			map.put("exist", false);
		}else{
			InvitationFormApplicantCheckIn invitationFormApplicantCheckIn = new InvitationFormApplicantCheckIn();
			invitationFormApplicantCheckIn.setInvitationFormApplicant(invitationFormApplicant);
			invitationFormApplicantCheckIn.setCheckInDateTime(new Date());
			invitationFormApplicantCheckIn.setHandleUser(user);
			invitationFormApplicantCheckInService.save(invitationFormApplicantCheckIn);
			map.put("exist", true);
			invitationFormApplicant.getInvitationForm().setDistrictCodeChinese((mcodeService.findByMtypeAndMvalue("COUNTRY_CHINESE", invitationFormApplicant.getInvitationForm().getDistrictCode()).getMkey()));
			map.put("invitationFormApplicant", invitationFormApplicant);
			map.put("invitationFormApplicantCheckInList", invitationFormApplicantCheckInService.getListByInvitationFormId(invitationFormApplicant.getId()));
		}
		return new ModelAndView("unis/backEnd/wwc/invitationFormApplicant/checkInList", map);

	}
	
	/**
	 * checkInDelete
	 * @param ids(格式："1,2,3")
	 */
	@RequestMapping(value = "/checkInDelete")
	public ModelAndView checkInDelete(HttpServletRequest request,String ids){	
		/*
		 * 方法 式 权限过滤
		 */
		if(!("".equals("invitationFormApplicant:checkInDelete") || SecurityUtils.getSubject().isPermitted("invitationFormApplicant:checkInDelete"))){
			return new ModelAndView(new StringView("<script type='text/javascript'>alert('权限不足');</script>"));
		}		
		StringView view = new StringView();
		if(StringUtils.isNotEmpty(ids)){			
			String[] arr= ids.split(",");
			try{				
				for(int i=0;i<arr.length;i++){
					invitationFormApplicantCheckInService.delete(Long.parseLong(arr[i]));
				}
			}catch (Exception e) {
				view.setContent("出现未知异常，操作失败!");
			}			
		}
		view.setContent("success");	
		return new ModelAndView(view);		
	}

}
