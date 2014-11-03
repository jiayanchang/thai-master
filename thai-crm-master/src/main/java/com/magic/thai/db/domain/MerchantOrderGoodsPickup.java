package com.magic.thai.db.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "merchant_order_goods_pickup")
@XmlRootElement
public class MerchantOrderGoodsPickup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(name = "merchant_order_goods_id", nullable = false)
	private int merchantOrderGoodsId;

	@Column(name = "merchant_order_id", nullable = false)
	private int merchantOrderId;

	@Column(name = "flight_no")
	private String flightNo;

	@Column(name = "arrived_date")
	private String arrivedDate;

	@Column(name = "arrived_time")
	private String arrivedTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMerchantOrderGoodsId() {
		return merchantOrderGoodsId;
	}

	public void setMerchantOrderGoodsId(int merchantOrderGoodsId) {
		this.merchantOrderGoodsId = merchantOrderGoodsId;
	}

	public int getMerchantOrderId() {
		return merchantOrderId;
	}

	public void setMerchantOrderId(int merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
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

}
