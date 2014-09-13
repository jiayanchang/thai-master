<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" /> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>List of User</title>

</head>
<body>
<div class="content">
<h1>List of Order</h1>
<p>Here you can see the list of the user, add them, remove or update.</p>

<p>${message}</p>


<br/>
<div class="data">
</div>
<table>
<tr>

<td>
</td>
<td>
<p><a href="${pageContext.request.contextPath}/user/add"><button class="button2">新增商家</button></a></p>
</td>
</tr>
</table>
</div>
</body>