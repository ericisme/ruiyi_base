<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta charset="GBK">
<title>支付宝 - 网上支付 安全快速！支付宝 - 网上支付 安全快速！</title>
<meta name="description" content="中国最大的第三方电子支付服务提供商">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<link rel="shortcut icon"
	href="https://i.alipayobjects.com/e/201310/1HFsvDt0EB.ico"
	type="image/x-icon">
<link charset="utf-8" rel="stylesheet"
	href="https://a.alipayobjects.com/cashier/cashier.main-2.22.css"
	media="all">
<noscript>&lt;link charset="utf-8" rel="stylesheet"
	href="https://a.alipayobjects.com/cashier/cashier.icon-1.3.css"
	media="all" /&gt;</noscript>

<link href="${ctx }/static/artDialog/apop.css">

<link href="https://a.alipayobjects.com/" rel="dns-prefetch">
<link href="https://app.alipay.com/" rel="dns-prefetch">
<link href="https://my.alipay.com/" rel="dns-prefetch">
<link href="https://lab.alipay.com/" rel="dns-prefetch">
<link href="https://cashier.alipay.com/" rel="dns-prefetch">
<link href="https://financeprod.alipay.com/" rel="dns-prefetch">
<link href="https://shenghuo.alipay.com/" rel="dns-prefetch">

<style>
.icon-gray {
	filter: url("https://cashier.alipay.com:443/filters.svg#grayscale");
	filter: gray\9;
	-webkit-filter: grayscale(100%);
}

.repair,.repair2,.repair3,.restricted {
	_display: block;
}
</style>

<style>
@font-face {
	font-family: "rei";
	src: url("https://i.alipayobjects.com/common/fonts/rei.eot?20131015");
	/* IE9 */
	src:
		url("https://i.alipayobjects.com/common/fonts/rei.eot?20131015#iefix")
		format("embedded-opentype"), /* IE6-IE8 */
		url("https://i.alipayobjects.com/common/fonts/rei.woff?20131015")
		format("woff"), /* chrome 6+、firefox 3.6+、Safari5.1+、Opera 11+ */
		url("https://i.alipayobjects.com/common/fonts/rei.ttf?20131015")
		format("truetype"),
		/* chrome、firefox、opera、Safari, Android, iOS 4.2+ */
		url("https://i.alipayobjects.com/common/fonts/rei.svg?20131015#rei")
		format("svg"); /* iOS 4.1- */
}

.iconfont {
	font-family: "rei";
	font-style: normal;
	font-weight: normal;
	cursor: default;
	-webkit-font-smoothing: antialiased;
}
</style>
<style type="text/css" class="iconStyle">
.ABC,.BJBANK,.BJRCB,.BOC,.CCB,.CDCB,.CEB,.CIB,.CITIC,.CMB,.CMBC,.COMM,.FDB,.GDB,.HZCB,.ICBC,.NBBANK,.PSBC,.SDB,.SHBANK,.SHRCB,.SPABANK,.SPDB,.WZCB
	{
	text-indent: -9999px;
	background-image:
		url("https://i.alipayobjects.com:443/combo.png?d=cashier&t=ABC,BJBANK,BJRCB,BOC,CCB,CDCB,CEB,CIB,CITIC,CMB,CMBC,COMM,FDB,GDB,HZCB,ICBC,NBBANK,PSBC,SDB,SHBANK,SHRCB,SPABANK,SPDB,WZCB&stamp=1383840000000");
}

.ABC {
	background-position: 0px -0px;
}

.BJBANK {
	background-position: 0px -36px;
}

.BJRCB {
	background-position: 0px -72px;
}

.BOC {
	background-position: 0px -108px;
}

.CCB {
	background-position: 0px -144px;
}

.CDCB {
	background-position: 0px -180px;
}

.CEB {
	background-position: 0px -216px;
}

.CIB {
	background-position: 0px -252px;
}

.CITIC {
	background-position: 0px -288px;
}

.CMB {
	background-position: 0px -324px;
}

.CMBC {
	background-position: 0px -360px;
}

.COMM {
	background-position: 0px -396px;
}

.FDB {
	background-position: 0px -432px;
}

