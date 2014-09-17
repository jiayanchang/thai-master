package com.magic.thai.db.service;

import com.magic.thai.db.domain.Order;
import com.magic.thai.db.domain.User;
import com.magic.thai.db.vo.OrderVo;
import com.magic.thai.exception.OrderStatusException;
import com.magic.thai.security.UserProfile;
import com.magic.thai.util.PaginationSupport;

public interface OrderService {

	public Order load(int id);

	public Order fetch(int id);

	/**
	 * 商家确认订单
	 * 
	 * @param order
	 * @param userprofile
	 */
	public void confirm(Order order, UserProfile userprofile) throws OrderStatusException;

	/**
	 * 下单
	 * 
	 * @param orderId
	 * @param userprofile
	 */
	public void create(int orderId, UserProfile userprofile) throws OrderStatusException;

	/**
	 * 变更
	 * 
	 * @param orderId
	 * @param userprofile
	 */
	public void change(int orderId, String reason, UserProfile userprofile) throws OrderStatusException;

	/**
	 * 退款
	 * 
	 * @param order
	 * @param userprofile
	 */
	public void refund(Order order, String reason, UserProfile userprofile) throws OrderStatusException;

	public void delete(int orderId, UserProfile userprofile) throws OrderStatusException;

	public void update(Order orderbean, UserProfile userprofile) throws OrderStatusException;

	public PaginationSupport getOrderesPage(OrderVo vo, int merchantId);

	public PaginationSupport getOrderesPage(OrderVo vo);

	/**
	 * 获取待确认订单数量
	 */
	public int auditingOrderCount(User user);
}
