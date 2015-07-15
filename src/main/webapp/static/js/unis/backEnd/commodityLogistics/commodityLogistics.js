

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
 * 分页查询
 */
function query(){
	var paras = 'page=1';
	//排序
	paras += '&orders=sortNumber,id__desc';
	//物流商名称
	if($("#_name").val() != ''){
		paras += '&name__like__string=' + encodeURI($("#_name").val(), "utf-8");
	}
	//联系电话
	if($("#_tel").val() != ''){
		paras += '&tel__like__string=' + encodeURI($("#_tel").val(), "utf-8");
	}
	//查询网址
	if($("#_path").val() != ''){
		paras += '&path__like__string=' + encodeURI($("#_path").val(), "utf-8");
	}
	//状态
	if($("#_status").val() != ''){
		paras += '&status__eq__int=' + encodeURI($("#_status").val(), "utf-8");
	}

	base.load("list", baseUrl+"/query?" + paras,function(){
			base.showList("head","list","edit");
		}
	);	
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
