
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<input type="hidden" name="pageSize" id="pageSize" value="${pageSize }"/>
<c:set var="pageNum" value="${pageSize + 1 }" scope="session"/>
<table class="biaoge_2" width="100%">
	<tr>
	    <th width="4%"><input type="checkbox" onclick="base.checkAll('cbx',this);"/></th>
	    <th width="15%">物流商名称</th>
	    <th width="25%">物流查询网址</th>
	    <th width="15%">联系电话</th>
	    <th width="6%">排序号</th>
	    <th width="6%">状态</th>
	    <th width="25%">操作</th>
	</tr>
	<c:choose>
		<c:when test="${pageSize eq 0 }">
			<tr class="biaoge_21">
				<td colspan="7" style="color:red;">查询结果为0条记录!</td>
			</tr>
		</c:when>	
		<c:otherwise>
			<c:forEach items="${page }" var="entity" varStatus="status">
					<c:choose>
						<c:when test="${(status.count mod 2) eq 0}">
				<tr class="biaoge_21" ondblclick="show(${entity.id});" style="cursor:pointer">
						</c:when>
						<c:otherwise>
				<tr class="biaoge_22" ondblclick="show(${entity.id});" style="cursor:pointer">
						</c:otherwise>
					</c:choose>
					<td><input type="checkbox" name="cbx" value="${entity.id }"/></td>
					<td title="双击查看">${entity.name  }</td>
					<td title="双击查看"><a href="${entity.path  }" target="_blank">${entity.path  }</a></td>
					<td title="双击查看">${entity.tel }</td>
					<td title="双击查看">${entity.sortNumber }</td>
					<td title="双击查看"><c:if test="${entity.status eq 1}">启用</c:if><c:if test="${entity.status eq 0}">停用</c:if></td>			
					<td>
						<shiro:hasPermission name="commodityLogistics:edit">
							<input class="button_2" name="" type="button" value="修改" onclick="edit(${entity.id});"/>
					    	<input class="button_2" id="setUnable${entity.id}" type="button" value="停用" onclick="setup(${entity.id}, 0);" <c:if test="${entity.status eq 0}">style="display:none;"</c:if> />
							<input class="button_2" id="setAble${entity.id}"   type="button" value="启用" onclick="setup(${entity.id}, 1);" <c:if test="${entity.status eq 1}">style="display:none;"</c:if> />
							<input class="button_2_short" name="" type="button" value="/\" onclick="up(${entity.id});"/>
					    	<input class="button_2_short" name="" type="button" value="\/" onclick="down(${entity.id});"/>
					    </shiro:hasPermission>
					</td>
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</table>

<div class="page">${roll }</div>
