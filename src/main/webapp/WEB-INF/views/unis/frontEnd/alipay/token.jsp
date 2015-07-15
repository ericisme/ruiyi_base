<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script src="${ctx }/static/artDialog/jquery.artDialog.js?skin=default"></script>
<script src="${ctx }/static/artDialog/iframeTools.js"></script>
<script src="${ctx }/static/static-page/js/jquery.validate.js"></script>
<script>


	var token = ${entity.token};
	var discount = ${entity.discount};
	var rechargetype = '${entity.rechargetype}';
	var rate ='${entity.rate}';

	var tmpJson = '${entity.gameSelectTmpDataJson }';
	var gameSelectTmpDataJson ;//输入帐号后，返回的游戏列表，已经游乐场信息的json
	if(tmpJson!=''){
		gameSelectTmpDataJson =$.parseJSON ('${entity.gameSelectTmpDataJson }');
	}
	$(document).ready(function() {
		  var gameSelectTmp = '${entity.gameSelect}';
		  var gameCenterSelectTmp = '${entity.gameCenterSelect}';
		 $(gameSelectTmpDataJson).each(function(){
			 if(this.gameKey==gameSelectTmp || gameSelectTmp==null ||gameSelectTmp==''){
				 $('#gameCenterSelect').html('');
				 $(this.gameCenterList).each(function(){
					 if(gameCenterSelectTmp==this.gameCenterKey ||gameCenterSelectTmp==null||gameCenterSelectTmp==''){
						 if(gameCenterSelectTmp==this.gameCenterKey ){
							 $('#rateLabel').html("(兑换比例 = 1 ：" + this.changeRate + ")");
						 }
						 $('#gameCenterSelect').append('<option selected changeRate= "' + this.changeRate + '" id="gameCenterSelectId' +this.gameCenterKey+ '" value="' + this.gameCenterKey +  '">' + this.gameCenterName + '</option>');
					 }else{
						 $('#gameCenterSelect').append('<option changeRate= "' + this.changeRate + '" id="gameCenterSelectId' +this.gameCenterKey+ '" value="' + this.gameCenterKey +  '">' + this.gameCenterName + '</option>');
					 }
				 });
			 }
		 });

		var tmpSelectBank ;

		/*
			重定向时，重新设置选中的银行
		*/
		var bankselect = $('#bankselect').val();//设置在表单中
		if(bankselect==''){
			$('input[value=ICBCB2C]').prop("checked",true);
		}
		else if(bankselect!='no'){
			setChoosedBank(bankselect);
			$('#unisUnionpay').prop("checked",true);
			$('#unisAlipay').removeAttr("checked");
			$('input[value=' + bankselect+']').prop("checked",true);
		}
		else{
			$('input[value=ICBCB2C]').prop("checked",true);
		}

		$('#unisAlipay').click(function(){
			$('#bankchoosed').html('');
		});
		/*
			重定向的时候，刷新价格
		*/
		$('#rechargePrice').html(FormatNumber(token * discount, 2));

		/*
			验证码看不清刷新
		*/
		$('#changeVerifyCode').click(function() {
			$('#captchaCode').attr('src', '/static/captchaCode/alipay');
		});
		/*
			在点击币数选择后清除错误提示，并显示对应价格
		*/
		$('[id^=optionsCZ]').click(function() {
			$('#czMountDiv').removeClass('has-error');
			$('#czMountP').removeClass('text-danger');
			$('#token').val($(this).val());
			$('#rechargePrice').html(FormatNumber($(this).val() * discount, 2));
		});
		/*
			在点击同意协议清除错误提示
		*/
		$('#agreement').click(function(){
			$('#agreeErrorSpan').removeClass('on');
		});
		/*
			点击验证码输入框，清除错误提示
		*/
		$('#captcha').click(function(){
			$('#captchaCodeError').removeClass('on');
		});
		/*
			点击再次输入号码输入框，清除错误提示
		*/
		$('#confirmusername').click(function(){
			$('#czNumberErrorDiv').removeClass('has-error');
			$('#czNumberErrorSpan').removeClass('on');
		});
		/*
			点击充值数量，清除错误提示
		*/
		$('#token').click(function(){
			$('[id^=optionsCZ]').removeAttr('checked');
			$('#czMountDiv').removeClass('has-error');
			$('#czMountP').removeClass('text-danger');
			/*$.ajax({
				cache: true,
				dataType: "json",//返回json格式的数据
				type: "POST",
				url:"/frontEnd/alipay/rate",
				data:'consumerKey=' + $('#gameSelect').val() + '&gameCenterKey=' + $('#gameCenterSelect').val(),
				async: false,
			    error: function(request) {

			    },
			    success: function(data) {
			    	rate = data;
			    	$('#rate').val(rate);
			    	$('#rateLabel').html("(兑换比例 = 1 ：" + rate + ")");
			    }
			});*/


		});
		/*
			点击位于银行选择的完成按钮，radio选中改变
		*/
		$('#finished').click(function(){

			setChoosedBank(tmpSelectBank);
			$('#unisUnionpay').prop("checked",true);
			$('#unisAlipay').removeAttr("checked");
			$('#bankselect').val(tmpSelectBank);
		});
		$('#gameSelect').click(function(){
			$('#gamePickErrorSpan').removeClass('on');
		});
		$('#gameCenterSelect').click(function(){
			$('#gamePickErrorSpan').removeClass('on');
		});
		/*
			记录下被选中的银行
		*/
		$('input[id^=J-b2]').click(function(){
			tmpSelectBank = $(this).val();
		});

		$('#gameSelect').change(function(){
			$('#rateLabel').html("(兑换比例 = 1 ：1)");
			$('#token').val(0);
			$('#rechargePrice').html(FormatNumber($('#token').val() * discount, 2));

			var tmp = $('#gameSelect').val();
			 $(gameSelectTmpDataJson).each(function(){
				 if(this.gameKey==tmp){
					 $('#gameCenterSelect').html('');
					 var flag1 ="1";
					 $(this.gameCenterList).each(function(){
					 	$('#gameCenterSelect').append('<option changeRate= "' + this.changeRate + '" id="gameCenterSelectId' +this.gameCenterKey+ '" value="' + this.gameCenterKey +  '">' + this.gameCenterName + '</option>');
					 	if(flag1=="1"){
					 		$('#rateLabel').html("(兑换比例 = 1 ：" + this.changeRate + ")");
					 		flag1="2";
					 	}
					 });
				 }
			 });
		});

		$('#gameCenterSelect').change(function(){
			$('#token').val(0);
			$('#rechargePrice').html(FormatNumber($('#token').val() * discount, 2));

			$('#rateLabel').html("(兑换比例 = 1 ：" + $('#gameCenterSelectId' + $('#gameCenterSelect').val()).attr("changeRate") + ")");
		});
	});

	/*
		数字格式化
	*/
	function FormatNumber(srcStr, nAfterDot) {
		return new Number(srcStr).toFixed(nAfterDot);
	}

	/*
		检查表单
	 */
	function check() {
		var username = $('#username').val();
		var confirm = $('#confirmusername').val();
		var token = $('#token').val();
		var flag = true;
		if (username == '' || confirm == '' || username != confirm) {
			$('#czNumberErrorDiv').addClass('has-error');
			$('#czNumberErrorSpan').addClass('on');
			flag = false;
		}
		if(token <=0){
			$('#czMountDiv').addClass('has-error');
			$('#czMountP').addClass('text-danger');
			flag = false;
		}
		if(!($('#agreement').is(':checked'))){
			$('#agreeErrorSpan').addClass('on');
			flag = false;
		}

		if($('#gameSelect').val()==null || $('#gameCenterSelect').val()==null){
			$('#gamePickErrorSpan').addClass('on');
			flag = false;
		}
		return flag;

	}


	function onkeyUP() {
		$('#rechargePrice').html(FormatNumber($('#token').val() * discount, 2));
	}
	function onChange() {
		var token = $('#token').val();
		if (isNaN(token)) {
			$('#czMountDiv').addClass('has-error');
			$('#czMountP').addClass('text-danger');
			$('#token').val(0);
			$('#rechargePrice').html($('#token').val());
			return false;
		} else {
			 if (token % 1 != 0 || token<=0) {
				$('#czMountDiv').addClass('has-error');
				$('#czMountP').addClass('text-danger');
				$('#token').val(0);
			}else{
				$('#czMountDiv').removeClass('has-error');
				$('#czMountP').removeClass('text-danger');
			}
		}
		var tmpToken = $('#token').val() * discount;
		if(isNaN(tmpToken)){
			tmpToken = "0";
		}
		$('#rechargePrice').html(FormatNumber(tmpToken, 2));
	}

	function onConfirmczNumberKeyUp(){
		$.ajax({
			cache: true,
			dataType: "json",//返回json格式的数据
			type: "POST",
			url:"/frontEnd/alipay/check",
			data:'account=' + $('#confirmusername').val(),
			async: false,
		    error: function(request) {

		    },
		    success: function(data) {
		    	$('#gameSelect').html('');
		    	$('#gameCenterSelect').html('');
		    	gameSelectTmpDataJson = data;
				if (data != "") {
					var flag1 = "1";
					 $(data).each(function(){
							$('#gameSelect').append('<option id="gameSelectId' +this.gameKey+ '" value="' + this.gameKey +  '">' + this.gameName + '</option>');
							if(flag1=="1"){
								$('#gameCenterSelect').html('');
								var flag2 = "1";
								$(this.gameCenterList).each(function(){
									$('#gameCenterSelect').append('<option changeRate= "' + this.changeRate + '" id="gameCenterSelectId' +this.gameCenterKey+ '" value="' + this.gameCenterKey +  '">' + this.gameCenterName + '</option>');
									if(flag2=="1"){
										$('#rateLabel').html("(兑换比例 = 1 ：" + this.changeRate + ")");
										flag2="2";
									}
								});
							}
							flag1="2";
						});
				}
		    }
		});
	}

	function setChoosedBank(bank){
		var qiye = bank;
		if(qiye=='CCBBTB'){
			qiye='CCB';
		}else if(qiye=='BOCBTB'){
			qiye='BOC';
		}
		else if(qiye=='SPDBB2B'){
			qiye='SPDB';
		}
		else if(qiye=='CMBBTB'){
			qiye='CMB';
		}
		else if(qiye=='ICBCB2C'){
			qiye='ICBC';
		}else if(qiye=='POSTGC'){
			qiye='PSBC';
		}else if(qiye=='BOCB2C'){
			qiye='BOC';
		}else if(qiye=='CEBBANK'){
			qiye='CEB';
		}else if(qiye=='HZCBB2C'){
			qiye='HZCB';
		}
		$('#bankchoosed').html("<label class=\"icon-box limited-coupon\"><span class=\"false icon " + qiye +  "\"></span> </label>");
	}
