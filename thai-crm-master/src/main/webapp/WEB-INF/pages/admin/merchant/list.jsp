<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="UTF-8"%>  
    
<h1>商户列表</h1>
<p>${message}</p>
<br/>
<c:url var="submitUrl" value="/a/merchant/list"/>
<form:form action="${submitUrl}" method="POST">
<table border="1px" cellpadding="0" cellspacing="0" width="100%">
<tr> 
	<td>商家</td>
	<td><input name="name" value="${name }"/></td>
	<td>状态</td>
	<td>
		<select name="status">
			<option value="-1" >全部</option>
			<option value="0">已启用</option>
			<option value="1">已停用</option>
		</select>
	</td>
	<td><input type="submit" value="submit" class="button2" /></td>
</tr>
</table>
<table border="1px" cellpadding="0" cellspacing="0" width="100%">
	<thead>
		<tr> 
			<th width="10%">ID</th>
			<th width="30%">商家</th>
			<th width="20%">商家编号</th>
			<th width="20%">状态</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${ps.items}" var="merchant" >
		<tr>
			<td>${merchant.id}</td>
			<td>${merchant.name}</td>
			<td>${merchant.codeName}</td>
			<td>${merchant.statusDesc}</td>
			<td>
			<a href="${pageContext.request.contextPath}/a/merchant/edit/${merchant.id} ">修改</a><br/>
			<a href="${pageContext.request.contextPath}/a/merchant/enable/${merchant.id} ">启用</a><br/>
			<a href="${pageContext.request.contextPath}/a/merchant/disable/${merchant.id} ">停用</a><br/>
			<a href="${pageContext.request.contextPath}/a/merchant/delete/${merchant.id} ">删除</a><br/>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<%@ include file="../../page.jsp"%>
</form:form>
