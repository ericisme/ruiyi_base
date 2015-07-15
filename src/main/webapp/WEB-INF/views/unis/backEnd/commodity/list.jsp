<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<input type="hidden" name="pageSize" id="pageSize" value="${pageSize }"/>
<c:set var="pageNum" value="${pageSize + 1 }" scope="session"/>
<table class="biaoge_2" width="100%">
	<tr>
	    <th width="8%"><input type="checkbox" onclick="base.checkAll('cbx',this);"/>全选</th>
	    <th width="10%">名字</th>
	    <th width="10%">排序号</th>
	    <th width="10%">状态</th>
	    <th width="10%">类别</th>
	    <th width="10%">积分</th>
	    <th width="10%">库存</th>
	    <th width="15%">操作</th>
	</tr>

			<c:forEach items="${page }" var="commodity" varStatus="status">
			<c:choose>
					<c:when test="${(status.count mod 2) eq 0}">
						<tr class="biaoge_21" ondblclick="edit(${commodity.id},0);" style="cursor:pointer">
					</c:when>
					<c:otherwise>
						<tr class="biaoge_22" ondblclick="edit(${commodity.id},0);" style="cursor:pointer">
					</c:otherwise>
				</c:choose>
					<td><input type="checkbox" name="cbx" value="${commodity.id}"/></td>
					<td>${commodity.name}</td>
					<td>${commodity.sortNumber}</td>
					<td>
					<c:if test="${commodity.status==0}">停用</c:if>
					<c:if test="${commodity.status==1}">启用</c:if>
					</td>	
					
					<td><!--${commodity.type}-->${commodity.commodityCategory.name}</td>	
					<td>${commodity.price}</td>	
					<td>${commodity.stocks}</td>	
					<td>
					<shiro:hasPermission name="commodity:edit">
					
							<input class="button_2" name="" type="button" value="复制" onclick="edit(${commodity.id},1);"/>
							<input class="button_2" name="" type="button" value="修改" onclick="edit(${commodity.id},0);"/>
							<input class="button_2" name="" type="button" value="删除" onclick="del(${commodity.id});" />
							<input class="button_2" name="" type="button" 
						    <c:if test="${commodity.status==0}">value="启用"</c:if>
						    <c:if test="${commodity.status==1}">value="停用"</c:if> onclick="setup(${commodity.id},${commodity.status});" />
				   </shiro:hasPermission>
					</td>
					
				</tr>
			</c:forEach>
		
</table>

<div class="page">${roll }</div>
