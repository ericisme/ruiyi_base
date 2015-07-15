var pOrderNum = 0;
var orderNum = 0;
var pmName = "";
var mName = "";
var addType = 0;
var hasNext = 0;

function onClick(e, treeId, node) {	
	pOrderNum = node.pMaxNum;
	orderNum = node.maxNum;
	$("#id1").val(node.id);
	$("#pid1").val(node.pId == null ? '0' : node.pId);
	$("#level").val(node.commodityCategoryLevel);	
	//changeSelect(node.bmgly_id);
	if (node.commodityCategoryLevel == 1) {
		pmName = "全部分类";
	}
	else {
		pmName = node.getParentNode().name;
	}
	mName = node.name;
	hasNext = node.hasNext;
	$("#name1").val(node.name);
	$("#description1").val(node.description);
	$("#orderNum1").val(node.orderNum);
	$("#status1").html(getStatusHtml(node.status));
	
	if(node.status == 1){
		$("#unable").show();
		$("#able").hide();
	}else{
		$("#unable").hide();
		$("#able").show();
	}	

	//全部分类 级，不能增加同圾,删除 ,保存,设置停/启用
	if(node.commodityCategoryLevel == 1){
		$("#addEqualNode").hide();
		$("#setupNode").hide();
		$("#saveNode").hide();
		$("#deleteNode").hide();
	}else{
		$("#addEqualNode").show();
		$("#setupNode").show();
		$("#saveNode").show();
		$("#deleteNode").show();
	}
	
	$("#showDiv").fadeIn(500);
	$("#addDiv").fadeOut(500);
	$("#optDiv").fadeOut(500);
}

function addNode(i){
	if(i == 1){	//增加同级
		$("#optStep").text("增加同级");
		$("#bmLevel2").html(pmName);
		$("#pid2").val($("#pid1").val());
		$("#orderNum2").val(pOrderNum + 1);
		addType = 1;
	}
	if(i == 2){	//增加下级
		$("#optStep").text("增加下级");
		$("#bmLevel2").html(mName);
		$("#pid2").val($("#id1").val());
		$("#orderNum2").val(orderNum + 1);
		addType = 2;
	}	
	$("#optDiv").fadeIn(500);
	$("#addDiv").fadeIn(500);
}

function save(i){
	
	if(checkForm(i)){
		if(i == 1){	//更新
			base.formSubmit(baseUrl + "/save",function(result){
				if(result == "success"){
					base.tips("修改成功!",'success',1500);
					setTimeout(function(){
						base.load("content",baseUrl + "/list",function(){});
					},2000);
				}else{
					base.tips(result);
				}
			},"form1");
		}
		if(i == 2){ //新增
			base.formSubmit(baseUrl + "/save",function(result){
				if(result == "success"){
					base.tips("添加成功!",'success',1500);
					setTimeout(function(){
						base.load("content",baseUrl + "/list",function(){});
					},2000);
				}else{
					base.tips(result);
				}
			},"form2");
		}
	};
}

function checkForm(i){
	var bmName = $("#bmmc" + i).val();
	if(bmName == ""){
		base.alert("部门名称 不许为空!");
		return false;
	}
	var bmgly_id = $("#bmgly_id" + i).find("option:selected").val(); 
	if(bmgly_id == "-1"){
		base.alert("请选择 部门管理员!");
		return false;
	}
	var status = $("#status" + i).find("option:selected").val(); 
	if(status == "-1"){
		base.alert("请选择 状态!");
		return false;
	}
	var order = $("#orderNum" + i).val();
	if(order == ""){
		if(addType == 1){
			$("#orderNum" + i).val(pOrderNum + 1);
		}else{
			$("#orderNum" + i).val(orderNum + 1);
		}
	} 
	
	return true;
}

function delNode(){
	var id = $("#id1").val();
	var msg = "";
	if(hasNext == 1){
		msg = "该菜单下还有子部门将会所有清除，您确定还要删除吗?";
	}else{
		msg = "您确定要删除这个部门吗?";
	}
	base.confirm(msg,'',function(){
		base.request(baseUrl + "/delNode","id=" + id,function(result){
				if(result == "success"){
					base.tips("删除成功!",'success',1500);
					setTimeout(function(){
						base.load("content",baseUrl + "/list",function(){});
					},2000);
				}else{
					base.tips(result);
				}
		},"POST","HTML");
	});
}

function setup(status){
	var id = $("#id1").val();
	base.request(baseUrl + "/setup","id=" + id + "&status=" + status,function(result){
			if(result == "success"){
				base.tips("操作成功!",'success',1500);
				setTimeout(function(){
					base.load("content",baseUrl + "/list",function(){});
				},2000);
			}else{
				base.tips("出现未知异常，操作失败!",'error');
			}
	},"POST","HTML");

}

function getStatusHtml(i){
	var html = "";
		html += "<select name='status' id='status1'>";
		html += "<option value='-1'>请选择</option>";
		if(i == 1){
			html += "<option value='1' selected>正常</option>";
		}else{
			html += "<option value='1'>正常</option>";
		}
		if(i == 0){
			html += "<option value='0' selected>停用</option>";
		}else{
			html += "<option value='0'>停用</option>";
		}
		
		html += "</select>";
		
		return html;
}

function   changeSelect(continent){ 	
    var sel   =   document.getElementById("bmgly_id1");      
    for(var i=0;i <sel.length;i++){     	 
        if(sel.options[i].value   ==   continent) 
  		sel.options[i].selected   =   true; 
    } 
} 
