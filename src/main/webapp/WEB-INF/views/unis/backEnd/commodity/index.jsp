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
<link type="text/css" href="${ctx }/static/jquery-zTree-v3.4/css/demo.css" rel="stylesheet"/>
<link type="text/css" href="${ctx }/static/jquery-zTree-v3.4/css/zTreeStyle/zTreeStyle.css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx }/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx }/static/jquery-zTree-v3.4/js/jquery.ztree.core-3.4.js"></script>
<script type="text/javascript" src="${ctx }/static/js/base/base.js"></script>
<script type="text/javascript" src="${ctx }/static/js/unis/backEnd/commodity/commodity.js"></script>
<script type="text/javascript" src="${ctx }/static/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx }/static/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx }/static/ckeditor/ckeditor.js"></script>

<script type="text/javascript">
$(document).ready(
		function(){
			commodityQuery();
		}
);
//回车事件搜索
document.onkeydown = function(e){
    var currKey=0,e=e||event;
    if(e.keyCode==13)
    {
    	commodityQuery();
    }
};
</script>
</head>
<body>
	<div id="head">
	<div class="weizhi" >你的位置是：商城管理 >> 商品管理</div>
		<table class="biaoge_1" style="margin-top:10px;">
			<tr>
				<td class="biaoge_11">查询条件</td>
				<td class="biaoge_12">
						名字：<input name="queryName" id="queryName" type="text" maxlength="10" /> &nbsp;&nbsp;

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
							&nbsp;&nbsp;
						类型:<!-- <select name="queryType" id="queryType">
									<option value="all">全部</option>
									${type }
							</select>	 -->

							<a                   id="_commodityCategory_name" name="_commodityCategory_name" href="javascript:selectCommodityCategoryForIndexPage();">全部分类</a>
							<input type="hidden" id="_commodityCategory_id"   name="_commodityCategory_id"   value="1"/>
				</td>
			</tr>
			<tr>
				<td class="biaoge_11">操作</td>
				<td class="biaoge_12">
				<shiro:hasPermission name="commodity:view">
					<input type="button" class="button_1" name="" value="查询" onclick="commodityQuery();"/>
				</shiro:hasPermission>
				<shiro:hasPermission name="commodity:edit">
						<input type="button" class="button_1" name="" value="添加" onclick="edit(0,1);"/>
						<input type="button" class="button_1" name="" value="删除" onclick="delMore();"/>
				</shiro:hasPermission>


				</td>
			</tr>
		</table>
	</div>
	<div id="list" style="margin-top:20px;"></div>
	<div id="edit"></div>



	<script type="text/javascript">
	//点击显示 商品分类选择器 for edit page
	function selectCommodityCategoryForEditPage(){
		base.load("commodityCategorySelectContent","${ctx }/backEnd/commodityCategory/listCommodityCategory?onClickMethord=editPageOnClick",function(){
			showProc();
		});
	}
	//点击选择 商品分类项后 for edit page
	function editPageOnClick(e, treeId, node) {
		if(node.id=="1"){
			return;
		}else{
			$("#commodityCategory_name").html(node.name);
			$("#commodityCategory_id").val(node.id);
		}
		closeProc();
		$("#body1").css("overflow-y","auto");
	}
	//点击显示 商品分类选择器 for index page
	function selectCommodityCategoryForIndexPage(){
		base.load("commodityCategorySelectContent","${ctx }/backEnd/commodityCategory/listCommodityCategory?onClickMethord=indexPageOnClick",function(){
			showProc();
		});
	}
	//点击选择 商品分类项后 for index page
	function indexPageOnClick(e, treeId, node) {
		$("#_commodityCategory_name").html(node.name);
		$("#_commodityCategory_id").val(node.id);
		closeProc();
		$("#body1").css("overflow-y","auto");
		query();
	}
	//显示
	function showProc(){
		  message_box.style.visibility='visible';

		  //创建灰色背景层
		  procbg = document.createElement("div");
		  procbg.setAttribute("id","mybg");
		  procbg.style.background = "#000";
		  procbg.style.width = "100%";
		  procbg.style.height = "100%";
		  procbg.style.position = "absolute";
		  procbg.style.top = "0";
		  procbg.style.left = "0";
		  procbg.style.zIndex = "500";
		  procbg.style.opacity = "0.3";
		  procbg.style.filter = "Alpha(opacity=30)";

		  //背景层加入页面
		  document.body.appendChild(procbg);
		  document.body.style.overflow = "hidden";
	}
	//拖动
	function drag(obj){
		  var s = obj.style;
		  var b = document.body;
		  var x = event.clientX + b.scrollLeft - s.pixelLeft;
		  var y = event.clientY + b.scrollTop - s.pixelTop;
		  var m = function(){
		   	if(event.button == 1){
			   if((event.clientX + b.scrollLeft - x)>0){
		    		s.pixelLeft = event.clientX + b.scrollLeft - x;
		    	}
		    	if((event.clientY + b.scrollTop - y)>0){
		    		s.pixelTop = event.clientY + b.scrollTop - y;
		    	}
		    	//$(".weizhi").html(s.pixelTop);
		   	}else {
		    	document.detachEvent("onmousemove", m);
		   	}
		  };
		  document.attachEvent("onmousemove", m);
		  if(!this.z)
		  	this.z = 999;
		  s.zIndex = ++this.z;
		  event.cancelBubble = true;
	}
	//关闭
	function closeProc(){
		  message_box.style.visibility='hidden';
		  procbg.style.visibility = "hidden";
		  document.body.style.overflow = "auto";
	}
	</script>
   <div id="message_box" style="position:absolute;left:4px;top:4px;width:246px;height:415px;z-index:1000;visibility:hidden">
   		<div id="message" style="border:skyblue solid; border-width:1 1 3 1; width:95%; height:95%; background:#fff; color:#036; font-size:12px; line-height:150%;">
    		<!-- DIV弹出状态行：标题、关闭按钮 -->
    		<!-- <div style="background:skyblue; height:5%; font-family:Verdana, Arial, Helvetica, sans-serif; font-size:12px; padding:3 5 0 5; color:#fff" onmousedown="drag(message_box);return false"> -->
     		<div style="background:skyblue; height:5%; font-family:Verdana, Arial, Helvetica, sans-serif; font-size:12px; padding:3 5 0 5; color:#fff" >
     			<span style="display:inline;width:150px;position:absolute;font-size:200%"></span>
     			<span onclick="closeProc();" style="float:right;display:inline;cursor:pointer;font-size:200%">×</span>
    		</div>
    		<div id="commodityCategorySelectContent">
    		</div>
   		</div><!-- message -->
  </div><!-- message_box -->


</body>
</html>
