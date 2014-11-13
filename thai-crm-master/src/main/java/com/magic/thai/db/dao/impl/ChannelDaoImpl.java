package com.magic.thai.db.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.ChannelDao;
import com.magic.thai.db.dao.ChannelGoodsInvDao;
import com.magic.thai.db.dao.ChannelMerchantInvDao;
import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.domain.Channel;
import com.magic.thai.util.PaginationSupport;

@Repository(value = "channelDao")
public class ChannelDaoImpl extends HibernateCommonDAO<Channel> implements ChannelDao {

	@Autowired
	ChannelGoodsInvDao channelGoodsInvDao;
	@Autowired
	ChannelMerchantInvDao channelMerchantInvDao;

	@Autowired
	public ChannelDaoImpl(SessionFactory sessionFactory) {
		// super.initDao();
		setEntityClass(Channel.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public Channel loadById(int id) {
		return super.loadById(id);
	}

	@Override
	public Integer create(Channel entity) {
		return (Integer) super.create(entity);
	}

	@Override
	public void delete(int ChannelId) {
		super.getSession().createQuery("update Channel set status = " + Channel.Status.DELETED + " where id = " + ChannelId)
				.executeUpdate();
	}

	@Override
	public PaginationSupport getChannelesPage(int currPage) {
		String hql = "from Channel where status != " + Channel.Status.DELETED;
		return super.find(hql, currPage, 30);
	}

	@Override
	public Channel loadByToken(String token) {
		String hql = "from Channel where token = '" + token + "' and status != " + Channel.Status.DELETED;
		List<Channel> channels = super.find(hql);
		return channels != null && channels.size() > 0 ? channels.get(0) : null;
	}

	@Override
	public Channel fetchByToken(String token) {
		String hql = "from Channel where token = '" + token + "' and status != " + Channel.Status.DELETED;
		List<Channel> channels = super.find(hql);
		Channel channel = null;
		if (channels != null && channels.size() > 0) {
			channel = channels.get(0);
			channel.setMerchantInvs(channelMerchantInvDao.getInvs(channel.getId()));
			channel.setGoodsInvs(channelGoodsInvDao.getInvs(channel.getId()));
		}
		return channel;
	}

	@Override
	public Channel loadByMerchantId(int merchantId) {
		String hql = "from Channel where merchantId = " + merchantId;
		List<Channel> channels = super.find(hql);
		return channels == null || channels.size() == 0 ? null : channels.get(0);
	}

}
