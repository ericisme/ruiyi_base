<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%response.setStatus(404);%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>404 - 页面不存在</title>
</head>

<body>

<div class="top_show">&nbsp;</div>

<div class="err404 clear" style="background:#fff;margin-top:10px;">

	<b>你可以尝试一下：</b><br />
	&nbsp;&nbsp;&nbsp;&nbsp;<a href="/backEnd/frame">返回主页</a>

</div>

<div id="footer">

</div>
<div id="append"></div>
</body>
</html>