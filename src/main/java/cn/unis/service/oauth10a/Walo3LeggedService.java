package cn.unis.service.oauth10a;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.commons.codec.binary.Base64;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.springframework.stereotype.Component;

import cn.ruiyi.base.util.PathUtil;
import cn.ruiyi.base.util.StringUtil;
import cn.unis.utils.HardWareUtils;

/**
 * Walo OAuth1.0a 验证及请求
 * @author eric
 *
 */
@Component
public class Walo3LeggedService {

	private OAuthService service = new ServiceBuilder()
			.provider(Walo3LeggedApi.class).apiKey(Walo3LeggedApi.APIKEY)
			.apiSecret(Walo3LeggedApi.APISECRET)
			.build();

	public Token getRequestToken(String callback_path){
		return new ServiceBuilder()
		.provider(Walo3LeggedApi.class).apiKey(Walo3LeggedApi.APIKEY)
		.apiSecret(Walo3LeggedApi.APISECRET)
		.callback(callback_path)
		.build().getRequestToken();
	}
	
	public Token getAccessToken(Token requestToken, String oauth_verifier){
		//System.out.println("getAccessToken request token:"+requestToken.getToken());
		//System.out.println("oauth_verifier:"+oauth_verifier);
		Verifier verifier = new Verifier(oauth_verifier);
		Token accessToken = service.getAccessToken(requestToken, verifier);
		return accessToken;
	}
	
	/**
	 * 使用OAuthRequest请求walo 3-legged 的 json api,此方法可以支持post,put等的api.
	 * 此方法不对返回值解码 
	 * @param apiUrl
	 * @param agrs
	 * @return
	 */
	public String requetJsonBodyForWalo3LeggedApiByOAuthRequestDoNotParse(Token accessToken, OAuthRequest request ) {
		try{
		}catch(org.scribe.exceptions.OAuthException e){
			System.out.println("org.scribe.exceptions.OAuthException, please check."+"\n"+e.toString());
			return "org.scribe.exceptions.OAuthException, please check."+"\n"+e.toString();
		}
		boolean ifAuth = false;
		String body = "";
		while (!ifAuth) {
			request.addHeader("REQUEST_ID", UUID.randomUUID().toString());
			request.addHeader("DEVICE_ID", HardWareUtils.getCpuId());
			service.signRequest(accessToken, request);
			Response response = request.send();
			//System.out.println("response.getCode():"+response.getCode());
			if ("401".equals(response.getCode())) {
				ifAuth = false;
			} else {
				body = response.getBody();
				ifAuth = true;
				//System.out.println(body);
			}
		}
		body = body.replaceAll("\"msg\":\"\"", "\"msg\":null");
		System.out.println(body);
		return body;
	}
	
	
	
	/**
	 * 使用OAuthRequest请求walo 3-legged 的 json api,此方法可以支持post,put等的api.
	 * 
	 * @param apiUrl
	 * @param agrs
	 * @return
	 */
	public String requetJsonBodyForWalo3LeggedApiByOAuthRequest(Token accessToken, OAuthRequest request ) {
		try{
		}catch(org.scribe.exceptions.OAuthException e){
			System.out.println("org.scribe.exceptions.OAuthException, please check."+"\n"+e.toString());
			return "org.scribe.exceptions.OAuthException, please check."+"\n"+e.toString();
		}
		boolean ifAuth = false;
		String body = "";
		while (!ifAuth) {
			request.addHeader("REQUEST_ID", UUID.randomUUID().toString());
			request.addHeader("DEVICE_ID", HardWareUtils.getCpuId());
			service.signRequest(accessToken, request);
			Response response = request.send();
			//System.out.println("response.getCode():"+response.getCode());
			if ("401".equals(response.getCode())) {
				ifAuth = false;
			} else {
				body = StringUtil.decodeUnicode(parseWaloResponse(response.getBody()));
				ifAuth = true;
				//System.out.println(body);
			}
		}
		body = body.replaceAll("\"msg\":\"\"", "\"msg\":null");
		System.out.println(body);
		return body;
	}
	
	
	/**
	 * 请求walo 3-legged 的 json api
	 * 
	 * @param apiUrl
	 * @param agrs
	 * @return
	 */
	public String requetJsonBodyForWalo3LeggedApi(Token accessToken, String apiUrl, String... agrs) {
		try{
		}catch(org.scribe.exceptions.OAuthException e){
			System.out.println("org.scribe.exceptions.OAuthException, please check."+"\n"+e.toString());
			return "org.scribe.exceptions.OAuthException, please check."+"\n"+e.toString();
		}
		boolean ifAuth = false;
		String body = "";
		while (!ifAuth) {
			//System.out.println("requetJsonBodyForWalo2LeggedApi");
			System.out.println(this.apiFormat(apiUrl, agrs));
			OAuthRequest request = new OAuthRequest(Verb.GET, this.apiFormat(apiUrl, agrs));
			request.addHeader("REQUEST_ID", UUID.randomUUID().toString());
			request.addHeader("DEVICE_ID", HardWareUtils.getCpuId());
			service.signRequest(accessToken, request);
			Response response = request.send();
			//System.out.println("response.getCode():"+response.getCode());
			if ("401".equals(response.getCode())) {
			} else {
				try{
					body = StringUtil.decodeUnicode(parseWaloResponse(response.getBody()));
				}catch(Exception e){
					body = response.getBody();
				}
				ifAuth = true;
				//System.out.println(body);
			}
		}
		body = body.replaceAll("\"msg\":\"\"", "\"msg\":null");
		System.out.println(body);
		return body;
	}

