<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<div class="weizhi">你的位置是：商城管理>> 订单管理 >> 查看<shiro:hasPermission name="commodityOrder:cancelDelivery">/留言</shiro:hasPermission>
</div>

<form name="mainForm" id="mainForm">
	<table class="biaoge_3" style="margin-top:10px;">		
		<tr>
			<td class="biaoge_31" colspan="6">
				下订用户<font size="1"></font>
			</td>			
		</tr>
		<tr>	
			<td class="biaoge_32" style="width:10%">
				<span style="color: #F00">*</span>用户名
			</td>
			<td class="biaoge_33" style="width:23%" >
				${user.username }
			</td>
			<td class="biaoge_32" style="width:10%">
				<span style="color: #F00">*</span>用户email
			</td>
			<td class="biaoge_33" style="width:23%" >
				${user.email }
			</td>
			<td class="biaoge_32" style="width:10%">
				<span style="color: #F00">*</span>世宇积分
			</td>
			<td class="biaoge_33" style="width:23%" >
				${user.achievement_score }
			</td>
		</tr>
	</table>	

	<table class="biaoge_3" style="margin-top:10px;">		
		<tr>
			<td class="biaoge_31" colspan="6">
				待发礼品<font size="1"></font>
			</td>			
		</tr>
		<tr>
				<th class="biaoge_32" style="width:8%">订单号</th>
				<th class="biaoge_32" style="width:10%">礼品名字</th>
				<th class="biaoge_32" style="width:10%">礼品编号</th>
				<th class="biaoge_32" style="width:6%"  nowrap="nowrap">数量</th>
				<th class="biaoge_32" style="width:30%">操作历史</th>
				<th class="biaoge_32" style="width:8%">留言</th>				
			</tr>
		<c:forEach items="${commodityOrderList }" var="commodityOrder"   varStatus="status1">	
			<input type="hidden" name="commodityOrder_ids" value="${commodityOrder.id }">
			<tr>	
			<td class="biaoge_33"  >
				${commodityOrder.orderNum }
			</td>
			<td class="biaoge_33"  nowrap="nowrap" >
				<a target="_blank" href="${ctx }/frontEnd/giftcenter/show/${commodityOrder.commodity.id}">${commodityOrder.commodity.name }</a>
			</td>			
			<td class="biaoge_33"   nowrap="nowrap">
				${commodityOrder.commodity.commodityNo }
			</td>
			<td class="biaoge_33" nowrap="nowrap" >
				${commodityOrder.amount }
			</td>
			<td class="biaoge_33" >
				<c:forEach items="${commodityOrder.commodityOrderStatusList }" var="commodityOrderStatus"   varStatus="status">
					<fmt:formatDate value="${commodityOrderStatus.statusDate}" pattern="yyyy-MM-dd hh:mm:ss" />
					<c:if test="${commodityOrderStatus.userType eq 1 }">
						_会员(${user.username })
					</c:if>
					<c:if test="${commodityOrderStatus.userType eq 2 }">
						_管理员(${commodityOrderStatus.user.username})
					</c:if>
					_${commodityOrderStatus.remarks},
				<br/>
				</c:forEach>
			</td>			
			<td class="biaoge_33" >
				<textarea id="adminAmessage_${commodityOrder.id }" name="adminAmessage_${commodityOrder.id }" rows="4">${commodityOrder.adminAmessage }</textarea>				
			</td>			
			</tr>
		</c:forEach>
	</table>		
		
	<table class="biaoge_3" style="margin-top:10px;">		
		<tr>
			<td class="biaoge_31" colspan="4">
				收货地址<font size="1"></font>
			</td>			
		</tr>
		<tr>	
			<td class="biaoge_32" style="width:10%">
				<span style="color: #F00">*</span>收货人名字
			</td>
			<td class="biaoge_33" style="width:30%" >
				${deliveryAddress.receiverName }
			</td>
			<td class="biaoge_32" style="width:10%">
				<span style="color: #F00">*</span>邮编
			</td>
			<td class="biaoge_33" style="width:20%" >
				${deliveryAddress.zipcode }
			</td>
		</tr>
		<tr>
			<td class="biaoge_32" style="width:10%">
				<span style="color: #F00">*</span>收货地址
			</td>
			<td class="biaoge_33" style="width:30%" >
				${deliveryAddress.address }
			</td>
			<td class="biaoge_32" style="width:10%">
				<span style="color: #F00">*</span>电话
			</td>
			<td class="biaoge_33" style="width:20%" >
				${deliveryAddress.cellNumber }
			</td>
		</tr>
	</table>		
		
	<table class="biaoge_3" style="margin-top:10px;">		
		<tr>
			<td class="biaoge_31" colspan="2">
				物流<font size="1"></font>
			</td>			
		</tr>
		<tr>	
			<td class="biaoge_32" style="width:20%">
				<span style="color: #F00">*</span>发货物流商
			</td>
			<td class="biaoge_33" style="width:80%" >
				${commodityOrderList[0].commodityLogistics.name }
			</td>
		</tr>
		<tr>
			<td class="biaoge_32" style="width:20%">
				<span style="color: #F00">*</span>物流运单号
			</td>
			<td class="biaoge_33" style="width:80%" >
				${commodityOrderList[0].logisticsCode }
			</td>
		</tr>
	</table>
		
	<table class="biaoge_3" style="margin-top:10px;">
		<tr>
			<th colspan="4" class="t_title t_c">
				<shiro:hasPermission name="commodityOrder:cancelDelivery">
					<input type="button" class="button_3" id="save_btn" name="" value="留言" onclick="excuteMessageFor70Or90();"/>			
					<input type="button" class="button_3" id="process_btn" name="" value="正在处理" style="display:none;" disabled/>		
				</shiro:hasPermission>	
					<input name="btn01" type="button" class="button_3" onclick="base.cancel('head','list','edit');" value="返 回" />
			</th>
		</tr>
	</table>
	
</form>