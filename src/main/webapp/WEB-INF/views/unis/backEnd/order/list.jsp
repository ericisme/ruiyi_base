
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
		<th width="10%">订单ID</th>
		<th width="10%">商品</th>
		<th width="10%">数量</th>
		<th width="10%">邮寄地址</th>
		<th width="10%">发货状态</th>
		<th width="15%">操作</th>
	</tr>

	<c:forEach items="${page }" var="order" varStatus="status">
		<tr <c:if test="${(status.count mod 2) eq 0}">class="biaoge_22"</c:if>
			<c:if test="${(status.count mod 2) eq 1}">class="biaoge_21"</c:if> >
			<td><input type="checkbox" name="cbx" value="" /></td>
			<td>0000000${status.count}</td>
			<td>毛巾</td>
			<td>${status.count}</td>
			<td>中山市民营科技园85号</td>
			<td>未发货</td>
			<td><input class="button_2" name="" type="button" value="发货" /></td>
		</tr>

	</c:forEach>
</table>

<div class="page">${roll }</div>
