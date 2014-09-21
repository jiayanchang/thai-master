package com.magic.thai.db.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.magic.thai.db.dao.MerchantOrderDao;
import com.magic.thai.db.service.impl.BaseTest;
import com.magic.thai.db.vo.OrderVo;
import com.magic.thai.util.PaginationSupport;

public class OrderDaoImplTest extends BaseTest {

	@Autowired
	MerchantOrderDao orderDao;

	@Test
	public void test() {
		PaginationSupport ps = orderDao.getOrderesPage(new OrderVo());
		System.out.println(ps);
		// fail("Not yet implemented");
	}

}
