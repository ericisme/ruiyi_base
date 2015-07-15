package cn.unis.wwc.controller;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ruiyi.base.constants.Constants;
import cn.ruiyi.base.service.mcode.McodeService;
import cn.ruiyi.base.util.DateUtil;
import cn.unis.utils.JsonUtils;
import cn.unis.utils.LocaleUtils;
import cn.unis.utils.PropertyAnnotationProcessUtil;
import cn.unis.wwc.dto.ApplyCodeDto;
import cn.unis.wwc.dto.ApplyCodeResponseDto;
import cn.unis.wwc.dto.InvitationFormDto;
import cn.unis.wwc.dto.SuccessDto;
import cn.unis.wwc.entity.InvitationCode;
import cn.unis.wwc.entity.InvitationForm;
import cn.unis.wwc.entity.InvitationFormApplicant;
import cn.unis.wwc.entity.InvitationNews;
import cn.unis.wwc.interfaces.IWWCIndexConstant;
import cn.unis.wwc.service.InvitationCodeService;
import cn.unis.wwc.service.InvitationFormApplicantService;
import cn.unis.wwc.service.InvitationFormService;
import cn.unis.wwc.service.InvitationNewsService;
import cn.unis.wwc.service.MessageService;

import com.google.common.collect.Lists;


/**
 * 邀请码管理 .controller
 * @author eric
 *
 */
@Controller
@RequestMapping(value = "/wwc/")
public class WWCIndexController implements IWWCIndexConstant{

	@Autowired
	private InvitationCodeService invitationCodeService ;
	@Autowired
	private InvitationFormService invitationFormService;
	@Autowired
	private InvitationNewsService invitationNewsService;
	@Autowired
	private InvitationFormApplicantService invitationFormApplicantService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private McodeService mcodeService;
	@PersistenceContext
	private EntityManager em;

	@RequestMapping(value = "/article/{id}", method = RequestMethod.GET)
	public String article(HttpServletRequest request,Model model,@PathVariable("id")long id){
		InvitationNews invitationNews = invitationNewsService.findById(id);
		model.addAttribute("news", invitationNews);
		return "/unis/frontEnd/wwc/article";
	}

