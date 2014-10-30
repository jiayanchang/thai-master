<%@page import="com.magic.thai.security.UserProfile"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui.min.js"></script>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/frame.css">
<style>
body, button, input, select, textarea, table, div, a {
	font-size: 15px;
}

body {
	padding-top: 40px;
	padding-bottom: 40px;
	background-color: #eee;
}

.form-signin {
	max-width: 330px;
	padding: 15px;
	margin: 0 auto;
}

.form-signin .form-signin-heading, .form-signin .checkbox {
	margin-bottom: 10px;
}

.form-signin .checkbox {
	font-weight: normal;
}

.form-signin .form-control {
	position: relative;
	height: auto;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
	padding: 10px;
	font-size: 16px;
}

.form-signin .form-control:focus {
	z-index: 2;
}

.form-signin input[type="email"] {
	margin-bottom: -1px;
	border-bottom-right-radius: 0;
	border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
	margin-bottom: 10px;
	border-top-left-radius: 0;
	border-top-right-radius: 0;
}
.verify-code-input {
	width: 90px !important;
}
</style>
<%-- <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" /> --%>
<title>Magic Thai</title>
</head>

<body>
	<div class="container" style="border:1px solid #999999; width:500px;padding:30px 10px;border-radius:8px;background-color:#FFF;">
		<form class="form-signin" role="form" method="POST" action="${pageContext.request.contextPath}/" enctype="multipart/form-data">
			<img height="230" src="${pageContext.request.contextPath}/css/logo.jpg"/>
			<c:if test="${not empty message}">
				<div class="alert alert-warning" role="alert">${message }</div>
			</c:if>
			<div class="form-group">
				<!-- <label for="loginName">用户名</label>  -->
				<input name="loginName" type="text" class="form-control" id="loginName" placeholder="username">
			</div>
			<div class="form-group">
				<!-- <label for="password">密码</label>  -->
				<input name="password" type="password" class="form-control" id="password" placeholder="password">
			</div>
			<div class="form-group" style="margin-bottom:10px;">
				<!-- <label for="captcha">验证码</label>
				<br> -->
				<input style="margin:0 10px 10px 0;float:left;" name="captcha" type="input" style="width:50px;" id="captcha" placeholder="captcha"  class="form-control verify-code-input">
				<img style="float:left;width:90px;height:40px;" id="image" border="0" onclick="refresh()" src="${pageContext.request.contextPath}/captcha" title="refresh"> 
			</div>
			<input type="submit" value="Sign in" class="btn btn-info btn-lg btn-block" />
		</form>
	</div>
</body>
<script type="text/javascript">
	function refresh() {
		document.getElementById("image").src = "${pageContext.request.contextPath}/captcha?" + new Date();
	}
	function ajaxreq() {
		jQuery.ajax({
			type : 'POST',
			encoding : "UTF-8",
			dataType : "json",
			data : 'loginName=admin&userId=1',
			contentType : "application/x-www-form-urlencoded;  charset=UTF-8",
			url : '/crm/json/validateLoginName.json',
			success : function(result) {
				alert(result.data.success);
			}
		});
	}
</script>
</html>
