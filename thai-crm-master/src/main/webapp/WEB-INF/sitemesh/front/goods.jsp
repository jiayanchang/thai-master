<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>  

<html>
<head>
	<jsp:include page="head.jsp"></jsp:include>
</head>
<body>
	<table>
	<tr>
	<td>
		LOGO
	</td>
	<td>
		<ul>
			<li>商品管理</li>
			<li>订单管理</li>
			<li>系统管理</li>
		</ul>
	</td>
	</tr>
	<tr>
	<td>
		<ul>
			<li>新建订单</li>
			<li>订单查询</li>
		</ul>
	</td>
	<td>
		<decorator:body/> 
	</td>
	</tr>
	</table>
</body>
</html>