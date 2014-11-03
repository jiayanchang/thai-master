package com.magic.thai.db.dao;

import com.magic.thai.db.domain.MerchantOrderGoodsPickup;

public interface MerchantOrderGoodsPickupDao {

	public MerchantOrderGoodsPickup loadById(int id);

	public MerchantOrderGoodsPickup loadByMogId(int mogid);

	public Integer create(MerchantOrderGoodsPickup entity);

	public void update(MerchantOrderGoodsPickup entity);
}
