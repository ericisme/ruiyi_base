<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="page" type="cn.unis.utils.MyPageRequest" required="true"%>
<%@ attribute name="url" type="java.lang.String" required="true" %>
<%@ attribute name="id" type="java.lang.String" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	int tabNumbers = page.getStep() * 2 + 1;//default = 5(最多显示的页数)
    int current = page.getPageNo();//当前页数
	int paginationSize =  page.getPageSize();//每页显示记录数
    int begin = 1;//开始
    int end = 1;//结束
    int delta = page.getTotalPages() - current;//当前页与最后一页差
    if(page.getTotalPages() > tabNumbers){
    	if(current < page.getStep() + 1 ){//如果少于一半
       	 end = tabNumbers ;
       	}
    	else if(delta < page.getStep()){
    		begin = current - (page.getStep() * 2 - delta);
    		end = page.getTotalPages();
    	}
       else{
       	 begin = Math.max(1, current -  page.getStep());
       	 end = Math.min( current + page.getStep(), page.getTotalPages());
       }
    }else{
    	begin = 1;
    	end = page.getTotalPages();
    }
    request.setAttribute("current", current);
    request.setAttribute("begin", begin);
    request.setAttribute("end", end);
%>
<div class="text-center">
	<ul class="pagination pagination-lg">
		<% if (page.isPrePage()) {%>
					<li><a  href="${url }?pageNo=${current-1}"><<</a></li>
		<%} else {%> 
					<li class="disabled"><a> << </a></li>
		<%} %>

		 
		<c:forEach var="i" begin="${begin}" end="${end}">
			<c:choose>
				<c:when test="${i == current}">
					<li class="active"><a id="${id }${i}" href="${url }?pageNo=${i}">${i}</a></li>
				</c:when>
				<c:otherwise>
					<li><a id="${id }${i}" href="${url }?pageNo=${i}">${i}</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>

		<% if (page.isNextPage()) {%>
				<li><a  href="${url }?pageNo=${current+1}">>></a></li>
		<%} else {%>
					<li class="disabled"><a> >> </a></li>
		<%} %>

	 </ul>
</div>

