package cn.ruiyi.base.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * Title: 		JAVA对Cookie的操作
 * Project: 	qtypt
 * Type:		cn.ruiyi.base.util.HttpCookieUtil
 * Author:		郭世江
 * Create:	 	2012-11-8 下午03:39:34
 * Copyright: 	Copyright (c) 2012
 * Company:		qtone
 * 
 */
public class HttpCookieUtil {
	
	/** 
	 * 设置cookie 
	 * @param response 
	 * @param name  cookie名字 
	 * @param value cookie值 
	 * @param maxAge cookie生命周期  以秒为单位 
	 */
	public static void addCookie(HttpServletResponse response,String name,String value,int maxAge){ 
	    Cookie cookie = new Cookie(name,value); 
	    cookie.setPath("/"); 
		if(maxAge > 0)  cookie.setMaxAge(maxAge); 
	    response.addCookie(cookie); 
	} 
	
	/** 
	 * 根据名字获取cookie 
	 * @param request 
	 * @param name cookie名字 
	 * @return 
	 */
	public static Cookie getCookieByName(HttpServletRequest request,String name){ 
	    Map<String,Cookie> cookieMap = readCookieMap(request); 
	    if(cookieMap.containsKey(name)){ 
	        Cookie cookie = (Cookie)cookieMap.get(name); 
	        return cookie; 
	    }else{ 
	        return null; 
	    }    
	} 
	  
	/** 
	 * 将cookie封装到Map里面 
	 * @param request 
	 * @return 
	 */
	private static Map<String,Cookie> readCookieMap(HttpServletRequest request){   
	    Map<String,Cookie> cookieMap = new HashMap<String,Cookie>(); 
	    Cookie[] cookies = request.getCookies(); 
	    if(null!=cookies){ 
	        for(Cookie cookie : cookies){ 
	            cookieMap.put(cookie.getName(), cookie); 
	        } 
	    } 
	    return cookieMap; 
	} 
}
