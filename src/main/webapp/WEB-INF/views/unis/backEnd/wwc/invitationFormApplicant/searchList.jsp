<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<input type="hidden" name="pageSize" id="pageSize" value="${pageSize }"/>
<c:set var="pageNum" value="${pageSize + 1 }" scope="session"/>
<table class="biaoge_2" width="100%">
	<tr>	    
	    <th width="8%">国家或地区</th>
	    <th width="15%">公司名</th>
	    <th width="15%">姓名</th>
	    <th width="8%">职位</th>
	    <th width="10%">手机号</th>
	    <th width="12%">email</th>
	    <th width="6%">签到次数</th>
	    <th >操作</th>
	    
	</tr>
	<c:choose>
		<c:when test="${pageSize eq 0 }">
			<tr class="biaoge_21">
				<td colspan="9" style="color:red;">查询结果为0条记录!</td>
			</tr>
		</c:when>	
		<c:otherwise>
			<c:forEach items="${page }" var="entity" varStatus="status">
					<c:choose>
						<c:when test="${(status.count mod 2) eq 0}">
				<tr class="biaoge_21" ondblclick="" style="cursor:pointer">
						</c:when>
						<c:otherwise>
				<tr class="biaoge_22" ondblclick="" style="cursor:pointer">
						</c:otherwise>
					</c:choose>
					<!-- <td >${entity.invitationForm.invitationCode.code  }</td> -->
					<td >${entity.invitationForm.districtCodeChinese  }</td>
					<td title="${entity.invitationForm.companyName }">
						<c:choose>   
    						<c:when test="${fn:length(entity.invitationForm.companyName) > 20}">   
        						<c:out value="${fn:substring(entity.invitationForm.companyName, 0, 20)}.." />   
    						</c:when>   
   							<c:otherwise>   
      							<c:out value="${entity.invitationForm.companyName}" />  
    						</c:otherwise>  
    					</c:choose>					
					</td>
					<td >
					
						<c:if test="${entity.invitationForm.formType eq 11 }">
							${entity.firstName }&nbsp;${entity.lastName }
						</c:if>
						<c:if test="${entity.invitationForm.formType eq 21 }">
								${entity.name }
						</c:if>	,
						<c:if test="${entity.gender eq 0 }">男</c:if>
						<c:if test="${entity.gender eq 1 }">女</c:if></td>
					<td >
					${entity.position  }
					</td>
					<td >${entity.mobileNumber  }</td>
					<td >${entity.email  }</td>	
					<td >${fn:length(entity.invitationFormApplicantCheckInList)}</td>			
					<td>
						<shiro:user>
							<shiro:hasPermission name="invitationFormApplicant:checkIn">
								<input type="button" class="button_2" name="" value="签到" onclick="parent.dialogArguments.checkInFromSearchQuery('${entity.identityCode}');window.close();"/>
							</shiro:hasPermission>
						</shiro:user>
					
					</td>	
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</table>

<div class="page">${roll }</div>