package com.magic.thai.db.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tuser")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(nullable = false)
	private String name;

	@Column(name = "code_name", nullable = false)
	private String codeName;

	@Column(name = "merchant_id")
	private int merchantId;

	@Column(nullable = false)
	private String password;

	@Column(name = "password_source", nullable = false)
	private String passwordSource;

	@Column(name = "group_type", nullable = false)
	private int groupType;

	@Column(name = "login_name", nullable = false)
	private String loginName;

	@Column
	private String mobile;
	@Column
	private int status;
	@Column
	private int type;

	@Column(name = "creator_id")
	private int creatorId;
	@Column(name = "created_date")
	private Date createdDate;
	@Column(name = "creator_name")
	private String creatorName;

	/**
	 * 0为普通人员，2为平台员工
	 * 
	 * @author yanchang
	 */
	public static final class Type {
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public int getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public int getGroupType() {
		return groupType;
	}

	public void setGroupType(int groupType) {
		this.groupType = groupType;
	}

	public String getPasswordSource() {
		return passwordSource;
	}

	public void setPasswordSource(String passwordSource) {
		this.passwordSource = passwordSource;
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

	public boolean isPlatformUser() {
		return getType() == User.Type.PLATFORM;
	}

	@Override
	public String toString() {
		return name + "(" + codeName + ")" + "[" + loginName + "]";
	}

	public boolean isAdministrator() {
		return this.groupType == 1;
	}

	public void setAdministrator() {
		this.groupType = 1;
	}

	public boolean isEnabled() {
		return this.status == User.Status.ENABLED;
	}

	public boolean isDisabled() {
		return this.status == User.Status.DISABLED;
	}
}
