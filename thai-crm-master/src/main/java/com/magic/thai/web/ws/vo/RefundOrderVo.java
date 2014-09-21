package com.magic.thai.web.ws.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "refundOrder")
public class RefundOrderVo {

	private String token;
	private String orderNo;
	private String reason;

	private List<RefundGoodsVo> goodsVo = new ArrayList<RefundGoodsVo>();

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<RefundGoodsVo> getGoodsVo() {
		return goodsVo;
	}

	public void setGoodsVo(List<RefundGoodsVo> goodsVo) {
		this.goodsVo = goodsVo;
	}

	@Override
	public String toString() {
		return "QueryOrderVo [token=" + token + ", orderNo=" + orderNo + "]";
	}

}
