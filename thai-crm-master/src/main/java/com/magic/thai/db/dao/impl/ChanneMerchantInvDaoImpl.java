package com.magic.thai.db.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.ChannelMerchantInvDao;
import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.domain.ChannelMerchantInv;

@Repository(value = "channelMerchantInvDao")
public class ChanneMerchantInvDaoImpl extends HibernateCommonDAO<ChannelMerchantInv> implements ChannelMerchantInvDao {

	@Autowired
	public ChanneMerchantInvDaoImpl(SessionFactory sessionFactory) {
		// super.initDao();
		setEntityClass(ChannelMerchantInv.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public ChannelMerchantInv loadById(int id) {
		return super.loadById(id);
	}

	@Override
	public Integer create(ChannelMerchantInv entity) {
		return (Integer) super.create(entity);
	}

	@Override
	public List<ChannelMerchantInv> getInvs(int id) {
		return super.find("from ChannelMerchantInv where channelId = " + id);
	}

}
