<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">
var gameSelectTmpDataJson = ${gameAndGameCenterListJsonData};

$(document).ready(function() {
		
	if (gameSelectTmpDataJson != "") {
		var flag1 = "1";
		 $(gameSelectTmpDataJson).each(function(){ 
				$('#gameSelect').append('<option id="gameSelectId' +this.gameKey+ '" value="' + this.gameKey +  '">' + this.gameName + '</option>');
				if(flag1=="1"){
					$('#gameCenterSelect').html('');
					var flag2 = "1";
					$(this.gameCenterList).each(function(){ 
						$('#gameCenterSelect').append('<option changeRate= "' + this.changeRate + '" id="gameCenterSelectId' +this.gameCenterKey+ '" value="' + this.gameCenterKey +  '">' + this.gameCenterName + '</option>');
						if(flag2=="1"){
							$('#rate').html("1:" + this.changeRate);
							flag="2";
						}
					});
				}
				flag1="2";
			}); 
	}
	
	$('#gameSelect').change(function(){
		var tmp = $('#gameSelect').val();
		 $(gameSelectTmpDataJson).each(function(){
			 if(this.gameKey==tmp){
				 $('#gameCenterSelect').html('');
				 var flag1 = "1";
				 $(this.gameCenterList).each(function(){ 
				 	$('#gameCenterSelect').append('<option changeRate= "' + this.changeRate + '" id="gameCenterSelectId' +this.gameCenterKey+ '" value="' + this.gameCenterKey +  '">' + this.gameCenterName + '</option>');
				 	if(flag1=="1"){
						$('#rate').html("1:" + this.changeRate);
						flag1="2";
					}
				 }); 
			 }
		 }); 
	})
	$('#gameCenterSelect').change(function(){
		$('#rate').html("1:" + $('#gameCenterSelectId' + $('#gameCenterSelect').val()).attr("changeRate"));
	});
	
	$('#submit').click(function(){
		if($('#chargeNumber').val() == '' || isNaN($('#chargeNumber').val()) || $('#chargeNumber').val() < 0 ){
			$('#chargeNumberDiv').addClass("has-error");
			return false;
		}
	    if(confirm("确认兑换？")){
		$.ajax({
			cache: true,
			dataType: "json",//返回json格式的数据
			type: "POST",
			url:"/frontEnd/accountCenter/change",
			data:'gameSelect=' + $('#gameSelect').val() +'&gameCenterSelect=' + $('#gameCenterSelect').val() + '&chargeNumber=' + $('#chargeNumber').val(),
			async: false,
		    error: function(request) {
		    	alert("error");
		    },
		    success: function(data) {
				if(data.exchangeStatus== -1 ){//出现错误
					$('#alert_danger').html("<strong>兑换失败！！！</strong>");
					$('#alert_danger').removeClass("hidden");
			    	$('#alert_danger').fadeIn();
			    	$('#alert_danger').fadeOut(2000);
				}else if(data.exchangeStatus == 0){//世宇币不足
					$('#alert_danger').html("<strong>兑换失败,世宇币不足！！！</strong>");
					$('#alert_danger').removeClass("hidden");
			    	$('#alert_danger').fadeIn();
			    	$('#alert_danger').fadeOut(2000);
				}else if(data.exchangeStatus == 1){//兑换成功
					$('#sycoinBalance').html(data.sycoin);
					$('#alert_success').html("<strong>兑换成功！！！</strong>");
			    	$('#alert_success').removeClass("hidden");
			    	$('#alert_success').fadeIn();
			    	$('#alert_success').fadeOut(2000);
				}
		    	
		    }
		});
		
	}
	});
});

</script>
<jsp:include
	page="/WEB-INF/views/unis/frontEnd/accountCenter/selector.jsp" />


<span id="account_center_menu_active" style="display: none">account_center_menu_exchange</span>
<section>
	<div class="alert alert-info">
		会员 <strong>${user.username }</strong> 游戏币充值, 当前世宇币数: <label style="color:red" id="sycoinBalance">${sycoin.balance }</label>个
	</div>
	 <div id="alert_danger" class="alert alert-danger hidden">
	</div> 
	<div id="alert_success" class="alert alert-success hidden">
	</div>
</section>
<section class="section-margin"></section>
<br />
<div class="container">
	<div class="row">
		<div class="col-sm-8 col-md-9">
			<div class="art-content">
				<div class="row">
					<div class="col-sm-8">

						<div id="gameorgamecenterpick">
							<div class="form-group">
								<label >游戏选择:</label> <select
									id="gameSelect"
									name="gameSelect" class="form-control unis-game-cz">
								</select>
							</div>
							<div class="form-group">
								<label >游乐场选择:</label> <select
									 id="gameCenterSelect"
									name="gameCenterSelect" class="form-control unis-game-cz">
								</select>
							</div>
						</div>
						
							<div class="form-group" id="chargeNumberDiv">
								<label for="czNumber">兑换数量:(世宇币)</label> <input type="text"
									autocomplete="off" class="form-control czNumber" id="chargeNumber"
									name="chargeNumber" placeholder="请数要兑换的世宇币数..." value="${chargeNumber }" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"> 
							</div>
							<div>
							<div class="form-group">
								<label >兑换比例:</label> <label id="rate"></label>
							</div>
						</div>
						
						
						<input id="submit" type="button"  class="btn btn-primary" value="立即充值" />
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<jsp:include
	page="/WEB-INF/views/unis/frontEnd/accountCenter/sidebar.jsp" />



