package com.magic.thai.db.dao;

import com.magic.thai.db.domain.SnapshotGoods;

public interface SnapshotGoodsDao {

	public SnapshotGoods loadById(int id);

	public Integer create(SnapshotGoods entity);

}
