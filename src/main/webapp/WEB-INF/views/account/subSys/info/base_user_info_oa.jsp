	<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<input type="hidden" id="utype" value="${user.usertype }"/>
<input type="hidden" id="_typeId" value="${user.typeId}"/>

<form name="form1" id="form1" method="GET">
<input type="hidden" id="e_switch" name="e_switch" value="">
<input type="hidden" id="groupFlag" name="groupFlag" value="">
<input type="hidden" id="itemFlag" name="itemFlag" value="">
<input type="hidden" id="olddeptid" name="olddeptid" value="">
<input type="hidden" id="datalevel" name="datalevel" value="">
<input type="hidden" name="userpwd" id="userpwd" value="${user.initPassword }">
<input type="hidden" name="identifyCode" id="identifyCode" value="${user.identifyCode }"/>
<div class="blank5"></div>
<table width="100%" border="1" align="center" class="TableList">
  <tr>
      <td align="center" colspan="2" class="TableHeader" height="10">增加新成员</td>
  </tr>
  <tr>
    <td width="13%" align="right" class="TableLine1"><font color="red">*</font>用户角色：</td>
    <td align="left" class="TableLine2" style="width: 68%">
    <select name="roleid" id="roleid" class="SmallSelect">
		<option value="">--请选择--</option>
	</select></td>
  </tr>
  <tr>
	<td width="13%" align="right" class="TableLine1">功能角色：</td>
	<td align="left" class="TableLine2">
		<input name="funcroleids" type="hidden" id="funcroleids"/>
		<textarea name="funcrolenames" id="funcrolenames" class="SmallInput" style="width:500px;overflow-y:visible;" readonly="readonly" onclick="funcroleselect()"></textarea> 
		<a href="javascript:cleanSelect('funcroleids','funcrolenames')">清空</a>
	</td>
  </tr>
  <tr>
    <td width="13%" align="right" class="TableLine1"><font color="red">*</font>职位：</td>
    <td align="left" class="TableLine2" style="width: 68%">
    <select name="job" id="job" class="SmallSelect">
		<option value="">--请选择--</option>
    </select></td>
  </tr>
  <tr>
    <td width="13%" align="right" class="TableLine1"><font color="red">*</font>工作单位：</td>
    <td align="left" class="TableLine2" style="width: 68%" id="workJob">
	</td>
  </tr>
  <tr>
	<td width="13%" align="right" class="TableLine1">兼职部门：</td>
	<td align="left" class="TableLine2">
		<input name="parttimedeptids" type="hidden" id="parttimedeptids"/>
		<textarea name="parttimedeptnames" id="parttimedeptnames" class="SmallInput" style="width:500px;overflow-y:visible;" readonly="readonly" onclick="parttimedeptselect()"></textarea> 
		<a href="javascript:cleanSelect('parttimedeptids','parttimedeptnames')">清空</a>
	</td>
  </tr>
 <tr>
    <td width="13%" align="right" class="TableLine1"><font color="red">*</font>用户帐号：</td>
    <td align="left" class="TableLine2" style="width: 68%">
  	    ${user.loginName}
    	<input name="username" type="hidden" class="SmallInput" value="${user.loginName}" id="username" >
     </td>
  </tr>
 <tr>
    <td width="13%" align="right" class="TableLine1"><font color="red">*</font>真实姓名：</td>
    <td align="left" class="TableLine2" style="width: 68%">
    	${user.username }
		<input name="truename" id="truename" type="hidden" class="SmallInput" value="${user.username }">
     </td>
  </tr>
  <tr>
    <td width="13%" align="right" class="TableLine1">继教号：</td>
    <td align="left" class="TableLine2"><input name="teach_no" type="text" class="SmallInput" value="" id="teach_no" maxlength="50"></td>
  </tr>
  <tr>
    <td width="13%" align="right" class="TableLine1">身份证：</td>
    <td align="left" class="TableLine2"><input name="card_no" type="text" class="SmallInput" value="${user.IDCard}" id="card_no" maxlength="50"></td>
  </tr>
  <tr>
    <td width="13%" align="right" class="TableLine1"><font color="red">*</font>手机号码：</td>
    <td align="left" class="TableLine2"><input name="mobile" type="text" class="SmallInput" value="${user.mobile}" id="mobile" maxlength="50">
    	广东省以外的号码请在号码前加“0”
    </td>
  </tr>
  <tr>
    <td width="13%" align="right" class="TableLine1">座机：</td>
    <td align="left" class="TableLine2"><input name="phone" type="text" class="SmallInput" value="" maxlength="20">
      &nbsp;格式说明：对外号码(短号) </td>
  </tr>
  <tr>
    <td width="13%" align="right" class="TableLine1">电子邮箱：</td>
    <td align="left" class="TableLine2"><input name="email" type="text" class="SmallInput" id="email" value="${user.email}" maxlength="50"></td>
  </tr>
  <tr>
    <td width="13%" align="right" class="TableLine1">排序号：</td>
    <td align="left" class="TableLine2">
    	<input name="order_no" type="text" class="SmallInput" value="0" id="order_no" maxlength="10">
    	默认为“0”，排序号越大，在同单位内相同角色的排序显示越前。</td>
  </tr>
  <tr>
    <td height="35" colspan="2" align="center" class="TableLine2">
	<input name="btnSave" id="btnSave" type="button" value="保 存" class="SmallButtonB" onMouseOut="this.className='SmallButtonB'" onMouseOver="this.className='SmallButtonBHover'" onClick="save()">
    <input name="btnBack" id="btnBack" type="button" value="取 消" class="SmallButtonA" onMouseOut="this.className='SmallButtonA'" onMouseOver="this.className='SmallButtonAHover'" onClick="rtn()">
	<input name="importInfo" type="button" class="SmallButtonD" id="importInfo"  value="正在处理,请稍候......" style="display:none" disabled="disabled">
    </td>
  </tr>
</table>

<div class="blank5"></div>
</form>
