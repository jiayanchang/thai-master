package com.magic.thai.db.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.magic.thai.util.CalendarUtils;

@Entity
@Table(name = "goods_price_seg")
public class GoodsPriceSegment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "goods_id")
	private Goods goods;

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

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public boolean exsits(Date date) {
		if (startDate == null && endDate == null) {
			return true;
		} else if (startDate == null && endDate != null) {
			return date.before(CalendarUtils.lastOfDay(endDate));
		} else if (startDate != null && endDate == null) {
			return date.after(startDate);
		} else {
			return date.after(startDate) && date.before(CalendarUtils.lastOfDay(endDate));
		}
	}

	@Override
	public String toString() {
		return "GoodsPriceSegment [id=" + id + ", goods=" + goods + ", startDate=" + startDate + ", endDate=" + endDate + ", orderBy="
				+ orderBy + ", auditPrice=" + auditPrice + ", childPrice=" + childPrice + "]";
	}
}
