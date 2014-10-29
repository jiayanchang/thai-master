<%@page import="com.magic.thai.db.domain.Goods"%>
<%@page import="com.magic.thai.util.PaginationSupport"%>
<%@ page language="java" pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1>商品列表</h1>
<br/>
<c:url var="submitUrl" value="/a/goods/list"/>
<form:form action="${submitUrl}" method="POST" commandName="vo">
<c:if test="${not empty message}">
	<div class="alert alert-success" role="alert">${message }</div>
</c:if>
<table class="table table-condensed">
<tr> 
	<td>商品名称：</td>
	<td colspan="3"><form:input path="titleKeyword" class="form-control"/></td>
	<td>状态</td>
	<td>
		<select name="status" class="form-control">
			<option value="-1" >全部</option>
			<option value="1">待审核</option>
			<option value="3">已上架</option>
			<option value="4">已下架</option>
		</select>
	</td>
</tr>
<tr> 
	<td>商家编号：</td>
	<td><form:input path="merchantId" class="form-control"/></td>
	<td>商家名称：</td>
	<td><form:input path="merchantName" class="form-control"/></td>
	<td colspan="2"><input type="submit" value="查询" class="btn btn-primary btn-block" /></td>
</tr>
</table>
<table class="table table-striped table-hover">
	<colgroup>
		<col class="col-xs-1">
		<col class="col-xs-2">
		<col class="col-xs-2">
		<col class="col-xs-1">
		<col class="col-xs-1">
		<col class="col-xs-1">
		<col class="col-xs-1">
		<col class="col-xs-1">
		<col class="col-xs-8">
	</colgroup>
	<thead>
	<tr> 
		<th>ID</th>
		<th>商家</th>
		<th>商品名称</th>
		<th>出发地</th>
		<th>抵达地</th>
		<th>行程天数</th>
		<th>商品库存</th>
		<th>状态</th>
		<th>操作</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${ps.items}" var="goods" varStatus="index">
		<tr>
			<td>${goods.id}</td>
			<td>${goods.merchantName}</td>
			<td><a href="${pageContext.request.contextPath}/a/goods/${goods.id}">${goods.title}</a></td>
			<td>${goods.dept}</td>
			<td>${goods.arrived}</td>
			<td>${goods.travelDays}</td>
			<td>${goods.goodsCount}</td>
			<td>${goods.statusDesc}</td>
			<td>
				<a class="btn btn-info" href="${pageContext.request.contextPath}/a/goods/edit/${goods.id} ">编辑</a>
				<c:if test="${goods.auditing }">
					<a class="btn btn-success" href="${pageContext.request.contextPath}/a/goods/audit/${goods.id} ">审批</a>
				</c:if>
				<c:if test="${goods.deployed }">
					<a id="cancel-btn" class="btn btn-danger" val="${goods.id }" href="javascript:openDialog(${goods.id}); ">下架</a>
				</c:if>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>

<%@ include file="../../page.jsp"%>

</form:form>
<div id="dialog-form" title="Basic dialog">
	<input type="hidden" id="goodsId"/>
	<textarea id="reason" rows="10" cols="40"></textarea>
</div>
<script type="text/javascript">
	var dialog = $( "#dialog-form" ).dialog({
	    autoOpen: false,
	    height: 300,
	    width: 350,
	    title:'原因',
	    modal: true,
	    buttons: {
	      "确认下架": function(){
	    	  jQuery.ajax({
		  		    type: 'POST',
		  			encoding:"UTF-8",
		  		    dataType:"json", 
		  		    data:'id=' + $("#goodsId").val() + '&reason=' + $("#reason").val(),
		  		    contentType: "application/x-www-form-urlencoded;  charset=UTF-8",
		  		    url: "${pageContext.request.contextPath}/a/goods/cancel.json",
		  			success: function(result) {//TODO 没执行success方法
		  				dialog.dialog( "close" );
		  				if(result.data.success) {
		  					alert("下架成功");
		  					window.location.reload();
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

function openDialog(id) {
	$("#goodsId").val(id);
	dialog.dialog( "open" );
}
</script>
