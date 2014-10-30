<%@page import="com.magic.thai.security.UserProfile"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<TITLE>Print-Pick Card</TITLE> 

<script src="http://code.jquery.com/jquery-migrate-1.1.0.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery/print/jquery-1.4.4.min.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery/print/jquery.jqprint-0.3.js"></script>
<style media="all">
	table, table td, table th{border:0px solid #000;border-collapse:collapse;height: 50px;text-align: center; }
	.table {
		width: 100%;
	}
</style>
<style media="screen"> 
	.Noprintscreen {display:none;} 
	.PageBreak {page-break-after: always;} 
</style> 
<style media="print"> 
	.Noprint {display:none;} 
	.PageBreak {page-break-after: always;} 
</style> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/frame.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<div class="Noprint">
		<h2>Pick Card</h2>
		<input type="button" value="Print" onclick="printf();" class="btn btn-primary"/>
		<input type="button" value="Close" onclick="back();" class="btn btn-default"/>
		<hr/>
	</div>
	<table class="table">
		<tr style="height: 150px;" valign="bottom">
			<td style='font-size:80px;font-family: 楷体;'> ${userprofile.merchant.name } </td>
		</tr>
		<tr style="height: 150px;">
			<td style='font-size:50px;font-family: Georgia'>${userprofile.merchant.nameEn }</td>
		</tr>
		<tr style="height: 100px;">
			<td style='font-size:100px;font-family: 楷体;'>${order.contractor }</td>
		</tr>
		<tr class="Noprint">
			<td>
				<input style="width:180px" id="fightNo" class="form-control"  placeholder="FlightNo"/>
				<input style="width:180px" id="arrDate" class="form-control"  placeholder="ArrivedDate"/>
				<input style="width:180px" id="arrTime" class="form-control"  placeholder="ArrivedTime"/>
			</td>
		</tr>
		<tr class="Noprintscreen">
			<td style='font-size:40px;font-family: Georgia'>
				<span id="pv"></span>
			</td>
		</tr>
		<tr>
			<td  style='font-size:30px;font-family: Georgia'> ${order.contractorMobile } </td>
		</tr>
		<tr>
			<td>  </td>
		</tr>
	</table>
<script>
	function back(){
		window.close();
	}
	function printf(){
		$("#pv").html( "FlightNo：" + $("#fightNo").val() + "&nbsp;&nbsp;&nbsp;" +  $("#arrDate").val()  + "&nbsp;&nbsp;&nbsp;" +  $("#arrTime").val() );
		window.print();
	}
	
	
	function saveinfo() {
		
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

</body>
</html>