</script>
<span id="sitemeshtitle" style="display: none">title_money</span>


<title>世宇游戏-充值中心</title>

<div class="container">

	<ol class="breadcrumb">
		<li><a href="/">首页</a></li>
		<li>充值中心</li>
	</ol>
	<!-- end breadcrumb -->
	<div class="row">

		<div class="col-sm-4 col-md-3">

			<div class="list-group">
				<a href="${ctx}/frontEnd/alipay/sycoin" class="list-group-item ">游币充值</a>
				<a href="${ctx}/frontEnd/alipay/token" class="list-group-item active">实体币充值</a>
			</div>

		</div>
		<!-- /.col-sm-4 -->

		<div class="col-sm-8 col-md-9">

			<div class="art-content">
				<div class="row">
					<div class="col-sm-8">
						<form role="form" id="formCZ" name="formCZ"
							action="${ctx }/frontEnd/alipay/request" method="post"
							>

							<input type="hidden" id="bankselect" name="bankselect" value="${entity.bankselect}"/>
							<input type="hidden" id="rate" name="rate" value="${entity.rate}"/>
							<input type="hidden" id="rechargetype" name="rechargetype" value="token"/>

							<div class="form-group">
								<label for="czNumber">充值账号:</label> <input type="text"
									autocomplete="off" class="form-control czNumber" id="username"
									name="username" placeholder="请输入账号" value="${entity.username }">
							</div>
							<div class="form-group " id="czNumberErrorDiv">
								<label for="czNumber">再次输入账号:</label> <input type="text"
									autocomplete="off" class="form-control czNumber"
									id="confirmusername" name="confirmusername"
									onpaste="return false;" placeholder="请再次输入账号"
									onkeyup="onConfirmczNumberKeyUp();"
									value="${entity.confirmusername }"> 	<span id="czNumberErrorSpan"

										<c:if test="${entity.userErrorReturnFlag>0 }">class="error alert-danger on"</c:if>
										<c:if test="${entity.userErrorReturnFlag==0}">class="error alert-danger"</c:if>

										><span class="glyphicon glyphicon-info-sign"></span>
										<c:if test="${entity.userErrorReturnFlag==0 || entity.userErrorReturnFlag==1 }">输入的账号不一致,请确认并再次输入</c:if>
										<c:if test="${entity.userErrorReturnFlag==2 }">输入的账号不存在或出现异常,请确认并再次输入</c:if>
										<c:if test="${entity.userErrorReturnFlag==3 }">输入的账号出现异常,请确认并再次输入userGrade</c:if>

										</span>
							</div>


							 <div id="gameorgamecenterpick" >
								<div class="form-group">
			                      <label for="czNumber">游戏选择:</label>
			                      <select  id="gameSelect" name="gameSelect" class="form-control unis-game-cz">
			                      		${entity.gameOptionHtml }
			                      </select>
                   			 	</div>
	                   			<div class="form-group">
				                   <label for="czNumber">游乐场选择:</label>
				                   <select id="gameCenterSelect" name="gameCenterSelect" class="form-control unis-game-cz">
				                   		${entity.gameCenterOptionHtml }
				                   </select>
	                   			</div>

	                   			<span
									id="gamePickErrorSpan" class="error alert-danger"><span
									class="glyphicon glyphicon-info-sign"></span>必须选择游戏和游乐场</span>
							 </div>

							 <div id="sycoin_recharge_mount">

								<div class="form-group">
									<div id="czMountDiv">
										<label for="optionsCZother">充值数量:</label> <input
										autocomplete="off"
											onkeyup="onkeyUP();" onchange="onChange();"
											onblur="onChange();" onmouseout="onChange();" type="text"
											name="token" value="${entity.token }" id="token"
											class="form-control otherInput"> 元

											<c:if test="${entity.rate==null}">
											<label id="rateLabel">
											(兑换比例 = 1 ：1)</label>
											</c:if>
											<c:if test="${entity.rate!=null}">
											<label id="rateLabel">
											(兑换比例 = 1 ：${entity.rate})</label>
											</c:if>

									</div>
								</div>

							</div>


							<div class="form-group">
								<label>充值方式:</label>
								<div class="clearfix"></div>
								<div class="radio-inline">
									<label> <input type="radio" name="optionsPay"
										id="unisAlipay" value="alipay" checked>支付宝
									</label>
								</div>
								<div class="radio-inline">
									<label> <input type="radio" name="optionsPay"
										id="unisUnionpay" value="bankpay" data-toggle="modal" data-target="#modal-bankpay-form"
										>网银
									</label>
								</div>
								<div id="bankchoosed" class="radio-inline">
									<!-- <label class="icon-box limited-coupon"><span class="false icon ICBC"></span> </label> -->
								</div>


							</div>
							<hr>
							<p>
								应付金额: <span class="fieldset-text"> <span
									id="rechargePrice"><strong>0.00</strong></span> 元
									<c:if test="${entity.discount < 1 }">
										<span id="discount_rate">( <fmt:formatNumber value="${entity.discount * 10 }" pattern="#0.0"/>  折)</span>
										</c:if>
									</span>
							</p>

							<div class="form-group">


								<label> <input type="checkbox" id="agreement"
									name="agreement"
									<c:if test="${entity.isAgree==1 }">checked</c:if>> 同意 <a
									href="agreement.html"
									target="${ctx }/frontEnd/alipay/agreement">服务条款</a>
								</label> <span id="agreeErrorSpan" class="error alert-danger"><span
									class="glyphicon glyphicon-info-sign"></span>必须同意服务条款！！！</span>
							</div>

							<div>

								<p>
									<label>验证码：</label> <input  type="text"
										id="captcha" name="captcha" size="5" autocomplete="off"
										class="form-control otherInput"
										value="${entity.captcha }" /> <span> <img
										id="captchaCode" src="/static/captchaCode/alipay" alt=""
										width="150" height="50" /></span> <a id="changeVerifyCode"
										name="changeVerifyCode" style="cursor: pointer; cursor: hand;">看不清楚？</a>
									<label id="captchaCodeLabel" name="captchaCodeLabel"
										class="error" style="display: none;">验证码不正确...</label> <span
										id="captchaCodeError"
										<c:if test="${entity.captchaFlag==true }">class="error alert-danger on"</c:if>
										<c:if test="${entity.captchaFlag==false }">class="error alert-danger"</c:if>><span
										class="glyphicon glyphicon-info-sign"></span> 验证码错误, 请再次输入！！！</span>

								</p>
							</div>
							<input id="submit" type="submit" onclick = "return check()" class="btn btn-primary"
								value="立即充值" />
						</form>


					</div>
					<!-- /.col-sm-8 -->

					<div class="col-sm-4">
						<div class="text-muted">
							<h3>温馨提示</h3>
							<p>* 消费者购买之世宇虚拟货币，均可在世宇集团服务范围内之手机游戏、街机游戏和游乐场中使用。</p>
						</div>
					</div>
					<!-- /.col-sm-4 -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.art-content -->

		</div>
		<!-- /.col-sm-8 -->

	</div>
	<!-- ./ row -->
