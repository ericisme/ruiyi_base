<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>        

                <span id="game_center_menu_active" style="display: none">game_center_menu_reports</span>
                <section>
                  <div class="alert alert-info">报表详细- <strong>個別帳號錢包</strong></div>
                </section>
                <a href="javascript:reportDetailBack();" class="btn btn-primary margin-bottom">&lsaquo;&nbsp;返回列表</a>
                <a href="javascript:query(1);" class="btn btn-primary">查询</a>
                <form class="form-inline">
	                <div class="form-group">
               			<label>查询条件：</label>
               			<select id="filter_key" name="filter_key" class="form-control">
               				<option value="handle_name">编号</option>
               				<option value="username">会员昵称</option>
               			 </select>
               			 <input id="filter_value" name="filter_value" value="" maxlength="20" class="form-control" />
                	</div>
                </form>
                <div class="row">
                  <div class="col-sm-12 animated animated_fast fadeIn" id="report_loading">
                  <div class="table-responsive">
                    <table class="table table-striped">
                      <thead >
                        <tr id="tr_title">
                        	<th colspan="2"><strong>用户/游场/钱包项目</strong></th>
                        	<th><strong>代币个数</strong></th>
                        	<th><strong>彩票张数</strong></th>
                        	<th><strong>状态</strong></th>
                        </tr>
                      </thead>
                      <tbody>                      
						<tr>
							<td colspan="3">loading...</td>
						</tr>
                      </tbody>
                    </table>
                    </div>
                  </div>
                  <div class="col-sm-26 animated animated_fast fadeIn" id="report_content">
                  <div class="table-responsive">
                    <table class="table table-striped">
                      <thead >
                        <tr id="tr_title">
                        	<th colspan="2"><strong>用户/游场/钱包项目</strong></th>
                        	<th><strong>代币个数</strong></th>
                        	<th><strong>彩票张数</strong></th>
                        	<th><strong>状态</strong></th>
                        </tr>
                      </thead>
                      <tbody id="tb_dailys">                      
                      	<!-- <tr>
                      		<td rowspan="2">PLAYER00000001</td>
                      		<td>walo game center</td>
                      		<td>100</td>
                      		<td>3652</td>
                      		<td>正常</td>
                      	</tr>
                      	<tr>
                      		<td>世宇乐园中山店</td>
                      		<td>20</td>
                      		<td>156</td>
                      		<td>正常</td>
                      	</tr>
                      	<tr>
                      		<td rowspan="2">PLAYER00000002</td>
                      		<td>walo game center</td>
                      		<td>2</td>
                      		<td>12</td>
                      		<td>正常</td>
                      	</tr>
                      	<tr>
                      		<td>世宇乐园中山店</td>
                      		<td>633</td>
                      		<td>516848</td>
                      		<td>正常</td>
                      	</tr> -->
                      </tbody>
                    </table>
                    </div>
                    <ul class="pager" style="margin-bottom: 50px;">
                  		<li>
                  			<a id="prewPage" href="#">&larr; 上一页</a>
                  		</li>
                  		&nbsp;&nbsp;第<span id="pageNum">1</span>页&nbsp;&nbsp;
                  		<li>
                  			<a id="nextPage" href="#">下一页 &rarr;</a>
                  		</li>
                	</ul>
                  </div>
                </div>
				<br/>
			<script type="text/javascript">
			query(1);
			function query(page){
				$("#report_loading").show();
				$("#report_content").hide();
				var filter_key   = $("#filter_key").val();
				var filter_value = $("#filter_value").val();
				$.ajax({
		   			url : '${ctx}/frontEnd/notDecoratorsGameCenter/report/USER_WALLETS_TX_REPORT_QUERY',
		   			type : 'post', //数据发送方式
		   			dataType : 'JSON', //接受数据格式       
		   			data: "filter_key="+filter_key+"&filter_value="+filter_value+"&page="+page, //要传递的数据       
		   			success :
			   			function (reportJson){//回传函数(这里是函数名)		   					
		   					initReport(reportJson,page);		   					
		       			}
				});
			}  					
			function initReport(reportJson,page){
				var userList = reportJson.userList;
				var walletsList = reportJson.walletsList;
				var hasNextPage = reportJson.hasNextPage;
				var tb_dailys = "";
				for(var i=0;i<userList.length;i++){
					for(var j=0;j<walletsList[i].length;j++){
						tb_dailys +="<tr>";	
						if(j==0){
							tb_dailys +="<td rowspan='"+walletsList[i].length+"'>"+userList[i].username+"</td>";						
						}
						tb_dailys +="<td>"+walletsList[i][j].game_center.name+"</td>";
						tb_dailys +="<td>"+walletsList[i][j].token+"</td>";
						tb_dailys +="<td>"+walletsList[i][j].ticket+"</td>";
						tb_dailys +="<td>"+walletsList[i][j].statusChinese+"</td>"	;
						tb_dailys +="</tr>";	
					}
				}
				
				$("#tb_dailys").html(tb_dailys); 	
				$("#pageNum").html(page);				
				if(page ==1){
					$("#prewPage").html("<font color='#888888'>&larr; 上一页</font>");
				}else{
					$("#prewPage").attr("href", "javascript:query("+(page-1)+")");
					$("#prewPage").html("&larr; 上一页");
				}
				if(hasNextPage){
					$("#nextPage").attr("href", "javascript:query("+(page+1)+")");
					$("#nextPage").html("下一页 &rarr;");
				}else{
					$("#nextPage").html("<font color='#888888'>下一页 &rarr;</font>");
				}
				
				$("#report_loading").hide();
				$("#report_content").show();
				
			}
		
			
			$('.basic-popover').popover();
			$('.basic-tooltip').tooltip();
			function reportDetailBack(){
				$('#report_list_head').show();
				$('#report_list_edit').hide();
			}
		</script>




     