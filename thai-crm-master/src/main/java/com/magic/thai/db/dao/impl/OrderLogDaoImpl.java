package com.magic.thai.db.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.dao.OrderLogDao;
import com.magic.thai.db.domain.Order;
import com.magic.thai.db.domain.OrderLog;

@Repository(value = "orderLogDao")
public class OrderLogDaoImpl extends HibernateCommonDAO<OrderLog> implements OrderLogDao {

	@Override
	public Integer create(OrderLog entity) {
		return (Integer) super.create(entity);
	}

	@Autowired
	public OrderLogDaoImpl(SessionFactory sessionFactory) {
		// super.initDao();
		setEntityClass(OrderLog.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public List<OrderLog> getLogs(int orderRootId) {
		return super.find("from OrderLog where orderRootId = " + orderRootId);
	}

	@Override
	public List<OrderLog> getLogs(Order order) {
		return getLogs(order.getId());
	}

}
