package com.magic.thai.web.front;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.magic.thai.db.dao.GoodsDao;
import com.magic.thai.db.dao.MerchantOrderNotesDao;
import com.magic.thai.db.domain.MerchantOrder;
import com.magic.thai.db.service.InterfaceOrderService;
import com.magic.thai.db.service.OrderService;
import com.magic.thai.security.UserProfile;

@Controller
@RequestMapping(value = "/f/order/print")
public class FrontCardPrintController {

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

	@RequestMapping(value = "/transfer_card/{id}")
	public ModelAndView transferCard(@PathVariable int id, HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		ModelAndView modelandView = new ModelAndView("/front/order/print/transfer_card");
		MerchantOrder order = orderService.fetch(id);
		modelandView.addObject("order", order);
		modelandView.addObject("userprofile", userprofile);
		modelandView.addObject("now", DateFormatUtils.format(new Date(), "MM/dd/yyyy"));
		return modelandView;
	}

	@RequestMapping(value = "/pick_card/{id}")
	public ModelAndView pickCard(@PathVariable int id, HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		ModelAndView modelandView = new ModelAndView("/front/order/print/pick_card");
		MerchantOrder order = orderService.fetch(id);
		modelandView.addObject("order", order);
		modelandView.addObject("userprofile", userprofile);
		modelandView.addObject("now", DateFormatUtils.format(new Date(), "MM/dd/yyyy"));
		return modelandView;
	}

	@RequestMapping(value = "/invoice_card/{id}")
	public ModelAndView invoiceCard(@PathVariable int id, HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		ModelAndView modelandView = new ModelAndView("/front/order/print/invoice_card");
		MerchantOrder order = orderService.fetch(id);
		modelandView.addObject("order", order);
		modelandView.addObject("userprofile", userprofile);
		modelandView.addObject("now", DateFormatUtils.format(new Date(), "MM/dd/yyyy"));
		return modelandView;
	}
}
