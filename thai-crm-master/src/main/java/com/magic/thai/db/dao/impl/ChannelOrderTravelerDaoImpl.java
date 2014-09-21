package com.magic.thai.db.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.ChannelOrderTravelerDao;
import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.domain.ChannelOrderTraveler;

@Repository(value = "orderTravelerDao")
public class ChannelOrderTravelerDaoImpl extends HibernateCommonDAO<ChannelOrderTraveler> implements ChannelOrderTravelerDao {

	@Autowired
	public ChannelOrderTravelerDaoImpl(SessionFactory sessionFactory) {
		// super.initDao();
		setEntityClass(ChannelOrderTraveler.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public ChannelOrderTraveler loadById(int id) {
		return super.loadById(id);
	}

	public Integer create(ChannelOrderTraveler orderTraveler) {
		return (Integer) super.create(orderTraveler);
	}

	@Override
	public List<ChannelOrderTraveler> getTravelers(int channelOrderId) {
		return super.find("from ChannelOrderTraveler where order.id = " + channelOrderId);
	}

}
