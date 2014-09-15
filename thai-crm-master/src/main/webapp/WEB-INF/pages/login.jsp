<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui.min.js"></script>

<%-- <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" /> --%>
<title>User Edit</title>
</head>

<body>
	<div class="content">
	<h1>login</h1>
		<form method="POST" action="${pageContext.request.contextPath}/" enctype="multipart/form-data">
			<table>
				<tr>
					<td>UserName :</td>
					<td><input name="loginName" type="text" /></td>
				</tr>
				<tr>
					<td>Password :</td>
					<td><input name="password" type="password" /></td>
				</tr>
				<tr>
					<td>Captcha :</td>
					<td>
						<input name="captcha" type="text" />
						<img id="image" border="0"  onclick="refresh()" src="${pageContext.request.contextPath}/captcha" title="点图刷新">
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
		<input type="button" value="start" onclick="ajaxreq()"/>
</body>
<script type="text/javascript">  
    function refresh() {  
        document.getElementById("image").src="/captcha?"+new Date();  
    }  
    function ajaxreq(){
		jQuery.ajax({
		    type: 'POST',
    		encoding:"UTF-8",
		    dataType:"json", 
		    data: 'loginName=admin&userId=1',
		    contentType: "application/x-www-form-urlencoded;  charset=UTF-8",
		    url: '/crm/json/validateLoginName.json',
			success: function(result) {
				alert(result.data.success);
			}
		});
	}
</script>  
</html>
