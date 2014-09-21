package com.magic.thai.db.dao;

import java.util.List;

import com.magic.thai.db.domain.MerchantOrder;
import com.magic.thai.db.domain.MerchantOrderNotes;

public interface MerchantOrderNotesDao {

	public Integer create(MerchantOrderNotes entity);

	public List<MerchantOrderNotes> getLogs(int orderId);

	public List<MerchantOrderNotes> getLogs(MerchantOrder order);
}
