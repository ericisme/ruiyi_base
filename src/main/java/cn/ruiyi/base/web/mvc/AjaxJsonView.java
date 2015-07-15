package cn.ruiyi.base.web.mvc;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;
/**
 * 
 * Title: 		AJASX JSON 格式输出
 * Project: 	qtypt
 * Type:		cn.ruiyi.base.web.mvc.AjaxJsonView
 * Author:		郭世江
 * Create:	 	2012-11-9 上午10:45:16
 * Copyright: 	Copyright (c) 2012
 * Company:		qtone
 *
 */
public class AjaxJsonView implements View {

	/**
	 * 标准的JSON格式字符串
	 */
	private String json;

	public AjaxJsonView(String json) {
		this.json = json;
	}

	public String getContentType() {
		return "text/plain;charset=UTF-8";
	}

	public void render(Map map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType(this.getContentType());
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		PrintWriter out = response.getWriter();
		out.write(this.json);
		out.close();
	}

}
