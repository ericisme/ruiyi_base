/**
 * 分页查询
 */
function query(){
	var paras = 'page=1';
	
	//待发货订单查询
	if($("#_status").val() == "30"){
		//显示在该查询状态下的操作按钮 
		$("#deliveryOutBtn").show();
		$("#cancelOrderBtn").show();
		//订单号
		if($("#_orderNum").val() != ''){
			paras += '&_orderNum=' + encodeURI($("#_orderNum").val(), "utf-8");
		}
		//状态
		if($("#_status").val() != ''){
			paras += '&_status=' + encodeURI($("#_status").val(), "utf-8");
		}
		//运单号
		if($("#_logisticsCode").val() != ''){
			paras += '&_logisticsCode=' + encodeURI($("#_logisticsCode").val(), "utf-8");
		}
			//向后台发起查询请求
			base.load("list", baseUrl+"/query30?" + paras,function(){
				base.showList("head","list","edit");
			});
	}else{
		//隐藏不在该查询状态下的操作按钮 
		$("#deliveryOutBtn").hide();
		$("#cancelOrderBtn").hide();
	}
	
	
	//已发货订单查询
	if($("#_status").val() == "70"){
		$("#cancelDeliveryBtn").show();
		
		//订单号
		if($("#_orderNum").val() != ''){
			paras += '&_orderNum=' + encodeURI($("#_orderNum").val(), "utf-8");
		}
		//状态
		if($("#_status").val() != ''){
			paras += '&_status=' + encodeURI($("#_status").val(), "utf-8");
		}
		//运单号
		if($("#_logisticsCode").val() != ''){
			paras += '&_logisticsCode=' + encodeURI($("#_logisticsCode").val(), "utf-8");
		}
			//向后台发起查询请求
			base.load("list", baseUrl+"/query70?" + paras,function(){
				base.showList("head","list","edit");
			});
		
	}else{
		$("#cancelDeliveryBtn").hide();
	}
	
	
	//交易成功订单查询
	if($("#_status").val() == "90"){		
		//订单号
		if($("#_orderNum").val() != ''){
			paras += '&_orderNum=' + encodeURI($("#_orderNum").val(), "utf-8");
		}
		//状态
		if($("#_status").val() != ''){
			paras += '&_status=' + encodeURI($("#_status").val(), "utf-8");
		}
		//运单号
		if($("#_logisticsCode").val() != ''){
			paras += '&_logisticsCode=' + encodeURI($("#_logisticsCode").val(), "utf-8");
		}
			//向后台发起查询请求
			base.load("list", baseUrl+"/query90?" + paras,function(){
				base.showList("head","list","edit");
			});
	}else{		
	}
	
	
	//取消订单查询
	if($("#_status").val() == "10"){
		//显示在该查询状态下的操作按钮
		$("#showOrMessageFor10And30Btn").show();
		
		//订单号
		if($("#_orderNum").val() != ''){
			paras += '&_orderNum=' + encodeURI($("#_orderNum").val(), "utf-8");
		}
		//状态
		if($("#_status").val() != ''){
			paras += '&_status=' + encodeURI($("#_status").val(), "utf-8");
		}
		//运单号
		if($("#_logisticsCode").val() != ''){
			paras += '&_logisticsCode=' + encodeURI($("#_logisticsCode").val(), "utf-8");
		}
			//向后台发起查询请求
			base.load("list", baseUrl+"/query10?" + paras,function(){
				base.showList("head","list","edit");
			});
	}else{		
	}
	
	//showOrMessageFor10And30Btn 按钮的显示/隐藏
	if($("#_status").val() == "10" || $("#_status").val() == "30"){
		$("#showOrMessageFor10And30Btn").show();
	}else{
		$("#showOrMessageFor10And30Btn").hide();
	}
	
	//showOrMessageFor70And90Btn 按钮的显示/隐藏
	if($("#_status").val() == "70" || $("#_status").val() == "90"){
		$("#showOrMessageFor70And90Btn").show();
	}else{
		$("#showOrMessageFor70And90Btn").hide();
	}
}



/**
 * 发货页面
 */
