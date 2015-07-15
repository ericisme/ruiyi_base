<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>        

                <span id="game_center_menu_active" style="display: none">game_center_menu_reports</span>
                <section>
                  <div class="alert alert-info">报表详细- <strong>總派票/總投幣 (不同遊戲)</strong></div>
                </section>
                <a href="javascript:reportDetailBack();" class="btn btn-primary">返回列表</a>
               
                  <div class="table-responsive">
                    <table class="table table-striped">
                      <thead >
                        <tr id="tr_title">
                        	<th><strong>游戏/统计项目</strong></th>
                        	<th><strong>總投幣-交易次数</strong></th>
                        	<th><strong>總投幣-充值总数</strong></th>
                        	<th><strong>總投幣-消费总数</strong></th>
                        	<th><strong>總派票-出票次数</strong></th>
                        	<th><strong>總派票-出票总数</strong></th>
                        	<th><strong>總派票-已兑换数</strong></th>
                        </tr>
                      </thead>
                      <tbody id="tb_games">

                      </tbody>
                    </table>
                    </div>
                  
				<br/>
				<script type="text/javascript">
				
					initReport();					
					function initReport(){
						var reportJson = JSON.parse('${reportJson}');
						var consumers = reportJson.msg.consumers;
						var tb_games         = "";
						for(var i = 0; i < consumers.length; i++){
							tb_games +="<tr>";
							tb_games +="<td>"+consumers[i].name+"</td>";
							tb_games +="<td>"+consumers[i].data.token.count+"</td>";
							tb_games +="<td>"+consumers[i].data.token.credit+"</td>";
							tb_games +="<td>"+consumers[i].data.token.debit+"</td>";
							tb_games +="<td>"+consumers[i].data.ticket.count+"</td>";
							tb_games +="<td>"+consumers[i].data.ticket.credit+"</td>";
							tb_games +="<td>"+consumers[i].data.ticket.debit+"</td>";
							tb_games +="</tr>";
						}
						$("#tb_games").html(tb_games);						
					}
				
					$('.basic-popover').popover();
					$('.basic-tooltip').tooltip();
					function reportDetailBack(){
						$('#report_list_head').show();
						$('#report_list_edit').hide();
					}
				</script>




     