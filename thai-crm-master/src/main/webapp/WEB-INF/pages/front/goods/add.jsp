<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<script type="text/javascript" src="${pageContext.request.contextPath}/fckeditor/fckeditor.js"></script>  
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validator.js"></script>  

	<h1>新建商品</h1>
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
				<td><font color="red">*</font>商品名称：</td>
				<td colspan="3"><form:input id="title" path="title" class="form-control" check="notEmpty" placeholder="请输入商品名称..."/></td>
				<td></td>
			</tr>
			<tr>
				<td><font color="red">*</font>英文名称：</td>
				<td colspan="3"><form:input id="titleEn" path="titleEn" class="form-control" check="notEmpty" placeholder="请输入商品英文名称..."/></td>
				<td></td>
			</tr>
			<tr>
				<td><font color="red">*</font>出发地：</td>
				<td><form:input path="dept"  class="form-control" check="notEmpty" placeholder="请输入出发地..."/></td>
				<td><font color="red">*</font>目的地：</td>
				<td><form:input path="arrived"  class="form-control" check="notEmpty"  placeholder="请输入目的地..."/></td>
				<td></td>
			</tr>
			<tr>
				<td><font color="red">*</font>行程天数：</td>
				<td>
					<div class="input-group">
					  <form:input path="travelDays" class="form-control" check="amount" placeholder="请输入一个整数或小数..."/>
					  <span class="input-group-addon">天</span>
					</div>
				</td>
				<td><font color="red">*</font>库存数量：</td>
				<td>
					<div class="input-group">
						<form:input path="goodsCount"  class="form-control" check="integer" placeholder="请输入一个整数..."/>
					  	<span class="input-group-addon">/天</span>
					</div>
				</td>
				<td></td>
			</tr>
			<tr>
				<td><font color="red">*</font>单价(成人)：</td>
				<td>
					<div class="input-group">
					  <form:input path="basePrice" class="form-control" check="integer" placeholder="请输入一个整数..."/>
					  <span class="input-group-addon">.00</span>
					</div>
				</td>
				<td><font color="red">*</font>单价(儿童)：</td>
				<td>
					<div class="input-group">
					  <form:input path="basePriceChild" class="form-control" check="integer" placeholder="请输入一个整数..."/>
					  <span class="input-group-addon">.00</span>
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
				<td><font color="red">*</font>推荐理由：</td>
				<td><form:textarea path="summary" /></td>
				<td><form:errors path="summary" cssClass="error" /></td>
			</tr>
			<script>
                var oFCKeditor1 = new FCKeditor( 'summary', 800, 200, 'Default', '${goods.summary}') ;  
                oFCKeditor1.BasePath = "${pageContext.request.contextPath}/fckeditor/" ;  
                oFCKeditor1.ReplaceTextarea() ;  
			</script>
			<tr>
				<td><font color="red">*</font>宣传图片：</td>
				<td><input type="file" name="picPathFile" /></td>
				<td></td>
			</tr>
			<tr>
				<td><font color="red">*</font>线路图片：</td>
				<td>
					<input type="file" name="linePicPathAFile" />
					<input type="file" name="linePicPathBFile" />
					<input type="file" name="linePicPathCFile" />
					<input type="file" name="linePicPathDFile" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td><font color="red">*</font>淡旺季价格：</td>
				<td>
					<a class="btn btn-success" href="javascript:addPriceSegment();">添加</a>
					<table id="price_tbl">
					
					</table>
				</td>
				<td></td>
			</tr>
			<tr>
				<td><font color="red">*</font>行程安排：</td>
				<td><form:textarea path="details.travelPlan" /></td>
				<td><form:errors path="details.travelPlan" cssClass="error" /></td>
			</tr>
			<script>
                var oFCKeditor2 = new FCKeditor( 'details.travelPlan', 800, 200, 'Default', '${goods.details.travelPlan}') ;  
                oFCKeditor2.BasePath = "${pageContext.request.contextPath}/fckeditor/" ;  
                oFCKeditor2.ReplaceTextarea() ;  
			</script>
			<tr>
				<td><font color="red">*</font>费用说明：</td>
				<td><form:textarea path="details.costDesc" /></td>
				<td><form:errors path="details.costDesc" cssClass="error" /></td>
			</tr>
			<script>
                var oFCKeditor3 = new FCKeditor( 'details.costDesc', 800, 200, 'Default', '${goods.details.costDesc}') ;  
                oFCKeditor3.BasePath = "${pageContext.request.contextPath}/fckeditor/" ;  
                oFCKeditor3.ReplaceTextarea() ;  
			</script>
			<tr>
				<td><font color="red">*</font>预定须知：</td>
				<td><form:textarea path="details.bookNotes" /></td>
				<td><form:errors path="details.bookNotes" cssClass="error" /></td>
			</tr>
			<script>
                var oFCKeditor4 = new FCKeditor( 'details.bookNotes', 800, 200, 'Default', '${goods.details.bookNotes}') ;  
                oFCKeditor4.BasePath = "${pageContext.request.contextPath}/fckeditor/" ;  
                oFCKeditor4.ReplaceTextarea() ;  
			</script>
			<tr>
				<td><font color="red">*</font>备注：</td>
				<td><form:textarea path="details.notes" /></td>
				<td><form:errors path="details.notes" cssClass="error" /></td>
			</tr>
			<script>
                var oFCKeditor5 = new FCKeditor( 'details.notes', 800, 200, 'Default', '${goods.details.notes}') ;  
                oFCKeditor5.BasePath = "${pageContext.request.contextPath}/fckeditor/" ;  
                oFCKeditor5.ReplaceTextarea() ;  
			</script>
			<tr>
				<td colspan="3"><input type="button" onclick="submitForm();" value="提交" class="btn btn-primary" /></td>
			</tr>
		</table>
	</form:form>
<script>
function addPriceSegment() {
	var index = $("#price_tbl tr").length;
	var html = '<tr index="' + index + '">'
		+'<td><input name="segments[' + index + '].startDate" style="width:110px;" check="notEmpty date" class="form-control" tag="date"  placeholder="请选择日期..."/></td>'
		+'<td>-</td>'
		+'<td><input name="segments[' + index + '].endDate" style="width:110px;" check="notEmpty date" class="form-control" tag="date"  placeholder="请选择日期..."/></td>'
		+'<td><font color="red">*</font>价格：</td>'
		+'<td><input name="segments[' + index + '].auditPrice" style="width:100px;" check="amount" class="form-control"  placeholder="请输入金额..."/></td>'
		+'<td>（成人）</td>'
		+'<td><input name="segments[' + index + '].childPrice" style="width:100px;" check="amount" class="form-control"  placeholder="请输入金额..."/></td>'
		+'<td>（儿童）</td>'
		+'<td><a class="btn btn-warning" href="javascript:removePriceSegment(' + index + ');">移除</a></td>'
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
	if(!confirm("是否确定？")) return false;
	validate(function(){
		$("form").submit();
	});
}
</script>