function deliveryOut(){
	var ids = "";
	var checked_ctxs = new Array();
	var ctxs = $("[name^=cbx_]");
	for(var i = 0; i<ctxs.length; i++){
		if(ctxs[i].checked)
			checked_ctxs.push(ctxs[i]);
	}
	if(checked_ctxs.length==0){
		base.tips("请选择要发货的订单！");
		return;
	}
	for(var i = 0; i<checked_ctxs.length; i++){
		if(i>0){
			if(checked_ctxs[i].name != checked_ctxs[i-1].name){
				base.tips("对不起，地址不相同的订单不能一起发货！");
				return;
			}
		}
		ids = ids +","+checked_ctxs[i].value;
	}
	base.load("edit", baseUrl+"/deliveryOut?ids=" + ids,function(){
		base.showEdit("head","list","edit");
	});	
}

/**
 * 发货操作
 */
function excuteDeliveryOut(){
	//var paras = 'page=1';
	var adminAmessage_list = $("[id^=adminAmessage_]");
	//var commodityOrderIds = new Array();
	//var adminAmessage_map_list = new Array();
	for(var i = 0; i < adminAmessage_list.length; i++){
		if(adminAmessage_list[i].value.length > 300){
			base.tips("留言不能超过300字符");
			adminAmessage_list[i].focus();
			return ;
		}				
		//adminAmessage_map_list.push(adminAmessage_list[i].name + ":" + encodeURI(adminAmessage_list[i].value, "utf-8"));
		//commodityOrderIds.push(adminAmessage_list[i].name);
	}
	//paras += "&commodityOrderIds="  + commodityOrderIds.join(",");
	//paras += "&adminAmessage_map_list=" + adminAmessage_map_list.join(",");
	
	var commodityLogistics_id = $("#commodityLogistics_id");
	var logisticsCode = $("#logisticsCode");
	if(commodityLogistics_id.val()=="" ){			
		base.tips("请选择 发货物流商。");
		commodityLogistics_id.focus();
		return;
	}
	if(logisticsCode.val()=="" ){			
		base.tips("物流运单号 不能为空。");
		logisticsCode.focus();
		return;
	}	
	//paras += "&commodityLogistics_id=" + commodityLogistics_id.val();
	//paras += "&logisticsCode=" + encodeURI(logisticsCode.val(), "utf-8");
	//alert(paras);	
	
	base.confirm("您确定要 发货 吗?", '', function(){
		base.processStatus(1,'save_btn','process_btn');
		$.ajax({                 
			cache: true,                 
			type : "POST",                 
			url  : baseUrl+"/excuteDeliveryOut",                 
			data : $('#mainForm').serialize(), // 你的formid                 
			async: false,                 
			error: function(request) {alert("Connection error");},                 
			success: function(result) {                     
				if(result == "success"){
					base.tips("发货成功!",'success',1500);
					setTimeout(function(){base.cancel('head','list','edit');},2000);
				}else{
					base.tips(result,'error');
					base.processStatus(0,'save_btn','process_btn');
				}                
			}             
		});
	});
	
}



/**
 * 取消发货页面
 */
function cancelDelivery(){
	var ids = "";
	var checked_ctxs = new Array();
	var ctxs = $("[name^=cbx_]");
	for(var i = 0; i<ctxs.length; i++){
		if(ctxs[i].checked)
			checked_ctxs.push(ctxs[i]);
	}
	if(checked_ctxs.length==0){
		base.tips("请选择要 取消发货 的订单！");
		return;
	}
	for(var i = 0; i<checked_ctxs.length; i++){
		if(i>0){
			if(checked_ctxs[i].name != checked_ctxs[i-1].name){
				base.tips("对不起，运单号 不相同的订单不能一起取消发货！");
				return;
			}
		}
		ids = ids +","+checked_ctxs[i].value;
	}
	base.load("edit", baseUrl+"/cancelDelivery?ids=" + ids,function(){
		base.showEdit("head","list","edit");
	});	
}
/**
 * 取消发货操作
 */
