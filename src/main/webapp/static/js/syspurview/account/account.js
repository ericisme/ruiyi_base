/**
* 根据provinceCode获得Game Center html OPTION
*/
function getGameCentersByProvinceCode(provinceCode, situ){
	base.request("/account/user/getGameCentersByProvinceCode","provinceCode=" + provinceCode, function(result){
		var opts = result.gc_select;
		if(situ==null || situ==undefined){			
			$("#s_gamecenterIds").html(opts);
		}else{
			$("#"+situ).html(opts);
		}
	},"POST","JSON");
}
/**
* 根据cityCode获得Game Center html OPTION
*/
function getGameCentersByCityCode(cityCode, situ){
	if($('#provinceSel').val()==cityCode){
		getGameCentersByProvinceCode($('#provinceSel').val(), situ);
		return;
	}
	base.request("/account/user/getGameCentersByCityCode","cityCode=" + cityCode, function(result){
		var opts = result.gc_select;
		if(situ==null || situ==undefined){			
			$("#s_gamecenterIds").html(opts);
		}else{
			$("#"+situ).html(opts);
		}
	},"POST","JSON");
}
function addGameCenter(){
	var slt1 = $("#s_gamecenterIds");
	var slt2 = $("#gamecenterIds");
	var opt = slt1.find("option:selected");
	if(opt.text()==""){
		return;
	}
	var flag = true;
	slt2.find("option").each(function(){
		if(flag){
			if($(this).val() == opt.val()){
				flag = false;
			}
		}
	});
	if(flag){
		slt2.append("<option value='"+opt.val()+"'>" +opt.text()+ "</option>");
		//opt.remove();
	}
}
function delGameCenter(){
	var slt1 = $("#s_gamecenterIds");
	var slt2 = $("#gamecenterIds");
	var opt = slt2.find("option:selected");
	var flag = true;
	slt1.find("option").each(function(){
		if(flag){
			if($(this).val() == opt.val()){
				flag = false;
			}
		}
	});
	//if(flag){
	//	slt1.append("<option value='"+opt.val()+"'>" +opt.text()+ "</option>");
	//}
	opt.remove();
}




function getTwoSelect(One_id){
	getXxSelect(One_id,'typeTwo');
}
function userQuery(){	
	//alert($('#qryName').val()+"==="+$('#_roleId').val()+"==="+typeId+"==="+userType);
	var paras='qryName=' + base.encode($('#qryName').val()) + '&roleId=' + $('#_roleId').val() +"&page=1";
	base.load("list","/account/user/query?" + paras,function(){
		base.showList("head","list","edit");		//标签的显示与隐藏，显示head、list、隐藏edit
	});
}
function del(id){
	base.confirm("您确定要删除用户吗?",'',function(){
		base.request("/account/user/delete","ids=" + id,function(result){
				if(result == "success"){
					base.tips("删除成功!",'success',1500);
					setTimeout(function(){
						base.cancel('head','list','edit',userQuery);
					},2000);
				}else{
					base.tips("出现未知异常，操作失败!",'error');
				}
		},"POST","HTML");
	});
}

function delMore(){
	var ids = base.getChecked("cbx",true);
	del(ids);
}

function setup(userId,status){
//	alert(userId+"=="+status);
	base.request("/account/user/setup","userId=" + userId+"&sta="+status,function(result){
		if(result == "success"){
			if(status==1){
				base.tips("停用成功!",'success',1500);
			}else{
				base.tips("启用成功!",'success',1500);
			}			
			setTimeout(function(){
				base.cancel('head','list','edit',userQuery);
			},2000);
		}else{
			base.tips("出现未知异常，操作失败!",'error');
		}
	},"POST","HTML");	
}
function edit(i){
	base.load("edit","/account/user/edit/" + i,function(){
		base.showEdit("head","list","edit");
	});
	
}



