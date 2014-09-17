package com.magic.thai.db.dao;

import com.magic.thai.db.domain.Order;
import com.magic.thai.db.vo.OrderVo;
import com.magic.thai.util.PaginationSupport;

public interface OrderDao {

	public Order loadById(int id);

	public Integer create(Order entity);

	public void update(Order entity);

	public PaginationSupport getOrderesPage(OrderVo vo);

}
