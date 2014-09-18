package com.magic.thai.db.dao;

import java.util.List;

import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.GoodsPriceSegment;

public interface GoodsPriceSegmentDao {

	public GoodsPriceSegment loadById(int id);

	public Integer create(GoodsPriceSegment entity);

	public void update(GoodsPriceSegment entity);

	public void delete(GoodsPriceSegment entity);

	public List<GoodsPriceSegment> getSegments(Goods goods);

}