function save(key){
	if(checkForm(key)){
		//角色
		var roles=$("#roleIds option");
		var roleIds="";
		roles.each(function(){
			roleIds =roleIds+$(this).val()+",";
		});
		if(roleIds.length > 0)
		roleIds = roleIds.substring(0, roleIds.length-1);  //拼接的角色ID字符串
		//游乐场
		var gamecenters=$("#gamecenterIds option");
		var gamecenterIds="";
		gamecenters.each(function(){
			gamecenterIds =gamecenterIds+$(this).val()+",";
		});
		//alert(gamecenterIds);
		if(gamecenterIds.length > 0)
			gamecenterIds = gamecenterIds.substring(0, gamecenterIds.length-1);  //拼接的游乐场ID字符串		
		//用户类型
		//var usertype = $("input[name='usertype'][checked]").val(); 
		
		base.processStatus(1,'save','process');
		base.formSubmit("/account/user/save?roleIds="+roleIds+"&gamecenterIds="+gamecenterIds, function(result){
				if(result == "success"){
					base.tips("保存成功!",'success',1500);
					setTimeout(function(){
						base.cancel('head','list','edit',userQuery);
					},2000);
				}else{
					base.tips("出现未知异常，操作失败!",'error');
				}
				base.processStatus(0,'save','process');
		},"mainForm");
	}else{
		$("#password").blur();
		base.tips("请完善用户信息!",'warning',1500);
	}
}



function modifyPassword(key){
	
	var oldPassword = $("#oldPassword").val();
	var newPassword = $("#newPassword").val();
	var newPasswordAgain = $("#newPasswordAgain").val();
	if (oldPassword == "" || newPassword == "" || newPasswordAgain == "" ) {
		if(key==1){
			alert("请完善密码信息!");
		}else{
			base.tips("请完善密码信息!", 'warning', 1500);
		}
		return false;
	}
	if (newPassword != newPasswordAgain ) {
		if(key==1){
			alert("两个密码不一致！！！");
		}else{
			base.tips("两个密码不一致！！！", 'warning', 1500);
		}
		return false;
	}
	
	
	$.ajax({
		cache: true,
		type: "POST",
		url:"/backEnd/modifyPassword",
		data:$('#mainForm').serialize(),// 你的formid
		//data:handlePassword(),
		async: false,
	    error: function(request) {
	    	if(key==1){
	    		alert("出现未知异常，操作失败!");
	    	}else{
	    		base.tips("出现未知异常，操作失败!", 'error');
	    	}
	    	
	    },
	    success: function(data) {
	    	
	    	if(key==1){
	    		refreshForm();
	    		if(data=='4'){
	    			alert("密码修改成功");
	    		}else if(data=='1'){
	    			alert("两个密码不一致！！！");
	    		}else if(data=='2'){
	    			alert("旧密码输入错误！");
	    		}else if(data=='3'){
	    			alert("密码修改失败！");
	    		}else{
	    			alert("出现未知异常，操作失败!");
	    		}
	    	}else{
	    		if (data == "4") {
	    			base.tips("密码修改成功", 'success', 1500);
	    			setTimeout(function() {
	    				refreshAfterModifyPassword();
	    			}, 1000)
	    			} else if(data == "1"){
	    				base.tips("两个密码不一致！！！", 'error');
	    			} else if(data == "2"){
	    				base.tips("旧密码输入错误！", 'error');
	    			} else if(data == "3"){
	    				base.tips("密码修改失败！", 'error');
	    			} 
	    			else{
	    				base.tips("出现未知异常，操作失败!", 'error');
	    			}
	    	}
			
	    }
	});
	
	refreshForm();
}

function handlePassword(){
	var data = 'oldPassword=' + $.md5($("#oldPassword").val()) + '&newPassword=' + $.md5($("#newPassword").val()) + '&newPasswordAgain=' +  $.md5($("#newPasswordAgain").val()) + '';
	return data;
}
function refreshForm(){
	 $("#oldPassword").val('');
	 $("#newPassword").val('');
	 $("#newPasswordAgain").val('');
}

function refreshAfterModifyPassword(){
	window.location.href = welcomeUrl;
}

function checkForm(key){
	var usertype=$("input[name='usertype']:checked").val();
	if(usertype==1){
		$("#typeTwo").attr("name","typeId");
		$("#typeOne").attr("name","typeOne");
	}else if(usertype==2){
		$("#typeOne").attr("name","typeId");
		$("#typeTwo").attr("name","typeTwo");
	}
	
	if(key){			//新增的时候才检查登录名的唯一性，修改的时候不允许修改登录名
		checks('loginName');
	}
	checks('username');
	checks('password');
	checks('confpas');
	checks('mobile');
	checks('email');

	if( $("#tipFour").html()=="" && $("#tipFive").html()=="" && $("#tips").html()==""){
		if(($("#tipOne").html()=="" && $("#tipTwo").html()=="" && $("#tipThree").html()=="") || $("#tipOne").html()==undefined){  //修改用户时不涉及到登录名、密码、确认密码后的提示检查
			return true;
		}	
	}else{
		return false;
	}

}

