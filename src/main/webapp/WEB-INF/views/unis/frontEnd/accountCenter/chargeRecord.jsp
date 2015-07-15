<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>        
          
<jsp:include page="/WEB-INF/views/unis/frontEnd/accountCenter/selector.jsp" />
    
    
                <span id="account_center_menu_active" style="display: none">account_center_menu_charge_record</span>
                <section>
                  <div class="alert alert-info">会员 <strong>${user.username }</strong> 充值记录查询</div>
                </section>
                <section class="section-margin">
                        <div class="btn-group margin-bottom">
                          <button id="btn-sycoin"      type="button" class="btn btn-default btn-date" >游币充值</button>
                          <button id="btn-tokens"      type="button" class="btn btn-default btn-date" >实体币充值</button>
                          <!-- <button id="btn-exchange"    type="button" class="btn btn-default btn-date" >兑换记录</button> -->
                        </div>    
                    <!-- 世宇币充值记录div -->    
                    <div id="btn-sycoin-div" style="display: none">
                    	<div class="table-responsive">
                    	<table class="table table-striped" >
                      		<thead >                      
                        		<tr id="tr_title">
                       				<th><strong>充值时间</strong></th>
                       				<th><strong>订单号</strong></th>
                       				<th><strong>充值数量</strong></th>
                        		</tr>
                      		</thead>
                      		<tbody id="btn-sycoin-tb"></tbody>
                    	</table>  
                    	</div>
                    	<ul class="pager" style="margin-bottom: 50px;">
                    		<li><a id="prewPage-sycoin" href="#">&larr; 上一页</a></li>
                  			&nbsp;&nbsp;第<span id="pageNum-sycoin">1</span>页&nbsp;&nbsp;
                  			<li><a id="nextPage-sycoin" href="#">下一页 &rarr;</a></li>
                		</ul>
                    </div>
                    <!-- 游戏币充值记录div -->    
                    <div id="btn-tokens-div"  style="display: none">
                    	<div class="table-responsive">
                    	<table class="table table-striped" >
                      		<thead >                      
                        		<tr id="tr_title">
                       				<th><strong>充值时间</strong></th>
                       				<th><strong>订单号</strong></th>                       				
                       				<!-- <th><strong>充值游戏</strong></th> -->
                       				<!-- <th><strong>充值游场</strong></th> -->
                       				<th><strong><nobr>充值数量</nobr></strong></th>
                        		</tr>
                      		</thead>
                      		<tbody id="btn-tokens-tb"></tbody>
                    	</table> 
                    	</div> 
                    	<ul class="pager" style="margin-bottom: 50px;">
                    		<li><a id="prewPage-tokens" href="#">&larr; 上一页</a></li>
                  			&nbsp;&nbsp;第<span id="pageNum-tokens">1</span>页&nbsp;&nbsp;
                  			<li><a id="nextPage-tokens" href="#">下一页 &rarr;</a></li>
                		</ul>
                    </div>     
                    <!-- 世宇币兑换记录div -->    
                    <div id="btn-exchange-div"  style="display: none">
                    	<div class="table-responsive">
                    	<table class="table table-striped" >
                      		<thead >                      
                        		<tr id="tr_title">
                       				<th><strong>兑换时间</strong></th>
                       				<th><strong>兑换游戏</strong></th>
                       				<th><strong>兑换游场</strong></th>
                       				<th><strong><nobr>兑换游戏币数</nobr></strong></th>
                       				<th><strong><nobr>扣减世宇币数</nobr></strong></th>
                        		</tr>
                      		</thead>
                      		<tbody id="btn-exchange-tb"></tbody>
                    	</table>  
                    	</div>
                    	<ul class="pager" style="margin-bottom: 50px;">
                    		<li><a id="prewPage-exchange" href="#">&larr; 上一页</a></li>
                  			&nbsp;&nbsp;第<span id="pageNum-exchange">1</span>页&nbsp;&nbsp;
                  			<li><a id="nextPage-exchange" href="#">下一页 &rarr;</a></li>
                		</ul>
                    </div>                                               
                </section>
				<br/>     
			<script type="text/javascript">
				$(".btn-group button").on('click', function() {
			      $(this).siblings().removeClass("active").end().addClass("active");
			    });
				//世宇币按钮
        		$("#btn-sycoin").click(function(){
        			$("#btn-tokens-div").hide();
        			$("#btn-exchange-div").hide();
        			$("#btn-sycoin-div").hide();
        			querySycoinRecord(1, "fade");
        			//$("#btn-sycoin-div").fadeIn();
          		});
				//游戏币按钮
        		$("#btn-tokens").click(function(){
        			$("#btn-sycoin-div").hide();
        			$("#btn-exchange-div").hide();
        			$("#btn-tokens-div").hide();
        			queryTokensRecord(1, "fade");
        			//$("#btn-tokens-div").fadeIn();
          		});
				//兑换记录按钮
        		$("#btn-exchange").click(function(){
        			$("#btn-sycoin-div").hide();
        			$("#btn-tokens-div").hide();
        			$("#btn-exchange-div").hide();
        			queryExchangeRecord(1, "fade");
        			//$("#btn-exchange-div").fadeIn();
          		});
	            //默认点中世宇币
              	$("#btn-sycoin").trigger("click");
	            //世宇币查询
				function querySycoinRecord(page,type){
	            	if(type==null){
	            		$("#btn-sycoin-div").slideUp();
	            	}	
					$.ajax({
			   			url : '${ctx}/frontEnd/accountCenter/chargeRecordQuerySycoinRechargeRecord',
			   			type : 'post', //数据发送方式
			   			dataType : 'JSON', //接受数据格式       
			   			data: "page="+page, //要传递的数据       
			   			success :
				   			function (responseJson){//回传函数(这里是函数名)
			   					var status = responseJson.status;
				   				var hasNextPage = responseJson.hasNextPage;
				   				var records = responseJson.records;
				   				if(status=="not login"){
				   					window.location.href='${ctx }/frontEnd/walo3LeggedLogin?protocol='+window.location.protocol+'&host='+window.location.host;
				   				}else if(status=="success"){
				   					var content = "";
				   					for(var i=0; i<records.length; i++){
				   						content +="<tr>";
				   						content +="<td>"+records[i].record_date+"</td>";
				   						content +="<td>"+records[i].outTradeNo+"</td>";
				   						content +="<td>"+records[i].amount+"</td>"; 
				   						content +="</tr>";
				   					}
				   					$("#btn-sycoin-tb").html(content);				   					
				   					$("#pageNum-sycoin").html(page);				
				   					if(page ==1){
				   						$("#prewPage-sycoin").html("<font color='#888888'>&larr; 上一页</font>");
				   					}else{
				   						$("#prewPage-sycoin").attr("href", "javascript:querySycoinRecord("+(page-1)+")");
				   						$("#prewPage-sycoin").html("&larr; 上一页");
				   					}
				   					if(hasNextPage){
				   						$("#nextPage-sycoin").attr("href", "javascript:querySycoinRecord("+(page+1)+")");
				   						$("#nextPage-sycoin").html("下一页 &rarr;");
				   					}else{
				   						$("#nextPage-sycoin").html("<font color='#888888'>下一页 &rarr;</font>");
				   					}
				   					if(type==null){
				   						$("#btn-sycoin-div").slideDown();
				   					}else{
				   						$("#btn-sycoin-div").fadeIn();
				   					}
				   				}
			       			}
					});
				}  
	            //游戏币查询
				function queryTokensRecord(page,type){
	            	if(type==null){
	            		$("#btn-tokens-div").slideUp();
	            	}		            	
					$.ajax({
			   			url : '${ctx}/frontEnd/accountCenter/chargeRecordQueryTokensRechargeRecord',
			   			type : 'post', //数据发送方式
			   			dataType : 'JSON', //接受数据格式       
			   			data: "page="+page, //要传递的数据       
			   			success :
				   			function (responseJson){//回传函数(这里是函数名)
			   					var status = responseJson.status;
				   				var hasNextPage = responseJson.hasNextPage;
				   				var records = responseJson.records;
				   				if(status=="not login"){
				   					window.location.href='${ctx }/frontEnd/walo3LeggedLogin?protocol='+window.location.protocol+'&host='+window.location.host;
				   				}else if(status=="success"){
				   					var content = "";
				   					for(var i=0; i<records.length; i++){
				   						content +="<tr>";
				   						content +="<td>"+records[i].record_date+"</td>";
				   						content +="<td>"+records[i].outTradeNo+"</td>";				   						
				   						//content +="<td>"+records[i].consumer.name+"</td>";
				   						//content +="<td>"+records[i].game_center.name+"</td>";
				   						content +="<td>"+records[i].amount+"</td>";
				   						content +="</tr>";
				   					}
				   					$("#btn-tokens-tb").html(content);
				   					$("#pageNum-sycoin").html(page);				
				   					if(page ==1){
				   						$("#prewPage-tokens").html("<font color='#888888'>&larr; 上一页</font>");
				   					}else{
				   						$("#prewPage-tokens").attr("href", "javascript:queryTokensRecord("+(page-1)+")");
				   						$("#prewPage-tokens").html("&larr; 上一页");
				   					}
				   					if(hasNextPage){
				   						$("#nextPage-tokens").attr("href", "javascript:queryTokensRecord("+(page+1)+")");
				   						$("#nextPage-tokens").html("下一页 &rarr;");
				   					}else{
				   						$("#nextPage-tokens").html("<font color='#888888'>下一页 &rarr;</font>");
				   					}
				   					if(type==null){
				   						$("#btn-tokens-div").slideDown();
				   					}else{
				   						$("#btn-tokens-div").fadeIn();
				   					}
				   				}
			       			}
					});
				}  
	            //兑换记录查询
				function queryExchangeRecord(page,type){
	            	if(type==null){
	            		$("#btn-exchange-div").slideUp();
	            	}					
					$.ajax({
			   			url : '${ctx}/frontEnd/accountCenter/chargeRecordQuerySycoinExchangeRecord',
			   			type : 'post', //数据发送方式
			   			dataType : 'JSON', //接受数据格式       
			   			data: "page="+page, //要传递的数据       
			   			success :
				   			function (responseJson){//回传函数(这里是函数名)
			   					var status = responseJson.status;
				   				var hasNextPage = responseJson.hasNextPage;
				   				var records = responseJson.records;
				   				if(status=="not login"){
				   					window.location.href='${ctx }/frontEnd/walo3LeggedLogin?protocol='+window.location.protocol+'&host='+window.location.host;
				   				}else if(status=="success"){
				   					var content = "";
				   					for(var i=0; i<records.length; i++){
				   						content +="<tr>";
				   						content +="<td>"+records[i].record_date+"</td>";			   						
				   						content +="<td>"+records[i].consumer.name+"</td>";
				   						content +="<td>"+records[i].game_center.name+"</td>";
				   						content +="<td>"+records[i].token_credited+"</td>";
				   						content +="<td>"+records[i].sycoin_debited+"</td>";
				   						content +="</tr>";
				   					}
				   					$("#btn-exchange-tb").html(content);
				   					$("#pageNum-exchange").html(page);				
				   					if(page ==1){
				   						$("#prewPage-exchange").html("<font color='#888888'>&larr; 上一页</font>");
				   					}else{
				   						$("#prewPage-exchange").attr("href", "javascript:queryExchangeRecord("+(page-1)+")");
				   						$("#prewPage-exchange").html("&larr; 上一页");
				   					}
				   					if(hasNextPage){
				   						$("#nextPage-exchange").attr("href", "javascript:queryExchangeRecord("+(page+1)+")");
				   						$("#nextPage-exchange").html("下一页 &rarr;");
				   					}else{
				   						$("#nextPage-exchange").html("<font color='#888888'>下一页 &rarr;</font>");
				   					}
				   					if(type==null){
				   						$("#btn-exchange-div").slideDown();
				   					}else{
				   						$("#btn-exchange-div").fadeIn();
				   					}
				   				}
			       			}
					});
				} 	          
			</script>

<jsp:include page="/WEB-INF/views/unis/frontEnd/accountCenter/sidebar.jsp" />



     