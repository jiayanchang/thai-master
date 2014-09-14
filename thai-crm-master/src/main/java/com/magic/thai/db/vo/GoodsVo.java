package com.magic.thai.db.vo;

public class GoodsVo {

	public GoodsVo() {
	}

	public GoodsVo(Integer[] statuses) {
		this.statuses = statuses;
	}

	public String titleKeyword;
	public int limitF4list = -1;
	public Integer[] statuses;
}
