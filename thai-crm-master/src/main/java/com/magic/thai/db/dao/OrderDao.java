package com.magic.thai.db.dao;

import java.util.Date;

import com.magic.thai.db.domain.Order;
import com.magic.thai.util.PaginationSupport;

public interface OrderDao {

	public Order loadById(int id);

	public Integer create(Order entity);

	public void update(Order entity);

	public void delete(Order Order);

	public void delete(int OrderId);

	public PaginationSupport getOrderesPage(String orderNo, Date startDate, Date endDate, String dept, String arr, int status,
			int currPage, Integer merchantId);

}
