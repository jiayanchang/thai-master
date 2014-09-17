package com.magic.thai.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.magic.thai.db.domain.Order;

public class OrderNoGenerator {

	public static String no(Order order) {
		StringBuffer b = new StringBuffer();
		b.append(sub(order.getMerchantId() + "", 2));
		b.append(sub(order.getChannelId() + "", 2));
		b.append(DateFormatUtils.format(order.getCreatedDate(), "yyMMdd"));
		b.append(sub(order.getId() + "", 8));
		return b.toString();
	}

	private static String sub(String source, int size) {
		source = StringUtils.trimToEmpty(source);
		if (source.length() > size) {
			return source.substring(source.length() - size);
		} else if (source.length() < size) {
			StringBuffer b = new StringBuffer();
			for (int i = size - source.length(); i > 0; i--) {
				b.append("0");
			}
			b.append(source);
			return b.toString();
		} else {
			return source;
		}
	}

	public static void main(String[] args) {
		System.out.println(sub("11", 6));
		System.out.println(sub("121411", 3));
		System.out.println(sub("312331231", 1));
		System.out.println(sub("2", 3));
		System.out.println(sub("4", 0));
	}
}
