<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<script type="text/javascript">
var setting = {
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: this.${onClickMethord}
		},
		view: {
			fontCss: function(treeId, node){
				return node.font ? node.font : {};
			},
			nameIsHTML: true
		},
		edit:{
			enable: true,
			drag:{
				isCopy: false,
				isMove: true
			}
		}
	};
var zNodes = null;

$(document).ready(function(){
	var result = $("#jValue").val();
	zNodes = base.json(result);
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
});
	
function closeTree(){
	$("#treeDemoDiv").hide();
}

</script>
<input type="hidden" name="jValue" id="jValue" value="${jValue }"/>
<div class="content_wrap" style="margin:0px;">
	<div class="zTreeDemoBackground left" name="treeDemoDiv" id="treeDemoDiv">
		<ul id="treeDemo" class="ztree" style="margin:1px;"></ul>
	</div>
	<div class="right">
	</div>
</div>
