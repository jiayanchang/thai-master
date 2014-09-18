package com.magic.thai.db.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.dao.SnapshotGoodsPriceSegmentDao;
import com.magic.thai.db.domain.SnapshotGoodsPriceSegment;

@Repository(value = "snapshotGoodsPriceSegmentDao")
public class SnapshotGoodsPriceSegmentDaoImpl extends HibernateCommonDAO<SnapshotGoodsPriceSegment> implements SnapshotGoodsPriceSegmentDao {

	@Autowired
	public SnapshotGoodsPriceSegmentDaoImpl(SessionFactory sessionFactory) {
		setEntityClass(SnapshotGoodsPriceSegment.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public Integer create(SnapshotGoodsPriceSegment entity) {
		return (Integer) super.create(entity);
	}
}
