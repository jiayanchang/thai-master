package com.magic.thai.db.service;

import com.magic.thai.db.domain.User;
import com.magic.thai.exception.LoginException;
import com.magic.thai.security.UserProfile;
import com.magic.thai.util.PaginationSupport;

public interface UserService {

	User findUserbyId(int id);

	int create(User user, UserProfile userprofile);

	void update(User user, UserProfile userprofile);

	void delete(User user, UserProfile userprofile);

	void delete(int userId, UserProfile userprofile);

	UserProfile login(String username, String password) throws LoginException;

	/**
	 * 获取商家列表
	 * 
	 * @param name
	 * @param status
	 * @param queryPage
	 *            需要查询的页数，每页30条
	 * @return
	 */
	public PaginationSupport getUsersPage(String name, String loginName, int status, int queryPage);
}
