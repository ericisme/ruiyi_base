<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<script type="text/javascript" src="${ctx }/static/js/base/ajaxupload.js"></script>
<script src="${ctx }/static/fineuploader/fineuploader-3.3.1.js"></script>



<script type="text/javascript">
	$(document).ready(function(){
		picutureUploader();
/* 		base.initUpload(baseUrl + '/uploadTileImg','ubtn1','jpg,gif,jpeg,png,bmp',1,function(file,filename){
			alert("file:"+file);
			alert("filename:"+filename);
			//正常图标
			//file 原文件名称
			//filename 新生成的文件名称
			//$("#visIcon").val(filename);
			//$("#imgfile1").val(filename);
			//$("#img1").attr("src","${ctx}/upload/temp/" + filename +"?" + Math.round(Math.random() * 10000) );
			//$("#showImg1").show(500);
		}); */
		
	});
	
	function deleteFile(i){
		var id = $("#id").val() == "" ? "0" : $("#id").val();
		var filename = $("#imgfile" + i).val();
		base.request(baseUrl + "/deleteFile","id=" + id + "&filename=" + filename + "&imgType=" + i,function(result){
			if(result == "success"){
				$("#showImg" + i).hide(300);
				$("#ubtn" + i).show(300);
				if(i == 1){
					$("#visIcon").val("");
				}else if(i == 2){
					$("#unvisIcon").val("");
				}else{
					$("#highIcon").val("");
				}
			}else{
				base.tips("出现未知异常，操作失败!",'error');
			}
		},"POST","HTML");
	}
	
	function picutureUploader() {
	    var uploader = new qq.FineUploader({
	      element: document.getElementById('fine-uploader'),
	      text: {
	          uploadButton: '点击上传',
	      },
	      request: {
	        endpoint: baseUrl + '/uploadTileImg'
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
	    	  },
	    	  onComplete: function(id, name, responseJSON){
	    		  if(responseJSON.filePath!='faild'){
					$("#preview").attr("src",responseJSON.filePath);
					$("#titleImg").val(responseJSON.filePath);
	    		  }else{
	    			  base.tips("图片上传出现问题!", 'error');
	    		  }
	          }
	      }
	    });
	  }
	
	
</script>

<div class="weizhi">你的位置是：发布会邀请管理>> 大会新闻管理 >>
<!-- key为true时新增，key为false时修改 -->
	<c:choose>
		<c:when test="${key eq true}">
		新增新闻
		</c:when>
		<c:otherwise>
		编辑新闻
		</c:otherwise>
	</c:choose>
</div>

<form name="mainForm" id="mainForm">
	<table class="biaoge_3" style="margin-top:10px;">
		<tr>
			<td class="biaoge_31" colspan="4">
				基本信息     <font size="1">注：带 * 为必填项</font>
				<input type="hidden" name="id" id="id" value="${entity.id}"/>
			</td>
		</tr>
		<tr>
			<td class="biaoge_32" style="width:15%">
				<span style="color: #F00">*</span>中英文版本
			</td>
			<td class="biaoge_33" style="width:35%">
				<select name="formType" id="formType">
					<option value="21" <c:if test="${entity.formType eq 21 }">selected</c:if> >中文</option>
					<option value="11" <c:if test="${entity.formType eq 11 }">selected</c:if> >英文</option>
				</select>
			</td>
			<td class="biaoge_32" style="width:15%"  rowspan="7">
				标题图片地址<br>请用分辨率 200x150 的图片
			</td>
			<td class="biaoge_33" style="width:35%" rowspan="6">
				<input type="hidden" name="titleImg" id="titleImg" value="${entity.titleImg }" />
				<div id="fine-uploader"></div><div align="right"></div>
				<a href="${entity.titleImg }" class="fancybox" id="preview_for_fancybox"><img  id="preview" src="${entity.titleImg }"><br/>	</a>		
				<script>
        			$("#preview_for_fancybox").attr('rel', 'gallery').fancybox();
        			//$("a[href$='.jpg'],a[href$='.png'],a[href$='.gif']").attr('rel', 'gallery').fancybox();
        		</script>	
			</td>
		</tr>
		<tr>
			<td class="biaoge_32" style="width:15%">
				<span style="color: #F00">*</span>标题
			</td>
			<td class="biaoge_33" style="width:35%" >
				<input type="text" class="text_4" name="title" id="title"  value="${entity.title }" maxlength="50" /><font color="red"  id="tips"></font>
			</td>
		</tr>
		<tr>
			<td class="biaoge_32" style="width:15%">
				<span style="color: #F00">*</span>标签
			</td>
			<td class="biaoge_33" style="width:35%" >
				<select name="tag" id="tag">
					${tage_select_html }
				</select>
				<!--
				<input type="text" class="text_3" name="tag" id="tag"  value="${entity.tag }" maxlength="10" /><font color="red"  id="tips"></font>
				 -->
			</td>
		</tr>
		<tr>
			<td class="biaoge_32" style="width:15%">
				<span style="color: #F00">*</span>作者
			</td>
			<td class="biaoge_33" style="width:35%">
				<input type="text" class="text_3" name="author" id="author"  value="${entity.author }" maxlength="50" />
			</td>
		</tr>
		<tr>
			<td class="biaoge_32" style="width:15%">
				<span style="color: #F00">*</span>日期
			</td>
			<td class="biaoge_33" style="width:35%">
				<input type="text" class="text_3" name="addTime_date_str" id="addTime_date_str"
					value="<fmt:formatDate value="${entity.addTime}" pattern="yyyy-MM-dd" />"
					size="65" readonly title="点击选择日期"
					onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"/>
			</td>
		</tr>
		<tr>
			<td class="biaoge_32" style="width:15%">
				<span style="color: #F00">*</span>排序号
			</td>
			<td class="biaoge_33" style="width:35%">
				<input type="text" class="text_3" name="sortNumber" id="sortNumber"  value="${entity.sortNumber }" maxlength="4" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" />
			</td>
		</tr>
		<tr>
			<td class="biaoge_32" style="width:15%">
				<span style="color: #F00">*</span>状态
			</td>
			<td class="biaoge_33" style="width:35%">
				<select name="status" id="status">
					<option value="1" <c:if test="${entity.status eq 1 }">selected</c:if> >启用</option>
					<option value="0" <c:if test="${entity.status eq 0 }">selected</c:if> >停用</option>
				</select>
			</td>
		</tr>
		<tr>

		</tr>
		<tr>
			<td class="biaoge_31" colspan="4">
				新闻内容
			</td>
		</tr>
		<tr>
			<td class="biaoge_33" style="width:35%" colspan="4" style="width:100px;">
			<div style="width:800px;background-color:#ccc;margin:auto;">
				<textarea cols="80" id="content" name="content" rows="10">${entity.content }</textarea>
				<script>
					CKEDITOR.replace( 'content', {
						pasteFromWordRemoveStyles : true,
						filebrowserImageUploadUrl : baseUrl+'/uploadImg?type=image'
					});
				</script>
			</div>
			</td>
		</tr>

		<tr>
			<th colspan="4" class="t_title t_c">
				<shiro:hasPermission name="invitationNews:edit">
					<input type="button" class="button_3" id="save_btn" name="" value="保存" onclick="save();"/>
					<input type="button" class="button_3" id="process_btn" name="" value="正在处理" style="display:none;" disabled/>
				</shiro:hasPermission>
					<input name="btn01" type="button" class="button_3" onclick="base.cancel('head','list','edit');" value="返 回" />
			</th>
		</tr>
	</table>
</form>
