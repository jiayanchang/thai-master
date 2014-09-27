<%@page import="com.magic.thai.security.UserProfile"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<html>
<head>
	<%@ include file="../head.jsp"%>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#"> <%@ include file="../logo.jsp"%>
			</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li role="presentation"><a href="${pageContext.request.contextPath}/a/goods/list">商品管理</a></li>
				<li role="presentation"><a href="${pageContext.request.contextPath}/a/order/list">订单管理</a></li>
				<li role="presentation"><a href="${pageContext.request.contextPath}/a/channel/list">渠道管理</a></li>
				<li role="presentation"  class="dropdown-toggle"><a style='color:#FFF;' href="#">系统管理</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li role="presentation">
					<a href="#" class="navbar-link"><%=((UserProfile)session.getAttribute("userprofile")).getUser().getName()  %></a>
				</li>
				<li role="presentation">
					<a href="${pageContext.request.contextPath}/logout">退出</a>
				</li>
			</ul>
		</div>
	</div>
</nav>
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-3 col-md-2 sidebar">
			<ul class="nav nav-sidebar">
				<li><a href="${pageContext.request.contextPath}/a/merchant/list">商家查询</a></li>
				<li><a href="${pageContext.request.contextPath}/a/merchant/add">新建商家</a></li>
				<li><a href="${pageContext.request.contextPath}/a/user/list">用户查询</a></li>
				<li><a href="${pageContext.request.contextPath}/a/user/add">新建用户</a></li>
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
