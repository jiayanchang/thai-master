package com.magic.thai.db.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(name = "order_no", nullable = false)
	private String orderNo;

	@Column(name = "channel_id", nullable = false)
	private int channelId;
	@Column(name = "merchant_id", nullable = false)
	private int merchantId;
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
	private int status;

	@Column(name = "creator_id")
	private int creatorId;
	@Column(name = "created_date")
	private Date createdDate;
	@Column(name = "creator_name")
	private String creatorName;

	@Column(name = "last_operator_id")
	private int lastOperatorId;
	@Column(name = "last_operator_date")
	private Date lastOperatorDate;
	@Column(name = "last_operator_name")
	private String lastOperatorName;

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

	// public String getStatusDesc() {
	// if (status == Status.DELETED) {
	// return "已删除";
	// } else if (status == Status.DISABLED) {
	// return "已停用";
	// } else {
	// return "已启用";
	// }
	// }

}
