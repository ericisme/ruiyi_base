package cn.unis.utils;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class LocaleUtils {
	public static String getLocale(HttpServletRequest request){
		Locale locale = request.getLocale();
		String country = locale.getCountry();
		Cookie[] cookiesArray = request.getCookies();
		int length = cookiesArray == null ? 0 : cookiesArray.length;
		for(int i=0;i<length;i++){
			Cookie cookie = cookiesArray[i];
			if(cookie.getName().equals("language")){
				return cookie.getValue().equals("0") ? "EN" : "CN";
			}
		}

		return country;
	}
}
