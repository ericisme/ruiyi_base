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
<title>邀请码管理</title>
<link type="text/css" rel="stylesheet" href="${ctx }/static/css/table.css"/>
<script type="text/javascript" src="${ctx }/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx }/static/js/base/base.js"></script>
<script type="text/javascript" src="${ctx }/static/js/provenceCityChoser.js"></script>
<script type="text/javascript" src="${ctx }/static/js/unis/backEnd/wwc/invitationCode/invitationCode.js"></script>
<script type="text/javascript" src="${ctx }/static/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx }/static/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
 var baseUrl = "${ctx}/backEnd/wwc/invitationCode";
$(document).ready(function(){
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
	<div class="weizhi" >你的位置是：发布会邀请管理 >>邀请码管理</div>
		<table class="biaoge_1" style="margin-top:10px;">
			<tr>
				<td class="biaoge_11">查询条件</td>
				<td class="biaoge_12">
					名字:
					<input name="_name" id="_name" type="text" maxlength="40" /> &nbsp;&nbsp;
					公司:
					<input name="_company" id="_company" type="text" maxlength="100" /> &nbsp;&nbsp;
					邀请码:
					<input name="_code" id="_code" type="text" maxlength="6" /> &nbsp;&nbsp;
					<br/>
					电话:
					<input name="_tel" id="_tel" type="text" maxlength="40" /> &nbsp;&nbsp;
					电邮:
					<input name="_email" id="_email" type="text" maxlength="80" /> &nbsp;&nbsp;
					备注:
					<input name="_remark" id="_remark" type="text" maxlength="300" /> 
					<br/>
					类型:
					<select name="_type" id="_type">
						<option value="">---请选择---</option>
						<option value="1">内部邀请</option>
						<option value="2">自助申请-全部</option>
						<option value="3">自助申请-申请中</option>
						<option value="4">自助申请-审核通过</option>
						<option value="5">自助申请-审核不通过</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="biaoge_11">操作</td>
				<td class="biaoge_12">
				<shiro:user>					
						<input type="button" class="button_1" name="" value="查询" onclick="query();"/> 					
					<shiro:hasPermission name="invitationCode:edit">
						<input type="button" class="button_1" name="" value="添加" onclick="edit(0);"/>
					</shiro:hasPermission>
					<shiro:hasPermission name="invitationCode:delete">
						<input type="button" class="button_1" name="" value="删除" onclick="delMore();"/>
					</shiro:hasPermission>
						<input type="button" class="button_1" name="" value="导出excel" onclick="exportXls();"/> 
				</shiro:user>
				</td>
			</tr>
		</table>
	</div>
	<div id="list" style="margin-top:20px;"></div>
	<div id="edit"></div>
</body>
</html>
