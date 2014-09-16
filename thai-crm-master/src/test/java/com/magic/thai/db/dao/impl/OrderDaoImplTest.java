package com.magic.thai.db.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.magic.thai.db.dao.OrderDao;
import com.magic.thai.db.service.impl.BaseTest;
import com.magic.thai.db.vo.OrderVo;
import com.magic.thai.util.PaginationSupport;

public class OrderDaoImplTest extends BaseTest {

	@Autowired
	OrderDao orderDao;

	@Test
	public void test() {
		PaginationSupport ps = orderDao.getOrderesPage(new OrderVo(), 1, null);
		System.out.println(ps);
		// fail("Not yet implemented");
	}

}
