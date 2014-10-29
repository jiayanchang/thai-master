package com.magic.thai.db.domain;

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
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "channel_order_traveler")
public class ChannelOrderTraveler {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private ChannelOrder order;

	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;

	@Column(name = "gender")
	private int gender;
	@Column(name = "id_type")
	private int idType = IdType.PASSPORT;
	@Column(name = "id_no")
	private String idNo;
	@Column(name = "effective_date")
	private String effectiveDate;// 证件有效期
	@Column(name = "birth")
	private String birth;
	@Column(name = "nationality")
	private String nationality;
	@Column(name = "mobile")
	private String mobile;
	@Column(name = "type", nullable = false)
	private int type;

	/**
	 * 0为启用，1为禁用，2为删除
	 * 
	 * @author yanchang
	 */
	public static final class Type {
		public static final int ADULT = 0;// 成人
		public static final int CHILD = 1;// 儿童
	}

	public String getTypeDesc() {
		if (type == Type.ADULT) {
			return "成人";
		} else {
			return "儿童";
		}
	}

	/**
	 * 0为男，1为女，2为未知或其他
	 * 
	 * @author yanchang
	 */
	public static final class Gender {
		public static final int MALE = 0;// 男
		public static final int FEMALE = 1;// 女
		public static final int UNKNOW = 2;// 未知
	}

	public String getGenderDesc() {
		if (gender == Gender.MALE) {
			return "男";
		} else if (gender == Gender.MALE) {
			return "女";
		} else {
			return "未知";
		}
	}

	/**
	 * 身份证、护照
	 * 
	 * @author yanchang
	 *
	 */
	public static class IdType {

		public static final int IDCARD = 0;
		public static final int PASSPORT = 1;
		public static final int OTHER = 9;
	}

	public String getIdTypeDesc() {
		if (idType == IdType.IDCARD) {
			return "身份证";
		} else if (idType == IdType.PASSPORT) {
			return "护照";
		} else {
			return "其他";
		}
	}

	@XmlTransient
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getIdType() {
		return idType;
	}

	public void setIdType(int idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@XmlTransient
	public ChannelOrder getOrder() {
		return order;
	}

	public void setOrder(ChannelOrder order) {
		this.order = order;
	}

	@XmlTransient
	public boolean isChild() {
		return this.type == Type.CHILD;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		return this.firstName + " " + this.lastName;
	}

	@Override
	public String toString() {
		return "ChannelOrderTraveler [id=" + id + ", order=" + order + ", firstName=" + firstName + ", lastName=" + lastName + ", gender="
				+ gender + ", idType=" + idType + ", idNo=" + idNo + ", effectiveDate=" + effectiveDate + ", birth=" + birth
				+ ", nationality=" + nationality + ", mobile=" + mobile + ", type=" + type + "]";
	}

}
