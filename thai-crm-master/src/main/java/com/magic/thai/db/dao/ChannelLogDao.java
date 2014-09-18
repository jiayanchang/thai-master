package com.magic.thai.db.dao;

import java.util.List;

import com.magic.thai.db.domain.Channel;
import com.magic.thai.db.domain.ChannelLog;

public interface ChannelLogDao {

	public Integer create(ChannelLog channelLog);

	public List<ChannelLog> getLogs(int channelId);

	public List<ChannelLog> getLogs(Channel channel);
}
