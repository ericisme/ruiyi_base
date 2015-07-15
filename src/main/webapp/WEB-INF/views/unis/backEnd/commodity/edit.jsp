<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script src="${ctx }/static/js/unis/backEnd/picture/choosepicture.js"></script>
<script src="${ctx }/static/fineuploader/fineuploader-3.3.1.js"></script>
<script type="text/javascript" src="${ctx }/static/js/unis/backEnd/commodity/commodity.js"></script>
<script src="${ctx }/static/artDialog/jquery.artDialog.js?skin=default"></script>

<!--
<script src="${ctx }/static/static-page/js/vendor/bootstrap.min.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx }/static/static-page/css/styles.min.css"/> -->
<script type="text/javascript">
$().ready(function(){

	$('#choose').click(function(){
		showProcForImageSelector(0);
		oneSelectDoAjaxRequestAndSetThePictureTable();
	});

	$('#searchBtn').click(function(){
		oneSelectSearchBtnAndSetPictureTable();
	});
	$('#choose1').click(function(){
		showProcForImageSelector(1);
		multiSelectDoAjaxRequestAndSetThePictureTable();
	});
	$('#searchBtn1').click(function(){
		multiSelectSearchBtnAndSetPictureTable();
	});

});

</script>
<div class="weizhi">
	你的位置是：商城管理 >> 商品管理 >>
	<c:choose>
		<c:when test="${commodity.id eq 0}">
		新增商品
		</c:when>
		<c:otherwise>
		编辑商品
		</c:otherwise>
	</c:choose>
</div>
<form name="mainForm" id="mainForm">
	<input type="hidden" name="id" id="id" value="${commodity.id }" />
	<table class="biaoge_3" style="margin-top: 10px;">
		<tr>
			<td class="biaoge_31" colspan="4">注：带"*"为必填项</td>
		</tr>
		<tr>
			<td class="biaoge_32" style="width: 15%"><span style="color: #F00">*</span>名字</td>
			<td class="biaoge_33" style="width: 35%"><input type="text" class="text_3" name="name" id="name" value="${commodity.name }"/></td>
			<td class="biaoge_32" style="width: 15%"><span style="color: #F00">*</span>排序号</td>
			<td class="biaoge_33" style="width: 35%"> <input type="text" class="text_3" name="sortNumber" id="sortNumber" value="${commodity.sortNumber }" /> </td>
		</tr>

		<tr>
			<td class="biaoge_32"><span style="color: #F00">*</span>商品类别</td>
			<td class="biaoge_33">
				<span                id="commodityCategory_name" name="commodityCategory_name">${commodity.commodityCategory.name }</span>
				<input type="hidden" id="commodityCategory_id"   name="commodityCategory_id" value="${commodity.commodityCategory.id }"/>
				&nbsp;&nbsp;
				<a href="javascript:selectCommodityCategoryForEditPage()"> 选择</a>
				<!-- <select name="type">
					${type }
				</select>
				 -->
			</td>
			<td class="biaoge_32" style="width: 15%"><span style="color: #F00">*</span>所需彩票</td>
			<td class="biaoge_33" style="width: 35%"> <input type="text" class="text_3" name="price" id="price" value="${commodity.price }" /> </td>
		</tr>


		<tr>
			<td class="biaoge_32"><span style="color: #F00">*</span>状态</td>
			<td class="biaoge_33">
				<input type="radio" id="status" name="status" value="1" checked="checked" />启用&nbsp;&nbsp;
				<input type="radio" id="status" name="status" value="0" <c:if test="${commodity.status==0 }">checked="checked" </c:if>/>停用&nbsp;&nbsp;
			</td>
			<td class="biaoge_32"><span style="color: #F00">*</span>库存</td>
			<td class="biaoge_33"><input type="text" class="text_3" name="stocks" id="stocks" value="${commodity.stocks }" /> </td>
		</tr>

		<tr>
			<td class="biaoge_32"><span style="color: #F00">*</span>商品编号</td>

			<td class="biaoge_33"><input type="text" class="text_3" name="commodityNo" id="commodityNo" value="${commodity.commodityNo }"/></td>

		</tr>
		<tr>


			<td class="biaoge_32">首页图片(300X236 px)</td>
			<td class="biaoge_33"><input readonly type="text" class="text_3" name="indexPagePictureNumber" id="indexPagePictureNumber" value="${commodity.indexPagePictureNumber }" /> <input type="button" id="choose" value="选择图片"/></td>

			<td class="biaoge_32">图片编号(730x420 px)<br/>（格式 12,13,15）</td>
			<td class="biaoge_33"><input readonly type="text" class="text_3" name="pictureNumber" id="pictureNumber" value="${commodity.pictureNumber }" /> <input type="button" id="choose1" value="选择图片"/></td>
			<!--  <td rowspan="2" class="biaoge_32">图片预览</td>
			<td rowspan="2" class="biaoge_33" ><a id="imgurl"  title= "点击查看大图" href="" target="_blank"><img  id="preview" width="80" height="80" src=""></a> </td>
			 -->
		</tr>



		<%--
		<tr>
			<td class="biaoge_32">扩展属性（格式 高度:12cm;长度45cm;）</td>
			<td class="biaoge_33">
				<textarea id="extendedAttribute" name="extendedAttribute" rows="5" cols="60" >${commodity.extendedAttribute }</textarea>
			</td>
		</tr> --%>
		<!--  <tr>
			<td class="biaoge_32"><span style="color: #F00">*</span>图片</td>
			<td class="biaoge_33">
			 <div id="fine-uploader"></div>
			 <div align="right"></div>
			</td>

		</tr>  -->
		<tr>
			<td class="biaoge_33" style="width:35%" colspan="4" style="width:100px;">
			<div style="width:800px;background-color:#ccc;margin:auto;">
				<textarea cols="80" id="description" name="description" rows="10" >${commodity.description }</textarea>
				<script>
				CKEDITOR.replace( 'description', {
					pasteFromWordRemoveStyles : true,
					filebrowserImageUploadUrl : '/backEnd/news/uploadImg?type=image'
				});
				</script>
			</div>
			</td>
		</tr>


		<tr>
			<th colspan="4" class="t_title t_c">
			<shiro:hasPermission name="commodity:edit">

				<input name="btn01" id="sys_update_btn" type="button" onclick="save()" class="button_1" value="提 交" />
				&nbsp;&nbsp;&nbsp;&nbsp;
				</shiro:hasPermission>
				<input
				name="btn01" type="button" class="button_1"
				onclick="base.cancel('head','list','edit',commodityQuery);" value="返 回" />
			</th>
		</tr>
	</table>
