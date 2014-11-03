package com.magic.thai.db.service.impl;

import java.util.Date;

import org.apache.http.impl.cookie.DateParseException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magic.thai.db.dao.ChannelOrderDao;
import com.magic.thai.db.dao.ChannelOrderTravelerDao;
import com.magic.thai.db.dao.GoodsDao;
import com.magic.thai.db.dao.MerchantOrderDao;
import com.magic.thai.db.dao.MerchantOrderGoodsDao;
import com.magic.thai.db.dao.MerchantOrderGoodsPickupDao;
import com.magic.thai.db.dao.MerchantOrderNotesDao;
import com.magic.thai.db.domain.MerchantOrder;
import com.magic.thai.db.domain.MerchantOrderGoods;
import com.magic.thai.db.domain.MerchantOrderGoodsPickup;
import com.magic.thai.db.domain.MerchantOrderNotes;
import com.magic.thai.db.domain.User;
import com.magic.thai.db.service.ChannelOrderService;
import com.magic.thai.db.service.OrderService;
import com.magic.thai.db.service.SnapshotGoodsService;
import com.magic.thai.db.service.strategy.LockManager;
import com.magic.thai.db.vo.OrderVo;
import com.magic.thai.exception.OrderStatusException;
import com.magic.thai.exception.ThaiException;
import com.magic.thai.security.GuestProfile;
import com.magic.thai.security.UserProfile;
import com.magic.thai.util.Asserts;
import com.magic.thai.util.MailUtils;
import com.magic.thai.util.PaginationSupport;
import com.magic.thai.util.TempleteUtils;

@Service("orderService")
@Transactional
public class OrderServiceImpl extends ServiceHelperImpl<MerchantOrder> implements OrderService {

	@Autowired
	GoodsDao goodsDao;
	@Autowired
	MerchantOrderDao merchantOrderDao;
	@Autowired
	SnapshotGoodsService snapshotGoodsService;
	@Autowired
	ChannelOrderDao channelOrderDao;
	@Autowired
	ChannelOrderService channelOrderService;
	@Autowired
	MerchantOrderNotesDao merchantOrderNotesDao;
	@Autowired
	MerchantOrderGoodsDao merchantOrderGoodsDao;
	@Autowired
	ChannelOrderTravelerDao channelOrderTravelerDao;
	@Autowired
	MerchantOrderGoodsPickupDao merchantOrderGoodsPickupDao;

	@Override
	public MerchantOrder load(int id) {
		return merchantOrderDao.loadById(id);
	}

	@Override
	public MerchantOrder fetch(int id) {
		MerchantOrder order = merchantOrderDao.loadById(id);
		Hibernate.initialize(order.getGoodses());
		order.setTravelers(channelOrderTravelerDao.getTravelers(order.getChannelOrderId()));
		return order;
	}

	@Override
	@Transactional
	public void confirm(int orderId, UserProfile userprofile) throws ThaiException {
		MerchantOrder order = merchantOrderDao.loadById(orderId);
		confirm(order, userprofile);
	}