	/**
	 * 申请邀请码
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/applycode", method = RequestMethod.GET)
	public String applycode_cn(HttpServletRequest request,Model model){
		String locale = LocaleUtils.getLocale(request);
		int apply_type = locale.equals(_CHINA)? 1 : 0;
		model.addAttribute("apply_type", apply_type);
		return "/unis/frontEnd/wwc/applycode";
	}

	@ResponseBody
	@RequestMapping(value = "/applycode/save", method = RequestMethod.POST)
	public String applycode_save(HttpServletRequest request,ApplyCodeDto applyCodeDto){
		ApplyCodeResponseDto applyCodeResponseDto = new ApplyCodeResponseDto();
		applyCodeResponseDto.setCode(-3);
		applyCodeResponseDto.setErr("亲，此功能已经暂停了！！！");

//		InvitationCode invitationCode = getInvitationCode(applyCodeDto, _OUTSIDE_APPLY);
//		if(isAllNotNull(applyCodeDto)){
//			String captchaInSession = (String) SecurityUtils.getSubject().getSession().getAttribute(Constants.WWC_APPLY_INVITATION_CAPTCHA);
//			if(applyCodeDto.getCaptcha().equalsIgnoreCase(captchaInSession)){
//				invitationCodeService.saveWithoutCode(invitationCode);
//				applyCodeResponseDto.setCode(1);
//			}else{
//				applyCodeResponseDto.setCode(-1);
//				applyCodeResponseDto.setErr(_ERR_CAPTCHA_CN);
//			}
//
//		}else{
//			applyCodeResponseDto.setCode(-2);
//			applyCodeResponseDto.setErr(_ERR_PROPERTERY_CN);
//		}

		SecurityUtils.getSubject().getSession().removeAttribute(Constants.WWC_APPLY_INVITATION_CAPTCHA);
		return JsonUtils.toJson(applyCodeResponseDto);
	}

	/**
	 * 入口地址
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@Transactional(readOnly=false)
	public String index(HttpServletRequest request,HttpServletResponse response) {
		//根据浏览器语言选择中文主页，或英文主页
		String locale = LocaleUtils.getLocale(request);
		String redirectPath = locale.equals(_CHINA) ? "redirect:/wwc/cn/index" :  "redirect:/wwc/en/index";
		return redirectPath;
	}
	/**
	 * 凭邀请码登录验证
	 * @param request
	 * @param captchaFromInput
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/invitation_code/verify", method = RequestMethod.POST)
	public String verifyInvitationCode(HttpServletRequest request,@RequestParam("captcha")String captchaFromInput,@RequestParam("invitationCode")String code){
		String captchaFromSession = (String) SecurityUtils.getSubject().getSession().getAttribute(Constants.WWC_KEY_CAPTCHA);
		String path;
		if(captchaFromInput.equalsIgnoreCase(captchaFromSession)){
			InvitationCode invitationCode = invitationCodeService.findByCode(code);
			String locale = LocaleUtils.getLocale(request);
			SecurityUtils.getSubject().getSession().setAttribute(_INVITATION_CODE_TAG, code);
			if(invitationCode!=null){
				if(locale.equals(_CHINA)){
					path = "redirect:/wwc/cn/edit";
				}else{
					path = "redirect:/wwc/en/edit";
				}
			}else{
				path = "redirect:/wwc/index";
				SecurityUtils.getSubject().getSession().setAttribute(Constants.WWC_ERR_INVITATION_CODE, "1");
			}
		}else{
			SecurityUtils.getSubject().getSession().setAttribute(Constants.WWC_ERR_CAPTCHA, "1");
			path = "redirect:/wwc/index";
		}
		SecurityUtils.getSubject().getSession().removeAttribute(Constants.WWC_KEY_CAPTCHA);
		return path;
	}

	/**
	 * 中文主页
	 */
	@RequestMapping(value = "/cn/index", method = RequestMethod.GET)
	public String index_cn(HttpServletRequest request,Model model) {
		setErrorModel(model);
		List<InvitationNews> newsList =invitationNewsService.getForIndexNewList(9999, _CN_FORM);
		model.addAttribute("news", newsList);
		return  "/unis/frontEnd/wwc/cn/home";
	}
	/**
	 * 中文表单编辑
	 */
	@RequestMapping(value = "/cn/edit", method = RequestMethod.GET)
	public String edit_cn(HttpServletRequest request,Model model){

		return  doEdit(model, _CN_FORM);
	}
	/**
	 * 中文表单保存
	 */
	@RequestMapping(value = "/cn/save", method = RequestMethod.POST)
	public String save_cn(HttpServletRequest request,InvitationFormDto in){
		return doSaveForm(request,_CN_FORM, in);
	}

	/**
	 * 英文主页
	 */
	@RequestMapping(value = "/en/index", method = RequestMethod.GET)
	public String index_en(HttpServletRequest request,Model model) {
		setErrorModel(model);
		List<InvitationNews> newsList =invitationNewsService.getForIndexNewList(9999, _EN_FORM);
		model.addAttribute("news", newsList);
		return  "/unis/frontEnd/wwc/en/home";
	}
	/**
	 * 英文表单编辑
	 */
	@RequestMapping(value = "/en/edit", method = {RequestMethod.GET,RequestMethod.POST})
	public String edit_en(HttpServletRequest request,Model model){

		return  doEdit(model, _EN_FORM);
	}

	/**
	 * 英文表单保存
	 */
	@RequestMapping(value = "/en/save", method = RequestMethod.POST)
	public String save_en(HttpServletRequest request,InvitationFormDto in){
		return doSaveForm(request,_EN_FORM, in);
	}
	/**
	 *设置model，判断是否出现错误，验证码还是邀请码错误
	 * @param model
	 */
	private void setErrorModel(Model model){
		Object err_captcha = SecurityUtils.getSubject().getSession().getAttribute(Constants.WWC_ERR_CAPTCHA);
		Object err_invitation_code = SecurityUtils.getSubject().getSession().getAttribute(Constants.WWC_ERR_INVITATION_CODE);
		if(err_captcha!=null || err_invitation_code != null){
			model.addAttribute("err", "1");
		}
		SecurityUtils.getSubject().getSession().removeAttribute(Constants.WWC_ERR_CAPTCHA);
		SecurityUtils.getSubject().getSession().removeAttribute(Constants.WWC_ERR_INVITATION_CODE);
	}

