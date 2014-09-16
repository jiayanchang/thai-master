package com.magic.thai.web.json;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import com.magic.thai.db.domain.User;
import com.magic.thai.db.service.GoodsService;
import com.magic.thai.db.service.MerchantService;
import com.magic.thai.db.service.OrderService;
import com.magic.thai.db.service.UserService;
import com.magic.thai.db.vo.GoodsVo;
import com.magic.thai.db.vo.HotelVo;
import com.magic.thai.db.vo.MerchantVo;
import com.magic.thai.exception.GoodsStatusException;
import com.magic.thai.exception.NoPermissionsException;
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

	@RequestMapping(value = "/hotels", method = RequestMethod.POST)
	@ResponseBody
	public String gethotels(@RequestParam String name, @RequestParam Integer limitF4list) {
		HotelVo vo = new HotelVo();
		vo.nameKeyword = name;
		if (limitF4list != null)
			vo.limitF4list = limitF4list;
		List<Hotel> hotels = hotelDao.list(vo);
		Gson gson = new Gson();
		// Gson gson = new
		// GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJson(hotels);
	}

	@RequestMapping(value = "/merchant/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String getmerchant(@PathVariable int id) {
		Gson gson = new Gson();
		Merchant merchant = merchantService.load(id);
		System.out.println(gson.toJson(merchant));
		return gson.toJson(merchant);
	}

	@RequestMapping(value = "/goods/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String getgoods(@PathVariable int id) {
		Gson gson = new Gson();
		return gson.toJson(goodsService.load(id));
	}

	@RequestMapping(value = "/goods/pass/{id}", method = RequestMethod.POST)
	@ResponseBody
	public String procAuditGoods(@PathVariable int id, HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		DataVo result;
		try {
			goodsService.pass(id, userprofile);
			result = DataVo.success(id);
		} catch (NoPermissionsException e) {
			e.printStackTrace();
			result = DataVo.fail("您不能审核该商品");
		} catch (GoodsStatusException e) {
			e.printStackTrace();
			result = DataVo.fail(e.getMessage());
		}
		Gson gson = new Gson();
		return gson.toJson(result);
	}

	@RequestMapping("/show")
	public String toShow(ModelMap model) {
		User user = new User();
		user.setName("中文");
		model.put("user", user);
		return "/show";
	}

	@RequestMapping("/validateLoginName")
	public ModelMap validateLoginName(@RequestParam String loginName, @RequestParam Integer userId, ModelMap model) {
		User user = userService.findByLoginName(loginName);
		DataVo vo;
		if (user == null || (userId != null && userId.intValue() == user.getId())) {
			vo = DataVo.success(null);
		} else {
			vo = DataVo.fail("当前登录名已存在");
		}
		model.put("data", vo);
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
