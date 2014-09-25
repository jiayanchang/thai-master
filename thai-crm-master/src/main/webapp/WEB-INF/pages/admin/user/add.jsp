<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<h1>新建员工</h1>
<c:url var="addUrl" value="/a/user/add"/>
<form:form action="${addUrl}" method="POST" commandName="user" >
	<form:hidden path="id"/>
	<table class="table">
		<colgroup>
			<col class="col-xs-1">
			<col class="col-xs-7">
			<col class="col-xs-7">
		</colgroup>
		<tr>
			<td>员工名称 :</td>
			<td><form:input path="name"  class="form-control"/></td>
			<td><form:errors path="name" cssClass="error" /></td>
		</tr>
		<tr>
			<td>英文名 :</td>
			<td><form:input path="codeName"  class="form-control"/></td>
			<td><form:errors path="codeName" cssClass="error" /></td>
		</tr>
		<tr>
			<td>登录名 :</td>
			<td><form:input path="loginName"  class="form-control"/></td>
			<td><form:errors path="loginName" cssClass="error" /></td>
		</tr>
		<tr>
			<td>密码 :</td>
			<td><form:password path="password"  class="form-control"/></td>
			<td><form:errors path="password" cssClass="error" /></td>
		</tr>
		<tr>
			<td>密码确认 :</td>
			<td><input name="passwordconfirm" type="password" class="form-control"/></td>
			<td></td>
		</tr>
		<tr>
			<td>手机:</td>
			<td><form:input path="mobile"  class="form-control"/></td>
			<td><form:errors path="mobile" cssClass="error" /></td>
		</tr>
		<tr>
			<td colspan="3"><input type="submit" value="保存" class="btn btn-primary" /></td>
		</tr>
	</table>
</form:form>
