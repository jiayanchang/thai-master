package com.magic.thai.db.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.ChannelLogDao;
import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.domain.Channel;
import com.magic.thai.db.domain.ChannelLog;

@Repository(value = "channelLogDao")
public class ChannelLogDaoImpl extends HibernateCommonDAO<ChannelLog> implements ChannelLogDao {

	@Override
	public Integer create(ChannelLog entity) {
		return (Integer) super.create(entity);
	}

	@Autowired
	public ChannelLogDaoImpl(SessionFactory sessionFactory) {
		// super.initDao();
		setEntityClass(ChannelLog.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public List<ChannelLog> getLogs(int channelId) {
		return super.find("from ChannelLog where channelId = " + channelId);
	}

	@Override
	public List<ChannelLog> getLogs(Channel channel) {
		return getLogs(channel.getId());
	}

}
