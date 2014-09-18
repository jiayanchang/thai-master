<%@page import="com.magic.thai.db.domain.Goods"%>
<%@page import="com.magic.thai.util.PaginationSupport"%>
<%@ page language="java" pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:url var="submitUrl" value="/a/order/list"/>
<form:form action="${submitUrl}" method="POST" commandName="vo">
<h1>List of Order</h1>
<p>${message}</p>
<br/>
<table border="1px" cellpadding="0" cellspacing="0" width="100%">
	<thead>
	<tr> 
		<th width="5%">ID</th>
		<th width="10%">订单号</th>
		<th width="10%">所属商家</th>
		<th width="5%">渠道</th>
		<th width="10%">下单时间</th>
		<th width="5%">联系人</th>
		<th width="10%">联系电话</th>
		<th width="10%">订单状态</th>
		<th width="10%">处理人</th>
		<th>操作</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${ps.items}" var="order" varStatus="index">
		<tr>
			<td>${order.id}</td>
			<td>${order.orderNo}</td>
			<td>${order.merchantName}</td>
			<td>${order.channelName}</td>
			<td>${order.createdDate}</td>
			<td>${order.contractor}</td>
			<td>${order.contractorTel}</td>
			<td>${order.statusDesc}</td>
			<td>${order.lastOperatorName}</td>
			<td>
				<a href="javascript:openDialog(${order.id}); ">编辑备注</a>
				<a href="${pageContext.request.contextPath}/a/order/${order.id} ">详情</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<%@ include file="../../page.jsp"%>
</form:form>
<div id="dialog-form" title="Basic dialog">
	<textarea id="reason" rows="7" cols="32"></textarea>
</div>

<script>

function openDialog(id, orderNo) {
	$("#reason").val("");
	jQuery.ajax({
	    type: 'POST',
		encoding:"UTF-8",
	    dataType:"json", 
	    data : 'orderNo=' + orderNo,
	    contentType: "application/x-www-form-urlencoded;  charset=UTF-8",
	    url: "${pageContext.request.contextPath}/a/order/lock.json",
		success: function(result) {
			if(result.data.success) {
				dialog.dialog( "open" );
			} else {
				alert(result.data.message);				
			}
		}
	});
	
	
	var dialog = $( "#dialog-form" ).dialog({
	    autoOpen: false,
	    height: 300,
	    width: 350,
	    title:'原因',
	    modal: true,
	    buttons: {
	      "提交": function(){
	    	  jQuery.ajax({
	    		    type: 'POST',
	    			encoding:"UTF-8",
	    		    dataType:"json", 
	    		    data : 'id=' + id + "&reason=" + $("#reason"),
	    		    contentType: "application/x-www-form-urlencoded;  charset=UTF-8",
	    		    url: "${pageContext.request.contextPath}/a/order/proc.json",
	    			success: function(result) {
	    				if(result.data.success) {
	    					dialog.dialog( "close" );
	    					$("form").submit();
	    				} else {
	    					alert(result.data.message);				
	    				}
	    			}
	    		});
	      },
	      "取消" : function() {
	        dialog.dialog( "close" );
	      }
	    },
	    close: function() {
	  	  dialog.dialog( "close" );
	    }
	});
}
</script>