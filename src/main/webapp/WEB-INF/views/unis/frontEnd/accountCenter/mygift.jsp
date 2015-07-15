<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include
	page="/WEB-INF/views/unis/frontEnd/accountCenter/selector.jsp" />
<script type="text/javascript">

$().ready(function(){
	$('[id^=confirmreceipt_btn]').click(function(){
		if(confirm('确认收货？')){
			window.location="${ctx}/frontEnd/accountCenter/mygift/confirmreceipt/" +$(this).val();
		}
	});
	$('[id^=cancelorder_btn]').click(function(){
		if(confirm('确认取消订单？')){
			window.location="${ctx}/frontEnd/accountCenter/mygift/cancelorder/" +$(this).val();
		}
	});
});

</script>
	


<span id="account_center_menu_active" style="display: none">account_center_menu_mygift</span>


			<section>
				<h3>商品兑换记录</h3>
			</section>

			<!-- 礼品订单 -->
			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">订单信息</h3>
				</div>
				<div class="table-responsive">
					<table class="table table-striped">
						<thead>
							<tr>
								<th><strong>订单号</strong></th>
								<th><strong>兑换时间</strong></th>
								<th><strong>商 品</strong></th>
								<th><strong>状 态</strong></th>
								<th><strong>操 作</strong></th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${commodityOrderList }" var="item" varStatus="status">
							<tr>
								<td>${item.orderNum }</td>
								<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.orderDate }"/></td>
								<td><a target="_blank" href="${ctx }/frontEnd/giftcenter/show/${item.commodity.id}">${item.commodity.name }</a></td>
								
								<c:if test="${item.status==10 }"><td class="text-muted">已关闭</td></c:if>
								<c:if test="${item.status==30 }"><td class="text-warning">待发货</td></c:if>
								<c:if test="${item.status==70 }"><td class="text-success">已发货</td></c:if>
								<c:if test="${item.status==90 }"><td class="text-muted">完成</td></c:if>
								
								<td><a href="${ctx }/frontEnd/accountCenter/mygift/show/${item.id }?pageNo=${pageRequest.pageNo }">详细&nbsp;&rsaquo;&nbsp;</a>
								<c:if test="${item.status==30 }">	<button id="cancelorder_btn_${item.id }" value="${item.id}" class="btn btn-default btn-xs">取消订单</button></c:if>
								<c:if test="${item.status==70 }">	<button id="confirmreceipt_btn_${item.id }" value="${item.id}" class="btn btn-default btn-xs">确认收货</button></c:if>
								</td>
							</tr>
						</c:forEach>
							
						</tbody>
					</table>
				</div>
			</div>
			<!-- /.礼品订单 -->

		
			<tags:pag page="${pageRequest}" url="${ctx}/frontEnd/accountCenter/mygift" id="gamelist_page_"/>


<jsp:include
	page="/WEB-INF/views/unis/frontEnd/accountCenter/sidebar.jsp" />