	@Override
	public void confirm(MerchantOrder order, UserProfile userprofile) throws ThaiException {
		Asserts.isFalse(order.isCompleted(), new OrderStatusException("订单已经完成"));
		order.setStatus(MerchantOrder.Status.COMPLETED);
		order.setLastOperatorId(userprofile.getUser().getId());
		order.setLastOperatorDate(new Date());
		order.setLastOperatorName(userprofile.getUser().getName());
		merchantOrderDao.update(order);
		merchantOrderNotesDao.create(new MerchantOrderNotes(order, userprofile.getUser(), "Confirmed the order", null));
		channelOrderService.confirm(order.getChannelOrderId());
		LockManager.unlock(order.getOrderNo());
		// mail to custom
		try {
			MailUtils.sendEmail(order.getContractorEmail(), "您在" + order.getChannelName() + "购买的旅游产品已经确认",
					TempleteUtils.genCompleteContent(order, merchantOrderGoodsPickupDao.loadByMogId(order.getGoodses().get(0).getId())),
					null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void proc(int orderId, String reason, UserProfile userprofile) throws OrderStatusException {
		MerchantOrder order = merchantOrderDao.loadById(orderId);
		proc(order, reason, userprofile);
	}

	public void proc(MerchantOrder order, String reason, UserProfile userprofile) throws OrderStatusException {
		order.setLastOperatorId(userprofile.getUser().getId());
		order.setLastOperatorName(userprofile.getUser().getName());
		order.setLastOperatorDate(new Date());

		Date lockdate = null;
		try {
			lockdate = LockManager.getLockedTime(userprofile.getUser().getId(), order.getOrderNo());
		} catch (DateParseException e) {
			e.printStackTrace();
		} catch (ThaiException e) {
			e.printStackTrace();
		}

		merchantOrderNotesDao.create(new MerchantOrderNotes(order, userprofile.getUser(), reason, lockdate));
		merchantOrderDao.update(order);
	}

	@Override
	public void change(int orderId, String reason, UserProfile userprofile) throws OrderStatusException {

	}

	@Override
	public void delete(int orderId, UserProfile userprofile) throws OrderStatusException {
		MerchantOrder order = merchantOrderDao.loadById(orderId);
		order.setStatus(MerchantOrder.Status.DELETED);
		merchantOrderDao.update(order);
		merchantOrderNotesDao.create(new MerchantOrderNotes(order, userprofile.getUser(), "删除订单"));
	}

	@Override
	public void update(MerchantOrder order, UserProfile userprofile) throws OrderStatusException {
		merchantOrderDao.update(order);
	}

	@Override
	public MerchantOrder update(MerchantOrder orderbean, MerchantOrderGoodsPickup pickupBean, Date deptDate, GuestProfile guestProfile)
			throws OrderStatusException {

		MerchantOrder order = fetch(orderbean.getId());
		order.setHotelAddress(orderbean.getHotelAddress());
		order.setHotelName(orderbean.getHotelName());
		order.setHotelRoom(orderbean.getHotelRoom());
		order.setHotelRoomTel(orderbean.getHotelRoomTel());
		order.setHotelTel(orderbean.getHotelTel());

		MerchantOrderGoods mog = order.getGoodses().get(0);
		if (pickupBean != null) {
			mog.setNeedsPickup(true);
			MerchantOrderGoodsPickup pickup = merchantOrderGoodsPickupDao.loadByMogId(mog.getId());
			if (pickup != null) {
				pickup.setArrivedDate(pickupBean.getArrivedDate());
				pickup.setArrivedTime(pickupBean.getArrivedTime());
				pickup.setFlightNo(pickupBean.getFlightNo());
				merchantOrderGoodsPickupDao.update(pickup);
			} else {
				pickup = new MerchantOrderGoodsPickup();
				pickup.setMerchantOrderGoodsId(mog.getId());
				pickup.setMerchantOrderId(order.getId());
				pickup.setArrivedDate(pickupBean.getArrivedDate());
				pickup.setArrivedTime(pickupBean.getArrivedTime());
				pickup.setFlightNo(pickupBean.getFlightNo());
				merchantOrderGoodsPickupDao.create(pickup);
			}
		} else {
			mog.setNeedsPickup(false);
		}
		mog.setDeptDate(deptDate);
		merchantOrderGoodsDao.update(mog);
		merchantOrderDao.update(order);
		merchantOrderNotesDao.create(new MerchantOrderNotes(order, guestProfile.getUser(), "顾客更新了订单"));
		return order;
	}

	@Override
	public PaginationSupport getOrderesPage(OrderVo vo, int merchantId) {
		vo.merchantId = merchantId;
		return merchantOrderDao.getOrderesPage(vo);
	}

	@Override
	public PaginationSupport getOrderesPage(OrderVo vo) {
		return merchantOrderDao.getOrderesPage(vo);
	}

	@Override
	public int auditingOrderCount(User user) {
		return merchantOrderDao.auditingOrderCount(user);
	}

}
