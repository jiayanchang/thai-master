<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<div class="content">
	<h1>审核商品</h1>
	<c:url var="addUrl" value="/a/goods/pass"/>
	<form:form action="${addUrl}" method="POST" commandName="goods">
		<form:hidden path="id"/>
		<table>
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
				<td>${goods.summary }<</td>
			</tr>
			<tr>
				<td>宣传图片：</td>
				<td><img src="${pageContext.request.contextPath}${goods.details.picPath }"/></td>
			</tr>
			
			<tr>
				<td>线路图片：</td>
				<td>
					<img src="${pageContext.request.contextPath}${goods.details.linePicPathA }"/>
					<img src="${pageContext.request.contextPath}${goods.details.linePicPathB }"/>
					<img src="${pageContext.request.contextPath}${goods.details.linePicPathC }"/>
					<img src="${pageContext.request.contextPath}${goods.details.linePicPathD }"/>
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
				<td>>${goods.details.bookNotes }</td>
			</tr>
			<tr>
				<td>备注：</td>
				<td>${goods.details.notes }</td>
			</tr>
		</table>
	</form:form>
	<button id="pass-dialog-confirm">商品确认</button>
	<button id="create-user">未通过</button>
</div>
<div id="dialog-form" title="Basic dialog">
<textarea rows="7" cols="32"></textarea>
</div>
<div id="pass-dialog" title="Basic dialog">
  <p>This is an animated dialog which is useful for displaying information. The dialog window can be moved, resized and closed with the 'x' icon.</p>
</div>
<script>
	
	
	$(function() {
		
		$( "#pass-dialog-confirm" ).click(function() {
	      $( "#pass-dialog" ).dialog( "open" );
	    });
		
		$( "#pass-dialog" ).dialog({
		  autoOpen: false,
	      resizable: false,
	      height:140,
	      modal: false,
	      buttons: {
	        "确认上架": function() {
	          $( this ).dialog( "close" );
	          $("form").submit();
	        },
	        Cancel: function() {
	          $( this ).dialog( "close" );
	        }
	      }
	    });
		
	    var dialog = $( "#dialog-form" ).dialog({
	      autoOpen: false,
	      height: 300,
	      width: 350,
	      title:'原因',
	      modal: true,
	      buttons: {
	        "确认拒绝": function(){
	        	
	        	
	        },
	        "取消" : function() {
	          dialog.dialog( "close" );
	        }
	      },
	      close: function() {
	    	  dialog.dialog( "close" );
	      }
	    });
	 
	    $( "#create-user" ).button().on( "click", function() {
	      dialog.dialog( "open" );
	    });
	  });
</script>