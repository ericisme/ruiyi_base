<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script src="${ctx }/static/fineuploader/fineuploader-3.3.1.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	picutureUploader();
});
var _lastPic = "${picture.previewUrl }";
function picutureUploader() {
    var uploader = new qq.FineUploader({
      element: document.getElementById('fine-uploader'),
      text: {
          uploadButton: '点击上传',
      },
      request: {
        endpoint: '/backEnd/picture/uploadPicture'
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

    		  if((responseJSON.status=='success')){
    			  $("#preview").attr("src",responseJSON.previewUrl);
    			  $('#previewLargeUrl').val(responseJSON.previewLargeUrl);
    				$("#previewUrl").val(responseJSON.previewUrl);
    				$("#url").val(responseJSON.url);
    				 $("#viewbigimage").attr("href",responseJSON.url);
    				 _lastPic = responseJSON.previewUrl;
    		  }else{
    			  base.tips("图片上传出现问题!", 'error');
    			  $('#previewLargeUrl').val("error");
    			  $("#previewUrl").val("error");
    			  $("#url").val( "error");
    		  }
          }
      }
    });
  }
</script>

<div class="weizhi">
	你的位置是：商城管理 >> 图片管理 >>
	<c:choose>
		<c:when test="${picture.id eq 0}">
		新增图片
		</c:when>
		<c:otherwise>
		编辑图片
		</c:otherwise>
	</c:choose>
</div>
<form name="mainForm" id="mainForm" >
	<input type="hidden" name="id" id="id" value="${picture.id }" />
	<input type="hidden" name="url" id="url" value="${picture.url }"/>
	<input type="hidden" name="previewUrl" id="previewUrl" value="${picture.previewUrl }"/>
	<input type="hidden" name="previewLargeUrl" id="previewLargeUrl" value="${picture.previewLargeUrl }"/>
	<table class="biaoge_3" style="margin-top: 10px;">
		<tr>
			<td class="biaoge_31" colspan="4">注：带"*"为必填项</td>
		</tr>
		<tr>
			<td class="biaoge_32" style="width: 15%"><span style="color: #F00">*</span>名字</td>
			<td class="biaoge_33" style="width: 35%"><input type="text" class="text_3" name="name" id="name" value="${picture.name }"/></td>
			<td class="biaoge_32"><span style="color: #F00">*</span>类型</td>
			<td class="biaoge_33">
				<select name="type">
					${type }
				</select>
			</td>
		</tr>
		<tr>
			<td class="biaoge_32">状态</td>
			<td class="biaoge_33">
				<input type="radio" id="status" name="status" value="1" checked="checked" />启用&nbsp;&nbsp;
				<input type="radio" id="status" name="status" value="0" <c:if test="${picture.status==0 }">checked="checked" </c:if>/>停用&nbsp;&nbsp;
			</td>
			<td rowspan="2" class="biaoge_32">图片预览</td>
			<td rowspan="2" class="biaoge_33" ><a id="viewbigimage"  title= "点击查看大图" href="${picture.url }" target="_blank"><img  id="preview" width="80" height="80" src="${picture.previewUrl }"></a> </td>
		</tr>
		<tr>
			<td class="biaoge_32"><span style="color: #F00">*</span>图片</td>
			<td class="biaoge_33">
			 <div id="fine-uploader"></div>
			 <div align="right"></div>
			</td>

		</tr>

		<tr>
			<th colspan="4" class="t_title t_c">
			<shiro:hasPermission name="picture:edit">

				<input name="btn01" id="sys_update_btn" type="button" onclick="save()" class="button_1" value="提 交" />&nbsp;&nbsp;&nbsp;&nbsp;
				</shiro:hasPermission>
				 <input name="btn01" type="button" class="button_1" onclick="base.cancel('head','list','edit',pictureQuery);" value="返 回" />
			</th>
		</tr>
	</table>
</form>