<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>        

                <span id="game_center_menu_active" style="display: none">game_center_menu_reports</span>
                <section>
                  <div class="alert alert-info">报表详细- <strong>個別機台派票/投币</strong></div>
                </section>
                <a href="javascript:reportDetailBack();" class="btn btn-primary margin-bottom">&lsaquo;&nbsp;返回列表</a>
                
                    <div class="margin-bottom"> <!-- 加上这个 div-->
                  			<select class="form-control" id="cuk" name="cuk" onchange="query();">
                  				<c:forEach  items="${arcades }" var="arcade" varStatus="status">
                  					<option value="${arcade.cuk }">${arcade.device_id },${arcade.console_index }<c:if test="${arcade.arcade_status eq '3' }">,revoked</c:if></option>
                  				</c:forEach>
                  			</select>
                    </div> <!-- 加上这个 div-->                  
                
                    <div class="row"> <!-- 条件选择 10月12日,2013 - 11月12日,2013 -->
                      <div class="col-md-9 col-md-offset-3 col-sm-12">
                        <h4 class="text-muted"><span id="date-range" ></span></h4>
                        <div class="btn-group btn-group-lg margin-bottom">
                          <button id="btn-7days"  type="button" class="btn btn-default btn-date">近7天</button>
                          <button id="btn-30days" type="button" class="btn btn-default btn-date">近30天</button>
                          <!-- <button id="btn-90days" type="button" class="btn btn-default btn-date">最近90天</button> -->
                          <button id="btn-custom-date" type="button" class="btn btn-default">自定义</button>
                        </div>                        
                        <div id="custom-date" style="display:none;"><!-- 日期查询 -->
							开始日期：
                          <input id="begin_date" type="text" onfocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" readonly="" name="begin_date" realvalue=""></input>
                          <img width="22" height="32" title="点击选择日期" style="cursor:hand;vertical-align: middle" src="/static/My97DatePicker/skin/datePicker.gif" onclick="WdatePicker({el:$dp.$('begin_date'),startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"></img>
          		          <br></br>
							结束日期：
                          <input id="until_date" type="text" onfocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" readonly="" name="until_date" realvalue=""></input>
                          <img width="22" height="32" title="点击选择日期" style="cursor:hand;vertical-align: middle" src="/static/My97DatePicker/skin/datePicker.gif" onclick="WdatePicker({el:$dp.$('until_date'),startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"></img>
                          <div class="clearfix"><a href="javascript:$('#date-range').html($('#begin_date').val()+' 至 '+$('#until_date').val());query();" class="btn btn-primary cachefly">查 询</a></div>
                        </div> <!-- /.日期查询 -->
                      </div> <!-- /.col-md-9 col-md-offset-3 col-sm-12 -->
                    </div> <!-- /.条件选择 -->      
                  			
                  <div class="col-sm-12 animated animated_fast fadeIn" id="report_loading" style="display:none">
                  <div class="table-responsive">
                    <table class="table table-striped" >
                      <thead >
                        <tr id="tr_title">
                        	<th><strong>日期/统计项目</strong></th>
                        	<th><strong>總投幣-交易次数</strong></th>
                        	<th><strong>總投幣-充值总数</strong></th>
                        	<th><strong>總投幣-消费总数</strong></th>
                        	<th><strong>總派票-出票次数</strong></th>
                        	<th><strong>總派票-出票总数</strong></th>
                        	<th><strong>總派票-已兑换数</strong></th>
                        </tr>
                      </thead>
                      <tbody>
                      	<tr><td colspan="6">Loading...</td></tr>
                      </tbody>
                    </table>
                    </div>
                  </div>                  		
                  <div class="col-sm-12 animated animated_fast fadeIn" id="report_content">
                  <div class="table-responsive">
                    <table class="table table-striped" >
                      <thead >
                        <tr id="tr_title">
                        	<th><strong>日期/统计项目</strong></th>
                        	<th><strong>總投幣-交易次数</strong></th>
                        	<th><strong>總投幣-充值总数</strong></th>
                        	<th><strong>總投幣-消费总数</strong></th>
                        	<th><strong>總派票-出票次数</strong></th>
                        	<th><strong>總派票-出票总数</strong></th>
                        	<th><strong>總派票-已兑换数</strong></th>
                        </tr>
                      </thead>
                      <tbody id="tb_dailys">
                      </tbody>
                    </table>
                    </div>
                  </div>
                
				<br/>
			<script type="text/javascript">
			buttonGroupActive("btn-group", "button");
			function GetDateStr(AddDayCount){ 
				var dd = new Date(); 
				dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期 
				var y = dd.getFullYear(); 
				var m = dd.getMonth()+1;//获取当前月份的日期 
				if((m+"").length ==1){
					m = "0"+m;
				}
				var d = dd.getDate(); 
				if((d+"").length ==1){
					d = "0"+d;
				}
				return y+"-"+m+"-"+d; 
			}
				//最近7天
        		$("#btn-7days").click(function(){
        			$("#date-range").html(GetDateStr(-7)+" 至 "+GetDateStr(-1));
        			$("#begin_date").val(GetDateStr(-7));
        			$("#until_date").val(GetDateStr(-1));
        			query();
          		});
				//最近30天
        		$("#btn-30days").click(function(){
        			$("#date-range").html(GetDateStr(-30)+" 至 "+GetDateStr(-1));
        			$("#begin_date").val(GetDateStr(-30));
        			$("#until_date").val(GetDateStr(-1));
        			query();
          		});
				//最近90天
        		$("#btn-90days").click(function(){
        			$("#date-range").html(GetDateStr(-90)+" 至 "+GetDateStr(-1));
        			$("#begin_date").val(GetDateStr(-90));
        			$("#until_date").val(GetDateStr(-1));
        			query();
          		});
				//日期 自定义 触发 事件
            	$("#btn-custom-date").click(function(){
                	$("#custom-date").slideDown(300);
              	});
              	$(".btn-date").click(function(){
                	$("#custom-date").slideUp(300);
              	});
              //默认30天
              	$("#btn-30days").trigger("click");			
						
			function query(){
				var begin_date   = $("#begin_date").val();
				var until_date   = $("#until_date").val();
				var cuk          = $("#cuk").val();
				if(begin_date=="" ){			
					base.tips("开始日期 不能为空。");
					$("#begin_date").focus();
					return;
				}	
				if(until_date=="" ){			
					base.tips("结束日期 不能为空。");
					$("#until_date").focus();
					return;
				}
				if(begin_date >until_date){
					base.tips("开始日期 不能大于 结束日期。");
					$("#begin_date").focus();
					return;
				}
				if(until_date >=getToDay()){
					base.tips("结束日期 不能为今天或将来。");
					$("#until_date").focus();
					return;
				}
				$("#report_loading").show();
				$("#report_content").hide();
				$.ajax({
		   			url : '${ctx}/frontEnd/notDecoratorsGameCenter/report/ARCADE_DAILY_TX_REPORT_QUERY',
		   			type : 'post', //数据发送方式
		   			dataType : 'JSON', //接受数据格式       
		   			data: "begin_date="+begin_date+"&until_date="+until_date+"&cuk="+cuk, //要传递的数据       
		   			success :
			   			function (reportJson){//回传函数(这里是函数名)
		   					initReport(reportJson);
		       			}
				});
			}  					
			function initReport(reportJson){	
				var data = reportJson.msg.data;
				var tb_dailys = "";	
				for(var i = 0; i < data.length; i++){
					tb_dailys +="<tr>";							
					tb_dailys +="<td>"+data[i].date+"</td>";
					tb_dailys +="<td>"+data[i].token.count+"</td>";
					tb_dailys +="<td>"+data[i].token.credit+"</td>";
					tb_dailys +="<td>"+data[i].token.debit+"</td>";
					tb_dailys +="<td>"+data[i].ticket.count+"</td>";
					tb_dailys +="<td>"+data[i].ticket.credit+"</td>";
					tb_dailys +="<td>"+data[i].ticket.debit+"</td>";							
					tb_dailys +="</tr>";
				}
				$("#tb_dailys").html(tb_dailys);	
				$("#report_loading").hide();
				$("#report_content").show();
			}
			  var newdate = null;
			  function getToDay(){
			   var now = new Date();
			   var nowYear = now.getFullYear();
			   var nowMonth = now.getMonth();
			   var nowDate = now.getDate();
			   newdate = new Date(nowYear,nowMonth,nowDate);
			   nowMonth = doHandleMonth(nowMonth + 1);
			   nowDate = doHandleMonth(nowDate);
			   return nowYear+"-"+nowMonth+"-"+nowDate;
			  }
			  function doHandleMonth(month){
			   if(month.toString().length == 1){
			    month = "0" + month;
			   }
			   return month;
			  }
			
			$('.basic-popover').popover();
			$('.basic-tooltip').tooltip();
			function reportDetailBack(){
				$('#report_list_head').show();
				$('#report_list_edit').hide();
			}
		</script>




     