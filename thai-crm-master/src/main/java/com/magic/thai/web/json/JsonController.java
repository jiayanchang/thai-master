package com.magic.thai.web.json;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
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
import com.magic.thai.db.service.GoodsService;
import com.magic.thai.db.service.MerchantService;
import com.magic.thai.db.vo.GoodsVo;
import com.magic.thai.db.vo.HotelVo;
import com.magic.thai.db.vo.MerchantVo;

@Controller
@RequestMapping(value = "/json")
public class JsonController {

	private static Logger logger = LoggerFactory.getLogger(JsonController.class);

	@Autowired
	MerchantService merchantService;

	@Autowired
	GoodsService goodsService;

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
		System.out.println("getmerchants");
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
		return gson.toJson(merchantService.load(id));
	}

	@RequestMapping(value = "/goods/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String getgoods(@PathVariable int id) {
		Gson gson = new Gson();
		return gson.toJson(goodsService.load(id));
	}

}
