package com.magic.thai.db.service;

import com.magic.thai.db.domain.ChannelOrder;
import com.magic.thai.db.vo.OrderVo;
import com.magic.thai.exception.ThaiException;
import com.magic.thai.security.UserProfile;
import com.magic.thai.util.PaginationSupport;

public interface ChannelOrderService {

	public ChannelOrder load(int id);

	public ChannelOrder fetch(int id);

	/**
	 * 跟进处理
	 * 
	 * @param orderId
	 * @param reason
	 * @param userprofile
	 * @throws ThaiException
	 */
	public void proc(int channelOrderId, String reason, UserProfile userprofile) throws ThaiException;

	/**
	 * 确定订单，如果其merchantorders全部完成，那么修改channelorder状态
	 * 
	 * @param channelId
	 */
	public void confirm(int channelId);

	public PaginationSupport getOrderesPage(OrderVo vo);

}
