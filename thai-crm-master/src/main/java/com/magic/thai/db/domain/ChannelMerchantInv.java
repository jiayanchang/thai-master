package com.magic.thai.db.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "channel_merchant_inv")
public class ChannelMerchantInv {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(name = "goods_id")
	private int goodsId;

	@Column(name = "merchant_id")
	private int merchantId;

	@Column(name = "allocated_amount")
	private float allocatedAmount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
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

	@Override
	public String toString() {
		return "ChannelMerchantInv [id=" + id + ", goodsId=" + goodsId + ", merchantId=" + merchantId + ", allocatedAmount="
				+ allocatedAmount + "]";
	}

}
