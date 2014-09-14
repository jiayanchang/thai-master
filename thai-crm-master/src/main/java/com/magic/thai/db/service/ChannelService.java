package com.magic.thai.db.service;

import com.magic.thai.db.domain.Channel;
import com.magic.thai.security.UserProfile;
import com.magic.thai.util.PaginationSupport;

public interface ChannelService {

	public Channel load(int id);

	public Channel fetch(int id);

	public void delete(int channelId, UserProfile userprofile);

	public void update(Channel channelbean, UserProfile userprofile);

	public int create(Channel channel, UserProfile userprofile);

	public PaginationSupport getChannelesPage(int queryPage);

}
