<%@page import="com.magic.thai.db.domain.Goods"%>
<%@page import="com.magic.thai.util.PaginationSupport"%>
<%@ page language="java" pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="content">
<!-- 商品列表 -->
<h1>รายชื่อสินค้า</h1>
<p >${message}</p>
<br/>
<c:url var="submitUrl" value="/f/goods/list"/>
<form:form action="${submitUrl}" method="POST" commandName="vo">
<table  class="table">
<tr> 
	<td>ชื่อสินค้า：</td><!-- 商品名称 -->
	<td colspan="3"><form:input path="titleKeyword" class="form-control"/></td>
	<td></td>
	<td></td>
</tr>
<tr> 
	<td>ออกเดินทาง：</td><!-- 出发地 -->
	<td><form:input path="dept"  class="form-control"/></td>
	<td>ปลายทาง：</td><!-- 目的地 -->
	<td><form:input path="arr"  class="form-control"/></td>
	<td>สถานะ</td><!-- 状态 -->
	<td>
		<form:select path="status"  class="form-control">
			<!-- 全部 -->
			<form:option value="-1" >ทั้งหมด</form:option>
			<!-- 新商品待上架 -->
			<form:option value="0">ผลิตภัณฑ์ใหม่ที่พร้อมวางตลาด</form:option>
			<!-- 待审核 -->
			<form:option value="1">รอการตรวจสอบ</form:option>
			<!-- 审核失败 -->
			<form:option value="2">ไม่ผ่านการดรวจสอบ</form:option>
			<!-- 已上架 -->
			<form:option value="3">พร้อมวางตลาด</form:option>
			<!-- 已下架 -->
			<form:option value="4">ปลดระวาง</form:option>
		</form:select>
	</td>
	<!-- 查询 -->
	<td><input type="submit" value="ค้นหา" class="btn btn-primary" /></td>
</tr>
</table>
<table  class="table table-striped table-hover table-condensed">
	<thead>
	<tr> 
		<th>ID</th>
		<th>商品名称</th>
		<th>出发地</th>
		<th>抵达地</th>
		<th>行程天数</th>
		<th>商品库存</th>
		<th>状态</th>
		<th>操作</th>
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
				<a class="btn btn-success" href="${pageContext.request.contextPath}/f/goods/edit/${goods.id} ">修改</a>
				<a class="btn btn-warning" href="javascript:del(${goods.id })">删除</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<script type="text/javascript">
function del(id){
	if(confirm("确定删除？")) {
		window.location = '${pageContext.request.contextPath}/f/goods/delete/' + id;
	}
	
}
</script>
<%@ include file="../../page.jsp"%>
</form:form>
</div>
