package com.magic.thai.web.ws.vo;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.magic.thai.db.domain.Order;
import com.magic.thai.db.domain.OrderTraveler;
import com.magic.thai.exception.ThaiException;

@XmlRootElement(name = "result")
@XmlSeeAlso({ ErrorMessage.class, Order.class, OrderTraveler.class })
public class WebServiceResult {

	private boolean success;

	private Object data;

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

	public WebServiceResult fail(ThaiException e) {
		setData(new ErrorMessage(e.getErrorCode().name(), e.getMessage()));
		setSuccess(true);
		return this;
	}

	public WebServiceResult success(Object data) {
		setData(data);
		setSuccess(true);
		return this;
	}
}
