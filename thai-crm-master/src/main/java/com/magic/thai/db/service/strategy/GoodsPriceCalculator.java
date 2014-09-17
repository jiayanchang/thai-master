package com.magic.thai.db.service.strategy;

import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.Order;
import com.magic.thai.db.domain.OrderTraveler;

public interface GoodsPriceCalculator {

	public double totalPrice(Goods goods, Order order);

	public double price(Goods goods, OrderTraveler traveler);

}
