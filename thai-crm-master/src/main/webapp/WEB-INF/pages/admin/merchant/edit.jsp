<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<h1>编辑商家</h1>
<c:url var="addUrl" value="/a/merchant/edit/proccess"/>
<form:form action="${addUrl}" method="POST" commandName="merchant"  enctype="multipart/form-data">
	<form:hidden path="id"/>
	<table class="table">
		<colgroup>
			<col class="col-xs-1">
			<col class="col-xs-7">
			<col class="col-xs-7">
		</colgroup>
		<tr>
			<td>商家名称 :</td>
			<td><form:input path="name"  class="form-control"/></td>
			<td><form:errors path="name" cssClass="error" /></td>
		</tr>
		<tr>
			<td>英文名 :</td>
			<td><form:input path="nameEn"  class="form-control"/></td>
			<td><form:errors path="nameEn" cssClass="error" /></td>
		</tr>
		<%-- <tr>
			<td>代码 :</td>
			<td><form:input path="codeName" /></td>
			<td><form:errors path="codeName" cssClass="error" /></td>
		</tr> --%>
		<tr>
			<td>联系电话 :</td>
			<td><form:input path="tel"  class="form-control"/></td>
			<td><form:errors path="tel" cssClass="error" /></td>
		</tr>
		<tr>
			<td>手机:</td>
			<td><form:input path="mobile"  class="form-control"/></td>
			<td><form:errors path="mobile" cssClass="error" /></td>
		</tr>
		<tr>
			<td>LOGO:</td>
			<td><input type="file" name="file"/></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td><img src="${pageContext.request.contextPath}/resources/logo/${merchant.id}.jpg"></td>
			<td></td>
		</tr>
		<tr>
			<td>备注:</td>
			<td><form:textarea path="details.notes"  class="form-control"/></td>
			<td><form:errors path="details.notes" cssClass="error" /></td>
		</tr>
		<tr>
			<td colspan="3"><input type="submit" value="submit" class="button2" /></td>
		</tr>
	</table>
	<form:hidden path="details.id" />
</form:form>
