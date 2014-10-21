<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<h1>查看商品</h1>
<c:url var="addUrl" value="/f/goods/${goods.id }"/>
<form:form action="${addUrl}" method="GET" commandName="goods">
	<form:hidden path="id"/>
	
	<%@ include file="../../goods_table_view.jsp" %>
	
</form:form>
