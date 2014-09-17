package com.magic.thai.db.service.strategy;

import org.springframework.stereotype.Repository;

import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.GoodsPriceSegment;
import com.magic.thai.db.domain.Order;
import com.magic.thai.db.domain.OrderTraveler;

@Repository("goodsPriceCalculator")
public class CommonGoodsPriceCalculator implements GoodsPriceCalculator {

	public double totalPrice(Goods goods, Order order) {
		GoodsPriceSegment segment = null;
		for (GoodsPriceSegment s : goods.getSegments()) {
			if (s.exsits(order.getDeptDate())) {
				segment = s;
				break;
			}
		}
		if (segment == null) {
			segment = goods.getLastSegment();
		}

		double total = 0d;
		for (OrderTraveler orderTraveler : order.getTravelers()) {
			if (orderTraveler.isChild()) {
				total += segment.getChildPrice();
			} else {
				total += segment.getAuditPrice();
			}
		}
		return total;
	}

	public double price(Goods goods, OrderTraveler traveler) {
		GoodsPriceSegment segment = null;
		for (GoodsPriceSegment s : goods.getSegments()) {
			if (s.exsits(traveler.getOrder().getDeptDate())) {
				segment = s;
				break;
			}
		}
		if (segment == null) {
			segment = goods.getLastSegment();
		}

		return traveler.isChild() ? segment.getChildPrice() : segment.getAuditPrice();
	}

}
