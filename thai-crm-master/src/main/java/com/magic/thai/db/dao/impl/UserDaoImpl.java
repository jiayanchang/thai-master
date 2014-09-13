package com.magic.thai.db.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magic.thai.db.dao.HibernateCommonDAO;
import com.magic.thai.db.dao.UserDao;
import com.magic.thai.db.domain.User;
import com.magic.thai.util.PaginationSupport;

@Repository(value = "userDao")
public class UserDaoImpl extends HibernateCommonDAO<User> implements UserDao {

	@Autowired
	public UserDaoImpl(SessionFactory sessionFactory) {
		// super.initDao();
		setEntityClass(User.class);
		setSessionFactory(sessionFactory);
	}

	@Override
	public List<User> list() {
		return super.find("from User where type != ?", User.Type.PLATFORM);
	}

	@Override
	public User loadById(int id) {
		return super.loadById(id);
	}

	@Override
	public Integer create(User entity) {
		return (Integer) super.create(entity);
	}

	@Override
	public void enable(User User) {
		enable(User.getId());
	}

	@Override
	public void enable(int UserId) {
		super.getSession().createQuery("update User set status = " + User.Status.ENABLED + " where id = " + UserId).executeUpdate();
	}

	@Override
	public void disable(User User) {
		disable(User.getId());
	}

	@Override
	public void disable(int UserId) {
		super.getSession().createQuery("update User set status = " + User.Status.DISABLED + " where id = " + UserId).executeUpdate();
	}

	@Override
	public void delete(int UserId) {
		super.getSession().createQuery("update User set status = " + User.Status.DELETED + " where id = " + UserId).executeUpdate();
	}

	@Override
	public PaginationSupport getUsers(String name, int status, int currPage) {
		return super.find("from User where name like '%" + name + "%' and status = " + status + " and status != " + User.Status.DELETED,
				currPage, 30);
	}

	@Override
	public User getUserByLoginName(String loginName) {
		List<User> users = super.find("from User where loginName = '" + loginName + "'");
		return users == null || users.size() == 0 ? null : users.get(0);
	}

}
