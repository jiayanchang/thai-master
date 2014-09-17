package com.magic.thai.db.dao;

import java.util.List;

import com.magic.thai.db.domain.Channel;
import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.User;
import com.magic.thai.db.vo.GoodsVo;
import com.magic.thai.util.PaginationSupport;

public interface GoodsDao {

	public Goods loadById(int id);

	public Integer create(Goods entity);

	public void update(Goods entity);

	public void delete(Goods Goods);

	public void delete(int GoodsId);

	public List<Goods> list(GoodsVo vo);

	public List<Goods> fetchList(GoodsVo vo, Channel channel);

	public PaginationSupport getGoodsesPage(GoodsVo vo);

	public int getAuditingGoodsCount(User user);
}
