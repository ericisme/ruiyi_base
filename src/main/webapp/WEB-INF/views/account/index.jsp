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
<title>用户管理</title>
<link type="text/css" rel="stylesheet" href="${ctx }/static/css/table.css"/>
<script type="text/javascript" src="${ctx }/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx }/static/js/base/base.js"></script>
<script type="text/javascript" src="${ctx }/static/js/syspurview/account/account.js"></script>
<script type="text/javascript" src="${ctx }/static/js/provenceCityChoser.js"></script>
<script type="text/javascript" src="${ctx }/static/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
<!--
$(document).ready(
		function(){
			userQuery();
		}
);
//回车事件搜索
document.onkeydown = function(e){ 
    var currKey=0,e=e||event; 
    if(e.keyCode==13) 
    {
    	userQuery();
    } 
};
//-->
</script>
</head>
<body>
	<div id="head">
	<div class="weizhi" >你的位置是：系统管理 >> 用户管理</div>
		<table class="biaoge_1" style="margin-top:10px;">
			<tr>
				<td class="biaoge_11">查询条件</td>
				<td class="biaoge_12">
						用户姓名：
					<input name="qryName" id="qryName" type="text" maxlength="10" /> &nbsp;&nbsp;
						角色：
					<select name="_roleId" id="_roleId">
					<option value="0">---请选择角色---</option>
					<c:forEach items="${roles}" var="role">
						<option value="${role.id}">${role.name}</option>
					</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td class="biaoge_11">操作</td>
				<td class="biaoge_12">
				<shiro:user>
				<shiro:hasPermission name="user:query">
					<input type="button" class="button_1" name="" value="查询" onclick="userQuery();"/> 
					</shiro:hasPermission>
					<input type="button" class="button_1" name="" value="添加" onclick="edit(0);"/>
					<input type="button" class="button_1" name="" value="删除" onclick="delMore();"/>
				</shiro:user>
				</td>
			</tr>
		</table>
	</div>
	<div id="list" style="margin-top:20px;"></div>
	<div id="edit"></div>
</body>
</html>
