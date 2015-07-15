

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
	var et = $("#title");
	if(et.val()=="" ){			
		base.tips("标题 不能为空。");
		et.focus();
		return;
	}	
	et = $("#tag");
	if(et.val()=="" ){			
		base.tips("标签 不能为空。");
		et.focus();
		return;
	}
	et = $("#author");
	if(et.val()=="" ){			
		base.tips("作者 不能为空。");
		et.focus();
		return;
	}	
	et = $("#addTime_date_str");
	if(et.val()=="" ){			
		base.tips("日期 不能为空。");
		et.focus();
		return;
	}
	et = $("#sortNumber");
	if(et.val()=="" ){			
		base.tips("排序号 不能为空。");
		et.focus();
		return;
	}
	var editor = CKEDITOR.instances.content;
	if(editor.getData()==""){
		base.tips("内容 不能为空。");
		return;
	}
	if(editor.getData().length>9000){
		base.tips("内容 不能超过9000字符。");
		return;
	}
	//$("#content").val(encodeHTML(editor.getData()));
	$("#content").val(editor.getData());
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
function query(){
	var paras = 'page=1';
	//排序
	paras += '&orders=sortNumber,addTime__desc';
	//中英文版本
	if($("#_formType").val() != ''){
		paras += '&formType__eq__int=' + encodeURI($("#_formType").val(), "utf-8");
	}
	//标题
	if($("#_title").val() != ''){
		paras += '&title__like__string=' + encodeURI($("#_title").val(), "utf-8");
	}	
	//作者
	if($("#_author").val() != ''){
		paras += '&author__like__string=' + encodeURI($("#_author").val(), "utf-8");
	}
	//标签
	if($("#_tag").val() != ''){
		paras += '&tag__like__string=' + encodeURI($("#_tag").val(), "utf-8");
	}
	//状态
	if($("#_status").val() != ''){
		paras += '&status__eq__int=' + encodeURI($("#_status").val(), "utf-8");
	}
	//起始时间
	if($("#_addTimeStrStart").val() != ''){
		paras += '&addTime__gte__date=' + $("#_addTimeStrStart").val();
	}
	//结束时间
	if($("#_addTimeStrEnd").val() != ''){
		paras += '&addTime__lte__date=' + $("#_addTimeStrEnd").val();
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
