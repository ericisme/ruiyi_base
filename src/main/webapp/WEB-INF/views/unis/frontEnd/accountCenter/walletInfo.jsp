<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>        
          
<jsp:include page="/WEB-INF/views/unis/frontEnd/accountCenter/selector.jsp" />
    
    
                <span id="account_center_menu_active" style="display: none">account_center_menu_wallet_info</span>
                <section>
                  <div class="alert alert-info">会员 <strong>${user.username }</strong> 的 游戏钱包信息</div>
                </section>
                <section class="section-margin">
                <div class="table-responsive">
                    <table class="table table-striped">
                      <thead >
                        <tr id="tr_title">
                        	<th><strong>世宇币数</strong></th>
                        	<th><strong>游场</strong></th>
                        	<th><strong>彩票张数</strong></th>
                        	<th><strong>游戏</strong></th>
                        	<th><strong>代币个数</strong></th>
                        </tr>
                      </thead>
                      <tbody id="tb_dailys">
                      </tbody>
                    </table> 
				</div>                
                </section>
				<br/>     
			<script type="text/javascript">
			query();
			function query(){
				$.ajax({
		   			url : '${ctx}/frontEnd/accountCenter/walletInfoQuery',
		   			type : 'post', //数据发送方式
		   			dataType : 'JSON', //接受数据格式       
		   			data: "", //要传递的数据       
		   			success :
			   			function (reportJson){//回传函数(这里是函数名)		   					
		   					initReport(reportJson);		   					
		       			}
				});
			}  					
			function initReport(reportJson){
				var userWallet = reportJson.userWallet;
				var tb_dailys = "";									
					for(var j=0;j<userWallet.game_centers.length;j++){
						for(var k=0;k<userWallet.game_centers[j].tokens.length;k++){
							tb_dailys +="<tr>";
							if(j==0){
								var user_rowspan = 0;
								for(var m=0;m<userWallet.game_centers.length;m++){
									for(var n=0;n<userWallet.game_centers[m].tokens.length;n++){
										user_rowspan++;
									}
								}
								if(user_rowspan==0){
									user_rowspan = 1;
								}
								if(k==0){
									tb_dailys +="<td rowspan='"+user_rowspan+"'>"+userWallet.sycoin.balance+"</td>";
								}
							}
							if(k==0){
								tb_dailys +="<td rowspan='"+userWallet.game_centers[j].tokens.length+"'>"+userWallet.game_centers[j].name+"</td>";
								tb_dailys +="<td rowspan='"+userWallet.game_centers[j].tokens.length+"'>"+userWallet.game_centers[j].ticket.balance+"</td>";
							}
							tb_dailys +="<td>"+userWallet.game_centers[j].tokens[k].name+"</td>";
							tb_dailys +="<td>"+userWallet.game_centers[j].tokens[k].balance+"</td>";	
							tb_dailys +="</tr>";
						}						
					}						
				$("#tb_dailys").html(tb_dailys); 			
			}
			</script>

<jsp:include page="/WEB-INF/views/unis/frontEnd/accountCenter/sidebar.jsp" />



     