</div>
<!-- end container -->


<link rel="shortcut icon" href="https://i.alipayobjects.com/e/201310/1HFsvDt0EB.ico" type="image/x-icon">
<noscript>&lt;link charset="utf-8" rel="stylesheet" href="https://a.alipayobjects.com/cashier/cashier.icon-1.3.css" media="all" /&gt;</noscript>
<link href="${ctx }/static/artDialog/apop.css">

 <style>
.icon-gray {
	filter: url("https://cashier.alipay.com:443/filters.svg#grayscale");
	filter: gray\9;
	-webkit-filter: grayscale(100%);
}

.repair,.repair2,.repair3,.restricted {
	_display: block;
}
</style>

 <style>
@font-face {
	font-family: "rei";
	src: url("https://i.alipayobjects.com/common/fonts/rei.eot?20131015");
	/* IE9 */
	src:
		url("https://i.alipayobjects.com/common/fonts/rei.eot?20131015#iefix")
		format("embedded-opentype"), /* IE6-IE8 */
		url("https://i.alipayobjects.com/common/fonts/rei.woff?20131015")
		format("woff"), /* chrome 6+、firefox 3.6+、Safari5.1+、Opera 11+ */
		url("https://i.alipayobjects.com/common/fonts/rei.ttf?20131015")
		format("truetype"),
		/* chrome、firefox、opera、Safari, Android, iOS 4.2+ */
		url("https://i.alipayobjects.com/common/fonts/rei.svg?20131015#rei")
		format("svg"); /* iOS 4.1- */
}

