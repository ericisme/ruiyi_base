<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script src="${ctx }/static/fineuploader/fineuploader-3.3.1.js"></script>
<script src="${ctx }/static/js/unis/backEnd/picture/choosepicture.js"></script>
<script src="${ctx }/static/js/unis/backEnd/game/game.js"></script>

<script type="text/javascript">
$(document).ready(
		function(){
			picutureUploader();
			addOrDelete();


			$('#choose1').click(function(){
				showProcForImageSelector(1);
				multiSelectDoAjaxRequestAndSetThePictureTable();
			});

			$('#searchBtn1').click(function(){
				multiSelectSearchBtnAndSetPictureTable();
			});
		}
);
var _lastPic = "${game.iconPath }";
function picutureUploader() {
    var uploader = new qq.FineUploader({
      element: document.getElementById('fine-uploader'),
      text: {
          uploadButton: '点击上传',
      },
      request: {
        endpoint: '/backEnd/game/uploadIcon'
      },
      validation: {
          allowedExtensions: ['jpeg','png','jpg','bmp','gif'],
      },
      callbacks:{
    	  onCancel: function(id, name){
    		  $("#preview").attr("src",_lastPic);
    	  },
    	  onSubmit: function(id, name){
    		  $("#preview").attr("src","/static/images/postgressbar.gif");
    		  $("#iconPath").val("uploading");
    	  },
    	  onComplete: function(id, name, responseJSON){

    		  if(!(responseJSON.status=='error')){
    			  $("#preview").attr("src",responseJSON.url);
    				$("#iconPath").val(responseJSON.url);
    				 _lastPic = responseJSON.url;
    		  }else{
    			  base.tips("图片上传出现问题!", 'error');
    			  $("#iconPath").val("error");
    		  }
          }
      }
    });
  }

  function addOrDelete(){

	  $("#ios_add").click(function(){
		  if($('.ios_platform').length > 4){
			  return ;
		  }
		$('.ios_platform :last').after(
		    "<tr class='ios_platform'>" +
			"<td class='biaoge_32'>市场名称</td>" +
			"<td class='biaoge_33'><input type='text' class='text_3' name='ios_marketname' /></td>" +
			"<td class='biaoge_32'>下载链接</td>" +
			"<td class='biaoge_33'>" +
			"<input type='text' class='text_4' name='ios_url' />"+
			"</td>"+
		    "</tr>"
		    );
	  });
	  $("#ios_delete").click(function(){
		  if($('.ios_platform').length == 1){
			  return ;
		  }
		  $('.ios_platform :last').remove();
	  });


	  $("#android_add").click(function(){
		  if($('.android_platform').length > 4){
			  return ;
		  }
		   $('.android_platform :last').after(
		  "<tr class='android_platform'>" +
			"<td class='biaoge_32'>市场名称</td>"+
			"<td class='biaoge_33'><input type='text' class='text_3' name='android_marketname' /></td>" +
			"<td class='biaoge_32'>下载链接</td>"+
			"<td class='biaoge_33'>"+
			"<input type='text' class='text_4' name='android_url' />"+
			"</td>"+
		"</tr>"
		  );

	  });
	  $("#android_delete").click(function(){
		  if($('.android_platform').length == 1){
			  return ;
		  }
		  $('.android_platform :last').remove();
	  });
  }

</script>

<div class="weizhi">
	你的位置是：主页管理 >> 游戏管理 >>
	<c:choose>
		<c:when test="${game.id eq 0}">
		新增游戏
		</c:when>
		<c:otherwise>
		编辑游戏
		</c:otherwise>
	</c:choose>
