package com.magic.thai.web.ws.vo;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "createOrder")
public class CreateOrderVo {

	private Integer goodsId;
	private String token;
	private String orderContactor;
	private String orderContactorMobile;
	private String deptDate;

	public Date deptDateObj;// 解析后

	private List<TravelerVo> travelers;

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOrderContactor() {
		return orderContactor;
	}

	public void setOrderContactor(String orderContactor) {
		this.orderContactor = orderContactor;
	}

	public String getOrderContactorMobile() {
		return orderContactorMobile;
	}

	public void setOrderContactorMobile(String orderContactorMobile) {
		this.orderContactorMobile = orderContactorMobile;
	}

	public String getDeptDate() {
		return deptDate;
	}

	public void setDeptDate(String deptDate) {
		this.deptDate = deptDate;
	}

	@XmlElementWrapper(name = "travelers")
	@XmlElement(name = "traveler")
	public List<TravelerVo> getTravelers() {
		return travelers;
	}

	public void setTravelers(List<TravelerVo> travelers) {
		this.travelers = travelers;
	}

	@Override
	public String toString() {
		return "CreateOrderVo [goodsId=" + goodsId + ", token=" + token + ", orderContactor=" + orderContactor + ", orderContactorMobile="
				+ orderContactorMobile + ", deptDate=" + deptDate + ", travelers=" + travelers + "]";
	}

}
