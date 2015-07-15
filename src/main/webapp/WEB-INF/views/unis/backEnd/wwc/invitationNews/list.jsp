
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<input type="hidden" name="pageSize" id="pageSize" value="${pageSize }"/>
<c:set var="pageNum" value="${pageSize + 1 }" scope="session"/>
<table class="biaoge_2" width="100%">
	<tr>
	    <th width="4%"><input type="checkbox" onclick="base.checkAll('cbx',this);"/></th>
	    <th width="6%">标签</th>
	    <th width="6%">版本</th>
	    <th width="25%">标题</th>
	    <th width="10%">作者</th>
	    <th width="10%">日期</th>
	    <th width="5%">排序号</th>
	    <th width="5%">状态</th>
	    <th width="25%">操作</th>
	</tr>
	<c:choose>
		<c:when test="${pageSize eq 0 }">
			<tr class="biaoge_21">
				<td colspan="8" style="color:red;">查询结果为0条记录!</td>
			</tr>
		</c:when>	
		<c:otherwise>
			<c:forEach items="${page }" var="entity" varStatus="status">
					<c:choose>
						<c:when test="${(status.count mod 2) eq 0}">
				<tr class="biaoge_21" style="cursor:pointer">
						</c:when>
						<c:otherwise>
				<tr class="biaoge_22" style="cursor:pointer">
						</c:otherwise>
					</c:choose>
					<td><input type="checkbox" name="cbx" value="${entity.id }"/></td>
					<td >${entity.tagChinese  }</td>
					<td ><c:if test="${entity.formType eq 11}">英文</c:if><c:if test="${entity.formType eq 21}">中文</c:if></td>
					<td >${entity.title  }</td>
					<td >${entity.author }</td>
					<td ><fmt:formatDate value="${entity.addTime}" pattern="yyyy-MM-dd" /></td>
					<td >${entity.sortNumber }</td>
					<td ><c:if test="${entity.status eq 1}">启用</c:if><c:if test="${entity.status eq 0}">停用</c:if></td>				
					<td>
						<shiro:hasPermission name="invitationNews:edit">
							<input class="button_2" name="" type="button" value="修改" onclick="edit(${entity.id});"/>
					    </shiro:hasPermission>
					    <shiro:hasPermission name="invitationNews:setup">
					    	<input class="button_2" id="setUnable${entity.id}" type="button" value="停用" onclick="setup(${entity.id}, 0);" <c:if test="${entity.status eq 0}">style="display:none;"</c:if> />
							<input class="button_2" id="setAble${entity.id}"   type="button" value="启用" onclick="setup(${entity.id}, 1);" <c:if test="${entity.status eq 1}">style="display:none;"</c:if> />
						</shiro:hasPermission>
						<shiro:hasPermission name="invitationNews:UpDown">
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