</div>
<form name="mainForm" id="mainForm" enctype="multipart/form-data" method="post" action="/backEnd/game/save">
	<input type="hidden" name="id" id="id" value="${game.id }" />
	<input type="hidden" name="downloadTimes" id="downloadTimes" value="${game.downloadTimes }" />
	<input type="hidden" name="popularity" id="popularity" value="${game.popularity }" />
	<input type="hidden" name="iconPath" id="iconPath" value="${game.iconPath }" />
	<table class="biaoge_3" style="margin-top: 10px;">
		<tr>
			<td class="biaoge_31" colspan="4">注：带"*"为必填项</td>
		</tr>
		<tr>
			<td class="biaoge_32" style="width: 15%"><span style="color: #F00">*</span>游戏名称(中文)</td>
			<td class="biaoge_33" style="width: 35%"><input type="text" class="text_3" name="nameForCH" id="nameForCH" value="${game.nameForCH }"/></td>
			<td class="biaoge_32" style="width: 15%"><span style="color: #F00">*</span>游戏名称(英文)</td>
			<td class="biaoge_33" style="width: 35%"> <input type="text" class="text_3" name="nameForEN" id="nameForEN" value="${game.nameForEN }" /> </td>
		</tr>

		<tr>
			<td class="biaoge_32" style="width: 15%"><span style="color: #F00">*</span>最新版本</td>
			<td class="biaoge_33" style="width: 35%"><input type="text" class="text_3" name="gameVersion" id="gameVersion" value="${game.gameVersion }"/></td>
			<td class="biaoge_32" style="width: 15%"><span style="color: #F00">*</span>资费</td>
			<td class="biaoge_33" style="width: 35%"> <input type="text" class="text_3" name="needMoneyOrNot" id="needMoneyOrNot" value="${game.needMoneyOrNot }" /> </td>
		</tr>

		<tr>
			<td class="biaoge_32"><span style="color: #F00">*</span>类型</td>
			<td class="biaoge_33">
				<select name="type">
					${type }
				</select>
			</td>
			<td class="biaoge_32"><span style="color: #F00">*</span>图标</td>
			<td class="biaoge_33"> <div id="fine-uploader"></div><div align="right"></div>
				<img  id="preview" width="80" height="80" src="${game.iconPath }">
			</td>
		</tr>
		<tr>
			<td class="biaoge_32"><span style="color: #F00">*</span>状态</td>
			<td class="biaoge_33">
				<input type="radio" id="status" name="status" value="1" checked="checked" />启用&nbsp;&nbsp;
				<input type="radio" id="status" name="status" value="0" <c:if test="${game.status==0&&game.id!=0 }">checked="checked" </c:if>/>停用
			</td>
			<td class="biaoge_32"><span style="color: #F00">*</span>排序号</td>
			<td class="biaoge_33" >
				 <input type="text" class="text_3" name="sortNumber" id="sortNumber" value="${game.sortNumber }" />
			</td>

		</tr>
		<tr>
			<td class="biaoge_32"><span style="color: #F00">*</span>热门</td>
			<td class="biaoge_33">
				<input type="radio" id="isHot" name="isHot" value="1" checked="checked" />是&nbsp;&nbsp;
				<input type="radio" id="isHot" name="isHot" value="0" <c:if test="${game.isHot==0&&game.id!=0 }">checked="checked" </c:if>/>否
			</td>
			<td class="biaoge_32"><span style="color: #F00">*</span>新游戏</td>
			<td class="biaoge_33" >
				<input type="radio" id="isNew" name="isNew" value="1" checked="checked" />是&nbsp;&nbsp;
				<input type="radio" id="isNew" name="isNew" value="0" <c:if test="${game.isNew==0&&game.id!=0 }">checked="checked" </c:if>/>否
			</td>

		</tr>
		<tr>
			<td class="biaoge_32"><span style="color:red;">*</span>联动</td>
			<td class="biaoge_33">
				<input type="radio" id="isLinked" name="isLinked" value="1" checked="checked" />是&nbsp;&nbsp;
				<input type="radio" id="isLinked" name="isLinked" value="0" <c:if test="${game.isLinked==0&&game.id!=0 }">checked="checked" </c:if>/>否
			</td>
		</tr>
		<tr>
			<td class="biaoge_32"><span style="color: #F00">*</span>发布日期</td>
			<td class="biaoge_33" style="width:35%">
				<input type="text" class="Wdate" name="release_Date" id="release_Date"
					value="<fmt:formatDate value="${game.releaseDate}" pattern="yyyy-MM-dd" />"
					size="20" readonly title="点击选择日期"
					onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" />
			</td>
			<td class="biaoge_32">描述(少于500字符)</td>
			<td class="biaoge_33"><textarea id="description" name="description" rows="8" cols="60" >${game.description }</textarea></td>
		</tr>

		<tr>
			<td class="biaoge_32" >图片编号（格式 12,13,15）</td>
			<td class="biaoge_33"><input type="text" class="text_3" name="pictureNumber" id="pictureNumber" value="${game.pictureNumber }"/> <input type="button" id="choose1" value="选择图片"/></td>
			<td class="biaoge_32"></td>
			<td class="biaoge_33"></td>
		</tr>

		<tr>
			<td class="biaoge_31" colspan="4">IOS平台</td>
		</tr>
		<tr>
			<td class="biaoge_32" >大小(MB) </td>
			<td class="biaoge_33"><input type="text" class="text_3" name="ios_size" id="ios_size" value="${game.ios_size }"/></td>
			<td class="biaoge_32">操作</td>
			<td class="biaoge_33"><input  id="ios_add" type="button" class="button_1" value=" + " /><input  id="ios_delete" type="button" class="button_1" value=" - " /></td>
		</tr>
		<c:choose>
			<c:when test="${game.id!=0 && ios_url_size!=0}">
				<c:forEach items="${iosPlatformAndMarkets }" var="ios_item" varStatus="ios_status">
					<tr class="ios_platform">
					<td class="biaoge_32">市场名称<input type="hidden" class="text_3" name="ios_id" value="${ios_item.id }"/></td>
					<td class="biaoge_33"><input type="text" class="text_3" name="ios_marketname"  value="${ios_item.marketName }"/></td>
					<td class="biaoge_32">下载链接</td>
					<td class="biaoge_33"><input type="text" class="text_4" name="ios_url" value="${ios_item.downloadUrl }"/></td>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				 <tr class="ios_platform">
					<td class="biaoge_32">市场名称</td>
					<td class="biaoge_33"><input type="text" class="text_3" name="ios_marketname"  value=""/></td>
					<td class="biaoge_32">下载链接</td>
					<td class="biaoge_33"><input type="text" class="text_4" name="ios_url"  value=""/></td>
				</tr>
			</c:otherwise>
		</c:choose>
		<tr>
			<td class="biaoge_31" colspan="4">Android平台</td>
		</tr>
		<tr>
			<td class="biaoge_32" >大小(MB) </td>
			<td class="biaoge_33"><input type="text" class="text_3" name="android_size" id="android_size" value="${game.android_size }"/></td>
			<td class="biaoge_32">操作</td>
			<td class="biaoge_33"><input  id="android_add" type="button" class="button_1" value=" + " /><input  id="android_delete" type="button" class="button_1" value=" - " /></td>
		</tr>

		<c:choose>
			<c:when test="${game.id!=0 && android_url_size!=0}">
				<c:forEach items="${androidPlatformAndMarkets }" var="android_item" varStatus="android_status">
					<tr class="android_platform">

					<td class="biaoge_32">市场名称<input type="hidden" class="text_3" name="android_id" value="${android_item.id }"/></td>
					<td class="biaoge_33"><input type="text" class="text_3" name="android_marketname"  value="${android_item.marketName }"/></td>
					<td class="biaoge_32">下载链接</td>
					<td class="biaoge_33"><input type="text" class="text_4" name="android_url"  value="${android_item.downloadUrl }"/></td>
				</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				 <tr class="android_platform">
					<td class="biaoge_32">市场名称</td>
					<td class="biaoge_33"><input type="text" class="text_3" name="android_marketname"  value=""/></td>
					<td class="biaoge_32">下载链接</td>
					<td class="biaoge_33"><input type="text" class="text_4" name="android_url" value=""/></td>
				</tr>
			</c:otherwise>
		</c:choose>


		<tr>
			<th colspan="4" class="t_title t_c">
			<shiro:hasPermission name="game:edit">
				<input name="btn01" id="sys_update_btn" type="button" onclick="save()" class="button_1" value="提 交" />
				&nbsp;&nbsp;&nbsp;&nbsp;
			</shiro:hasPermission>
				<input name="btn01" type="button" class="button_1" onclick="base.cancel('head','list','edit',gameQuery);" value="返 回" />
			</th>
		</tr>
	</table>
