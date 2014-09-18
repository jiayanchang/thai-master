package com.magic.thai.util;

import org.apache.commons.lang.StringUtils;

import com.magic.thai.exception.ThaiException;

public class Asserts {

	public static void notBlank(String obj, ThaiException e) throws ThaiException {
		isTrue(StringUtils.isNotBlank(obj), e);
	}

	public static void notNull(Object obj, ThaiException e) throws ThaiException {
		isTrue(obj != null, e);
	}

	public static void isFalse(boolean obj, ThaiException e) throws ThaiException {
		isTrue(!obj, e);
	}

	public static void isTrue(boolean obj, ThaiException e) throws ThaiException {
		if (!obj) {
			throw e;
		}
	}
}
