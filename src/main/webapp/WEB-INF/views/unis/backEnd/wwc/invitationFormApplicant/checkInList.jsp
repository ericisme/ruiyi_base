<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
	<c:choose>
		<c:when test="${exist eq true}">
		</c:when>
		<c:otherwise>
		<font color="red"><strong>对不起，系统查询没有 该 与会者二维码： ${identityCode }</strong></font><br/><br/>
		</c:otherwise>
	</c:choose>	
	<table class="biaoge_3" style="margin-top:10px;">			
		<tr>
			<td class="biaoge_31" colspan="4">
				<strong>${invitationFormApplicant.invitationForm.companyName}</strong>
			</td>
		</tr>
		<tr>
			<td class="biaoge_32" style="width:15%">
				邀请码
			</td>
			<td class="biaoge_33" style="width:35%" >
				<font></font>${invitationFormApplicant.invitationForm.invitationCode.code }
			</td>

			<td class="biaoge_32" style="width:15%">
				版本
			</td>
			<td class="biaoge_33" style="width:35%">
				<c:if test="${invitationFormApplicant.invitationForm.formType eq 11 }">
					英文
				</c:if>
				<c:if test="${invitationFormApplicant.invitationForm.formType eq 21 }">
					中文
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="biaoge_32" style="width:15%">
				<!-- 预计到达时间邀请类型 -->所属公司
			</td>
			<td class="biaoge_33" style="width:35%" >
				<!-- fmt:formatDate value="${invitationFormApplicant.invitationForm.arriveDate}" pattern="yyyy-MM-dd" /> -->
				<!--<c:if test="${invitationFormApplicant.invitationForm.invitationCode.type eq 1 }">内部邀请</c:if>
				<c:if test="${invitationFormApplicant.invitationForm.invitationCode.type eq 2 }"><i>自助申请</i></c:if>
				-->
				${invitationFormApplicant.invitationForm.companyName }
			</td>

			<td class="biaoge_32" style="width:15%">
				<!-- 是否需要接机 -->国家/地区
			</td>
			<td class="biaoge_33" style="width:35%">
				${invitationFormApplicant.invitationForm.districtCodeChinese }
			<!-- 
				<c:if test="${invitationFormApplicant.invitationForm.pickUpOrNot eq 0 }">否</c:if>
				<c:if test="${invitationFormApplicant.invitationForm.pickUpOrNot eq 1 }">是 &nbsp;&nbsp;航班号:${invitationFormApplicant.invitationForm.flightNo}</c:if>
			 -->
			</td>
		</tr>
		<tr>
			<td class="biaoge_33" colspan="4">
				基本信息
			</td>
		</tr>
		<tr>
		</tr>
		<tr>
			<td class="biaoge_32" style="width:10%">
				姓名
			</td>
			<td class="biaoge_33" style="width:20%" >
				<font color="blue">
				<strong>
				<c:if test="${invitationFormApplicant.invitationForm.formType eq 11 }">
					${invitationFormApplicant.firstName }&nbsp;${invitationFormApplicant.lastName }
				</c:if>
				<c:if test="${invitationFormApplicant.invitationForm.formType eq 21 }">
					${invitationFormApplicant.name }
				</c:if>					
				</strong>				
				</font>
				,
				<!--<c:if test="${not(empty invitationFormApplicant.name)  }">,</c:if>		-->	
				<c:if test="${invitationFormApplicant.gender eq 0 }">男</c:if>
				<c:if test="${invitationFormApplicant.gender eq 1 }">女</c:if>
			</td>
			<td class="biaoge_32" style="width:10%">
				职位
			</td>
			<td class="biaoge_33" style="width:20%">
				<font color="green"><strong>${invitationFormApplicant.position }</strong></font>
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
		<!-- 
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
				<c:if test="${empty  invitationFormApplicant.invitationForm.hotel}">不用，谢谢</c:if>
				<c:if test="${invitationFormApplicant.invitationForm.hotel eq 11 }">喜来登酒店</c:if>
				<c:if test="${invitationFormApplicant.invitationForm.hotel eq 21 }">金钻酒店</c:if>
				<c:if test="${invitationFormApplicant.invitationForm.hotel eq 32 }">金沙酒店</c:if>
			</td>
			<td class="biaoge_32" style="width:15%">
				餐饮
			</td>
			<td class="biaoge_33" style="width:35%">
				<c:if test="${invitationFormApplicant.invitationForm.dinnerType eq 1 }">中餐</c:if>
				<c:if test="${invitationFormApplicant.invitationForm.dinnerType eq 2 }">西餐</c:if>
				&nbsp;&nbsp;
				${invitationFormApplicant.invitationForm.specialDiet }
			</td>
		
		</tr>
		<tr>
			<td class="biaoge_32" style="width:15%">
				参与活动
			</td>
			<td class="biaoge_33" style="width:35%" colspan="3">
				<c:if test="${invitationFormApplicant.invitationForm.nightTourOfQiRiver eq 1 }">岐江夜游(10月20晚)&nbsp;&nbsp;</c:if>
				<c:if test="${invitationFormApplicant.invitationForm.visitFormerResidenceOfSunYatSen eq 1 }">孙中山故居(10月20~22下午)&nbsp;&nbsp;</c:if>
				<c:if test="${invitationFormApplicant.invitationForm.shopping eq 1 }">旅游购物&nbsp;&nbsp;</c:if>
				<c:if test="${invitationFormApplicant.invitationForm.entertainment eq 1 }">娱乐活动(10月20、21日晚)&nbsp;&nbsp;</c:if>
			</td>
		</tr>	
		<tr>
			<td class="biaoge_32" style="width:15%">
				建议
			</td>
			<td class="biaoge_33" style="width:35%"  colspan="3">
				${invitationFormApplicant.invitationForm.message }
			</td>
		</tr> -->
	</table>
签到历史：
<table class="biaoge_2" width="100%">
	<tr>
		<th width="40%">签到时间</th>
	    <th width="40%">协助签到后台用户</th>
	    <th width="20%">操作</th>	    
	</tr>
			<c:forEach items="${invitationFormApplicantCheckInList }" var="entity" varStatus="status">
					<c:choose>
						<c:when test="${(status.count mod 2) eq 0}">
				<tr class="biaoge_21" ondblclick="" style="cursor:pointer" id="checkInTr_${entity.id}">
						</c:when>
						<c:otherwise>
				<tr class="biaoge_22" ondblclick="" style="cursor:pointer" id="checkInTr_${entity.id}">
						</c:otherwise>
					</c:choose>					
					<td title="双击查看"><c:if test="${status.index eq 0 }"><strong></c:if><fmt:formatDate value="${entity.checkInDateTime}" pattern="yyyy-MM-dd HH:mm:ss" /><c:if test="${status.index eq 0 }"></strong></c:if></td>
					<td title="双击查看"><c:if test="${status.index eq 0 }"><strong></c:if>${entity.handleUser.username  }<c:if test="${status.index eq 0 }"></strong></c:if></td>				
					<td>
						<shiro:user>
							<shiro:hasPermission name="invitationFormApplicant:checkInDelete">
								<input type="button" class="button_2" name="" value="删除" onclick="checkInDelete(${entity.id});"/>
							</shiro:hasPermission>
						</shiro:user>
					</td>	
				</tr>
			</c:forEach>
</table>