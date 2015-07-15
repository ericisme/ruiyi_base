<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<input type="hidden" name="pageSize" id="pageSize" value="${pageSize }"/>
<c:set var="pageNum" value="${pageSize + 1 }" scope="session"/>
<table class="biaoge_2" width="100%">
	<tr>
	    <th width="4%"><input type="checkbox" onclick="base.checkAll('cbx',this);"/></th>
	    <th width="8%">邀请码</th>
	    <!--<th width="8%">邀请类型</th> -->
	    <th width="8%">国家/地区</th>
	    <th width="8%">表单类型</th>
	    <th width="10%">是否已确认</th>
	    <th width="25%">公司名称</th>
	    <th width="12%">预计到达时间</th>
	    <th width="10%">是否需要接机</th>
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
					<td><input type="checkbox" name="cbx" value="${entity.id }"/></td>
					<td title="双击查看">${entity.invitationCode.code  }</td>
					<!--<td title="双击查看">${entity.invitationCode.typeChinese  }</td> -->
					<td title="双击查看">${entity.districtCodeChinese  }</td>
					<td title="双击查看">
						<c:if test="${entity.formType eq 11 }">
							英文
						</c:if>
						<c:if test="${entity.formType eq 21 }">
							中文
						</c:if>
					</td>
					<td title="双击查看">
						<c:if test="${entity.isSure eq 1 }">
							<strong><font color="blue">已确认</font></strong>
						</c:if>
						<c:if test="${entity.isSure eq 0 }">
							<i>未确认</i>
						</c:if>
					</td>
					<td title="${entity.companyName}">
						<strong>
							<c:choose>   
    							<c:when test="${fn:length(entity.companyName) > 20}">   
        							<c:out value="${fn:substring(entity.companyName, 0, 20)}.." />   
    							</c:when>   
   								<c:otherwise>   
      								<c:out value="${entity.companyName}" />  
    							</c:otherwise>  
    						</c:choose>
						</strong>
					</td>
					<td title="双击查看"><fmt:formatDate value="${entity.arriveDate}" pattern="yyyy-MM-dd" /></td>
					<td title="双击查看">				
						<c:if test="${entity.pickUpOrNot eq 0 }">否</c:if>
						<c:if test="${entity.pickUpOrNot eq 1 }">是</c:if>
					</td>					
					<td>
						<input type="button" class="button_2" name="" value="查看" onclick="show(${entity.id});"/>
					</td>	
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</table>

<div class="page">${roll }</div>