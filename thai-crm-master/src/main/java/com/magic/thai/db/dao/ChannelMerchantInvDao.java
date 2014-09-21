package com.magic.thai.db.dao;

import java.util.List;

import com.magic.thai.db.domain.ChannelMerchantInv;
import com.magic.thai.db.domain.MerchantOrder;

public interface ChannelMerchantInvDao {

	public ChannelMerchantInv loadById(int id);

	public Integer create(ChannelMerchantInv entity);

	public void update(ChannelMerchantInv entity);

	public List<ChannelMerchantInv> getInvs(int id);

	public void delete(ChannelMerchantInv entity);

	public void updateStat(MerchantOrder merchantOrder);

}
