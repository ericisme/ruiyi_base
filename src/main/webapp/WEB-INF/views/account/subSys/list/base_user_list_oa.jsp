<%@ page contentType="text/html;charset=gbk"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<input type="hidden" name="pageSize" id="pageSize" value="${pageSize }"/>
<div class="blank5"></div>
<table width="100%" cellspacing="0" cellpadding="0" bordercolor="#AAC7DD" class="TableList" align="center">
	<tr valign="middle" class="TableHeader">
	    <th style="text-align:center;width:16%" class="tabletitle1">�� λ</th>
	    <th style="text-align:center;width:16%" class="tabletitle1">�� ��</th>
	    <th style="text-align:center;width:17%" class="tabletitle1">ְ ��</th>
	    <th style="text-align:center;width:21%" class="tabletitle1">���֤����</th>
	    <th style="text-align:center;width:21%" class="tabletitle1">����������</th>
	    <th style="text-align:center" class="tabletitle1">�� ��</th>
	</tr>
	<c:choose>
		<c:when test="${pageSize eq 0 }">
			<tr valign="middle">
				<td colspan="6" style="color:red;text-align:center;" bgcolor="#FFFFEF">������Ϣ����ϵͳ��δ��Ӹ��û�����û���ѯ��Ϣ���������ڻ�����Ϣ����ϵͳ������û������ʵ�û���Ϣ�������²�ѯ</td>
			</tr>
		</c:when>	
		<c:otherwise>
			<c:forEach items="${page }" var="user" varStatus="status">
				<c:choose>
					<c:when test="${user.usertype eq 1}">
						<input type="hidden" id="typeName${user.id }" value="${user.school.schoolName }"/>
						<input type="hidden" id="JGName${user.id }" value="${user.unit.unitName }"/>
					</c:when>
					<c:otherwise>
						<input type="hidden" id="typeName${user.id }" value="${user.unit.unitName }"/>
					</c:otherwise>
				</c:choose>
				<tr valign="middle">
					<c:choose>
						<c:when test="${user.usertype eq 1}">
							<td align="center" bgcolor="#FFFFEF">${user.school.schoolName }</td>
						</c:when>
						<c:otherwise>
							<td align="center" bgcolor="#FFFFEF">${user.unit.unitName }</td>
						</c:otherwise>
					</c:choose>
					<td align="center" bgcolor="#FFFFEF">${user.username }</td>
					<td align="center" bgcolor="#FFFFEF"></td>
					<td align="center" bgcolor="#FFFFEF">${user.IDCard }</td>
					<td align="center" bgcolor="#FFFFEF"></td>
					<td align="center" bgcolor="#FFFFEF">
						<input class="sort_sub" name="" type="button" value="ѡ ��" onclick="selectUser(${user.id},'${user.identifyCode }')"/>
					</td>
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</table>

<div class="page" style="text-align:right;">${roll }</div>
