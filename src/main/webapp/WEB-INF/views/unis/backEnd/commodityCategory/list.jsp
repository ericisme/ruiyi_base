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
			onClick: this.onClick
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

</script>
<input type="hidden" name="jValue" id="jValue" value="${jValue }"/>
<div class="content_wrap" style="margin-left:50px;">
	<div class="zTreeDemoBackground left">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	<div class="right">
		<table>
			<tr>
				<td id="showDiv" style="display:none;">
						<ul id="log" class="log" style="margin-top:180px;">
							<li style="margin-top:10px;">
								<shiro:user>
									<shiro:hasPermission name="commodityCategory:setup">
									<span id="setupNode">
										<input type="button" value="禁 用" onclick="setup(0)" id="unable" style="display:none;" />
										<input type="button" value="启 用" onclick="setup(1)" id="able"   style="display:none;"   />
									</span>
									</shiro:hasPermission>
									<shiro:hasPermission name="commodityCategory:delete">
										<input type="button" value="删 除" onclick="delNode();" id="deleteNode"/>
									</shiro:hasPermission>
									<shiro:hasPermission name="commodityCategory:save">
										<input type="button" value="增加同级" onclick="addNode(1);" id="addEqualNode"/>
										<input type="button" value="增加下级" onclick="addNode(2);" id="addLowerNode"/>
										<input type="button" value="保 存" onclick="save(1);"       id="saveNode"/>
									</shiro:hasPermission>
								
								</shiro:user>
							</li>
							<li>
								<form name="form1" id="form1">
									<input type="hidden" name="id" id="id1" value=""/>
									<input type="hidden" name="pid" id="pid1" value=""/>
									<input type="hidden" name="levell" id="level" value=""/>
									<table style="width:96%;margin-top:15px;" cellpadding="0" cellspacing="0">
										<!-- <tr>
											<td style="width:25%;text-align:right;padding-right:5px;">部门级别：</td>
											<td><span id="bmLevel1"></span></td>
										</tr> -->
										<tr>
											<td style="text-align:right;padding-right:5px;">商品类别名称：</td>
											<td><input type="text" name="name" id="name1" maxlength="33"/></td>
										</tr>
										<tr>										<tr>
											<td style="text-align:right;padding-right:5px;">描述(非必填)：</td>
											<td><input type="text" name="description" id="description1"  maxlength="33"/></td>
										</tr>
										<!-- <tr id="reqUrl">
											<td style="text-align:right;padding-right:5px;">请求地址：</td>
											<td><input type="text" name="menuUrl" id="menuUrl1"/></td>
										</tr> -->
										<tr>
											<td style="text-align:right;padding-right:5px;">同级排序：</td>
											<td><input type="text" name="orderNum" id="orderNum1" onkeydown="base.digitOnly();"/></td>
										</tr>
										<!-- <tr id="bmgly_node">+
											<td style="text-align:right;padding-right:5px;">部门管理员：</td>
											<td>
												<select name="bmgly_id" id="bmgly_id1">
													<option value="-1">---请选择---</option>
													${user_listOptionHtml}
												</select>
											</td>
										</tr> -->
										<tr>
											<td style="text-align:right;padding-right:5px;">状态：</td>
											<td><span id="status1"></span></td>
										</tr>
									</table>
								</form>
							</li>
						</ul>
				</td>
				<td style="padding:45px;display:none;" id="optDiv">
					<div id="optStep"></div>
					<img alt="" src="${ctx }/static/jquery-zTree-v3.4/css/zTreeStyle/img/diy/arrowRight.png" style="width:100px;margin-top:30px;">
				</td>
				<td id="addDiv" style="display:none;">
					<ul id="log" class="log" style="margin-top:180px;">
							<li style="margin-top:10px;">
								<shiro:user>
									<shiro:hasPermission name="commodityCategory:save">
										<input type="button" value="保 存" onclick="save(2);"/>
									</shiro:hasPermission>
								</shiro:user>
							</li>
							<li>
								<form name="form2" id="form2">
									<input type="hidden" name="id" id="id2" value="0"/>
									<input type="hidden" name="pid" id="pid2" />
									<table style="width:96%;margin-top:15px;" cellpadding="0" cellspacing="0">
										<!-- <tr>
											<td style="width:25%;text-align:right;padding-right:5px;">父级菜单：</td>
											<td><span id="level2"></span></td>
										</tr>
										 -->
										<!-- <tr>
											<td style="width:25%;text-align:right;padding-right:5px;">当前级别：</td>
											<td><span id="currLevel"></span></td>
										</tr> -->
										<tr>
											<td style="text-align:right;padding-right:5px;">商品类别名称：</td>
											<td><input type="text" name="name" id="name2"  maxlength="33"/></td>
										</tr>
										<tr>										<tr>
											<td style="text-align:right;padding-right:5px;">描述(非必填)：</td>
											<td><input type="text" name="description" id="description2"  maxlength="33"/></td>
										</tr>
										<!--<tr>
											<td style="text-align:right;padding-right:5px;">请求地址：</td>
											<td><input type="text" name="menuUrl" id="menuUrl2"/>&nbsp;&nbsp;<font color=red>默认根路径</font></td>
										</tr>-->
										<tr>
											<td style="text-align:right;padding-right:5px;">同级排序：</td>
											<td><input type="text" name="orderNum" id="orderNum2" onkeydown="base.digitOnly();"/></td>
										</tr>
										<!-- <tr>
											<td style="text-align:right;padding-right:5px;">部门管理员：</td>
											<td>
												<select name="bmgly_id" id="bmgly_id2">
													<option value="-1">---请选择---</option>
													${user_listOptionHtml}
												</select>
											</td>
										</tr> -->
										<tr>
											<td style="text-align:right;padding-right:5px;">状态：</td>
											<td>		
												<select name="status" id="status2">
													<option value="-1">请选择</option>
													<option value="1" selected>正常</option>
													<option value="0">停用</option>
												</select>
											</td>
										</tr>
									</table>
								</form>
							</li>
					</ul>
				</td>
			</tr>
		</table>
	</div>
</div>
