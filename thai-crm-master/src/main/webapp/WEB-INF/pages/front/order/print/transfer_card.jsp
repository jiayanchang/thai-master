<%@page import="com.magic.thai.security.UserProfile"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.10.2.js"></script>

<style media="all">
	table, table td, table th{border:1px solid #000;border-collapse:collapse;height: 25px}
	.table {
		width: 100%;
		float: inherit;
	}
</style>
<style media="print"> 
	.Noprint {display:none;} 
	.PageBreak {page-break-after: always;} 
</style> 
<div class="Noprint">
<h1>>Print Divers Shuttle Card</h1>
<hr/>
</div>
<div id="print_body">
<table class="table">
	<tr>
		<td rowspan="4"><img height="80px" width="80px" src="${pageContext.request.contextPath}/resources/logo/<%=((UserProfile)session.getAttribute("userprofile")).getMerchant().getId() + ".jpg"  %>"/></td>
		<td c>
			<p>${userprofile.merchant.name }</p>
		</td>
		<td rowspan="3">Voucher</td>
		<td></td>
	</tr>
	<tr>
		<td><p>${userprofile.merchant.address } </p></td>
		<td></td>
	</tr>
	<tr>
		<td>
			<p>
				TEL：${userprofile.merchant.tel }   &nbsp; &nbsp;&nbsp;
				FAX：${userprofile.merchant.fax } 
			</p>
		</td>
		<td>${now }</td>
	</tr>
	<tr>
		<td>OrderNo： ${order.orderNo }</td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td title="Coach name" rowspan="2" colspan="2">TO：</td>
		<td>No. of Pax</td>
		<td>Tour Date</td>
	</tr>
	<tr>
		<td id="person_count">${fn:length(order.travelers)}</td>
		<td></td>
	</tr>
</table>
<table class="table">
	<tr>
		<td rowspan="${fn:length(order.travelers) + 3}" width="80" valign="top">reservation date：${order.createdDateDesc }</td>
		<td colspan="8" align="center">for diving</td>
	</tr>
	<tr>
		<td rowspan="2" width="120px">name</td>
		<td rowspan="2">dive level</td>	
		<td rowspan="2">last dive</td>	
		<td rowspan="2">no.long</td>
		<td colspan="4" align="center">equipment</td>
	</tr>
	<tr>
		<td>reg</td>		
		<td>BCD</td>		
		<td>wet suit</td>		
		<td>fins</td>
	</tr>
	<c:forEach var="traveler" items="${order.travelers }" varStatus="status">
		<tr id="tr_${status.index }">
			<td>${traveler.name } <label class="Noprint"><a href="javascript:remove(${status.index });">Remove</a></label></td>
			<td></td>		
			<td></td>		
			<td></td>		
			<td></td>		
			<td></td>		
			<td></td>		
			<td></td>	
			<td></td>	
		</tr>
	</c:forEach>
	
	<tr>
		<td colspan="5">customer name:${order.contractor }</td>		
		<td colspan="4" rowspan="3" valign="top">Sign：</td>		
	</tr>
	<tr>
		<td colspan="3">pick up:</td>		
		<td colspan="2">room：</td>		
		<td></td>		
	</tr>
	<tr>
		<td colspan="5">time:</td>		
	</tr>
	<tr>
		<td colspan="8" align="center">non refundable deposit or balance payment upon booking</td>	           
		<td>thank you</td>		
	</tr>
	<tr>
		<td colspan="9"> </td>		
	</tr>
</table>
</div>
<div class="Noprint">
	<input type="button" value="Print" onclick="printf();" class="btn btn-primary"/>
	<input type="button" value="Reset" onclick="reset();" class="btn btn-primary"/>
	<input type="button" value="Close" onclick="back();" class="btn btn-default"/>
</div>
<script>
	function back(){
		$("form").attr('action', '${pageContext.request.contextPath}/f/order/list');
		$("form").submit();
	}
	
	function remove(index) {
		$("#tr_" + index).remove();
		$("#person_count").html(parseInt($("#person_count").html()) - 1);
	}
	
	function reset(){
		window.location.reload();
	}
	
	function back(){
		window.close();
	}
	function pre(){

	}
	function printf(){
		window.print();
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
