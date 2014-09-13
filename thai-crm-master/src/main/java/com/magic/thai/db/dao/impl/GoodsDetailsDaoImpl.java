package com.magic.thai.db.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.GoodsDetailsDao;
import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.domain.GoodsDetails;

@Repository(value = "goodsDetailDao")
public class GoodsDetailsDaoImpl extends HibernateCommonDAO<GoodsDetails> implements GoodsDetailsDao {

	@Autowired
	public GoodsDetailsDaoImpl(SessionFactory sessionFactory) {
		// super.initDao();
		setEntityClass(GoodsDetails.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public GoodsDetails loadById(int id) {
		return super.loadById(id);
	}

}
