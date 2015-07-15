<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>
.error {
	color: #ff0000;
}
 
.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>
<script type="text/javascript">
<!--
function menuChange()
{
	var varPermType = $("#permType").val();
	if(varPermType =='undefined' || varPermType =='0' || varPermType =='1')
	{
		$("#permValue").attr("value",'');
	}
	//alert(varPermType);
	//alert($(".menu option:selected").val());
	if(varPermType =='-1')
	{
		$("#permValue").attr("value",$(".menu option:selected").val());
	}
}
//-->
</script>
<div class="weizhi">你的位置是：权限管理>>添加</div>
<form:form commandName="permission" action="${ctx }/permission/save/${permission.id}"  >
<form:errors path="*" cssClass="errorblock" element="div" />
<input type="hidden" value="${currentIndex}" name="currentIndex"/>
<table class="biaoge_3" style="margin-top:10px;">
	<tr>
		<td class="biaoge_31" colspan="4">注：带"*"为必填项</td>
	</tr>
	<tr>
		<td class="biaoge_32" style="width:15%"><span style="color: #F00">*</span>权限名称</td>
		<td class="biaoge_33" style="width:35%"><form:input path="permName" cssClass="text_3"/><form:errors path="permName" cssClass="error" /></td>
		<td class="biaoge_32" style="width:15%">权限描述</td>
		<td class="biaoge_33" style="width:35%"><form:input path="descript" cssClass="text_3"/></td>
	</tr>
	<tr>
		<td class="biaoge_32"><span style="color: #F00">*</span>权限类型</td>
		<td class="biaoge_33">
			<form:select path="permType" cssStyle="width:120px;" >
					<option value="0">--请选择--</option>
					<option value="1" <c:if test="${permission.permType eq 1}">selected</c:if>>功能权限</option>
					<option value="-1" <c:if test="${permission.permType eq -1}">selected</c:if>>菜单权限</option>
			</form:select>
		</td>
		<td class="biaoge_32"><span style="color: #F00">*</span>权限所属菜单</td>
		<td class="biaoge_33"><form:select path="menu.id" items="${mlist}" itemLabel="menuName" itemValue="id" cssClass="menu" onchange="menuChange()"></form:select></td>
	</tr>
	<tr id="per_value">
		<td class="biaoge_32"><span style="color: #F00">*</span>权限值</td>
		<td class="biaoge_33"><form:input path="permValue" cssClass="text_3"/><form:errors path="permValue" cssClass="error" /></td>
			
		<td class="biaoge_32">&nbsp;</td>
		<td class="biaoge_33">&nbsp;</td>
	</tr>
	<tr>
		<td class="biaoge_34" colspan="4">
			<input type="submit" class="button_3" name="submit" value="保存" />
			<input type="button" class="button_3" name="" value="返回" onclick="history.back();"/>
		</td>
	</tr>
</table>
</form:form>