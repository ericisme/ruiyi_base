<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
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
<script type="text/javascript" src="${ctx }/static/js/jQuery.md5.js"></script>
<script type="text/javascript" src="${ctx }/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx }/static/js/base/base.js"></script>
<script type="text/javascript" src="${ctx }/static/js/syspurview/account/account.js"></script>
<script type="text/javascript" src="${ctx }/static/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx }/static/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
var welcomeUrl = "${ctx }/backEnd/welcome";

</script>
</head>
<body>
	<div id="head">
	<div class="weizhi" >你的位置是：帐号管理 >> 密码修改</div>
	<form name="mainForm" id="mainForm" >
	
		<table class="biaoge_3" style="margin-top: 10px;">
		<tr>
			<td class="biaoge_31" colspan="4">注：带"*"为必填项</td>
		</tr>
		<tr>
			<td class="biaoge_32" style="width: 15%" ><span style="color: #F00">*</span>原密码：</td>
			<td class="biaoge_33" style="width: 35%" colspan="3"><input type="password" class="text_3" name="oldPassword" id="oldPassword"/></td>
		</tr>
		
		<tr>
			<td class="biaoge_32" style="width: 15%"><span style="color: #F00">*</span>新密码：</td>
			<td class="biaoge_33" style="width: 35%"  colspan="3"><input type="password" class="text_3" name="newPassword" id="newPassword"/></td>
		</tr>
		
		<tr>
			<td class="biaoge_32" style="width: 15%"><span style="color: #F00">*</span>再输入一遍</td>
			<td class="biaoge_33" style="width: 35%"  colspan="3"> <input type="password" class="text_3" name="newPasswordAgain" id="newPasswordAgain"  /> </td>
		</tr>

		<tr>
			<th colspan="4" class="t_title t_c">
				<input name="btn01" id="sys_update_btn" type="button" onclick="modifyPassword()" class="button_1" value="提 交" />
				&nbsp;&nbsp;&nbsp;&nbsp;
				 <input name="btn01" type="button" class="button_1" onclick="refreshAfterModifyPassword()" value="返 回" />
			</th>
		</tr>
	</table>
	
	</form>
	
	</div>
</body>
</html>
