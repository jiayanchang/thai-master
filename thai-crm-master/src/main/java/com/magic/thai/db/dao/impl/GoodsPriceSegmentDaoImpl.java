package com.magic.thai.db.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.GoodsPriceSegmentDao;
import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.GoodsPriceSegment;

@Repository(value = "goodsPriceSegmentDao")
public class GoodsPriceSegmentDaoImpl extends HibernateCommonDAO<GoodsPriceSegment> implements GoodsPriceSegmentDao {

	@Autowired
	public GoodsPriceSegmentDaoImpl(SessionFactory sessionFactory) {
		// super.initDao();
		setEntityClass(GoodsPriceSegment.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public Integer create(GoodsPriceSegment segment) {
		return (Integer) super.create(segment);
	}

	@Override
	public GoodsPriceSegment loadById(int id) {
		return super.loadById(id);
	}

	@Override
	public List<GoodsPriceSegment> getSegments(Goods goods) {
		return super.find("from GoodsPriceSegment where goodsId = " + goods.getId());
	}

}
