
function pictureQuery() {
	var paras = 'queryName=' + base.encode($('#queryName').val()) + "&queryStatus=" + $('#queryStatus').val() + "&queryType="  + $('#queryType').val() + "&page=1";
	base.load("list", "/backEnd/picture/query?" + paras, function() {
		base.showList("head", "list", "edit"); // 标签的显示与隐藏，显示head、list、隐藏edit
	});
}

function query() {
	pictureQuery();
}

function del(id) {
	base.confirm("您确定要删除这张图片吗?", '', function() {
		base.request("/backEnd/picture/delete", "ids=" + id, function(result) {
			if (result == "success") {
				base.tips("删除成功!", 'success', 1500);
				setTimeout(function() {
					base.cancel('head', 'list', 'edit', pictureQuery);
				}, 2000)
			} else if(result=="error"){
				base.tips("图片被引用不能删除!", 'error');
			} else {
				base.tips("出现未知异常，操作失败111!", 'error');
			}
		}, "POST", "HTML");
	});
}

function delMore() {
	var ids = base.getChecked("cbx", true);
	del(ids);
}

function edit(i) {
	base.load("edit", "/backEnd/picture/edit/" + i, function() {
		base.showEdit("head", "list", "edit");
	});
}
function multiupload(){
	var type = $('#queryType').val();
	if(type == 'all'){
		base.tips("请选择批量添加类型!", 'warning', 1500);
	}else{
		base.load("edit", "/backEnd/picture/multiupload?type=" + type, function() {
			base.showEdit("head", "list", "edit");
		});
	}

}

function save() {
	var pictrueName = $("#pictrueName").val();
	var status = $("#status").val();
	var url = $("#url").val();
	if (pictrueName == "" || status=="") {
		base.tips("请完善幻灯片信息!", 'warning', 1500);
		return false;
	}
	if (url=="" || url=="/upload/ppt/default/default.png"){
		base.tips("请选择图片！", 'warning', 1500);
		return false;
	}
	if (url=="uploading"){
		base.tips("图片上传中,请在上传完成后再点击提交！", 'warning', 1500);
		return false;
	}
	if (url=="error"){
		base.tips("图片上传失败！", 'warning', 1500);
		return false;
	}
	base.processStatus(1, 'save', 'process');

	$.ajax({
		cache: true,
		type: "POST",
		url:"/backEnd/picture/save",
		data:$('#mainForm').serialize(),// 你的formid
		async: false,
	    error: function(request) {
	    	base.tips("出现未知异常，操作失败!", 'error');
	    },
	    success: function(data) {
			if (data == "success") {
			base.tips("保存成功!", 'success', 1500);
			setTimeout(function() {
				base.cancel('head', 'list', 'edit', pictureQuery);
			}, 1000)
			} else {
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
	base.request("/backEnd/picture/setup?","id=" + id + "&status=" + status,function(result){
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
