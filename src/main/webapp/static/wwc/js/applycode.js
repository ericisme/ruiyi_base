var arr = new Array();
arr[0] = new Array();
arr[1] = new Array();
arr[2] = new Array();
arr[3] = new Array();
arr[4] = new Array();
arr[5] = new Array();
arr[6] = new Array();
arr[7] = new Array();
arr[8] = new Array();
arr[9] = new Array();
arr[10] = new Array();
arr[11] = new Array();
arr[12] = new Array();
arr[13] = new Array();
arr[14] = new Array();
arr[15] = new Array();
arr[16] = new Array();
arr[17] = new Array();

arr[1][0] = '申請全球發佈大會邀請碼';
arr[1][1] = '返回';
arr[1][2] = '公司';
arr[1][3] = '公司名称';
arr[1][4] = '姓名';
arr[1][5] = '如: 楊過';
arr[1][6] = '職位';
arr[1][7] = '如: 遊戲工程師';
arr[1][8] = '郵箱';
arr[1][9] = '郵箱地址';
arr[1][10] = '聯繫電話';
arr[1][11] = '手機號碼';
arr[1][12] = '驗證碼';
arr[1][13] = '申 請';
arr[1][14] = '遞交成功!';
arr[1][15] = '請檢查以下紅色表格是否有誤, 請填寫正確后再遞交, 謝謝.';
arr[1][16] = '申請邀請碼';
arr[1][17] = '看不清？换一张';

arr[0][0] = 'Apply an invitation number for WWC 2014';
arr[0][1] = 'Back';
arr[0][2] = 'Company';
arr[0][3] = 'company name';
arr[0][4] = 'Full name';
arr[0][5] = 'eg. William Wang';
arr[0][6] = 'Position';
arr[0][7] = 'eg. game developer';
arr[0][8] = 'Email';
arr[0][9] = 'email address';
arr[0][10] = 'Phone';
arr[0][11] = 'phone number';
arr[0][12] = 'Captcha code';
arr[0][13] = 'Apply';
arr[0][14] = 'Application sent successful!';
arr[0][15] = 'Bummer! It looks like there was an error in the information you gave us. Take a look at your sign-up form for more details.';
arr[0][16] = 'apply invitation number';
arr[0][17] = 'Change one';


function formCheck() {

	$('#failed_').addClass('hidden');
	$('#success_').addClass('hidden');

	var flag = true;
	var elements = $('[role=form] input');
	for (var i = 0; i < elements.length; i++) {
		if ($(elements[i]).val() == '') {
			$(elements[i]).addClass('error');
			$('#err').removeClass('hidden');
			flag = false;
		}
	}
	if(flag){
		formSubmit();
	}

	return flag;
}

function formSubmit(){
	$.ajax({
		dataType: "json",
		type: "post",
		url:"/wwc/applycode/save",
		data:$('[role=form]').serialize(),
		async: false,
	    error: function(request) {
	    	$('#failed_').removeClass('hidden');
	    	$('#failed_ p').text(data.err);
	    },
	    success: function(data) {
	    	if(data.code == 1){
	    		$('#success_').removeClass('hidden');
	    		$('#apply_').html('申请成功');
	    		$('#apply_').attr("disabled","disabled");
	    	}else{
	    		$('#failed_ p').text(data.err);
	    		$('#failed_').removeClass('hidden');
	    	}

	    }
	});
}

function switchText(num) {
	$('[name=apply_type]').val(num);
	document.title =  arr[num][16];//操蛋IE，垃圾IE
	$('#header_apply').text(arr[num][0]);
	$('#header_back').text(arr[num][1]);
	$('#err').text(arr[num][15]);
	$('[for=companyname]').text(arr[num][2]);
	$('[name=companyname]').attr("placeholder",arr[num][3]);
	$('[for=fullname]').text(arr[num][4]);
	$('[name=fullname]').attr("placeholder",arr[num][5]);
	$('[for=position]').text(arr[num][6]);
	$('[name=position]').attr("placeholder",arr[num][7]);
	$('[for=email]').text(arr[num][8]);
	$('[name=email]').attr("placeholder",arr[num][9]);
	$('[for=phone]').text(arr[num][10]);
	$('[name=phone]').attr("placeholder",arr[num][11]);
	$('[for=captcha]').text(arr[num][12]);
	$('[name=captcha]').attr("placeholder",arr[num][12]);
	$('#apply_').text(arr[num][13]);
	$('#success').text(arr[num][14]);
	$('#captchaChange').text(arr[num][17]);
}
$().ready(function() {

	$('[role=form] input').click(function() {
		$('[role=form] input').removeClass("error");
		$('#err').addClass('hidden');
		$('#failed_').addClass('hidden');
		$('#success_').addClass('hidden');
	});

	$('.language a').click(function() {
		var rel = $(this).attr("rel");
		$('.language a').removeClass("active");
		$(this).addClass('active');
		switchText(rel);
		setCookie('language', rel, 30);
	});


	$('.language a').bind("mouseover", function() {
		switchText($(this).attr("rel"));
	});
	$('.language a').bind("mouseout", function() {
		var elements = $('.language a ');
		for(var i=0;i<elements.length;i++){
			if($(elements[i]).hasClass("active")){
				switchText($(elements[i]).attr("rel"));
				break;
			}
		}
	});
	$('#captchaChange').click(function(){
		$('#captchaImg').attr("src","/static/captchaCode/wwc/applycode");
	});
	$('#captchaImg').click(function(){
		$('#captchaImg').attr("src","/static/captchaCode/wwc/applycode");
	});

	switchText($('.language .active').attr("rel"));

});
