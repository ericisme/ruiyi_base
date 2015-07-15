<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
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
        <!-- <script>window.jQuery || document.write('<script src="/static/wwc/js/vendor/jquery-1.10.2.min.js"><\/script>')</script> -->
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
                <img src="/static/wwc/img/logo.png" alt="" class="visible-xs" width="150">
                <a href="/wwc/index">
                <img src="/static/wwc/img/logo.png" alt="" class="hidden-xs">
                </a>
                <h2 class="clearfix signup-heading">完成!</h2>
            </div>

            <div class="overlap">
                <div class="primary-content">
                  <div class="bs-callout bs-callout-info">
                    <h4>登記成功!</h4>
                   <p class="text-info">
                    進入會場前, 請出示以下二維碼換取你的入場身份證.<br>
                    二維碼已同時發送到你郵箱, 請查收.
                   </p>
                 </div>


                  <div class="row">
                  <c:forEach items="${successDtoList }" var="item" varStatus="status">
                         <div class="col-sm-4">
                      <div class="thumbnail text-center">
                        <img src="${item.qrcodeurl }" alt="..." width="160">
                        <div class="caption">
                          	<h4>${item.name}</h4>
                        </div>
                      </div>
                    </div>
                  </c:forEach>

                  </div>

                  <a href="/wwc/index" class="btn btn-info btn-block btn-lg">大會详情</a>
                </div> <!-- /.primary-content -->
            </div> <!-- /.overlap -->
            <div class="margin-bottom" style="color:#ededed; margin-top:2em;">
                <small>
                  <span class="glyphicon glyphicon-phone-alt"></span> 111111111 <br>
                  <span class="glyphicon glyphicon-envelope"></span> hlj@zs-shiyu.com <br>
                  QQ: 1554684868
                  <br>
                  © 2014 世宇科技 Unis Technology, Inc.
                </small>
              </div>
        </div>

        <script src="/static/wwc/js/vendor/bootstrap.min.js"></script>
        <script src="/static/wwc/js/vendor/icheck.min.js"></script>
        <script src="/static/wwc/js/vendor/bootstrap-datetimepicker.min.js"></script>
        <script src="/static/wwc/js/vendor/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
        <script src="/static/wwc/js/plugins.js"></script>
        <script src="/static/wwc/js/main.js"></script>
    </body>
</html>