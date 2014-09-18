package com.magic.thai.db.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.dao.SnapshotGoodsDetailsDao;
import com.magic.thai.db.domain.SnapshotGoodsDetails;

@Repository(value = "snapshotGoodsDetailDao")
public class SnapshotGoodsDetailsDaoImpl extends HibernateCommonDAO<SnapshotGoodsDetails> implements SnapshotGoodsDetailsDao {

	@Autowired
	public SnapshotGoodsDetailsDaoImpl(SessionFactory sessionFactory) {
		setEntityClass(SnapshotGoodsDetails.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public SnapshotGoodsDetails loadById(int id) {
		return super.loadById(id);
	}
}
