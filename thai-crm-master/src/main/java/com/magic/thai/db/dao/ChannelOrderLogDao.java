package com.magic.thai.db.dao;

import java.util.List;

import com.magic.thai.db.domain.ChannelOrder;
import com.magic.thai.db.domain.ChannelOrderLog;

public interface ChannelOrderLogDao {

	public Integer create(ChannelOrderLog channelLog);

	public List<ChannelOrderLog> getLogs(int channelOrderId);

	public List<ChannelOrderLog> getLogs(ChannelOrder channelOrder);
}
