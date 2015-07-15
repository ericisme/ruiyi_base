
function query(){
	var pUnit = $("#p_unit option:selected").val();
	var unitName = $("#unitName").val();
	var unitCode = $("#unitCode").val();
	var paras = "page=1&unitName=" + encodeURI(unitName,"utf-8") + "&unitCode=" + unitCode + "&pUnit=" + pUnit;
	base.load("list",baseUrl + "/list?" + paras,function(){
		base.showList("head","list","edit");
	});
}

function edit(i){
	base.load("edit",baseUrl + "/edit/" + i,function(){
		base.showEdit("head","list","edit");
	});
}

function delMore(){
	var ids = base.getChecked("cbx",true);
	del(ids);
}

function del(id){
	base.confirm("删除该机构也将删除其下的所有所属单位机构，您确定还要删除吗?",'',function(){
		base.request(baseUrl + "/delete","ids=" + id,function(result){
				if(result == "success"){
					base.tips("删除成功!",'success',1500);
					setTimeout(function(){
						base.cancel('head','list','edit');
					},2000)
				}else{
					base.tips("出现未知异常，操作失败!",'error');
				}
		},"POST","HTML");
	});
}

function save(){
	if(checkForm()){
		base.processStatus(1,'save_btn','process_btn');
		base.formSubmit(baseUrl + "/save",function(result){
				if(result == "success"){
					base.tips("保存成功!",'success',1500);
					setTimeout(function(){
						//base.cancel('head','list','edit');
					},2000)
				}else{
					base.tips("出现未知异常，操作失败!",'error');
				}
				base.processStatus(1,'save_btn','process_btn');
		},"mainForm");
	}
}

function checkForm(){
	
	var unitName = $("#mainForm #unitName");
	if(unitName.val() == ""){
		base.alert("机构名称不能为空!");
		unitName.focus();
		return false;
	}
	
	var dwdm = $("#mainForm #dwdm");
	if(dwdm.val() == ""){
		base.alert("单位代码不能为空!");
		dwdm.focus();
		return false;
	}
	
	var dq_m = $("#mainForm #dq_m");
	if(dq_m.val() == ""){
		base.alert("地区码不能为空!");
		dq_m.focus();
		return false;
	}
	
	var gjdwdm = $("#mainForm #gjdwdm");
	if(gjdwdm.val() == ""){
		base.alert("国家单位代码不能为空!");
		gjdwdm.focus();
		return false;
	}
	
	var gldwdm = $("#mainForm #gldwdm");
	if(gldwdm.val() == ""){
		base.alert("管理单位代码不能为空!");
		gldwdm.focus();
		return false;
	}
	
	return true;
}

/*
function setup(id,status){
	base.request(baseUrl + "/setup","id=" + id + "&status=" + status,function(result){
				if(result == "success"){
					base.tips("修改成功!",'success',1500);
					if(status == 1){
						$("#setUnable" + id).show();
						$("#setAble" + id).hide();
						$("#statusDisplay" + id).html("正常");
					}else{
						$("#setUnable" + id).hide();
						$("#setAble" + id).show();
						$("#statusDisplay" + id).html("<font color='red'>停用</font>");
					}
				}else{
					base.tips("出现未知异常，操作失败!",'error');
				}
	},"POST","HTML");
}
*/