<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 


<body>
	<div class="content">
		<h1>New Emploee</h1>
		<c:url var="addUrl" value="/c/user/add"/>
		<form:form action="${addUrl}" method="POST" commandName="user" >
			<form:hidden path="id"/>
			<table class="table" >
				<colgroup>
					<col class="col-xs-1">
					<col class="col-xs-7">
				</colgroup>
				<tr>
					<td>Name :</td>
					<td><form:input path="name"  class="form-control" check="notEmpty" placeholder="Please enter name..."/></td>
					<td><form:errors path="name" cssClass="error" /></td>
				</tr>
				<tr>
					<td>En :</td>
					<td><form:input path="codeName"  class="form-control" check="notEmpty" placeholder="Please enter english name..."/></td>
					<td><form:errors path="codeName" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Login Name :</td>
					<td><form:input id="loginName" path="loginName"  class="form-control" check="notEmpty" placeholder="Please enter login name..."/></td>
					<td><form:errors path="loginName" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Password :</td>
					<td><form:password id="password" path="password"  class="form-control" check="notEmpty" placeholder="Please enter password..."/></td>
					<td><form:errors path="password" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Confirm Password :</td>
					<td><input id="passwordconfirm" name="passwordconfirm" type="password" class="form-control" check="notEmpty" placeholder="Please enter password again..."/></td>
					<td></td>
				</tr>
				<tr>
					<td>Mobile:</td>
					<td><form:input path="mobile"  class="form-control" check="notEmpty" placeholder="Please enter mobile..."/></td>
					<td><form:errors path="mobile" cssClass="error" /></td>
				</tr>
				<tr>
					<td colspan="3"><input type="button" value="Submit" class="btn btn-primary btn-group btn-group-lg"  onclick="submitForm();"/></td>
				</tr>
			</table>
		</form:form>
	</div>

	<script>
		function submitForm() {
			if($("#password").val() != $("#passwordconfirm").val()) {
				alert("The entered passwords don't match..");
				return false;
			}
			if(!confirm("Are you sure？")) return false;
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