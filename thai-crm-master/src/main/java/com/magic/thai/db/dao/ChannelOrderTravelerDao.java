package com.magic.thai.db.dao;

import java.util.List;

import com.magic.thai.db.domain.ChannelOrderTraveler;

public interface ChannelOrderTravelerDao {

	public ChannelOrderTraveler loadById(int id);

	public void update(ChannelOrderTraveler entity);

	public Integer create(final ChannelOrderTraveler entity);

	public List<ChannelOrderTraveler> getTravelers(int channelOrderId);
}
