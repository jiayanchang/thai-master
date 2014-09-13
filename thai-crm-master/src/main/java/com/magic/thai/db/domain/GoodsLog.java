package com.magic.thai.db.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "log_goods")
public class GoodsLog {

	public GoodsLog() {

	}

	public GoodsLog(Goods goods, User user, String content) {
		this.goodsId = goods.getId();
		this.goodsRootId = goods.getRootId();
		this.content = content;
		this.creatorId = user.getId();
		this.createdDate = new Date();
		this.creatorName = user.getCodeName();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;
	@Column(name = "goods_root_id")
	private int goodsRootId;
	@Column(name = "goods_id")
	private int goodsId;
	private String content;

	@Column(name = "creator_id")
	private int creatorId;
	@Column(name = "created_date")
	private Date createdDate;
	@Column(name = "creator_name")
	private String creatorName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGoodsRootId() {
		return goodsRootId;
	}

	public void setGoodsRootId(int goodsRootId) {
		this.goodsRootId = goodsRootId;
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

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	@Override
	public String toString() {
		return "GoodsLog [id=" + id + ", goodsRootId=" + goodsRootId + ", goodsId=" + goodsId + ", content=" + content + ", creatorId="
				+ creatorId + ", createdDate=" + createdDate + ", creatorName=" + creatorName + "]";
	}

}
