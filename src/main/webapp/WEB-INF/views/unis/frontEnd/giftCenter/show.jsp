<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<span id="sitemeshtitle" style="display: none">title_mall</span>
<title>世宇游戏-礼品中心-${commodity.name }</title>


        <div class="container">
          
          <ol class="breadcrumb">
              <li><a href="${ctx}/">首页</a></li>
              <li><a href="${ctx}/frontEnd/giftcenter/index">礼品中心</a></li>
              <li><a href="${ctx}/frontEnd/giftcenter/index?_c_id=${topCommodityCategory.id }&order=0">${topCommodityCategory.name }</a></li>
              <li>${commodity.name }</li>
          </ol> <!-- end breadcrumb -->
          <div class="row">
            <div class="col-sm-8">
              <!-- item gallery -->
                <div class="unis-item-gallery">
                    <div class="item-large-preview">
                    <!--<div align="center">-->
                    	<img src="${commodity.pictureList[0].url }" alt="" id="active-img">
                    </div>
                    <div class="item-thumbnails">
                      <ul class="nav-images desktop-thums hidden-xs">
                      	<c:forEach items="${commodity.pictureList }" var="picture" varStatus="status">
                      		<c:if test="${status.index < 7 }">
                      			<li <c:if test="${status.index eq 0}">class="active"</c:if>>
                      				<img src="${picture.previewUrl }" alt="" onclick="$('#active-img').attr('src','${picture.url }');">
                      			</li>
                      		</c:if>
                      	</c:forEach>
                      </ul>
                      <ul class="nav-images mobi-thums visible-xs">
                      	<c:forEach items="${commodity.pictureList }" var="picture" varStatus="status">
                      		<c:if test="${status.index < 4 }">
                        		<li <c:if test="${status.index eq 0}">class="active"</c:if>>
                        			<img src="${picture.previewUrl }" alt="" onclick="$('#active-img').attr('src','${picture.url }');">
                        		</li>
                        	</c:if>   
                        </c:forEach>
                      </ul>
                    </div>
                      <script type="text/javascript">
                      		$().ready(function (){
                      			buttonGroupActive("nav-images", "li");
                      		})
                      </script>
                </div>
            </div> <!-- /.col-sm-8 -->

            <div class="col-sm-4">
              <div class="action-card">
              <!-- 判断是否已经登录 -->
              	<c:if test="${isLogin }">
                	<a href="" class="pull-right text-muted" style="border-bottom: dotted 2px #ddd;">你的彩票结余：<span>${ticket.balance}</span></a>
                </c:if>
                <div class="clearfix"></div>
                <h3>${commodity.name }</h3>
                <form action="">
                  <div class="purchase-info">
                    <ul>
                      <li>物品编号:&nbsp;<span>${commodity.commodityNo }</span></li>
                      <li>已兑数量:&nbsp;<span>${count }</span></li>
                      <li>库 存:&nbsp;<span>${commodity.stocks }</span></li>
                      <li>邮 费:&nbsp;<span>免费</span></li>
                    </ul>
                    
                  </div>

                    
                  <div class="unis-item-qty">
                    <label for="gift-item-qty">兑换数量:&nbsp;</label>
                    <input type="hidden" value="${commodity.price }" id="single-price"/>
                    <span class="btn" onclick="gift_itm_qty_minus();"><i class="fa fa-minus"></i></span>
                    <input id="gift-item-qty" class="number-form-control" type="text" value="1" onChange="$('#unis-total-price').html($('#single-price').val()*this.value);" readonly maxlength="2"/>
                    <span class="btn btn-defalut" onclick="gift_itm_qty_plus();"><i class="fa fa-plus"></i></span>
                    <script type="text/javascript">
                    	function gift_itm_qty_minus(){
                    		var gift_itm_qty =  parseInt($('#gift-item-qty').val());
                    		if(gift_itm_qty >1){
                    			gift_itm_qty = gift_itm_qty-1;
                    		}
                    		$('#gift-item-qty').val(gift_itm_qty);
                    		$("#unis-total-price").html(gift_itm_qty*$("#single-price").val());
                    	}
                    	function gift_itm_qty_plus(){
                    		var gift_itm_qty =  parseInt($('#gift-item-qty').val());
                    		if(true){
                    			gift_itm_qty = gift_itm_qty+1;
                    		}
                    		$('#gift-item-qty').val(gift_itm_qty);
                    		$("#unis-total-price").html(gift_itm_qty*$("#single-price").val());
                    	}
                    	
                    	function saveOrder() {
							var amount = $('#gift-item-qty').val();//amount
							var commodityId = ${commodity.id };//商品id
							var receiverName = $('#post-name').val();//姓名
							var zipcode = $('#postcode').val();//邮编
							var address = $('#post-address').val();//地址
							var cellNumber = $('#post-mobile-number').val();//手机号码
							var flag = true;
							if(amount==''){
								flag = false;
							}
							if(commodityId==''){
								flag = false;
							}
							if(receiverName==''){
								$('#post-name-div').addClass("has-error");
								flag = false;
							}
							if(zipcode==''){
								$('#postcode-div').addClass("has-error");
								flag = false;
							}
							if(address==''){
								$('#post-address-div').addClass("has-error");
								flag = false;
							}
							if(cellNumber==''){
								$('#post-mobile-number-div').addClass("has-error");
								flag = false;
							}
							if(!flag){
								return flag;
							}
							
							if(receiverName.length > 20 || zipcode.length > 20  ||  cellNumber.length > 20 || address.length > 255){
								alert("输入的地址信息长度不符合要求");
								return false;
							}
							
							
							
							var params = "amount=" + amount + "&commodityId=" + commodityId + 
									"&receiverName=" + receiverName + "&zipcode=" + zipcode + 
									"&address=" + address + "&cellNumber=" + cellNumber ;
							var encodeParams = encodeURI(params,"utf-8");
							
							$('#shipments-btn').attr("disabled","disabled");
                    		$.ajax({
                    			cache: true,
                    			type: "POST",
                    			dataType:"json",
                    			url:"/frontEnd/giftcenter/exchange",
                    			data:encodeParams,
                    			async: false,
                    		    error: function(request) {
                    		    	$('#shipments-btn').removeAttr("disabled");
                    		    },
                    		    success: function(data) {
                    		    	$('#shipments-btn').removeAttr("disabled");
                    		    	if(data.code=='0'){
                    		    		window.location="${ctx}/frontEnd/accountCenter/mygift/show/" + data.msg;
                    		    	}else{
                    		    		alert(data.msg);
                    		    	}
                    		    }
                    		});
                    	}
                    	function removeHaserror(){
                    		$('[id^=post]').removeClass("has-error");
                    	}
                    	$().ready(function(){
                    		$('#shipments-btn').click(function(){
                    			removeHaserror();
                    			saveOrder();
                    		});
                    		$('#cancel-btn').click(function(){
                    			removeHaserror();
                    		});
                    	})
                    	
                    </script>
                  </div>

                  <div class="item-price">
                    <span id="unis-total-price">${commodity.price }</span><span> 彩票</span>
                  </div>
                  
                  <!-- notification alert 错误显示-->
                  <c:if test="${isLogin }">
                  <c:if test="${user.accumulated_ascore < commodity.price}">
                  <div class="margin-bottom">
                   <!--  <span class="purchase-error text-danger error on"><i class="fa fa-frown-o fa-fw" style="color:#e97878;"></i>哎哟~ 你的积分不够哦~!</span> -->
                  </div>
                  </c:if>
                  </c:if>
                  
                  <!-- /.notification-->
                  	<c:if test="${isLogin }">
                  		<a class="btn btn-lg btn-block btnRedeem" id="redeemBtn" ><span class="glyphicon glyphicon-gift text-danger"></span>&nbsp;兑 换</a>
               	 	</c:if>
                	<c:if test="${ not isLogin }">
                		<a class="btn btn-lg btn-block btnRedeem" href="javascript:walo3LeggedLogin();"><span class="glyphicon glyphicon-gift text-danger"></span>&nbsp;请先登录</a>
                	</c:if>
                	
               	  <!-- 收货地址 -->
                  <div class="panel panel-default deliveryinfo" style="display:none;">
                    <div class="panel-heading listheader"><h3 class="panel-title"><i class="fa-truck fa fa-fw"></i>收货地址</h3></div>
                    <div class="panel-body">
                      <div id="post-name-div" class="form-group ">
                        <label class="control-label" for="post-name">收货人姓名:</label>
                        <input type="text" class="form-control" id="post-name" value="${deliveryAddress.receiverName }"placeholder="请输入姓名">
                      </div>
                      <div id="post-address-div" class="form-group">
                        <label class="control-label" for="post-address">详细地址:</label>
                        <textarea id="post-address" class="form-control" rows="2" placeholder="请输入省市街道门牌号" >${deliveryAddress.address }</textarea>
                        <span class="help-block">请正确填写地址, 以确保礼物送达你手上</span>
                      </div>
                      <div id="postcode-div" class="form-group">
                        <label class="control-label" for="postcode">邮编:</label>
                        <input type="text" class="form-control" id="postcode" placeholder="请输入邮政编码" value="${deliveryAddress.zipcode }">
                      </div>
                      <div id="post-mobile-number-div" class="form-group">
                        <label class="control-label" for="post-mobile-number">手机:</label>
                        <input type="text" class="form-control" id="post-mobile-number" placeholder="请输入收货人手机号码" value="${deliveryAddress.cellNumber }">
                      </div>
                      <div class="btn-group btn-group-lg">
                        <button id="shipments-btn" type="button" class="btn btn-default" >发货</button>
                        <button id="cancel-btn" type="button" class="btn btn-default cancel" >取消</button>
                      </div>
                    </div>
                  </div>
                  <!-- /. 收货地址-->
                  <script type="text/javascript">
          			$(document).ready(function(){
            			$("#redeemBtn").click(function(){
            				$(".deliveryinfo").slideDown();
            			});
            			$(".cancel").click(function(){
            				$(".deliveryinfo").slideUp();
            			});
          			});
        		</script>	
                	
                </form>
              </div> <!-- /.item-info-r -->


            </div> <!-- /.col-sm-4 -->
          </div> <!-- ./ row -->

          <!-- /***产品描述***/ -->
          <div class="row">
            <div class="col-sm-12">
              <div class="unis-item-spec">
                <h3>礼品资讯</h3>
                <hr>

                <section><!-- 产品描述开始 -->
                ${commodity.description }
				</section><!-- 产品描述结束 -->
                
              </div>
            </div>
          </div>
          <!-- /.产品描述 -->

        </div>  <!-- end container -->

        <!-- <a href="http://wpa.qq.com/msgrd?v=3&amp;uin=937007032&amp;site=qq&amp;menu=yes" target="_blank" class="kf-btn"><i class="fa fa-headphones fa-2x"></i></a> -->
        <a href="http://wpa.qq.com/msgrd?v=3&uin=2875109253&site=qq&menu=yes" target="_blank" class="kf-btn"><span class="glyphicon glyphicon-headphones"></span>&nbsp;客服</a>
        
