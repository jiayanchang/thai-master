package com.magic.thai.db.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.GoodsLogDao;
import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.GoodsLog;

@Repository(value = "goodsLogDao")
public class GoodsLogDaoImpl extends HibernateCommonDAO<GoodsLog> implements GoodsLogDao {

	@Override
	public Integer create(GoodsLog entity) {
		return (Integer) super.create(entity);
	}

	@Autowired
	public GoodsLogDaoImpl(SessionFactory sessionFactory) {
		// super.initDao();
		setEntityClass(GoodsLog.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public List<GoodsLog> getLogs(int goodsRootId) {
		return super.find("from GoodsLog where goodsId = " + goodsRootId);
	}

	@Override
	public List<GoodsLog> getLogs(Goods goods) {
		return getLogs(goods.getId());
	}

}
