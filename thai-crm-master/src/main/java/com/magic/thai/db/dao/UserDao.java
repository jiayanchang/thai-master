package com.magic.thai.db.dao;

import java.util.List;

import com.magic.thai.db.domain.User;
import com.magic.thai.db.vo.UserVo;
import com.magic.thai.util.PaginationSupport;

public interface UserDao {

	public List<User> list(UserVo vo);

	public User loadById(int id);

	public User getUserByLoginName(String loginName);

	public Integer create(User entity);

	public void update(User entity);

	/**
	 * 启用
	 * 
	 * @param User
	 * @param userprofile
	 */
	public void enable(User User);

	/**
	 * 启用
	 * 
	 * @param UserId
	 * @param userprofile
	 */
	public void enable(int UserId);

	/**
	 * 禁用
	 * 
	 * @param User
	 * @param userprofile
	 */
	public void disable(User User);

	/**
	 * 禁用
	 * 
	 * @param UserId
	 * @param userprofile
	 */
	public void disable(int UserId);

	public void delete(User User);

	public void delete(int UserId);

	public PaginationSupport getUsersPage(String name, String loginName, int status, int currPage, int merchantId);

}
