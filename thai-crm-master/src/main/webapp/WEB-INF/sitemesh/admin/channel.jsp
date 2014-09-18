<%@page import="com.magic.thai.security.UserProfile"%>
<%@ page language="java" pageEncoding="UTF-8"%>  
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>  
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui.min.js"></script>
<html>
<header>
	<%@ include file="../head.jsp"%>
</header>
<body>
<table>
<tr>
<td>
	<img src="${pageContext.request.contextPath}/<%=((UserProfile)session.getAttribute("userprofile")).getMerchant().getDetails().getLogoPath()  %>"/>
	<%=((UserProfile)session.getAttribute("userprofile")).getUser().getName()  %>
	<a href="${pageContext.request.contextPath}/logout">退出</a>
</td>
<td>
	<ul>
		<li><a href="${pageContext.request.contextPath}/a/goods/list">商品管理</a></li>
		<li><a href="${pageContext.request.contextPath}/a/order/list">订单管理</a></li>
		<li>渠道管理</li>
		<li><a href="${pageContext.request.contextPath}/a/merchant/list">系统管理</a></li>
	</ul>
</td>
</tr>
<tr>
<td>
	<ul>
		<li><a href="${pageContext.request.contextPath}/a/channel/add">新建渠道</a></li>
		<li><a href="${pageContext.request.contextPath}/a/channel/list">渠道查询</a></li>
	</ul>
</td>
<td>
<decorator:body/> 
</td>
</tr>
</table>
</body>
</html>