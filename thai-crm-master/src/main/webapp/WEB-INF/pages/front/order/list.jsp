<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="UTF-8"%>  
    
<h1>订单列表</h1>
<p>${message}</p>
<br/>
<div class="data">
<c:url var="submitUrl" value="/f/order/list"/>
<form:form action="${submitUrl}" method="POST" commandName="vo">
<table border="1px" cellpadding="0" cellspacing="0" width="100%">
<tr> 
	<td>订单号：</td>
	<td><form:input path="orderNo" /></td>
	<td>下单时间：</td>
	<td><form:input path="startDate" tag="date"/></td>
	<td>--</td>
	<td><form:input path="endDate" tag="date"/></td>
	<td>状态</td>
	<td>
		<form:select path="status">
			<form:option value="-1" >全部</form:option>
			<form:option value="0">待确认</form:option>
			<form:option value="1">已确认</form:option>
		</form:select>
	</td>
	<td><input type="submit" value="submit" class="button2" /></td>
</tr>
<%-- <tr> 	
	<td>出发地：</td>
	<td><form:input path="dept" /></td>
	<td>目的地：</td>
	<td><form:input path="arr" /></td>
	<td><input type="submit" value="submit" class="button2" /></td>
</tr> --%>
</table>
<table border="1px" cellpadding="0" cellspacing="0" width="100%">
<thead>
<tr> 
<th width="10%">订单号</th>
<th width="30%">下单时间</th>
<th width="10%">联系人</th>
<th width="20%">联系电话</th>
<th width="10%">订单状态</th>
<th width="10%">处理人</th>
<th>Action</th>
</tr>
</thead>
<tbody>
<c:forEach items="${ps.items}" var="order" >
<tr>
	<td>${order.orderNo}</td>
	<td>${order.createdDate}</td>
	<td>${order.contractor}</td>
	<td>${order.contractorMobile}</td>
	<td>${order.statusDesc}</td>
	<td>${order.lastOperatorName}</td>
	<td>
		<a href="javascript:openDialog(${order.id}, '${order.orderNo}'); ">编辑备注</a>
		<a href="${pageContext.request.contextPath}/f/order/${order.id} ">详情</a>
	</td>
</tr>
</c:forEach>
</tbody>
</table>
</div>
<%@ include file="../../page.jsp"%>

</form:form>
<div id="dialog-form" title="Basic dialog" style="display:none;">
	<textarea id="reason" rows="7" cols="32"></textarea>
</div>
<script>
$(function() {
	$("form [tag=date]").datepicker({dateFormat:'yy/mm/dd'});
});
</script>