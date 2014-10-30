<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h1>Order Details</h1>
<c:url var="addUrl" value="/f/order/${order.id }"/>
<form:form action="${addUrl}" method="POST" commandName="order">
	<form:hidden path="id"/>
	<table class="table">
		<colgroup>
			<col class="col-xs-1">
			<col class="col-xs-5">
			<col class="col-xs-1">
			<col class="col-xs-5">
		</colgroup>
		<tr>
			<td>Order No：</td>
			<td>${order.orderNo }</td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>Status：</td>
			<td>${order.statusDesc }</td>
			<td>Hotel Name：</td>
			<td>${order.hotelName }</td>
		</tr>
		<tr>
			<td>Contracts：</td>
			<td>${order.contractor }</td>
			<td>Hotel Address：</td>
			<td>${order.hotelAddress }</td>
		</tr>
		<tr>
			<td>Contract Number：</td>
			<td>${order.contractorMobile }</td>
			<td>Hotel Room：</td>
			<td>${order.hotelRoom }</td>
		</tr>
		<tr>
			<td>Contract Email：</td>
			<td>${order.contractorEmail }</td>
			<td>Hotel Tel：</td>
			<td>${order.hotelTel }</td>
		</tr>
		<tr>
			<td>Amount：</td>
			<td>${order.amount }</td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>Driver：</td>
			<td class="form-inline">
				<input style="width:180px" id="driverName" class="form-control" value="${order.driverName }" placeholder="driver's name"/>
				<input id="driverMobile" style="width:180px" class="form-control" value="${order.driverMobile }"  placeholder="driver's mobile"/>
			</td>
			<td></td>
			<td></td>
		</tr>
	</table>
	<table class="table table-bordered table-striped " >
		<c:forEach var="mgoods" items="${order.goodses }">
			<tr>
				<td>Goods No：</td>
				<td>${mgoods.id }</td>
				<td>Goods：</td>
				<td><a href="javascript:window.open('${pageContext.request.contextPath}/f/goods/snapshot/${mgoods.id }')">${mgoods.goodsName }</a></td>
				<td>Qty：</td>
				<td>${mgoods.quantity }</td>
			</tr>
		</c:forEach>
	</table>
	
	<h4>Guest Details：</h4>
	<table   class="table table-striped table-hover ">
		<colgroup>
			<col class="col-xs-2">
			<col class="col-xs-1">
			<col class="col-xs-1">
			<col class="col-xs-1">
			<col class="col-xs-1">
			<col class="col-xs-7">
		</colgroup>
		<tr>
			<th>Name</th>
			<th>Nationality</th>
			<th>Sex</th>
			<th>Type</th>
			<th>Mobile</th>
			<th></th>
		</tr>
		<c:forEach var="traveler" items="${order.travelers }">
			<tr>
				<td>${traveler.name }</td>
				<td>${traveler.nationality }</td>
				<td>${traveler.genderDesc }</td>
				<td>${traveler.typeDesc }</td>
				<td>${traveler.mobile }</td>
				<td></td>
			</tr>
		</c:forEach>
	</table>
	
	<h4>Order Processing Records：<a class="btn btn-warning" href="javascript:openDialog(${order.id}, '${order.orderNo}');">New Note</a></h4> 
	<table  class="table table-striped table-hover ">
		<colgroup>
			<col class="col-xs-1">
			<col class="col-xs-2">
			<col class="col-xs-2">
			<col class="col-xs-7">
		</colgroup>
		<tr>
			<th>Personnel</th>
			<th>Processing Time</th>
			<th>Submit Time</th>
			<th>Note</th>
		</tr>
		<c:forEach var="log" items="${logs }">
			<tr>
				<td>${log.creatorName }</td>
				<td><fmt:formatDate value="${log.lockedDate }" type="date" pattern="yyyy/MM/dd HH:mm"/></td>
				<td><fmt:formatDate value="${log.createdDate }" type="date" pattern="yyyy/MM/dd HH:mm"/></td>
				<td>${log.content }</td>
			</tr>
		</c:forEach>
	</table>
	<c:if test="${!order.completed }">
		<input type="button" value="Confirm Order" onclick="complete();" class="btn btn-primary"/>
	</c:if>
	<c:if test="${order.completed }">
		<input type="button" value="Print Divers Shuttle Card" onclick="transferCardPrint();" class="btn btn-primary"/>
		<input type="button" value="Print Pick Card" onclick="pickCardPrint();" class="btn btn-primary"/>
		<input type="button" value="Print Invoice" onclick="invoicePrint();" class="btn btn-primary"/>
	</c:if>
	<input type="button" value="Back" onclick="back();" class="btn btn-default"/>
</form:form>
<div id="dialog-form" title="Basic dialog" style="display:none;">
	<textarea id="reason" rows="10" cols="40"></textarea>
</div>
<script>
	function back(){
		$("form").attr('action', '${pageContext.request.contextPath}/f/order/list');
		$("form").submit();
	}
	
	function transferCardPrint(){
		window.open("${pageContext.request.contextPath}/f/order/print/transfer_card/${order.id }");
	}
	
	function pickCardPrint(){
		window.open("${pageContext.request.contextPath}/f/order/print/pick_card/${order.id }");
	}
	
	function invoicePrint(){
		window.open("${pageContext.request.contextPath}/f/order/print/invoice_card/${order.id }");
	}
	
	function complete() {
		if(!confirm("Are you sure？")) return false;
		jQuery.ajax({
		    type: 'POST',
			encoding:"UTF-8",
		    dataType:"json", 
		    data : 'id=${order.id}&driverName=' + $("#driverName") + '&driverMobile=' + $("#driverMobile"),
		    contentType: "application/x-www-form-urlencoded;  charset=UTF-8",
		    url: "${pageContext.request.contextPath}/f/order/confirm.json",
			success: function(result) {
				alert(result.data.message);
				window.location.reload();
			}
		});
	}
	
</script>
