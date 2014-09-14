package com.magic.thai.db.dao;

import java.util.List;

import com.magic.thai.db.domain.ChannelMerchantInv;

public interface ChannelMerchantInvDao {

	public ChannelMerchantInv loadById(int id);

	public Integer create(ChannelMerchantInv entity);

	public void update(ChannelMerchantInv entity);

	public List<ChannelMerchantInv> getInvs(int id);

}
