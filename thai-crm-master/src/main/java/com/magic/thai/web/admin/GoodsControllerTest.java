package com.magic.thai.web.admin;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class GoodsControllerTest {

	@Test
	public void test() {
		try {
			Document doc = Jsoup.connect("http://localhost:8080/crm/a/goods/8.json")
					.data("testmode", "true", "username", "admin", "password", "1111").post();
			System.out.println(doc.text());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
