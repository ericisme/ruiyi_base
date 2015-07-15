
/**
 * 查询,不分页
 */
function query(){	
	var consumer_key = $("#consumer_key").val();
	var provinceSel = $("#provinceSel").val();
	var citySel = $("#citySel").val();	
	var type = $("#type").val();
	if(consumer_key == "" && provinceSel == "000000" && type==""){
		alert("请选择查询条件 方能查询");
		return;
	}	
	var paras = 'consumer_key=' + consumer_key + '&provinceSel=' + provinceSel + '&citySel=' + citySel+'&type='+type;	
	base.load("list", baseUrl+"/query?" + paras,function(){
		base.showList("head","list","edit");
		$("#edit_btn").show();
		$("#show_btn").hide();
	});
}


/**
 * 新增/编辑
 */
function edit(n){
	$("span[name='show_rate_span']").each(function(i, o){
		if(n ==1 ){
			$(o).hide();
		}
		if(n == 0){
			$(o).show();
		}
	});
	$("span[name='edit_rate_span']").each(function(i, o){
		if(n ==1 ){
			$(o).show();
		}
		if(n == 0){
			$(o).hide();
		}		
	});
	if(n==1){
		$("#edit_btn").hide();
		$("#show_btn").show();		
	}
	if(n==0){
		$("#edit_btn").show();
		$("#show_btn").hide();		
	}
}

/**
 * 保存
 */
function save(game_center_key, consumer_key , rate){
	if(rate == ""){
		return;
	}
//	if(isNaN(parseFloat(rate))){
//		alert("字符格式不正确");
//		$("#"+game_center_key+"_"+consumer_key+"_input").focus();
//		return;
	if(isNaN(parseInt(rate))){
		alert("只能为正整数");
		$("#"+game_center_key+"_"+consumer_key+"_input").focus();
		return;
	}else if(parseInt(rate)<1){
		alert("只能为正整数");
		$("#"+game_center_key+"_"+consumer_key+"_input").focus();
		return;
	}else{
		
			$.ajax({
		   			url:baseUrl+'/save?game_center_key='+game_center_key+'&consumer_key='+consumer_key+"&rate="+parseInt(rate),
		   			type:'post', //数据发送方式
		   			dataType:'JSON', //接受数据格式       
		   			data:null, //要传递的数据       
		   			success:
			   			function (data){//回传函数(这里是函数名)
		   					var success = data.success;
		   					if(success){
		   						$("#"+game_center_key+"_"+consumer_key+"_span").html(parseInt(rate));
		   						$("#"+game_center_key+"_"+consumer_key+"_input").val(parseInt(rate));
								$("#"+game_center_key+"_"+consumer_key+"_img_loading").hide();
							}else{
								alert("保存出错，保存不成功！");
							}		   				
		       			}
				});		
	}
	
}
function BASEisNotInt(theInt){
	//判断是否为整数
	theInt=BASEtrim(theInt);
	if ((theInt.length>1 && theInt.substring(0,1)=="0") || BASEisNotNum(theInt)){
		return true;
	}
	return false;
}

/**
 * html 转义
 */
function encodeHTML(str) {
    return  str.replace(/&/g,'&amp;')
                .replace(/</g,'&lt;')
                .replace(/>/g,'&gt;')
                .replace(/"/g, "&quot;")
                .replace(/'/g, "&#39;");
}

/**
 * 分页查询
 */
//function query(){
//	var paras = 'page=1';
//	//排序
//	paras += '&orders=sortNumber,addTime__desc';
//	//标题
//	if($("#_title").val() != ''){
//		paras += '&title__like__string=' + encodeURI($("#_title").val(), "utf-8");
//	}	
//	//作者
//	if($("#_author").val() != ''){
//		paras += '&author__like__string=' + encodeURI($("#_author").val(), "utf-8");
//	}
//	//标签
//	if($("#_tag").val() != ''){
//		paras += '&tag__like__string=' + encodeURI($("#_tag").val(), "utf-8");
//	}
//	//状态
//	if($("#_status").val() != ''){
//		paras += '&status__eq__int=' + encodeURI($("#_status").val(), "utf-8");
//	}
//	//起始时间
//	if($("#_addTimeStrStart").val() != ''){
//		paras += '&addTime__gte__date=' + $("#_addTimeStrStart").val();
//	}
//	//结束时间
//	if($("#_addTimeStrEnd").val() != ''){
//		paras += '&addTime__lte__date=' + $("#_addTimeStrEnd").val();
//	}	
//	base.load("list", baseUrl+"/query?" + paras,function(){
//			base.showList("head","list","edit");
//		}
//	);	
//}
/**
 * 查看
 */
function show(i){	
	window.open("/frontEnd/news/show/"+i);
	//base.load("edit",baseUrl+"/show/" + i,function(){
	//	base.showEdit("head","list","edit");
	//});
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
