package com.magic.thai.db.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "goods")
@XmlRootElement
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

	@Column(name = "title_en")
	@Expose
	private String titleEn;

	@Column(name = "title_cn")
	@Expose
	private String titleCn;
	@Column
	private String dept;
	@Column
	private String arrived;
	@Column(columnDefinition = "TEXT")
	private String summary;
	@Column
	private int status;

	@Column(name = "hotel_id")
	private int hotelId;
	@Column(name = "hotel_name")
	private String hotelName;

	@Column(name = "travel_days")
	private int travelDays = 1;// 行程天数

	@Expose
	@Column(name = "goods_count")
	private int goodsCount = 200;// 库存

	@Column(name = "base_price")
	private double basePrice;// 底价

	@Expose
	@Column(name = "sold_count")
	private int soldCount;// 已售

	@OneToOne(mappedBy = "goods", fetch = FetchType.LAZY)
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

	@XmlTransient
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

	@XmlTransient
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

	@XmlTransient
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@XmlTransient
	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	@XmlTransient
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

	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}

	public String getTitleCn() {
		return titleCn;
	}

	public void setTitleCn(String titleCn) {
		this.titleCn = titleCn;
	}

	@XmlTransient
	public int getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(int goodsCount) {
		this.goodsCount = goodsCount;
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
		return details;
	}

	@XmlElementWrapper(name = "segments")
	@XmlElement(name = "segment")
	public List<GoodsPriceSegment> getSegments() {
		return segments;
	}

	@XmlTransient
	public int getSoldCount() {
		return soldCount;
	}

	public void setSoldCount(int soldCount) {
		this.soldCount = soldCount;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}

	@XmlTransient
	public boolean isAuditing() {
		return this.status == Status.AUDITING;
	}

	@XmlTransient
	public boolean isDeployed() {
		return this.status == Status.DEPLOYED;
	}

	public GoodsPriceSegment getSegment(Date date) {
		for (GoodsPriceSegment goodsPriceSegment : getSegments()) {
			if (goodsPriceSegment.getStartDate().after(date) && goodsPriceSegment.getEndDate().before(date)) {
				return goodsPriceSegment;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "Goods [merchantName=" + merchantName + ", title=" + title + ", dept=" + dept + ", arrived=" + arrived + ", status="
				+ status + ", hotelName=" + hotelName + ", travelDays=" + travelDays + ", goodsCount=" + goodsCount + ", basePrice="
				+ basePrice + "]";
	}

}
