package com.magic.thai.web.json;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.magic.thai.db.dao.HotelDao;
import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.Hotel;
import com.magic.thai.db.domain.Merchant;
import com.magic.thai.db.domain.MerchantOrder;
import com.magic.thai.db.domain.User;
import com.magic.thai.db.service.GoodsService;
import com.magic.thai.db.service.MerchantService;
import com.magic.thai.db.service.OrderService;
import com.magic.thai.db.service.UserService;
import com.magic.thai.db.service.strategy.LockManager;
import com.magic.thai.db.vo.GoodsVo;
import com.magic.thai.db.vo.HotelVo;
import com.magic.thai.db.vo.MerchantVo;
import com.magic.thai.db.vo.UserVo;
import com.magic.thai.exception.ThaiException;
import com.magic.thai.security.UserProfile;
import com.magic.thai.web.DataVo;

@Controller
@RequestMapping(value = "/json")
public class JsonController {

	private static Logger logger = LoggerFactory.getLogger(JsonController.class);

	@Autowired
	MerchantService merchantService;

	@Autowired
	UserService userService;

	@Autowired
	GoodsService goodsService;

	@Autowired
	OrderService orderService;

	@Autowired
	HotelDao hotelDao;

	String message;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/merchants", method = RequestMethod.POST)
	@ResponseBody
	public String getmerchants(@RequestParam String title, @RequestParam Integer limitF4list) {
		MerchantVo vo = new MerchantVo();
		vo.nameKeyword = title;
		if (limitF4list != null)
			vo.limitF4list = limitF4list;
		List<Merchant> merchants = merchantService.list(vo);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJson(merchants);
	}

	@RequestMapping(value = "/goodses", method = RequestMethod.POST)
	@ResponseBody
	public String getgoodses(@RequestParam String title, @RequestParam Integer limitF4list) {
		// UserProfile userprofile = (UserProfile)
		// session.getAttribute("userprofile");
		GoodsVo vo = new GoodsVo();
		vo.titleKeyword = title;
		if (limitF4list != null)
			vo.limitF4list = limitF4list;
		List<Goods> goodses = goodsService.list(vo);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJson(goodses);

	}

	@RequestMapping(value = "/hotels")
	@ResponseBody
	public ModelMap gethotels(@RequestParam String name, ModelMap model) {
		HotelVo vo = new HotelVo();
		vo.nameKeyword = name;
		vo.limitF4list = 20;
		List<Hotel> hotels = hotelDao.list(vo);
		model.put("data", DataVo.success(hotels).setMessage("获取成功"));
		return model;
	}

	@RequestMapping(value = "/merchant/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ModelMap getmerchant(@PathVariable int id, ModelMap model) {
		Merchant merchant = merchantService.load(id);
		model.put("data", DataVo.success(merchant).setMessage("获取成功"));
		return model;
	}

	@RequestMapping(value = "/goods/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ModelMap getgoods(@PathVariable int id, ModelMap model) {
		Goods goods = goodsService.load(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", goods.getId());
		map.put("name", goods.getTitle());
		map.put("goodsCount", goods.getGoodsCount());
		map.put("soldCount", goods.getSoldCount());
		model.put("data", DataVo.success(map).setMessage("获取成功"));
		return model;
		// Gson gson = new
		// GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		// return gson.toJson(goods);
	}

	@RequestMapping("/validateUser")
	public ModelMap validateUser(@RequestParam String loginName, @RequestParam String userId, ModelMap model, HttpSession session) {
		User user = userService.findByLoginName(loginName);
		DataVo vo;

		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		if (user == null || !StringUtils.equals(userId, user.getId() + "")) {
			vo = DataVo.success(null);
			List<User> users = userService.list(new UserVo(new Integer[] { User.Status.DISABLED, User.Status.ENABLED }, userprofile
					.getMerchant().getId()));
			if (users.size() > 9) {
				vo = DataVo.fail("用户数量已经用完");
			}
		} else {
			vo = DataVo.fail("当前登录名已存在");
		}
		model.put("data", vo);
		return model;
	}

	@RequestMapping(value = "/lock", method = RequestMethod.POST)
	public ModelMap lock(@RequestParam String orderNo, HttpSession session, ModelMap model) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		try {
			LockManager.lock(orderNo, userprofile, true);
			model.put("data", DataVo.success("锁单成功").setMessage("锁单成功"));
		} catch (ThaiException e) {
			e.printStackTrace();
			model.put("data", DataVo.fail(null).setMessage(e.getMessage()));
		}
		return model;
	}

	@RequestMapping(value = "/unlock", method = RequestMethod.POST)
	public ModelMap unlock(@RequestParam String orderNo, HttpSession session, ModelMap model) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		try {
			LockManager.unlock(orderNo);
			model.put("data", DataVo.success("锁单成功").setMessage("锁单成功"));
		} catch (ThaiException e) {
			e.printStackTrace();
			model.put("data", DataVo.fail(null).setMessage(e.getMessage()));
		}
		return model;
	}

	@RequestMapping(value = "/orderproc", method = RequestMethod.POST)
	public ModelMap procPost(@RequestParam int orderId, @RequestParam String reason, HttpSession session, ModelMap model) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		MerchantOrder order = orderService.load(orderId);
		try {
			if (!LockManager.hasLock(order.getOrderNo()) || LockManager.isOwnLock(order.getOrderNo(), userprofile)
					|| LockManager.isInvalidLock(order.getOrderNo())) {
				LockManager.lock(order.getOrderNo(), userprofile, false);
				orderService.proc(orderId, reason, userprofile);
				model.put("data", DataVo.success("success"));
				LockManager.unlock(order.getOrderNo());
			} else {
				model.put("data", DataVo.fail("failed:" + LockManager.getLock(order.getOrderNo())));
			}
		} catch (ThaiException e) {
			e.printStackTrace();
			model.put("data", DataVo.fail("failed:" + e.getMessage()));
		}

		return model;
	}

	@RequestMapping("/auditingGoodsCount")
	public ModelMap auditingGoodsCount(HttpSession session, ModelMap model) {
		DataVo vo = DataVo.success(goodsService.getAuditingGoodsCount(null));
		model.put("data", vo);
		return model;
	}

	@RequestMapping("/auditingOrderCount")
	public ModelMap auditingOrderCount(HttpSession session, ModelMap model) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		DataVo vo = DataVo.success(orderService.auditingOrderCount(userprofile.getUser()));
		model.put("data", vo);
		return model;
	}

}
