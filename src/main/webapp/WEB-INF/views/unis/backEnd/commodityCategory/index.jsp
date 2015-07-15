<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商品分类管理</title>
<link type="text/css" href="${ctx }/static/jquery-zTree-v3.4/css/demo.css" rel="stylesheet"/>
<link type="text/css" href="${ctx }/static/jquery-zTree-v3.4/css/zTreeStyle/zTreeStyle.css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx }/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx }/static/jquery-zTree-v3.4/js/jquery.ztree.core-3.4.js"></script>
<script type="text/javascript" src="${ctx }/static/js/base/base.js"></script>
<script type="text/javascript" src="${ctx }/static/js/unis/backEnd/commodityCategory/commodityCategory.js"></script>

<script type="text/javascript">
	var baseUrl = "${ctx}/backEnd/commodityCategory";

	$(document).ready(function(){
		base.load("content",baseUrl + "/list",function(){});
	});
</script>
</head>
<body>
<div class="weizhi">你的位置是：商城管理>>商品分类管理</div>
<div id="content">
</div>
</body>
</html>
