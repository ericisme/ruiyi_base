package cn.unis.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import cn.ruiyi.base.util.Encrypt;
import cn.ruiyi.base.util.PathUtil;

/**
 * 无iv的AES加密
 *
 * @author Administrator
 *
 */
public class Security {
	// 加密的key
	public static String ENCRYPTION_KEY = Encrypt.MD5(PathUtil
			.getConfigResource("ENCRYPTION_KEY"));

	public static String encrypt(String input, String key) {
		byte[] crypted = null;
		try {
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			crypted = cipher.doFinal(input.getBytes());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return new String(Base64.encodeBase64(crypted));
	}

	public static String decrypt(String input, String key) {
		byte[] output = null;
		try {
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skey);
			output = cipher.doFinal(Base64.decodeBase64(input));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return new String(output);
	}

	public static void main(String[] args) {
		String key = Encrypt.MD5("57902c191da183c1893accafea1c48");
		String content_after_encrypt = "PiMDzInacs0JMTFEcDponynoQqBGzSuChhgEhxToaJrQDLFiLRmZLGNTg6SRScevqrqdEv532EpHCaC6v15OAJRPGD6dLXwqmRT+r6lJnScvObfmqB0Q736PvHv9Pxbgix+1zhtQ4BbyMiYxD9gSviEXh8v54K3pycyc6EFGm9ZTAhuIe353gi+oEilqW90M";
		// System.out.println(Security.decrypt(content_after_encrypt, key));

		String content1 = "{\"user_key\":\"0a26071099692979d609f47c442549a1\",\"oauth_token\":\"7fd93c302b\",\"oauth_token_secret\":\"249553c462c50a4696a4f25af5c537bb\"}";
		String content2 = "{\"outTradeNo\":\"aac99863-89ba-4c5b-b6a1-2f9dbaa6c3f1\",\"date_time\":\"2014-06-18 16:20:56\",\"payMoney\":0.01}";
		String content3 = "{\"outTradeNo\":\"aac99863-89ba-4c5b-b6a1-2f9dbaa6c3f1\",\"date_time\":\"2014-06-18 16:20:56\",\"payMoney\":1999.99}";

		System.out.println("content1:" + content1);
		System.out.println("content1 after encrypt:"
				+ Security.encrypt(content1, key));
		System.out.println("content2:" + content2);
		System.out.println("content2 after encrypt:"
				+ Security.encrypt(content2, key));
		System.out.println("content3:" + content3);
		System.out.println("content3 after encrypt:"
				+ Security.encrypt(content3, key));

		// String key = "1234567891234567";
		// String data = "example";

		// System.out.println(Security.encrypt(data, key));

		// System.out.println(Security.decrypt(Security.encrypt(data, key),
		// key));
		//

	}
}