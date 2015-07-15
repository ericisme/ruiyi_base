<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>        
          
<jsp:include page="/WEB-INF/views/unis/frontEnd/gameCenter/selector.jsp" />
    
    
           <span id="game_center_menu_active" style="display: none">game_center_menu_arcades</span>
                
           <script type="text/javascript" src="${ctx }/static/js/base/base.js"></script>     
           <div id="arcade_list_head" class="animated animated_fast fadeIn">
                
               <section>
                  <!-- <h3>游乐场会员详细资讯</h3> -->
                  <div class="alert alert-info">游乐场机台资讯 -- 列表</div>
                  <!-- <div class="alert alert-danger alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                   		 紧急通告
                  </div>-->
                </section>
				<div class="table-responsive">
                <table class="table table-hover">
                  <thead>
                    <tr>
                    	<th>所属游戏</th>
                    	<th>设备ID</th>
                    	<!-- <th>CUK</th> -->
                    	<th>序号</th>                    	
                    	<th>状态</th>
                    	<th>连接</th>
                    </tr>
                  </thead>

                  <tbody>
                  <c:forEach items="${arcade_list }" var="arcade" varStatus="status">
                  	<tr>
                  	 <td>${arcade.consumer.name }</td>
                      <td>${arcade.device_id }</td>
                      <!-- <td>${arcade.cuk }</td> -->
                      <td>${arcade.console_index }</td>                     
                      <td>
                      	<c:if test="${arcade.arcade_status eq '0' }">未处理</c:if>
                      	<c:if test="${arcade.arcade_status eq '1' }">正常</c:if>
                      	<c:if test="${arcade.arcade_status eq '2' }">隐藏</c:if>
                      	<c:if test="${arcade.arcade_status eq '3' }">停权</c:if>
                      	<c:if test="${arcade.arcade_status eq '9' }">已移除</c:if>
                      </td>
                      <td>
                      	<c:if test="${arcade.connection_state eq 'offline' }">离线</c:if>
                      	<c:if test="${arcade.connection_state eq 'idle' }">闲置</c:if>
                      	<c:if test="${arcade.connection_state eq 'standalone' }">离线游戏中</c:if>
                      	<c:if test="${arcade.connection_state eq 'online' }">在线游戏中</c:if>
                      	<c:if test="${arcade.connection_state eq 'redeem' }">换领礼品中</c:if>
                      </td>
                    </tr>
                  </c:forEach>  
                  </tbody>
                </table>
                </div>

                <ul class="pager" style="margin-bottom: 50px;">
                  <li>
                  	<c:if test="${currPageNum gt 1 }"><a href="${ctx }/frontEnd/gameCenter/arcades?page=${currPageNum-1}">&larr; 上一页</a></c:if>
                  	<c:if test="${currPageNum eq 1 }"><a href="#"><font color="#888888">&larr; 上一页</font></a></c:if>
                  </li>
                  	&nbsp;&nbsp;第${currPageNum}页&nbsp;&nbsp;
                  <li>
                  	<c:if test="${hasNextPage eq true}">  <a href="${ctx }/frontEnd/gameCenter/arcades?page=${currPageNum+1}">下一页 &rarr;</a></c:if>
                  	<c:if test="${hasNextPage eq false}"><a href="#"><font color="#888888">下一页 &rarr;</font></a></c:if>
                  </li>
                </ul>
                <br/>
			</div>
			<div id="arcade_list_list"></div>
			<div id="arcade_list_edit"></div>

<jsp:include page="/WEB-INF/views/unis/frontEnd/gameCenter/sidebar.jsp" />



     