package com.magic.thai.db.service.impl;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magic.thai.db.dao.ChannelOrderDao;
import com.magic.thai.db.dao.ChannelOrderLogDao;
import com.magic.thai.db.dao.MerchantOrderDao;
import com.magic.thai.db.domain.ChannelOrder;
import com.magic.thai.db.domain.MerchantOrder;
import com.magic.thai.db.service.ChannelOrderService;
import com.magic.thai.db.vo.OrderVo;
import com.magic.thai.exception.OrderStatusException;
import com.magic.thai.security.UserProfile;
import com.magic.thai.util.PaginationSupport;

@Service("channelOrderService")
@Transactional
public class ChannelOrderServiceImpl extends ServiceHelperImpl<ChannelOrder> implements ChannelOrderService {

	@Autowired
	ChannelOrderDao channelOrderDao;
	@Autowired
	ChannelOrderLogDao channelOrderLogDao;
	@Autowired
	MerchantOrderDao merchantOrderDao;

	@Override
	public ChannelOrder load(int id) {
		return channelOrderDao.loadById(id);
	}

	@Override
	public ChannelOrder fetch(int id) {
		ChannelOrder order = channelOrderDao.loadById(id);
		Hibernate.initialize(order.getTravelers());
		order.setMerchantOrders(merchantOrderDao.fetchByChannelOrder(order.getId()));
		return order;
	}

	@Override
	@Transactional
	public void proc(int orderId, String reason, UserProfile userprofile) throws OrderStatusException {
		// ChannelOrder order = merchantOrderDao.loadById(orderId);
		// proc(order, reason, userprofile);
	}

	@Override
	public PaginationSupport getOrderesPage(OrderVo vo) {
		return channelOrderDao.getOrderesPage(vo);
	}

	@Override
	public void confirm(int channelId) {
		ChannelOrder channelOrder = channelOrderDao.fetch(channelId);
		boolean all = true;
		for (MerchantOrder merchantOrder : channelOrder.getMerchantOrders()) {
			if (!merchantOrder.isCompleted()) {
				all = false;
				break;
			}
		}
		if (all) {
			channelOrder.setCompleted();
			channelOrderDao.update(channelOrder);
		}
	}

}
