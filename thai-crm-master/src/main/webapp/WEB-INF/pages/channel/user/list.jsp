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
<h1>Employee List</h1>
<br/>
<div class="data">
<c:url var="submitUrl" value="/c/user/list"/>
<form:form action="${submitUrl}" method="POST">
<c:if test="${not empty message}">
	<div class="alert alert-success" role="alert">${message }</div>
</c:if>
<table class="table">
<tr> 
	<td style="width:60px;">Name</td>
	<td style="width:160px;"><input name="name" value="${name }" class="form-control"/></td>
	<td style="width:70px;">Login Name</td>
	<td style="width:160px;"><input name="loginName" value="${loginName }" class="form-control"/></td>
	<td style="width:70px;">Status</td>
	<td style="width:130px;">
		<select name="status" class="form-control">
			<option value="-1" >All</option>
			<option value="0">Enabled</option>
			<option value="1">Disabled</option>
		</select>
	</td>
	<td><input type="submit" value="Search" class="btn btn-primary" /></td>
	<td></td>
</tr>
</table>
<table  class="table table-hover table-striped">
<thead>
<tr> 
<th>ID</th>
<th>Name</th>
<th>En</th>
<th>LoginName</th>
<th>Mobile</th>
<th>Status</th>
<th>Oper</th>
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
		<a class="btn btn-warning" href="${pageContext.request.contextPath}/f/user/edit/${user.id} ">Edit</a>
		<c:if test="${user.enabled }">
			<a class="btn btn-info" href="${pageContext.request.contextPath}/f/user/disable/${user.id} ">Disable</a>
		</c:if>
		<c:if test="${user.disabled }">
			<a class="btn btn-success" href="${pageContext.request.contextPath}/f/user/enable/${user.id} ">Enable</a>
		</c:if>
		<a class="btn btn-danger" href="javascript:del(${user.id }); ">Delete</a>
	</td>
</tr>
</c:forEach>
</tbody>
</table>
</div>
<script>
function del(id){
	if(confirm("Are you sure？")) {
		window.location = '${pageContext.request.contextPath}/f/user/delete/' + id;
	}
}
</script>
<%@ include file="../../page.jsp"%>

</form:form>
</div>
</body>