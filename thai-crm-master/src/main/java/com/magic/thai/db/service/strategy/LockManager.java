package com.magic.thai.db.service.strategy;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

import com.magic.thai.exception.ThaiException;
import com.magic.thai.exception.ThaiLogicException;
import com.magic.thai.security.UserProfile;
import com.magic.thai.util.Asserts;

public class LockManager {

	/**
	 * key=orderNo, value=-userid-username-datetime
	 */
	private static ConcurrentHashMap<String, String> orderLocks = new ConcurrentHashMap<String, String>();

	static final int watting_minute = 5 * 60 * 1000;

	public static void lock(String orderNo, UserProfile userprofile) throws ThaiException {
		Asserts.notBlank(orderNo, new ThaiLogicException("订单号为空"));
		synchronized (orderLocks) {
			if (hasLock(orderNo)) {
				if (isOwnLock(orderNo, userprofile)) {
					setLock(orderNo, userprofile);
				} else if (isInvalidLock(orderNo)) {
					setLock(orderNo, userprofile);
				} else {
					throw new ThaiLogicException("当前订单锁定失败");
				}
			} else {
				setLock(orderNo, userprofile);
			}
		}
	}

	public static String getLock(String orderNo) throws ThaiException {
		String value = orderLocks.get(orderNo);
		Asserts.notBlank(value, new ThaiLogicException("LOCK的值为空"));
		String[] varr = value.split("-");
		return varr[2] + "在" + varr[3] + "已经开始处理了";
	}

	private static void setLock(String orderNo, UserProfile userprofile) {
		orderLocks.put(
				orderNo,
				"-" + userprofile.getUser().getId() + "-" + userprofile.getUser().getName() + "-"
						+ DateFormatUtils.format(new Date(), "yyyy/MM/dd hh:mm:ss") + "-");
	}

	public static void unlock(String orderNo) throws ThaiException {
		orderLocks.remove(orderNo);
	}

	public static boolean hasLock(String orderNo) {
		return orderLocks.containsKey(orderNo);
	}

	public static boolean isOwnLock(String orderNo, UserProfile userprofile) throws ThaiException {
		String value = orderLocks.get(orderNo);
		Asserts.notBlank(value, new ThaiLogicException("LOCK的值为空"));
		return value.indexOf("-" + userprofile.getUser().getId() + "-") == 0;
	}

	public static boolean isInvalidLock(String orderNo) throws ThaiException {
		String value = orderLocks.get(orderNo);
		Asserts.notBlank(value, new ThaiLogicException("LOCK的值为空"));
		String dstr = value.split("-")[3];
		Date date = null;
		try {
			DateUtils.parseDate(dstr, new String[] { "yyyy/MM/dd hh:mm:ss" });
		} catch (DateParseException e) {
			e.printStackTrace();
			return true;
		}

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, watting_minute);

		return c.getTime().after(new Date());
	}

	public static void main(String[] args) {
		String[] s = "-userid-username-date".split("-");
		for (String string : s) {
			System.out.println(string);
		}
	}
}
