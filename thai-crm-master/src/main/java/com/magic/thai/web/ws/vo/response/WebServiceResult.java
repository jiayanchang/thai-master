package com.magic.thai.web.ws.vo.response;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.magic.thai.db.domain.ChannelOrderTraveler;
import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.GoodsDetails;
import com.magic.thai.db.domain.GoodsPriceSegment;
import com.magic.thai.db.domain.MerchantOrder;
import com.magic.thai.exception.ThaiException;

@XmlRootElement(name = "result")
@XmlSeeAlso({ ErrorMessage.class, MerchantOrder.class, ChannelOrderTraveler.class, GoodsListVo.class, Goods.class, ArrayList.class,
		GoodsDetails.class, GoodsPriceSegment.class, QueryOrderResult.class })
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
		setSuccess(false);
		return this;
	}

	public WebServiceResult success(Object data) {
		setData(data);
		setSuccess(true);
		return this;
	}
}
