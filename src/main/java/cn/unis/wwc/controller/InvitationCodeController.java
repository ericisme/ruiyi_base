package cn.unis.wwc.controller;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ruiyi.base.service.userActionLog.UserActionLogService;
import cn.ruiyi.base.web.mvc.StringView;
import cn.ruiyi.base.simplemvc.controller.BaseManagerController;
import cn.ruiyi.base.simplemvc.service.BaseManagerService;
import cn.unis.wwc.entity.InvitationCode;
import cn.unis.wwc.service.InvitationCodeService;
import cn.unis.wwc.service.MessageService;


/**
 * 邀请码管理 .controller
 * @author eric
 *
 */
@Controller
@RequestMapping(value = "/backEnd/wwc/invitationCode")
public class InvitationCodeController extends BaseManagerController<InvitationCode>{
	@Autowired
	private MessageService messageService;
	@Autowired
	private UserActionLogService userActionLogService;
	@Autowired
	private InvitationCodeService invitationCodeService;
	@Override
	protected BaseManagerService<InvitationCode> getDomainService() {
		return invitationCodeService;
	}

	@PostConstruct//此注解功能是，让spring加载bean之后会调用一次的方法。
	public void controllerInit(){
		indexPage  = "unis/backEnd/wwc/invitationCode/index";
		listPage   = "unis/backEnd/wwc/invitationCode/list";
		editPage   = "unis/backEnd/wwc/invitationCode/edit";
		showPage   = "unis/backEnd/wwc/invitationCode/show";

		indexPermission   = "";
		queryPermission   = "";
		editPermission    = "invitationCode:edit";
		savePermission    = "invitationCode:edit";
		deletePermission  = "invitationCode:delete";
	}

	/**
	 * 生成多个邀请码
	 * num : 生成个数
	 * begin : 开始序号
	 */
	@RequestMapping(value = "/genInvitationCode")
	@ResponseBody
	public String genInvitationCode(HttpServletRequest request, Integer num, Integer begin){
		/*
		 * 方法 式 权限过滤
		 */
		if(!("".equals("invitationCode:review") || SecurityUtils.getSubject().isPermitted("invitationCode:review"))){
			return "权限不足";
		}		
		for(int i =0; i<num;i++){
			InvitationCode invitationCode = new InvitationCode();
			invitationCode.setName("无名氏"+(i+begin));
			invitationCodeService.save(invitationCode);
		}
		return "生成成功";
	}
	
	/**
	 * review审核
	 */
	@RequestMapping(value = "/review/{id}")
	public ModelAndView review(HttpServletRequest request, @PathVariable Long id){
		/*
		 * 方法 式 权限过滤
		 */
		if(!("".equals("invitationCode:review") || SecurityUtils.getSubject().isPermitted("invitationCode:review"))){
			return new ModelAndView(new StringView("<script type='text/javascript'>alert('权限不足');</script>"));
		}
		Map<String,Object> map = new HashMap<String,Object>();
		InvitationCode doamin = getDomainService().findById(id);
		map.put("entity", doamin);				//编辑新闻，当为新增时，此变量为空
		return new ModelAndView("unis/backEnd/wwc/invitationCode/review", map);
	}


	/**
	 * saveReview 处理审核提交
	 * @param request
	 * @param doamin
	 * @return
	 */
	@RequestMapping(value = "/saveReview")
	public ModelAndView saveReview(HttpServletRequest request, InvitationCode doamin, Integer status) {
		/*
		 * 方法 式 权限过滤
		 */
		if(!("".equals("invitationCode:review") || SecurityUtils.getSubject().isPermitted("invitationCode:review"))){
			return new ModelAndView(new StringView("<script type='text/javascript'>alert('权限不足');</script>"));
		}
		StringView view = new StringView();
		try{
			//审核通过，生成邀请码
			if(status.equals(1)){
				if(doamin.getCode()==null){
					doamin.setCode(invitationCodeService.genANewCode(6));
				}else if("".equals(doamin.getCode())){
					doamin.setCode(invitationCodeService.genANewCode(6));
				}
				doamin.setReviewStatus(1);
				invitationCodeService.saveWithoutCode(doamin);
				//发送邀请码到SMS/EMAIL
				messageService.sent_invitation_code_message(doamin);
				//SMSUtil.send(doamin.getTel(),"你好："+doamin.getName()+"("+doamin.getCompany()+") 你的邀请码申请成功，为"+doamin.getCode()+",请登录http://www.shiyugame.com/wwc/index使用");
				//MailUtil.sendEmail(doamin.getEmail(), "你的邀请码申请成功【世宇科技】", "你好："+doamin.getName()+"("+doamin.getCompany()+") 你的邀请码申请成功，为"+doamin.getCode()+",请登录http://www.shiyugame.com/wwc/index使用");
				userActionLogService.log("邀请码审核", "通过邀请码记录 id:"+doamin.getId()+" 申请人名字:"+doamin.getName());
			}
			//审核不通过，不生成邀请码
			if(status.equals(0)){
				doamin.setCode(null);
				doamin.setReviewStatus(0);
				invitationCodeService.saveWithoutCode(doamin);
				//SMSUtil.send(doamin.getTel(),"你好："+doamin.getName()+"("+doamin.getCompany()+") 你的邀请码申请不通过");
				//MailUtil.sendEmail(doamin.getEmail(), "你的邀请码申请不成功【世宇科技】", "你好："+doamin.getName()+"("+doamin.getCompany()+") 你的邀请码申请不通过");
				userActionLogService.log("邀请码审核", "不通过邀请码记录 id:"+doamin.getId()+" 申请人名字:"+doamin.getName());
			}


			view.setContent("success");


		}catch(Exception ex){
			view.setContent("保存不成功");
			ex.printStackTrace();
		}
		return new ModelAndView(view);
	}

	/**
	 * 导出excel
	 */
	@RequestMapping(value = "exportXls")
	public void exportXls(HttpServletRequest request, HttpServletResponse response) throws Exception{
		/*
		 * 方法 式 权限过滤
		 */
		if(!("".equals(queryPermission) || SecurityUtils.getSubject().isPermitted(queryPermission))){
			return ;
		}
		Page<InvitationCode> paginate = getDomainService().pageParamQuery(request);
		if(paginate.getContent().size()>0){
			OutputStream os = response.getOutputStream();
			String filename = new String("邀请码信息".getBytes("GB2312"), "ISO_8859_1");
			response.setHeader("Content-Disposition","attachment;filename=" + filename + ".xls");
			response.setContentType("application/vnd.ms-excel");
			invitationCodeService.exportXls(paginate.getContent(), os);
		}else{
			String message = "<script>alert(\"对不起，你的条件查询结果记录为0条\"); window.close();</script>";
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(message);
		}
	}

}




