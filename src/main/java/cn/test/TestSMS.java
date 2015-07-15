package cn.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;

import com.alipay.config.AlipayConfig;
import com.alipay.util.httpClient.HttpProtocolHandler;
import com.alipay.util.httpClient.HttpRequest;
import com.alipay.util.httpClient.HttpResponse;
import com.alipay.util.httpClient.HttpResultType;

public class TestSMS {

	public static void main(String[] args) throws HttpException, IOException {
		// TODO Auto-generated method stub
        //待请求参数数组
        Map<String, String> sPara = new HashMap<String, String>();
        sPara.put("UserID", "960015");
        sPara.put("Account", "admin");
        sPara.put("Password", "N1WBT2");
        //sPara.put("UserID", "992538");
        //sPara.put("Account", "121212");
        //sPara.put("Password", "liu121212");
        //sPara.put("Phones", "18676240925;18575008030;13798959507;");
        sPara.put("Phones", "18676240925;");
        //sPara.put("Phones", "18664434035;");
        //sPara.put("Phones", "13798959507;");
        sPara.put("Content", "111感谢你填写的保贵的资料，我们已经审核通过啦，这个是你的个人身份二维码http://199.199.14.205:8080/frontEnd/game/description/qrcode/21，请妥善保管,大会时入场使用.【世宇科技】");
        sPara.put("SendTime", "");
        sPara.put("SendType", "1");
        sPara.put("PostFixNumber", "1");

        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

        HttpRequest request = new HttpRequest(HttpResultType.STRING);
        //设置编码集
        request.setCharset(AlipayConfig.input_charset);

        request.setParameters(generatNameValuePair(sPara));
        request.setUrl("http://www.mxtong.net.cn/GateWay/Services.asmx/DirectSend");

        HttpResponse response = httpProtocolHandler.execute(request,"","");
        String aba =response.getStringResult();
        System.out.println(aba);
        if(aba!=null){
        	if(aba.indexOf("<RetCode>Sucess</RetCode>")>0){
        		System.out.println("成功");
        	}
        }
	}
	
	
    /**
     * MAP类型数组转换成NameValuePair类型
     * @param properties  MAP类型数组
     * @return NameValuePair类型数组
     */
    private static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }

        return nameValuePair;
    }

    
}
