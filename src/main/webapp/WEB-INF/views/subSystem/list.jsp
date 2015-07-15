
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<input type="hidden" name="pageSize" id="pageSize" value="${pageSize }"/>
<c:set var="pageNum" value="${pageSize + 1 }" scope="session"/>
<table class="biaoge_2" width="100%">
	<tr>
	    <th width="10%"><input type="checkbox" onclick="base.checkAll('cbx',this);"/></th>
	    <th width="20%">子系统名称</th>
	    <th width="25%">访问地址</th>
	    <th width="10%">排序号</th>
	    <th width="10%">状态</th>
	    <th>操作</th>
	</tr>
	<c:choose>
		<c:when test="${pageSize eq 0 }">
			<tr class="biaoge_21">
				<td colspan="6" style="color:red;">系统暂时还不存在任何子系统!</td>
			</tr>
		</c:when>	
		<c:otherwise>
			<c:forEach items="${page }" var="subSys" varStatus="status">
				<c:choose>
					<c:when test="${(status.count mod 2) eq 0}">
						<tr class="biaoge_21" ondblclick="edit(${subSys.id});" style="cursor:pointer">
					</c:when>
					<c:otherwise>
						<tr class="biaoge_22" ondblclick="edit(${subSys.id});" style="cursor:pointer">
					</c:otherwise>
				</c:choose>
					<td><input type="checkbox" name="cbx" value="${subSys.id }"/></td>
					<td>${subSys.subName }</td>
					<td>${subSys.mainpage }</td>
					<td>${subSys.orderNum }</td>
					<td>
						<c:choose>
							<c:when test="${subSys.status eq 0}">
								<span id="statusDisplay${subSys.id}"><font color="red">停用</font></span>
							</c:when>
							<c:otherwise>
								<span id="statusDisplay${subSys.id}">正常</span>
							</c:otherwise>
						</c:choose>
					</td>
					<td>
					<shiro:user>
						<shiro:hasPermission name="subSys:edit">
							<input class="button_2" name="" type="button" value="修改" onclick="edit(${subSys.id});"/>
						</shiro:hasPermission>
						<shiro:hasPermission name="subSys:delete">
					   	 	<input class="button_2" name="" type="button" value="删除" onclick="del(${subSys.id});" />
					    </shiro:hasPermission>
					    <shiro:hasPermission name="subSys:setup">
						    <input class="button_2" id="setUnable${subSys.id}" type="button" value="停用" onclick="setup(${subSys.id},0);" <c:if test="${subSys.status eq 0}">style="display:none;"</c:if> />
						    <input class="button_2" id="setAble${subSys.id}" type="button" value="启用" onclick="setup(${subSys.id},1);" <c:if test="${subSys.status eq 1}">style="display:none;"</c:if>/>
					    </shiro:hasPermission>
					</shiro:user>
					</td>
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</table>

<div class="page">${roll }</div>
