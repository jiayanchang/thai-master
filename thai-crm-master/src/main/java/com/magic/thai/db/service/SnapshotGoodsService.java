package com.magic.thai.db.service;

import com.magic.thai.db.domain.MerchantOrderGoods;
import com.magic.thai.db.domain.SnapshotGoods;

public interface SnapshotGoodsService {

	public SnapshotGoods fetch(int id);

	public SnapshotGoods fetchByMogId(int id);

	public void create(MerchantOrderGoods merchantOrderGoods);
}
