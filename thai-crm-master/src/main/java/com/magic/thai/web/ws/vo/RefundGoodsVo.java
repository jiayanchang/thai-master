package com.magic.thai.web.ws.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "refundGoods")
public class RefundGoodsVo {

	public RefundGoodsVo() {

	}

	public RefundGoodsVo(int goodsId, int type, int qty, String deptDate) {
		this.goodsId = goodsId;
		this.type = type;
		this.qty = qty;
		this.deptDate = deptDate;
	}

	private int goodsId;
	private int type; // 0为退 1为改
	private int qty;// 需修改的数量
	private String deptDate;

	public static final class Type {
		public static final int REFUND = 0;
		public static final int CHANGE = 1;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getDeptDate() {
		return deptDate;
	}

	public void setDeptDate(String deptDate) {
		this.deptDate = deptDate;
	}

}