function excuteCancelDelivery(){
	//var paras = 'page=1';
	var adminAmessage_list = $("[id^=adminAmessage_]");
	//var commodityOrderIds = new Array();
	//var adminAmessage_map_list = new Array();
	for(var i = 0; i < adminAmessage_list.length; i++){
		if(adminAmessage_list[i].value.length > 300){
			base.tips("留言不能超过300字符");
			adminAmessage_list[i].focus();
			return ;
		}				
		//adminAmessage_map_list.push(adminAmessage_list[i].name + ":" + encodeURI(adminAmessage_list[i].value, "utf-8"));
		//commodityOrderIds.push(adminAmessage_list[i].name);
	}
	//paras += "&commodityOrderIds="  + commodityOrderIds.join(",");
	//paras += "&adminAmessage_map_list=" + adminAmessage_map_list.join(",");
	
/*	var commodityLogistics_id = $("#commodityLogistics_id");
	var logisticsCode = $("#logisticsCode");
	if(commodityLogistics_id.val()=="" ){			
		base.tips("请选择 发货物流商。");
		commodityLogistics_id.focus();
		return;
	}
	if(logisticsCode.val()=="" ){			
		base.tips("物流运单号 不能为空。");
		logisticsCode.focus();
		return;
	}	
	paras += "&commodityLogistics_id=" + commodityLogistics_id.val();
	paras += "&logisticsCode=" + encodeURI(logisticsCode.val(), "utf-8");*/
	//alert(paras);	
	
	base.confirm("您确定要 取消这些订单的发货 吗?", '', function(){
		base.processStatus(1,'save_btn','process_btn');
		$.ajax({                 
			cache: true,                 
			type : "POST",                 
			url  : baseUrl+"/excuteCancelDelivery",                 
			data : $('#mainForm').serialize(), // 你的formid                 
			async: false,                 
			error: function(request) {alert("Connection error");},                 
			success: function(result) {                     
				if(result == "success"){
					base.tips("取消发货 成功!",'success',1500);
					setTimeout(function(){base.cancel('head','list','edit');},2000);
				}else{
					base.tips(result,'error');
					base.processStatus(0,'save_btn','process_btn');
				}                
			}             
		});
	});
}



/**
 * 查看/留言 页面（仅供 取消状态 根 待发货 状态 订单组 使用）
 */
function showOrMessageFor10And30(){
	var ids = "";
	var checked_ctxs = new Array();
	var ctxs = $("[name^=cbx_]");
	for(var i = 0; i<ctxs.length; i++){
		if(ctxs[i].checked)
			checked_ctxs.push(ctxs[i]);
	}
	if(checked_ctxs.length==0){
		base.tips("请选择要查看/留言的订单！");
		return;
	}
	for(var i = 0; i<checked_ctxs.length; i++){
		if(i>0){
			if(checked_ctxs[i].name != checked_ctxs[i-1].name){
				base.tips("对不起，地址不相同的订单不能一起查看/留言！");
				return;
			}
		}
		ids = ids +","+checked_ctxs[i].value;
	}
	base.load("edit", baseUrl+"/showOrMessageFor10And30?ids=" + ids,function(){
		base.showEdit("head","list","edit");
	});	
}
/**
 *  留言操作 （仅供 取消状态(10) 根 待发货(30) 状态 订单组 使用）
 */
function excuteMessageFor10Or30(){
	var adminAmessage_list = $("[id^=adminAmessage_]");
	for(var i = 0; i < adminAmessage_list.length; i++){
		if(adminAmessage_list[i].value.length > 300){
			base.tips("留言不能超过300字符");
			adminAmessage_list[i].focus();
			return ;
		}			
	}
	
	base.confirm("您确定要 留言 吗?", '', function(){
		base.processStatus(1,'save_btn','process_btn');
		$.ajax({                 
			cache: true,                 
			type : "POST",                 
			url  : baseUrl+"/excuteMessageFor10Or30",                 
			data : $('#mainForm').serialize(), // 你的formid                 
			async: false,                 
			error: function(request) {alert("Connection error");},                 
			success: function(result) {                     
				if(result == "success"){
					base.tips("留言成功!",'success',1500);
					setTimeout(function(){base.cancel('head','list','edit');},2000);
				}else{
					base.tips(result,'error');
					base.processStatus(0,'save_btn','process_btn');
				}                
			}             
		});
	});
}



/**
 * 查看/留言 页面（仅供 交易成功 根 已发货 状态 订单组 使用）
 */
function showOrMessageFor70And90(){
	var ids = "";
	var checked_ctxs = new Array();
	var ctxs = $("[name^=cbx_]");
	for(var i = 0; i<ctxs.length; i++){
		if(ctxs[i].checked)
			checked_ctxs.push(ctxs[i]);
	}
	if(checked_ctxs.length==0){
		base.tips("请选择要查看/留言的订单！");
		return;
	}
	for(var i = 0; i<checked_ctxs.length; i++){
		if(i>0){
			if(checked_ctxs[i].name != checked_ctxs[i-1].name){
				base.tips("对不起，地址不相同的订单不能一起查看/留言！");
				return;
			}
		}
		ids = ids +","+checked_ctxs[i].value;
	}
	base.load("edit", baseUrl+"/showOrMessageFor70And90?ids=" + ids,function(){
		base.showEdit("head","list","edit");
	});	
}
/**
 *  留言操作 （仅供 交易成功(70) 根 已发货(90) 状态 订单组 使用）
 */
