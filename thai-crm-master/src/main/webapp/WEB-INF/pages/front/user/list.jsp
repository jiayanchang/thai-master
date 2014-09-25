<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="UTF-8"%>  
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" /> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>List of User</title>

</head>
<body>
<div class="content">
<h1>员工列表</h1>

<p>${message}</p>

<br/>
<div class="data">
<c:url var="submitUrl" value="/f/user/list"/>
<form:form action="${submitUrl}" method="POST">
<table class="table">
<tr> 
	<td style="width:60px;">用户</td>
	<td style="width:70px;"><input name="name" value="${name }"/></td>
	<td style="width:70px;">登录名</td>
	<td style="width:70px;"><input name="loginName" value="${loginName }"/></td>
	<td style="width:70px;">状态</td>
	<td style="width:70px;">
		<select name="status">
			<option value="-1" >全部</option>
			<option value="0">已启用</option>
			<option value="1">已停用</option>
		</select>
	</td>
	<td><input type="submit" value="查询" class="btn btn-primary" /></td>
	<td></td>
</tr>
</table>
<table  class="table table-hover table-striped">
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
		<a class="btn btn-success" href="${pageContext.request.contextPath}/f/user/edit/${user.id} ">修改</a>
		<a class="btn btn-warning" href="${pageContext.request.contextPath}/f/user/delete/${user.id} ">删除</a>
	</td>
</tr>
</c:forEach>
</tbody>
</table>
</div>

<%@ include file="../../page.jsp"%>

</form:form>
</div>
</body>