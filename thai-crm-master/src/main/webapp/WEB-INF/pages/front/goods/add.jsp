<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<script type="text/javascript" src="${pageContext.request.contextPath}/fckeditor/fckeditor.js"></script>  
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validator.js"></script>  
	<!-- 新建商品 -->
	<h1>New Goods</h1>
	<c:url var="addUrl" value="/f/goods/add"/>
	<form:form action="${addUrl}" method="POST" commandName="goods"   enctype="multipart/form-data">
		<form:hidden path="id"/>
		<table class="table">
			<colgroup>
				<col class="col-xs-1">
				<col class="col-xs-3">
				<col class="col-xs-1">
				<col class="col-xs-3">
				<col class="col-xs-3">
			</colgroup>
			<tr>
				<!-- 商品名称 -->
				<td><font color="red">*</font>Name：</td>
				<td colspan="3"><form:input id="title" path="title" class="form-control" check="notEmpty" placeholder="Please input goods name..."/></td>
				<td></td>
			</tr>
			<tr>
				<!-- 英文名称 -->
				<td><font color="red">*</font>English Name：</td>
				<td colspan="3"><form:input id="titleEn" path="titleEn" class="form-control" check="notEmpty" placeholder="Please input english name..."/></td>
				<td></td>
			</tr>
			<tr>
				<!-- 出发地 -->
				<td><font color="red">*</font>Departure：</td>
				<td><form:input path="dept"  class="form-control" check="notEmpty" placeholder="Please input departure..."/></td>
				<!-- 目的地 -->
				<td><font color="red">*</font>Destination：</td>
				<td><form:input path="arrived"  class="form-control" check="notEmpty"  placeholder="Please input destination..."/></td>
				<td></td>
			</tr>
			<tr>
				<!-- 行程天数 -->
				<td><font color="red">*</font>Travel Days：</td>
				<td>
					<div class="input-group">
					  <form:input path="travelDays" class="form-control" check="amount" placeholder="Please input a nubmer..."/>
					  <span class="input-group-addon">day</span>
					</div>
				</td>
				<!--  库存数量 -->
				<td><font color="red">*</font>Stock Qty：</td>
				<td>
					<div class="input-group">
						<form:input path="goodsCount"  class="form-control" check="integer" placeholder="Please input a number..."/>
					  	<span class="input-group-addon">/day</span>
					</div>
				</td>
				<td></td>
			</tr>
			<tr>
				<td><font color="red">*</font>Price(Audit)：</td>
				<td>
					<div class="input-group">
					  <form:input path="basePrice" class="form-control" check="amount" placeholder="Please input the amount..."/>
					  <!-- <span class="input-group-addon">.00</span> -->
					</div>
				</td>
				<td><font color="red">*</font>Price(Child)：</td>
				<td>
					<div class="input-group">
					  <form:input path="basePriceChild" class="form-control" check="amount" placeholder="Please input the amount..."/>
					  <!-- <span class="input-group-addon">.00</span> -->
					</div>
				</td>
				<td></td>
			</tr>
		</table>
		<table class="table">
			<colgroup>
				<col class="col-xs-1">
				<col class="col-xs-5">
				<col class="col-xs-1">
			</colgroup>
			<tr>
				<td><font color="red">*</font>Recommended Reason：</td>
				<td><form:textarea path="summary" /></td>
				<td><form:errors path="summary" cssClass="error" /></td>
			</tr>
			<script>
                var oFCKeditor1 = new FCKeditor( 'summary', 800, 200, 'Default', '${goods.summary}') ;  
                oFCKeditor1.BasePath = "${pageContext.request.contextPath}/fckeditor/" ;  
                oFCKeditor1.ReplaceTextarea() ;  
			</script>
			<tr>
				<td><font color="red">*</font>Promotion Picture：</td>
				<td><input type="file" name="picPathFile" /></td>
				<td></td>
			</tr>
			<tr>
				<td><font color="red">*</font>Route Pictures：</td>
				<td>
					<input type="file" name="linePicPathAFile" />
					<input type="file" name="linePicPathBFile" />
					<input type="file" name="linePicPathCFile" />
					<input type="file" name="linePicPathDFile" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td><font color="red">*</font>Floating Price：</td>
				<td>
					<a class="btn btn-success" href="javascript:addPriceSegment();">Add</a>
					<table id="price_tbl">
					
					</table>
				</td>
				<td></td>
			</tr>
			<tr>
				<td><font color="red">*</font>Itinerary：</td>
				<td><form:textarea path="details.travelPlan" /></td>
				<td><form:errors path="details.travelPlan" cssClass="error" /></td>
			</tr>
			<script>
                var oFCKeditor2 = new FCKeditor( 'details.travelPlan', 800, 200, 'Default', '${goods.details.travelPlan}') ;  
                oFCKeditor2.BasePath = "${pageContext.request.contextPath}/fckeditor/" ;  
                oFCKeditor2.ReplaceTextarea() ;  
			</script>
			<tr>
				<td><font color="red">*</font>Expense：</td>
				<td><form:textarea path="details.costDesc" /></td>
				<td><form:errors path="details.costDesc" cssClass="error" /></td>
			</tr>
			<script>
                var oFCKeditor3 = new FCKeditor( 'details.costDesc', 800, 200, 'Default', '${goods.details.costDesc}') ;  
                oFCKeditor3.BasePath = "${pageContext.request.contextPath}/fckeditor/" ;  
                oFCKeditor3.ReplaceTextarea() ;  
			</script>
			<tr>
				<td><font color="red">*</font>Booking Notes：</td>
				<td><form:textarea path="details.bookNotes" /></td>
				<td><form:errors path="details.bookNotes" cssClass="error" /></td>
			</tr>
			<script>
                var oFCKeditor4 = new FCKeditor( 'details.bookNotes', 800, 200, 'Default', '${goods.details.bookNotes}') ;  
                oFCKeditor4.BasePath = "${pageContext.request.contextPath}/fckeditor/" ;  
                oFCKeditor4.ReplaceTextarea() ;  
			</script>
			<tr>
				<td><font color="red">*</font>Details：</td>
				<td><form:textarea path="details.notes" /></td>
				<td><form:errors path="details.notes" cssClass="error" /></td>
			</tr>
			<script>
                var oFCKeditor5 = new FCKeditor( 'details.notes', 800, 200, 'Default', '${goods.details.notes}') ;  
                oFCKeditor5.BasePath = "${pageContext.request.contextPath}/fckeditor/" ;  
                oFCKeditor5.ReplaceTextarea() ;  
			</script>
			<tr>
				<td colspan="3"><input type="button" onclick="submitForm();" value="Submit" class="btn btn-primary" /></td>
			</tr>
		</table>
	</form:form>
