package com.magic.thai.db.service;

import java.util.List;
import java.util.Map;

public interface ServiceHelper<T> {
	List<T> getAll(Class<T> clazz);

	long countAll(Map<Object, String> param);

	void create(T t);

	void delete(T t);

	T find(Class<T> clazz, int id);

	void update(T t);
}
