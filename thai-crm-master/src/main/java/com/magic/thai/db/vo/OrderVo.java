package com.magic.thai.db.vo;

public class OrderVo {

	public OrderVo() {
	}

	public OrderVo(Integer[] statuses) {
		this.statuses = statuses;
	}

	public Integer channelId;
	public Integer orderType;
	public Integer merchantId;

	public String merchantName;
	public String channelName;

	public String orderNo;
	public String startDate;
	public String endDate;
	public String dept;
	public String arr;
	public int limitF4list = -1;
	public Integer[] statuses;
	public Integer status = -1;

	public int page = 1;

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getArr() {
		return arr;
	}

	public void setArr(String arr) {
		this.arr = arr;
	}

	public int getLimitF4list() {
		return limitF4list;
	}

	public void setLimitF4list(int limitF4list) {
		this.limitF4list = limitF4list;
	}

	public Integer[] getStatuses() {
		return statuses;
	}

	public void setStatuses(Integer[] statuses) {
		this.statuses = statuses;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

}
