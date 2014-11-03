package com.magic.thai.db.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.magic.thai.util.DoubleUtils;

@Entity
@Table(name = "merchant_order_goods")
@XmlRootElement
public class MerchantOrderGoods {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(name = "goods_id", nullable = false)
	private int goodsId;

	@Column(name = "goods_name", nullable = false)
	private String goodsName;

	@Column(name = "channel_id", nullable = false)
	private int channelId;

	@Column(name = "merchant_id", nullable = false)
	private int merchantId;

	@Column(name = "amount")
	private double amount;// 商品单价

	@Column(name = "profit")
	private double profit;// 单个商品利润总价

	@Column(name = "dept_date")
	// @DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date deptDate;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "needs_pick_up")
	private Boolean needsPickup = false;

	@Column(name = "traveler_names")
	private String travelerNames;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "merchant_order_id")
	private MerchantOrder merchantOrder;

	@XmlTransient
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlTransient
	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	@XmlTransient
	public int getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}

	public Date getDeptDate() {
		return deptDate;
	}

	public void setDeptDate(Date deptDate) {
		this.deptDate = deptDate;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getTravelerNames() {
		return travelerNames;
	}

	public void setTravelerNames(String travelerNames) {
		this.travelerNames = travelerNames;
	}

	public MerchantOrder getMerchantOrder() {
		return merchantOrder;
	}

	public void setMerchantOrder(MerchantOrder merchantOrder) {
		this.merchantOrder = merchantOrder;
	}

	public double getTotalAmount() {
		return DoubleUtils.mul(this.getAmount(), new Double(this.getQuantity()));
	}

	public double getTotalProfitAmount() {
		return DoubleUtils.mul(this.getProfit(), new Double(this.getQuantity()));
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public Boolean isNeedsPickup() {
		return needsPickup;
	}

	public void setNeedsPickup(Boolean needsPickup) {
		this.needsPickup = needsPickup;
	}

}
