package com.magic.thai.web.ws.vo;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.magic.thai.db.domain.ChannelOrderTraveler;

public class BuyGoodsVo {

	private String deptDate;
	private int qty;// 数量
	private int goodsId;
	private int type = ChannelOrderTraveler.Type.ADULT;
	private double price;

	private boolean needsPickup;
	private String flightNo;
	private String arrivedDate;
	private String arrivedTime;

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isChild() {
		return this.type != ChannelOrderTraveler.Type.ADULT;
	}

	public boolean isNeedsPickup() {
		return needsPickup;
	}

	public void setNeedsPickup(boolean needsPickup) {
		this.needsPickup = needsPickup;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getArrivedDate() {
		return arrivedDate;
	}

	public void setArrivedDate(String arrivedDate) {
		this.arrivedDate = arrivedDate;
	}

	public String getArrivedTime() {
		return arrivedTime;
	}

	public void setArrivedTime(String arrivedTime) {
		this.arrivedTime = arrivedTime;
	}

	public BuyGoodsVo fmt() {
		try {
			this.deptDateObj = DateUtils.parseDate(this.deptDate, new String[]{"yyyy/MM/dd"});
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return this;
	}
}