	@RequestMapping(value = "/idcode/{content}", method = RequestMethod.GET)
	public String show_qrcode(HttpServletRequest request,Model model,@PathVariable(value="content") String content){
		String returnPath  = "/unis/frontEnd/wwc/qrcode";
		if(content!=null){
			InvitationFormApplicant ifa = invitationFormApplicantService.findByIdentityCode(content);
			String ifaqrcode ="" ,ifaname = "";
			if(ifa != null){
				ifaqrcode = "/wwc/qrcode?content=" + ifa.getIdentityCode();
				if(ifa.getInvitationForm().getFormType() == _CN_FORM){
					ifaname = ifa.getName();
					returnPath = "/unis/frontEnd/wwc/cn/qrcode";
				}else if(ifa.getInvitationForm().getFormType() == _EN_FORM){
					ifaname =  ifa.getFirstName() + " " +  ifa.getLastName();
					returnPath = "/unis/frontEnd/wwc/en/qrcode";
				}
			}else{
				ifaqrcode = "/wwc/qrcode?content=error";
				ifaname = "error";
			}
			model.addAttribute("qrcode",ifaqrcode);
			model.addAttribute("name",ifaname);
		}

		return returnPath;
	}

	@RequestMapping(value = "/cn/success", method = RequestMethod.GET)
	public String success(HttpServletRequest request,Model model){
		Object successTag = request.getSession().getAttribute(_INVITATION_FORM_SUCCESS_TAG);
		Object code = request.getSession().getAttribute(_INVITATION_CODE_TAG);
		if(code!=null){
			if(successTag!=null){
				@SuppressWarnings("unchecked")
				List<SuccessDto> successDtoList = (List<SuccessDto> ) successTag;
				model.addAttribute("successDtoList",successDtoList);
			}
		}else{
			return "redirect:/wwc/index";
		}
		return "/unis/frontEnd/wwc/cn/success" ;
	}

	@RequestMapping(value = "/en/success", method = RequestMethod.GET)
	public String englishSuccess(HttpServletRequest request,Model model){
		Object successTag = request.getSession().getAttribute(_INVITATION_FORM_SUCCESS_TAG);
		Object code = request.getSession().getAttribute(_INVITATION_CODE_TAG);
		if(code!=null){
			if(successTag!=null){
				@SuppressWarnings("unchecked")
				List<SuccessDto> successDtoList = (List<SuccessDto> ) successTag;
				model.addAttribute("successDtoList",successDtoList);
			}
		}else{
			return "redirect:/wwc/index";
		}
		return "/unis/frontEnd/wwc/en/success" ;

	}

