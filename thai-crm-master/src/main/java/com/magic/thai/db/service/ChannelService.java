package com.magic.thai.db.service;

import com.magic.thai.db.domain.Channel;
import com.magic.thai.security.UserProfile;
import com.magic.thai.util.PaginationSupport;

public interface ChannelService {

	public Channel load(int id);

	public Channel load(String token);

	public Channel fetch(int id);

	public void delete(int channelId, UserProfile userprofile);

	public void update(Channel channelbean, UserProfile userprofile);

	public int create(Channel channel, UserProfile userprofile);

	public void open(int channelId, UserProfile userprofile);

	public void close(int channelId, UserProfile userprofile);

	public PaginationSupport getChannelesPage(int queryPage);

	/**
	 * 刷新推送商品数量
	 * 
	 * @param channel
	 * @param count
	 */
	public void refreshSoldGoodsCount(Channel channel, int count);
	
	/**
	 * 获取渠道，开通页面的
	 * @param merchantId
	 * @return 可能为空
	 */
	public Channel loadByMerchantId(int merchantId);

}
