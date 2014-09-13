package com.magic.thai.web.front;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.magic.thai.db.domain.Order;
import com.magic.thai.db.service.OrderService;
import com.magic.thai.security.UserProfile;

@Controller
@RequestMapping(value = "/f/order")
public class FrontOrderController {

	// @Autowired
	OrderService orderService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/list")
	public ModelAndView listPost(@RequestParam String title, @RequestParam String dept, @RequestParam String arr,
			@RequestParam Integer status, @RequestParam Integer page, HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");

		ModelAndView modelandView = new ModelAndView("/admin/order/list");
		modelandView.addObject("ps", orderService.getOrderesPage(title, dept, arr, status == null ? -1 : status, page == null ? 1 : page,
				userprofile.getUser().getMerchantId()));
		return modelandView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() {
		ModelAndView modelAndView = new ModelAndView("/front/order/add");
		Order order = new Order();
		modelAndView.addObject("order", order);
		return modelAndView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addProcess(@ModelAttribute Order order) {
		ModelAndView modelAndView = new ModelAndView("/front/order/list");

		modelAndView.addObject("order", order);
		return modelAndView;
	}
}
