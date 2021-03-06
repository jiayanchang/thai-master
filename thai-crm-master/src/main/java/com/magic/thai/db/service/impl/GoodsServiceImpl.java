package com.magic.thai.db.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magic.thai.db.dao.ChannelDao;
import com.magic.thai.db.dao.GoodsDao;
import com.magic.thai.db.dao.GoodsDetailsDao;
import com.magic.thai.db.dao.GoodsLogDao;
import com.magic.thai.db.dao.GoodsPriceSegmentDao;
import com.magic.thai.db.dao.MerchantDao;
import com.magic.thai.db.domain.Channel;
import com.magic.thai.db.domain.ChannelGoodsInv;
import com.magic.thai.db.domain.ChannelMerchantInv;
import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.GoodsDetails;
import com.magic.thai.db.domain.GoodsLog;
import com.magic.thai.db.domain.GoodsPriceSegment;
import com.magic.thai.db.domain.Merchant;
import com.magic.thai.db.domain.User;
import com.magic.thai.db.service.GoodsService;
import com.magic.thai.db.service.UserService;
import com.magic.thai.db.vo.GoodsVo;
import com.magic.thai.exception.GoodsCheckedException;
import com.magic.thai.exception.GoodsStatusException;
import com.magic.thai.exception.NoPermissionsException;
import com.magic.thai.exception.ThaiException;
import com.magic.thai.exception.webservice.ParameterException;
import com.magic.thai.security.UserProfile;
import com.magic.thai.util.Asserts;
import com.magic.thai.util.CalendarUtils;
import com.magic.thai.util.PaginationSupport;
import com.magic.thai.web.ws.vo.QueryGoodsesVo;

@Service("goodsService")
@Transactional
public class GoodsServiceImpl extends ServiceHelperImpl<Goods> implements GoodsService {

