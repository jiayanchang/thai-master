<%@page import="com.magic.thai.db.domain.Goods"%>
<%@page import="com.magic.thai.util.PaginationSupport"%>
<%@ page language="java" pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="content">
<h1>商品列表</h1>
<p>Here you can see the list of the goods, add them, remove or update.</p>

<p>${message}</p>

<br/>
<div class="data">
<c:url var="submitUrl" value="/f/goods/list"/>
<form:form action="${submitUrl}" method="POST" commandName="goodsVo">
<table border="1px" cellpadding="0" cellspacing="0" width="100%">
<tr> 
	<td>产品名称：</td>
	<td colspan="3"><form:input path="titleKeyword"/></td>
	<td></td>
	<td></td>
</tr>
<tr> 
	<td>出发地：</td>
	<td><form:input path="dept"/></td>
	<td>目的地：</td>
	<td><form:input path="arr"/></td>
	<td>状态</td>
	<td>
		<form:select path="status">
			<form:option value="-1" >全部</form:option>
			<form:option value="0">新商品待上架</form:option>
			<form:option value="1">待审核</form:option>
			<form:option value="2">审核失败</form:option>
			<form:option value="3">已上架</form:option>
			<form:option value="4">已下架</form:option>
		</form:select>
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
				<a href="${pageContext.request.contextPath}/f/goods/edit/${goods.id} ">修改</a><br/>
				<a href="${pageContext.request.contextPath}/f/goods/delete/${goods.id} ">删除</a><br/>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</div>
<input name="page" type="hidden" value="1"/>
</form:form>
</div>
