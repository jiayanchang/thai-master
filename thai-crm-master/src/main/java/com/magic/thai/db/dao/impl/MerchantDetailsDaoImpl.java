package com.magic.thai.db.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.dao.MerchantDetailsDao;
import com.magic.thai.db.domain.MerchantDetails;

@Repository(value="merchantDetailDao")
public class MerchantDetailsDaoImpl extends HibernateCommonDAO<MerchantDetails> implements MerchantDetailsDao {

	@Autowired
	public MerchantDetailsDaoImpl(SessionFactory sessionFactory) {
//		super.initDao();
		setEntityClass(MerchantDetails.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public MerchantDetails loadById(int id) {
		return super.loadById(id);
	}

}