	private static Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);

	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private UserService userService;
	@Autowired
	private GoodsDetailsDao goodsDetailsDao;
	@Autowired
	private GoodsLogDao goodsLogDao;
	@Autowired
	private GoodsPriceSegmentDao goodsPriceSegmentDao;
	@Autowired
	private MerchantDao merchantDao;
	@Autowired
	private ChannelDao channelDao;

	@Override
	public Goods load(int id) {
		return goodsDao.loadById(id);
	}

	@Override
	public Goods fetch(int id) {
		Goods goods = goodsDao.loadById(id);
		if(goods == null) return null;
		Hibernate.initialize(goods.getSegments());
		Hibernate.initialize(goods.getDetails());
		return goods;
	}

	@Override
	public List<Goods> fetchList(QueryGoodsesVo vo, Channel channel) {
		List<Goods> goods = goodsDao.fetchList(vo, channel);
		return goods;
	}

	@Override
	@Transactional
	public void submit(Goods goods, UserProfile userprofile) throws GoodsStatusException {
		if (goods.getStatus() != Goods.Status.NEW) {
			throw new GoodsStatusException(goods.getStatusDesc() + "状态的订单不能提交上架");
		}
		goods.setStatus(Goods.Status.AUDITING);
		goodsDao.update(goods);
		goodsLogDao.create(new GoodsLog(goods, userprofile.getUser(), "商品提交上架"));
	}

	@Override
	@Transactional
	public void pass(int goodsId, UserProfile userprofile) throws NoPermissionsException, GoodsStatusException {
		if (!userprofile.isPlatformUser()) {
			throw new NoPermissionsException("当前用户无权审核");
		}

		Goods goods = goodsDao.loadById(goodsId);
		if (goods.getStatus() != Goods.Status.AUDITING) {
			throw new GoodsStatusException(goods.getStatusDesc() + "状态的订单不能审核通过");
		}
		goods.setStatus(Goods.Status.DEPLOYED);
		goodsDao.update(goods);
		goodsLogDao.create(new GoodsLog(goods, userprofile.getUser(), "商品通过审核，已上架"));
	}

	@Override
	@Transactional
	public void reject(int goodsId, String reason, UserProfile userprofile) throws GoodsStatusException {
		Goods goods = goodsDao.loadById(goodsId);
		if (goods.getStatus() != Goods.Status.AUDITING) {
			throw new GoodsStatusException(goods.getStatusDesc() + "状态的订单不能驳回申请");
		}
		goods.setStatus(Goods.Status.REJECTED);
		goodsDao.update(goods);
		goodsLogDao.create(new GoodsLog(goods, userprofile.getUser(), "驳回商品上架申请"));

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void cancel(int goodsId, String reason, UserProfile userprofile) throws GoodsStatusException {
		Goods goods = goodsDao.loadById(goodsId);
		if (goods.getStatus() != Goods.Status.DEPLOYED) {
			throw new GoodsStatusException(goods.getStatusDesc() + "状态的订单不能下架");
		}
		goods.setStatus(Goods.Status.CANNELED);
		goodsDao.update(goods);
		goodsLogDao.create(new GoodsLog(goods, userprofile.getUser(), "商品下架,原因：" + reason));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(int goodsId, UserProfile userprofile) throws GoodsStatusException {
		Goods goods = goodsDao.loadById(goodsId);

		if (goods.getStatus() == Goods.Status.DEPLOYED) {
			throw new GoodsStatusException(goods.getStatusDesc() + "状态的订单不能删除");
		}

		goods.setStatus(Goods.Status.DELETED);
		goodsDao.update(goods);
		goodsLogDao.create(new GoodsLog(goods, userprofile.getUser(), "商品删除"));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(Goods goodsbean, UserProfile userprofile) throws GoodsStatusException {
		Goods goods = fetch(goodsbean.getId());
		if (goods.getStatus() == Goods.Status.DEPLOYED || goods.getStatus() == Goods.Status.DELETED) {
			throw new GoodsStatusException(goods.getStatusDesc() + "状态的订单不能修改");
		}
		goods.setTitle(goodsbean.getTitle());
		// goods.setTitleCn(titleCn);
		goods.setTitleEn(goodsbean.getTitleEn());
		goods.setDept(goodsbean.getDept());
		goods.setArrived(goodsbean.getArrived());
		goods.setTravelDays(goodsbean.getTravelDays());
		goods.setGoodsCount(goodsbean.getGoodsCount());
		goods.setSummary(goodsbean.getSummary());
		goods.setBasePrice(goodsbean.getBasePrice());
		goods.setBasePriceChild(goodsbean.getBasePriceChild());
		goods.setStatus(goodsbean.getStatus());

		goods.getDetails().setTravelPlan(goodsbean.getDetails().getTravelPlan());
		goods.getDetails().setCostDesc(goodsbean.getDetails().getCostDesc());
		goods.getDetails().setBookNotes(goodsbean.getDetails().getBookNotes());
		goods.getDetails().setNotes(goodsbean.getDetails().getNotes());

		for (int j = goods.getSegments().size() - 1; j >= 0; j--) {
			GoodsPriceSegment segment = goods.getSegments().get(j);
			boolean exsits = false;
			for (int i = goodsbean.getSegments().size() - 1; i >= 0; i--) {
				GoodsPriceSegment segmentbean = goodsbean.getSegments().get(i);

				if (segmentbean.getId() > 0 && segment.getId() == segmentbean.getId()) {
					segment.setAuditPrice(segmentbean.getAuditPrice());
					segment.setChildPrice(segmentbean.getChildPrice());
					segment.setStartDate(segmentbean.getStartDate());
					segment.setEndDate(segmentbean.getEndDate());
					segment.setGoods(goods);
					goodsPriceSegmentDao.update(segment);
					goodsbean.getSegments().remove(i);
					exsits = true;
				}
			}

			if (!exsits) {
				goods.getSegments().remove(j);
				goodsPriceSegmentDao.delete(segment);
			}
		}

		for (GoodsPriceSegment segmentbean : goodsbean.getSegments()) {
			segmentbean.setGoods(goods);
			goodsPriceSegmentDao.create(segmentbean);
		}

		goodsDao.update(goods);
		goodsLogDao.create(new GoodsLog(goods, userprofile.getUser(), "修改商品信息，新内容为" + goodsbean));
	}

	@Override
	public void update(GoodsDetails goodsDetails, UserProfile userprofile) {
		goodsDetailsDao.update(goodsDetails);
	}

	@Override
	@Transactional
	public int create(Goods goods, UserProfile userprofile) {
		goods.setStatus(Goods.Status.AUDITING);
		Merchant merchant = merchantDao.loadById(userprofile.getUser().getMerchantId());
		goods.setMerchantId(merchant.getId());
		goods.setMerchantName(merchant.getName());
		goods.getDetails().setGoods(goods);
		int id = goodsDao.create(goods);

		goodsDetailsDao.create(goods.getDetails());
		for (GoodsPriceSegment segment : goods.getSegments()) {
			segment.setGoods(goods);
			goodsPriceSegmentDao.create(segment);
		}
		// update total amount
		goodsDao.update(goods);
		goodsLogDao.create(new GoodsLog(goods, userprofile.getUser(), "创建商品信息，新内容为" + goods));
		return 0;
	}

	@Override
	public PaginationSupport getGoodsesPage(GoodsVo vo) {
		return goodsDao.getGoodsesPage(vo);
	}

	@Override
	public List<Goods> list(GoodsVo vo) {
		return goodsDao.list(vo);
	}

	@Override
	public boolean checkGoods(String channelToken, int goodsId, Date deptDate, int count) throws ThaiException {
		Channel channel = channelDao.fetchByToken(channelToken);
		Asserts.notNull(channel, new GoodsCheckedException("当前渠道TOKEN无效"));
		Goods goods = goodsDao.loadById(goodsId);
		Asserts.notNull(goods, new GoodsCheckedException("当前商品已变更或不存在"));
		return checkGoods(channel, goods, deptDate, count);
	}

	@Override
	public boolean checkGoods(Channel channel, Goods goods, Date deptDate, int count) throws ThaiException {
		// 每天极限200人
		Asserts.notNull(channel, new GoodsCheckedException("当前渠道TOKEN无效"));
		Asserts.notNull(goods, new GoodsCheckedException("当前商品已变更或不存在"));
		Asserts.isTrue(goods.isDeployed(), new GoodsCheckedException("当前商品不可用"));

		if (CalendarUtils.large(new Date(), deptDate, 20)) {
			logger.info("TOKEN={},出发时间={},商品={},库存={}，已售={}, NOW={}",
					new Object[] { channel.getToken(), deptDate, goods.getId(), goods.getGoodsCount(), goods.getSoldCount(), new Date() });
			return true;
		}

		ChannelGoodsInv channelGoodsInv = channel.getGoodsInv(goods.getId());

		if (channelGoodsInv != null) {
			logger.info("TOKEN={},出发时间={},商品={},G分配量={}, 库存={}，已售={}", new Object[] { channel.getToken(), deptDate, goods.getId(),
					channelGoodsInv.getAllocatedAmount(), goods.getGoodsCount(), goods.getSoldCount() });
			int enable = ((Float) ((channelGoodsInv.getAllocatedAmount() / 100f) * goods.getGoodsCount())).intValue();
			return enable >= goods.getSoldCount() + count;
		} else {
			ChannelMerchantInv channelMerchantInv = channel.getMerchantInv(goods.getMerchantId());
			if (channelMerchantInv != null) {
				logger.info("TOKEN={},出发时间={},商品={},M分配量={}, 库存={}，已售={}", new Object[] { channel.getToken(), deptDate, goods.getId(),
						channelMerchantInv.getAllocatedAmount(), goods.getGoodsCount(), goods.getSoldCount() });
				int enable = ((Float) ((channelMerchantInv.getAllocatedAmount() / 100f) * goods.getGoodsCount())).intValue();
				return enable >= goods.getSoldCount() + count;
			} else {
				logger.info("TOKEN={},出发时间={},商品={},库存={}，已售={}  没有匹配条件",
						new Object[] { channel.getToken(), deptDate, goods.getId(), goods.getGoodsCount(), goods.getSoldCount() });
				throw new ParameterException("当前商品不可售,ID：" + goods.getId());
			}
		}
	}

	@Override
	public int getAuditingGoodsCount(User user) {
		return goodsDao.getAuditingGoodsCount(user);
	}

}
