<%@page import="com.magic.thai.security.UserProfile"%>
<%@ page language="java" pageEncoding="UTF-8"%>  
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>  
<html>
<head>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.10.2.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
<!-- 	  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
 -->	
</head>
<body  >
<table>
<tr>
<td>
	<img src="${pageContext.request.contextPath}/<%=((UserProfile)session.getAttribute("userprofile")).getMerchant().getDetails().getLogoPath()  %>"/>
		<a href="${pageContext.request.contextPath}/logout">退出</a>
</td>
<td>
	<ul>
		<li>商品管理</li>
		<li><a href="${pageContext.request.contextPath}/a/order/list">订单管理</a></li>
		<li><a href="${pageContext.request.contextPath}/a/channel/list">渠道管理</a></li>
		<li><a href="${pageContext.request.contextPath}/a/merchant/list">系统管理</a></li>
	</ul>
</td>
</tr>
<tr>
<td>
	<ul>
		<li><a href="${pageContext.request.contextPath}/a/goods/list">商品查询</a></li>
		<li><a href="${pageContext.request.contextPath}/a/goods/audits">待审批商品</a></li>
	</ul>
</td>
<td>
<decorator:body/> 
</td>
</tr>
</table>
</body>
</html>