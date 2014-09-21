<%@page import="com.magic.thai.db.domain.Goods"%>
<%@page import="com.magic.thai.util.PaginationSupport"%>
<%@ page language="java" pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:url var="submitUrl" value="/a/order/list"/>
<form:form action="${submitUrl}" method="POST" commandName="vo">
<h1>List of Order</h1>
<p>${message}</p>
<br/>
<table border="1px" cellpadding="0" cellspacing="0" width="100%">
	<thead>
	<tr> 
		<th width="5%">ID</th>
		<th width="10%">订单号</th>
		<th width="10%">所属商家</th>
		<th width="5%">渠道</th>
		<th width="10%">下单时间</th>
		<th width="5%">联系人</th>
		<th width="10%">联系电话</th>
		<th width="10%">订单状态</th>
		<th width="10%">处理人</th>
		<th>操作</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${ps.items}" var="order" varStatus="index">
		<tr>
			<td>${order.id}</td>
			<td>${order.orderNo}</td>
			<td>${order.merchantName}</td>
			<td>${order.channelName}</td>
			<td>${order.createdDate}</td>
			<td>${order.contractor}</td>
			<td>${order.contractorTel}</td>
			<td>${order.statusDesc}</td>
			<td>${order.lastOperatorName}</td>
			<td>
				<a href="javascript:openDialog(${order.id}, '${order.orderNo}'); ">编辑备注</a>
				<a href="${pageContext.request.contextPath}/a/order/${order.id} ">详情</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<%@ include file="../../page.jsp"%>
</form:form>
<div id="dialog-form" title="Basic dialog" style="display:none;">
	<textarea id="reason" rows="7" cols="32"></textarea>
</div>
