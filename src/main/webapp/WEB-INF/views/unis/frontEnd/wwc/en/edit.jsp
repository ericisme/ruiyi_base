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
		 //接机选项
		var pickup = $('#pickUpOrNot').val() == '1';
		if (pickup) {
			$('#flight_no').fadeIn(150);
		}
		//语言选择，切换并且记录种类
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
			<h2 class="clearfix signup-heading">Register for UNIS Global Partner's Expo</h2>
			<a href="/wwc/index" class="btn btn-lg" style="color: #56bc8a;"><span class="glyphicon glyphicon-circle-arrow-left"></span> Back</a>
		</div>

		<div class="overlap">
			<div class="primary-content">
				<div id="languageselect" class="language">
					<a rel="1" href="/wwc/cn/edit" class="btn btn-lg btn-lan ">中 文</a>
					<a rel="0" href="/wwc/en/edit" class="btn btn-lg btn-lan active">English</a>
				</div>
				<div class="clearfix"></div>
				<div class="signup_form">
					<!-- 表单错误提示 -->
					<div id="err" class="alert alert-danger alert-dismissable hidden">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						Bummer! It looks like there was an error in the information you
						gave us. Take a look at your sign-up form for more details.
					</div>
					<form role="form" action="/wwc/en/save" method="post" onsubmit="return englishFormCheck();">
						<input type="hidden" name="id" value="${invitationForm.id }" />
						<div class="row">
							<div class="col-sm-12">
								<!-- Company name* -->
								<div class="form-group">
									<label class="sr-only" for="companyname">Company name *</label>
									<input type="text" class="form-control" id="companyname" name="companyname" placeholder="Company name *" value="${invitationForm.companyName }" maxlength="${length.companyName }">
								</div>
							</div>
						</div>
						<!-- /.row -->

						<div class="row">
							<!-- Arrival Date/Time -->
							<div class="col-sm-6">
								<h4 for="" class="signup_label">Arrival Date/Time(2014-10-18)</h4>
								<input type="text" class="form-control col-sm-6" name="arrvialdate" id="arrvial-date" placeholder="2014-10-18" readonly value="${invitationForm.arriveDateStr }" />
							</div>

							<div class="col-sm-6">
								<h4 for="" class="signup_label">Country or region</h4>
								<select name="country" class="form-control" >
									${country_english}
								</select>
							</div>
							<!-- 接机选项关闭 -->
							<%-- <div class="col-sm-6 hidden">
								<h4 for="" class="signup_label">Do you need airport pick up service?</h4>


								<div class="row">

								<input type="hidden" id="pickUpOrNot" value="${invitationForm.pickUpOrNot}"/>

									<div class="col-xs-3">
										<label class="signup_input"> <input type="radio" name="pickup" id="pickup_no" value="false" <c:if test="${invitationForm.pickUpOrNot == null || invitationForm.pickUpOrNot == 0  }">checked</c:if>>No</label>
									</div>
									<div class="col-xs-3">
										<label class="signup_input"> <input type="radio" name="pickup" id="pickup_yes" value="true" <c:if test="${invitationForm.pickUpOrNot == 1 }">checked</c:if>> Yes</label>
									</div>
									<div class="col-xs-6">
										<input style="display: none;" type="text" class="form-control" id="flight_no" name="flight_no" placeholder="Flight Number *" value="${invitationForm.flightNo }" maxlength="${length.flightNo }">
									</div>
								</div>
							</div> --%>
						</div>
						<!-- /.row -->
						<!-- step 1 basic info -->
						<div class="row">
							<div class="col-sm-12">
								<h3>
									<span class="steplabel">1</span>Your basic infomation
								</h3>
								<p class="help-block">*Make sure the <strong>first contact</strong> is mostly available, we may contact you later.</p>

								<div id="group_applicant">
								<!-- 如果没有填写记录 -->
									<c:if test="${invitationForm == null || fn:length(invitationForm.invitationFormApplicantList) == 0 }">
										<div class="applicant">
											<h4></h4>
											<!-- 关闭按钮 -->
											<button class="btn btn-default close" type="button" onclick="deleteApplicant(this)">×delete</button>
											<div class="clearfix"></div>
											<div class="row">
											<!-- 全名用 firstname 和 lastname -->
												<%-- <div class="col-xs-8">
													<div class="form-group">
														<input type="text" class="form-control" placeholder="Full Name *" name="app_fullname" onkeyup="nameCard(this);" maxlength="${length.name }">
													</div>
												</div> --%>

												<div class="col-xs-6">
	                                              <div class="form-group">
	                                                <input type="text" class="form-control"  name="firstName" maxlength="${length.firstName }" placeholder="First name *" onkeyup="nameCard(this);">
	                                              </div>
                                            	</div>
	                                            <div class="col-xs-6">
	                                              <div class="form-group">
	                                                <input type="text" class="form-control"  name="lastName" maxlength="${length.lastName }" placeholder="Last name *">
	                                              </div>
	                                            </div>

											</div>
											<!-- /.row -->

											<div class="row">
												<div class="col-xs-6">
													<select class="form-control" name="app_gender">
														<option value="-1">Gender</option>
														<option value="0">Male</option>
														<option value="1">Female</option>
													</select>
												</div>
												<div class="col-sm-6">
													<div class="form-group">
														<input type="text" class="form-control" name="app_position" placeholder="Position" maxlength="${length.position }">
													</div>
												</div>
											</div>
											<div class="row">

												<div class="col-sm-6">
													<div class="form-group">
														<input type="email" class="form-control" name="app_email" placeholder="email *" maxlength="${length.email }">
													</div>
												</div>
												<div class="col-sm-6">
	                                                <div class="form-group">
	                                                  	<input type="email" class="form-control"  name="confirmEmail" placeholder="confirm email *">
	                                                 </div>
                                              	</div>
											</div>
											<div class="row">
												 <div class="col-sm-6">
													<div class="form-group">
														<input type="text" class="form-control" name="app_mobilenumber" placeholder="mobile number " maxlength="${length.mobileNumber }">
													</div>
												</div>
											</div>
										</div>
										<!-- /.applicant -->
									</c:if> <!-- end of 没有填写记录 -->

									<!-- 已经填写记录 -->
									<c:if test="${fn:length(invitationForm.invitationFormApplicantList) > 0 }">
										<c:forEach items="${invitationForm.invitationFormApplicantList }" var="item" varStatus="status">
											<div class="applicant">
												<h4>${item.firstName}</h4>
												<button class="btn btn-default close" type="button" onclick="deleteApplicant(this)">×delete</button>

												<input type="hidden" name="app_id" value="${item.id }" />
												<input type="hidden" name="app_identityCode" value="${item.identityCode }" />

												<div class="clearfix"></div>
												<div class="row">
													<%--
													<div class="col-xs-8">
														<div class="form-group">
															<input type="text" class="form-control" placeholder="Full Name *" name="app_fullname" onkeyup="nameCard(this);" value="${item.name }" maxlength="${length.name }">
														</div>
													</div> --%>

												 	<div class="col-xs-6">
	                                              		<div class="form-group">
	                                                		<input type="text" class="form-control"  name="firstName" maxlength="${length.firstName }" placeholder="First name *" value="${item.firstName }"onkeyup="nameCard(this);">
	                                              		</div>
                                            		</div>
	                                            	<div class="col-xs-6">
	                                              		<div class="form-group">
	                                                		<input type="text" class="form-control" name="lastName" maxlength="${length.lastName }" value="${item.lastName }" placeholder="Last name *">
	                                              		</div>
	                                            	</div>

												</div>
												<!-- /.row -->

												<div class="row">
													<div class="col-xs-6">
														<select class="form-control" name="app_gender">
															<option value="-1" <c:if test="${item.gender !=0 && item.gender !=1 }"> selected="selected"</c:if>>Gender</option>
															<option value="0"  <c:if test="${item.gender ==0 }"> selected="selected"</c:if>>Male</option>
															<option value="1"  <c:if test="${item.gender ==1 }"> selected="selected"</c:if>>Female</option>
														</select>
													</div>
													<div class="col-sm-6">
														<div class="form-group">
															<input type="text" class="form-control" name="app_position" placeholder="Position" value="${item.position }" maxlength="${length.position }">
														</div>
													</div>
												</div>
												<div class="row">

													<div class="col-sm-6">
														<div class="form-group">
															<input type="email" class="form-control" name="app_email" placeholder="email *" value="${item.email }" maxlength="${length.email }">
														</div>
													</div>
													<div class="col-sm-6">
		                                                <div class="form-group">
		                                                  	<input type="email" class="form-control" name="confirmEmail"  value="${item.email }" placeholder="confirm email *">
		                                                 </div>
                                              		</div>
												</div>

												<div class="row">
												 	<div class="col-sm-6">
														<div class="form-group">
															<input type="text" class="form-control" name="app_mobilenumber" placeholder="mobile number " value="${item.mobileNumber }" maxlength="${length.mobileNumber }">
														</div>
													</div>
												</div>
											</div>
											<!-- /.applicant -->
										</c:forEach>

									</c:if>
									<!-- end of 已经填写记录 -->


								</div>
								<!-- /.group applicant -->

								<!-- 如果存在记录，少于三条并且还没有锁定表单 -->

									<div id="add_applicant_div" <c:if test="${fn:length(invitationForm.invitationFormApplicantList) == 3 || invitationForm.isSure == 1}"> class="hidden" </c:if> >
										<div class="row">

											<div class="col-sm-12">
												<button type="button" id="add_applicant" onclick="addApplicant()" class="add btn btn-default btn-block">
													<span class="glyphicon glyphicon-plus"></span> Add a guest(max 3)
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
									<span class="steplabel">2</span>Arrangement
								</h3>
								<p class="help-block">*Please click on the relevant options you would like to choose.</p>
								<h4>
									<span class="glyphicon glyphicon-home"></span>&nbsp;&nbsp;Accommodation
								</h4>
								<div class="row">
									<div class="col-xs-6">
										<label class="signup_input input-block"> <input type="radio" name="hotel" id="hotel1" value="11" <c:if test="${invitationForm==null || invitationForm.hotel == 11 }"> checked</c:if>>Sheraton Hotel
										</label>

									</div>
									<div class="col-xs-6">
										<label class="signup_input input-block"> <input type="radio" name="hotel" id="hotel-none" value="-1" <c:if test="${invitationForm.hotel == -1 ||  invitationForm.hotel == null}"> checked</c:if>>No, thanks.
										</label>
									</div>
								</div>
								<!-- 餐饮 -->
								<h4>
									<span class="glyphicon glyphicon-cutlery"></span>&nbsp;&nbsp;Food preference
								</h4>
								<div class="row">
									<div class="col-sm-4 hidden">
										<label class="signup_input input-block"> <input type="radio" name="food" id="food1" value="1" <c:if test="${invitationForm == null ||invitationForm.dinnerType == 1 }"> checked</c:if>>Chinese cuisine
										</label>
									</div>
									<div class="col-sm-4 hidden">
										<label class="signup_input input-block"> <input type="radio" name="food" id="food2" value="2" <c:if test="${invitationForm.dinnerType == 2 }"> checked</c:if>> Western cuisine
										</label>
									</div>
									<div class="col-sm-4">
										<input type="text" class="form-control" id="food3" name="special_diet" placeholder="Special Diet? Like vegetarian" value="${invitationForm.specialDiet }">
									</div>
								</div>



								<h4>
									<span class="glyphicon glyphicon-heart"></span>&nbsp;&nbsp;Recreation
								</h4>
								<p>*Please select the activities that you would like to join in which will be available to you free of charge</p>
								<div class="row">
									<div class="col-sm-12">
										<label class="signup_input input-block"> <input type="checkbox" name="activity1" id="activity1" <c:if test="${invitationForm.nightTourOfQiRiver == 1 }"> checked</c:if>> 1. Night tour of Qi River
										</label>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<label class="signup_input input-block"> <input type="checkbox" name="activity2" id="activity2" <c:if test="${invitationForm.visitFormerResidenceOfSunYatSen == 1 }"> checked</c:if>> 2. Visit Former Residence of Sun-Yat-Sen
										</label>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
										<label class="signup_input input-block"> <input type="checkbox" name="activity3" id="activity3" <c:if test="${invitationForm.shopping == 1 }"> checked</c:if>> 3. Shopping
										</label>
									</div>
									<div class="col-sm-6">
										<label class="signup_input input-block"> <input type="checkbox" name="activity4" id="activity4" <c:if test="${invitationForm.entertainment == 1 }"> checked</c:if>> 4. Entertainment
										</label>
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
									<span class="steplabel">3</span>Additional Contents
								</h3>

								<textarea class="form-control margin-bottom" rows="3" name="message" placeholder="Please let us know of any further information required or requests" maxlength="${length.message }">${invitationForm.message }</textarea>
								<div class="hidden">
									<p>Do you like us to promote your company during the event?</p>
									<div class="row">
										<div class="col-sm-3">
											<label class="signup_input input-block">
												<input type="radio" name="join" id="join_no" value="no" checked>No, thanks!
											</label>
										</div>
										<div class="col-sm-3">
											<label class="signup_input input-block">
												<input type="radio" name="join" id="join_yes" value="yes">Yes, please.
											</label>
										</div>
										<div class="col-sm-6">
											<div id="send_us" style="display: none;">
												<a href=mailto:hlj@zs-shiyu.com class="btn btn-default btn-block"><span class="glyphicon glyphicon-paperclip text-info"></span> send email</a>
												<p class="help-block"> send your company LOGO or VIDEO to us via email or QQ <br>email:hlj@zs-shiyu.com | QQ:1554684868</p>
											</div>
										</div>
									</div>
								</div>
								<!-- /.row -->
							</div>
							<!-- /.col-sm-12 -->
						</div>
						<!-- /.row -->

						<button class="btn btn-block btn-primary btn-lg" type="submit"  <c:if test="${invitationForm.isSure==1 }">disabled</c:if> >
						 <c:if test="${invitationForm.isSure!=1 }">Submit</c:if>
						 <c:if test="${invitationForm.isSure==1 }">Infomation has Confirmed by <br/>adminstrator,can't not modify</c:if>
						</button>


					</form>
				</div>
				<!-- /.signup_form -->
			</div>
			<!-- /.primary-content -->
		</div>
		<!-- /.overlap -->
		<div class="margin-bottom" style="color: #ededed; margin-top: 2em;">
			<small> <span class="glyphicon glyphicon-envelope"></span>
				If you have any questions or need additional information please, email us: marketing@universal-space.com <br>
				© 2014 世宇科技 Unis Technology, Inc.
			</small>
		</div>
	</div>



	<script src="/static/wwc/js/vendor/bootstrap.min.js"></script>
	<!-- <script src="/static/wwc/js/vendor/jquery.placeholder.min.js"></script> -->
	<script src="/static/wwc/js/vendor/icheck.min.js"></script>
	<script src="/static/wwc/js/plugins.js"></script>
	<script src="/static/wwc/js/main.js"></script>
	<script src="/static/wwc/js/vendor/bootstrap-datetimepicker.min.js"></script>
	<script src="/static/wwc/js/vendor/bootstrap-datetimepicker.zh-CN.js"
		charset="UTF-8"></script>
	<script>
		$('#arrvial-date').datetimepicker({
			format : 'yyyy-mm-dd',
			// language: 'zh-CN',
			//minuteStep: 15,
			//autoclose: 1
			startView : 2,
			minView : 2,
			autoclose : 1
		});
	</script>
</body>
</html>
