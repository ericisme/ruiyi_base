
function commodityQuery() {
	//var paras = 'queryName=' + base.encode($('#queryName').val()) + "&queryStatus=" + $('#queryStatus').val()+ "&queryType=" + $('#queryType').val() + "&page=1";
	var paras = 'queryName=' + base.encode($('#queryName').val()) + "&queryStatus=" + $('#queryStatus').val()+ "&_commodityCategory_id=" + $('#_commodityCategory_id').val() + "&page=1";
	base.load("list", "/backEnd/commodity/query?" + paras, function() {
		base.showList("head", "list", "edit"); // 标签的显示与隐藏，显示head、list、隐藏edit
	});
}

function query() {
	commodityQuery();
}

function del(id) {
	base.confirm("您确定要删除幻灯片吗?", '', function() {
		base.request("/backEnd/commodity/delete", "ids=" + id, function(result) {
			if (result == "success") {
				base.tips("删除成功!", 'success', 1500);
				setTimeout(function() {
					base.cancel('head', 'list', 'edit', commodityQuery);
				}, 2000);
			} else {
				base.tips("出现未知异常，操作失败!", 'error');
			}
		}, "POST", "HTML");
	});
}

function delMore() {
	var ids = base.getChecked("cbx", true);
	del(ids);
}

function edit(i,flag) {
	base.load("edit", "/backEnd/commodity/edit/" + i + "/" + flag, function() {
		base.showEdit("head", "list", "edit");
	});
}

function isInteger(obj){
	reg =  /^[0-9]*[1-9][0-9]*$/;
	return (!reg.test(obj));
}

function save() {
	var pictrueName = $("#pictrueName").val();
	var sortNumber = $("#sortNumber").val();
	var stocks = $("#stocks").val();
	var price = $("#price").val();
	var status = $("#status").val();
	var commodityCategory_id = $("#commodityCategory_id").val();
	var indexPagePictureNumber = $("#indexPagePictureNumber").val();
	var commodityNo = $('#commodityNo').val();
	
	if (pictrueName == "" || sortNumber == ""  || status=="" ||commodityNo=="") {
		base.tips("请完善商品信息!", 'warning', 1500);
		return false;
	}
	if(commodityCategory_id == ""){
		base.tips("请选择商品类别!", 'warning', 1500);
		return;
	}	
	if (isNaN(sortNumber) || isInteger(sortNumber)) {
		base.tips("排序号必须为正整数!", 'warning', 1500);
		return false;
	}
	if (isNaN(stocks) || isInteger(stocks)) {
		base.tips("库存必须为正整数!", 'warning', 1500);
		return false;
	}
	
	if (isNaN(price) || isInteger(price)) {
		base.tips("积分必须为正整数!", 'warning', 1500);
		return false;
	}
	if (indexPagePictureNumber !=null && indexPagePictureNumber !='' && (isNaN(indexPagePictureNumber) || isInteger(indexPagePictureNumber))) {
		base.tips("图片编号必须为正整数!", 'warning', 1500);
		return false;
	}
	
	var editor = CKEDITOR.instances.description;
	if(editor.getData()==""){
		base.tips("内容 不能为空。");
		return;
	}
	if(editor.getData().length>9000){
		base.tips("内容 不能超过9000字符。");
		return;
	}
	$("#description").val(editor.getData());
	
	
	base.processStatus(1, 'save', 'process');
	
	$.ajax({
		cache: true,
		type: "POST",
		url:"/backEnd/commodity/save",
		data:$('#mainForm').serialize(),// 你的formid
		async: false,
	    error: function(request) {
	    	base.tips("出现未知异常，操作失败!", 'error');
	    },
	    success: function(data) {
			if (data == "success") {
			base.tips("保存成功!", 'success', 1500);
			setTimeout(function() {
				base.cancel('head', 'list', 'edit', commodityQuery);
			}, 1000);
			} else if(data == "error1"){
					base.tips("商品名称不能重复！", 'error');
			}else if(data == "error2"){
					base.tips("商品编号不能重复！", 'error');
			}else {
					base.tips("出现未知异常，操作失败!", 'error');
			}
	    }
	});
	
	
}

/**
 * 验证图片格式是否正确
 * @param pictureName
 * @returns
 */
function checkValid(pictureName){
	 reg=/[^\s]+\.(([j,J][p,P][g,G])|([g,G][i,I][f,F])|([j,J][p,P][e,E][g,G])|([p,P][n,N][g,G])|([b,B][m,M][p,P]))/gi;
	 return reg.test(pictureName);
}
/**
 * 停用 / 启用
 */
function setup(id,status){
	status = status == 0 ? 1 : 0;
	base.request("/backEnd/commodity/setup?","id=" + id + "&status=" + status,function(result){
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

