package com.magic.thai.db.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magic.thai.db.dao.MerchantDao;
import com.magic.thai.db.dao.MerchantDetailsDao;
import com.magic.thai.db.domain.Merchant;
import com.magic.thai.db.domain.User;
import com.magic.thai.db.service.MerchantService;
import com.magic.thai.db.service.UserService;
import com.magic.thai.security.UserProfile;
import com.magic.thai.util.PaginationSupport;

@Service("merchantService")
@Transactional
public class MerchantServiceImpl extends ServiceHelperImpl<Merchant> implements MerchantService {

	private static Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);

	@Autowired
	private MerchantDao merchantDao;

	@Autowired
	private UserService userService;

	@Autowired
	private MerchantDetailsDao merchantDetailsDao;

	@Override
	public List<Merchant> list() {
		return merchantDao.list();
	}

	@Override
	public Merchant load(int id) {
		return merchantDao.loadById(id);
	}

	@Override
	public Merchant fetch(int id) {
		Merchant m = load(id);
		m.setDetails(merchantDetailsDao.loadById(id));
		return m;
	}

	@Override
	@Transactional
	public void update(Merchant merchant, UserProfile userprofile) {
		merchantDao.update(merchant);
		merchantDetailsDao.update(merchant.getDetails());
		logger.info("{} is update merchant :{}", userprofile.getUser().getCodeName(), merchant);
	}

	@Override
	@Transactional
	public int create(Merchant merchant, User admin, UserProfile userprofile) {
		merchant.setCreatorId(userprofile.getUser().getId());
		merchant.setCreatorName(userprofile.getUser().getName());
		merchant.setCreatedDate(new Date());

		int id = merchantDao.create(merchant);
		merchant.getDetails().setId(id);
		merchant.getDetails().setLogoPath("/upload/logo/_" + id + ".jpg");
		merchantDetailsDao.create(merchant.getDetails());
		logger.info("{} is create merchant :{}", userprofile.getUser().getCodeName(), merchant);

		userService.create(admin, merchantDao.loadById(id), userprofile);
		logger.info("{} is create admin :{}", userprofile.getUser().getCodeName(), admin);
		return id;
	}

	@Override
	public void enable(Merchant merchant, UserProfile userprofile) {
		merchantDao.enable(merchant.getId());
		logger.info("{} is enable merchant :{}", userprofile.getUser().getCodeName(), merchant);
	}

	@Override
	public void disable(Merchant merchant, UserProfile userprofile) {
		merchantDao.disable(merchant.getId());
		logger.info("{} is disable merchant :{}", userprofile.getUser().getCodeName(), merchant);
	}

	@Override
	public void delete(Merchant merchant, UserProfile userprofile) {
		merchantDao.disable(merchant.getId());
		logger.info("{} is disable merchant :{}", userprofile.getUser().getCodeName(), merchant);
	}

	@Override
	public void enable(int merchantId, UserProfile userprofile) {
		merchantDao.enable(merchantId);
		logger.info("{} is enable merchant, id:{}", userprofile.getUser().getCodeName(), merchantId);
	}

	@Override
	public void disable(int merchantId, UserProfile userprofile) {
		merchantDao.disable(merchantId);
		logger.info("{} is disable merchant, id:{}", userprofile.getUser().getCodeName(), merchantId);
	}

	@Override
	public void delete(int merchantId, UserProfile userprofile) {
		merchantDao.delete(merchantId);
		logger.info("{} is delete merchant, id:{}", userprofile.getUser().getCodeName(), merchantId);
	}

	@Override
	@Transactional(readOnly = true)
	public PaginationSupport getMerchantsPage(String name, int status, int currPage) {
		return merchantDao.getMerchantsPage(name, status, currPage);
	}

}
