package com.magic.thai.db.dao;

import com.magic.thai.db.domain.ChannelOrder;
import com.magic.thai.db.vo.OrderVo;
import com.magic.thai.exception.ThaiException;
import com.magic.thai.util.PaginationSupport;

public interface ChannelOrderDao {

	public ChannelOrder loadById(int id);

	public ChannelOrder fetch(int id);

	public ChannelOrder loadByNo(String orderNo);

	public ChannelOrder fetchByNo(String orderNo) throws ThaiException;

	public Integer create(ChannelOrder entity);

	public void update(ChannelOrder entity);

	public PaginationSupport getOrderesPage(OrderVo vo);

}
