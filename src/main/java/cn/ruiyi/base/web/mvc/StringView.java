package cn.ruiyi.base.web.mvc;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

/**
 * 
 * Title: 		用于向页面直接返回字符串内容
 * Project: 	qtypt
 * Type:		cn.ruiyi.base.web.mvc.StringView
 * Author:		郭世江
 * Create:	 	2012-11-9 上午10:51:24
 * Copyright: 	Copyright (c) 2012
 * Company:		qtone
 *
 */
public class StringView implements View {
	
	private String content;
	
	public StringView() {
	}

	public StringView(String content) {
		this.content = content;
	}

	public String getContentType() {
		return "text/plain;charset=UTF-8";
	}

	@SuppressWarnings("unchecked")
	public void render(Map params, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		resp.setContentType(getContentType());
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		try {
			out.write(getContent());
		} finally {
			out.close();
			out = null;
		}
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
