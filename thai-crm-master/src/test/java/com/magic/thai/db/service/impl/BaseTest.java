package com.magic.thai.db.service.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.magic.thai.db.domain.Merchant;
import com.magic.thai.db.domain.User;
import com.magic.thai.security.UserProfile;

//@Ignore
@ContextConfiguration(locations = "/spring-ctx.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {

	static UserProfile userprofile;
	static User user = new User();
	static Merchant merchant = new Merchant();
	static {
		user.setId(1);
		user.setCodeName("admin");
		user.setMerchantId(1);
		userprofile = new UserProfile(user, merchant);
	}

	@Autowired
	SessionFactory sessionFactory;

	protected void flush() {
		sessionFactory.getCurrentSession().flush();
	}

	protected Session getSessionFactory() {
		return sessionFactory.getCurrentSession();

	}
}
