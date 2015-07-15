
function query(){
	var subName = $("#subName").val();
	var status = $("#status option:selected").val();
	var paras = "subName=" + encodeURI(subName,"utf-8") + "&status=" + status + "&page=1";
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
	base.confirm("您确定要删除该子系统吗?",'',function(){
		base.request(baseUrl + "/delete","ids=" + id,function(result){
				if(result == "success"){
					base.tips("删除成功!",'success',1500);
					setTimeout(function(){
						base.cancel('head','list','edit');
						generateSybSystemICOHTML();
					},2000)
				}else{
					base.tips("出现未知异常，操作失败!",'error');
				}
		},"POST","HTML");
	});
}

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
					generateSybSystemICOHTML();
				}else{
					base.tips("出现未知异常，操作失败!",'error');
				}
	},"POST","HTML");
}

function save(){
	if(checkForm()){
		base.processStatus(1,'save_btn','process_btn');
		base.formSubmit(baseUrl + "/save",function(result){
				if(result == "success"){
					base.tips("保存成功!",'success',1500);
					setTimeout(function(){
						base.cancel('head','list','edit');
						generateSybSystemICOHTML();
					},2000)
				}else{
					base.tips("出现未知异常，操作失败!",'error');
				}
				base.processStatus(1,'save_btn','process_btn');
		},"mainForm");
	}
}

function checkForm(){
	
	var subName = $("#mainForm #subName");
	if(subName.val() == ""){
		base.alert("子系统名称不能为空!");
		subName.focus();
		return false;
	}
	
	var identifyCode = $("#mainForm #identifyCode");
	if(identifyCode.val() == ""){
		base.alert("子系统标识不能为空!");
		identifyCode.focus();
		return false;
	}
	
	var status = $("#mainForm #status option:selected");
	if(status.val() == -1){
		base.alert("请选择子系统状态!");
		return false;
	}
	
	var mainpage = $("#mainForm #mainpage");
	if(mainpage.val() == ""){
		base.alert("子系统访问地址不能为空!");
		mainpage.focus();
		return false;
	}
	
	var orderNum = $("#mainForm #orderNum");
	if(orderNum.val() == "") orderNum.val(Number($("#pageSize").val()) + 1);
	
	return true;
}

function generateSybSystemICOHTML(){
	parent.parent.document.getElementById("topFrame").src = "/top";
}
