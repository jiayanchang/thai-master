<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/lock.js">
<h1>订单详情</h1>
<c:url var="addUrl" value="/a/order/${order.id }"/>
<form:form action="${addUrl}" method="GET" commandName="order">
	<form:hidden path="id"/>
	订单详情:
	<table>
		<tr>
			<td>订单号：</td>
			<td>${order.orderNo }</td>
		</tr>
		<tr>
			<td>订单状态：</td>
			<td>${order.statusDesc }</td>
		</tr>
		<tr>
			<td>联系人：</td>
			<td>${order.contractor }</td>
		</tr>
		<tr>
			<td>联系电话：</td>
			<td>${order.contractorMobile }</td>
		</tr>
		<tr>
			<td>联系邮箱：</td>
			<td>${order.contractorEmail }</td>
		</tr>
		<c:forEach var="mgoods" items="${order.goodses }">
			<tr>
				<td>购买商品编号：</td>
				<td>${mgoods.id }</td>
				<td>购买商品名称：</td>
				<td><a href="javascript:window.open('${pageContext.request.contextPath}/a/goods/snapshot/${mgoods.id }')">${mgoods.goodsName }</a></td>
				<td>购买商品数量：</td>
				<td>${mgoods.quantity }</td>
			</tr>
		</c:forEach>
	</table>
	
	旅客详情：
	<table>
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
		<c:forEach var="traveler" items="${order.travelers }">
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
	
	订单处理记录：<a href="javascript:openDialog(${order.id}, '${order.orderNo}');">新建备注</a>
	<table>
		<tr>
			<th>处理人员</th>
			<th>备注</th>
			<th>处理时间</th>
			<th>提交时间</th>
		</tr>
		<c:forEach var="log" items="${logs }">
			<tr>
				<td>${log.content }</td>
				<td>${log.creatorName }</td>
				<td>${log.lockedDate }</td>
				<td>${log.createdDate }</td>
			</tr>
		</c:forEach>
	</table>
</form:form>
<div id="dialog-form" title="Basic dialog" style="display:none;">
	<textarea id="reason" rows="7" cols="32"></textarea>
</div>
<script>
	function back(){
		$("form").action = '${pageContext.request.contextPath}/a/order/list';
		$("form").submit();
	}
</script>
