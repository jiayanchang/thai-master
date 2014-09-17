package com.magic.thai.db.dao;

import com.magic.thai.db.domain.OrderTraveler;

public interface OrderTravelerDao {

	public OrderTraveler loadById(int id);

	public void update(OrderTraveler entity);

	public Integer create(final OrderTraveler entity);
}
