package com.magic.thai.db.dao;

import java.io.Serializable;

import com.magic.thai.db.domain.MerchantDetails;

public interface MerchantDetailsDao {
	
	public MerchantDetails loadById(int id);

	public Serializable create(MerchantDetails entity);

	public void update(MerchantDetails entity);
}
