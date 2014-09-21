package com.magic.thai.db.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.dao.MerchantOrderGoodsDao;
import com.magic.thai.db.domain.MerchantOrderGoods;

@Repository(value = "merchantOrderGoodsDao")
public class MerchantOrderGoodsDaoImpl extends HibernateCommonDAO<MerchantOrderGoods> implements MerchantOrderGoodsDao {

	@Autowired
	public MerchantOrderGoodsDaoImpl(SessionFactory sessionFactory) {
		// super.initDao();
		setEntityClass(MerchantOrderGoods.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public MerchantOrderGoods loadById(int id) {
		return super.loadById(id);
	}

	@Override
	public MerchantOrderGoods fetch(int id) {
		MerchantOrderGoods goods = loadById(id);
		return goods;
	}

	@Override
	public Integer create(MerchantOrderGoods entity) {
		return (Integer) super.create(entity);
	}

}
