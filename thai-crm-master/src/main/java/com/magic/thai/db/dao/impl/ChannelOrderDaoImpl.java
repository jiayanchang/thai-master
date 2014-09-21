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

import com.magic.thai.db.dao.ChannelOrderDao;
import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.dao.MerchantOrderDao;
import com.magic.thai.db.domain.ChannelOrder;
import com.magic.thai.db.vo.OrderVo;
import com.magic.thai.exception.ThaiException;
import com.magic.thai.exception.webservice.ParameterException;
import com.magic.thai.util.Asserts;
import com.magic.thai.util.PaginationSupport;

@Repository(value = "channelOrderDao")
public class ChannelOrderDaoImpl extends HibernateCommonDAO<ChannelOrder> implements ChannelOrderDao {

	@Autowired
	MerchantOrderDao merchantOrderDao;

	@Autowired
	public ChannelOrderDaoImpl(SessionFactory sessionFactory) {
		// super.initDao();
		setEntityClass(ChannelOrder.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public ChannelOrder loadById(int id) {
		return super.loadById(id);
	}

	@Override
	public ChannelOrder fetch(int id) {
		ChannelOrder order = loadById(id);
		Hibernate.initialize(order.getTravelers());
		order.setMerchantOrders(merchantOrderDao.fetchByChannelOrder(order.getId()));
		return order;
	}

	@Override
	public ChannelOrder loadByNo(String orderNo) {
		ArrayList<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("channelOrderNo", orderNo));
		// criterions.add(Restrictions.ne("status", Channel.Status.DELETED));
		List<ChannelOrder> orders = super.find(criterions);
		return orders == null || orders.size() == 0 ? null : orders.get(0);
	}

	@Override
	public ChannelOrder fetchByNo(String orderNo) throws ThaiException {
		ChannelOrder order = loadByNo(orderNo);
		Asserts.notNull(order, new ParameterException("单号有误：" + orderNo));
		Hibernate.initialize(order.getTravelers());
		order.setMerchantOrders(merchantOrderDao.fetchByChannelOrder(order.getId()));
		return order;
	}

	@Override
	public Integer create(ChannelOrder entity) {
		return (Integer) super.create(entity);
	}

	@Override
	public PaginationSupport getOrderesPage(OrderVo vo) {
		ArrayList<Criterion> criterions = new ArrayList<Criterion>();
		if (StringUtils.isNotBlank(vo.orderNo)) {
			criterions.add(Restrictions.eq("channelOrderNo", vo.orderNo));
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
		if (vo.startDate != null) {
			criterions.add(Restrictions.ge("createdDate", vo.startDate));
		}
		if (vo.endDate != null) {
			criterions.add(Restrictions.le("createdDate", vo.endDate));
		}

		criterions.add(Restrictions.eq("type", ChannelOrder.OrderType.CHANNEL_ORDER));
		return super.find(criterions, vo.page, 30);
	}
}
