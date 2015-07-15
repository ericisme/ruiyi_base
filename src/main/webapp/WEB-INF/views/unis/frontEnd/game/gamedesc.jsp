<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<span id="sitemeshtitle" style="display: none">title_games</span>

<title>世宇游戏-游戏介绍-${game.nameForCH }</title>
       
    

        <div class="container">
          
          <ol class="breadcrumb">
              <li><a href="/">首页</a></li>
              <li><a href="${ctx }/frontEnd/game/list">游戏列表</a></li>
              <li>${game.nameForCH }</li>
          </ol> <!-- end breadcrumb -->
          
          <div class="row">
              <div class="col-sm-3 text-center">
                <h2 style="margin-top:3px;">${game.nameForCH }</h2>
                <img src="${game.iconPath }" alt="" width="150px">              
              
              <!--<c:if test="${game.nameForCH eq '梦幻水世界' }">
              	<div class="clearfix margin-bottom"></div>
                <a href="http://jquery.xwcms.net/" class="btn btn-default" target="_blank">游戏官网</a>  
              </c:if>   -->
                         
              </div>
              
              <div class="col-sm-6">
                <div class="row margin-bottom">
                  <div class="col-sm-6 game-info-list">
                    <ul>
                      <li>资 费: ${game.needMoneyOrNot }</li>
                      <li>语 言: 中文</li>
                      <li>发行商: 世宇科技</li>
                      <li>更新时间:                       
                      <fmt:formatDate value="${game.releaseDate }" pattern="yyyy-MM-dd" />
                     </li>
                    </ul>
                  </div>
                  <div class="col-sm-6 game-info-list">
                    <ul>
                      <li>最新版本：${game.gameVersion }</li>
                      <!--<li>
                      	大 小：
                      </li>
                       -->
                      <li>类 型：${game.type }</li>
                      <c:if test="${game.ios_downloadNumber > 0 }">
                        <li>苹果系统：iOS 5.1 或更高版本</li>
                      </c:if>
                     
                      <c:if test="${game.android_downloadNumber > 0 }">
                      	<li>安卓系统：2.3.3或更高版本</li>
                      </c:if>
                      
                    </ul>
                  </div>
                </div> <!-- /.row -->
                <div class="game-download text-center">
                  <div class="row">
                    <h3 style="color:#3a87ad;"><i class="fa fa-download fa-fw"></i>各版本下载</h3>
                    
                    <c:if test="${game.ios_downloadNumber > 0 }">
	                    <div class="col-sm-6 cachefly">
	                        <div class="dropdown">
	                        	大小:${game.ios_size }MB<br/>
	                          <button type="button" 
	                          
	                          <c:if test="${game.ios_downloadNumber > 0 }">
	                        	class="btn btn-default dropdown-toggle"
	                        </c:if>
	                         <c:if test="${game.ios_downloadNumber == 0 }">
	                        	class="btn btn-default dropdown-toggle disabled"
	                        </c:if>
	                          
	                          
	                          data-toggle="dropdown">
	                            <i class="fa fa-apple fa-fw"></i>iOS下载
	                            <span class="caret"></span>
	                          </button>
	                          <ul class="dropdown-menu">
	                          
	                          <c:forEach items="${game.platformAndMarkets }" var="item">
		                          	<c:if test="${item.platform==1 }">
		                          		 <li><a href="${ctx }/frontEnd/downloader/platformAndMarket/${item.id }"  target="_blank" >${item.marketName }</a></li>
		                          	</c:if>
		                      </c:forEach>
	                          </ul>
	                        </div>
	                    </div>
					</c:if>
					
					<c:if test="${game.android_downloadNumber > 0 }">
	                    <div class="col-sm-6">
	                      <div class="dropdown">
	                      	大小:${game.android_size }MB<br/>
	                        <button type="button" 
	                        
	                        
	                        <c:if test="${game.android_downloadNumber > 0 }">
	                        	class="btn btn-default dropdown-toggle"
	                        </c:if>
	                         <c:if test="${game.android_downloadNumber == 0 }">
	                        	class="btn btn-default dropdown-toggle disabled"
	                        </c:if>
	                        
	                        
	                        data-toggle="dropdown">
	                          <i class="fa fa-android fa-fw"></i>Android下载
	                          <span class="caret"></span>
	                        </button>
	                        <ul class="dropdown-menu">
	                           <c:forEach items="${game.platformAndMarkets }" var="item">
		                          	<c:if test="${item.platform==2 }">
		                          		<li><a href="${ctx }/frontEnd/downloader/platformAndMarket/${item.id }"  target="_blank" >${item.marketName }</a></li>
		                          	</c:if>
		                      </c:forEach>
	                        </ul>
	                      </div>
	                    </div>
                    </c:if>
                    
                    
                  </div>
                </div>
              </div> <!-- /.col-sm-6 -->

              <div  class="col-sm-3 text-center">
               <h4><i class="fa fa-tablet fa-fw"></i>扫描二维码下载</h4>
                <img  class="img-thumbnail qrcodeimg" src="/frontEnd/game/description/qrcode/${game.id }"/>
           		<!-- <img  src="/static/static-page/img/QrCode.png" alt="" class="img-thumbnail qrcodeimg">  -->
            	<!-- <div id="iosqrcodeimg"></div> -->
              </div>
              
              
              
          </div> <!-- ./ row -->
          
          <div class="section-margin">
            <div class="row">
              <div class="col-sm-3">
                <div class="section-title games-title text-center">游戏简介</div>
              </div>
            </div>
            <div class="game-description">
              <p>${game.description }</p>
              <p></p>
              <p></p>
              <p></p>
              <p></p>
              <p></p>
            </div> <!-- /.game-description -->
             <span class="btnDisplayall">...详细<i class="fa fa-caret-down fa-fw"></i></span>

            <div class="game-screenshot">
              <div class="screenshot-wrapper">
               <c:forEach items="${game.sortedPictureList }" var="item">
              	<div class="loopup"><img src="${item.url }" alt="" name="gameImgs"></div>
               </c:forEach>  
                <!-- <div class="loopup"><img src="http://placehold.it/320x568" alt="" name="gameImgs"></div>
                <div class="loopup"><img src="http://placehold.it/320x568" alt="" name="gameImgs"></div>
                <div class="loopup"><img src="http://placehold.it/320x568" alt="" name="gameImgs"></div>
                <div class="loopup"><img src="http://placehold.it/320x568" alt="" name="gameImgs"></div> -->
              </div> <!-- /.screenshot-wrapper -->
            </div> <!-- /.game-screenshot -->
          </div>
        </div>  <!-- end container -->


		<script src="${ctx }/static/js/jquery.qrcode.min.js"></script>
		<script src="${ctx }/static/static-page/js/vendor/bootstrap.min.js"></script>
        <script src="${ctx }/static/static-page/js/vendor/bootstrap.min.js"></script>
        <script src="${ctx }/static/static-page/js/plugins.js"></script>
        <script src="${ctx }/static/static-page/js/main.js"></script>
        <script>
        window.onload = function(){
          var imgs = document.getElementsByName("gameImgs");
          var totalWidth = 0;
          for(var i=0; i<imgs.length;i++){
            totalWidth += (imgs[i].offsetWidth+30);
          }
          $(".screenshot-wrapper").width(totalWidth);
        }
        //统计点击量(人气)
        $.ajax({
			type : 'GET',
			url : "${ctx}/frontEnd/counts/game/countGamePopularity?"+"game_id="+${game.id}
		}); 
        </script>
        
        <script>
jQuery(function(){
        jQuery('#iosqrcodeimg').qrcode({width: 148,height: 148,text: '${game.qrcodeUrl}'});
});
</script>
