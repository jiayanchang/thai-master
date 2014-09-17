package com.magic.thai.db.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "log_order")
public class OrderLog {

	public OrderLog() {

	}

	public OrderLog(Order order, User user, String content) {
		this.orderId = order.getId();
		this.content = content;
		this.creatorId = user.getId();
		this.createdDate = new Date();
		this.creatorName = user.getCodeName();
		this.creatorType = Type.USER;
	}

	public OrderLog(Order order, Channel channel, String content) {
		this.orderId = order.getId();
		this.content = content;
		this.creatorId = channel.getId();
		this.createdDate = new Date();
		this.creatorName = channel.getName();
		this.creatorType = Type.CHANNEL;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;
	@Column(name = "order_id")
	private int orderId;
	@Column(columnDefinition = "TEXT")
	private String content;

	@Column(name = "creator_id")
	private int creatorId;
	@Column(name = "creator_type")
	private int creatorType;
	@Column(name = "created_date")
	private Date createdDate;
	@Column(name = "creator_name")
	private String creatorName;

	/**
	 * 0=用户 1=客服 2=系统 3=渠道接口
	 * 
	 * @author yanchang
	 */
	public static class Type {
		public static final int USER = 0;
		public static final int STAFF = 1;
		public static final int SYSTEM = 2;
		public static final int CHANNEL = 3;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	public int getCreatorType() {
		return creatorType;
	}

	public void setCreatorType(int creatorType) {
		this.creatorType = creatorType;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

}