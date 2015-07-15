function gameQuery() {
	var paras = 'queryName=' + base.encode($('#queryName').val())
			+ "&queryIsHot=" + base.encode($('#queryIsHot').val())
			+ "&queryStatus=" + base.encode($('#queryStatus').val())
			+ "&queryIsNew=" + base.encode($('#queryIsNew').val())
			+ "&queryType=" + base.encode($('#queryType').val()) + "&page=1";
	base.load("list", "/backEnd/game/query?" + paras, function() {
		base.showList("head", "list", "edit"); // 标签的显示与隐藏，显示head、list、隐藏edit
	});
}

function query() {
	gameQuery();
}

function del(id) {
	base.confirm("您确定要删除本游戏吗?", '', function() {
		base.request("/backEnd/game/delete", "ids=" + id, function(result) {
			if (result == "success") {
				base.tips("删除成功!", 'success', 1500);
				setTimeout(function() {
					base.cancel('head', 'list', 'edit', gameQuery);
				}, 2000)
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

function edit(i) {
	base.load("edit", "/backEnd/game/edit/" + i, function() {
		base.showEdit("head", "list", "edit");
	});
}

function save() {

	var nameForCH = $("#nameForCH").val();
	var nameForEN = $("#nameForEN").val();
	var release_Date = $("#release_Date").val();
	var iconPath = $("#iconPath").val();
	var sortNumber = $("#sortNumber").val();

	if (nameForCH == "" || nameForEN == "" || release_Date == ""
			|| sortNumber == "") {
		base.tips("请完善游戏信息!", 'warning', 1500);
		return false;
	}
	if (iconPath == "" || iconPath == "/upload/game/default/default.png") {
		base.tips("请上传游戏图标！", 'warning', 1500);
		return false;
	}
	if (iconPath == "uploading") {
		base.tips("游戏图标上传中,请在上传完成后再点击提交！", 'warning', 1500);
		return false;
	}
	if (iconPath == "error") {
		base.tips("游戏图标上传失败！", 'warning', 1500);
		return false;
	}
	// 限制描述内容长度，不能超过500
	if ($("#description").val().length > 500) {
		alert("描述内容不能超过500个字符");
		$("#description").focus();
		return false;
	}

	/*
	 * for(var k=0 ,len = $("[name='ios_marketname']").length ; k<len ; k++ ){
	 * if($("[name='ios_marketname'] :eq(" + k +")").val() == ''){
	 * base.tips("完善下载信息！", 'warning', 1500); return false; } } for(var k=0 ,len =
	 * $("[name='ios_url']").length ; k < len ; k++){
	 * if($("[name='ios_url']:eq(" + k +")").val() == ''){ base.tips("完善下载信息！",
	 * 'warning', 1500); return false; } }
	 * 
	 * for(var k=0 ,len = $("[name='android_marketname']").length ;k < len ;
	 * k++){ if($("[name='android_marketname']:eq(" + k +")").val() == ''){
	 * base.tips("完善下载信息！", 'warning', 1500); return false; } } for(var k=0,len =
	 * $("[name='android_url']").length ; k<len ; k++){
	 * if($("[name='android_url']:eq(" + k +")").val() == ''){
	 * base.tips("完善下载信息！", 'warning', 1500); return false; } }
	 */
	$.ajax({
		type : "POST",
		url : "/backEnd/game/save",
		data : $('#mainForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			base.tips("出现未知异常，操作失败!", 'error');
		},
		success : function(data) {
			if (data == "success") {
				base.tips("保存成功!", 'success', 1500);
				setTimeout(function() {
					base.cancel('head', 'list', 'edit', gameQuery);
				}, 1000);
			} else {
				base.tips("出现未知异常，操作失败!", 'error');
			}
		}
	});

}
/**
 * 验证图片格式是否正确
 * 
 * @param pictureName
 * @returns
 */
function checkValid(pictureName) {
	reg = /[^\s]+\.(([j,J][p,P][g,G])|([g,G][i,I][f,F])|([j,J][p,P][e,E][g,G])|([p,P][n,N][g,G])|([b,B][m,M][p,P]))/gi;
	return reg.test(pictureName);
}
/**
 * 停用 / 启用
 */
function setup(id, status) {
	status = status == 0 ? 1 : 0;
	base.request("/backEnd/game/setup?", "id=" + id + "&status=" + status,
			function(result) {
				if (result == "success") {
					base.tips((status == 1 ? "启用" : "停用") + " 成功!", 'success',
							1500);
					setTimeout(function() {
						base.cancel('head', 'list', 'edit');
					}, 100);
				} else {
					base.tips(result);
				}
			}, "POST", "HTML");
}
