package com.magic.thai.web;

public class DataVo {

	public static DataVo success(Object data) {
		DataVo vo = new DataVo();
		vo.data = data;
		vo.setMessage(null);
		vo.setSuccess(true);
		return vo;
	}

	public static DataVo fail(String msg) {
		DataVo vo = new DataVo();
		vo.setData(null);
		vo.setMessage(msg);
		vo.setSuccess(true);
		return vo;
	}

	private boolean success;
	private Object data;
	private String message;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
