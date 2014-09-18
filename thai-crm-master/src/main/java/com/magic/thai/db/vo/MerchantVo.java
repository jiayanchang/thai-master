package com.magic.thai.db.vo;

public class MerchantVo {

	public MerchantVo() {
	}

	public MerchantVo(Integer[] statuses) {
		this.statuses = statuses;
	}

	public String nameKeyword;
	public int limitF4list = -1;
	public boolean containsPf4list = false;// 是否包含平台
	public Integer[] statuses;
}
