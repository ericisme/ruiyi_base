<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<script type="text/javascript" src="${ctx }/static/js/base/ajaxupload.js"></script>

<script type="text/javascript">
<!--
	$(document).ready(function(){
		base.initUpload(baseUrl + '/upload','ubtn1','jpg,gif,jpeg,png,bmp',1,function(file,filename){
			//正常图标
			//file 原文件名称
			//filename 新生成的文件名称
			$("#visIcon").val(filename);
			$("#imgfile1").val(filename);
			$("#img1").attr("src","${ctx}/upload/temp/" + filename +"?" + Math.round(Math.random() * 10000) );
			$("#showImg1").show(500);
		});
		
		base.initUpload(baseUrl + '/upload','ubtn2','jpg,gif,jpeg,png,bmp',1,function(file,filename){
			//小图标
			//file 原文件名称
			//filename 新生成的文件名称
			$("#unvisIcon").val(filename);
			$("#imgfile2").val(filename);
			$("#img2").attr("src","${ctx}/upload/temp/" + filename +"?" + Math.round(Math.random() * 10000));
			$("#showImg2").show(500);
		});
		
		base.initUpload(baseUrl + '/upload','ubtn3','jpg,gif,jpeg,png,bmp',1,function(file,filename){
			//小图标
			//file 原文件名称
			//filename 新生成的文件名称
			$("#highIcon").val(filename);
			$("#imgfile3").val(filename);
			$("#img3").attr("src","${ctx}/upload/temp/" + filename +"?" + Math.round(Math.random() * 10000));
			$("#showImg3").show(500);
		});
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
//-->
</script>

<div class="weizhi">你的位置是：系统管理 >> 子系统管理 >> 编辑子系统</div>
<form name="mainForm" id="mainForm">
	<input type="hidden" name="id" id="id" value="${subSys.id }"/>
	<table class="biaoge_3" style="margin-top:10px;">
		<tr>
			<td class="biaoge_31" colspan="4">注：带"*"为必填项</td>
		</tr>
		<tr>
			<td class="biaoge_32" style="width:15%"><span style="color: #F00">*</span>子系统名称</td>
			<td class="biaoge_33" style="width:35%"><input type="text" class="text_3" name="subName" id="subName" value="${subSys.subName }" /></td>
			<td class="biaoge_32" style="width:15%"><span style="color: #F00">*</span>子系统标识</td>
			<td class="biaoge_33" style="width:35%"><input type="text" class="text_3" name="identifyCode" id="identifyCode" value="${subSys.identifyCode }" /></td>
		</tr>
		<tr>
			<td class="biaoge_32">排序号</td>
			<td class="biaoge_33">
				<c:choose>
					<c:when test="${empty subSys.orderNum }">
						<input type="text" class="text_3" name="orderNum" id="orderNum" value="${pageNum }"  onkeydown="base.digitOnly();"/>
					</c:when>
					<c:otherwise>
						<input type="text" class="text_3" name="orderNum" id="orderNum" value="${subSys.orderNum }"  onkeydown="base.digitOnly();"/>
					</c:otherwise>
				</c:choose>
			</td>
			<td class="biaoge_32"><span style="color: #F00">*</span>状态</td>
			<td class="biaoge_33">
				<select name="status" style="width:120px;" id="status">
						<option value="-1">请选择...</option>
						<option value="1" <c:if test="${subSys.status eq 1}">selected</c:if>>正常</option>
						<option value="0" <c:if test="${subSys.status eq 0}">selected</c:if>>停用</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="biaoge_32"><span style="color: #F00">*</span>系统访问地址</td>
			<td class="biaoge_33">
				<c:choose>
					<c:when test="${empty subSys.mainpage }">
							<input type="text" class="text_4" name="mainpage" id="mainpage" value="http://" />
						</c:when>
						<c:otherwise>
							<input type="text" class="text_4" name="mainpage" id="mainpage" value="${subSys.mainpage }" />
					</c:otherwise>
				</c:choose>
			</td>
			<td class="biaoge_32">正常图标</td>
			<td class="biaoge_33">
				<span id="showImg1" <c:if test="${empty subSys.visIcon }">style="display:none;"</c:if> ><img src="${ctx }/upload/system/icon/${subSys.visIcon }" id="img1" style="width:80;height:80px;border:2 solid #c3c6c9;"/>&nbsp;&nbsp;<a href="javascript:void(0);" onclick="deleteFile(1);">重新上传</a></span>
				<input type="button" class="button_1" name="ubtn1" id="ubtn1" value="上传图标" <c:if test="${not empty subSys.visIcon }">style="display:none;"</c:if>/>
				<input type="hidden" id="visIcon" name="visIcon" value="${subSys.visIcon }"/>
				<input type="hidden" id="imgfile1" name="imgfile1" value="${subSys.visIcon }"/>
			</td>
		</tr>
		<tr>
			<td class="biaoge_32">高亮图标</td>
			<td class="biaoge_33">
				<span id="showImg3" <c:if test="${empty subSys.highIcon }">style="display:none;"</c:if> ><img src="${ctx }/upload/system/icon/${subSys.highIcon }" id="img3" style="width:80;height:80px;border:2 solid #c3c6c9;"/>&nbsp;&nbsp;<a href="javascript:void(0);" onclick="deleteFile(3);">重新上传</a></span>
				<input type="button" class="button_1" name="ubtn3" id="ubtn3" value="上传图标" <c:if test="${not empty subSys.highIcon }">style="display:none;"</c:if>/>
				<input type="hidden" id="highIcon" name="highIcon" value="${subSys.highIcon }"/>
				<input type="hidden" id="imgfile3" name="imgfile3" value="${subSys.highIcon }"/>
			</td>
			<td class="biaoge_32">灰色图标</td>
			<td class="biaoge_33">
				<span id="showImg2" <c:if test="${empty subSys.unvisIcon }">style="display:none;"</c:if> ><img src="${ctx }/upload/system/icon/${subSys.unvisIcon }" id="img2" style="width:80;height:80px;border:2 solid #c3c6c9;"/>&nbsp;&nbsp;<a href="javascript:void(0);" onclick="deleteFile(2);">重新上传</a></span>
				<input type="button" class="button_1" name="ubtn2" id="ubtn2" value="上传图标" <c:if test="${not empty subSys.unvisIcon }">style="display:none;"</c:if> />
				<input type="hidden" id="unvisIcon" name="unvisIcon" value="${subSys.unvisIcon }"/>
				<input type="hidden" id="imgfile2" name="imgfile2" value="${subSys.unvisIcon }"/>
			</td>
		</tr>
		<tr>
			<td class="biaoge_34" colspan="4">
				<shiro:user>
						<shiro:hasPermission name="subSys:save">
							<input type="button" class="button_3" id="save_btn" name="" value="保存" onclick="save();"/>
						</shiro:hasPermission>
					<input type="button" class="button_3" id="process_btn" name="" value="正在处理" style="display:none;" disabled/>
					<input type="button" class="button_3" name="" value="返回" onclick="base.cancel('head','list','edit');"/>
				</shiro:user>
			</td>
		</tr>
	</table>
</form>