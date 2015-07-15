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
	    <th width="6%">类型</th>
	    <th width="10%">邀请码</th>
	    <th width="15%">被邀人名字</th>
	    <th width="15%">公司</th>
	    <th width="10%">电话</th>
	    <th width="10%">电邮</th>
	    <th width="6%">地区</th>
	    <th width="20%">备注</th>	    
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
					<td title="双击查看">
						<c:if test="${entity.type eq 1 }">内部邀请</c:if>
						<c:if test="${entity.type eq 2 }"><i>自助申请</i></c:if>
					</td>
					<td title="双击查看">
						<c:if test="${entity.type eq 2 }">
							<c:if test="${entity.reviewStatus eq null }"><font color="blue">申请中</font></c:if>
							<c:if test="${entity.reviewStatus eq 0 }">审核不通过</c:if>
							<c:if test="${entity.reviewStatus eq 1 }"><font color="red"><strong>${entity.code  }</strong></font></c:if>
						</c:if>
						<c:if test="${entity.type eq 1 }">
							<font color="red"><strong>${entity.code  }</strong></font>
						</c:if>					
					</td>	
					<td title="${entity.name  }">
						<strong>
						<c:choose>   
    						<c:when test="${fn:length(entity.name) > 20}">   
        						<c:out value="${fn:substring(entity.name, 0, 20)}.." />   
    						</c:when>   
   							<c:otherwise>   
      							<c:out value="${entity.name}" />  
    						</c:otherwise>  
    					</c:choose>								
						</strong>
					</td>
					<td title="${entity.company  }">
						<c:choose>   
    						<c:when test="${fn:length(entity.company) > 20}">   
        						<c:out value="${fn:substring(entity.company, 0, 20)}.." />   
    						</c:when>   
   							<c:otherwise>   
      							<c:out value="${entity.company}" />  
    						</c:otherwise>  
    					</c:choose>								
					</td>
					<td title="双击查看">${entity.tel  }</td>
					<td title="双击查看">${entity.email  }</td>
					<td title="双击查看">${entity.districtCode  }</td>
					<td title="${entity.remark  }">
						      <c:choose>
          						<c:when test="${fn:length(entity.remark) > 12}">
              						<c:out value="${fn:substring(entity.remark, 0, 12)}..." />
          						</c:when>
        						<c:otherwise>
            						<c:out value="${entity.remark}" />
          						</c:otherwise>
      						</c:choose>
					</td>
					
					<td>
						<shiro:hasPermission name="invitationCode:edit">
							<c:if test="${entity.type eq 1 }">
								<input type="button" class="button_2" name="" value="编辑" onclick="edit(${entity.id});"/>
							</c:if>
						</shiro:hasPermission>
						<shiro:hasPermission name="invitationCode:review">
							<c:if test="${entity.type eq 2 }">
								<input type="button" class="button_2" name="" value="审核" onclick="review(${entity.id});"/>
							</c:if>
						</shiro:hasPermission>
					</td>	
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</table>

<div class="page">${roll }</div>
