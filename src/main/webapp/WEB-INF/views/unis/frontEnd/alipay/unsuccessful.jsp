<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="container">

	<div class="jumbotron">
		<div class="text-center">
			<h1>
				<i class="fa fa-frown-o fa-fw" style="color: #e97878;"></i>对不起, 充值失败
			</h1>
			<p>
			<h2>订单号: ${outTradeNo }</h2>
			</p>
			<p class="text-danger">如有支付疑难, 欢迎您联系我们的客服</p>
		</div>
	</div>

</div>
