<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<div class="weizhi">你的位置是：商城管理>> 物流商管理 >> 查看

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
				<span style="color: #F00">*</span>物流商名称
			</td>
			<td class="biaoge_33" style="width:35%" >
				${entity.name }
			</td>
			<td class="biaoge_32" style="width:15%">
				<span style="color: #F00">*</span>物流查询网址
			</td>
			<td class="biaoge_33" style="width:35%" >
				<a href="${entity.path }" target="_blank">${entity.path }</a>	
			</td>
		</tr>
		<tr>	
			<td class="biaoge_32" style="width:15%">
				<span style="color: #F00">*</span>联系电话
			</td>
			<td class="biaoge_33" style="width:35%" >
				${entity.tel }
			</td>

			<td class="biaoge_32" style="width:15%">
				备注
			</td>
			<td class="biaoge_33" style="width:35%" >				
				<textarea rows="3" cols="32" name="remark" id="remark" readonly></textarea>	
			</td>
		</tr>
		<tr>	
			<td class="biaoge_32" style="width:15%">
				<span style="color: #F00">*</span>排序号
			</td>
			<td class="biaoge_33" style="width:35%">
				${entity.sortNumber }
			</td>
			<td class="biaoge_32" style="width:15%">
				<span style="color: #F00">*</span>状态
			</td>
			<td class="biaoge_33" style="width:35%">
				<c:if test="${entity.status eq 1 }">启用</c:if>
				<c:if test="${entity.status eq 0 }">停用</c:if>
			</td>
		</tr>	
		<tr>
			<th colspan="4" class="t_title t_c">
				<input name="btn01" type="button" class="button_3" onclick="base.cancel('head','list','edit');" value="返 回" />
			</th>
		</tr>
	</table>
</form>