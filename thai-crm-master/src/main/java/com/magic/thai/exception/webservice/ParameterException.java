package com.magic.thai.exception.webservice;

import com.magic.thai.exception.ThaiException;

public class ParameterException extends ThaiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3534661606644604070L;

	public ParameterException(String message) {
		super(ErrorCode.parameter_exception, message);
	}
}
