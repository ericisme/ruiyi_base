<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>        
          
<jsp:include page="/WEB-INF/views/unis/frontEnd/gameCenter/selector.jsp" />
    
                <span id="game_center_menu_active" style="display: none">game_center_menu_reports</span>    
                            
           <script type="text/javascript" src="${ctx }/static/js/base/base.js"></script>     
           <script type="text/javascript" src="${ctx }/static/My97DatePicker/WdatePicker.js"></script>
           <div id="report_list_head"  class="animated animated_fast fadeIn">                
               <section>
                  <div class="alert alert-info">游乐场 交易报表 资讯 -- 列表</div>
                </section>
                <table class="table table-hover">
                  <thead>
                    <tr>
                      <th>序号</th>
                      <th>名称</th>
                      <th>详细信息</th>
                    </tr>
                  </thead>
                  <tbody>
                     <tr>
                      <td>1</td>
                      <td>總派票/總投幣 (不同遊戲)</td>
                      <td><a href="javascript:reportDetail('TX_REPORT_BY_GAME');">详 细 &raquo;</td>
                    </tr>     
                     <tr>
                      <td>2</td>
                      <td>指定日期(派票/投幣)(不同遊戲)</td>
                      <td><a href="javascript:reportDetail('GAME_DAILY_TX_REPORT_INDEX');">详 细 &raquo;</td>
                    </tr>
                     <tr>
                      <td>3</td>
                      <td>選擇遊戲(派票/投幣)(不同遊戲)</td>
                      <td><a href="javascript:reportDetail('GAME_DAILY_TX_REPORT_INDEX_SELECTED_GAME');">详 细 &raquo;</td>
                    </tr>
                     <tr>
                      <td>4</td>
                      <td>個別機台派票/投币</td>
                      <td><a href="javascript:reportDetail('ARCADE_DAILY_TX_REPORT_INDEX');">详 细 &raquo;</td>
                    </tr> 
                    <c:if test="${userType == 3 }"><!-- 世宇方管理员才能查看  個別帳號錢包 报表-->
                    <tr>
                      <td>5</td>
                      <td>個別帳號錢包</td>
                      <td><a href="javascript:reportDetail('USER_WALLETS_TX_REPORT_INDEX');">详 细 &raquo;</td>
                    </tr> 
                    </c:if>              
                  </tbody>
                </table>
                <br/>
			</div>
			<div id="report_list_list"></div>
			<div id="report_list_edit"></div>
			<script type="text/javascript">
				function reportDetail(report_path){
					$("#report_list_edit").load("${ctx }/frontEnd/notDecoratorsGameCenter/report/"+ report_path ,function(){
						$("#report_list_head").hide();
						$("#report_list_edit").addClass("animated animated_fast fadeIn");
						$("#report_list_edit").show();
					});

				}
			</script>

<jsp:include page="/WEB-INF/views/unis/frontEnd/gameCenter/sidebar.jsp" />



     