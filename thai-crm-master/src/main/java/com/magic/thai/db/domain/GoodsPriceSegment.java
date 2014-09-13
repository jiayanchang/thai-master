package com.magic.thai.db.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "goods_price_seg")
public class GoodsPriceSegment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(name = "goods_id")
	private int goodsId;
	@Column(name = "start_date")
	private Date startDate;
	@Column(name = "end_date")
	private Date endDate;
	@Column(name = "order_by")
	private int orderBy;

	@Column(name = "audit_price")
	private double auditPrice;
	@Column(name = "child_price")
	private double childPrice;

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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public double getAuditPrice() {
		return auditPrice;
	}

	public void setAuditPrice(double auditPrice) {
		this.auditPrice = auditPrice;
	}

	public double getChildPrice() {
		return childPrice;
	}

	public void setChildPrice(double childPrice) {
		this.childPrice = childPrice;
	}

	@Override
	public String toString() {
		return "GoodsPriceSegment [id=" + id + ", goodsId=" + goodsId + ", startDate=" + startDate + ", endDate=" + endDate + ", orderBy="
				+ orderBy + ", auditPrice=" + auditPrice + ", childPrice=" + childPrice + "]";
	}
}
