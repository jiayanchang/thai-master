<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validator.js"></script>  

<style>
.custom-combobox {
	position: relative;
	display: inline-block;
}
.custom-combobox-toggle {
	position: absolute;
	top: 0;
	bottom: 0;
	margin-left: -1px;
	padding: 0;
}
.custom-combobox-input {
	margin: 0;
	padding: 5px 10px;
}
</style>
<h1>添加渠道</h1>
<c:url var="addUrl" value="/a/channel/add"/>
<div style="width:900px;">
<form:form action="${addUrl}" method="POST" commandName="channel">
	<table class="table">
		<colgroup>
			<col class="col-xs-1">
			<col class="col-xs-7">
		</colgroup>
		<tr>
			<td>渠道名称 :</td>
			<td><form:input path="name" class="form-control" check="notEmpty" placeholder="请填写渠道名称..."/></td>
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
	
	
	<div class="tab-pane active panel panel-default" id="inv">
		<div class="panel panel-info">
		<div class="panel-heading">按商品匹配 
			<select id="goods" name="goods">
				<option value="0"></option>
				<c:forEach var="goods" items="${goodses }">
					<option value="${goods.id}">${goods.title }</option>
				</c:forEach>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="btn btn-success" href="javascript:addGoods();">添加</a>
		</div>
		<div class="panel-body">
			<table id="goodsInvsTbl" class="table">
				<colgroup>
					<col class="col-xs-3">
					<col class="col-xs-1">
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
					<th>加价金额</th>
					<th>已售数量</th>
					<th>剩余数量</th>
					<th>操作</th>
				</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>
	
	
	<div class="panel panel-info">
	<div class="panel-heading">按商家匹配
		<select id="merchant" name="merchant" class="form-control">
			<option value="0"></option>
			<c:forEach var="merchant" items="${merchants }">
				<option value="${merchant.id}">${merchant.name }</option>
			</c:forEach>
		</select>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a class="btn btn-success" href="javascript:addMerchant();">添加</a>
	</div>
	<div class="panel-body">
		<table id="merchantInvsTbl" class="table">
			<colgroup>
				<col class="col-xs-3">
				<col class="col-xs-2">
				<col class="col-xs-2">
				<col class="col-xs-2">
				<col class="col-xs-1">
				<col class="col-xs-1">
				<col class="col-xs-1">
			</colgroup>
			<thead>
				<tr>
					<th>商家名称</th>
					<th>分配库存</th>
					<th>加价比例</th>
					<th>加价金额</th>
					<th>订单量</th>
					<th>交易额</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	</div>
	</div>
	
	
	<table>
		<tr>
			<td><input type="button" value="提交" onclick="submitForm();" class="btn btn-primary" /></td>
		</tr>
	</table>
	
</form:form>
</div>
<script type="text/javascript">

function submitForm() {
	if(!confirm("是否确定？")) return false;
	validate(function(){
		$("form").submit();
	});
}


function addGoods(){
	var goodsId = $("#goods").val();
	if(goodsId == 0){
		alert("请选择商品");
		return false;
	}
	jQuery.ajax({
	    type: 'GET',
		encoding:"UTF-8",
	    dataType:"json", 
	    contentType: "application/x-www-form-urlencoded;  charset=UTF-8",
	    url: "${pageContext.request.contextPath}/json/goods/" + goodsId,
		success: function(result) {
			var last_tr = $("#goodsInvsTbl tbody tr:last");
			var index = $("#goodsInvsTbl tbody tr").length;
			var html = '<tr idx="' + goodsId + '">'
				+ '<td>' + result.data.data.name + '</td>'
				+ '<td>' + result.data.data.goodsCount + '</td>'
				+ '<td><input type="hidden" tag="gid" name="goodsInvs[' + index + '].goodsId" value="' + goodsId + '"/>'
				+ '<input tag="allocatedAmount" class="form-control" name="goodsInvs[' + index + '].allocatedAmount" check="notEmpty amount" placeholder="请输入一个数字..." value="0.0"/></td>'
				+ '<td><input name="goodsInvs[' + index + '].profitPrice" value="0.0" class="form-control" check="notEmpty amount" placeholder="请输入一个金额..."/></td>'
				+ '<td>' + result.data.data.soldCount + '</td>'
				+ '<td>' + (result.data.data.goodsCount - result.data.data.soldCount) + '</td>'
				+ '<td><a href="javascript:removeGoods(' + goodsId + ');">删除</a></td>'
				+ '</tr>';	
				
			$("#goodsInvsTbl tbody").append($(html));
		}
	});
}

function addMerchant(){
	var merchantId = $("#merchant").val();
	if(merchantId == 0){
		alert("请选择商品");
		return false;
	}
	
	jQuery.ajax({
	    type: 'GET',
		encoding:"UTF-8",
	    dataType:"json", 
	    contentType: "application/x-www-form-urlencoded;  charset=UTF-8",
	    url: "${pageContext.request.contextPath}/json/merchant/" + merchantId,
		success: function(result) {
			var last_tr = $("#merchantInvsTbl tbody tr:last");
			var index = $("#merchantInvsTbl tbody tr").length;
			var html = '<tr idx="' + merchantId + '">'
					+ '<td>' + result.data.data.name + '</td>'
					+ '<td><input type="hidden" tag="mid" name="merchantInvs[' + index + '].merchantId" value="' + merchantId + '"/>'
					+ '<div class="input-group">'
					+ '	<input tag="allocatedAmount" class="form-control" name="merchantInvs[' + index + '].allocatedAmount" value="0.0" check="notEmpty amount" placeholder="请输入一个数字..."/>'
					+ '	<span class="input-group-addon">%</span>'
					+ '</div>'
					+ '</td>'
					
					+ '<td>'
					+ '<div class="input-group">'
					+ '	<input type="type" tag="profitRate" name="merchantInvs[' + index + '].profitRate" class="form-control" value="0.0" check="notEmpty amount" placeholder="请输入一个数字..."/>'
					+ '	<span class="input-group-addon">%</span>'
					+ '</div>'			
					+ '</td>'
					
					+ '<td>'
					+ '	<input type="type" tag="profitPrice" name="merchantInvs[' + index + '].profitPrice" class="form-control" value="0.0" check="notEmpty amount" placeholder="请输入一个金额..."/>'
					+ '</td>'
					
					+ '<td>0</td>'
					+ '<td>0.0</td>'
					+ '<td><a href="javascript:removeMerchant(' + merchantId + ');">删除</a></td>'
					+ '</tr>';	
				
			$("#merchantInvsTbl tbody").append($(html));
		}
	});
}

