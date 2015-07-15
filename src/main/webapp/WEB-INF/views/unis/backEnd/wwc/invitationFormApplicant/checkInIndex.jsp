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
<title>与会者签到</title>
<link type="text/css" rel="stylesheet" href="${ctx }/static/css/table.css"/>
<script type="text/javascript" src="${ctx }/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx }/static/js/base/base.js"></script>
<script type="text/javascript" src="${ctx }/static/js/unis/backEnd/wwc/invitationFormApplicant/invitationFormApplicant.js"></script>
<script type="text/javascript" src="${ctx }/static/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx }/static/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
var baseUrl = "${ctx}/backEnd/wwc/invitationFormApplicant";
$(document).ready(function(){
		$("#_identityCode").focus();
		//签到率定时器
		setInterval(function(){refleshCheckInCount();},1000);
	}
); 
//回车事件搜索
document.onkeydown = function(e){ 
    var currKey=0,e=e||event; 
    if(e.keyCode==13){
    	checkIn();
    }else{
    	$("#_identityCode").focus();
    }
};

function refleshCheckInCount(){
	//$("#totalCount").html('');
	//$("#checkInCount").html('');
	//$("#checkInRate").html('');
	$.ajax({
			url:'${ctx}/backEnd/wwc/invitationFormApplicant/checkInCount',
			type:'post', //数据发送方式
			dataType:'JSON', //接受数据格式       
			data:null, //要传递的数据       
			success:
   			function (data){//回传函数(这里是函数名)				
   				$("#totalCount").html(data.totalCount);
   				$("#checkInCount").html(data.checkInCount);
   				$("#checkInRate").html(Math.round(data.checkInCount/data.totalCount*10000)/100);
   			}
	});
}

</script>
</head>
<body>
	<div id="head">
	<div class="weizhi" >你的位置是：发布会邀请管理 >>与会者签到管理</div>
		<table class="biaoge_1" style="margin-top:10px;">
			<tr>
				<td class="biaoge_11">查询条件</td>
				<td class="biaoge_12">		
					与会者二维码:
					<input name="_identityCode" id="_identityCode" type="text" maxlength="60" size="40" /> &nbsp;&nbsp;	
					<shiro:user>
						<shiro:hasPermission name="invitationFormApplicant:checkIn">
						<input type="button" class="button_1" name="" value="签到" onclick="checkIn();"/> 
						</shiro:hasPermission>
						<input type="button" class="button_1" name="" value="查找" onclick="searchIndex();"/> 
						<input type="button" class="button_1" name="" value="刷新签到率" onclick="refleshCheckInCount();"/> 						
					</shiro:user>
				</td>
			</tr>
			<tr>
				<td class="biaoge_11">签到情况</td>
				<td class="biaoge_12">		
					总人数：<font color="blue"><span id="totalCount"></span></font> &nbsp;&nbsp;	
					已签到人数：<font color="blue"><span id="checkInCount"></span></font> &nbsp;&nbsp;	
					签到率:<font color="blue"><span id="checkInRate"></span>%</font> &nbsp;&nbsp;						
				</td>
			</tr>
			<!-- 
			<tr>
				<td class="biaoge_11">操作</td>
				<td class="biaoge_12">
				<shiro:user>					
						<input type="button" class="button_1" name="" value="签到" onclick="checkIn();"/> 
				</shiro:user>
				</td>
			</tr> -->
		</table>
	</div>
	<div id="list" style="margin-top:20px;"></div>
	<div id="edit"></div>
</body>
</html>
