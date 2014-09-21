package com.magic.thai.db.dao;

import java.util.List;

import com.magic.thai.db.domain.MerchantOrder;
import com.magic.thai.db.domain.User;
import com.magic.thai.db.vo.OrderVo;
import com.magic.thai.util.PaginationSupport;

public interface MerchantOrderDao {

	public MerchantOrder loadById(int id);

	public MerchantOrder fetch(int id);

	public MerchantOrder loadByNo(String orderNo);

	public MerchantOrder fetchByNo(String orderNo);

	public List<MerchantOrder> fetchByChannelOrder(int channelOrderId);

	public Integer create(MerchantOrder entity);

	public void update(MerchantOrder entity);

	public PaginationSupport getOrderesPage(OrderVo vo);

	public int auditingOrderCount(User user);
}
