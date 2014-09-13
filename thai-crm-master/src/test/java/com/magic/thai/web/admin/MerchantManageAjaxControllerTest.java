package com.magic.thai.web.admin;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class MerchantManageAjaxControllerTest {

	@Test
	public void test() {
		try {
			Document doc = Jsoup.connect("http://localhost:8080/mchts/listData").timeout(300000).post();
			System.out.println(doc.text());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

}
