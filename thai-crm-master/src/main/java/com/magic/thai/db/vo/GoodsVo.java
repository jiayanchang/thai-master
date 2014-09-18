package com.magic.thai.db.vo;

public class GoodsVo {

	public GoodsVo() {
	}

	public GoodsVo(Integer[] statuses) {
		this.statuses = statuses;
	}

	public String titleKeyword;
	public int limitF4list = -1;
	public Integer[] statuses;

	public int page = 1;
	public int status = -1;

	public String dept;
	public String arr;

	public String merchantId;
	public String merchantName;
	public String goodsId;
	public String goodsName;

	public Integer[] excludeStatuses;

	public String getTitleKeyword() {
		return titleKeyword;
	}

	public void setTitleKeyword(String titleKeyword) {
		this.titleKeyword = titleKeyword;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

}
