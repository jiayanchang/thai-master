<%@page import="com.magic.thai.security.UserProfile"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>  

<html>
<head>
	<%@ include file="../head.jsp"%>
</head>
<body>
	<nav class="navbar navbar-inverse navbar-default navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"> <%@ include file="../logo.jsp"%>
				</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse navbar-header-collapse">
				<ul class="nav navbar-nav">
					<!-- 商品管理 -->
					<li role="presentation"  class="dropdown-toggle"><a style='color:#FFF;' href="#">การบริหารจัดการสินค้า</a></li>
					<!-- 订单管理 -->
					<li role="presentation"><a href="${pageContext.request.contextPath}/f/order/list">การบริหารจัดการสั่งซื้อสินค้า</a></li>
					<!-- 系统管理 -->
					<% if(((UserProfile)session.getAttribute("userprofile")).isAdministrator()) { %>
						<li role="presentation" ><a href="${pageContext.request.contextPath}/f/user/list">การบริหารจัดการระบบ</a></li>
					<% } %>
				</ul>
				
				<ul class="nav navbar-nav navbar-right">
					<li role="presentation">
						<a href="#" class="navbar-link"><%=((UserProfile)session.getAttribute("userprofile")).getUser().getName()  %></a>
					</li>
					<li role="presentation">
						<!-- 退出 -->
						<a href="${pageContext.request.contextPath}/logout">เลื่อนออก</a>
					</li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<!-- 新建商品 -->
					<li><a href="${pageContext.request.contextPath}/f/goods/add">สินค้าผลิตใหม่</a></li>
					<!-- 商品查询 -->
					<li><a href="${pageContext.request.contextPath}/f/goods/list">การค้นหาสินค้า</a></li>
				</ul>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<decorator:body />
			</div>
		</div>
	</div>
	<%@ include file="../notify.jsp"%>
</body>
</html>