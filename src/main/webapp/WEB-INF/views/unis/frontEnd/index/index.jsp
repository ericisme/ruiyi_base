<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<span id="sitemeshtitle" style="display: none">title_index</span>
<title>世宇游戏-主页</title>
<!-- slider -->

<div class="container banner">
	<div id="unisCarousel" class="carousel slide">
		<!-- Indicators -->
		<ol class="carousel-indicators">
			<c:forEach items="${ppt }" varStatus="status">
				<li data-target="#unisCarousel" data-slide-to="${status.index }"
					<c:if test="${status.index eq 0 }"> class="active"</c:if>></li>
			</c:forEach>
		</ol>

		<!-- Wrapper for slides -->
			<div class="carousel-inner">
				<c:forEach items="${ppt }" var="item" varStatus="status">
					<c:if test="${status.index eq 0 }">
						<div class="item active">
							<a href="${ item.articleUrl}" target="_blank"><img
								src="${ctx}${item.url}" alt="..."></a>
							<div class="carousel-caption">
								<h2>${item.content }</h2>
							</div>
						</div>
					</c:if>
					<c:if test="${status.index != 0 }">
						<div class="item">
							<a href="${ item.articleUrl}" target="_blank"><img
								src="${ctx }${item.url}" alt="..."></a>
							<div class="carousel-caption">
								<h2>${item.content }</h2>
							</div>
						</div>
					</c:if>
				</c:forEach>
			</div>

		<!-- Controls -->
		<a class="left carousel-control" href="#unisCarousel"
			data-slide="prev"> <span class="glyphicon glyphicon-chevron-left"></span>
		</a> <a class="right carousel-control" href="#unisCarousel"
			data-slide="next"> <span class="glyphicon glyphicon-chevron-right"></span>
		</a>
	</div><!-- end unisCarousel -->

</div><!-- end unisCarousel -->

<section class="feature-games section-margin">
	<div class="container">
			<div class="row margin-bottom">
				<div class="col-sm-6 col-md-3 cachefly">
					<div class="section-title games-title">
						精品游戏<span class="more"><i class="icon-plus-sign-alt"></i>
							<a href="${ctx }/frontEnd/game/list" target="_blank"> 更多 &raquo;</a></span>
					</div>
				</div>
			</div>
			<div class="row margin-bottom">
				<c:forEach items="${game }" var="item" varStatus="status">
					<div class="col-xs-6 col-sm-6 col-md-3 cachefly">
						<div
							class=<c:choose>
            			<c:when test="${item.isNew==1 }">"tile tile-tag tile-new"</c:when>
            			<c:when test="${item.isNew==0&&item.isHot==1 }">"tile tile-tag tile-hot"</c:when>
            			<c:otherwise>"tile"</c:otherwise>
            		</c:choose>>
            		<a href="${ctx }/frontEnd/game/description?id=${item.id}">
            			<img  src="${ctx }${item.iconPath }" alt=""
								class="tile-image bounceIn">
            		</a>

							<h3 class="tile-title hidden-xs">${item.nameForCH }</h3>
							<h4 class="tile-title hidden-sm hidden-md hidden-lg">${item.nameForCH }</h4>
							<p  class="hidden-xs">
								<c:if test="${item.description=='' }">not description....................</c:if>
								<c:if test="${item.description!='' }">
									<c:choose>
										<c:when test="${fn:length(item.description) > 28}">
											<c:out value="${fn:substring(item.description, 0, 28)}.." />
										</c:when>
										<c:otherwise>
											<c:out value="${item.description}" />
										</c:otherwise>
									</c:choose>
								</c:if>
							</p>

				<div class="dropup">
                    <a type="button" class="btn btn-lg btn-block dropdown-toggle" data-toggle="dropdown">下 载</a>
                    <ul class="dropdown-menu pull-right">
                        <!-- Dropdown menu links -->
                         <c:set var="iosFlag" scope="session" value="1"/>
                        <c:set var="androidFlag" scope="session" value="1"/>
                        <c:forEach items="${item.platformAndMarkets}" var="varItem1">
                        	<c:if test="${varItem1.platform==1 && iosFlag==1 }">
                        		<li><a href="${ctx }/frontEnd/downloader/platformAndMarket/${varItem1.id }" target="_blank" ><i class="fa fa-apple"></i> iOS </a></li>
                        		 <c:set var="iosFlag" scope="session" value="2"/>
                        	</c:if>
                        </c:forEach>
                        <c:forEach items="${item.platformAndMarkets }"  var="varItem2">
                       	 	<c:if test="${varItem2.platform==2 && androidFlag==1}">
                       	 		<li><a href="${ctx }/frontEnd/downloader/platformAndMarket/${varItem2.id }" target="_blank" ><i class="fa fa-android"> Android </i></a></li>
                       	 		 <c:set var="androidFlag" scope="session" value="2"/>
                       	 	</c:if>
                        </c:forEach>

                        <li class="divider"></li>
                        <li><a href="${ctx }/frontEnd/game/description?id=${item.id}">其他系统版本</a></li>
                    </ul>
                  </div>

							<%-- <a class="btn btn-primary btn-lg btn-block"
								href="${item.platformAndMarkets[0].downloadUrl }"
								target="_blank">下 载</a> --%>
						</div>
					</div>
					<c:if test="${status.count%4==0 and (status.count != fn:length(game))}">
						</div>
						<div class="row">
				</c:if>
				</c:forEach>
			</div>
	</div>
