<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<span id="sitemeshtitle" style="display: none">title_games</span>
 <script>
 $().ready(function(){
	 $('[id^=game_type1_]').click(function(){
		$(this).attr("href","${ctx}/frontEnd/game/list?f1=" + $(this).attr("value") + "&f2=" + $('[id^=game_type2_][class$=active]').attr("value"));
	 	});
	 $('[id^=game_type2_]').click(function(){
		 $(this).attr("href","${ctx}/frontEnd/game/list?f1=" + $('[id^=game_type1_][class$=active]').attr("value") + "&f2=" + $(this).attr("value"));
	 	});

	 $('[id^=gamelist_page_]').click(function(){
		 var href = $(this).attr("href");
		 $(this).attr("href",href + "&f1=" + $('[id^=game_type1_][class$=active]').attr("value") + "&f2=" + $('[id^=game_type2_][class$=active]').attr("value"));

	 });
 })
 </script>
<title>世宇游戏-游戏列表</title>

        <div class="container">

          <ol class="breadcrumb">
              <li><a href="/">首页</a></li>
              <li>游戏中心</li>
            </ol> <!-- end breadcrumb -->
          <div class="row">
            <div class="col-sm-8 col-md-9">
              <!-- ******************我是分割线****************************** -->
              <c:forEach items="${page }" var="item" varStatus="status">
              	 <div class="row">
                  <div class="col-sm-3 text-center"><a href="${ctx }/frontEnd/game/description?id=${item.id}"><img src="${item.iconPath }" alt="" width="120"></a></div>
                  <div class="col-sm-9">
                    <h3><a  href="${ctx }/frontEnd/game/description?id=${item.id}">${item.nameForCH }</a></h3>
                    <h5 class="text-muted">${item.type }</h5>
                    <p>
                    	${fn:substring(item.description,0,40) }
                    </p>
                  </div>
              </div> <!-- ./row -->
              <hr>
              </c:forEach>


              <!-- ******************我是分割线****************************** -->
             <!--  <div class="row">
                  <div class="col-sm-3 text-center"><a href=""><img src="img/fruit.png" alt="" width="120"></a></div>
                  <div class="col-sm-9">
                    <h3><a href="">南瓜大本营</a></h3>
                    <h5 class="text-muted">飞行射击类</h5>
                    <p>南瓜王率領众多水果怪物抢夺大家辛苦获得的金币，要使用金币攻击水果怪物，打...</p>
                  </div>
              </div>
              <hr> --><!-- ./row -->
              <!-- ******************我是分割线****************************** -->
             <!--  <div class="row">
                  <div class="col-sm-3 text-center"><a href=""><img src="img/fruit.png" alt="" width="120"></a></div>
                  <div class="col-sm-9">
                    <h3><a href="">南瓜大本营</a></h3>
                    <h5 class="text-muted">飞行射击类</h5>
                    <p>南瓜王率領众多水果怪物抢夺大家辛苦获得的金币，要使用金币攻击水果怪物，打...</p>
                  </div>
              </div>
              <hr> --><!-- ./row -->
              <!-- ******************我是分割线****************************** -->
              <!-- <div class="row">
                  <div class="col-sm-3 text-center"><a href=""><img src="img/fruit.png" alt="" width="120"></a></div>
                  <div class="col-sm-9">
                    <h3><a href="">南瓜大本营</a></h3>
                    <h5 class="text-muted">飞行射击类</h5>
                    <p>南瓜王率領众多水果怪物抢夺大家辛苦获得的金币，要使用金币攻击水果怪物，打...</p>
                  </div>
              </div>
              <hr> --> <!-- ./row -->
              <!-- ******************我是分割线****************************** -->
             <!--  <div class="row">
                  <div class="col-sm-3 text-center"><a href=""><img src="img/fruit.png" alt="" width="120"></a></div>
                  <div class="col-sm-9">
                    <h3><a href="">南瓜大本营</a></h3>
                    <h5 class="text-muted">飞行射击类</h5>
                    <p>南瓜王率領众多水果怪物抢夺大家辛苦获得的金币，要使用金币攻击水果怪物，打...</p>
                  </div>
              </div>
              <hr> --><!-- ./row -->
              <!-- ******************我是分割线****************************** -->
             <!--  <div class="text-center"> 分页
                <ul class="pagination pagination-lg">
                  <li class="disabled"><a href="#">&laquo;</a></li>
                  <li class="active"><a href="#">1</a></li>
                  <li><a href="#">2</a></li>
                  <li><a href="#">3</a></li>
                  <li><a href="#">4</a></li>
                  <li><a href="#">5</a></li>
                  <li><a href="#">&raquo;</a></li>
                </ul>
              </div>  --><!-- /.分页 -->

              <tags:pag page="${pageRequest}" url="${ctx}/frontEnd/game/list" id="gamelist_page_"/>

            </div> <!-- /.col-sm-8 -->

            <div class="col-sm-4 col-md-3">

                <div id="game_type1" class="list-group">




                  <a id="game_type1_0" value="0" href="#"
                  	<c:if test="${select1=='0' }">class="list-group-item active"</c:if>
                	<c:if test="${select1!='0' }">class="list-group-item "</c:if>
                  >全部</a>
                  <a id="game_type1_1" value="1" href="#"
                  	<c:if test="${select1=='1' }">class="list-group-item active"</c:if>
                	<c:if test="${select1!='1' }">class="list-group-item "</c:if>
                  >热门游戏</a>
                  <a id="game_type1_2" value="2" href="#"
					<c:if test="${select1=='2' }">class="list-group-item active"</c:if>
                	<c:if test="${select1!='2' }">class="list-group-item "</c:if>
				  >最新游戏</a>
                </div>

                <div id="game_type2" class="list-group">
                  <a id="game_type2_0" value="0" href="#"
                  	<c:if test="${select2=='0' }">class="list-group-item active"</c:if>
                  		<c:if test="${select2!='0' }">class="list-group-item"</c:if>
                  >全部</a>

                  <c:forEach items="${gameType }" var="type" varStatus="status">
                  	<a id="game_type2_${status.count }" value="${type.mvalue }" href="#"
                  		<c:if test="${select2==type.mvalue }">class="list-group-item active"</c:if>
                  		<c:if test="${select2!=type.mvalue }">class="list-group-item"</c:if>
                  	>${type.mkey }</a>
                  </c:forEach>
                </div>

            </div> <!-- /.col-sm-4 -->


          </div> <!-- ./ row -->
        </div>  <!-- end container -->
