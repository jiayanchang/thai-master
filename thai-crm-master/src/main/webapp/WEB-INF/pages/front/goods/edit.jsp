<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/fckeditor/fckeditor.js"></script>  

<div class="content">
	<h1>编辑商品</h1>
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
				<td><font color="red">*</font>商品名称：</td>
				<td colspan="3"><form:input path="title" class="form-control"/></td>
				<td></td>
			</tr>
			<tr>
				<td><font color="red">*</font>英文名称：</td>
				<td colspan="3"><form:input path="titleEn" class="form-control"/></td>
				<td></td>
			</tr>
			<tr>
				<td><font color="red">*</font>出发地：</td>
				<td><form:input path="dept"  class="form-control"/></td>
				<td><font color="red">*</font>目的地：</td>
				<td><form:input path="arrived"  class="form-control"/></td>
				<td></td>
			</tr>
			<tr>
				<td><font color="red">*</font>行程天数：</td>
				<td><form:input path="travelDays"  class="form-control"/></td>
				<td><font color="red">*</font>库存数量：</td>
				<td><form:input path="goodsCount"  class="form-control"/></td>
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
                var oFCKeditor1 = new FCKeditor( 'summary', 800, 200, 'Default', '${goods.summary}') ;  
                oFCKeditor1.BasePath = "/crm/fckeditor/" ;  
                oFCKeditor1.ReplaceTextarea() ;  
			</script>
			<tr>
				<td><font color="red">*</font>宣传图片：</td>
				<td>
					<input type="file" name="picPathFile" />
					<img alt="" src="${pageContext.request.contextPath}${goods.details.picPath }">
				</td>
			</tr>
			
			<tr>
				<td><font color="red">*</font>线路图片：</td>
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
				<td><font color="red">*</font>价格管理：<a class="btn btn-success" href="javascript:addPriceSegment();">添加</a></td>
				<td>
					<table id="price_tbl">
						<c:forEach var="segment" items="${goods.segments }" varStatus="status">
						<tr index="${status.index }">
							<td>
								<input type="hidden" tag="id" name="segments[${status.index }].id" value="${segment.id }"/>
								<input name="segments[${status.index }].startDate" tag="date" class="form-control"  value="<fmt:formatDate value="${segment.startDate }" type="date" pattern="yyyy/MM/dd"/>"/>
							</td>
							<td></td>
							<td><input name="segments[${status.index }].endDate" tag="date" class="form-control" value="<fmt:formatDate value="${segment.endDate }" type="date" pattern="yyyy/MM/dd"/>"/></td>
							<td><font color="red">*</font>价格：</td>
							<td><input name="segments[${status.index }].auditPrice" class="form-control" value="${segment.auditPrice }"  placeholder="金额"/></td>
							<td>（成人）</td>
							<td><input name="segments[${status.index }].childPrice" class="form-control" value="${segment.childPrice }"  placeholder="金额"/></td>
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
				<td colspan="3"><input type="button" onclick="validate();" value="提交" class="btn btn-primary" /></td>
			</tr>
		</table>
		<form:hidden path="details.id" />
	</form:form>
</div>
<script>
	function addPriceSegment(){
		var index = 1 + parseInt($("#price_tbl tr:last").attr("index"));
		var html = '<tr index="' + index + '">'
			+'<td><input name="segments[' + index + '].startDate" tag="date" class="form-control"/></td>'
			+'<td></td>'
			+'<td><input name="segments[' + index + '].endDate" tag="date" class="form-control"/></td>'
			+'<td><font color="red">*</font>价格：</td>'
			+'<td><input name="segments[' + index + '].auditPrice" class="form-control"/></td>'
			+'<td>（成人）</td>'
			+'<td><input name="segments[' + index + '].childPrice" class="form-control"/></td>'
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
	
	function validate() {
		$(".has-error").removeClass("has-error");
		var pass = true;
		$("form input").each(function() {
			var check = $(this).attr("check");
			var val = $(this).val();
			if(check) {
				if(check.indexOf('amount') >= 0){
					if(!isDigital(val)) {
						pass = false;
						$(this).parent().addClass("form-group has-error");
					}
				}
				if (check.indexOf('notEmpty') >= 0) {
					if(isEmpty(val)) {
						pass = false;
						$(this).parent().addClass("form-group has-error");
					}
				} 
				if (check.indexOf('integer') >= 0) {
					if(!isInteger(val)) {
						pass = false;
						$(this).parent().addClass("form-group has-error");
					}
				} 
				if (check.indexOf('date') >= 0) {
					
				}
			}
		});
		
		if(pass) {
			$("from").submit();
		}
	}
</script>