<%@page import="com.magic.thai.db.domain.Goods"%>
<%@page import="com.magic.thai.util.PaginationSupport"%>
<%@ page language="java" pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="content">
<!-- 商品列表 -->
<h1>Goods List</h1>
<p >${message}</p>
<br/>
<c:url var="submitUrl" value="/f/goods/list"/>
<form:form action="${submitUrl}" method="POST" commandName="vo">
<table  class="table">
<tr> 
	<td>Name:</td><!-- 商品名称 -->
	<td colspan="3"><form:input path="titleKeyword" class="form-control"/></td>
	<td></td>
	<td></td>
</tr>
<tr> 
	<td>Dept.：</td><!-- 出发地 -->
	<td><form:input path="dept"  class="form-control"/></td>
	<td>Dest.：</td><!-- 目的地 -->
	<td><form:input path="arr"  class="form-control"/></td>
	<td>Status</td><!-- 状态 -->
	<td>
		<form:select path="status"  class="form-control">
			<!-- 全部 -->
			<form:option value="-1" >All</form:option>
			<!-- 新商品待上架 -->
			<form:option value="0">New Goods</form:option>
			<!-- 待审核 -->
			<form:option value="1">Pending Verify</form:option>
			<!-- 审核失败 -->
			<form:option value="2">Verify Failure</form:option>
			<!-- 已上架 -->
			<form:option value="3">Ready For Sale</form:option>
			<!-- 已下架 -->
			<form:option value="4">Out Of List</form:option>
		</form:select>
	</td>
	<!-- 查询 -->
	<td><input type="submit" value="Search" class="btn btn-primary" /></td>
</tr>
</table>
<table  class="table table-striped table-hover table-condensed">
	<thead>
	<tr> 
		<th>ID</th>
		<th>Name</th>
		<th>Dept</th>
		<th>Dest</th>
		<th>Travel Days</th>
		<th>Stock</th>
		<th>Status</th>
		<th>Oper</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${ps.items}" var="goods" varStatus="index">
		<tr>
			<td>${goods.id}</td>
			<td>${goods.title}</td>
			<td>${goods.dept}</td>
			<td>${goods.arrived}</td>
			<td>${goods.travelDays}</td>
			<td>${goods.goodsCount}</td>
			<td>${goods.statusDesc}</td>
			<td>
				<a class="btn btn-success" href="${pageContext.request.contextPath}/f/goods/edit/${goods.id} ">Edit</a>
				<a class="btn btn-warning" href="javascript:del(${goods.id })">Delete</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<script type="text/javascript">
function del(id){
	if(confirm("Are you sure？")) {
		window.location = '${pageContext.request.contextPath}/f/goods/delete/' + id;
	}
	
}
</script>
<%@ include file="../../page.jsp"%>
</form:form>
</div>
