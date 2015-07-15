<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<span id="sitemeshtitle" style="display: none">title_game_center</span>
<title>世宇游戏-游乐场管理</title>
      <style type="text/css">
        .dropdown-menu.backend-carnie-radio{
          min-width: 300px;
          height: auto;
          left: -70%;
        }
        .dropdown-menu.backend-carnie-radio ul li{
           padding: 10px 15px;
        }
        #modal-carnie-select{
          overflow: hidden;
        }
        #modal-carnie-select.modal-body{
           min-height: 300px;
        }
        .form-control {
            margin: 0 auto;
            width: 80%;
            height: auto;
        }
        .citychoose1{
          padding-top: 10px;
          margin: 0 auto;
          width: 80%; 
          border: solid 1px #cccccc;
          box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.15);
        }
        
        .citychoose2 li a{
          border-bottom: solid 1px #cccccc;
          display: block;
          padding: 5px;
        }
        .citychoose2 li a:hover{
          color: #fff;
          background: #428BCA;
        }
        
      </style>
<script type="text/javascript" src="${ctx }/static/js/base/base.js"></script>
<script type="text/javascript" src="${ctx }/static/js/provenceCityChoser.js"></script>
<script type="text/javascript">
 			$.ajax({
	   			url:'${ctx}/frontEnd/gameCenter/checkGameCenters',
	   			type:'post', //数据发送方式
	   			dataType:'JSON', //接受数据格式       
	   			data:null, //要传递的数据       
	   			success:
		   			function (data){//回传函数(这里是函数名)
		   				var userType = data.userType;
		   					if(userType == 2){	   	
		   						$("#type2SelectorDiv").show();
		   						var gameCenters = data.gameCenters;
		   						$("#type2SelectorBtn").html(data.selectedGameCenterName);
		   						//if(data.gameCenters.length>0){
		   						//	$("#type2SelectorBtn").html(gameCenters[0].gameCenterName);
		   						//}
		   						for(var i=0;i<gameCenters.length;i++){
		   							$("#gameCentersForType2SelectorUl").html(
		   									$("#gameCentersForType2SelectorUl").html() + 
		   									"<li><a href=\"javascript:type2Select('"+gameCenters[i].gameCenterKey+"','"+gameCenters[i].gameCenterName+"');\">" + 
		   									gameCenters[i].gameCenterName + 
		   									"</a></li>"
		   							);
		   						}
		   					}
		   					if(userType == 3){
		   						$("#type3SelectorBtn").show();
		   						//$("#type3SelectorBtn").html("世宇樂園中山大信店");
		   						$("#type3SelectorBtn").html(data.selectedGameCenterName);
		   					}
	       			}
			}); 
 			//一般场主，确认选择
 			function type2Select(gameCenterKey, gameCenterName){
 	 			$.ajax({
 		   			url:'${ctx}/frontEnd/gameCenter/setSelectedGameCenterKeyToSession?game_center_key='+gameCenterKey,
 		   			type:'post', //数据发送方式
 		   			dataType:'JSON', //接受数据格式       
 		   			data:null, //要传递的数据       
 		   			success:
 			   			function (data){//回传函数(这里是函数名)
 		   					var success = data.success;
							if(success){
								$("#type2SelectorBtn").html(gameCenterName);
								window.location.reload();
							}
 		       			}
 				});  				
 			}
 			//一般世宇方场主，确认选择
 			function type3Select(gameCenterKey, gameCenterName){ 			
 	 			$.ajax({
 		   			url:'${ctx}/frontEnd/gameCenter/setSelectedGameCenterKeyToSession?game_center_key='+gameCenterKey,
 		   			type:'post', //数据发送方式
 		   			dataType:'JSON', //接受数据格式       
 		   			data:null, //要传递的数据       
 		   			success:
 			   			function (data){//回传函数(这里是函数名)
 		   					var success = data.success;
							if(success){
				 				$("#type3SelectorBtn").html(gameCenterName);
				 				//$("#type3SelectorCloseBtn").click(); 
				 				window.location.reload();
							}
 		       			}
 				});  		
 			}
 			/**
 			* 根据provinceCode获得Game Center html OPTION
 			*/
 			function getGameCentersByProvinceCodeForGameCenterIndex(provinceCode, situ, page){
 				if(page==null){
 					page = 1;
 				}
 				base.request("${ctx}/frontEnd/gameCenter/getGameCentersByProvinceCode","provinceCode=" + provinceCode + "&page=" + page, function(result){
 					var opts = result.gc_select; 					
 					if(situ==null || situ==undefined){			
 						$("#s_gamecenterIds").html(opts);
 					}else{
 						$("#"+situ).html(opts);
 					}
 					if(page>1){
 						$("#selecterPrewPageId").attr("href","javascript:getGameCentersByProvinceCodeForGameCenterIndex('"+provinceCode+"','"+situ+"',"+(page-1)+")");
 						if(result.hasNextPage){
 							$("#selecterNextPageId").attr("href","javascript:getGameCentersByProvinceCodeForGameCenterIndex('"+provinceCode+"','"+situ+"',"+(page+1)+")");
 						}
 					}else{
 						$("#selecterPrewPageId").attr("href","#");
 						if(result.hasNextPage){
 							$("#selecterNextPageId").attr("href","javascript:getGameCentersByProvinceCodeForGameCenterIndex('"+provinceCode+"','"+situ+"',"+(2)+")");
 						}
 					} 					
 				},"POST","JSON");
 			}
 			/**
 			* 根据cityCode获得Game Center html OPTION
 			*/
 			function getGameCentersByCityCodeForGameCenterIndex(cityCode, situ, page){
 				if($('#provinceSel').val()==cityCode){
 					getGameCentersByProvinceCodeForGameCenterIndex($('#provinceSel').val(), situ);
 					return;
 				}
 				if(page==null){
 					page = 1;
 				}
 				base.request("${ctx}/frontEnd/gameCenter/getGameCentersByCityCode","cityCode=" + cityCode + "&page=" + page, function(result){
 					var opts = result.gc_select;
 					if(situ==null || situ==undefined){			
 						$("#s_gamecenterIds").html(opts);
 					}else{
 						$("#"+situ).html(opts);
 					}
 					if(page>1){
 						$("#selecterPrewPageId").attr("href","javascript:getGameCentersByCityCodeForGameCenterIndex('"+cityCode+"','"+situ+"',"+(page-1)+")");
 						if(result.hasNextPage){
 							$("#selecterNextPageId").attr("href","javascript:getGameCentersByCityCodeForGameCenterIndex('"+cityCode+"','"+situ+"',"+(page+1)+")");
 						}
 					}else{
 						$("#selecterPrewPageId").attr("href","#");
 						if(result.hasNextPage){
 							$("#selecterNextPageId").attr("href","javascript:getGameCentersByCityCodeForGameCenterIndex('"+cityCode+"','"+situ+"',"+(2)+")");
 						}
 					} 		
 				},"POST","JSON");
 			}
