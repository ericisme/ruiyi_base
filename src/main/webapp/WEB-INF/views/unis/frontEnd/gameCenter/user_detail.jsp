<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>        

                <span id="game_center_menu_active" style="display: none">game_center_menu_users</span>
                <section>
                  <div class="alert alert-info">会员 <strong>${user.username }</strong> 的 详细信息</div>
                </section>
                <a href="javascript:userDetailBack();" class="btn btn-primary">返回会员列表</a>
                <section class="section-margin">
                  <div class="row">
                    <div class="col-sm-6 col-md-6">
                      <table class="table simple-table">
                        <tbody>
                          <tr>
                            <td><strong>用户名</strong></td>
                            <td>${user.username }</td>
                          </tr>
                          <tr>
                            <td><strong>昵 称</strong></td>
                            <td>${user.handle_name }</td>
                          </tr>
                          <tr>
                            <td><strong>国 家</strong></td>
                            <td>${user.country }</td>
                          </tr>
                          <!--
                          <tr>
                            <td><strong>email</strong></td>
                            <td>
                            ${user.email }                       	
                            </td>
                          </tr>
                          -->
                          <!-- 
                          <tr>
                            <td><strong>电 话</strong></td>
                            <td>${user.phone }</td>
                          </tr>
                           -->
                          <tr>
                            <td><strong>註冊時間</strong></td>
                            <td><fmt:formatDate value="${user.registered_at}" pattern="yyyy-MM-dd" /></td>
                          </tr>
                          <tr>
                            <td><strong>注销时间</strong></td>
                            <td><fmt:formatDate value="${user.expire}" pattern="yyyy-MM-dd" /></td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                    <div class="col-sm-6 col-md-6">
                      <table class="table simple-table">
                        <tbody>
                          <tr>
                            <td><strong>世宇积分</strong></td>
                            <td>${user.achievement_score }</td>
                          </tr>
                          <!--
                          <tr>
                            <td><strong>累积成就点数</strong></td>
                            <td>${user.accumulated_ascore }</td>
                          </tr>
                          -->
                          <tr>
                            <td><strong>等 级</strong></td>
                            <td>${user.grade }</td>
                          </tr>
                          <!-- 
                          <tr>
                            <td><strong>游戏币数</strong></td>
                            <td></td>
                          </tr>
                           -->
                          <tr>
                            <td><strong>彩票数</strong></td>
                            <td>${userGameCenterWallet.ticket.balance }</td>
                          </tr>
                          <tr>
                            <td><strong>状 态</strong></td>
                            <td>${user.statusChinese }</td>
                          </tr>
                          <tr>
                            <td><strong>个性签名</strong></td>
                            <td>${user.personal_status }</td>
                          </tr>                          
                        </tbody>
                      </table>
                    </div>
                    <!-- <div class="col-sm-6 col-md-6">
                      <table class="table simple-table">
                        <tbody>


                        </tbody>
                      </table>
                    </div> -->
                  </div>
                </section>

                <!-- <div>
                  <div class="col-sm-6">
                    <table class="table table-striped">
                      <thead>
                        <tr>
                          <th><strong>序号</strong></th>
                          <th><strong>游戏名称</strong></th>
                          <th><strong>登入次数</strong> </th>
                      </thead>
                      <tbody>
                      	<c:forEach items="${waloGames }" var="waloGame" varStatus="status">
                      		<tr>
                          		<td>${status.index + 1 }</td>
                          		 <td>
                          		 <span class="basic-tooltip" data-toggle="tooltip" data-placement="top" title="${waloGame.consumer_name }">
                          			<c:choose>
										<c:when test="${fn:length(waloGame.consumer_name) > 25}">
											<c:out
												value="${fn:substring(waloGame.consumer_name, 0, 25)}.." />
										</c:when>
										<c:otherwise>
											<c:out value="${waloGame.consumer_name}" />
										</c:otherwise>
									</c:choose>
									</span>
									
                          		</td> 
                          		<td>${waloGame.login_count }</td>
                        	</tr>
                      	</c:forEach>
                      </tbody>
                    </table>
                  </div>
                </div>
                 -->
                 <div>
                  <div class="col-sm-6">
                    <table class="table table-striped">
                      <thead>
                        <tr>
                        	<th><strong>序号</strong></th>
                          	<th><strong>游戏名称</strong></th>
                          	<th><strong>游戏代币</strong></th>
                      </thead>
                      <tbody>
                      	<c:forEach items="${userGameCenterWallet.tokens }" var="tokens" varStatus="status">
                      		<tr>
                      			<td>${status.index + 1 }</td>
                          		<td>
                          			<span class="basic-tooltip" data-toggle="tooltip" data-placement="top" title="${tokens.name }">
                          		    <c:choose>
										<c:when test="${fn:length(tokens.name) > 25}">
											<c:out
												value="${fn:substring(tokens.name, 0, 25)}.." />
										</c:when>
										<c:otherwise>
											<c:out value="${tokens.name}" />
										</c:otherwise>
									</c:choose>		
									</span>					
                          		</td> 
                          		<td>
                          			${tokens.balance }
								</td>
                        	</tr>
                      	</c:forEach>
                      </tbody>
                    </table>
                  </div>
                </div>
                               
				<br/>
				<script type="text/javascript">
					$('.basic-popover').popover();
					$('.basic-tooltip').tooltip();
					function userDetailBack(){
						$('#user_list_head').show();
						$('#user_list_edit').hide();
					}
				</script>
                <!-- <ul class="pager" style="margin-bottom: 50px;">
                  <li><a href="#">&larr; 上一条</a></li>
                  <li><a href="#">下一条 &rarr;</a></li>
                </ul>
				 -->



     