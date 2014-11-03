package com.magic.thai.db.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.dao.MerchantOrderGoodsPickupDao;
import com.magic.thai.db.domain.MerchantOrderGoodsPickup;

@Repository(value = "merchantOrderGoodsPickupDao")
public class MerchantOrderGoodsPickupDaoImpl extends HibernateCommonDAO<MerchantOrderGoodsPickup> implements MerchantOrderGoodsPickupDao {

	@Autowired
	public MerchantOrderGoodsPickupDaoImpl(SessionFactory sessionFactory) {
		setEntityClass(MerchantOrderGoodsPickup.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public MerchantOrderGoodsPickup loadById(int id) {
		return super.loadById(id);
	}

	@Override
	public MerchantOrderGoodsPickup loadByMogId(int mogid) {
		List<MerchantOrderGoodsPickup> pickups = super.find("from MerchantOrderGoodsPickup where merchantOrderGoodsId = " + mogid);
		return pickups == null || pickups.size() == 0 ? null : pickups.get(0);
	}

	public Integer create(MerchantOrderGoodsPickup entity) {
		return (Integer) super.create(entity);
	}

}
