package com.magic.thai.util;

import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

public class CalendarUtilsTest {

	@Test
	public void test() {

		try {
			Date before = DateUtils.parseDate("2014-05-10", new String[] { "yyyy-MM-dd" });
			Date after = DateUtils.parseDate("2014-05-20", new String[] { "yyyy-MM-dd" });
			Assert.assertFalse(CalendarUtils.large(before, after, 9));
			System.out.println(CalendarUtils.large(before, after, 10));
			Assert.assertTrue(CalendarUtils.large(before, after, 11));

		} catch (ParseException e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}

		Calendar bc = Calendar.getInstance();
		// bc.add(Calendar.HOUR_OF_DAY, 25);
		bc.add(Calendar.DAY_OF_MONTH, 16);
		System.out.println(bc.getTime());
		System.out.println(CalendarUtils.isIn24hour(bc.getTime()));
		System.out.println(CalendarUtils.isInHalfMonth(bc.getTime()));
	}

}
