<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>        
          
<jsp:include page="/WEB-INF/views/unis/frontEnd/accountCenter/selector.jsp" />
    
    
                <span id="account_center_menu_active" style="display: none">account_center_menu_info</span>
                <section>
                  <div class="alert alert-info">会员 <strong>${user.username }</strong> 的 基本信息</div>
                </section>
                <section class="section-margin">
                  <div class="row">
                    <div class="col-sm-6 col-md-6">
                      <table class="table simple-table">
                        <tbody>
                          <tr>
                            <td><strong>手机号</strong></td>
                            <td>${user.username }</td>
                          </tr>
                          <tr>
                            <td><strong>昵 称</strong></td>
                            <td>
                           		<span id="handle_name_show" onclick="editField('handle_name_show', 'handle_name_input');" title="单击修改">${user.handle_name }&nbsp;</span>
                            	<input type="text" id="handle_name_input" value="${user.handle_name }" name="handle_name" maxlength="50" size="20" style="display: none" title="回车确定修改." onkeydown="if(event.keyCode==13) {save_edit('handle_name_show',this);}" onblur="save_edit('handle_name_show',this);"/>
                            </td>
                          </tr>
                          <tr>
                            <td><strong>国 家</strong></td>
                            <td>${user.country }</td>
                          </tr>
                          <!-- <tr>
                            <td><strong>email</strong></td>
                            <td>
                            ${user.email }-->
                            		<!--<c:choose>
										<c:when test="${fn:length(user.email) > 15}">
											<c:out
												value="${fn:substring(user.email, 0, 15)}.." />
										</c:when>
										<c:otherwise>
											<c:out value="${user.email}" />
										</c:otherwise>
									</c:choose>     -->                       	
                             <!-- </td>
                          </tr>-->
                          <!--
                          <tr>
                            <td><strong>电 话</strong></td>
                            <td>
                           		<span id="phone_show" onclick="editField('phone_show', 'phone_input');" title="单击修改">${user.phone }&nbsp;</span>
                            	<input type="text" id="phone_input" value="${user.phone }" name="phone" maxlength="12" size="20" style="display: none" title="回车确定修改." onkeydown="if(event.keyCode==13) {save_edit('phone_show',this);}" onblur="save_edit('phone_show',this);"/>                            
                            </td>
                          </tr>-->
                          <tr>
                            <td><strong>註冊時間</strong></td>
                            <td><fmt:formatDate value="${user.registered_at}" pattern="yyyy-MM-dd" /></td>
                          </tr>
                          <tr>
                            <td><strong>注销时间</strong></td>
                            <td><fmt:formatDate value="${user.expire}" pattern="yyyy-MM-dd" /></td>
                          </tr>
                          <tr>
                            <td><strong>个性签名</strong></td>
                            <td>
                           		<span id="personal_status_show" onclick="editField('personal_status_show', 'personal_status_input');" title="单击修改">${user.personal_status }&nbsp;</span>
                            	<input type="text" id="personal_status_input" value="${user.personal_status }" name="personal_status" maxlength="50" size="20" style="display: none" title="回车确定修改." onkeydown="if(event.keyCode==13) {save_edit('personal_status_show',this);}" onblur="save_edit('personal_status_show',this);"/>                               
                            </td>
                          </tr>    
                        </tbody>
                      </table>
                    </div>
                    <div class="col-sm-6 col-md-6">
                      <table class="table simple-table">
                        <tbody>
                          <tr>
                            <td><strong>游币</strong></td>
                            <td><font color="red">${sycoin.balance }</font></td>
                          </tr>          
                          <tr>
                            <td><strong>积分</strong></td>
                            <td><font color="red">${user.achievement_score }</font></td>
                          </tr>      
                          <tr>
                            <td><strong>实体币</strong></td>
                            <td><font color="blue">${token.balance }</font></td>
                          </tr>                                                     
                          <tr>
                            <td><strong>彩票</strong></td>
                            <td><font color="blue">${ticket.balance }</font></td>
                          </tr>                        
                          <!-- <tr>
                            <td><strong>世宇积分</strong></td>
                            <td>${user.achievement_score }</td>
                          </tr> -->
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

                          <tr>
                            <td><strong>状 态</strong></td>
                            <td>${user.statusChinese }</td>
                          </tr>
                      
                          
                        </tbody>
                      </table>
                    </div>
					<script type="text/javascript">
						function editField(show_id, input_id){
							$("#"+show_id).hide();
							$("#"+input_id).show();
							$("#"+input_id).focus();
						}
						function save_edit(show_id,input){
							var edit_field = $(input).attr("name");
							var edit_value = $(input).val();
							if(edit_field=="handle_name"){
								if(edit_value==""){
									alert("昵 称 不能为空");
									return;
								}
							}
							$.ajax({
					   			url:'${ctx}/frontEnd/accountCenter/accountEditProfile',
					   			type:'post', //数据发送方式
					   			dataType:'JSON', //接受数据格式       
					   			data:'edit_field='+edit_field+'&edit_value='+edit_value, //要传递的数据       
					   			success:
						   			function (data){//回传函数(这里是函数名)
					   					if(data.status=="not login"){
					   						window.location.href='${ctx }/frontEnd/walo3LeggedLogin?protocol='+window.location.protocol+'&host='+window.location.host;
					   					}else if(data.status=="success"){
					   						window.location.reload();
					   						//$("#"+show_id).html(edit_value+"&nbsp;");
					   					}
										//$(input).hide();
										//$("#"+show_id).show();
					       			}
							});
						}
					</script>
                  </div>
                </section>
				<br/>     


<jsp:include page="/WEB-INF/views/unis/frontEnd/accountCenter/sidebar.jsp" />



     