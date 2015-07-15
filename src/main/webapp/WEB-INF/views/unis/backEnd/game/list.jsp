
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<input type="hidden" name="pageSize" id="pageSize" value="${pageSize }" />
<c:set var="pageNum" value="${pageSize + 1 }" scope="session" />
<table class="biaoge_2" width="100%">
	<tr>
		<th width="8%"><input type="checkbox"
			onclick="base.checkAll('cbx',this);" />全选</th>
		<th width="8%">类型</th>
		<th width="8%">游戏名称(中文)</th>
		<th width="8%">游戏名称(英文)</th>
		<th width="8%">热门</th>
		<th width="8%">新游戏</th>
		<th width="8%">发布日期</th>
		<!-- <th width="10%">描述</th> -->
		<th width="8%">排序号</th>
		<th width="8%">状态</th>
		<th width="16%">操作</th>
	</tr>

	<c:forEach items="${page }" var="game" varStatus="status">
		<c:choose>
			<c:when test="${(status.count mod 2) eq 0}">
				<tr class="biaoge_21" ondblclick="edit(${game.id});"
					style="cursor: pointer">
			</c:when>
			<c:otherwise>
				<tr class="biaoge_22" ondblclick="edit(${game.id});"
					style="cursor: pointer">
			</c:otherwise>
		</c:choose>
		<td><input type="checkbox" name="cbx" value="${game.id}" /></td>
		<td>${game.type}</td>
		<td>${game.nameForCH}</td>
		<td>${game.nameForEN}</td>
		<td><c:if test="${game.isHot==0}">否</c:if> <c:if
				test="${game.isHot==1}">是</c:if></td>
		<td><c:if test="${game.isNew==0}">否</c:if> <c:if
				test="${game.isNew==1}">是</c:if></td>

		<td>${game.releaseDate}</td>
		<%-- <td>${game.description}</td> --%>
		<td>${game.sortNumber}</td>
		<td><c:if test="${game.status==0}">停用</c:if> <c:if
				test="${game.status==1}">启用</c:if></td>
		<td><shiro:hasPermission name="game:edit">
				<input class="button_2" name="" type="button" value="修改"
					onclick="edit(${game.id});" />
			</shiro:hasPermission> <shiro:hasPermission name="game:delete">
				<input class="button_2" name="" type="button" value="删除"
					onclick="del(${game.id});" />
			</shiro:hasPermission> <shiro:hasPermission name="game:edit">
				<input class="button_2" name="" type="button"
					<c:if test="${game.status==0}">value="启用"</c:if>
					<c:if test="${game.status==1}">value="停用"</c:if>
					onclick="setup(${game.id},${game.status});" />
			</shiro:hasPermission></td>

		</tr>
	</c:forEach>

</table>

<div class="page">${roll }</div>
