package com.magic.thai.web.ws.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "queryOrder")
public class QueryOrderVo {

	private String token;
	private String orderNo;

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

	@Override
	public String toString() {
		return "QueryOrderVo [token=" + token + ", orderNo=" + orderNo + "]";
	}

}
