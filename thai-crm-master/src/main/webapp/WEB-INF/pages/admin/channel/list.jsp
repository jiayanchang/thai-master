<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="UTF-8"%>  
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

<h1>渠道列表</h1>
<p>${message}</p>
<br/>
<c:url var="submitUrl" value="/a/channel/list"/>
<form:form action="${submitUrl}" method="POST">
<c:if test="${not empty message}">
	<div class="alert alert-success" role="alert">${message }</div>
</c:if>
<table class="table table-striped table-hover ">
<thead>
<tr> 
	<th width="10%">渠道编号</th>
	<th width="10%">渠道名称</th>
	<th width="10%">订单量</th>
	<th width="10%">交易额（元）</th>
	<th width="10%">在售商品数</th>
	<th width="10%">运营人员</th>
	<th width="10%">状态</th>
	<th>操作</th>
</tr>
</thead>
<tbody>
<c:forEach items="${ps.items}" var="channel" >
<tr>
	<td>${channel.id}</td>
	<td>${channel.name}</td>
	<td>${channel.orderCount}</td>
	<td>${channel.amount}</td>
	<td>${channel.goodsCount}</td>
	<td>${channel.operatorName}</td>
	<td>${channel.statusDesc}</td>
	<td>
	<a class="btn btn-info" href="${pageContext.request.contextPath}/a/channel/edit/${channel.id} ">库存管理</a>
	
	<div class="btn-group">
	  <button class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
	    	设置
	  		<span class="caret"></span>
	  </button>
	  <ul class="dropdown-menu">
		<c:if test="${channel.enabled}">
			<li><a href="${pageContext.request.contextPath}/a/channel/close/${channel.id} ">关闭渠道</a></li>
		</c:if>
		<c:if test="${channel.disabled}">
			<li><a href="${pageContext.request.contextPath}/a/channel/open/${channel.id} ">开启渠道</a></li>
		</c:if>
		<c:if test="${not channel.openedMerchant}">
			<li><a href="${pageContext.request.contextPath}/a/channel/open/${channel.id}/merchant ">开通页面</a></li>
		</c:if>
		<c:if test="${channel.openedMerchant}">
			<li><a href="${pageContext.request.contextPath}/a/channel/close/${channel.id}/merchant ">关闭页面</a></li>
		</c:if>
		<li><a href="${pageContext.request.contextPath}/a/channel/delete/${channel.id} ">删除渠道</a></li>
	  </ul>
	</div>
	
	</td>
</tr>
</c:forEach>
</tbody>
</table>
<input name="page" type="hidden" value="1"/>
</form:form>
