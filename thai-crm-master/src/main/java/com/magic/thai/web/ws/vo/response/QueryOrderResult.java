package com.magic.thai.web.ws.vo.response;

import java.util.HashMap;
import java.util.Map;

public class QueryOrderResult {

	private boolean completed;

	private Map<Integer, String> goodsStatuses = new HashMap<Integer, String>();

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public Map<Integer, String> getGoodsStatuses() {
		return goodsStatuses;
	}

	public void setGoodsStatuses(Map<Integer, String> goodsStatuses) {
		this.goodsStatuses = goodsStatuses;
	}

}
