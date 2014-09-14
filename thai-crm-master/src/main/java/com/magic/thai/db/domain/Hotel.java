package com.magic.thai.db.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hotel")
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Column
	private String name;
	@Column(name = "translated_name")
	private String translatedName;
	@Column(name = "formerly_name")
	private String formerlyName;
	@Column
	private String continent;
	@Column
	private String country;
	@Column
	private String city;
	@Column
	private String state;
	@Column
	private String address;
	@Column(name = "chain_name")
	private String chainName;

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

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getChainName() {
		return chainName;
	}

	public void setChainName(String chainName) {
		this.chainName = chainName;
	}

	public String getTranslatedName() {
		return translatedName;
	}

	public void setTranslatedName(String translatedName) {
		this.translatedName = translatedName;
	}

	public String getFormerlyName() {
		return formerlyName;
	}

	public void setFormerlyName(String formerlyName) {
		this.formerlyName = formerlyName;
	}

	@Override
	public String toString() {
		return "Hotel [id=" + id + ", name=" + name + ", translatedName=" + translatedName + ", formerlyName=" + formerlyName
				+ ", continent=" + continent + ", country=" + country + ", city=" + city + ", state=" + state + ", address=" + address
				+ ", chainName=" + chainName + "]";
	}

}
