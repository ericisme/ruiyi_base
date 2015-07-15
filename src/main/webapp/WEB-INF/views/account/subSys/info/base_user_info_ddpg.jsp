<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<br/>
<input type="hidden" id="gender" value="${user.gender }"/>
<input type="hidden" id="utype" value="${user.usertype }"/>
<input type="hidden" id="_xx_id" value="${user.school.id }"/>
<input type="hidden" id="_dw_id" value="${user.unit.id }"/>
<form name="formAdd" id="formAdd" method="get" action="/ddpg/saveUser.action" onsubmit="return checkForm();" >
<input type="hidden" id="schoolid" name="schoolid" value="" />
<input type="hidden" id="opt" name="opt" value="add" />
<input name="loginPassword" id="loginPassword" type="hidden" value="${user.initPassword }"/>
<input type="hidden" name="identifyCode" id="identifyCode" value="${user.identifyCode }"/>
<table width="100%" align="center" cellspacing="0" cellpadding="0" bordercolor="#AAC7DD" border="1">
	<tr valign="middle">
	    <th style="text-align:left;height:30px;" class="t_title" colspan='4'></th>
	</tr>
	<tr>
		<td class="t_title t_r" width="15%">登录名称：</td>
		<td class="t_right" width="35%">
			${user.loginName }
			<input name="loginName" id="loginName" type="hidden" value="${user.loginName }"/>
		</td>
		<td class="t_title t_r" width="15%">中文名称：</td>
		<td class="t_right" width="35%">
			${user.username }
			<input name="name" id="name" type="hidden" maxlength="100" size="30" value="${user.username }"/>
		</td>
	</tr>
	<tr>
		<td class="t_title t_r">地 区：</td>
		<td class="t_right" id="txt_area">
			<!-- 
			<select name="area" id="area" onchange="AjaxSelectArea(this);">
					<option value="" >--请选择--</option>
		    </select>
		     -->
		</td>
		<td class="t_title t_r" width="10%">学 校： </td>
		<td class="t_right" width="20%" id="txt_school">
			<!-- 
			<select name="schools" id="schools">
					<option value="">-请选择-</option>
			</select>
			 -->
		</td>
	</tr>
	<tr>
		<td class="t_title t_r" width="10%">角 色 ：</td>
		<td class="t_right" width="20%">
			<select name="roleId" id="roleId">
					<option value="">-请选择-</option>
			</select>
		</td>
		<td class="t_title t_r" width="10%"> </td>
		<td class="t_right" width="20%">
		</td>
	</tr>
</table>

<table width="70%" border="0" align="center" cellspacing="0" cellpadding="0">
	<tr>
		<td height="5">&nbsp;</td>
	</tr>
	<tr>
		<td height="5" align="center">
			<input name="submitBtn" id="save_btn" type="submit" class="sort_sub" value="提 交" />&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
</table>
</form>
