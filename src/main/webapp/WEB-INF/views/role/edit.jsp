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
<div class="weizhi">你的位置是：角色管理>>添加</div>
<form:form commandName="role" action="${ctx }/role/save/${role.id}"  >
<form:errors path="*" cssClass="errorblock" element="div" />
<input type="hidden" value="${currentIndex}" name="currentIndex"/>
<table class="biaoge_3" style="margin-top:10px;">
	<tr>
		<td class="biaoge_31" colspan="4">注：带"*"为必填项</td>
	</tr>
	<tr>
		<td class="biaoge_32" style="width:15%"><span style="color: #F00">*</span>角色名称</td>
		<td class="biaoge_33" style="width:35%"><form:input path="name" cssClass="text_3"/><form:errors path="name" cssClass="error" /></td>
		<td class="biaoge_32" style="width:15%">角色描述</td>
		<td class="biaoge_33" style="width:35%"><form:input path="description" cssClass="text_3"/></td>
	</tr>
	<tr>
		<td class="biaoge_32"><span style="color: #F00">*</span>角色状态</td>
		<td class="biaoge_33">
			<form:select path="status" cssStyle="width:120px;" >
					<option value="0">--请选择--</option>
					<option value="1" <c:if test="${role.status eq 1}">selected</c:if>>正常</option>
					<option value="-1" <c:if test="${role.status eq -1}">selected</c:if>>停用</option>
			</form:select>
		</td>
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