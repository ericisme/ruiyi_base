<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script type="text/javascript">
$().ready(function(){


	var lastSaved_receiverName =  '${commodityOrder.deliveryAddress.receiverName }';
	var lastSaved_address =  '${commodityOrder.deliveryAddress.address }';
	var lastSaved_zipcode =  '${commodityOrder.deliveryAddress.zipcode }';
	var lastSaved_cellNumber =  '${commodityOrder.deliveryAddress.cellNumber }';

	$('#reviseAddress_Save').click(function(){
		var receiverName = $('#receiverName').val();
		var address = $('#address').val();
		var zipcode = $('#zipcode').val();
		var cellNumber = $('#cellNumber').val();;
		var encodeParams = "commodityOrderId=" +$('#commodityOrderId').val()+ "&receiverName=" + receiverName + "&address=" + address + "&zipcode=" + zipcode + "&cellNumber=" + cellNumber;
		if(receiverName=='' || address=='' || zipcode =='' ||cellNumber=='' ){
			alert('请完善地址信息！');
			return false;
		}

		if(receiverName.length > 20 || zipcode.length > 20  ||  cellNumber.length > 20 || address.length > 255){
			alert("输入的地址信息长度不符合要求");
			return false;
		}

	});



	$('#confirmreceipt').click(function(){
		if(confirm('确认收货？')){
			window.location="${ctx}/frontEnd/accountCenter/mygift/confirmreceipt/" +$('#commodityOrderId').val() + "?pageNo=" + ${pageNo};
		}
	});
	$('#cancelorder').click(function(){
		if(confirm('确认取消订单？')){
			window.location="${ctx}/frontEnd/accountCenter/mygift/cancelorder/" +$('#commodityOrderId').val() + "?pageNo=" + ${pageNo};
		}
	});

	$('#addDeadLineDate').click(function(){
		$.ajax({
			cache: true,
			type: "POST",
			dataType:"json",
			url:"${ctx}/frontEnd/accountCenter/mygift/addDeadLineDate/" + $('#commodityOrderId').val(),
			data:"",
			async: false,
		    error: function(request) {
		    },
		    success: function(data) {
		    	if(data.code=='1'){
		    		$('#deadLineDate_span').html(data.msg);
		    		$('#addDeadLineDate').hide();
		    		alert('延长成功！');
		    	}else {
		    		alert(data.msg);
		    	}
		    }
		});
	});

});

</script>
<jsp:include
	page="/WEB-INF/views/unis/frontEnd/accountCenter/selector.jsp" />