.iconfont {
	font-family: "rei";
	font-style: normal;
	font-weight: normal;
	cursor: default;
	-webkit-font-smoothing: antialiased;
}
</style>
<style type="text/css" class="iconStyle">
.ABC,.BJBANK,.BJRCB,.BOC,.CCB,.CDCB,.CEB,.CIB,.CITIC,.CMB,.CMBC,.COMM,.FDB,.GDB,.HZCB,.ICBC,.NBBANK,.PSBC,.SDB,.SHBANK,.SHRCB,.SPABANK,.SPDB,.WZCB
	{
	text-indent: -9999px;
	background-image:
		url("https://i.alipayobjects.com:443/combo.png?d=cashier&t=ABC,BJBANK,BJRCB,BOC,CCB,CDCB,CEB,CIB,CITIC,CMB,CMBC,COMM,FDB,GDB,HZCB,ICBC,NBBANK,PSBC,SDB,SHBANK,SHRCB,SPABANK,SPDB,WZCB&stamp=1383840000000");
}

.ABC {
	background-position: 0px -0px;
}

.BJBANK {
	background-position: 0px -36px;
}

.BJRCB {
	background-position: 0px -72px;
}

.BOC {
	background-position: 0px -108px;
}

.CCB {
	background-position: 0px -144px;
}

