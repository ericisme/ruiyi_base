<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta name="renderer" content="webkit">
<meta charset="utf-8">
<meta http-equiv=”X-UA-Compatible” content=”IE=Edge,chrome=1″ >
<title>世宇|UNIS</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	<h2>
		你的入场券
	</h2>
	<div style="text-align: center;">
		<img src="${qrcode }" alt="" width="80%">
		<h2>${name}</h2>
	</div>
</body>
</html>