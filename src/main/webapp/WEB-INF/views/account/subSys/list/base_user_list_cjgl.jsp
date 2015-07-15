<%@ page contentType="text/html;charset=gbk"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<input type="hidden" name="pageSize" id="pageSize" value="${pageSize }"/>
<table width="100%" cellspacing="0" cellpadding="0" bordercolor="#AAC7DD" class="maintb m_t">
	<tr valign="middle">
	    <th style="text-align:center;width:16%" class="t_title">�� λ</th>
	    <th style="text-align:center;width:16%" class="t_title">�� ��</th>
	    <th style="text-align:center;width:17%" class="t_title">ְ ��</th>
	    <th style="text-align:center;width:21%" class="t_title">���֤����</th>
	    <th style="text-align:center;width:21%" class="t_title">����������</th>
	    <th style="text-align:center" class="t_title">�� ��</th>
	</tr>
	<c:choose>
		<c:when test="${pageSize eq 0 }">
			<tr valign="middle">
				<td colspan="6" style="color:red;text-align:center;">������Ϣ����ϵͳ��δ��Ӹ��û�����û���ѯ��Ϣ���������ڻ�����Ϣ����ϵͳ������û������ʵ�û���Ϣ�������²�ѯ</td>
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
							<td align="center" class="s_t5">${user.school.schoolName }</td>
						</c:when>
						<c:otherwise>
							<td align="center" class="s_t5">${user.unit.unitName }</td>
						</c:otherwise>
					</c:choose>
					<td align="center" class="s_t5">${user.username }</td>
					<td align="center" class="s_t5"></td>
					<td align="center" class="s_t5">${user.IDCard }</td>
					<td align="center" class="s_t5"></td>
					<td align="center" class="s_t5">
						<input class="sort_sub" name="" type="button" value="ѡ ��" onclick="selectUser(${user.id},'${user.identifyCode }')"/>
					</td>
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</table>

<div class="page" style="text-align:right;">${roll }</div>
