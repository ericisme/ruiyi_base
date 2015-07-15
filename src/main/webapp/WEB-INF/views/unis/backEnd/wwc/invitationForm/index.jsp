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
<title>用户表单管理</title>
<link type="text/css" rel="stylesheet" href="${ctx }/static/css/table.css"/>
<script type="text/javascript" src="${ctx }/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx }/static/js/base/base.js"></script>
<script type="text/javascript" src="${ctx }/static/js/unis/backEnd/wwc/invitationForm/invitationForm.js"></script>
<script type="text/javascript" src="${ctx }/static/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx }/static/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
var baseUrl = "${ctx}/backEnd/wwc/invitationForm";
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
	<div class="weizhi" >你的位置是：发布会邀请管理 >>用户表单管理</div>
		<table class="biaoge_1" style="margin-top:10px;">
			<tr>
				<td class="biaoge_11">查询条件</td>
				<td class="biaoge_12">			
					邀请码:
					<input name="_code" id="_code" type="text" maxlength="6" /> &nbsp;&nbsp;	
					公司名称:
					<input name="_companyName" id="_companyName" type="text" maxlength="100" /> &nbsp;&nbsp;
					<br/>
					填写版本:
					<select name="_formType" id="_formType">
						<option value="">---请选择---</option>
						<option value="11">英文</option>
						<option value="21">中文</option>
					</select>					
					是否已确认:
					<select name="_isSure" id="_isSure">
						<option value="">---请选择---</option>
						<option value="1">已确认</option>
						<option value="0">未确认</option>
					</select>
					&nbsp;
					国家/地区:
					<select id="_districtCode" name="_districtCode">
						<option selected="selected" value="">-选择国家/地区-</option>
						${districtCodeOptions}
					</select>
					<br/>
					住宿：
					<select name="_hotel" id="_hotel">
						<option value="">---请选择---</option>
						<option value="-1">不入住</option>
						<option value="11">喜来登酒店(英文版)</option>
						<option value="21">金钻酒店 (中文版)</option>
						<option value="32">金沙酒店 (中文版)</option>
					</select>
					特殊餐饮:
					<select name="_specialDiet" id="_specialDiet">
						<option value="">---请选择---</option>
						<option value="1">有</option>
						<option value="0">没有</option>
					</select>
					<br/>
					岐江夜游:
					<select name="_nightTourOfQiRiver" id="_nightTourOfQiRiver">
						<option value="">---请选择---</option>
						<option value="1">参加</option>
						<option value="0">不参加</option>
					</select>
					故居参观:
					<select name="_visitFormerResidenceOfSunYatSen" id="_visitFormerResidenceOfSunYatSen">
						<option value="">---请选择---</option>
						<option value="1">参加</option>
						<option value="0">不参加</option>
					</select>	
					旅游购物:	
					<select name="_shopping" id="_shopping">
						<option value="">---请选择---</option>
						<option value="1">参加</option>
						<option value="0">不参加</option>
					</select>	
					娱乐活动:		
					<select name="_entertainment" id="_entertainment">
						<option value="">---请选择---</option>
						<option value="1">参加</option>
						<option value="0">不参加</option>
					</select>					
				</td>
			</tr>
			<tr>
				<td class="biaoge_11">操作</td>
				<td class="biaoge_12">
				<shiro:user>					
						<input type="button" class="button_1" name="" value="查询" onclick="query();"/> 
						<input type="button" class="button_1" name="" value="导出excel" onclick="exportXls();"/> 
											
					<!--<shiro:hasPermission name="invitationCode:edit">
						<input type="button" class="button_1" name="" value="添加" onclick="edit(0);"/>
					</shiro:hasPermission>
					<shiro:hasPermission name="invitationCode:delete">
						<input type="button" class="button_1" name="" value="删除" onclick="delMore();"/>
					</shiro:hasPermission>
						<input type="button" class="button_1" name="" value="导出excel" onclick="exportXls();"/> 
					-->
				</shiro:user>
				</td>
			</tr>
		</table>
	</div>
	<div id="list" style="margin-top:20px;"></div>
	<div id="edit"></div>
</body>
</html>
