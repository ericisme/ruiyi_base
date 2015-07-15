
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<input type="hidden" name="pageSize" id="pageSize" value="${pageSize }"/>
<c:set var="pageNum" value="${pageSize + 1 }" scope="session"/>
<table class="biaoge_2" width="100%">
	<tr>
	    <th width="8%"><input type="checkbox" onclick="base.checkAll('cbx',this);"/>全选</th>
	    <th width="15%">真实姓名</th>
	    <th width="15%">登录名称</th>
	    <th width="5%">性别</th>
	    <th width="5%">状态</th>
	    <th width="11%">手机号码</th>
	    <th width="20%">操作</th>
	</tr>
	<c:choose>
		<c:when test="${pageSize eq 0 }">
			<tr class="biaoge_21">
				<td colspan="6" style="color:red;">系统未检测到此类型用户!</td>
			</tr>
		</c:when>	
		<c:otherwise>
			<c:forEach items="${page }" var="user" varStatus="status">
				<c:choose>
					<c:when test="${(status.count mod 2) eq 0}">
						<tr class="biaoge_21" ondblclick="edit(${user.id});" style="cursor:pointer">
					</c:when>
					<c:otherwise>
						<tr class="biaoge_22" ondblclick="edit(${user.id});" style="cursor:pointer">
					</c:otherwise>
				</c:choose>
					<td><input type="checkbox" name="cbx" value="${user.id }"/></td>
					<td>${user.username }</td>
					<td>${user.loginName }</td>
					<td><c:choose>
					<c:when test="${user.gender eq 1 }">男</c:when>								
					<c:otherwise>女</c:otherwise>
					</c:choose>
					<td>
						<c:choose>
							<c:when test="${user.status eq 1}">
								<span id="statusDisplay${user.id}"><font color="red">停用</font></span>
							</c:when>
							<c:otherwise>
								<span id="statusDisplay${user.id}">正常</span>
							</c:otherwise>
						</c:choose>
					</td>
					<c:choose>
					<c:when test="${not empty user.mobile}">
						<td>${user.mobile}</td>
					</c:when>
					<c:otherwise>
						<td>待完善</td>
					</c:otherwise>
					</c:choose>					
					<td>
						<input class="button_2" name="" type="button" value="修改" onclick="edit(${user.id});"/>
					    <input class="button_2" name="" type="button" value="删除" onclick="del(${user.id});" />
					    <input class="button_2" id="setUnable${user.id}" type="button" value="停用" onclick="setup(${user.id},1);" <c:if test="${user.status eq 1}">style="display:none;"</c:if> />
					    <input class="button_2" id="setAble${user.id}" type="button" value="启用" onclick="setup(${user.id},0);" <c:if test="${user.status eq 0}">style="display:none;"</c:if>/>
					</td>
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</table>

<div class="page">${roll }</div>
