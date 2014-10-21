<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

	<table class="table">
		<colgroup>
			<col class="col-xs-1">
			<col class="col-xs-3">
			<col class="col-xs-1">
			<col class="col-xs-3">
		</colgroup>
		<tr>
			<td>商品名称：</td>
			<td>${goods.title }</td>
			<td>行程天数：</td>
			<td>${goods.travelDays }</td>
		</tr>
		<tr>
			<td>英文名称：</td>
			<td>${goods.titleEn }</td>
			<td>库存数量：</td>
			<td>${goods.goodsCount }/天</td>
		</tr>
		<tr>
			<td>出发地：</td>
			<td>${goods.dept }</td>
			<td>目的地：</td>
			<td>${goods.arrived }</td>
		</tr>
		<tr>
			<td>单价(成人)：</td>
			<td>${goods.basePrice }</td>
			<td>单价(儿童)：</td>
			<td>${goods.basePriceChild }</td>
		</tr>
		<tr>
			<td>推荐理由：</td>
			<td colspan="3">${goods.summary }</td>
		</tr>
		<tr>
			<td>宣传图片：</td>
			<td colspan="3"><img src="${pageContext.request.contextPath}${goods.details.picPath }"/></td>
		</tr>
		
		<tr>
			<td>线路图片：</td>
			<td colspan="3">
				<img src="${pageContext.request.contextPath}${goods.details.linePicPathA }"/>
				<img src="${pageContext.request.contextPath}${goods.details.linePicPathB }"/>
				<img src="${pageContext.request.contextPath}${goods.details.linePicPathC }"/>
				<img src="${pageContext.request.contextPath}${goods.details.linePicPathD }"/>
			</td>
		</tr>
		
		<tr>
			<td>淡旺季价格：</td>
			<td colspan="3">
				<table id="price_tbl">
					<c:forEach var="segment" items="${goods.segments }">
						<tr>
							<td><fmt:formatDate value="${segment.startDate }" type="date"  pattern="yyyy/MM/dd"/></td>
							<td> - </td>
							<td><fmt:formatDate value="${segment.endDate }" type="date"  pattern="yyyy/MM/dd"/></td>
							<td> &nbsp;&nbsp;&nbsp;价格：</td>
							<td>${segment.auditPrice }</td>
							<td>（成人）</td>
							<td>${segment.childPrice }</td>
							<td>（儿童）</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		<tr>
			<td>行程安排：</td>
			<td colspan="3">${goods.details.travelPlan }</td>
		</tr>
		<tr>
			<td>费用说明：</td>
			<td colspan="3">${goods.details.costDesc }</td>
		</tr>
		<tr>
			<td>预定须知：</td>
			<td colspan="3">${goods.details.bookNotes }</td>
		</tr>
		<tr>
			<td>备注：</td>
			<td colspan="3">${goods.details.notes }</td>
		</tr>
	</table>
