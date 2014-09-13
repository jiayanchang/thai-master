<%@page import="com.magic.thai.db.domain.Goods"%>
<%@page import="com.magic.thai.util.PaginationSupport"%>
<%@ page language="java" pageEncoding="UTF-8"%>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="content">
<h1>商品列表</h1>
<p>Here you can see the list of the goods, add them, remove or update.</p>

<p>${message}</p>

<br/>
<div class="data">
<c:url var="submitUrl" value="/f/goods/list"/>
<form:form action="${submitUrl}" method="POST">
<table border="1px" cellpadding="0" cellspacing="0" width="100%">
<tr> 
	<td>产品名称：</td>
	<td colspan="3"><input name="title" value="${title }"/></td>
	<td></td>
	<td></td>
</tr>
<tr> 
	<td>出发地：</td>
	<td><input name="dept" value="${dept }"/></td>
	<td>目的地：</td>
	<td><input name="arr" value="${arr }"/></td>
	<td>状态</td>
	<td>
		<select name="status">
			<option value="-1" >全部</option>
			<option value="0">新商品待上架</option>
			<option value="1">待审核</option>
			<option value="2">审核失败</option>
			<option value="3">已上架</option>
			<option value="4">已下架</option>
		</select>
	</td>
	<td><input type="submit" value="submit" class="button2" /></td>
</tr>
</table>
<table border="1px" cellpadding="0" cellspacing="0" width="100%">
	<thead>
	<tr> 
		<th width="10%">ID</th>
		<th width="30%">商品名称</th>
		<th width="10%">出发地</th>
		<th width="20%">抵达地</th>
		<th width="10%">行程天数</th>
		<th width="10%">商品库存</th>
		<th width="10%">状态</th>
		<th>Action</th>
	</tr>
	</thead>
	<tbody>
	<%
	PaginationSupport ps = (PaginationSupport)request.getAttribute("ps");
	Goods goods = (Goods) ps.getItems().get(0);
	out.print(goods.getTitle());
	out.print(ps.getItems().size());
	
	%>
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
				<a href="${pageContext.request.contextPath}/a/merchant/edit/${merchant.id} ">修改</a><br/>
				<a href="${pageContext.request.contextPath}/a/merchant/delete/${merchant.id} ">删除</a><br/>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</div>
<input name="page" type="hidden" value="1"/>
</form:form>
</div>
