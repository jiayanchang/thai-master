<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery/jquery-ui.css">
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/frame.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<div class="content">
	<h1>查看商品</h1>
	<c:url var="addUrl" value="/f/goods/${goods.id }"/>
	<form:form action="${addUrl}" method="GET" commandName="goods">
		<form:hidden path="id"/>
		<table class="table">
			<colgroup>
				<col class="col-xs-1">
				<col class="col-xs-7">
			</colgroup>
			<tr>
				<td>商品名称：</td>
				<td>${goods.title }</td>
			</tr>
			<tr>
				<td>出发地：</td>
				<td>${goods.dept }</td>
			</tr>
			<tr>
				<td>目的地：</td>
				<td>${goods.arrived }</td>
			</tr>
			<tr>
				<td>行程天数：</td>
				<td>${goods.travelDays }</td>
			</tr>
			<tr>
				<td>库存数量：</td>
				<td>${goods.goodsCount }</td>
			</tr>
			<tr>
				<td>推荐理由：</td>
				<td>${goods.summary }</td>
			</tr>
			<tr>
				<td>宣传图片：</td>
				<td><img src="${pageContext.request.contextPath}${goods.details.picPath }"/></td>
			</tr>
			
			<tr>
				<td>线路图片：</td>
				<td>
					<img alt="" src="${pageContext.request.contextPath}${goods.details.linePicPathA }">
					<img alt="" src="${pageContext.request.contextPath}${goods.details.linePicPathB }">
					<img alt="" src="${pageContext.request.contextPath}${goods.details.linePicPathC }">
					<img alt="" src="${pageContext.request.contextPath}${goods.details.linePicPathD }">
				</td>
			</tr>
			
			<tr>
				<td>价格管理：</td>
				<td>
					<table id="price_tbl">
						<c:forEach var="segment" items="${goods.segments }">
							<tr>
								<td>${segment.startDate }</td>
								<td>-</td>
								<td>${segment.endDate }</td>
								<td>价格：</td>
								<td>${segment.auditPrice }</td>
								<td>（成人）</td>
								<td>${segment.childPrice }</td>
								<td>（儿童）</td>
							</tr>
						</c:forEach>
					</table>
				</td>
				<td></td>
			</tr>
			<tr>
				<td>行程安排：</td>
				<td>${goods.details.travelPlan }</td>
			</tr>
			<tr>
				<td>费用说明：</td>
				<td>${goods.details.costDesc }</td>
			</tr>
			<tr>
				<td>预定须知：</td>
				<td>${goods.details.bookNotes }</td>
			</tr>
			<tr>
				<td>备注：</td>
				<td>${goods.details.notes }</td>
			</tr>
		</table>
	</form:form>
</div>
<a class="btn btn-primary btn-lg btn-block" href="javascript:window.close();">关闭</a>
