function openDialog(id, orderNo) {
	$("#reason").val("");
	jQuery.ajax({
	    type: 'POST',
		encoding:"UTF-8",
	    dataType:"json", 
	    data : 'orderNo=' + orderNo,
	    contentType: "application/x-www-form-urlencoded;  charset=UTF-8",
	    url: "${pageContext.request.contextPath}/json/lock.json",
		success: function(result) {
			if(result.data.success) {
				dialog.dialog( "open" );
			} else {
				alert(result.data.message);
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
	      "提交": function(){
	    	  jQuery.ajax({
	    		    type: 'POST',
	    			encoding:"UTF-8",
	    		    dataType:"json", 
	    		    data : 'orderId=' + id + "&reason=" + $("#reason").val(),
	    		    contentType: "application/x-www-form-urlencoded;  charset=UTF-8",
	    		    url: "${pageContext.request.contextPath}/json/orderproc.json",
	    			success: function(result) {
	    				if(result.data.success) {
	    					dialog.dialog( "close" );
	    					$("form").submit();
	    				} else {
	    					alert(result.data.message);				
	    				}
	    			}
	    		});
	      },
	      "取消" : function() {
		        jQuery.ajax({
		    	    type: 'POST',
		    		encoding:"UTF-8",
		    	    dataType:"json", 
		    	    data : 'orderNo=' + orderNo,
		    	    contentType: "application/x-www-form-urlencoded;  charset=UTF-8",
		    	    url: "${pageContext.request.contextPath}/json/unlock.json",
		    		success: function(result) {
		    			if(result.data.success) {
		    				 dialog.dialog( "close" );
		    			} else {
		    				alert(result.data.message);
		    			}
		    		}
		    	});
	      }
	    },
	    close: function() {
	  	  dialog.dialog( "close" );
	    }
	});
}