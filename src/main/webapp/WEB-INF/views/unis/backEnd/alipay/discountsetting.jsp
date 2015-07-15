<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>主页管理</title>
<link type="text/css" rel="stylesheet" href="${ctx }/static/css/table.css"/>
<link type="text/css" href="${ctx }/static/fineuploader/fineuploader-3.3.1.css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx }/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx }/static/js/base/base.js"></script>
<script type="text/javascript" src="${ctx }/static/js/unis/backEnd/alipaydiscount/alipaydiscount.js"></script>
<script type="text/javascript" src="${ctx }/static/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx }/static/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="weizhi">
	你的位置是：充值管理 >> 充值折扣管理 >> 编辑折扣
</div>
<form name="mainForm" id="mainForm" >
		<input type="hidden" id="id" name="id" value="${alipayDiscount.id }"/>
		<table class="biaoge_3" style="margin-top: 10px;">
		<tr>
			<td class="biaoge_31" colspan="4">注：带"*"为必填项</td>
		</tr>
		<tr>
			<td class="biaoge_32" style="width: 15%" ><span style="color: #F00">*</span>折扣：</td>
			<td class="biaoge_33" style="width: 35%" colspan="3"><input type="text" class="text_3" name="discount" id="discount" value="${alipayDiscount.discount }"/></td>
		</tr>

		<tr>
			<td class="biaoge_32" style="width: 15%"><span style="color: #F00">*</span>开始日期：</td>
			<td class="biaoge_33" style="width: 35%"><input type="text" class="Wdate text_4" name="begin" id="d0005"
					value="<fmt:formatDate value="${alipayDiscount.effectiveBegin }" pattern="yyyy-MM-dd HH:mm:ss" />"
					onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,readOnly:true,maxDate:'#F{$dp.$D(\'d0006\',{d:-0});}'})" /></td>

			<td class="biaoge_32" style="width: 15%"><span style="color: #F00">*</span>结束日期：</td>
			<td class="biaoge_33" style="width: 35%"> <input type="text" class="Wdate text_4" name="end" id="d0006"
					value="<fmt:formatDate value="${alipayDiscount.effectiveEnd }" pattern="yyyy-MM-dd HH:mm:ss" />"
					onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,readOnly:true,minDate:'#F{$dp.$D(\'d0005\',{d:0});}'})" /> </td>
		</tr>


		<tr>
			<th colspan="4" class="t_title t_c">
				<shiro:hasPermission name="alipaydiscount:edit">
					<input name="btn01" id="sys_update_btn" type="button" onclick="save()" class="button_1" value="提 交" />&nbsp;&nbsp;&nbsp;&nbsp;
				</shiro:hasPermission>

				<input name="btn01" type="button" class="button_1" onclick="window.location.href = '${ctx }/backEnd/welcome'" value="返 回" />
			</th>
		</tr>
	</table>
	</form>
</body>
</html>