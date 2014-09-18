package com.magic.thai.db.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.dao.SnapshotGoodsDao;
import com.magic.thai.db.domain.SnapshotGoods;

@Repository(value = "snapshotGoodsDao")
public class SnapshotGoodsDaoImpl extends HibernateCommonDAO<SnapshotGoods> implements SnapshotGoodsDao {

	@Autowired
	public SnapshotGoodsDaoImpl(SessionFactory sessionFactory) {
		setEntityClass(SnapshotGoods.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public SnapshotGoods loadById(int id) {
		return super.loadById(id);
	}

	@Override
	public Integer create(SnapshotGoods entity) {
		return (Integer) super.create(entity);
	}

}
