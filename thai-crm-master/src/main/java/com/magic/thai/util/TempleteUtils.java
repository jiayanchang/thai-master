package com.magic.thai.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang.time.DateFormatUtils;

import com.magic.thai.db.domain.MerchantOrder;
import com.magic.thai.db.domain.MerchantOrderGoods;
import com.magic.thai.db.domain.MerchantOrderGoodsPickup;

public class TempleteUtils {

	private static Properties properties;

	static {
		InputStream inputStream = MailUtils.class.getClassLoader().getResourceAsStream("system.properties");
		if (inputStream == null) {
			throw new RuntimeException("can not read  file system.properties");
		}
		try {
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	static String FISRT_TEMPLATE = "";
	static String COMPLETE_TEMPLATE = "";

	static {
		String fp = TempleteUtils.class.getClassLoader().getResource("").getPath() + "template/first.html";
		String cp = TempleteUtils.class.getClassLoader().getResource("").getPath() + "template/complete.html";

		FISRT_TEMPLATE = getContent(fp);
		COMPLETE_TEMPLATE = getContent(cp);
	}

	private static String getContent(String fp) {
		try {
			/* 读入TXT文件 */
			File filename = new File(fp); // 要读取以上路径的input。txt文件
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String line = "";
			StringBuffer content = new StringBuffer();
			while ((line = br.readLine()) != null) {
				content.append(line);
			}
			return content.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String genFirstContent(MerchantOrder mo) {

		String host = properties.getProperty("first.host");
		// 您的订单需要完善，order No
		String html = FISRT_TEMPLATE
				.replaceAll("\\{traveler_name\\}", mo.getContractor())
				.replaceAll(
						"\\{gotourl\\}",
						host + "/g/order/edit/" + mo.getId() + "?t="
								+ Md5CryptoUtils.create(mo.getContractorEmail() + mo.getOrderNo()).substring(0, 8))
				.replaceAll("\\{order_no\\}", mo.getOrderNo()).replaceAll("\\{traveler_tel\\}", mo.getContractorMobile())
				.replaceAll("\\{goods_title\\}", mo.getGoodses().get(0).getGoodsName())
				.replaceAll("\\{goods_count\\}", mo.getGoodses().get(0).getQuantity() + "");
		return html;
	}

	public static String genCompleteContent(MerchantOrder mo, MerchantOrderGoodsPickup pickup) {

		MerchantOrderGoods mog = mo.getGoodses().get(0);

		String deptDateStr = DateFormatUtils.format(mog.getDeptDate(), "yyyy-MM-dd");
		String host = properties.getProperty("complete.host");
		// 您的订单需要完善，order No
		String html = COMPLETE_TEMPLATE
				.replaceAll("\\{traveler_name\\}", mo.getContractor())
				.replaceAll(
						"\\{gotourl\\}",
						host + "/g/order/" + mo.getId() + "?t="
								+ Md5CryptoUtils.create(mo.getContractorEmail() + mo.getOrderNo()).substring(0, 8))
				.replaceAll("\\{order_no\\}", mo.getOrderNo()).replaceAll("\\{traveler_tel\\}", mo.getContractorMobile())
				.replaceAll("\\{goods_title\\}", mog.getGoodsName()).replaceAll("\\{goods_count\\}", mog.getQuantity() + "")
				.replaceAll("\\{dept_date\\}", deptDateStr).replaceAll("\\{pickup.flightNo\\}", pickup == null ? "" : pickup.getFlightNo())
				.replaceAll("\\{pickup.arrivedDate\\}", pickup == null ? "" : pickup.getArrivedDate())
				.replaceAll("\\{pickup.arrivedTime\\}", pickup == null ? "" : pickup.getArrivedTime());
		return html;
	}

	public static void main(String[] args) {
		System.out.println(FISRT_TEMPLATE);
		System.out.println(COMPLETE_TEMPLATE);
	}
}
