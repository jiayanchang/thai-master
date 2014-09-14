package com.magic.thai.db.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.ChannelGoodsInvDao;
import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.domain.ChannelGoodsInv;

@Repository(value = "channelGoodsInvDao")
public class ChannelGoodsInvDaoImpl extends HibernateCommonDAO<ChannelGoodsInv> implements ChannelGoodsInvDao {

	@Autowired
	public ChannelGoodsInvDaoImpl(SessionFactory sessionFactory) {
		// super.initDao();
		setEntityClass(ChannelGoodsInv.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public ChannelGoodsInv loadById(int id) {
		return super.loadById(id);
	}

	@Override
	public Integer create(ChannelGoodsInv entity) {
		return (Integer) super.create(entity);
	}

	@Override
	public List<ChannelGoodsInv> getInvs(int id) {
		return super.find("from ChannelGoodsInv where channelId = " + id);
	}

}
