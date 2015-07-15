<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<span id="sitemeshtitle" style="display: none">title_news</span>
<title>世宇游戏-活动中心</title>

<script type="text/javascript">
	function rollDirect(_listDiv,_url, curPage){
		window.location.href = _url + curPage;
	}
</script>
<div class="container">
	<!-- page header -->
	<div class="row">

		<div class="col-sm-8 col-md-9">

			<section>
				<ol class="breadcrumb">
					<li><a href="/">首页</a></li>
					<li><a href="${ctx }/frontEnd/news/list">活动中心</a></li>
					<li><a href="${ctx }/frontEnd/news/list?tag=${tag}">${tagChinese }</a></li>
				</ol>
				<!-- end breadcrumb -->


              <div class="news-preview">

                <div class="row news-row">
				<c:forEach items="${page }" var="item" varStatus="status">		
                  <div class="col-sm-6 cachefly">
                    <a href="${ctx }/frontEnd/news/show/${item.id }"><img src="<c:if test="${empty item.titleImg  }">${ctx }/static/static-page/img/demo1.png</c:if>${ctx }${item.titleImg}" alt="" class="img-thumbnail"></a>
                    <a href="${ctx }/frontEnd/news/show/${item.id }"><h4>${item.title}</h4></a>
                    <small class="article-tags"><i class="fa fa-bookmark text-danger"></i> <a href="${ctx }/frontEnd/news/list?tag=${item.tag}">&nbsp;${item.tagChinese }</a></small>
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
                  </div> <!-- /.col-sm-6 -->
                <c:if test="${status.count % 2 == 0  and (status.count != fn:length(page))}">
	         	</div>
	         	<div class="row news-row">
	         	</c:if>
				</c:forEach>				
				</div> <!-- end row -->
				
              </div> <!-- end news preview -->


				<!-- backup for old style begin--><!-- 
				 <div class="news-preview">
					<c:forEach items="${page }" var="item" varStatus="status">
						<div class="row news-row">
							<div class="col-sm-4 col-md-4 text-center">
								<a href="${ctx }/frontEnd/news/show/${item.id }" target="_blank"><img src="<c:if test="${empty item.titleImg  }">${ctx }/static/static-page/img/demo1.png</c:if>${ctx }${item.titleImg}" alt="" class="img-thumbnail"></a>
							
							</div>
							<div class="col-sm-8 col-md-8">
								<a href="${ctx }/frontEnd/news/show/${item.id }" target="_blank"><h4>${item.title}</h4></a> 
								<small class="article-tags"><i class="fa fa-bookmark text-danger"></i><a href="${ctx }/frontEnd/news/list?tag=${item.tag}">&nbsp;${item.tagChinese }</a></small>
								<p>
									<c:choose>
										<c:when test="${fn:length(item.contentWithoutHtml) > 90}">
											<c:out
												value="${fn:substring(item.contentWithoutHtml, 0, 90)}.." />
										</c:when>
										<c:otherwise>
											<c:out value="${item.contentWithoutHtml}" />
										</c:otherwise>
									</c:choose>
									<a href="${ctx }/frontEnd/news/show/${item.id }"
										target="_blank"><span class="more">更多 &raquo;</span></a>
								</p>
							</div>
						</div>
					</c:forEach>
				</div> 				-->
				<!-- end news preview -->
				<!-- backup for old style end-->
				
				
				<tags:pag page="${pageRequest}" url="${ctx}/frontEnd/news/list"/>
				
				
				<div class="text-center">
					
					 <ul class="pagination pagination-lg">
					<!-- ${roll } -->
						<!--<li class="disabled"><a href="#">&laquo;</a></li>
						<li class="active"><a href="#">1</a></li>
						<li><a href="#">2</a></li>
						<li><a href="#">3</a></li>
						<li><a href="#">4</a></li>
						<li><a href="#">5</a></li>
						<li><a href="#">&raquo;</a></li> -->
					</ul>
					
				</div>
			</section>

		</div>
		<!-- end col-md-9 -->

		<div class="col-sm-4 col-md-3">
			<aside id="sidebar">
				<div class="sidebar-content">

					<section class="widget">
						<h4 class="widget-title news-list">最近活动</h4>
						<ul>
							<c:forEach  items="${lastestFiveNews }" var="item" varStatus="status">
								<li><i class="fa fa-angle-right"></i>
									<a href="${ctx }/frontEnd/news/show/${item.id }" target="_blank" title="${item.title }"> 
										<c:choose>   
    										<c:when test="${fn:length(item.title) > 7}">   
        										<c:out value="${fn:substring(item.title, 0, 7)}.." />   
    										</c:when>   
   											<c:otherwise>   
      											<c:out value="${item.title}" />  
    										</c:otherwise>  
    									</c:choose>
									</a>
								</li>
							</c:forEach>
							<!-- <li><i class="fa fa-angle-right"></i><a href="#"> news</a></li>
							<li><i class="fa fa-angle-right"></i><a href="#"> news</a></li>
							<li><i class="fa fa-angle-right"></i><a href="#"> news</a></li>
							<li><i class="fa fa-angle-right"></i><a href="#"> news</a></li>
							 -->
						</ul>
					</section>
					<!-- <section class="last widget">东西1</section> -->

				</div>
				<!-- end sidebar content -->
				<!-- 
				<div class="sidebar-content side-ad">
					<section class="widget">
						<h4 class="widget-title">广告栏</h4>
					</section>
					<section class="widget">
						<a href=""><img src="img/fruit.png" alt=""></a>
					</section>
					<section class="widget last">
						<a href=""><img src="img/fruit.png" alt=""></a>
					</section>
				</div>
				 -->
				<!-- 广告栏 end side-ad content -->
			</aside>
			<!-- end sidebar -->
		</div>

	</div>
	<!-- end row -->
</div>
<!-- end container -->