	private List<SuccessDto> getSuccessDtoList(List<InvitationFormApplicant> invitationFormApplicantList){
		List<SuccessDto> successDtoList = Lists.newArrayList();
		if (invitationFormApplicantList != null) {
			for (int i = 0; i < invitationFormApplicantList.size(); i++) {
				SuccessDto successDto = new SuccessDto();
				successDto.setName(invitationFormApplicantList.get(i).getName());
				successDto.setFirstName(invitationFormApplicantList.get(i).getFirstName());
				successDto.setLastName(invitationFormApplicantList.get(i).getLastName());
				successDto.setQrcodeurl("/wwc/qrcode?content=" + invitationFormApplicantList.get(i).getIdentityCode());
				successDtoList.add(successDto);
			}
		}

		return successDtoList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String doEdit(Model model,int formType){
		Object invitationCode = SecurityUtils.getSubject().getSession().getAttribute(_INVITATION_CODE_TAG);
		String path ;
		InvitationForm invitationForm = null;
		if(invitationCode == null){
			path = "redirect:/wwc/index";
		}else{
			invitationForm = invitationFormService.getByCode((String)invitationCode);

			if(formType == _EN_FORM ){
				path = "/unis/frontEnd/wwc/en/edit";
				String selectid = invitationForm == null || StringUtils.isBlank(invitationForm.getDistrictCode()) ? "-1" : invitationForm.getDistrictCode();
				String country_english = mcodeService.getMbHtmlSelect("COUNTRY_ENGLISH", selectid);
				model.addAttribute("country_english", country_english);
			}else{
				path = "/unis/frontEnd/wwc/cn/edit";
				String selectid = invitationForm == null || StringUtils.isBlank(invitationForm.getDistrictCode()) ? "CN" : invitationForm.getDistrictCode();
				String country_chinese = mcodeService.getMbHtmlSelect("COUNTRY_CHINESE", selectid);
				model.addAttribute("country_chinese", country_chinese);
			}



		}
		model.addAttribute("invitationForm", invitationForm);
		Class[] clazz = new Class[]{InvitationForm.class,InvitationFormApplicant.class};
		model.addAttribute("length", PropertyAnnotationProcessUtil.getPropertyLength(clazz));
		return  path;
	}


	private String doSaveForm(HttpServletRequest request,int formType,InvitationFormDto in){
		Object invitationCode = SecurityUtils.getSubject().getSession().getAttribute(_INVITATION_CODE_TAG);
		String path;
		if(invitationCode != null ){
			in.setCode((String) invitationCode );
			InvitationForm invitationForm = null;

			if(formType == _CN_FORM){
				path = "redirect:/wwc/cn/success";
				invitationForm = getChineseInvitationForm(in);
			}else{
				path = "redirect:/wwc/en/success";
				invitationForm = getEnglishInvitationForm(in);
			}

			//boolean need2SendMessage = invitationForm.getId()!=null && invitationForm.getId() > 0L ? false:true; // 编辑or修改标记

			InvitationForm existInvitationForm = invitationFormService.getByCode((String)invitationCode);
			if(!(existInvitationForm != null && existInvitationForm.getIsSure() == _YES)){ //未确认
				invitationFormService.save(invitationForm);
			}

			//发送SMS/EMAIL
			//if(need2SendMessage){
				messageService.sent_identity_code_message(invitationForm);
			//}

			List<SuccessDto> successDtoList = getSuccessDtoList(invitationForm.getInvitationFormApplicantList());
			request.getSession().setAttribute(_INVITATION_FORM_SUCCESS_TAG, successDtoList);//重定向到成功页面，也可以id值传递，查询数据库记录
		}else{
			path = "redirect:/wwc/index";
		}
		return path;
	}


	private InvitationForm getEnglishInvitationForm(InvitationFormDto in){
		InvitationForm invitationForm = new InvitationForm();
		invitationForm.setId(in.getId());//记录id值
		InvitationCode iCode = invitationCodeService.findByCode(in.getCode());//此值通过session存储，不传送到前台
		invitationForm.setInvitationCode(iCode);//邀请码
		invitationForm.setIsSure(_NO);//确认
		invitationForm.setFormType(_EN_FORM);//表单类型
		invitationForm.setPickUpOrNot( _NO);//是否需要接机,已经取消
		invitationForm.setCompanyName(in.getCompanyname());//公司
		Date arriveDate = DateUtil.parseSimpleDT(_DATE_FORMAT, in.getArrvialdate());//到达日期
		invitationForm.setArriveDate(arriveDate);
		invitationForm.setHotel(in.getHotel() > -1 ? in.getHotel() : null );//酒店编号
		invitationForm.setSpecialDiet(in.getSpecial_diet());//特殊饮食习惯
		invitationForm.setNightTourOfQiRiver(in.getActivity1() ?_YES : _NO);//活动一：夜游岐江
		invitationForm.setVisitFormerResidenceOfSunYatSen(in.getActivity2()? _YES : _NO);//活动二：参观孙中山故居
		invitationForm.setShopping(in.getActivity3() ? _YES : _NO);//活动三：购物
		invitationForm.setEntertainment(in.getActivity4() ? _YES : _NO);//活动四：其他娱乐活动
		invitationForm.setDistrictCode(in.getCountry());//国家或地区
		String message = in.getMessage();
		if(message != null && message.length() > 512){
			message = "";//建议
		}
		invitationForm.setMessage(message);

		int applicantLen = 0;
		if(in.getFirstName()!=null && in.getLastName()!=null&&in.getApp_email()!=null){
			int firstNameLen = in.getFirstName().length;
			int lastNameLen = in.getLastName().length;
			int emailLen = in.getApp_email().length;
			applicantLen = firstNameLen;
			if(applicantLen >= lastNameLen){
				applicantLen = lastNameLen;
			}
			if(applicantLen >= emailLen){
				applicantLen = emailLen;
			}
		}
		System.out.println("applicantlen" + applicantLen);
		List<InvitationFormApplicant> list = Lists.newArrayList();
		int positionLen = in.getApp_position() == null ? 0 : in.getApp_position().length;
		for(int i=0;i<applicantLen;i++){
			if(i == _MAX_APPLICANT){
				break;
			}
			InvitationFormApplicant invitationFormApplicant = new InvitationFormApplicant();
			Long app_id = in.getApp_id() == null || i >= in.getApp_id().length ? null : in.getApp_id()[i]; //前台传回来的邀请嘉宾id
			invitationFormApplicant.setId(app_id);
			invitationFormApplicant.setFirstName(in.getFirstName()[i]);//first name
			invitationFormApplicant.setLastName(in.getLastName()[i]);//last name
			invitationFormApplicant.setEmail(in.getApp_email()[i]); //嘉宾email
			int gender = 0;
			try {
				gender = Integer.parseInt(in.getApp_gender()[i]);
			} catch (Exception e) {
				gender = 0;
			}
			invitationFormApplicant.setGender(gender);//性别
			invitationFormApplicant.setIsRoutine(i == 0 ? _YES : _NO); //表单传回来的第一个是常务联系人
			String mobileNumber = i >= in.getApp_mobilenumber().length ? "" : in.getApp_mobilenumber()[i];//手机号码
			invitationFormApplicant.setMobileNumber(mobileNumber);
			//身份二维码内容
			String identityCode = in.getApp_identityCode() == null || i >= in.getApp_identityCode().length  || ( in.getApp_identityCode()[i].equals("")) ? invitationFormApplicantService.generateAIdentityCode(8):in.getApp_identityCode()[i] ;
			invitationFormApplicant.setIdentityCode(identityCode);
			invitationFormApplicant.setInvitationForm(invitationForm);
			String position = i >= positionLen  ? "" : in.getApp_position()[i];
			invitationFormApplicant.setPosition(position);//职位
			list.add(invitationFormApplicant);
		}
		invitationForm.addInvitationFormApplicantList(list);
		return invitationForm;
	}

	private InvitationForm getChineseInvitationForm(InvitationFormDto in){
		InvitationForm invitationForm = new InvitationForm();
		invitationForm.setId(in.getId());//记录id值
		InvitationCode iCode = invitationCodeService.findByCode(in.getCode());//此值通过session存储，不传送到前台
		invitationForm.setInvitationCode(iCode);//邀请码
		invitationForm.setIsSure(_NO);//确认
		invitationForm.setFormType(_CN_FORM);//表单类型
		invitationForm.setCompanyName(in.getCompanyname());//公司
		Date arriveDate = DateUtil.parseSimpleDT(_DATE_FORMAT, in.getArrvialdate());//到达日期
		invitationForm.setArriveDate(arriveDate);
		invitationForm.setPickUpOrNot(_NO);//是否需要接机
		invitationForm.setFlightNo("");//航班号
		invitationForm.setDistrictCode(in.getCountry());//国家或地区
//		invitationForm.setPickUpOrNot(in.isPickup() ? _YES : _NO);//是否需要接机
//		String flightNo = "";
//		if(in.getFlight_no() != null && in.getFlight_no().length() < 50){//标准航班命名规则 “CZ3731”
//			flightNo = in.getFlight_no();
//		}
//		invitationForm.setFlightNo(flightNo);//航班号

		invitationForm.setHotel(in.getHotel() > -1 ? in.getHotel() : null );//酒店编号
//		invitationForm.setDinnerType(in.getFood());//餐饮类型
		invitationForm.setSpecialDiet(in.getSpecial_diet());//特殊饮食习惯
//		invitationForm.setNightTourOfQiRiver(in.getActivity1() ?_YES : _NO);//活动一：夜游岐江
//		invitationForm.setVisitFormerResidenceOfSunYatSen(in.getActivity2()? _YES : _NO);//活动二：参观孙中山故居
//		invitationForm.setShopping(in.getActivity3() ? _YES : _NO);//活动三：购物
//		invitationForm.setEntertainment(in.getActivity4() ? _YES : _NO);//活动四：其他娱乐活动
		String message = in.getMessage();
		if(message != null && message.length() > 512){
			message = "";//建议
		}
		invitationForm.setMessage(message);
		//防止提交的时候被篡改长度不一致
		int applicantLen = 0;
		if(in.getApp_fullname() != null && in.getApp_mobilenumber() != null){
			int fullNameLen = in.getApp_fullname().length;
			int mobileNumberLen = in.getApp_mobilenumber().length;
			applicantLen = fullNameLen;
			if(applicantLen >= mobileNumberLen){
				applicantLen = mobileNumberLen;
			}
		}
		int positionArrLen = in.getApp_position() == null ? 0 : in.getApp_position().length;
		int appIdLen = in.getApp_id() == null ? 0 : in.getApp_id().length;
		int emailLen = in.getApp_email() == null ? 0 : in.getApp_email().length;
		int genderLen = in.getApp_gender() == null ? 0 : in.getApp_gender().length;
		List<InvitationFormApplicant> list = Lists.newArrayList();

		for(int i=0;i<applicantLen;i++){
			if(i == _MAX_APPLICANT){
				break;
			}
			InvitationFormApplicant invitationFormApplicant = new InvitationFormApplicant();
			Long app_id = i >= appIdLen ? null : in.getApp_id()[i]; //前台传回来的app_id
			invitationFormApplicant.setId(app_id);
			String name = in.getApp_fullname()[i]; // 嘉宾名字
			invitationFormApplicant.setName(name);
			String email = i >= emailLen ? "" : in.getApp_email()[i]; //嘉宾email
			invitationFormApplicant.setEmail(email);
			int gender = 0;
			try {
				if(i<genderLen){
					gender = Integer.parseInt(in.getApp_gender()[i]);
				}
			} catch (Exception e) {
				gender = 0;
			}
			invitationFormApplicant.setGender(gender);
			invitationFormApplicant.setIsRoutine(i == 0 ? _YES : _NO); //表单传回来的第一个是常务联系人

			String mobileNumber = in.getApp_mobilenumber()[i];
			invitationFormApplicant.setMobileNumber(mobileNumber);
			String identityCode = in.getApp_identityCode() == null || i >= in.getApp_identityCode().length  || ( in.getApp_identityCode()[i].equals("")) ? invitationFormApplicantService.generateAIdentityCode(8):in.getApp_identityCode()[i] ;
			invitationFormApplicant.setIdentityCode(identityCode);
			invitationFormApplicant.setInvitationForm(invitationForm);
			String position = i >= positionArrLen  ? "" : in.getApp_position()[i];
			invitationFormApplicant.setPosition(position);
			list.add(invitationFormApplicant);
		}
		invitationForm.addInvitationFormApplicantList(list);
		return invitationForm;
	}

	/**
	 * 申请邀请码实体
	 * @param applyCodeDto
	 * @param type
	 * @return
	 */
//	private InvitationCode getInvitationCode(ApplyCodeDto applyCodeDto,int  type){
//		InvitationCode invitationCode = new InvitationCode();
//		invitationCode.setType(type);
//		invitationCode.setName(applyCodeDto.getFullname());
//		invitationCode.setCompany(applyCodeDto.getCompanyname());
//		invitationCode.setPosition(applyCodeDto.getPosition());
//		invitationCode.setTel(applyCodeDto.getPhone());
//		invitationCode.setEmail(applyCodeDto.getEmail());
//		return invitationCode;
//	}

}


