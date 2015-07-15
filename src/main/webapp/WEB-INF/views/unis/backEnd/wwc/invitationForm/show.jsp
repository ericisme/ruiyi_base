<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<div class="weizhi">你的位置是：发布会邀请管理 >>用户表单管理 >> 查看表单
</div>

<form name="mainForm" id="mainForm">
	<table class="biaoge_3" style="margin-top:10px;">			
		<tr>
			<td class="biaoge_31" colspan="4">
				<strong>基本信息</strong>
			</td>
		</tr>
		<tr>
		<td class="biaoge_32" style="width:15%">
				公司名称
			</td>
			<td class="biaoge_33" style="width:35%" >
				<font></font>${entity.companyName}
			</td>

			<td class="biaoge_32" style="width:15%">
				国家或地区
			</td>
			<td class="biaoge_33" style="width:35%">
				${entity.districtCodeChinese}
			</td>
		</tr>
		<tr>
			<td class="biaoge_32" style="width:15%">
				邀请码
			</td>
			<td class="biaoge_33" style="width:35%" >
				<font></font>${entity.invitationCode.code }
			</td>

			<td class="biaoge_32" style="width:15%">
				版本
			</td>
			<td class="biaoge_33" style="width:35%">
				<c:if test="${entity.formType eq 11 }">
					英文
				</c:if>
				<c:if test="${entity.formType eq 21 }">
					中文
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="biaoge_32" style="width:15%">
				预计到达时间
			</td>
			<td class="biaoge_33" style="width:35%" >
				<fmt:formatDate value="${entity.arriveDate}" pattern="yyyy-MM-dd" />
			</td>

			<td class="biaoge_32" style="width:15%">
				是否需要接机
			</td>
			<td class="biaoge_33" style="width:35%">
				<c:if test="${entity.pickUpOrNot eq 0 }">否</c:if>
				<c:if test="${entity.pickUpOrNot eq 1 }">是 &nbsp;&nbsp;航班号:${entity.flightNo}</c:if>
			</td>
		</tr>
		<tr>
			<td class="biaoge_33" colspan="4">
				与会人员信息
			</td>
		</tr>
		<tr>
		</tr>
		<c:forEach items="${entity.invitationFormApplicantList }" var="invitationFormApplicant" varStatus="status">
		<tr>
			<td class="biaoge_32" style="width:10%">
				<c:if test="${status.index eq 0}">(常务联系人)</c:if> (联系人${status.index +1})姓名
			</td>
			<td class="biaoge_33" style="width:20%" >
				<c:if test="${entity.formType eq 11 }">
					${invitationFormApplicant.firstName }&nbsp;${invitationFormApplicant.lastName }
				</c:if>
				<c:if test="${entity.formType eq 21 }">
					${invitationFormApplicant.name }
				</c:if>				
			,
				<c:if test="${invitationFormApplicant.gender eq 0 }">男</c:if>
				<c:if test="${invitationFormApplicant.gender eq 1 }">女</c:if>
			</td>
			<td class="biaoge_32" style="width:10%">
				职位
			</td>
			<td class="biaoge_33" style="width:20%">
				${invitationFormApplicant.position }
			</td>
		</tr>
		<tr>
			<td class="biaoge_32" style="width:15%">
				电话
			</td>
			<td class="biaoge_33" style="width:35%">
				${invitationFormApplicant.mobileNumber }
			</td>
			<td class="biaoge_32" style="width:15%">
				email
			</td>
			<td class="biaoge_33" style="width:35%">
				${invitationFormApplicant.email }
			</td>
		</tr>
		<tr>
		</tr>
		</c:forEach>
		<tr>
			<td class="biaoge_33" colspan="4">
				食宿安排和建议
			</td>
		</tr>
		<tr>
			<td class="biaoge_32" style="width:15%">
				住宿
			</td>
			<td class="biaoge_33" style="width:35%">
				<c:if test="${empty  entity.hotel}">不用，谢谢</c:if>
				<c:if test="${entity.hotel eq 11 }">喜来登酒店</c:if>
				<c:if test="${entity.hotel eq 21 }">金钻酒店</c:if>
				<c:if test="${entity.hotel eq 32 }">金沙酒店</c:if>
			</td>
			<td class="biaoge_32" style="width:15%">
				餐饮
			</td>
			<td class="biaoge_33" style="width:35%">
				<c:if test="${entity.dinnerType eq 1 }">中餐</c:if>
				<c:if test="${entity.dinnerType eq 2 }">西餐</c:if>
				&nbsp;&nbsp;
				${entity.specialDiet }
			</td>
		
		</tr>
		<tr>
			<td class="biaoge_32" style="width:15%">
				参与活动
			</td>
			<td class="biaoge_33" style="width:35%" colspan="3">
				<c:if test="${entity.nightTourOfQiRiver eq 1 }">岐江夜游(10月20晚)&nbsp;&nbsp;</c:if>
				<c:if test="${entity.visitFormerResidenceOfSunYatSen eq 1 }">孙中山故居(10月20~22下午)&nbsp;&nbsp;</c:if>
				<c:if test="${entity.shopping eq 1 }">旅游购物&nbsp;&nbsp;</c:if>
				<c:if test="${entity.entertainment eq 1 }">娱乐活动(10月20、21日晚)&nbsp;&nbsp;</c:if>
			</td>
		</tr>	
		<tr>
			<td class="biaoge_32" style="width:15%">
				建议
			</td>
			<td class="biaoge_33" style="width:35%"  colspan="3">
				${entity.message }
			</td>
		</tr>

		<tr>
			<th colspan="4" class="t_title t_c"> 
				<c:if test="${entity.isSure eq 0}">
					<input name="btn01" type="button" id="save_btn"  class="button_3" onclick="comfirmInfo(${entity.id},1);" value="确认信息" />
				</c:if>
				<c:if test="${entity.isSure eq 1}">
					<input name="btn01" type="button" id="save_btn"  class="button_3" onclick="comfirmInfo(${entity.id},0);" value="取消确认" />
				</c:if>				
				<input name="btn01" type="button" class="button_3" onclick="base.cancel('head','list','edit');" value="返 回" />
			</th>
		</tr>
	</table>
</form>