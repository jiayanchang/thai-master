<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<div class="content">
	<h1>编辑商品</h1>
	<c:url var="addUrl" value="/f/goods/edit/proccess"/>
	<form:form action="${addUrl}" method="POST" commandName="goods" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<table>
			<tr>
				<td><font color="red">*</font>商品名称：</td>
				<td><form:input path="title" /></td>
				<td><form:errors path="title" cssClass="error" /></td>
			</tr>
			<tr>
				<td><font color="red">*</font>出发地：</td>
				<td><form:input path="dept" /></td>
				<td><form:errors path="dept" cssClass="error" /></td>
			</tr>
			<tr>
				<td><font color="red">*</font>目的地：</td>
				<td><form:input path="arrived" /></td>
				<td><form:errors path="arrived" cssClass="error" /></td>
			</tr>
			<tr>
				<td><font color="red">*</font>行程天数：</td>
				<td><form:input path="travelDays" /></td>
				<td><form:errors path="travelDays" cssClass="error" /></td>
			</tr>
			<tr>
				<td><font color="red">*</font>库存数量：</td>
				<td><form:input path="goodsCount" /></td>
				<td><form:errors path="goodsCount" cssClass="error" /></td>
			</tr>
			<tr>
				<td><font color="red">*</font>推荐理由：</td>
				<td><form:textarea path="summary" /></td>
				<td><form:errors path="summary" cssClass="error" /></td>
			</tr>
			
			
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
				<td><font color="red">*</font>价格管理：</td>
				<td>
					<table id="price_tbl">
						<tr>
							<td><input name="startDate"/></td>
							<td></td>
							<td><input name="endDate"/></td>
							<td><font color="red">*</font>价格：</td>
							<td><input name="auditPrice"/></td>
							<td>（成人）</td>
							<td><input name="childPrice"/></td>
							<td>（儿童）</td>
							<td><a href="" onclick="addPriceSegment();">【+】</a></td>
						</tr>
					</table>
				</td>
				<td></td>
			</tr>
			
			<tr>
				<td><font color="red">*</font>价格管理：</td>
				<td><form:textarea path="summary" /></td>
				<td><form:errors path="summary" cssClass="error" /></td>
			</tr>
			
			
			
			<tr>
				<td><font color="red">*</font>行程安排：</td>
				<td><form:textarea path="details.travelPlan" /></td>
				<td><form:errors path="details.travelPlan" cssClass="error" /></td>
			</tr>
			<tr>
				<td><font color="red">*</font>费用说明：</td>
				<td><form:textarea path="details.costDesc" /></td>
				<td><form:errors path="details.costDesc" cssClass="error" /></td>
			</tr>
			<tr>
				<td><font color="red">*</font>预定须知：</td>
				<td><form:textarea path="details.bookNotes" /></td>
				<td><form:errors path="details.bookNotes" cssClass="error" /></td>
			</tr>
			<tr>
				<td><font color="red">*</font>备注：</td>
				<td><form:textarea path="details.notes" /></td>
				<td><form:errors path="details.notes" cssClass="error" /></td>
			</tr>
			<tr>
				<td colspan="3"><input type="submit" value="submit" class="button2" /></td>
			</tr>
		</table>
		<form:hidden path="details.id" />
	</form:form>
	<p>
		<a href="${pageContext.request.contextPath}/"><button class="button2">Back</button></a>
	</p>
</div>
