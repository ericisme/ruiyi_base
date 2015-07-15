
function query(){
	var pUnit = $("#p_unit option:selected").val();
	var schoolName = $("#schoolName").val();
	var schoolCode = $("#schoolCode").val();
	var paras = "page=1&schoolName=" + encodeURI(schoolName,"utf-8") + "&schoolCode=" + schoolCode + "&pUnit=" + pUnit;
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
	var conf = "";
	conf = $("#mainForm #schoolName");
	if(conf.val() == ""){
		base.alert("学校名称不能为空!");
		conf.focus();
		return false;
	}
	conf = $("#mainForm #xx_m");
	if(conf.val() == ""){
		base.alert("学校代码不能为空!");
		conf.focus();
		return false;
	}
	conf = $("#mainForm #xxlb_m option:selected");
	if(conf.val() == ""){
		base.alert("请选择行业分类!");
		conf.focus();
		return false;
	}
	conf = $("#mainForm #xxxlb_m option:selected");
	if(conf.val() == ""){
		base.alert("请选择主体学校办学类型!");
		conf.focus();
		return false;
	}
	conf = $("#mainForm #xxbxlb_m option:selected");
	if(conf.val() == ""){
		base.alert("请选择办学类别!");
		conf.focus();
		return false;
	}
	conf = $("#mainForm #xqbb_m option:selected");
	if(conf.val() == ""){
		base.alert("请选择学前办学类别!");
		conf.focus();
		return false;
	}
	conf = $("#mainForm #szdq_m option:selected");
	if(conf.val() == "0"){
		base.alert("请选择学校(机构)地址代码!");
		conf.focus();
		return false;
	}
	conf = $("#mainForm #cxfl_m option:selected");
	if(conf.val() == ""){
		base.alert("请选择城乡分类!");
		conf.focus();
		return false;
	}
	conf = $("#mainForm #topUnit option:selected");
	if(conf.val() == "0"){
		base.alert("请选择上级管理部门!");
		conf.focus();
		return false;
	}
	conf = $("#mainForm #xxdqlb_m option:selected");
	if(conf.val() == ""){
		base.alert("请选择学校地区类别!");
		conf.focus();
		return false;
	}
	conf = $("#mainForm #ywmc");
	if(conf.val() == ""){
		base.alert("学校(机构)英文名称不能为空!");
		conf.focus();
		return false;
	}
	conf = $("#mainForm #fzr");
	if(conf.val() == ""){
		base.alert("学校(机构)负责人姓名不能为空!");
		conf.focus();
		return false;
	}
	conf = $("#mainForm #lxdh");
	if(conf.val() == ""){
		base.alert("办公电话不能为空!");
		conf.focus();
		return false;
	}
	conf = $("#mainForm #cz");
	if(conf.val() == ""){
		base.alert("传真电话不能为空!");
		conf.focus();
		return false;
	}
	conf = $("#mainForm #yb");
	if(conf.val() == ""){
		base.alert("邮政编码不能为空!");
		conf.focus();
		return false;
	}
	conf = $("#mainForm #dzyj");
	if(conf.val() == ""){
		base.alert("单位电子信箱不能为空!");
		conf.focus();
		return false;
	}
	conf = $("#mainForm #tbr");
	if(conf.val() == ""){
		base.alert("填表人不能为空!");
		conf.focus();
		return false;
	}	
	conf = $("#mainForm #tbrdzyj");
	if(conf.val() == ""){
		base.alert("填表人电子邮件不能为空!");
		conf.focus();
		return false;
	}		
	conf = $("#mainForm #gyqybxlb_m option:selected");
	if(conf.val() == ""){
		base.alert("请选择国有企业办学类别!");
		conf.focus();
		return false;
	}
	conf = $("#mainForm #zsxxdj_m option:selected");
	if(conf.val() == ""){
		base.alert("请选择直属学校等级!");
		conf.focus();
		return false;
	}
	conf = $("#mainForm #xxdj_m option:selected");
	if(conf.val() == ""){
		base.alert("请选择学校等级!");
		conf.focus();
		return false;
	}
	conf = $("#mainForm #gfhxx option:selected");
	if(conf.val() == "-1"){
		base.alert("请选择是否规范化学校!");
		conf.focus();
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

function showSzdqCode(obj){
	$("#showSzdqCode").text($(obj).find("option:selected").val());
}
