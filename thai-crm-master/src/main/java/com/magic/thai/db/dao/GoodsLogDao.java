package com.magic.thai.db.dao;

import java.util.List;

import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.GoodsLog;

public interface GoodsLogDao {

	public Integer create(GoodsLog entity);

	public List<GoodsLog> getLogs(int goodsRootId);

	public List<GoodsLog> getLogs(Goods goods);
}
