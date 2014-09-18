package com.magic.thai.db.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "goods")
public class Goods {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	@Expose
	private int id;

	@Column(name = "merchant_id")
	private int merchantId;

	@Column(name = "merchant_name")
	private String merchantName;

	@Column
	@Expose
	private String title;
	@Column
	private String dept;
	@Column
	private String arrived;
	@Column
	private String summary;
	@Column
	private int status;

	@Column(name = "hotel_id")
	private int hotelId;
	@Column(name = "hotel_name")
	private String hotelName;

	@Column(name = "travel_days")
	private int travelDays;// 行程天数

	@Column(name = "child_total_price")
	private double childTotalPrice;

	@Column(name = "adult_total_price")
	private double adultTotalPrice;

	@Expose
	@Column(name = "goods_count")
	private int goodsCount;// 库存

	@Expose
	@Column(name = "sold_count")
	private int soldCount;// 已售

	@Transient
	private GoodsDetails details; // 非hibnate关联

	@OneToMany(mappedBy = "goods")
	private List<GoodsPriceSegment> segments = new ArrayList<GoodsPriceSegment>();

	/**
	 * 0=新商品待上架 1=待审核 2=已上架 3=已下架 4=已删除
	 * 
	 * @author yanchang
	 */
	public static final class Status {
		public static final int NEW = 0;// 新商品待上架
		public static final int AUDITING = 1;// 待审核
		public static final int REJECTED = 2; // 审核失败
		public static final int DEPLOYED = 3;// 已上架
		public static final int CANNELED = 4;// 已下架
		public static final int DELETED = 5;// 已删除
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getArrived() {
		return arrived;
	}

	public void setArrived(String arrived) {
		this.arrived = arrived;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public int getTravelDays() {
		return travelDays;
	}

	public void setTravelDays(int travelDays) {
		this.travelDays = travelDays;
	}

	public int getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(int goodsCount) {
		this.goodsCount = goodsCount;
	}

	public double getChildTotalPrice() {
		return childTotalPrice;
	}

	public void setChildTotalPrice(double childTotalPrice) {
		this.childTotalPrice = childTotalPrice;
	}

	public double getAdultTotalPrice() {
		return adultTotalPrice;
	}

	public void setAdultTotalPrice(double adultTotalPrice) {
		this.adultTotalPrice = adultTotalPrice;
	}

	public void setDetails(GoodsDetails details) {
		this.details = details;
	}

	public void setSegments(List<GoodsPriceSegment> segments) {
		this.segments = segments;
	}

	public String getStatusDesc() {
		if (status == Status.DELETED) {
			return "已删除";
		} else if (status == Status.CANNELED) {
			return "已下架";
		} else if (status == Status.REJECTED) {
			return "未通过";
		} else if (status == Status.DEPLOYED) {
			return "已上架";
		} else if (status == Status.AUDITING) {
			return "待审核";
		} else {
			return "新商品";
		}
	}

	public GoodsDetails getDetails() {
		if (details == null) {
			details = new GoodsDetails();
		}
		return details;
	}

	public List<GoodsPriceSegment> getSegments() {
		return segments;
	}

	public int getSoldCount() {
		return soldCount;
	}

	public void setSoldCount(int soldCount) {
		this.soldCount = soldCount;
	}

	public boolean isAuditing() {
		return this.status == Status.AUDITING;
	}

	public boolean isDeployed() {
		return this.status == Status.DEPLOYED;
	}

	public GoodsPriceSegment getLastSegment() {
		if (getSegments().size() > 0) {
			return getSegments().get(getSegments().size() - 1);
		}
		return null;
	}

	@Override
	public String toString() {
		return "Goods [merchantName=" + merchantName + ", title=" + title + ", dept=" + dept + ", arrived=" + arrived + ", status="
				+ status + ", hotelName=" + hotelName + ", travelDays=" + travelDays + ", goodsCount=" + goodsCount + ", childTotalPrice="
				+ childTotalPrice + ", adultTotalPrice=" + adultTotalPrice + "]";
	}

	// @Override
	// public boolean equals(Object obj) {
	// Goods goods = (Goods) obj;
	//
	// return goods.getArrived().equalsIgnoreCase(this.getArrived()) &&
	// goods.getDept().equalsIgnoreCase(this.getDept())
	// && goods.getTravelDays() == this.getTravelDays() &&
	// this.getAdultTotalPrice() == goods.getAdultTotalPrice()
	// && this.getChildTotalPrice() == goods.getChildTotalPrice() &&
	// this.getHotelId() == goods.getHotelId() && this.get;
	// }
}
