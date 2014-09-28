<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/crm/js/bootstrap-combobox-ajax.js"></script>
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
<h1>新增订单</h1>
<c:url var="addUrl" value="/f/order/add/process"/>
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
			<td>联系人：</td>
			<td><form:input path="orderContactor" class="form-control"/></td>
			<td>酒店名称：</td>
			<td>
				<select class="combobox form-control" id="hotelName" name="hotelName" onBlur="">
					
	            </select>
			</td>
		</tr>
		<tr>
			<td>联系电话：</td>
			<td><form:input path="orderContactorMobile" class="form-control"/></td>
			<td>酒店地址：</td>
			<td><form:input id="hotelAddress" path="hotelAddress" class="form-control"/></td>
		</tr>
		<tr>
			<td>联系邮箱：</td>
			<td><form:input path="orderContactorEmail" class="form-control"/></td>
			<td></td>
			<td class="form-inline">
				<input  style="width:150px" id="hotelTel" name="hotelTel" class="form-control" placeholder="酒店电话"/>
				<input  style="width:150px" name="hotelRoom" class="form-control" placeholder="酒店房间号"/>
			</td>
		</tr>
		<tr>
			<td>商品：</td>
			<td>
				<select name="goodses[0].goodsId" class=" form-control">
					<option value="0"></option>
					<c:forEach var="goods" items="${goodses }">
						<option value="${goods.id}">${goods.title }</option>
					</c:forEach>
				</select>
			</td>
			<td>司机：</td>
			<td class="form-inline">
				<input style="width:150px" id="driverName" name="driverName" class="form-control" placeholder="姓名"/>
				<input style="width:150px" id="driverMobile" name="driverMobile" class="form-control" placeholder="电话"/>
			</td>
		</tr>
		<tr>
			<td>总价：</td>
			<td><input name="goodses[0].price" class="form-control"/></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>数量：</td>
			<td><input name="goodses[0].qty" class="form-control"/></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>执行时间：</td>
			<td><input name="goodses[0].deptDate" tag="date" class="form-control"/></td>
			<td></td>
			<td></td>
		</tr>
	</table>
	
	<%-- <h4>商品：</h4>
	<table id="goods_tbl" class="table table-hover">
		<thead>
			<tr>
				<th>商品</th>
				<th>总价</th>
				<th>数量</th>
				<th>执行时间</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		<tr index="0">
			<td>
				<select name="goodses[0].goodsId">
					<option value="0"></option>
					<c:forEach var="goods" items="${goodses }">
						<option value="${goods.id}">${goods.title }</option>
					</c:forEach>
				</select>
			</td>
			<td><input name="goodses[0].price"/></td>
			<td><input name="goodses[0].qty"/></td>
			<td><input name="goodses[0].deptDate" tag="date"/></td>
		</tr>
		</tbody>
	</table> --%>
	
	<h4>旅客详情：<a href="javascript:addTraveler();" class="btn btn-success">添加</a> </h4>
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
			<th>乘客类型</th>
			<th>手机号码</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
	
	</tbody>
	</table>
	
	<%-- <c:if test="${!order.completed }">
		<input type="button" value="确认订单" onclick="complete();"/>
	</c:if> --%>
	<input class="btn btn-primary" type="button" value="保存" onclick="save();"/>
</form:form>
<script>

	function addTraveler(){
		var index = $("#traveler_table tbody tr").length;
		log(index);
		var html = '<tr tg="tr'+index+'">'
			+ '<td><input tg=name name="travelers['+index+'].name" class="form-control"/></td>'
			+ '<td><input tg=nationality name="travelers['+index+'].nationality" class="form-control"/></td>'
			+ '<td>'
			+ '	<select tg=gender name="travelers['+index+'].gender"  class="form-control">'
			+ '		<option value="0">男</option>'
			+ '		<option value="1">女</option>'
			+ '		<option value="2">未知</option>'
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
			+ '		<option value="'+index+'">成人</option>'
			+ '		<option value="1">儿童</option>'
			+ '	</select>'
			+ '</td>'
			+ '<td><input tg=mobile name="travelers['+index+'].mobile"  class="form-control"/></td>'
			+ '<td><a href="javascript:removeTraveler('+index+');" class="btn btn-danger">删除</a></td>'
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
		$("form").action = '${pageContext.request.contextPath}/f/order/list';
		$("form").submit();
	}
	
	function save() {
		if(!confirm("是否确定？")) return false;
		$("form").submit();
		/* jQuery.ajax({
		    type: 'POST',
			encoding:"UTF-8",
		    dataType:"json", 
		    data : 'id=${order.id}',
		    contentType: "application/x-www-form-urlencoded;  charset=UTF-8",
		    url: "${pageContext.request.contextPath}/f/order/confirm.json",
			success: function(result) {
				alert(result.data.message);
				window.location.reload();
			}
		}); */
	}

	var combobox = $('.combobox').combobox();
	$(function() {
		$("form [tag=date]").datepicker({dateFormat:'yy/mm/dd'});
	});
	
</script>
