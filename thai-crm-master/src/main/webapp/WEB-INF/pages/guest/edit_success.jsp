<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<title>完善订单</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery/jquery-ui.css">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/frame.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap-combobox.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validator.js"></script>  

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

</head>
<body>	
<div class="container" style="width:800px;border-radius:20px;background-color:#FFF;">

<h1>订单信息修改成功</h1>
<c:url var="addUrl" value="/g/order/edit/process"/>
<form:form action="${addUrl}" method="POST" commandName="order">
	<form:hidden path="id"/>
	<table class="table" >
		<tr>
			<td style="width:80px;">订单号：</td>
			<td style="width:200px;">${order.orderNo }</td>
			<td style="width:80px;">酒店名称：</td>
			<td style="width:300px;">${order.hotelName }</td>
		</tr>
		<tr>
			<td>联系人：</td>
			<td>${order.contractor }</td>
			<td>酒店地址：</td>
			<td>${order.hotelAddress }</td>
		</tr>
		<tr>
			<td>联系电话：</td>
			<td>${order.contractorMobile }</td>
			<td>房间信息：</td>
			<td>${order.hotelRoom } -- ${order.hotelRoomTel }</td>
		</tr>
		<tr>
			<td>联系邮箱：</td>
			<td>${order.contractorEmail }</td>
			<td></td>
			<td></td>
		</tr>
		<c:if test="${isNeedsPickup}">
			<tr>
				<td>接机信息：</td>
				<td colspan="3">
					<p>航班号-${pickup.flightNo }</p>
					<p>抵达日期-${pickup.arrivedDate }</p>
					<p>抵达时间-${pickup.arrivedTime }</p>
				</td>
			</tr>
		</c:if>
		<c:forEach var="mgoods" items="${order.goodses }">
			<tr>
				<td>商品名称：</td>
				<td><a href="javascript:window.open('${pageContext.request.contextPath}/a/goods/snapshot/${mgoods.id }')">${mgoods.goodsName }</a></td>
				<td>商品数量：</td>
				<td>${mgoods.quantity }</td>
			</tr>
		</c:forEach>
	</table>
	
	<h4><strong>出行人员信息：</strong></h4>
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
	
	<input type="button" value="关闭" onclick="back();" class="btn btn-default"/>
</form:form>
</div>
<script>
	function back(){
		window.close();
	}
</script>
</body>
</html>