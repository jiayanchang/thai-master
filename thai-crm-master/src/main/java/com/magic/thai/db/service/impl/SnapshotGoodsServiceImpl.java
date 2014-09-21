package com.magic.thai.db.service.impl;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magic.thai.db.dao.GoodsDao;
import com.magic.thai.db.dao.SnapshotGoodsDao;
import com.magic.thai.db.dao.SnapshotGoodsDetailsDao;
import com.magic.thai.db.dao.SnapshotGoodsPriceSegmentDao;
import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.GoodsPriceSegment;
import com.magic.thai.db.domain.MerchantOrderGoods;
import com.magic.thai.db.domain.SnapshotGoods;
import com.magic.thai.db.domain.SnapshotGoodsDetails;
import com.magic.thai.db.domain.SnapshotGoodsPriceSegment;
import com.magic.thai.db.service.SnapshotGoodsService;

@Service("snapshotGoodsService")
@Transactional
public class SnapshotGoodsServiceImpl implements SnapshotGoodsService {

	private static Logger logger = LoggerFactory.getLogger(SnapshotGoodsServiceImpl.class);

	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private SnapshotGoodsDao snapshotGoodsDao;
	@Autowired
	private SnapshotGoodsPriceSegmentDao snapshotGoodsPriceSegmentDao;
	@Autowired
	private SnapshotGoodsDetailsDao snapshotGoodsDetailsDao;

	@Override
	public SnapshotGoods fetch(int id) {
		SnapshotGoods snapshotGoods = snapshotGoodsDao.loadById(id);
		Hibernate.initialize(snapshotGoods.getSegments());
		Hibernate.initialize(snapshotGoods.getDetails());
		return snapshotGoods;
	}

	@Override
	public SnapshotGoods fetchByMogId(int id) {
		SnapshotGoods snapshotGoods = snapshotGoodsDao.loadByMerchanOrderGoodsId(id);
		Hibernate.initialize(snapshotGoods.getSegments());
		Hibernate.initialize(snapshotGoods.getDetails());
		return snapshotGoods;
	}

	@Override
	public void create(MerchantOrderGoods merchantOrderGoods) {

		Goods goods = goodsDao.loadById(merchantOrderGoods.getGoodsId());

		SnapshotGoods snapshotGoods = new SnapshotGoods();
		snapshotGoods.setDetails(new SnapshotGoodsDetails());
		snapshotGoods.setTitle(goods.getTitle());
		snapshotGoods.setDept(goods.getDept());
		snapshotGoods.setArrived(goods.getArrived());
		snapshotGoods.setTravelDays(goods.getTravelDays());
		snapshotGoods.setGoodsCount(goods.getGoodsCount());
		snapshotGoods.setSummary(goods.getSummary());
		snapshotGoods.setChannelId(merchantOrderGoods.getChannelId());

		snapshotGoods.setMerchantOrderGoodsId(merchantOrderGoods.getId());

		snapshotGoods.setGoodsId(goods.getId());
		snapshotGoods.setMerchantId(goods.getMerchantId());

		snapshotGoods.getDetails().setTravelPlan(goods.getDetails().getTravelPlan());
		snapshotGoods.getDetails().setCostDesc(goods.getDetails().getCostDesc());
		snapshotGoods.getDetails().setBookNotes(goods.getDetails().getBookNotes());
		snapshotGoods.getDetails().setNotes(goods.getDetails().getNotes());

		snapshotGoods.getDetails().setPicPath(goods.getDetails().getPicPath());
		snapshotGoods.getDetails().setLinePicPathA(goods.getDetails().getLinePicPathA());
		snapshotGoods.getDetails().setLinePicPathB(goods.getDetails().getLinePicPathB());
		snapshotGoods.getDetails().setLinePicPathC(goods.getDetails().getLinePicPathC());
		snapshotGoods.getDetails().setLinePicPathD(goods.getDetails().getLinePicPathD());
		snapshotGoods.getDetails().setLinePicPathE(goods.getDetails().getLinePicPathE());
		snapshotGoods.getDetails().setLinePicPathF(goods.getDetails().getLinePicPathF());

		int id = snapshotGoodsDao.create(snapshotGoods);

		snapshotGoods.getDetails().setGoods(snapshotGoods);
		snapshotGoodsDetailsDao.create(snapshotGoods.getDetails());

		for (GoodsPriceSegment segment : goods.getSegments()) {
			SnapshotGoodsPriceSegment snapshotSegment = new SnapshotGoodsPriceSegment();
			snapshotSegment.setGoodsId(segment.getGoods().getId());
			snapshotSegment.setAuditPrice(segment.getAuditPrice());
			snapshotSegment.setChildPrice(segment.getChildPrice());
			snapshotSegment.setStartDate(segment.getStartDate());
			snapshotSegment.setEndDate(segment.getEndDate());
			snapshotSegment.setSnapshotGoods(snapshotGoods);
			snapshotGoodsPriceSegmentDao.create(snapshotSegment);
		}
	}
}
