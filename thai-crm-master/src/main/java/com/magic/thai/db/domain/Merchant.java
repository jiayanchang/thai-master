package com.magic.thai.db.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "merchant")
public class Merchant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(nullable = false)
	private String name;
	@Column(name = "code_name")
	private String codeName;
	@Column
	private String tel;
	@Column
	private String mobile;

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

	@Transient
	private MerchantDetails details; // 非hibnate关联

	/**
	 * 0=普通商户,2=平台
	 * 
	 * @author yanchang
	 *
	 */
	public static class Type {
		public static final int NORMAL = 0;
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
