package com.magic.thai.db.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "channel_goods_inv")
public class ChannelGoodsInv {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(name = "goods_id")
	private int goodsId;

	@Column(name = "channel_id")
	private int channelId;

	@Column(name = "allocated_amount")
	private float allocatedAmount;

	@Transient
	private Goods goods;

	/**
	 * 分配利润后的价格
	 */
	@Column(name = "profit_price")
	private double profitPrice;

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

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public float getAllocatedAmount() {
		return allocatedAmount;
	}

	public void setAllocatedAmount(float allocatedAmount) {
		this.allocatedAmount = allocatedAmount;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public double getProfitPrice() {
		return profitPrice;
	}

	public void setProfitPrice(double profitPrice) {
		this.profitPrice = profitPrice;
	}

	@Override
	public String toString() {
		return "ChannelGoodsInv [id=" + id + ", goodsId=" + goodsId + ", channelId=" + channelId + ", allocatedAmount=" + allocatedAmount
				+ ", goods=" + goods + ", profitPrice=" + profitPrice + "]";
	}

}
