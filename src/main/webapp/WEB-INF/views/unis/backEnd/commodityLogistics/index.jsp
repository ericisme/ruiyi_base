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
<title>物流商管理</title>
<link type="text/css" rel="stylesheet" href="${ctx }/static/css/table.css"/>
<script type="text/javascript" src="${ctx }/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx }/static/js/base/base.js"></script>
<script type="text/javascript" src="${ctx }/static/js/unis/backEnd/commodityLogistics/commodityLogistics.js"></script>
<script type="text/javascript" src="${ctx }/static/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx }/static/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
 var baseUrl = "${ctx}/backEnd/commodityLogistics";
$(document).ready(
		function(){
			query();
		}
);
//回车事件搜索
document.onkeydown = function(e){ 
    var currKey=0,e=e||event; 
    if(e.keyCode==13) 
    {
    	query();
    } 
}; 
</script>
</head>
<body>
	<div id="head">
	<div class="weizhi" >你的位置是：商城管理 >>物流商管理</div>
		<table class="biaoge_1" style="margin-top:10px;">
			<tr>
				<td class="biaoge_11">查询条件</td>
				<td class="biaoge_12">
					物流查询网址:
					<input name="_path" id="_path" type="text" maxlength="50" /> 
					&nbsp;&nbsp;
					物流商名称：
					<input name="_name" id="_name" type="text" maxlength="50" /> 				
					<br/>
					物流联系电话:
					<input name="_tel" id="_tel" type="text" maxlength="50" /> 
					&nbsp;&nbsp;
					物流商状态：
					<select name="_status" id="_status">
						<option value="" >---请选择---</option>
						<option value="1" >启用</option>
						<option value="0" >停用</option>
					</select>
					<br/>
				</td>
			</tr>
			<tr>
				<td class="biaoge_11">操作</td>
				<td class="biaoge_12">
				<shiro:user>					
						<input type="button" class="button_1" name="" value="查询" onclick="query();"/> 					
					<shiro:hasPermission name="commodityLogistics:edit">
						<input type="button" class="button_1" name="" value="添加" onclick="edit(0);"/>
					</shiro:hasPermission>
					<shiro:hasPermission name="commodityLogistics:delete">
						<input type="button" class="button_1" name="" value="删除" onclick="delMore();"/>
					</shiro:hasPermission>
				</shiro:user>
				</td>
			</tr>
		</table>
	</div>
	<div id="list" style="margin-top:20px;"></div>
	<div id="edit"></div>
</body>
</html>
