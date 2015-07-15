
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<table class="biaoge_2" width="100%">
	<tr>
		<th width="6%">所在省</th>
		<th width="6%">所在市</th>
		<th width="6%">游场名</th>
		<th width="6%">游戏名</th>
		<th width="6%">兑换比率</th>
	</tr>
	<c:choose>
		<c:when test="${pageSize eq 0 }">
			<tr class="biaoge_21">
				<td colspan="7" style="color:red;">查询结果为0条记录!</td>
			</tr>
		</c:when>	
		<c:otherwise>
			<c:forEach items="${gameWalletRateList }" var="entity" varStatus="status">
					<c:choose>
						<c:when test="${(status.count mod 2) eq 0}">
							<tr class="biaoge_21" style="cursor:pointer">
						</c:when>
						<c:otherwise>
							<tr class="biaoge_22" style="cursor:pointer">
						</c:otherwise>
					</c:choose>
					<td>${entity.gameCenterProvinceName  }</td>
					<td>${entity.gameCenterCityName  }</td>
					<td>${entity.gameCenterName  }</td>
					<td align="left">${entity.consumerName  }</td>					
					<td>
						<span name="show_rate_span" id="${entity.gameCenterKey  }_${entity.consumerKey  }_span">
							${entity.rate  }
						</span>
						<span name="edit_rate_span" style="display:none">
							<input id="${entity.gameCenterKey  }_${entity.consumerKey  }_input" 
									value="${entity.rate  }" 
									maxlength="7" 
									size="4" 								
									onkeydown="if(event.keyCode==13) {save('${entity.gameCenterKey  }','${entity.consumerKey  }',this.value,'onkeydown');}"
									onfocus="$('#${entity.gameCenterKey  }_${entity.consumerKey  }_img_loading').show();" 									
							/>
							<img src="${ctx }/static/images/themes/default/loading.gif" id="${entity.gameCenterKey  }_${entity.consumerKey  }_img_loading" style="display:none">
						</span>
					</td>
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</table>

