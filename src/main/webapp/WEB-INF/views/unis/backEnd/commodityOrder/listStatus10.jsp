<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- 待发货订单组列表 子页 -->
<input type="hidden" name="pageSize" id="pageSize" value="${pageSize }"/>
<c:set var="pageNum" value="${pageSize + 1 }" scope="session"/>
<table class="biaoge_2" width="100%">
	<tr>
	 	<th width="2%"><!-- <input type="checkbox" onclick="base.checkAll('cbx',this);"/> --></th>
	    <th width="2%"><!--<input type="checkbox" onclick="base.checkAll('cbx',this);"/> --></th>
	    <th width="8%">订单号</th>
	    <th width="10%">商品名字</th>
	    <th width="10%">商品编号</th>
	    <th width="5%">单价</th>
	    <th width="4%">数量</th>
	    <th width="5%">总价</th>
	    <th width="12%">下单时间</th>
	    <!--<th width="12%">操作</th>-->
	</tr>
</table>
<br/>
	<c:choose>
		<c:when test="${pageSize eq 0 }">
			<table class="biaoge_2" width="100%">
			<tr class="biaoge_21">
				<td colspan="7" style="color:red;">查询结果为0条记录!</td>
			</tr>
			</table>
		</c:when>	
		<c:otherwise>
			<c:forEach items="${page }" var="commodityOrderGroupList"   varStatus="status1">

			<table class="biaoge_2" width="100%">	
			<tr class="biaoge_21">
				<td colspan="9" align="left">
					<font size="2">
					状态：
						<c:if test="${commodityOrderGroupList.commodityOrderList[0].status eq 10 }">订单已取消</c:if>
						<c:if test="${commodityOrderGroupList.commodityOrderList[0].status eq 30 }">等待发货</c:if>
						<c:if test="${commodityOrderGroupList.commodityOrderList[0].status eq 70 }">发经发货</c:if>
						<c:if test="${commodityOrderGroupList.commodityOrderList[0].status eq 90 }">交易成功</c:if>
					<br/>
					收货人：${commodityOrderGroupList.commodityOrderList[0].deliveryAddress.receiverName }&nbsp;
					电话：${commodityOrderGroupList.commodityOrderList[0].deliveryAddress.cellNumber}&nbsp;					 
					邮编：${commodityOrderGroupList.commodityOrderList[0].deliveryAddress.zipcode }&nbsp;
					<br/>
					收货地址：${commodityOrderGroupList.commodityOrderList[0].deliveryAddress.address }
					</font>
				</td>
			</tr>		
			<c:forEach items="${commodityOrderGroupList.commodityOrderList }" var="commodityOrder" varStatus="status2">

					<c:choose>
						<c:when test="${(status2.count mod 2) eq 0}">
				<tr class="biaoge_22"  >
						</c:when>
						<c:otherwise>
				<tr class="biaoge_22"  >
						</c:otherwise>
					</c:choose>			
					
					<c:if test="${(status2.count eq 1)}">
						<td width="2%" rowspan="${fn:length(commodityOrderGroupList.commodityOrderList) }">
							<c:if test="${fn:length(commodityOrderGroupList.commodityOrderList) gt 1 }">
								<input type="checkbox" onclick="base.checkAll('cbx_${commodityOrderGroupList.groupDeliveryAddressId }',this);"/>
							</c:if>
							<c:if test="${fn:length(commodityOrderGroupList.commodityOrderList) eq 1 }">
							</c:if>
						</td>
					</c:if>		
					<td width="2%"><input type="checkbox" name="cbx_${commodityOrderGroupList.groupDeliveryAddressId }" value="${commodityOrder.id }"/></td>
					<td width="8%">${commodityOrder.orderNum }</td>
					<td width="10%"><a target="_blank" href="${ctx }/frontEnd/giftcenter/show/${commodityOrder.commodity.id}">${commodityOrder.commodity.name  }</a></td>
					<td width="10%"><a target="_blank" href="${ctx }/frontEnd/giftcenter/show/${commodityOrder.commodity.id}">${commodityOrder.commodity.commodityNo  }</a></td>
					<td width="5%">${commodityOrder.singlePrice  }</td>
					<td width="4%">${commodityOrder.amount  }</td>
					<td width="5%">${commodityOrder.totalPrice  }</td>
					<td width="12%"><fmt:formatDate value="${commodityOrder.orderDate}" pattern="yyyy-MM-dd hh:mm:ss" /></td>		
					<!--<td width="12%">
						<shiro:hasPermission name="commodityLogistics:edit">
							<input class="button_2" name="" type="button" value="发货" onclick=""/>
							<input class="button_2" name="" type="button" value="留言" onclick=""/>
					    </shiro:hasPermission>
					</td>
					 -->
				</tr>
			</c:forEach>
			</table>
				<br/>
			</c:forEach>
		</c:otherwise>
	</c:choose>


<div class="page">${roll }</div>
