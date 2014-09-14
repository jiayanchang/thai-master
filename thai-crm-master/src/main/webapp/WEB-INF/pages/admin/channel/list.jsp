<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="UTF-8"%>  
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" /> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>List of User</title>

</head>
<body>
<div class="content">
<h1>渠道列表</h1>
<p>Here you can see the list of the Channel, add them, remove or update.</p>

<p>${message}</p>

<br/>
<div class="data">
<c:url var="submitUrl" value="/a/channel/list"/>
<form:form action="${submitUrl}" method="POST">
<table border="1px" cellpadding="0" cellspacing="0" width="100%">
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
	<a href="${pageContext.request.contextPath}/a/channel/edit/${channel.id} ">库存管理</a><br/>
	<a href="${pageContext.request.contextPath}/a/channel/close/${channel.id} ">关闭</a><br/>
	<a href="${pageContext.request.contextPath}/a/channel/open/${channel.id} ">开启</a><br/>
	<a href="${pageContext.request.contextPath}/a/channel/delete/${channel.id} ">删除</a><br/>
	</td>
</tr>
</c:forEach>
</tbody>
</table>
</div>
<table>
<tr>
<td>
</td>
<td>
<p><a href="${pageContext.request.contextPath}/a/channel/add">新增渠道</a></p>
</td>
</tr>
</table>
<input name="page" type="hidden" value="1"/>
</form:form>
</div>
</body>