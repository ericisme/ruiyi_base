/**
 * 单选
 */
function oneSelectDoAjaxRequestAndSetThePictureTable(){
	$.ajax({
		cache: true,
		dataType: "json",
		type: "get",
		url:"/backEnd/picture/pickPicture",
		data:"sortByIds= " + $('#indexPagePictureNumber').val() + "&page=1",
		async: false,
	    error: function(request) {
	    	base.tips("出现未知异常，操作失败!", 'error');
	    },
	    success: function(data) {
	    	$('#pictureTable').html("<tr><th class=\"biaoge_33\">编号</th><th class=\"biaoge_33\">名称</th><th class=\"biaoge_33\">预览</th><th class=\"biaoge_33\">选中</th></tr>");
	    	$(data.pageContent).each(function(){
	    		$('#pictureTable').append("<tr><td class=\"biaoge_33\"> " +this.id +  "</td><td class=\"biaoge_33\">" +this.name+ "</td><td class=\"biaoge_33\">" +
	    				"<img height=\"40\" width=\"40\" src='" + this.previewUrl + "'/></td><td class=\"biaoge_33\"><input id='picture_id_" + this.id + "' class='btn btn-default' pictureid='" + this.id + "' type='button' value='选择' data-dismiss='modal'/></td></tr>");
	    		setDefaultChoose(this.id);
	    	});
	    	$('#pagenum').html('');
	    	for(var i=1;i<=data.maxPageNum;i++){
	    		$('#pagenum').append("<option value='" + i + "' > 第" + i +  "页</option>");
	    	}
	    	$("#pagenum").change(function(){
	    		var queryString = getQueryString();
	    		$.ajax({
	    			cache: true,
	    			dataType: "json",
	    			type: "get",
	    			url:"/backEnd/picture/pickPicture",
	    			data:"sortByIds= " + $('#indexPagePictureNumber').val() + "&page=" + $('#pagenum').val() + queryString,
	    			async: false,
	    		    error: function(request) {
	    		    	base.tips("出现未知异常，操作失败!", 'error');
	    		    },
	    		    success: function(data) {
	    		    	$('#pictureTable').html("<tr><th class=\"biaoge_33\">编号</th><th class=\"biaoge_33\">名称</th><th class=\"biaoge_33\">预览</th><th class=\"biaoge_33\">选中</th></tr>");
	    		    	$(data.pageContent).each(function(){
	    		    		$('#pictureTable').append("<tr><td class=\"biaoge_33\"> " +this.id +  "</td><td class=\"biaoge_33\">" +this.name+ "</td><td class=\"biaoge_33\">" +
	    		    				"<img height=\"40\" width=\"40\" src='" + this.previewUrl + "'/></td><td class=\"biaoge_33\"><input id='picture_id_" + this.id + "' class='btn btn-default' pictureid='" + this.id + "' type='button' value='选择' data-dismiss='modal'/></td></tr>");
	    		    		setDefaultChoose(this.id);
	    		    	});
	    		    	$("input[id^=picture_id_]").click(function(){
	    		    		$('#indexPagePictureNumber').val($(this).attr('pictureid'));
	    		    		closeProcForImageSelector();
	    		    	});
	    		    }
	    		});


	    	});

	    	$("input[id^=picture_id_]").click(function(){
	    		$('#indexPagePictureNumber').val($(this).attr('pictureid'));
	    		closeProcForImageSelector();
	    	});
	    }
	});
}

/**
 * 单选
 */
function oneSelectSearchBtnAndSetPictureTable(){
	var queryString = getQueryString();
	$.ajax({
		cache: true,
		dataType: "json",
		type: "get",
		url:"/backEnd/picture/pickPicture",
		data:"page=1" + queryString,
		async: false,
	    error: function(request) {
	    	base.tips("出现未知异常，操作失败!", 'error');
	    },
	    success: function(data) {
	    	$('#pictureTable').html("<tr><th class=\"biaoge_33\">编号</th><th class=\"biaoge_33\">名称</th><th class=\"biaoge_33\">预览</th><th class=\"biaoge_33\">选中</th></tr>");
	    	$(data.pageContent).each(function(){
	    		$('#pictureTable').append("<tr><td class=\"biaoge_33\"> " +this.id +  "</td><td class=\"biaoge_33\">" +this.name+ "</td><td class=\"biaoge_33\">" +
	    				"<img height=\"40\" width=\"40\" src='" + this.previewUrl + "'/></td><td class=\"biaoge_33\"><input id='picture_id_" + this.id + "' class='btn btn-default' pictureid='" + this.id + "' type='button' value='选择' data-dismiss='modal'/></td></tr>");
	    		setDefaultChoose(this.id);
	    	});

	    	$('#pagenum').html('');
	    	for(var i=1;i<=data.maxPageNum;i++){
	    		$('#pagenum').append("<option value='" + i + "' > 第" + i +  "页</option>");
	    	}
	    	$("input[id^=picture_id_]").click(function(){
	    		$('#indexPagePictureNumber').val($(this).attr('pictureid'));
	    		closeProcForImageSelector();
	    	});
	    }


	});
}


