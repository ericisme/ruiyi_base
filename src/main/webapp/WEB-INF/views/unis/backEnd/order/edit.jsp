<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script src="${ctx }/static/fineuploader/fineuploader-3.3.1.js"></script>
<script type="text/javascript">
$(document).ready(
		function(){
			picutureUploader();
		}
);
var _lastPic = "${ppt.previewUrl }";
function picutureUploader() {
    var uploader = new qq.FineUploader({
      element: document.getElementById('fine-uploader'),
      text: {
          uploadButton: '点击上传',
      },
      request: {
        endpoint: '/backEnd/ppt/uploadPicture2'
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
    		  $("#url").val("uploading");
    	  },
    	  onComplete: function(id, name, responseJSON){
    		 
    		  if(!(responseJSON.status=='error')){
    			  $("#preview").attr("src",responseJSON.previewUrl);
    				$("#previewUrl").val(responseJSON.previewUrl);
    				$("#url").val(responseJSON.url);
    				 $("#imgurl").attr("href",responseJSON.url);
    				 _lastPic = responseJSON.previewUrl;
    		  }else{
    			  base.tips("图片上传出现问题!", 'error');
    			  $("#previewUrl").val("error");
    			  $("#url").val( "error");
    		  }
        	  
          }
      }
    });
  }
</script>

<div class="weizhi">
	你的位置是：主页管理 >> 幻灯片管理 >>
	<c:choose>
		<c:when test="${ppt.id eq 0}">
		新增幻灯片
		</c:when>
		<c:otherwise>
		编辑幻灯片
		</c:otherwise>
	</c:choose>
</div>
<form name="mainForm" id="mainForm" enctype="multipart/form-data" method="post" action="/backEnd/ppt/save">
	<input type="hidden" name="id" id="id" value="${ppt.id }" />
	<input type="hidden" name="url" id="url" value="${ppt.url }"/>
	<input type="hidden" name="previewUrl" id="previewUrl" value="${ppt.previewUrl }"/>
	<table class="biaoge_3" style="margin-top: 10px;">
		<tr>
			<td class="biaoge_31" colspan="4">注：带"*"为必填项</td>
		</tr>
		<tr>
			<td class="biaoge_32" style="width: 15%"><span style="color: #F00">*</span>名字</td>
			<td class="biaoge_33" style="width: 35%"><input type="text" class="text_3" name="pictrueName" id="pictrueName" value="${ppt.pictrueName }"/></td>
			<td class="biaoge_32" style="width: 15%"><span style="color: #F00">*</span>排序号</td>
			<td class="biaoge_33" style="width: 35%"> <input type="text" class="text_3" name="sortNumber" id="sortNumber" value="${ppt.sortNumber }" /> </td>
		</tr>
		<tr>
			<td class="biaoge_32">状态</td>
			<td class="biaoge_33">
				<input type="radio" id="status" name="status" value="1" checked="checked" />启用&nbsp;&nbsp;
				<input type="radio" id="status" name="status" value="0" <c:if test="${ppt.status==0 }">checked="checked" </c:if>/>停用&nbsp;&nbsp;
			</td>
			<td class="biaoge_32"><span style="color: #F00"></span>内容</td>
			<td class="biaoge_33"><textarea id="content" name="content" rows="5" cols="20" >${ppt.content }</textarea></td>
		</tr>
		<tr>
			<td class="biaoge_32"><span style="color: #F00">*</span>文章地址</td>
			<td class="biaoge_33"> <input type="text" class="text_3" name="articleUrl" id="articleUrl" value="${ppt.articleUrl }" /> </td>
			<td rowspan="2" class="biaoge_32">图片预览</td>
			<td rowspan="2" class="biaoge_33" ><a id="imgurl"  title= "点击查看大图" href="${ppt.url }" target="_blank"><img  id="preview" width="80" height="80" src="${ppt.previewUrl }"></a> </td>
			
		</tr>
		<tr>
			<td class="biaoge_32"><span style="color: #F00">*</span>图片</td>
			<td class="biaoge_33"><!-- <input type="file" id="pic" name="pic" onchange="picChange();"/>  -->
			 <div id="fine-uploader"></div>
			 <div align="right"></div>
			</td>
			
		</tr>
		
		

		<tr>
			<th colspan="4" class="t_title t_c">
			<shiro:hasPermission name="ppt:edit">
				<input name="btn01" id="sys_update_btn" type="button" onclick="save()" class="button_1" value="提 交" />
			</shiro:hasPermission>
				&nbsp;&nbsp;&nbsp;&nbsp; <input
				name="btn01" type="button" class="button_1"
				onclick="base.cancel('head','list','edit',pptQuery);" value="返 回" />
			</th>
		</tr>
	</table>
</form>