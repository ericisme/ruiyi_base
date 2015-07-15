package cn.ruiyi.base.constants;

/**
 * <pre>
 * 常量定义
 * </pre>
 * @author eric
 * @version 1.0, 2013-10-14
 */
public interface Constants
{
	/**
	 * 基础数据库SCHEMA引用
	 */
	final static String BASE = "unis_commerce";
	/**
	 * 每页显示记录数
	 */
	final static int PAGE_SIZE = 10;
	/**
	 * 角色停用状态
	 */
	final static int ROLE_STOP_STATUS = -1;
	/**
	 * 角色启用状态
	 */
	final static int ROLE_START_STATUS = 1;
	/**
	 * 子系统正常标题
	 */
	final static String SUBSYS_PERMIS_NORMAL_TITLE = "提供家校互动的专门服务!";
	/**
	 * 子系统停用标题
	 */
	final static String SUBSYS_PERMIS_UNABLE_TITLE = "很抱歉,此系统暂未启用!";
	/**
	 * 子系统无权限标题
	 */
	final static String SUBSYS_PERMIS_NOTENOUGH_TITLE = "很抱歉,您没有访问该系统的权限!";

	/**
	 * 新闻图片上传目录
	 */
	final static String NewsImgPath="/upload/newsImgPath";

	/**
	 * 大会新闻图片上传目录
	 */
	final static String InvitationNewsImgPath="/upload/wwc/invitationNewsImgPath";
	/**
	 * 大会新闻图片上传目录
	 */
	final static String InvitationNewsTitleImgPath="/upload/wwc/invitationNewsTitleImg";
	
	
	final static String ALIPAY_KEY_CAPTCHA = "ALIPAY_KEY_CAPTCHA";

	final static String WWC_KEY_CAPTCHA = "WWC_KEY_CAPTCHA";

	final static String WWC_APPLY_INVITATION_CAPTCHA = "WWC_APPLY_INVITATION_CAPTCHA";

	final static String WWC_ERR_CAPTCHA = "WWC_ERR_CAPTCHA";

	final static String WWC_ERR_INVITATION_CODE = "WWC_ERR_INVITATION_CODE";

	final static String SERVER_IP_ADDRESS = "http://199.199.13.34:8080";


}
