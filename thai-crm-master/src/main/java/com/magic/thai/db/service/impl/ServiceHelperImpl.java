package com.magic.thai.db.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.magic.thai.db.dao.GenericDao;
import com.magic.thai.db.service.ServiceHelper;

@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
@Service("serviceHelper")
public abstract class ServiceHelperImpl<T> implements ServiceHelper<T> {

	@Autowired
	protected GenericDao<T> genericDao;

	@Transactional(readOnly = true)
	public List<T> getAll(Class<T> clazz) {
		// TODO Auto-generated method stub
		return genericDao.getAll(clazz);
	}

	public long countAll(Map<Object, String> param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional
	public void create(T t) {
		// TODO Auto-generated method stub
		genericDao.create(t);
	}

	@Transactional
	public void delete(T t) {
		// TODO Auto-generated method stub
		genericDao.delete(t);
	}

	@Transactional(readOnly = true)
	public T find(Class<T> clazz, int id) {
		return genericDao.find(clazz, id);
	}

	@Transactional
	public void update(T t) {
		// TODO Auto-generated method stub

		genericDao.update(t);

	}

}