</form>

<!-- Modal -->
<!--
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">图片选择</h4>
        名称：<input type="text" name="queryName" id="queryname" />
            <input type="button" id="searchBtn" value="筛选" />
      </div>
      <div class="modal-body">
       	<table id="pictureTable"  class="table">
       		<tr>
       			<th>编号</th>
       			<th>名称</th>
       			<th>预览</th>
       			<th>选中</th>
       		</tr>
       		<tr>
       			<td>1</td>
       			<td>2</td>
       			<td><img src="/upload/picture/preview/VGh1IEphbiAxNiAxNDo0MTo0NSBDU1QgMjAxNA==80x80.jpeg"/></td>
       			<td><input class="btn btn-default" type="button" value="选择" data-dismiss="modal"/></td>
       		</tr>

       	</table>
      </div>
       <div class="modal-footer">

       <select name="pagenum" id="pagenum">
       </select>
      </div>
    </div>
  </div>
</div> -->


<!-- Modal1 -->
<!-- <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">图片选择</h4>
        名称：<input type="text" name="queryName" id="queryname1" />
            <input type="button" id="searchBtn1" value="筛选" />
      </div>
      <div class="modal-body">
       	<table id="pictureTable1"  class="table">

       	</table>
      </div>
       <div class="modal-footer">

       <select name="pagenum1" id="pagenum1">

       </select>
      </div>
    </div>
  </div>
</div>-->


<!-- message_box -->

   <div id="message_box_for_image_selector" style="position:absolute;left:4px;top:4px;width:1024px;height:500px;z-index:1000;visibility:hidden">
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
    				<select name="pagenum" id="pagenum"></select>

    		</div>
    		<!-- 内容 end -->
   		</div><!-- message -->
  	</div><!-- message_box -->










