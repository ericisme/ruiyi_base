package cn.unis.wwc.controller;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.service.mcode.McodeService;
import cn.ruiyi.base.service.userActionLog.UserActionLogService;
import cn.ruiyi.base.web.mvc.Paginate;
import cn.ruiyi.base.web.mvc.StringView;
import cn.unis.wwc.entity.InvitationForm;
import cn.unis.wwc.service.InvitationFormService;


/**
 * 用户表单管理controller
 * @author eric
 *
 */
@Controller
@RequestMapping(value = "/backEnd/wwc/invitationForm")
public class InvitationFormController {

	@Autowired
	private InvitationFormService invitationFormService;
	@Autowired
	private UserActionLogService userActionLogService;
	@Autowired
	private McodeService mcodeService;

	/**
	 * index
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("districtCodeOptions", mcodeService.getMbHtmlSelect("COUNTRY_CHINESE", "-1"));
		return  new ModelAndView("unis/backEnd/wwc/invitationForm/index",map);
	}

	/**
	 * page query
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "query")
	public ModelAndView query(HttpServletRequest request,Integer page, String _code, String _companyName, Integer _formType, Integer _isSure,
			String _districtCode, Integer _hotel, Integer _specialDiet, Integer _nightTourOfQiRiver, Integer _visitFormerResidenceOfSunYatSen, Integer _shopping, Integer _entertainment 
	){
		Map<String,Object> map = new HashMap<String,Object>();
		PageRequest pageable = new PageRequest(page - 1, Constants.PAGE_SIZE, Sort.Direction.DESC, "id");
		Page<InvitationForm> paginate = invitationFormService.pageParamQuery(pageable, _code, _companyName, _formType, _isSure, _districtCode, _hotel, _specialDiet, _nightTourOfQiRiver, _visitFormerResidenceOfSunYatSen, _shopping, _entertainment);
		String roll = Paginate.getPage(request, page, paginate, "base.roll","list");
		for(InvitationForm invitationForm : paginate.getContent()){
			invitationForm.setDistrictCodeChinese(mcodeService.findByMtypeAndMvalue("COUNTRY_CHINESE", invitationForm.getDistrictCode()).getMkey());
		}
		map.put("page", paginate.getContent());
		map.put("pageSize", paginate.getContent().size());
		map.put("roll", roll);
		return new ModelAndView("unis/backEnd/wwc/invitationForm/list", map);

	}


	/**
	 * show
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/show/{id}")
	public ModelAndView show(@PathVariable Long id){
		Map<String,Object> map = new HashMap<String,Object>();
		InvitationForm invitationForm = invitationFormService.findById(id);
		invitationForm.setDistrictCodeChinese(mcodeService.findByMtypeAndMvalue("COUNTRY_CHINESE", invitationForm.getDistrictCode()).getMkey());
		map.put("entity", invitationForm);
		return new ModelAndView("unis/backEnd/wwc/invitationForm/show", map);
	}

	/**
	 * 确认信息/取消确认
	 */
	@RequestMapping(value = "/comfirmInfo")
	public ModelAndView comfirmInfo(HttpServletRequest request, Long invitationFormId, Integer isSure) {	
		/*
		 * 方法 式 权限过滤
		 */
		if(!("".equals("invitationForm:comfirmInfo") || SecurityUtils.getSubject().isPermitted("invitationForm:comfirmInfo"))){
			return new ModelAndView(new StringView("<script type='text/javascript'>alert('权限不足');</script>"));
		}		
		StringView view = new StringView();
		try{		
			//System.out.println("invitationFormId:"+invitationFormId);
			System.out.println("isSure:"+isSure);
			InvitationForm invitationForm = invitationFormService.findById(invitationFormId);
			invitationForm.setIsSure(isSure);
			invitationFormService.save(invitationForm);		
			//用户行为记录
			userActionLogService.log("邀请表单确认", (isSure==1?"确认":"取消确认")+" 表单id:"+invitationForm.getId()+" 表单公司名:"+invitationForm.getCompanyName());
			view.setContent("success");			
		}catch(Exception ex){
			view.setContent("操作不成功");
			ex.printStackTrace();
		}		
		return new ModelAndView(view);
	}
	
	/**
	 * page query 
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "exportXls")
	public void exportXls(HttpServletResponse response, HttpServletRequest request, Integer page, String _code, String _companyName, Integer _formType, Integer _isSure,
			String _districtCode, Integer _hotel, Integer _specialDiet, Integer _nightTourOfQiRiver, Integer _visitFormerResidenceOfSunYatSen, Integer _shopping, Integer _entertainment 
	) throws Exception{
		PageRequest pageable = new PageRequest(0, 999999999, Sort.Direction.DESC, "id");
		Page<InvitationForm> paginate = invitationFormService.pageParamQuery(pageable, _code, _companyName, _formType, _isSure, _districtCode, _hotel, _specialDiet, _nightTourOfQiRiver, _visitFormerResidenceOfSunYatSen, _shopping, _entertainment);
		if(paginate.getContent().size()>0){
			OutputStream os = response.getOutputStream();
			String filename = new String("合作伙伴参会表单信息".getBytes("GB2312"), "ISO_8859_1");
			response.setHeader("Content-Disposition","attachment;filename=" + filename + ".xls");
			response.setContentType("application/vnd.ms-excel");		
			invitationFormService.exportXls(paginate.getContent(), os);
		}else{
			String message = "<script>alert(\"对不起，你的条件查询结果记录为0条\"); window.close();</script>";
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(message);
		}
	}

}
