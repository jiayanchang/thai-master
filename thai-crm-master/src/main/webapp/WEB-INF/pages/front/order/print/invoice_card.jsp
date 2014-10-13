<%@page import="com.magic.thai.security.UserProfile"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

<h1 class="Noprint">打印发票</h1>
<div id="print_body">
	<table class="table">
		<tr >
			<td rowspan="5" valign="middle" align="center">
				<img height="80px" width="80px" src="${pageContext.request.contextPath}/resources/logo/<%=((UserProfile)session.getAttribute("userprofile")).getMerchant().getId() + ".jpg"  %>"/>
			</td>
			<td align="center">
				<p>公司名称：${userprofile.merchant.name }</p>
			</td>
		</tr>
		<tr>
			<td align="center"><p>公司英文名称：${userprofile.merchant.nameEn }</p></td>
		</tr>
		<tr>
			<td align="center"><p>公司地址：${userprofile.merchant.address } </p></td>
		</tr>
		<tr>
			<td align="center">
				<p>
					电话：${userprofile.merchant.tel }   &nbsp; &nbsp;&nbsp;
					FAX：${userprofile.merchant.fax } 
				</p>
			</td>
		</tr>
		<tr>
			<td align="center">订单号：${order.orderNo }</td>
		</tr>
	</table>
	<table class="table">
		<tr>
			<td>客户（CUSTOMER）：${order.contractor }</td>
			<td rowspan="2">发票日期（DATE）：${now }</td>
		</tr>
		<tr>
			<td>地址（ADDRESS）：</td>
		</tr>
	</table>	
	<table class="table">
		<thead>
			<tr>
				<th>ITEM</th>
				<th width="300px">详情（Details ）</th>
				<th>数量（QTY）</th>
				<th>单价（Unit Price）</th>
				<th>总额（AMOUNT)</th>
			</tr>
		</thead>
		<c:forEach var="mgoods" items="${order.goodses }" varStatus="status">
		<tr>
			<td>${status.index }</td>
			<td>${mgoods.goodsName }</td>
			<td>${mgoods.quantity }</td>
			<td>${mgoods.amount }</td>
			<td>${mgoods.totalAmount}</td>
		</tr>
		</c:forEach>
		<tr>
			<td colspan="4" align="right">小计（SUB TOTAL）</td>
			<td colspan="1">${order.amount }</td>
		</tr>
		<tr>
			<td colspan="4" align="right">增值税（VAT）7%</td>
			<td colspan="1">${order.vatAmount }</td>
		</tr>
		<tr>
			<td colspan="4" align="right">税后金额（AMOUNT EXCLUDED VAT）</td>
			<td colspan="1">${order.incomeAmount }</td>
		</tr>
	</table>	
	<table class="table">
		<tr>
			<td><input type="checkbox"/>现金（Cash）</td>
			<td></td>
			<td width="200px"></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td><input type="checkbox"/>支票（Cheque）</td>
			<td>Bank</td>
			<td></td>
			<td>Cheque no.</td>
			<td colspan="2"  width="200px"></td>
			<td>Cheque Date</td>
			<td width="200px"></td>
		</tr>
		<tr>
			<td><input type="checkbox"/>信用卡（Credit Card）</td>
			<td>Card Type</td>
			<td></td>
			<td>no.</td>
			<td colspan="4"></td>
		</tr>
		<tr>
			<td colspan="6" align="right">Date: </td>
			<td colspan="2">${now }</td>
		</tr>
		<tr>
			<td colspan="6" align="right">Receiver:</td>
			<td colspan="2"> ${userprofile.user.name }</td>
		</tr>
	</table>
</div>
<div  class="Noprint">
	<input type="button" value="打印" onclick="printf();" class="btn btn-primary"/>
	<input type="button" value="关闭" onclick="back();" class="btn btn-default"/>
</div>
<script>
	function back(){
		window.close();
	}
	function pre(){
		
	}
	function printf(){
		window.print();
	}
	
	
</script>
