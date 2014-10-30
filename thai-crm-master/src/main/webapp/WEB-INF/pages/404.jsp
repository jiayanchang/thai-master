<%@page contentType="text/html;charset=UTF-8" isErrorPage="true"%> 
<html> 
<head><title>ErrorPage</title></head>
<body> 
	Code： <%=request.getAttribute("javax.servlet.error.status_code")%> <br> 
 Message： <%=request.getAttribute("javax.servlet.error.message")%> <br> 
   Stack： <%=request.getAttribute("javax.servlet.error.exception_type")%> <br>
</body> 
</html> 