<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" pageEncoding="UTF-8"%>  
<table>
	<tr>
		<td>
			<c:if test="${ps.currentPage eq 1 }">
				首页
				上一页
			</c:if>
			<c:if test="${ps.currentPage ne 1 }">
				<a href="javascript:homePage()">首页</a>
				<a href="javascript:prevPage(${s })">上一页</a>
			</c:if>
			<c:forEach var="s"  begin="1" end="${ps.totalPageCount }">
				<c:if test="${ps.currentPage eq s }">
					[ ${s } ]
				</c:if>
				<c:if test="${ps.currentPage ne s }">
					<a href="javascript:page(${s })">[ ${s } ]</a>
				</c:if>
			</c:forEach>
			<c:choose> 
			   <c:when test='${ps.totalPageCount eq ps.currentPage}'> 
			   		下一页
					末页
			   </c:when> 
			   <c:otherwise>
					<a href="javascript:nextPage()">下一页</a>
					<a href="javascript:lastPage()">末页</a>
			   </c:otherwise> 
			</c:choose> 
			
			共 ${ps.totalPageCount } 页   ${ps.totalCount } 条记录
		</td>
	</tr>
</table>
<input id="page" name="vo.page" type="hidden" value="${ps.currentPage}"/>
<input id="totalPageCount" name="totalPageCount" type="hidden" value="${ps.totalPageCount}"/>
<script>
	function page(pageIndex) {
		$("#page").val(pageIndex);
		$("form").submit();
	}
	
	function homePage() {
		$("#page").val(1);
		$("form").submit();
	}
	
	function lastPage() {
		$("#page").val($("#totalPageCount"));
		$("form").submit();
	}
	
	function prevPage() {
		$("#page").val(parseInt($("#page").val(pageIndex)) - 1);
		$("form").submit();
	}
	
	function nextPage() {
		$("#page").val(parseInt($("#page").val(pageIndex)) + 1);
		$("form").submit();
	}
</script>