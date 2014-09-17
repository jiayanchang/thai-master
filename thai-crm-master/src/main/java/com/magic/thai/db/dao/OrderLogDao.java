package com.magic.thai.db.dao;

import java.util.List;

import com.magic.thai.db.domain.Order;
import com.magic.thai.db.domain.OrderLog;

public interface OrderLogDao {

	public Integer create(OrderLog entity);

	public List<OrderLog> getLogs(int orderRootId);

	public List<OrderLog> getLogs(Order order);
}
