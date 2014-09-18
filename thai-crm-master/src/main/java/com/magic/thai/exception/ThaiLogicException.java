package com.magic.thai.exception;

import com.magic.thai.exception.webservice.ErrorCode;

public class ThaiLogicException extends ThaiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8016233384226063523L;

	public ThaiLogicException() {
		// TODO Auto-generated constructor stub
	}

	public ThaiLogicException(String message) {
		super(message);
		this.code = ErrorCode.logic_exception;
	}

	public ThaiLogicException(ErrorCode code, String message) {
		super(message);
		this.code = code;
	}

}
