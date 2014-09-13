package com.magic.thai.db.service.impl;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.magic.thai.db.domain.Merchant;
import com.magic.thai.db.domain.User;
import com.magic.thai.db.service.MerchantService;
import com.magic.thai.security.UserProfile;

public class MerchantServiceImplTest extends BaseTest {

	@Autowired
	MerchantService merchantService;

	static UserProfile userprofile;
	static User user = new User();
	static {
		user.setCodeName("testu");
		userprofile = new UserProfile(user);
	}

	@Test
	public void test() {

		Merchant merchant = new Merchant();
		merchant.setCodeName("test");
		merchant.setName("测试");
		merchant.setTel("1265478965");
		int id = merchantService.create(merchant, userprofile);
		merchantService.disable(id, userprofile);

		merchant = merchantService.load(id);
		Assert.assertTrue(merchant.getStatus() == Merchant.Status.DISABLED);

		// fail("Not yet implemented");
	}

}
