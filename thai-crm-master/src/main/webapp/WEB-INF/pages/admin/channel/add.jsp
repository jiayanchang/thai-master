<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<script src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-combobox.js"></script>

<h1>添加渠道</h1>
<c:url var="addUrl" value="/a/channel/add"/>
<div style="width:600px;">
<form:form action="${addUrl}" method="POST" commandName="channel">
	<table class="table">
		<colgroup>
			<col class="col-xs-1">
			<col class="col-xs-7">
		</colgroup>
		<tr>
			<td>渠道名称 :</td>
			<td><form:input path="name" class="form-control"/></td>
		<tr>
		</tr>
			<td>运营人 :</td>
			<td>
				<form:select path="operatorId" class="form-control">
					<c:forEach var="user" items="${users }">
						<form:option value="${user.id}">${user.name }</form:option>
					</c:forEach>
				</form:select>
			</td>
		</tr>
	</table>
	<h4><strong>库存分配</strong></h4>
	
	按商品匹配：
	<table class="table">
		<tr>
			<td>
				<select id="goods" name="goods" class="combobox form-control">
					<option value="0"></option>
					<c:forEach var="goods" items="${goodses }">
						<option value="${goods.id}">${goods.title }</option>
					</c:forEach>
				</select>
			</td>
			<td>
				<a href="javascript:addGoods();" class="btn btn-success">添加</a>
			</td>
		</tr>
	</table>
	<table id="goodsInvsTbl" class="table">
		<colgroup>
			<col class="col-xs-2">
			<col class="col-xs-1">
			<col class="col-xs-1">
			<col class="col-xs-1">
			<col class="col-xs-1">
			<col class="col-xs-1">
		</colgroup>
		<thead>
		<tr>
			<th>商品名称</th>
			<th>总库存</th>
			<th>分配库存</th>
			<th>已售数量</th>
			<th>商品剩余</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	按商家匹配：
	<table class="table">
		<tr>
			<td>
				<select id="merchant" name="merchant" class="combobox form-control">
					<option value="0"></option>
					<c:forEach var="merchant" items="${merchants }">
						<option value="${merchant.id}">${merchant.name }</option>
					</c:forEach>
				</select>
			</td>
			<td>
				<a href="javascript:addMerchant();" class="btn btn-success">添加</a>
			</td>
		</tr>
	</table>
	<table id="merchantInvsTbl" class="table">
		<colgroup>
			<col class="col-xs-1">
			<col class="col-xs-2">
			<col class="col-xs-1">
			<col class="col-xs-1">
			<col class="col-xs-1">
		</colgroup>
		<thead>
			<tr>
				<th>商家名称</th>
				<th>分配库存</th>
				<th>订单量</th>
				<th>交易额</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	
	<table>
		<tr>
			<td><input type="submit" value="提交"  class="btn btn-primary"/></td>
		</tr>
	</table>
</form:form>
</div>
<script type="text/javascript">

function addGoods(){
	var id = $("#goods").val();
	if(id == 0){
		alert("请选择商品");
		return false;
	}
	
	jQuery.ajax({
	    type: 'GET',
		encoding:"UTF-8",
	    dataType:"json", 
	    contentType: "application/x-www-form-urlencoded;  charset=UTF-8",
	    url: "${pageContext.request.contextPath}/json/goods/" + id + ".json",
		success: function(result) {
			var last_tr = $("#goodsInvsTbl tbody tr:last");
			var index = last_tr.length > 0 ? last_tr.attr("index") : 0;
			var html = '<tr index="' + index + '">'
				+ '<td>' + result.data.data.name + '</td>'
				+ '<td>' + result.data.data.goodsCount + '</td>'
				+ '<td><input type="hidden" name="goodsInvs[' + index + '].goodsId" value="' + result.data.data.id + '"/>'
				+ '<input name="goodsInvs[' + index + '].allocatedAmount" class="form-control"/></td>'
				+ '<td>' + result.data.data.soldCount + '</td>'
				+ '<td>' + (result.data.data.goodsCount - result.data.data.soldCount) + '</td>'
				+ '<td><a href="javascript:removeGoods(' + index + ');">删除</a></td>'
				+ '</tr>';	
				
			$("#goodsInvsTbl tbody").append($(html));
		}
	});
}
function addMerchant(){
	var id = $("#merchant").val();
	if(id == 0){
		alert("请选择商品");
		return false;
	}
	jQuery.ajax({
	    type: 'POST',
		encoding:"UTF-8",
	    dataType:"json", 
	    contentType: "application/x-www-form-urlencoded;  charset=UTF-8",
	    url: "${pageContext.request.contextPath}/a/merchant/" + id + ".json",
		success: function(result) {
			var last_tr = $("#merchantInvsTbl tbody tr");
			var index = $("#merchantInvsTbl tbody tr").length;
			var html = '<tr index="' + index + '">'
					+ '<td>' + result.merchant.name + '</td>'
					+ '<td><input type="hidden" name="merchantInvs[' + index + '].merchantId" value="' + result.merchant.id + '"/>'
					
					+ '<div class="input-group">'
					+ '	<input tag="allocatedAmount" class="form-control" name="merchantInvs[' + index + '].allocatedAmount" value="0.0"/>'
					+ '	<span class="input-group-addon">%</span>'
					+ '</div>'
					
					+ '<td></td>'
					+ '<td></td>'
					+ '<td><a href="javascript:removeGoods(' + index + ');">删除</a></td>'
					+ '</tr>';	
				
			$("#merchantInvsTbl tbody").append($(html));
		}
	});
}

function removeGoods(index) {
	$("#goodsInvsTbl tbody tr[index=" + index + "]").remove();
}
/* $('.combobox').combobox(); */
</script>