</form>


<!-- message_box -->

   <div id="message_box_for_image_selector" style="position:absolute;left:4px;top:4px;width:1024px;height:400px;z-index:1000;visibility:hidden">
   		<div id="message" style="border:skyblue solid; border-width:1 1 3 1; width:95%; height:95%; background:#fff; color:#036; font-size:12px; line-height:150%;">
    		<!-- DIV弹出状态行：标题、关闭按钮 -->
    		<!-- <div style="background:skyblue; height:5%; font-family:Verdana, Arial, Helvetica, sans-serif; font-size:12px; padding:3 5 0 5; color:#fff" onmousedown="drag(message_box);return false"> -->
     		<div style="background:skyblue; height:5%; font-family:Verdana, Arial, Helvetica, sans-serif; font-size:12px; padding:3 5 0 5; color:#fff" >
     			<span style="display:inline;width:150px;position:absolute;font-size:200%"></span>
     			<span onclick="closeProcForImageSelector();" style="float:right;display:inline;cursor:pointer;font-size:200%">×</span>
    		</div>
    		<div id="content">
    		    编号：<input type="text" name="queryId" id="queryid" />
        		名称：<input type="text" name="queryName" id="queryname" />
        		类型：<select  name="pictureType" id="pictureType">
        			<option value="all">全部</option>
        			${pictureType }</select>
            		<input type="button" id="searchBtn" value="筛选" />
            		<input type="button" id="searchBtn1" value="筛选" />
    				<table id="pictureTable"  class="biaoge_3" style="margin-top: 10px;">

       				</table>
    				<select name="pagenum" id="pagenum" ></select>

    		</div>
    		<!-- 内容 end -->
   		</div><!-- message -->
  </div><!-- message_box -->