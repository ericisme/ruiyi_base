<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<div class="weizhi">你的位置是：主页管理>> 码表管理 >> 查看记录
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
				<span style="color: #F00">*</span>类型
			</td>
			<td class="biaoge_33" style="width:35%" colspan="3">
				${entity.mtype }				
			</td>
		</tr>
		<tr>	
			<td class="biaoge_32" style="width:15%">
				<span style="color: #F00">*</span>名称
			</td>
			<td class="biaoge_33" style="width:35%">
				${entity.mkey }
			</td>
			<td class="biaoge_32" style="width:15%">
				<span style="color: #F00">*</span>码值
			</td>
			<td class="biaoge_33" style="width:35%">
				${entity.mvalue }
			</td>
		</tr>
		<tr>	
			<td class="biaoge_32" style="width:15%">
				<span style="color: #F00">*</span>排序号
			</td>
			<td class="biaoge_33" style="width:35%">
				${entity.orderNum }				
			</td>
			<td class="biaoge_32" style="width:15%">
				<span style="color: #F00">*</span>状态
			</td>
			<td class="biaoge_33" style="width:35%">
				<c:if test="${entity.datelevel eq 1 }">启用</c:if>
				<c:if test="${entity.datelevel eq 0 }">停用</c:if>
			</td>
		</tr>	
		<tr>
			<th colspan="4" class="t_title t_c">
				<input name="btn01" type="button" class="button_3" onclick="base.cancel('head','list','edit');" value="返 回" />
			</th>
		</tr>
	</table>
</form>