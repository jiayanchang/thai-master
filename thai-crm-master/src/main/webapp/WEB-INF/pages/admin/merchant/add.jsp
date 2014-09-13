<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<html>
<head>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<title>添加商家</title>
</head>


<body>
	<div class="content">
		<h1>添加商家</h1>
		<c:url var="addUrl" value="/a/merchant/add"/>
		<form:form action="${addUrl}" method="POST" commandName="merchant"  enctype="multipart/form-data">
			<table>
				<tr>
					<td>商家名称 :</td>
					<td><form:input path="name" /></td>
					<td><form:errors path="name" cssClass="error" /></td>
				</tr>
				<tr>
					<td>代码 :</td>
					<td><form:input path="codeName" /></td>
					<td><form:errors path="codeName" cssClass="error" /></td>
				</tr>
				<tr>
					<td>联系电话 :</td>
					<td><form:input path="tel" /></td>
					<td><form:errors path="tel" cssClass="error" /></td>
				</tr>
				<tr>
					<td>手机:</td>
					<td><form:input path="mobile" /></td>
					<td><form:errors path="mobile" cssClass="error" /></td>
				</tr>
				<tr>
					<td>管理员登陆名:</td>
					<td><input name="adminLoginName" /></td>
					<td></td>
				</tr>
				<tr>
					<td>管理员密码:</td>
					<td><input type="password" name="adminPassword" /></td>
					<td></td>
				</tr>
				<tr>
					<td>管理员密码确认:</td>
					<td><input type="password" name="adminPasswordconfirm" /></td>
					<td></td>
				</tr>
				<tr>
					<td>LOGO:</td>
					<td><input type="file" name="file"/></td>
					<td></td>
				</tr>
				<tr>
					<td>备注:</td>
					<td><form:textarea path="details.notes" /></td>
					<td><form:errors path="details.notes" cssClass="error" /></td>
				</tr>
				<tr>
					<td colspan="3"><input type="submit" value="submit" class="button2" /></td>
				</tr>
			</table>
		</form:form>
		<p>
			<a href="${pageContext.request.contextPath}/"><button class="button2">Back</button></a>
		</p>
	</div>
</body>

</html>