package com.magic.thai.db.dao.impl;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.dao.OrderDao;
import com.magic.thai.db.domain.Merchant;
import com.magic.thai.db.domain.Order;
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
	public void delete(int OrderId) {
		super.getSession().createQuery("update Order set status = " + Order.Status.DELETED + " where id = " + OrderId).executeUpdate();
	}

	@Override
	public PaginationSupport getOrderesPage(String orderNo, Date startDate, Date endDate, String dept, String arr, int status,
			int currPage, Integer merchantId) {
		String hql = "from Order where status != " + Merchant.Status.DELETED + " and readOnly = false";
		// if (StringUtils.isNotBlank(title)) {
		// hql += " and title like '%" + title + "%'";
		// }
		// if (StringUtils.isNotBlank(dept)) {
		// hql += " and dept like '%" + dept + "%'";
		// }
		// if (StringUtils.isNotBlank(arrived)) {
		// hql += " and arrived like '%" + arrived + "%'";
		// }
		// if (status >= 0) {
		// hql += " and status = " + status;
		// }
		// if (merchantId != null) {
		// hql += " and merchantId = " + merchantId;
		// }
		return super.find(hql, currPage, 30);
	}

}
