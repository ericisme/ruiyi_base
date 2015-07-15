<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script src="${ctx }/static/fineuploader/fineuploader-3.3.1.js"></script>
<script type="text/javascript" src="${ctx }/static/js/unis/backEnd/commodity/commodity.js"></script>
<script src="${ctx }/static/artDialog/jquery.artDialog.js?skin=default"></script>
<!-- 
<script src="${ctx }/static/static-page/js/vendor/bootstrap.min.js"></script>  
<link type="text/css" rel="stylesheet" href="${ctx }/static/static-page/css/styles.min.css"/> -->
<script type="text/javascript">
$().ready(function(){
	
	
	$('#choose').click(function(){
		
		$.ajax({
			cache: true,
			dataType: "json",//返回json格式的数据
			type: "get",
			url:"/backEnd/picture/pickPicture",
			data:"page=1",// 你的formid
			async: false,
		    error: function(request) {
		    	base.tips("出现未知异常，操作失败!", 'error');
		    },
		    success: function(data) {
		    	$('#pictureTable').html("<tr><th>编号</th><th>名称</th><th>预览</th><th>选中</th></tr>");
		    	$(data.pageContent).each(function(){
		    		$('#pictureTable').append("<tr><td> " +this.id +  "</td><td>" +this.name+ "</td><td>" + 
		    				"<img src='" + this.previewUrl + "'/></td><td><input id='picture_id_" + this.id + "' class='btn btn-default' pictureid='" + this.id + "' type='button' value='选择' data-dismiss='modal'/></td></tr>");
	       			
		    	});
		    	$('#pagenum').html('');
		    	for(var i=1;i<=data.maxPageNum;i++){
		    		$('#pagenum').append("<option value='" + i + "' > 第" + i +  "页</option>");
		    	}
		    	$("#pagenum").click(function(){
		    		
		    		$.ajax({
		    			cache: true,
		    			dataType: "json",//返回json格式的数据
		    			type: "get",
		    			url:"/backEnd/picture/pickPicture",
		    			data:"page=" +$("#pagenum").val() ,// 你的formid
		    			async: false,
		    		    error: function(request) {
		    		    	base.tips("出现未知异常，操作失败!", 'error');
		    		    },
		    		    success: function(data) {
		    		    	$('#pictureTable').html("<tr><th>编号</th><th>名称</th><th>预览</th><th>选中</th></tr>");
		    		    	$(data.pageContent).each(function(){
		    		    		$('#pictureTable').append("<tr><td> " +this.id +  "</td><td>" +this.name+ "</td><td>" + 
		    		    				"<img src='" + this.previewUrl + "'/></td><td><input id='picture_id_" + this.id + "' class='btn btn-default' pictureid='" + this.id + "' type='button' value='选择' data-dismiss='modal'/></td></tr>");
		    	       			
		    		    	});
		    		    	$("input[id^=picture_id_]").click(function(){
		    		    		$('#indexPagePictureNumber').val($(this).attr('pictureid'));
		    		    	});	
		    		    }
		    		});
		    		
		    		
		    	});
		    	
		    	$("input[id^=picture_id_]").click(function(){
		    		$('#indexPagePictureNumber').val($(this).attr('pictureid'));
		    	});	
		    }
		});
	});
	
	
$('#searchBtn').click(function(){
		
		$.ajax({
			cache: true,
			dataType: "json",//返回json格式的数据
			type: "get",
			url:"/backEnd/picture/pickPicture",
			data:"queryName=" + $('#queryname').val() + "&page=1",// 你的formid
			async: false,
		    error: function(request) {
		    	base.tips("出现未知异常，操作失败!", 'error');
		    },
		    success: function(data) {
		    	$('#pictureTable').html("<tr><th>编号</th><th>名称</th><th>预览</th><th>选中</th></tr>");
		    	$(data.pageContent).each(function(){
		    		$('#pictureTable').append("<tr><td> " +this.id +  "</td><td>" +this.name+ "</td><td>" + 
		    				"<img src='" + this.previewUrl + "'/></td><td><input id='picture_id_" + this.id + "' class='btn btn-default' pictureid='" + this.id + "' type='button' value='选择' data-dismiss='modal'/></td></tr>");
	       			
		    	});
		    	
		    	$('#pagenum').html('');
		    	for(var i=1;i<=data.maxPageNum;i++){
		    		$('#pagenum').append("<option value='" + i + "' > 第" + i +  "页</option>");
		    	}
		    	$("input[id^=picture_id_]").click(function(){
		    		$('#indexPagePictureNumber').val($(this).attr('pictureid'));
		    	});	
		    }
		    
		    
		});
		
	});
	
	
	$('#choose1').click(function(){
		$.ajax({
			cache: true,
			dataType: "json",//返回json格式的数据
			type: "get",
			url:"/backEnd/picture/pickPicture",
			data:"page=1",// 你的formid
			async: false,
		    error: function(request) {
		    	base.tips("出现未知异常，操作失败!", 'error');
		    },
		    success: function(data) {
		    	$('#pictureTable1').html("<tr><th>编号</th><th>名称</th><th>预览</th><th>选中</th></tr>");
		    	$(data.pageContent).each(function(){
		    		$('#pictureTable1').append("<tr><td> " +this.id +  "</td><td>" +this.name+ "</td><td>" + 
		    				"<img src='" + this.previewUrl + "'/></td><td><input id='picture_id1_" + this.id + "' class='btn btn-default' pictureid1='" + this.id + "' type='checkbox' /></td></tr>");
		    		setDefaultChecked(this.id,$('#picture_id1_' + this.id));
		    		//var pnum = $('#pictureNumber').val();
		    		//if(pnum.indexOf(','+ this.id +',') > -1 || pnum.startWith(this.id + ',')){
		    			//alert(this.id);
		    		//	$('#picture_id1_' + this.id).attr("checked","true");
		    		//}
		    		//alert($('#picture_id1_' + this.id).attr("pictureid1"));
		    	});
		    	
		    	
		    	$('#pagenum1').html('');
		    	for(var i=1;i<=data.maxPageNum;i++){
		    		$('#pagenum1').append("<option value='" + i + "' > 第" + i +  "页</option>");
		    	}
		    	$("#pagenum1").click(function(){
		    		
		    		$.ajax({
		    			cache: true,
		    			dataType: "json",//返回json格式的数据
		    			type: "get",
		    			url:"/backEnd/picture/pickPicture",
		    			data:"page=" +$("#pagenum1").val() ,// 你的formid
		    			async: false,
		    		    error: function(request) {
		    		    	base.tips("出现未知异常，操作失败!", 'error');
		    		    },
		    		    success: function(data) {
		    		    	$('#pictureTable1').html("<tr><th>编号</th><th>名称</th><th>预览</th><th>选中</th></tr>");
		    		    	$(data.pageContent).each(function(){
		    		    		$('#pictureTable1').append("<tr><td> " +this.id +  "</td><td>" +this.name+ "</td><td>" + 
		    		    				"<img src='" + this.previewUrl + "'/></td><td><input id='picture_id1_" + this.id + "' class='btn btn-default' pictureid1='" + this.id + "' type='checkbox' /></td></tr>");
		    	       			
		    		    		setDefaultChecked(this.id,$('#picture_id1_' + this.id));
		    		    		//var pnum = $('#pictureNumber').val();
		    		    		//if(pnum.indexOf(','+ this.id +',') > -1 || pnum.startWith(this.id + ',')){
		    		    			//alert(this.id);
		    		    		//	$('#picture_id1_' + this.id).attr("checked","true");
		    		    		//}
		    		    		//alert($('#picture_id1_' + this.id).attr("pictureid1"));
		    		    	});
		    		    	$("input[id^=picture_id1_]").click(function(){
		    		    		//alert($(this).attr("pictureid1"));
		    		    		//doCheckedOrUnchecked($(this).is(":checked"));
		    		    		doCheckedOrUnchecked($(this));
		    		    	});	
		    		    }
		    		});
		    		
		    		
		    	});
		    	
		    	$("input[id^=picture_id1_]").click(function(){
		    		//alert($(this).attr("pictureid1"));
		    		//doCheckedOrUnchecked($(this).is(":checked"));
		    		doCheckedOrUnchecked($(this));
		    	});	
		    }
		});
	});
	
	
$('#searchBtn1').click(function(){
		
		$.ajax({
			cache: true,
			dataType: "json",//返回json格式的数据
			type: "get",
			url:"/backEnd/picture/pickPicture",
			data:"queryName=" + $('#queryname1').val() + "&page=1",// 你的formid
			async: false,
		    error: function(request) {
		    	base.tips("出现未知异常，操作失败!", 'error');
		    },
		    success: function(data) {
		    	$('#pictureTable1').html("<tr><th>编号</th><th>名称</th><th>预览</th><th>选中</th></tr>");
		    	$(data.pageContent).each(function(){
		    		$('#pictureTable1').append("<tr><td> " +this.id +  "</td><td>" +this.name+ "</td><td>" + 
		    				"<img src='" + this.previewUrl + "'/></td><td><input id='picture_id1_" + this.id + "' class='btn btn-default' pictureid1='" + this.id + "' type='checkbox' /></td></tr>");
	       			
		    		setDefaultChecked(this.id,$('#picture_id1_' + this.id));
		    		//var pnum = $('#pictureNumber').val();
		    		//if(pnum.indexOf(','+ this.id +',') > -1 || pnum.startWith(this.id + ',')){
		    			//alert(this.id);
		    		//	$('#picture_id1_' + this.id).attr("checked","true");
		    		//}
		    		//alert($('#picture_id1_' + this.id).attr("pictureid1"));
		    	});
		    	
		    	$('#pagenum1').html('');
		    	for(var i=1;i<=data.maxPageNum;i++){
		    		$('#pagenum1').append("<option value='" + i + "' > 第" + i +  "页</option>");
		    	}
		    	$("input[id^=picture_id1_]").click(function(){
		    		//alert($(this).attr("pictureid1"));
		    		//doCheckedOrUnchecked($(this).is(":checked"));
		    		doCheckedOrUnchecked($(this));
		    	});	
		    }
		    
		    
		});
		
});

String.prototype.startWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substr(0,str.length)==str)
	  return true;
	else
	  return false;
	return true;
}

