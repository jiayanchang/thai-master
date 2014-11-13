package com.magic.thai.db.domain;

import java.util.ArrayList;
import java.util.Date;
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

	@Column(name = "refresh_time")
	private Date refreshTime;

	@Column(name = "is_opened_merchant")
	private boolean openedMerchant;

	@Column(name = "merchant_id")
	private Integer merchantId;

	@Transient
	private List<ChannelGoodsInv> goodsInvs = new ArrayList<ChannelGoodsInv>();
	@Transient
	private List<ChannelMerchantInv> merchantInvs = new ArrayList<ChannelMerchantInv>();

	/**
	 * 0为开启，1为关闭，2为删除
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

	public Date getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}

	public boolean isOpenedMerchant() {
		return openedMerchant;
	}

	public void setOpenedMerchant(boolean openedMerchant) {
		this.openedMerchant = openedMerchant;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public List<ChannelGoodsInv> getGoodsInvs() {
		if (goodsInvs == null) {
			goodsInvs = new ArrayList<ChannelGoodsInv>();
		}
		return goodsInvs;
	}

	public ChannelGoodsInv getGoodsInv(int goodsId) {
		for (ChannelGoodsInv channelGoodsInv : getGoodsInvs()) {
			if (channelGoodsInv.getGoodsId() == goodsId) {
				return channelGoodsInv;
			}
		}
		return null;
	}

	public void setGoodsInvs(List<ChannelGoodsInv> goodsInvs) {
		this.goodsInvs = goodsInvs;
	}

	public List<ChannelMerchantInv> getMerchantInvs() {
		if (merchantInvs == null) {
			merchantInvs = new ArrayList<ChannelMerchantInv>();
		}
		return merchantInvs;
	}

	public ChannelMerchantInv getMerchantInv(int merchantId) {
		for (ChannelMerchantInv channelMerchantInv : getMerchantInvs()) {
			if (channelMerchantInv.getMerchantId() == merchantId) {
				return channelMerchantInv;
			}
		}
		return null;
	}

	public void setMerchantInvs(List<ChannelMerchantInv> merchantInvs) {
		this.merchantInvs = merchantInvs;
	}

	public String getStatusDesc() {
		if (status == Status.DELETED) {
			return "已删除";
		} else if (status == Status.DISABLED) {
			return "已关闭";
		} else {
			return "已启用";
		}
	}

	public boolean isEnabled() {
		return status == Status.ENABLED;
	}

	public boolean isDisabled() {
		return status == Status.DISABLED;
	}

	@Override
	public String toString() {
		String goodsInvsDesc = "";
		String merchantInvsDesc = "";

		for (ChannelGoodsInv channelGoodsInv : goodsInvs) {
			goodsInvsDesc += channelGoodsInv + ";";
		}
		for (ChannelMerchantInv channelMerchantInv : merchantInvs) {
			merchantInvsDesc += channelMerchantInv + ";";
		}

		return "Channel [id=" + id + ", name=" + name + ", token=" + token + ", status=" + status + ", operatorId=" + operatorId
				+ ", operatorName=" + operatorName + ", orderCount=" + orderCount + ", amount=" + amount + ", goodsCount=" + goodsCount
				+ ", goodsInvs=" + goodsInvsDesc + ", merchantInvs=" + merchantInvsDesc + "]";
	}

}
