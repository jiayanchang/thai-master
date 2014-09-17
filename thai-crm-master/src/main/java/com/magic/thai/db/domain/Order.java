package com.magic.thai.db.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "channel_order")
@XmlRootElement
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(name = "order_no")
	private String orderNo;

	@Column(name = "goods_id", nullable = false)
	private int goodsId;

	@Column(name = "goods_name", nullable = false)
	private String goodsName;

	@Column(name = "channel_id", nullable = false)
	private int channelId;

	@Column(name = "channel_name", nullable = false)
	private String channelName;

	@Column(name = "merchant_id", nullable = false)
	private int merchantId;

	@Column(name = "merchant_name", nullable = false)
	private String merchantName;

	@Column
	private String contractor;

	@Column(name = "contractor_tel")
	private String contractorTel;

	@Column(name = "contractor_mobile")
	private String contractorMobile;

	@Column(name = "total_price")
	private double totalPrice;

	@Column(name = "dept_date")
	private Date deptDate;

	@Column(name = "traveler_num", nullable = false)
	private int travelerNum;

	@Column(nullable = false)
	private int status = Status.NEW;

	@Column(name = "creator_id")
	private int creatorId;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "creator_name")
	private String creatorName;

	@Column(name = "creator_type")
	private int creatorType;

	@Column(name = "last_operator_id")
	private int lastOperatorId;

	@Column(name = "last_operator_date")
	private Date lastOperatorDate;

	@Column(name = "last_operator_name")
	private String lastOperatorName;

	@OneToMany(mappedBy = "order")
	private List<OrderTraveler> travelers = new ArrayList<OrderTraveler>();

	/**
	 * 0为启用，1为禁用，2为删除
	 * 
	 * @author yanchang
	 */
	public static final class Status {
		public static final int NEW = 0;// 新订单待确认
		public static final int COMPLETED = 1;// 已确认
		public static final int DELETED = 2;
	}

	public String getStatusDesc() {
		if (status == Status.DELETED) {
			return "已删除";
		} else if (status == Status.COMPLETED) {
			return "已确认";
		} else {
			return "待确认";
		}
	}

	/**
	 * 0=用户 1=客服 2=系统 3=渠道接口
	 * 
	 * @author yanchang
	 */
	public static class UserType {
		public static final int USER = 0;
		public static final int STAFF = 1;
		public static final int SYSTEM = 2;
		public static final int CHANNEL = 3;
	}

	@XmlTransient
	public boolean isCompleted() {
		return this.status == Status.COMPLETED;
	}

	@XmlTransient
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getContractor() {
		return contractor;
	}

	public void setContractor(String contractor) {
		this.contractor = contractor;
	}

	public String getContractorTel() {
		return contractorTel;
	}

	public void setContractorTel(String contractorTel) {
		this.contractorTel = contractorTel;
	}

	@XmlTransient
	public String getContractorMobile() {
		return contractorMobile;
	}

	public void setContractorMobile(String contractorMobile) {
		this.contractorMobile = contractorMobile;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getDeptDate() {
		return deptDate;
	}

	public void setDeptDate(Date deptDate) {
		this.deptDate = deptDate;
	}

	public int getTravelerNum() {
		return travelerNum;
	}

	public void setTravelerNum(int travelerNum) {
		this.travelerNum = travelerNum;
	}

	@XmlTransient
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@XmlTransient
	public int getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@XmlTransient
	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	@XmlTransient
	public int getLastOperatorId() {
		return lastOperatorId;
	}

	public void setLastOperatorId(int lastOperatorId) {
		this.lastOperatorId = lastOperatorId;
	}

	@XmlTransient
	public Date getLastOperatorDate() {
		return lastOperatorDate;
	}

	public void setLastOperatorDate(Date lastOperatorDate) {
		this.lastOperatorDate = lastOperatorDate;
	}

	@XmlTransient
	public String getLastOperatorName() {
		return lastOperatorName;
	}

	public void setLastOperatorName(String lastOperatorName) {
		this.lastOperatorName = lastOperatorName;
	}

	@XmlElementWrapper(name = "travelers")
	@XmlElement(name = "traveler")
	public List<OrderTraveler> getTravelers() {
		return travelers;
	}

	public void setTravelers(List<OrderTraveler> travelers) {
		this.travelers = travelers;
	}

	@XmlTransient
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	@XmlTransient
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
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

	public int getCreatorType() {
		return creatorType;
	}

	public void setCreatorType(int creatorType) {
		this.creatorType = creatorType;
	}

}
