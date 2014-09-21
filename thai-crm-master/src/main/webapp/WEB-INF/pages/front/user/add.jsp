<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<body>
	<div class="content">
		<h1>新建员工</h1>
		<c:url var="addUrl" value="/f/user/add"/>
		<form:form action="${addUrl}" method="POST" commandName="user" >
			<form:hidden path="id"/>
			<table>
				<tr>
					<td>员工名称 :</td>
					<td><form:input path="name" /></td>
					<td><form:errors path="name" cssClass="error" /></td>
				</tr>
				<tr>
					<td>代码 :</td>
					<td><form:input path="codeName" /></td>
					<td><form:errors path="codeName" cssClass="error" /></td>
				</tr>
				<tr>
					<td>登录名 :</td>
					<td><form:input id="loginName" path="loginName" /></td>
					<td><form:errors path="loginName" cssClass="error" /></td>
				</tr>
				<tr>
					<td>密码 :</td>
					<td><form:password path="password" /></td>
					<td><form:errors path="password" cssClass="error" /></td>
				</tr>
				<tr>
					<td>密码确认 :</td>
					<td><input name="passwordconfirm" type="password"/></td>
					<td></td>
				</tr>
				<tr>
					<td>手机:</td>
					<td><form:input path="mobile" /></td>
					<td><form:errors path="mobile" cssClass="error" /></td>
				</tr>
				<tr>
					<td colspan="3"><input type="button" value="提交" class="button2"  onclick="submitForm();"/></td>
				</tr>
			</table>
		</form:form>
	</div>

	<script>
		function submitForm() {
			jQuery.ajax({
			    type: 'POST',
				encoding:"UTF-8",
			    dataType:"json", 
			    data:'loginName=' + $("#loginName").val() + "&userId=",
			    contentType: "application/x-www-form-urlencoded;  charset=UTF-8",
			    url: "${pageContext.request.contextPath}/json/validateUser.json",
				success: function(result) {
					if(result.data.success) {
						$("form").submit();
					} else {
						alert(result.data.message);
					}
				}
			});
		}
	</script>