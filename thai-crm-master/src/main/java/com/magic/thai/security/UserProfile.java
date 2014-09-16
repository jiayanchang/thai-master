package com.magic.thai.security;

import com.magic.thai.db.domain.Merchant;
import com.magic.thai.db.domain.User;

/**
 * user装饰器， session存储
 * 
 * @author yanchang
 *
 */
public class UserProfile {

	public UserProfile(User user, Merchant merchant) {
		this.user = user;
		this.merchant = merchant;
	}

	private User user;

	public User getUser() {
		return user;
	}

	private Merchant merchant;

	public Merchant getMerchant() {
		return merchant;
	}

	public boolean isPlatformUser() {
		return user.isPlatformUser();
	}

}
