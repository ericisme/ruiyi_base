function save() {
	var discount = $("#discount").val();
	var effectiveBegin = $("#effectiveBegin").val();
	var effectiveEnd = $("#effectiveEnd").val();
	if (discount == "" || effectiveBegin == ""  || effectiveEnd=="") {
		base.tips("请完善支付宝充值折扣信息!", 'warning', 1500);
		return false;
	}
	
	if (isNaN(discount)) {
		base.tips("折扣必须为数字!", 'warning', 1500);
		return false;
	}
	if (!(0 < (discount) && (discount) <= 1)) {
		base.tips("折扣必须大于0少于等于1", 'warning', 1500);
		return false;
	}
	
	
	base.processStatus(1, 'save', 'process');
	
	$.ajax({
		cache: true,
		type: "POST",
		url:"/backEnd/alipay/discountsave",
		data:$('#mainForm').serialize(),// 你的formid
		async: false,
	    error: function(request) {
	    	base.tips("出现未知异常，操作失败!", 'error');
	    },
	    success: function(data) {
			if (data == "success") {
			base.tips("保存成功!", 'success', 1500);
			setTimeout(function() {
				window.location.href = '/backEnd/alipay/discountsetting';
			}, 1000)
			} else {
				base.tips("出现未知异常，操作失败!", 'error');
			}
	    }
	});
}
