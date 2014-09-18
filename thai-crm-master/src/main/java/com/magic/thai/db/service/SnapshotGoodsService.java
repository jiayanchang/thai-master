package com.magic.thai.db.service;

import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.Order;
import com.magic.thai.db.domain.SnapshotGoods;

public interface SnapshotGoodsService {

	public SnapshotGoods fetch(int id);

	public void create(Goods goods, Order order);
}
