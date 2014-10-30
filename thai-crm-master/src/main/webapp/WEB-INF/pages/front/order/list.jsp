<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="UTF-8"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

<h1>Order List</h1><!-- 订单列表 -->
<p>${message}</p>
<br/>
<div class="data">
<c:url var="submitUrl" value="/f/order/list"/>
<form:form action="${submitUrl}" method="POST" commandName="vo">
<table class="table">
<tr> 
	<td style="width:150px;">Order No:</td><!-- 订单号 -->
	<td  style="width:200px;"><form:input path="orderNo" class="form-control"/></td>
	
	<td style="width:100px;">Order Time:</td><!-- 下单时间 -->
	<td style="width:100px;"><form:input path="startDate" tag="date" class="form-control"/></td>
	<td style="width:20px;">-</td>
	<td style="width:100px;"><form:input path="endDate" tag="date" class="form-control"/></td>
	<td style="width:80px;">Order Status</td><!-- 状态 -->
	<td style="width:100px;">
		<form:select path="status" class="form-control">
			<form:option value="-1" >All</form:option>
			<form:option value="0">Uncompleted</form:option>
			<form:option value="1">Completed</form:option>
		</form:select>
	</td>
	<td  style="width:120px;"><input type="submit" value="Submit" class="btn btn-primary" /></td>
	<td>&nbsp;</td>
</tr>
<%-- <tr> 	
	<td>出发地：</td>
	<td><form:input path="dept" /></td>
	<td>目的地：</td>
	<td><form:input path="arr" /></td>
	<td><input type="submit" value="submit" class="button2" /></td>
</tr> --%>
</table>
<table class="table table-striped table-hover table-condensed">
<thead>
	<tr> 
		<th>Order No</th>
		<th>Order Time</th>
		<th>Contracts</th>
		<th width="10%">ContractTel</th>
		<th>Status</th>
		<th>Processor</th>
		<th>Oper</th>
	</tr>
</thead>
<tbody>
<c:forEach items="${ps.items}" var="order" >
<tr>
	<td>${order.orderNo}</td>
	<td><fmt:formatDate value="${order.createdDate}" type="date" pattern="yyyy/MM/dd HH:mm"/></td>
	<td>${order.contractor}</td>
	<td>${order.contractorMobile}</td>
	<td>${order.statusDesc}</td>
	<td>${order.lastOperatorName}</td>
	<td>
		<a class="btn btn-warning" href="javascript:openDialog(${order.id}, '${order.orderNo}'); ">Edit Note</a>
		<a class="btn btn-info" href="${pageContext.request.contextPath}/f/order/${order.id} ">Details</a>
		<c:if test="${order.completed }">
		<div class="btn-group">
		  <button class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
		    	Print
		    <span class="caret"></span>
		  </button>
		  <ul class="dropdown-menu">
		  	<li><a href="javascript:transferCardPrint(${order.id});">Print Divers Shuttle Card</a></li>
			<li><a href="javascript:pickCardPrint(${order.id});">Print Pick Card</a></li>
			<li><a href="javascript:invoicePrint(${order.id});">Print Invoice</a></li>
		  </ul>
		</div>
		</c:if>
		
	</td>
</tr>
</c:forEach>
</tbody>
</table>
</div>
<%@ include file="../../page.jsp"%>

</form:form>
<div id="dialog-form" title="Basic dialog" style="display:none;">
	<textarea id="reason" rows="10" cols="40"></textarea>
</div>
<script>
$(function() {
	$("form [tag=date]").datepicker({dateFormat:'yy/mm/dd'});
});

function transferCardPrint(id){
	window.open("${pageContext.request.contextPath}/f/order/print/transfer_card/" + id);
}

function pickCardPrint(id){
	window.open("${pageContext.request.contextPath}/f/order/print/pick_card/" + id);
}

function invoicePrint(id){
	window.open("${pageContext.request.contextPath}/f/order/print/invoice_card/" + id);
}

</script>