<%@page import="com.magic.thai.db.domain.Goods"%>
<%@page import="com.magic.thai.util.PaginationSupport"%>
<%@ page language="java" pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="content">
<h1>商品列表</h1>
<p>Here you can see the list of the goods, add them, remove or update.</p>

<p>${message}</p>

<br/>
<div class="data">
<c:url var="submitUrl" value="/f/goods/list"/>
<form:form action="${submitUrl}" method="POST">
<table border="1px" cellpadding="0" cellspacing="0" width="100%">
<tr> 
	<td>商品名称：</td>
	<td colspan="3"><input name="title" value="${title }"/></td>
	<td></td>
	<td></td>
</tr>
<tr> 
	<td>出发地：</td>
	<td><input name="dept" value="${dept }"/></td>
	<td>目的地：</td>
	<td><input name="arr" value="${arr }"/></td>
	<td>状态</td>
	<td>
		<select name="status">
			<option value="-1" >全部</option>
			<option value="0">新商品待上架</option>
			<option value="1">待审核</option>
			<option value="2">审核失败</option>
			<option value="3">已上架</option>
			<option value="4">已下架</option>
		</select>
	</td>
	<td><input type="submit" value="submit" class="button2" /></td>
</tr>
</table>
<table border="1px" cellpadding="0" cellspacing="0" width="100%">
	<thead>
	<tr> 
		<th width="10%">ID</th>
		<th width="30%">商家</th>
		<th width="30%">商品名称</th>
		<th width="10%">出发地</th>
		<th width="20%">抵达地</th>
		<th width="10%">行程天数</th>
		<th width="10%">商品库存</th>
		<th width="10%">状态</th>
		<th>Action</th>
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
			<c:if test="${goods.auditing }">
				<a href="${pageContext.request.contextPath}/a/goods/audit/${goods.id} ">审批</a><br/>
			</c:if>
			<c:if test="${goods.deployed }">
				<a href="javascript:cancel(${goods.id }) ">下架</a><br/>
			</c:if>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</div>
<input name="page" type="hidden" value="1"/>
</form:form>
<script type="text/javascript">
function cancel(goodsId) {
	if(confirm("确认下架？")) {
		jQuery.ajax({
		    type: 'POST',
			encoding:"UTF-8",
		    dataType:"json", 
		    data:'id=' + goodsId,
		    contentType: "application/x-www-form-urlencoded;  charset=UTF-8",
		    url: "${pageContext.request.contextPath}/a/cancel.json",
			success: function(result) {
				if(result.data.success) {
					alert("下架成功");
					$("form").submit();
				} else {
					alert(result.data.message);
				}
			}
		});
	}
}

</script>
</div>
