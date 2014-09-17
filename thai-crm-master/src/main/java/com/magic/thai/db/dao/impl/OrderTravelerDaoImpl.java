package com.magic.thai.db.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.dao.OrderTravelerDao;
import com.magic.thai.db.domain.OrderTraveler;

@Repository(value = "orderTravelerDao")
public class OrderTravelerDaoImpl extends HibernateCommonDAO<OrderTraveler> implements OrderTravelerDao {

	@Autowired
	public OrderTravelerDaoImpl(SessionFactory sessionFactory) {
		// super.initDao();
		setEntityClass(OrderTraveler.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public OrderTraveler loadById(int id) {
		return super.loadById(id);
	}

	public Integer create(OrderTraveler orderTraveler) {
		return (Integer) super.create(orderTraveler);
	}

}