function removeGoods(goodsId) {
	var tbl = "goodsInvsTbl";
	$("#" + tbl + " tbody tr[idx=" + goodsId + "]").remove();
	var i = 0;
	$("#" + tbl + " tbody tr").each(function(){
		$(this).find("input[tag=id]").attr("name", "goodsInvs[" + i + "].id");
		$(this).find("input[tag=gid]").attr("name", "goodsInvs[" + i + "].goodsId");
		$(this).find("input[tag=allocatedAmount]").attr("name", "goodsInvs[" + i + "].allocatedAmount");
		$(this).find("input[tag=profitPrice]").attr("name", "goodsInvs[" + i + "].profitPrice");
		++i;
	});
}

function removeMerchant(merchantId) {
	var tbl = "merchantInvsTbl";
	$("#" + tbl + " tbody tr[idx=" + merchantId + "]").remove();
	var i = 0;
	$("#" + tbl + " tbody tr").each(function(){
		$(this).find("input[tag=id]").attr("name", "merchantInvs[" + i + "].id");
		$(this).find("input[tag=mid]").attr("name", "merchantInvs[" + i + "].merchantId");
		$(this).find("input[tag=allocatedAmount]").attr("name", "merchantInvs[" + i + "].allocatedAmount");
		$(this).find("input[tag=profitPrice]").attr("name", "merchantInvs[" + i + "].profitPrice");
		$(this).find("input[tag=profitRate]").attr("name", "merchantInvs[" + i + "].profitRate");
		++i;
	});
}


(function( $ ) {
	$.widget( "custom.combobox", {
		_create: function() {
			this.wrapper = $( "<span>" )
				.addClass( "custom-combobox" )
				.insertAfter( this.element );

			this.element.hide();
			this._createAutocomplete();
			this._createShowAllButton();
		},

		_createAutocomplete: function() {
			var selected = this.element.children( ":selected" ),
				value = selected.val() ? selected.text() : "";

			this.input = $( "<input>" )
				.appendTo( this.wrapper )
				.val( value )
				.attr( "title", "" )
				.addClass( "custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left" )
				.autocomplete({
					delay: 0,
					minLength: 0,
					source: $.proxy( this, "_source" )
				})
				.tooltip({
					tooltipClass: "ui-state-highlight"
				});

			this._on( this.input, {
				autocompleteselect: function( event, ui ) {
					ui.item.option.selected = true;
					this._trigger( "select", event, {
						item: ui.item.option
					});
				},

				autocompletechange: "_removeIfInvalid"
			});
		},

		_createShowAllButton: function() {
			var input = this.input,
				wasOpen = false;

			$( "<a>" )
				.attr( "tabIndex", -1 )
				.attr( "title", "Show All Items" )
				.tooltip()
				.appendTo( this.wrapper )
				.button({
					icons: {
						primary: "ui-icon-triangle-1-s"
					},
					text: false
				})
				.removeClass( "ui-corner-all" )
				.addClass( "custom-combobox-toggle ui-corner-right" )
				.mousedown(function() {
					wasOpen = input.autocomplete( "widget" ).is( ":visible" );
				})
				.click(function() {
					input.focus();

					// Close if already visible
					if ( wasOpen ) {
						return;
					}

					// Pass empty string as value to search for, displaying all results
					input.autocomplete( "search", "" );
				});
		},

		_source: function( request, response ) {
			var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
			response( this.element.children( "option" ).map(function() {
				var text = $( this ).text();
				if ( this.value && ( !request.term || matcher.test(text) ) )
					return {
						label: text,
						value: text,
						option: this
					};
			}) );
		},

		_removeIfInvalid: function( event, ui ) {

			// Selected an item, nothing to do
			if ( ui.item ) {
				return;
			}

			// Search for a match (case-insensitive)
			var value = this.input.val(),
				valueLowerCase = value.toLowerCase(),
				valid = false;
			this.element.children( "option" ).each(function() {
				if ( $( this ).text().toLowerCase() === valueLowerCase ) {
					this.selected = valid = true;
					return false;
				}
			});

			// Found a match, nothing to do
			if ( valid ) {
				return;
			}

			// Remove invalid value
			this.input
				.val( "" )
				.attr( "title", value + " didn't match any item" )
				.tooltip( "open" );
			this.element.val( "" );
			this._delay(function() {
				this.input.tooltip( "close" ).attr( "title", "" );
			}, 2500 );
			this.input.autocomplete( "instance" ).term = "";
		},

		_destroy: function() {
			this.wrapper.remove();
			this.element.show();
		}
	});
})( jQuery );

$(function() {
	$( "#merchant" ).combobox();
	$( "#goods" ).combobox();
});

</script>
