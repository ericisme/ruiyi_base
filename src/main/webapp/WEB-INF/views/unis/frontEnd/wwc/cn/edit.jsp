<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta name="renderer" content="webkit">
<meta charset="utf-8">
<meta http-equiv=”X-UA-Compatible” content=”IE=Edge,chrome=1″ >
<title>世宇|UNIS</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
<!--  <link rel="stylesheet" href="/static/wwc/css/styles.css"> -->
        <link rel="stylesheet" href="/static/wwc/css/bootstrap.min.css">
        <link rel="stylesheet" href="/static/wwc/css/form.min.css">



<link rel="stylesheet" href="/static/wwc/css/datetimepicker.min.css">
<script src="/static/wwc/js/vendor/modernizr-2.6.2.min.js"></script>
<script src="/static/wwc/js/vendor/jquery-1.10.2.min.js"></script>
<script src="/static/wwc/js/cookies.js"></script>
<script>

	 $().ready(function() {
		 var pickup = $('#pickUpOrNot').val() == '1';
		if (pickup) {
			$('#flight_no').fadeIn(150);
		}

		$('#languageselect a').click(function(){
			var rel = $(this).attr("rel");

			setCookie('language', rel, 30);

			if(rel=='0'){
				$(this).attr("href",'/wwc/en/edit');
			}else{
				$(this).attr("href",'/wwc/cn/edit');
			}
		});


		//点击之后清除错误提醒
		$('[role=form] input,textarea').click(function(){
			$(this).removeClass('error');
		});

	});
