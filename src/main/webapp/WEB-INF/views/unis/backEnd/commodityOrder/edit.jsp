<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<div class="weizhi">你的位置是：商城管理>> 物流商管理 >> 
<!-- key为true时新增，key为false时修改 -->
	<c:choose>
		<c:when test="${key eq true}">
		新增 物流商
		</c:when>
		<c:otherwise>
		编辑 物流商
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
				<span style="color: #F00">*</span>物流商名称
			</td>
			<td class="biaoge_33" style="width:35%" >
				<input type="text" class="text_3" name="name" id="name"  value="${entity.name }" maxlength="40"/>
			</td>

			<td class="biaoge_32" style="width:15%">
				<span style="color: #F00">*</span>物流查询网址
			</td>
			<td class="biaoge_33" style="width:35%" >
				<input type="text" class="text_4" name="path" id="path"  value="${entity.path }" maxlength="300" />		
			</td>
		</tr>
		<tr>	
			<td class="biaoge_32" style="width:15%">
				<span style="color: #F00">*</span>联系电话
			</td>
			<td class="biaoge_33" style="width:35%" >
				<input type="text" class="text_4" name="tel" id="tel"  value="${entity.tel }" maxlength="40" />
			</td>

			<td class="biaoge_32" style="width:15%">
				备注
			</td>
			<td class="biaoge_33" style="width:35%" >
				<textarea rows="3" cols="32" name="remark" id="remark"></textarea>	
			</td>
		</tr>
		<tr>	
			<td class="biaoge_32" style="width:15%">
				<span style="color: #F00">*</span>排序号
			</td>
			<td class="biaoge_33" style="width:35%">
				<input type="text" class="text_3" name="sortNumber" id="sortNumber"  value="${entity.sortNumber }" maxlength="4" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" />
			</td>
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
			<th colspan="4" class="t_title t_c">
				<shiro:hasPermission name="commodityLogistics:edit">
					<input type="button" class="button_3" id="save_btn" name="" value="保存" onclick="save();"/>			
					<input type="button" class="button_3" id="process_btn" name="" value="正在处理" style="display:none;" disabled/>		
				</shiro:hasPermission>	
					<input name="btn01" type="button" class="button_3" onclick="base.cancel('head','list','edit');" value="返 回" />
			</th>
		</tr>
	</table>
</form>