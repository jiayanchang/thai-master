package com.magic.thai.db.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.magic.thai.db.dao.UserDao;
import com.magic.thai.db.domain.Merchant;
import com.magic.thai.db.domain.User;
import com.magic.thai.db.service.UserService;
import com.magic.thai.db.vo.UserVo;
import com.magic.thai.exception.LoginException;
import com.magic.thai.security.UserProfile;
import com.magic.thai.util.Md5CryptoUtils;
import com.magic.thai.util.PaginationSupport;

@Service("userService")
@Transactional
public class UserServiceImpl extends ServiceHelperImpl<User> implements UserService {

	@Autowired
	private UserDao userDao;

	public User findUserbyId(int id) {
		return super.find(User.class, id);
	}

	@Override
	public User findByLoginName(String loginName) {
		return userDao.getUserByLoginName(loginName);
	}

	@Override
	public UserProfile login(String username, String password) throws LoginException {
		User user = userDao.getUserByLoginName(username);
		Assert.notNull(user, "用户不存在");
		Assert.isTrue(user.getPassword().equals(Md5CryptoUtils.create(password)), "用户名或密码错误");
		return new UserProfile(user);
	}

	@Override
	public int create(User user, UserProfile userprofile) {
		user.setMerchantId(userprofile.getUser().getMerchantId());
		if (StringUtils.isBlank(user.getCodeName())) {
			user.setCodeName(user.getLoginName());
		}
		user.setCreatedDate(new Date());
		user.setCreatorId(userprofile.getUser().getId());
		user.setCreatorName(userprofile.getUser().getCodeName());
		user.setPassword(Md5CryptoUtils.create(user.getPassword()));
		return userDao.create(user);
	}

	@Override
	public int create(User user, Merchant merchant, UserProfile userprofile) {
		user.setMerchantId(merchant.getId());
		if (StringUtils.isBlank(user.getCodeName())) {
			user.setCodeName(user.getLoginName());
		}
		user.setCreatedDate(new Date());
		user.setCreatorId(userprofile.getUser().getId());
		user.setCreatorName(userprofile.getUser().getCodeName());
		user.setPassword(Md5CryptoUtils.create(user.getPassword()));
		return userDao.create(user);
	}

	@Override
	public void update(User user, UserProfile userprofile) {
		user.setPassword(Md5CryptoUtils.create(user.getPassword()));
		userDao.update(user);
	}

	@Override
	public void delete(User user, UserProfile userprofile) {
		userDao.delete(user.getId());
	}

	@Override
	public void delete(int userId, UserProfile userprofile) {
		userDao.delete(userId);
	}

	@Override
	public PaginationSupport getUsersPage(String name, String loginName, int status, int queryPage, UserProfile userprofile) {
		return userDao.getUsersPage(name, loginName, status, queryPage, userprofile.getUser().getMerchantId());
	}

	@Override
	public List<User> list(UserVo vo) {
		return userDao.list(vo);
	}

}
