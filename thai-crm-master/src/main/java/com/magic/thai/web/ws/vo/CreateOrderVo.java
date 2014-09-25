package com.magic.thai.web.ws.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "createOrder")
public class CreateOrderVo {

	private String token;
	private String orderContactor;
	private String orderContactorMobile;
	private String orderContactorEmail;
	private String hotelName;
	private String hotelAddress;
	private String hotelRoom;
	private String hotelRoomTel;

	private List<TravelerVo> travelers = new ArrayList<TravelerVo>();

	private List<BuyGoodsVo> goodses = new ArrayList<BuyGoodsVo>();

	@XmlElementWrapper(name = "goodses")
	@XmlElement(name = "goods")
	public List<BuyGoodsVo> getGoodses() {
		return goodses;
	}

	public void setGoodses(List<BuyGoodsVo> goodses) {
		this.goodses = goodses;
	}

	@XmlElementWrapper(name = "travelers")
	@XmlElement(name = "traveler")
	public List<TravelerVo> getTravelers() {
		if (travelers == null) {
			travelers = new ArrayList<TravelerVo>();
		}
		return travelers;
	}

	public void setTravelers(List<TravelerVo> travelers) {
		this.travelers = travelers;
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

	public String getOrderContactorEmail() {
		return orderContactorEmail;
	}

	public void setOrderContactorEmail(String orderContactorEmail) {
		this.orderContactorEmail = orderContactorEmail;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelAddress() {
		return hotelAddress;
	}

	public void setHotelAddress(String hotelAddress) {
		this.hotelAddress = hotelAddress;
	}

	public String getHotelRoom() {
		return hotelRoom;
	}

	public void setHotelRoom(String hotelRoom) {
		this.hotelRoom = hotelRoom;
	}

	public String getHotelRoomTel() {
		return hotelRoomTel;
	}

	public void setHotelRoomTel(String hotelRoomTel) {
		this.hotelRoomTel = hotelRoomTel;
	}

}
