package com.magic.thai.web.ws.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "querGoods")
public class QueryGoodsVo {

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
