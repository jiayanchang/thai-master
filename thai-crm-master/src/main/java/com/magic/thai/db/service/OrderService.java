package com.magic.thai.db.service;

import java.util.Date;

import com.magic.thai.db.domain.MerchantOrder;
import com.magic.thai.db.domain.MerchantOrderGoodsPickup;
import com.magic.thai.db.domain.User;
import com.magic.thai.db.vo.OrderVo;
import com.magic.thai.exception.OrderStatusException;
import com.magic.thai.exception.ThaiException;
import com.magic.thai.security.GuestProfile;
import com.magic.thai.security.UserProfile;
import com.magic.thai.util.PaginationSupport;

public interface OrderService {

	public MerchantOrder load(int id);

	public MerchantOrder fetch(int id);

	/**
	 * 商家确认订单
	 * 
	 * @param order
	 * @param userprofile
	 */
	public void confirm(MerchantOrder order, UserProfile userprofile) throws ThaiException;

	/**
	 * 商家确认订单
	 * 
	 * @param order
	 * @param userprofile
	 */
	public void confirm(int orderId, UserProfile userprofile) throws ThaiException;

	/**
	 * 变更
	 * 
	 * @param orderId
	 * @param userprofile
	 */
	public void change(int orderId, String reason, UserProfile userprofile) throws OrderStatusException;

	/**
	 * 顾客修改订单
	 * 
	 * @param order
	 * @param guestProfile
	 * @throws OrderStatusException
	 */
	public MerchantOrder update(MerchantOrder orderbean, MerchantOrderGoodsPickup pickupBean, Date deptDate, GuestProfile guestProfile)
			throws OrderStatusException;

	/**
	 * 跟进处理
	 * 
	 * @param orderId
	 * @param reason
	 * @param userprofile
	 * @throws ThaiException
	 */
	public void proc(int orderId, String reason, UserProfile userprofile) throws ThaiException;

	public void delete(int orderId, UserProfile userprofile) throws OrderStatusException;

	public void update(MerchantOrder orderbean, UserProfile userprofile) throws OrderStatusException;

	public PaginationSupport getOrderesPage(OrderVo vo, int merchantId);

	public PaginationSupport getOrderesPage(OrderVo vo);

	/**
	 * 获取待确认订单数量
	 */
	public int auditingOrderCount(User user);
}
