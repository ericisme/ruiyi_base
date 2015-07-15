<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>世宇手机游戏网站平台</title>
<link rel="icon" href="${ctx }/static/images/favicon.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="${ctx }/static/images/favicon.ico" type="image/x-icon"/>
<link type="text/css"  href="${ctx }/static/box/css/ui2.css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx }/static/box/js/landing-min.js"></script>



<frameset rows="147px,*,40px"  frameborder="0">
 
  <frame src="${ctx }/backEnd/top" name="topFrame" id="topFrame" noresize="noresize" scrolling="no"/>
  <frame src="${ctx }/backEnd/main" name="middleFrame" id="middleFrame" scrolling="no"/>
  <frame src="${ctx }/backEnd/bottom" name="bottomFrame" id="bottomFrame" noresize="noresize" scrolling="no"/>
</frameset>
<noframes>您的浏览器不支持框架</noframes>
</head>
</html>
