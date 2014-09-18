package com.magic.thai.db.service;

import java.util.List;

import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.Order;
import com.magic.thai.exception.ThaiException;
import com.magic.thai.web.ws.vo.CheckGoodsVo;
import com.magic.thai.web.ws.vo.CreateOrderVo;
import com.magic.thai.web.ws.vo.QueryGoodsesVo;
import com.magic.thai.web.ws.vo.QueryOrderVo;
import com.magic.thai.web.ws.vo.RefundOrderVo;

public interface InterfaceOrderService {

	/**
	 * 下单
	 * 
	 * @param orderId
	 * @param userprofile
	 * @return 返回订单号
	 */
	public Order create(CreateOrderVo vo) throws ThaiException;

	/**
	 * 校验库存量
	 * 
	 * @param orderId
	 * @param userprofile
	 * @return 返回订单号
	 */
	public boolean checkGoods(CheckGoodsVo vo) throws ThaiException;

	/**
	 * 查询订单
	 * 
	 * @param vo
	 * @return
	 * @throws ThaiException
	 */
	public Order query(QueryOrderVo vo) throws ThaiException;

	/**
	 * 查询商品列表
	 * 
	 * @param vo
	 * @return
	 * @throws ThaiException
	 */
	public List<Goods> queryGoodses(QueryGoodsesVo vo) throws ThaiException;

	/**
	 * 变更
	 * 
	 * @param orderId
	 * @param userprofile
	 */
	public void change(CreateOrderVo vo) throws ThaiException;

	/**
	 * 退款
	 * 
	 * @param order
	 * @param userprofile
	 */
	public void refund(RefundOrderVo vo) throws ThaiException;
}
