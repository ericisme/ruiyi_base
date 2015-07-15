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
<title>与会者查询</title>
<link type="text/css" rel="stylesheet" href="${ctx }/static/css/table.css"/>
<script type="text/javascript" src="${ctx }/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx }/static/js/base/base.js"></script>
<script type="text/javascript" src="${ctx }/static/js/unis/backEnd/wwc/invitationFormApplicant/invitationFormApplicant.js"></script>
<script type="text/javascript" src="${ctx }/static/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx }/static/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
var baseUrl = "${ctx}/backEnd/wwc/invitationFormApplicant";
$(document).ready(function(){
					searchQuery();
				 }
);
//回车事件搜索
document.onkeydown = function(e){ 
    var currKey=0,e=e||event; 
    if(e.keyCode==13){
    	searchQuery();
    } 
};
</script>
</head>
<body>
	<div id="head">
	<div class="weizhi" >你的位置是：发布会邀请管理 >>与会者查询</div>
		<table class="biaoge_1" style="margin-top:10px;">
			<tr>
				<td class="biaoge_11">查询条件</td>
				<td class="biaoge_12">			
					签到与否:
					<select id="_checkOrNot" name="_checkOrNot">
						<option value="">---请选择---</option>
						<option value="1">是</option>
						<option value="0">否</option>
					</select>	
					国家/地区:
					<select id="_districtCode" name="_districtCode">
						<option selected="selected" value="">-选择国家/地区-</option>
						${districtCodeOptions}
					</select>	
					<br/>
					公司名:
					<input name="_companyName" id="_companyName" type="text" maxlength="100" /> &nbsp;&nbsp;
					中文姓名:
					<input name="_name" id="_name" type="text" maxlength="50" /> &nbsp;&nbsp;
					<br/>
					英文firstName:
					<input name="_firstName" id="_firstName" type="text" maxlength="50" /> &nbsp;&nbsp;
					英文lastName:
					<input name="_lastName" id="_lastName" type="text" maxlength="50" /> &nbsp;&nbsp;						
					<br/>
					手机号:
					<input name="_mobileNumber" id="_mobileNumber" type="text" maxlength="30" /> &nbsp;&nbsp;	
					email:
					<input name="_email" id="_email" type="text" maxlength="100" /> &nbsp;&nbsp;
					<br/>
				</td>
			</tr>
			<tr>
				<td class="biaoge_11">操作</td>
				<td class="biaoge_12">
				<shiro:user>					
						<input type="button" class="button_1" name="" value="查询" onclick="searchQuery();"/> 
						<input type="button" class="button_1" name="" value="关闭" onclick="window.close();"/> 
						<!-- <input type="button" class="button_1" name="" value="导出excel" onclick="exportXls();"/>  -->
											
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