function changeUserType(chosen){
	if(chosen == 1){
		$("#usertype_tab1").show();
		$("#usertype_tab2").hide();
		$("#usertype_tab3").hide();
	}
	if(chosen == 2){
		$("#usertype_tab1").hide();
		$("#usertype_tab2").show();
		$("#usertype_tab3").hide();
	}
	if(chosen == 3){
		$("#usertype_tab1").hide();
		$("#usertype_tab2").hide();
		$("#usertype_tab3").show();
	}
}

function addRole(){
	var slt1 = $("#s_roleId");
	var slt2 = $("#roleIds");
	var opt = slt1.find("option:selected");
	var flag = true;
	slt2.find("option").each(function(){
		if(flag){
			if($(this).val() == opt.val()){
				flag = false;
			}
		}
	});
	if(flag){
		slt2.append("<option value='"+opt.val()+"'>" +opt.text()+ "</option>");
		opt.remove();
	}
}

function delRole(){
	var slt1 = $("#s_roleId");
	var slt2 = $("#roleIds");
	var opt = slt2.find("option:selected");
	var flag = true;
	slt1.find("option").each(function(){
		if(flag){
			if($(this).val() == opt.val()){
				flag = false;
			}
		}
	});
	if(flag){
		slt1.append("<option value='"+opt.val()+"'>" +opt.text()+ "</option>");
	}
	opt.remove();
}


//字符串去空格方法
function trim(str){   
	if(str == "" || str == undefined) return "";
    str = str.replace(/^(\s|\u00A0)+/,'');   
    for(var i=str.length-1; i>=0; i--){   
        if(/\S/.test(str.charAt(i))){   
            str = str.substring(0, i+1);   
            break;   
        }   
    }   
    return str;   
}  
//邮箱验证方法
function IsEmail(str){
		var myreg = /^[-_A-Za-z0-9\.]+@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;	
		return (myreg.test(str) || str.length==0);
}
//正则表达式验证手机号码
function checkmobile(s){
		if(/^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/.test(s) || /^1[3|4|5|8][0-9]\d{4,8}$/.test(s) || s.length==0)
		{
			return true;
		}
		else{
			return false;
		}		
}
function checks(id){
	if(id=='loginName'){  //新增用户时登录名才获取验证
		var logins=trim($("#loginName").val()).length;
		if(logins==0){
			$("#tipOne").html("登录名不能为空！");
			$("#loginName").focus();
		}else {
			base.request(
					'/account/user/check',
					'loginName='+encodeURI($("#loginName").val(),"utf-8"),
					function(msg){
						if(msg == "false"){
							$("#tipOne").html("此登录名已被注册");
							$("#loginName").focus();
						} else{
							$("#tipOne").html("");
						}		
					},
					'POST',
					'HTML'
			);
		}
		
	}else if(id=='password'){
		var len = trim($("#initPassword").val()).length;
		if(len==0){			
			$("#tipTwo").html("密码不能为空！");
			$("#initPassword").focus();
		}else{
			$("#tipTwo").html("");
		}
	}else if(id=='confpas'){
		if($("#initPassword").val() !=$("#confpas").val()){
			$("#tipThree").html("密码输入不一致！");
			$("#confpas").attr("value","");
			$("#confpas").focus();
		}else{
			$("#tipThree").html("");
		}		
	}else if(id=='mobile'){
		if(!checkmobile($("#mobile").val())){
			$("#tipFour").html("手机格式不正确！");
			$("#mobile").focus();
		}else{
			$("#tipFour").html("");
		}
	}else if(id =='email'){
			if(!IsEmail($("#email").val())){
				$("#tipFive").html("邮箱格式不正确");
				$("#email").focus();
			}else{
				$("#tipFive").html("");
			}
		}else if(id=='username'){
			var pas = trim($("#username").val()).length;
			if(pas==0){			
				$("#tips").html("真实姓名不能为空！");
				$("#username").focus();
			}else{
				$("#tips").html("");
			}
			
		}		
}



















