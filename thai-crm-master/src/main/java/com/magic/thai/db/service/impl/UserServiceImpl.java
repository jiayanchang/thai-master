package com.magic.thai.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.magic.thai.db.dao.UserDao;
import com.magic.thai.db.domain.User;
import com.magic.thai.db.service.ServiceHelperImpl;
import com.magic.thai.db.service.UserService;
import com.magic.thai.exception.LoginException;
import com.magic.thai.security.UserProfile;
import com.magic.thai.util.Md5CryptoUtils;

@Service("userService")
@Transactional
public class UserServiceImpl extends ServiceHelperImpl<User> implements UserService {

	@Autowired
	private UserDao userDao;

	public User findUserbyId(int id) {
		return super.find(User.class, id);
	}

	@Override
	public UserProfile login(String username, String password) throws LoginException {
		User user = userDao.getUserByLoginName(username);
		Assert.notNull(user, "用户不存在");
		Assert.isTrue(password.equals(Md5CryptoUtils.create(password)), "用户名或密码错误");
		return new UserProfile(user);
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(User user, UserProfile userprofile) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(User user, UserProfile userprofile) {
		// TODO Auto-generated method stub

	}

}
