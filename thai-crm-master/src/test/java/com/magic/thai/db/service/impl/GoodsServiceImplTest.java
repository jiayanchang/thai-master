package com.magic.thai.db.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.GoodsDetails;
import com.magic.thai.db.domain.GoodsPriceSegment;
import com.magic.thai.db.service.GoodsService;

public class GoodsServiceImplTest extends BaseTest {

	@Autowired
	GoodsService goodsService;

	@Test
	public void test() {
		// int id = createNewGoods();
		// Goods newGoods = goodsService.load(id);
		// System.out.println(newGoods);
		// fail("Not yet implemented");

		List<Goods> goodses = goodsService.fetchList(null);
		for (Goods goods : goodses) {
			System.out.println(goods);
			for (GoodsPriceSegment s : goods.getSegments()) {
				System.out.println(s);
			}
		}
	}

	private int createNewGoods() {
		Goods goods = new Goods();
		goods.setArrived("PEK");
		goods.setDept("CAN");

		GoodsDetails details = new GoodsDetails();
		goods.setDetails(details);

		goods.setGoodsCount(10);
		// goods.setMerchantId(merchantId);
		// goods.setMerchantName(merchantName);

		GoodsPriceSegment segment = new GoodsPriceSegment();
		segment.setAuditPrice(10);
		segment.setChildPrice(5);
		segment.setEndDate(new Date());
		segment.setGoods(goods);
		segment.setStartDate(new Date());

		GoodsPriceSegment segmentb = new GoodsPriceSegment();
		segmentb.setAuditPrice(1201);
		segmentb.setChildPrice(512);
		segmentb.setEndDate(new Date());
		segmentb.setGoods(goods);
		segmentb.setStartDate(new Date());

		List<GoodsPriceSegment> segments = new ArrayList<GoodsPriceSegment>();
		segments.add(segmentb);
		segments.add(segment);
		goods.setSegments(segments);

		goods.setSummary("skdfjsldfkjsldfjls");
		goods.setTitle("测试类3");
		goods.setTravelDays(3);

		int id = goodsService.create(goods, userprofile);
		return id;
	}

}
