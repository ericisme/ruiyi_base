<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />


<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
		<meta name="renderer" content="webkit">
        <meta charset="utf-8">
        <meta http-equiv=”X-UA-Compatible” content=”IE=Edge,chrome=1″ >
        <title><sitemesh:title/></title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no" >





		<!-- add by Fanzz 2013/11/21 -->
		<c:if test="${  fn:indexOf(pageContext.request.requestURI,'/frontEnd/alipay')  ge 0 }">
			<link href="${ctx }/static/artDialog/cashier.main-2.22.css" rel="stylesheet" media="all">
		</c:if>


        <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->



        <link rel="stylesheet" href="${ctx }/static/static-page/fonts/awesome/css/font-awesome.min.css">
        <!--[if IE 7]>
        <link rel="stylesheet" href="${ctx }/static/static-page/fonts/awesome/css/font-awesome-ie7.min.css">
        <![endif]-->
        <!--   <link rel="stylesheet" href="${ctx }/static/static-page/css/styles.min.css"> -->
        <link rel="stylesheet" href="${ctx }/static/static-page/css/bootstrap.css">
       <!--  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script> -->
		<link rel="stylesheet" href="${ctx }/static/static-page/css/main.css">

        <!-- <script src="//ajax.aspnetcdn.com/ajax/jQuery/jquery-1.10.2.min.js"></script> -->
        <!-- <script>window.jQuery || document.write('<script src="${ctx }/static/static-page/js/vendor/jquery-1.10.2.min.js"><\/script>')</script> -->
        <script src="${ctx }/static/static-page/js/vendor/jquery-1.10.2.min.js"></script>
        <script type="text/javascript">
        	//检查登录
        	$(document).ready(function(){
        		checkLoginFunction();
        	})
		</script>

    </head>
    <body>

      <!-----------------------------------------------------------------------------------------------
       __    __  .__   __.  __       _______.     _______      ___      .___  ___.  _______
      |  |  |  | |  \ |  | |  |     /       |    /  _____|    /   \     |   \/   | |   ____|
      |  |  |  | |   \|  | |  |    |   (----`   |  |  __     /  ^  \    |  \  /  | |  |__
      |  |  |  | |  . `  | |  |     \   \       |  | |_ |   /  /_\  \   |  |\/|  | |   __|
      |  `--'  | |  |\   | |  | .----)   |      |  |__| |  /  _____  \  |  |  |  | |  |____
       \______/  |__| \__| |__| |_______/        \______| /__/     \__\ |__|  |__| |_______|
		世宇游戏
      --------------------------------------------------------------------------------------------------->
        <!--[if lt IE 9]>
            <p class="browsehappy">你用的浏览器 <strong>版本太低啦</strong>. 为了你可以正常显示网页, 建议你更新到IE8以上浏览器，或者chrome浏览器，360浏览器请使用极速模式 <a href="http://browsehappy.com/">upgrade your browser</a></p>
        <![endif]-->

         <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="${ctx }/static/static-page/js/vendor/html5shiv.js"></script>
        <script src="${ctx }/static/static-page/js/vendor/respond.min.js"></script>
        <script src="${ctx }/static/static-page/js/vendor/jquery.placeholder.min.js"></script>
        <script>
        	$('input, textarea').placeholder();
        </script>
        <![endif]-->

        <!-- 广告 -->
        <div class="toptop_ad hidden-xs" style="display:none;">
          <div class="container">
            <div class="alert-dismissable top_ad_banner" style="display:none;">
              <button type="button" class="close" id="close1" data-dismiss="alert" aria-hidden="true">&times;</button>
              <img id="show_ad" src="http://lorempixel.com/800/50" alt="" width="100%" >
            </div>
          </div>
        </div>
        <div class="top_ad_content hidden-xs" style="display:none;">
          <button type="button" class="close" id="close2">&times;</button>
          <embed src="http://player.youku.com/player.php/Type/Folder/Fid/17940672/Ob/1/sid/XNzI4MDE5MDI0/v.swf" quality="high" width="960" height="600" align="middle" allowScriptAccess="always" allowFullScreen="true" mode="transparent" type="application/x-shockwave-flash"></embed>
          <div class="clearfix"></div>
          <a href="" class="btn btn-primary btn-lg" style="margin-top:10px;">下 载</a>
        </div>
        

        <div id="wrappp">
        <!-- top navigation -->
        <div class="topbar">
                <div class="container text-center">
                    <ul class="list-inline row">
                        <li id="head_fly" class="col-sm-4 col-md-4 unislogo center1 bounceInLeft "><a href="${ctx}/">世宇游戏平台</a></li>
                        <li class="col-sm-3 center1 hidden-xs">
                            <form method="get" action="http://www.baidu.com/baidu"  id="search_form" target="_blank">
                            	<input name="ct" type=hidden value="2097152">
                              	<input name="si" type=hidden value="www.shiyugame.com">
                            	<div class="input-group" id="search">
                              		<input type="text" name="word" id="s_word" class="form-control" placeholder="南瓜大本营..." x-webkit-speech onkeydown="if(event.keyCode==13) {document.getElementById('search_form').submit();}">
                              		<span class="input-group-addon"><a href="javascript:document.getElementById('search_form').submit()"><span class="glyphicon glyphicon-search"></span></a></span>
                            	</div>
                            </form>
                        </li>

                        <li class="login col-sm-2 col-sm-offset-2 col-md-1 col-md-offset-3 center1 pull-right" style="display:none;" id="notLogin">
                        	<!-- <button id="loginBtn" class="btn btn-sm" data-toggle="modal" data-target="#modal-login-form">登 录</button> -->
                        	<button id="loginBtn" class="btn btn-sm">登录 / 注册</button>
                        	<script>
                        		//登 录 按钮事件
        						$("#loginBtn").click(function(){
        							walo3LeggedLogin();
          						});
                        	</script>
                        </li>


                        <li class="member-icon col-sm-5 center1" style="display:none;" id="alreadyLogin">
                          <div class="dropdown" >
                              <a href="#" class="dropdown" data-toggle="dropdown">
                              	<button class="member-name btn btn-default"><span id="loginedUserName"></span>&nbsp;</button>
                              </a>

                              <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                              	<input type="hidden" id="userType" value=""/>
                                <li role="presentation" id="loginedUserInfoLi" style="display:none;">
                                	<a role="menuitem" tabindex="1" href="${ctx}/frontEnd/accountCenter/accountInfo" ><i class="fa fa-user"></i> 会员中心</a>
                                </li>
                                <li role="presentation">
                                	<a id="changePassword_for_system_user" onclick="refreshForm()" role="menuitem" tabindex="1" href="#" data-toggle="modal" data-target="#modal-login-form"><i class="fa fa-cog"></i> 修改密码</a>
                                	<a id="changePassword_for_walo_user"  href="${ctx }/frontEnd/accountCenter/changePassord" ><i class="fa fa-cog"></i> 修改密码</a>

                                </li>
                                <li role="presentation" id="backEndBtnLi" style="display:none;">
                                	<a role="menuitem" tabindex="1" href="#" id="backEndBtnA"><i class="fa fa-sign-in"></i> 进入后台</a>
                                </li>
                                <li role="presentation" class="divider"></li>
                                <li role="presentation">
                                	<a role="menuitem" id="loginedUserLogout" tabindex="1" href="javascript:logout();"><i class="fa fa-power-off text-danger"></i> 注销</a>
                                </li>
                              </ul>
                          </div>
                        </li>

                    </ul>
                </div>
        </div>

        <!-- Static navbar -->
        <div class="navbar navbar-default navbar-static-top">
          <div class="container">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="${ctx}/">世宇游戏平台</a>


            </div>
            <div class="navbar-collapse collapse">
              <ul class="nav navbar-nav unis-nav">
                <li id="title_index"          ><a href="${ctx}/"><span class="glyphicon glyphicon-home"></span> 首 页</a></li>
                <li id="title_news"           ><a href="${ctx}/frontEnd/news/list">活动中心</a></li>
                <li id="title_games"          ><a href="${ctx}/frontEnd/game/list">游戏中心</a></li>
                <li id="title_money"          ><a href="${ctx}/frontEnd/alipay/sycoin">充值中心</a></li>

                <!-- <li id="title_mall"       ><a href="#">礼品平台</a></li>  -->
                <!-- <li id="title_account_center"><a href="${ctx}/frontEnd/">会员中心</a></li> -->
              </ul>
              <ul class="nav navbar-nav navbar-right unis-nav">
                <!-- <li id="title_bbs"        ><a href="http://bbs.shiyugame.com" target="_blank">世宇社区</a></li> -->
				<li id="title_account_center" ><a href="${ctx }/frontEnd/accountCenter/accountInfo">会员中心</a></li>
                <li id="title_mall"       ><a href="${ctx}/frontEnd/giftcenter/index">礼品中心</a></li>

                <li id="title_game_center"  style="display:none;" class="highlighted"><a href="#" id="title_game_center_a">游场管理</a></li>
              </ul>
            </div><!--/.nav-collapse -->
          </div> <!-- end container -->
        </div>  <!-- end nav bar -->


        <!-- model login form -->
        <div class="modal fade" id="modal-login-form" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel" aria-hidden="true">
          <div class="modal-dialog login-width">
            <div class="modal-content">
              <div class="modal-header text-center">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="loginModalLabel">修改密码</h4>
              </div>

              <div class="modal-body">
                <form class="form-horizontal" role="form" id="mainForm">
                  <div class="form-group">
                    <label  class="col-sm-3 control-label">原 密 码</label>
                    <div class="col-sm-7">
                      <input type="password" class="form-control shake" id="oldPassword" autocomplete="off" name="oldPassword" placeholder="请输入原密码">
                      <span class="label label-warning pull-right" style="display:none;" id="usernameWrongSpan">  密码错误 </span>
                    </div>
                  </div>
                  <div class="form-group">
                    <label  class="col-sm-3 control-label">新 密 码</label>
                    <div class="col-sm-7">
                      <input type="password" class="form-control shake" id="newPassword" autocomplete="off" name="newPassword" placeholder="请输入新密码">
                    </div>
                  </div>
                   <div class="form-group">
                    <label  class="col-sm-3 control-label">确 认 密 码</label>
                    <div class="col-sm-7">
                      <input type="password" class="form-control shake" id="newPasswordAgain" autocomplete="off" name="newPasswordAgain" placeholder="请再次输入新密码">
                      <span class="label label-warning pull-right" style="display:none;" id="confirmPasswordWrongSpan"> 密码输入不一致 </span>
                    </div>
                  </div>
                  <div class="form-group">
                    <div class="col-xs-6 col-sm-offset-3 col-sm-4 col-md-offset-3 col-md-3">
                    	<input type="button" class="btn btn-default btn-primary btn-block"  onclick="modifyPassword(1);" value="确认"/>
                          <!-- <small><a href="">忘记密码</a></small> -->
                    </div>
                  </div>
                </form>
              </div> <!-- end modal body -->

              <div class="modal-footer">
              	<!--
                <button type="button" class="btn btn-primary btn-success">还没有注册?</button> -->
              </div>

            </div><!-- /.modal-content -->
          </div><!-- /.modal-dialog -->
        </div><!-- /. model login form -->

	<div>
		<sitemesh:body/>
	</div>

</div> <!-- /.wrappp -->


      <div id="footer">
          <div class="container">
          	<span id="admin_login_span"><p><a href="${ctx }/backEnd/loginIndex">管理用户登录</a></p></span>
            <p class="text-muted credit">Unis Game <a href="#">世宇科技</a> <a href="#">&copy; 2014</a>.</p>
            <p class="text-muted credit">备案号:<a href="http://www.miibeian.gov.cn">粤ICP备05070169号-3</a></p>
            <p class="text-muted credit">经营许可:<a href="/static/business_license/BusinessLicense.jpg" target="_blank">粤网文[2014]0405-105号</a></p>
          </div>
      </div>
		<script type="text/javascript" src="${ctx }/static/js/jQuery.md5.js"></script>
		<script type="text/javascript" src="${ctx }/static/js/base/base.js"></script>
		<script type="text/javascript" src="${ctx }/static/js/syspurview/account/account.js"></script>
        <script src="${ctx }/static/static-page/js/vendor/modernizr-2.6.2.min.js"></script>
        <script src="${ctx }/static/static-page/js/vendor/bootstrap.min.js"></script>
        <script src="${ctx }/static/static-page/js/plugins.js"></script>
        <script src="${ctx }/static/static-page/js/main.js"></script>
        <script type="text/javascript" src="${ctx }/static/RSA/RSA.js"></script>
		<script type="text/javascript" src="${ctx }/static/RSA/BigInt.js"></script>
		<script type="text/javascript" src="${ctx }/static/RSA/Barrett.js"></script>
        <script type="text/javascript">
        	/**
        	 *walo用户登录
        	 */
        	 function walo3LeggedLogin(){
        		 window.location.href='${ctx }/frontEnd/walo3LeggedLogin?protocol='+window.location.protocol+'&host='+window.location.host;
        	}

			/**
	 		* 登录动作
	 		*/
			function login() {
				$("#inputUsername1").removeClass("animated");
				$("#inputPassword1").removeClass("animated");
				$("#usernameWrongSpan").hide();
				$("#passwordWrongSpan").hide();
				var username = $("#inputUsername1").val();
				var password = $("#inputPassword1").val();
				//取RSA public key
				$.ajax({
		   			url:'${ctx}/frontEnd/getLoginRSAPublicKey',
		   			type:'post', //数据发送方式
		   			dataType:'JSON', //接受数据格式      
		   			data:null, //要传递的数据      
		   			success:
			   			function (data){//回传函数(这里是函数名)
							//RSA加密
							setMaxDigits(130);
							var key = new RSAKeyPair(data.empoent,"",data.module);
							var en_uri_pawword = encodeURIComponent(password);
							var s_password = encryptedString(key, en_uri_pawword);

							//login
							$.ajax({
								type : 'POST',
								url : "${ctx}/frontEnd/login?"+"username="+username,
								data : "s_password="+s_password,
								dataType : 'HTML',
								success : loginCallback,
								error : function(XMLHttpRequest, textStatus, errorThrown) {
											base.alert('出现未知异常，操作失败！');
										}
							});
		       			}
				});
			}
			function loginCallback(result) {
				if (result == "success1" || result == "success2" || result == "success3") {
					window.location.reload();
				} else {
					if(result == "用户名不存在系统！"){
						$("#usernameWrongSpan").show();
						$("#inputUsername1").addClass("animated");
						return;
					}
					if(result == "密码错误！"){
						$("#passwordWrongSpan").show();
						$("#inputPassword1").addClass("animated");
						return;
					}
					alert(result);
				}
			}
			//注销本系统帐号
			function logout(){
				if(!confirm("您确定要注销并退出系统吗?")){
					return;
				}
				window.location.href="${ctx }/logout";
			}
			//注销walo帐号
			function logoutWaloAccount(){
				if(!confirm("您确定要注销并退出系统吗?")){
					return;
				}
				window.location.href="${ctx }/frontEnd/walo3LeggedLogout";
			}
			//用户名输入框监听回车键
			//document.getElementById("inputUsername1").onkeydown = function(e){
			//    var currKey=0,e=e||event;
			//    if(e.keyCode==13){
			//    	login();
			//    }
			//};
			//密码输入框监听回车键
			//document.getElementById("inputPassword1").onkeydown = function(e){
			//    var currKey=0,e=e||event;
			//    if(e.keyCode==13){
			//   	login();
			//    }
			//};
		</script>
        <script type="text/javascript">
        //window.onload=function(){
        //	checkLoginFunction();
        //}
        //$(document).ready(function(){
        //	checkLoginFunction();
        //})
        function checkLoginFunction(){
			$.ajax({
	   			url:'${ctx}/frontEnd/checkLogin',
	   			type:'post', //数据发送方式
	   			dataType:'JSON', //接受数据格式      
	   			data:null, //要传递的数据      
	   			success:
		   			function (data){//回传函数(这里是函数名)
		   				var userName = data.userName;
		   				var userType = data.userType;
		   				if(userName == null){
		   					$("#notLogin").fadeIn(50);
		   					//$("#alreadyLogin").hide();
		   					//$("#loginRSAprivateKey_module").val(data.module);
		   					//$("#loginRSAprivateKey_empoent").val(data.empoent);
		   				}else{
		   					//$("#notLogin").hide();
		   					$("#alreadyLogin").fadeIn(50);
		   					$("#admin_login_span").hide();
		   					$("#loginedUserName").html(userName);
		   					$("#userType").val(userType);
		   					if(userType == 1){
		   						$("#backEndBtnLi").show();
		   						$("#backEndBtnA").attr("href",data.backEndPath);
		   						$("#loginedUserLogout").attr("href","javascript:logout();");
		   						$("#changePassword_for_system_user").show();
		   						$("#changePassword_for_walo_user").hide();
		   					}
		   					if(userType == 2 || userType == 3){
		   						$("#title_game_center").show();
		   						$("#title_game_center_a").attr("href",data.gameCenterPath);
		   						$("#loginedUserLogout").attr("href","javascript:logout();");
		   						$("#changePassword_for_system_user").show();
		   						$("#changePassword_for_walo_user").hide();
		   					}
		   					if(userType == 9){
		   						$("#loginedUserInfoLi").show();
		   						$("#loginedUserLogout").attr("href","javascript:logoutWaloAccount();");
		   						$("#changePassword_for_system_user").hide();
		   						$("#changePassword_for_walo_user").show();
		   						//$("#changePassword_for_walo_user").attr("href",data.waloPath+"/account/password.html?uk="+data.userKey);
		   					}
		   				}
	       			}
			});
        }
      
        //统计下载量
        function countDownloadTimes(platformAndMarket_id){
            $.ajax({
    			type : 'GET',
    			url : "${ctx}/frontEnd/counts/game/countGameDownloadTimes?"+"platformAndMarket_id="+platformAndMarket_id
    		});
        }
		</script>
        <script type="text/javascript">
        	//控制菜单的active
        	$("#"+$("#sitemeshtitle").text()).addClass("active");
        	//if($("#sitemeshtitle").text() == "title_index"){$("#head_fly").addClass("animated");}
		</script>
 		<script>
/*  			//控制顶头广告与全屏广告显示与关闭
 			if(window.location.href.indexOf("/frontEnd/index")>0){
 				$(".top_ad_banner").show(); 
 				if(getCookie("closed_top_ad_content")==null )
 					$(".top_ad_content").fadeIn(); 
 			}
            $("#show_ad").click(function(){
              $(".top_ad_content").fadeIn();
            });
            $("#close2").click(function(){            	
              $(".top_ad_content").fadeOut();
              SetCookie("closed_top_ad_content", true);
            });            
			function SetCookie(name,value)//两个参数，一个是cookie的名子，一个是值
            {
            	var Days = 7; //此 cookie 将被保存 30 天
            	var exp = new Date();    //new Date("December 31, 9998");
            	exp.setTime(exp.getTime() + Days*24*60*60*1000);
				document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
			}
			function getCookie(name)//取cookies函数 
			{
				var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
				if(arr != null) return unescape(arr[2]); return null;
              }     */       
        </script>
    </body>
</html>

