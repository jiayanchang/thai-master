package com.magic.thai.db.dao;

import java.util.List;

import com.magic.thai.db.domain.ChannelGoodsInv;

public interface ChannelGoodsInvDao {

	public ChannelGoodsInv loadById(int id);

	public Integer create(ChannelGoodsInv entity);

	public void update(ChannelGoodsInv entity);

	public List<ChannelGoodsInv> getInvs(int id);
}
