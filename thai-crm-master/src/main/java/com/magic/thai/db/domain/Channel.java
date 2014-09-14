package com.magic.thai.db.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "channel")
public class Channel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Column
	private String name;

	@Column
	private String token;

	@Column
	private int status;

	@Column(name = "operator_id")
	private int operatorId;

	@Column(name = "operator_name")
	private String operatorName;

	@Column(name = "order_count")
	private int orderCount;

	@Column(name = "amount")
	private double amount;

	@Column(name = "goods_count")
	private int goodsCount;

	@Transient
	private List<ChannelGoodsInv> goodsInvs;
	@Transient
	private List<ChannelMerchantInv> merchantInvs;

	/**
	 * 0为启用，1为禁用，2为删除
	 * 
	 * @author yanchang
	 */
	public static final class Status {
		public static final int ENABLED = 0;
		public static final int DISABLED = 1;
		public static final int DELETED = 2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public int getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(int goodsCount) {
		this.goodsCount = goodsCount;
	}

	public List<ChannelGoodsInv> getGoodsInvs() {
		return goodsInvs;
	}

	public void setGoodsInvs(List<ChannelGoodsInv> goodsInvs) {
		this.goodsInvs = goodsInvs;
	}

	public List<ChannelMerchantInv> getMerchantInvs() {
		return merchantInvs;
	}

	public void setMerchantInvs(List<ChannelMerchantInv> merchantInvs) {
		this.merchantInvs = merchantInvs;
	}

	@Override
	public String toString() {
		return "Channel [id=" + id + ", name=" + name + ", token=" + token + ", status=" + status + ", operatorId=" + operatorId
				+ ", operatorName=" + operatorName + ", orderCount=" + orderCount + ", amount=" + amount + ", goodsCount=" + goodsCount
				+ "]";
	}

}
