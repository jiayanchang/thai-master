package com.magic.thai.db.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magic.thai.db.dao.ChannelDao;
import com.magic.thai.db.dao.ChannelMerchantInvDao;
import com.magic.thai.db.dao.ChannelOrderDao;
import com.magic.thai.db.dao.ChannelOrderTravelerDao;
import com.magic.thai.db.dao.GoodsDao;
import com.magic.thai.db.dao.MerchantOrderDao;
import com.magic.thai.db.dao.MerchantOrderGoodsDao;
import com.magic.thai.db.dao.MerchantOrderGoodsPickupDao;
import com.magic.thai.db.dao.MerchantOrderNotesDao;
import com.magic.thai.db.domain.Channel;
import com.magic.thai.db.domain.ChannelGoodsInv;
import com.magic.thai.db.domain.ChannelMerchantInv;
import com.magic.thai.db.domain.ChannelOrder;
import com.magic.thai.db.domain.ChannelOrderTraveler;
import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.GoodsPriceSegment;
import com.magic.thai.db.domain.MerchantOrder;
import com.magic.thai.db.domain.MerchantOrderGoods;
import com.magic.thai.db.domain.MerchantOrderGoodsPickup;
import com.magic.thai.db.domain.MerchantOrderNotes;
import com.magic.thai.db.service.ChannelService;
import com.magic.thai.db.service.GoodsService;
import com.magic.thai.db.service.InterfaceOrderService;
import com.magic.thai.db.service.OrderService;
import com.magic.thai.db.service.SnapshotGoodsService;
import com.magic.thai.db.service.strategy.GoodsPriceCalculator;
import com.magic.thai.exception.OrderStatusException;
import com.magic.thai.exception.ThaiException;
import com.magic.thai.exception.webservice.ParameterException;
import com.magic.thai.security.UserProfile;
import com.magic.thai.util.Asserts;
import com.magic.thai.util.CalendarUtils;
import com.magic.thai.util.DoubleUtils;
import com.magic.thai.util.OrderNoGenerator;
import com.magic.thai.web.ws.vo.BuyGoodsVo;
import com.magic.thai.web.ws.vo.CheckGoodsVo;
import com.magic.thai.web.ws.vo.CreateOrderVo;
import com.magic.thai.web.ws.vo.QueryGoodsesVo;
import com.magic.thai.web.ws.vo.QueryOrderVo;
import com.magic.thai.web.ws.vo.RefundGoodsVo;
import com.magic.thai.web.ws.vo.RefundOrderVo;
import com.magic.thai.web.ws.vo.TravelerVo;

