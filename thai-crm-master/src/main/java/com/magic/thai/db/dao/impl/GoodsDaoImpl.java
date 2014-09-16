package com.magic.thai.db.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.GoodsDao;
import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.domain.Channel;
import com.magic.thai.db.domain.ChannelGoodsInv;
import com.magic.thai.db.domain.ChannelMerchantInv;
import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.Merchant;
import com.magic.thai.db.domain.User;
import com.magic.thai.db.vo.GoodsVo;
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
	public List<Goods> list(GoodsVo vo) {
		ArrayList<Criterion> criterions = new ArrayList<Criterion>();
		if (StringUtils.isNotBlank(vo.titleKeyword)) {
			criterions.add(Restrictions.like("title", vo.titleKeyword, MatchMode.ANYWHERE));
		}
		if (vo.statuses != null && vo.statuses.length > 0) {
			criterions.add(Restrictions.in("status", vo.statuses));
		}
		criterions.add(Restrictions.eq("readOnly", false));
		criterions.add(Restrictions.ne("status", Merchant.Status.DELETED));
		return super.find(criterions, vo.limitF4list);
	}

	@Override
	public PaginationSupport getGoodsesPage(String title, String dept, String arrived, Integer[] statuses, int currPage, Integer merchantId) {
		ArrayList<Criterion> criterions = new ArrayList<Criterion>();
		if (StringUtils.isNotBlank(title)) {
			criterions.add(Restrictions.like("title", title, MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(dept)) {
			criterions.add(Restrictions.eq("dept", dept));
		}
		if (StringUtils.isNotBlank(arrived)) {
			criterions.add(Restrictions.eq("arrived", dept));
		}
		if (statuses != null && statuses.length > 0) {
			criterions.add(Restrictions.in("status", statuses));
		}
		if (merchantId != null) {
			criterions.add(Restrictions.eq("merchantId", merchantId));
		}
		criterions.add(Restrictions.eq("readOnly", false));
		criterions.add(Restrictions.ne("status", Merchant.Status.DELETED));
		return super.find(criterions, currPage, 30);
	}

	@Override
	public int getAuditingGoodsCount(User user) {
		String hql = "";
		hql = "select count(1) from Goods where status = " + Goods.Status.AUDITING;
		Query query = super.getSession().createQuery(hql);
		Object o = query.uniqueResult();
		return (Integer) o;
	}

	@Override
	public List<Goods> fetchList(GoodsVo vo, Channel channel) {
		String hql = "SELECT DISTINCT g FROM Goods g LEFT JOIN FETCH g.segments WHERE 1=1";

		if (channel.getGoodsInvs().size() > 0) {
			String ids = "";
			for (ChannelGoodsInv inv : channel.getGoodsInvs()) {
				ids += "," + inv.getGoodsId();
			}
			hql += " AND g.rootId in ( " + ids.substring(1) + " )";
		}

		if (channel.getMerchantInvs().size() > 0) {
			String ids = "";
			for (ChannelMerchantInv inv : channel.getMerchantInvs()) {
				ids += "," + inv.getMerchantId();
			}
			hql += " AND g.merchantId in ( " + ids.substring(1) + " )";
		}
		return super.find(hql);
	}

}
