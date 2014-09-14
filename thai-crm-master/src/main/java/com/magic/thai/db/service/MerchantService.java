package com.magic.thai.db.service;

import java.util.List;

import com.magic.thai.db.domain.Merchant;
import com.magic.thai.db.domain.User;
import com.magic.thai.db.vo.MerchantVo;
import com.magic.thai.security.UserProfile;
import com.magic.thai.util.PaginationSupport;

public interface MerchantService {

	public List<Merchant> list(MerchantVo vo);

	public Merchant load(int id);

	public Merchant fetch(int id);

	/**
	 * 启用
	 * 
	 * @param merchant
	 * @param userprofile
	 */
	public void enable(Merchant merchant, UserProfile userprofile);

	/**
	 * 启用
	 * 
	 * @param merchantId
	 * @param userprofile
	 */
	public void enable(int merchantId, UserProfile userprofile);

	/**
	 * 禁用
	 * 
	 * @param merchant
	 * @param userprofile
	 */
	public void disable(Merchant merchant, UserProfile userprofile);

	/**
	 * 禁用
	 * 
	 * @param merchantId
	 * @param userprofile
	 */
	public void disable(int merchantId, UserProfile userprofile);

	public void delete(Merchant merchant, UserProfile userprofile);

	public void delete(int merchantId, UserProfile userprofile);

	public void update(Merchant merchant, UserProfile userprofile);

	public int create(Merchant merchant, User admin, UserProfile userprofile);

	/**
	 * 获取商家列表
	 * 
	 * @param name
	 * @param status
	 * @param queryPage
	 *            需要查询的页数，每页30条
	 * @return
	 */
	public PaginationSupport getMerchantsPage(String name, int status, int queryPage);

}
