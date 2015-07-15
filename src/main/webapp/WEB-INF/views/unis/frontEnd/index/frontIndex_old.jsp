<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/main.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="http://zzcode.sinaapp.com/css/main.css">
<script src="http://zzcode.sinaapp.com/css/jquery-1.4.2.js" type="text/javascript" ></script>
<script src="http://zzcode.sinaapp.com/css/menuslide.js" type="text/javascript" ></script>
<script src="http://zzcode.sinaapp.com/css/menuslide.js" type="text/javascript" ></script>
<script src="http://zzcode.sinaapp.com/css/MSClass.js" type="text/javascript" ></script>
<!-- InstanceBeginEditable name="doctitle" -->
<title>世宇游戏平台Demo</title>
<!-- InstanceEndEditable -->
<!-- InstanceBeginEditable name="head" --><!-- InstanceEndEditable -->
</head>
 
<body>
<!--main head-->
<div class="main_head">
	<div class="logo_left">
		<H1>游戏中心</H1>
	</div>
	<div class="logo_right">
		<a href="#" title="中文"> 中文</a>|
		<a href="#" title="English"> English</a>|
		<a href="#" title="登录">登录</a>|
		<a href="#" title="免费注册" >免费注册</a>
		<input name="" type="text" /><input name="" value="搜 索" type="button" />
	</div>
