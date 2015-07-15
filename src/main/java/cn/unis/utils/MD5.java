package cn.unis.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	// 使用MD5加密
	public static String MD5Encode(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		char str[] = null;

		byte[] strTemp = s.getBytes();
		// 使用MD5创建MessageDigest对象
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");

			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				// 将没个数(int)b进行双字节加密
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];

			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
		return new String(str);

	}
}
