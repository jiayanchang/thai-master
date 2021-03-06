package com.magic.thai.db.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "channel_merchant_inv")
public class ChannelMerchantInv {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(name = "channel_id")
	private int channelId;

	@Column(name = "order_count")
	private int orderCount;

	@Column(name = "merchant_id")
	private int merchantId;

	@Column(name = "allocated_amount")
	private float allocatedAmount;

	@Column
	private double amount;

	@Column(name = "profit_rate")
	private double profitRate;// 利润比例

	@Column(name = "profit_price")
	private double profitPrice;// 利润加价

	@Transient
	private Merchant merchant;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public int getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}

	public float getAllocatedAmount() {
		return allocatedAmount;
	}

	public void setAllocatedAmount(float allocatedAmount) {
		this.allocatedAmount = allocatedAmount;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(double profitRate) {
		this.profitRate = profitRate;
	}

	public double getProfitPrice() {
		return profitPrice;
	}

	public void setProfitPrice(double profitPrice) {
		this.profitPrice = profitPrice;
	}

	@Override
	public String toString() {
		return "ChannelMerchantInv [id=" + id + ", channelId=" + channelId + ", orderCount=" + orderCount + ", merchantId=" + merchantId
				+ ", allocatedAmount=" + allocatedAmount + ", amount=" + amount + ", profitRate=" + profitRate + ", profitPrice="
				+ profitPrice + ", merchant=" + merchant + "]";
	}

}
