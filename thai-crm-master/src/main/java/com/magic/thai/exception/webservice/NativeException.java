package com.magic.thai.exception.webservice;

import com.magic.thai.exception.ThaiException;

public class NativeException extends ThaiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3534661606644604070L;

	public NativeException(String message) {
		super(ErrorCode.native_exception, message);
	}
}
