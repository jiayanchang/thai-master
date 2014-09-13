package com.magic.thai.db.dao;

import java.util.List;

import com.magic.thai.db.domain.Merchant;
import com.magic.thai.util.PaginationSupport;

public interface MerchantDao {

	public List<Merchant> list();

	public Merchant loadById(int id);

	public Integer create(Merchant entity);

	public void update(Merchant entity);

	/**
	 * 启用
	 * 
	 * @param merchant
	 * @param userprofile
	 */
	public void enable(Merchant merchant);

	/**
	 * 启用
	 * 
	 * @param merchantId
	 * @param userprofile
	 */
	public void enable(int merchantId);

	/**
	 * 禁用
	 * 
	 * @param merchant
	 * @param userprofile
	 */
	public void disable(Merchant merchant);

	/**
	 * 禁用
	 * 
	 * @param merchantId
	 * @param userprofile
	 */
	public void disable(int merchantId);

	public void delete(Merchant merchant);

	public void delete(int merchantId);

	public PaginationSupport getMerchantsPage(String name, int status, int currPage);

}
