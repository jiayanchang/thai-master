<%@page import="com.magic.thai.db.domain.Goods"%>
<%@page import="com.magic.thai.util.PaginationSupport"%>
<%@ page language="java" pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:url var="submitUrl" value="/a/order/channelorders"/>
<form:form action="${submitUrl}" method="POST" commandName="vo">
<h1>渠道总单列表</h1>
<p>${message}</p>
<br/>
订单号：<form:input path="orderNo"/>
<table border="1px" cellpadding="0" cellspacing="0" width="100%">
	<thead>
	<tr> 
		<th width="5%">ID</th>
		<th width="10%">渠道单号</th>
		<th width="10%">渠道</th>
		<th width="10%">下单时间</th>
		<th width="10%">订单总额</th>
		<th width="5%">联系人</th>
		<th width="10%">联系电话</th>
		<th width="10%">联系邮箱</th>
		<th width="10%">订单状态</th>
		<th>操作</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${ps.items}" var="order" varStatus="index">
		<tr>
			<td>${order.id}</td>
			<td>${order.channelOrderNo}</td>
			<td>${order.channelName}</td>
			<td>${order.createdDate}</td>
			<td>${order.amount}</td>
			<td>${order.contractor}</td>
			<td>${order.contractorMobile}</td>
			<td>${order.contractorEmail}</td>
			<td>${order.statusDesc}</td>
			<td>
				<a href="${pageContext.request.contextPath}/a/order/channelorder/${order.id} ">详情</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<%@ include file="../../page.jsp"%>
</form:form>
