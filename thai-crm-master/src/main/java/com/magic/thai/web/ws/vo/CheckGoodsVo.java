package com.magic.thai.web.ws.vo;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "checkGoods")
public class CheckGoodsVo {

	private String token;
	private Integer goodsId;
	private String deptDate;
	private Integer travelerNum = 1;

	public Date deptDateObj;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public String getDeptDate() {
		return deptDate;
	}

	public void setDeptDate(String deptDate) {
		this.deptDate = deptDate;
	}

	public Integer getTravelerNum() {
		return travelerNum;
	}

	public void setTravelerNum(Integer travelerNum) {
		this.travelerNum = travelerNum;
	}

}
