<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<span id="sitemeshtitle" style="display: none">title_news</span>
<title>世宇游戏-活动中心-${news.title }</title>

<script type="text/javascript" src="http://v3.jiathis.com/code_mini/jia.js" charset="utf-8"></script>
        <div class="container">
          <!-- page header -->
          <section class="article">

            <div class="row">
              <div class="col-md-9 col-sm-9">

                <ul class="breadcrumb">
                  <li><a href="${ctx }/index.html">首页</a><span class="divider"></span></li>
                  <li><a href="${ctx }/frontEnd/news/list">活动中心</a></li>
				  <li><a href="${ctx }/frontEnd/news/list?tag=${news.tag}">${news.tagChinese }</a></li>
                </ul> <!-- end breadcrumb -->
                <div class="art-content">

                  <h2 class="art-title">${news.title }</h2>
                  <span class="meta-info text-muted">
                    
                    <span class="date-meta"><fmt:formatDate value="${news.addTime}" pattern="yyyy-MM-dd" /></span>
                    <span class="divider">/</span>
                    <span class="tag-author">by ${news.author }</span>

                  </span> <!-- end meta -->

                  <!-- <section class="art-paragraph"> -->
                  <section id="article-content" class="art-paragraph animated fadeIn">
                  <%=((cn.unis.entity.News) request.getAttribute("news")).getContent()%>
					
                  </section> <!-- end paragraph -->

                  <ul class="pager">      
                  	<c:if test="${prewId eq 0}">
                  		<li class="previous disabled"><a href="#">&larr; 上一篇</a></li>
                  	</c:if>
                  	<c:if test="${prewId != 0}">
                  		<li class="previous"><a href="${ctx }/frontEnd/news/show/${prewId}">&larr; 上一篇</a></li>
                  	</c:if>
                    <c:if test="${nextId eq 0}">
                    	<li class="next disabled"><a href="#">下一篇&rarr;</a></li>
                    </c:if>
                    <c:if test="${nextId != 0}">
                   		<li class="next"><a href="${ctx }/frontEnd/news/show/${nextId}"> 下一篇&rarr;</a></li>
                    </c:if>
                  </ul>
				<br/>

                </div> <!-- end art-content -->

              </div>

              <aside id="sidebar" class="col-md-3 col-sm-3">

                <div class="sidebar-content">

                  <section class="widget news-list">
                    <h4 class="widget-title">相关活动</h4>
                    <ul>
                    	<c:forEach items="${relatedNewsList }" var="relatedNews" varStatus="status">
                    		<li>
                    			<i class="icon-angle-right "></i>&nbsp;
                    			<a title="${relatedNews.title }" href="${ctx }/frontEnd/news/show/${relatedNews.id }">
                    				<c:choose>   
    									<c:when test="${fn:length(relatedNews.title) > 8}">   
        									<c:out value="${fn:substring(relatedNews.title, 0, 8)}.." />   
    									</c:when>   
   										<c:otherwise>   
      										<c:out value="${relatedNews.title}" />  
    									</c:otherwise>  
    								</c:choose>
                    			</a>
                    		</li>
                    	</c:forEach>
                      <li><a href="#">更多...</a></li>
                    </ul>
                  </section>
                  <section class="widget news-list">
                    <h4 class="widget-title">其它活动</h4>
                    <ul>
                    	<c:forEach items="${otherNewsList }" var="otherNews" varStatus="status">
                    		<li>
                    			<i class="icon-angle-right"></i>&nbsp;
                    			<a title="${otherNews.title }" href="${ctx }/frontEnd/news/show/${otherNews.id }">
                    				<c:choose>   
    									<c:when test="${fn:length(otherNews.title) > 8}">   
        									<c:out value="${fn:substring(otherNews.title, 0, 8)}.." />   
    									</c:when>   
   										<c:otherwise>   
      										<c:out value="${otherNews.title}" />  
    									</c:otherwise>  
    								</c:choose>
                    			</a>
                    		</li>
                    	</c:forEach>
                      <li><a href="#">更多...</a></li>
                    </ul>
                  </section>
                  <!-- <section class="widget"></section>
                  <section class="widget"></section>
                  <section class="last widget">东西1</section> -->

                </div> <!-- end sidebar content -->

              </aside>
              
            </div> <!-- end row -->


                  
          </section>
        </div>

     