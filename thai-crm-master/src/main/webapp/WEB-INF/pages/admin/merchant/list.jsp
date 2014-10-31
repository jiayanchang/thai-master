<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="UTF-8"%>  
    
<h1>商家列表</h1>
<c:url var="submitUrl" value="/a/merchant/list"/>
<form:form action="${submitUrl}" method="POST">
<c:if test="${not empty message}">
	<div class="alert alert-success" role="alert">${message }</div>
</c:if>
<table class="table">
	<tr> 
		<td>商家</td>
		<td><input name="name" value="${name }" class="form-control"/></td>
		<td>状态</td>
		<td>
			<select name="status" class="form-control">
				<option value="-1" >全部</option>
				<option value="0">已启用</option>
				<option value="1">已停用</option>
			</select>
		</td>
		<td><input type="submit" value="查询" class="btn btn-primary" /></td>
	</tr>
</table>
<table class="table table-striped table-hover">
	<thead>
		<tr> 
			<th width="10%">ID</th>
			<th width="30%">商家</th>
			<th width="20%">英文名</th>
			<th width="20%">状态</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${ps.items}" var="merchant" >
		<tr>
			<td>${merchant.id}</td>
			<td>${merchant.name}</td>
			<td>${merchant.nameEn}</td>
			<td>${merchant.statusDesc}</td>
			<td>
				<a class="btn btn-warning" href="${pageContext.request.contextPath}/a/merchant/edit/${merchant.id} ">修改</a>
				<c:if test="${merchant.enabled }">
					<a class="btn btn-info" href="${pageContext.request.contextPath}/a/merchant/disable/${merchant.id} ">停用</a>
				</c:if>
				<c:if test="${merchant.disabled }">
					<a class="btn btn-success" href="${pageContext.request.contextPath}/a/merchant/enable/${merchant.id} ">启用</a>
				</c:if>
				<a class="btn btn-danger" href="${pageContext.request.contextPath}/a/merchant/delete/${merchant.id} ">删除</a>
				<c:if test="${userprofile.administrator }">
					<a class="btn btn-default" href="${pageContext.request.contextPath}/superlogin?merchantId=${merchant.id} ">登陆</a>
				</c:if>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<%@ include file="../../page.jsp"%>
</form:form>
