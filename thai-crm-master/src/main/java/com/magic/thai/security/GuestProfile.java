package com.magic.thai.security;

import com.magic.thai.db.domain.Merchant;
import com.magic.thai.db.domain.User;

public class GuestProfile extends UserProfile {

	public GuestProfile(User user, Merchant merchant) {
		super(user, merchant);
	}

	@Override
	public boolean isAdministrator() {
		return false;
	}

	@Override
	public boolean isPlatformUser() {
		return false;
	}

	@Override
	public boolean isGuest() {
		return true;
	}

}
