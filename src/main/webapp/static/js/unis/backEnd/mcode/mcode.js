

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
	var et = $("#mtype");
	if(et.val()=="" ){			
		base.tips("类型不能为空。");
		et.focus();
		return;
	}
	et = $("#mkey");
	if(et.val()=="" ){			
		base.tips("名称 不能为空。");
		et.focus();
		return;
	}
	et = $("#mvalue");
	if(et.val()=="" ){			
		base.tips("码值 不能为空。");
		et.focus();
		return;
	}	
	et = $("#orderNum");
	if(et.val()=="" ){			
		base.tips("排序号 不能为空。");
		et.focus();
		return;
	}
	
	base.processStatus(1,'save_btn','process_btn');
	base.formSubmit(baseUrl + "/save",function(result){
			if(result == "success"){
				base.tips("保存成功!",'success',1500);
				setTimeout(function(){
					base.cancel('head','list','edit');
					query();
				},2000);
			}else{
				base.tips(result);
				base.processStatus(0,'save_btn','process_btn');
			}
	},"mainForm");	
}


/**
 * 分页查询
 */
function query(){
	var paras = 'page=1';
	//排序
	paras += '&orders=mtype,orderNum__asc';
	//类型
	if($("#_mtype").val() != ''){
		paras += '&mtype__like__string=' + encodeURI($("#_mtype").val(), "utf-8");
	}	
	//名称
	if($("#_mkey").val() != ''){
		paras += '&mkey__like__string=' + encodeURI($("#_mkey").val(), "utf-8");
	}
	//码值
	if($("#_mvalue").val() != ''){
		paras += '&mvalue__like__string=' + encodeURI($("#_mvalue").val(), "utf-8");
	}
	//状态
	if($("#_datelevel").val() != ''){
		paras += '&datelevel__eq__int=' +$("#_datelevel").val();
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
function setup(id,datelevel){
	base.request(baseUrl + "/setup","id=" + id + "&datelevel=" + datelevel,function(result){
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
