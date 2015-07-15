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
<title>主页管理</title>
<link type="text/css" rel="stylesheet" href="${ctx }/static/css/table.css"/>
<link type="text/css" href="${ctx }/static/fineuploader/fineuploader-3.3.1.css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx }/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx }/static/js/base/base.js"></script>
<script type="text/javascript" src="${ctx }/static/js/unis/backEnd/picture/picture.js"></script>
<script type="text/javascript" src="${ctx }/static/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx }/static/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(document).ready(
		function(){
			pictureQuery();
		}
);
//回车事件搜索
document.onkeydown = function(e){
    var currKey=0,e=e||event;
    if(e.keyCode==13)
    {
    	pictureQuery();
    }
};
</script>
</head>
<body>
	<div id="head">
	<div class="weizhi" >你的位置是：商城管理 >> 图片管理</div>
		<table class="biaoge_1" style="margin-top:10px;">
			<tr>
				<td class="biaoge_11">查询条件</td>
				<td class="biaoge_12">
						名字：<input name="queryName" id="queryName" type="text" maxlength="10" /> &nbsp;&nbsp;
						类型:<select name="queryType" id="queryType">
									<option value="all">全部</option>
									${type }
							</select>
						状态: <select name="queryStatus" id="queryStatus">
								<option value="-1">
									全部
								</option>
								<option value="1">
									启用
								</option>
								<option value="0">
									停用
								</option>
							</select>
				</td>
			</tr>
			<tr>
				<td class="biaoge_11">操作</td>
				<td class="biaoge_12">
				<shiro:hasPermission name="picture:view">
					<input type="button" class="button_1"  value="查询" onclick="pictureQuery();"/>
				</shiro:hasPermission>
				<shiro:hasPermission name="picture:edit">
					<input type="button" class="button_1"  value="添加" onclick="edit(0);"/>
					<input type="button" class="button_1"  value="批量添加" onclick="multiupload()"/>
					<input type="button" class="button_1"  value="删除" onclick="delMore();"/>
				</shiro:hasPermission>

				</td>
			</tr>
		</table>
	</div>
	<div id="list" style="margin-top:20px;"></div>
	<div id="edit"></div>
</body>
</html>
