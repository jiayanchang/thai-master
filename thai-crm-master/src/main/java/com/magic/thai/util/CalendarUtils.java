package com.magic.thai.util;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {

	public static boolean large(Date before, Date after, int days) {
		Calendar bc = Calendar.getInstance();
		bc.setTime(before);
		bc.set(Calendar.HOUR_OF_DAY, 0);
		bc.set(Calendar.MINUTE, 0);
		bc.set(Calendar.SECOND, 0);

		Calendar ac = Calendar.getInstance();
		ac.setTime(after);
		ac.set(Calendar.HOUR_OF_DAY, 0);
		ac.set(Calendar.MINUTE, 0);
		ac.set(Calendar.SECOND, 0);

		bc.add(Calendar.DAY_OF_MONTH, days);

		return bc.before(ac);
	}

	public static Date lastOfDay(Date before) {
		Calendar bc = Calendar.getInstance();
		bc.setTime(before);
		bc.set(Calendar.HOUR_OF_DAY, 23);
		bc.set(Calendar.MINUTE, 59);
		bc.set(Calendar.SECOND, 59);
		return bc.getTime();
	}

	public static Date firstOfDay(Date before) {
		Calendar bc = Calendar.getInstance();
		bc.setTime(before);
		bc.set(Calendar.HOUR_OF_DAY, 0);
		bc.set(Calendar.MINUTE, 0);
		bc.set(Calendar.SECOND, 0);
		return bc.getTime();
	}
}