</script>
</head>
<body>
	<!--[if lt IE 9]>
            <p class="browsehappy">你使用的浏览器版本太低啦, 建议你使用火狐或Chrome以获得最好体验.
                <a href="http://www.firefox.com.cn/">升级到火狐浏览器</a> @世宇技术支持
            <br>
            You are using an <strong>outdated</strong> browser, please upgrade your browser to improve your experience.
             <a href="https://www.mozilla.org/en-US/">upgrade to Firfox</a> @UNIS Tech.</p>
        <![endif]-->

	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
        <script src="/static/wwc/js/vendor/html5shiv.js"></script>
        <script src="/static/wwc/js/vendor/respond.min.js"></script>
        <script src="/static/wwc/js/vendor/jquery.placeholder.min.js"></script>
        <![endif]-->

	<div class="container">
		<div class="header">
		<a href="/">
			<img src="/static/wwc/img/logo.png" alt="" class="visible-xs"
				width="150"><img src="/static/wwc/img/logo.png" alt=""
				class="hidden-xs"></a>
			<h2 class="clearfix signup-heading">全球發佈會2014註冊</h2>
			<a href="/wwc/index" class="btn btn-lg" style="color: #56bc8a;"><span
				class="glyphicon glyphicon-circle-arrow-left"></span> 返回</a>
		</div>

		<div class="overlap">
			<div class="primary-content">
				<div id="languageselect" class="language">
					<a rel="1"  class="btn btn-lg btn-lan active">中 文</a>
					<a rel="0"  class="btn btn-lg btn-lan">English</a>
				</div>
				<div class="clearfix"></div>
				<div class="signup_form">
					<!-- 表单错误提示 -->
					<div id="err" class="alert alert-danger alert-dismissable hidden">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>請檢查以下紅色表格是否有誤, 請填寫正確后再遞交, 謝謝.
					</div>
					<form role="form" action="/wwc/cn/save" method="post" onsubmit="return chineseFormCheck();">

						<input type="hidden" name="id" value="${invitationForm.id }" />
						<div class="row">
							<div class="col-sm-12">
								<!-- Company name* -->
								<div class="form-group">
									<label class="sr-only" for="companyname">公司名稱 *</label>
									<input type="text" class="form-control" id="companyname" name="companyname" placeholder="貴公司名稱 *" value="${invitationForm.companyName }" maxlength="${length.companyName }">
								</div>
							</div>
						</div>
						<!-- /.row -->

						<div class="row">
							<!-- Arrival Date/Time -->
							<div class="col-sm-6">
								<h4 for="" class="signup_label">預計到達日期</h4>
								<input type="text" class="form-control col-sm-6" name="arrvialdate" readonly id="arrvial-date" placeholder="2014-10-20" value="${invitationForm.arriveDateStr }">
							</div>

							<div class="col-sm-6">
								<h4 for="" class="signup_label">地区</h4>
								<select name="country" class="form-control" >
									${country_chinese}
								</select>
							</div>
							<%-- <div class="col-sm-6 hidden">
								<input type="hidden" id="pickUpOrNot" value="${invitationForm.pickUpOrNot}"/>
								<h4 for="" class="signup_label">是否需要為您提供機場接送?</h4>
								<div class="row">
									<div class="col-xs-3">
										<label class="signup_input">
										<input type="radio" name="pickup" id="pickup_no" value="false" <c:if test="${invitationForm.pickUpOrNot == null || invitationForm.pickUpOrNot == 0  }">checked</c:if>>否
										</label>
									</div>
									<div class="col-xs-3">
										<label class="signup_input">
										<input type="radio" name="pickup" id="pickup_yes" value="true" <c:if test="${invitationForm.pickUpOrNot == 1 }">checked</c:if>>是
										</label>
									</div>
									<div class="col-xs-6">
										<input style="display: none;" type="text" class="form-control" id="flight_no" name="flight_no" placeholder="航班號碼 *" value="${invitationForm.flightNo }" maxlength="${length.flightNo }">
									</div>
								</div>
							</div> --%>
						</div>
						<!-- /.row -->
						<!-- step 1 basic info -->
						<div class="row">
							<div class="col-sm-12">
								<h3>
									<span class="steplabel">1</span>您的基本信息
								</h3>
								<p class="help-block">*請把常務聯繫人寫在第一欄, 我們稍後會有專人與您聯繫,以確保你的旅程安排順利</p>
								<div id="group_applicant">
									<!-- 如果没有填写记录 -->
									<c:if test="${invitationForm == null || fn:length(invitationForm.invitationFormApplicantList) == 0 }">
										<div class="applicant">
											<h4></h4>
											<button class="btn btn-default close" type="button" onclick="deleteApplicant(this)">× 刪除</button>
											<div class="clearfix"></div>
											<div class="row">
												<div class="col-xs-8">
													<div class="form-group">
														<input type="text" class="form-control" name="app_fullname" placeholder="姓名 *" onkeyup="nameCard(this);" maxlength="${length.name }">
													</div>
												</div>
												<div class="col-xs-4">
													<select class="form-control" name="app_gender">
														<option value="-1">性別</option>
														<option value="0">男</option>
														<option value="1">女</option>
													</select>
												</div>
											</div>
											<!-- /.row -->

											<div class="row">
												<div class="col-sm-6">
													<div class="form-group">
														<input type="text" class="form-control" name="app_position" placeholder="職位" maxlength="${length.position }">
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-sm-6">
													<div class="form-group">
														<input type="text" class="form-control" name="app_mobilenumber" placeholder="手機號碼 *" maxlength="${length.mobileNumber }">
													</div>
												</div>
												<div class="col-sm-6">
													<div class="form-group">
														<input type="email" class="form-control" name="app_email" placeholder="email地址" maxlength="${length.email }">
													</div>
												</div>
											</div>
										</div>
										<!-- /.applicant -->
									</c:if>

									<!-- 已经填写记录 -->
									<c:if test="${fn:length(invitationForm.invitationFormApplicantList) > 0 }">
										<c:forEach items="${invitationForm.invitationFormApplicantList }" var="item" varStatus="status">

											<div class="applicant">

												<h4>${item.name}</h4>

												<button class="btn btn-default close" type="button" onclick="deleteApplicant(this)">× 刪除</button>

												<input type="hidden" name="app_id" value="${item.id }" />

												<input type="hidden" name="app_identityCode" value="${item.identityCode }" />

												<div class="clearfix"></div>
												<div class="row">
													<div class="col-xs-8">
														<div class="form-group">
															<input type="text" class="form-control" name="app_fullname" placeholder="姓名 *" onkeyup="nameCard(this);" value="${item.name }" maxlength="${length.name }">
														</div>
													</div>
													<div class="col-xs-4">
														<select class="form-control" name="app_gender">
															<option value="-1" <c:if test="${item.gender !=0 && item.gender !=1 }"> selected="selected"</c:if>>性別</option>
															<option value="0"  <c:if test="${item.gender ==0 }"> selected="selected"</c:if>>男</option>
															<option value="1"  <c:if test="${item.gender ==1 }"> selected="selected"</c:if>>女</option>
														</select>
													</div>
												</div>
												<!-- /.row -->

												<div class="row">
													<div class="col-sm-6">
														<div class="form-group">
															<input type="text" class="form-control" name="app_position" placeholder="職位" value="${item.position }" maxlength="${length.position }">
														</div>
													</div>
												</div>
												<div class="row">
													<div class="col-sm-6">
														<div class="form-group">
															<input type="text" class="form-control" name="app_mobilenumber" placeholder="手機號碼 *" value="${item.mobileNumber }" maxlength="${length.mobileNumber }">
														</div>
													</div>
													<div class="col-sm-6">
														<div class="form-group">
															<input type="email" class="form-control" name="app_email" placeholder="email地址 " value="${item.email }" maxlength="${length.email }">
														</div>
													</div>
												</div>
											</div>
											<!-- /.applicant -->
										</c:forEach>
									</c:if>

								</div>
								<!-- /.group applicant -->


									<div id="add_applicant_div" <c:if test="${fn:length(invitationForm.invitationFormApplicantList) == 3 || invitationForm.isSure == 1}"> class="hidden" </c:if> >
										<div class="row">
											<div class="col-sm-12">
												<button type="button" id="add_applicant" onclick="addApplicant()" class="add btn btn-default btn-block">
													<span class="glyphicon glyphicon-plus"></span> 添加一個 (最多3個)
												</button>
											</div>
										</div>
									</div>



							</div>
							<!-- /.col-sm-12 -->
						</div>
						<!-- /.row -->
						<!-- /.step 1 basic info -->

						<!-- step 2 Arrangement -->
						<div class="row">
							<div class="col-sm-12">
								<h3>
									<span class="steplabel">2</span>食宿安排
								</h3>
								<h4>
									<span class="glyphicon glyphicon-home"></span>&nbsp;&nbsp;住宿
								</h4>
								<div class="row">
									<div class="col-xs-4">
										<label class="signup_input input-block">
										<input type="radio" name="hotel" id="hotel1" value="21" <c:if test="${invitationForm == null || invitationForm.hotel == 21 }"> checked</c:if>> 金钻酒店
										</label>
									</div>
									<div class="col-xs-4">
										<label class="signup_input input-block">
										<input type="radio" name="hotel" id="hotel2" value="32" <c:if test="${invitationForm.hotel == 32 }"> checked</c:if>> 金沙酒店
										</label>
									</div>
									<div class="col-xs-4">
										<label class="signup_input input-block">
										<input type="radio" name="hotel" id="hotel-none" value="-1" <c:if test="${invitationForm.hotel == -1 || invitationForm.hotel == null }"> checked</c:if>> 不用,謝謝
										</label>
									</div>
								</div>


								<!-- 餐饮 -->
								<h4>
									<span class="glyphicon glyphicon-cutlery"></span>&nbsp;&nbsp;餐飲
								</h4>
								<div class="row">
									<div class="col-sm-4 hidden">
										<label class="signup_input input-block">
										<input type="radio" name="food" id="food1" value="1" <c:if test="${invitationForm==null || invitationForm.dinnerType == 1 }"> checked</c:if>> 中餐
										</label>
									</div>
									<div class="col-sm-4 hidden">
										<label class="signup_input input-block">
										<input type="radio" name="food" id="food2" value="2" <c:if test="${invitationForm.dinnerType == 2 }"> checked</c:if>> 西餐
										</label>
									</div>
									<div class="col-sm-4">
										<input type="text" class="form-control" id="food3" name="special_diet" placeholder="特別需求? 如素食者" value="${invitationForm.specialDiet }">
									</div>
								</div>

								<!-- 活动 -->
								<div class="hidden">
									<h4>
										<span class="glyphicon glyphicon-heart"></span>&nbsp;&nbsp;娱乐
									</h4>
									<div class="row">
										<div class="col-sm-6">
											<label class="signup_input input-block">
											<input type="checkbox" name="activity1" id="activity1" <c:if test="${invitationForm.nightTourOfQiRiver == 1 }"> checked</c:if>> 1. 岐江夜遊（10月20晚）
											</label>
										</div>

										<div class="col-sm-6">
											<label class="signup_input input-block">
											<input type="checkbox" name="activity2" id="activity2" <c:if test="${invitationForm.visitFormerResidenceOfSunYatSen == 1 }"> checked</c:if>> 2. 孫中山故居（10月20~22下午）
											</label>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<label class="signup_input input-block">
											<input type="checkbox" name="activity3" id="activity3" <c:if test="${invitationForm.shopping == 1 }"> checked</c:if>> 3. 旅游购物
											</label>
										</div>
										<div class="col-sm-6">
											<label class="signup_input input-block">
											<input type="checkbox" name="activity4" id="activity4" <c:if test="${invitationForm.entertainment == 1 }"> checked</c:if>>4. 娛樂活動（10月20、21日晚）
											</label>
										</div>
									</div>
								</div>


							</div>
							<!-- /.col-sm-12 -->
						</div>
						<!-- /.row -->
						<!-- /.step 2 Arrangement -->

						<!-- step 3 Arrangement -->
						<div class="row">
							<div class="col-sm-12">
								<h3>
									<span class="steplabel">3</span>建议
								</h3>
								<textarea class="form-control margin-bottom" rows="3" name="message" placeholder="您的其它需要或建议" maxlength="${length.message }">${invitationForm.message }</textarea>


								<div class="hidden">
									<p>为了感谢贵司的支持，大会现场将设有合作伙伴信息展示环节，请问贵司是否愿意参加？</p>
									<div class="row">
										<div class="col-sm-3">
											<label class="signup_input input-block">
											<input type="radio" name="join" id="join_no" value="no" checked> 不用,謝謝!
											</label>
										</div>
										<div class="col-sm-3">
											<label class="signup_input input-block">
											<input type="radio" name="join" id="join_yes" value="yes"> 是的
											</label>
										</div>
										<div class="col-sm-6">
											<div id="send_us" style="display: none;">
												<a href=mailto:hlj@zs-shiyu.com class="btn btn-default btn-block"><span class="glyphicon glyphicon-paperclip text-info"></span> 附件發到email</a>
												<p class="help-block"> 發送公司LOGO圖片和視頻資料給我們 <br>郵箱: hlj@zs-shiyu.com | QQ:1554684868 </p>
											</div>
										</div>
									</div>

								 </div>
								<!-- /.row -->
							</div>
							<!-- /.col-sm-12 -->
						</div>
						<!-- /.row -->

						<button class="btn btn-block btn-primary btn-lg" type="submit" <c:if test="${invitationForm.isSure==1 }">disabled</c:if> >

						<c:if test="${invitationForm.isSure!=1 }">遞交</c:if>

						<c:if test="${invitationForm.isSure==1 }">信息已被管理员确认，不能修改</c:if>

						</button>



					</form>
				</div>
				<!-- /.signup_form -->
			</div>
			<!-- /.primary-content -->
		</div>
		<!-- /.overlap -->
		<div class="margin-bottom" style="color: #ededed; margin-top: 2em;">
			<small> <span class="glyphicon glyphicon-phone-alt"></span>
				0760-23886157 <br> <span class="glyphicon glyphicon-envelope"></span>
				hlj@zs-shiyu.com <br> QQ: 1554684868 <br> © 2014 世宇科技 Unis
				Technology, Inc.
			</small>
		</div>
	</div>

	<script src="/static/wwc/js/vendor/bootstrap.min.js"></script>
	<!-- <script src="/static/wwc/js/vendor/jquery.placeholder.min.js"></script> -->
	<script src="/static/wwc/js/vendor/icheck.min.js"></script>
	<script src="/static/wwc/js/vendor/bootstrap-datetimepicker.min.js"></script>
	<script src="/static/wwc/js/vendor/bootstrap-datetimepicker.zh-CN.js"
		charset="UTF-8"></script>
	<script src="/static/wwc/js/plugins.js"></script>
	<script src="/static/wwc/js/main.js"></script>
	<script>
		$('#arrvial-date').datetimepicker({
			format : 'yyyy-mm-dd',
			language : 'zh-CN',
			startView : 2,
			minView : 2,
			autoclose : 1
		});
	</script>
</body>
</html>