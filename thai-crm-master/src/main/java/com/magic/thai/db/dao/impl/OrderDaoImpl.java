package com.magic.thai.db.dao.impl;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.dao.OrderDao;
import com.magic.thai.db.domain.Merchant;
import com.magic.thai.db.domain.Order;
import com.magic.thai.db.vo.OrderVo;
import com.magic.thai.util.PaginationSupport;

@Repository(value = "orderDao")
public class OrderDaoImpl extends HibernateCommonDAO<Order> implements OrderDao {

	@Autowired
	public OrderDaoImpl(SessionFactory sessionFactory) {
		// super.initDao();
		setEntityClass(Order.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public Order loadById(int id) {
		return super.loadById(id);
	}

	@Override
	public Integer create(Order entity) {
		return (Integer) super.create(entity);
	}

	@Override
	public PaginationSupport getOrderesPage(OrderVo vo, int currPage, Integer merchantId) {
		ArrayList<Criterion> criterions = new ArrayList<Criterion>();
		if (StringUtils.isNotBlank(vo.orderNo)) {
			criterions.add(Restrictions.eq("orderNo", vo.orderNo));
		}
		if (vo.statuses != null && vo.statuses.length > 0) {
			criterions.add(Restrictions.in("status", vo.statuses));
		}
		if (vo.channelId != null) {
			criterions.add(Restrictions.eq("channelId", vo.channelId));
		}
		if (merchantId != null) {
			criterions.add(Restrictions.eq("merchantId", merchantId));
		}
		criterions.add(Restrictions.ne("status", Merchant.Status.DELETED));
		return super.find(criterions, currPage, 30);
	}

}