function excuteMessageFor70Or90(){
	var adminAmessage_list = $("[id^=adminAmessage_]");
	for(var i = 0; i < adminAmessage_list.length; i++){
		if(adminAmessage_list[i].value.length > 300){
			base.tips("留言不能超过300字符");
			adminAmessage_list[i].focus();
			return ;
		}			
	}
	
	base.confirm("您确定要 留言 吗?", '', function(){
		base.processStatus(1,'save_btn','process_btn');
		$.ajax({                 
			cache: true,                 
			type : "POST",                 
			url  : baseUrl+"/excuteMessageFor70Or90",                 
			data : $('#mainForm').serialize(), // 你的formid                 
			async: false,                 
			error: function(request) {alert("Connection error");},                 
			success: function(result) {                     
				if(result == "success"){
					base.tips("留言成功!",'success',1500);
					setTimeout(function(){base.cancel('head','list','edit');},2000);
				}else{
					base.tips(result,'error');
					base.processStatus(0,'save_btn','process_btn');
				}                
			}             
		});
	});
}






























































/**
 * 新增/编辑
 */
function edit(i){
	base.load("edit",baseUrl+"/edit/" + i,function(){
		base.showEdit("head","list","edit");
	});
	
}

/**
 * 保存
 */
function save(){
	var et = $("#name");
	if(et.val()=="" ){			
		base.tips("物流商名称 不能为空。");
		et.focus();
		return;
	}
	et = $("#path");
	if(et.val()=="" ){			
		base.tips("物流查询网址 不能为空。");
		et.focus();
		return;
	}
	et = $("#tel");
	if(et.val()=="" ){			
		base.tips("联系电话 不能为空。");
		et.focus();
		return;
	}
	et = $("#sortNumber");
	if(et.val()=="" ){			
		base.tips("排序号 不能为空。");
		et.focus();
		return;
	}

	base.processStatus(1,'save_btn','process_btn');
	$.ajax({                 
		cache: true,                 
		type : "POST",                 
		url  : baseUrl+"/save",                 
		data : $('#mainForm').serialize(),// 你的formid                 
		async: false,                 
		error: function(request) {                     
			alert("Connection error");                 
			},                 
		success: function(result) {                     
				if(result == "success"){
					base.tips("保存成功!",'success',1500);
					setTimeout(function(){base.cancel('head','list','edit');},2000);
				}else{
					base.tips(result,'error');
				}                
			}             
	});
	
}


/**
 * 查看
 */
function show(i){	
	base.load("edit",baseUrl+"/show/" + i,function(){
		base.showEdit("head","list","edit");
	});
}

/**
 * 删除方法
 */
function del(id){
		base.confirm("您确定要删除 记录 吗?", '', function(){
			base.request(baseUrl+"/delete", "ids=" + id, function(result){
					if(result == "success"){
						base.tips("删除成功!", 'success', 1500);
						setTimeout(function(){
							base.cancel('head','list','edit');
						},2000);
					}else{
						base.tips(result, 'error');
					}
			},"POST","HTML");
		});
}
/**
 * 删除多条记录
 */
function delMore(){
	var ids = base.getChecked("cbx",true);
	del(ids);
}

/**
 * 停用 / 启用
 */
function setup(id,status){
	base.request(baseUrl + "/setup","id=" + id + "&status=" + status,function(result){
				if(result == "success"){
					base.tips((status==1?"启用":"停用")+" 成功!",'success',1500);
					setTimeout(function(){
						base.cancel('head','list','edit');
					},100);
				}else{
					base.tips(result);
				}
	},"POST","HTML");
}

/**
 * 排序号加1
 */
function up(id){
	base.request(baseUrl + "/up","id=" + id ,function(result){
				if(result == "success"){
					//base.tips(" 排序号加1 成功!",'success',1500);
					setTimeout(function(){
						base.cancel('head','list','edit');
					},100);
				}else{
					base.tips(result);
				}
	},"POST","HTML");
}
/**
 * 排序号减1
 */
function down(id){
	base.request(baseUrl + "/down","id=" + id ,function(result){
				if(result == "success"){
					//base.tips(" 排序号减1 成功!",'success',1500);
					setTimeout(function(){
						base.cancel('head','list','edit');
					},100);
				}else{
					base.tips(result);
				}
	},"POST","HTML");
}
