package cn.unis.wwc.service;

import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

import cn.ruiyi.base.util.PathUtil;
import cn.unis.wwc.entity.InvitationCode;
import cn.unis.wwc.entity.InvitationForm;
import cn.unis.wwc.entity.InvitationFormApplicant;
import cn.unis.wwc.service.thread.EmailThread;
import cn.unis.wwc.service.thread.SMSThread;


/**
 * 邀请系统 短信/email 发送service;
 */
@Component
public class MessageService {

	/**
	 * 成功填写资料表单，返回个人身份二维码
	 */
	// 内容中文
	//public static String INVITATION_FORM_SUCCESS_CONTENT_CN = "感谢你的参与，你(<applicantName>)的个人身份二维码为 <CURRENT_SYSTEM_DOMAIN>/wwc/idcode/<identityCode> ,用于大会入场时使用，请妥善保管。";
	public static String INVITATION_FORM_SUCCESS_CONTENT_CN = "感谢你的参与，这是您(<applicantName>)的个人身份二维码: <CURRENT_SYSTEM_DOMAIN>/wwc/idcode/<identityCode> ,大会当天入场时验证使用，请您妥善保管。（温馨提示：这是唯一的入场凭证，请您尽量保存图片至手机或打印携带）。";
	// 内容英文
	//public static String INVITATION_FORM_SUCCESS_CONTENT_EN = "Thank you for your participation, your(<applicantName>) identityCode is <CURRENT_SYSTEM_DOMAIN>/wwc/idcode/<identityCode> ，"
	//		+ " using for the entrance of the comferene, please safekeeping.";
	public static String INVITATION_FORM_SUCCESS_CONTENT_EN = "Thank you for your participation, your(<applicantName>) identityCode is <CURRENT_SYSTEM_DOMAIN>/wwc/idcode/<identityCode> ，"
			+ "  please keep this safe and use this for entry to the conference. Universal Space UNIS GLobal Partner's Expo.";
	// email标题中文
	public static String INVITATION_FORM_SUCCESS_EMAIL_TITLE_CN = " <applicantName> 的大会入场二维码【世宇科技】";
	// email标题英文
	public static String INVITATION_FORM_SUCCESS_EMAIL_TITLE_EN = "<applicantName>'s identity Code for entry to the conference[UNIS Global Partner's Expo]";

	
	/**
	 * 邀请码 自助申请，审核成功
	 */
	//内容中文
	public static String INVITATION_CODE_PASS_CONTENT_CN = "你好：<name>(<companyName>) 你的大会邀请码申请成功，为 <invitationCode> , 请登录 <CURRENT_SYSTEM_DOMAIN>/wwc/index 使用。";
	//内容英文
	public static String INVITATION_CODE_PASS_CONTENT_EN = "hello：<name>(<companyName>) your invitation_code for the conference is <invitationCode> , please login to <CURRENT_SYSTEM_DOMAIN>/wwc/index .";
	// email标题中文
	public static String INVITATION_CODE_PASS_EMAIL_TITLE_CN = "<name>, 你的大会邀请码申请成功【世宇科技】";
	//email标题英文
	public static String INVITATION_CODE_PASS_EMAIL_TITLE_EN = "<name>, your supply for the invitation_code of the conferece is success【UNIS TECH】";
	
	private Properties getProperties(){
		Properties props = new Properties();
		InputStream inputStream = PathUtil.class.getClassLoader().getResourceAsStream("config-wwc.properties");
			if(inputStream!=null){
				try {
					props.load(inputStream);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return props;
	}	
	/**
	 * 发送个人身份二维码
	 * @param invitationForm
	 */
	public void sent_identity_code_message(InvitationForm invitationForm){
		String send_idcode_sms =  getProperties().getProperty("send_idcode_sms");
		String send_idcode_email =  getProperties().getProperty("send_idcode_email");
		
		String content;
		String emailTile;		

		for(InvitationFormApplicant invitationFormApplicant : invitationForm.getInvitationFormApplicantList()){
			String applicantName = invitationForm.getFormType()==21?invitationFormApplicant.getName():(invitationFormApplicant.getFirstName()+" "+invitationFormApplicant.getLastName());
			if(invitationForm.getFormType()==21){
				content = INVITATION_FORM_SUCCESS_CONTENT_CN;
				emailTile = INVITATION_FORM_SUCCESS_EMAIL_TITLE_CN;
			}else{
				content = INVITATION_FORM_SUCCESS_CONTENT_EN;
				emailTile = INVITATION_FORM_SUCCESS_EMAIL_TITLE_EN;
			}
			content = content.replace("<applicantName>", applicantName)
					.replace("<CURRENT_SYSTEM_DOMAIN>", PathUtil.getConfigResource("CURRENT_SYSTEM_DOMAIN"))
					.replace("<identityCode>", invitationFormApplicant.getIdentityCode());
			emailTile = emailTile.replace("<applicantName>", applicantName);
			//每个人都发全部人的二维码
			for(InvitationFormApplicant invitationFormApplicant2 : invitationForm.getInvitationFormApplicantList()){
				//SMS
				if("true".equals(send_idcode_sms))
					(new Thread(new SMSThread(invitationFormApplicant2.getMobileNumber(), content))).start(); 
				//EMAIL
				if("true".equals(send_idcode_email))
					(new Thread(new EmailThread(invitationFormApplicant2.getEmail(), emailTile, content+"<br/> <img src='"+PathUtil.getConfigResource("CURRENT_SYSTEM_DOMAIN")+"/wwc/qrcode?content="+invitationFormApplicant.getIdentityCode()+"' /><br/>"+applicantName))).start(); 
			}
		}
	}
	
	
	/**
	 * 发送审核成功后的 邀请码
	 */
	public void sent_invitation_code_message(InvitationCode invitationCode){
		//自助申请类型才发送
		if(invitationCode.getType()==2){
			String content_cn = INVITATION_CODE_PASS_CONTENT_CN;
			String content_en = INVITATION_CODE_PASS_CONTENT_EN;
			String email_title_cn = INVITATION_CODE_PASS_EMAIL_TITLE_CN;
			String email_title_en = INVITATION_CODE_PASS_EMAIL_TITLE_EN;
			//组装发送信息
			content_cn = content_cn.replace("<name>", invitationCode.getName())
					.replace("<companyName>", invitationCode.getCompany())
					.replace("<invitationCode>", invitationCode.getCode())
					.replace("<CURRENT_SYSTEM_DOMAIN>", PathUtil.getConfigResource("CURRENT_SYSTEM_DOMAIN"));
			content_en =content_en.replace("<name>", invitationCode.getName())
					.replace("<companyName>", invitationCode.getCompany())
					.replace("<invitationCode>", invitationCode.getCode())
					.replace("<CURRENT_SYSTEM_DOMAIN>", PathUtil.getConfigResource("CURRENT_SYSTEM_DOMAIN"));
			email_title_cn = email_title_cn.replace("<name>", invitationCode.getName());
			email_title_en = email_title_en.replace("<name>", invitationCode.getName());
			//SMS
			(new Thread(new SMSThread(invitationCode.getTel(), content_en))).start();
			(new Thread(new SMSThread(invitationCode.getTel(), content_cn))).start(); 			
			//EMAIL
			String email_title = email_title_en +"; " + email_title_cn;
			String email_content = content_en + "   \n"+"\n"+"\n   " + content_cn ;
			(new Thread(new EmailThread(invitationCode.getEmail(), email_title, email_content))).start(); 			
		}
	}
	
	
	
	
}
