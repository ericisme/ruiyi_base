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
<title>大会新闻管理</title>
<link type="text/css" rel="stylesheet" href="${ctx }/static/css/table.css"/>
<link type="text/css" href="${ctx }/static/fineuploader/fineuploader-3.3.1.css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="/static/wwc/source/jquery.fancybox.css" media="screen" />
<script type="text/javascript" src="${ctx }/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx }/static/js/base/base.js"></script>
<script type="text/javascript" src="${ctx }/static/js/unis/backEnd/wwc/invitationNews/invitationNews.js"></script>
<script type="text/javascript" src="${ctx }/static/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx }/static/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="/static/wwc/source/jquery.fancybox.pack.js"></script>
        
<script type="text/javascript">

var baseUrl = "${ctx}/backEnd/wwc/invitationNews";
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
	<div class="weizhi" >你的位置是：发布会邀请管理 >> 大会新闻管理</div>
		<table class="biaoge_1" style="margin-top:10px;">
			<tr>
				<td class="biaoge_11">查询条件</td>
				<td class="biaoge_12">
					中英文版本：
					<select name="_formType" id="_formType">
						<option value="" >---请选择---</option>						
						<option value="21" >中文</option>
						<option value="11" >英文</option>
					</select>
					<br/>
					新闻标题：
					<input name="_title" id="_title" type="text" maxlength="50" /> 
					&nbsp;&nbsp;
					新闻作者:
					<input name="_author" id="_author" type="text" maxlength="50" /> 
					&nbsp;&nbsp;
					状态：
					<select name="_status" id="_status">
						<option value="" >---请选择---</option>
						<option value="1" >启用</option>
						<option value="0" >停用</option>
					</select>
					<br/>
					开始日期：
					<input name="_addTimeStrStart" id="_addTimeStrStart" type="text" 
						maxlength="10" readonly title="点击选择日期" 
						onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"/> 
					&nbsp;&nbsp;
					结束日期:
					<input name="_addTimeStrEnd"   id="_addTimeStrEnd"   type="text" 
						maxlength="10" readonly title="点击选择日期" 
						onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"/> 
					&nbsp;&nbsp;
					标签：
					<select name="_tag" id="_tag">
						<option value="">--全部--</option>
						${_tage_select_html }
					</select>
				</td>
			</tr>
			<tr>
				<td class="biaoge_11">操作</td>
				<td class="biaoge_12">
				<shiro:user>
					
						<input type="button" class="button_1" name="" value="查询" onclick="query();"/> 
					
					<shiro:hasPermission name="invitationNews:edit">
						<input type="button" class="button_1" name="" value="添加" onclick="edit(0);"/>
					</shiro:hasPermission>
					<shiro:hasPermission name="invitationNews:delete">
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
