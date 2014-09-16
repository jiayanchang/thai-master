package com.magic.thai.db.service;

import java.util.Date;
import java.util.List;

import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.GoodsDetails;
import com.magic.thai.db.domain.User;
import com.magic.thai.db.vo.GoodsVo;
import com.magic.thai.exception.GoodsStatusException;
import com.magic.thai.exception.NoPermissionsException;
import com.magic.thai.exception.ThaiException;
import com.magic.thai.security.UserProfile;
import com.magic.thai.util.PaginationSupport;

public interface GoodsService {

	public Goods load(int id);

	public Goods fetch(int id);

	public List<Goods> fetchList(String channelToken);

	public List<Goods> list(GoodsVo vo);

	/**
	 * 商家提交上架
	 * 
	 * @param goods
	 * @param userprofile
	 */
	public void submit(Goods goods, UserProfile userprofile) throws GoodsStatusException;

	/**
	 * 生效
	 * 
	 * @param goodsId
	 * @param userprofile
	 */
	public void pass(int goodsId, UserProfile userprofile) throws NoPermissionsException, GoodsStatusException;

	/**
	 * 驳回
	 * 
	 * @param goodsId
	 * @param userprofile
	 */
	public void reject(int goodsId, String reason, UserProfile userprofile) throws GoodsStatusException;

	/**
	 * 商家申请下架
	 * 
	 * @param goods
	 * @param userprofile
	 */
	public void cancel(int goodsId, String reason, UserProfile userprofile) throws GoodsStatusException;

	public void delete(int goodsId, UserProfile userprofile) throws GoodsStatusException;

	public void update(Goods goodsbean, UserProfile userprofile) throws GoodsStatusException;

	public void update(GoodsDetails goodsDetails, UserProfile userprofile);

	public int create(Goods goods, UserProfile userprofile);

	public PaginationSupport getGoodsesPage(String title, String dept, String arr, Integer[] statuses, int queryPage, int merchantId);

	public PaginationSupport getGoodsesPage(String title, String dept, String arr, Integer[] statuses, int queryPage);

	/**
	 * 检测商品库存， 通过通道规则的设置校验库存量
	 * 
	 * @param channelToken
	 * @param goodsId 商品id(root id)
	 * @param deptDate 出发时间
	 * @return
	 */
	public boolean checkGoods(String channelToken, int goodsId, Date deptDate, int count) throws ThaiException;

	public int getAuditingGoodsCount(User user);

}
