package com.magic.thai.db.vo;

import java.util.Date;

public class OrderVo {

	public OrderVo() {
	}

	public OrderVo(Integer[] statuses) {
		this.statuses = statuses;
	}

	public Integer channelId;
	public String orderNo;
	public Date startDate;
	public Date endDate;
	public String dept;
	public String arr;
	public int limitF4list = -1;
	public Integer[] statuses;
}
