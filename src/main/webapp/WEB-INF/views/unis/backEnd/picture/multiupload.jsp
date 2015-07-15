<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="weizhi">
	你的位置是：商城管理 >> 图片管理 >> 多图片上传
</div>
<link   rel="stylesheet" href="${ctx }/static/Plupload/jquery.plupload.queue/css/jquery.plupload.queue.css" type="text/css" media="screen" />
<script type="text/javascript" src="${ctx }/static/Plupload/plupload.full.min.js"></script>
<script type="text/javascript" src="${ctx }/static/Plupload/jquery.plupload.queue/jquery.plupload.queue.js"></script>
<form id="form" method="post" action="#">

        <div  id="html5_uploader" style="width: 100%; height: 100%"></div>
         <input name="btn01" type="button" style="margin-left: 2em" class="button_1" onclick="base.cancel('head','list','edit',pictureQuery);" value="返 回" />
</form>
<script type="text/javascript">
    $(function() {
        // Setup html5 version
        var setttings = {
            // General settings
            runtimes : 'html5',
            url : '/backEnd/picture/multiupload/save',
            chunk_size : '10mb',
            unique_names : true,

            filters : {
                max_file_size : '10mb',
                mime_types: [
                    {title : "Image files", extensions : "jpg,gif,png,jpeg"},
                    {title : "Zip files", extensions : "zip"}
                ]
            }
            //,resize : {width : 320, height : 240, quality : 90}
        };
        var html5_uploader = $('#html5_uploader').pluploadQueue(setttings);
    });
</script>