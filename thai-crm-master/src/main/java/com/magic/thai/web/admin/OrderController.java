package com.magic.thai.web.admin;

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
import org.springframework.web.servlet.ModelAndView;

import com.magic.thai.db.dao.MerchantOrderGoodsPickupDao;
import com.magic.thai.db.dao.MerchantOrderNotesDao;
import com.magic.thai.db.domain.ChannelOrder;
import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.MerchantOrder;
import com.magic.thai.db.service.ChannelOrderService;
import com.magic.thai.db.service.GoodsService;
import com.magic.thai.db.service.InterfaceOrderService;
import com.magic.thai.db.service.MerchantService;
import com.magic.thai.db.service.OrderService;
import com.magic.thai.db.vo.GoodsVo;
import com.magic.thai.db.vo.MerchantVo;
import com.magic.thai.db.vo.OrderVo;
import com.magic.thai.exception.ThaiException;
import com.magic.thai.security.UserProfile;
import com.magic.thai.web.ws.vo.CreateOrderVo;

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
	MerchantOrderNotesDao merchantOrderNotesDao;
	@Autowired
	ChannelOrderService channelOrderService;
	@Autowired
	InterfaceOrderService interfaceOrderService;
	@Autowired
	MerchantOrderGoodsPickupDao merchantOrderGoodsPickupDao;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable int id) {
		ModelAndView modelandView = new ModelAndView("/admin/order/view");
		MerchantOrder order = orderService.fetch(id);
		modelandView.addObject("order", order);
		modelandView.addObject("isNeedsPickup", order.getGoodses().get(0).isNeedsPickup());
		modelandView.addObject("pickup", merchantOrderGoodsPickupDao.loadByMogId(order.getGoodses().get(0).getId()));
		modelandView.addObject("logs", merchantOrderNotesDao.getLogs(id));
		return modelandView;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listUserPage() {
		OrderVo vo = new OrderVo();
		vo.orderType = ChannelOrder.OrderType.CHANNEL_ORDER;
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
		vo.orderType = ChannelOrder.OrderType.CHANNEL_ORDER;
		vo.statuses = new Integer[] { MerchantOrder.Status.NEW };
		return listPost(vo);
	}

	@RequestMapping(value = "/channelorders")
	public ModelAndView channelorders(@ModelAttribute OrderVo vo) {
		ModelAndView modelandView = new ModelAndView("/admin/order/channelorders");
		modelandView.addObject("vo", vo);
		modelandView.addObject("ps", channelOrderService.getOrderesPage(vo));
		return modelandView;
	}

	@RequestMapping(value = "/channelorder/{id}")
	public ModelAndView channelorders(@PathVariable int id) {
		ModelAndView modelandView = new ModelAndView("/admin/order/channel_order_view");
		modelandView.addObject("channelOrder", channelOrderService.fetch(id));
		return modelandView;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model, HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		model.addAttribute("goodses",
				goodsService.list(new GoodsVo(new Integer[] { Goods.Status.DEPLOYED }, userprofile.getMerchant().getId())));
		
		model.addAttribute("merchants", merchantService.list(new MerchantVo()));
		model.addAttribute("createOrderVo", new CreateOrderVo());
		return "/admin/order/add";
	}

	@RequestMapping(value = "/add/process", method = RequestMethod.POST)
	public String addprocess(@ModelAttribute CreateOrderVo createOrderVo, ModelMap model, HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		try {
			interfaceOrderService.adminCreateOrder(createOrderVo, userprofile);
		} catch (ThaiException e) {
			model.addAttribute("message", e.getMessage());
			model.addAttribute("merchants", merchantService.list(new MerchantVo()));
			e.printStackTrace();
			return "/admin/order/add";
		}
		return "redirect:/a/order/list";
	}

}
