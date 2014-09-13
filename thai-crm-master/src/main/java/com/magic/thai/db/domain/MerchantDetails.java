package com.magic.thai.db.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "merchant_details")
public class MerchantDetails {

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "id", strategy = "assigned")
	@Column(nullable = false)
	private int id;

	@Column(name = "logo_path")
	private String logoPath;

	@Column(name = "notes")
	private String notes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
