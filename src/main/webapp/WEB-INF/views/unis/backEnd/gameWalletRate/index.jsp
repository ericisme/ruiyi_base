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
<title>游戏钱包兑换率管理</title>
<link type="text/css" rel="stylesheet" href="${ctx }/static/css/table.css"/>
<script type="text/javascript" src="${ctx }/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx }/static/js/base/base.js"></script>
<script type="text/javascript" src="${ctx }/static/js/unis/backEnd/gameWalletRate/gameWalletRate.js"></script>
<script type="text/javascript" src="${ctx }/static/js/provenceCityChoser.js"></script>
<script type="text/javascript" src="${ctx }/static/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx }/static/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
<!--
 var baseUrl = "${ctx}/backEnd/gameWalletRate";
 /*$(document).ready(
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
}; */
//-->
</script>
</head>
<body>
	<div id="head">
	<div class="weizhi" >你的位置是：充值管理 >> 游戏钱包兑换率管理</div>
		<table class="biaoge_1" style="margin-top:10px;">
			<tr>
				<td class="biaoge_11">查询条件</td>
				<td class="biaoge_12">
				游戏：
				<select id="consumer_key" name="consumer_key">
					<option value="">---请选择---</option>
					<c:forEach items="${chargeableConsumerList}" var="consumer" >
						<option value="${consumer.key }">${consumer.name }</option>
					</c:forEach>					
				</select>
				平台类型：
				<select id="type" name="type">
					<option value="">---请选择---</option>
					<option value="mobile">mobile</option>
					<option value="tablet">tablet</option>
					<option value="arcade">arcade</option>
					<option value="server">server</option>
					<option value="web">web</option>				
				</select>
				<br/>
				游场省/市:
				<Select size=1 id="provinceSel">
 					<OPTION selected></OPTION>
 				</Select>
 				<Select size=1 id="citySel" onchange="">
 					<OPTION selected></OPTION>
 				</Select>
				<SCRIPT language=javascript>
					InitCitySelect(document.getElementById('provinceSel'), document.getElementById('citySel'),"");
				</SCRIPT>
				</td>
			</tr>
			<tr>
				<td class="biaoge_11">操作</td>
				<td class="biaoge_12">
				<shiro:user>					
						<input type="button" class="button_1" name="" value="查询" onclick="query();"/> 					
					<shiro:hasPermission name="gameWalletRate:edit">
						<input type="button" class="button_1" name="" value="编辑" onclick="edit(1);" id="edit_btn"/>
						<input type="button" class="button_1" name="" value="返回" onclick="edit(0);" id="show_btn" style="display:none"/>
					</shiro:hasPermission>
				</shiro:user>
				</td>
			</tr>
			<tr>
				<td class="biaoge_11">说明：</td>
				<td class="biaoge_12">
						 1.兑换比值;一世宇币换多少游戏币;必须为大于或等于1的整数，如，如果1世宇币兑换60游戏币，则填写60; 如果不创建此记录，兑换比值默认为1
						 <br/>
						 2.编辑完成之后要按回车键方能保存。
				</td>
			</tr>
		</table>
	</div>
	<div id="list" style="margin-top:20px;"></div>
	<div id="edit"></div>
</body>
</html>