	/**
	 * 请求walo 3-legged 的 html api
	 * 
	 * @param apiUrl
	 * @param agrs
	 * @return
	 */
	public String requetHtmlBodyForWalo3LeggedApi(Token accessToken, String apiUrl, String... agrs) {
		try{
		}catch(org.scribe.exceptions.OAuthException e){
			System.out.println("org.scribe.exceptions.OAuthException, please check."+"\n"+e.toString());
			return "org.scribe.exceptions.OAuthException, please check."+"\n"+e.toString();
		}		
		boolean ifAuth = false;
		String body = "";
		while (!ifAuth) {
			OAuthRequest request = new OAuthRequest(Verb.GET, this.apiFormat(apiUrl, agrs));
			request.addHeader("REQUEST_ID", UUID.randomUUID().toString());
			request.addHeader("DEVICE_ID", HardWareUtils.getCpuId());
			service.signRequest(accessToken, request);
			Response response = request.send();
			if ("401".equals(response.getCode())) {
			} else {
				body = response.getBody();
				ifAuth = true;
			}
		}
		return body;
	}

	/**
	 * 格式化apiUrl, args参数按顺序替换<?>
	 */
	public String apiFormat(String apiUrl, String... agrs){
		String formatedApiUrl = apiUrl;
		for (int i = 0; i < agrs.length; i++) {
			String replaceStr = formatedApiUrl.substring(
					formatedApiUrl.indexOf("<"),
					formatedApiUrl.indexOf(">") + 1);
			formatedApiUrl = formatedApiUrl.replace(replaceStr, agrs[i]);
		}
		return formatedApiUrl;
	}

	/**
	 * 解析walo response 字串。
	 * @param response
	 * @return
	 */
	public static String parseWaloResponse(String response) {
		String jsonStr = null;
		try {
			jsonStr = new String(Inflate(Base64.decodeBase64(response
					.getBytes())));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (DataFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}	
	
	
	public static byte[] Inflate(byte[] source) throws DataFormatException,
			IOException {
		Inflater inflater = new Inflater();
		ByteArrayOutputStream stream = null;
		byte[] result = null;
		try {
			inflater.setInput(source);
			stream = new ByteArrayOutputStream(source.length);
			byte[] buffer = new byte[1024];
			while (!inflater.finished()) {
				int decompressed = inflater.inflate(buffer);
				stream.write(buffer, 0, decompressed);
			}
			stream.close();
			result = stream.toByteArray();
			stream = null;
		} finally {
			inflater.end();
			if (stream != null) {
				stream.close();
			}
		}
		return result;
	}

	public static byte[] Deflate(byte[] source) throws IOException {
		Deflater deflater = new Deflater();
		ByteArrayOutputStream stream = null;
		byte[] result;
		try {
			deflater.setInput(source);
			deflater.finish();
			stream = new ByteArrayOutputStream(source.length);
			byte[] buffer = new byte[1024];
			while (!deflater.finished()) {
				int compressed = deflater.deflate(buffer);
				stream.write(buffer, 0, compressed);
			}
			stream.close();
			result = stream.toByteArray();
			stream = null;
		} finally {
			deflater.end();
			if (stream != null) {
				stream.close();
			}
		}
		return result;
	}




	/**
	 * test
	 * @param args
	 */
	public static void main(String[] args) {
	//	OAuthService serviceTest = new ServiceBuilder()
	//	.provider(Walo2LeggedApi.class).apiKey("539e8f4451")
	//	.apiSecret("57902c191da183c1893accafea1c48").build();
		
	//	Walo2LeggedService walo2LeggedAuthService = new Walo2LeggedService();
		/*System.out
				.println(walo2LeggedAuthService
						.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.GAME_CENTER_INFO_JSON_URL,"a449623b427d90567555c972dfb77344"));
		System.out
				.println(walo2LeggedAuthService
						.requetHtmlBodyForWalo2LeggedApi(Walo2LeggedApi.SCORES_HTML_URL));*/
		//System.out.println("Walo2LeggedApi.USER_URL:"+Walo2LeggedApi.USER_URL);
		//System.out.println(walo2LeggedAuthService.requetJsonBodyForWalo2LeggedApi(Walo2LeggedApi.USER_URL, "1215410ebc20ec9a6a00f8290c350d96"));
	}
}
