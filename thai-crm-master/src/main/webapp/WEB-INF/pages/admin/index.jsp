<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<html>
<head>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<title>User Edit</title>
</head>

<body>
	<div class="content">
	<h1>login success</h1>
		<form method="POST" action="/">
			<table>
				<tr>
					<td>UserName :</td>
					<td><input name="username" type="text" /></td>
				</tr>
				<tr>
					<td>Password :</td>
					<td><input name="password" type="password" /></td>
				</tr>
				<tr>
					<td>Captcha :</td>
					<td>
						<input name="captcha" type="text" />
						<img id="image" border="0"  onclick="refresh()" src="/captcha" title="µã»÷¸ü»»Í¼Æ¬">
					</td>
					<td><form:errors path="password" cssClass="error" /></td>
				</tr>
				<tr>
					<td colspan="3">
					<input type="submit" value="submit" class="button2" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
<script type="text/javascript">  
    function refresh() {  
        document.getElementById("image").src="/captcha?"+new Date();  
    }  
</script>  
</html>
