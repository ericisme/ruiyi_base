package cn.unis.utils;



import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import cn.ruiyi.base.util.Encrypt;
import cn.ruiyi.base.util.PathUtil;


/**
 * 替换jre/lib/security/local_policy.jar US_export_policy.jar(增强包)
 * AES CBC加密方式
 * @author fanzz
 *
 */
public class AESUtils {
	// 加密的key
	private static String ENCRYPTION_KEY = PathUtil.getConfigResource("ENCRYPTION_KEY");
	// 初始向量iv
	private static String ENCRYPTION_IV =  PathUtil.getConfigResource("ENCRYPTION_IV");

	public static String encrypt(String src) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, makeKeyWithMD5(ENCRYPTION_KEY), makeIv(ENCRYPTION_IV));
			return Base64Coder.encode(cipher.doFinal(src.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static String encrypt(String src,String key,String iv){
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, makeKeyWithMD5(key), makeIv(iv));
			return Base64Coder.encode(cipher.doFinal(src.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String decrypt(String src) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, makeKeyWithMD5(ENCRYPTION_KEY), makeIv(ENCRYPTION_IV));
			return  new String(cipher.doFinal(Base64Coder.decode(src)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String decrypt(String src,String key,String iv) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, makeKeyWithMD5(key), makeIv(iv));
			return new String(cipher.doFinal(Base64Coder.decode(src)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static AlgorithmParameterSpec makeIv(String iv) {
		try {
			return new IvParameterSpec(iv.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Key makeKeyWithMD5(String key)  {
		try {
			return new SecretKeySpec(Encrypt.MD5(key).getBytes("UTF-8"), "AES");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
//		String src = "Hello,CryptWorld";
//		String encrypted = AESUtils.encrypt(src);
//		String decrypted = AESUtils.decrypt(encrypted);
//		System.out.println("src: " + src);
//		System.out.println("encrypted: " + encrypted);
//		System.out.println("decrypted: " + decrypted);
//		System.out.println("111:" + AESUtils.decrypt("U/gwwrbd2yhZioZziuuE1vf"
//				+ "PXGK/rwCzmmGoVIXMuwIdmQTO/wOcUuh463xVg/lZuzGxIcdFZpuYUdMhi9Sxne"
//				+ "oK6wIU/mt9DUxpb3S8iDO6erp7xLuzAxc7nPcAF6ShPXWYq3a3SBuVHUv1HJsL7"
//				+ "j/v/YikR0DOQpaweQw8iew=","57902c191da183c1893accafea1c48","Shiyu@1013243227"));
		System.out.println("AES KEY: " +  Encrypt.MD5(ENCRYPTION_KEY));
		System.out.println("AES IV: " + ENCRYPTION_IV);
		
		String str = "{\"user_key\":\"0a26071099692979d609f47c442549a1\",\"oauth_token\":\"7fd93c302b\",\"oauth_token_secret\":\"249553c462c50a4696a4f25af5c537\"}";
		//String str = "{\"outTradeNo\":\"aac99863-89ba-4c5b-b6a1-2f9dbaa6c3f1\",\"date_time\":\"2014-06-18 16:20:56\",\"payMoney\":0.01}";
		
		System.out.println("要加密的字符串: " + str);
		String encrypted2 = AESUtils.encrypt(str);
		System.out.println("加密的字符串: " + encrypted2);
		System.out.println("解密后:"+AESUtils.decrypt(encrypted2));
		
		String abc = "@53f830c2b6dddb28598a86738aeb84d6f7cf5c62bfaf00b39a61a85485ccbb021d9904ceff039c52e878eb7c5583f959a7be68726fa79c1c238beb88ba57de33db35eca686985b954e21fb1e324accd83eb0869bd860733a461e34cb4dda9258f5f420b10de3133fcb03b0990ec50d8e3d254432e5ecb5591ce0fe13efc17ef8ab19222100daed7ab1b1237d85323f55";
		System.out.println("abc decrypt:"+AESUtils.decrypt(abc));
	}
}