.CDCB {
	background-position: 0px -180px;
}

.CEB {
	background-position: 0px -216px;
}

.CIB {
	background-position: 0px -252px;
}

.CITIC {
	background-position: 0px -288px;
}

.CMB {
	background-position: 0px -324px;
}

.CMBC {
	background-position: 0px -360px;
}

.COMM {
	background-position: 0px -396px;
}

.FDB {
	background-position: 0px -432px;
}

.GDB {
	background-position: 0px -468px;
}

.HZCB {
	background-position: 0px -504px;
}

.ICBC {
	background-position: 0px -540px;
}

.NBBANK {
	background-position: 0px -576px;
}

.PSBC {
	background-position: 0px -612px;
}

.SDB {
	background-position: 0px -648px;
}

.SHBANK {
	background-position: 0px -684px;
}

.SHRCB {
	background-position: 0px -720px;
}

.SPABANK {
	background-position: 0px -756px;
}

.SPDB {
	background-position: 0px -792px;
}

.WZCB {
	background-position: 0px -828px;
}
</style>

 <style>
.ui-list-icons .healthy,.ui-list-icons .healthy-normal {
	display: block !important;
	right: auto;
	top: -18px;
	left: -1px;
	margin: 0;
	width: auto;
	color: #fff;
	padding: 1px 2px 0;
	line-height: 14px;
}

.ui-list-icons .healthy-normal {
	position: absolute;
	background: #46b900;
	width: 28px;
	height: 18px;
	color: #fff;
	text-align: center;
}

.ui-list-icons .healthy .icon,.ui-list-icons .healthy-normal .icon {
	display: none;
}

.ui-list-icons .busy {
	background: #ff9900;
}

.ui-list-icons .repair2 {
	background: #ff3333;
}

.ui-list-icons .repair2 {
	background: #999999;
}

.ui-list-icons .healthy-ok {
	background: #46b900;
}

.long-logo .ui-list-icons .icon-gray {
	filter: none;
}

.long-logo .ui-list-icons .icon-gray  span.icon {
	filter: url("https://cashier.alipay.com:443/filters.svg#grayscale");
	filter: gray\9;
}
</style>

