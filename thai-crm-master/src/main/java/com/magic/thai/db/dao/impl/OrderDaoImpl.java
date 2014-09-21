package com.magic.thai.db.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.dao.OrderDao;
import com.magic.thai.db.domain.Merchant;
import com.magic.thai.db.domain.Order;
import com.magic.thai.db.domain.User;
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
	public Order fetch(int id) {
		Order order = loadById(id);
		Hibernate.initialize(order.getTravelers());
		return order;
	}

	@Override
	public Order loadByNo(String orderNo) {
		ArrayList<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("orderNo", orderNo));
		criterions.add(Restrictions.ne("status", Merchant.Status.DELETED));
		List<Order> orders = super.find(criterions);
		return orders == null || orders.size() == 0 ? null : orders.get(0);
	}

	@Override
	public Order fetchByNo(String orderNo) {
		Order order = loadByNo(orderNo);
		if (order != null) {
			Hibernate.initialize(order.getTravelers());
		}
		return order;
	}

	@Override
	public Integer create(Order entity) {
		return (Integer) super.create(entity);
	}

	@Override
	public PaginationSupport getOrderesPage(OrderVo vo) {
		ArrayList<Criterion> criterions = new ArrayList<Criterion>();
		if (StringUtils.isNotBlank(vo.orderNo)) {
			criterions.add(Restrictions.eq("orderNo", vo.orderNo));
		}
		if (vo.statuses != null && vo.statuses.length > 0) {
			criterions.add(Restrictions.in("status", vo.statuses));
		}
		if (vo.status != null && vo.status >= 0) {
			criterions.add(Restrictions.eq("status", vo.status));
		}
		if (vo.channelId != null) {
			criterions.add(Restrictions.eq("channelId", vo.channelId));
		}
		if (vo.merchantId != null) {
			criterions.add(Restrictions.eq("merchantId", vo.merchantId));
		}
		if (StringUtils.isNotBlank(vo.startDate)) {
			criterions.add(Restrictions.ge("createdDate", vo.startDate));
		}
		if (StringUtils.isNotBlank(vo.endDate)) {
			criterions.add(Restrictions.le("createdDate", vo.endDate));
		}
		if (StringUtils.isNotBlank(vo.dept)) {
			criterions.add(Restrictions.eq("dept", vo.dept));
		}
		if (StringUtils.isNotBlank(vo.dept)) {
			criterions.add(Restrictions.eq("createdDate", vo.dept));
		}

		criterions.add(Restrictions.ne("status", Merchant.Status.DELETED));
		return super.find(criterions, vo.page, 30);
	}

	@Override
	public int auditingOrderCount(User user) {
		String hql = "select count(*) from Order where status = " + Order.Status.NEW;
		if (!user.isPlatformUser()) {
			hql += " and merchantId = " + user.getMerchantId();
		}

		return ((Long) super.getSession().createQuery(hql).uniqueResult()).intValue();
	}
}