/**
 * 点击选中/取消选中
 */
function doCheckedOrUnchecked(obj){
	var pid = $(obj).attr("pictureid1");
	var pnum = $('#pictureNumber').val();
	if($(obj).is(":checked")==true){
		$('#pictureNumber').val(pnum + pid + ",");
	}else{
		//alert("12345".indexOf('0'));
		//alert("12,3,456".substring(3, 6));//456
		
		if(pnum.startWith(pid + ",")){
			$('#pictureNumber').val(pnum.substring(pid.length + 1,pnum.length));
		}else{
			$('#pictureNumber').val(pnum.replace(','+pid+',' , ','));
		} 
		//alert(pnum.replace('2','3222'));
		//alert(pnum.length);
	}
	//$('#pictureNumber').val();
	//alert($(obj).is(":checked") + "123");
}

function setDefaultChecked(id,obj){
	var pnum = $('#pictureNumber').val();
	if(pnum.indexOf(','+ id +',') > -1 || pnum.startWith(id + ',')){
		//alert(this.id);
		$(obj).attr("checked","true");
	}
	//alert($('#picture_id1_' + this.id).attr("pictureid1"));
}
	

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
			<td class="biaoge_32" style="width: 15%"><span style="color: #F00">*</span>积分</td>
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
		
		
			<td class="biaoge_32">首页图片（ 300X216 px）</td>
			<td class="biaoge_33"><input type="text" class="text_3" name="indexPagePictureNumber" id="indexPagePictureNumber" value="${commodity.indexPagePictureNumber }" /></td>
			
			<td class="biaoge_32">图片编号（格式 12,13,15）</td>
			<td class="biaoge_33"><input type="text" class="text_3" name="pictureNumber" id="pictureNumber" value="${commodity.pictureNumber }" /></td>
			<!--  <td rowspan="2" class="biaoge_32">图片预览</td>
			<td rowspan="2" class="biaoge_33" ><a id="imgurl"  title= "点击查看大图" href="" target="_blank"><img  id="preview" width="80" height="80" src=""></a> </td>
			 -->
		</tr>
		
	    <tr>
	    	<td class="biaoge_32">选择图片（ 300X216 px）</td>
	    	<td class="biaoge_33"> 
	    	<input type="button" onclick="showProcForImageSelector();" value="选择图片"/>
	    	<button id="choose" data-toggle="modal" data-target="#myModal"> 选择 </button></td>
	    	
	    	<td class="biaoge_32">图片编号（格式 12,13,15）</td>
			<td class="biaoge_33"><button id="choose1" data-toggle="modal" data-target="#myModal1"> 选择 </button></td>
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
					CKEDITOR.editorConfig = function (config) {
						// 换行方式
						config.enterMode = CKEDITOR.ENTER_BR;
						// 当输入：shift+Enter是插入的标签
						config.shiftEnterMode = CKEDITOR.ENTER_BR;// 
						//图片处理
						config.pasteFromWordRemoveStyles = true;config.filebrowserImageUploadUrl = "ckUploadImage.action?type=image";
						// 去掉ckeditor“保存”按钮
						config.removePlugins = 'save';
					};
					
					CKEDITOR.replace( 'description' );
				</script>
			</div>
			</td>
		</tr>
		

		<tr>
			<th colspan="4" class="t_title t_c">
				<input name="btn01" id="sys_update_btn" type="button" onclick="save()" class="button_1" value="提 交" />
				&nbsp;&nbsp;&nbsp;&nbsp; <input
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