<div class=" long-logo modal fade" id="modal-bankpay-form" tabindex="-1"
	role="dialog" aria-labelledby="loginModalLabel" aria-hidden="true">
	<div class="modal-dialog bankpay-width">
		<div class="modal-content ">
			<div id="container" class="xbox ui-form">
				<h2>支付宝的合作银行</h2>

					<ul class="ui-list-icons ui-four-icons fn-clear cashier-bank"
						id="J-chooseBankList" style="padding-top: 10px;">


						<li class="cashier-bank ">
							<input type="radio" name="channelToken" id="J-b2c_ebank-icbc105-1" value="ICBCB2C" >
						 	<label
								class="icon-box limited-coupon" for="J-b2c_ebank-icbc105-1"
								data-channel="biz-channelType(B2C_EBANK)-ICBC-中国工商银行-type(unsave)">

								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon ICBC" title="中国工商银行"></span>
								<span class="bank-name">中国工商银行</span>

							</label>
						</li>

						<li class="cashier-bank ">
							<input type="radio" name="channelToken" id="J-b2c_ebank-ccb103-2" value="CCB">
							<label
								class="icon-box limited-coupon " for="J-b2c_ebank-ccb103-2"
								data-channel="biz-channelType(B2C_EBANK)-CCB-中国建设银行-type(unsave)">

								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon CCB" title="中国建设银行"></span>
								<span class="bank-name">中国建设银行</span>
							</label>
						</li>

						<li class="cashier-bank ">
							<input type="radio" name="channelToken" id="J-b2c_ebank-abc101-3" value="ABC">
							<label
								class="icon-box limited-coupon " for="J-b2c_ebank-abc101-3"
								data-channel="biz-channelType(B2C_EBANK)-ABC-中国农业银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon ABC" title="中国农业银行"></span>
								<span class="bank-name">中国农业银行</span>
							</label>
						</li>

						<li class="cashier-bank ">
							<input type="radio" name="channelToken" id="J-b2c_ebank-psbc102-4" value="POSTGC">
							<label
								class="icon-box limited-coupon " for="J-b2c_ebank-psbc102-4"
								data-channel="biz-channelType(B2C_EBANK)-PSBC-中国邮政储蓄银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon PSBC" title="中国邮政储蓄银行"></span>
								<span class="bank-name">中国邮政储蓄银行</span>
							</label>
						</li>
						<!-- API中没有 -->
						<li class="cashier-bank ">
							<input type="radio" name="channelToken" id="J-b2c_ebank-comm102-5" value="COMM">
							<label
									class="icon-box limited-coupon " for="J-b2c_ebank-comm102-5"
									data-channel="biz-channelType(B2C_EBANK)-COMM-交通银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon COMM" title="交通银行"></span>
								<span class="bank-name">交通银行</span>
							</label>
						</li>

						<li class="cashier-bank ">
						<input type="radio" name="channelToken" id="J-b2c_ebank-cmb103-6" value="CMB">
							<label
									class="icon-box limited-coupon " for="J-b2c_ebank-cmb103-6"
									data-channel="biz-channelType(B2C_EBANK)-CMB-招商银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon CMB" title="招商银行"></span>
								<span class="bank-name">招商银行</span>
							</label>
						</li>

						<li class="cashier-bank ">
							<input type="radio" name="channelToken" id="J-b2c_ebank-boc102-7" value="BOCB2C">
							<label
									class="icon-box limited-coupon " for="J-b2c_ebank-boc102-7"
									data-channel="biz-channelType(B2C_EBANK)-BOC-中国银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon BOC" title="中国银行"></span>
								<span class="bank-name">中国银行</span>
							</label>
						</li>

						<li class="cashier-bank ">
							<input type="radio" name="channelToken" id="J-b2c_ebank-ceb102-8" value="CEBBANK">
							<label
									class="icon-box limited-coupon " for="J-b2c_ebank-ceb102-8"
									data-channel="biz-channelType(B2C_EBANK)-CEB-中国光大银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon CEB" title="中国光大银行"></span>
								<span class="bank-name">中国光大银行</span>
							</label>
						</li>

						<li class="cashier-bank ">
							<input type="radio" name="channelToken" id="J-b2c_ebank-citic104-9" value="CITIC">
							<label
								class="icon-box limited-coupon " for="J-b2c_ebank-citic104-9"
								data-channel="biz-channelType(B2C_EBANK)-CITIC-中信银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon CITIC" title="中信银行"></span>
								<span class="bank-name">中信银行</span>
							</label>
						</li>
						<!-- API中没有 -->
						<!-- <li class="cashier-bank "><input type="radio" name="channelToken" id="J-b2c_ebank-sdb102-10" value="SDB">
						<label
								class="icon-box limited-coupon " for="J-b2c_ebank-sdb102-10"
								data-channel="biz-channelType(B2C_EBANK)-SDB-深圳发展银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon SDB" title="深圳发展银行"></span>
								<span class="bank-name">深圳发展银行</span>
							</label>
						</li>
 -->
						<!-- 上海浦东发展银行 -->
						<li class="cashier-bank ">
							<input type="radio" name="channelToken" id="J-b2c_ebank-spdb103-11" value="SPDB">
							<label
									class="icon-box limited-coupon " for="J-b2c_ebank-spdb103-11"
									data-channel="biz-channelType(B2C_EBANK)-SPDB-浦发银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon SPDB" title="浦发银行"></span>
								<span class="bank-name">浦发银行</span>
							</label>
						</li>

						<li class="cashier-bank ">
							<input type="radio" name="channelToken" id="J-b2c_ebank-cmbc101-12" value="CMBC">
							<label
								class="icon-box limited-coupon " for="J-b2c_ebank-cmbc101-12"
								data-channel="biz-channelType(B2C_EBANK)-CMBC-中国民生银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon CMBC" title="中国民生银行"></span>
								<span class="bank-name">中国民生银行</span>
							</label>
						</li>

						<li class="cashier-bank "><input type="radio" name="channelToken" id="J-b2c_ebank-cib102-13" value="CIB">
							<label
								class="icon-box limited-coupon " for="J-b2c_ebank-cib102-13"
								data-channel="biz-channelType(B2C_EBANK)-CIB-兴业银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon CIB" title="兴业银行"></span>
								<span class="bank-name">兴业银行</span>
							</label>
						</li>

						<li class="cashier-bank ">
							<input type="radio" name="channelToken" id="J-b2c_ebank-spabank101-14" value="SPABANK">
							<label class="icon-box limited-coupon "
									for="J-b2c_ebank-spabank101-14"
									data-channel="biz-channelType(B2C_EBANK)-SPABANK-深圳平安银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon SPABANK" title="深圳平安银行"></span>
								<span class="bank-name">深圳平安银行</span>
							</label>
						</li>

						<li class="cashier-bank ">
							<input type="radio" name="channelToken" id="J-b2c_ebank-gdb102-15" value="GDB">
							<label
								class="icon-box limited-coupon " for="J-b2c_ebank-gdb102-15"
								data-channel="biz-channelType(B2C_EBANK)-GDB-广发银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon GDB" title="广发银行"></span>
								<span class="bank-name">广发银行</span>
							</label>
						</li>

						<li class="cashier-bank ">
							<input type="radio" name="channelToken" id="J-b2c_ebank-shrcb101-16" value="SHRCB">
							<label
								class="icon-box limited-coupon " for="J-b2c_ebank-shrcb101-16"
								data-channel="biz-channelType(B2C_EBANK)-SHRCB-上海农商银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon SHRCB" title="上海农商银行"></span>
								<span class="bank-name">上海农商银行</span>
							</label>
						</li>

						<li class="cashier-bank ">
							<input type="radio" name="channelToken" id="J-b2c_ebank-shbank102-17" value="SHBANK">
							<label
								class="icon-box limited-coupon " for="J-b2c_ebank-shbank102-17"
								data-channel="biz-channelType(B2C_EBANK)-SHBANK-上海银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon SHBANK" title="上海银行"></span>
								<span class="bank-name">上海银行</span>
							</label>
						</li>

						<li class="cashier-bank ">
							<input type="radio" name="channelToken" id="J-b2c_ebank-nbbank102-18" value="NBBANK">
							<label
								class="icon-box limited-coupon " for="J-b2c_ebank-nbbank102-18"
								data-channel="biz-channelType(B2C_EBANK)-NBBANK-宁波银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon NBBANK" title="宁波银行"></span>
								<span class="bank-name">宁波银行</span>
							</label>
						</li>

						<li class="cashier-bank ">
							<input type="radio" name="channelToken" id="J-b2c_ebank-hzcb102-19" value="HZCBB2C">
							<label
								class="icon-box limited-coupon " for="J-b2c_ebank-hzcb102-19"
								data-channel="biz-channelType(B2C_EBANK)-HZCB-杭州银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon HZCB" title="杭州银行"></span>
								<span class="bank-name">杭州银行</span>
							</label>
						</li>

						<li class="cashier-bank ">
							<input type="radio" name="channelToken" id="J-b2c_ebank-bjbank101-20" value="BJBANK">
							<label
								class="icon-box limited-coupon " for="J-b2c_ebank-bjbank101-20"
								data-channel="biz-channelType(B2C_EBANK)-BJBANK-北京银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon BJBANK" title="北京银行"></span>
								<span class="bank-name">北京银行</span>
							</label>
						</li>

						<li class="cashier-bank ">
							<input type="radio" name="channelToken" id="J-b2c_ebank-bjrcb101-21" value="BJRCB">
							<label
								class="icon-box limited-coupon " for="J-b2c_ebank-bjrcb101-21"
								data-channel="biz-channelType(B2C_EBANK)-BJRCB-北京农商行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon BJRCB" title="北京农商行"></span>
								<span class="bank-name">北京农商行</span>
							</label>
						</li>

						<li class="cashier-bank ">
							<input type="radio" name="channelToken" id="J-b2c_ebank-fdb101-22" value="FDB">
							<label
								class="icon-box limited-coupon " for="J-b2c_ebank-fdb101-22"
								data-channel="biz-channelType(B2C_EBANK)-FDB-富滇银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon FDB" title="富滇银行"></span>
								<span class="bank-name">富滇银行</span>
							</label>
						</li>

						<li class="cashier-bank ">
							<input type="radio" name="channelToken" id="J-b2c_ebank-wzcb101-23" value="WZCB">
							<label
								class="icon-box limited-coupon " for="J-b2c_ebank-wzcb101-23"
								data-channel="biz-channelType(B2C_EBANK)-WZCB-温州银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon WZCB" title="温州银行"></span>
								<span class="bank-name">温州银行</span>
							</label>
						</li>

						<li class="cashier-bank "><input type="radio" name="channelToken" id="J-b2c_ebank-cdcb101-24" value="CDCB">
							<label
								class="icon-box limited-coupon  current"
								for="J-b2c_ebank-cdcb101-24"
								data-channel="biz-channelType(B2C_EBANK)-CDCB-成都银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon CDCB" title="成都银行"></span>
								<span class="bank-name">成都银行</span>
							</label>
						</li>
						<!-- API中没有 -->
						<!-- <li class="cashier-bank-qy ">
							<input type="radio" name="channelToken" id="J-b2b_ebank-icbc102-25" value="ICBCicbc102_PAYMENT_DEBIT_EBANK_XBOX_MODEL">
							<label
								class="icon-box limited-coupon  " for="J-b2b_ebank-icbc102-25"
								data-channel="biz-channelType(B2B_EBANK)-ICBC-中国工商银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon ICBC" title="中国工商银行"></span>
								<span class="bank-name">中国工商银行</span>
								<em class="icon-info qiye">企业</em>
							</label>
						</li> -->

						<li class="cashier-bank-qy ">
							<input type="radio" name="channelToken" id="J-b2b_ebank-ccb104-26" value="CCBBTB">
							<label
								class="icon-box limited-coupon " for="J-b2b_ebank-ccb104-26"
								data-channel="biz-channelType(B2B_EBANK)-CCB-中国建设银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon CCB" title="中国建设银行"></span>
								<span class="bank-name">中国建设银行</span>
								<em style="margin-left: 32px" class="icon-info qiye">企业</em>
							</label>
						</li>
						<!-- API中没有 -->
						<!-- <li class="cashier-bank-qy ">
							<input type="radio" name="channelToken" id="J-b2b_ebank-abc102-27" value="ABCabc102_PAYMENT_DEBIT_EBANK_XBOX_MODEL">
							<label
								class="icon-box limited-coupon " for="J-b2b_ebank-abc102-27"
								data-channel="biz-channelType(B2B_EBANK)-ABC-中国农业银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon ABC" title="中国农业银行"></span>
								<span class="bank-name">中国农业银行</span>
								<em class="icon-info qiye">企业</em>
							</label>
						</li> -->
						<!-- API中没有 -->
						<!-- <li class="cashier-bank-qy ">
							<input type="radio" name="channelToken" id="J-b2b_ebank-cmb102-28" value="CMBcmb102_PAYMENT_DEBIT_EBANK_XBOX_MODEL">
							<label
								class="icon-box limited-coupon " for="J-b2b_ebank-cmb102-28"
								data-channel="biz-channelType(B2B_EBANK)-CMB-招商银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon CMB" title="招商银行"></span>
								<span class="bank-name">招商银行</span>
								<em class="icon-info qiye">企业</em>
							</label>
						</li> -->

						<li class="cashier-bank-qy ">
							<input type="radio" name="channelToken" id="J-b2b_ebank-boc104-29" value="BOCBTB">
							<label
								class="icon-box limited-coupon " for="J-b2b_ebank-boc104-29"
								data-channel="biz-channelType(B2B_EBANK)-BOC-中国银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon BOC" title="中国银行"></span>
								<span class="bank-name">中国银行</span>
								<em style="margin-left: 32px" class="icon-info qiye">企业</em>
							</label>
						</li>

						<li class="cashier-bank-qy ">
							<input type="radio" name="channelToken" id="J-b2b_ebank-spdb102-30" value="SPDBB2B">
							<label
								class="icon-box limited-coupon " for="J-b2b_ebank-spdb102-30"
								data-channel="biz-channelType(B2B_EBANK)-SPDB-浦发银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon SPDB" title="浦发银行"></span>
								<span class="bank-name">浦发银行</span>
								<em style="margin-left: 32px" class="icon-info qiye">企业</em>
							</label>
						</li>

						<li class="cashier-bank ">
						<input type="radio" name="channelToken" id="J-b2c_ebank-cmbbtb103-6" value="CMBBTB">
							<label
									class="icon-box limited-coupon " for="J-b2c_ebank-cmbbtb103-6"
									data-channel="biz-channelType(B2C_EBANK)-CMB-招商银行-type(unsave)">
								<span class="icon-box-sparkling" bbd="false"> </span>
								<span class="false icon CMB" title="招商银行"></span>
								<span class="bank-name">招商银行</span>
								<em style="margin-left: 32px" class="icon-info qiye">企业</em>
							</label>
						</li>
					</ul>
					<cite class="ui-list-tip"></cite>
					<p class="ui-btn ui-btn-ok">
						<input type="button" id="finished"  data-dismiss="modal" aria-hidden="true" class="ui-btn-text" value="完成"
							seed="next-step-btn">
					</p>
			</div>
		</div>
	</div>
</div>
