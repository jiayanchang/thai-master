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
import com.magic.thai.db.dao.HotelDao;
import com.magic.thai.db.domain.Hotel;
import com.magic.thai.db.domain.Merchant;
import com.magic.thai.db.vo.HotelVo;
import com.magic.thai.util.PaginationSupport;

@Repository(value = "hotelDao")
public class HotelDaoImpl extends HibernateCommonDAO<Hotel> implements HotelDao {

	@Autowired
	public HotelDaoImpl(SessionFactory sessionFactory) {
		// super.initDao();
		setEntityClass(Hotel.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public Hotel loadById(int id) {
		return super.loadById(id);
	}

	@Override
	public Integer create(Hotel entity) {
		return (Integer) super.create(entity);
	}

	@Override
	public void delete(int HotelId) {
		// super.getSession().createQuery("update Hotel set status = " +
		// Hotel.Status.DELETED + " where id = " + HotelId).executeUpdate();
	}

	@Override
	public PaginationSupport getHotelesPage(String title, String dept, String arrived, int status, int currPage, Integer merchantId) {
		String hql = "from Hotel where status != " + Merchant.Status.DELETED + " and readOnly = false";
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

	@Override
	public List<Hotel> list(HotelVo vo) {
		ArrayList<Criterion> criterions = new ArrayList<Criterion>();
		if (StringUtils.isNotBlank(vo.nameKeyword)) {
			criterions.add(Restrictions.like("name", vo.nameKeyword, MatchMode.ANYWHERE));
		}
		// if (vo.statuses != null && vo.statuses.length > 0) {
		// criterions.add(Restrictions.in("status", vo.statuses));
		// }
		// criterions.add(Restrictions.eq("readOnly", false));
		// criterions.add(Restrictions.ne("status", Merchant.Status.DELETED));
		return super.find(criterions, vo.limitF4list);
	}

}
