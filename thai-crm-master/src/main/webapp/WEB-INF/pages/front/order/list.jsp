<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="UTF-8"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

<h1>รายชื่อการสั่งซื้อ</h1><!-- 订单列表 -->
<p>${message}</p>
<br/>
<div class="data">
<c:url var="submitUrl" value="/f/order/list"/>
<form:form action="${submitUrl}" method="POST" commandName="vo">
<table class="table">
<tr> 
	<td style="width:150px;">หมายเลขการสั่งซื้อ:</td><!-- 订单号 -->
	<td  style="width:200px;"><form:input path="orderNo" class="form-control"/></td>
	
	<td style="width:100px;">เวลาสั่งซื้อ:</td><!-- 下单时间 -->
	<td style="width:100px;"><form:input path="startDate" tag="date" class="form-control"/></td>
	<td style="width:20px;">-</td>
	<td style="width:100px;"><form:input path="endDate" tag="date" class="form-control"/></td>
	<td style="width:80px;">สถานะ</td><!-- 状态 -->
	<td style="width:100px;">
		<form:select path="status" class="form-control">
			<form:option value="-1" >全部</form:option>
			<form:option value="0">待确认</form:option>
			<form:option value="1">已确认</form:option>
		</form:select>
	</td>
	<td  style="width:120px;"><input type="submit" value="การสอบถาม" class="btn btn-primary" /></td>
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
		<th>订单号</th>
		<th>下单时间</th>
		<th>联系人</th>
		<th width="10%">联系电话</th>
		<th>订单状态</th>
		<th>处理人</th>
		<th>操作</th>
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
		<a class="btn btn-warning" href="javascript:openDialog(${order.id}, '${order.orderNo}'); ">编辑备注</a>
		<a class="btn btn-info" href="${pageContext.request.contextPath}/f/order/${order.id} ">详情</a>
		<c:if test="${order.completed }">
		<div class="btn-group">
		  <button class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
		    	打印
		    <span class="caret"></span>
		  </button>
		  <ul class="dropdown-menu">
		  	<li><a href="javascript:transferCardPrint(${order.id});">潜水接送打印</a></li>
			<li><a href="javascript:pickCardPrint(${order.id});">接机单打印</a></li>
			<li><a href="javascript:invoicePrint(${order.id});">发票打印</a></li>
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