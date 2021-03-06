<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/fckeditor/fckeditor.js"></script>  
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validator.js"></script>  

<div class="content">
	<h1>编辑商品</h1>
	<c:if test="${not empty message}">
		<div class="alert alert-danger" role="alert">${message }</div>
	</c:if>
	<c:url var="addUrl" value="/a/goods/edit/proccess"/>
	<form:form action="${addUrl}" method="POST" commandName="goods" enctype="multipart/form-data">
	
		<form:hidden path="id"/>
		<table class="table">
			<colgroup>
				<col class="col-xs-1">
				<col class="col-xs-3">
				<col class="col-xs-1">
				<col class="col-xs-3">
				<col class="col-xs-1">
			</colgroup>
			<tr>
				<td><font color="red">*</font>商品名称：</td>
				<td colspan="3"><form:input path="title" class="form-control" check="notEmpty" placeholder="请输入商品名称..."/></td>
				<td></td>
			</tr>
			<tr>
				<td><font color="red">*</font>英文名称：</td>
				<td colspan="3"><form:input path="titleEn" class="form-control" check="notEmpty" placeholder="请输入商品英文名称..."/></td>
				<td></td>
			</tr>
			<tr>
				<td><font color="red">*</font>出发地：</td>
				<td><form:input path="dept"  class="form-control"  check="notEmpty" placeholder="请输入出发地..."/></td>
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
					  <form:input path="basePrice" class="form-control" check="amount" placeholder="请输入一个金额..."/>
					 <!--  <span class="input-group-addon">.00</span> -->
					</div>
				</td>
				<td><font color="red">*</font>单价(儿童)：</td>
				<td>
					<div class="input-group">
					  <form:input path="basePriceChild" class="form-control" check="amount" placeholder="请输入一个金额..."/>
					 <!--  <span class="input-group-addon">.00</span> -->
					</div>
				</td>
				<td></td>
			</tr>
		</table>
		<table class="table">
			<colgroup>
				<col class="col-xs-1">
				<col class="col-xs-7">
				<col class="col-xs-1">
			</colgroup>
			<tr>
				<td><font color="red">*</font>推荐理由：</td>
				<td><form:textarea path="summary" /></td>
				<td><form:errors path="summary" cssClass="error" /></td>
			</tr>
			<script>
                var oFCKeditor1 = new FCKeditor( 'summary', 800, 200, 'Default') ;  
                oFCKeditor1.BasePath = "/crm/fckeditor/" ;  
                oFCKeditor1.ReplaceTextarea() ;  
			</script>
			<tr>
				<td><font color="red">*</font>Promotion Picture：</td>
				<td>
					<input type="file" name="picPathFile" />
					<img alt="" src="${pageContext.request.contextPath}${goods.details.picPath }">
				</td>
			</tr>
			
			<tr>
				<td><font color="red">*</font>Route Picture：</td>
				<td>
					<input type="file" name="linePicPathAFile" />
					<img alt="" src="${pageContext.request.contextPath}${goods.details.linePicPathA }">
					<input type="file" name="linePicPathBFile" />
					<img alt="" src="${pageContext.request.contextPath}${goods.details.linePicPathB }">
					<input type="file" name="linePicPathCFile" />
					<img alt="" src="${pageContext.request.contextPath}${goods.details.linePicPathC }">
					<input type="file" name="linePicPathDFile" />
					<img alt="" src="${pageContext.request.contextPath}${goods.details.linePicPathD }">
				</td>
				<td>
				</td>
			</tr>
			
			<tr>
				<td><font color="red">*</font>Floating Price：</td>
				<td>
					<a class="btn btn-success" href="javascript:addPriceSegment();">Add</a>
					<table id="price_tbl">
						<c:forEach var="segment" items="${goods.segments }" varStatus="status">
						<tr index="${status.index }">
							<td>
								<input type="hidden" tag="id" name="segments[${status.index }].id" value="${segment.id }"/>
								<input name="segments[${status.index }].startDate" tag="date" check="notEmpty date" class="form-control"  value="<fmt:formatDate value="${segment.startDate }" type="date"  pattern="yyyy/MM/dd"/>" placeholder="Please select date..." />
							</td>
							<td></td>
							<td><input name="segments[${status.index }].endDate" tag="date" check="notEmpty date" class="form-control" value="<fmt:formatDate value="${segment.endDate }" type="date"  pattern="yyyy/MM/dd"/>" placeholder="Please select date..." /></td>
							<td><font color="red">*</font>价格：</td>
							<td><input name="segments[${status.index }].auditPrice" class="form-control" value="${segment.auditPrice }" check="amount"  placeholder="Please enter the amount..."/></td>
							<td>（成人）</td>
							<td><input name="segments[${status.index }].childPrice" class="form-control" value="${segment.childPrice }"  check="amount" placeholder="Please enter the amount..."/></td>
							<td>（儿童）</td>
							<td>
								<a class="btn btn-warning" href="javascript:removePriceSegment( ${status.index });">移除</a>
							</td>
						</tr>
						</c:forEach>
					</table>
				</td>
				<td></td>
			</tr>
			
			<tr>
				<td><font color="red">*</font>行程安排：</td>
				<td><textarea name="details.travelPlan" >${goods.details.travelPlan}</textarea></td>
				<td><form:errors path="details.travelPlan" cssClass="error" /></td>
			</tr>
			<script>
                var oFCKeditor2 = new FCKeditor( 'details.travelPlan', 800, 200, 'Default') ;  
                oFCKeditor2.BasePath = "/crm/fckeditor/" ;  
                oFCKeditor2.ReplaceTextarea() ;  
			</script>
			<tr>
				<td><font color="red">*</font>费用说明：</td>
				<td><textarea name="details.costDesc">${goods.details.costDesc}</textarea></td>
				<td><form:errors path="details.costDesc" cssClass="error" /></td>
			</tr>
			<script>
                var oFCKeditor3 = new FCKeditor( 'details.costDesc', 800, 200, 'Default') ;  
                oFCKeditor3.BasePath = "/crm/fckeditor/" ;  
                oFCKeditor3.ReplaceTextarea(); 
			</script>
			<tr>
				<td><font color="red">*</font>预定须知：</td>
				<td><textarea name="details.bookNotes" >${goods.details.bookNotes}</textarea></td>
				<td><form:errors path="details.bookNotes" cssClass="error" /></td>
			</tr>
			<script>
                var oFCKeditor4 = new FCKeditor( 'details.bookNotes', 800, 200, 'Default') ;  
                oFCKeditor4.BasePath = "/crm/fckeditor/" ;  
                oFCKeditor4.ReplaceTextarea(); 
			</script>
			<tr>
				<td><font color="red">*</font>备注：</td>
				<td><textarea name="details.notes">${goods.details.notes}</textarea></td>
				<td><form:errors path="details.notes" cssClass="error" /></td>
			</tr>
			<script>
                var oFCKeditor5 = new FCKeditor( 'details.notes', 800, 200, 'Default') ;  
                oFCKeditor5.BasePath = "/crm/fckeditor/" ;  
                oFCKeditor5.ReplaceTextarea(); 
			</script>
			<tr>
				<td colspan="3"><input type="button" onclick="submitForm();" value="提交" class="btn btn-primary" /></td>
			</tr>
		</table>
		<form:hidden path="details.id" />
	</form:form>
