<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="container">

	<div class="jumbotron">
		<div class="text-center">
			<h1>
				<i class="fa fa-check-circle-o fa-fw" style="color: #cbdf22;"></i>恭喜你!
				支付成功!
			</h1>
			<p>
			<h2>订单号: ${outTradeNo }</h2>
			</p>
			<p>
				<span class="text-info"><strong>${usename }</strong></span>充值成功, 已支付金额 <span
					class="text-info"><strong>¥ ${payMoney } 元</strong></span>
			</p>
			<p class="text-danger">请记录好你的订单号, 如有发生交易错误, 请及时联系我们的客服</p>
		</div>
	</div>

</div>
<!-- end container -->