@Service("interfaceOrderService")
@Transactional
public class InterfaceOrderServiceImpl extends ServiceHelperImpl<MerchantOrder> implements InterfaceOrderService {

	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private ChannelOrderTravelerDao orderTravelerDao;
	@Autowired
	private ChannelDao channelDao;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private SnapshotGoodsService snapshotGoodsService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private MerchantOrderNotesDao merchantOrderNotesDao;
	@Autowired
	private ChannelOrderDao channelOrderDao;
	@Autowired
	private MerchantOrderDao merchantOrderDao;
	@Autowired
	MerchantOrderGoodsDao merchantOrderGoodsDao;
	@Autowired
	MerchantOrderGoodsPickupDao merchantOrderGoodsPickupDao;
	@Autowired
	ChannelMerchantInvDao channelMerchantInvDao;
	@Autowired
	private GoodsPriceCalculator goodsPriceCalculator;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ChannelOrder merchantCreateOrder(CreateOrderVo vo, UserProfile userprofile) throws ThaiException {

		ChannelOrder channelOrder = new ChannelOrder();
		channelOrder.setChannelId(userprofile.getMerchant().getId());
		channelOrder.setChannelName(userprofile.getMerchant().getName());
		channelOrder.setContractor(vo.getOrderContactor());
		channelOrder.setContractorEmail(vo.getOrderContactorEmail());
		channelOrder.setContractorMobile(vo.getOrderContactorMobile());
		channelOrder.setCreatedDate(new Date());
		channelOrder.setType(ChannelOrder.OrderType.OFFLINE_ORDER);
		channelOrder.setStatus(ChannelOrder.Status.COMPELED);

		int channelOrderId = channelOrderDao.create(channelOrder);

		Map<String, MerchantOrder> merchantOrderMap = new HashMap<String, MerchantOrder>();
		Map<MerchantOrderGoods, BuyGoodsVo> goodsVoMap = new HashMap<MerchantOrderGoods, BuyGoodsVo>();

		for (BuyGoodsVo goodsVo : vo.getGoodses()) {
			try {
				goodsVo.deptDateObj = DateUtils.parseDate(goodsVo.getDeptDate(), new String[] { "yyyy-MM-dd", "yyyy/MM/dd" });
			} catch (Exception e) {
				throw new ParameterException("出发日期格式异常");
			}
			Goods goods = goodsDao.loadById(goodsVo.getGoodsId());
			Asserts.notNull(goods, new ParameterException("商品ID有误：" + goodsVo.getGoodsId()));

			double totalamount = goodsVo.getPrice() * goodsVo.getQty();

			MerchantOrderGoods merchantOrderGoods = new MerchantOrderGoods();
			merchantOrderGoods.setAmount(goodsVo.getPrice());
			merchantOrderGoods.setProfit(goodsVo.getPrice());
			merchantOrderGoods.setChannelId(0);
			merchantOrderGoods.setDeptDate(goodsVo.deptDateObj);
			merchantOrderGoods.setGoodsId(goods.getId());
			merchantOrderGoods.setGoodsName(goods.getTitle());
			merchantOrderGoods.setMerchantId(goods.getMerchantId());

			merchantOrderGoods.setQuantity(goodsVo.getQty());
			// mogoods.setTravelerNames(travelerNames);

			if (merchantOrderMap.containsKey(goods.getMerchantId() + "")) {
				MerchantOrder merchantOrder = merchantOrderMap.get(goods.getMerchantId() + "");
				merchantOrder.getGoodses().add(merchantOrderGoods);
				merchantOrder.setAmount(merchantOrder.getAmount() + totalamount);
				merchantOrder.setProfitAmount(merchantOrder.getAmount());
				merchantOrderGoods.setMerchantOrder(merchantOrder);
			} else {

				MerchantOrder merchantOrder = new MerchantOrder();
				merchantOrder.setChannelId(0);
				merchantOrder.setChannelName("");
				merchantOrder.setChannelOrderId(channelOrderId);
				merchantOrder.setContractor(vo.getOrderContactor());
				merchantOrder.setContractorEmail(vo.getOrderContactorEmail());
				merchantOrder.setContractorMobile(vo.getOrderContactorMobile());
				merchantOrder.setCreatedDate(new Date());
				merchantOrder.setCreatorName(userprofile.getUser().getName());
				merchantOrder.setCreatorType(MerchantOrder.UserType.CHANNEL);
				merchantOrder.setMerchantId(goods.getMerchantId());
				merchantOrder.setMerchantName(goods.getMerchantName());
				merchantOrder.setStatus(MerchantOrder.Status.NEW);
				merchantOrder.setTravelerNum(goodsVo.getQty());
				merchantOrder.setHotelAddress(vo.getHotelAddress());
				merchantOrder.setHotelName(vo.getHotelName());
				merchantOrder.setHotelRoom(vo.getHotelRoom());
				merchantOrder.setHotelRoomTel(vo.getHotelRoomTel());
				merchantOrder.setStatus(MerchantOrder.Status.COMPLETED);

				merchantOrderMap.put(goods.getMerchantId() + "", merchantOrder);
				merchantOrder.setAmount(merchantOrder.getAmount() + totalamount);
				merchantOrder.setProfitAmount(merchantOrder.getAmount());
				merchantOrder.setDriverMobile(vo.getDriverMobile());
				merchantOrder.setDriverName(vo.getDriverName());
				merchantOrder.setHotelTel(vo.getHotelTel());

				merchantOrder.getGoodses().add(merchantOrderGoods);
			}
			// Asserts.isTrue(goodsService.checkGoods(channel, goods,
			// goodsVo.deptDateObj, vo.getTravelers().size()),
			// new GoodsCheckedException("商品数量不足"));
			// 没超出20天的商品已售叠加
			goods.setSoldCount(goods.getSoldCount() + vo.getTravelers().size());
			goodsDao.update(goods);
			channelOrder.setAmount(channelOrder.getAmount() + totalamount);
			goodsVoMap.put(merchantOrderGoods, goodsVo);
		}

		saveOrderInfo(channelOrder, merchantOrderMap, goodsVoMap);

		createTravelers(vo, channelOrder);

		return channelOrder;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ChannelOrder adminCreateOrder(CreateOrderVo vo, UserProfile userprofile) throws ThaiException {

		ChannelOrder channelOrder = new ChannelOrder();
		channelOrder.setChannelId(userprofile.getMerchant().getId());
		channelOrder.setChannelName(userprofile.getMerchant().getName());
		// channelOrder.setChannelOrderNo(channelOrderNo);
		channelOrder.setContractor(vo.getOrderContactor());
		channelOrder.setContractorEmail(vo.getOrderContactorEmail());
		channelOrder.setContractorMobile(vo.getOrderContactorMobile());
		channelOrder.setCreatedDate(new Date());
		channelOrder.setType(ChannelOrder.OrderType.CHANNEL_ORDER);
		channelOrder.setStatus(ChannelOrder.Status.NEW);

		int channelOrderId = channelOrderDao.create(channelOrder);

		Map<String, MerchantOrder> merchantOrderMap = new HashMap<String, MerchantOrder>();
		Map<MerchantOrderGoods, BuyGoodsVo> goodsVoMap = new HashMap<MerchantOrderGoods, BuyGoodsVo>();
		for (BuyGoodsVo goodsVo : vo.getGoodses()) {
			try {
				goodsVo.deptDateObj = DateUtils.parseDate(goodsVo.getDeptDate(), new String[] { "yyyy-MM-dd", "yyyy/MM/dd" });
			} catch (Exception e) {
				throw new ParameterException("出发日期格式异常");
			}
			Goods goods = goodsDao.loadById(goodsVo.getGoodsId());
			Asserts.notNull(goods, new ParameterException("商品ID有误：" + goodsVo.getGoodsId()));

			// 验证加价价格,在此这个goodsvo。price在页面用于存入利润
			double goodsPrice = CreateOrderValidator.getPirce(goods, goodsVo);
			double profitPrice = DoubleUtils.add(goodsVo.getPrice(), goodsPrice);

			double goodsAmount = DoubleUtils.mul(goodsPrice, (double) goodsVo.getQty());
			double profitAmount = DoubleUtils.mul(profitPrice, (double) goodsVo.getQty());

			MerchantOrderGoods merchantOrderGoods = new MerchantOrderGoods();
			merchantOrderGoods.setAmount(goodsPrice);
			merchantOrderGoods.setProfit(profitPrice);
			merchantOrderGoods.setChannelId(0);
			merchantOrderGoods.setDeptDate(goodsVo.deptDateObj);
			merchantOrderGoods.setGoodsId(goods.getId());
			merchantOrderGoods.setGoodsName(goods.getTitle());
			merchantOrderGoods.setMerchantId(goods.getMerchantId());

			merchantOrderGoods.setQuantity(goodsVo.getQty());
			// mogoods.setTravelerNames(travelerNames);
			merchantOrderGoods.setNeedsPickup(goodsVo.isNeedsPickup());

			if (merchantOrderMap.containsKey(goods.getMerchantId() + "")) {
				MerchantOrder merchantOrder = merchantOrderMap.get(goods.getMerchantId() + "");
				merchantOrder.getGoodses().add(merchantOrderGoods);
				merchantOrder.setAmount(merchantOrder.getAmount() + goodsAmount);
				merchantOrder.setProfitAmount(merchantOrder.getProfitAmount() + profitAmount);
				merchantOrderGoods.setMerchantOrder(merchantOrder);
			} else {

				MerchantOrder merchantOrder = new MerchantOrder();
				merchantOrder.setChannelId(0);
				merchantOrder.setChannelName("");
				merchantOrder.setChannelOrderId(channelOrderId);
				merchantOrder.setContractor(vo.getOrderContactor());
				merchantOrder.setContractorEmail(vo.getOrderContactorEmail());
				merchantOrder.setContractorMobile(vo.getOrderContactorMobile());
				merchantOrder.setCreatedDate(new Date());
				merchantOrder.setCreatorName(userprofile.getUser().getName());
				merchantOrder.setCreatorType(MerchantOrder.UserType.CHANNEL);
				merchantOrder.setMerchantId(goods.getMerchantId());
				merchantOrder.setMerchantName(goods.getMerchantName());
				merchantOrder.setStatus(MerchantOrder.Status.NEW);
				merchantOrder.setTravelerNum(goodsVo.getQty());
				merchantOrder.setHotelAddress(vo.getHotelAddress());
				merchantOrder.setHotelName(vo.getHotelName());
				merchantOrder.setHotelRoom(vo.getHotelRoom());
				merchantOrder.setHotelRoomTel(vo.getHotelRoomTel());
				merchantOrder.setStatus(MerchantOrder.Status.NEW);

				merchantOrderMap.put(goods.getMerchantId() + "", merchantOrder);
				merchantOrder.setAmount(merchantOrder.getAmount() + goodsAmount);
				merchantOrder.setProfitAmount(merchantOrder.getProfitAmount() + profitAmount);
				merchantOrder.setDriverMobile(vo.getDriverMobile());
				merchantOrder.setDriverName(vo.getDriverName());
				merchantOrder.setHotelTel(vo.getHotelTel());

				merchantOrder.getGoodses().add(merchantOrderGoods);
			}
			// Asserts.isTrue(goodsService.checkGoods(channel, goods,
			// goodsVo.deptDateObj, vo.getTravelers().size()),
			// new GoodsCheckedException("商品数量不足"));
			// 没超出20天的商品已售叠加
			// if (!CalendarUtils.large(new Date(), goodsVo.deptDateObj, 20)) {
			goods.setSoldCount(goods.getSoldCount() + vo.getTravelers().size());
			goodsDao.update(goods);
			// }
			channelOrder.setAmount(channelOrder.getAmount() + profitAmount);

			goodsVoMap.put(merchantOrderGoods, goodsVo);
		}

		saveOrderInfo(channelOrder, merchantOrderMap, goodsVoMap);

		createTravelers(vo, channelOrder);

		return channelOrder;
	}

	private void saveOrderInfo(ChannelOrder channelOrder, Map<String, MerchantOrder> merchantOrderMap,
			Map<MerchantOrderGoods, BuyGoodsVo> goodsVoMap) {
		channelOrderDao.create(channelOrder);
		for (Entry<String, MerchantOrder> entry : merchantOrderMap.entrySet()) {
			MerchantOrder merchantOrder = entry.getValue();
			int merchantOrderId = merchantOrderDao.create(merchantOrder);
			for (MerchantOrderGoods merchantOrderGoods : merchantOrder.getGoodses()) {
				merchantOrderGoods.setMerchantOrder(merchantOrder);
				merchantOrderGoodsDao.create(merchantOrderGoods);
				// 生成商品快照
				snapshotGoodsService.create(merchantOrderGoods);

				if (merchantOrderGoods.isNeedsPickup()) {
					BuyGoodsVo buyGoodsVo = goodsVoMap.get(merchantOrderGoods);
					MerchantOrderGoodsPickup pickup = new MerchantOrderGoodsPickup();
					pickup.setArrivedDate(buyGoodsVo.getArrivedDate());
					pickup.setArrivedTime(buyGoodsVo.getArrivedTime());
					pickup.setFlightNo(buyGoodsVo.getFlightNo());
					pickup.setMerchantOrderGoodsId(merchantOrderGoods.getId());
					pickup.setMerchantOrderId(merchantOrderId);
					merchantOrderGoodsPickupDao.create(pickup);
				}
			}
			merchantOrder.setOrderNo(OrderNoGenerator.generateMerchantOrderNo(merchantOrder));
			merchantOrderDao.update(merchantOrder);

			channelMerchantInvDao.updateStat(merchantOrder);
		}
		// 生成单号
		channelOrder.setChannelOrderNo(OrderNoGenerator.generateChannelOrderNo(channelOrder));
		channelOrderDao.update(channelOrder);
	}

	/**
	 * 创建订单时，保存游客信息
	 * 
	 * @param vo
	 * @param channelOrder
	 */
	private void createTravelers(CreateOrderVo vo, ChannelOrder channelOrder) {
		for (TravelerVo travelerVo : vo.getTravelers()) {
			ChannelOrderTraveler orderTraveler = new ChannelOrderTraveler();
			orderTraveler.setOrder(channelOrder);
			orderTraveler.setBirth(travelerVo.getBirth());
			orderTraveler.setEffectiveDate(travelerVo.getEffectiveDate());
			orderTraveler.setGender(travelerVo.getGender());
			// orderTraveler.setId(travelerVo.get);
			orderTraveler.setIdNo(travelerVo.getIdNo());
			orderTraveler.setIdType(travelerVo.getIdType());
			orderTraveler.setMobile(travelerVo.getMobile());
			orderTraveler.setFirstName(travelerVo.getFirstName());
			orderTraveler.setLastName(travelerVo.getLastName());
			orderTraveler.setType(travelerVo.getType());
			orderTraveler.setNationality(travelerVo.getNationality());
			orderTravelerDao.create(orderTraveler);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ChannelOrder create(CreateOrderVo vo) throws ThaiException {

		// 下单逻辑需要重新梳理
		/*
		 * 1.传入商品需要传价格来进行验证 因为下单和查询时间差可能很大 期间有可能有调整<br> 2.商品下单时需要保存商品的单价和加价值 3.商家的商品数量验证也需要修改为每天200个的逻辑<br> 4.快照也需要存入加价值，供客服查看
		 */

		Channel channel = channelDao.fetchByToken(vo.getToken());

		Asserts.notNull(channel, new ParameterException("TOKEN有误"));
		Asserts.isTrue(channel.isEnabled(), new ParameterException("TOKEN无效"));

		ChannelOrder channelOrder = new ChannelOrder();
		channelOrder.setChannelId(channel.getId());
		channelOrder.setChannelName(channel.getName());
		channelOrder.setAssociateOrderNo(vo.getAssociateOrderNo());
		// channelOrder.setChannelOrderNo(channelOrderNo);
		channelOrder.setContractor(vo.getOrderContactor());
		channelOrder.setContractorEmail(vo.getOrderContactorEmail());
		channelOrder.setContractorMobile(vo.getOrderContactorMobile());
		channelOrder.setCreatedDate(new Date());

		int channelOrderId = channelOrderDao.create(channelOrder);

		Map<String, MerchantOrder> merchantOrderMap = new HashMap<String, MerchantOrder>();
		Map<MerchantOrderGoods, BuyGoodsVo> goodsVoMap = new HashMap<MerchantOrderGoods, BuyGoodsVo>();

		for (BuyGoodsVo goodsVo : vo.getGoodses()) {
			if (StringUtils.isNotBlank(goodsVo.getDeptDate())) {
				try {
					goodsVo.deptDateObj = DateUtils.parseDate(goodsVo.getDeptDate(), new String[] { "yyyy-MM-dd", "yyyy/MM/dd" });
				} catch (Exception e) {
					throw new ParameterException("出发日期格式异常");
				}
			}
			Goods goods = goodsService.fetch(goodsVo.getGoodsId());
			Asserts.notNull(goods, new ParameterException("商品ID有误：" + goodsVo.getGoodsId()));

			// 验证加价价格
			double profitPrice = CreateOrderValidator.validate(channel, goods, goodsVo);
			double goodsPrice = CreateOrderValidator.getPirce(goods, goodsVo);

			double goodsAmount = DoubleUtils.mul(goodsPrice, (double) goodsVo.getQty());
			double profitAmount = DoubleUtils.mul(profitPrice, (double) goodsVo.getQty());

			MerchantOrderGoods merchantOrderGoods = new MerchantOrderGoods();
			merchantOrderGoods.setAmount(goodsPrice);
			merchantOrderGoods.setProfit(profitPrice);
			merchantOrderGoods.setChannelId(channel.getId());
			merchantOrderGoods.setDeptDate(goodsVo.deptDateObj);
			merchantOrderGoods.setGoodsId(goods.getId());
			merchantOrderGoods.setGoodsName(goods.getTitle());
			merchantOrderGoods.setMerchantId(goods.getMerchantId());

			merchantOrderGoods.setQuantity(goodsVo.getQty());

			if (merchantOrderMap.containsKey(goods.getMerchantId() + "")) {
				MerchantOrder merchantOrder = merchantOrderMap.get(goods.getMerchantId() + "");
				merchantOrder.getGoodses().add(merchantOrderGoods);
				merchantOrder.setAmount(merchantOrder.getAmount() + goodsAmount);
				merchantOrder.setProfitAmount(merchantOrder.getProfitAmount() + profitAmount);
				merchantOrderGoods.setMerchantOrder(merchantOrder);
			} else {

				MerchantOrder merchantOrder = new MerchantOrder();
				merchantOrder.setChannelId(channel.getId());
				merchantOrder.setChannelName(channel.getName());
				merchantOrder.setChannelOrderId(channelOrderId);
				merchantOrder.setContractor(vo.getOrderContactor());
				merchantOrder.setContractorEmail(vo.getOrderContactorEmail());
				merchantOrder.setContractorMobile(vo.getOrderContactorMobile());
				merchantOrder.setCreatedDate(new Date());
				merchantOrder.setCreatorName(channel.getName());
				merchantOrder.setCreatorType(MerchantOrder.UserType.CHANNEL);
				merchantOrder.setMerchantId(goods.getMerchantId());
				merchantOrder.setMerchantName(goods.getMerchantName());
				merchantOrder.setStatus(MerchantOrder.Status.NEW);
				merchantOrder.setTravelerNum(goodsVo.getQty());
				merchantOrder.setHotelAddress(vo.getHotelAddress());
				merchantOrder.setHotelName(vo.getHotelName());
				merchantOrder.setHotelRoom(vo.getHotelRoom());
				merchantOrder.setHotelRoomTel(vo.getHotelRoomTel());

				merchantOrderMap.put(goods.getMerchantId() + "", merchantOrder);
				merchantOrder.setAmount(merchantOrder.getAmount() + goodsAmount);
				merchantOrder.setProfitAmount(merchantOrder.getProfitAmount() + profitAmount);
				merchantOrder.getGoodses().add(merchantOrderGoods);
				channelOrder.getMerchantOrders().add(merchantOrder);
			}

			// Asserts.isTrue(goodsService.checkGoods(channel, goods, goodsVo.deptDateObj, vo.getTravelers().size()),
			// new GoodsCheckedException("商品数量不足"));
			goods.setSoldCount(goods.getSoldCount() + vo.getTravelers().size());
			goodsDao.update(goods);
			channelOrder.setAmount(channelOrder.getAmount() + profitAmount);

			goodsVoMap.put(merchantOrderGoods, goodsVo);
		}

		saveOrderInfo(channelOrder, merchantOrderMap, goodsVoMap);

		createTravelers(vo, channelOrder);

		// 更新渠道信息
		updateChannelInfo(channel, channelOrder);

		return channelOrder;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ChannelOrder channelCreateOrder(CreateOrderVo vo, Channel channel, UserProfile userprofile) throws ThaiException {
		ChannelOrder channelOrder = new ChannelOrder();
		channelOrder.setChannelId(channel.getId());
		channelOrder.setChannelName(channel.getName());
		channelOrder.setAssociateOrderNo(vo.getAssociateOrderNo());
		// channelOrder.setChannelOrderNo(channelOrderNo);
		channelOrder.setContractor(vo.getOrderContactor());
		channelOrder.setContractorEmail(vo.getOrderContactorEmail());
		channelOrder.setContractorMobile(vo.getOrderContactorMobile());
		channelOrder.setCreatedDate(new Date());

		int channelOrderId = channelOrderDao.create(channelOrder);

		Map<String, MerchantOrder> merchantOrderMap = new HashMap<String, MerchantOrder>();
		Map<MerchantOrderGoods, BuyGoodsVo> goodsVoMap = new HashMap<MerchantOrderGoods, BuyGoodsVo>();

		for (BuyGoodsVo goodsVo : vo.getGoodses()) {
			if (StringUtils.isNotBlank(goodsVo.getDeptDate())) {
				try {
					goodsVo.deptDateObj = DateUtils.parseDate(goodsVo.getDeptDate(), new String[] { "yyyy-MM-dd", "yyyy/MM/dd" });
				} catch (Exception e) {
					throw new ParameterException("出发日期格式异常");
				}
			}
			Goods goods = goodsService.fetch(goodsVo.getGoodsId());
			Asserts.notNull(goods, new ParameterException("商品ID有误：" + goodsVo.getGoodsId()));

			// 验证加价价格
			double profitPrice = CreateOrderValidator.validate(channel, goods, goodsVo);
			double goodsPrice = CreateOrderValidator.getPirce(goods, goodsVo);

			double goodsAmount = DoubleUtils.mul(goodsPrice, (double) goodsVo.getQty());
			double profitAmount = DoubleUtils.mul(profitPrice, (double) goodsVo.getQty());

			MerchantOrderGoods merchantOrderGoods = new MerchantOrderGoods();
			merchantOrderGoods.setAmount(goodsPrice);
			merchantOrderGoods.setProfit(profitPrice);
			merchantOrderGoods.setChannelId(channel.getId());
			merchantOrderGoods.setDeptDate(goodsVo.deptDateObj);
			merchantOrderGoods.setGoodsId(goods.getId());
			merchantOrderGoods.setGoodsName(goods.getTitle());
			merchantOrderGoods.setMerchantId(goods.getMerchantId());

			merchantOrderGoods.setQuantity(goodsVo.getQty());

			if (merchantOrderMap.containsKey(goods.getMerchantId() + "")) {
				MerchantOrder merchantOrder = merchantOrderMap.get(goods.getMerchantId() + "");
				merchantOrder.getGoodses().add(merchantOrderGoods);
				merchantOrder.setAmount(merchantOrder.getAmount() + goodsAmount);
				merchantOrder.setProfitAmount(merchantOrder.getProfitAmount() + profitAmount);
				merchantOrderGoods.setMerchantOrder(merchantOrder);
			} else {

				MerchantOrder merchantOrder = new MerchantOrder();
				merchantOrder.setChannelId(channel.getId());
				merchantOrder.setChannelName(channel.getName());
				merchantOrder.setChannelOrderId(channelOrderId);
				merchantOrder.setContractor(vo.getOrderContactor());
				merchantOrder.setContractorEmail(vo.getOrderContactorEmail());
				merchantOrder.setContractorMobile(vo.getOrderContactorMobile());
				merchantOrder.setCreatedDate(new Date());
				merchantOrder.setCreatorName(channel.getName());
				merchantOrder.setCreatorType(MerchantOrder.UserType.CHANNEL);
				merchantOrder.setMerchantId(goods.getMerchantId());
				merchantOrder.setMerchantName(goods.getMerchantName());
				merchantOrder.setStatus(MerchantOrder.Status.NEW);
				merchantOrder.setTravelerNum(goodsVo.getQty());
				merchantOrder.setHotelAddress(vo.getHotelAddress());
				merchantOrder.setHotelName(vo.getHotelName());
				merchantOrder.setHotelRoom(vo.getHotelRoom());
				merchantOrder.setHotelRoomTel(vo.getHotelRoomTel());

				merchantOrderMap.put(goods.getMerchantId() + "", merchantOrder);
				merchantOrder.setAmount(merchantOrder.getAmount() + goodsAmount);
				merchantOrder.setProfitAmount(merchantOrder.getProfitAmount() + profitAmount);
				merchantOrder.getGoodses().add(merchantOrderGoods);
				channelOrder.getMerchantOrders().add(merchantOrder);
			}

			// Asserts.isTrue(goodsService.checkGoods(channel, goods, goodsVo.deptDateObj, vo.getTravelers().size()),
			// new GoodsCheckedException("商品数量不足"));
			goods.setSoldCount(goods.getSoldCount() + vo.getTravelers().size());
			goodsDao.update(goods);
			channelOrder.setAmount(channelOrder.getAmount() + profitAmount);

			goodsVoMap.put(merchantOrderGoods, goodsVo);
		}

		saveOrderInfo(channelOrder, merchantOrderMap, goodsVoMap);

		createTravelers(vo, channelOrder);

		// 更新渠道信息
		updateChannelInfo(channel, channelOrder);

		return channelOrder;
	}

	private void updateChannelInfo(Channel channel, ChannelOrder channelOrder) {
		channel.setOrderCount(channel.getOrderCount() + 1);
		channel.setAmount(DoubleUtils.add(channel.getAmount(), channelOrder.getAmount()));
		channelDao.update(channel);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Goods> queryGoodses(QueryGoodsesVo vo) throws ThaiException {
		Channel channel = channelDao.fetchByToken(vo.getToken());
		Asserts.notNull(channel, new ParameterException("TOKEN有误"));
		Asserts.isTrue(channel.isEnabled(), new ParameterException("TOKEN无效"));
		List<Goods> goodses = goodsService.fetchList(vo, channel);
		for (Goods goods : goodses) {
			updatePrice4Profit(channel, goods);
		}
		return goodses;
	}

	private void updatePrice4Profit(Channel channel, Goods goods) {
		ChannelGoodsInv goodsInv = channel.getGoodsInv(goods.getId());
		if (goodsInv != null) {
			goods.setBasePrice(DoubleUtils.add(goods.getBasePrice(), goodsInv.getProfitPrice()));
			goods.setBasePriceChild(DoubleUtils.add(goods.getBasePriceChild(), goodsInv.getProfitPrice()));
			for (GoodsPriceSegment goodsPriceSegment : goods.getSegments()) {
				goodsPriceSegment.setAuditPrice(DoubleUtils.add(goodsPriceSegment.getAuditPrice(), goodsInv.getProfitPrice()));
				goodsPriceSegment.setChildPrice(DoubleUtils.add(goodsPriceSegment.getChildPrice(), goodsInv.getProfitPrice()));
			}
		} else {
			ChannelMerchantInv merchantInv = channel.getMerchantInv(goods.getMerchantId());
			if (merchantInv.getProfitPrice() > 0) {
				goods.setBasePrice(DoubleUtils.add(goods.getBasePrice(), merchantInv.getProfitPrice()));
				goods.setBasePriceChild(DoubleUtils.add(goods.getBasePriceChild(), merchantInv.getProfitPrice()));
				for (GoodsPriceSegment goodsPriceSegment : goods.getSegments()) {
					goodsPriceSegment.setAuditPrice(DoubleUtils.add(goodsPriceSegment.getAuditPrice(), merchantInv.getProfitPrice()));
					goodsPriceSegment.setChildPrice(DoubleUtils.add(goodsPriceSegment.getChildPrice(), merchantInv.getProfitPrice()));
				}
			} else {
				goods.setBasePrice(DoubleUtils.add(goods.getBasePrice(), goods.getBasePrice() * merchantInv.getProfitRate()));
				goods.setBasePriceChild(DoubleUtils.add(goods.getBasePriceChild(), goods.getBasePrice() * merchantInv.getProfitRate()));
				for (GoodsPriceSegment goodsPriceSegment : goods.getSegments()) {
					goodsPriceSegment.setAuditPrice(DoubleUtils.add(goodsPriceSegment.getAuditPrice(), goodsPriceSegment.getAuditPrice()
							* merchantInv.getProfitRate()));
					goodsPriceSegment.setChildPrice(DoubleUtils.add(goodsPriceSegment.getChildPrice(), goodsPriceSegment.getChildPrice()
							* merchantInv.getProfitRate()));
				}
			}
		}
	}

	@Override
	public ChannelOrder query(QueryOrderVo vo) throws ThaiException {
		Channel channel = channelDao.fetchByToken(vo.getToken());
		Asserts.notNull(channel, new ParameterException("TOKEN有误"));
		Asserts.isTrue(channel.isEnabled(), new ParameterException("TOKEN无效"));
		ChannelOrder order = channelOrderDao.fetchByNo(vo.getOrderNo());
		Asserts.notNull(order, new ParameterException("订单号有误"));
		return order;
	}

	@Override
	public boolean checkGoods(CheckGoodsVo vo) throws ThaiException {
		Channel channel = channelDao.fetchByToken(vo.getToken());
		Asserts.notNull(channel, new ParameterException("TOKEN有误"));
		Asserts.isTrue(channel.isEnabled(), new ParameterException("TOKEN无效"));
		Goods goods = goodsService.load(vo.getGoodsId());
		return goodsService.checkGoods(channel, goods, vo.deptDateObj, vo.getTravelerNum());
	}

	@Override
	@Transactional
	public void change(CreateOrderVo vo) throws ThaiException {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public void refund(RefundOrderVo vo) throws ThaiException {
		Channel channel = channelDao.fetchByToken(vo.getToken());
		Asserts.notNull(channel, new ParameterException("TOKEN有误"));
		Asserts.isTrue(channel.isEnabled(), new ParameterException("TOKEN无效"));

		// MerchantOrder order = orderDao.fetchByNo(vo.getOrderNo());

		ChannelOrder channelOrder = channelOrderDao.fetchByNo(vo.getOrderNo());
		Asserts.notNull(channelOrder, new ParameterException("订单号有误"));
		Asserts.isTrue(channelOrder.getChannelId() == channel.getId(), new OrderStatusException("没有权限操作此订单"));
		Asserts.isTrue(channelOrder.isCompleted(), new OrderStatusException("订单在" + channelOrder.getStatusDesc() + "状态下不能申请退单"));

		boolean changed = false;
		for (int i = vo.getGoodsVo().size() - 1; i >= 0; i--) {
			RefundGoodsVo refundGoods = vo.getGoodsVo().get(i);
			for (MerchantOrder merchantOrder : channelOrder.getMerchantOrders()) {
				boolean contains = false;
				String reason = "";
				for (MerchantOrderGoods merchantOrderGoods : merchantOrder.getGoodses()) {
					if (merchantOrderGoods.getGoodsId() == refundGoods.getGoodsId()) {
						// 一天内 一天内不许退单
						reason = "申请商品" + merchantOrderGoods.getGoodsName()
								+ (refundGoods.getType() == RefundGoodsVo.Type.REFUND ? "退款" : ("改期,时间改为" + refundGoods.getDeptDate()));
						if (CalendarUtils.isIn24hour(merchantOrderGoods.getDeptDate())) {
							throw new ParameterException("商品ID" + refundGoods.getGoodsId() + "出行时间在二十四小时内，不能退改");
						} else if (CalendarUtils.isInHalfMonth(merchantOrderGoods.getDeptDate())) {
							// 2-15天扣2%
							reason += ",2-15天扣2%手续费;";
						} else {
							reason += ";";
						}
						vo.getGoodsVo().remove(i);
						contains = true;
					}
				}

				if (contains) {
					// 清空最后操作者，列表页面客服可直接区分哪些订单没有处理
					merchantOrder.setLastOperatorDate(null);
					merchantOrder.setLastOperatorName(null);
					merchantOrder.setLastOperatorId(0);
					merchantOrder.setStatus(MerchantOrder.Status.NEW);// 恢复为待确认
					merchantOrderDao.update(merchantOrder);
					merchantOrderNotesDao
							.create(new MerchantOrderNotes(merchantOrder, channel, "退单申请：" + vo.getReason() + ",明细：" + reason));
					changed = true;
				}
			}
		}

		if (vo.getGoodsVo().size() > 0) {
			throw new ParameterException("商品ID" + vo.getGoodsVo().get(0).getGoodsId() + "不存在");
		}

		if (changed) {
			channelOrder.setStatus(ChannelOrder.Status.NEW);
			channelOrderDao.update(channelOrder);
		}

	}
}
