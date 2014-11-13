<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

	<table class="table">
		<colgroup>
			<col class="col-xs-1">
			<col class="col-xs-3">
			<col class="col-xs-1">
			<col class="col-xs-3">
		</colgroup>
		<tr>
			<td>Goods Name：</td>
			<td>${goods.title }</td>
			<td>Travel Days：</td>
			<td>${goods.travelDays }</td>
		</tr>
		<tr>
			<td>English Name：</td>
			<td>${goods.titleEn }</td>
			<td>Stock Qty：</td>
			<td>${goods.goodsCount }/day</td>
		</tr>
		<tr>
			<td>Departure：</td>
			<td>${goods.dept }</td>
			<td>Arrived：</td>
			<td>${goods.arrived }</td>
		</tr>
		<tr>
			<td>Price(Audit)：</td>
			<td>${goods.basePrice }</td>
			<td>Price(Child)：</td>
			<td>${goods.basePriceChild }</td>
		</tr>
		<tr>
			<td>Recommended Reason：</td>
			<td colspan="3">${goods.summary }</td>
		</tr>
		<tr>
			<td>Promotion Picture：</td>
			<td colspan="3"><img src="${pageContext.request.contextPath}${goods.details.picPath }"/></td>
		</tr>
		
		<tr>
			<td>Route Pictures：</td>
			<td colspan="3">
				<c:if test="${not empty goods.details.linePicPathA}">
					<img src="${pageContext.request.contextPath}${goods.details.linePicPathA }"/>
				</c:if>
				<c:if test="${ empty goods.details.linePicPathA}">
					<img width="100px" height="80px" src="${pageContext.request.contextPath}/css/none.jpg"/>
				</c:if>
				
				<c:if test="${not empty goods.details.linePicPathB}">
					<img src="${pageContext.request.contextPath}${goods.details.linePicPathB }"/>
				</c:if>
				<c:if test="${ empty goods.details.linePicPathB}">
					<img width="100px" height="80px" src="${pageContext.request.contextPath}/css/none.jpg"/>
				</c:if>
				
				<c:if test="${not empty goods.details.linePicPathC}">
					<img src="${pageContext.request.contextPath}${goods.details.linePicPathC }"/>
				</c:if>
				<c:if test="${ empty goods.details.linePicPathC}">
					<img width="100px" height="80px" src="${pageContext.request.contextPath}/css/none.jpg"/>
				</c:if>
				
				<c:if test="${not empty goods.details.linePicPathD}">
					<img src="${pageContext.request.contextPath}${goods.details.linePicPathD }"/>
				</c:if>
				<c:if test="${ empty goods.details.linePicPathD}">
					<img width="100px" height="80px" src="${pageContext.request.contextPath}/css/none.jpg"/>
				</c:if>
			</td>
		</tr>
		
		<tr>
			<td>Floating Price：</td>
			<td colspan="3">
				<table id="price_tbl">
					<c:forEach var="segment" items="${goods.segments }">
						<tr>
							<td><fmt:formatDate value="${segment.startDate }" type="date"  pattern="yyyy/MM/dd"/></td>
							<td> - </td>
							<td><fmt:formatDate value="${segment.endDate }" type="date"  pattern="yyyy/MM/dd"/></td>
							<td> &nbsp;&nbsp;&nbsp;Price：</td>
							<td>${segment.auditPrice }</td>
							<td>（Audit）</td>
							<td>${segment.childPrice }</td>
							<td>（Child）</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		<tr>
			<td>Itinerary：</td>
			<td colspan="3">${goods.details.travelPlan }</td>
		</tr>
		<tr>
			<td>Expense：</td>
			<td colspan="3">${goods.details.costDesc }</td>
		</tr>
		<tr>
			<td>Booking Notes：</td>
			<td colspan="3">${goods.details.bookNotes }</td>
		</tr>
		<tr>
			<td>Details：</td>
			<td colspan="3">${goods.details.notes }</td>
		</tr>
	</table>