</div>
<script>
	function addPriceSegment(){
		var index = $("#price_tbl tr").length;
		var html = '<tr index="' + index + '">'
			+'<td><input name="segments[' + index + '].startDate" tag="date" class="form-control" check="notEmpty date" placeholder="Please select date..."/></td>'
			+'<td></td>'
			+'<td><input name="segments[' + index + '].endDate" tag="date" class="form-control" check="notEmpty date" placeholder="Please select date..."/></td>'
			+'<td><font color="red">*</font>价格：</td>'
			+'<td><input name="segments[' + index + '].auditPrice" class="form-control"check="amount"  placeholder="Please enter the amount..."/></td>'
			+'<td>（成人）</td>'
			+'<td><input name="segments[' + index + '].childPrice" class="form-control"check="amount"  placeholder="Please enter the amount..."/></td>'
			+'<td>（儿童）</td>'
			+'<td><a class="btn btn-warning" href="javascript:removePriceSegment(' + index + ');">移除</a></td>'
			+'</tr>';	
		var newtr = $(html);
		$("#price_tbl").append(newtr);
		$("#price_tbl [tag=date]").datepicker({dateFormat:'yy/mm/dd'});
	}

	
	
	function removePriceSegment(index){
		$("#price_tbl tr[index=" + index + "]").remove();
	}
	
	$("#price_tbl [tag=date]").datepicker({dateFormat:'yy/mm/dd'});
	
	function submitForm() {
		if(!confirm("Are you sure？")) return false;
		validate(function(){
			$("form").submit();
		});
	}
</script>