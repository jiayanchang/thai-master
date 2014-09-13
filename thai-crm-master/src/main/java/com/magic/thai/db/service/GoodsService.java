package com.magic.thai.db.service;

import com.magic.thai.db.domain.Goods;
import com.magic.thai.exception.GoodsStatusException;
import com.magic.thai.security.UserProfile;
import com.magic.thai.util.PaginationSupport;

public interface GoodsService {

	public Goods load(int id);

	public Goods fetch(int id);

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
	public void pass(int goodsId, UserProfile userprofile) throws GoodsStatusException;

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
	public void cancel(Goods goods, String reason, UserProfile userprofile) throws GoodsStatusException;

	public void delete(int goodsId, UserProfile userprofile) throws GoodsStatusException;

	public void update(Goods goodsbean, UserProfile userprofile) throws GoodsStatusException;

	public int create(Goods goods, UserProfile userprofile);

	public PaginationSupport getGoodsesPage(String title, String dept, String arr, int status, int queryPage, int merchantId);

	public PaginationSupport getGoodsesPage(String title, String dept, String arr, int status, int queryPage);

}
