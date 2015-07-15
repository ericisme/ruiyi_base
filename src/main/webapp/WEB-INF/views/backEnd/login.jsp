<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>

<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="${ctx }/static/static-page/favicon.png">

<title>Unis Login</title>

<!-- Bootstrap core CSS -->
        <link rel="stylesheet" href="${ctx }/static/static-page/fonts/awesome/css/font-awesome.min.css">
        <!--[if IE 7]>
        <link rel="stylesheet" href="${ctx }/static/static-page/fonts/awesome/css/font-awesome-ie7.min.css">
        <![endif]-->
        <link rel="stylesheet" href="${ctx }/static/static-page/css/styles.min.css">
       <!--  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script> -->

<!-- Custom styles for this template -->
<link href="${ctx }/static/static-page/css/signin.css" rel="stylesheet">

<script type="text/javascript" src="${ctx }/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx }/static/js/base/base.js"></script>
<script type="text/javascript" src="${ctx }/static/RSA/RSA.js"></script>  
<script type="text/javascript" src="${ctx }/static/RSA/BigInt.js"></script>  
<script type="text/javascript" src="${ctx }/static/RSA/Barrett.js"></script> 
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="${ctx }/static/static-page/js/vendor/html5shiv.js"></script>
        <script src="${ctx }/static/static-page/js/vendor/respond.min.js"></script>
        <script src="${ctx }/static/static-page/js/vendor/jquery.placeholder.min.js"></script>

        <![endif]-->   
<script type="text/javascript">
	var baseUrl = "${ctx}/backEnd/login";
	/**
	 * 登录动作
	 */
	function login() {		
		var username = $("#username").val();
		var password = $("#password").val();	
		if(username==""){
			alert("用户名不能为空！");
			$("#username").focus();
			return;
		}
		if(password==""){
			alert("密码不能为空！");
			$("#password").focus();
			return;
		}
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
						url : baseUrl+"?username="+username,
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
		if (result == "success1") {			//后台用户，跳转到后台
			base.forward("/backEnd/frame");
		} else if(result == "success2"){	//游乐场用户，跳转到前台
			base.forward("/");
		} else if(result == "success3"){	//游乐场世宇管理员，跳转到前台
			base.forward("/");
		} else{
			alert(result);
		}
	}
</script>
     
    
</head>

<body>

	<div class="container">

		<form class="form-signin" id="mainForm" name="mainForm">
			<h2 class="form-signin-heading">管理用户登录</h2>
			<input type="text"     class="form-control" name="username" id="username" value="" placeholder="用户名" autofocus> 
			<input type="password" class="form-control" name="password" id="password" value="" placeholder="密码">
			<!-- <label class="checkbox">
          <input type="checkbox" value="remember-me"> Remember me
        </label> -->
			<button class="btn btn-lg btn-primary btn-block" type="button" onclick="login();">登录</button>
			<button class="btn btn-lg btn-primary btn-block" type="button" onclick="window.location.href='${ctx}/'">取消</button>
		</form>

	</div>
	<!-- /container -->
	<script type="text/javascript">
		$('input, textarea').placeholder();
	</script>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
</body>
</html>
