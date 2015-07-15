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


        <link rel="stylesheet" href="/static/wwc/css/bootstrap.min.css">
        <link rel="stylesheet" href="/static/wwc/css/home.min.css">
        <link rel="stylesheet" href="/static/wwc/css/animate.css">
        <script src="/static/wwc/js/vendor/modernizr-2.6.2.min.js"></script>
        <script src="/static/wwc/js/vendor/jquery-1.10.2.min.js"></script>
        <script src="/static/wwc/js/cookies.js"></script>


        <script type="text/javascript" src="/static/wwc/source/jquery.fancybox.pack.js"></script>
        <link rel="stylesheet" type="text/css" href="/static/wwc/source/jquery.fancybox.css" media="screen" />


		<script type="text/javascript">
		$().ready(function(){
			if('${err}'=='1'){
				alert("邀请码或者验证码错误！！！");
			}
			$('#languageselect a').click(function(){
				var rel = $(this).attr("rel");

				setCookie('language', rel, 30);

				if(rel=='0'){
					$(this).attr("href",'/wwc/en/index');
				}else{
					$(this).attr("href",'/wwc/cn/index');
				}
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

          <div class="stars"></div>
           <div class="container"><div class="theme_bg hidden-xs"></div></div>
          <section class="landing">
            <div class="container">
              <div class="rocket"></div>
              <div class="moon"></div>
              <div class="saturn"></div>
              <div class="logo animated bounceIn animateDelay1">
              <a href="/">
              <img src="/static/wwc/img/logo.png" alt="" width="220">
              </a>
              </div>
              <div id="languageselect" class="language btn-group pull-right">
                <a rel="0"  class="btn btn-default">EN</a>
                <a rel="1"  class="btn btn-default active">中 文</a>
              </div>
              <div class="animated bounceInDown">
                <h1>世宇科技全球发布会</h1>
                <h3>合作·感动 创新·跨越</h3>
              </div>

              <div class="row invitation-form">
                <div class="col-sm-5 col-md-4 col-sm-offset-1">
                  <form role="form" action="/wwc/invitation_code/verify" method="post">
                    <div class="form-group">
                      <label for="invitation">邀請碼</label>
                      <input type="text" class="form-control unis-form" id="invitation" name="invitationCode" placeholder="邀請碼">
                    </div>
                    <div class="form-group">
                      <label for="invitation">驗證碼</label>
                      <div class="row">
                        <div class="col-xs-6">
                          <input type="text" class="form-control unis-form" id="authcode" name="captcha" placeholder="驗證碼">
                        </div>
                        <div class="col-xs-6">
                           <img src="/static/captchaCode/wwc" alt="">
                        </div>
                      </div>
                    </div>
                    <div class="clearfix"></div>
                    <button type="submit" class="btn btn-default btn-block unis-btn">馬上申請</button>
                  </form>

                  <!-- <div class="get-inviation"><a href="/wwc/applycode">申請邀請碼</a></div> -->
                </div>
              </div>

            </div> <!-- /.container -->
          </section>

          <section class="event-info main_content">
            <div class="container">
              <div class="row">
                    <div class="col-sm-6">
                  <div class="event-when text-center">
                    <h3>时间</h3>
                    <p>2014年10月20日</p>
                    <p><span>至 <span><br>2014年10月21日</p>
                  </div>
                </div>

                <div class="col-sm-6">
                  <div class="event-where text-center">
                    <h3>地点</h3>
                    <p>中山市</p>
                    <p><span>中国广东省中山市西区  世宇动漫产业园 </span></p>
                    <a href="http://goo.gl/maps/Mah03" target="_blank" class="btn btn-default btn-info">百度地图上查看</a>
                  </div>
                </div>
              </div> <!-- /.row -->
            </div><!-- /.container -->
          </section>

            <section class="event-about main_content">
            <div class="container">
              <div class="row">
                <div class="col-sm-6">
                  <div class="thumb_img">
                    <a href="/static/wwc/img/newfac_large.jpg" class="fancybox"><img src="/static/wwc/img/newfac_thumb.jpg" alt=""></a>
                  </div>
                </div>
                <div class="col-sm-6">
                  <h2>世宇科技全球合作伙伴大会简介</h2>
                  <p>
                    借此世宇公司成立20周年和世宇动漫产业园建成开业之际，我公司定于2014年10月21日在中国广东省中山市举行世宇科技全球合作伙伴大会，会议主题为“合作.感动  创新.跨越”。大会将邀请来自全世界近百个国家、地区和国内的500位世宇科技合作企业、媒体及政府部门的代表，共同见证、总结、回顾我们多年合作的成果，展望、探讨、谋划我们继续合作的前景，发布、展示、分享世宇科技最新的游戏产品和服务平台，为促进世界游艺娱乐事业的跨越共聚情谊、共襄大计。</p>
                </div>
              </div>
            </div>
          </section>

          <section class="articles main_content">
            <div class="container">
              <div class="row">
                <div class="col-sm-12">
                  <div class="event-heading text-center">
                  <h2>大会动态</h2>
                  <hr>
                </div>
              </div>
              </div>

<div class="row margin-bottom">
              <c:forEach items="${news }" var="item" varStatus="status">




				 <div class="col-xs-6">
                    <div class="row">
                      <div class="article-preview">
                          <div class="col-sm-5">
                            <div href="#" class="article_thumbnail">
                              <img src="${item.titleImg }" alt="">
                              <div class="category text-center">
                                <a href="#"><span class="badge-list badge">${item.tagChinese}</span></a>
                              </div>
                            </div>
                          </div>
                          <div class="col-sm-7">
                            <a class="frame-article fancybox.iframe" href="/wwc/article/${item.id }">
                              <h3>${item.title }</h3>
                              <p name="news_content">
                              <c:if test="${fn:length(item.contentWithoutHtml) > 50}">${fn:substring(item.contentWithoutHtml,0,50) }......</c:if>
                               <c:if test="${fn:length(item.contentWithoutHtml) <= 50}">${item.contentWithoutHtml}</c:if>
                              </p>
                            </a>
                          </div>
                        </div> <!-- /.article-pre -->
                    </div>
                  </div> <!-- /.col-sm-6 -->

                  	<c:if test="${status.count % 2 == 0}">
						</div>
						<div class="row margin-bottom">
					</c:if>

              </c:forEach>
</div>








            </div>
          </section>

          <div id="footer" class="main_content">
            <div class="container">
              <div class="row contact-info">
                <div class="col-sm-4">
                  <ul>
                  <li><span class="glyphicon glyphicon-phone-alt"></span> 电话: 0760-23886157</li>
                  <li><span class="glyphicon glyphicon-envelope"></span> 邮件: hlj@zs-shiyu.com</li>
                  <li>QQ: 1554684868</li>
                  </ul>
                </div>
                <div class="col-sm-4 margin-bottom">
                  <p>微 信: (下面放二维码)</p>
                  <img src="http://lorempixel.com/130/130" alt="">
                </div>
                <div class="col-sm-4">
                  <p>微 博:</p>
                  <a href="www.weibo.com/#"><img src="/static/wwc/img/weibo.png" alt=""></a>
                </div>
              </div>
              <div class="clearfix"></div>
              <div class="row legal">
                <div class="col-sm-14">
                  <p style="font-size:12px">TM + © 2014 中山市世宇实业有限公司 UNIS Technology, All rights reserved.</p>
                </div>
              </div>
            </div>
          </div>



        <script src="/static/wwc/js/vendor/bootstrap.min.js"></script>
        <script src="/static/wwc/js/vendor/icheck.min.js"></script>
        <script src="/static/wwc/js/plugins.js"></script>
        <script src="/static/wwc/js/main.js"></script>


            <script>
        $("a[href$='.jpg'],a[href$='.png'],a[href$='.gif']").attr('rel', 'gallery').fancybox();

        $(".frame-article").fancybox({
          maxWidth  : 900,
          maxHeight : 800,
          fitToView : false,
          width   : '90%',
          height    : '80%',
          autoSize  : false,
          closeClick  : false,
          openEffect  : 'elastic',
          closeEffect : 'elastic',
        });

        </script>


    </body>
</html>
