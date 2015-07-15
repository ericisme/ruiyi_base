<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

</div><!--/span-->

<div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar11"	role="navigation">
	<div class="list-group">
		<a id="game_center_menu_info"            href="${ctx }/frontEnd/gameCenter/index"   class="list-group-item">游乐场资讯</a> 
		<a id="game_center_menu_reports"         href="${ctx }/frontEnd/gameCenter/reports" class="list-group-item">交易报表</a> 
		<a id="game_center_menu_users"           href="${ctx }/frontEnd/gameCenter/users"   class="list-group-item">账号资讯</a>
		<!-- <a id="game_center_menu_ad"              href="#" class="list-group-item">商业宣传</a>  -->
		<a id="game_center_menu_arcades"         href="${ctx }/frontEnd/gameCenter/arcades" class="list-group-item">机台资讯</a> 
		<a id="game_center_menu_exception_users" href="#" class="list-group-item">账号异常</a>
	</div>
</div>
<script type="text/javascript">
	//控制游乐场管理菜单的active
	$("#"+$("#game_center_menu_active").text()).addClass("active");
</script>
<!--/sidebar-->

</div><!--/row-->
</div> <!-- end container -->