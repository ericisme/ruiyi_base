<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<input type="hidden" id="gender" value="${user.gender }"/>
<input type="hidden" id="utype" value="${user.usertype }"/>
<input type="hidden" id="_xx_id" value="${user.school.id }"/>
<input type="hidden" id="_dw_id" value="${user.unit.id }"/>
<form name="userUpdateForm" id="userUpdateForm" method="POST" action="" onsubmit="return save();">
	<input name="typeId" id="typeId" type="hidden" value="${user.typeId }">
	<input name="userType" id="userType" type="hidden" value="1">
	<input name="nj_ids" id="nj_ids" type="hidden" >
	<input name="bj_ids" id="bj_ids" type="hidden" >
	<input name="loginPassword" id="loginPassword" type="hidden" value="${user.password }"/>
	<input name="identifyCode" id="identifyCode" type="hidden" value="${user.identifyCode }"/>
	<table width="100%" cellspacing="0" cellpadding="1" border="1" bordercolor="#AAC7DD">
		<tr>
			<th colspan="4" class="t_title t_tip">注： 带“*”为必填项</th>
		</tr>
		<tr>
			<td class="t_title t_r" width="10%"><font color="red"><B>*</B></font> 真实姓名： </td>
			<td class="t_right" width="40%"><input name="realName" id="realName" type="hidden" value="${user.username }"/>${user.username }</td>
			<td class="t_title t_r" width="10%"><font color="red"><B>*</B></font> 登陆名称：</td>
			<td class="t_right" width="40%"><input name="loginName" id="loginName" type="hidden"  value="${user.loginName }" />${user.loginName }</td></tr>
		<tr>
			<td class="t_title t_r" width="10%""> 性别： </td>
			<td class="t_right" width="40%"><input name="man" id="man" type="radio" value="true" checked="checked"/> 男<input name="man" id="woman" type="radio" value="false"/> 女</td>
			<td class="t_title t_r" width="10%">出生日期： </td>
			<td class="t_right" width="40%"><input name="birthday" id="birthday" type="text" size="30" class="form_txt" value="${user.birthday }" readonly/></td></tr>
		<tr>
			<td class="t_title t_r" width="10%"> 手机号码： </td>
			<td class="t_right" width="40%"><input name="mobile" id="mobile" type="text" value="${user.mobile }" maxlength="11" size="30" class="form_txt" /></td>
			<td class="t_title t_r" width="10%"> 电子邮件： </td>
			<td class="t_right" width="40%"><input name="email" id="email" type="text" maxlength="100" value="${user.email }" size="30" class="form_txt" /></td>
		</tr>
		
		<c:choose>
			<c:when test="${user.usertype eq 2}">
				<tr>
					<td class="t_title t_r" width="10%"><font color="red"><B>*</B></font> 用户类型： </td>
					<td class="t_right" width="40%"><input name="tu" id="JBRadio" type="radio" value=‘2’ checked/> 教办用户</td>
					<td class="t_title t_r" width="10%"><font color="red"><B>*</B></font>机构： </td>
					<td class="t_right" width="40%" id="dw_name"></td>
				</tr>
			</c:when>
			<c:otherwise>
				<tr>
					<td class="t_title t_r" width="10%"><font color="red"><B>*</B></font> 用户类型： </td>
					<td class="t_right" width="40%"><input name="tu" id="typeRadio" type="radio" value=‘1’ checked /> 学校用户</td>
					<td class="t_title t_r" width="10%">机构： </td>
					<td class="t_right" width="40%" id="dw_name"></td>
				</tr>
			</c:otherwise>
		</c:choose>
		
		<tr id="trXx">
			<td class="t_title t_r" width="10%"><font color="red"><B>*</B></font>学校： </td>
			<td class="t_right" width="40%" id="xx_name"></td>
			<td class="t_title t_r" width="10%"><font color="red"><B>*</B></font> 用户角色： </td>
			<td class="t_right" width="40%">
				<select name="roleIds" id="roleIds" onchange="initGradeSelect()"></select>
			</td>
		</tr>
		<tr id="gradesTr" style="display:none">
			<td width="10%" class="t_title t_r" ><font style="color: red">*</font> 选择年级： </td>
			<td width="90%" class="t_right" colspan=3>
				<input type="button" name="cxflBut" id="cxflBu" class="long_sub"value="选择年级" onclick="selectGrade(this)" style="cursor:hand"/>
				<span id="jobCxflSelectorShow" style="display:none;">
				</span>
			</td>
		</tr>
		<tr id="classTr" style="display:none">
			<td width="10%" class="t_title t_r" ><font style="color: red">*</font> 选择班级： </td>
			<td width="40%" class="t_right"><input type="button" name="cxflBut2" id="cxflBu2" class="long_sub"value="选择班级" onclick="selectBj(this)" style="cursor:hand"/><span id="bjSelectorShow" style="display:none;"></span></td>
			<td class="t_title t_r" width="10%"><font color="red"><B>*</B></font> 科目： </td>
			<td class="t_right" width="40%">
				<select name="km_id" id="km_id">
					<option value="0">--请选择--</option>
				</select>
			</td>
		</tr>
		<tr>
			<th colspan="4" class="t_title t_c"><input name="btn01" id="sys_update_btn" type="submit" class="sort_sub" value="提 交" />&nbsp;&nbsp;&nbsp;&nbsp;</th>
		</tr>
	</table>
</form>
<div id="showhtml" style="display:none"></div>
<div id="showhtml2" style="display:none"></div>

