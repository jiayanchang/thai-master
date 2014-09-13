<%@page import="com.magic.thai.security.UserProfile"%>
<%@ page language="java" pageEncoding="UTF-8"%>  
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>  
<h1>User Manager</h1>  
<hr/>  
<table>
<tr>
<td>
	<ul>
		<li><a href="${pageContext.request.contextPath}/a/goods/list">商品管理</a></li>
		<li><a href="${pageContext.request.contextPath}/a/order/list">订单管理</a></li>
		<li>渠道管理</li>
		<li><a href="${pageContext.request.contextPath}/a/merchant/list">系统管理</a></li>
	</ul>
</td>
<td>

</td>
</tr>
<tr>
<td>
	<ul>
		<li>新建渠道</li>
		<li>渠道查询</li>
	</ul>
</td>
<td>
<decorator:body/> 
</td>
</tr>
</table>
