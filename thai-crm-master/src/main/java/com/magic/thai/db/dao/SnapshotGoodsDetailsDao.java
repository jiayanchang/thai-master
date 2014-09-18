package com.magic.thai.db.dao;

import java.io.Serializable;

import com.magic.thai.db.domain.SnapshotGoodsDetails;

public interface SnapshotGoodsDetailsDao {

	public SnapshotGoodsDetails loadById(int id);

	public Serializable create(SnapshotGoodsDetails entity);
}
