<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<span id="sitemeshtitle" style="display: none">title_mall</span>
<title>世宇游戏-积分商城</title>
<script type="text/javascript" src="${ctx }/static/js/base/CookieUtils.js"></script>
<script type="text/javascript">
 $().ready(function(){


	 $('[id^=commodity_sort]').click(function(){
		 var sort = $(this).attr("value");
		 var order = $('[id^=commodity_order_li][class$=active]').attr("value");

		 var currentId = $('[id^=commodity_order_li][class$=active]').attr("id");

		 if(currentId == 'commodity_order_li_0' ){
			 order = getCookie('gift_sort_flag_0');
			 if(order == undefined){
				 order = 0;
			 }
		 }else if(currentId == 'commodity_order_li_1'){
			 order = getCookie('gift_sort_flag_1');
			 if(order == undefined){
				 order = 1;
			 }

		 }else if(currentId == 'commodity_order_li_2'){
			 order = getCookie('gift_sort_flag_2');
			 if(order == undefined){
				 order = 2;
			 }
		 }


		 $(this).attr("href","${ctx}/frontEnd/giftcenter/index?_c_id=" + sort + '&order=' + order);
	 });

	 $('[id^=commodity_order]').click(function(){
		 var order = $(this).attr("value");
		 var sort = $('[id^=commodity_sort_li][class$=active]').attr("value");


		 var currentId = $(this).attr("id");
		 if(currentId == 'commodity_order_0' ){
			 order = getCookie('gift_sort_flag_0');
			 if( order == undefined || order == 0 ){
				 SetCookie('gift_sort_flag_0', 10);
				 order = 10;
			 }else{
				 SetCookie('gift_sort_flag_0', 0);
				 order = 0;
			 }
		 }else if(currentId == 'commodity_order_1' ){
			 order = getCookie('gift_sort_flag_1');
			 if( order == undefined || order == 1 ){
				 SetCookie('gift_sort_flag_1', 11);
				 order = 11;
			 }else{
				 SetCookie('gift_sort_flag_1', 1);
				 order = 1;
			 }

		 }else if(currentId == 'commodity_order_2'){
			 order = getCookie('gift_sort_flag_2');
			 if( order == undefined || order == 2 ){
				 SetCookie('gift_sort_flag_2', 12);
				 order = 12;
			 }else{
				 SetCookie('gift_sort_flag_2', 2);
				 order = 2;
			 }
		 }

		 $(this).attr("href","${ctx}/frontEnd/giftcenter/index?_c_id=" + sort + '&order=' + order);
	 });

	 $('[id^=giftcenter_page_]').click(function(){
		 var order = $('[id^=commodity_order_li][class$=active]').attr("value");
		 var sort = $('[id^=commodity_sort_li][class$=active]').attr("value");

		 var currentId = $('[id^=commodity_order_li][class$=active]').attr("id");
		 if(currentId == 'commodity_order_li_0' ){
			 order = getCookie('gift_sort_flag_0');
			 if(order == undefined){
				 order = 0;
			 }
		 }else if(currentId == 'commodity_order_li_1'){
			 order = getCookie('gift_sort_flag_1');
			 if(order == undefined){
				 order = 1;
			 }

		 }else if(currentId == 'commodity_order_li_2'){
			 order = getCookie('gift_sort_flag_2');
			 if(order == undefined){
				 order = 2;
			 }
		 }

		 $(this).attr("href", $(this).attr("href") + "&_c_id=" + sort + '&order=' + order);
	 });



 });
</script>

