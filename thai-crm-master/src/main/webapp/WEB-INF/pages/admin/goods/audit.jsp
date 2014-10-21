<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h1>审核商品</h1>
<c:url var="addUrl" value="/a/goods/pass"/>
<form:form action="${addUrl}" method="POST" commandName="goods">
	<form:hidden id="goodsId" path="id"/>
	<%@ include file="../../goods_table_view.jsp"%>
</form:form>
<button id="pass-dialog-confirm" class="btn btn-success">商品确认</button>
<button id="create-user" class="btn">未通过</button>

<div id="dialog-form" title="Basic dialog">
	<textarea rows="10" cols="40"></textarea>
</div>
<script>
	
	
	$(function() {
		
		$( "#pass-dialog-confirm" ).click(function() {
	      //$( "#pass-dialog" ).dialog( "open" );
	      if(confirm("确认上架?")) {
	    	  $("form").submit();  
	      }
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
	        	jQuery.ajax({
		  		    type: 'POST',
		  			encoding:"UTF-8",
		  		    dataType:"json", 
		  		    data:'id=' + $("#goodsId").val() + '&reason=' + $("#reason").val(),
		  		    contentType: "application/x-www-form-urlencoded;  charset=UTF-8",
		  		    url: "${pageContext.request.contextPath}/a/goods/reject.json",
		  			success: function(result) {//TODO 没执行success方法
		  				dialog.dialog( "close" );
		  				if(result.data.success) {
		  					alert("下架成功");
		  					window.location = '${pageContext.request.contextPath}/a/goods/list';
		  				} else {
		  					alert(result.data.message);
		  				}
		  			}
		  		});
	        	
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