function setDefaultChoose(obj){
	if(obj == $('#indexPagePictureNumber').val()){
			$('#picture_id_' + obj).attr("disabled","true");
			$('#picture_id_' + obj).val("已选择");
		}
}
/**
 * 多选
 */
function multiSelectDoAjaxRequestAndSetThePictureTable(){
	$.ajax({
		cache: true,
		dataType: "json",
		type: "get",
		url:"/backEnd/picture/pickPicture",
		data:"sortByIds= " + $('#pictureNumber').val() + "&page=1",
		async: false,
	    error: function(request) {
	    	base.tips("出现未知异常，操作失败!", 'error');
	    },
	    success: function(data) {
	    	$('#pictureTable').html("<tr><th class=\"biaoge_33\">编号</th><th class=\"biaoge_33\">名称</th><th class=\"biaoge_33\">预览</th><th class=\"biaoge_33\">选中</th ></tr>");
	    	$(data.pageContent).each(function(){
	    		$('#pictureTable').append("<tr><td class=\"biaoge_33\"> " +this.id +  "</td><td class=\"biaoge_33\">" +this.name+ "</td><td class=\"biaoge_33\">" +
	    				"<img height=\"40\" width=\"40\" src='" + this.previewUrl + "'/></td><td class=\"biaoge_33\"><input id='picture_id1_" + this.id + "' class='btn btn-default' pictureid1='" + this.id + "' type='checkbox' /></td></tr>");
	    		setDefaultChecked(this.id,$('#picture_id1_' + this.id));
	    	});


	    	$('#pagenum').html('');
	    	for(var i=1;i<=data.maxPageNum;i++){
	    		$('#pagenum').append("<option value='" + i + "' > 第" + i +  "页</option>");
	    	}
	    	$("#pagenum").change(function(){
	    		var queryString = getQueryString();
	    		$.ajax({
	    			cache: true,
	    			dataType: "json",
	    			type: "get",
	    			url:"/backEnd/picture/pickPicture",
	    			data:"sortByIds= " + $('#pictureNumber').val() + "&page=" + $('#pagenum').val() + queryString,
	    			async: false,
	    		    error: function(request) {
	    		    	base.tips("出现未知异常，操作失败!", 'error');
	    		    },
	    		    success: function(data) {
	    		    	$('#pictureTable').html("<tr><th class=\"biaoge_33\">编号</th><th class=\"biaoge_33\">名称</th><th class=\"biaoge_33\">预览</th><th class=\"biaoge_33\">选中</th></tr>");
	    		    	$(data.pageContent).each(function(){
	    		    		$('#pictureTable').append("<tr><td class=\"biaoge_33\"> " +this.id +  "</td><td class=\"biaoge_33\">" +this.name+ "</td><td class=\"biaoge_33\">" +
	    		    				"<img height=\"40\" width=\"40\" src='" + this.previewUrl + "'/></td><td class=\"biaoge_33\"><input id='picture_id1_" + this.id + "' class='btn btn-default' pictureid1='" + this.id + "' type='checkbox' /></td></tr>");

	    		    		setDefaultChecked(this.id,$('#picture_id1_' + this.id));
	    		    	});
	    		    	$("input[id^=picture_id1_]").click(function(){
	    		    		doCheckedOrUnchecked($(this));
	    		    	});
	    		    }
	    		});


	    	});

	    	$("input[id^=picture_id1_]").click(function(){
	    		doCheckedOrUnchecked($(this));
	    	});
	    }
	});
}
/**
 * 多选
 */