<div class="container">

	<ol class="breadcrumb">
		<li><a href="/">首页</a></li>
		<li>礼品中心</li>
	</ol>
	<!-- end breadcrumb -->
	<div class="row">

		<div class="col-sm-4 col-md-3">

			<c:if test="${UsernameAndScore.isShow==1 }">
				<div class="panel panel-default score-panel">
					<div class="panel-body">
						<ul>
							<li>用户名：${UsernameAndScore.userName }</li>
							<!-- <li>积 分：${UsernameAndScore.score }</li> -->
							<li>彩 票：${UsernameAndScore.score }</li>
							<li><a href="${ctx }/frontEnd/accountCenter/mygift"	class="text-info">查看我的礼品</a></li>
							<li><a href="${ctx }/frontEnd/giftcenter/index?c_flag=1" class="btn btn-info">看看我可以兑换什么礼品？</a></li>
							<li><a href="${ctx }/frontEnd/accountCenter/accountInfo"
								class="text-muted">彩票明细 &raquo;</a></li>
						</ul>
					</div>
				</div>
			</c:if>


			<!-- 广告位和其他 | 手机端不显示内容 -->
			<div class="hidden-xs side-widget">
			<c:forEach items="${adsList }" var="var" varStatus="status">
				<a target="_blank" href="${var.articleUrl }"><img src="${var.url }" alt="${var.adsName }" class="margin-bottom"></a>
			</c:forEach>
			</div>


		</div>
		<!-- /.col-sm-4 -->

		<div class="col-sm-8 col-md-9">
			<!-- 筛选 分类参考京东的一级菜单名称-->
			<div class="well item-filter">
				<ul class="nav nav-pills" style="border-bottom: dotted 2px #ddd;">
					<li class="nav-pills-title">分 类：</li>
					<li id="commodity_sort_li_0" value="0" <c:if test="${UsernameAndScore.sort==0 }"> class="active" </c:if> ><a href="#" id="commodity_sort_0" value="0">全 部</a></li>
					<c:forEach items="${commodityCategoryList }" var="commodityCategory" varStatus="varStatus">
						<li id="commodity_sort_li_${varStatus.count }" value="${commodityCategory.id }" <c:if test="${UsernameAndScore.sort==commodityCategory.id }"> class="active" </c:if> > <a id="commodity_sort_${varStatus.count }" value="${commodityCategory.id }" href="#">${commodityCategory.name }</a></li>
					</c:forEach>
				</ul>
				<ul class="nav nav-pills">
					<li class="nav-pills-title">排 序：</li>
					<!-- <li class="active"><a href="#">我可以兑换的礼品</a></li>  -->
					<li id="commodity_order_li_0" value="0" <c:if test="${UsernameAndScore.order==0 or UsernameAndScore.order==10}">class="active"</c:if> ><a id="commodity_order_0"  value="0"  href="#">彩票数</a></li>
					<li id="commodity_order_li_1" value="1" <c:if test="${UsernameAndScore.order==1 or UsernameAndScore.order==11}">class="active"</c:if> ><a id="commodity_order_1"  value="1"  href="#">热 门</a></li>
					<li id="commodity_order_li_2" value="2" <c:if test="${UsernameAndScore.order==2 or UsernameAndScore.order==12}">class="active"</c:if> ><a id="commodity_order_2"  value="2"  href="#">新上架</a></li>
				</ul>
			</div>


			<!-- 消息显示 -->
			<!-- 我可以换取的礼物 -->

			<c:if test="${UsernameAndScore.showWeChooseFor==1 }">
				<div class="alert alert-success">
					<p>
						<i class="fa fa-smile-o fa-fw" style="color: #529ecc;"></i>我们为你挑选了以下礼品
					</p>
				</div>
			</c:if>

			<!-- 没有我可以换取的礼物，积分不够 -->

			<c:if test="${UsernameAndScore.enoughScore==0 }">
				<div class="no-items text-center">
					<div class="alert alert-warning">
						<h3>
							<i class="fa fa-frown-o fa-fw" style="color: #e97878;"></i>哎哟~
							你的彩票不够哦~!
						</h3>
						<p>
							玩玩游戏放松一下，大奖等你来拿哦!<a href="/frontEnd/game/list"> 进入游戏 &raquo;</a>
						</p>
					</div>
					<a href="/frontEnd/game/list"><img
						src="/static/static-page/img/no-items.jpg" alt=""></a>
				</div>
			</c:if>
			<!-- /.消息显示 -->




			<!-- 商品列表 -->
			<div class="row">
				<c:forEach items="${ page}" var="commodity">
					<div class="col-sm-6 col-md-4">
						<div class="thumbnail gift-item">
							<div class="text-center">
								<a href="${ctx }/frontEnd/giftcenter/show/${commodity.id }"><img height="400px" width="300px" src="${commodity.indexPagePictureUrl }"
									alt=""></a>
							</div>
							<span class="price">${commodity.price } 彩票</span>
							<div class="caption">
								<h4>${commodity.name }</h4>
								<small>已兑换数: ${commodity.exchangeCount }</small>
								<a class="btn btn-lg btn-block btnRedeem"
									href="${ctx }/frontEnd/giftcenter/show/${commodity.id }"><span
									class="glyphicon glyphicon-gift text-danger"></span>&nbsp;兑 换</a>
							</div>
						</div>
					</div>
				</c:forEach>
				<!-- 商品列表 -->



			</div>

			<!-- 分页 -->
				<div class="text-center">
					<c:if test="${pageRequest.totalCount>0 }">
					<tags:pag page="${pageRequest}" url="${ctx}/frontEnd/giftcenter/index" id="giftcenter_page_" />
					</c:if>
					<!-- <ul class="pagination">
						<li class="disabled"><a href="#">&laquo;</a></li>
						<li class="active"><a href="#">1</a></li>
						<li><a href="#">2</a></li>
						<li><a href="#">3</a></li>
						<li><a href="#">4</a></li>
						<li><a href="#">5</a></li>
						<li><a href="#">&raquo;</a></li>
					</ul> -->
				</div>
				<!-- /.分页 -->
			<!-- /.col-sm-8 -->

		</div>
		<!-- ./ row -->
	</div>
</div>

<!-- end container -->

<!-- <a href="http://wpa.qq.com/msgrd?v=3&amp;uin=937007032&amp;site=qq&amp;menu=yes" target="_blank" class="kf-btn"><i class="fa fa-headphones fa-2x"></i></a> -->
<a href="http://wpa.qq.com/msgrd?v=3&uin=2875109253&site=qq&menu=yes"
	target="_blank" class="kf-btn"><span
	class="glyphicon glyphicon-headphones"></span>&nbsp;客服</a>
