<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>世宇手机游戏</title>
<link type="text/css"  href="${ctx }/static/css/css.css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx }/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx }/static/js/base/base.js"></script>


<script type="text/javascript"> 

//系统切换
function onMShow(XelementId,Xstatus){ 
    if(Xstatus) 
      document.getElementById(XelementId).style.display="block"; 
    else 
      document.getElementById(XelementId).style.display="none"; 
} 
//常见问题
function question(){
	base.tips("我们将稍后更新，不便之处请见谅!!!","success");
}
//帮助文档
function helpDoc(){
	base.tips("我们将稍后更新，不便之处请见谅!!!","success");
}
//桌面
function sysdesk(){
	parent.document.getElementById("middleFrame").src= "${ctx}/main";
}
//返回首页
function goHome(url){
	base.forward(url,parent);
}
//注销
function logout(){
	if(!confirm("您确定要注销并退出系统吗?")){
		return;
	}
	base.forward("/logout",parent);
}
function refresh(){
	parent.document.getElementById('middleFrame').contentWindow.document.getElementById('mainFrame').src='${ctx}/backEnd/modifyPasswordEdit';
}
function gotoFrontEnd(){
	top.window.location.href="/";
}
function getCurDate(){
	var d = new Date();
	var week;
	switch (d.getDay()){
		case 1: week="星期一"; 
		break; 
		case 2: week="星期二"; 
		break; 
		case 3: week="星期三"; 
		break; 
		case 4: week="星期四"; 
		break; 
		case 5: week="星期五";
		break; 
		case 6: week="星期六"; 
		break; 
		default: week="星期天";
	} 
	var years = d.getYear();
	var month = add_zero(d.getMonth()+1);
	var days = add_zero(d.getDate());
	var hours = add_zero(d.getHours());
	var minutes = add_zero(d.getMinutes());
	var seconds=add_zero(d.getSeconds());
	var ndate = years+"年"+month+"月"+days+"日 "+hours+":"+minutes+":"+seconds+" "+week; 
	$(".time").text(ndate);
}
function add_zero(temp){
	if(temp<10) 
		return "0"+temp; 
	else 
		return temp;
}

$(function(){
	setInterval("getCurDate()",100);
});

//系统顶部 的    机构  联动   学校列表下拉
function getDingXxSelect(dw_id,situ){
	base.request("/school/getDingXxSelect","dw_id=" + dw_id,function(result){
		var opts = result.result.xx_select;
		if(situ==null || situ==undefined){			
			$("#_xx_id").html("<option value=\"0\">请选择</option>"+opts);
		}else{
			$("#"+situ).html(""+opts);
		}
		
		var xx_id = $("#_ding_xx_id").find("option:selected").val();
		if(xx_id == "" || xx_id == "-1"){
			base.alert("当前没有可操作的学校，系统自动跳回首页!");
			sysdesk();
		}else{
			window.parent.middleFrame.mainFrame.location.reload(); //刷新main窗口
		}
	},"POST","JSON");
}
//系统 顶部 的学校 列表 onchange
function dingXx_idSelectChange(xx_id){
	base.request("/school/dingXx_idSelectChange","xx_id=" + xx_id,function(result){		
			window.parent.middleFrame.mainFrame.location.reload(); //刷新main窗口
	},"POST","JSON");
}

</script>
</head>
<body>
<div id="header">
  <div class="top">
        <div class="logo_yun"><!-- <img src="${ctx }/static/images/logo_yun_2.gif"/> --></div>
        <div class="splitline"></div>
        <div class="logo_ecs">
          <div class="cname">世宇游戏-<font size="5">后台管理系统</font></div>
          <div class="ename">Unis mobile-game System</div>
        </div>
        <div class="logo_sys"><!--<img src="${ctx }/static/images/logo_sys_1.gif"/>--></div>    
        <div class="info">
          <div class="school">
          	<!--<img class="school_logo" src="${ctx }/static/images/school_1.png"/>-->
          </div>
          <div class="clear"></div>
          <div class="user_info">
          		  欢迎您：${user.username } >> <!-- 角色：教师 > 职位：主任 > --> 
          </div> 
        </div>  
  </div>
  <div class="banner1">
    <!-- <div class="onMShow" onmouseover="onMShow('buttons',true)" onmouseout="onMShow('buttons',false)">系统切换</div>
    <div id="buttons" onmouseover="onMShow('buttons',true)" onmouseout="onMShow('buttons',false)">
      ${HTML }
    </div>
     -->
    <!-- <div class="menu_b" style="width:40%"> -->
    <div class="menu_b">
       <div class="time"></div>
       <!-- <div class="ico_b"><a href="#" onclick="question();" title="常见问题"><img border="0" src="${ctx }/static/images/ico_b_1.gif"/></a></div>
       <div class="ico_b"><a href="#" onclick="helpDoc();" title="帮助文档"><img border="0" src="${ctx }/static/images/ico_b_2.gif"/></a></div>
       <div class="ico_b"><a href="#" onclick="sysdesk();" title="桌面"><img border="0" src="${ctx }/static/images/ico_b_3.gif"/></a></div>
       
        -->
        
        <div class="ico_b"><a href="#" onclick="refresh();" title="修改密码"><img border="0" src="${ctx }/static/images/ico_b_3.gif"/></a></div>
        <div class="ico_b"><a href="#" onclick="gotoFrontEnd();" title="返回前台"><img border="0" src="${ctx }/static/images/ico_b_4.gif"/></a></div>
        <div class="ico_b"><a href="#" onclick="logout('${logoutUrl }');" title="注销"><img border="0" src="${ctx }/static/images/ico_b_5.gif"/></a></div>
        
        
    </div>
  </div>
</div>
</body>
</html>
