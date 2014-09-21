package com.magic.thai.db.service.strategy;

import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.MerchantOrder;
import com.magic.thai.db.domain.ChannelOrderTraveler;

public interface GoodsPriceCalculator {

	public double totalPrice(Goods goods, MerchantOrder order);

	public double price(Goods goods, ChannelOrderTraveler traveler);

}
