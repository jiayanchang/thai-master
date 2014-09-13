package com.magic.thai.db.dao;

import java.io.Serializable;

import com.magic.thai.db.domain.GoodsDetails;

public interface GoodsDetailsDao {

	public GoodsDetails loadById(int id);

	public Serializable create(GoodsDetails entity);

	public void update(GoodsDetails entity);
}
