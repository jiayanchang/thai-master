package com.magic.thai.web.admin;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.GoodsPriceSegment;
import com.magic.thai.db.service.GoodsService;

@Controller
@RequestMapping(value = "/a/goods")
public class GoodsController {

	@Autowired
	GoodsService goodsService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/list")
	public ModelAndView listPost(@RequestParam String title, @RequestParam String dept, @RequestParam String arr,
			@RequestParam Integer status, @RequestParam Integer page) {
		ModelAndView modelandView = new ModelAndView("/admin/goods/list");
		modelandView.addObject("ps", goodsService.getGoodsesPage(title, dept, arr, status == null ? -1 : status, page == null ? 1 : page));
		return modelandView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() {
		ModelAndView modelAndView = new ModelAndView("/admin/goods/add");
		Goods goods = new Goods();
		goods.getSegments().add(new GoodsPriceSegment());
		modelAndView.addObject("goods", goods);
		return modelAndView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addProcess(@ModelAttribute Goods goods) {
		ModelAndView modelAndView = new ModelAndView("/admin/goods/list");

		modelAndView.addObject("goods", goods);
		return modelAndView;
	}
}
