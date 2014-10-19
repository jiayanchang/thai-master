package com.magic.thai.db.service.impl;

import com.magic.thai.db.domain.Channel;
import com.magic.thai.db.domain.ChannelGoodsInv;
import com.magic.thai.db.domain.ChannelMerchantInv;
import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.GoodsPriceSegment;
import com.magic.thai.exception.ThaiException;
import com.magic.thai.exception.webservice.ParameterException;
import com.magic.thai.util.Asserts;
import com.magic.thai.util.DoubleUtils;
import com.magic.thai.web.ws.vo.BuyGoodsVo;

public class CreateOrderValidator {

	public static double validate(Channel channel, Goods goods, BuyGoodsVo buyGoodsVo) throws ThaiException {
		double price = getPirce(goods, buyGoodsVo);

		ChannelGoodsInv goodsInv = channel.getGoodsInv(goods.getId());
		if (goodsInv != null) {
			price = DoubleUtils.add(price, goodsInv.getProfitPrice());
			Asserts.isTrue(price == buyGoodsVo.getPrice(), new ParameterException("验证价格不通过"));
		} else {
			ChannelMerchantInv merchantInv = channel.getMerchantInv(goods.getMerchantId());
			if (merchantInv.getProfitPrice() > 0) {
				price = DoubleUtils.add(price, merchantInv.getProfitPrice());
				Asserts.isTrue(price == buyGoodsVo.getPrice(), new ParameterException("验证价格不通过"));
			} else {
				price = DoubleUtils.add(price, price * merchantInv.getProfitRate());
				Asserts.isTrue(price == buyGoodsVo.getPrice(), new ParameterException("验证价格不通过"));
			}
		}
		return price;

	}

	public static double getPirce(Goods goods, BuyGoodsVo buyGoodsVo) {
		GoodsPriceSegment segment = goods.getSegment(buyGoodsVo.deptDateObj);
		double price = 0d;
		if (segment == null) {
			price = goods.getBasePrice();
		} else {
			price = buyGoodsVo.isChild() ? segment.getChildPrice() : segment.getAuditPrice();
		}
		return price;
	}
}
