package com.magic.thai.web.ws.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "refundOrder")
public class RefundOrderVo {

	private String token;
	private String orderNo;
	private String reason;

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

	@Override
	public String toString() {
		return "QueryOrderVo [token=" + token + ", orderNo=" + orderNo + "]";
	}

}
