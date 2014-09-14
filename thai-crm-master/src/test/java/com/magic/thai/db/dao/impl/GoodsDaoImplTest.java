package com.magic.thai.db.dao.impl;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.magic.thai.db.dao.GoodsDao;
import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.service.impl.BaseTest;
import com.magic.thai.db.vo.GoodsVo;

public class GoodsDaoImplTest extends BaseTest {

	@Autowired
	GoodsDao goodsDao;

	@Test
	public void test() {
		GoodsVo vo = new GoodsVo();
		List<Goods> goodses = goodsDao.list(vo);
		for (Goods goods : goodses) {
			System.out.println(goods);
		}
	}

}
