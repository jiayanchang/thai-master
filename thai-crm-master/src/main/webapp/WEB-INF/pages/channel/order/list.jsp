<%@page import="com.magic.thai.db.domain.Goods"%>
<%@page import="com.magic.thai.util.PaginationSupport"%>
<%@ page language="java" pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:url var="submitUrl" value="/c/order/list"/>
<form:form action="${submitUrl}" method="POST" commandName="vo">
<h1>订单列表</h1>
<p>${message}</p>
<br/>
<table>
	<tr>
		<td>订单号：</td>
		<td><form:input path="orderNo" class="form-control"/></td>
		<td><input type="submit" value="查询"  class="btn btn-primary"/></td>
	</tr>
</table>

<table class="table table-striped table-hover">
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
				<a class="btn btn-default" href="${pageContext.request.contextPath}/c/order/${order.id} ">详情</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<%@ include file="../../page.jsp"%>
</form:form>
