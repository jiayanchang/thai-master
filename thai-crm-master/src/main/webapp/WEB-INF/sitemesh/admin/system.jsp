<%@page import="com.magic.thai.security.UserProfile"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<table>
<tr>
<td>
	<img src="${pageContext.request.contextPath}/<%=((UserProfile)session.getAttribute("userprofile")).getMerchant().getDetails().getLogoPath()  %>"/>
		<a href="${pageContext.request.contextPath}/logout">退出</a>
</td>
<td>
	<ul>
		<li><a href="${pageContext.request.contextPath}/a/goods/list">商品管理</a></li>
		<li><a href="${pageContext.request.contextPath}/a/order/list">订单管理</a></li>
		<li><a href="${pageContext.request.contextPath}/a/channel/list">渠道管理</a></li>
		<li>系统管理</li>
	</ul>
</td>
</tr>
<tr>
<td>
	<ul>
		<li><a href="${pageContext.request.contextPath}/a/merchant/list">商户查询</a></li>
		<li><a href="${pageContext.request.contextPath}/a/merchant/add">新建商户</a></li>
		<li><a href="${pageContext.request.contextPath}/a/user/list">用户查询</a></li>
		<li><a href="${pageContext.request.contextPath}/a/user/add">新建用户</a></li>
	</ul>
</td>
<td>
<decorator:body/> 
</td>
</tr>
</table>
