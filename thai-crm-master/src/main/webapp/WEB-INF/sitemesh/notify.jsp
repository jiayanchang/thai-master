<%@ page language="java" pageEncoding="UTF-8"%>  
<script>
	function orderNotify() {
		if($("#orderMonitorSpan").length > 0) {
			jQuery.ajax({
			    type: 'POST',
				encoding:"UTF-8",
			    dataType:"json", 
			    contentType: "application/x-www-form-urlencoded;  charset=UTF-8",
			    url: "${pageContext.request.contextPath}/json/auditingOrderCount.json",
				success: function(result) {
					if(result.data.success) {
						//alert(result.data.message);
						$("#orderMonitorSpan").text("[" + result.data.data + "]");
					}
					var order_notify_time_task = setTimeout("orderNotify()", 10 * 60 * 1000);
				}
			});
		}
	}
	orderNotify();
</script>