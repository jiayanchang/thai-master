package com.magic.thai.exception.webservice;

import com.magic.thai.exception.ThaiException;

public class FormatException extends ThaiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3534661606644604070L;

	public FormatException(String message) {
		super(ErrorCode.format_exception, message);
	}
}