<span id="account_center_menu_active" style="display: none">account_center_menu_mygift</span>



			<a class="btn btn-default btn-sm margin-bottom" href="/frontEnd/accountCenter/mygift?pageNo=${pageNo}"><span class="glyphicon glyphicon-chevron-left"></span>&nbsp;返 回</a>

			<c:if test="${ not empty commodityOrder.adminAmessage}">
				<div class="alert alert-warning">${commodityOrder.adminAmessage }</div>
			</c:if>

			<!-- 订单信息 -->

			<input type="hidden" id="commodityOrderId" value="${commodityOrder.id}">
			<div class="panel panel-info">
				<div class="panel-heading">
					<h4>
						订单号: <span>${commodityOrder.orderNum }</span> <br>
						状态: <span><c:if test="${commodityOrder.status==10 }">关闭订单</c:if>
								<c:if test="${commodityOrder.status==30 }">待发货</c:if>
								<c:if test="${commodityOrder.status==70 }">已发货</c:if>
								<c:if test="${commodityOrder.status==90 }">完成</c:if>

						</span>
					</h4>

					<!-- 状态是"待发货/地址错误" 显示取消订单按钮; 状态是"已发货",显示确认收货按钮-->
					<c:if test="${commodityOrder.status==30 }"><button id="cancelorder" class="btn btn-sm btn-danger">取消订单</button></c:if>
					<c:if test="${commodityOrder.status==70 }"><button id="confirmreceipt"class="btn btn-sm btn-success">确认收货</button></c:if>

				</div>

				<div class="table-responsive">
					<table class="table table-condensed table-striped">
						<thead>
							<tr>
								<th><strong>日期时间</strong></th>
								<th><strong>处理信息</strong></th>
								<th><strong>操作人</strong></th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${commodityOrder.commodityOrderStatusList }" var="item" varStatus="status">
							<tr>
								<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.statusDate }"/></td>
								<td>${item.remarks }</td>
								<td><c:if test="${item.userType==1 }">我</c:if><c:if test="${item.userType==2 }">世宇客服</c:if></td>
							</tr>
						</c:forEach>

						</tbody>
					</table>
				</div>
				<div class="panel-footer">
					<div class="row">
						<div class="col-sm-6">
							<span>承运公司: </span><a target="_blank" href="${commodityOrder.commodityLogistics.path}">${commodityOrder.commodityLogistics.name}</a>
						</div>
						<div class="col-sm-6">
							<span>货运单号: </span><span>${commodityOrder.logisticsCode}</span>
						</div>
					</div>
				</div>
				<c:if test="${commodityOrder.status==70 }">

				<div class="panel-footer">
					<div class="row">
						<div class="col-sm-6">
							<span>截至收货日期: </span><span id="deadLineDate_span"><fmt:formatDate pattern="yyyy-MM-dd" value="${commodityOrder.deadLineDate }"/></span>
							<c:if test="${lastDeadLineFlag==true }"><button id="addDeadLineDate"  class="btn btn-default btn-sm">延长15天</button></c:if>
						</div>
					</div>
				</div>


				</c:if>

			</div>

			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">收货地址</h3>
				</div>

				<c:if test="${reviseAddressFlag==false }">
					<dl class="dl-horizontal">
						<dt>收货人:</dt>
						<dd id="dd1">${commodityOrder.deliveryAddress.receiverName }</dd>
						<dt>地址:</dt>
						<dd id="dd2">${commodityOrder.deliveryAddress.address }</dd>
						<dt>邮编:</dt>
						<dd id="dd3">${commodityOrder.deliveryAddress.zipcode }</dd>
						<dt>联系电话:</dt>
						<dd id="dd4">${commodityOrder.deliveryAddress.cellNumber }</dd>
					</dl>

					<c:if test="${commodityOrder.status==30 }">
						<!-- 如果状态是"待发货/地址错误" 显示修改地址按钮-->
						<div class="text-center margin-bottom">
							<a class="btn btn-default btn-sm" href="/frontEnd/accountCenter/mygift/show/${commodityOrder.id}?raf=1&pageNo=${pageNo}">修改地址</a>
						</div>
					</c:if>
				</c:if>

				<c:if test="${reviseAddressFlag==true }">

				<form action="/frontEnd/accountCenter/mygift/reviseAddress">
				<input type="hidden" name="pageNo" value="${pageNo }"/>
				<input type="hidden" name="commodityOrderId" value="${commodityOrder.id}"/>

					<dl class="dl-horizontal">
						<dt>收货人:</dt>
						<dd id="dd1"><input id="receiverName" name="receiverName" class="form-control czNumber" type="text"  value="${commodityOrder.deliveryAddress.receiverName }" /></dd>
						<dt>地址:</dt>
						<dd id="dd2">
						<textarea id="address" name="address" class="form-control czNumber" rows="3" cols="80">${commodityOrder.deliveryAddress.address }</textarea>
						<dt>邮编:</dt>
						<dd id="dd3"><input id="zipcode" name="zipcode" class="form-control czNumber" type="text"  value="${commodityOrder.deliveryAddress.zipcode }" /></dd>
						<dt>联系电话:</dt>
						<dd id="dd4"><input id="cellNumber" name="cellNumber" class="form-control czNumber" type="text"  value="${commodityOrder.deliveryAddress.cellNumber }" /></dd>
					</dl>
					<c:if test="${commodityOrder.status==30 }">
						<div class="text-center margin-bottom">
							<button id="reviseAddress_Save"  class="btn btn-default btn-sm" type="submit">保存</button>
							<a class="btn btn-default btn-sm" href="/frontEnd/accountCenter/mygift/show/${commodityOrder.id}?&pageNo=${pageNo}">取消</a>
						</div>
					</c:if>

				</form>

				</c:if>





			</div>

			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">商品信息</h3>
				</div>
				<div class="table-responsive">
					<table class="table table-condense">
						<thead>
							<tr>
								<th><strong>商品编号</strong></th>
								<th><strong>商品名称</strong></th>
								<th><strong>单 价</strong></th>
								<th><strong>数 量</strong></th>
								<th><strong>实际扣除彩票</strong></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><a target="_blank" href="${ctx }/frontEnd/giftcenter/show/${commodityOrder.commodity.id }">${commodityOrder.commodity.commodityNo }</a></td>
								<td><a target="_blank" href="${ctx }/frontEnd/giftcenter/show/${commodityOrder.commodity.id }">${commodityOrder.commodity.name }</a></td>
								<td>${commodityOrder.commodity.price }彩票</td>
								<td>${commodityOrder.amount }</td>
								<td>${commodityOrder.totalPrice }彩票</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<!-- /.订单信息 -->





<jsp:include
	page="/WEB-INF/views/unis/frontEnd/accountCenter/sidebar.jsp" />