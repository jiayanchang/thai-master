package com.magic.thai.security;

import com.magic.thai.db.domain.User;

/**
 * user装饰器， session存储
 * 
 * @author yanchang
 *
 */
public class UserProfile {

	public UserProfile(User user) {
		this.user = user;
	}

	private User user;

	public User getUser() {
		return user;
	}

	public boolean isPlatformUser() {
		return user.getType() == User.Type.PLATFORM;
	}

}
