package com.magic.thai.db.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.dao.MerchantOrderNotesDao;
import com.magic.thai.db.domain.MerchantOrder;
import com.magic.thai.db.domain.MerchantOrderNotes;

@Repository(value = "orderLogDao")
public class MerchantOrderNotesDaoImpl extends HibernateCommonDAO<MerchantOrderNotes> implements MerchantOrderNotesDao {

	@Override
	public Integer create(MerchantOrderNotes entity) {
		return (Integer) super.create(entity);
	}

	@Autowired
	public MerchantOrderNotesDaoImpl(SessionFactory sessionFactory) {
		// super.initDao();
		setEntityClass(MerchantOrderNotes.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public List<MerchantOrderNotes> getLogs(int orderId) {
		return super.find("from MerchantOrderNotes where orderId = " + orderId);
	}

	@Override
	public List<MerchantOrderNotes> getLogs(MerchantOrder order) {
		return getLogs(order.getId());
	}

}
