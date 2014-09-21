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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "channel_order")
@XmlRootElement
public class ChannelOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(name = "channel_order_no")
	private String channelOrderNo;

	@Column(name = "channel_id", nullable = false)
	private int channelId;

	@Column(name = "channel_name", nullable = false)
	private String channelName;

	@Column
	private String contractor;

	@Column(name = "contractor_mobile")
	private String contractorMobile;

	@Column(name = "contractor_email")
	private String contractorEmail;

	@Column(name = "amount")
	private double amount;

	@Column(name = "created_date")
	private Date createdDate;

	@Column
	private int status;

	@Column
	private int type;

	@OneToMany(mappedBy = "order")
	private List<ChannelOrderTraveler> travelers = new ArrayList<ChannelOrderTraveler>();

	@Transient
	private List<MerchantOrder> merchantOrders = new ArrayList<MerchantOrder>();

	public static final class Status {
		public static final int NEW = 0;
		public static final int COMPELED = 1;
	}

	public static final class OrderType {
		public static final int CHANNEL_ORDER = 0;
		public static final int OFFLINE_ORDER = 1;
	}

	@XmlElementWrapper(name = "travelers")
	@XmlElement(name = "traveler")
	public List<ChannelOrderTraveler> getTravelers() {
		return travelers;
	}

	public void setTravelers(List<ChannelOrderTraveler> travelers) {
		this.travelers = travelers;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getChannelOrderNo() {
		return channelOrderNo;
	}

	public void setChannelOrderNo(String channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getContractor() {
		return contractor;
	}

	public void setContractor(String contractor) {
		this.contractor = contractor;
	}

	public String getContractorMobile() {
		return contractorMobile;
	}

	public void setContractorMobile(String contractorMobile) {
		this.contractorMobile = contractorMobile;
	}

	public String getContractorEmail() {
		return contractorEmail;
	}

	public void setContractorEmail(String contractorEmail) {
		this.contractorEmail = contractorEmail;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<MerchantOrder> getMerchantOrders() {
		return merchantOrders;
	}

	public void setMerchantOrders(List<MerchantOrder> merchantOrders) {
		this.merchantOrders = merchantOrders;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setCompleted() {
		setStatus(Status.COMPELED);
	}

	public String getStatusDesc() {
		return status == Status.COMPELED ? "已完成" : "待确定";
	}

	public boolean isCompleted() {
		return status == Status.COMPELED;
	}

	@Override
	public String toString() {
		return "ChannelOrder [id=" + id + ", channelOrderNo=" + channelOrderNo + ", channelId=" + channelId + ", channelName="
				+ channelName + ", contractor=" + contractor + ", contractorMobile=" + contractorMobile + ", contractorEmail="
				+ contractorEmail + ", amount=" + amount + ", createdDate=" + createdDate + "]";
	}

}
