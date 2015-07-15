$('#pickup_yes').on('ifChecked', function(event){
  $('#flight_no').fadeIn(150);
});
$('#pickup_no').on('ifChecked', function(event){
  $('#flight_no').fadeOut(150);
});

$('#join_yes').on('ifChecked', function(event){
  $('#send_us').fadeIn(150);
});
$('#join_no').on('ifChecked', function(event){
  $('#send_us').fadeOut(150);
});

function englishFormCheck(){
	var flag = 0; //  错误标记
	if($('[name=companyname]').val() == ''){
		$('[name=companyname]').addClass("error"); //公司名称为空
		flag++;
	}
	if($('[name=arrvialdate]').val() == ''){
		$('[name=arrvialdate]').addClass("error");//到达日期未选择
		flag++;
	}
	if($('[name=message]').text().length > 512){
		$('[name=message]').addClass("error"); // 建议填写超长
		flag++;
	}
	var elems = $(".applicant"); //邀请嘉宾的list
	var len = elems.length;

	if(len == 1){
		var tmp1 = englishApplicantCheck(".applicant:first [name=firstName]",".applicant:first [name=lastName]",".applicant:first [name=app_email]",".applicant:first [name=confirmEmail]");
		flag = flag + tmp1;
	}else if(len == 2){
		var tmp1 = englishApplicantCheck(".applicant:first [name=firstName]",".applicant:first [name=lastName]",".applicant:first [name=app_email]",".applicant:first [name=confirmEmail]");
		var tmp2 = englishApplicantCheck(".applicant:last [name=firstName]",".applicant:last [name=lastName]",".applicant:last [name=app_email]",".applicant:last [name=confirmEmail]");
		flag = flag + tmp1 + tmp2;
	}else if(len == 3){
		var tmp1 = englishApplicantCheck(".applicant:first [name=firstName]",".applicant:first [name=lastName]",".applicant:first [name=app_email]",".applicant:first [name=confirmEmail]");
		var tmp2 = englishApplicantCheck(".applicant:eq(1) [name=firstName]",".applicant:eq(1) [name=lastName]",".applicant:eq(1) [name=app_email]",".applicant:eq(1) [name=confirmEmail]");
		var tmp3 = englishApplicantCheck(".applicant:last [name=firstName]",".applicant:last [name=lastName]",".applicant:last [name=app_email]",".applicant:last [name=confirmEmail]");
		flag = flag + tmp1 + tmp2 + tmp3;
	}

	if(flag > 0){
		$('#err').removeClass("hidden");
		document.getElementsByTagName('body')[0].scrollTop = 0;
	}
	return flag==0;
}
/**
 * 必要字段检查
 */
function englishApplicantCheck(firstName,lastName,email,confirmEmail){
	var flag = 0;
	if($(firstName).val() == ''){
		$(firstName).addClass('error');
		flag++;
	}
	if($(lastName).val() == ''){
		$(lastName).addClass('error');
		flag++;
	}
	if($(email).val() =='' ){
		$(email).addClass('error');
		flag++;
	}
	if(!isValidEmail($(email).val())){
		$(email).addClass('error');
		flag++;
		console.log($(email).val() + '===========' +isValidEmail($(email).val()) );
	}
	if($(confirmEmail).val() ==''){
		$(confirmEmail).addClass('error');
		flag++;
	}
	if(!isValidEmail($(confirmEmail).val())){
		$(confirmEmail).addClass('error');
		flag++;
		console.log($(confirmEmail).val() + '===========' +isValidEmail($(confirmEmail).val()) );
	}

	if(($(email).val())!=($(confirmEmail).val())){
		$(email).addClass('error');
		$(confirmEmail).addClass('error');
		flag++;
	}
	return flag;
}


function chineseFormCheck(){
	var flag = 0 ;
	if($('[name=companyname]').val() == ''){
		$('[name=companyname]').addClass("error");
		flag++;
	}
	if($('[name=arrvialdate]').val() == ''){
		$('[name=arrvialdate]').addClass("error");
		flag++;
	}
	if($('[name=message]').text().length > 512){
		$('[name=message]').addClass("error");
		flag++;
	}
	var elems = $(".applicant");

	if(elems.length == 1){
		var tmp1 = 0;
		tmp1 = chineseApplicantCheck(".applicant:first [name=app_fullname]",".applicant:first [name=app_mobilenumber]");
		flag = tmp1 + flag;
	}
	else if(elems.length == 2){
		var tmp1 = 0;
		var tmp2 = 0 ;
		tmp1 =  chineseApplicantCheck(".applicant:first [name=app_fullname]",".applicant:first [name=app_mobilenumber]");
		tmp2 =	chineseApplicantCheck(".applicant:last [name=app_fullname]",".applicant:last [name=app_mobilenumber]");
		flag = tmp1 + tmp2 + flag;
	}
	else if(elems.length == 3){
		var tmp1 = 0;
		var tmp2 = 0;
		var tmp3 = 0;
		tmp1 = chineseApplicantCheck(".applicant:first [name=app_fullname]",".applicant:first [name=app_mobilenumber]");
		tmp2 = chineseApplicantCheck(".applicant:eq(1) [name=app_fullname]",".applicant:eq(1) [name=app_mobilenumber]") ;
		tmp3 = chineseApplicantCheck(".applicant:last [name=app_fullname]",".applicant:last [name=app_mobilenumber]");
		flag = tmp1 + tmp2 + tmp3 + flag;
	}

	if(flag > 0){
		$('#err').removeClass("hidden");
		document.getElementsByTagName('body')[0].scrollTop = 0;
	}
	return flag==0;
}
function chineseApplicantCheck(app_fullname,app_mobilenumber){
	var flag = 0;
	if($(app_fullname).val()  == ''){
		$(app_fullname).addClass("error");
		flag++;
	}
	if($(app_mobilenumber).val() == ''){
		$(app_mobilenumber).addClass("error");
		flag++;
	}
	return flag;
}
//增加嘉宾
function addApplicant(){
    var elems = $(".applicant");

    if(elems.length < 3){
    	$("#group_applicant").append($(".applicant").prop('outerHTML'));
        $(".applicant:last h4:first").html("");
        $(".applicant:last input").val("");
        $(".applicant:last input").removeClass("error");
        $(".applicant:last option").removeAttr("selected");
        $(".applicant:last option").removeClass("error");
    }
    if(elems.length==2){
    	$("#add_applicant_div").addClass('hidden');
    }

    $('[role=form] input,textarea').click(function(){
		$(this).removeClass('error');
	});
}
//关闭嘉宾
function deleteApplicant(btn){
	var elems = $(".applicant");
	 if(elems.length>1){
	    	$(btn).parent().remove();
	 }
	 if(elems.length<=3){
		 $("#add_applicant_div").removeClass('hidden');
	 }
}
function isValidEmail(sText) {
	//正则可能出问题
	 var reEmail = /^[0-9A-Za-z\.\-_]+\@((([0-9A-Za-z\-_]+\.)+[A-Za-z]{2,4})|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))$/;
    return reEmail.test(sText.toLocaleLowerCase());
}

function nameCard(input){
	$(input).parent().parent().parent().parent().children().first().html(input.value.substr(0,30));
}

$('input, textarea').placeholder();