.GDB {
	background-position: 0px -468px;
}

.HZCB {
	background-position: 0px -504px;
}

.ICBC {
	background-position: 0px -540px;
}

.NBBANK {
	background-position: 0px -576px;
}

.PSBC {
	background-position: 0px -612px;
}

.SDB {
	background-position: 0px -648px;
}

.SHBANK {
	background-position: 0px -684px;
}

.SHRCB {
	background-position: 0px -720px;
}

.SPABANK {
	background-position: 0px -756px;
}

.SPDB {
	background-position: 0px -792px;
}

.WZCB {
	background-position: 0px -828px;
}
</style>
<link rel="stylesheet" type="text/css" href="./apop.css">
</head>
<body>

	<style>
.ui-list-icons .healthy,.ui-list-icons .healthy-normal {
	display: block !important;
	right: auto;
	top: -18px;
	left: -1px;
	margin: 0;
	width: auto;
	color: #fff;
	padding: 1px 2px 0;
	line-height: 14px;
}

.ui-list-icons .healthy-normal {
	position: absolute;
	background: #46b900;
	width: 28px;
	height: 18px;
	color: #fff;
	text-align: center;
}

.ui-list-icons .healthy .icon,.ui-list-icons .healthy-normal .icon {
	display: none;
}

.ui-list-icons .busy {
	background: #ff9900;
}

.ui-list-icons .repair2 {
	background: #ff3333;
}

.ui-list-icons .repair2 {
	background: #999999;
}

.ui-list-icons .healthy-ok {
	background: #46b900;
}

.long-logo .ui-list-icons .icon-gray {
	filter: none;
}

