package com.magic.thai.db.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.GoodsDao;
import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.Merchant;
import com.magic.thai.util.PaginationSupport;

@Repository(value = "goodsDao")
public class GoodsDaoImpl extends HibernateCommonDAO<Goods> implements GoodsDao {

	@Autowired
	public GoodsDaoImpl(SessionFactory sessionFactory) {
		// super.initDao();
		setEntityClass(Goods.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public Goods loadById(int id) {
		return super.loadById(id);
	}

	@Override
	public Integer create(Goods entity) {
		return (Integer) super.create(entity);
	}

	@Override
	public void delete(int GoodsId) {
		super.getSession().createQuery("update Goods set status = " + Goods.Status.DELETED + " where id = " + GoodsId).executeUpdate();
	}

	@Override
	public PaginationSupport getGoodsesPage(String title, String dept, String arrived, int status, int currPage, Integer merchantId) {
		String hql = "from Goods where status != " + Merchant.Status.DELETED + " and readOnly = false";
		if (StringUtils.isNotBlank(title)) {
			hql += " and title like '%" + title + "%'";
		}
		if (StringUtils.isNotBlank(dept)) {
			hql += " and dept like '%" + dept + "%'";
		}
		if (StringUtils.isNotBlank(arrived)) {
			hql += " and arrived like '%" + arrived + "%'";
		}
		if (status >= 0) {
			hql += " and status = " + status;
		}
		if (merchantId != null) {
			hql += " and merchantId = " + merchantId;
		}
		return super.find(hql, currPage, 30);
	}

}