function multiSelectSearchBtnAndSetPictureTable(){
	var queryString = getQueryString();
	$.ajax({
		cache: true,
		dataType: "json",
		type: "get",
		url:"/backEnd/picture/pickPicture",
		data: "page=1" + queryString,
		async: false,
	    error: function(request) {
	    	base.tips("出现未知异常，操作失败!", 'error');
	    },
	    success: function(data) {
	    	$('#pictureTable').html("<tr><th class=\"biaoge_33\">编号</th><th class=\"biaoge_33\">名称</th><th class=\"biaoge_33\">预览</th><th class=\"biaoge_33\">选中</th></tr>");
	    	$(data.pageContent).each(function(){
	    		$('#pictureTable').append("<tr><td class=\"biaoge_33\"> " +this.id +  "</td><td class=\"biaoge_33\">" +this.name+ "</td><td class=\"biaoge_33\">" +
	    				"<img height=\"40\" width=\"40\" src='" + this.previewUrl + "'/></td><td class=\"biaoge_33\"><input id='picture_id1_" + this.id + "' class='btn btn-default' pictureid1='" + this.id + "' type='checkbox' /></td></tr>");

	    		setDefaultChecked(this.id,$('#picture_id1_' + this.id));
	    	});

	    	$('#pagenum').html('');
	    	for(var i=1;i<=data.maxPageNum;i++){
	    		$('#pagenum').append("<option value='" + i + "' > 第" + i +  "页</option>");
	    	}
	    	$("input[id^=picture_id1_]").click(function(){
	    		doCheckedOrUnchecked($(this));
	    	});
	    }
	});
}

String.prototype.startWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substr(0,str.length)==str)
	  return true;
	else
	  return false;
	return true;
}

function getQueryString(){
	var queryid =$('#queryid').val();
	var queryname = $('#queryname').val();
	var pictureType = $('#pictureType').val();
	if(queryid == undefined){
		queryid = '';
	}
	if(queryname == undefined){
		queryname = '';
	}
	if(pictureType == undefined){
		pictureType = 'all';
	}
	var queryString  = '&queryId=' +queryid + '&queryName=' + queryname + '&queryType=' + pictureType;
	return queryString;
}

function doCheckedOrUnchecked(obj){
	var pid = $(obj).attr("pictureid1");
	var pnum = $('#pictureNumber').val();
	if($(obj).is(":checked")==true){
		$('#pictureNumber').val(pnum + pid + ",");
	}else{

		if(pnum.startWith(pid + ",")){
			$('#pictureNumber').val(pnum.substring(pid.length + 1,pnum.length));
		}else{
			$('#pictureNumber').val(pnum.replace(','+pid+',' , ','));
		}
	}
}

function setDefaultChecked(id,obj){
	var pnum = $('#pictureNumber').val();
	if(pnum.indexOf(','+ id +',') > -1 || pnum.startWith(id + ',')){
		$(obj).attr("checked","true");
	}
}

//显示
function showProcForImageSelector(flag){

	  if($.browser.msie){
		  message_box_for_image_selector.style.top = (document.documentElement.scrollTop + 4)+"px";
	  }else{
		  message_box_for_image_selector.style.top = (document.body.scrollTop + 4)+"px";
	  }
	  message_box_for_image_selector.style.visibility='visible';

	  if(flag==0){
		  $('#searchBtn').show();
		  $('#searchBtn1').hide();
	  }else if(flag==1){
		  $('#searchBtn').hide();
		  $('#searchBtn1').show();
	  }
	  //创建灰色背景层
	  procbg = document.createElement("div");
	  procbg.setAttribute("id","mybg");
	  procbg.style.background = "#000";
	  procbg.style.width = "100%";
	  procbg.style.height = document.body.scrollHeight+"px";
	  procbg.style.position = "absolute";
	  procbg.style.top = "0";
	  procbg.style.left = "0";
	  procbg.style.zIndex = "500";
	  procbg.style.opacity = "0.3";
	  procbg.style.filter = "Alpha(opacity=30)";

	  //背景层加入页面
	  document.body.appendChild(procbg);
	  document.body.style.overflow = "hidden";
}
//关闭
function closeProcForImageSelector(){
	  message_box_for_image_selector.style.visibility='hidden';
	  procbg.style.visibility = "hidden";
	  document.body.style.overflow = "auto";
}
