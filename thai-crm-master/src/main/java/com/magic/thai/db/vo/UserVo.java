package com.magic.thai.db.vo;

public class UserVo {

	public UserVo() {
	}

	public UserVo(Integer[] statuses) {
		this(statuses, 0);
	}

	public UserVo(Integer[] statuses, int merchantId) {
		this.statuses = statuses;
		this.merchantId = merchantId;
	}

	public String nameKeyword;
	public int limitF4list = -1;
	public Integer[] statuses;
	public boolean containsPf4list;
	public int merchantId = 0;
}
