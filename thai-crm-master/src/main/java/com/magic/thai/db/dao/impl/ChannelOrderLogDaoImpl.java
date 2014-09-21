package com.magic.thai.db.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.ChannelOrderLogDao;
import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.domain.ChannelOrder;
import com.magic.thai.db.domain.ChannelOrderLog;

@Repository(value = "channelOrderLogDao")
public class ChannelOrderLogDaoImpl extends HibernateCommonDAO<ChannelOrderLog> implements ChannelOrderLogDao {

	@Override
	public Integer create(ChannelOrderLog entity) {
		return (Integer) super.create(entity);
	}

	@Autowired
	public ChannelOrderLogDaoImpl(SessionFactory sessionFactory) {
		// super.initDao();
		setEntityClass(ChannelOrderLog.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public List<ChannelOrderLog> getLogs(int channelId) {
		return super.find("from ChannelOrderLog where channelOrderId = " + channelId);
	}

	@Override
	public List<ChannelOrderLog> getLogs(ChannelOrder channelOrder) {
		return getLogs(channelOrder.getId());
	}

}
