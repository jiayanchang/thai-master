<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<body>
	<div class="content">
		<h1>编辑员工</h1>
		<c:url var="addUrl" value="/a/user/edit/proccess"/>
		<form:form action="${addUrl}" method="POST" commandName="user" >
			<form:hidden path="id"/>
			<table class="table table-striped" >
				<colgroup>
					<col class="col-xs-1">
					<col class="col-xs-7">
				</colgroup>
				<tr>
					<td>员工名称 :</td>
					<td><form:input path="name"  class="form-control" /></td>
					<td><form:errors path="name" cssClass="error" /></td>
				</tr>
				<tr>
					<td>代码 :</td>
					<td>${user.codeName }</td>
					<td></td>
				</tr>
				<tr>
					<td>登录名 :</td>
					<td>${user.loginName }</td>
					<td></td>
				</tr>
				<tr>
					<td>密码 :</td>
					<td><form:password path="password"  class="form-control" /></td>
					<td><form:errors path="password" cssClass="error" /></td>
				</tr>
				<tr>
					<td>密码确认 :</td>
					<td><input type="password"  class="form-control" name="passwordconfirm" /></td>
					<td></td>
				</tr>
				<tr>
					<td>手机:</td>
					<td><form:input path="mobile"  class="form-control" /></td>
					<td><form:errors path="mobile" cssClass="error" /></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="保存" class="btn btn-primary btn-lg btn-block" /></td>
					<td></td>
				</tr>
			</table>
		</form:form>
	</div>
