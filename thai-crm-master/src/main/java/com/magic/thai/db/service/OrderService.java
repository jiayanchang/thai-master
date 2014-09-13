package com.magic.thai.db.service;

import com.magic.thai.db.domain.Order;
import com.magic.thai.db.domain.User;
import com.magic.thai.exception.OrderStatusException;
import com.magic.thai.security.UserProfile;
import com.magic.thai.util.PaginationSupport;

public interface OrderService {

	public Order load(int id);

	public Order fetch(int id);

	/**
	 * 商家提交上架
	 * 
	 * @param order
	 * @param userprofile
	 */
	public void submit(Order order, UserProfile userprofile) throws OrderStatusException;

	/**
	 * 生效
	 * 
	 * @param orderId
	 * @param userprofile
	 */
	public void pass(int orderId, UserProfile userprofile) throws OrderStatusException;

	/**
	 * 驳回
	 * 
	 * @param orderId
	 * @param userprofile
	 */
	public void reject(int orderId, String reason, UserProfile userprofile) throws OrderStatusException;

	/**
	 * 商家申请下架
	 * 
	 * @param order
	 * @param userprofile
	 */
	public void cancel(Order order, String reason, UserProfile userprofile) throws OrderStatusException;

	public void delete(int orderId, UserProfile userprofile) throws OrderStatusException;

	public void update(Order orderbean, UserProfile userprofile) throws OrderStatusException;

	public int create(Order order, User admin, UserProfile userprofile);

	public PaginationSupport getOrderesPage(String title, String dept, String arr, int status, int queryPage, int merchantId);

	public PaginationSupport getOrderesPage(String title, String dept, String arr, int status, int queryPage);

}
