package com.magic.thai.db.service.impl;

import java.util.Date;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magic.thai.db.dao.OrderDao;
import com.magic.thai.db.dao.OrderLogDao;
import com.magic.thai.db.domain.Order;
import com.magic.thai.db.domain.OrderLog;
import com.magic.thai.db.domain.User;
import com.magic.thai.db.service.OrderService;
import com.magic.thai.db.vo.OrderVo;
import com.magic.thai.exception.OrderStatusException;
import com.magic.thai.exception.ThaiException;
import com.magic.thai.security.UserProfile;
import com.magic.thai.util.Asserts;
import com.magic.thai.util.PaginationSupport;

@Service("orderService")
@Transactional
public class OrderServiceImpl extends ServiceHelperImpl<User> implements OrderService {

	@Autowired
	OrderDao orderDao;

	@Autowired
	OrderLogDao orderLogDao;

	@Override
	public Order load(int id) {
		return orderDao.loadById(id);
	}

	@Override
	public Order fetch(int id) {
		Order order = orderDao.loadById(id);
		Hibernate.initialize(order.getTravelers());
		return order;
	}

	@Override
	@Transactional
	public void confirm(int orderId, String reason, UserProfile userprofile) throws ThaiException {
		Order order = orderDao.loadById(orderId);
		Asserts.isFalse(order.isCompleted(), new OrderStatusException("订单已经完成"));
		order.setStatus(Order.Status.COMPLETED);
		proc(orderId, reason, userprofile);
	}

	@Override
	@Transactional
	public void proc(int orderId, String reason, UserProfile userprofile) throws OrderStatusException {
		Order order = orderDao.loadById(orderId);
		proc(order, reason, userprofile);
	}

	public void proc(Order order, String reason, UserProfile userprofile) throws OrderStatusException {
		order.setLastOperatorId(userprofile.getUser().getId());
		order.setLastOperatorName(userprofile.getUser().getName());
		order.setLastOperatorDate(new Date());
		orderLogDao.create(new OrderLog(order, userprofile.getUser(), reason));
		orderDao.update(order);
	}

	@Override
	public void change(int orderId, String reason, UserProfile userprofile) throws OrderStatusException {

	}

	@Override
	public void delete(int orderId, UserProfile userprofile) throws OrderStatusException {
		Order order = orderDao.loadById(orderId);
		order.setStatus(Order.Status.DELETED);
		orderDao.update(order);
		orderLogDao.create(new OrderLog(order, userprofile.getUser(), "删除订单"));
	}

	@Override
	public void update(Order orderbean, UserProfile userprofile) throws OrderStatusException {
		// TODO Auto-generated method stub

	}

	@Override
	public PaginationSupport getOrderesPage(OrderVo vo, int merchantId) {
		vo.merchantId = merchantId;
		return orderDao.getOrderesPage(vo);
	}

	@Override
	public PaginationSupport getOrderesPage(OrderVo vo) {
		return orderDao.getOrderesPage(vo);
	}

	@Override
	public int auditingOrderCount(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

}
