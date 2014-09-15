<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

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

	<div class="content">
		<h1>添加渠2道</h1>
		<c:url var="addUrl" value="/a/channel/add"/>
		<form:form action="${addUrl}" method="POST" commandName="channel">
			<table>
				<tr>
					<td>渠道名称 :</td>
					<td><form:input path="name" /></td>
					<td><form:errors path="name" cssClass="error" /></td>
					<td>运营人 :</td>
					<td>
						<form:select path="operatorId">
							<option value="0"></option>
							<c:forEach var="user" items="${users }">
								<form:option value="${user.id}">${user.name }</form:option>
							</c:forEach>
						</form:select>
					</td>
					<td><form:errors path="operatorId" cssClass="error" /></td>
				</tr>
			</table>
			按商品匹配：
			<select id="goods" name="goods">
				<option value="0"></option>
				<c:forEach var="goods" items="${goodses }">
					<option value="${goods.id}">${goods.title }</option>
				</c:forEach>
			</select>
			<br>
			<a href="javascript:addGoods();">添加</a>
			<table id="goodsInvsTbl">
				<thead>
				<tr>
					<th>商品名称</th>
					<th>总库存</th>
					<th>分配库存</th>
					<th>已售数量</th>
					<th>商品剩余数量</th>
					<th>操作</th>
				</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			按商家匹配：
			<select id="merchant" name="merchant">
				<option value="0"></option>
				<c:forEach var="merchant" items="${merchants }">
					<option value="${merchant.id}">${merchant.name }</option>
				</c:forEach>
			</select>
			<br>
			<a href="javascript:addMerchant();">添加</a>
			<table id="merchantInvsTbl">
				<thead>
					<tr>
						<th>商家名称</th>
						<th>总库存</th>
						<th>分配库存</th>
						<th>已售数量</th>
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
					<td><input type="submit" value="submit" class="button2" /></td>
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
	    type: 'POST',
		encoding:"UTF-8",
	    dataType:"json", 
	    contentType: "application/x-www-form-urlencoded;  charset=UTF-8",
	    url: "${pageContext.request.contextPath}/a/goods/" + id + ".json",
		success: function(result) {
			var last_tr = $("#goodsInvsTbl tbody tr:last");
			var index = last_tr.length > 0 ? last_tr.attr("index") : 0;
			var html = '<tr index="' + index + '">'
				+ '<td>' + result.goods.title + '</td>'
				+ '<td>' + result.goods.goodsCount + '</td>'
				+ '<td><input type="hidden" name="goodsInvs[' + index + '].goodsId" value="' + result.goods.id + '"/>'
				+ '<input name="goodsInvs[' + index + '].allocatedAmount"/></td>'
				+ '<td>' + result.goods.soldCount + '</td>'
				+ '<td>' + (result.goods.goodsCount - result.goods.soldCount) + '</td>'
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
			var last_tr = $("#merchantInvsTbl tbody tr:last");
			var index = last_tr.length > 0 ? last_tr.attr("index") : 0;
			var html = '<tr index="' + index + '">'
					+ '<td>' + result.merchant.name + '</td>'
					+ '<td></td>'
					+ '<td><input type="hidden" name="merchantInvs[' + index + '].merchantId" value="' + result.merchant.id + '"/>'
					+ '<input name="merchantInvs[' + index + '].allocatedAmount"/></td>'
					+ '<td></td>'
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
