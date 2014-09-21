<%@page import="com.magic.thai.security.UserProfile"%>
<%@ page language="java" pageEncoding="UTF-8"%>  
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="../head.jsp"%>
</head>
<body>
<table>
<tr>
<td>
		<%@ include file="../logo.jsp"%>
</td>
<td>
	<ul style="float: left">
		<li><a href="${pageContext.request.contextPath}/a/goods/list">商品管理</a></li>
		<li>订单管理</li>
		<li><a href="${pageContext.request.contextPath}/a/channel/list">渠道管理</a></li>
		<% if(((UserProfile)session.getAttribute("userprofile")).isAdministrator()) { %>
			<li><a href="${pageContext.request.contextPath}/a/merchant/list">系统管理</a></li>
		<% } %>
	</ul>
</td>
</tr>
<tr>
<td>
	<ul>
		<li><a href="${pageContext.request.contextPath}/a/order/list">订单查询</a></li>
		<li><a href="${pageContext.request.contextPath}/a/order/waittings" title="创建时间大于15分钟的订单数量">待确定订单<span id="orderMonitorSpan"></span></a></li>
		<li><a href="${pageContext.request.contextPath}/a/order/channelorders">渠道总单</a></li>
	</ul>
</td>
<td>
<decorator:body/> 
</td>
</tr>
</table>
<%@ include file="../notify.jsp"%>
</body>
</html>