</div><!--main head end--> 
<!-- InstanceBeginEditable name="EditRegion4" -->
<!--top banner begin-->
<style type="text/css"> 
.slide-banner{position:relative;width:740px;height:350px;}
.slide-banner .slider{position:absolute;}
.slide-banner .slider li{width:740px;height:350px;}
.slide-banner .btn-slide,.slide-banner .slider-bg{position:absolute;left:0;bottom:0;z-index:6;width:740px;height:45px;}
.slide-banner .btn-slide li{font-size:12px;font-weight:bold;cursor:pointer;float:left;color:#fff;width:160px;line-height:44px;height:45px;}
.slide-banner .btn-slide li img{vertical-align:bottom;float:left;margin:7px;border:2px solid #004a6a;width:42px;height:30px;}
.slide-banner .btn-slide .active{margin-top:-10px;border:none;height:56px;line-height:54px;position:relative;margin-left:-5px;padding-left:10px;width:160px;}
.slide-banner .btn-slide .active img{border-color:#fff;width:56px;height:40px;color:#fff;margin:5px;}
.slide-banner .slider-bg{z-index:4;background:#000;opacity:0.6;filter:alpha(opacity=60);}
</style>
 
<div class="top_banner"><!--top banner begin-->
<div class="top_banner_img"><!--top img begin-->
<div class="slide-banner">
	<div id="sliderbox">
		<ul class="slider" id="slider">
			<li><a><img src="http://zzcode.sinaapp.com/image/01.jpg"></a></li>
			<li><a><img src="http://zzcode.sinaapp.com/image/02.jpg"></a></li>
			<li><a><img src="http://zzcode.sinaapp.com/image/03.jpg"></a></li>
			<li><a><img src="http://zzcode.sinaapp.com/image/04.jpg"></a></li>
		</ul>
	</div>
	<ul class="btn-slide" id="btn-slide">
		<li><img src="http://zzcode.sinaapp.com/image/01_small.jpg">世宇游戏中心</li>
		<li><img src="http://zzcode.sinaapp.com/image/02_small.jpg">玩家社区</li>
		<li><img src="http://zzcode.sinaapp.com/image/03_small.jpg">全新游乐场管理</li>
		<li><img src="http://zzcode.sinaapp.com/image/04_small.jpg">畅游积分商城</li>
	</ul>
	<div class="slider-bg"></div>
</div>
 
</div><!--top img end-->
</div><!--top banner end-->
<script type="text/javascript"> 
new Marquee({
	MSClass:["sliderbox","slider","btn-slide"],
	Direction:0,
	Step:0.3,
	Width:740,
	Height:350,
	DelayTime:2,
	WaitTime:2,
	AutoStart:1
})
</script>
 
<!--top banner end-->
<!-- InstanceEndEditable -->
<!--导航Start-->
<div style=" background:#000000; ">
<div class="main_nav">
	<ul>
		<li><a href="http://zzcode.sinaapp.com/index.html" >首&nbsp;&nbsp;&nbsp;&nbsp;页</a></li>
		<li><a href="http://zzcode.sinaapp.com/center.html" >游戏中心</a>
				<ul style="display:none">
				<li><a href="http://zzcode.sinaapp.com/center.html" >热门游戏</a></li>
				<li><a href="http://zzcode.sinaapp.com/center_1.html" >游戏下载</a></li>
				<!--<li><a href="../center_2.html" >游戏资讯</a></li>-->
				</ul>
		</li>
		<li><a href="http://zzcode.sinaapp.com/point_4.html" >积分商城</a>
				<ul style="display:none">
				<li><a href="http://www.shiyushop.cn/" target="_blank">世宇商城</a></li>
				<li><a href="http://zzcode.sinaapp.com/point_4.html" >积分活动</a></li>
				<li><a href="http://zzcode.sinaapp.com/point.html" >线上兑换</a></li>
				<li><a href="http://zzcode.sinaapp.com/point_1.html" >实体店兑换</a></li>
				<li><a href="http://zzcode.sinaapp.com/point_2.html" >虚拟角色</a></li>
				<li><a href="http://zzcode.sinaapp.com/point_3.html" >物流系统</a></li>
				</ul>
		</li>
		
		<li><a href="http://zzcode.sinaapp.com/forum.html" >社&nbsp;&nbsp;&nbsp;&nbsp;群</a>
				<ul style="display:none">
				<li><a href="http://zzcode.sinaapp.com/forum.html" >竞争排名</a></li>
				<li><a href="http://bbs.shiyugame.com/"  target="_blank">讨论区</a></li>
				<li><a href="http://zzcode.sinaapp.com/forum_2.html" >社区活动</a></li>
				<li><a href="http://zzcode.sinaapp.com/forum_3.html" >玩家组织</a></li>
				</ul>
		</li>
		
		<li><a href="http://zzcode.sinaapp.com/pay.html" >充值中心</a>
				<ul style="display:none">
				<li><a href="#.html" >支付宝充值</a></li>
				<li><a href="#.html" >神州行充值</a></li>
				<li><a href="#.html" >移动手机支付</a></li>	
				<li><a href="#.html" >联通充值卡</a></li>	
				<li><a href="#.html" >银行转账</a></li>	
				</ul>
		</li>
		
		<li><a href="http://zzcode.sinaapp.com/member_1.html" >会员中心</a>
				<ul style="display:none">
				<li><a href="http://zzcode.sinaapp.com/member_1.html" >会员账户管理</a></li>
				<li><a href="http://zzcode.sinaapp.com/member_1.html" >成就积分系统</a></li>
				<li><a href="http://zzcode.sinaapp.com/member_1.html" >彩票管理</a></li>	
				<li><a href="http://zzcode.sinaapp.com/member_1.html" >游戏记录</a></li>	
				<li><a href="http://zzcode.sinaapp.com/member_1.html" >兑换记录</a></li>
				<li><a href="http://zzcode.sinaapp.com/member_2.html" >充值记录</a></li>
				</ul>
		</li>
		<li><a href="http://zzcode.sinaapp.com/develop.html" style="color:#FA9F08; ">开发者中心</a>
				<ul style="display:none">
				<!--<li><a href="../#" ></a></li>-->
				
				</ul>
		</li>
		<li><a href="http://zzcode.sinaapp.com/playground.html" style="color:#FF99FF; ">游乐场管理</a>
				<ul style="display:none">
				<!--<li><a href="../#" ></a></li>-->
				
				</ul>
		</li>
		<li style="background:none;" ><a style="color:#00FFFF;" href="http://zzcode.sinaapp.com/admin.html" >管理员</a>
				<ul style="display:none">
				<!--<li><a href="../#" ></a></li>-->
				
				</ul>
		</li>
	</ul>
</div>
</div>
<div class="blank10">&nbsp;</div>
<!--导航End-->
<div id="container">
<!--Banner Start-->
<!--
<div class="main_banner"></div>
<div class="blank10">&nbsp;</div>-->
<!--Banner End-->
 
<div class="mbody">
<!-- InstanceBeginEditable name="可编辑区域" -->
<div class="lbody left">
	<ul>
		<li><a href="#">左侧栏目一</a></li>
		<li><a href="#">左侧栏目二</a></li>
		<li><a href="#">左侧栏目三</a></li>
		<br>
		<img src="http://zzcode.sinaapp.com/image/weixin.jpg" alt="微信" />
		<br>
		&nbsp;&nbsp;&nbsp;&nbsp;扫描关注我们
	</ul>
</div>
<div class="rbody right">
<h2 class="r_body_text">
<img src="http://zzcode.sinaapp.com/image/index_03.jpg" alt="首页示例图片" />
</h2>
</div>
<!-- InstanceEndEditable -->
<div class="clearit"></div>
</div>
<div class="blank10">&nbsp;</div>
<div class="main_footer">
	<p>世宇科技  Copyright © 2013-2015 All Rights Reserved. </p>
</div>
</div>
</body>
<!-- InstanceEnd --></html>

