<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<table>	
		<tr>
			<td >
				<h1>${news.title }</h1>
			</td>			
		</tr>
		<tr>
			<td >
				标签：${news.tag } 
				<br/>
				作者：${news.author } 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				日期：<fmt:formatDate value="${news.addTime}" pattern="yyyy-MM-dd" />
				<br/>
				<!-- ${gameCenterInfo }  -->
			</td>			
		</tr>
		<tr>	
			<td >
			<br/>
			<div style="width:800px;background-color:#fff;margin:auto;">
				${news.content }
			</div>
			</td>
		</tr>
	</table>
