package com.magic.thai.web.ws.vo;

import java.util.Date;

public class BuyGoodsVo {

	private String deptDate;
	private int qty;// 数量
	private int goodsId;
	private double price;

	public Date deptDateObj;

	public BuyGoodsVo() {
	}

	public BuyGoodsVo(String deptDate, int qty, int goodsId) {
		this.deptDate = deptDate;
		this.qty = qty;
		this.goodsId = goodsId;
	}

	public String getDeptDate() {
		return deptDate;
	}

	public void setDeptDate(String deptDate) {
		this.deptDate = deptDate;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
