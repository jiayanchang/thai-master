package com.magic.thai.exception;

import com.magic.thai.exception.webservice.ErrorCode;

public class ThaiException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8016233384226063523L;

	public ThaiException() {
		// TODO Auto-generated constructor stub
	}

	public ThaiException(String message) {
		super(message);
	}

	public ThaiException(ErrorCode code, String message) {
		super(message);
		this.code = code;
	}

	protected ErrorCode code = ErrorCode.exception;

	public ErrorCode getErrorCode() {
		return code;
	}
}
