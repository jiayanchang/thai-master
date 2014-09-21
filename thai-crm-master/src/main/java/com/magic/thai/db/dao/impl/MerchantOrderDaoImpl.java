package com.magic.thai.db.dao.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.dao.MerchantOrderDao;
import com.magic.thai.db.domain.ChannelOrder;
import com.magic.thai.db.domain.Merchant;
import com.magic.thai.db.domain.MerchantOrder;
import com.magic.thai.db.domain.User;
import com.magic.thai.db.vo.OrderVo;
import com.magic.thai.util.PaginationSupport;

@Repository(value = "merchantOrderDao")
public class MerchantOrderDaoImpl extends HibernateCommonDAO<MerchantOrder> implements MerchantOrderDao {

	@Autowired
	public MerchantOrderDaoImpl(SessionFactory sessionFactory) {
		// super.initDao();
		setEntityClass(MerchantOrder.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public MerchantOrder loadById(int id) {
		return super.loadById(id);
	}

	@Override
	public MerchantOrder fetch(int id) {
		MerchantOrder order = loadById(id);
		return order;
	}

	@Override
	public MerchantOrder loadByNo(String orderNo) {
		ArrayList<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("orderNo", orderNo));
		criterions.add(Restrictions.ne("status", Merchant.Status.DELETED));
		List<MerchantOrder> orders = super.find(criterions);
		return orders == null || orders.size() == 0 ? null : orders.get(0);
	}

	@Override
	public MerchantOrder fetchByNo(String orderNo) {
		MerchantOrder order = loadByNo(orderNo);
		return order;
	}

	@Override
	public Integer create(MerchantOrder entity) {
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
		if (vo.orderType != null) {
			criterions.add(Restrictions.eq("orderType", vo.orderType));
		}
		if (StringUtils.isNotBlank(vo.channelName)) {
			criterions.add(Restrictions.like("channelName", vo.channelName, MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(vo.merchantName)) {
			criterions.add(Restrictions.like("merchantName", vo.merchantName, MatchMode.ANYWHERE));
		}
		try {
			if (StringUtils.isNotBlank(vo.startDate)) {
				criterions.add(Restrictions.ge("createdDate", DateUtils.parseDate(vo.startDate, new String[] { "yyyy/MM/dd" })));
			}
			if (StringUtils.isNotBlank(vo.endDate)) {
				criterions.add(Restrictions.le("createdDate", DateUtils.parseDate(vo.endDate, new String[] { "yyyy/MM/dd" })));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		criterions.add(Restrictions.ne("status", Merchant.Status.DELETED));
		return super.find(criterions, vo.page, 30);
	}

	@Override
	public int auditingOrderCount(User user) {
		String hql = "select count(*) from MerchantOrder where status = " + MerchantOrder.Status.NEW;
		if (!user.isPlatformUser()) {
			hql += " and merchantId = " + user.getMerchantId();
		} else {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MINUTE, -15);
			hql += " and orderType = " + ChannelOrder.OrderType.CHANNEL_ORDER + " and createdDate < '"
					+ DateFormatUtils.format(c, "yyyy-MM-dd HH:mm:ss") + "'";
		}

		return ((Long) super.getSession().createQuery(hql).uniqueResult()).intValue();
	}

	@Override
	public List<MerchantOrder> fetchByChannelOrder(int channelOrderId) {
		String hql = "from MerchantOrder where channelOrderId = " + channelOrderId;
		List<MerchantOrder> orders = super.find(hql);
		for (MerchantOrder merchantOrder : orders) {
			Hibernate.initialize(merchantOrder.getGoodses());
		}
		return orders;
	}

}
