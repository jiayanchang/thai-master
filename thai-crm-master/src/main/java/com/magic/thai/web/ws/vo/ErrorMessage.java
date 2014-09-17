package com.magic.thai.web.ws.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
public class ErrorMessage {

	public ErrorMessage() {

	}

	public ErrorMessage(String code, String message) {
		this.code = code;
		this.message = message;
	}

	private String message;

	private String code;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
