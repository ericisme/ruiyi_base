<!DOCTYPE html>
<html>
<head>
	<title>fancyBox - iframe demo</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
</head>
<body>

	<p>
		<Strong>${news.title }</Strong>
	</p>



	<p>
		${news.content }
	</p>
	
	<p align="right">
		<fmt:formatDate value="${news.addTime}" pattern="yyyy-MM-dd" />
	</p>
</body>
</html>