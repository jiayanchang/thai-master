<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h1>完善订单</h1>
<c:url var="addUrl" value="/g/order/edit/process"/>
<form:form action="${addUrl}" method="POST" commandName="order">
	<form:hidden path="id"/>
	<table class="table">
		<colgroup>
			<col class="col-xs-2">
			<col class="col-xs-5">
			<col class="col-xs-2">
			<col class="col-xs-5">
		</colgroup>
		<tr>
			<td>订单号：</td>
			<td>${order.orderNo }</td>
			<td>酒店名：</td>
			<td>
	            <form:input path="hotelName" class="form-control"  placeholder="Please input hotel's name......"/>
			</td>
		</tr>
		<tr>
			<td>联系人：</td>
			<td>${order.contractor }</td>
			<td>酒店地址：</td>
			<td><form:input path="hotelAddress" class="form-control"  placeholder="Please input hotel's address..."/></td>
		</tr>
		<tr>
			<td>联系电话：</td>
			<td>${order.contractorMobile }</td>
			<td>房间信息：</td>
			<td class="form-inline">
				<input  style="width:200px" id="hotelTel" name="hotelTel" class="form-control"  placeholder="Please input the hotel tel..."/>
				<input  style="width:260px" name="hotelRoom" class="form-control"  placeholder="Please input the hotel room number..."/>
			</td>
		</tr>
		<tr>
			<td>联系邮箱：</td>
			<td>${order.contractorEmail }</td>
			<td></td>
			<td>></td>
		</tr>
	</table>
	<table class="table table-bordered table-striped " >
		<c:forEach var="mgoods" items="${order.goodses }">
			<tr>
				<td>所买商品：<a href="javascript:window.open('${pageContext.request.contextPath}/f/goods/snapshot/${mgoods.id }')">${mgoods.goodsName }</a></td>
			</tr>
			<tr>
				<td>商品数量：${mgoods.quantity }</td>
			</tr>
			<tr>
				<td>出玩时间：<input name="deptDate"  tag="date" class="form-control" value="${mgoods.deptDate }"  check="notEmpty date"  placeholder="Please select date..."/</td>
			</tr>
			<tr>
				<td><input type="checkbox" name="needsPickup" value="true" onclick="changeNeedsPickup();"> 是否需要接机</td>
			</tr>
			<tr id="pickupInfo" class="form-inline" style="display:none;">
				<td>
					航班号：  <input style="width:100px" name="flightNo" class="form-control"  placeholder="请填写航班号..."/>
					抵达日期：<input style="width:100px" name="arrivedDate" class="form-control"  placeholder="请填写抵达日期..."/>
					抵达时间：<input style="width:100px" name="arrivedTime" class="form-control"  placeholder="请填写抵达时间..."/>
				</td>
			</tr>
		</c:forEach>
	</table>
	
	<h4>出行人员：<a href="javascript:addTraveler();" class="btn btn-success">Add</a> </h4>
	<table id="traveler_table" class="table table-hover">
	
	<thead>
		<tr>
			<th>Name</th>
			<th>Nationality</th>
			<th>Sex</th>
			<!-- <th>证件类型</th>
			<th>证件号码</th>
			<th>证件有效期</th>
			<th>出生日期</th> -->
			<th>Type</th>
			<th>Mobile</th>
			<th>Oper</th>
		</tr>
	</thead>
	<tbody>
	
	</tbody>
	</table>
	
	<input type="button" value="保存" onclick="complete();" class="btn btn-primary"/>
	<input type="button" value="关闭" onclick="back();" class="btn btn-default"/>
</form:form>
<div id="dialog-form" title="Basic dialog" style="display:none;">
	<textarea id="reason" rows="10" cols="40"></textarea>
</div>
<script>
	function back(){
		window.close();
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
	
	function addTraveler(){
		var index = $("#traveler_table tbody tr").length;
		log(index);
		var html = '<tr tg="tr'+index+'">'
			+ '<td  class="form-inline">'
			+ '<input style="width:100px" tg=name name="travelers['+index+'].firstName" class="form-control"  check="notEmpty"  placeholder="Please Input First Name..."/>'
			+ '<input style="width:120px" tg=name name="travelers['+index+'].lastName" class="form-control"  check="notEmpty"  placeholder="Please Input Last Name..."/>'
			+ '</td>'
			+ '<td><input tg=nationality name="travelers['+index+'].nationality" class="form-control"/></td>'
			+ '<td>'
			+ '	<select tg=gender name="travelers['+index+'].gender"  class="form-control">'
			+ '		<option value="0">男</option>'
			+ '		<option value="1">女</option>'
			/* + '		<option value="2">unknown</option>' */
			+ '	</select>'
			+ '</td>'
			/* + '<td>'
			+ '	<select tg=idType name="travelers['+index+'].idType">'
			+ '		<option value="0">身份证</option>'
			+ '		<option value="1">护照</option>'
			+ '		<option value="2">其他</option>'
			+ '	</select>'
			+ '</td>'
			+ '<td><input tg=idNo name="travelers['+index+'].idNo"  style="width:120px;"/></td>'
			+ '<td><input tg=effectiveDate name="travelers['+index+'].effectiveDate"  style="width:100px;" tag="date"/></td>'
			+ '<td><input tg=birth name="travelers['+index+'].birth" style="width:100px;"  tag="date"/></td>' */
			+ '<td>'
			+ '	<select tg=type name="travelers['+index+'].type"  class="form-control">'
			+ '		<option value="0">成人</option>'
			+ '		<option value="1">儿童</option>'
			+ '	</select>'
			+ '</td>'
			+ '<td><input tg=mobile name="travelers['+index+'].mobile"  class="form-control"/></td>'
			+ '<td><a href="javascript:removeTraveler('+index+');" class="btn btn-danger">Delete</a></td>'
			+ '</tr>';
		$("#traveler_table tbody").append($(html));
		$("#traveler_table [tag=date]").datepicker({dateFormat:'yy/mm/dd'});
	}
	addTraveler();

	function removeTraveler(index){
		$("#traveler_table tbody tr").each(function(i, val){
			if(i == index) {
				val.remove();
			} else if(i > index) {
				var _val = $(val);
				_val.find('input[tg=name]').attr("name", 'travelers['+(i-1)+'].name');
				_val.find('input[tg=nationality]').attr("name", 'travelers['+(i-1)+'].nationality');
				_val.find('input[tg=gender]').attr("name", 'travelers['+(i-1)+'].gender');
				/* _val.find('input[tg=idType]').attr("name", 'travelers['+(i-1)+'].idType');
				_val.find('input[tg=idNo]').attr("name", 'travelers['+(i-1)+'].idNo');
				_val.find('input[tg=effectiveDate]').attr("name", 'travelers['+(i-1)+'].effectiveDate');
				_val.find('input[tg=birth]').attr("name", 'travelers['+(i-1)+'].birth'); */
				_val.find('input[tg=type]').attr("name", 'travelers['+(i-1)+'].type');
				_val.find('input[tg=mobile]').attr("name", 'travelers['+(i-1)+'].mobile');
			}
		});
	}
	
	function changeNeedsPickup(){
		if($("#needsPickup").attr("checked")) {
			$("#pickupInfo").show();
		} else {
			$("#pickupInfo").hide();
		}
	}
	
	function changeNeedsPickup(){
		if($("#needsPickup").attr("checked")) {
			$("#pickupInfo").show();
			$("#pickupInfo input").attr("check", "notEmpty");
		} else {
			$("#pickupInfo").hide();
			$("#pickupInfo input").attr("check", "");
		}
	}
</script>
