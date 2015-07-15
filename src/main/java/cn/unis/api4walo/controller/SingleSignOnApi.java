package cn.unis.api4walo.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.unis.service.interfaces.IGameCenterService;
import cn.unis.transit.BaseReturnValue;
import cn.unis.utils.JsonUtils;
import cn.unis.utils.Security;

import com.fasterxml.jackson.core.type.TypeReference;



/**
 * 用于平台app 跳转浏览器 登录
 * @author eric
 *
 */
@Controller
@RequestMapping(value = "/api/public")
public class SingleSignOnApi {
	
	@Autowired
	private IGameCenterService iGameCenterService;
	
	
	/**
	 * 一次性密码登录
	 * @param user_key	需要登录的user_key
	 * @param ls		一次性登录密码
	 * @param back_url	登录后跳转的url
	 * @return
	 * @throws JSONException 
	 */
	@RequestMapping(value="/shiyugame_app_login",method={RequestMethod.GET})
	public String shiyugame_app_login( HttpServletRequest request, String user_key, String ls, String back_url) throws JSONException{	
		//for test "http://127.0.0.1/api/public/shiyugame_app_login?user_key=abc&ls=fsdfsdfsdfsdnfsdkfnsdf&back_url=/frontEnd/giftcenter/index"
		//根据user_key,ls获得带accessToekn信息的反馈
		BaseReturnValue<String> verify_returns = iGameCenterService.getAccessTokenForSSO(user_key, ls);
		//使用AES解密反馈，获得oauth_token和oauth_token_secret
		JSONObject decrypt_json =  new JSONObject(Security.decrypt(verify_returns.getMsg(), Security.ENCRYPTION_KEY));
		String oauth_token = decrypt_json.getString("oauth_token");
		String oauth_token_secret =  decrypt_json.getString("oauth_token_secret");
		//根据oauth_token和oauth_token_secret 构造一个accessToken
		Token accessToken = new Token(oauth_token, oauth_token_secret);
		//把accessToken放入session以作为登录
		request.getSession().setAttribute("accessToken", accessToken);
		return "redirect:"+back_url;
	}
	
}