<script>
function addPriceSegment() {
	var index = $("#price_tbl tr").length;
	var html = '<tr index="' + index + '">'
		+'<td><input name="segments[' + index + '].startDate" style="width:110px;" check="notEmpty date" class="form-control" tag="date"  placeholder="Please select date..."/></td>'
		+'<td>-</td>'
		+'<td><input name="segments[' + index + '].endDate" style="width:110px;" check="notEmpty date" class="form-control" tag="date"  placeholder="Please select date..."/></td>'
		+'<td><font color="red">*</font>Price：</td>'
		+'<td><input name="segments[' + index + '].auditPrice" style="width:100px;" check="amount" class="form-control"  placeholder="Please enter the amount..."/></td>'
		+'<td>(Audit)</td>'
		+'<td><input name="segments[' + index + '].childPrice" style="width:100px;" check="amount" class="form-control"  placeholder="Please enter the amount..."/></td>'
		+'<td>(Child)</td>'
		+'<td><a class="btn btn-warning" href="javascript:removePriceSegment(' + index + ');">Remove</a></td>'
		+'</tr>';	
	var newtr = $(html);
	$("#price_tbl").append(newtr);
	$("#price_tbl [tag=date]").datepicker({dateFormat:'yy/mm/dd'});
}
//addPriceSegment();

function removePriceSegment(index){
	$("#price_tbl tr[index=" + index + "]").remove();
}

$(function() {
	$("#price_tbl [tag=date]").datepicker({dateFormat:'yy/mm/dd'});
});

function submitForm() {
	if(!confirm("Are you sure？")) return false;
	validate(function(){
		$("form").submit();
	});
}
</script>