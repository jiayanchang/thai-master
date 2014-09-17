package com.magic.thai.db.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magic.thai.db.dao.ChannelDao;
import com.magic.thai.db.dao.GoodsDao;
import com.magic.thai.db.dao.OrderDao;
import com.magic.thai.db.dao.OrderTravelerDao;
import com.magic.thai.db.domain.Channel;
import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.Order;
import com.magic.thai.db.domain.OrderTraveler;
import com.magic.thai.db.service.GoodsService;
import com.magic.thai.db.service.InterfaceOrderService;
import com.magic.thai.db.service.OrderService;
import com.magic.thai.db.service.strategy.GoodsPriceCalculator;
import com.magic.thai.exception.GoodsCheckedException;
import com.magic.thai.exception.OrderStatusException;
import com.magic.thai.exception.ThaiException;
import com.magic.thai.exception.webservice.ParameterException;
import com.magic.thai.util.Asserts;
import com.magic.thai.util.CalendarUtils;
import com.magic.thai.util.OrderNoGenerator;
import com.magic.thai.web.ws.vo.CheckGoodsVo;
import com.magic.thai.web.ws.vo.CreateOrderVo;
import com.magic.thai.web.ws.vo.QueryOrderVo;
import com.magic.thai.web.ws.vo.RefundOrderVo;
import com.magic.thai.web.ws.vo.TravelerVo;

@Service("interfaceOrderService")
@Transactional
public class IntefaceOrderServiceImpl extends ServiceHelperImpl<Order> implements InterfaceOrderService {

	@Autowired
	private OrderDao orderDao;
	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private OrderTravelerDao orderTravelerDao;
	@Autowired
	private ChannelDao channelDao;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private GoodsPriceCalculator goodsPriceCalculator;

	@Override
	@Transactional
	public Order create(CreateOrderVo vo) throws ThaiException {
		Channel channel = channelDao.fetchByToken(vo.getToken());
		Goods goods = goodsService.load(vo.getGoodsId());
		Asserts.isTrue(goodsService.checkGoods(channel, goods, vo.deptDateObj, vo.getTravelers().size()), new GoodsCheckedException(
				"商品数量不足"));

		// 没超出20天的商品已售叠加
		if (!CalendarUtils.large(new Date(), vo.deptDateObj, 20)) {
			goods.setSoldCount(goods.getSoldCount() + vo.getTravelers().size());
			goodsDao.update(goods);
		}

		Order order = new Order();
		order.setChannelId(channel.getId());
		order.setChannelName(channel.getName());
		order.setGoodsId(goods.getId());
		order.setGoodsName(goods.getTitle());

		order.setContractor(vo.getOrderContactor());
		order.setContractorMobile(vo.getOrderContactorMobile());
		// order.setContractorTel();
		order.setCreatedDate(new Date());
		// order.setCreatorId(creatorId);
		// order.setCreatorName(creatorName);

		order.setDeptDate(vo.deptDateObj);

		// order.setLastOperatorDate(lastOperatorDate);
		// order.setLastOperatorId(lastOperatorId);
		// order.setLastOperatorName(lastOperatorName);
		order.setMerchantId(goods.getMerchantId());
		order.setMerchantName(goods.getMerchantName());
		// order.setStatus(status);
		order.setTravelerNum(vo.getTravelers().size());

		double sum = 0d;
		for (TravelerVo travelerVo : vo.getTravelers()) {
			OrderTraveler orderTraveler = new OrderTraveler();

			orderTraveler.setBirth(travelerVo.getBirth());
			orderTraveler.setEffectiveDate(travelerVo.getEffectiveDate());
			orderTraveler.setGender(travelerVo.getGender());
			// orderTraveler.setId(travelerVo.get);
			orderTraveler.setIdNo(travelerVo.getIdNo());
			orderTraveler.setIdType(travelerVo.getIdType());
			orderTraveler.setMobile(travelerVo.getMobile());
			orderTraveler.setName(travelerVo.getName());
			orderTraveler.setType(travelerVo.getType());
			orderTraveler.setPrice(goodsPriceCalculator.price(goods, orderTraveler));
			order.getTravelers().add(orderTraveler);
			sum += orderTraveler.getPrice();
		}
		order.setTotalPrice(sum);
		orderDao.create(order);
		for (OrderTraveler t : order.getTravelers()) {
			orderTravelerDao.create(t);
		}

		// 生成单号
		order.setOrderNo(OrderNoGenerator.no(order));
		orderDao.update(order);

		// 更新渠道信息
		channel.setOrderCount(channel.getOrderCount() + 1);
		channel.setAmount(order.getTotalPrice() + channel.getAmount());
		channel.setGoodsCount(channel.getGoodsCount() + order.getTravelerNum());
		channelDao.update(channel);

		return order;
	}

	@Override
	public Order query(QueryOrderVo vo) throws ThaiException {
		Channel channel = channelDao.fetchByToken(vo.getToken());
		Asserts.notNull(channel, new ParameterException("TOKEN有误"));
		Order order = orderDao.fetchByNo(vo.getOrderNo());
		Asserts.notNull(order, new ParameterException("订单号有误"));
		return order;
	}

	@Override
	public boolean checkGoods(CheckGoodsVo vo) throws ThaiException {
		Channel channel = channelDao.fetchByToken(vo.getToken());
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
		Order order = orderDao.fetchByNo(vo.getOrderNo());
		Asserts.notNull(order, new ParameterException("订单号有误"));
		Asserts.isTrue(order.isCompleted(), new OrderStatusException("订单在" + order.getStatusDesc() + "状态下不能申请退单"));

		order.setStatus(Order.Status.NEW);// 恢复为待确认
		orderDao.update(order);

	}
}
