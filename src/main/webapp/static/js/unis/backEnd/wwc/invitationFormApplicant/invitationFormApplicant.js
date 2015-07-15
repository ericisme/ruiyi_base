/**
 * 查找query
 */
function searchQuery(){
	var paras = 'page=1';
	//签到与否
	if($("#_checkOrNot").val() != ''){
		paras += '&_checkOrNot=' +$("#_checkOrNot").val();
	}
	//国家/地区
	if($("#_districtCode").val() != ''){
		paras += '&_districtCode=' +encodeURI($("#_districtCode").val(), "utf-8");
	}
	//公司名
	if($("#_companyName").val() != ''){
		paras += '&_companyName=' + encodeURI($("#_companyName").val(), "utf-8");
	}
	//中文姓名
	if($("#_name").val() != ''){
		paras += '&_name=' + encodeURI($("#_name").val(), "utf-8");
	}
	//英文firstName
	if($("#_firstName").val() != ''){
		paras += '&_firstName=' + encodeURI($("#_firstName").val(), "utf-8");
	}
	//英文lastName
	if($("#_lastName").val() != ''){
		paras += '&_lastName=' + encodeURI($("#_lastName").val(), "utf-8");
	}
	//手机号
	if($("#_mobileNumber").val() != ''){
		paras += '&_mobileNumber=' + encodeURI($("#_mobileNumber").val(), "utf-8");
	}
	//email
	if($("#_email").val() != ''){
		paras += '&_email=' + encodeURI($("#_email").val(), "utf-8");
	}

	//请求查询
	base.load("list", baseUrl+"/searchQuery?" + paras,function(){
			base.showList("head","list","edit");
		});
}
/**
 * 查找Index
 */
function searchIndex(){
	URL= baseUrl+"/searchIndex";
	loc_x=document.body.scrollLeft+event.clientX-event.offsetX+50;
	loc_y=document.body.scrollTop+event.clientY-event.offsetY+50;
	window.showModalDialog(URL,self,"edge:raised;scroll:yes;status:0;help:0;resizable:yes;dialogWidth:1000px;dialogHeight:600px;dialogTop:"+loc_y+"px;dialogLeft:"+loc_x+"px");
}
/**
 * 从选择列表中 签到
 * @returns
 */
function checkInFromSearchQuery(identityCode){
	$("#_identityCode").val(identityCode);
	checkIn();
}

/**
 * 签到
 */
function checkIn(){
		var paras = '_identityCode='+$("#_identityCode").val();		
		//请求查询
		base.load("list", baseUrl+"/checkIn?" + paras,function(){
				base.showList("head","list","edit");
			});	
	$("#_identityCode").val("");
	$("#_identityCode").focus();
}

/**
 * 删除签到记录方法
 */
function checkInDelete(id){
		base.confirm("您确定要删除 记录 吗?", '', function(){
			base.request(baseUrl+"/checkInDelete", "ids=" + id, function(result){
					if(result == "success"){
						base.tips("删除成功!", 'success', 1500);
						$("#checkInTr_"+id).remove();
					}else{
						base.tips(result, 'error');
					}
			},"POST","HTML");
		});
}


