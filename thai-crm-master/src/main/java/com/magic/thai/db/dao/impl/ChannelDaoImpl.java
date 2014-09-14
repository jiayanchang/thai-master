package com.magic.thai.db.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.ChannelDao;
import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.domain.Channel;
import com.magic.thai.util.PaginationSupport;

@Repository(value = "channelDao")
public class ChannelDaoImpl extends HibernateCommonDAO<Channel> implements ChannelDao {

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

}