<script type="text/javascript">	
//显示
function showProcForImageSelector(){
	  message_box_for_image_selector.style.visibility='visible';
	  
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
//关闭
function closeProcForImageSelector(){
	  message_box_for_image_selector.style.visibility='hidden';
	  procbg.style.visibility = "hidden";
	  document.body.style.overflow = "auto";
}
</script>
   <div id="message_box_for_image_selector" style="position:absolute;left:4px;top:4px;width:1024px;height:415px;z-index:1000;visibility:hidden">
   		<div id="message" style="border:skyblue solid; border-width:1 1 3 1; width:95%; height:95%; background:#fff; color:#036; font-size:12px; line-height:150%;">
    		<!-- DIV弹出状态行：标题、关闭按钮 -->
    		<!-- <div style="background:skyblue; height:5%; font-family:Verdana, Arial, Helvetica, sans-serif; font-size:12px; padding:3 5 0 5; color:#fff" onmousedown="drag(message_box);return false"> -->
     		<div style="background:skyblue; height:5%; font-family:Verdana, Arial, Helvetica, sans-serif; font-size:12px; padding:3 5 0 5; color:#fff" >
     			<span style="display:inline;width:150px;position:absolute;font-size:200%"></span>
     			<span onclick="closeProcForImageSelector();" style="float:right;display:inline;cursor:pointer;font-size:200%">×</span>
    		</div>
    		<!-- 内容 begin -->
    		<div id="content">    		
        		<!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button> -->
        		<!-- <h4 class="modal-title" id="myModalLabel">图片选择</h4> -->
        		名称：<input type="text" name="queryName" id="queryname" />
            	<input type="button" id="searchBtn" value="筛选" />    		
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
    			<select name="pagenum" id="pagenum">    	
    				
    		</div>
    		<!-- 内容 end -->
   		</div><!-- message -->
  </div><!-- message_box -->


