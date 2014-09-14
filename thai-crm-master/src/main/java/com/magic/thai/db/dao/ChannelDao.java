package com.magic.thai.db.dao;

import com.magic.thai.db.domain.Channel;
import com.magic.thai.util.PaginationSupport;

public interface ChannelDao {

	public Channel loadById(int id);

	public Integer create(Channel entity);

	public void update(Channel entity);

	public void delete(Channel Channel);

	public void delete(int ChannelId);

	public PaginationSupport getChannelesPage(int currPage);

}
