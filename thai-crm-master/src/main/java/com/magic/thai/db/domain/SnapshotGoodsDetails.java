package com.magic.thai.db.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "snapshot_goods_details")
public class SnapshotGoodsDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private int id;

	@Column(name = "goods_details_id")
	private int goodsDetailsId;

	@OneToOne(targetEntity = SnapshotGoods.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "snapshot_goods_id")
	@XmlTransient
	private SnapshotGoods goods;

	@Column(name = "travel_plan", columnDefinition = "TEXT")
	private String travelPlan;

	@Column(name = "cost_desc", columnDefinition = "TEXT")
	private String costDesc;
	@Column(name = "book_notes", columnDefinition = "TEXT")
	private String bookNotes;
	@Column(name = "pic_path")
	private String picPath;
	@Column(columnDefinition = "TEXT")
	private String notes;
	@Column(name = "line_pic_path_a")
	private String linePicPathA;
	@Column(name = "line_pic_path_b")
	private String linePicPathB;
	@Column(name = "line_pic_path_c")
	private String linePicPathC;
	@Column(name = "line_pic_path_d")
	private String linePicPathD;
	@Column(name = "line_pic_path_e")
	private String linePicPathE;
	@Column(name = "line_pic_path_f")
	private String linePicPathF;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTravelPlan() {
		return travelPlan;
	}

	public void setTravelPlan(String travelPlan) {
		this.travelPlan = travelPlan;
	}

	public String getCostDesc() {
		return costDesc;
	}

	public void setCostDesc(String costDesc) {
		this.costDesc = costDesc;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getLinePicPathA() {
		return linePicPathA;
	}

	public void setLinePicPathA(String linePicPathA) {
		this.linePicPathA = linePicPathA;
	}

	public String getLinePicPathB() {
		return linePicPathB;
	}

	public int getGoodsDetailsId() {
		return goodsDetailsId;
	}

	public void setGoodsDetailsId(int goodsDetailsId) {
		this.goodsDetailsId = goodsDetailsId;
	}

	public void setLinePicPathB(String linePicPathB) {
		this.linePicPathB = linePicPathB;
	}

	public String getLinePicPathC() {
		return linePicPathC;
	}

	public void setLinePicPathC(String linePicPathC) {
		this.linePicPathC = linePicPathC;
	}

	public String getLinePicPathD() {
		return linePicPathD;
	}

	public void setLinePicPathD(String linePicPathD) {
		this.linePicPathD = linePicPathD;
	}

	public String getLinePicPathE() {
		return linePicPathE;
	}

	public void setLinePicPathE(String linePicPathE) {
		this.linePicPathE = linePicPathE;
	}

	public String getLinePicPathF() {
		return linePicPathF;
	}

	public void setLinePicPathF(String linePicPathF) {
		this.linePicPathF = linePicPathF;
	}

	public String getBookNotes() {
		return bookNotes;
	}

	public void setBookNotes(String bookNotes) {
		this.bookNotes = bookNotes;
	}

	public SnapshotGoods getGoods() {
		return goods;
	}

	public void setGoods(SnapshotGoods goods) {
		this.goods = goods;
	}

}