</section><!-- end games list -->


<!-- 排行榜 -->
      <section class="top_games section-margin">
        <div class="container">
        
          <div class="row margin-bottom">
              <div class="col-sm-6 col-md-3 cachefly">
                <div class="section-title games-title">排行榜<span class="more"><i class="fa fa-plus-square"></i> <a href="/frontEnd/game/list"> 更多 &raquo;</a></span></div>
              </div>
          </div>
          
           <!-- row -->
          <div class="row margin-bottom">

	         <c:forEach items="${rank }" var="item" varStatus="status">

					<div class="col-xs-6 col-md-2 cachefly">
		              <div class="tile tile-tag">
		                <a href="${ctx }/frontEnd/game/description?id=${item.id}" target="_blank"><img src="${item.iconPath }" width="128" alt="" class="tile-image"></a>

		                <a href="${ctx }/frontEnd/game/description?id=${item.id}" target="_blank">
		                <h4 class="tile-title"><span <c:if test="${status.count < 4}">class="badge top_badge top_badge_hot"</c:if> <c:if test="${status.count >= 4}">class="badge top_badge "</c:if>
		                >${status.count }</span>${item.nameForCH }</h4></a>
		                <small>下载量: ${item.downloadTimes }</small>
		              </div>
            		</div>
            		<c:if test="${status.count % 6 == 0 and (status.count!=fn:length(rank)) }">
	         		</div>
	         		<div class="row">
	         	</c:if>
	         </c:forEach>

		</div>
		
		
        </div> <!-- /.container -->
      </section>
<!-- /.排行榜 -->

<section class="news-preview">
		<div class="container">
			<div class="row margin-bottom">
				<div class="col-sm-6 col-md-3">
					<div class="section-title news-title">
						最近活动<span class="more"><i class="fa fa-plus-square"></i> <a
							href="${ctx }/frontEnd/news/list" target="_blank"> 更多 &raquo;</a></span>
					</div>
				</div>
			</div>


            <div class="row">

			<c:forEach items="${news }" var="item" varStatus="status">
              <!-- news start -->
              <div class="col-sm-4 cachefly">
                  <a href="${ctx }/frontEnd/news/show/${item.id }"><img src="${ctx }${item.titleImg}" alt="" class="img-thumbnail"></a>
                  <a href="${ctx }/frontEnd/news/show/${item.id }"><h4>${item.title}</h4></a>
                  <small class="article-tags"><i class="fa fa-bookmark text-danger"></i><a href="${ctx }/frontEnd/news/list?tag=${item.tag}" >&nbsp;${item.tagChinese }</a></small>
                  <p class="p_excerpt">
									<c:choose>
										<c:when test="${fn:length(item.contentWithoutHtml) > 45}">
											<c:out
												value="${fn:substring(item.contentWithoutHtml, 0, 45)}.." />
										</c:when>
										<c:otherwise>
											<c:out value="${item.contentWithoutHtml}" />
										</c:otherwise>
									</c:choose>
                  <a href="${ctx }/frontEnd/news/show/${item.id }"><span class="more">阅读全文 &raquo;</span></a>
                  </p>
              </div> <!-- /.col-sm-4 -->
			<c:if test="${status.count % 3 == 0  and (status.count != fn:length(page))}">
			</div>
			<div class="row">
			</c:if>
			</c:forEach>
            </div>


			<!-- backup for old style begin -->
			<!-- 
			<div class="row margin-bottom">

				<c:forEach items="${news }" var="item" varStatus="status">
					<div class="col-xs-12 col-sm-12 col-md-6">
						<div class="row">
							<div class="col-sm-4 col-md-4">
								<a href="${ctx }/frontEnd/news/show/${item.id }" target="_blank"><img
									src="${ctx }${item.titleImg}" alt="" class="img-thumbnail"></a>
							</div>
							<div class="col-sm-8 col-md-8">
								<a href="${ctx }/frontEnd/news/show/${item.id }" target="_blank"
									title="${item.title}">
									<h4>
										<c:choose>
											<c:when test="${fn:length(item.title) > 15}">
												<c:out value="${fn:substring(item.title, 0, 15)}.." />
											</c:when>
											<c:otherwise>
												<c:out value="${item.title}" />
											</c:otherwise>
										</c:choose>
									</h4>
								</a> <small class="article-tags"><i
									class="fa fa-bookmark text-danger"> </i><a href="${ctx }/frontEnd/news/list?tag=${item.tag}" target="_blank">&nbsp;${item.tagChinese }</a>
								</small>
								<p>
									<c:choose>
										<c:when test="${fn:length(item.contentWithoutHtml) > 60}">
											<c:out
												value="${fn:substring(item.contentWithoutHtml, 0, 60)}.." />
										</c:when>
										<c:otherwise>
											<c:out value="${item.contentWithoutHtml}" />
										</c:otherwise>
									</c:choose>
									<a href="${ctx }/frontEnd/news/list"
										target="_blank"><span class="more">更多 &raquo;</span></a>
								</p>
							</div>
						</div>
					</div>
				</c:forEach>

			</div> -->
			<!-- end row -->
			<!-- backup for old style end-->
			
			
			
			
		</div>
		<!-- end container -->
</section><!-- end news preview -->
