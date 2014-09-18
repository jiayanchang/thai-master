package com.magic.thai.web.json;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.magic.thai.db.service.MerchantService;
import com.magic.thai.db.service.impl.BaseTest;

public class JsonControllerTest extends BaseTest {

	@Autowired
	MerchantService merchantService;

	@Autowired
	JsonController jsonController;

	@Test
	public void testgetmerchants() {
		System.out.println(jsonController.getmerchants("jia", null));
	}

	@Test
	public void testgetgoodses() {
		System.out.println(jsonController.getgoodses("q", null));
	}

	@Test
	public void testgethotelss() {
		System.out.println(jsonController.gethotels("q", null));
	}

}