.long-logo .ui-list-icons .icon-gray  span.icon {
	filter: url("https://cashier.alipay.com:443/filters.svg#grayscale");
	filter: gray\9;
}
</style>

	<div class="long-logo">
		<div id="container" class="xbox ui-form">
			<h2>支付宝的合作银行</h2>
			<form name="bankCardForm" method="POST" id="bankCardForm"
				action="https://cashier.alipay.com/standard/payment/bankCardForm.htm"
				target="_top">
				<input type="hidden" id="orderId" name="orderId"
					value="1121446e94b6d140bae67b6012303392"> <input
					type="hidden" id="isCompositeWithBalance"
					name="isCompositeWithBalance" value="N">

				<ul class="ui-list-icons ui-four-icons fn-clear cashier-bank"
					id="J-chooseBankList" style="padding-top: 10px;">


					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-icbc105-1"
						value="ICBCicbc105_PAYMENT_DEBIT_EBANK_XBOX_MODEL"
						checked="checked"> <label
						class="icon-box limited-coupon  " for="J-b2c_ebank-icbc105-1"
						data-channel="biz-channelType(B2C_EBANK)-ICBC-中国工商银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon ICBC" title="中国工商银行"></span> <span
							class="bank-name">中国工商银行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-ccb103-2"
						value="CCBccb103_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2c_ebank-ccb103-2"
						data-channel="biz-channelType(B2C_EBANK)-CCB-中国建设银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon CCB" title="中国建设银行"></span> <span
							class="bank-name">中国建设银行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-abc101-3"
						value="ABCabc101_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2c_ebank-abc101-3"
						data-channel="biz-channelType(B2C_EBANK)-ABC-中国农业银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon ABC" title="中国农业银行"></span> <span
							class="bank-name">中国农业银行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-psbc102-4"
						value="PSBCpsbc102_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2c_ebank-psbc102-4"
						data-channel="biz-channelType(B2C_EBANK)-PSBC-中国邮政储蓄银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon PSBC" title="中国邮政储蓄银行"></span> <span
							class="bank-name">中国邮政储蓄银行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-comm102-5"
						value="COMMcomm102_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2c_ebank-comm102-5"
						data-channel="biz-channelType(B2C_EBANK)-COMM-交通银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon COMM" title="交通银行"></span> <span
							class="bank-name">交通银行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-cmb103-6"
						value="CMBcmb103_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2c_ebank-cmb103-6"
						data-channel="biz-channelType(B2C_EBANK)-CMB-招商银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon CMB" title="招商银行"></span> <span
							class="bank-name">招商银行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-boc102-7"
						value="BOCboc102_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2c_ebank-boc102-7"
						data-channel="biz-channelType(B2C_EBANK)-BOC-中国银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon BOC" title="中国银行"></span> <span
							class="bank-name">中国银行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-ceb102-8"
						value="CEBceb102_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2c_ebank-ceb102-8"
						data-channel="biz-channelType(B2C_EBANK)-CEB-中国光大银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon CEB" title="中国光大银行"></span> <span
							class="bank-name">中国光大银行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-citic104-9"
						value="CITICcitic104_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2c_ebank-citic104-9"
						data-channel="biz-channelType(B2C_EBANK)-CITIC-中信银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon CITIC" title="中信银行"></span> <span
							class="bank-name">中信银行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-sdb102-10"
						value="SDBsdb102_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2c_ebank-sdb102-10"
						data-channel="biz-channelType(B2C_EBANK)-SDB-深圳发展银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon SDB" title="深圳发展银行"></span> <span
							class="bank-name">深圳发展银行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-spdb103-11"
						value="SPDBspdb103_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2c_ebank-spdb103-11"
						data-channel="biz-channelType(B2C_EBANK)-SPDB-浦发银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon SPDB" title="浦发银行"></span> <span
							class="bank-name">浦发银行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-cmbc101-12"
						value="CMBCcmbc101_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2c_ebank-cmbc101-12"
						data-channel="biz-channelType(B2C_EBANK)-CMBC-中国民生银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon CMBC" title="中国民生银行"></span> <span
							class="bank-name">中国民生银行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-cib102-13"
						value="CIBcib102_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2c_ebank-cib102-13"
						data-channel="biz-channelType(B2C_EBANK)-CIB-兴业银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon CIB" title="兴业银行"></span> <span
							class="bank-name">兴业银行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-spabank101-14"
						value="SPABANKspabank101_PAYMENT_DEBIT_EBANK_XBOX_MODEL">
						<label class="icon-box limited-coupon "
						for="J-b2c_ebank-spabank101-14"
						data-channel="biz-channelType(B2C_EBANK)-SPABANK-深圳平安银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon SPABANK" title="深圳平安银行"></span> <span
							class="bank-name">深圳平安银行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-gdb102-15"
						value="GDBgdb102_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2c_ebank-gdb102-15"
						data-channel="biz-channelType(B2C_EBANK)-GDB-广发银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon GDB" title="广发银行"></span> <span
							class="bank-name">广发银行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-shrcb101-16"
						value="SHRCBshrcb101_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2c_ebank-shrcb101-16"
						data-channel="biz-channelType(B2C_EBANK)-SHRCB-上海农商银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon SHRCB" title="上海农商银行"></span> <span
							class="bank-name">上海农商银行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-shbank102-17"
						value="SHBANKshbank102_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2c_ebank-shbank102-17"
						data-channel="biz-channelType(B2C_EBANK)-SHBANK-上海银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon SHBANK" title="上海银行"></span> <span
							class="bank-name">上海银行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-nbbank102-18"
						value="NBBANKnbbank102_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2c_ebank-nbbank102-18"
						data-channel="biz-channelType(B2C_EBANK)-NBBANK-宁波银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon NBBANK" title="宁波银行"></span> <span
							class="bank-name">宁波银行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-hzcb102-19"
						value="HZCBhzcb102_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2c_ebank-hzcb102-19"
						data-channel="biz-channelType(B2C_EBANK)-HZCB-杭州银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon HZCB" title="杭州银行"></span> <span
							class="bank-name">杭州银行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-bjbank101-20"
						value="BJBANKbjbank101_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2c_ebank-bjbank101-20"
						data-channel="biz-channelType(B2C_EBANK)-BJBANK-北京银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon BJBANK" title="北京银行"></span> <span
							class="bank-name">北京银行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-bjrcb101-21"
						value="BJRCBbjrcb101_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2c_ebank-bjrcb101-21"
						data-channel="biz-channelType(B2C_EBANK)-BJRCB-北京农商行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon BJRCB" title="北京农商行"></span> <span
							class="bank-name">北京农商行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-fdb101-22"
						value="FDBfdb101_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2c_ebank-fdb101-22"
						data-channel="biz-channelType(B2C_EBANK)-FDB-富滇银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon FDB" title="富滇银行"></span> <span
							class="bank-name">富滇银行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-wzcb101-23"
						value="WZCBwzcb101_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2c_ebank-wzcb101-23"
						data-channel="biz-channelType(B2C_EBANK)-WZCB-温州银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon WZCB" title="温州银行"></span> <span
							class="bank-name">温州银行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank "><input type="radio"
						name="channelToken" id="J-b2c_ebank-cdcb101-24"
						value="CDCBcdcb101_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon  current"
						for="J-b2c_ebank-cdcb101-24"
						data-channel="biz-channelType(B2C_EBANK)-CDCB-成都银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon CDCB" title="成都银行"></span> <span
							class="bank-name">成都银行</span> <!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm -->
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank-qy "><input type="radio"
						name="channelToken" id="J-b2b_ebank-icbc102-25"
						value="ICBCicbc102_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon  " for="J-b2b_ebank-icbc102-25"
						data-channel="biz-channelType(B2B_EBANK)-ICBC-中国工商银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon ICBC" title="中国工商银行"></span> <span
							class="bank-name">中国工商银行</span> <em class="icon-info qiye">企业</em>
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm --> <!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank-qy "><input type="radio"
						name="channelToken" id="J-b2b_ebank-ccb104-26"
						value="CCBccb104_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2b_ebank-ccb104-26"
						data-channel="biz-channelType(B2B_EBANK)-CCB-中国建设银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon CCB" title="中国建设银行"></span> <span
							class="bank-name">中国建设银行</span> <em class="icon-info qiye">企业</em>
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm --> <!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank-qy "><input type="radio"
						name="channelToken" id="J-b2b_ebank-abc102-27"
						value="ABCabc102_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2b_ebank-abc102-27"
						data-channel="biz-channelType(B2B_EBANK)-ABC-中国农业银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon ABC" title="中国农业银行"></span> <span
							class="bank-name">中国农业银行</span> <em class="icon-info qiye">企业</em>
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm --> <!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank-qy "><input type="radio"
						name="channelToken" id="J-b2b_ebank-cmb102-28"
						value="CMBcmb102_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2b_ebank-cmb102-28"
						data-channel="biz-channelType(B2B_EBANK)-CMB-招商银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon CMB" title="招商银行"></span> <span
							class="bank-name">招商银行</span> <em class="icon-info qiye">企业</em>
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm --> <!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank-qy "><input type="radio"
						name="channelToken" id="J-b2b_ebank-boc104-29"
						value="BOCboc104_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2b_ebank-boc104-29"
						data-channel="biz-channelType(B2B_EBANK)-BOC-中国银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon BOC" title="中国银行"></span> <span
							class="bank-name">中国银行</span> <em class="icon-info qiye">企业</em>
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm --> <!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>

					<li class="cashier-bank-qy "><input type="radio"
						name="channelToken" id="J-b2b_ebank-spdb102-30"
						value="SPDBspdb102_PAYMENT_DEBIT_EBANK_XBOX_MODEL"> <label
						class="icon-box limited-coupon " for="J-b2b_ebank-spdb102-30"
						data-channel="biz-channelType(B2B_EBANK)-SPDB-浦发银行-type(unsave)">
							<span class="icon-box-sparkling" bbd="false"> </span> <span
							class="false icon SPDB" title="浦发银行"></span> <span
							class="bank-name">浦发银行</span> <em class="icon-info qiye">企业</em>
							<!-- CMS:统一收银台/帮助/渠道健康度提示文案开始:cashier/help/healthyText.vm --> <!-- CMS:统一收银台/帮助/渠道健康度提示文案结束:cashier/help/healthyText.vm -->
					</label></li>
				</ul>
				<cite class="ui-list-tip"></cite>
				<p class="ui-btn ui-btn-ok">
					<input type="button" id="finished" class="ui-btn-text" value="下一步"
						seed="next-step-btn">
				</p>
			</form>
		</div>
	</div>

</body>
</html>
