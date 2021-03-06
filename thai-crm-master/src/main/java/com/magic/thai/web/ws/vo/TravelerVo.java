package com.magic.thai.web.ws.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.magic.thai.db.domain.ChannelOrderTraveler;

@XmlRootElement(name = "traveler")
public class TravelerVo {

	private String firstName;
	private String lastName;
	private Integer idType = ChannelOrderTraveler.IdType.IDCARD;
	private Integer gender = ChannelOrderTraveler.Gender.MALE;
	private String birth;
	private String effectiveDate;// 证件有效期
	private String idNo;
	private String nationality;
	private String mobile;
	private Integer type = ChannelOrderTraveler.Type.ADULT;// 乘客类型 默认成人

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

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

}
