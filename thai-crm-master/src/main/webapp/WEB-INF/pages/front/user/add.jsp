<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<body>
	<div class="content">
		<h1>新建员工</h1>
		<c:url var="addUrl" value="/f/user/add"/>
		<form:form action="${addUrl}" method="POST" commandName="user" >
			<form:hidden path="id"/>
			<table class="table" >
				<colgroup>
					<col class="col-xs-1">
					<col class="col-xs-7">
				</colgroup>
				<tr>
					<td>员工名称 :</td>
					<td><form:input path="name"  class="form-control" check="notEmpty" placeholder="请输入员工名称..."/></td>
					<td><form:errors path="name" cssClass="error" /></td>
				</tr>
				<tr>
					<td>英文名 :</td>
					<td><form:input path="codeName"  class="form-control" check="notEmpty" placeholder="请输入英文名..."/></td>
					<td><form:errors path="codeName" cssClass="error" /></td>
				</tr>
				<tr>
					<td>登录名 :</td>
					<td><form:input id="loginName" path="loginName"  class="form-control" check="notEmpty" placeholder="请输入登录名..."/></td>
					<td><form:errors path="loginName" cssClass="error" /></td>
				</tr>
				<tr>
					<td>密码 :</td>
					<td><form:password id="password" path="password"  class="form-control" check="notEmpty" placeholder="请输入密码..."/></td>
					<td><form:errors path="password" cssClass="error" /></td>
				</tr>
				<tr>
					<td>密码确认 :</td>
					<td><input id="passwordconfirm" name="passwordconfirm" type="password" class="form-control" check="notEmpty" placeholder="请输入确认密码..."/></td>
					<td></td>
				</tr>
				<tr>
					<td>手机:</td>
					<td><form:input path="mobile"  class="form-control" check="notEmpty" placeholder="请输入手机..."/></td>
					<td><form:errors path="mobile" cssClass="error" /></td>
				</tr>
				<tr>
					<td colspan="3"><input type="button" value="提交" class="btn btn-primary btn-group btn-group-lg"  onclick="submitForm();"/></td>
				</tr>
			</table>
		</form:form>
	</div>

	<script>
		function submitForm() {
			if($("#password").val() != $("#passwordconfirm").val()) {
				alert("密码不一致");
				return false;
			}
			if(!confirm("是否确定？")) return false;
			validate(function(){
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
			});
		}
	</script>