<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div class="weizhi">你的位置是：系统管理 >> 用户管理 >> 
<!-- key为true时新增，key为false时修改 -->
	<c:choose>
		<c:when test="${key eq true}">
		新增用户
		</c:when>
		<c:otherwise>
		编辑用户
		</c:otherwise>
	</c:choose>
</div>
<form name="mainForm" id="mainForm">
	<input type="hidden" name="id" id="id" value="${user.id }"/>
	<table class="biaoge_3" style="margin-top:10px;">
		<tr>
			<td class="biaoge_31" colspan="4">注：带"*"为必填项</td>
		</tr>
		<tr>
			<td class="biaoge_32" style="width:15%"><span style="color: #F00">*</span>登录名称</td>
			<c:choose>
			<c:when test="${key eq true}">
			<td class="biaoge_33" style="width:35%"><input type="text" class="text_3" name="loginName" id="loginName" /><font color="red"  id="tipOne"></font>（字母、数字及下划线，不包含中文）</td>
			</c:when>
			<c:otherwise>
			<td class="biaoge_33" style="width:35%"><input type="text" class="text_3" name="loginName" id="loginName"  readonly="readonly"   value="${user.loginName }" /></td>
			</c:otherwise>
			</c:choose>			
			<td class="biaoge_32" style="width:15%"><span style="color: #F00">*</span>真实姓名</td>
			<td class="biaoge_33" style="width:35%"><input type="text" class="text_3" name="username" id="username"  value="${user.username }" /><font color="red"  id="tips"></font></td>
		</tr>
		<tr>
			<td class="biaoge_32">性别</td>
			<td class="biaoge_33">
			<c:choose>
			<c:when test="${user.gender eq 0}">
			<input type="radio" id="man" name="gender" value="1"/>男&nbsp;&nbsp;
			<input type="radio" id="woman" name="gender" checked="checked" value="0"/>女&nbsp;&nbsp;
			</c:when>
			<c:otherwise>
			<input type="radio" id="man" name="gender" checked="checked" value="1"/>男&nbsp;&nbsp;
			<input type="radio" id="woman" name="gender" value="0"/>女&nbsp;&nbsp;
			</c:otherwise>
			</c:choose>			
			</td>
			<td class="biaoge_32"><span style="color: #F00"></span>出生日期</td>
			<td class="biaoge_33">
				<input type="text" name="birth" id="birth" value="<fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/>" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"/>&nbsp;&nbsp;<img onclick="WdatePicker({el:$dp.$('birth'),startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" src="${ctx }/static/My97DatePicker/skin/datePicker.gif" width="22" height="32" style="cursor:hand;vertical-align: middle" title="点击选择日期">
			</td>
		</tr>
		<c:if test="${key eq true}">
		<tr>
			<td class="biaoge_32"><span style="color: #F00">*</span>登录密码</td>
			<td class="biaoge_33">
				<input type="password" name="initPassword" id="initPassword" /><font color="red"  id="tipTwo"></font>
			</td>
			<td class="biaoge_32"><span style="color: #F00">*</span>确认密码</td>
			<td class="biaoge_33">
				<input type="password" name="confpas" id="confpas" /><font color="red"  id="tipThree"></font>
			</td>
		</tr>
		</c:if>
		<tr>
			<td class="biaoge_32"><span style="color: #F00"></span>手机号码</td>
			<td class="biaoge_33">
				<input type="text" name="mobile" id="mobile" value="${user.mobile}" /><font color="red"  id="tipFour"></font>
			</td>
			<td class="biaoge_32"><span style="color: #F00"></span>电子邮件</td>
			<td class="biaoge_33">
				<input type="text" name="email" id="email" value="${user.email}" /><font color="red"  id="tipFive"></font>
			</td>
		</tr>

		<tr>
			<td class="biaoge_32">用户类型</td>
			<td class="biaoge_33">			
			<c:if test="${user.usertype eq 1}">
				<input type="radio" id="XX" name="usertype" value="1" onclick="changeUserType(this.value)" checked="checked"/>后台用户&nbsp;&nbsp;
				<input type="radio" id="JG" name="usertype" value="2" onclick="changeUserType(this.value)"/>游乐场用户&nbsp;&nbsp;
				<input type="radio" id="JG" name="usertype" value="3" onclick="changeUserType(this.value)"/>游乐场世宇管理员&nbsp;&nbsp;
			</c:if>
			<c:if test="${user.usertype eq 2}">
				<input type="radio" id="XX" name="usertype" value="1" onclick="changeUserType(this.value)"/>后台用户&nbsp;&nbsp;
				<input type="radio" id="JG" name="usertype" value="2" onclick="changeUserType(this.value)" checked="checked"/>游乐场用户&nbsp;&nbsp;
				<input type="radio" id="JG" name="usertype" value="3" onclick="changeUserType(this.value)"/>游乐场世宇管理员&nbsp;&nbsp;
			</c:if>
			<c:if test="${user.usertype eq 3}">
				<input type="radio" id="XX" name="usertype" value="1" onclick="changeUserType(this.value)">后台用户&nbsp;&nbsp;
				<input type="radio" id="JG" name="usertype" value="2" onclick="changeUserType(this.value)"/>游乐场用户&nbsp;&nbsp;
				<input type="radio" id="JG" name="usertype" value="3" onclick="changeUserType(this.value)" checked="checked"/>游乐场世宇管理员&nbsp;&nbsp;
			</c:if>
			</td>
		</tr>
		
		
		
		<tr id="usertype_tab1" <c:if test="${!(user.usertype eq 1)}">style="display:none;"</c:if>>
			<td class="biaoge_32">用户角色</td>
			<td  class="biaoge_33" >
					<table  class="biaoge_3" style="border:none;" >
						<tr>
							<td style="width:131px;">
									<select name="s_roleId" id="s_roleId" size="6" multiple style="width:200px;height:80px;overflow: hidden;overflow-y:scroll; ">
										<c:forEach items="${roleList}" var="s_role">
										<option value="${s_role.id}">${s_role.name}</option>
										</c:forEach>
									</select>
							</td>
							<td>
								<p><input name="btn01" type="button" class="sort_sub" onclick="addRole()" value="添 加" /></p>
								<p><input name="btn01" type="button" class="sort_sub" onclick="delRole()" value="删 除" /></p>
							</td>
							<td>
								<select name="roleIds" id="roleIds" size="6" multiple style="width:200px;height:80px;overflow: hidden;overflow-y:auto;">
								<c:forEach items="${roleList}" var="t_role">
									<c:if test="${not empty roles}">
									<c:forEach items="${roles}" var="v_role">
											<c:if test="${v_role.id eq t_role.id}">
												<option value="${v_role.id}">${v_role.name}</option>
											</c:if>
										</c:forEach>
									</c:if>
								</c:forEach>
								</select>
							</td>
						</tr>
					</table>
				</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
		</tr>
		<tr id="usertype_tab2" <c:if test="${!(user.usertype eq 2)}"> style="display:none;"</c:if>>
			<td class="biaoge_32">游乐场</td>
			<td  class="biaoge_33" >
 				<Select size=1 id="provinceSel">
 					<OPTION selected></OPTION>
 				</Select>
 				<Select size=1 id="citySel" onchange="getGameCentersByCityCode(this.value,'s_gamecenterIds')">
 					<OPTION selected></OPTION>
 				</Select>
				<SCRIPT language=javascript>
					InitCitySelect(document.getElementById('provinceSel'), document.getElementById('citySel'),"getGameCentersByProvinceCode(this.value,'s_gamecenterIds');");
				</SCRIPT>
				<br/>
					<table  class="biaoge_3" style="border:none;" >
						<tr>
							<td style="width:131px;">
								<select name="s_gamecenterIds" id="s_gamecenterIds" size="12" multiple style="width:200px;height:80px;overflow: hidden;overflow-y:scroll; ">
								</select>
							</td>
							<td>
								<p><input name="btn01" type="button" class="sort_sub" onclick="addGameCenter()" value="添 加" /></p>
								<p><input name="btn01" type="button" class="sort_sub" onclick="delGameCenter()" value="删 除" /></p>
							</td>
							<td>
								<select name="gamecenterIds" id="gamecenterIds" size="12" multiple style="width:200px;height:80px;overflow: hidden;overflow-y:auto;">									
									<c:if test="${not empty gameCenters}">
										<c:forEach items="${gameCenters}" var="v_gameCenter">
											<option value="${v_gameCenter.gameCenterKey}">${v_gameCenter.gameCenterName}</option>
										</c:forEach>
									</c:if>
								</select>
							</td>
						</tr>
					</table>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
		</tr>

		<tr id="usertype_tab3" <c:if test="${!(user.usertype eq 3)}">style="display:none;"</c:if>></tr>
		<tr>
		<th colspan="4" class="t_title t_c">
			<input name="btn01" id="sys_update_btn" type="button" onclick="save(${key})" class="sort_sub" value="提 交" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input name="btn01" type="button" class="sort_sub" onclick="base.cancel('head','list','edit',userQuery);" value="返 回" />
		</th>
	</tr>
	</table>
</form>