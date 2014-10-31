<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="UTF-8"%>  

<h1>员工列表</h1>
<p>${message}</p>
<c:url var="submitUrl" value="/a/user/list"/>
<form:form action="${submitUrl}" method="POST">
<c:if test="${not empty message}">
	<div class="alert alert-success" role="alert">${message }</div>
</c:if>
<table class="table">
<tr> 
	<td>用户</td>
	<td><input name="name" value="${name }" class="form-control"/></td>
	<td>登录名</td>
	<td><input name="loginName" value="${loginName }" class="form-control"/></td>
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
<table class="table">
<thead>
<tr> 
<th>ID</th>
<th>用户</th>
<th>编号</th>
<th>登录名</th>
<th>手机</th>
<th>状态</th>
<th>操作</th>
</tr>
</thead>
<tbody>
<c:forEach items="${ps.items}" var="user" >
<tr>
	<td>${user.id}</td>
	<td>${user.name}</td>
	<td>${user.codeName}</td>
	<td>${user.loginName}</td>
	<td>${user.mobile}</td>
	<td>${user.statusDesc}</td>
	<td>
		<a class="btn btn-warning" href="${pageContext.request.contextPath}/a/user/edit/${user.id} ">修改</a>
		<c:if test="${user.enabled }">
			<a class="btn btn-info" href="${pageContext.request.contextPath}/a/user/disable/${user.id} ">停用</a>
		</c:if>
		<c:if test="${user.disabled }">
			<a class="btn btn-success" href="${pageContext.request.contextPath}/a/user/enable/${user.id} ">启用</a>
		</c:if>
		<a class="btn btn-info" href="${pageContext.request.contextPath}/a/user/delete/${user.id} ">删除</a>
	</td>
</tr>
</c:forEach>
</tbody>
</table>
<%@ include file="../../page.jsp"%>

</form:form>