</script>
    <div class="container">      
          <div class="text-center margin-bottom"> <!-- 游乐场选择 -->
              <!-- 选择游乐场 1  for 分店老板管理-->
                <div class="btn-group" id="type2SelectorDiv" style="display:none">
                  <button type="button" class="btn btn-primary" id="type2SelectorBtn">游乐场选择</button>
                  <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                    <span class="caret"></span>
                    <span class="sr-only">Toggle Dropdown</span>
                  </button>

                  <div class="dropdown-menu backend-carnie-radio" role="menu">
                      <ul class="list-inline" id="gameCentersForType2SelectorUl">
                      </ul>
                  </div>
                </div>
              <!-- 选择游乐场 2 for 大大大老板管理-->
                <button id="type3SelectorBtn"  style="display:none" class="btn btn-primary animated animated_fast fadeIn" data-toggle="modal" data-target="#modal-carnie-select" id="type3SelectorBtn">游乐场选择</button>
                <div class="modal fade" id="modal-carnie-select" tabindex="-1" role="dialog" aria-labelledby="carnieModal1" aria-hidden="true">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="type3SelectorCloseBtn">&times;</button>
                        <h4 class="modal-title" id="carnieModal1">请选择游乐场</h4>
                      </div>
                      <div class="modal-body">
                        <form role="form">
                          <select class="form-control" style="margin-bottom:10px;" id="provinceSel">
                            <option>选择省份</option>
                          </select>
                          <select class="form-control" style="margin-bottom:10px;"  id="citySel" onchange="getGameCentersByCityCodeForGameCenterIndex(this.value,'s_gamecenterIds')">
                            <option>选择城市</option>
                          </select>
                         	<SCRIPT language=javascript>
								InitCitySelect(document.getElementById('provinceSel'), document.getElementById('citySel'),"getGameCentersByProvinceCodeForGameCenterIndex(this.value,'s_gamecenterIds',1);");
							</SCRIPT>
                        </form>
                      <div class="citychoose1">
                        <ul class="citychoose2" id="s_gamecenterIds">
                          <!-- <li><a href="">鹤山</a></li>
                          <li><a href="javascript:type3Select(key,name)">衡山</a></li>
                          <li><a href="">中山</a></li>
                          <li><a href="">黄山</a></li>
                          <li><a href="">佛山</a></li>
                           -->
                        </ul>
                        <ul class="pager">
                        <!-- <input type="text" id="pageNum" value="1"/>-->
                          <li><a id="selecterPrewPageId" href="#">上一页</a></li>
                          <li><a id="selecterNextPageId" href="#">下一页</a></li>
                        </ul>
                      </div>
                      </div> <!-- /.body -->
                    </div><!-- /.modal-content -->
                  </div><!-- /.modal-dialog -->
                </div><!-- /. model login form -->
				
            </div> <!-- /.游乐场选择 -->

              <div class="row row-offcanvas row-offcanvas-right">

                <div class="col-xs-12 col-sm-9">
                  <p class="pull-right visible-xs animated animated_fast fadeIn">
                    <button type="button" class="btn btn-primary btn-sm" data-toggle="offcanvas" id="jjkkll"><i class="fa fa-reorder"></i> 菜 单</button>
                  </p>          
