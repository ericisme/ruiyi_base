/**
 * 分页查询
 */
function query(){
	var paras = 'page=1';
	//邀请码
	if($("#_code").val() != ''){
		paras += '&_code=' + $("#_code").val();
	}
	//公司名称
	if($("#_companyName").val() != ''){
		paras += '&_companyName=' +  encodeURI($("#_companyName").val(), "utf-8");
	}
	//填写版本
	if($("#_formType").val() != ''){
		paras += '&_formType=' + $("#_formType").val();
	}
	//是否已确认
	if($("#_isSure").val() != ''){
		paras += '&_isSure=' + $("#_isSure").val();
	}	
	//国家或地区
	if($("#_districtCode").val() != ''){
		paras += '&_districtCode=' + $("#_districtCode").val();
	}	
	//住宿
	if($("#_hotel").val() != ''){
		paras += '&_hotel=' + $("#_hotel").val();
	}	
	//特殊餐饮
	if($("#_specialDiet").val() != ''){
		paras += '&_specialDiet=' + $("#_specialDiet").val();
	}
	//岐江夜游
	if($("#_nightTourOfQiRiver").val() != ''){
		paras += '&_nightTourOfQiRiver=' + $("#_nightTourOfQiRiver").val();
	}
	//故居参观
	if($("#_visitFormerResidenceOfSunYatSen").val() != ''){
		paras += '&_visitFormerResidenceOfSunYatSen=' + $("#_visitFormerResidenceOfSunYatSen").val();
	}
	//旅游购物
	if($("#_shopping").val() != ''){
		paras += '&_shopping=' + $("#_shopping").val();
	}
	//娱乐活动
	if($("#_entertainment").val() != ''){
		paras += '&_entertainment=' + $("#_entertainment").val();
	}
	//请求查询
	base.load("list", baseUrl+"/query?" + paras,function(){
			base.showList("head","list","edit");
		}
	);		
}
/*
 *导出excel 
 */
function exportXls(){
	var paras = 'page=1';
	//邀请码
	if($("#_code").val() != ''){
		paras += '&_code=' + $("#_code").val();
	}
	//公司名称
	if($("#_companyName").val() != ''){
		paras += '&_companyName=' +  encodeURI($("#_companyName").val(), "utf-8");
	}
	//填写版本
	if($("#_formType").val() != ''){
		paras += '&_formType=' + $("#_formType").val();
	}
	//是否已确认
	if($("#_isSure").val() != ''){
		paras += '&_isSure=' + $("#_isSure").val();
	}	
	//请求查询
	window.open(baseUrl+"/exportXls?" + paras);
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
 * 确认信息/取消确认
 */
function comfirmInfo(invitationFormId, isSure){
	if(isSure==1){
		base.confirm("确认后客户将无法修改信息，您确定要 确认这个表单信息 吗?", '', function(){
			base.processStatus(1,'save_btn','process_btn');
			$.ajax({                 
				cache: true,                 
				type : "POST",                 
				url  : baseUrl+"/comfirmInfo",                 
				data : "invitationFormId="+invitationFormId+"&isSure="+isSure,// 你的formid                 
				async: false,                 
				error: function(request) {                     
					alert("Connection error");                 
					},                 
				success: function(result) {                     
						if(result == "success"){
							base.tips("确认信息成功!",'success',1500);
							setTimeout(function(){base.cancel('head','list','edit');},2000);
						}else{
							base.tips(result,'error');
						}                
					}             
			});
		});
	}
	if(isSure==0){
		base.confirm("取消确认后客户将允许修改信息，您确定要 取消确认这个表单信息 吗?", '', function(){
			base.processStatus(1,'save_btn','process_btn');
			$.ajax({                 
				cache: true,                 
				type : "POST",                 
				url  : baseUrl+"/comfirmInfo",                 
				data : "invitationFormId="+invitationFormId+"&isSure="+isSure,// 你的formid                 
				async: false,                 
				error: function(request) {                     
					alert("Connection error");                 
					},                 
				success: function(result) {                     
						if(result == "success"){
							base.tips("取消确认成功!",'success',1500);
							setTimeout(function(){base.cancel('head','list','edit');},2000);
						}else{
							base.tips(result,'error');
						}                
					}             
			});
		});
	}	

}

