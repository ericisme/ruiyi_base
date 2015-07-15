
function adsQuery() {
	var paras = 'queryName=' + base.encode($('#queryName').val()) + "&queryStatus=" + $('#queryStatus').val() + "&page=1";
	base.load("list", "/backEnd/ads/query?" + paras, function() {
		base.showList("head", "list", "edit"); // 标签的显示与隐藏，显示head、list、隐藏edit
	});
}

function query() {
	adsQuery();
}

function del(id) {
	base.confirm("您确定要删除这个广告吗?", '', function() {
		base.request("/backEnd/ads/delete", "ids=" + id, function(result) {
			if (result == "success") {
				base.tips("删除成功!", 'success', 1500);
				setTimeout(function() {
					base.cancel('head', 'list', 'edit', adsQuery);
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

function edit(i) {
	base.load("edit", "/backEnd/ads/edit/" + i, function() {
		base.showEdit("head", "list", "edit");
	});
}

function save() {
	var pictrueName = $("#pictrueName").val();
	var sortNumber = $("#sortNumber").val();
	var status = $("#status").val();
	var url = $("#url").val();
	if (pictrueName == "" || sortNumber == "" || status=="") {
		base.tips("请完善幻灯片信息!", 'warning', 1500);
		return false;
	}
	if (isNaN(sortNumber)) {
		base.tips("排序号必须为数字!", 'warning', 1500);
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
		url:"/backEnd/ads/save",
		data:$('#mainForm').serialize(),
		async: false,
	    error: function(request) {
	    	base.tips("出现未知异常，操作失败!", 'error');
	    },
	    success: function(data) {
			if (data == "success") {
			base.tips("保存成功!", 'success', 1500);
			setTimeout(function() {
				base.cancel('head', 'list', 'edit', adsQuery);
			}, 1000);
			} else {
					base.tips("出现未知异常，操作失败!", 'error');
			}
	    }
	});

}
/**
 * 幻灯片信息表单提交，ajaxFileUpload插件有bug，IE浏览器下有问题
 */
function picChange() {
	var pic = $("#pic").val();
	if(!checkValid(pic)){
		base.tips("选择的不是图片！", 'error');
		return false;
	}

	$("#preview").attr("src","/static/images/postgressbar.gif");
	$("#url").attr("value", "uploading");

	$.ajaxFileUpload({
		url : '/backEnd/ads/uploadPicture',
		secureuri : false,
		fileElementId : 'pic',
		dataType : 'xml',//json的话要引入jquery1.4.2以下版本
		success : function(data, status) {
			$("#preview").attr("src", $(data).find("previewUrl").text());
			$("#previewUrl").val($(data).find("previewUrl").text());
			$("#url").val($(data).find("url").text());
		},
		error : function(data, status, e)
		{
			$("#previewUrl").val("error");
			$("#url").val( "error");
		}
	});
};


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
	base.request("/backEnd/ads/setup?","id=" + id + "&status=" + status,function(result){
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

function updateCount(){
	$.ajax({
		cache: false,
		type: "POST",
		url:"/backEnd/ads/update_count",
		data:"count="+$('#biggest').val(),
		async: false,
	    error: function(request) {
	    	base.tips("出现未知异常，操作失败!", 'error');
	    },
	    success: function(data) {
			if(data == "1"){
				base.tips("更新成功!", 'success');
			}else{
				base.tips("出现未知异常，操作失败!", 'error');
			}


	    }
	});

}