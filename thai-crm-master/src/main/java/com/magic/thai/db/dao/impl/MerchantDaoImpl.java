package com.magic.thai.db.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.dao.MerchantDao;
import com.magic.thai.db.dao.MerchantDetailsDao;
import com.magic.thai.db.domain.Merchant;
import com.magic.thai.db.vo.MerchantVo;
import com.magic.thai.util.PaginationSupport;

@Repository(value = "merchantDao")
public class MerchantDaoImpl extends HibernateCommonDAO<Merchant> implements MerchantDao {

	@Autowired
	MerchantDetailsDao merchantDetailsDao;

	@Autowired
	public MerchantDaoImpl(SessionFactory sessionFactory) {
		// super.initDao();
		setEntityClass(Merchant.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public List<Merchant> list(MerchantVo vo) {
		ArrayList<Criterion> criterions = new ArrayList<Criterion>();
		if (StringUtils.isNotBlank(vo.nameKeyword)) {
			criterions.add(Restrictions.like("name", vo.nameKeyword, MatchMode.ANYWHERE));
		}
		if (!vo.containsPf4list) {
			criterions.add(Restrictions.ne("type", Merchant.Type.PLATFORM));
		}
		if (vo.statuses != null && vo.statuses.length > 0) {
			criterions.add(Restrictions.in("status", vo.statuses));
		}
		criterions.add(Restrictions.ne("status", Merchant.Status.DELETED));
		return super.find(criterions, vo.limitF4list);
	}

	@Override
	public Merchant loadById(int id) {
		return super.loadById(id);
	}

	@Override
	public Merchant fetch(int id) {
		Merchant merchant = loadById(id);
		merchant.setDetails(merchantDetailsDao.loadById(id));
		return merchant;
	}

	@Override
	public Integer create(Merchant entity) {
		return (Integer) super.create(entity);
	}

	@Override
	public void enable(Merchant merchant) {
		enable(merchant.getId());
	}

	@Override
	public void enable(int merchantId) {
		super.getSession().createQuery("update Merchant set status = " + Merchant.Status.ENABLED + " where id = " + merchantId)
				.executeUpdate();
	}

	@Override
	public void disable(Merchant merchant) {
		disable(merchant.getId());
	}

	@Override
	public void disable(int merchantId) {
		super.getSession().createQuery("update Merchant set status = " + Merchant.Status.DISABLED + " where id = " + merchantId)
				.executeUpdate();
	}

	@Override
	public void delete(int merchantId) {
		super.getSession().createQuery("update Merchant set status = " + Merchant.Status.DELETED + " where id = " + merchantId)
				.executeUpdate();
	}

	@Override
	public PaginationSupport getMerchantsPage(String name, int status, int currPage) {
		String hql = "from Merchant where status != " + Merchant.Status.DELETED;
		if (StringUtils.isNotBlank(name)) {
			hql += " and name like '%" + name + "%'";
		}
		if (status >= 0) {
			hql += " and status = " + status;
		}
		return super.find(hql, currPage, 30);
	}

}
