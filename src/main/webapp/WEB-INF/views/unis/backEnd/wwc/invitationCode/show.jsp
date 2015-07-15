<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<div class="weizhi">你的位置是：发布会邀请管理>> 邀请码管理 >> 查看
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
				<span style="color: #F00">*</span>名字
			</td>
			<td class="biaoge_33" style="width:35%" >
				${entity.name }
			</td>

			<td class="biaoge_32" style="width:15%">
				公司
			</td>
			<td class="biaoge_33" style="width:35%" >
				${entity.company }	
			</td>
		</tr>
		<tr>	
			<td class="biaoge_32" style="width:15%">
				电话
			</td>
			<td class="biaoge_33" style="width:35%" >
				${entity.tel }
			</td>
			<td class="biaoge_32" style="width:15%">
				电邮(email)
			</td>
			<td class="biaoge_33" style="width:35%" >
				${entity.email }
			</td>
		</tr>
		<tr>	
			<td class="biaoge_32" style="width:15%">
				备注
			</td>
			<td class="biaoge_33" style="width:35%" >
				<textarea rows="3" cols="32" name="remark" id="remark" readonly>${entity.remark }</textarea>	
			</td>
			<td class="biaoge_32" style="width:15%">
				<span style="color: #F00">*</span>邀请码
			</td>
			<td class="biaoge_33" style="width:35%" >
						 <font color="red">${entity.code }</font>
			</td>
		</tr>


		<tr>
			<th colspan="4" class="t_title t_c">
				<input name="btn01" type="button" class="button_3" onclick="base.cancel('head','list','edit');" value="返 回" />
			</th>
		</tr>
	</table>
</form>