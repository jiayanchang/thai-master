<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-combobox-ajax2.js"></script>
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
<h1>New Order</h1>
<c:url var="addUrl" value="/a/order/add/process"/>
<form:form action="${addUrl}" method="POST" modelAttribute="createOrderVo">
	<p class="bg-warning">${message }</p>
	<table class="table">
		<colgroup>
			<col class="col-xs-1">
			<col class="col-xs-3">
			<col class="col-xs-1">
			<col class="col-xs-6">
		</colgroup>
		<tr>
			<td>Contacts：</td>
			<td><form:input path="orderContactor" class="form-control" check="notEmpty" placeholder="Please input contacts..."/></td>
			<td>Hotel Name：</td>
			<td>
				<select class="combobox form-control" id="hotelName" name="hotelName" placeholder="Please input hotel's name...">
					
	            </select>
			</td>
		</tr>
		<tr>
			<td>Contact Number：</td>
			<td><form:input path="orderContactorMobile" class="form-control" check="notEmpty" placeholder="Please input contact number..."/></td>
			<td>Hotel Address：</td>
			<td><form:input id="hotelAddress" path="hotelAddress" class="form-control"  placeholder="Please input hotel's address..."/></td>
		</tr>
		<tr>
			<td>Contact Email：</td>
			<td><form:input path="orderContactorEmail" class="form-control" check="notEmpty email" placeholder="Please input contact email..."/></td>
			<td></td>
			<td class="form-inline">
				<input  style="width:200px" id="hotelTel" name="hotelTel" class="form-control"  placeholder="Please input the hotel tel..."/>
				<input  style="width:260px" name="hotelRoom" class="form-control"  placeholder="Please input the hotel room number..."/>
			</td>
		</tr>
		<tr>
			<td>Merchant：</td>
			<td>
				<select id="merchantId" class="form-control" check="notEmpty" onchange="reloadGoodses();" placeholder="Please select...">
					<option value="0"></option>
					<c:forEach var="merchant" items="${merchants }">
						<option value="${merchant.id}">${merchant.name }</option>
					</c:forEach>
				</select>
			</td>
			<td>Driver：</td>
			<td class="form-inline">
				<input style="width:150px" id="driverName" name="driverName" class="form-control" placeholder="Please input driver's name..."/>
				<input style="width:150px" id="driverMobile" name="driverMobile" class="form-control" placeholder="Please input driver's mobile..."/>
			</td>
		</tr>
		<tr>
			<td>Goods：</td>
			<td>
				<select id="goodsId" name="goodses[0].goodsId" class=" form-control" check="notEmpty" placeholder="Please select...">
					<option value="0"></option>
					<c:forEach var="goods" items="${goodses }">
						<option value="${goods.id}">${goods.title }</option>
					</c:forEach>
				</select>
			</td>
			<td>Execution Date：</td>
			<td><input name="goodses[0].deptDate" tag="date" class="form-control"  check="notEmpty date"  placeholder="Please select date..."/></td>
		</tr>
		<tr>
			<td>Unit Profit：</td>
			<td><input name="goodses[0].price" class="form-control" check="amount"  placeholder="Please input an amount ..."/></td>
			<td>Quantity：</td>
			<td><input name="goodses[0].qty" class="form-control" check="integer"  placeholder="Please input an integer..."/></td>
		</tr>
	</table>
	
	<h4>Guest Details：<a href="javascript:addTraveler();" class="btn btn-success">Add</a> </h4>
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
	
	<%-- <c:if test="${!order.completed }">
		<input type="button" value="确认订单" onclick="complete();"/>
	</c:if> --%>
	<input class="btn btn-primary" type="button" value="Save" onclick="submitForm();"/>
</form:form>
<script>

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
			+ '		<option value="0">male</option>'
			+ '		<option value="1">female</option>'
			+ '		<option value="2">unknown</option>'
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
			+ '		<option value="0">adult</option>'
			+ '		<option value="1">child</option>'
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
			log("removeTraveler i=" + i + ",index=" + index);
			if(i == index) {
				log("removeTraveler i == index");
				val.remove();
			} else if(i > index) {
				var _val = $(val);
				log("removeTraveler i > index");
				_val.find('input[tg=name]').attr("name", 'travelers['+(i-1)+'].fisrtName');
				_val.find('input[tg=name]').attr("name", 'travelers['+(i-1)+'].lastName');
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
	
	function reloadGoodses() {
		jQuery.ajax({
		    type: 'post',
			encoding:"UTF-8",
		    dataType:"json", 
		    contentType: "application/x-www-form-urlencoded;  charset=UTF-8",
		    url: "${pageContext.request.contextPath}/json/"+ $("#merchantId").val() + "/goodses.json",
			success: function(result) {
				//var data = jQuery.parseJSON(result.data.data);
				var data = eval("("+result.data.data+")");
				console.log("t:" + data);
				$("#goodsId").empty();
				for(var i = 0; i < data.length; i++) {
					console.log(data[i].title);
					$("#goodsId").append($("<option value='" + data[i].id + "'>" + data[i].title + "</option>"));
				}
			}
		    
		});
	}
	
	function back(){
		$("form").action = '${pageContext.request.contextPath}/f/order/list';
		$("form").submit();
	}

	var combobox = $('.combobox').combobox();
	$(function() {
		$("form [tag=date]").datepicker({dateFormat:'yy/mm/dd'});
	});

	function submitForm() {
		if(!confirm("Are you sure？")) return false;
		validate(function(){
			$("form").submit();
		});
	}
</script>
