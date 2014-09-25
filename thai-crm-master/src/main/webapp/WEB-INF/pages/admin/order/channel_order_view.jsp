<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/lock.js">
<h1>渠道订单详情</h1>
<c:url var="addUrl" value="/a/order/${order.id }"/>
<form:form action="${addUrl}" method="GET" commandName="channelOrder">
	<form:hidden path="id"/>
	订单详情:
	<table class="table">
		<colgroup>
			<col class="col-xs-1">
			<col class="col-xs-7">
		</colgroup>
		<tr>
			<td>订单号：</td>
			<td>${channelOrder.channelOrderNo }</td>
		</tr>
		<tr>
			<td>订单状态：</td>
			<td>${channelOrder.statusDesc }</td>
		</tr>
		<tr>
			<td>联系人：</td>
			<td>${channelOrder.contractor }</td>
		</tr>
		<tr>
			<td>联系电话：</td>
			<td>${channelOrder.contractorMobile }</td>
		</tr>
		<tr>
			<td>订单总金额：</td>
			<td>${channelOrder.amount }</td>
		</tr>
		<tr>
			<td>下单时间：</td>
			<td>${channelOrder.createdDate }</td>
		</tr>
	</table>
	
	<table class="table table-striped table-hover">
		<c:forEach var="merchantOrder" items="${channelOrder.merchantOrders }">
			<tr>
				<td>商家：${merchantOrder.merchantName }</td>
				<td>单号：<a href="javascript:window.open('${pageContext.request.contextPath}/a/order/${merchantOrder.id }')">${merchantOrder.orderNo }</a></td>
				<td>金额：${merchantOrder.amount }</td>
				<td>状态：${merchantOrder.statusDesc }</td>
			</tr>
			<tr>
				<td colspan="4">
				<div style="width:700px;">
					<table  class="table table-striped">
					<c:forEach var="mgoods" items="${merchantOrder.goodses }">
							<tr>
								<td>购买商品编号：</td>
								<td>${mgoods.goodsId }</td>
								<td>购买商品名称：</td>
								<td><a href="javascript:window.open('${pageContext.request.contextPath}/a/goods/snapshot/${mgoods.id }')">${mgoods.goodsName }</a></td>
								<td>购买商品数量：</td>
								<td>${mgoods.quantity }</td>
								<td>购买商品金额：</td>
								<td>${mgoods.amount }</td>
							</tr>
					</c:forEach>
					</table>
				</div>
				</td>
			</tr>
		</c:forEach>
	</table>
	
	
	<h4><strong>旅客详情</strong></h4>
	<table class="table table-striped table-hover">
		<tr>
			<th>姓名</th>
			<th>国籍</th>
			<th>性别</th>
			<th>证件类型</th>
			<th>证件号码</th>
			<th>证件有效期</th>
			<th>出生日期</th>
			<th>手机号码</th>
		</tr>
		<c:forEach var="traveler" items="${channelOrder.travelers }">
			<tr>
				<td>${traveler.name }</td>
				<td>${traveler.nationality }</td>
				<td>${traveler.genderDesc }</td>
				<td>${traveler.idTypeDesc }</td>
				<td>${traveler.idNo }</td>
				<td>${traveler.effectiveDate }</td>
				<td>${traveler.birth }</td>
				<td>${traveler.mobile }</td>
			</tr>
		</c:forEach>
	</table>
</form:form>
