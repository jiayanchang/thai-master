<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/fckeditor/fckeditor.js"></script>  
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validator.js"></script>  

<div class="content">
	<h1>Edit Goods</h1>
	<c:url var="addUrl" value="/f/goods/edit/proccess"/>
	<form:form action="${addUrl}" method="POST" commandName="goods" enctype="multipart/form-data">
	
	<font color="red">${message }</font>
	
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
				<td><font color="red">*</font>Travel Days：</td>
				<td>
					<div class="input-group">
					  <form:input path="travelDays" class="form-control" check="amount" placeholder="Please input a number..."/>
					  <span class="input-group-addon">Day</span>
					</div>
				</td>
				<td><font color="red">*</font>Stock Qty：</td>
				<td>
					<div class="input-group">
						<form:input path="goodsCount"  class="form-control" check="integer" placeholder="Please input an integer..."/>
					  	<span class="input-group-addon">/Day</span>
					</div>
				</td>
				<td></td>
			</tr>
			<tr>
				<td><font color="red">*</font>Price(Audit)：</td>
				<td>
					<div class="input-group">
					  <form:input path="basePrice" class="form-control" check="amount" placeholder="Please input an amount..."/>
					 <!--  <span class="input-group-addon">.00</span> -->
					</div>
				</td>
				<td><font color="red">*</font>Price(Child)：</td>
				<td>
					<div class="input-group">
					  <form:input path="basePriceChild" class="form-control" check="amount" placeholder="Please input an amount..."/>
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
				<td><font color="red">*</font>Recommended Reason：</td>
				<td>
					<textarea name="summary" >${goods.summary}</textarea>
				</td>
				<td><form:errors path="summary" cssClass="error" /></td>
			</tr>
			<script>
                var oFCKeditor2 = new FCKeditor( 'summary', 800, 200, 'Default') ;  
                oFCKeditor2.BasePath = "/crm/fckeditor/" ;  
                oFCKeditor2.ReplaceTextarea() ;  
			</script>
			<tr>
				<td><font color="red">*</font>Promotion Picture：</td>
				<td>
					<input type="file" name="picPathFile" />
					
					<c:if test="${not empty goods.details.picPath}">
						<img src="${pageContext.request.contextPath}${goods.details.picPath }"/>
					</c:if>
					<c:if test="${ empty goods.details.picPath}">
						<img width="100px" height="80px" src="${pageContext.request.contextPath}/css/none.jpg"/>
					</c:if>
				</td>
			</tr>
			
			<tr>
				<td><font color="red">*</font>Route Picture：</td>
				<td>
					<input type="file" name="linePicPathAFile" />
					<c:if test="${not empty goods.details.linePicPathA}">
						<img src="${pageContext.request.contextPath}${goods.details.linePicPathA }"/>
					</c:if>
					<c:if test="${ empty goods.details.linePicPathA}">
						<img width="100px" height="80px" src="${pageContext.request.contextPath}/css/none.jpg"/>
					</c:if>
					
					<input type="file" name="linePicPathBFile" />
					<c:if test="${not empty goods.details.linePicPathB}">
						<img src="${pageContext.request.contextPath}${goods.details.linePicPathB }"/>
					</c:if>
					<c:if test="${ empty goods.details.linePicPathB}">
						<img width="100px" height="80px" src="${pageContext.request.contextPath}/css/none.jpg"/>
					</c:if>
					
					<input type="file" name="linePicPathCFile" />
					<c:if test="${not empty goods.details.linePicPathC}">
						<img src="${pageContext.request.contextPath}${goods.details.linePicPathC }"/>
					</c:if>
					<c:if test="${ empty goods.details.linePicPathC}">
						<img width="100px" height="80px" src="${pageContext.request.contextPath}/css/none.jpg"/>
					</c:if>
					
					<input type="file" name="linePicPathDFile" />
					<c:if test="${not empty goods.details.linePicPathD}">
						<img src="${pageContext.request.contextPath}${goods.details.linePicPathD }"/>
					</c:if>
					<c:if test="${ empty goods.details.linePicPathD}">
						<img width="100px" height="80px" src="${pageContext.request.contextPath}/css/none.jpg"/>
					</c:if>
					
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
							<td><font color="red">*</font>Price：</td>
							<td><input name="segments[${status.index }].auditPrice" class="form-control" value="${segment.auditPrice }" check="amount"  placeholder="Please enter the amount..."/></td>
							<td>(Audit)</td>
							<td><input name="segments[${status.index }].childPrice" class="form-control" value="${segment.childPrice }"  check="amount" placeholder="Please enter the amount..."/></td>
							<td>(Child)</td>
							<td>
								<a class="btn btn-warning" href="javascript:removePriceSegment( ${status.index });">Remove</a>
							</td>
						</tr>
						</c:forEach>
					</table>
				</td>
				<td></td>
			</tr>
			
			<tr>
				<td><font color="red">*</font>Itinerary：</td>
				<td><textarea name="details.travelPlan" >${goods.details.travelPlan}</textarea></td>
				<td><form:errors path="details.travelPlan" cssClass="error" /></td>
			</tr>
			<script>
                var oFCKeditor2 = new FCKeditor( 'details.travelPlan', 800, 200, 'Default') ;  
                oFCKeditor2.BasePath = "/crm/fckeditor/" ;  
                oFCKeditor2.ReplaceTextarea() ;  
			</script>
			<tr>
				<td><font color="red">*</font>Expense：</td>
				<td><textarea name="details.costDesc">${goods.details.costDesc}</textarea></td>
				<td><form:errors path="details.costDesc" cssClass="error" /></td>
			</tr>
			<script>
                var oFCKeditor3 = new FCKeditor( 'details.costDesc', 800, 200, 'Default') ;  
                oFCKeditor3.BasePath = "/crm/fckeditor/" ;  
                oFCKeditor3.ReplaceTextarea(); 
			</script>
			<tr>
				<td><font color="red">*</font>Booking Notes：</td>
				<td><textarea name="details.bookNotes" >${goods.details.bookNotes}</textarea></td>
				<td><form:errors path="details.bookNotes" cssClass="error" /></td>
			</tr>
			<script>
                var oFCKeditor4 = new FCKeditor( 'details.bookNotes', 800, 200, 'Default') ;  
                oFCKeditor4.BasePath = "/crm/fckeditor/" ;  
                oFCKeditor4.ReplaceTextarea(); 
			</script>
			<tr>
				<td><font color="red">*</font>Details：</td>
				<td><textarea name="details.notes">${goods.details.notes}</textarea></td>
				<td><form:errors path="details.notes" cssClass="error" /></td>
			</tr>
			<script>
                var oFCKeditor5 = new FCKeditor( 'details.notes', 800, 200, 'Default') ;  
                oFCKeditor5.BasePath = "/crm/fckeditor/" ;  
                oFCKeditor5.ReplaceTextarea(); 
			</script>
			<tr>
				<td colspan="3"><input type="button" onclick="submitForm();" value="Submit" class="btn btn-primary" /></td>
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
			+'<td>(Audit)</td>'
			+'<td><input name="segments[' + index + '].childPrice" class="form-control"check="amount"  placeholder="Please enter the amount..."/></td>'
			+'<td>(Child)</td>'
			+'<td><a class="btn btn-warning" href="javascript:removePriceSegment(' + index + ');">Remove</a></td>'
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