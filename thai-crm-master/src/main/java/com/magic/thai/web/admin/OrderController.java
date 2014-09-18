package com.magic.thai.web.admin;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.magic.thai.db.dao.OrderLogDao;
import com.magic.thai.db.domain.Order;
import com.magic.thai.db.service.GoodsService;
import com.magic.thai.db.service.MerchantService;
import com.magic.thai.db.service.OrderService;
import com.magic.thai.db.vo.OrderVo;

@Controller
@RequestMapping(value = "/a/order")
public class OrderController {

	@Autowired
	MerchantService merchantService;
	@Autowired
	OrderService orderService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	OrderLogDao orderLogDao;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable int id) {
		ModelAndView modelandView = new ModelAndView("/admin/order/view");
		Order order = orderService.fetch(id);
		modelandView.addObject("order", order);
		modelandView.addObject("logs", orderLogDao.getLogs(id));
		return modelandView;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listUserPage() {
		OrderVo vo = new OrderVo();
		return listPost(vo);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView listPost(@ModelAttribute OrderVo vo) {
		ModelAndView modelandView = new ModelAndView("/admin/order/list");
		modelandView.addObject("vo", vo);
		modelandView.addObject("ps", orderService.getOrderesPage(vo));
		return modelandView;
	}

	@RequestMapping(value = "/waittings", method = RequestMethod.GET)
	public ModelAndView waittings() {
		OrderVo vo = new OrderVo();
		vo.statuses = new Integer[] { Order.Status.NEW };
		return listPost(vo);
	}

}
