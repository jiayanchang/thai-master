<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-combobox-ajax.js"></script>
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
<h1>新建订单</h1>
<c:url var="addUrl" value="/c/order/add/process"/>
<form:form action="${addUrl}" method="POST" modelAttribute="createOrderVo">
	<c:if test="${not empty message}">
		<div class="alert alert-danger" role="alert">${message }</div>
	</c:if>
	<table class="table">
		<colgroup>
			<col class="col-xs-1">
			<col class="col-xs-3">
			<col class="col-xs-1">
			<col class="col-xs-4">
		</colgroup>
		<tr>
			<td>联系人：</td>
			<td><form:input path="orderContactor" class="form-control" check="notEmpty" placeholder="请输入联系人..."/></td>
			<td>酒店名：</td>
			<td>
				<select class="combobox form-control" id="hotelName" name="hotelName" placeholder="请输入酒店名称...">
					
	            </select>
			</td>
		</tr>
		<tr>
			<td>联系电话：</td>
			<td><form:input path="orderContactorMobile" class="form-control" check="notEmpty" placeholder="请输入联系电话..."/></td>
			<td>酒店地址：</td>
			<td><form:input id="hotelAddress" path="hotelAddress" class="form-control"  placeholder="请输入酒店地址..."/></td>
		</tr>
		<tr>
			<td>联系邮箱：</td>
			<td><form:input path="orderContactorEmail" class="form-control" check="notEmpty email" placeholder="请输入联系邮箱..."/></td>
			<td></td>
			<td class="form-inline">
				<input  style="width:180px" id="hotelTel" name="hotelTel" class="form-control"  placeholder="请输入酒店电话..."/>
				<input  style="width:230px" name="hotelRoom" class="form-control"  placeholder="请输入酒店房间号..."/>
			</td>
		</tr>
		<tr>
			<td>出玩时间：</td>
			<td><input id="deptDate" name="goodses[0].deptDate" tag="date" class="form-control" onkeyup="updateGoodsPrice();" value="<fmt:formatDate value="${deptDate }" type="date"  pattern="yyyy/MM/dd"/>" check="notEmpty date"  placeholder="请选择时间..."/></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>商品：</td>
			<td>
				<select id="goodsId" name="goodses[0].goodsId" class=" form-control" check="notEmpty" onchange="updateGoodsPrice();" placeholder="请选择...">
					<option value="0"></option>
					<c:forEach var="goods" items="${goodses }">
						<option value="${goods.id}">${goods.title }</option>
					</c:forEach>
				</select>
			</td>
			<td></td>
			<td class="form-inline">
			</td>
		</tr>
		<tr>
			<td>单价：</td>
			<td>
				<input id="priceRo" class="form-control" check="amount" disabled/>
				<input id="price" name="goodses[0].price" type="hidden"/>
			</td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>数量：</td>
			<td><input name="goodses[0].qty" class="form-control" check="integer"  placeholder="请填写数量..."/></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td colspan="4"><input type="checkbox" id="needsPickup" name="goodses[0].needsPickup" value="true" onclick="changeNeedsPickup();"> 需要接机服务</td>
		</tr>
		<tr id="pickupInfo" class="form-inline" style="display:none;">
			<td colspan="4">
				航班号：  <input style="width:150px" name="goodses[0].flightNo" class="form-control"  placeholder="输入航班号..."/>
				抵达日期：<input style="width:150px" name="goodses[0].arrivedDate" class="form-control"  placeholder="输入抵达日期..."/>
				抵达时间：<input style="width:150px" name="goodses[0].arrivedTime" class="form-control"  placeholder="输入抵达时间..."/>
			</td>
		</tr>
	</table>
	
	<h4>游客信息：<a href="javascript:addTraveler();" class="btn btn-success">添加</a> </h4>
	<table id="traveler_table" class="table table-hover">
	
	<thead>
		<tr>
			<th>姓名</th>
			<th>国籍</th>
			<th>性别</th>
			<!-- <th>证件类型</th>
			<th>证件号码</th>
			<th>证件有效期</th>
			<th>出生日期</th> -->
			<th>类型</th>
			<th>电话</th>
			<th>操作</th>
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
			+ '<input style="width:100px" tg=name name="travelers['+index+'].firstName" class="form-control"  check="notEmpty"  placeholder="请输入姓氏..."/>'
			+ '<input style="width:120px" tg=name name="travelers['+index+'].lastName" class="form-control"  check="notEmpty"  placeholder="请输入名字..."/>'
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
				_val.find('input[tg=name]').attr("name", 'travelers['+(i-1)+'].name');
				_val.find('input[tg=nationality]').attr("name", 'travelers['+(i-1)+'].nationality');
				_val.find('input[tg=gender]').attr("name", 'travelers['+(i-1)+'].gender');
				_val.find('input[tg=idType]').attr("name", 'travelers['+(i-1)+'].idType');
				_val.find('input[tg=idNo]').attr("name", 'travelers['+(i-1)+'].idNo');
				_val.find('input[tg=effectiveDate]').attr("name", 'travelers['+(i-1)+'].effectiveDate');
				_val.find('input[tg=birth]').attr("name", 'travelers['+(i-1)+'].birth');
				_val.find('input[tg=type]').attr("name", 'travelers['+(i-1)+'].type');
				_val.find('input[tg=mobile]').attr("name", 'travelers['+(i-1)+'].mobile');
			}
		});
	}
	
	
	function back(){
		$("form").action = '${pageContext.request.contextPath}/c/order/list';
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
	
	function changeNeedsPickup(){
		if($("#needsPickup").is(':checked')) {
			$("#pickupInfo").show();
			$("#pickupInfo input").attr("check", "notEmpty");
		} else {
			$("#pickupInfo").hide();
			$("#pickupInfo input").attr("check", "");
		}
	}
	
	function updateGoodsPrice() {
		jQuery.ajax({
		    type: 'GET',
			encoding:"UTF-8",
		    dataType:"json", 
		    contentType: "application/x-www-form-urlencoded;  charset=UTF-8",
		    url: "${pageContext.request.contextPath}/json/goods/" + $("#goodsId").val() + "/deptdate?date=" + $("#deptDate").val(),
			success: function(result) {
				$("#priceRo").val(result.data.data.price);
				$("#price").val(result.data.data.price);
			}
		});
	}
</script>
