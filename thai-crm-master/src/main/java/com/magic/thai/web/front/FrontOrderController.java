package com.magic.thai.web.front;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.magic.thai.db.dao.GoodsDao;
import com.magic.thai.db.dao.MerchantOrderNotesDao;
import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.MerchantOrder;
import com.magic.thai.db.service.InterfaceOrderService;
import com.magic.thai.db.service.OrderService;
import com.magic.thai.db.vo.GoodsVo;
import com.magic.thai.db.vo.OrderVo;
import com.magic.thai.exception.ThaiException;
import com.magic.thai.security.UserProfile;
import com.magic.thai.web.DataVo;
import com.magic.thai.web.ws.vo.CreateOrderVo;

@Controller
@RequestMapping(value = "/f/order")
public class FrontOrderController {

	@Autowired
	OrderService orderService;
	@Autowired
	GoodsDao goodsDao;

	@Autowired
	InterfaceOrderService interfaceOrderService;

	@Autowired
	MerchantOrderNotesDao merchantOrderNotesDao;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/{id}")
	public ModelAndView view(@PathVariable int id) {
		ModelAndView modelandView = new ModelAndView("/front/order/view");
		MerchantOrder order = orderService.fetch(id);
		modelandView.addObject("order", order);
		modelandView.addObject("logs", merchantOrderNotesDao.getLogs(id));
		return modelandView;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpSession session) {
		OrderVo vo = new OrderVo();
		return listPost(vo, session);
	}

	@RequestMapping(value = "/audits", method = RequestMethod.GET)
	public ModelAndView audits(HttpSession session) {
		OrderVo vo = new OrderVo();
		vo.setStatus(MerchantOrder.Status.NEW);
		return listPost(vo, session);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView listPost(@ModelAttribute OrderVo orderVo, HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		ModelAndView modelandView = new ModelAndView("/front/order/list");
		modelandView.addObject("ps", orderService.getOrderesPage(orderVo, userprofile.getUser().getMerchantId()));
		modelandView.addObject("vo", orderVo);
		return modelandView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model, HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		model.addAttribute("goodses",
				goodsDao.list(new GoodsVo(new Integer[] { Goods.Status.DEPLOYED }, userprofile.getMerchant().getId())));
		model.addAttribute("createOrderVo", new CreateOrderVo());
		return "/front/order/add";
	}

	@RequestMapping(value = "/add/process", method = RequestMethod.POST)
	public String addprocess(@ModelAttribute CreateOrderVo createOrderVo, ModelMap model, HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		try {
			interfaceOrderService.create(createOrderVo, userprofile);
		} catch (ThaiException e) {
			model.addAttribute("message", e.getMessage());
			model.addAttribute("goodses",
					goodsDao.list(new GoodsVo(new Integer[] { Goods.Status.DEPLOYED }, userprofile.getMerchant().getId())));
			e.printStackTrace();
			return "/front/order/add";
		}
		return "redirect:/f/order/list";
	}

	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public ModelMap confirm(@RequestParam int id, @RequestParam String driverName, @RequestParam String driverMobile, HttpSession session,
			ModelMap model) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		try {

			MerchantOrder merchantOrder = orderService.load(id);
			merchantOrder.setDriverMobile(driverMobile);
			merchantOrder.setDriverName(driverName);
			orderService.confirm(merchantOrder, userprofile);
			model.put("data", DataVo.success(null).setMessage("订单确认成功"));
		} catch (ThaiException e) {
			e.printStackTrace();
			model.put("data", DataVo.fail(null).setMessage(e.getMessage()));
		}
		return model;
	}

}
