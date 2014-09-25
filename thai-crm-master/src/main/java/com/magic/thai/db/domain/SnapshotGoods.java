package com.magic.thai.db.domain;

import java.util.ArrayList;
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

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "snapshot_goods")
public class SnapshotGoods {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	@Expose
	private int id;

	@Column(name = "merchant_id")
	private int merchantId;

	@Column(name = "merchant_name")
	private String merchantName;

	@Column(name = "goods_id")
	private int goodsId;

	@Column(name = "merchant_order_goods_id")
	private int merchantOrderGoodsId;

	@Column(name = "channel_id")
	private int channelId;

	@Column
	@Expose
	private String title;
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
	private int travelDays;// 行程天数

	@Expose
	@Column(name = "goods_count")
	private int goodsCount;// 库存

	@Expose
	@Column(name = "sold_count")
	private int soldCount;// 已售

	@OneToOne(mappedBy = "goods", fetch = FetchType.LAZY)
	private SnapshotGoodsDetails details; // 非hibnate关联

	@OneToMany(mappedBy = "snapshotGoods")
	private List<SnapshotGoodsPriceSegment> segments = new ArrayList<SnapshotGoodsPriceSegment>();

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

	public int getGoodsId() {
		return goodsId;
	}

	public int getMerchantOrderGoodsId() {
		return merchantOrderGoodsId;
	}

	public void setMerchantOrderGoodsId(int merchantOrderGoodsId) {
		this.merchantOrderGoodsId = merchantOrderGoodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public SnapshotGoodsDetails getDetails() {
		return details;
	}

	public void setDetails(SnapshotGoodsDetails details) {
		this.details = details;
	}

	public List<SnapshotGoodsPriceSegment> getSegments() {
		return segments;
	}

	public void setSegments(List<SnapshotGoodsPriceSegment> segments) {
		this.segments = segments;
	}

	public int getSoldCount() {
		return soldCount;
	}

	public void setSoldCount(int soldCount) {
		this.soldCount = soldCount;
	}

	@Override
	public String toString() {
		return "SnapshotGoods [id=" + id + ", merchantId=" + merchantId + ", merchantName=" + merchantName + ", goodsId=" + goodsId
				+ ", merchantOrderGoodsId=" + merchantOrderGoodsId + ", channelId=" + channelId + ", title=" + title + ", dept=" + dept
				+ ", arrived=" + arrived + ", summary=" + summary + ", status=" + status + ", hotelId=" + hotelId + ", hotelName="
				+ hotelName + ", travelDays=" + travelDays + ", goodsCount=" + goodsCount + ", soldCount=" + soldCount + ", details="
				+ details + ", segments=" + segments + "]";
	}

}
