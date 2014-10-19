<%@page import="com.magic.thai.security.UserProfile"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="../head.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/validator.js"></script>  
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"> <%@ include file="../logo.jsp"%>
				</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<!-- 商品管理 -->
					<li role="presentation"><a href="${pageContext.request.contextPath}/f/goods/list">การบริหารจัดการสินค้า</a></li>
					<!-- 订单管理 -->
					<li role="presentation"><a href="${pageContext.request.contextPath}/f/order/list">การบริหารจัดการสั่งซื้อสินค้า</a></li>
					<!-- 系统管理 -->
					<li role="presentation"  class="dropdown-toggle"><a style='color:#FFF;' href="#">การบริหารจัดการระบบ</a></li>
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
					<!-- 新建员工 -->
					<li><a href="${pageContext.request.contextPath}/f/user/add">พนักงานใหม่</a></li>
					<!-- 员工查询 -->
					<li><a href="${pageContext.request.contextPath}/f/user/list">การหาพนักงาน</a></li>
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