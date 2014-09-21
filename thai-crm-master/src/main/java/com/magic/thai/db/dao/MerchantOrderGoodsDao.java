package com.magic.thai.db.dao;

import com.magic.thai.db.domain.MerchantOrderGoods;

public interface MerchantOrderGoodsDao {

	public MerchantOrderGoods loadById(int id);

	public MerchantOrderGoods fetch(int id);

	public Integer create(MerchantOrderGoods entity);

	public void update(MerchantOrderGoods entity);
}
