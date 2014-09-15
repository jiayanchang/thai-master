package com.magic.thai.db.dao;

import java.util.List;

import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.vo.GoodsVo;
import com.magic.thai.util.PaginationSupport;

public interface GoodsDao {

	public Goods loadById(int id);

	public Integer create(Goods entity);

	public void update(Goods entity);

	public void delete(Goods Goods);

	public void delete(int GoodsId);

	public List<Goods> list(GoodsVo vo);

	public PaginationSupport getGoodsesPage(String title, String dept, String arr, Integer[] statuses, int currPage, Integer merchantId);

}
