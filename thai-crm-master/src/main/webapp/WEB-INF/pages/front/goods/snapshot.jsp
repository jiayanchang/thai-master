<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery/jquery-ui.css">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/frame.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<div class="content">
	<h1>View Goods</h1>
	<c:url var="addUrl" value="/f/goods/${goods.id }"/>
	<form:form action="${addUrl}" method="GET" commandName="goods">
		<form:hidden path="id"/>
		<%@ include file="../../goods_table_view.jsp"%>
	</form:form>
</div>
<a class="btn btn-primary btn-lg btn-block" href="javascript:window.close();">Close</a>
