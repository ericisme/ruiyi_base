<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

</div><!-- end col-xs-12 col-sm-9-->

<div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar11"	role="navigation">
	<div class="list-group">
		<a id="account_center_menu_info"           href="${ctx }/frontEnd/accountCenter/accountInfo" class="list-group-item">个人资料</a> 
		<a id="account_center_menu_repassword"     href="${ctx }/frontEnd/accountCenter/changePassord" class="list-group-item">修改密码</a>
		<!-- <a id="account_center_menu_wallet_info"    href="${ctx }/frontEnd/accountCenter/walletInfo"  class="list-group-item">游戏钱包</a> -->
		<!-- <a id="account_center_menu_exchange"       href="${ctx }/frontEnd/accountCenter/exchange"	 class="list-group-item">游戏币兑换</a>  -->
		<a id="account_center_menu_charge_record"  href="${ctx }/frontEnd/accountCenter/chargeRecordIndex"    class="list-group-item">充值/兑换记录</a>
		<a id="account_center_menu_mygift"         href="${ctx }/frontEnd/accountCenter/mygift"    class="list-group-item">我的礼品</a>
	</div>
</div>
<script type="text/javascript">
	//控制会员中心菜单的active
	$("#"+$("#account_center_menu_active").text()).addClass("active");
</script>
<!--/sidebar-->

</div><!--end row row-offcanvas row-offcanvas-right-->
</div> <!-- end container -->