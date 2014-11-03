<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/lock.js">
<h1>订单详情</h1>
<c:url var="addUrl" value="/a/order/${order.id }"/>
<form:form action="${addUrl}" method="GET" commandName="order">
	<form:hidden path="id"/>
	<table class="table" >
		<colgroup>
			<col class="col-xs-1">
			<col class="col-xs-4">
			<col class="col-xs-1">
			<col class="col-xs-5">
		</colgroup>
		<tr>
			<td>订单号：</td>
			<td>${order.orderNo }</td>
			<td>酒店名称：</td>
			<td>${order.hotelName }</td>
		</tr>
		<tr>
			<td>订单状态：</td>
			<td>${order.statusDesc }</td>
			<td>酒店地址：</td>
			<td>${order.hotelAddress }</td>
		</tr>
		<tr>
			<td>联系人：</td>
			<td>${order.contractor }</td>
			<td>房间信息：</td>
			<td>${order.hotelRoom } -- ${order.hotelRoomTel }</td>
		</tr>
		<tr>
			<td>联系电话：</td>
			<td>${order.contractorMobile }</td>
			<td>司机信息：</td>
			<td>${order.driverName } -- ${order.driverMobile }</td>
		</tr>
		<tr>
			<td>联系邮箱：</td>
			<td>${order.contractorEmail }</td>
			<td>订单金额：</td>
			<td>平台总额：${order.profitAmount } 商家：${order.amount }  </td>
		</tr>
		<c:if test="${isNeedsPickup}">
			<tr>
				<td>接机信息：</td>
				<td colspan="3">
					航班号 : ${pickup.flightNo } - 抵达日期 : ${pickup.arrivedDate } - 抵达时间 : ${pickup.arrivedTime }
				</td>
			</tr>
		</c:if>
	</table>
	
	
	
	<div style="width:600px;">
	<h4><strong>商品信息</strong></h4>
	<table class="table" >
		<c:forEach var="mgoods" items="${order.goodses }">
			<tr>
				<td>购买商品编号：${mgoods.id }</td>
				<td>购买商品名称：<a href="javascript:window.open('${pageContext.request.contextPath}/a/goods/snapshot/${mgoods.id }')">${mgoods.goodsName }</a></td>
				<td>购买商品数量：${mgoods.quantity }</td>
			</tr>
		</c:forEach>
	</table>
	</div>
	<h4><strong>旅客详情：</strong></h4>
	<table class="table table-striped">
		<tr>
			<th>姓名</th>
			<th>国籍</th>
			<th>性别</th>
			<!-- <th>证件类型</th>
			<th>证件号码</th>
			<th>证件有效期</th>
			<th>出生日期</th> -->
			<th>类型</th>
			<th>手机号码</th>
		</tr>
		<c:forEach var="traveler" items="${order.travelers }">
			<tr>
				<td>${traveler.firstName }${traveler.lastName }</td>
				<td>${traveler.nationality }</td>
				<td>${traveler.genderDesc }</td>
				<%-- <td>${traveler.idTypeDesc }</td>
				<td>${traveler.idNo }</td>
				<td>${traveler.effectiveDate }</td>
				<td>${traveler.birth }</td> --%>
				<td>${traveler.typeDesc }</td>
				<td>${traveler.mobile }</td>
			</tr>
		</c:forEach>
	</table>
	
	<h4><strong>订单处理记录：</strong><a  class="btn btn-success" href="javascript:openDialog(${order.id}, '${order.orderNo}');" ><i class=" icon-plus">新建备注</i></a></h4>
	<table class="table table-striped table-hover ">
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
	<textarea id="reason" rows="10" cols="40"></textarea>
</div>
<script>
	function back(){
		$("form").action = '${pageContext.request.contextPath}/a/order/list';
		$("form").submit();
	}
</script>
