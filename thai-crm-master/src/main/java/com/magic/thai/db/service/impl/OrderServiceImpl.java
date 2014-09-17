package com.magic.thai.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magic.thai.db.dao.OrderDao;
import com.magic.thai.db.domain.Order;
import com.magic.thai.db.domain.User;
import com.magic.thai.db.service.OrderService;
import com.magic.thai.db.vo.OrderVo;
import com.magic.thai.exception.OrderStatusException;
import com.magic.thai.security.UserProfile;
import com.magic.thai.util.PaginationSupport;

@Service("orderService")
@Transactional
public class OrderServiceImpl extends ServiceHelperImpl<User> implements OrderService {

	@Autowired
	OrderDao orderDao;

	@Override
	public Order load(int id) {
		return orderDao.loadById(id);
	}

	@Override
	public Order fetch(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void confirm(Order order, UserProfile userprofile) throws OrderStatusException {
		// TODO Auto-generated method stub

	}

	@Override
	public void create(int orderId, UserProfile userprofile) throws OrderStatusException {
		// TODO Auto-generated method stub

	}

	@Override
	public void change(int orderId, String reason, UserProfile userprofile) throws OrderStatusException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int orderId, UserProfile userprofile) throws OrderStatusException {
		// TODO Auto-generated method stub

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
