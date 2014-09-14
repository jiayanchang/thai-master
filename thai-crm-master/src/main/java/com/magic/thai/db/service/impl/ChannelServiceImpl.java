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
import com.magic.thai.db.dao.GoodsDao;
import com.magic.thai.db.dao.MerchantDao;
import com.magic.thai.db.dao.UserDao;
import com.magic.thai.db.domain.Channel;
import com.magic.thai.db.domain.ChannelGoodsInv;
import com.magic.thai.db.domain.ChannelLog;
import com.magic.thai.db.domain.ChannelMerchantInv;
import com.magic.thai.db.service.ChannelService;
import com.magic.thai.security.UserProfile;
import com.magic.thai.util.Md5CryptoUtils;
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
	@Autowired
	private GoodsDao goodsDao;

	@Override
	public Channel load(int id) {
		return channelDao.loadById(id);
	}

	@Override
	public Channel fetch(int id) {
		Channel channel = channelDao.loadById(id);
		channel.setGoodsInvs(channelGoodsInvDao.getInvs(id));
		for (ChannelGoodsInv channelGoodsInv : channel.getGoodsInvs()) {
			channelGoodsInv.setGoods(goodsDao.loadById(channelGoodsInv.getGoodsId()));
		}
		channel.setMerchantInvs(channelMerchantInvDao.getInvs(id));
		for (ChannelMerchantInv channelMerchantInv : channel.getMerchantInvs()) {
			channelMerchantInv.setMerchant(merchantDao.loadById(channelMerchantInv.getMerchantId()));
		}
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
		channel.setOperatorId(channelbean.getOperatorId());
		channel.setOperatorName(userDao.loadById(channel.getOperatorId()).getName());
		channelDao.update(channel);

		for (int i = channel.getGoodsInvs().size() - 1; i >= 0; i--) {
			ChannelGoodsInv record = channel.getGoodsInvs().get(i);
			for (ChannelGoodsInv bean : channelbean.getGoodsInvs()) {
				if (bean.getId() == 0) {
					bean.setChannelId(channel.getId());
					channelGoodsInvDao.create(bean);
				} else if (bean.getId() == record.getId()) {
					record.setAllocatedAmount(bean.getAllocatedAmount());
					channelGoodsInvDao.update(record);
					channel.getGoodsInvs().remove(i);
				}
			}
		}
		for (ChannelGoodsInv record : channel.getGoodsInvs()) {
			channelGoodsInvDao.delete(record);
		}

		for (int i = channel.getMerchantInvs().size() - 1; i >= 0; i--) {
			ChannelMerchantInv record = channel.getMerchantInvs().get(i);
			for (ChannelMerchantInv bean : channelbean.getMerchantInvs()) {
				if (bean.getId() == 0) {
					bean.setChannelId(channel.getId());
					channelMerchantInvDao.create(bean);
				} else if (bean.getId() == record.getId()) {
					record.setAllocatedAmount(bean.getAllocatedAmount());
					channelMerchantInvDao.update(record);
					channel.getMerchantInvs().remove(i);
				}
			}
		}
		for (ChannelMerchantInv record : channel.getMerchantInvs()) {
			channelMerchantInvDao.delete(record);
		}

		channelLogDao.create(new ChannelLog(channel, userprofile.getUser(), "修改渠道信息，新内容为" + channel));
	}

	@Override
	@Transactional
	public int create(Channel channel, UserProfile userprofile) {
		channel.setToken(Md5CryptoUtils.create(channel.getName()));
		channel.setOperatorName(userDao.loadById(channel.getOperatorId()).getName());
		int id = channelDao.create(channel);

		for (ChannelGoodsInv channelGoodsInv : channel.getGoodsInvs()) {
			channelGoodsInv.setChannelId(id);
			channelGoodsInvDao.create(channelGoodsInv);
		}
		for (ChannelMerchantInv channelMerchantInv : channel.getMerchantInvs()) {
			channelMerchantInv.setChannelId(id);
			channelMerchantInvDao.create(channelMerchantInv);
		}

		channelLogDao.create(new ChannelLog(channel, userprofile.getUser(), "创建渠道信息，新内容为" + channel));
		return id;
	}

	@Override
	public PaginationSupport getChannelesPage(int queryPage) {
		return channelDao.getChannelesPage(queryPage);
	}

}
