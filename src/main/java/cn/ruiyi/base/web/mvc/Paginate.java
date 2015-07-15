package cn.ruiyi.base.web.mvc;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;

import cn.ruiyi.base.util.Encoding;
import cn.ruiyi.base.util.StringUtil;

/**
 * 
 * Title: 		生成JS进行控制的翻页转换
 * Project: 	qtypt
 * Type:		cn.ruiyi.base.web.mvc.Paginate
 * Author:		郭世江
 * Create:	 	2012-11-14 下午03:52:44
 * Copyright: 	Copyright (c) 2012
 * Company:		qtone
 *
 */
public class Paginate{
	
	private int curPage; // 当前页
	private long totals; // 总记录数
	private int perPage; // 每页大小	
	private long totalPages; // 总页数
	private StringBuffer roll; // 解析的结果

	public Paginate(long totals, int curPage, int perPage)
	{
		this.totals = totals;
		this.curPage = curPage;
		this.perPage = perPage;
		if (totals / this.perPage < 1) {
			this.totalPages = 1;
		} else {
			this.totalPages = this.totals / perPage;
			if ((this.totals % perPage) != 0) {
				this.totalPages += 1;
			}
		}
	}
	
	/**
	 * 设置URL地址.参数请自行加在地址后面.
	 * @param url 请求地址
	 * @param funcName 跳转的JS函数的名称
	 * @param listDiv 页面容器用于装载返回页面的ID
	 */
	public void setUrl(String url, String funcName,String listDiv)
	{
		parse(parseURL(url), parseSelURL(url), funcName,listDiv);
	}
	
	public void setUrl(HttpServletRequest request, String funcName,String listDiv)
	{
		setUrl(request.getRequestURL().toString() + "?" + request.getQueryString(), funcName,listDiv);
	}
	
	
	/**
	 * 解析并返回最终的URL地址,避免了page重复的问题.
	 * @param url
	 * @return
	 */
	private String parseURL(String url)
	{
		url = StringUtil.trim(url);
		int index = url.indexOf("?");
		if (index == -1 || index == url.length() - 1) return url;
		String query = StringUtils.trimToEmpty(url.substring(index + 1));
		String[] parts = StringUtil.trim(query.split("&"));
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < parts.length; i ++) {
			String[] tmp = StringUtil.trim(parts[i].split("="));
			if (tmp.length != 2) {
				buf.append(parts[i] + "&");
				continue;
			}
			if (tmp[0].equals("page")) continue;
			
			if("orders".equals(tmp[0]))
				buf.append(tmp[0] + "=" + tmp[1] + "&");
			else
				buf.append(tmp[0] + "=" + Encoding.escape(tmp[1]) + "&");
		}
		if (buf.length() > 0 && buf.charAt(buf.length() - 1) == '&') {
			buf.deleteCharAt(buf.length() - 1);
		}
		return url.substring(0, index + 1) + buf.toString();
	}
	
	/**
	 * 解析下拉框地址(下拉框为乱码问题)
	 * @param url
	 * @return
	 */
	private String parseSelURL(String url)
	{
		url = StringUtil.trim(url);
		int index = url.indexOf("?");
		if (index == -1 || index == url.length() - 1) return url;
		String query = StringUtils.trimToEmpty(url.substring(index + 1));
		String[] parts = StringUtil.trim(query.split("&"));
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < parts.length; i ++) {
			String[] tmp = StringUtil.trim(parts[i].split("="));
			if (tmp.length != 2) {
				buf.append(parts[i] + "&");
				continue;
			}
			if (tmp[0].equals("page")) continue;
			
			buf.append(tmp[0] + "=" + tmp[1] + "&");
		}
		if (buf.length() > 0 && buf.charAt(buf.length() - 1) == '&') {
			buf.deleteCharAt(buf.length() - 1);
		}
		return url.substring(0, index + 1) + buf.toString();
	}
	
	/**
	 * 获取翻页控制代码.
	 * @return
	 */
	public String getRoll()
	{
		return this.roll.toString();
	}
	
	/**
	 * 覆盖toString方法.
	 */
	@Override
	public String toString()
	{
		return getRoll();
	}

	/**
	 * 解析翻页控制
	 *
	 */
	private void parse(String url, String selurl, String funcName,String listDiv)
	{
		funcName = funcName == null ? "base.roll" : funcName;
		String paras = url + (url.indexOf('?') == -1 ? "?" : "&") + "page=";
		String selparas = selurl + (url.indexOf('?') == -1 ? "?" : "&") + "page=";
		
		boolean pre = curPage > 1; // 是否可以向前翻页
		boolean end = curPage < totalPages; // 是否可以向后翻页
		roll = new StringBuffer("[ ");
		if (pre) {
			roll.append("<a href=\"javascript:" + funcName);
			roll.append("('"+listDiv+"','" + paras + "',1);\">");
		}
		roll.append("首  页");
		if (pre) roll.append("</a>");
		roll.append(" | ");
		if (pre) {
			roll.append("<a href=\"javascript:" + funcName);
			roll.append("('"+listDiv+"','" + paras + "'," + (curPage - 1) + ");\">");
		}
		roll.append("上一页");
		if (pre) roll.append("</a>");
		roll.append(" | ");
		if (end) {
			roll.append("<a href=\"javascript:" + funcName);
			roll.append("('"+listDiv+"','" + paras + "'," + (curPage + 1) + ");\">");
		}
		roll.append("下一页");
		if (end) roll.append("</a>");
		roll.append(" | ");
		if (end) {
			roll.append("<a href=\"javascript:" + funcName);
			roll.append("('"+listDiv+"','" + paras + "'," + totalPages + ");\">");
		}
		roll.append("末  页");
		if (end) roll.append("</a>");
		roll.append(" ]");
		roll.append("总数:" + this.totals + "&nbsp;");
		roll.append("页数:");
		roll.append("<select onchange=\"" + funcName);
		roll.append("('"+listDiv+"','" + selparas + "',this.value)\">");
		for (int i = 0; i < totalPages; i ++) {
			roll.append("<option value='" + (i + 1) + "'");
			if (curPage == i + 1) {
				roll.append(" selected");
			}
			roll.append(">" + (i + 1) + "/" + totalPages);
			roll.append("</option>");
		}
		roll.append("</select>");
	}
	
	/**
	 * 获取ajax形式的分页代码.使用自定义的分页方法
	 * @param request
	 * @return
	 */
	public static String getPage(HttpServletRequest request, int curPage,Page page, String funcName,String listDiv)
	{
		Paginate html = new Paginate(page.getTotalElements(), curPage, page.getSize());
		html.setUrl(request, funcName,listDiv);
		return html.getRoll();
	}

	
	/**
	 * 获取ajax形式的分页代码.使用自定义的分页方法
	 * @param request
	 * @return
	 */
	public static String getPage(HttpServletRequest request, int curPage,long totalElements, int  sizePerPage, String funcName,String listDiv)
	{
		Paginate html = new Paginate(totalElements, curPage, sizePerPage);
		html.setUrl(request, funcName,listDiv);
		return html.getRoll();
	}
}
