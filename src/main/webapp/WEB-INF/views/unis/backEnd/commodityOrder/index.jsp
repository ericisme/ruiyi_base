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
<title>订单管理</title>
<link type="text/css" rel="stylesheet" href="${ctx }/static/css/table.css"/>
<script type="text/javascript" src="${ctx }/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx }/static/js/base/base.js"></script>
<script type="text/javascript" src="${ctx }/static/js/unis/backEnd/commodityOrder/commodityOrder.js"></script>
<script type="text/javascript" src="${ctx }/static/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx }/static/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
var baseUrl = "${ctx}/backEnd/commodityOrder";
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
	<div class="weizhi" >你的位置是：商城管理 >>订单管理</div>
		<table class="biaoge_1" style="margin-top:10px;">
			<tr>
				<td class="biaoge_11">查询条件</td>
				<td class="biaoge_12">
					订单号:
					<input name="_orderNum" id="_orderNum" type="text" maxlength="50" /> 				
					状态:
					<select name="_status" id="_status" onchange="query();">
						<option value="30" >等待发货</option>												
						<option value="70" >已经发货</option>
						<option value="90" >交易完成</option>
						<option value="10" >订单取消</option>
					</select>
					<br/>
					运单号:
					<input name="_logisticsCode" id="_logisticsCode" type="text" maxlength="50" /> 
					<br/>
				</td>
			</tr>
			<tr>
				<td class="biaoge_11">操作</td>
				<td class="biaoge_12">
				<shiro:user>					
						<input type="button" class="button_1" name="" value="查询" onclick="query();"/> 						
					<shiro:hasPermission name="commodityOrder:deliveryOut">
						<input type="button" class="button_1" name="" value="发货"      onclick="deliveryOut();"             id="deliveryOutBtn"             style="display:none"/>
						<!-- <input type="button" class="button_1" name="" value="取消订单"  onclick="cancelOrder();"             id="cancelOrderBtn"             style="display:none"/>	 -->
					</shiro:hasPermission>
					<shiro:hasPermission name="commodityOrder:cancelDelivery">							
						<input type="button" class="button_1" name="" value="取消发货"  onclick="cancelDelivery();"          id="cancelDeliveryBtn"          style="display:none"/>
					</shiro:hasPermission>					
						<input type="button" class="button_1" name="" value="查看<shiro:hasPermission name="commodityOrder:deliveryOut">/留言</shiro:hasPermission>" onclick="showOrMessageFor10And30();" id="showOrMessageFor10And30Btn" style="display:none" />		
						<input type="button" class="button_1" name="" value="查看<shiro:hasPermission name="commodityOrder:cancelDelivery">/留言</shiro:hasPermission>" onclick="showOrMessageFor70And90();" id="showOrMessageFor70And90Btn" style="display:none" />				
				</shiro:user>
				</td>
			</tr>
		</table>
	</div>
	<div id="list" style="margin-top:20px;"></div>
	<div id="edit"></div>
</body>
</html>
