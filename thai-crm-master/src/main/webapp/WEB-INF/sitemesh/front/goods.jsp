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
			<li><a href="${pageContext.request.contextPath}/f/order/list">订单管理</a></li>
			<li><a href="${pageContext.request.contextPath}/f/user/list">系统管理</a></li>
		</ul>
	</td>
	</tr>
	<tr>
	<td>
		<ul>
			<li><a href="${pageContext.request.contextPath}/f/goods/add">新建商品</a></li>
			<li><a href="${pageContext.request.contextPath}/f/goods/list">商品查询</a></li>
		</ul>
	</td>
	<td>
		<decorator:body/> 
	</td>
	</tr>
	</table>
</body>
</html>