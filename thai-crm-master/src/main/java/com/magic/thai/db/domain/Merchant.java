package com.magic.thai.db.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "merchant")
public class Merchant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	@Expose
	private int id;

	@Column(nullable = false)
	@Expose
	private String name;

	@Column(name = "name_en")
	@Expose
	private String nameEn;

	@Column(name = "code_name")
	@Expose
	private String codeName;
	@Column
	private String tel;
	@Column
	private String fax;
	@Column
	private String mobile;
	@Column
	private String address;

	@Column
	private int type = Type.NORMAL;
	@Column
	private int status = Status.ENABLED;

	@Column(name = "creator_id")
	private int creatorId;
	@Column(name = "created_date")
	private Date createdDate;
	@Column(name = "creator_name")
	private String creatorName;
	
	@Column(name = "is_opened_channel")
	private boolean isOpenedChannel;//目前如果为true那么表示当前商家为渠道，不具有商家的功能
	
	@Transient
	private MerchantDetails details; // 非hibnate关联

	/**
	 * 0=普通商家,2=平台
	 * 
	 * @author yanchang
	 *
	 */
	public static class Type {
		public static final int NORMAL = 0;
		public static final int CHANNEL = 1;
		public static final int PLATFORM = 2;
	}

	/**
	 * 0为启用，1为禁用，2为删除
	 * 
	 * @author yanchang
	 */
	public static final class Status {
		public static final int ENABLED = 0;
		public static final int DISABLED = 1;
		public static final int DELETED = 2;
	}

	public boolean isEnabled() {
		return this.status == Status.ENABLED;
	}

	public boolean isDisabled() {
		return this.status == Status.DISABLED;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public int getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public boolean isOpenedChannel() {
		return isOpenedChannel;
	}

	public boolean isChannel() {
		return this.type == Merchant.Type.CHANNEL;
	}
	
	public void setOpenedChannel(boolean isOpenedChannel) {
		this.isOpenedChannel = isOpenedChannel;
	}

	public MerchantDetails getDetails() {
		if (details == null) {
			details = new MerchantDetails();
		}
		return details;
	}

	public void setDetails(MerchantDetails details) {
		this.details = details;
	}

	public String getStatusDesc() {
		if (status == Status.DELETED) {
			return "已删除";
		} else if (status == Status.DISABLED) {
			return "已停用";
		} else {
			return "已启用";
		}
	}

	@Override
	public String toString() {
		return name + "(" + codeName + ")[" + getStatusDesc() + "]";
	}
}
