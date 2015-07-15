package cn.ruiyi.base.web.mvc;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.View;

import cn.ruiyi.base.util.StringUtil;

/**
 * 
 * Title: 		返回AJAX操作后的JSON提示信息
 * Project: 	qtypt
 * Type:		cn.ruiyi.base.web.mvc.JsonView
 * Author:		郭世江
 * Create:	 	2012-11-9 上午10:49:31
 * Copyright: 	Copyright (c) 2012
 * Company:		qtone
 *
 */
public class JsonView implements View {

	private String msg;

	private boolean success = true;

	private Map<String, Object> map = new HashMap<String, Object>();

	public JsonView(String msg) {
		this.msg = StringUtils.trimToEmpty(msg);
	}

	public JsonView(boolean success, String msg) {
		this.msg = StringUtils.trimToEmpty(msg);
		this.success = success;
	}

	public String getContentType() {
		return "text/plain;charset=UTF-8";
	}

	/**
	 * 设置返回到客户端的JSON对象的属性，success和message属性不允许设置
	 */
	public void setProperty(String key, Object value) {
		if ("sucess".equals(key) || "message".equals(key))
			return;
		map.put(key, value);
	}

	/**
	 * 返回JSON数据供前台JS进行解析.
	 */
	@SuppressWarnings("unchecked")
	public void render(Map arg0, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		PrintWriter out = resp.getWriter();
		out.write(this.getJSONString());
		out.close();
		out = null;
	}

	/**
	 * 获取JSON提示信息 ["success":true,"message":"操作成功"]
	 */
	private String getJSONString() {
		JSONObject obj = JSONObject.fromMap(this.getMap());
		obj.put("success", success);
		obj.put("message", msg);
		return obj.toString();
	}
	
	public boolean getSuccess() {
		return success;
	}
	
	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
}
