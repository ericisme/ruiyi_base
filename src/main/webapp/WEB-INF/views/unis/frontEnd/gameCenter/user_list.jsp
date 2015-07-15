<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>        
          
<jsp:include page="/WEB-INF/views/unis/frontEnd/gameCenter/selector.jsp" />
    
    
                <span id="game_center_menu_active" style="display: none">game_center_menu_users</span>
                
           <script type="text/javascript" src="${ctx }/static/js/base/base.js"></script>     
           <div id="user_list_head"  class="animated animated_fast fadeIn">
                
               <section>
                  <!-- <h3>游乐场会员详细资讯</h3> -->
                  <div class="alert alert-info">游乐场会员资讯 -- 列表</div>
                  <!-- <div class="alert alert-danger alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                   		 紧急通告
                  </div>-->
                </section>
				<div class="table-responsive">
                <table class="table table-hover">
                  <thead>
                    <tr>
                      <th>用户名</th>
                      <th>会员昵称</th>
                      <th>成就积分</th>
                      <th>累计成就积分</th>
                      <th>注册时间</th>
                      <th>详细信息</th>
                    </tr>
                  </thead>

                  <tbody>
                  <c:forEach items="${userPage }" var="user" varStatus="status">
                  	<tr>
                      <td>${user.username }</td>
                      <td>${user.handle_name }</td>
                      <td>${user.achievement_score }</td>
                      <td>${user.accumulated_ascore }</td>
                      <td><fmt:formatDate value="${user.registered_at}" pattern="yyyy-MM-dd" /></td>
                      <td><a href="javascript:userDetail('${user.key}');">详 细 &raquo;</a></td>
                    </tr>
                  </c:forEach>  
                  </tbody>
                </table>
				</div>
                <ul class="pager" style="margin-bottom: 50px;">
                  <li>
                  	<c:if test="${currPageNum gt 1 }"><a href="${ctx }/frontEnd/gameCenter/users?page=${currPageNum-1}">&larr; 上一页</a></c:if>
                  	<c:if test="${currPageNum eq 1 }"><a href="#"><font color="#888888">&larr; 上一页</font></a></c:if>
                  </li>
                  	&nbsp;&nbsp;第${currPageNum}页&nbsp;&nbsp;
                  <li>
                  	<c:if test="${hasNextPage eq true}">  <a href="${ctx }/frontEnd/gameCenter/users?page=${currPageNum+1}">下一页 &rarr;</a></c:if>
                  	<c:if test="${hasNextPage eq false}"><a href="#"><font color="#888888">下一页 &rarr;</font></a></c:if>
                  </li>
                </ul>
                <br/>
			</div>
			<div id="user_list_list"></div>
			<div id="user_list_edit"></div>
			<script type="text/javascript">
				function userDetail(user_key){
					$("#user_list_edit").load("${ctx }/frontEnd/notDecoratorsGameCenter/userDetail?user_key="+ user_key ,function(){
						$("#user_list_head").hide();
						$("#user_list_edit").addClass("animated animated_fast fadeIn");
						$("#user_list_edit").show();
					});

				}
			</script>

<jsp:include page="/WEB-INF/views/unis/frontEnd/gameCenter/sidebar.jsp" />



     