package com.magic.thai.db.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magic.thai.db.dao.ChannelDao;
import com.magic.thai.db.dao.ChannelGoodsInvDao;
import com.magic.thai.db.dao.ChannelLogDao;
import com.magic.thai.db.dao.ChannelMerchantInvDao;
import com.magic.thai.db.dao.MerchantDao;
import com.magic.thai.db.dao.UserDao;
import com.magic.thai.db.domain.Channel;
import com.magic.thai.db.domain.ChannelLog;
import com.magic.thai.db.domain.Merchant;
import com.magic.thai.db.service.ChannelService;
import com.magic.thai.security.UserProfile;
import com.magic.thai.util.PaginationSupport;

@Service("channelService")
@Transactional
public class ChannelServiceImpl extends ServiceHelperImpl<Channel> implements ChannelService {

	static Logger logger = LoggerFactory.getLogger(ChannelServiceImpl.class);

	@Autowired
	private ChannelDao channelDao;
	@Autowired
	private ChannelLogDao channelLogDao;
	@Autowired
	private ChannelGoodsInvDao channelGoodsInvDao;
	@Autowired
	private ChannelMerchantInvDao channelMerchantInvDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private MerchantDao merchantDao;

	@Override
	public Channel load(int id) {
		return channelDao.loadById(id);
	}

	@Override
	public Channel fetch(int id) {
		Channel channel = channelDao.loadById(id);
		channel.setGoodsInvs(channelGoodsInvDao.getInvs(id));
		channel.setMerchantInvs(channelMerchantInvDao.getInvs(id));
		return channel;
	}

	@Override
	@Transactional
	public void delete(int channelId, UserProfile userprofile) {
		channelDao.delete(channelId);
	}

	@Override
	@Transactional
	public void update(Channel channelbean, UserProfile userprofile) {
		Channel channel = fetch(channelbean.getId());

		create(channelbean);
		channelDao.update(channelbean);

		channelDao.update(channel);

		channelLogDao.create(new ChannelLog(channel, userprofile.getUser(), "修改商品信息，新内容为" + channelbean));
	}

	@Override
	@Transactional
	public int create(Channel channel, UserProfile userprofile) {
		Merchant merchant = merchantDao.loadById(userprofile.getUser().getMerchantId());
		int id = channelDao.create(channel);
		// channel.getDetails().setId(id);
		//
		// channelDetailsDao.create(channel.getDetails());
		// int i = 0;
		// for (ChannelPriceSegment segment : channel.getSegments()) {
		// segment.setChannelId(id);
		// segment.setOrderBy(i++);
		// channel.setAdultTotalPrice(segment.getAuditPrice() +
		// channel.getAdultTotalPrice());
		// channel.setChildTotalPrice(segment.getChildPrice() +
		// channel.getChildTotalPrice());
		// channelPriceSegmentDao.create(segment);
		// }
		// update total amount and rootid
		channelDao.update(channel);
		channelLogDao.create(new ChannelLog(channel, userprofile.getUser(), "创建商品信息，新内容为" + channel));
		return 0;
	}

	@Override
	public PaginationSupport getChannelesPage(int queryPage) {
		return channelDao.getChannelesPage(queryPage);
	}

}
