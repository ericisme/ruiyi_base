package cn.unis.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;

import com.alipay.config.AlipayConfig;
import com.alipay.util.httpClient.HttpProtocolHandler;
import com.alipay.util.httpClient.HttpRequest;
import com.alipay.util.httpClient.HttpResponse;
import com.alipay.util.httpClient.HttpResultType;

public class SMSUtil {

	/**
	 * 发送
	 * 
	 * @param phoneNumber
	 *            手机号
	 * @param contant
	 *            内容
	 * @param serviceCompany
	 *            1=mxton,2=ihuyi
	 * @return
	 * @throws Exception
	 */
	public static boolean send(String phoneNumber, String contant, Integer serviceCompany) throws Exception {

		//mxton
		if (serviceCompany == 1) {
			Map<String, String> sPara = new HashMap<String, String>();
			sPara.put("UserID", "960015");
			sPara.put("Account", "admin");
			sPara.put("Password", "N1WBT2");
			sPara.put("Phones", phoneNumber + ";");
			sPara.put("Content", contant + "【世宇科技】");
			sPara.put("SendTime", "");
			sPara.put("SendType", "1");
			sPara.put("PostFixNumber", "1");
			HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler
					.getInstance();
			HttpRequest request = new HttpRequest(HttpResultType.STRING);
			// 设置编码集
			request.setCharset(AlipayConfig.input_charset);
			request.setParameters(generatNameValuePair(sPara));
			request.setUrl("http://www.mxtong.net.cn/GateWay/Services.asmx/DirectSend");
			HttpResponse response = httpProtocolHandler
					.execute(request, "", "");
			String aba = response.getStringResult();
			System.out.println(aba);
			System.out.println(aba.indexOf("<RetCode>Sucess</RetCode>"));
			if (aba != null) {
				if (aba.indexOf("<RetCode>Sucess</RetCode>") > 0) {
					return true;
				}
			}
			return false;
		}
		
		//ihuyi
		if(serviceCompany==2){
			Map<String, String> sPara = new HashMap<String, String>();
			sPara.put("account", "cf_shiyuyouxi");
			sPara.put("password", "shiyuyouxi123");
			sPara.put("mobile", phoneNumber);
			sPara.put("content", contant);
			HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler
					.getInstance();
			HttpRequest request = new HttpRequest(HttpResultType.STRING);
			request.setParameters(generatNameValuePair(sPara));
			request.setUrl("http://106.ihuyi.cn/webservice/sms.php?method=Submit");
			HttpResponse response = httpProtocolHandler
					.execute(request, "", "");
			String aba = response.getStringResult();
			System.out.println(aba);
		}
		
		return false;
	}

	/**
	 * MAP类型数组转换成NameValuePair类型
	 * 
	 * @param properties
	 *            MAP类型数组
	 * @return NameValuePair类型数组
	 */
	private static NameValuePair[] generatNameValuePair(
			Map<String, String> properties) {
		NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
		int i = 0;
		for (Map.Entry<String, String> entry : properties.entrySet()) {
			nameValuePair[i++] = new NameValuePair(entry.getKey(),
					entry.getValue());
		}
		return nameValuePair;
	}

	public static void main(String args[]) throws Exception {
		SMSUtil.send("18676240925","明杰，您的验证码是：1234。请不要把验证码泄露给其他人。", 1);
		//SMSUtil.send("18676240925","您的验证请不要把验证码泄露给其他人。", 2);
		// SMSUtil.send("18676240925","youyi,这是我们网站用的单位的短信接口，本短信发送时间为 8:44:40");
		// SMSUtil.send("18664434035","youyi,这是我们网站用的单位的短信接口，本短信发送时间为 8:44:40");
		// SMSUtil.send("18676240925","感谢你的参与，这是(steve jobs)的身份二维码：http://www.shiyugame.com/wwc/idcode/fdse873d, 用于大会入场时使用，请妥善保管");
	}
}
