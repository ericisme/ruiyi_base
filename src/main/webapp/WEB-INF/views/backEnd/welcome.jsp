<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>top</title>

<script type="text/javascript" src="${ctx }/static/jquery/jquery-1.8.2.min.js"></script>

</head>

<body>
	<h1>欢迎光临 世宇手游 网站系统</h1>	
	<br/>
	<shiro:hasPermission name="commodityLogistics:edit">
		<c:if test="${numOf10CommodityOrder gt 0 }">
			<div>订单管理: 有 <a href="${ctx }/backEnd/commodityOrder/index" id="numOf10CommodityOrder">${numOf10CommodityOrder }</a> 条订单需要发货处理</div>
		</c:if>
	</shiro:hasPermission>
</body>
</html>
