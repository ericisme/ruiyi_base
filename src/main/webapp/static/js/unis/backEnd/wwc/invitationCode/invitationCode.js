/**
 * 审核
 */
function review(i){
	base.load("edit",baseUrl+"/review/" + i,function(){
		base.showEdit("head","list","edit");
	});
	
}
/**
 * 保存审核
 */
function saveReview(status){
	var email = $("#email");
	if(email.val() != ''){
		if(!(testEmail(email.val()))){		
			base.tips("email格式不正确");
			email.focus();
			return;
		}
	}
	
	var et = $("#name");
	if(et.val()=="" ){			
		base.tips("被邀请人名字 不能为空。");
		et.focus();
		return;
	}
	et = $("#remark");
	if(et.val().length>300){
		base.tips("备注 不能大于300个字符。");
		et.focus();
		return;
	}
	
	base.processStatus(1,'review_1_btn','process_btn');
	base.processStatus(1,'review_0_btn','process_btn');
	$.ajax({                 
		cache: true,                 
		type : "POST",                 
		url  : baseUrl+"/saveReview?status="+status,                 
		data : $('#mainForm').serialize(),// 你的formid                 
		async: false,                 
		error: function(request) {                     
			alert("Connection error");                 
			},                 
		success: function(result) {                     
				if(result == "success"){
					if(status==1){
						base.tips("操作成功!邀请码已发到申请人手机号/email",'success',3000);
					}else{
						base.tips("操作成功!",'success',3000);
					}
					setTimeout(function(){base.cancel('head','list','edit');},3000);
				}else{
					base.tips(result,'error');
				}                
			}             
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
	var email = $("#email");
	if(email.val() != ''){
		if(!(testEmail(email.val()))){		
			base.tips("email格式不正确");
			email.focus();
			return;
		}
	}
	
	var et = $("#name");
	if(et.val()=="" ){			
		base.tips("被邀请人名字 不能为空。");
		et.focus();
		return;
	}
	et = $("#remark");
	if(et.val().length>300){
		base.tips("备注 不能大于300个字符。");
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
 * 分页查询
 */
function query(){
	var paras = 'page=1';
	//排序
	paras += '&orders=id__desc';
	//名字
	if($("#_name").val() != ''){
		paras += '&name__like__string=' + encodeURI($("#_name").val(), "utf-8");
	}
	//公司
	if($("#_company").val() != ''){
		paras += '&company__like__string=' + encodeURI($("#_company").val(), "utf-8");
	}
	//电话
	if($("#_tel").val() != ''){
		paras += '&tel__like__string=' + encodeURI($("#_tel").val(), "utf-8");
	}
	//电话
	if($("#_email").val() != ''){
		paras += '&email__like__string=' + encodeURI($("#_email").val(), "utf-8");
	}
	//备注
	if($("#_remark").val() != ''){
		paras += '&remark__like__string=' + encodeURI($("#_remark").val(), "utf-8");
	}
	//邀请码
	if($("#_code").val() != ''){
		paras += '&code__like__string=' + encodeURI($("#_code").val(), "utf-8");
	}
	//类型
	if($("#_type").val() != ''){
		if($("#_type").val() ==1){
			paras += '&type__eq__int=1';
		}
		if($("#_type").val() ==2){
			paras += '&type__eq__int=2';
		}
		if($("#_type").val() ==3){
			paras += '&type__eq__int=2';
			paras += '&reviewStatus__eq__int=null';
		}
		if($("#_type").val() ==4){
			paras += '&type__eq__int=2';
			paras += '&reviewStatus__eq__int=1';
		}
		if($("#_type").val() ==5){
			paras += '&type__eq__int=2';
			paras += '&reviewStatus__eq__int=0';
		}
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
	var paras = 'page=1&size_per_page=99999999';
	//排序
	paras += '&orders=id__desc';
	//名字
	if($("#_name").val() != ''){
		paras += '&name__like__string=' + encodeURI($("#_name").val(), "utf-8");
	}
	//公司
	if($("#_company").val() != ''){
		paras += '&company__like__string=' + encodeURI($("#_company").val(), "utf-8");
	}
	//电话
	if($("#_tel").val() != ''){
		paras += '&tel__like__string=' + encodeURI($("#_tel").val(), "utf-8");
	}
	//电话
	if($("#_email").val() != ''){
		paras += '&email__like__string=' + encodeURI($("#_email").val(), "utf-8");
	}
	//备注
	if($("#_remark").val() != ''){
		paras += '&remark__like__string=' + encodeURI($("#_remark").val(), "utf-8");
	}
	//邀请码
	if($("#_code").val() != ''){
		paras += '&code__like__string=' + encodeURI($("#_code").val(), "utf-8");
	}
	//类型
	if($("#_type").val() != ''){
		if($("#_type").val() ==1){
			paras += '&type__eq__int=1';
		}
		if($("#_type").val() ==2){
			paras += '&type__eq__int=2';
		}
		if($("#_type").val() ==3){
			paras += '&type__eq__int=2';
			paras += '&reviewStatus__eq__int=null';
		}
		if($("#_type").val() ==4){
			paras += '&type__eq__int=2';
			paras += '&reviewStatus__eq__int=1';
		}
		if($("#_type").val() ==5){
			paras += '&type__eq__int=2';
			paras += '&reviewStatus__eq__int=0';
		}
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

function testEmail(str){
	//var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	var reEmail = /[a-z0-9-]{1,30}@[a-z0-9-]{1,65}.[a-z]{3}/ ;
	if(reEmail.test(str)){
		return true;
	}else{
		return false;
	}
}

