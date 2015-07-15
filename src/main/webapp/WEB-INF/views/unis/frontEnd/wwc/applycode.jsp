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
        <title>申請邀請碼</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

       <!--  <link rel="stylesheet" href="/static/wwc/css/styles.css"> -->
        <link rel="stylesheet" href="/static/wwc/css/bootstrap.min.css">
        <link rel="stylesheet" href="/static/wwc/css/form.min.css">


        <script src="/static/wwc/js/vendor/modernizr-2.6.2.min.js"></script>
        <script src="/static/wwc/js/vendor/jquery-1.10.2.min.js"></script>
        <script src="/static/wwc/js/cookies.js"></script>
        <!-- <script>window.jQuery || document.write('<script src="/static/wwc/js/vendor/jquery-1.10.2.min.js"><\/script>');</script> -->
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

        <div class="container container_apply">
            <div class="header">
                <img src="/static/wwc/img/logo.png" alt="" class="visible-xs" width="150">
                 <a href="/wwc/index">
                <img src="/static/wwc/img/logo.png" alt="" class="hidden-xs">
                </a>
                <h2 id="header_apply" class="clearfix signup-heading">申請全球發佈大會邀請碼</h2>
                <a  id="header_back" href="/wwc/index" class="btn btn-lg" style="color:#56bc8a;"><span class="glyphicon glyphicon-circle-arrow-left"></span> 返回</a>
            </div>

            <div class="overlap">
                <div class="primary-content">
                    <div class="language">
                        <a  rel="1"  <c:if test="${apply_type == 1}"> class="btn btn-lg btn-lan active"</c:if> <c:if test="${apply_type != 1}"> class="btn btn-lg btn-lan"</c:if> >中 文</a>
                        <a  rel="0"  <c:if test="${apply_type == 0}"> class="btn btn-lg btn-lan active"</c:if> <c:if test="${apply_type != 0}"> class="btn btn-lg btn-lan"</c:if> >English</a>
                    </div>
                    <div class="clearfix"></div>
                    <div class="signup_form">
                      <!-- 表单错误提示 -->
                        <div id="err" class="alert alert-danger alert-dismissable hidden">
                          <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                          請檢查以下紅色表格是否有誤, 請填寫正確后再遞交, 謝謝.
                        </div>

                        <form class="form-horizontal invitation" role="form"  >

                        	<input type="hidden" name="apply_type" value="${apply_type }"/>

                          <div class="form-group">
                            <label for="companyname" class="col-sm-4 control-label">公司</label>
                            <div class="col-sm-6">
                              <input type="text" class="form-control" name="companyname" placeholder="公司名称" maxlength="128">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="fullname" class="col-sm-4 control-label">姓名</label>
                            <div class="col-sm-6">
                              <input type="text" class="form-control" name="fullname" placeholder="如: 楊過" maxlength="128">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="position" class="col-sm-4 control-label">職位</label>
                            <div class="col-sm-6">
                              <input type="text" class="form-control" name="position" placeholder="如: 遊戲工程師" maxlength="128">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="email" class="col-sm-4 control-label">郵箱</label>
                            <div class="col-sm-6">
                              <input type="email" class="form-control" name="email" placeholder="郵箱地址" maxlength="256">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="phone" class="col-sm-4 control-label">聯繫電話</label>
                            <div class="col-sm-6">
                              <input type="text" class="form-control" name="phone" placeholder="手機號碼" maxlength="128">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="captcha" class="col-sm-4 control-label">驗證碼</label>
                            <div class="col-sm-6">
                              <div class="row">
                                <div class="col-xs-6">
                                  <input type="text" class="form-control" name="captcha" placeholder="驗證碼">
                                </div>
                                <div class="col-xs-6">
                                  <img id="captchaImg" src="/static/captchaCode/wwc/applycode" alt="" width="130" height="48">  <a  id="captchaChange"  style="color:#56bc8a; cursor: pointer; cursor: hand;"> 看不清？换一张</a>
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="form-group">
                            <div class="col-sm-offset-4 col-sm-6">
                              <button type="button" id="apply_" onclick="formCheck()"  class="btn btn-primary btn-block btn-lg btn-blue">申 請</button>
                            </div>
                          </div>
                          <div id="success_" class="col-sm-offset-4 col-sm-6 hidden">
                            <p  class="text-success"><span class="glyphicon glyphicon-ok-circle"></span> 遞交成功!</p>
                          </div>
                          <div id="failed_" class="col-sm-offset-4 col-sm-6 hidden">
                            <p  class="text-danger"><span class="glyphicon glyphicon-ok-circle"></span> 遞交失败!</p>
                          </div>
                        </form>

                    </div> <!-- /.signup_form -->
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
        <script src="/static/wwc/js/plugins.js"></script>
        <script src="/static/wwc/js/applycode.js"></script>

    </body>
</html>