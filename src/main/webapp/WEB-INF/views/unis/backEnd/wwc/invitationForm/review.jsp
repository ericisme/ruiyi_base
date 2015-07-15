<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<div class="weizhi">你的位置是：发布会邀请管理>> 邀请码管理 >> 
<!-- key为true时新增，key为false时修改 -->
	<c:choose>
		<c:when test="${key eq true}">
		新增 
		</c:when>
		<c:otherwise>
		编辑 
		</c:otherwise>
	</c:choose>
</div>

<form name="mainForm" id="mainForm">
	<table class="biaoge_3" style="margin-top:10px;">		
		<tr>
			<td class="biaoge_31" colspan="4">
				基本信息     <font size="1">注：带 * 为必填项</font>
				<input type="hidden" name="id" id="id" value="${entity.id}"/>
				
			</td>			
		</tr>
		<tr>	
			<td class="biaoge_32" style="width:15%">
				<span style="color: #F00">*</span>类型
			</td>
			<td class="biaoge_33" style="width:35%" >
				<c:choose>
					<c:when test="${entity.type eq 2}">
						自助申请
					</c:when>
					<c:otherwise>
						内部邀请
					</c:otherwise>
				</c:choose>
				<input type="hidden" name="type" id="type" value="${entity.type}"/>
			</td>
			<td class="biaoge_32" style="width:15%">
				<span style="color: #F00">*</span>状态
			</td>
			<td class="biaoge_33" style="width:35%" >
				<c:if test="${entity.reviewStatus eq null }">申请中</c:if>
				<c:if test="${entity.reviewStatus eq 1 }">审核通过</c:if>
				<c:if test="${entity.reviewStatus eq 0 }">审核不通过</c:if>
			</td>
		</tr>
		<tr>	
			<td class="biaoge_32" style="width:15%">
				<span style="color: #F00">*</span>名字
			</td>
			<td class="biaoge_33" style="width:35%" >
				<input type="text" class="text_4" name="name" id="name"  value="${entity.name }" maxlength="40"/>
			</td>

			<td class="biaoge_32" style="width:15%">
				公司
			</td>
			<td class="biaoge_33" style="width:35%" >
				<input type="text" class="text_4" name="company" id="company"  value="${entity.company }" maxlength="100" />		
			</td>
		</tr>
		<tr>	
			<td class="biaoge_32" style="width:15%">
				电话
			</td>
			<td class="biaoge_33" style="width:35%" >
				<input type="text" class="text_4" name="tel" id="tel"  value="${entity.tel }" maxlength="40" />
			</td>
			<td class="biaoge_32" style="width:15%">
				电邮(email)
			</td>
			<td class="biaoge_33" style="width:35%" >
				<input type="text" class="text_4" name="email" id="email"  value="${entity.email }" maxlength="80" />
			</td>
		</tr>
		<tr>	
			<td class="biaoge_32" style="width:15%">
				备注
			</td>
			<td class="biaoge_33" style="width:35%" >
				<textarea rows="3" cols="32" name="remark" id="remark">${entity.remark }</textarea>	
			</td>
			<td class="biaoge_32" style="width:15%">
				<span style="color: #F00">*</span>邀请码
			</td>
			<td class="biaoge_33" style="width:35%" >
				<c:choose>
					<c:when test="${key eq true}">
						<font color="red">保存后生成</font>
					</c:when>
					<c:otherwise>
						<input type="hidden" id="code" name="code" value="${entity.code }"/>
						 <font color="red"><strong>${entity.code }</strong></font>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>


		<tr>
			<th colspan="4" class="t_title t_c">
				<shiro:hasPermission name="invitationCode:review">
					<input type="button" class="button_3" id="review_1_btn" name="" value="审核通过" onclick="saveReview(1);"/>		
					<input type="button" class="button_3" id="review_0_btn" name="" value="审核不通过" onclick="saveReview(0);"/>		
					<input type="button" class="button_3" id="process_btn" name="" value="正在处理" style="display:none;" disabled/>		
				</shiro:hasPermission>	
					<input name="btn01" type="button" class="button_3" onclick="base.cancel('head','list','edit');" value="返 回" />
			</th>
		</tr>
	</table>
</form>